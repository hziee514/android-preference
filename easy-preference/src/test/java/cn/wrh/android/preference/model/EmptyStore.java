package cn.wrh.android.preference.model;

import android.content.Context;

import cn.wrh.android.preference.AbstractPreferenceStorage;

/**
 * @author bruce.wu
 * @date 2018/7/2
 */
public class EmptyStore extends AbstractPreferenceStorage<EmptyModel> {
    public EmptyStore(Context context) {
        super(context, "empty");
    }
}
