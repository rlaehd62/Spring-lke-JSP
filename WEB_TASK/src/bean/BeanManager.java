package bean;

import annotation.CustomController;
import annotation.EndPoint;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.*;

public class BeanManager
{
    static
    {
        // Scan
        BeanManager manager = BeanManager.getInstance();
        Map<String, ControllerBean> beanManager = manager.getManager();

        Reflections reflections = new Reflections();
        Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(CustomController.class);
        try
        {
            for(Class<?> cls : classSet)
            {
                String ControllerName = cls.getAnnotation(CustomController.class).value();

                Object instance = cls.getConstructor().newInstance();
                Method[] methods = cls.getMethods();

                ControllerBean bean = new ControllerBean(instance);
                Map<String, Method> map = bean.getMethodMap();
                beanManager.put(ControllerName, bean);

                for(Method method : methods)
                {
                    if(method.isAnnotationPresent(EndPoint.class))
                    {
                        String mapping = method.getAnnotation(EndPoint.class).value();
                        map.put(mapping, method);
                    }
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static BeanManager instance;
    private final Map<String, ControllerBean> manager;

    private BeanManager() { manager = new HashMap<>(); }
    public static BeanManager getInstance()
    {
        return (Objects.nonNull(instance)) ? instance : (instance = new BeanManager());
    }

    public Map<String, ControllerBean> getManager()
    {
        return manager;
    }

    public List<ControllerBean> allBeans()
    {
        return new ArrayList<>(manager.values());
    }

    public Optional<ControllerBean> findBean(String key)
    {
        ControllerBean bean = manager.getOrDefault(key, null);
        return Optional.ofNullable(bean);
    }
}
