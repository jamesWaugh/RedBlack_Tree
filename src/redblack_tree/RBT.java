package redblack_tree;

import java.io.File;
import java.io.FileNotFoundException;
import static java.lang.Integer.max;
import java.util.ArrayList;
import java.util.Scanner;

class Node {

    Node parent, left, right;
    int data;
    int color;

    public Node(int nData) {
        data = nData;
    }

}

class RedBlackTree {

    private Node root;

    public int BLACK = 1;
    public int RED = -1;

    public RedBlackTree(int d) {
        this.BLACK = 1;
        this.RED = -1;
        root = new Node(d);
    }

    public Node getRoot() {
        return this.root;
    }

    public void insert(Node z) {
        Node y = null;
        Node x = root;
        while (x != null) {
            y = x;
            if (z.data < x.data) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        z.parent = y;
        if (y == null) {
            root = z;
        } else if (z.data < y.data) {
            y.left = z;
        } else {
            y.right = z;
        }
        z.left = null;
        z.right = null;
        z.color = RED;
        fix(z, y);
    }

    public void fix(Node z, Node y) {
        while (z.parent != null && z.parent.color == RED) {
            if (z.parent == z.parent.parent.left) {
                y = z.parent.parent.right;
                if (y.color == RED) {
                    z.parent.color = BLACK;
                    y.color = BLACK;
                    z.parent.parent.color = RED;
                    z = z.parent.parent;
                } else if (z == z.parent.right) {
                    z = z.parent;
                    leftRotate(z, z.right);
                    z.parent.color = BLACK;
                    z.parent.parent.color = RED;
                    rightRotate(z.parent.parent, z.parent.parent.left);
                }
            } else {
                y = z.parent.parent.left;
                if (y.color == -1) {
                    z.parent.color = BLACK;
                    y.color = BLACK;
                    z.parent.parent.color = RED;
                    z = z.parent.parent;
                } else if (z == z.parent.left) {
                    z = z.parent;
                    rightRotate(z.parent.parent, z.parent.parent.left);
                    z.parent.color = BLACK;
                    z.parent.parent.color = RED;
                    leftRotate(z, z.right);
                }
            }
        }
        root.color = BLACK;
    }

    public void leftRotate(Node x, Node y) {
        y = x.right;
        x.right = y.left;
        if (y.left != null) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    public void rightRotate(Node x, Node y) {
        x = y.right;
        y.right = x.left;
        if (x.left != null) {
            x.left.parent = y;
        }
        x.parent = y.parent;
        if (y.parent == null) {
            root = x;
        } else if (y == y.parent.left) {
            y.parent.left = x;
        } else {
            y.parent.right = x;
        }
        x.left = y;
        y.parent = x;
    }

    public void colorFix(Node n) {
        if (n.color == RED) {
            n.data = 0 - n.data;
        }
        if (n.left != null) {
            colorFix(n.left);
        }
        if (n.right != null) {
            colorFix(n.right);
        }
    }

    public void printTree(Node n) {
        int h = height(n);
        for (int i = 1; i <= h; i++) {
            printHelper(n, i);
            System.out.println();
        }
    }

    public void printHelper(Node n, int l) {
        if (n == null) {
            return;
        }
        if (l == 1) {
            if (n.parent != null) {
                System.out.print("(" + n.data + ", " + n.parent.data + ")");
            } else {
                System.out.print("(" + n.data + ", null)");
            }
        } else if (l > 1) {
            printHelper(n.left, l - 1);
            printHelper(n.right, l - 1);
        }
    }

    public int height(Node n) {
        if (n == null) {
            return 0;
        }
        return 1 + max(height(n.left), height(n.right));
    }
}

public class RBT {

    public static ArrayList<Integer> readFile(String fileName, String directory) {
        System.out.println("Reading file...\n");
        File file = new File(directory + "\\" + fileName + ".txt");
        ArrayList<Integer> intList = new ArrayList();
        try {
            System.out.println("Reading integers from file...\n");
            Scanner scan = new Scanner(file);
            while (scan.hasNextInt()) {
                intList.add(scan.nextInt());
            }
            return intList;
        } catch (FileNotFoundException ex) {
            System.out.println("READ FILE ERROR");
            return null;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanMain = new Scanner(System.in);
        System.out.println("Input File Name (without .txt extension):");
        String name = scanMain.nextLine();
        System.out.println("Input File Directory:\nWARNING: The output files will be printed to this directory.\nEXAMPLE: C:\\Users\\YourName\\TargetFolder");
        String dir = scanMain.nextLine();
        System.out.println("Beginning analysis.\n");
        ArrayList<Integer> list = readFile(name, dir);
        RedBlackTree tree = new RedBlackTree(list.get(0));
        System.out.println("Creating RBT...");
        for (int i = 1; i < list.size(); i++) {
            tree.insert(new Node(list.get(i)));
        }
        System.out.println("Printing tree...\n");
        tree.colorFix(tree.getRoot());
        tree.printTree(tree.getRoot());
        System.out.println("\nDone.");
    }
}
