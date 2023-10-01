import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        int number = 3;
        while (number != 0) {
            System.out.println("Enter a 1 to start program. Enter a 2 to add to dictionary");
            Scanner scan = new Scanner(System.in);
            number = scan.nextInt();

            if (number == 1) {
                LevenshteinDistance.start();
            }
            if (number == 2) {
                System.out.println("Enter the word you'd like to add");
                Scanner look = new Scanner(System.in);
                String letter = look.nextLine();
                LevenshteinDistance.add(letter);
            }
        }
    }
}
