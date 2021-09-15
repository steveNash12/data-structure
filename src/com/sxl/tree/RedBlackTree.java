package com.sxl.tree;

/**
 * @author songxl
 * @create 2021-09-15 18:31
 * @desc 红黑树
 **/
public class RedBlackTree<Key extends Comparable<Key>,Value> {

    private Node root;
    private int n;

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

    private static class Node<Key extends Comparable<Key>,Value> {
        public Node left;
        public Node right;
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


}
