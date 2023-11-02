public class Node {
    private String word;
    private Node rightChild, leftChild;

    Node(String value){
        word = value;
        rightChild = null;
        leftChild = null;

    }

    public String getWord(){
        return this.word;
    }

    public void setWord(String data){
        word = data;
    }

    public Node getRightChild(){
        return this.rightChild;
    }

    public void setRightChild(Node root){
        rightChild = root;
    }


    public Node getLeftChild(){
        return this.leftChild;
    }

    public void setLeftChild(Node root){
        leftChild = root;
    }

}
