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
 * @param <T> 
 * @author umar
 */
public abstract class AbstractOp<T extends AbstractOp<T>> {

    public abstract String getSQL();

    public abstract StringBuilder op();

    public abstract List<ColumnValue> getValues();

    /**
     * Fills the PreparedStatement object with values
     *
     * @param ps The PreparedStatement object to fill
     * @return The PreparedStatement object
     */
    public PreparedStatement fill(PreparedStatement ps){
        try {
            populate(ps, getValuesArray());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ps;
    }



    /**
     * Appends the column name suffixed by =? to a PreparedStatement object.
     * It also adds the column values passed to java.util.List<ColumnValue> list
     *
     * @param columnValues The ColumnValue objects
     * @return Returns the current object
     */
    public T columnValueEq(ColumnValue ...columnValues) {
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
     * SQL EQUAL condition.
     * @param column The column to append
     * @return Returns this object
     */
    public T columnEq(String column) {
        op().append(column);
        op().append("=?");
        return (T) this;
    }

    /**
     * SQL EQUAL condition.
     * @param column The column to append
     * @return Returns this object
     */
    public T columnEq(Column column) {
        op().append(column);
        op().append("=?");
        return (T) this;
    }
    
    /**
     * The NOT EQUAL operator
     * @param condition The condition to suffix with <> operator
     * @return Returns this object
     */
    public T ne(Column condition) {
        op().append("<>");
        op().append(condition);
        return (T) this;
    }

    /**
     * SQL NOT statement
     * @return Returns this object
     */
    public T not() {
        op().append(" NOT ");
        return (T) this;
    }

    /**
     * SQL IS statement
     * @return Returns this object
     */
    public T is() {
        op().append(" IS ");
        return (T) this;
    }

    /**
     * The SQL NULL operator
     * @return Returns this object
     */
    public T nul(){
        op().append(" NULL");
        return (T) this;
    }

    /**
     * SQL AND statement
     * @return Returns this object
     */
    public T and() {
        op().append(" AND ");
        return (T) this;
    }

    /**
     * SQL AND statement
     * @param column The column to append with AND clause
     * @return Returns this object
     */
    public T and(Column column) {
        op().append(" AND ");
        op().append(column);
        return (T) this;
    }

    /**
     * Marks the beginning of a complex statement
     *
     * @return Returns this object
     */
    public T beginComplex() {
        op().append("(");
        return (T) this;
    }

    /**
     * SQL OR statement
     * @return Returns this object
     */
    public T or() {
        op().append(" OR ");
        return (T) this;
    }

    /**
     * Ends a complex statement that was begun by <code>beginComplex()</code> method
     *
     * @return Returns this object
     */
    public T endComplex() {
        op().append(")");
        return (T) this;
    }

    /**
     * SQL greater than statement
     *
     * @param column The column to prefix with >. It should be followed by <code>populate()</code> method to fill
     * @return Returns this object
     */
    public T gtCol(String column) {
        op().append(column);
        op().append(">?");
        return (T) this;
    }

    /**
     * SQL greater than statement
     *
     * @param column The column to prefix with >. It should be followed by <code>populate()</code> method to fill
     * @return Returns this object
     */
    public T gtCol(Column column) {
        op().append(column);
        op().append(">?");
        return (T) this;
    }

    /**
     * SQL less than statement
     * @param column The column to prefix with <. It should be followed by <code>populate()</code> method to fill
     * @return Returns this object
     */
    public T ltCol(String column) {
        op().append(column);
        op().append("<?");
        return (T) this;
    }

    /**
     * SQL less than statement
     * @param column The column to prefix with <. It should be followed by <code>populate()</code> method to fill
     * @return Returns this object
     */
    public T ltCol(Column column) {
        op().append(column);
        op().append("<?");
        return (T) this;
    }

    /**
     * SQL greater or equal to statement. It suffixes >=? to the PreparedStatement Object
     *
     * @param column The column to prefix with >=.
     * @return Returns this object
     */
    public T geCol(String column) {
        op().append(column);
        op().append(">=?");
        return (T) this;
    }

    /**
     * SQL greater or equal to statement
     *
     * @param column The column to prefix with >= It suffixes >=? to the PreparedStatement Object
     * @return Returns this object
     */
    public T geCol(Column column) {
        op().append(column);
        op().append(">=?");
        return (T) this;
    }

    /**
     * SQL less or equal to statement. It suffixes <=? to the PreparedStatement Object
     *
     * @param column The column to prefix with <=.
     * @return Returns this object
     */
    public T leCol(Column column) {
        op().append(column);
        op().append("<=?");
        return (T) this;
    }

    /**
     * SQL UPPER clause
     *
     * @return Returns this object
     */
    public T upper() {
        op().append(" UPPER ");
        return (T)this;
    }

    /**
     * SQL HAVING clause
     * @return Returns this object
     */
    public T having() {
        op().append(" HAVING ");
        return (T)this;
    }

    /**
     * SQL FROM clause
     * @return Returns this object
     * @param table The table to use with FROM clause
     */
    public T from(String table) {
        op().append(" FROM ");
        op().append(table);
        return (T) this;
    }

    /**
     * SQL FROM clause
     * @return Returns this object
     * @param table The table to use with FROM clause
     */
    public T from(Table table) {
        op().append(" FROM ");
        op().append(table);
        return (T) this;
    }
    
    /**
     * SQL FROM clause.
     * @return Returns this object
     * @param tables The tables to use with FROM clause
     */
    public T from(List<Table> tables) {
        int len = tables.size();
        int cnt = 1;
        op().append(" FROM(");
        for(Table table:tables){
            op().append(table);
            if(cnt++ < len){
                op().append(",");
            }
        }
        op().append(")");
        return (T) this;
    }

    /**
     * SQL WHERE clause.
     * @return Returns this object
     */
    public T where() {
        op().append(" WHERE ");
        return (T) this;
    }

    /**
     * SQL WHERE clause.
     * @return Returns this object
     */
    public T where(SelectOp op) {
        op().append(" WHERE (");
        op().append(op);
        op().append(")");
        return (T) this;
    }

    /**
     * SQL WHERE IN clause
     * @param column The column name
     * @param values The column populate to set
     * @return The current object
     */
    public T whereIn(Column column, List<ColumnValue> values) {
        int len = values.size();
        int cnt = 1;
        op().append(" WHERE ");
        op().append(column);
        op().append(" IN (");
        for(ColumnValue e: values) {
            op().append("?");
            if(cnt++ < len){
                op().append(",");
            }
            getValues().add(e);
        }
        op().append(")");

        return (T) this;
    }

    /**
     * SQL WHERE clause
     * @param column The column to append with WHERE clause
     * @return Returns this object
     */
    public T where(Column column) {
        op().append(" WHERE ");
        op().append(column);
        return (T) this;
    }

    /**
     * SQL TABLE to be worked with
     * @param table The table name
     * @return Returns this object
     */
    public T table(String table) {
        op().append(table);
        return (T) this;
    }

    /**
     * SQL TABLE to be worked with
     * @param table The table name
     * @return Returns this object
     */
    public T table(Table table) {
        op().append(table);
        return (T) this;
    }

    /**
     * SQL YEAR() function
     * @param column The column to use with YEAR()
     * @return Returns this object
     */
    public T year(Column column) {
        op().append(" YEAR(");
        op().append(column);
        op().append(")");
        return (T) this;
    }

    /**
     * SQL DISTINCT clause
     * @return Returns this object
     */
    public T distinct() {
        op().append("DISTINCT ");
        return (T) this;
    }

    /**
     * SQL DISTINCT clause
     * @param column The column to use with DISTINCT operator
     * @return Returns this object
     */
    public T distinct(String column) {
        op().append("DISTINCT ");
        op().append(column);
        return (T)this;
    }

    /**
     * SQL DISTINCT clause
     * @param column The column to use with DISTINCT operator
     * @return Returns this object
     */
    public T distinct(Column column) {
        op().append("DISTINCT ");
        op().append(column);
        return (T)this;
    }

    /**
     * SQL COUNT() function
     * @param column The column to use with count()
     * @return Returns this object
     */
    public T count(String column) {
        op().append("COUNT(");
        op().append(column);
        op().append(")");
        return (T) this;
    }

    /**
     * SQL COUNT() function
     * @param column The column to use with count()
     * @return Returns this object
     */
    public T count(Column column) {
        op().append("COUNT(");
        op().append(column);
        op().append(")");
        return (T) this;
    }

    /**
     * SQL COUNT() function
     * @param op T type operation
     * @return Returns this object
     */
    public T count(T op) {
        op().append("COUNT(");
        op().append(op);
        op().append(")");
        return (T) this;
    }

    /**
     * SQL MAX() function
     * @param column The column to use with MAX()
     * @return Returns this object
     */
    public T max(Column column) {
        op().append("MAX(");
        op().append(column);
        op().append(")");
        return (T) this;
    }

    /**
     * SQL MIN() function
     * @param column The column to use with MIN()
     * @return Returns this object
     */
    public T min(Column column) {
        op().append("MIN(");
        op().append(column);
        op().append(")");
        return (T) this;
    }

    /**
     * SQL AVG() function
     * @param column The column to use with AVG()
     * @return Returns this object
     */
    public T avg(Column column) {
        op().append("AVG(");
        op().append(column);
        op().append(")");
        return (T) this;
    }

    /**
     * SQL SUM() function. 'SUM' function is appended around the column
     * @param column The column to sum
     * @return Returns this object
     */
    public T sum(Column column) {
        op().append("SUM(");
        op().append(column);
        op().append(")");
        return (T) this;
    }

    /**
     * The SQL EXISTS clause
     * @param op The SQL operation
     * @return The current object
     */
    public T exists(T op) {
        op().append("EXISTS");
        op().append(" (");
        op().append(op);
        op().append(" )");
        return (T) this;
    }

    /**
     * SQL BETWEEN clause.
     *
     * @return Returns this object
     */
    public T between(List<ColumnValue> columnValues) {
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
     * @param op The SQL operation to wrap in 'BETWEEN' clause
     * @return Returns this object
     */
    public T between(T op) {
        op().append(" BETWEEN (");
        op().append(op);
        op().append(")");
        return (T) this;
    }

    /**
     * SQL LIKE clause. 'LIKE' operator is appended followed by ''pattern''.
     * @param pattern The String pattern to look for.
     * @return Returns this object
     */
    public T like(String pattern) {
        op().append(" LIKE ");
        op().append("?");
        getValues().add(ColumnValue.set(pattern));
        return (T) this;
    }

    /**
     * The SQL IN() function. The 'IN' function is appended followed by the SQL operation
     *
     * @param op The SQL operation to wrap in 'IN' function
     * @return Returns this object
     */
    public T in(T op) {
        op().append(" IN (");
        op().append(op);
        op().append(")");
        return (T) this;
    }

    /**
     * MySQL LIMIT Function
     *
     * @param n The int value of limit
     * @return Returns this object
     */
    public T limit(int n) {
        getValues().add(ColumnValue.set(n));
        op().append(" LIMIT ");
        op().append("?");
        return (T) this;
    }

    /**
     * MySQL OffSet Function
     *
     * @param n The int value to pass in offset
     * @return Returns this object
     */
    public T offset(int n) {
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
    public T as(String alias) {
        op().append(" AS ");
        op().append(alias);
        op().append(" ");
        return (T) this;
    }

    /**
     * SQL ORDER BY clause
     * @param column The column on which ORDER BY to apply
     * @return Returns this object
     */
    public T orderBy(String column) {
        op().append(" ORDER BY ");
        op().append(column);
        return (T) this;
    }
    
    /**
     * SQL ORDER BY clause
     * @param column The column on which ORDER BY to apply
     * @return Returns this object
     */
    public T orderBy(Column column) {
        op().append(" ORDER BY ");
        op().append(column);
        return (T) this;
    }

    /**
     * SQL ASC clause for ascending order
     * @return Returns this object
     */
    public T asc() {
        op().append(" ASC ");
        return (T) this;
    }

    /**
     * SQL DESC clause for descending order
     * @return Returns this object
     */
    public T desc() {
        op().append(" DESC ");
        return (T) this;
    }

    /**
     * SQL GREATER THAN clause.
     * @return Returns this object
     */
    public T gt() {
        op().append(">");
        return (T) this;
    }

    /**
     * SQL GREATER THAN clause.
     * @return Returns this object
     */
    public T gt(ColumnValue value) {
        op().append(">");
        op().append("?");
        getValues().add(value);
        return (T) this;
    }

    /**
     * SQL LESS THAN clause.
     * @return Returns this object
     */
    public T lt() {
        op().append("<");
        return (T) this;
    }

    /**
     * SQL LESS THAN clause.
     * @return Returns this object
     */
    public T lt(ColumnValue value) {
        op().append("<");
        op().append("?");
        getValues().add(value);
        return (T) this;
    }

    /**
     * SQL GREATER OR EQUAL clause.
     * @return Returns this object
     */
    public T ge(){
        op().append(">=");
        return (T) this;
    }

    /**
     * SQL GREATER OR EQUAL TO clause.
     * @return Returns this object
     */
    public T ge(ColumnValue value) {
        op().append(">=");
        op().append("?");
        getValues().add(value);
        return (T) this;
    }

    /**
     * SQL LESS OR EQUAL TO clause.
     * @return Returns this object
     */
    public T le(ColumnValue value) {
        op().append(">=");
        op().append("?");
        getValues().add(value);
        return (T) this;
    }

    /**
     * SQL LESS OR EQUAL clause.
     * @return Returns this object
     */
    public T le() {
        op().append("<=");
        return (T) this;
    }

    /**
     * SQL = operator. An '=' symbol is appended by this method followed by the 'condition'.
     * @param condition The condition to append with = operator
     * @return Returns this object
     */
    public T eq(String condition) {
        op().append("=");
        op().append(condition);
        return (T) this;
    }
    
    /**
     * SQL = operator. An '=' symbol is appended by this method followed by the 'condition'.
     * @param condition The condition to append with = operator
     * @return Returns this object
     */
    public T eq(Column condition) {
        op().append("=");
        op().append(condition);
        return (T) this;
    }

    /**
     * SQL = operator. An '=' symbol is appended by this method.<br>
     *
     * @return Returns this object
     */
    public T eq() {
        op().append("=");
        return (T) this;
    }

    /**
     * SQL GROUP BY clause. 'GROUP BY' operator is appended followed by columns to group.
     * @param columns The columns to group together
     * @return Returns this object
     *
     */
    public T groupBy(List<Column> columns){
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
    public T groupBy(Column column){
        op().append(" GROUP BY ");
        op().append(column);
        return (T) this;
    }

    /**
     * Use this method when grouping columns in
     * conjunction with count(), avg(), min(), max() method
     * @param columns The columns to use
     * @return Returns this object
     */
    public T with(List<Column> columns) {
        for (Column column:columns){
            op().append(",");
            op().append(column);
        }
        return (T) this;
    }

    /**
     * Use this method when grouping columns in
     * conjunction with count(), avg(), min(), max() method
     * @param column The columns to use
     * @return Returns this object
     */
    public T with(Column column) {
        op().append(", ");
        op().append(column);
        return (T) this;
    }

    /**
     * MySQL USING operator. 'USING' operator is appended around the 'column'
     * @param column The column
     * @return Returns this object
     */
    public T using(String column) {
        op().append(" USING(");
        op().append(column);
        op().append(")");
        return (T) this;
    }

    /**
     * MySQL USING operator. 'USING' operator is appended around the 'column'
     * @param column The column
     * @return Returns this object
     */
    public T using(Column column) {
        op().append(" USING(");
        op().append(column);
        op().append(")");
        return (T) this;
    }

    /**
     * MySQL MINUS operator
     * @return Returns this object
     */
    public T minus(){
        op().append(" MINUS ");
        return (T) this;
    }

    /**
     * SQL INNER clause
     * @return Returns this object
     */
    public T inner(){
        op().append(" INNER");
        return (T) this;
    }

    /**
     *
     * SQL JOIN operation
     * @return Returns this object
     */
    public T join() {
        op().append(" JOIN ");
        return (T) this;
    }

    /**
     * SQL LEFT JOIN clause
     * @return Returns this object
     */
    public T left() {
        op().append(" LEFT");
        return (T) this;
    }

    /**
     * SQL RIGHT JOIN clause
     * @return Returns this object
     */
    public T right() {
        op().append(" RIGHT");
        return (T) this;
    }

    /**
     * SQL UNION clause
     * @return Returns this object
     */
    public T union() {
        op().append(" UNION ");
        return (T) this;
    }

    /**
     * SQL ON clause used in conjunction with JOIN
     * @return Returns this object
     */
    public T on() {
        op().append(" ON ");
        return (T) this;
    }

    /**
     * SQL ANY clause
     * @return Returns this object
     */
    public T any() {
        op().append(" ANY ");
        return (T) this;
    }

    /**
     * SQL SOME clause. It's an alias for ANY
     * @return Returns this object
     */
    public T some(){
        op().append(" SOME ");
        return (T) this;
    }

    /**
     * SQL SUBSTRING function
     *
     * @return Returns this object
     */
    public T substring(){
        op().append(" SUBSTRING ");
        return (T) this;
    }

    /**
     * SQL charindex() function
     * @return Returns this object
     */
    public T charindex(){
        op().append(" CHARINDEX ");
        return (T) this;
    }

    /**
     * SQL SELECT clause
     * @return Returns this object
     */
    public T select() {
        op().append("SELECT ");
        return (T) this;
    }

    /**
     * SQL SELECT clause
     * @return Returns this object
     */
    public T select(List<Column> columns) {
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
     * @return Returns this object
     */
    public T select(Column column) {
        op().append("SELECT ");
        op().append(column);
        return (T) this;
    }

    /**
     * SQL WITH INDEX clause
     * @param index The index name
     * @return Returns this object
     */
    public T withIndex(Index index) {
        op().append("WITH(");
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
}
