package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * A generic tree is a tree which can have as many children as it can be It
 * might be possible that every node present is directly connected to root node.
 * 泛型树
 * <p>
 * In this code Every function has two copies: one function is helper function
 * which can be called from main and from that function a private function is
 * called which will do the actual work. I have done this, while calling from
 * main one have to give minimum parameters.
 */
public class GenericTree {

    public static void main(String[] args) {
        GenericTree a = new GenericTree();
//        a.display();
        System.out.println("泛型树有"+a.size2()+"个节点");
        System.out.println("泛型树最大节点值为:"+a.max());

        a.removeleaves();
    }
    private static class Node {

        int data;
        ArrayList<Node> child = new ArrayList<>();
        public Node(){}
        public Node(int data){
            this.data = data;
            child = null;
        }

    }
//    根节点
    private Node root;

    private int size;

    public GenericTree() { // Constructor
        Scanner scn = new Scanner(System.in);
        root = create_treeG(null, 0, scn);
    }

    private Node create_treeG(Node node, int childIndex, Scanner scn) {
        // display
        if (node == null) {
            System.out.println("Enter root's data");
        } else {
            System.out.println("Enter data of parent of index " + node.data + " " + childIndex);
        }
        // input
        node = new Node();
        node.data = scn.nextInt();
        System.out.println("number of children");
        int number = scn.nextInt();
        for (int i = 0; i < number; i++) {
            Node child = create_treeG(node, i, scn);
            size++;
            node.child.add(child);
        }
        return node;
    }

    /**
     * Function to display the generic tree
     */
    public void display() { // Helper function
        display_1(root);
    }

    private void display_1(Node parent) {
        System.out.print(parent.data + "=>");
        for (int i = 0; i < parent.child.size(); i++) {
            System.out.print(parent.child.get(i).data + " ");
        }
        System.out.println(".");
        for (int i = 0; i < parent.child.size(); i++) {
            display_1(parent.child.get(i));
        }
    }

    /**
     * One call store the size directly but if you are asked compute size this
     * function to calculate size goes as follows
     *
     * @return size
     */
    public int size2() {
        return size2(root);
    }

    private int size2(Node roott) {
        int sz = 0;
        for (int i = 0; i < roott.child.size(); i++) {
            sz += size2(roott.child.get(i));
        }
        return sz + 1;
    }

    /**
     * Function to compute maximum value in the generic tree
     *
     * @return maximum value
     */
    public int max() {
        int maxi = root.data;
        return max(root, maxi);
    }

    private int max(Node roott, int maxi) {
        if (maxi < roott.data) {
            maxi = roott.data;
        }
        for (int i = 0; i < roott.child.size(); i++) {
            maxi = max(roott.child.get(i), maxi);
        }

        return maxi;
    }

    /**
     * Function to compute HEIGHT of the generic tree
     *
     * @return height
     */
    public int height() {
        return height(root) - 1;
    }

    private int height(Node node) {
        int h = 0;
        for (int i = 0; i < node.child.size(); i++) {
            int k = height(node.child.get(i));
            if (k > h) {
                h = k;
            }
        }
        return h + 1;
    }

    /**
     * Function to find whether a number is present in the generic tree or not
     *
     * @param info number
     * @return present or not
     */
    public boolean find(int info) {
        return find(root, info);
    }

    private boolean find(Node node, int info) {
        if (node.data == info) {
            return true;
        }
        for (int i = 0; i < node.child.size(); i++) {
            if (find(node.child.get(i), info)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Function to calculate depth of generic tree
     *
     * @param dep depth
     */
    public void depthcaller(int dep) {
        depth(root, dep);
    }

    public void depth(Node node, int dep) {
        if (dep == 0) {
            System.out.println(node.data);
            return;
        }
        for (int i = 0; i < node.child.size(); i++) {
            depth(node.child.get(i), dep - 1);
        }
        return;
    }

    /**
     * Function to print generic tree in pre-order
     */
    public void preordercall() {
        preorder(root);
        System.out.println(".");
    }

    private void preorder(Node node) {
        System.out.print(node.data + " ");
        for (int i = 0; i < node.child.size(); i++) {
            preorder(node.child.get(i));
        }
    }

    /**
     * Function to print generic tree in post-order
     */
    public void postordercall() {
        postorder(root);
        System.out.println(".");
    }

    private void postorder(Node node) {
        for (int i = 0; i < node.child.size(); i++) {
            postorder(node.child.get(i));
        }
        System.out.print(node.data + " ");
    }

    /**
     * Function to print generic tree in level-order
     */
    public void levelorder() {
        LinkedList<Node> q = new LinkedList<>();
        q.addLast(root);
        while (!q.isEmpty()) {
            int k = q.getFirst().data;
            System.out.print(k + " ");

            for (int i = 0; i < q.getFirst().child.size(); i++) {
                q.addLast(q.getFirst().child.get(i));
            }
            q.removeFirst();
        }
        System.out.println(".");
    }

    /**
     * Function to remove all leaves of generic tree
     */
    public void removeleaves() {
        removeleaves(root);
    }

    private void removeleaves(Node node) {
        ArrayList<Integer> arr = new ArrayList<>();
        for (int i = 0; i < node.child.size(); i++) {
            if (node.child.get(i).child.size() == 0) { // 如果为叶子节点，加入arr
                arr.add(i);
                // node.child.remove(i);
                // i--;
            } else {
                removeleaves(node.child.get(i));
            }
        }
        for (int i = arr.size() - 1; i >= 0; i--) {
            node.child.remove(arr.get(i) + 0);
        }
    }
}
