

import lab.TestClasses.Person;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] argc){
        Person person = new Person("a", "b", "r", 20, 1000);
        Serializer serializer = new Serializer(new XMLParser(), new XMLPrinter());
        try {
            serializer.serialize(person, "sc.xml");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}