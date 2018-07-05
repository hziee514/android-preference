package cn.wrh.android.preference.mapper;

import android.content.SharedPreferences;
import android.content.res.Resources;

import cn.wrh.android.preference.util.PreferenceUtils;

import java.lang.reflect.Field;

/**
 * @author bruce.wu
 * @date 2018/7/4
 */
class DoubleMapper extends AbstractMapper {

    DoubleMapper(Mapper next) {
        super(next);
    }

    @Override
    public void fieldToPreference(SharedPreferences.Editor editor, String key,
                                  Field field, Object instance) throws Exception {
        if (!supported(field.getType())) {
            super.fieldToPreference(editor, key, field, instance);
            return;
        }
        Object val = field.get(instance);
        if (val != null) {
            editor.putString(key, PreferenceUtils.getDoubleString(field, (double) val));
        }
    }

    @Override
    public void preferenceToField(SharedPreferences sp, String key,
                                  Field field, Object instance, Resources resources) throws Exception {
        if (!supported(field.getType())) {
            super.preferenceToField(sp, key, field, instance, resources);
            return;
        }
        String def = PreferenceUtils.getDefaultString(field, resources, "0");
        String val = sp.getString(key, def);
        field.set(instance, PreferenceUtils.getDoubleValue(field, val));
    }

    @Override
    public boolean supported(Class<?> type) {
        return Double.class.equals(type) || double.class.equals(type);
    }

}
