package cn.wrh.android.preference.mapper;

import android.content.SharedPreferences;
import android.content.res.Resources;

import java.lang.reflect.Field;

/**
 * @author bruce.wu
 * @date 2018/7/4
 */
abstract class AbstractMapper implements Mapper {

    private Mapper next;

    AbstractMapper(Mapper next) {
        this.next = next;
    }

    @Override
    public void fieldToPreference(SharedPreferences.Editor editor, String key,
                                  Field field, Object instance) throws Exception {
        if (next == null) {
            throw new RuntimeException("Unsupported field type: " + field.getType().getSimpleName());
        }
        next.fieldToPreference(editor, key, field, instance);
    }

    @Override
    public void preferenceToField(SharedPreferences sp, String key,
                                  Field field, Object instance, Resources resources) throws Exception {
        if (next == null) {
            throw new RuntimeException("Unsupported field type: " + field.getType().getSimpleName());
        }
        next.preferenceToField(sp, key, field, instance, resources);
    }

    @Override
    public boolean supported(Class<?> type) {
        return false;
    }

}
