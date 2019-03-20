package lab.Annotations;


import java.lang.annotation.*;


@Retention(value = RetentionPolicy.RUNTIME)
public @interface XmlAttribute {
    String tag() default "";
}
