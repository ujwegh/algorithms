import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int dequeSize;

    private class Node {
        private Item item;
        private Node next;
        private Node prev;

        public Node(Item item) {
            this.item = item;
        }
    }

    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return dequeSize == 0;
    }

    // return the number of items on the deque
    public int size() {
        return dequeSize;
    }

    // add the item to the front
    public void addFirst(Item item) {
        checkNotNull(item);
        Node newNode = new Node(item);
        if (first != null) {
            Node oldFirst = first;
            newNode.next = oldFirst;
            oldFirst.prev = newNode;
        }
        else {
            last = newNode;
        }
        dequeSize++;
        first = newNode;
    }

    // add the item to the back
    public void addLast(Item item) {
        checkNotNull(item);
        Node newNode = new Node(item);
        if (last != null) {
            Node oldLast = last;
            newNode.prev = oldLast;
            oldLast.next = newNode;
        }
        else {
            first = newNode;
        }
        dequeSize++;
        last = newNode;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (first == null) {
            throw new NoSuchElementException("Can't find first element");
        }
        if (first.next != null) {
            Item item = first.item;
            Node next = first.next;
            next.prev = null;
            first = next;
            dequeSize--;
            return item;
        }
        else {
            Item item = first.item;
            first = null;
            last = null;
            dequeSize--;
            return item;
        }
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (last == null) {
            throw new NoSuchElementException("Can't find last element");
        }
        if (last.prev != null) {
            Item item = last.item;
            Node prev = last.prev;
            prev.next = null;
            last = prev;
            dequeSize--;
            return item;
        }
        else {
            Item item = last.item;
            first = null;
            last = null;
            dequeSize--;
            return item;
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            private Node current = first;

            public boolean hasNext() {
                return current != null;
            }

            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("Can't find next element");
                }
                Item item = current.item;
                current = current.next;
                return item;
            }

            public void remove() {
                throw new UnsupportedOperationException("Remove operation not supperted");
            }
        };
    }

    // unit testing (required)
    public static void main(String[] args) {
        assertRemoveAndAddOperations();
        assertCornerCases();
    }

    private static void assertCornerCases() {
        StdOut.println("init deque");
        Deque<Integer> deque = new Deque<>();
        assert deque.isEmpty();
        assert deque.size() == 0;
        printDequeStatus(deque);

        try {
            deque.addFirst(null);
        } catch (IllegalArgumentException e) {
            StdOut.println("Got exception on adding first null element");
        }
        try {
            deque.addLast(null);
        } catch (IllegalArgumentException e) {
            StdOut.println("Got exception on adding last null element");
        }
        try {
            deque.removeFirst();
        } catch (NoSuchElementException e) {
            StdOut.println("Got exception on removing first element");
        }
        try {
            deque.removeLast();
        } catch (NoSuchElementException e) {
            StdOut.println("Got exception on removing last element");
        }
    }

    private static void assertRemoveAndAddOperations() {
        StdOut.println("init deque");
        Deque<Integer> deque = new Deque<>();
        assert deque.isEmpty();
        assert deque.size() == 0;
        printDequeStatus(deque);

        int element = 9;
        StdOut.println("add first element: " + element);
        deque.addFirst(element);
        assert !deque.isEmpty();
        assert deque.size() == 1;
        printDequeStatus(deque);

        StdOut.println("removing first element");
        Integer number = deque.removeFirst();
        assert number != null;
        assert deque.isEmpty();
        assert deque.size() == 0;
        printDequeStatus(deque);

        element = 10;
        StdOut.println("add last element: " + element);
        deque.addLast(element);
        assert !deque.isEmpty();
        assert deque.size() == 1;
        printDequeStatus(deque);

        StdOut.println("removing last element");
        deque.removeFirst();
        assert deque.isEmpty();
        assert deque.size() == 0;
        printDequeStatus(deque);

        StdOut.println("add 2 first elements");
        deque.addFirst(11);
        deque.addFirst(22);
        assert !deque.isEmpty();
        assert deque.size() == 2;
        printDequeStatus(deque);

        StdOut.println("remove last element");
        number = deque.removeLast();
        assert number != null;
        number = deque.removeLast();
        assert number != null;
        assert deque.isEmpty();
        assert deque.size() == 0;
        printDequeStatus(deque);
    }

    private static <Item> void printDequeStatus(Deque<Item> deque) {
        StdOut.println("deque size: " + deque.size());
        StdOut.println("is empty: " + deque.isEmpty());
        int i = 0;
        for (Item item : deque) {
            StdOut.println(i + " deque element: " + item);
            i++;
        }
        StdOut.println("---------------");
    }

    private void checkNotNull(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item must not be null");
        }
    }
}
