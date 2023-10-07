public interface Deque<Item> {
    public void printDeque();
    public void addFirst(Item item);
    public void addLast(Item item);
    public boolean isEmpty();
    public int size();
    public Item get(int index);
    public Item getRecursive(int i);
    public Item removeFirst();
    public Item removeLast();


}
