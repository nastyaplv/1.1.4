package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl us1 = new UserServiceImpl();
        us1.createUsersTable();
        us1.saveUser("Ivan", "Petrov", (byte)20);
        us1.saveUser("Olga", "Malutina", (byte)31);
        us1.saveUser("Anastasiya", "Zinnyatullova", (byte)31);
        us1.saveUser("Dmitriy", "Pchelintsev", (byte)42);
        System.out.println(us1.getAllUsers().toString());
        us1.cleanUsersTable();
        us1.dropUsersTable();
    }
}
