import java.util.EmptyStackException;

public class SuperList<E> {
    private ListNode<E> root;
    private ListNode<E> end;
    private int size;

    public SuperList() {

    }

    public SuperList(E root) {
        this.root = new ListNode<>(root);
        this.end = this.root;
        size = 1;
    }

    public void add(E nextValue) {
        ListNode<E> newNode = new ListNode<>(nextValue);
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
        add(nextValue);
    }

    public void add(int index, E newValue) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException("Index " + index + " is not in SuperList of size " + size);
        if (index == 0) {
            ListNode<E> newNode = new ListNode<>(newValue);
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
            ListNode<E> temp = root;
            for (int i = 0; i < index - 1; i++)
                temp = temp.getNext();
            ListNode<E> next = temp.getNext();
            ListNode<E> newNode = new ListNode<>(newValue);
            newNode.setNext(next);
            newNode.setPrevious(temp);
            temp.setNext(newNode);
            next.setPrevious(newNode);
        }
        size++;
    }

    public E remove(int index) {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException("Index " + index + " is not in SuperList of size " + size);
        if (index == 0) {
            return poll();

        }
        if (index == size - 1) {
            return pop();
        }
        ListNode<E> temp = root; // temp is value we are removing
        for (int i = 0; i < index - 1; i++)
            temp = temp.getNext();
        ListNode<E> prev = temp;
        temp = temp.getNext();
        ListNode<E> next = temp.getNext();
        prev.setNext(next);
        next.setPrevious(prev);
        size--;
        return temp.getValue();
    }

    public E get(int index) {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException("Index " + index + " is not in SuperList of size " + size);
        ListNode<E> temp = root;
        for (int i = 0; i < index; i++)
            temp = temp.getNext();
        return temp.getValue();
    }

    public E pop() {
        if (size == 0)
            throw new EmptyStackException();
        if (size == 1) {
            E val = end.getValue();
            clear();
            return val;
        }
        E val = end.getValue();
        end = end.getPrevious();
        end.setNext(null);
        size--;
        return val;
    }

    public E poll() {
        if (size == 0)
            throw new EmptyStackException();
        if (size == 1) {
            E val = end.getValue();
            clear();
            return val;
        }
        E val = root.getValue();
        root = root.getNext();
        root.setPrevious(null);
        size--;
        return val;
    }

    public E peekStack() {
        if (end == null)
            throw new EmptyStackException();
        return end.getValue();
    }

    public E peekQueue() {
        return root != null ? root.getValue() : null;
    }

    public String toString() {
        String s = "[";
        for (ListNode<E> node = root; node != null; node = node.getNext())
            s += node.getValue() + ", ";
        return (s.length() > 1 ? s.substring(0, s.length() - 2) : s) + "]";
    }

    public boolean contains(E value) {
        for (ListNode<E> node = root; node != null; node = node.getNext())
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

    // public SuperList<E> copy() {
    // SuperList<E> duplicate = new SuperList<>();
    // return duplicate.addAll(this);
    // }

    // public SuperList<E> addAll(SuperList<E> other) {
    // for (E a : other)
    // add(a);
    // return this;
    // }

    public static interface ListFilter<E> {
        public boolean filter(E obj);
    }

    public class ListNode<E> {

        private E value;
        private ListNode<E> next;
        private ListNode<E> previous;

        public ListNode(E value) {
            this.value = value;
        }

        public E getValue() {
            return value;
        }

        public void setValue(E value) {
            this.value = value;
        }

        public ListNode<E> getPrevious() {
            return previous;
        }

        public void setPrevious(ListNode<E> previous) {
            this.previous = previous;
        }

        public boolean hasPrevious() {
            return previous != null;
        }

        public ListNode<E> getNext() {
            return next;
        }

        public void setNext(ListNode<E> next) {
            this.next = next;
        }

        public boolean hasNext() {
            return next != null;
        }

    }
}