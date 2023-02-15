package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Connection connection = (new Util()).getConnection(); Statement statement = connection.createStatement()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet res = metaData.getTables("users", null, "users", null);
            if (res.next()) {
                System.out.println("Таблица с таким название уже существует");
            } else {
                String sql = "CREATE TABLE `users`.`users` (\n" +
                        "  `id` BIGINT NOT NULL AUTO_INCREMENT,\n" +
                        "  `name` VARCHAR(45) NOT NULL,\n" +
                        "  `lastName` VARCHAR(45) NOT NULL,\n" +
                        "  `age` TINYINT NOT NULL,\n" +
                        "  PRIMARY KEY (`id`))";
                statement.executeUpdate(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE `users`.`users`";
        try (Connection connection = (new Util()).getConnection(); Statement statement = connection.createStatement()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet res = metaData.getTables("users", null, "users", null);
            if (res.next()) {
                statement.executeUpdate(sql);
            } else {
                System.out.println("Таблицы users не существует");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        try (Connection connection = (new Util()).getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("User с именем " + name + " добавлен в базу данных");
    }

    public void removeUserById(long id) {
        String sql = "delete from users where id = ?";
        try (Connection connection = (new Util()).getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        String sql = "select * from users";
        try (Connection connection = (new Util()).getConnection(); Statement statement = connection.createStatement()) {
            if (statement.execute(sql)) {
                ResultSet resultSet = statement.getResultSet();
                List<User> list = new ArrayList<>();
                while (resultSet.next()) {
                    list.add(new User(resultSet.getString(2), resultSet.getString(3), resultSet.getByte(4)));
                }
                return list;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanUsersTable() {
        String sql = "delete from users";
        try (Connection connection = (new Util()).getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
