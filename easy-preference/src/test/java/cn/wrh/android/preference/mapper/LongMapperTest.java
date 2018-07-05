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
import static org.mockito.Matchers.anyLong;
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
public class LongMapperTest {

    private LongMapper mapper = new LongMapper(null);

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
        Field field = MapperModel.class.getDeclaredField("long1");
        MapperModel model = new MapperModel();
        SharedPreferences.Editor editor = mock(SharedPreferences.Editor.class);
        mapper.fieldToPreference(editor, "long1", field, model);
        verify(editor, times(0)).putLong(anyString(), anyLong());
    }

    @Test
    public void fieldToPreference_long1() throws Exception {
        Field field = MapperModel.class.getDeclaredField("long1");
        MapperModel model = new MapperModel();
        model.setLong1(12345L);
        SharedPreferences.Editor editor = mock(SharedPreferences.Editor.class);
        mapper.fieldToPreference(editor, "long1", field, model);
        verify(editor, times(1)).putLong("long1", 12345);
    }

    @Test
    public void fieldToPreference_long2() throws Exception {
        Field field = MapperModel.class.getDeclaredField("long2");
        MapperModel model = new MapperModel();
        model.setLong2(-123456);
        SharedPreferences.Editor editor = mock(SharedPreferences.Editor.class);
        mapper.fieldToPreference(editor, "long2", field, model);
        verify(editor, times(1)).putLong("long2", -123456);
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
    public void preferenceToField_long1() throws Exception {
        Field field = MapperModel.class.getDeclaredField("long1");
        MapperModel model = new MapperModel();
        SharedPreferences sp = mock(SharedPreferences.class);
        when(sp.getLong("long1", 0)).thenReturn(12345L);
        mapper.preferenceToField(sp, "long1", field, model, mock(Resources.class));
        assertEquals(12345, (long)model.getLong1());
    }

    @Test
    public void preferenceToField_long2() throws Exception {
        Field field = MapperModel.class.getDeclaredField("long2");
        MapperModel model = new MapperModel();
        SharedPreferences sp = mock(SharedPreferences.class);
        when(sp.getLong("long2", 0)).thenReturn(654321L);
        mapper.preferenceToField(sp, "long2", field, model, mock(Resources.class));
        assertEquals(654321, model.getLong2());
    }

    @Test
    public void supported_boolean() {
        assertTrue(mapper.supported(Long.class));
        assertTrue(mapper.supported(long.class));
    }

    @Test
    public void supported_string() {
        assertFalse(mapper.supported(String.class));
    }

}