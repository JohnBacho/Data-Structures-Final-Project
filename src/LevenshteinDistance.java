
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class LevenshteinDistance {
    private static HashSet<String> Dictionary = new HashSet<>();
    private static int counter;
    private static ArrayList<String> list = new ArrayList<>();
    private static ArrayList<String> Final = new ArrayList<>();

    public static void start() {
        Final.removeAll(Final); // Clears the Final list before every run
        int wordCount = 0;
        System.out.println("Type a word or sentence");
        Scanner scanner = new Scanner(System.in);
        String inputSentence = scanner.nextLine();

        String words[] = inputSentence.split("\\s+|(?=[.,?!])");
        int numberOfWords = words.length; // This is used later to determine when the array has gone through every word

        // Split the sentence into words and put those words in an array called "words"

        for (String word : words) {
            wordCount++;
            LevenshteinDistance.ScanHashSet(word, 0);
            if (wordCount == numberOfWords) { // Used to determine when the array has gone through every item
                PrintCorrectedSentence(); // If it has gone through every word in the array, call the printAll function
                                          // to
                // print every word in the final array
            }
        }
    }

    public static void ScanHashSet(String Word1, int tolerance) {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("wiki-100k.txt"));
            String line = reader.readLine();

            while (line != null) {
                Dictionary.add(line);
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
         * The purpose of this method is to first check if the word is spelled
         * correctly,
         * and if the word isn't spelled correctly, it then calls the calculateDistance
         * method to find the edit distance.
         */

        boolean isCorrectlySpelled = Dictionary.contains(Word1); // Checks if the word exists in the dictionary

        if (!isCorrectlySpelled) { // If it doesn't exist, it goes through the edit distance algorithm
            counter = 0;
            GoThroughDictionary(Word1, tolerance);
        } else { // If the word is spelled correctly, it prints the word and a check mark
            System.out.println(Word1 + " ✅");
            if (Word1.equals(".") || Word1.equals(",") || Word1.equals("?")) {
                Final.add(Word1);
            } else {
                Final.add(" " + Word1);
            }
        }
    }

    public static void GoThroughDictionary(String word1, int Tolerance) {
        for (String word2 : Dictionary) {
            counter++;
            if (Dictionary.size() == counter) {
                Tolerance++;
                // System.out.println(Tolerance);
                if (Tolerance != 3) {
                    ScanHashSet(word1, Tolerance);
                } else if (Tolerance == 3) {
                    getTopSuggestions("GAVIN", 10, true, word1);
                    list.removeAll(list);
                    Tolerance = 0;
                    break;
                }
            }
            if (Math.abs(word1.length() - word2.length()) != Tolerance) {
                continue;
            }
            if (word1.length() > 3 && word2.length() > 3) {
                if (!word1.substring(0, 2).equals(word2.substring(0, 2))
                        && !word1.substring(word1.length() - 2, word1.length())
                                .equals(word2.substring(word2.length() - 2, word2.length()))) {
                    continue;
                }
            }
            SpellChecker(word1, word2);
        }
    }

    public static void SpellChecker(String word1, String word2) {
        int distance = calculateDistance(word1, word2);

        if (distance < 3) {/*
                            * if the edit distance is less than 3 it returns the incorrect spelled word and
                            * then it shows that word it suggust to correct it with
                            */
            getTopSuggestions(word2, distance, false, word1);
        }
    }

    public static int calculateDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int cost = (word1.charAt(i - 1) != word2.charAt(j - 1)) ? 1 : 0;
                dp[i][j] = Math.min(
                        Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                        dp[i - 1][j - 1] + cost);
            }
        }
        return dp[m][n];
    }

    public static void AddToTXT(String item) { // this Just appends the word entered into the txt file :)
        System.out.println(item + " added to dictionary");
        try (BufferedWriter writer = new BufferedWriter((new FileWriter("wiki-100k.txt", true)))) {
            writer.write("\n" + item);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getTopSuggestions(String word1, int editDistance, boolean shouldStop, String word2) {
        if (editDistance == 1) {
            list.add(0, word1);
        } else if (editDistance == 2) {
            list.add(word1);
        }

        if (shouldStop) {
            if (list.size() > 5) {
                System.out.println("Suggestions for " + word2);
                for (int i = 0; i < 5; i++) {
                    System.out.println(i + 1 + ": " + list.get(i));
                }
                System.out.println("6: More suggestions");
                choose(list, 6);
            }
            if (list.size() <= 5 && !list.isEmpty()) {
                System.out.println("Suggestions for " + word2);
                for (int i = 0; i < list.size(); i++) {
                    System.out.println(i + 1 + ": " + list.get(i));
                }
                choose(list, list.size());
            } else if (list.isEmpty()) {
                System.out.println("No suggestions found");
                Final.add(" " + word2);
            }
        }
    }

    public static void choose(ArrayList<String> suggestions, int numberOfSuggestions) {
        Scanner inputScanner = new Scanner(System.in);
        boolean validInput = false;
        boolean STOP = false;

        while (!validInput) {
            System.out.println("Enter a number within the range to accept the suggestion");
            int choice = inputScanner.nextInt();

            if (STOP && (choice < (numberOfSuggestions - 4) || choice > numberOfSuggestions)) {
                System.out.println("Invalid input. Please enter a valid number.");
            } else if (choice < (numberOfSuggestions - 5) || choice > numberOfSuggestions) {
                System.out.println("Invalid input. Please enter a valid number.");
            } else if (STOP) {
                choice--;
                Final.add(" " + suggestions.get(choice));
                validInput = true;
            } else if (choice == numberOfSuggestions && choice != suggestions.size()) {
                for (int i = numberOfSuggestions - 1; i < numberOfSuggestions + 4 && i < suggestions.size(); i++) {
                    System.out.println(i + 1 + ": " + suggestions.get(i));
                }
                if (numberOfSuggestions + 6 >= suggestions.size()) {
                    numberOfSuggestions += 4;
                    STOP = true;
                } else {
                    System.out.println(numberOfSuggestions + 5 + ": More suggestions");
                    numberOfSuggestions += 5;
                }
            } else if (!STOP) {
                choice--;
                Final.add(" " + suggestions.get(choice));
                validInput = true;
            }
        }
    }

    public static void PrintCorrectedSentence() {
        System.out.print("Your corrected sentance: ");
        for (int i = 0; i < Final.size(); i++) { // goes through all the words that are stored in final
            if (i == 0) { // Prints out the first word and removes the space at the start
                String r = Final.get(0);
                System.out.print(r.substring(1, r.length()));
            } else {
                System.out.print(Final.get(i)); // prints out the word
            }
        }
        System.out.println("");
    }

}