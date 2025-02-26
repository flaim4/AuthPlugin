package net.flaim.auth;

import java.sql.SQLException;

public class DataBase {
    static Users users;

    static {
        try {
            if (users == null) users = new Users();
            users.createDB();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}