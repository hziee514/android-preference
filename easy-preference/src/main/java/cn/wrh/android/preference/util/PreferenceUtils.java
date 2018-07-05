package cn.wrh.android.preference.util;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import cn.wrh.android.preference.annotation.Default;
import cn.wrh.android.preference.annotation.Key;
import cn.wrh.android.preference.annotation.Precision;

import java.lang.reflect.Field;
import java.math.BigDecimal;

/**
 * @author bruce.wu
 * @date 2018/7/2
 */
public final class PreferenceUtils {

    public static String getKey(@NonNull Field field, @NonNull Resources resources) {
        if (field.isAnnotationPresent(Key.class)) {
            Key annotation = field.getAnnotation(Key.class);
            if (annotation.resId() != 0) {
                return resources.getString(annotation.resId());
            }
            if (!TextUtils.isEmpty(annotation.name())) {
                return annotation.name();
            }
        }
        return field.getName();
    }

    public static String getDefaultString(@NonNull Field field, @NonNull Resources resources, String value) {
        if (field.isAnnotationPresent(Default.class)) {
            Default annotation = field.getAnnotation(Default.class);
            if (annotation.resId() != 0) {
                return resources.getString(annotation.resId());
            }
            if (!TextUtils.isEmpty(annotation.value())) {
                return annotation.value();
            }
        }
        return value;
    }

    public static boolean getDefaultBoolean(@NonNull Field field, @NonNull Resources resources, boolean value) {
        String val = getDefaultString(field, resources, "");
        if (TextUtils.isEmpty(val)) {
            return value;
        }
        return Boolean.valueOf(val);
    }

    public static int getDefaultInt(@NonNull Field field, @NonNull Resources resources, int value) {
        String val = getDefaultString(field, resources, "");
        if (TextUtils.isEmpty(val)) {
            return value;
        }
        return Integer.valueOf(val);
    }

    public static long getDefaultLong(@NonNull Field field, @NonNull Resources resources, long value) {
        String val = getDefaultString(field, resources, "");
        if (TextUtils.isEmpty(val)) {
            return value;
        }
        return Long.valueOf(val);
    }

    public static double getDoubleValue(@NonNull Field field, String value) {
        if (field.isAnnotationPresent(Precision.class)) {
            Precision annotation = field.getAnnotation(Precision.class);
            return new BigDecimal(value).setScale(annotation.scale(), annotation.roundingMode()).doubleValue();
        }
        return new BigDecimal(value).doubleValue();
    }

    public static String getDoubleString(@NonNull Field field, double value) {
        if (field.isAnnotationPresent(Precision.class)) {
            Precision annotation = field.getAnnotation(Precision.class);
            return new BigDecimal(value).setScale(annotation.scale(), annotation.roundingMode()).toPlainString();
        }
        return new BigDecimal(value).toPlainString();
    }

}
