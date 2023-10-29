package lab9;

import java.security.Key;
import java.util.*;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();

    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p==null){
            return null;
        }
        if (key.compareTo(p.key)>0) {
            return getHelper(key, p.right);
        } else if (key.compareTo(p.key)<0) {
            return getHelper(key,p.left);
        }else {
            return p.value;
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key,root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p==null){
            return new Node(key,value);
        }
        if (key.compareTo(p.key)>0){
            p.right= putHelper(key,value,p.right);
        } else if (key.compareTo(p.key)<0) {
            p.left= putHelper(key, value, p.left);
        }else {
            p.value = value;
            return p;
        }
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        if (get(key)==null){
            size++;
        }
        root = putHelper(key,value,root);

    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        Node tmp = root;
        if (tmp!=null){
            findKeys(tmp,keys);
        }
        return keys;
    }

    private void findKeys(Node node, Set<K> set){
        if (node != null){
            set.add(node.key);
            findKeys(node.left,set);
            findKeys(node.right,set);
        }
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     *  1.先找到key对应的节点
     *  2.找到后分两种情况：
     *      1）若左右子树都存在，则找到当前节点的后续节点（右子树的最左侧节点）也就是右子树的minvalue，替换当前节点，删除当前节点
     *      2）若有至少一侧子树不存在,则将其子树替换当前节点后，将当前节点删除
     *  3.删除节点怎么删？没有父子关系？这个地方没懂
     */
    @Override
    public V remove(K key) {
        Node node = delete(root,key);
        if (node==null){
            return null;
        }else {
            return node.value;
        }
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        if (!keySet().contains(key)){
            return null;
        }
        Node node = deleteS(root,key,value);
        if (node==null){
            return null;
        }else {
            return value;
        }
    }

    private Node deleteS(Node x, K k,V value){
        if (x==null){
            return null;
        }
        if (k.compareTo(x.key)<0){
            x.left = deleteS(x.left,k,value);
        } else if (k.compareTo(x.key)>0) {
            x.right = deleteS(x.right,k,value);
        }else {
            if (x.right==null){
                return x.left;
            }
            if (x.left==null){
                return x.right;
            }
            if (x.value == value) {
                Node t = x;
                x = min(t.right);
                x.right = deleteMin(t.right);
                x.left = t.left;
            }
        }
        if (x.value == value){
            return x;
        }
        return null;
    }


    private Node delete(Node x, K k){
        if (x==null){
            return null;
        }
        if (k.compareTo(x.key)<0){
            x.left = delete(x.left,k);
        } else if (k.compareTo(x.key)>0) {
            x.right = delete(x.right,k);
        }else {
//            Node p = null;
//            if (x.left!=null&&x.right!=null){
//                p = x.right;
//                while (p.left!=null){
//                    p = p.left;
//                }
//                x.value = p.value;
//                delete(x.right,k);
//            }else{
//                if (x.right ==null){
//                    return x.left;
//                }
//                return x.right;
//            }
//            return x;
            if (x.right==null){
                return x.left;
            }
            if (x.left==null){
                return x.right;
            }
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        return x;
    }

    private Node min(Node x){
        if (x.left == null){
            return x;
        }
        return min(x.left);
    }

    private Node deleteMin(Node x){
        if (x.left==null){
            return x.right;
        }
        x.left = deleteMin(x.left);
        return x;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    public static void main(String[] args) {
        BSTMap<String, Integer> bstMap = new BSTMap<>();
        bstMap.put("hello",5);
        bstMap.put("cat",10);
        bstMap.put("fish",22);
        System.out.println(bstMap.keySet());
    }
}
