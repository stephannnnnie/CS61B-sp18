import javax.xml.soap.Node;
import java.util.LinkedList;

public class LinkedListDeque<Item> extends LinkedList<Item> implements Deque<Item> {
    public class IntNode{
        public Item item;
        public IntNode prev;
        public IntNode next;

        public IntNode(Item item, IntNode prev,IntNode next) {
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

    @Override
    public void addFirst(Item item){
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
    @Override
    public void addLast(Item item){
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

    @Override
    public boolean isEmpty(){
        return size == 0;
    }

    @Override
    public int size(){
        return size;
    }

    @Override
    public void printDeque(){
        IntNode p = sentinel;
        while (p.next!=sentinel&&p.next!=null){
            if (p==sentinel){
                p = p.next;
            }else{
                System.out.print(p.item +" ");
                p = p.next;
            }
        }
        System.out.println();
    }

    @Override
    public Item removeFirst(){
        if (isEmpty()){
            return null;
        }else {
            size--;
            IntNode first = sentinel.next;
            sentinel.next = first.next;
            first.next.prev= sentinel;
            first.next=null;
            first.prev = null;

            return first.item;
        }

    }

    @Override
    public Item removeLast(){
        if (isEmpty()){
            return null;
        }else {
            size--;
            IntNode p = sentinel.prev;
            sentinel.prev = p.prev;
            p.prev.next = sentinel;
            p.next = null;
            p.prev = null;
            return p.item;
        }
    }

    @Override
    public Item get(int index){
        IntNode p = sentinel;
        if (!isEmpty()&&index<size){
            for (int i=0;i<index+1;i++){
                p = p.next;
            }
            return p.item;
        }
        return null;
    }

    @Override
    public Item getRecursive(int index){
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