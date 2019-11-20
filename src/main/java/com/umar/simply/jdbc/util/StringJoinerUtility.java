package com.umar.simply.jdbc.util;

import java.util.Iterator;

public class StringJoinerUtility {

    public static <E> String joinUtil(Iterable<E> iterable, String delim) {
        Iterator<E> iterator = iterable.iterator();
        if (!iterator.hasNext()) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        while (iterator.hasNext()) {
            builder.append(delim).append(iterator.next());
        }

        return builder.toString();
    }
}
