package cn.wrh.android.preference.mapper;

import android.content.SharedPreferences;
import android.content.res.Resources;

import java.lang.reflect.Field;

/**
 * @author bruce.wu
 * @date 2018/7/4
 */
public interface Mapper {

    void fieldToPreference(SharedPreferences.Editor editor, String key,
                           Field field, Object instance) throws Exception;

    void preferenceToField(SharedPreferences sp, String key,
                           Field field, Object instance, Resources resources) throws Exception;

    boolean supported(Class<?> type);

}
