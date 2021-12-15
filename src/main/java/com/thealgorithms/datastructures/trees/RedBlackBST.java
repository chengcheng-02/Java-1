package com.thealgorithms.datastructures.trees;

import java.util.Scanner;

/**
 * 1.每个节点是红色的或黑色的
 * 2.根节点是黑色的
 * 3.每个叶节点（NIL）是黑色的,叶子节点不存储数据
 * 4.相邻节点不能同时为红
 * 5.对每个节点，从该节点到其所有后代叶节点的简单路径上，均包含相同数目的黑色节点
 */


public class RedBlackBST {
    public static void main(String[] args) {
        RedBlackBST a = new RedBlackBST();
        Node v1 = new Node(10);
        Node v2 = new Node(8);
        Node v3 = new Node(13);
        Node v4 = new Node(11);
        Node v5 = new Node(17);
        Node v6 = new Node(15);
        Node v7 = new Node(16);

        a.insert(v1);
        a.insert(v2);
        a.insert(v3);
        a.insert(v4);
        a.insert(v5);
        a.insert(v6);
        a.insert(v7);
        a.printTreepre(v1);
        a.delete(v4);

    }
//    红为0，黑为1
    private final static int R = 0;
    private final static int B = 1;

    private static class Node{

        int key = -1, color = B;
        Node left = nil, right = nil, p = nil;

        Node(int key) {
            this.key = key;
        }
    }
//    指向叶节点的引用
    private final static Node nil = new Node(-1);

    private Node root = nil;

    public void printTree(Node node) {
        if (node == nil) {
            return;
        }
        printTree(node.left);
        System.out.print(
                ((node.color == R) ? " R " : " B ") + "Key: " + node.key + " Parent: " + node.p.key + "\n");
        printTree(node.right);
    }

    public void printTreepre(Node node) {
        if (node == nil) {
            return;
        }
        System.out.print(
                ((node.color == R) ? " R " : " B ") + "Key: " + node.key + " Parent: " + node.p.key + "\n");
        printTree(node.left);
        printTree(node.right);
    }

    private Node findNode(Node findNode, Node node) {
        if (root == nil) {
            return null;
        }
        if (findNode.key == node.key) {
            return node;
        }
        else if (findNode.key < node.key) {
            if (node.left != nil) {
                return findNode(findNode, node.left);
            }
        } else if (findNode.key > node.key) {
            if (node.right != nil) {
                return findNode(findNode, node.right);
            }
        }
        return null;
    }

    private void insert(Node node) {
        Node temp = root;
        if (root == nil) { //没有节点，插入到根节点
            root = node;
            node.color = B;
            node.p = nil;
        } else {
            node.color = R;
            while (true) {
                if (node.key < temp.key) { //插入结点值比根节点小
                    if (temp.left == nil) { // 左孩子为空，插入到左孩子
                        temp.left = node;
                        node.p = temp;
                        break;
                    } else {
                        temp = temp.left;
                    }
                } else if (node.key >= temp.key) {
                    if (temp.right == nil) {
                        temp.right = node;
                        node.p = temp;
                        break;
                    } else {
                        temp = temp.right;
                    }
                }
            }
            fixTree(node);
        }
    }

    private void fixTree(Node node) {
//        当结点的父节点为红时，才需要修复操作
        while (node.p.color == R) {
//            叔叔节点
            Node uncle = nil;
//            如果节点的父节点是它父节点的左孩子，叔叔节点就是右孩子
            if (node.p == node.p.p.left) {

                uncle = node.p.p.right;
//                第一种情况: 叔叔节点存在，且为红
                if (uncle != nil && uncle.color == R) {
//                    将父节点，叔节点，与祖父节点换颜色，将要修复的节点迭代为祖父节点
                    node.p.color = B;
                    uncle.color = B;
                    node.p.p.color = R;
                    node = node.p.p;
                    continue;
                }
//                第二种情况: 新节点与父节点、祖父节点不在同一条斜线上，左右，先对父节点左旋即变为第三种情况
                if (node == node.p.right) {
                    node = node.p;
                    rotateLeft(node);
                }
//                第三种情况: 新节点与父节点、祖父节点在同一条斜线上，新节点的父节点右旋，父节点和祖父交换颜色
                node.p.color = B;
                node.p.p.color = R;
                rotateRight(node.p.p);
            } else {//            如果节点的父节点是它父节点的右孩子，叔叔节点就是左孩子，上面的镜像
                uncle = node.p.p.left;
                if (uncle != nil && uncle.color == R) {
                    node.p.color = B;
                    uncle.color = B;
                    node.p.p.color = R;
                    node = node.p.p;
                    continue;
                }
                if (node == node.p.left) {
                    node = node.p;
                    rotateRight(node);
                }
                node.p.color = B;
                node.p.p.color = R;
                rotateLeft(node.p.p);
            }
        }
//        当插入节点的父节点为黑时不会破坏红黑树结构
        root.color = B;
    }
//    左旋
    void rotateLeft(Node node) {
        if (node.p != nil) { //节点有父节点
            if (node == node.p.left) { //node节点是父节点的左孩子节点
//              父节点的左孩子节点等于节点的右孩子
                node.p.left = node.right;
            } else {    //node节点是父节点的右孩子节点
                node.p.right = node.right;
            }
            node.right.p = node.p;
            node.p = node.right;
            if (node.right.left != nil) {
                node.right.left.p = node;
            }
            node.right = node.right.left;
            node.p.left = node;
        } else { // 节点没有父节点
//            指向根节点的右孩子的引用right
            Node right = root.right;
//            原节点的右孩子的左孩子放到原节点的右孩子
            root.right = right.left;
//            修改父节点
            right.left.p = root;
            root.p = right;
//            right的左孩子为原节点
            right.left = root;
            right.p = nil;
//            将局部变量赋给root
            root = right;
        }
    }
//    右旋
    void rotateRight(Node node) {
        if (node.p != nil) {
            if (node == node.p.left) {
                node.p.left = node.left;
            } else {
                node.p.right = node.left;
            }

            node.left.p = node.p;
            node.p = node.left;
            if (node.left.right != nil) {
                node.left.right.p = node;
            }
            node.left = node.left.right;
            node.p.right = node;
        } else {
            Node left = root.left;
            root.left = root.left.right;
            left.right.p = root;
            root.p = left;
            left.right = root;
            left.p = nil;
            root = left;
        }
    }
//    待删除节点与with节点交换值
    void transplant(Node target, Node with) {
        if (target.p == nil) { // 删除的是根节点
            root = with;
        } else if (target == target.p.left) { // 删除的节点时父亲节点的左孩子
            target.p.left = with;
        } else {
            target.p.right = with;
        }
        with.p = target.p;
    }

