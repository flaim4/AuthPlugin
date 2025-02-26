package net.flaim.auth;

import java.sql.*;
import java.util.UUID;
import java.util.function.Consumer;
import org.bukkit.Bukkit;

public class Users implements DB {
    private Connection conn;

    public Users() {
        connect(() -> createDB());
    }

    public void connect(Runnable onConnected) {
        Bukkit.getScheduler().runTaskAsynchronously(Auth.getInstance(), () -> {
            try {
                conn = DriverManager.getConnection("jdbc:sqlite:auth.db");
                System.out.println("База данных подключена!");
                onConnected.run();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void createDB() {
        if (conn == null) {
            System.out.println("Ошибка: База данных не подключена!");
            return;
        }

        Bukkit.getScheduler().runTaskAsynchronously(Auth.getInstance(), () -> {
            try (Statement stmt = conn.createStatement()) {
                String sql = "CREATE TABLE IF NOT EXISTS users (" + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "uuid TEXT UNIQUE NOT NULL, " + "password TEXT NOT NULL)";
                stmt.executeUpdate(sql);
                System.out.println("Таблица users создана или уже существует!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void writeData(UUID uuid, String password) {
        Bukkit.getScheduler().runTaskAsynchronously(Auth.getInstance(), () -> {
            try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (uuid, password) VALUES (?, ?)")) {
                stmt.setString(1, uuid.toString());
                stmt.setString(2, password);
                stmt.executeUpdate();
                System.out.println("Данные записаны!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void getUserData(String uuid, Consumer<String> callback) {
        Bukkit.getScheduler().runTaskAsynchronously(Auth.getInstance(), () -> {
            try (PreparedStatement stmt = conn.prepareStatement("SELECT password FROM users WHERE uuid = ?")) {
                stmt.setString(1, uuid);
                try (ResultSet resSet = stmt.executeQuery()) {
                    if (resSet.next()) {
                        String password = resSet.getString("password");
                        callback.accept(password);
                    } else {
                        System.out.println("Пользователь не найден!");
                        callback.accept(null);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                callback.accept(null);
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
