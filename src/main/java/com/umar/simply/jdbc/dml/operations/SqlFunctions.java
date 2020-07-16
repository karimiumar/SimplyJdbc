package com.umar.simply.jdbc.dml.operations;

import com.umar.simply.jdbc.meta.Column;
import com.umar.simply.jdbc.meta.ColumnValue;
import com.umar.simply.jdbc.meta.Index;
import com.umar.simply.jdbc.meta.Table;

import java.sql.PreparedStatement;
import java.util.List;

public interface SqlFunctions<T extends AbstractOp<T>> {

    String getSQL();

    StringBuilder op();

    List<ColumnValue<?>> getValues();

    /**
     * Fills the PreparedStatement object with values
     *
     * @param ps The PreparedStatement object to fill
     * @return The PreparedStatement object
     */
    PreparedStatement setParametersOfPreparedStatement(PreparedStatement ps);

    T COLUMN(String column);

    /**
     * A typesafe method for SQL operation which takes the form
     * <code>WHERE column1 = x AND column2 = y AND column3 = 'abc'</code>
 Appends the COLUMN name suffixed by =? to a PreparedStatement object.
     *
     * @param columnValues The ColumnValue objects
     * @return Returns the current object
     */
    T EQ(ColumnValue<?>... columnValues);

    /**
     * The NOT EQUAL operator
     * @return Returns this object
     */
    T NE();

    /**
     * The NOT EQUAL operator
     * @param condition The condition to suffix <> operator
     * @return Returns this object
     */
    T NE(Column<?> condition);

    /**
     * SQL NOT statement
     * @return Returns this object
     */
    T NOT();

    T NOT(SelectOp sql);

    /**
     * SQL IS statement
     * @return Returns this object
     */
    T IS();

    /**
     * The SQL NULL operator
     * @return Returns this object
     */
    T NULL();

    /**
     * SQL AND statement
     * @return Returns this object
     */
    T AND();

    /**
     * SQL AND statement
     * @param column The COLUMN to append AND clause
     * @return Returns this object
     */
    T AND(Column<?> column);

    T AND(SelectOp sql);

    T ALL();

    /**
     * SQL OR statement
     * @return Returns this object
     */
    T OR();

    /**
     * SQL UPPER clause
     *
     * @return Returns this object
     */
    T UPPER();

    /**
     * SQL HAVING clause
     * @return Returns this object
     */
    T HAVING();

    /**
     * SQL FROM clause
     * @return Returns this object
     * @param table The TABLE to use with FROM clause
     */
    T FROM(String table);

    /**
     * SQL FROM clause
     * @return Returns this object
     * @param table The TABLE to use with FROM clause
     */
    T FROM(Table table);

    /**
     * SQL FROM clause.
     * @return Returns this object
     * @param tables The tables to use with FROM clause
     */
    T FROM(List<Table> tables);

    /**
     * SQL WHERE clause.
     * @return Returns this object
     */
    T WHERE();

    /**
     * SQL WHERE clause. Use this for the scenario <code>WHERE (SELECT AVG(T) FROM X) AS AVG </code>
     * @param op The INNER query to use
     * @return Returns this object
     */
    T WHERE(SelectOp op);

    /**
     * SQL WHERE clause
     * @param column The COLUMN to append with WHERE clause
     * @return Returns this object
     */
    T WHERE(Column column);

    /**
     * SQL TABLE to be worked with
     * @param table The TABLE name
     * @return Returns this object
     */
    T TABLE(String table);

    /**
     * SQL TABLE to be worked with
     * @param table The TABLE name
     * @return Returns this object
     */
    T TABLE(Table table);

    /**
     * SQL YEAR() function
     * @param column The COLUMN to use with YEAR()
     * @return Returns this object
     */
    T YEAR(Column column);

    /**
     * SQL DISTINCT clause
     * @return Returns this object
     */
    T DISTINCT();

    /**
     * SQL DISTINCT clause
     * @param column The COLUMN to use with DISTINCT operator
     * @return Returns this object
     */
    T DISTINCT(String column);

    /**
     * SQL DISTINCT clause
     * @param column The COLUMN to use with DISTINCT operator
     * @return Returns this object
     */
    T DISTINCT(Column column);

    /**
     * SQL COUNT() function
     * @param column The COLUMN to use with COUNT()
     * @return Returns this object
     */
    T COUNT(String column);

    /**
     * SQL COUNT() function
     * @param column The COLUMN to use with COUNT()
     * @return Returns this object
     */
    T COUNT(Column column);

    /**
     * SQL COUNT() function
     * @param op T type operation
     * @return Returns this object
     */
    T COUNT(SelectOp op);

