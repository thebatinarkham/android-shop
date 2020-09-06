package com.example.shop.screen.common.views;

import com.asksira.bsimagepicker.BSImagePicker;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class BaseObservableViewMvc <ListnerType> extends BaseViewMvc implements
        ObservableViewMvc<ListnerType>{
    //unique set
    private Set<ListnerType> mListeners = new HashSet<>();

    @Override
    public void registerListener(ListnerType listener) {
        mListeners.add(listener);
    }

    @Override
    public void unregisterListener(ListnerType listener) {
        mListeners.remove(listener);
    }

    protected Set<ListnerType> getListeners(){
        return Collections.unmodifiableSet(mListeners);
    }
}
