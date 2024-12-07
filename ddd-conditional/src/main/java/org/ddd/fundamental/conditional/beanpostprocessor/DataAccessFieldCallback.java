package org.ddd.fundamental.conditional.beanpostprocessor;

import org.ddd.fundamental.conditional.beanpostprocessor.annotation.DataAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class DataAccessFieldCallback implements ReflectionUtils.FieldCallback {

    private final static Logger LOGGER = LoggerFactory.getLogger(DataAccessFieldCallback.class);

    private static int AUTOWIRE_MODE = AutowireCapableBeanFactory.AUTOWIRE_BY_NAME;

    private static String ERROR_ENTITY_VALUE_NOT_SAME = "@DataAccess(entity) "
            + "value should have same type with injected generic type.";
    private static String WARN_NON_GENERIC_VALUE = "@DataAccess annotation assigned "
            + "to raw (non-generic) declaration. This will make your code less type-safe.";
    private static String ERROR_CREATE_INSTANCE = "Cannot create instance of "
            + "type '{}' or instance creation is failed because: {}";


    private ConfigurableListableBeanFactory configurableBeanFactory;
    private Object bean;


    public DataAccessFieldCallback(ConfigurableListableBeanFactory configurableBeanFactory, Object bean){
        this.configurableBeanFactory = configurableBeanFactory;
        this.bean = bean;
    }


    @Override
    public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
        if (!field.isAnnotationPresent(DataAccess.class)) {
            return;
        }
        ReflectionUtils.makeAccessible(field);
        Type fieldGenericType = field.getGenericType();
        Class<?> generic = field.getType();
        Class<?> classValue = field.getDeclaredAnnotation(DataAccess.class).entity();
        if (genericTypeIsValid(classValue,fieldGenericType)) {
            String beanName = classValue.getSimpleName() + generic.getSimpleName();
            Object beanInstance = getBeanInstance(beanName, generic, classValue);
            field.set(bean, beanInstance);
        } else {
            throw new IllegalArgumentException(ERROR_ENTITY_VALUE_NOT_SAME);
        }
    }

    public Object getBeanInstance(
            String beanName, Class<?> genericClass, Class<?> paramClass) {
        Object daoInstance = null;
        if (!configurableBeanFactory.containsBean(beanName)) {
            LOGGER.info("Creating new DataAccess bean named '{}'.", beanName);
            Object toRegister = null;
            try {
                Constructor<?> ctr = genericClass.getConstructor(Class.class);
                toRegister = ctr.newInstance(paramClass);
            }catch (Exception e){
                LOGGER.error(ERROR_CREATE_INSTANCE, genericClass.getTypeName(), e);
                throw new RuntimeException(e);
            }
            daoInstance = configurableBeanFactory.initializeBean(toRegister,beanName);
            configurableBeanFactory.autowireBeanProperties(daoInstance, AUTOWIRE_MODE, true);
            configurableBeanFactory.registerSingleton(beanName,daoInstance);
        } else {
            daoInstance = configurableBeanFactory.getBean(beanName);
            LOGGER.info(
                    "Bean named '{}' already exists used as current bean reference.", beanName);
        }
        return daoInstance;
    }

    public boolean genericTypeIsValid(Class<?> clazz, Type field) {
        if (field instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) field;
            Type type = parameterizedType.getActualTypeArguments()[0];

            return type.equals(clazz);
        } else {
            LOGGER.warn(WARN_NON_GENERIC_VALUE);
            return true;
        }
    }
}
