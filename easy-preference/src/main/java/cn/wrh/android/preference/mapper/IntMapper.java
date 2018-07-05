package cn.wrh.android.preference.mapper;

import android.content.SharedPreferences;
import android.content.res.Resources;

import cn.wrh.android.preference.util.PreferenceUtils;

import java.lang.reflect.Field;

/**
 * @author bruce.wu
 * @date 2018/7/4
 */
class IntMapper extends AbstractMapper {

    IntMapper(Mapper next) {
        super(next);
    }

    @Override
    public void fieldToPreference(SharedPreferences.Editor editor, String key, Field field, Object instance) throws Exception {
        if (!supported(field.getType())) {
            super.fieldToPreference(editor, key, field, instance);
            return;
        }
        Object val = field.get(instance);
        if (val != null) {
            editor.putInt(key, (int) val);
        }
    }

    @Override
    public void preferenceToField(SharedPreferences sp, String key, Field field, Object instance, Resources resources) throws Exception {
        if (!supported(field.getType())) {
            super.preferenceToField(sp, key, field, instance, resources);
            return;
        }
        int def = PreferenceUtils.getDefaultInt(field, resources, 0);
        field.set(instance, sp.getInt(key, def));
    }

    @Override
    public boolean supported(Class<?> type) {
        return Integer.class.equals(type) || int.class.equals(type);
    }
}
