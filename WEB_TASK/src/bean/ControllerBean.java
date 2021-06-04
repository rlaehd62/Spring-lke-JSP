package bean;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ControllerBean
{
    private Object classObject;
    private Map<String, Method> methodMap;

    public ControllerBean(Object classObject)
    {
        this.classObject = classObject;
        methodMap = new HashMap<>();
    }

    public Map<String, Method> getMethodMap()
    {
        return methodMap;
    }

    public Optional<Method> getMethod(String key)
    {
        if(!methodMap.containsKey(key)) return Optional.empty();
        return Optional.of(methodMap.get(key));
    }

    public Object getClassObject()
    {
        return classObject;
    }
}
