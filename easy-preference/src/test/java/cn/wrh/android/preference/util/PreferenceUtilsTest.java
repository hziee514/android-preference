package cn.wrh.android.preference.util;

import android.content.res.Resources;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.lang.reflect.Field;
import java.math.RoundingMode;

import cn.wrh.android.preference.BuildConfig;
import cn.wrh.android.preference.annotation.Default;
import cn.wrh.android.preference.annotation.Key;
import cn.wrh.android.preference.annotation.Precision;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * @author bruce.wu
 * @date 2018/7/4
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
public class PreferenceUtilsTest {

    private Resources resources;

    @Before
    public void setUp() {
        resources = Mockito.mock(Resources.class);
        Mockito.when(resources.getString(0x7f150001)).thenReturn("storage");
        Mockito.when(resources.getString(0x7f150002)).thenReturn("true");
        Mockito.when(resources.getString(0x7f150003)).thenReturn("12345");
        Mockito.when(resources.getString(0x7f150004)).thenReturn("123456789");
    }

    @Test
    public void getKey_fieldKey() throws Exception {
        Field field = TestModel.class.getDeclaredField("fieldKey");
        assertEquals("fieldKey", PreferenceUtils.getKey(field, resources));
    }

    @Test
    public void getKey_nameKey() throws Exception {
        Field field = TestModel.class.getDeclaredField("nameKey");
        assertEquals("hello", PreferenceUtils.getKey(field, resources));
    }

    @Test
    public void getKey_resKey() throws Exception {
        Field field = TestModel.class.getDeclaredField("resKey");
        assertEquals("storage", PreferenceUtils.getKey(field, resources));
    }

    @Test
    public void getDefaultString_noDef() throws Exception {
        Field field = DefaultStringModel.class.getDeclaredField("noDef");
        assertNull(PreferenceUtils.getDefaultString(field, resources, null));
        assertEquals("", PreferenceUtils.getDefaultString(field, resources, ""));
        assertEquals("hello", PreferenceUtils.getDefaultString(field, resources, "hello"));
    }

    @Test
    public void getDefaultString_defValue() throws Exception {
        Field field = DefaultStringModel.class.getDeclaredField("defValue");
        assertEquals("hello", PreferenceUtils.getDefaultString(field, resources, null));
    }

    @Test
    public void getDefaultString_defRes() throws Exception {
        Field field = DefaultStringModel.class.getDeclaredField("defRes");
        assertEquals("storage", PreferenceUtils.getDefaultString(field, resources, null));
    }

    @Test
    public void getDefaultBoolean_noDef() throws Exception {
        Field field = DefaultBoolModel.class.getDeclaredField("noDef");
        assertFalse(PreferenceUtils.getDefaultBoolean(field, resources, false));
        assertTrue(PreferenceUtils.getDefaultBoolean(field, resources, true));
    }

    @Test
    public void getDefaultBoolean_defValue() throws Exception {
        Field field = DefaultBoolModel.class.getDeclaredField("defValue");
        assertTrue(PreferenceUtils.getDefaultBoolean(field, resources, false));
    }

    @Test
    public void getDefaultBoolean_defRes() throws Exception {
        Field field = DefaultBoolModel.class.getDeclaredField("defRes");
        assertTrue(PreferenceUtils.getDefaultBoolean(field, resources, false));
    }

    @Test
    public void getDefaultInt_noDef() throws Exception {
        Field field = DefaultIntModel.class.getDeclaredField("noDef");
        assertEquals(111, PreferenceUtils.getDefaultInt(field, resources, 111));
        assertEquals(222, PreferenceUtils.getDefaultInt(field, resources, 222));
    }

    @Test
    public void getDefaultInt_defValue() throws Exception {
        Field field = DefaultIntModel.class.getDeclaredField("defValue");
        assertEquals(54321, PreferenceUtils.getDefaultInt(field, resources, 111));
    }

    @Test
    public void getDefaultInt_defRes() throws Exception {
        Field field = DefaultIntModel.class.getDeclaredField("defRes");
        assertEquals(12345, PreferenceUtils.getDefaultInt(field, resources, 111));
    }

    @Test
    public void getDefaultLong_noDef() throws Exception {
        Field field = DefaultLongModel.class.getDeclaredField("noDef");
        assertEquals(111111111, PreferenceUtils.getDefaultLong(field, resources, 111111111));
        assertEquals(222222222, PreferenceUtils.getDefaultLong(field, resources, 222222222));
    }

    @Test
    public void getDefaultLong_defValue() throws Exception {
        Field field = DefaultLongModel.class.getDeclaredField("defValue");
        assertEquals(987654321, PreferenceUtils.getDefaultLong(field, resources, 111111111));
    }

    @Test
    public void getDefaultLong_defRes() throws Exception {
        Field field = DefaultLongModel.class.getDeclaredField("defRes");
        assertEquals(123456789, PreferenceUtils.getDefaultLong(field, resources, 111111111));
    }

    @Test
    public void getDoubleValue_noScale() throws Exception {
        Field field = DefaultDoubleModel.class.getDeclaredField("noScale");
        assertEquals(123.4567, PreferenceUtils.getDoubleValue(field, "123.4567"), 0.00001);
        assertEquals(0.0, PreferenceUtils.getDoubleValue(field, "0"), 0.00001);
    }

    @Test
    public void getDoubleValue_scaleDef() throws Exception {
        Field field = DefaultDoubleModel.class.getDeclaredField("scaleDef");
        assertEquals(123.46, PreferenceUtils.getDoubleValue(field, "123.4556"), 0.00001);
        assertEquals(123.45, PreferenceUtils.getDoubleValue(field, "123.4545"), 0.00001);
    }

    @Test
    public void getDoubleValue_scale3() throws Exception {
        Field field = DefaultDoubleModel.class.getDeclaredField("scale3");
        assertEquals(123.456, PreferenceUtils.getDoubleValue(field, "123.4555"), 0.00001);
        assertEquals(123.454, PreferenceUtils.getDoubleValue(field, "123.4544"), 0.00001);
    }

    @Test
    public void getDoubleValue_floor() throws Exception {
        Field field = DefaultDoubleModel.class.getDeclaredField("floor");
        assertEquals(123.45, PreferenceUtils.getDoubleValue(field, "123.4555"), 0.00001);
        assertEquals(123.45, PreferenceUtils.getDoubleValue(field, "123.4544"), 0.00001);
    }

    @Test
    public void getDoubleString() {
    }

    static class TestModel {

        String fieldKey;

        @Key(name = "hello")
        String nameKey;

        @Key(resId = 0x7f150001)
        String resKey;

    }

    static class DefaultStringModel {

        String noDef;

        @Default("hello")
        String defValue;

        @Default(resId = 0x7f150001)
        String defRes;

    }

    static class DefaultBoolModel {

        boolean noDef;

        @Default("true")
        boolean defValue;

        @Default(resId = 0x7f150002)
        boolean defRes;

    }

    static class DefaultIntModel {

        int noDef;

        @Default("54321")
        int defValue;

        @Default(resId = 0x7f150003)
        int defRes;

    }

    static class DefaultLongModel {

        long noDef;

        @Default("987654321")
        long defValue;

        @Default(resId = 0x7f150004)
        long defRes;

    }

    static class DefaultDoubleModel {

        double noScale;

        @Precision
        double scaleDef;

        @Precision(scale = 3)
        double scale3;

        @Precision(roundingMode = RoundingMode.FLOOR)
        double floor;

    }

}