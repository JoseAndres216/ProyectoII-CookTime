package Logic.FileManagement;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class JsonSimpleReadExample {

    public static void main(String[] args) {

        JSONParser parser = new JSONParser();

        try (Reader reader = new FileReader("C:\\Users\\eduar\\Desktop\\CookTime\\ProyectoII-CookTime\\CookTimeServer\\src\\Logic\\FileManagement\\Outputs\\test.json")) {

            JSONObject bst = (JSONObject) parser.parse(reader);
            System.out.println(bst);


        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }


}