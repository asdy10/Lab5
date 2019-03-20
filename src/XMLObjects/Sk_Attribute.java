package lab.XMLObjects;

import lab.Annotations.XmlAttribute;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

public class Sk_Attribute {
    private Member obj;
    private String tagName;
    private String name;
    private String value;


    public Sk_Attribute(Field obj) {
        this.obj = obj;
        tagName = null;
        name = obj.getName();
        value = null;
    }

    public Sk_Attribute(Method obj) {
        this.obj = obj;
        tagName = null;
        name = obj.getName();
        value = null;
    }

    public String getName() {
        return name;
    }

    public String getTagName() {
        return tagName;
    }

    public Member getObj() {
        return obj;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