    /**
     * SQL MAX() function
     * @param column The COLUMN to use with MAX()
     * @return Returns this object
     */
    T MAX(Column column);

    /**
     * SQL MIN() function
     * @param column The COLUMN to use with MIN()
     * @return Returns this object
     */
    T MIN(Column column);

    /**
     * SQL AVG() function
     * @param column The COLUMN to use with AVG()
     * @return Returns this object
     */
    T AVG(Column column);

    /**
     * SQL SUM() function. 'SUM' function is appended around the column
     * @param column The COLUMN to SUM
     * @return Returns this object
     */
    T SUM(Column column);

    /**
     * The SQL EXISTS clause
     * @param op The SQL operation
     * @return The current object
     */
    T EXISTS(SelectOp op);

    /**
     * The SQL BETWEEN clause
     * @return Returns this object
     */
    T BETWEEN();

    /**
     * SQL BETWEEN clause.
     *
     * @param columnValues The ColumnValue. Contains COLUMN name AND COLUMN value
     * @return Returns this object
     */
    T BETWEEN(List<ColumnValue<?>> columnValues);

    /**
     * SQL BETWEEN clause. 'BETWEEN' operator is appended around the SQL operation
     * @param op The SQL operation to wrap IN 'BETWEEN' clause
     * @return Returns this object
     */
    T BETWEEN(SelectOp op);

    /**
     * SQL LIKE clause. 'LIKE' operator is appended followed by ''pattern''.
     * @param pattern The String pattern to look for.
     * @return Returns this object
     */
    T LIKE(String pattern);

    /**
     * The SQL IN() function. The 'IN' function is appended followed by the SQL operation
     * Use it for the scenario <code>WHERE first_name IN (SELECT first_name FROM customer)</code>
     * @param op The SQL operation to wrap IN 'IN' function
     * @return Returns this object
     */
    T IN(SelectOp op);

    /**
     * The SQL IN() function.The 'IN' takes a list of ColumnValue
     * @param columnValues The ColumnValue for SQL IN clause
     *@return Returns this object
     */
    T IN(List<ColumnValue<?>> columnValues);

    /**
     * This method can be used when creating IN queries from JPA
     * @param objects The objects
     * @return Returns this
     */
    T IN(Object... objects);

    /**
     * MySQL LIMIT Function
     *
     * @param n The int value of LIMIT
     * @return Returns this object
     */
    T LIMIT(int n);

    /**
     * MySQL OffSet Function
     *
     * @param n The int value to pass IN OFFSET
     * @return Returns this object
     */
    T OFFSET(int n);

    /**
     * SQL AS clause
     * @param alias The alias to use
     * @return Returns this object
     */
    T AS(String alias);

    T AS(SelectOp operation);

    /**
     * SQL ORDER BY clause
     * @return Returns this object
     */
    T ORDERBY();

    /**
     * SQL ORDER BY clause
     * @param column The COLUMN ON which ORDER BY to apply
     * @return Returns this object
     */
    T ORDERBY(Column column);

    /**
     * SQL ASC clause for ascending order
     * @return Returns this object
     */
    T ASC();

    /**
     * SQL DESC clause for descending order
     * @return Returns this object
     */
    T DESC();

    /**
     * SQL GREATER THAN clause.
     * @return Returns this object
     */
    T GT();

    /**
     * SQL GREATER THAN clause.
     * @param value
     * @return Returns this object
     */
    T GT(ColumnValue value);

    /**
     * SQL LESS THAN clause.
     * @return Returns this object
     */
    T LT();

    /**
     * SQL LESS THAN clause.
     * @param value The ColumnValue
     * @return Returns this object
     */
    T LT(ColumnValue value);

    /**
     * SQL GREATER OR EQUAL clause.
     * @return Returns this object
     */
    T GE();

    /**
     * SQL GREATER OR EQUAL TO clause.
     * @param value The ColumnValue
     * @return Returns this object
     */
    T GE(ColumnValue value);

    /**
     * SQL LESS OR EQUAL TO clause.
     * @param value The ColumnValue
     * @return Returns this object
     */
    T LE(ColumnValue value);

    /**
     * SQL LESS OR EQUAL clause.
     * @return Returns this object
     */
    T LE();

    /**
     * SQL = operator. An '=' symbol is appended by this method followed by the 'condition'.
     * @param condition The condition to append with = operator
     * @return Returns this object
     */
    T EQ(String condition);

    /**
     * SQL = operator. An '=' symbol is appended by this method followed by the 'condition'.
     * @param condition The condition to append with = operator
     * @return Returns this object
     */
    T EQ(Column condition);

