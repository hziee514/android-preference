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
import static org.mockito.Matchers.anyInt;
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
public class IntMapperTest {

    private IntMapper mapper = new IntMapper(null);

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
        Field field = MapperModel.class.getDeclaredField("int1");
        MapperModel model = new MapperModel();
        SharedPreferences.Editor editor = mock(SharedPreferences.Editor.class);
        mapper.fieldToPreference(editor, "int1", field, model);
        verify(editor, times(0)).putInt(anyString(), anyInt());
    }

    @Test
    public void fieldToPreference_int1() throws Exception {
        Field field = MapperModel.class.getDeclaredField("int1");
        MapperModel model = new MapperModel();
        model.setInt1(12345);
        SharedPreferences.Editor editor = mock(SharedPreferences.Editor.class);
        mapper.fieldToPreference(editor, "int1", field, model);
        verify(editor, times(1)).putInt("int1", 12345);
    }

    @Test
    public void fieldToPreference_int2() throws Exception {
        Field field = MapperModel.class.getDeclaredField("int2");
        MapperModel model = new MapperModel();
        model.setInt2(-123456);
        SharedPreferences.Editor editor = mock(SharedPreferences.Editor.class);
        mapper.fieldToPreference(editor, "int2", field, model);
        verify(editor, times(1)).putInt("int2", -123456);
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
    public void preferenceToField_int1() throws Exception {
        Field field = MapperModel.class.getDeclaredField("int1");
        MapperModel model = new MapperModel();
        SharedPreferences sp = mock(SharedPreferences.class);
        when(sp.getInt("int1", 0)).thenReturn(12345);
        mapper.preferenceToField(sp, "int1", field, model, mock(Resources.class));
        assertEquals(12345, (int)model.getInt1());
    }

    @Test
    public void preferenceToField_int2() throws Exception {
        Field field = MapperModel.class.getDeclaredField("int2");
        MapperModel model = new MapperModel();
        SharedPreferences sp = mock(SharedPreferences.class);
        when(sp.getInt("int2", 0)).thenReturn(654321);
        mapper.preferenceToField(sp, "int2", field, model, mock(Resources.class));
        assertEquals(654321, model.getInt2());
    }

    @Test
    public void supported_boolean() {
        assertTrue(mapper.supported(Integer.class));
        assertTrue(mapper.supported(int.class));
    }

    @Test
    public void supported_string() {
        assertFalse(mapper.supported(String.class));
    }

}