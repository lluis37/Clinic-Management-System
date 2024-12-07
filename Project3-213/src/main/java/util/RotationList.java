package util;

/**
 * This is a class which supports the management of a rotational list of elements.
 * This class extends the List class and has additional methods to allow for rotation
 * of elements.
 * @param <E> the type of elements in this rotation list.
 * @author Andrew Downey.
 * @author Luis Rodriguez
 */
public class RotationList<E> extends List<E> {
    private static final int LIST_START = 0;

    private int nextObject = LIST_START; // pointer to the next element of the rotation list

    /**
     * This is the default constructor used to create an instance of RotationList.
     * This constructor calls the default constructor of its superclass.
     */
    public RotationList() {
        super();
    }

    /**
     * This method retrieves the element which the pointer currently points to.
     * The method then rotates the pointer to the next object in the list.
     * @return the element pointed to by the pointer of the list.
     */
    public E rotate() {
        if (size() == 0) {
            return null;
        }
        E next = this.get(nextObject);
        nextObject++;
        if(nextObject == this.size()){
            nextObject = LIST_START;
        }
        return next;
    }

    /**
     * This method un-rotates the List by moving the pointer back one in the list.
     */
    public void unrotate(){
        if(size() == 0){
            return;
        }
        nextObject--;
        if(nextObject < LIST_START){
            nextObject = this.size() -1;
        }
    }

    /**
     * This is an add method that overrides the add method in its superclass.
     * This method adds the element to the end of the RotationList. It then moves the added
     * element to the front and shifts all other elements over by one index.
     * @param e element to be added to the list.
     */
    @Override
    public void add(E e) {
        super.add(e);

        E temp1 = e;
        for (int i = 0; i < size(); i++) {
            E temp2 = get(i);
            set(i, temp1);
            temp1 = temp2;
        }
    }
}
