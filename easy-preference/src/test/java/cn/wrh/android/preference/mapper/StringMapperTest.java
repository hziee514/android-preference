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
public class StringMapperTest {

    private StringMapper mapper = new StringMapper(null);

    @Test
    public void fieldToPreference_bool() throws Exception {
        Field field = MapperModel.class.getDeclaredField("bool1");
        MapperModel model = new MapperModel();
        try {
            mapper.fieldToPreference(mock(SharedPreferences.Editor.class), "bool1", field, model);
            fail();
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().startsWith("Unsupported field type: "));
        }
    }

    @Test
    public void fieldToPreference_null() throws Exception {
        Field field = MapperModel.class.getDeclaredField("str");
        MapperModel model = new MapperModel();
        SharedPreferences.Editor editor = mock(SharedPreferences.Editor.class);
        mapper.fieldToPreference(editor, "str", field, model);
        verify(editor, times(0)).putString(anyString(), anyString());
    }

    @Test
    public void fieldToPreference_string() throws Exception {
        Field field = MapperModel.class.getDeclaredField("str");
        MapperModel model = new MapperModel();
        model.setStr("hello");
        SharedPreferences.Editor editor = mock(SharedPreferences.Editor.class);
        mapper.fieldToPreference(editor, "str", field, model);
        verify(editor, times(1)).putString("str", "hello");
    }

    @Test
    public void preferenceToField_bool() throws Exception {
        Field field = MapperModel.class.getDeclaredField("bool2");
        MapperModel model = new MapperModel();
        try {
            mapper.preferenceToField(mock(SharedPreferences.class), "bool2", field, model, mock(Resources.class));
            fail();
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().startsWith("Unsupported field type: "));
        }
    }

    @Test
    public void preferenceToField_string() throws Exception {
        Field field = MapperModel.class.getDeclaredField("str");
        MapperModel model = new MapperModel();
        SharedPreferences sp = mock(SharedPreferences.class);
        when(sp.getString("str", null)).thenReturn("hello");
        mapper.preferenceToField(sp, "str", field, model, mock(Resources.class));
        assertEquals("hello", model.getStr());
    }

    @Test
    public void supported_string() {
        assertTrue(mapper.supported(String.class));
    }

}