public class TreeSet<E extends Comparable<E>> {
    private TreeNode<E> root;
    private int size;
    private String output = "";

    public TreeSet() {
        root = null;
        size = 0;
    }

    public void add(E val) {
        if (root == null) {
            root = new TreeNode<>(val);
            size = 1;
        } else {
            add(val, root);
        }
    }

    public void add(E val, TreeNode<E> root) {
        int x = val.compareTo(root.getValue());
        if (x == 0)
            return;
        else {
            if (x < 0) {
                if (root.getLeft() == null) {
                    root.setLeft(new TreeNode<>(val));
                    size++;
                    return;
                }
                add(val, root.getLeft());
            } else {
                if (root.getRight() == null) {
                    root.setRight(new TreeNode<>(val));
                    size++;
                    return;
                }
                add(val, root.getRight());
            }
        }
    }

    public String preOrder() {
        if (size == 0) {
            return "[]";
        } else {
            output += "[";
            preOrder(root);
        }
        output = output.substring(0, output.length() - 2);
        output += "]";
        return output;
    }

    public void preOrder(TreeNode<E> root) {
        if (root != null) {
            output += root.getValue() + ", ";
            preOrder(root.getLeft());
            preOrder(root.getRight());
        }
    }

    public class TreeNode<E extends Comparable<E>> {
        private E value;
        private TreeNode<E> left, right;

        public TreeNode(E value) {
            this.value = value;
            left = right = null;
        }

        public TreeNode<E> getRight() {
            return right;
        }

        public TreeNode<E> getLeft() {
            return left;
        }

        public void setRight(TreeNode<E> right) {
            this.right = right;
        }

        public void setLeft(TreeNode<E> left) {
            this.left = left;
        }

        public E getValue() {
            return value;
        }

        public String toString() {
            return value.toString();
        }

    }
}