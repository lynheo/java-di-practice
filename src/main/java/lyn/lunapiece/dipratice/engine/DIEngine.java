package lyn.lunapiece.dipratice.engine;

import lyn.lunapiece.dipratice.engine.annotation.LynAutowired;
import lyn.lunapiece.dipratice.engine.annotation.LynComponent;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.*;

import static org.reflections.scanners.Scanners.SubTypes;
import static org.reflections.scanners.Scanners.TypesAnnotated;

public class DIEngine {
    private final Set<Class<?>> beanClasses = new HashSet<>();
    private final Map<String, Class<?>> beanClassesNameMap = new HashMap<>();

    public void scanComponents(String basePackage) {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> annotatedClass = reflections.get(SubTypes.of(TypesAnnotated.with(LynComponent.class)).asClass());

        annotatedClass.forEach(clazz -> {
            beanClassesNameMap.put(getClassBeanName(clazz), clazz);
        });

        beanClasses.addAll(annotatedClass);
    }

    public <T> T createInstance(Class<T> clazz) {
        try {
            var constructors = clazz.getDeclaredConstructors();
            if (constructors.length != 1) {
                throw new RuntimeException("This sample required only default constructor.");
            }
            var newInstance = constructors[0].newInstance();
            Arrays.stream(clazz.getDeclaredFields())
                    .forEach(field -> {
                                var annotation = field.getAnnotation(LynAutowired.class);
                                if ((annotation != null) && beanClasses.contains(field.getType())) {
                                    var fieldInstance = createInstance(field.getType());
                                    try {
                                        var beforeAccesssible = Modifier.isPrivate(field.getModifiers());
                                        field.setAccessible(true);
                                        field.set(newInstance, fieldInstance);
                                        field.setAccessible(beforeAccesssible);
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                    );

            return (T) newInstance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getClassBeanName(Class<?> clazz) {
        return clazz.getSimpleName().toLowerCase(Locale.US);
    }
}
