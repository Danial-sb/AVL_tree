import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class AvlTree {
    Node root;
    int size;
    List<String[]> list = new ArrayList<>();
    int numSugges=0;
    public void find(String key)
    {
        Node current = root;
        if(current==null){
            System.out.println("Tree is Empty.");
            return ;
        }

        while (current != null)
        {
            int compare = (current.getTitle()).compareTo(key);
            if (compare == 0)
            {
                break;
            }
            else
            {
                if( compare < 0)
                {
                    current = current.right;
                }
                else
                {
                    current = current.left;
                }
            }
        }
        if(current==null){
            Suggestion(root, key);

        }else{
            System.out.println("Title: "+current.getTitle()+"\n"+"description:"+current.getDes());
            return;
        }


    }
    private void Suggestion(  Node node,String key){


        if (node != null && numSugges<2)
        {
            boolean compare = node.getTitle().contains(key);

            if (compare==true)
            {
                numSugges++;
                System.out.println("Suggestion:\nTitle: "+node.getTitle()+"\n"+"description:"+node.getDes());

            }
            Suggestion(node.left, key);
            Suggestion(node.right, key);


        }else{
            return;
        }

    }

    public Node insert(String key,String des)
    {
        root = insert(root, key,des);

        return root;
    }
    public void delete(String key)
    {
        root = delete(root, key);
    }
    public Node getRoot()
    {
        return root;
    }


    private Node insert(Node node, String title,String des)
    {
        if (node == null)
        {
            return new Node(title,des);

        }else{
            int compare = (node.getTitle()).compareTo(title);
            if (compare > 0)
            {
                node.left = insert(node.left, title,des);
            }
            else if (compare < 0)
            {
                node.right = insert(node.right, title,des);
            }
            else
            {
                System.out.println("That key has already been used");
                return node;
            }
        }

        return rebalance(node);


    }

    Node delete(Node node, String key)
    {
        int compare = (node.title).compareTo(key);

        if (node == null)
        {
            return node;
        }
        else if(compare > 0)
        {
            node.left = delete(node.left, key);
        }
        else if(compare < 0)
        {
            node.right = delete(node.right, key);
        }
        else
        {
            if (node.left == null || node.right == null)
            {
                if(node.left == null)
                {
                    node = node.right;
                }
                else
                {
                    node = node.left;
                }
            }
            else
            {
                Node mostLeftChild = mostLeftChild(node.right);
                node.title = mostLeftChild.title;
                node.right = delete(node.right, node.title);
            }
        }

        if (node != null)
        {
            node = rebalance(node);
        }
        return node;
    }

    private Node mostLeftChild(Node node)
    {
        Node current = node;

        while (current.left != null)
        {
            current = current.left;
        }
        return current;
    }


    Node rotateRight(Node y)
    {
        Node x = y.left;
        Node z = x.right;
        x.right = y;
        y.left = z;

        updateHeight(y);
        updateHeight(x);
        return x;
    }

    Node rotateLeft(Node y)
    {
        Node x = y.right;
        Node z = x.left;
        x.left = y;
        y.right = z;

        updateHeight(y);
        updateHeight(x);
        return x;
    }

    Node rebalance(Node z)
    {
        updateHeight(z);
        int balance = getBalance(z);
        if (balance > 1)
        {
            if (height(z.right.right) > height(z.right.left))
            {
                z = rotateLeft(z);
            }
            else
            {
                z.right = rotateRight(z.right);
                z = rotateLeft(z);
            }
        }
        else if (balance < -1)
        {
            if (height(z.left.left) > height(z.left.right))
            {
                z = rotateRight(z);
            }
            else
            {
                z.left = rotateLeft(z.left);
                z = rotateRight(z);
            }
        }
        return z;
    }




    void updateHeight (Node n)
    {
        n.height = 1 + Math.max(height(n.left), height(n.right));
    }

    int height (Node n)
    {
        if (n == null)
        {
            return 0;
        }
        else
        {
            return n.height;
        }
    }

    int getBalance (Node n)
    {
        if (n == null)
        {
            return 0;
        }
        else
        {
            return height(n.right) - height(n.left);
        }
    }

    void preOrder(Node node)
    {
        if (node != null)
        {

            String[] data = {node.getTitle(),node.getDes()};
            list.add(data);
            preOrder(node.left);
            preOrder(node.right);
        }
    }
    void printInorder(Node node)
    {
        if (node == null)
            return;

        /* first recur on left child */
        printInorder(node.left);

        String[] data = {node.getTitle(),node.getDes()};
        System.out.print(data[0]+"->");

        /* now recur on right child */
        printInorder(node.right);
    }

    public void WriteToCsvFile() throws IOException{
        CsvWriterSimple writer = new CsvWriterSimple();
        writer.writeToCsvFile(createCsvDataSpecial(), new File("C:\\Users\\ASUS ZENBOOK\\Desktop\\Book1.csv"));


    }
    public String traversePreOrder(Node root) {

        if (root == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(root.getTitle());

        String pointerRight = "└──";
        String pointerLeft = (root.getRight() != null) ? "├──" : "└──";

        traverseNodes(sb, "", pointerLeft, root.getLeft(), root.getRight() != null);
        traverseNodes(sb, "", pointerRight, root.getRight(), false);

        return sb.toString();
    }
    public void traverseNodes(StringBuilder sb, String padding, String pointer, Node node,
                              boolean hasRightSibling) {
        if (node != null) {
            sb.append("\n");
            sb.append(padding);
            sb.append(pointer);
            sb.append(node.getTitle());

            StringBuilder paddingBuilder = new StringBuilder(padding);
            if (hasRightSibling) {
                paddingBuilder.append("│  ");
            } else {
                paddingBuilder.append("   ");
            }

            String paddingForBoth = paddingBuilder.toString();
            String pointerRight = "└──";
            String pointerLeft = (node.getRight() != null) ? "├──" : "└──";

            traverseNodes(sb, paddingForBoth, pointerLeft, node.getLeft(), node.getRight() != null);
            traverseNodes(sb, paddingForBoth, pointerRight, node.getRight(), false);
        }
    }
    public void print(PrintStream os) {
        os.print(traversePreOrder(root));
        System.out.println("");
        printInorder(root);
        System.out.println("");
    }



    private  List<String[]> createCsvDataSpecial() {


        preOrder(root);
        return list;

    }

}