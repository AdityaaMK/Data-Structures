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
        output = "";
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

    public String inOrder() {
        output = "";
        if (size == 0) {
            return "[]";
        } else {
            output += "[";
            inOrder(root);
        }
        output = output.substring(0, output.length() - 2);
        output += "]";
        return output;
    }

    public String postOrder() {
        output = "";
        if (size == 0) {
            return "[]";
        } else {
            output += "[";
            postOrder(root);
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

    public void inOrder(TreeNode<E> root) {
        if (root != null) {
            inOrder(root.getLeft());
            output += root.getValue() + ", ";
            inOrder(root.getRight());
        }
    }

    public void postOrder(TreeNode<E> root) {
        if (root != null) {
            postOrder(root.getLeft());
            postOrder(root.getRight());
            output += root.getValue() + ", ";
        }
    }

    public boolean contains(TreeNode<E> root, E value) {
        if (root == null) {
            return false;
        }
        int x = value.compareTo(root.getValue());
        if (root.getValue() == value) {
            return true;
        } else if (x < 0) {
            return contains(root.getLeft(), value);
        } else {
            return contains(root.getRight(), value);
        }
    }

    public E minValue(TreeNode<E> temp) {
        if (temp.getLeft() == null)
            return temp.getValue();
        return minValue(temp.getLeft());
    }

    public void remove(E value) {
        if (root == null) {
            return;
        } else if (contains(root, value)) {
            if (root.getLeft() == null && root.getRight() == null) {
                root = null; // root is the value
                size = 0;
                return;
            } else {
                size--;
                root = remove(root, value); // root is not the value, need to search in tree
            }
        }
    }

    public TreeNode<E> remove(TreeNode<E> root, E value) {
        if (root == null) {
            return null;
        }
        int x = value.compareTo(root.getValue());
        if (x < 0) {
            root.setLeft(remove(root.getLeft(), value));
        } else if (x > 0) {
            root.setRight(remove(root.getRight(), value));
        } else {
            if (root.getLeft() == null && root.getRight() == null)
                root = null;
            else if (root.getLeft() == null)
                return root.getRight();
            else if (root.getRight() == null)
                return root.getLeft();
            else {
                E minValueTemp = minValue(root.getRight());
                root.setValue(minValueTemp);
                root.setRight(remove(root.getRight(), minValueTemp));
            }
        }
        return root;
    }

    public int size() {
        return size;
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

        public E getValue() {
            return value;
        }

        public void setRight(TreeNode<E> right) {
            this.right = right;
        }

        public void setLeft(TreeNode<E> left) {
            this.left = left;
        }

        public void setValue(E value) {
            this.value = value;
        }

        public String toString() {
            return value.toString();
        }

    }
}