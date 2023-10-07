package synthesizer;
// TODO: Make sure to make this class a part of the synthesizer package
// package <package name>;
import java.util.Iterator;

//TODO: Make sure to make this class and all of its methods public
//TODO: Make sure to make this class extend AbstractBoundedQueue<t>
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T>{
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue, so
        //       you'll need to use this.capacity to set the capacity.
        first = 0;
        last = 0;
        fillCount = 0;
        this.capacity = capacity;
        rb =(T[]) new Object[capacity];
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    @Override
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update last.
        if (isFull()){
            throw new RuntimeException("Ring buffer overflow");
        }else {
            rb[last]=x;
            fillCount++;
            if (last==capacity-1){
                last = 0;
            }else{
                last++;
            }
        }
    }

    /**
     * Dequeue the oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    @Override
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and update first.
        if (isEmpty()){
            throw new RuntimeException("Ring buffer underflow");
        }else {
            T res = peek();
            rb[first]=null;
            if (first==capacity-1){
                first=0;
            }else {
                first++;
            }
            fillCount--;
            return res;
        }
    }

    /**
     * Return oldest item, but don't remove it.
     */
    @Override
    public T peek() {
        // TODO: Return the first item. None of your instance variables should change.
        return rb[first];
    }

    @Override
    public Iterator<T> iterator() {
        return new keyIterator();
    }

    private class keyIterator implements Iterator<T>{
        private int wizPos;

        @Override
        public boolean hasNext() {
            return wizPos<capacity;
        }

        @Override
        public T next() {
            T returnItem = rb[wizPos];
            wizPos++;
            return returnItem;
        }
    }

    // TODO: When you get to part 5, implement the needed code to support iteration.
}
