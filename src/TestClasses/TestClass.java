package lab.TestClasses;

import lab.Annotations.XmlObject;
import lab.Annotations.XmlTag;

@XmlObject
public class TestClass {

    @XmlTag
    private String object;

    TestClass(String object){
        this.object = object;
    }
}
