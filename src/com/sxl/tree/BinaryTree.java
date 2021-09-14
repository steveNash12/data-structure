package com.sxl.tree;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author songxl
 * @create 2021-09-01 16:51
 * @desc
 **/
public class BinaryTree {

    public Node root;
    private Integer size = 0;

    public void put(Integer key,String val){
        root = put(root,key,val);
    }

    /**
     * 跟新节点
     * @param tree
     * @param key
     * @param val
     * @return
     */
    public Node put(Node tree,Integer key,String val){

        if (Objects.isNull(tree)) {
            size++;
            return new Node(null,null,key,val);
        }

        if (key > tree.getKey()) {
            tree.right = put(tree.right,key,val);
        }else if (key < tree.getKey()) {
            tree.left = put(tree.left,key,val);
        }else {
            // 相等 跟新val
            tree.val = val;
        }
        return tree;
    }

    public String get(Integer key) {
        return get(root,key);
    }

    public String get(Node tree,Integer key) {

        Node tmp =  getNode(tree,key);
        if (Objects.nonNull(tmp)) {
            return tmp.getVal();
        }
        return null;

    }

    public Node getNode(Node tree,Integer key) {
        if (Objects.isNull(tree)) {
            return null;
        }
        Integer tmpKey = tree.getKey();
        if (tmpKey.equals(key)) {
            return tree;
        }else if (tmpKey > key) {
            return getNode(tree.getLeft(),key);
        }else {
            return getNode(tree.getRight(),key);
        }

    }
    public Node getMinNode(Node tree) {
        if (Objects.nonNull(tree) && Objects.isNull(tree.left)) {
            return tree;
        }
        return getMinNode(tree.left);
    }
    public void del (Integer key) {
        root = del(root,key);
    }
    public Node  del(Node tree,Integer key) {
        if (Objects.isNull(tree)) {
            return null;
        }
        // 1. 找到待删除节点
        Node node = getNode(tree, key);
        if (Objects.isNull(node)) {
            return null;
        }
//        2. 找到代替节点   node 右子树的最小节点
        // 2.1
        if (Objects.isNull(node.right)) {
            return node.left;
        }
        // 2.2
        if (Objects.isNull(node.left)) {
            return node.right;
        }

        // 2.3 找到代替节点   node 右子树的最小节点
        Node replaeNode = getMinNode(node);

        // 3. 删除右子树的最小节点

        return null;
    }

    /**
     * 前序遍历
     * @return
     */
    public Queue preErgodic(){
        Queue keys = new ArrayBlockingQueue(size);
        return preErgodic(root,keys);
    }
    public Queue preErgodic(Node tree,Queue keys){

        keys.add(tree.val);
        if (Objects.nonNull(tree.left)) {
            preErgodic(tree.left,keys);
        }
        if (Objects.nonNull(tree.right)) {
            preErgodic(tree.right,keys);
        }
        return keys;
    }

    /**
     * 中序遍历
     * @return
     */
    public Queue midErgodic(){
        Queue keys = new ArrayBlockingQueue(size);
        return midErgodic(root,keys);
    }

    public Queue midErgodic(Node tree,Queue keys) {

        if (Objects.nonNull(tree.left)) {
            midErgodic(tree.left,keys);
        }
        keys.add(tree.val);
        if (Objects.nonNull(tree.right)) {
            midErgodic(tree.right,keys);
        }
        return keys;
    }

    public Queue afterErgodic(){
        Queue keys = new ArrayBlockingQueue(size);
        return afterErgodic(root,keys);
    }

    public Queue afterErgodic(Node tree,Queue keys) {

        if (Objects.nonNull(tree.left)) {
            afterErgodic(tree.left,keys);
        }
        if (Objects.nonNull(tree.right)) {
            afterErgodic(tree.right,keys);
        }
        keys.add(tree.val);
        return keys;
    }

    /**
     * 层序遍历
     * @return
     */
    public Queue layerErgodic() {
        Queue<Node> nodes = new ArrayBlockingQueue<Node>(size);
        Queue keys = new ArrayBlockingQueue(size);
        nodes.add(root);
        while (!nodes.isEmpty()) {
            Node poll = nodes.poll();
            if (poll.left != null) {
                nodes.add(poll.left);
            }
            if (poll.right != null) {
                nodes.add(poll.right);
            }
            keys.add(poll.getVal());
        }
        return keys;
    }
    public int maxDepth() {
        return maxDepth(root);
    }
    public int maxDepth(Node tree){

        if (tree == null) {
            return 0;
        }
        int max = 0;
        int leftMax = 0;
        int rightMax = 0;
        if (tree.left != null) {
            leftMax = maxDepth(tree.left);
        }
        if (tree.right != null) {
            rightMax = maxDepth(tree.right);
        }
        max = leftMax>rightMax?leftMax+1:rightMax+1;
        return max;

    }

    /**
     * 折纸树
     * @param tree
     * @return
     */
    public Node paperFold(Node tree) {
        if (tree == null) {
            tree = new Node(null,null,1,"down");
            return tree;
        }

        tree.left =  new Node(null,null,1,"down");
        tree.right =  new Node(null,null,1,"up");
        return tree;
    }

    private class Node {
        public Node left;
        public Node right;
        public Integer key;
        public String val;

        public Node (Node left,Node right, Integer key,String val) {
            this.left = left;
            this.right = right;
            this.key = key;
            this.val = val;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public Integer getKey() {
            return key;
        }

        public void setKey(Integer key) {
            this.key = key;
        }

        public String getVal() {
            return val;
        }

        public void setVal(String val) {
            this.val = val;
        }
    }
    public static void main(String[] args) {
        BinaryTree binaryTree = new BinaryTree();
//        binaryTree.put(8,"我是8");
//        binaryTree.put(3,"我是3");
//        binaryTree.put(7,"我是7");
//        binaryTree.put(6,"我是6");
//        binaryTree.put(9,"我是9");
        // A B C D E F G H
        // 1 2 3 4 5 6 7 8
        binaryTree.put(5,"E");
        binaryTree.put(2,"B");
        binaryTree.put(7,"G");

        binaryTree.put(1,"A");
        binaryTree.put(4,"D");
        binaryTree.put(3,"C");
        binaryTree.put(6,"F");
        binaryTree.put(8,"H");

        System.out.println(binaryTree.size );
        System.out.println(binaryTree.get(7));
        System.out.println(binaryTree.get(7));
        binaryTree.preErgodic().forEach(e->{
            System.out.print(e);
        });
        System.out.println();
        binaryTree.midErgodic().forEach(e->{
            System.out.print(e);
        });
        System.out.println();
        binaryTree.afterErgodic().forEach(e->{
            System.out.print(e);
        });
        System.out.println();
        binaryTree.layerErgodic().forEach(e->{
            System.out.print(e);
        });
        System.out.println(binaryTree.maxDepth());
    }
}
