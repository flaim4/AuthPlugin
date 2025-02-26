package net.flaim.auth;

import java.sql.*;
import org.bukkit.Bukkit;

public class Users implements DB{

    private Connection conn;

    public Users() {
        connect();
    }

    public void connect() {
        Bukkit.getScheduler().runTaskAsynchronously(Auth.getInstance(), () -> {
            try {
                conn = DriverManager.getConnection("jdbc:sqlite:auth.db");
                System.out.println("База данных подключена!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void writeData(String name, String password) {
        Bukkit.getScheduler().runTaskAsynchronously(Auth.getInstance(), () -> {
            try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (name, password) VALUES (?, ?)")) {
                stmt.setString(1, name);
                stmt.setString(2, password);
                stmt.executeUpdate();
                System.out.println("Данные записаны!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void close() {
        Bukkit.getScheduler().runTaskAsynchronously(Auth.getInstance(), () -> {
            try {
                if (conn != null) conn.close();
                System.out.println("Соединение с БД закрыто!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

}
