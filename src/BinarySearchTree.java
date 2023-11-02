public class BinarySearchTree {
    Node root;

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

}
