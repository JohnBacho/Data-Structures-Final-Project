
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

public class LevenshteinDistance {
    private static HashSet<String> Dictionary = new HashSet<>();
    private static int counter;

    public static void start() {
        System.out.println("Type a word or sentance");
        Scanner scan = new Scanner(System.in);
        String word = scan.nextLine();
        // String word = WORD.toLowerCase();

        String NEW1[] = word.split(" ");
        // this splits the sentance into words and then puts those words in an array
        // called NEW1[]

        for (String word1 : NEW1) { // this line goes through each word in the sentance that the user entered
            LevenshteinDistance.Scanheap(word1, 0);// it then calls the scanheap class
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
         * the point of this class is to first check if the word is right and if the
         * word isnt it then uses the edit distance
         */

        boolean Checker = Dictionary.contains(word1);// checks if the word exist in the dictionary

        if (Checker == false) { // if it doesn't exist it goes through the edit distance alogrithm
            counter = 0;
            for (String word2 : Dictionary) {
                counter++;
                if (Dictionary.size() == counter) {
                    Tolerance++;
                    System.out.println(Tolerance);
                    if (Tolerance != 3) {
                        Scanheap(word1, Tolerance);
                    } else if (Tolerance == 3) {
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
                                    .equals(word2.substring(word2.length() - 2, word2.length()))
                            && word1.length() > 3) {
                        continue;
                    }
                }

                int distance = calculateDistance(word1, word2);

                if (distance < 3) { /*
                                     * if the edit distance is less than 3 it returns the incorrect spelled word and
                                     * then it shows that word it suggust to correct it with
                                     */
                    System.out.println(
                            "Levenshtein distance between '" + word1 + "' and '" + word2 + "' is: " + distance);
                }
            }

        } else {// if the word is spelled correctly it prints the word and a check mark
            System.out.println(word1 + " ✅");
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
        try (BufferedWriter writer = new BufferedWriter((new FileWriter("wiki-100k.txt", true)))) {
            writer.write("\n" + item);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}