    Node treeMinimum(Node subTreeRoot) {
        while (subTreeRoot.left != nil) {
            subTreeRoot = subTreeRoot.left;
        }
        return subTreeRoot;
    }

    boolean delete(Node z) {
        if ((z = findNode(z, root)) == null) {
            return false;
        }
//        x为待删除节点
        Node x;
        Node y = z;
        int yorigcolor = y.color;
//        若果待删除的节点不是叶子节点，找到待删除节点的中序遍历的后继节点
        if (z.left == nil) {
            x = z.right;
            transplant(z, z.right);
        } else if (z.right == nil) {
            x = z.left;
            transplant(z, z.left);
        } else { //待删除节点的左右子树都不为空
//            找到待删除节点的后继节点y
            y = treeMinimum(z.right);
            yorigcolor = y.color;
            x = y.right;
            if (y.p == z) {//若y就是待删除节点z的右孩子
                x.p = y;
            } else {
                transplant(y, y.right);
                y.right = z.right;
                y.right.p = y;
            }
//            待删除的节点与中序遍历的后继节点值交换
            transplant(z, y);
            y.left = z.left;
            y.left.p = y;
            y.color = z.color;
        }
//        删除节点为黑色，修复x，若为叶子节点则x为nil
        if (yorigcolor == B) {
            deleteFixup(x);
        }
        return true;
    }

    void deleteFixup(Node x) {
        while (x != root && x.color == B) {
            if (x == x.p.left) {
                Node w = x.p.right;
                if (w.color == R) {
                    w.color = B;
                    x.p.color = R;
                    rotateLeft(x.p);
                    w = x.p.right;
                }
                if (w.left.color == B && w.right.color == B) {
                    w.color = R;
                    x = x.p;
                    continue;
                } else if (w.right.color == B) {
                    w.left.color = B;
                    w.color = R;
                    rotateRight(w);
                    w = x.p.right;
                }
                if (w.right.color == R) {
                    w.color = x.p.color;
                    x.p.color = B;
                    w.right.color = B;
                    rotateLeft(x.p);
                    x = root;
                }
            } else {
                Node w = x.p.left;
                if (w.color == R) {
                    w.color = B;
                    x.p.color = R;
                    rotateRight(x.p);
                    w = x.p.left;
                }
                if (w.right.color == B && w.left.color == B) {
                    w.color = R;
                    x = x.p;
                    continue;
                } else if (w.left.color == B) {
                    w.right.color = B;
                    w.color = R;
                    rotateLeft(w);
                    w = x.p.left;
                }
                if (w.left.color == R) {
                    w.color = x.p.color;
                    x.p.color = B;
                    w.left.color = B;
                    rotateRight(x.p);
                    x = root;
                }
            }
        }
        x.color = B;
    }

    public void insertDemo() {
        Scanner scan = new Scanner(System.in);
        while(true) {
            System.out.println("Add items");

            int item;
            Node node;

            item = scan.nextInt();
            while (item != -999) {
                node = new Node(item);
                insert(node);
                item = scan.nextInt();
            }
            printTree(root);
            System.out.println("Pre order");
            printTreepre(root);
            break;
        }
        scan.close();
    }

    public void deleteDemo() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Delete items");
        int item;
        Node node;
        item = scan.nextInt();
        node = new Node(item);
        System.out.print("Deleting item " + item);
        if (delete(node)) {
            System.out.print(": deleted!");
        } else {
            System.out.print(": does not exist!");
        }

        System.out.println();
        printTree(root);
        System.out.println("Pre order");
        printTreepre(root);
        scan.close();
    }
}
