import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class BinarySearchTree {
    static Node root;

    private static void insert(String word, Node root){
        if( root == null){
            return;
        }
        if (word.compareTo(root.getWord())<0){
            if(root.getLeftChild() == null){
                root.setLeftChild(new Node(word));
            }
            else
                insert(word,root.getLeftChild());
        }
        else if ( word.compareTo(root.getWord())>0){
            if (root.getRightChild() == null){
                root.setRightChild(new Node(word));
            }
            else
                insert(word,root.getRightChild());
        }
    }

   private static Node search(Node root, String word){
        if(root == null || root.getWord().equals(word)){
            return root;
        }
        if(word.compareTo(root.getWord()) > 0){
            return search(root.getRightChild(), word);
        }
        else {
            return  search(root.getLeftChild(),word);
        }
   }

   public static void readTextFile(){
       BufferedReader reader;

       try {
           reader = new BufferedReader(new FileReader("wiki-100k.txt"));
           String line = reader.readLine();


           while(line != null){
               Node wordNode = new Node(line);
               insert(line, wordNode);

           }

        reader.close();
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

    public static void ScanBinarySearchTree(){
        Scanner scan = new Scanner(System.in);
        String word = scan.nextLine();
        long start = System.nanoTime();
        Node result = search(root, word);
        long end = System.nanoTime();
        System.err.println(end - start + " NS");
    }

}
