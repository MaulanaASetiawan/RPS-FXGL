package org.example.rpsfxgl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Akun {
    private String Username;
    private String Password;
    private int Health;
    private int Xp;
    private int Level;
    private int Damage;

    public Akun(String username, String password, int health, int xp, int level, int damage) {
        Username = username;
        Password = password;
        Health = health;
        Xp = xp;
        Level = level;
        Damage = damage;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getHealth() {
        return Health;
    }

    public void setHealth(int health) {
        Health = health;
    }

    public int getXp() {
        return Xp;
    }

    public void setXp(int xp) {
        Xp = xp;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        Level = level;
    }

    public int getDamage() {
        return Damage;
    }

    public void setDamage(int damage) {
        Damage = damage;
    }

    public void updatePlayerData() {
        try (Connection connection = Koneksi.DBConnect()) {
            String query = "UPDATE user SET xp = ?, level = ?, health = ? WHERE Username = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, Xp);
                preparedStatement.setInt(2, Level);
                preparedStatement.setInt(3, Health);
                preparedStatement.setString(4, Username);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