    /**
     * SQL = operator. An '=' symbol is appended by this method.<br>
     *
     * @return Returns this object
     */
    T EQ();

    /**
     * SQL GROUP BY clause. 'GROUP BY' operator is appended followed by columns to group.
     * @param columns The columns to group together
     * @return Returns this object
     *
     */
    T GROUPBY(List<Column<?>> columns);

    /**
     * SQL GROUP BY clause. 'GROUP BY' operator is appended followed by columns to group.
     * @param column The columns to group together
     * @return Returns this object
     *
     */
    T GROUPBY(Column<?> column);

    /**
     * Use this method when grouping columns IN
     * conjunction with COUNT(), AVG(), MIN(), MAX() method
     * For example if the desired SQL operation is as
     * <code>
     * SELECT  COUNT(id), country, city FROM customer AS c1  GROUP BY country
     * </code>
     * ;then it can be written AS:
     *   <code>
     *   SelectOp sql = create().SELECT().COUNT(CUSTOMER_ID).GROUP_WITH(asList(CUSTOMER_COUNTRY,CUSTOMER_CITY)).FROM(TBL_CUSTOMERS).AS("c1").GROUPBY(CUSTOMER_COUNTRY);
     *   </code>
     * @param columns The columns to use
     * @return Returns this object
     */
    T GROUP_WITH(List<Column<?>> columns);

    /**
     * Use this method when grouping columns in
     * conjunction with count(), avg(), min(), max() method.
     * For example if the desired SQL operation is as
     * <code>
     * SELECT  COUNT(id), country FROM customer AS c1  GROUP BY country
     * </code>
     * ;then it can be written AS:
     *   <code>
     *   SelectOp sql = create().SELECT().COUNT(CUSTOMER_ID).GROUP_WITH(CUSTOMER_COUNTRY).FROM(TBL_CUSTOMERS).AS("c1").GROUPBY(CUSTOMER_COUNTRY);
     *   </code>
     * @param column The columns to use
     * @return Returns this object
     */
    T GROUP_WITH(Column<?> column);

    /**
     * MySQL USING operator. 'USING' operator is appended around the 'column'
     * @param column The COLUMN
     * @return Returns this object
     */
    T USING(String column);

    /**
     * MySQL USING operator. 'USING' operator is appended around the 'column'
     * @param column The COLUMN
     * @return Returns this object
     */
    T USING(Column column);

    /**
     * MySQL MINUS operator
     * @return Returns this object
     */
    T MINUS();

    /**
     * SQL INNER clause
     * @return Returns this object
     */
    T INNER();

    /**
     *
     * SQL JOIN operation
     * @return Returns this object
     */
    T JOIN();

    /**
     * SQL JOIN operation INNER SELECT query
     * @param query The INNER query to use with JOIN
     * @return Returns this object
     */
    T JOIN(SelectOp query);

    /**
     * SQL JOIN operation
     * @param table The JOIN TABLE
     * @return Returns this object
     */
    T JOIN(Table table);

    /**
     * SQL LEFT JOIN clause
     * @return Returns this object
     */
    T LEFT();

    /**
     * SQL RIGHT JOIN clause
     * @return Returns this object
     */
    T RIGHT();

    /**
     * SQL UNION clause
     * @return Returns this object
     */
    T UNION();

    /**
     * SQL ON clause used IN conjunction with JOIN
     * @return Returns this object
     */
    T ON();

    T ON(SelectOp selectOp);

    /**
     * SQL ON clause used IN conjunction with JOIN
     * @param column The COLUMN to JOIN ON
     * @return Returns this object
     */
    T ON(Column column);

    /**
     * SQL ANY clause
     * @return Returns this object
     */
    T ANY();

    /**
     * SQL SOME clause. It's an alias for ANY
     * @return Returns this object
     */
    T SOME();

    /**
     * SQL SUBSTRING function
     *
     * @return Returns this object
     */
    T SUBSTRING();

    /**
     * SQL CHARINDEX() function
     * @return Returns this object
     */
    T CHARINDEX();

    /**
     * SQL SELECT clause
     * @return Returns this object
     */
    T SELECT();

    /**
     * SQL SELECT clause
     * @param columns List of columns to SELECT
     * @return Returns this object
     */
    T SELECT(List<Column<?>> columns);

    /**
     * SQL SELECT clause
     * @param column The COLUMN to SELECT
     * @return Returns this object
     */
    T SELECT(Column<?> column);

    /**
     * SQL WITH INDEX clause
     * @param index The index name
     * @return Returns this object
     */
    T WITHINDEX(Index index);

    T WITH(String alias);
}
