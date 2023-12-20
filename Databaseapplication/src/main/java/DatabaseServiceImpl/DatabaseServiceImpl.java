/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DatabaseServiceImpl;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author rasel
 */
public class DatabaseServiceImpl extends UnicastRemoteObject implements DatabaseService {
    private Connection connection;

    public DatabaseServiceImpl() throws RemoteException {
        super();

        // Initialize database connection (Update the URL, username, and password)
        String url = "jdbc:mysql://localhost:3306/test";
        String username = "your_username";
        String password = "your_password";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("Error connecting to the database.");
        }
    }

    public void insertPerson(Person person) throws RemoteException {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Person (name, age) VALUES (?, ?)");
            statement.setString(1, person.getName());
            statement.setInt(2, person.getAge());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Error inserting person record.");
        }
    }

    public List<Person> getAllPersons() throws RemoteException {
        List<Person> persons = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Person");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                persons.add(new Person(id, name, age));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Error retrieving persons.");
        }

        return persons;
    }

    public Person getPersonById(int id) throws RemoteException {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Person WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                return new Person(id, name, age);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Error retrieving person by ID.");
        }

        return null;
    }

    public void updatePerson(Person person) throws RemoteException {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE Person SET name = ?, age = ? WHERE id = ?");
            statement.setString(1, person.getName());
            statement.setInt(2, person.getAge());
            statement.setInt(3, person.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Error updating person record.");
        }
    }

    public void deletePerson(int id) throws RemoteException {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Person WHERE id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Error deleting person record.");
        }
    }
}