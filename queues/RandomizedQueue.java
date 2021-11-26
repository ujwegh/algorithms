/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int size;
    private int lastElementIndex;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[16];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item must not be null");
        }

        items[lastElementIndex] = item;
        lastElementIndex++;
        size++;
        if (items.length == size) {
            resizeAndZip(size * 2);
        }
    }

    // remove and return a random item
    public Item dequeue() {
        checkEmptyArray();
        Item item = null;
        int randomIndex = 0;
        while (item == null) {
            randomIndex = StdRandom.uniform(size);
            item = items[randomIndex];
        }
        items[randomIndex] = null;
        size--;
        int newSize = items.length / 4;
        if (size <= newSize) {
            resizeAndZip(newSize * 2);
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        checkEmptyArray();
        Item item = null;
        int randomIndex;
        while (item == null) {
            randomIndex = StdRandom.uniform(items.length);
            item = items[randomIndex];
        }
        return item;
    }


    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomQueueIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        assertGetAddRemoveCases();
        assertCornerCases();
    }

    private static void assertCornerCases() {
        StdOut.println("init queue");
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        assert queue.isEmpty();
        assert queue.size == 0;
        printQueueStatus(queue);

        try {
            queue.enqueue(null);
        } catch (IllegalArgumentException e) {
            StdOut.println("Got exception on adding null element");
        }
        try {
            queue.dequeue();
        } catch (NoSuchElementException e) {
            StdOut.println("Got exception on removing element from empty queue");
        }
        try {
            queue.sample();
        } catch (NoSuchElementException e) {
            StdOut.println("Got exception on getting random element from empty queue");
        }

    }

    private static void assertGetAddRemoveCases() {
        StdOut.println("init queue");
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        assert queue.isEmpty();
        assert queue.size == 0;
        printQueueStatus(queue);

        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
        }
        assert !queue.isEmpty();
        assert queue.size == 10;
        printQueueStatus(queue);


        for (int i = 0; i < 5; i++) {
            queue.dequeue();
        }
        assert !queue.isEmpty();
        assert queue.size == 5;
        printQueueStatus(queue);

        for (int i = 0; i < 5; i++) {
            StdOut.println("random element: " + queue.sample());
        }
    }

    private static <Item> void printQueueStatus(RandomizedQueue<Item> queue) {
        StdOut.println("queue size: " + queue.size());
        StdOut.println("is empty: " + queue.isEmpty());
        int i = 0;
        for (Item item : queue) {
            StdOut.println(i + " queue element: " + item);
            i++;
        }
        StdOut.println("---------------");
    }


    private class RandomQueueIterator implements Iterator<Item> {
        private Item[] innerItems = (Item[]) new Object[size];
        private int index = 0;


        public RandomQueueIterator() {
            int k = 0;
            for (int i = 0; i < items.length; i++) {
                if (items[i] != null) {
                    innerItems[k] = items[i];
                    k++;
                }
            }
            StdRandom.shuffle(innerItems);
        }

        public boolean hasNext() {
            return index < size;
        }

        // grab next element from shuffled array
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Can't find next element");
            }
            Item item = innerItems[index];
            index++;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove operation not supperted");
        }
    }

    private void resizeAndZip(int newSize) {
        Item[] newItems = (Item[]) new Object[newSize];

        int k = 0;
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) {
                newItems[k] = items[i];
                k++;
            }
        }
        lastElementIndex = k;
        items = newItems;
    }

    private void checkEmptyArray() {
        if (isEmpty()) {
            throw new NoSuchElementException("There are no elements");
        }
    }
}
