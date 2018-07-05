package cn.wrh.android.preference.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * assigned preference key
 *   order: keyResId -> key -> fieldName
 *
 * @author bruce.wu
 * @date 2018/7/2
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface Key {

    String name() default "";

    /**
     * default key from string resource
     *
     * @return R.string.xxx
     */
    int resId() default 0;

}
