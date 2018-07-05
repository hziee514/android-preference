package cn.wrh.android.preference.mapper;

import android.content.SharedPreferences;
import android.content.res.Resources;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.lang.reflect.Field;

import cn.wrh.android.preference.BuildConfig;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyBoolean;
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
public class BooleanMapperTest {

    private BooleanMapper mapper = new BooleanMapper(null);

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
        Field field = MapperModel.class.getDeclaredField("bool1");
        MapperModel model = new MapperModel();
        SharedPreferences.Editor editor = mock(SharedPreferences.Editor.class);
        mapper.fieldToPreference(editor, "bool1", field, model);
        verify(editor, times(0)).putBoolean(anyString(), anyBoolean());
    }

    @Test
    public void fieldToPreference_bool1() throws Exception {
        Field field = MapperModel.class.getDeclaredField("bool1");
        MapperModel model = new MapperModel();
        model.setBool1(true);
        SharedPreferences.Editor editor = mock(SharedPreferences.Editor.class);
        mapper.fieldToPreference(editor, "bool1", field, model);
        verify(editor, times(1)).putBoolean("bool1", true);
    }

    @Test
    public void fieldToPreference_bool2() throws Exception {
        Field field = MapperModel.class.getDeclaredField("bool2");
        MapperModel model = new MapperModel();
        model.setBool2(true);
        SharedPreferences.Editor editor = mock(SharedPreferences.Editor.class);
        mapper.fieldToPreference(editor, "bool2", field, model);
        verify(editor, times(1)).putBoolean("bool2", true);
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
    public void preferenceToField_bool1() throws Exception {
        Field field = MapperModel.class.getDeclaredField("bool1");
        MapperModel model = new MapperModel();
        SharedPreferences sp = mock(SharedPreferences.class);
        when(sp.getBoolean("bool1", false)).thenReturn(true);
        mapper.preferenceToField(sp, "bool1", field, model, mock(Resources.class));
        assertTrue(model.getBool1());
    }

    @Test
    public void preferenceToField_bool2() throws Exception {
        Field field = MapperModel.class.getDeclaredField("bool2");
        MapperModel model = new MapperModel();
        SharedPreferences sp = mock(SharedPreferences.class);
        when(sp.getBoolean("bool2", false)).thenReturn(true);
        mapper.preferenceToField(sp, "bool2", field, model, mock(Resources.class));
        assertTrue(model.isBool2());
    }

    @Test
    public void supported_boolean() {
        assertTrue(mapper.supported(Boolean.class));
        assertTrue(mapper.supported(boolean.class));
    }

    @Test
    public void supported_string() {
        assertFalse(mapper.supported(String.class));
    }

}