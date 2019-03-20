package lab.XMLObjects;

import lab.Annotations.XmlTag;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.*;

public class Sk_Tag {
    private Member obj;
    private String tagName;
    private String name;
    private String value;

   private Set<Sk_Attribute> attributes;
   private Map<String, Sk_Tag> childTags;
   private Sk_Tag childTag;

    public Sk_Tag(Field obj, XmlTag tag) {
        obj.setAccessible(true);
        this.obj = obj;
        this.tagName = tag.name();
        this.name = obj.getName();
        if(tagName.equals("")){
            tagName = name;
        }

        this.value = null;

        attributes = new HashSet<>();
        childTags = new HashMap<>();
        childTag = null;
    }

    public Sk_Tag(Class aclass){
        obj = null;
        tagName = aclass.getSimpleName();
        name = aclass.getSimpleName();
        value = null;
        attributes = new HashSet<>();
        childTags = new HashMap<>();
        childTag = null;
    }

    public Sk_Tag(Method obj, XmlTag tag) {
        this.obj = obj;
        this.tagName = tag.name();
        this.name = obj.getName();

        this.value = null;

        attributes = new HashSet<>();
        childTags = new HashMap<>();
        childTag = null;
    }

    public Map<String, Sk_Tag> getChildTags() {
        return childTags;
    }

    public void setChild(Sk_Tag tag){
        this.childTag = tag;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public Set<Sk_Attribute> getAttributes() {
        return attributes;
    }

    public String getTagName() {
        return tagName;
    }

    public Member getObj() {
        return obj;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void addChild(Sk_Tag tag) {
        this.childTags.put(tag.getTagName(), tag);
    }

    public void addAttribute(Sk_Attribute attribute){
        this.attributes.add(attribute);
    }

    public boolean attributesContains(Sk_Attribute attribute){
        return this.attributes.contains(attribute);
    }

    public void setChildTags(Map<String, Sk_Tag> tags){
        this.childTags = tags;
    }

    public Sk_Tag getChildTag(){
        return childTag;
    }
}
