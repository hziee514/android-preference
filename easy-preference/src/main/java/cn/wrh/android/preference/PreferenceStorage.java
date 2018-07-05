package cn.wrh.android.preference;

import android.support.annotation.NonNull;

/**
 * @author bruce.wu
 * @date 2018/7/2
 */
public interface PreferenceStorage<T> {

    boolean isPersistence();

    void save(@NonNull T model);

    T load();

    void clear();

}
