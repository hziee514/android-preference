package cn.wrh.android.preference;

import android.content.Context;
import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import cn.wrh.android.preference.mapper.Builder;
import cn.wrh.android.preference.model.EmptyModel;
import cn.wrh.android.preference.model.EmptyStore;
import cn.wrh.android.preference.model.FullModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
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
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@PrepareForTest({ Builder.class })
public class AbstractPreferenceStorageTest {

    @Rule
    public PowerMockRule rule = new PowerMockRule();

    private Context appContext;

    @Before
    public void setUp() {
        appContext = RuntimeEnvironment.application;
        ShadowLog.stream = System.out;
    }

    @Test
    public void getModelType() {
        assertEquals(EmptyModel.class, new EmptyStore(appContext).getModelType());
    }

    @Test
    public void isPersistence_default() {
        assertFalse(new EmptyStore(appContext).isPersistence());
    }

    @Test
    public void isPersistence_saved() {
        new EmptyStore(appContext).save(new EmptyModel());
        assertTrue(new EmptyStore(appContext).isPersistence());
    }

    @Test
    public void save_default() {
        SharedPreferences.Editor editor = mock(SharedPreferences.Editor.class);
        SharedPreferences sp = mock(SharedPreferences.class);
        when(sp.edit()).thenReturn(editor);
        FullStore store = new FullStore(appContext, sp);
        FullModel model = new FullModel();
        store.save(model);
        verify(editor, times(0)).putBoolean("bool1", false);
        verify(editor, times(1)).putBoolean("bool2", false);
        verify(editor, times(0)).putString("str", null);
        verify(editor, times(0)).putInt("int1", 0);
        verify(editor, times(1)).putInt("int2", 0);
        verify(editor, times(0)).putLong("long1", 0);
        verify(editor, times(1)).putLong("long2", 0);
        verify(editor, times(0)).putString("double1", "0.00");
        verify(editor, times(1)).putString("double2", "0.00");
    }

    @Test
    public void save_full() {
        SharedPreferences.Editor editor = mock(SharedPreferences.Editor.class);
        SharedPreferences sp = mock(SharedPreferences.class);
        when(sp.edit()).thenReturn(editor);
        FullStore store = new FullStore(appContext, sp);
        FullModel model = new FullModel();
        model.setBool1(true);
        model.setBool2(true);
        model.setStr("hello");
        model.setInt1(123);
        model.setInt2(-321);
        model.setLong1(123456L);
        model.setLong2(-654321);
        model.setDouble1(123.456);
        model.setDouble2(-654.321);
        store.save(model);
        verify(editor, times(1)).putBoolean("bool1", true);
        verify(editor, times(1)).putBoolean("bool2", true);
        verify(editor, times(1)).putString("str", "hello");
        verify(editor, times(1)).putInt("int1", 123);
        verify(editor, times(1)).putInt("int2", -321);
        verify(editor, times(1)).putLong("long1", 123456L);
        verify(editor, times(1)).putLong("long2", -654321);
        verify(editor, times(1)).putString("double1", "123.46");
        verify(editor, times(1)).putString("double2", "-654.32");
    }

    @Test
    public void load_default() {
        FullModel model = new FullModel();
        new FullStore(appContext).save(model);

        FullModel m = new FullStore(appContext).load();
        assertNotNull(m);
        assertFalse(m.getBool1());
        assertFalse(m.isBool2());
        assertNull(m.getStr());
        assertEquals(0, (int) m.getInt1());
        assertEquals(0, m.getInt2());
        assertEquals(0, (long) m.getLong1());
        assertEquals(0, m.getLong2());
        assertEquals(0.00, m.getDouble1(), 0.0001);
        assertEquals(0.00, m.getDouble2(), 0.0001);
    }

    @Test
    public void load_full() {
        FullModel model = new FullModel();
        model.setBool1(true);
        model.setBool2(true);
        model.setStr("hello");
        model.setInt1(123);
        model.setInt2(-321);
        model.setLong1(123456L);
        model.setLong2(-654321);
        model.setDouble1(123.456);
        model.setDouble2(-654.321);
        new FullStore(appContext).save(model);

        FullModel m = new FullStore(appContext).load();
        assertNotNull(m);
        assertTrue(m.getBool1());
        assertTrue(m.isBool2());
        assertEquals("hello", m.getStr());
        assertEquals(123, (int)m.getInt1());
        assertEquals(-321, m.getInt2());
        assertEquals(123456L, (long)m.getLong1());
        assertEquals(-654321, m.getLong2());
        assertEquals(123.46, m.getDouble1(), 0.0001);
        assertEquals(-654.32, m.getDouble2(), 0.0001);
    }

    static class FullStore extends AbstractPreferenceStorage<FullModel> {
        FullStore(Context context) {
            super(context, "full");
        }
        FullStore(Context context, SharedPreferences sp) {
            super(context, sp);
        }
    }

}