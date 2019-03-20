

import lab.XMLObjects.Sk_Tag;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;

public interface ObjectPrinter {

    void printObj(Sk_Tag tag);

    void printObj(Sk_Tag tag, FileWriter writer) throws IOException;

}
