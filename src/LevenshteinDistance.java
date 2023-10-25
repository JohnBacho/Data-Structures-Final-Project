
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
        Final.removeAll(Final); // clears the Final list before every run
        int number = 0;
        System.out.println("Type a word or sentence");
        Scanner scan = new Scanner(System.in);
        String word = scan.nextLine();
        // String word = WORD.toLowerCase();

        String NEW1[] = word.split("\\s+|(?=[.,?])");
        int SizeOfArray = NEW1.length; // this is used down below to find out when the array has gone through every
                                       // word

        // this splits the sentance into words and then puts those words in an array
        // called NEW1[]

        for (String word1 : NEW1) { // this line goes through each word in the sentance that the user entered // it
                                    // then calls the scanheap class
            number++;
            LevenshteinDistance.Scanheap(word1, 0);
            if (number == SizeOfArray) { // used to find when the array has gone through every item
                printall(); // if it has gone through every word in the array it calls the print all
                            // function to print every word in the final array
            }

        }
    }

    public static void Scanheap(String word1, int Tolerance) {
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
         * the point of this method is to first check if the word is spelled correctly
         * and if the
         * word isn't it then calls the calculateDistance whihc find the edit distance
         */

        boolean Checker = Dictionary.contains(word1);// checks if the word exist in the dictionary

        if (Checker == false) { // if it doesn't exist it goes through the edit distance alogrithm
            counter = 0;
            GoThroughDictionary(word1, Tolerance);
        } else {// if the word is spelled correctly it prints the word and a check mark
            System.out.println(word1 + " ✅");
            if (word1.equals(".") || word1.equals(",") || word1.equals("?")) {
                Final.add(word1);
            } else
                Final.add(" " + word1);
        }
    }

    public static void GoThroughDictionary(String word1, int Tolerance) {
        for (String word2 : Dictionary) {
            counter++;
            if (Dictionary.size() == counter) {
                Tolerance++;
                // System.out.println(Tolerance);
                if (Tolerance != 3) {
                    Scanheap(word1, Tolerance);
                } else if (Tolerance == 3) {
                    getTop5Suggestions("GAVIN", 10, true, word1);
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
            phone(word1, word2);
        }

    }

    public static void phone(String word1, String word2) {
        int distance = calculateDistance(word1, word2);

        if (distance < 3) {/*
                            * if the edit distance is less than 3 it returns the incorrect spelled word and
                            * then it shows that word it suggust to correct it with
                            */
            getTop5Suggestions(word2, distance, false, word1);
        }
    }

    public static int calculateDistance(String word1, String word2) {
        int len1 = word1.length();
        int len2 = word2.length();
        /*
         * this method finds the minimum edit distance between word 1 and word 2
         * to find the minimum edit distance between the 2 it trys inserting a charter
         * deleting a charter and subsituting a charter
         * it is trying to find out how many actions need to be performed to make word1
         * look like word2
         */

        if (len1 == 0) {
            return len2;
        }

        if (len2 == 0) {
            return len1;
        }

        if (word1.charAt(0) == word2.charAt(0)) {
            return calculateDistance(word1.substring(1), word2.substring(1));
        }

        int insertion = calculateDistance(word1, word2.substring(1)) + 1;
        int deletion = calculateDistance(word1.substring(1), word2) + 1;
        int substitution = calculateDistance(word1.substring(1), word2.substring(1)) + 1;

        return Math.min(Math.min(insertion, substitution), deletion);

    }

    public static void AddToTXT(String item) { // this Just appends the word entered into the txt file :)
        System.out.println(item + " added to dictionary");
        try (BufferedWriter writer = new BufferedWriter((new FileWriter("wiki-100k.txt", true)))) {
            writer.write("\n" + item);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getTop5Suggestions(String word2, int distance, boolean STOP, String word1) {
        if (distance == 1) {
            list.add(0, word2);
        } else if (distance == 2)
            list.add(word2);
        if (STOP) {
            if (list.size() > 5) {
                System.out.println("Suggestions for " + word1);
                for (int i = 0; i < 5; i++) {
                    System.out.println(i + 1 + ": " + list.get(i));
                }
                System.out.println("6: More suggestions");
                choose(list, 6);
            }
            if (list.size() <= 5 && !list.isEmpty()) {
                System.out.println("Suggestions for " + word1);
                for (int i = 0; i < list.size(); i++) {
                    System.out.println(i + 1 + ": " + list.get(i));
                }
                choose(list, list.size());
            } else if (list.isEmpty())
                System.out.println("No suggestions found");
        }
    }

    public static void choose(ArrayList<String> list, int numberofitems) {
        Scanner lookfor = new Scanner(System.in);
        boolean validInput = false;
        boolean STOP = false;

        while (!validInput) {
            System.out.println("Enter a number in the range to accept the suggestion");
            int reduce = lookfor.nextInt();
            if (STOP & reduce < (numberofitems - 4) || reduce > numberofitems) {
                System.out.println("Invalid input. Please enter a valid number.");
            } else if (reduce < (numberofitems - 5) || reduce > numberofitems) {
                System.out.println("Invalid input. Please enter a valid number.");
            } else if (STOP) {
                reduce--;
                Final.add(" " + list.get(reduce));
                validInput = true;
            } else if (reduce == numberofitems && reduce != list.size()) {
                for (int i = numberofitems - 1; i < numberofitems + 4 && i < list.size(); i++) {
                    System.out.println(i + 1 + ": " + list.get(i));
                }
                if (7 + numberofitems >= list.size()) {
                    numberofitems = numberofitems + 4;
                    STOP = true;
                } else {
                    System.out.println(numberofitems + 5 + ": More suggestions");
                    numberofitems = numberofitems + 5;
                }
            } else if (!STOP) {
                reduce--;
                Final.add(" " + list.get(reduce));
                validInput = true;
            }
        }
    }

    public static void printall() {
        System.out.print("Your corrected sentance: ");
        for (int i = 0; i < Final.size(); i++) {
            if (i == 0) {
                String r = Final.get(0);
                System.out.print(r.substring(1, r.length()));
            } else {
                System.out.print(Final.get(i));
            }
        }
        System.out.println("");
    }

}