/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DatabaseServiceImpl;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
/**
 *
 * @author rasel
 */
public class DatabaseServer {
    public static void main(String[] args) {
        try {
            DatabaseService databaseService = (DatabaseService) new DatabaseServiceImpl();

            LocateRegistry.createRegistry(1099);

            Naming.rebind("rmi://localhost:1099/DatabaseService", (Remote) databaseService);

            System.out.println("Database server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}