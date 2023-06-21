import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;

/**
 * the ArrayStack class represents a stack using an array to implement it
 * @param <E> generic type of all elements in the stack
 */
public class ArrayStack<E extends Cloneable> implements Stack<E>, Cloneable, Iterable<E>{
    private transient Object[] arr;
    private int lastIndex;

    /**
     * constructs ArrayStack
     * @param maxCapacity maximum capacity of stack
     * @throws NegativeCapacityException unchecked exception - it is illegal to enter a negative number as capacity
     */
    public ArrayStack(int maxCapacity) throws NegativeCapacityException{
        if(maxCapacity<0)
            throw new NegativeCapacityException("Max capacity can not be a negative number");
        this.arr = new Object[maxCapacity];
        this.lastIndex = -1;
    }

    /**
     * adding an element at the top of stack
     * @param element element to push
     * @throws StackOverflowException unchecked exception - cannot push an element after reaching max capacity
     */
    @Override
    public void push(E element) throws StackOverflowException{
        if(this.lastIndex + 1 >= this.arr.length)
            throw new StackOverflowException("Tried to push element into a full stack");
        this.arr[this.lastIndex + 1] = element;
        this.lastIndex++;
    }

    /**
     * taking out the top element of the stack
     * @return the top element of the stack
     * @throws EmptyStackException unchecked exception - cannot take an element out of an empty stack
     */
    @Override
    public E pop() throws EmptyStackException{
        if(lastIndex<0)
            throw new EmptyStackException("Tried to pop an element from an empty stack");
        if(this.arr[lastIndex] == null)
            return null;
        E lastElement =(E) this.arr[lastIndex];
        this.arr[lastIndex] = null;
        this.lastIndex--;
        return lastElement;
    }

    /**
     * returns the top element of the stack without changing the stack
     * @return top element of stack
     * @throws EmptyStackException unchecked exception - cannot peek at an element out of an empty stack
     */
    @Override
    public E peek() throws EmptyStackException{
        if(lastIndex<0)
            throw new EmptyStackException("Tried to peek at an empty stack");
        if(this.arr[lastIndex] == null)
            return null;
        return (E) this.arr[lastIndex];
    }

    /**
     * returns number of elements currently in stack
     * @return stack size - the number of elements in stack
     */
    @Override
    public int size(){
        return lastIndex+1;
    }

    /**
     * checks if there are any elements in stack
     * @return true if the stack is empty, false otherwise
     */
    @Override
    public boolean isEmpty(){
        return (lastIndex < 0);
    }


    /**
     * clone the current stack in deep copy
     * @return the copy of the stack
     */
    @Override
    public ArrayStack<E> clone(){
        ArrayStack<E> copy = new ArrayStack<>(this.arr.length);
        try {
            for (int i = 0; i <= this.lastIndex; i++) {
                E elementToCopy = (E) this.arr[i];
                Method method = elementToCopy.getClass().getMethod("clone", null);
                Object elementCopy = method.invoke(elementToCopy);
                copy.push((E) elementCopy);
            }
        }
        catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            return null;
        }
        copy.lastIndex = this.lastIndex;
        return copy;
    }

    /**
     * Create and return an iterator of the current ArrayStack
     * @return the iterator
     */
    @Override
    public StackIterator<E> iterator(){
        return new StackIterator<E>();
    }

    /**
     * An iterator for ArrayStack object
     * @param <E> the same type of object as in its ArrayStack. should be Cloneable as well
     */
    public class StackIterator<E> implements Iterator<E> {
        int nextIndex;

        public StackIterator(){
            this.nextIndex = lastIndex;
        }

        /**
         * check if there is another element to iterate on
         * @return true if there is another element, false otherwise
         */
        @Override
        public boolean hasNext() {
            return this.nextIndex >= 0;
        }

        /**
         * assuming there is 'next' element, find it
         * @return the next element to iterate on
         */
        @Override
        public E next() {
            return (E)arr[nextIndex--];
        }
    }
}
