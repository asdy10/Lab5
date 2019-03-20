package lab.TestClasses;

import lab.Annotations.XmlAttribute;
import lab.xml_serializer.Annotations.XmlObject;
import lab.Annotations.XmlTag;

@XmlObject
public class Person {

    @XmlTag(name = "first")
    private String name;

    @XmlTag(name = "second_name")
    private String second_name;

    @XmlAttribute(tag = "first")
    private String lang;

    @XmlTag
    private TestClass test = new TestClass("1");

    @XmlTag
    public Integer getName(){
        return (int) age;
    }

    @XmlAttribute
    public String lenght(){
        return "qwer";
    }

    @XmlAttribute
    private float age;

    @XmlAttribute(tag = "second_name")
    public Integer money;

    public Person(String name, String second_name, String lang, float age, int money) {
        this.name = name;
        this.second_name = second_name;
        this.lang = lang;
        this.age = age;
        this.money = money;
    }
}