import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        while (true) {
            System.out.println("Enter a 1 to start program. Enter a 2 to add a word to the dictionary");
            Scanner scan = new Scanner(System.in);
            int number = scan.nextInt();
            if (number != 1 && number != 2) {
                System.out.println("invaild input");
            } else if (number == 1) {
                LevenshteinDistance.start();
            } else if (number == 2) {
                System.out.println("Enter the word you'd like to add");
                Scanner look = new Scanner(System.in);
                String letter = look.nextLine();
                LevenshteinDistance.AddToTXT(letter);
            }
        }
    }
}