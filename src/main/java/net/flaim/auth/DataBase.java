package net.flaim.auth;

import java.sql.SQLException;

public class DataBase {
    public static Users users;

    static {
        if (users == null) users = new Users();
        users.createDB();
    }


}