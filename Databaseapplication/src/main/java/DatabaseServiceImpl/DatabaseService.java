/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DatabaseServiceImpl;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
/**
 *
 * @author rasel
 */
public interface DatabaseService extends Remote {
    void insertPerson(Person person) throws RemoteException;
    List<Person> getAllPersons() throws RemoteException;
    Person getPersonById(int id) throws RemoteException;
    void updatePerson(Person person) throws RemoteException;
    void deletePerson(int id) throws RemoteException;

    public List<Person> getAllPersons();
}
