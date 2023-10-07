public class ArrayDeque<T> {
    private T[] items;
    private int size;

    public ArrayDeque(){
        items = (T[]) new Object[8];
        size = 0;
    }
    public void addFirst(T item){
        if (size>=items.length-1){
            resizeLarge();
        }
        for (int i = size-1;i>=0;i--){
            items[i+1]=items[i];
        }
        items[0]=item;
        size++;
    }

    public void resizeLarge(){
        T[] newArray = (T[]) new Object[size*4];
        System.arraycopy(this.items,0,newArray,0,size);
        this.items = newArray;
    }

    public void addLast(T item){
        if (size>=items.length-1){
            resizeLarge();
        }
        items[size]=item;
        size++;
    }

    public boolean isEmpty(){
        return size==0;
    }
    public int size(){
        return size;
    }
    public void printDeque(){
        for (int i = 0; i < size(); i++) {
            System.out.print(this.items[i]+" ");
        }
        System.out.println();
    }
    public T removeFirst(){
        if (size<items.length/4){
            resizeSmall();
        }
        T p = items[0];
        for (int i = 1;i<size();i++){
            items[i-1]=items[i];
        }
        size--;
        return p;
    }

    public void resizeSmall(){
        T[] newArray = (T[]) new Object[size/4];
        System.arraycopy(this.items,0,newArray,0,size);
        this.items = newArray;
    }
    public T removeLast(){
        if (size<items.length/4){
            resizeSmall();
        }
        T p = items[size-1];
        items[size-1]= (T) new Object();
        size--;
        return p;
    }
    public T get(int index){
        return items[index];
    }
}
