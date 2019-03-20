
import lab.XMLObjects.Sk_Tag;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

interface ObjectParser {

    Sk_Tag parse(Object obj) throws IllegalAccessException, InvocationTargetException;
}
