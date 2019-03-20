
import lab.XMLObjects.Sk_Attribute;
import lab.XMLObjects.Sk_Tag;

import java.io.FileWriter;
import java.io.IOException;

public class XMLPrinter implements ObjectPrinter {

    @Override
    public void printObj(Sk_Tag tag) {
        System.out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        System.out.print("<" + tag.getName());
        for (Sk_Attribute a : tag.getAttributes()){
            if(a.getValue() != null) {
                System.out.print(" " + a.getName() + "=\"" + a.getValue() + "\"");
            }
        }
        System.out.println(">");
        this.print(tag, 1);
        System.out.println("</"+tag.getName()+">");
    }

    private void print(Sk_Tag tag, int level){
        for (Sk_Tag t : tag.getChildTags().values()){
            this.printSpace(level);
            System.out.print("<" + t.getName());
            for (Sk_Attribute a : t.getAttributes()){
                if(a.getValue() != null) {
                    System.out.print(" " + a.getName() + "=\"" + a.getValue() + "\"");
                }
            }
            System.out.println(">");
            if(t.getChildTag() != null){
                this.print(t.getChildTag(), level + 1);
            }
            else if(t.getValue() != null){
                this.printSpace(level + 2);
                System.out.println(t.getValue());
            }
            this.printSpace(level);
            System.out.println("</"+t.getName()+">");
        }
    }

    private void printSpace(int level){
        for(int i = 0; i < level; i++){
            System.out.print("   ");
        }
    }

    @Override
    public void printObj(Sk_Tag tag, FileWriter writer) throws IOException {
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        writer.write("<" + tag.getName());
        for (Sk_Attribute a : tag.getAttributes()){
            if(a.getValue() != null) {
                writer.write(" " + a.getName() + "=\"" + a.getValue() + "\"");
            }
        }
        writer.write(">\n");
        this.print(tag, 1, writer);
        writer.write("</"+tag.getName()+">\n");
        writer.close();
    }

    private void print(Sk_Tag tag, int level, FileWriter writer) throws IOException {
        for (Sk_Tag t : tag.getChildTags().values()){
            this.printSpace(level, writer);
            writer.write("<" + t.getName());
            for (Sk_Attribute a : t.getAttributes()){
                if(a.getValue() != null) {
                    writer.write(" " + a.getName() + "=\"" + a.getValue() + "\"");
                }
            }
            writer.write(">\n");
            if(t.getChildTag() != null){
                this.print(t.getChildTag(), level + 1, writer);
            }
            else if(t.getValue() != null){
                this.printSpace(level + 1, writer);
                writer.write(t.getValue());
                writer.write("\n");
            }
            this.printSpace(level, writer);
            writer.write("</"+t.getName()+">\n");
        }
    }

    private void printSpace(int level, FileWriter writer) throws IOException {
        for(int i = 0; i < level; i++){
            writer.write("   ");
        }
    }
}
