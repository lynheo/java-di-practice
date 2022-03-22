package lyn.lunapiece.dipratice.engine;

import lyn.lunapiece.dipratice.engine.annotation.LynAutowired;
import lyn.lunapiece.dipratice.engine.annotation.LynComponent;
import lyn.lunapiece.dipratice.engine.annotation.LynQualified;
import org.reflections.Reflections;

import java.lang.reflect.Field;
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
        var beanName = getClassBeanName(clazz);
        return (T) createInstanceByName(beanName);
    }

    private Object createInstanceByName(String beanName) {
        try {
            var targetClass = beanClassesNameMap.get(beanName);

            var constructors = targetClass.getDeclaredConstructors();
            if (constructors.length != 1) {
                throw new RuntimeException("This sample required only default constructor.");
            }

            var newInstance = constructors[0].newInstance();
            Arrays.stream(targetClass.getDeclaredFields())
                    .forEach(field -> {
                                var annotation = field.getAnnotation(LynAutowired.class);
                                if ((annotation != null) && beanClassesNameMap.containsKey(beanName)) {
                                    var fieldInstance = createInstanceByName(getFieldBeanName(field));
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

            return newInstance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getFieldBeanName(Field field) {
        var qualifiedAnnotation = field.getAnnotation(LynQualified.class);
        if (qualifiedAnnotation == null) {
            return getClassBeanName(field.getType());
        } else {
            return qualifiedAnnotation.beanName();
        }
    }

    private String getClassBeanName(Class<?> clazz) {
        var beanName = clazz.getAnnotation(LynComponent.class).beanName();

        if (beanName.isEmpty()) {
            return clazz.getSimpleName().toLowerCase(Locale.US);
        } else {
            return beanName;
        }
    }

    private String makeClassBeanName(Class<?> clazz) {
        return clazz.getSimpleName().toLowerCase(Locale.US);
    }
}
