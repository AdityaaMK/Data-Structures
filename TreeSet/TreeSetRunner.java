import java.util.Random;

public class TreeSetRunner {
    public static void main(String[] args) {
        new TreeSetRunner();
    }

    public TreeSetRunner() {
        TreeSet<Character> tree = new TreeSet<>();
        Random rand = new Random();
        String original = "";

        double avg = 0;
        for (int i = 0; i < 26; i++) {
            char temp = (char) (rand.nextInt(26) + 'a');
            avg += (double) temp;
            tree.add(temp);
            original += temp + "";
        }
        avg /= 26;
        System.out.println("Average ASCII Value: " + avg);
        System.out.println("Average ASCII Character: " + (char) (avg));
        System.out.println("TreeSet Size: " + tree.size());
        System.out.println(original);
        System.out.println("Original Set - PreOrder: " + tree.preOrder());
        System.out.println("Original Set - InOrder: " + tree.inOrder());
        System.out.println("Original Set - PostOrder: " + tree.postOrder());

        TreeSet<Character> preOrderCopy = tree.preOrderCopy();
        System.out.println("-------------------------------------------------------------");
        System.out.println("PreOrder Copy - PreOrder: " + preOrderCopy.preOrder());
        System.out.println("PreOrder Copy - InOrder: " + preOrderCopy.inOrder());
        System.out.println("PreOrder Copy - PostOrder: " + preOrderCopy.postOrder());

        TreeSet<Character> inOrderCopy = tree.inOrderCopy();
        System.out.println("-------------------------------------------------------------");
        System.out.println("InOrder Copy - PreOrder: " + inOrderCopy.preOrder());
        System.out.println("InOrder Copy - InOrder: " + inOrderCopy.inOrder());
        System.out.println("InOrder Copy - PostOrder: " + inOrderCopy.postOrder());

        TreeSet<Character> postOrderCopy = tree.inOrderCopy();
        System.out.println("-------------------------------------------------------------");
        System.out.println("PostOrder Copy - PreOrder: " + postOrderCopy.preOrder());
        System.out.println("PostOrder Copy - InOrder: " + postOrderCopy.inOrder());
        System.out.println("PostOrder Copy - PostOrder: " + postOrderCopy.postOrder());

        System.out.println("-------------------------------------------------------------");
        System.out.println("Rotate Right 1");
        tree.rotateRight();
        System.out.println("Original Set - PreOrder: " + tree.preOrder());
        System.out.println("Original Set - InOrder: " + tree.inOrder());
        System.out.println("Original Set - PostOrder: " + tree.postOrder());

        System.out.println("-------------------------------------------------------------");
        System.out.println("Rotate Right 2");
        tree.rotateRight();
        System.out.println("Original Set - PreOrder: " + tree.preOrder());
        System.out.println("Original Set - InOrder: " + tree.inOrder());
        System.out.println("Original Set - PostOrder: " + tree.postOrder());

        System.out.println("-------------------------------------------------------------");
        System.out.println("Rotate Right 3");
        tree.rotateRight();
        System.out.println("Original Set - PreOrder: " + tree.preOrder());
        System.out.println("Original Set - InOrder: " + tree.inOrder());
        System.out.println("Original Set - PostOrder: " + tree.postOrder());

        System.out.println("-------------------------------------------------------------");
        System.out.println("Rotate Left 1");
        tree.rotateLeft();
        System.out.println("Original Set - PreOrder: " + tree.preOrder());
        System.out.println("Original Set - InOrder: " + tree.inOrder());
        System.out.println("Original Set - PostOrder: " + tree.postOrder());

        System.out.println("-------------------------------------------------------------");
        System.out.println("Rotate Left 2");
        tree.rotateLeft();
        System.out.println("Original Set - PreOrder: " + tree.preOrder());
        System.out.println("Original Set - InOrder: " + tree.inOrder());
        System.out.println("Original Set - PostOrder: " + tree.postOrder());

        System.out.println("-------------------------------------------------------------");
        System.out.println("Rotate Left 3");
        tree.rotateLeft();
        System.out.println("Original Set - PreOrder: " + tree.preOrder());
        System.out.println("Original Set - InOrder: " + tree.inOrder());
        System.out.println("Original Set - PostOrder: " + tree.postOrder());
    }
}
