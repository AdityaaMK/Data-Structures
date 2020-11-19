public class SuperList<E> {
    private ListNode root;
    private ListNode end;
    private int size;

    public SuperList() {

    }

    public SuperList(E root) {
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
        size++;
    }

    public void push(E nextValue) {
        ListNode newNode = new ListNode(nextValue);
        if (root == null) {
            root = newNode;
            end = newNode;
        } else {
            end.setNext(newNode);
            newNode.setPrevious(end);
            end = newNode;
        }
        size++;
    }

    public void add(int index, E newValue) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException("Index " + index + " is not in SuperList of size " + size);
        if (index == 0) {
            ListNode newNode = new ListNode(newValue);
            if (root == null) {
                root = newNode;
                end = newNode;
            } else {
                root.setPrevious(newNode);
                newNode.setNext(root);
                root = newNode;
            }
        } else if (index == size) {
            add(newValue);
            return;
        } else {
            ListNode temp = root;
            for (int i = 0; i < index - 1; i++)
                temp = temp.getNext();
            ListNode newNode = new ListNode(newValue);
            newNode.setNext(temp.getNext());
            newNode.setPrevious(temp);
            temp.setNext(newNode);
            temp.getNext().setPrevious(newNode);
        }
        size++;
    }

    public E remove(int index) {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException("Index " + index + " is not in SuperList of size " + size);
        if (index == 0) {
            E temp = root.getValue();
            root = root.getNext();
            if (root != null)
                root.setPrevious(null);
            size--;
            return temp;
        }
        if (index == size - 1) {
            E temp = end.getValue();
            end = end.getPrevious();
            if (end != null)
                end.setNext(null);
            size--;
            return temp;
        }
        ListNode temp = root; // temp is value we are removing
        for (int i = 0; i < index - 1; i++)
            temp = temp.getNext();
        ListNode prev = temp;
        temp = temp.getNext();
        ListNode next = temp.getNext();
        prev.setNext(next);
        next.setPrevious(prev);
        size--;
        return temp.getValue();
    }

    public E get(int index) {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException("Index " + index + " is not in SuperList of size " + size);
        ListNode temp = root;
        for (int i = 0; i < index; i++)
            temp = temp.getNext();
        return temp.getValue();
    }

    public E pop() {
        return remove(size - 1);
    }

    public E poll() {
        return remove(0);
    }

    public E peekStack() {
        return end != null ? end.getValue() : null;
    }

    public E peekQueue() {
        return root != null ? root.getValue() : null;
    }

    public String toString() {
        String s = "[";
        for (ListNode node = root; node != null; node = node.getNext())
            s += node.getValue() + ", ";
        return (s.length() > 1 ? s.substring(0, s.length() - 2) : s) + "]";
    }

    public boolean contains(E value) {
        for (ListNode node = root; node != null; node = node.getNext())
            if (value.equals(node.getValue()))
                return true;
        return false;
    }

    public void clear() {
        root = null;
        end = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
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
