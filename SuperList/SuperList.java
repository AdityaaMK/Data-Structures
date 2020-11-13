public class SuperList<E extends Comparable<E>> implements Iterable<E> {
    private ListNode root;
    private ListNode end;
    private int currentIndex;
    private int size;

    public SuperList() {

    }

    public SuperList(E value) {
        this.root = new ListNode(root);
        this.end = this.root;
        size = 1;
    }

    public void add(E nextValue) {
        ListNode newNode = new ListNode(nextValue);
        if (root == null) {
            root = newNode;
            end = newNode;
        } else {
            end.setNext(newNode);
            newNode.setPrevious(end);
            end = newNode;
        }
    }

    public class ListNode {

        private E value;
        private ListNode next;
        private ListNode previous;

        public ListNode(E value) {
            this.value = value;
        }

        public E getValue() {
            return value;
        }

        public void setValue(E value) {
            this.value = value;
        }

        public ListNode getPrevious() {
            return previous;
        }

        public void setPrevious(ListNode previous) {
            this.previous = previous;
        }

        public boolean hasPrevious() {
            return previous != null;
        }

        public ListNode getNext() {
            return next;
        }

        public void setNext(ListNode next) {
            this.next = next;
        }

        public boolean hasNext() {
            return next != null;
        }

    }
}
