package com.umar.simply.jdbc.meta;

public class Index {

    private final String index;

    public Index(String index) {
        this.index = index;
    }

    public String getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return getIndex();
    }
}
