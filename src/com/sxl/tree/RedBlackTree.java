package com.sxl.tree;

/**
 * @author songxl
 * @create 2021-09-15 18:31
 * @desc 红黑树
 **/
public class RedBlackTree<Key extends Comparable<Key>,Value> {

    private Node<Key,Value> root;
    private int n;

    public RedBlackTree() {
        this.n = 0;
    }

    /**
     * 红色
     */
    private static final boolean RED = true;
    private static final boolean BLACK = false;


    /**
     * 判断当前节点的父指向链接是否为红色
     * @param node
     * @return
     */
    private boolean isRed(Node node) {
        if (node == null) {
            return false;
        }
        return node.color;
    }

    /**
     * 左旋
     * @param h
     * @return
     */
    private Node rotateLeft(Node h) {

        Node hRight = h.right;
        // 让右子节点的左子节点成为h右子节点
        h.right = hRight.left;
        // 让h 成为 右子节点的左子节点
        hRight.left = h;
        // 让h的color属性变为 右子节点的 color属性（设置x 节点color属性）
        hRight.color = h.color;
        //  让h的color属性变为red
        h.color = RED;
        return hRight;
    }

    /**
     * 右旋
     * 前提：当前节点为h, 它的左子节点为x;
     * 左旋过程：
     * 1. 让x的右子节点变为h 的左子节点
     * 2. 让h成为x的右子节点
     * 3. 让x的color属性变为h的 color属性（设置h 节点color属性）
     * 4. 让x的color属性变为red
     * @param h
     * @return
     */
    private Node rotateRight(Node h) {
        Node hLeft = h.left;
        h.left = hLeft.left;
        hLeft.right = h;
        h.color = hLeft.color;
        hLeft.color = RED;
        return hLeft;
    }

    /**
     * 颜色反转 相当于拆分4-节点
     * @param h
     */
    private void flipRed(Node h) {

        h.left.color = BLACK;
        h.right.color = BLACK;
        h.color = RED;
    }


    public Value get(Key key) {
        return get(root,key);
    }
    public Value get(Node<Key,Value> x,Key key) {

        if (x == null) {
            return null;
        }
        int diff = key.compareTo(x.key);
        if (diff == 0) {
            return x.value;
        }else if (diff < 0) {
           return get(x.left,key);
        }else {
            return get(x.right,key);
        }
    }

    public void put(Key key,Value value) {

       root = put(root,key,value);
       root.color = BLACK;

    }

    public Node put(Node<Key,Value> h,Key key,Value value) {
        if (h == null) {
            n++;
            return new Node(null,null,key,value,RED);
        }

        int diff = key.compareTo(h.key);
        if (diff <  0) {
            h.left = put(h.left,key,value);
        }else if (diff > 0) {
            h.right = put(h.right,key,value);
        }else {
            h.value = value;
        }
        // 左链接为黑色，有链接为红色 左旋
        if (!isRed(h.left) && isRed(h.right)) {
            h = rotateLeft(h);
        }
        // 左链接为红色、左子链接也为红色  右旋
        if (isRed(h.left) && isRed(h.left.left)) {
            h = rotateRight(h);
        }
        // 左右链接都为红色
        if (isRed(h.left) && isRed(h.right)) {
            flipRed(h);
        }
        return h;

    }
    private static class Node<Key extends Comparable<Key>,Value> {
        public Node<Key,Value> left;
        public Node<Key,Value> right;
        public Key key;
        public Value value;

        /**
         * 如果父指向链接 的颜色是红色 那么就是true
         * 黑色 为false
         */
        public boolean color;


        public Node(Node left, Node right, Key key, Value value, boolean color) {
            this.left = left;
            this.right = right;
            this.key = key;
            this.value = value;
            this.color = color;
        }
    }

    public static void main(String[] args) {

        RedBlackTree<Integer,String> redBlackTree = new RedBlackTree();
        redBlackTree.put(4,"44");
        redBlackTree.put(1,"11");

        redBlackTree.put(3,"33");

        redBlackTree.put(5,"55");
        System.out.println(redBlackTree.n);
    }

}
