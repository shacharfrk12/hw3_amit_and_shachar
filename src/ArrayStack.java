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
     * @param maxCapacity maximum capicity of stack
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


    @Override
    public ArrayStack<E> clone(){
        ArrayStack<E> copy = new ArrayStack<>(this.arr.length);
        ArrayStack<E> temp = new ArrayStack<>(this.arr.length);
        if (this.isEmpty()) {
            copy.lastIndex = this.lastIndex;
            return copy;
        }
        Method method = null;
        try {
            // TODO: should I delete the import for this?
            method = this.peek().getClass().getMethod("clone", null);
        }
        catch (NoSuchMethodException e) {
            return null;
        }
        while (!temp.isEmpty()) {
            try {
                Object elementCopy = method.invoke((temp.pop()));
                copy.push((E) elementCopy);
            }
            catch (IllegalAccessException e) {
                return null;
            }
            catch (InvocationTargetException e) {
                return null;
            }
            catch (ClassCastException e) {
                throw new RuntimeException("This is a problem (in clone in ArrayStack)");
            }
        }
        copy.lastIndex = this.lastIndex;
        return copy;
        //todo: implement clone (remember to do it in deep copy - copy the stack object as well as the stack
    }
    @Override
    public StackIterator<E> iterator(){
        return new StackIterator<E>();
    }

    public class StackIterator<E> implements Iterator<E> {
        int nextIndex = 0;

        @Override
        public boolean hasNext() {
            return this.nextIndex <= lastIndex;
        }

        @Override
        public E next() {
            return (E)arr[nextIndex++];
        }
    }
}
