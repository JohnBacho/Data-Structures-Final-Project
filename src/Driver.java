import java.util.HashSet;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        while (true) {
            System.out.println(
                    "1. Main \n2. Recursive\n3. Dynamic Programming\n4. Add a word to the dictionary\n5. Hashset Search Time\n6. Binary Search Tree Time");
            Scanner scan = new Scanner(System.in);
            int number = scan.nextInt();
            if (number != 1 && number != 2 && number != 3 && number != 4 && number != 5 && number != 6) {
                System.out.println("invalid input");
            } else if (number == 1) {
                LevenshteinDistance.start();
            } else if (number == 2) {
                Recursive.start();
            } else if (number == 3) {
                dynamic_programming.start();
            } else if (number == 4) {
                System.out.println("Enter the word you'd like to add");
                Scanner look = new Scanner(System.in);
                String letter = look.nextLine();
                LevenshteinDistance.AddToTXT(letter);
            } else if (number == 5) {
                System.out.println("Enter word");
                Hashset.ScanHashSet();
            } else if (number == 6) {
                BinarySearchTree.readTextFile();
                System.out.println("Enter word");
                BinarySearchTree.ScanBinarySearchTree();
            }

        }
    }
}