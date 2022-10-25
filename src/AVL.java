import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class AVL {


    public static void main(String[] args) throws IOException {
        AvlTree tree = new AvlTree();
        String fileName = "C:\\Users\\ASUS ZENBOOK\\Desktop\\list.csv";
        File file = new File(fileName);
        try {
            Scanner inputStream = new Scanner(file);
            if(inputStream.hasNext()){
                inputStream.nextLine();
            }
            while (inputStream.hasNext()){
                String data = inputStream.nextLine();
                String[] dsplit= data.split(",");
                tree.root = tree.insert(dsplit[0], dsplit[1]);
            }
            inputStream.close();

        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }

        System.out.println("Import Data From CSV File to AVLTree.");

        do{
            Scanner n = new Scanner(System.in);
            System.out.println("\n=========================================");
            System.out.println("Options");
            System.out.println("1) Insert");
            System.out.println("2) Find");
            System.out.println("3) Delete");
            System.out.println("4) Data Diagram");
            System.out.println("5) Export");
            System.out.println("6) Exit");
            System.out.println("===========================");
            System.out.println("Select an Option:");
            int op = n.nextInt();
            switch(op){
                case 1:
                    System.out.println("Enter Title:");
                    String gettitle = n.next();
                    System.out.println("Enter Description:");
                    String getdes = n.next();
                    tree.insert(gettitle, getdes);
                    break;
                case 2:
                    System.out.println("Enter Title:");
                    String gettitle2 = n.next();
                    tree.find(gettitle2);
                    break;
                case 3:
                    System.out.println("Enter Title:");
                    String gettitle3 = n.next();
                    tree.delete(gettitle3);
                    break;
                case 4:
                    System.out.println("=========================================");
                    tree.print(System.out);
                    break;
                case 5:
                    tree.WriteToCsvFile();
                    break;
                case 6:
                    System.exit(0);
                default:
                    System.out.println("InValid Number");
            }

        }while(true);

    }

}
