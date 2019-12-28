package com.umar.simply.jdbc.meta;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

/**
 * This class represents the value of a database column
 *
 * @author umar
 */

public class ColumnValue<T> {

    private final T value;
    private final Column<T> columnName;
    
    private ColumnValue(Column<T> columnName, T value) {
        this.columnName = columnName;
        this.value = value;
    }

    /**
     * Sets the column and the value. Used when both column name and value needs to be assigned
     *
     * @param <T> The column type
     * @param column The column to set
     * @param value The value to set
     * @return Returns new ColumnValue object with the column and the value
     */
    public static <T> ColumnValue<T> set(Column<T> column, T value) {
        isValidType(value);
        return new ColumnValue<>(column, value);
    }
    
    /**
     * Its same as <code>set(Column<T> column, T value)</code>. Use it with <code>WHERE COLUMN_EQ</code> clause for
     * readability.
     * 
     * Sets the column and the value. Used when both column name and value needs to be assigned
     *
     * @param <T> The column type
     * @param column The column to set
     * @param value The value to set
     * @return Returns new ColumnValue object with the column and the value
     */
    public static <T> ColumnValue<T> eq(Column<T> column, T value) {
        isValidType(value);
        return new ColumnValue<>(column, value);
    }

    private static <T> void isValidType(T value) {
        if(!(value instanceof Integer
                || value instanceof Long
                || value instanceof Short
                || value instanceof Byte
                || value instanceof Float
                || value instanceof Double
                || value instanceof BigDecimal
                || value instanceof BigInteger
                || value instanceof Character
                || value instanceof Boolean
                || value instanceof String
                || value instanceof CharSequence
                || value instanceof Timestamp
                || value instanceof Date
                || value instanceof Calendar
                || value instanceof LocalDate
                || value instanceof LocalDateTime
                || value instanceof LocalTime
                || value == null)){
            throw new RuntimeException("Invalid type of value passed ");
        }
    }

    /**
     * Sets the value. When only value is to be assigned to a parameter without specifying column name
     *
     * @param value The value to set
     * @return Returns new ColumnValue object with the value
     */
    public static <T> ColumnValue<T> set( T value) {
        isValidType(value);
        return new ColumnValue<>(null, value);
    }

    /**
     *
     * @return Returns the value held
     */
    public T getValue() {
        return value;
    }

    /**
     * Returns the column Name
     *
     * @return Returns the Column object
     */
    public Column<T> getColumnName() {
        return columnName;
    }

    @Override
    public String toString() {
        return columnName +"->"+ value;
    }
}
