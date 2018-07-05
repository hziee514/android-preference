package cn.wrh.android.preference.mapper;

import android.content.SharedPreferences;
import android.content.res.Resources;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.lang.reflect.Field;

import cn.wrh.android.preference.BuildConfig;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author bruce.wu
 * @date 2018/7/4
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
public class DoubleMapperTest {

    private DoubleMapper mapper = new DoubleMapper(null);

    @Test
    public void fieldToPreference_string() throws Exception {
        Field field = MapperModel.class.getDeclaredField("str");
        MapperModel model = new MapperModel();
        try {
            mapper.fieldToPreference(mock(SharedPreferences.Editor.class), "str", field, model);
            fail();
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().startsWith("Unsupported field type: "));
        }
    }

    @Test
    public void fieldToPreference_null() throws Exception {
        Field field = MapperModel.class.getDeclaredField("double1");
        MapperModel model = new MapperModel();
        SharedPreferences.Editor editor = mock(SharedPreferences.Editor.class);
        mapper.fieldToPreference(editor, "double1", field, model);
        verify(editor, times(0)).putString(anyString(), anyString());
    }

    @Test
    public void fieldToPreference_double1() throws Exception {
        Field field = MapperModel.class.getDeclaredField("double1");
        MapperModel model = new MapperModel();
        model.setDouble1(123.45);
        SharedPreferences.Editor editor = mock(SharedPreferences.Editor.class);
        mapper.fieldToPreference(editor, "double1", field, model);
        verify(editor, times(1)).putString("double1", "123.45");
    }

    @Test
    public void fieldToPreference_double2() throws Exception {
        Field field = MapperModel.class.getDeclaredField("double2");
        MapperModel model = new MapperModel();
        model.setDouble2(-1234.56);
        SharedPreferences.Editor editor = mock(SharedPreferences.Editor.class);
        mapper.fieldToPreference(editor, "double2", field, model);
        verify(editor, times(1)).putString("double2", "-1234.56");
    }

    @Test
    public void preferenceToField_string() throws Exception {
        Field field = MapperModel.class.getDeclaredField("str");
        MapperModel model = new MapperModel();
        try {
            mapper.preferenceToField(mock(SharedPreferences.class), "str", field, model, mock(Resources.class));
            fail();
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().startsWith("Unsupported field type: "));
        }
    }

    @Test
    public void preferenceToField_double1() throws Exception {
        Field field = MapperModel.class.getDeclaredField("double1");
        MapperModel model = new MapperModel();
        SharedPreferences sp = mock(SharedPreferences.class);
        when(sp.getString("double1", "0")).thenReturn("123.45");
        mapper.preferenceToField(sp, "double1", field, model, mock(Resources.class));
        assertEquals(123.45, model.getDouble1(), 0.0001);
    }

    @Test
    public void preferenceToField_double2() throws Exception {
        Field field = MapperModel.class.getDeclaredField("double2");
        MapperModel model = new MapperModel();
        SharedPreferences sp = mock(SharedPreferences.class);
        when(sp.getString("double2", "0")).thenReturn("-6543.21");
        mapper.preferenceToField(sp, "double2", field, model, mock(Resources.class));
        assertEquals(-6543.21, model.getDouble2(), 0.00001);
    }

    @Test
    public void supported_boolean() {
        assertTrue(mapper.supported(Double.class));
        assertTrue(mapper.supported(double.class));
    }

    @Test
    public void supported_string() {
        assertFalse(mapper.supported(String.class));
    }

}