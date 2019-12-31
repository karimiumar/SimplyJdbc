package com.umar.simply.jdbc.dml.operations;

import com.umar.simply.jdbc.meta.Column;
import com.umar.simply.jdbc.meta.ColumnValue;
import com.umar.simply.jdbc.meta.Index;
import com.umar.simply.jdbc.meta.Table;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Most of the SQL operators are defined here
 * @param <T> The Type of AbstractOp
 * @author umar
 */
public abstract class AbstractOp<T extends AbstractOp<T>> {

    public abstract String getSQL();

    public abstract StringBuilder op();

    public abstract List<ColumnValue> getValues();

    /**
     * Fills the PreparedStatement object GROUP_WITH values
     *
     * @param ps The PreparedStatement object to fill
     * @return The PreparedStatement object
     */
    public PreparedStatement fill(PreparedStatement ps){
        try {
            populate(ps, getValuesArray());
            reset();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ps;
    }

    public T COLUMN(String column) {
        op().append(column);
        return (T)this;
    }
    
    /**
     * A typesafe method for SQL operation which takes the form 
     * <code>WHERE column1 = x AND column2 = y AND column3 = 'abc'</code>
 Appends the COLUMN name suffixed by =? to a PreparedStatement object.
     *
     * @param columnValues The ColumnValue objects
     * @return Returns the current object
     */
    public T COLUMN_EQ(ColumnValue ...columnValues) {
        int len = columnValues.length;
        int cnt = 1;
        for(ColumnValue e: columnValues) {
            getValues().add(e);
            op().append(e.getColumnName());
            op().append("=?");
            if(cnt++ < len){
                op().append(" AND ");
            }
        }
        return (T) this;
    }

    /**
     * The NOT EQUAL operator
     * @param condition The condition to suffix GROUP_WITH <> operator
     * @return Returns this object
     */
    public T NE(Column condition) {
        op().append("<>");
        op().append(condition);
        return (T) this;
    }

    /**
     * SQL NOT statement
     * @return Returns this object
     */
    public T NOT() {
        op().append(" NOT ");
        return (T) this;
    }
    
    public T NOT(SelectOp sql) {
        op().append(" NOT( ");
        op().append(sql);
        op().append(" )");
        getValues().addAll(sql.getValues());
        return (T) this;
    }

    /**
     * SQL IS statement
     * @return Returns this object
     */
    public T IS() {
        op().append(" IS ");
        return (T) this;
    }

    /**
     * The SQL NULL operator
     * @return Returns this object
     */
    public T NULL(){
        op().append(" NULL");
        return (T) this;
    }

    /**
     * SQL AND statement
     * @return Returns this object
     */
    public T AND() {
        op().append(" AND ");
        return (T) this;
    }

    /**
     * SQL AND statement
     * @param column The COLUMN to append GROUP_WITH AND clause
     * @return Returns this object
     */
    public T AND(Column column) {
        op().append(" AND ");
        op().append(column);
        return (T) this;
    }
    
    public T AND(SelectOp sql) {
        op().append(" AND (");
        op().append(sql);
        op().append(" )");
        getValues().addAll(sql.getValues());
        return (T) this;
    }

    /**
     * SQL OR statement
     * @return Returns this object
     */
    public T OR() {
        op().append(" OR ");
        return (T) this;
    }

    /**
     * SQL UPPER clause
     *
     * @return Returns this object
     */
    public T UPPER() {
        op().append(" UPPER ");
        return (T)this;
    }

    /**
     * SQL HAVING clause
     * @return Returns this object
     */
    public T HAVING() {
        op().append(" HAVING ");
        return (T)this;
    }

    /**
     * SQL FROM clause
     * @return Returns this object
     * @param table The TABLE to use GROUP_WITH FROM clause
     */
    public T FROM(String table) {
        op().append(" FROM ");
        op().append(table);
        return (T) this;
    }

    /**
     * SQL FROM clause
     * @return Returns this object
     * @param table The TABLE to use GROUP_WITH FROM clause
     */
    public T FROM(Table table) {
        op().append(" FROM ");
        op().append(table);
        return (T) this;
    }
    
    /**
     * SQL FROM clause.
     * @return Returns this object
     * @param tables The tables to use GROUP_WITH FROM clause
     */
    public T FROM(List<Table> tables) {
        int len = tables.size();
        int cnt = 1;
        op().append(" FROM ");
        for(Table table:tables){
            op().append(table);
            if(cnt++ < len){
                op().append(",");
            }
        }
        return (T) this;
    }

    /**
     * SQL WHERE clause.
     * @return Returns this object
     */
    public T WHERE() {
        op().append(" WHERE ");
        return (T) this;
    }

    /**
     * SQL WHERE clause. Use this for the scenario <code>WHERE (SELECT AVG(T) FROM X) AS AVG </code>
     * @param op The INNER query to use
     * @return Returns this object
     */
    public T WHERE(SelectOp op) {
        op().append(" WHERE (");
        op().append(op);
        op().append(")");
        //explicitly add the SelectOp ColumnValues to the original getValues() to retain it for later use otherwise 
        //it will be lost due to local variable scope of SelectOp parameter
        getValues().addAll(op.getValues()); 
        return (T) this;
    }    

    /**
     * SQL WHERE clause
     * @param column The COLUMN to append GROUP_WITH WHERE clause
     * @return Returns this object
     */
    public T WHERE(Column column) {
        op().append(" WHERE ");
        op().append(column);
        return (T) this;
    }

    /**
     * SQL TABLE to be worked GROUP_WITH
     * @param table The TABLE name
     * @return Returns this object
     */
    public T TABLE(String table) {
        op().append(table);
        return (T) this;
    }

    /**
     * SQL TABLE to be worked GROUP_WITH
     * @param table The TABLE name
     * @return Returns this object
     */
    public T TABLE(Table table) {
        op().append(table);
        return (T) this;
    }

    /**
     * SQL YEAR() function
     * @param column The COLUMN to use GROUP_WITH YEAR()
     * @return Returns this object
     */
    public T YEAR(Column column) {
        op().append(" YEAR(");
        op().append(column);
        op().append(")");
        return (T) this;
    }

    /**
     * SQL DISTINCT clause
     * @return Returns this object
     */
    public T DISTINCT() {
        op().append(" DISTINCT ");
        return (T) this;
    }

    /**
     * SQL DISTINCT clause
     * @param column The COLUMN to use GROUP_WITH DISTINCT operator
     * @return Returns this object
     */
    public T DISTINCT(String column) {
        op().append(" DISTINCT ");
        op().append(column);
        return (T)this;
    }

    /**
     * SQL DISTINCT clause
     * @param column The COLUMN to use GROUP_WITH DISTINCT operator
     * @return Returns this object
     */
    public T DISTINCT(Column column) {
        op().append(" DISTINCT ");
        op().append(column);
        return (T)this;
    }

    /**
     * SQL COUNT() function
     * @param column The COLUMN to use GROUP_WITH COUNT()
     * @return Returns this object
     */
    public T COUNT(String column) {
        op().append(" COUNT(");
        op().append(column);
        op().append(")");
        return (T) this;
    }

    /**
     * SQL COUNT() function
     * @param column The COLUMN to use GROUP_WITH COUNT()
     * @return Returns this object
     */
    public T COUNT(Column column) {
        op().append(" COUNT(");
        op().append(column);
        op().append(")");
        return (T) this;
    }

    /**
     * SQL COUNT() function
     * @param op T type operation
     * @return Returns this object
     */
    public T COUNT(SelectOp op) {
        op().append(" COUNT(");
        op().append(op);
        op().append(")");
        getValues().addAll(op.getValues());
        //explicitly add the SelectOp ColumnValues to the original getValues() to retain it for later use otherwise 
        //it will be lost due to local variable scope of SelectOp parameter
        return (T) this;
    }

    /**
     * SQL MAX() function
     * @param column The COLUMN to use GROUP_WITH MAX()
     * @return Returns this object
     */
    public T MAX(Column column) {
        op().append(" MAX(");
        op().append(column);
        op().append(")");
        return (T) this;
    }

    /**
     * SQL MIN() function
     * @param column The COLUMN to use GROUP_WITH MIN()
     * @return Returns this object
     */
    public T MIN(Column column) {
        op().append(" MIN(");
        op().append(column);
        op().append(")");
        return (T) this;
    }

    /**
     * SQL AVG() function
     * @param column The COLUMN to use GROUP_WITH AVG()
     * @return Returns this object
     */
    public T AVG(Column column) {
        op().append(" AVG(");
        op().append(column);
        op().append(")");
        return (T) this;
    }

    /**
     * SQL SUM() function. 'SUM' function is appended around the column
     * @param column The COLUMN to SUM
     * @return Returns this object
     */
    public T SUM(Column column) {
        op().append(" SUM(");
        op().append(column);
        op().append(")");
        return (T) this;
    }

    /**
     * The SQL EXISTS clause
     * @param op The SQL operation
     * @return The current object
     */
    public T EXISTS(SelectOp op) {
        op().append(" EXISTS");
        op().append(" (");
        op().append(op);
        op().append(" )");
        //explicitly add the SelectOp ColumnValues to the original getValues() to retain it for later use otherwise 
        //it will be lost due to local variable scope of SelectOp parameter
        getValues().addAll(op.getValues());
        return (T) this;
    }

    /**
     * SQL BETWEEN clause.
     *
     * @param columnValues The ColumnValue. Contains COLUMN name AND COLUMN value
     * @return Returns this object
     */
    public T BETWEEN(List<ColumnValue> columnValues) {
        int len = columnValues.size();
        int cnt = 1;
        op().append(" BETWEEN ");
        for (ColumnValue e: columnValues) {
            op().append("?");
            if(cnt++ < len){
                op().append(" AND ");
            }
            getValues().add(e);
        }
        return (T) this;
    }

    /**
     * SQL BETWEEN clause. 'BETWEEN' operator is appended around the SQL operation
     * @param op The SQL operation to wrap IN 'BETWEEN' clause
     * @return Returns this object
     */
    public T BETWEEN(SelectOp op) {
        op().append(" BETWEEN (");
        op().append(op);
        op().append(")");
        //explicitly add the SelectOp ColumnValues to the original getValues() to retain it for later use otherwise 
        //it will be lost due to local variable scope of SelectOp parameter
        getValues().addAll(op.getValues());
        return (T) this;
    }

    /**
     * SQL LIKE clause. 'LIKE' operator is appended followed by ''pattern''.
     * @param pattern The String pattern to look for.
     * @return Returns this object
     */
    public T LIKE(String pattern) {
        op().append(" LIKE ");
        op().append("?");
        getValues().add(ColumnValue.set(pattern));
        return (T) this;
    }
    
    /**
     * The SQL IN() function. The 'IN' function is appended followed by the SQL operation
     * Use it for the scenario <code>WHERE first_name IN (SELECT first_name FROM customer)</code>
     * @param op The SQL operation to wrap IN 'IN' function
     * @return Returns this object
     */
    public T IN(SelectOp op) {
        op().append(" IN (");
        op().append(op);
        op().append(")");
        //explicitly add the SelectOp ColumnValues to the original getValues() to retain it for later use otherwise 
        //it will be lost due to local variable scope of SelectOp parameter
        getValues().addAll(op.getValues());
        return (T) this;
    }

    /**
     * The SQL IN() function.The 'IN' takes a list of ColumnValue
     * @param columnValues The ColumnValue for SQL IN clause
     *@return Returns this object
     */
    public T IN(List<ColumnValue> columnValues) {
        int len = columnValues.size();
        int cnt = 1;
        op().append(" IN (");
        for(ColumnValue cv: columnValues) {
            op().append("?");
            if(cnt++ < len){
                op().append(",");
            }
            getValues().add(cv);
        }
        op().append(")");
        return (T) this;
    }

    /**
     * MySQL LIMIT Function
     *
     * @param n The int value of LIMIT
     * @return Returns this object
     */
    public T LIMIT(int n) {
        getValues().add(ColumnValue.set(n));
        op().append(" LIMIT ");
        op().append("?");
        return (T) this;
    }

    /**
     * MySQL OffSet Function
     *
     * @param n The int value to pass IN OFFSET
     * @return Returns this object
     */
    public T OFFSET(int n) {
        getValues().add(ColumnValue.set(n));
        op().append(" OFFSET ");
        op().append("?");
        return (T) this;
    }

    /**
     * SQL AS clause
     * @param alias The alias to use
     * @return Returns this object
     */
    public T AS(String alias) {
        op().append(" AS ");
        op().append(alias);
        op().append(" ");
        return (T) this;
    }

    /**
     * SQL ORDER BY clause
     * @return Returns this object
     */
    public T ORDERBY() {
        op().append(" ORDER BY ");
        return (T) this;
    }
    
    /**
     * SQL ORDER BY clause
     * @param column The COLUMN ON which ORDER BY to apply
     * @return Returns this object
     */
    public T ORDERBY(Column column) {
        op().append(" ORDER BY ");
        op().append(column);
        return (T) this;
    }

    /**
     * SQL ASC clause for ascending order
     * @return Returns this object
     */
    public T ASC() {
        op().append(" ASC ");
        return (T) this;
    }

    /**
     * SQL DESC clause for descending order
     * @return Returns this object
     */
    public T DESC() {
        op().append(" DESC ");
        return (T) this;
    }

    /**
     * SQL GREATER THAN clause.
     * @return Returns this object
     */
    public T GT() {
        op().append(">");
        return (T) this;
    }

    /**
     * SQL GREATER THAN clause.
     * @param value
     * @return Returns this object
     */
    public T GT(ColumnValue value) {
        op().append(">");
        op().append("?");
        getValues().add(value);
        return (T) this;
    }

    /**
     * SQL LESS THAN clause.
     * @return Returns this object
     */
    public T LT() {
        op().append("<");
        return (T) this;
    }

    /**
     * SQL LESS THAN clause.
     * @param value The ColumnValue
     * @return Returns this object
     */
    public T LT(ColumnValue value) {
        op().append("<");
        op().append("?");
        getValues().add(value);
        return (T) this;
    }

    /**
     * SQL GREATER OR EQUAL clause.
     * @return Returns this object
     */
    public T GE(){
        op().append(">=");
        return (T) this;
    }

    /**
     * SQL GREATER OR EQUAL TO clause.
     * @param value The ColumnValue
     * @return Returns this object
     */
    public T GE(ColumnValue value) {
        op().append(value.getColumnName());
        op().append(">=");
        op().append("?");
        getValues().add(value);
        return (T) this;
    }

    /**
     * SQL LESS OR EQUAL TO clause.
     * @param value The ColumnValue
     * @return Returns this object
     */
    public T LE(ColumnValue value) {
        op().append(value.getColumnName());
        op().append("<=");
        op().append("?");
        getValues().add(value);
        return (T) this;
    }

    /**
     * SQL LESS OR EQUAL clause.
     * @return Returns this object
     */
    public T LE() {
        op().append("<=");
        return (T) this;
    }

    /**
     * SQL = operator. An '=' symbol is appended by this method followed by the 'condition'.
     * @param condition The condition to append GROUP_WITH = operator
     * @return Returns this object
     */
    public T EQ(String condition) {
        op().append("=");
        op().append(condition);
        return (T) this;
    }
    
    /**
     * SQL = operator. An '=' symbol is appended by this method followed by the 'condition'.
     * @param condition The condition to append GROUP_WITH = operator
     * @return Returns this object
     */
    public T EQ(Column condition) {
        op().append("=");
        op().append(condition);
        return (T) this;
    }

    /**
     * SQL = operator. An '=' symbol is appended by this method.<br>
     *
     * @return Returns this object
     */
    public T EQ() {
        op().append("=");
        return (T) this;
    }
    
    public T EQ(ColumnValue value) {
        op().append("=?");
        getValues().add(value);
        return (T) this;
    }

    /**
     * SQL GROUP BY clause. 'GROUP BY' operator is appended followed by columns to group.
     * @param columns The columns to group together
     * @return Returns this object
     *
     */
    public T GROUPBY(List<Column> columns){
        int len = columns.size();
        int cnt = 1;
        op().append(" GROUP BY ");
        for(Column column:columns) {
            op().append(column);
            if(cnt++ < len){
                op().append(",");
            }
        }
        return (T) this;
    }

    /**
     * SQL GROUP BY clause. 'GROUP BY' operator is appended followed by columns to group.
     * @param column The columns to group together
     * @return Returns this object
     *
     */
    public T GROUPBY(Column column){
        op().append(" GROUP BY ");
        op().append(column);
        return (T) this;
    }
    
    /**
     * Use this method when grouping columns IN
 conjunction GROUP_WITH COUNT(), AVG(), MIN(), MAX() method
     * @param columns The columns to use
     * @return Returns this object
     */
    public T GROUP_WITH(List<Column> columns) {
        columns.stream().map((column) -> {
            op().append(",");
            return column;
        }).forEachOrdered((column) -> {
            op().append(column);
        });
        return (T) this;
    }

    /**
     * Use this method when grouping columns in
     * conjunction with count(), avg(), min(), max() method.
     * For example if the desired SQL operation is as
     * <code>
     * SELECT  COUNT(id), country FROM customer AS c1  GROUP BY country
     * </code>
 ;then it can be written AS:
 <code>
 SelectOp sql = create().SELECT().COUNT(CUSTOMER_ID)
                .GROUP_WITH(CUSTOMER_COUNTRY)
                .FROM(TBL_CUSTOMERS).AS("c1").GROUPBY(CUSTOMER_COUNTRY);
 </code>
     * @param column The columns to use
     * @return Returns this object
     */
    public T GROUP_WITH(Column column) {
        op().append(", ");
        op().append(column);
        return (T) this;
    }

    /**
     * MySQL USING operator. 'USING' operator is appended around the 'column'
     * @param column The COLUMN
     * @return Returns this object
     */
    public T USING(String column) {
        op().append(" USING(");
        op().append(column);
        op().append(")");
        return (T) this;
    }

    /**
     * MySQL USING operator. 'USING' operator is appended around the 'column'
     * @param column The COLUMN
     * @return Returns this object
     */
    public T USING(Column column) {
        op().append(" USING(");
        op().append(column);
        op().append(")");
        return (T) this;
    }

    /**
     * MySQL MINUS operator
     * @return Returns this object
     */
    public T MINUS(){
        op().append(" MINUS ");
        return (T) this;
    }

    /**
     * SQL INNER clause
     * @return Returns this object
     */
    public T INNER(){
        op().append(" INNER");
        return (T) this;
    }

    /**
     *
     * SQL JOIN operation
     * @return Returns this object
     */
    public T JOIN() {
        op().append(" JOIN ");
        return (T) this;
    }
    
    /**
     * SQL JOIN operation GROUP_WITH INNER SELECT query
     * @param query The INNER query to use GROUP_WITH JOIN
     * @return Returns this object
     */
    public T JOIN(SelectOp query) {
        op().append(" JOIN (");
        op().append(query);
        op().append(" )");
        return (T) this;
    }
    /**
     * SQL JOIN operation
     * @param table The JOIN TABLE 
     * @return Returns this object
     */
    public T JOIN(Table table) {
        op().append(" JOIN ");
        op().append(table);
        return (T) this;
    }

    /**
     * SQL LEFT JOIN clause
     * @return Returns this object
     */
    public T LEFT() {
        op().append(" LEFT");
        return (T) this;
    }

    /**
     * SQL RIGHT JOIN clause
     * @return Returns this object
     */
    public T RIGHT() {
        op().append(" RIGHT");
        return (T) this;
    }

    /**
     * SQL UNION clause
     * @return Returns this object
     */
    public T UNION() {
        op().append(" UNION ");
        return (T) this;
    }

    /**
     * SQL ON clause used IN conjunction GROUP_WITH JOIN
     * @return Returns this object
     */
    public T ON() {
        op().append(" ON ");
        return (T) this;
    }
    
    /**
     * SQL ON clause used IN conjunction GROUP_WITH JOIN
     * @param column The COLUMN to JOIN ON
     * @return Returns this object
     */
    public T ON(Column column) {
        op().append(" ON ");
        op().append(column);
        op().append(" ");
        return (T) this;
    }

    /**
     * SQL ANY clause
     * @return Returns this object
     */
    public T ANY() {
        op().append(" ANY ");
        return (T) this;
    }

    /**
     * SQL SOME clause. It's an alias for ANY
     * @return Returns this object
     */
    public T SOME(){
        op().append(" SOME ");
        return (T) this;
    }

    /**
     * SQL SUBSTRING function
     *
     * @return Returns this object
     */
    public T SUBSTRING(){
        op().append(" SUBSTRING ");
        return (T) this;
    }

    /**
     * SQL CHARINDEX() function
     * @return Returns this object
     */
    public T CHARINDEX(){
        op().append(" CHARINDEX ");
        return (T) this;
    }

    /**
     * SQL SELECT clause
     * @return Returns this object
     */
    public T SELECT() {
        op().append("SELECT ");
        return (T) this;
    }

    /**
     * SQL SELECT clause
     * @param columns List of columns to SELECT
     * @return Returns this object
     */
    public T SELECT(List<Column> columns) {
        op().append("SELECT ");
        int len = columns.size();
        int cnt = 1;
        for(Column column:columns) {
            op().append(column);
            if(cnt++ < len){
                op().append(",");
            }
        }
        return (T) this;
    }

    /**
     * SQL SELECT clause
     * @param column The COLUMN to SELECT
     * @return Returns this object
     */
    public T SELECT(Column column) {
        op().append("SELECT ");
        op().append(column);
        return (T) this;
    }

    /**
     * SQL WITH INDEX clause
     * @param index The index name
     * @return Returns this object
     */
    public T WITHINDEX(Index index) {
        op().append(" WITH(");
        op().append("INDEX(");
        op().append(index);
        op().append("))");
        return (T) this;
    }

    private ColumnValue [] getValuesArray(){
        ColumnValue [] vals = new ColumnValue[getValues().size()];
        int idx = 0;
        for (ColumnValue e:getValues()) {
            vals[idx++] = e;
        }
        return vals;
    }

    /**
     * Converts the incoming populate to appropriate type in conjunction with PreparedStatement object.
     * These populate will be then be persisted in database
     *
     * @param ps The PreparedStatement object to use
     * @param params The value parameters to use
     * @throws SQLException The SQLException thrown by this method
     */
    private void populate(PreparedStatement ps, ColumnValue... params) throws SQLException {
        for (int i = 0, length = params.length; i < length; i++) {
            final Object param = params[i].getValue();
            final int paramIndex = i + 1;
            if (null == param) {
                ps.setObject(paramIndex, null);
            } else if (param instanceof Boolean) {
                ps.setBoolean(paramIndex, (Boolean) param);
            } else if (param instanceof Character) {
                ps.setString(paramIndex, String.valueOf(param));
            } else if (param instanceof Byte) {
                ps.setByte(paramIndex, (Byte) param);
            } else if (param instanceof Short) {
                ps.setShort(paramIndex, (Short) param);
            } else if (param instanceof Integer) {
                ps.setInt(paramIndex, (Integer) param);
            } else if (param instanceof Long) {
                ps.setLong(paramIndex, (Long) param);
            } else if (param instanceof Float) {
                ps.setFloat(paramIndex, (Float) param);
            } else if (param instanceof Double) {
                ps.setDouble(paramIndex, (Double) param);
            } else if (param instanceof String) {
                ps.setString(paramIndex, (String) param);
            } else if (param instanceof Date) {
                Date other = (Date) param;
                Timestamp ts = new Timestamp(other.getTime());
                ps.setTimestamp(paramIndex, new Timestamp(ts.getTime()));
            } else if (param instanceof Calendar) {
                ps.setDate(paramIndex, new java.sql.Date(((Calendar) param).getTimeInMillis()));
            } else if (param instanceof BigDecimal) {
                ps.setBigDecimal(paramIndex, (BigDecimal) param);
            } else if(param instanceof LocalDate) {
                LocalDate ld = (LocalDate) param;
                ps.setDate(paramIndex, java.sql.Date.valueOf(ld));
            } else if(param instanceof LocalTime) {
                LocalTime lt = (LocalTime) param;
                ps.setTime(paramIndex, java.sql.Time.valueOf(lt));
            } else if(param instanceof LocalDateTime) {
                LocalDateTime lt = (LocalDateTime) param;
                Timestamp ts = Timestamp.valueOf(lt);
                ps.setTimestamp(paramIndex, ts);
            }   
            else {
                throw new IllegalArgumentException(String.format("Unknown type of the param is found. [param: %s, paramIndex:%s]", param, paramIndex));
            }
        }
    }
    
    private void reset() {
        op().setLength(0); //clear StringBuilder
        getValues().clear();//clear the List
    }
}
