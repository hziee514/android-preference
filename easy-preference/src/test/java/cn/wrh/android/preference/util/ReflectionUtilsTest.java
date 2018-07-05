package cn.wrh.android.preference.util;

import org.junit.Test;
import cn.wrh.android.preference.annotation.Ignore;

import static org.junit.Assert.assertEquals;

/**
 * @author bruce.wu
 * @date 2018/7/4
 */
public class ReflectionUtilsTest {

    @Test
    public void getActualType() {
        assertEquals(Integer.class, ReflectionUtils.getActualType(Tpl2.class, 0));
    }

    @Test
    public void getTypeFields() {
        assertEquals(1, ReflectionUtils.getTypeFields(C1.class).size());
        assertEquals(2, ReflectionUtils.getTypeFields(C2.class).size());
    }

    @Test
    public void getPreferenceFields_ignore() {
        assertEquals(2, ReflectionUtils.getPreferenceFields(C3.class).size());
    }

    @Test
    public void getPreferenceFields() {

    }

    static class Tpl<T> {
    }

    static class Tpl2 extends Tpl<Integer> {

    }

    static class C1 {
        private String f1;
    }

    static class C2 extends C1 {
        private String f2;
    }

    static class C3 extends C2 {

        @Ignore
        private String f3;

        private static String f4;

        private transient String f5;

    }
}