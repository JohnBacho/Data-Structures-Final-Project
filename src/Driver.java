import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        while (true) {
            System.out.println("1. Word checker \n2. Testing\n3. Dynamic Programming\n4. Add a word to the dictionary");
            Scanner scan = new Scanner(System.in);
            int number = scan.nextInt();
            if (number != 1 && number != 2 && number != 3 && number != 4) {
                System.out.println("invaild input");
            } else if (number == 1) {
                LevenshteinDistance.start();
            } else if (number == 2) {
                Testing.start();
            } else if (number == 3) {
                dynamic_programming.start();
            } else if (number == 4) {
                System.out.println("Enter the word you'd like to add");
                Scanner look = new Scanner(System.in);
                String letter = look.nextLine();
                LevenshteinDistance.AddToTXT(letter);
            }
        }
    }
}