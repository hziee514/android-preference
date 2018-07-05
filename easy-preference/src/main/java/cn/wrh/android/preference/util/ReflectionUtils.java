package cn.wrh.android.preference.util;

import cn.wrh.android.preference.annotation.Ignore;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author bruce.wu
 * @date 2018/7/2
 */
public final class ReflectionUtils {

    public static <T> T newInstance(Class<T> type) throws Exception {
        Constructor<T> constructor = type.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> getActualType(Class<?> type, int index) {
        if (!(type.getGenericSuperclass() instanceof ParameterizedType)) {
            return null;
        }
        return (Class<T>)((ParameterizedType)type.getGenericSuperclass()).getActualTypeArguments()[index];
    }

    public static List<Field> getTypeFields(Class<?> type) {
        Class<?> t = type;
        List<Field> fields = new ArrayList<>();
        while (t != null) {
            Collections.addAll(fields, t.getDeclaredFields());
            t = t.getSuperclass();
        }
        return fields;
    }

    public static List<Field> getPreferenceFields(Class<?> type) {
        List<Field> fields = getTypeFields(type);
        List<Field> preferenceFields = new ArrayList<>(fields.size());
        for (Field field : fields) {
            if (field.isAnnotationPresent(Ignore.class)) {
                continue;
            }
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            if (Modifier.isTransient(field.getModifiers())) {
                continue;
            }
            preferenceFields.add(field);
        }
        return preferenceFields;
    }

}
