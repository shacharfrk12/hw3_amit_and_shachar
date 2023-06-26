public interface Stack<E extends Cloneable> extends Iterable<E>, Cloneable {
    /**
     * add an element at the beginning of the stack
     * @param element the element to add
     */
    void push(E element);

    /**
     * remove the element from the beginning of the stack
     * @return the element that was removed
     */
    E pop();

    /**
     * return the element from the beginning of the stack
     * @return the element at the biginning of the stack
     */
    E peek();

    /**
     * calculate the number of elements in the stack
     * @return the number of elements
     */
    int size();

    /**
     * check if the stack has at least 1 element
     * @return true if there is at least 1 element, false otherwise
     */
    boolean isEmpty();

    /**
     * copy the stack, in format of deep copy
     * @return a copy (deep copy) of the stack
     */
    Stack<E> clone();
}


