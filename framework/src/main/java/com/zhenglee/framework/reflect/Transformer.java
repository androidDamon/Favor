package com.zhenglee.framework.reflect;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Transformer {

    /**
     * Transform object to map
     *
     * @param o
     *            The object to convert
     * @return the map with field-value mapping
     */
    public static final Map<?, ?> transform(final Object o) {
        if (null == o)
            return Collections.emptyMap();

        if (o instanceof Map<?, ?>)
            return (Map<?, ?>) o;

        final Class<?> clazz = o.getClass();
        final Map<String, Object> map = new LinkedHashMap<String, Object>();

        if (clazz.isArray() && Map.Entry.class.isAssignableFrom(clazz.getComponentType())) {
            final Map.Entry<?, ?>[] entries = (Map.Entry<?, ?>[]) o;

            for (final Map.Entry<?, ?> entry : entries) {
                map.put(String.valueOf(entry.getKey()), entry.getValue());
            }
        } else if (o instanceof JSONObject) {
            final JSONObject json = (JSONObject) o;
            for (final Iterator<?> i = json.keys(); i.hasNext();) {
                final String key = String.valueOf(i.next());
                map.put(key, json.opt(key));
            }
        } else {
            for (final Field field : clazz.getDeclaredFields()) {
                final String name = field.getName();
                final int mod = field.getModifiers();

                if (Modifier.isStatic(mod) || Modifier.isTransient(mod)) {
                    continue;
                }

                field.setAccessible(true);

                try {
                    final Object value = field.get(o);
                    if (null != value) {
                        map.put(name, value);
                    }
                } catch (IllegalAccessException e) {
                    continue;
                }
            }
        }

        return map;
    }

    private Transformer() {
    }

}
