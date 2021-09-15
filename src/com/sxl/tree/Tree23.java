package com.sxl.tree;

/**
 * @author songxl
 * @create 2021-09-13 16:27
 * @desc 2-3 查找树
 **/
public class Tree23 {


    private Node root;
    private int n;
    public Tree23() {
        root = new Node();
        n = 0;
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return  n==0;
    }

    /**
     * 查找含有key的键值对
     * @param key
     * @return
     */
    public String find(int key) {
        Node currentNode = root;
        int childNum;
        while (true) {
            if ((childNum = currentNode.findItem(key)) != -1) {
                // key 存在
                return currentNode.itemDatas[childNum].value;
            }else if (currentNode.isLeaf()) {
                // 当前节点不是要找的key 并且没有叶子节点
                return null;
            }else {
                // 当前节点不是要找的key 并且有叶子节点
                currentNode = getNextChild(currentNode,key);
            }
        }
    }

    /**
     * 插入数据到2-3 查找树
     * @param key
     * @param value
     */
    public void insert(Integer key,String value) {

        Data data = new Data(key,value);
        Node currentNode = root;
        while (true) {
            if (currentNode.isLeaf()) {
                break;
            }else {
//                不是叶子节点
                // 获取下一个子节点
                currentNode = getNextChild(currentNode,key);
                for (int i = 0; i < currentNode.getItemNum(); i++) {
                    if(currentNode.getData(i).key.equals(key)) {
                        // 找到了 赋值
                        currentNode.getData(i).value = value;
                        return;
                    }
                }

            }
        }
        // 没有找到(但是找到了插入位置)，执行插入操作
        if (currentNode.isFull()) {
            spit(currentNode,data);
        }else {
            currentNode.insertData(data);
        }

    }
    /**
     * 裂变节点
     * 构造新节点替换
     * @param node 被裂变的节点
     * @param data 要保存的数据
     */
    private void spit(Node node, Data data) {
        Node parent = node.getParent();
        // 最大节点
        Node maxNode = new Node();
        // 中间节点
        Node midNode = new Node();
        // 找出 maxNode  midNode 和最小节点 node
        if(data.key < node.getData(0).key) {
            maxNode.insertData(node.removeLastItem());
            midNode.insertData(node.removeLastItem());
        }else if (data.key < node.getData(1).key) {
            // 中间
            maxNode.insertData(node.removeLastItem());
            midNode.insertData(data);
        }else {
            maxNode.insertData(data);
            midNode.insertData(node.removeLastItem());
        }

        if (node == root) {
            root = midNode;
        }
        midNode.connectChild(0,node);
        midNode.connectChild(1,maxNode);
//        conn
    }

    /**
     * 连接节点
     * @param parent
     * @param node
     */
    private void connectNoe(Node parent,Node node) {

    }

    /**
     * 获取指定节点  含 key节点的子节点(树) （key <=  node.getData(i).key）
     * @param node
     * @param key
     * @return
     */
    private Node getNextChild(Node node,Integer key) {

        for (int i = 0; i < node.getItemNum(); i++) {
            if (key < node.getData(i).key) {
                // 要查找的key 小于指定节点的key 最左边
                return node.getChild(i);
            }else if (node.getData(i).key.equals(key)) {
                return node;
            }
        }
        // 没有找到，直接返回子节点中的 左节点/中/右 节点
        return node.getChild(node.getItemNum());
    }
    private static class Node {
        private static final Integer N = 3;
        public Node parent;
        /**
         * 子节点
         * 0：左节点
         * 1：中间节点
         * 2：右节点
         */
        public Node[] childNodes = new Node[N];

        /**
         * 节点保存的数据
         */
        public Data[] itemDatas = new Data[N-1];
        /**
         * 节点保存数据的个数
         */
        public int itemCount = 0;


        public void print() {
            for(int i= 0; i< itemCount;i++) {
                itemDatas[i].print();
            }
            System.out.println();
        }

        /**
         * 判断节点是否是叶子节点
         * 子要没有节点就是叶子节点
         * @return
         */
        public boolean isLeaf() {
            return childNodes[0] == null && childNodes[1] == null && childNodes[2] == null;
        }

        /**
         * 判断节点是否存满
         * itemCount ==2
         * @return
         */
        public boolean isFull () {
            return itemCount == N-1;
        }
        public Node getParent() {
            return parent;
        }

        /**
         * 链接子节点
         * @param index 连接的位置
         * @param child
         */
        private void connectChild(int index,Node child) {
            childNodes[index] = child;
            if (child != null) {
                child.parent == this;
            }
        }

        /**
         * 解除节点
         * @param index
         * @return
         */
        private Node disconnectChild(int index) {
            Node tmp = childNodes[index];
            childNodes[index] = null;
            return tmp;
        }

        /**
         * 获取节点的左或者右键值对
         * @param index 0 左  1 右
         * @return
         */
        private Data getData(int index) {
            return itemDatas[index];
        }

        /**
         * 获取某位置的子树
         * @param index
         * @return
         */
        private Node getChild(int index) {
            return childNodes[index];
        }

        /**
         * 返回节点中键值对数量
         * @return
         */
        public int getItemNum() {
            return itemCount;

        }

        /**
         * 寻找key在节点的位置
         * @param key
         * @return 如果不存在返回-1
         */
        private int findItem(Integer key) {
            for (int i = 0; i < itemDatas.length; i++) {
                if (itemDatas[i] == null) {
                    break;
                }else if (itemDatas[i].key.equals(key)) {
                    return i;
                }
            }
            return -1;
        }

        /**
         * 插入数据 前提是节点未满
         * @return
         */
        private int insertData(Data data) {
            itemCount++;
            for (int i = N-2; i>=0;i++) {
                if (itemDatas[i] == null) {
                    continue;
                }else {
                    if (data.key < itemDatas[i].key) {
                        // 要插入的数据小于当前key
                        // 当前key右移，要插入的数据插入到现有位置
                        itemDatas[i+1] = itemDatas[i];
                    }else {
                        // >= 直接在后面插入
                        itemDatas[i+1] = data;
                        return i+1;
                    }
                }
            }
            itemDatas[0] = data;
            return 0;

        }

        /**
         *移除最后一个键值对
         * @return
         */
        private Data removeLastItem() {
            Data tmp = itemDatas[itemCount-1];
            itemDatas[itemCount-1] = null;
            itemCount--;
            return tmp;
        }
    }




    /**
     * 节点保存的数据
     */
    private static class Data {
        public Integer key;
        public String value;

        public Data(Integer key, String value) {
            this.key = key;
            this.value = value;
        }

        public void print() {
            System.out.print("/"+key+"----"+value);
        }
    }
}
