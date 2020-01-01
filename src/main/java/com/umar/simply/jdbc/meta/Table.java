package com.umar.simply.jdbc.meta;

public class Table {

    private final String tableName;
    private final Column<Integer> idColumn;

    public Table(String tableName, Column<Integer> idColumn) {
        this.tableName = tableName;
        this.idColumn = idColumn;
    }

    public static Table table(String tableName, Column<Integer> idColumn){
        return new Table(tableName, idColumn);
    }

    public String getTableName() {
        return tableName;
    }

    public Column<Integer> getIdColumn() {
        return idColumn;
    }

    public static Table as(String tableName, String alias, Column<Integer> idColumn) {
        return new Table(tableName +" "+alias, idColumn);
    }
    
    public static Table AS(String tableName, String alias) {
        return new Table(tableName +" AS "+alias, Column.column(""));
    }

    @Override
    public String toString() {
        return getTableName();
    }

}
