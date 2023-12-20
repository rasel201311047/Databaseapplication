/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DatabaseServiceImpl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.util.List;
/**
 *
 * @author rasel
 */
public class DatabaseClient extends JFrame {
    private DatabaseService databaseService;

    public DatabaseClient() {
        super("RMI Database Client");

        try {
            databaseService = (DatabaseService) Naming.lookup("rmi://localhost:1099/DatabaseService");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database server.");
            System.exit(1);
        }

        initializeUI();
    }

    private void initializeUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JButton addButton = new JButton("Add Person");
        JButton refreshButton = new JButton("Refresh Persons");

        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText();
                    int age = Integer.parseInt(ageField.getText());

                    databaseService.insertPerson(new Person(0, name, age));
                    refreshPersons(resultArea);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(DatabaseClient.this, "Invalid input. Please enter valid values.");
                }
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshPersons(resultArea);
            }
        });

        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Age:"));
        panel.add(ageField);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(addButton);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(refreshButton);

        this.setLayout(new BorderLayout());
        this.add(panel, BorderLayout.NORTH);
        this.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 300);
        this.setLocationRelativeTo(null); // Center the window
    }

    private void refreshPersons(JTextArea resultArea) {
        try {
            List<Person> persons = databaseService.getAllPersons();

            StringBuilder resultText = new StringBuilder();
            resultText.append("Persons:\n");

            for (Person person : persons) {
                resultText.append("ID: ").append(person.getId()).append(", Name: ").append(person.getName())
                        .append(", Age: ").append(person.getAge()).append("\n");
            }

            resultArea.setText(resultText.toString());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(DatabaseClient.this, "Error refreshing persons.");
        }
    }

    public static void main(String[] args) {
        DatabaseClient client = new DatabaseClient();
        client.setVisible(true);
    }
}