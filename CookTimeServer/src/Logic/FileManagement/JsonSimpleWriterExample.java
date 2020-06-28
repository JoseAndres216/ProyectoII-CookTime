package Logic.FileManagement;


import Logic.DataStructures.BinarySearchTree.BST;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class JsonSimpleWriterExample {

    public static void main(String[] args) {

        JSONObject obj = new JSONObject();
        BST<Integer> arbol = new BST<>();
        arbol.insert(50);
        arbol.insert(580);
        arbol.insert(5);
        arbol.insert(55);
        arbol.insert(85);


        try (FileWriter file = new FileWriter("C:\\Users\\eduar\\Desktop\\CookTime\\ProyectoII-CookTime\\CookTimeServer\\src\\Logic\\FileManagement\\Outputs\\test.json")) {
            file.write(obj.toJSONString());

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.print(obj);

    }

}