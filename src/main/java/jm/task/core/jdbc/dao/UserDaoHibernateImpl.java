package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users " +
                "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(45) NOT NULL, lastName VARCHAR(45) NOT NULL, " +
                "age TINYINT NOT NULL)";
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
            try (Session session = Util.getSessionFactory().openSession()) {
                session.beginTransaction();
                session.save(new User(name, lastName, age));
                session.getTransaction().commit();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        System.out.println("User с именем " + name + " добавлен в базу данных");
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            list = session.createQuery("from User").getResultList();
            session.getTransaction().commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createQuery("delete User ").executeUpdate();
            session.getTransaction().commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
