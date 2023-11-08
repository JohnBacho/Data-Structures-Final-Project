import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Trie {

    private static TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public static void readTextFile() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("wiki-100k.txt"));
            String line = reader.readLine();

            while (line != null) {
                insert(line);
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void insert(String word) {
        HashMap<Character, TrieNode> children = root.getChildren();
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            TrieNode node;
            if (children.containsKey(c)) {
                node = children.get(c);
            } else {
                node = new TrieNode(c);
                children.put(c, node);
            }
            children = node.getChildren();

            if (i == word.length() - 1) {
                node.setLeaf(true);
            }
        }
    }

    public boolean search(String word) {
        HashMap<Character, TrieNode> children = root.getChildren();

        TrieNode node = null;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (children.containsKey(c)) {
                node = children.get(c);
                children = node.getChildren();
            } else {
                node = null;
                break;
            }
        }

        if (node != null && node.isLeaf()) {
            //System.out.println("found");
            return true;
        } else {
            //System.err.println("Not found");
            return false;
        }
    }

}

// contrubited by Amogh Avadhani