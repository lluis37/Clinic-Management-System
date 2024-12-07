package util;

import java.util.Iterator;

/**
 * The List class provides an array-based implementation of a "List" data structure.
 * The List class supports the management of a list of elements.
 * @param <E> the type of elements in this list.
 * @author Luis Rodriguez
 */
public class List<E> implements Iterable<E> {
    private static final int INITIAL_CAPACITY = 4; // the initial capacity for the list
    private static final int GROW_SIZE = 4; // the size by which the list grows when full
    private static final int NOT_FOUND = -1; // variable to indicate that an appointment has not been found within the list

    private E[] objects;
    private int size; // number of elements in the array

    /**
     * A default constructor that creates a list of capacity 4.
     * The initial length of the list is determined by INITIAL_CAPACITY.
     * The size of the list is set to 0.
     */
    public List() {
        this.objects = (E[]) new Object[INITIAL_CAPACITY];
        this.size = 0;
    }

    /**
     * Helper method to search for an element in the list.
     * If the element is found, the method will return the index of the element,
     * and the method will return NOT_FOUND otherwise.
     * @param e the element to search for.
     * @return the index of the element if it has been found, otherwise NOT_FOUND.
     */
    private int find(E e) {
        for (int i = 0; i < this.size; i++) {
            if (this.objects[i].equals(e)) {
                return i;
            }
        }

        return NOT_FOUND;
    }

    /**
     * Helper method to increase the capacity of the list by 4.
     */
    private void grow() {
        E[] moreObjects = (E[]) new Object[this.size + GROW_SIZE];

        for (int i = 0; i < this.size; i++) {
            moreObjects[i] = this.objects[i];
        }

        this.objects = moreObjects;
    }

    /**
     * Method to determine whether the list contains the specified element.
     * @param e the element to be searched for.
     * @return true if the specified element is contained within the list, false otherwise.
     */
    public boolean contains(E e) {
        return this.find(e) != NOT_FOUND;
    }

    /**
     * Method to add the specified element to the end of the list.
     * If the list already contains the specified element, the specified element will not
     * be added to the list. If the list is full, the list will be expanded using the grow()
     * method before adding the element to the list.
     * @param e the element to be added to the list.
     */
    public void add(E e) {
        if (this.contains(e)) {
            return;
        }

        if (this.size == this.objects.length) {
            this.grow();
        }
        this.objects[size] = e;
        size++;
    }

    /**
     * Method to remove the specified element from the list.
     * If the list does not contain the specified element, nothing will happen.
     * If the list contains the specified element, the specified element is moved
     * to the end of the list and removed, and the size of the list is decremented. The
     * capacity of the list does not change.
     * @param e the element to be removed from the list.
     */
    public void remove(E e) {
        if (!this.contains(e)) {
            return;
        }

        int objectIndex = this.find(e);
        E temporary = this.objects[size - 1];
        this.objects[size - 1] = this.objects[objectIndex];
        this.objects[objectIndex] = temporary;
        size--;
        this.objects[size] = null;
    }

    /**
     * Method to determine whether this list is empty.
     * @return true if this list is empty, false otherwise.
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Method to get the size of this list.
     * @return the size of this list.
     */
    public int size() {
        return this.size;
    }

    /**
     * Method which creates an iterator over the elements in this list in proper sequence.
     * @return an iterator over the elements in this list in proper sequence.
     */
    @Override
    public Iterator<E> iterator() {
        return new ListIterator<>();
    }

    /**
     * Method that gets the element in this list which is at the specified index.
     * The specified index must be greater than or equal to 0 and less than the size
     * of this list. If the specified index is outside said bounds, the method returns
     * null.
     * @param index index of the element in this list.
     * @return the element at the specified index, or null if the index is out of bounds.
     */
    public E get(int index) {
        if( (index < this.size) && (index >= 0) ) {
            return this.objects[index];
        }

        return null;
    }

    /**
     * Method which places the specified element at the specified index in this list.
     * The specified index must be greater than or equal to 0 and less than the size
     * of this list. If the specified index is outside said bounds, the method does nothing.
     * @param index the index of this list where the specified element is to be placed.
     * @param e the element to place at the specified index.
     */
    public void set(int index, E e) {
        if ( (index < this.size) && (index >= 0) ) {
            this.objects[index] = e;
        }
    }

    /**
     * Method to return the index of the specified element in this list.
     * @param e the element whose index is to be found.
     * @return the index of the specified element in this list, or -1 if the element is not found.
     */
    public int indexOf(E e) {
        return find(e);
    }

    /**
     * The ListIterator class is a private class which helps create an iterator of this list.
     * @param <E> the type of elements in this list iterator.
     * @author Luis Rodriguez
     */
    private class ListIterator<E> implements Iterator<E> {
        private int i = 0;

        /**
         * Method to determine if this iterator has more elements.
         * @return false if this iterator is empty or end of iterator, true otherwise.
         */
        @Override
        public boolean hasNext() {
            return List.this.size != i;
        }

        /**
         * Method to retrieve the next element in this iterator.
         * @return the next element in this iterator.
         */
        @Override
        public E next() {
            return (E) List.this.objects[i++];
        }
    }
}
