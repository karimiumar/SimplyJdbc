package com.umar.simply.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetMapper<T> {
    /**
     * Map the given SQL ResultSet to the corresponding type T
     * @param resultSet The ResultSet to map
     * @return Returns the type mapped
     * @throws SQLException 
     */
    T map (ResultSet resultSet) throws SQLException;
}
