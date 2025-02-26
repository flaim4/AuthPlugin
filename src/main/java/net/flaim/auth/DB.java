package net.flaim.auth;

import java.sql.*;

public interface DB {

    public default void conn() throws ClassNotFoundException, SQLException {}

    public default void createDB() throws ClassNotFoundException, SQLException {}

    public default void WriteDB() throws SQLException {}

    public default void readDB() throws ClassNotFoundException, SQLException {}

    public default void closeDB() throws ClassNotFoundException, SQLException {}

}
