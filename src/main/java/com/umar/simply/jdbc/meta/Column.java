package com.umar.simply.jdbc.meta;

/**
 * Represents a table column name
 * 
 * @author umar
 */
public class Column<T> {
   
    private final String columnName;

    protected Column(String columnName){
        this.columnName = columnName;
    }

    public static <T> Column<T> column(String columnName) {
        return new Column<>(columnName);
    }

    public String getColumnName() {
        return columnName;
    }

    @Override
    public String toString() {
        return getColumnName();
    }

    public static <T>Column <T> as(String columnName, String alias) {
        return new Column<>(columnName+"."+alias);
    }
    
    public static <T>Column <T> as(Table tableName, String alias) {
        return new Column<>(tableName.getTableName()+"."+ alias);
    }
    
    public static <T>Column <T> as(Column columnName, String alias) {
        return new Column<>(columnName.columnName + " AS " + alias);
    }
}
