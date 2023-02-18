package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl us = new UserServiceImpl();
        us.createUsersTable();
        us.saveUser("Ivan", "Kulikov", (byte) 30);
        us.saveUser("Marina", "Sergeeva", (byte) 35);
        us.saveUser("Alexey", "Ryazanov", (byte) 18);
        us.saveUser("Tatyana", "Loginova", (byte) 45);
        System.out.println(us.getAllUsers());
        us.cleanUsersTable();
        us.dropUsersTable();
    }
}
