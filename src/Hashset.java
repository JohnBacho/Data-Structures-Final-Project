import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

public class Hashset {
    private static HashSet<String> Dictionary = new HashSet<>();
    public static void readtextfile() {
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
}
public static void ScanHashSet(){
    Scanner scan = new Scanner(System.in);
    String word = scan.nextLine();
    long start = System.nanoTime();
    Dictionary.contains(word);
    long end = System.nanoTime();
    System.err.println(end - start + " NS");
}
}