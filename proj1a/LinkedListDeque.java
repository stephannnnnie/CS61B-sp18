public class LinkedListDeque<T> {
    public class IntNode{
        public T item;
        public IntNode prev;
        public IntNode next;

        public IntNode(T item, IntNode prev,IntNode next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
        public IntNode(IntNode prev, IntNode next){
            this.prev = prev;
            this.next = next;
        }
    }
    private IntNode sentinel;
    private int size;
    public LinkedListDeque() {
        sentinel = new IntNode(null,null,null);
//        sentinel = new IntNode(null,null);
//        System.out.println(sentinel);
        //到底为什么加一个这样的初始化就不会有空指针的错误了？why?why?
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }
    public void addFirst(T item){
        IntNode node = new IntNode(item,null,null);
        if (isEmpty()){

            sentinel.next = node;
            sentinel.prev = node;
            node.prev = null;
            node.next = null;
        }else {
            IntNode p = sentinel.next;
            IntNode q = sentinel;
            sentinel.next.prev = node;
            sentinel.next = node;
            node.next = p;
            node.prev = q;
        }
        size++;
    }
    public void addLast(T item){
        IntNode node = new IntNode(item,null,null);
        if (isEmpty()){
            sentinel.next = node;
            sentinel.prev = node;
            node.prev = null;
            node.next = null;
        }else{
            IntNode p = sentinel.prev;
            IntNode q = sentinel;
            sentinel.prev.next = node;
            sentinel.prev = node;
            node.prev = p;
            node.next = q;
        }
        size++;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    public void printDeque(){
        IntNode p = sentinel;
        while (p.next!=sentinel&&p.next!=null){
            System.out.print(p.item +" ");
            p = p.next;
        }
        System.out.println();
    }

    public T removeFirst(){
        if (isEmpty()){
            return null;
        }else {
            size--;
            return sentinel.next.item;
        }

    }

    public T removeLast(){
        if (isEmpty()){
            return null;
        }else {
            size--;
            return sentinel.prev.item;
        }
    }

    public T get(int index){
        IntNode p = sentinel;
        if (!isEmpty()&&index<size){
            for (int i=0;i<index;i++){
                p = p.next;
            }
            return p.item;
        }
        return null;
    }

    public T getRecursive(int index){
        IntNode p = sentinel;
        if (!isEmpty()&&index<size){
            return find(p.next,0,index).item;
        }
        return null;
    }
    public IntNode find(IntNode next,int i,int limit){
        if (i!=limit) {
            return find(next.next,i+1,limit);
        }
        return next;
    }
}
