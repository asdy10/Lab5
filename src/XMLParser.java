
import lab.Annotations.XmlAttribute;
import lab.Annotations.XmlObject;
import lab.Annotations.XmlTag;
import lab.XMLObjects.Sk_Attribute;
import lab.XMLObjects.Sk_Tag;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class XMLParser implements ObjectParser{

    private List<Class> classes;

    public XMLParser(){
        classes = new LinkedList<>();
    }

    @Override
    public Sk_Tag parse(Object obj) throws IllegalAccessException, InvocationTargetException {
        if(!obj.getClass().isAnnotationPresent(XmlObject.class)){
            throw new IllegalArgumentException("class '" + obj.getClass().getSimpleName() + "' is not annotated with 'XmlObject'");
        }
        if(classes.contains(obj.getClass())){
            throw new RuntimeException("infinity cicle was founded");
        }
        Class aclass = obj.getClass();
        classes.add(aclass);

        Sk_Tag objTag = new Sk_Tag(aclass);
        Map<String, Sk_Tag> tags = new HashMap<>();
        Map<String, List<Sk_Attribute>> attributes = new HashMap<>();

        tags.put(objTag.getName(), objTag);

        for(Field f : aclass.getDeclaredFields()){
            if(!f.isAnnotationPresent(XmlAttribute.class) && !f.isAnnotationPresent(XmlTag.class)){
                System.out.println(f.getName() + " have no annotations");
                continue;
            }
            if(f.getType() == void.class){
                throw new IllegalArgumentException("field '" + f.getName() + "' returns void");
            }
            if(f.isAnnotationPresent(XmlTag.class)){
                f.setAccessible(true);
                Sk_Tag tag = new Sk_Tag(f, f.getAnnotation(XmlTag.class));
                if(f.get(obj) != null){
                    tag.setValue(f.get(obj).toString());
                }
                if(f.getType().isAnnotationPresent(XmlObject.class)){
                    tag.setChild(this.parse(f.get(obj)));
                }
                if(tags.containsKey(tag.getName())){
                    throw new IllegalArgumentException("tag with name '" + tag.getName() + "' almost exist");
                }
                tags.put(tag.getTagName(), tag);
            }
            if(f.isAnnotationPresent(XmlAttribute.class)){
                f.setAccessible(true);
                Sk_Attribute attribute = new Sk_Attribute(f);
                if (f.get(obj) != null) {
                    attribute.setValue(f.get(obj).toString());
                }
                if(!f.getAnnotation(XmlAttribute.class).tag().equals("")){
                    attribute.setTagName(f.getAnnotation(XmlAttribute.class).tag());
                }
                else attribute.setTagName(aclass.getSimpleName());
                if(attributes.computeIfAbsent(attribute.getTagName(), k -> new LinkedList<>()).contains(attribute)){
                    throw new IllegalArgumentException("tag with name '" + attribute.getTagName() + "' almost have this attribute");
                }
                attributes.computeIfAbsent(attribute.getTagName(), k -> new LinkedList<>()).add(attribute);
            }
        }

        for(Method m : aclass.getDeclaredMethods()){
            if(!m.isAnnotationPresent(XmlAttribute.class) && !m.isAnnotationPresent(XmlTag.class)){
                System.out.println(m.getName() + " have no annotations");
                continue;
            }
            if(m.getReturnType() == void.class){
                throw new IllegalArgumentException("field '" + m.getName() + "' returns void");
            }
            if(m.getParameterTypes().length > 0){
                throw new IllegalArgumentException("method '"+ m.getName() + "' have parameters");
            }

            if(m.isAnnotationPresent(XmlTag.class)){
                Sk_Tag tag = new Sk_Tag(m, m.getAnnotation(XmlTag.class));
                if(m.invoke(obj) != null){
                    tag.setValue(m.invoke(obj).toString());
                }
                if(m.getReturnType().isAnnotationPresent(XmlObject.class)){
                    tag.setChild(this.parse(m.getReturnType()));
                }
                tags.put(tag.getTagName(), tag);
            }
            if(m.isAnnotationPresent(XmlAttribute.class)){
                Sk_Attribute attribute = new Sk_Attribute(m);
                if (m.invoke(obj) != null) {
                    attribute.setValue(m.invoke(obj).toString());
                }
                if(!m.getAnnotation(XmlAttribute.class).tag().equals("")){
                    attribute.setTagName(m.getAnnotation(XmlAttribute.class).tag());
                }
                else attribute.setTagName(aclass.getSimpleName());
                attributes.computeIfAbsent(attribute.getTagName(), k -> new LinkedList<>()).add(attribute);
            }
        }

        if(aclass.getSuperclass().isAnnotationPresent(XmlObject.class)){
            Sk_Tag superClassTag = this.parse(aclass.getSuperclass());
            Map<String, Sk_Tag> sTags = superClassTag.getChildTags();
            for(Sk_Tag t : sTags.values()){
                Sk_Tag tag = tags.get(t.getTagName());
                if(t.getTagName().equals(tag.getTagName())){
                    if(t.getName().equals(tag.getName())){
                        continue;
                    }
                    else throw new IllegalArgumentException("objects '" + t.getName() + "' and '" + tag.getName() + "' have similar tag");
                }
                tags.put(t.getTagName(), t);
            }
        }

        for (String s : attributes.keySet()){
            Sk_Tag curTag = tags.get(s);
            for (Sk_Attribute a : attributes.get(s)){
                curTag.addAttribute(a);
            }
        }
        tags.remove(objTag.getName(), objTag);
        objTag.setChildTags(tags);
        return objTag;
    }
}
