package com.sleepyduck.sleepycalculator.utils;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import java.util.Objects;

public class MuteLiveData<T> extends MutableLiveData<T> {
    public MuteLiveData() {
        super();
    }

    public MuteLiveData(@Nullable T value) {
        super(value);
    }

    @Override
    public void setValue(@Nullable T value) {
        if (!Objects.equals(getValue(), value)) {
            super.setValue(value);
        }
    }
}
