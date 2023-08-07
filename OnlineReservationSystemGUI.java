import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OnlineReservationSystemGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField trainNumberField;
    private JTextField classTypeField;
    private JTextField dateOfJourneyField;
    private JTextField sourceField;
    private JTextField destinationField;
    private JTextField pnrField;

    private Map<String, String> users;
    private List<Reservation> reservations;

    public OnlineReservationSystemGUI() {
        users = new HashMap<>();
        users.put("pinky", "password"); 

        reservations = new ArrayList<>();

        initUI();
    }

    private void initUI() {
        // Login Form
        JPanel loginPanel = new JPanel(new GridLayout(3, 2));
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(15);
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(15);
        JButton loginButton = new JButton("Login");

        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(new JLabel());
        loginPanel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        // Reservation Form
        JPanel reservationPanel = new JPanel(new GridLayout(6, 2));
        JLabel trainNumberLabel = new JLabel("Train Number:");
        trainNumberField = new JTextField(10);
        trainNumberField.setEditable(false);
        JLabel classTypeLabel = new JLabel("Class Type:");
        classTypeField = new JTextField(10);
        JLabel dateOfJourneyLabel = new JLabel("Date of Journey:");
        dateOfJourneyField = new JTextField(10);
        JLabel sourceLabel = new JLabel("From (Place):");
        sourceField = new JTextField(10);
        JLabel destinationLabel = new JLabel("To (Destination):");
        destinationField = new JTextField(10);
        JButton insertButton = new JButton("Insert");

        reservationPanel.add(trainNumberLabel);
        reservationPanel.add(trainNumberField);
        reservationPanel.add(classTypeLabel);
        reservationPanel.add(classTypeField);
        reservationPanel.add(dateOfJourneyLabel);
        reservationPanel.add(dateOfJourneyField);
        reservationPanel.add(sourceLabel);
        reservationPanel.add(sourceField);
        reservationPanel.add(destinationLabel);
        reservationPanel.add(destinationField);
        reservationPanel.add(new JLabel());
        reservationPanel.add(insertButton);

        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleReservationInsert();
            }
        });

        // Cancellation Form
        JPanel cancellationPanel = new JPanel(new GridLayout(2, 2));
        JLabel pnrLabel = new JLabel("PNR Number:");
        pnrField = new JTextField(10);
        JButton cancelButton = new JButton("OK");

        cancellationPanel.add(pnrLabel);
        cancellationPanel.add(pnrField);
        cancellationPanel.add(new JLabel());
        cancellationPanel.add(cancelButton);

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleCancellation();
            }
        });

        // Create the main frame and add the login, reservation, and cancellation panels to it
        JFrame frame = new JFrame("Online Reservation System");
        frame.setLayout(new GridLayout(3, 1));
        frame.add(loginPanel);
        frame.add(reservationPanel);
        frame.add(cancellationPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void handleLogin() {
        // Perform login validation here
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (users.containsKey(username) && users.get(username).equals(password)) {
            trainNumberField.setEditable(true);
            classTypeField.setEditable(true);
            dateOfJourneyField.setEditable(true);
            sourceField.setEditable(true);
            destinationField.setEditable(true);
            pnrField.setEditable(true);

            JOptionPane.showMessageDialog(null, "Login successful!");
        } else {
            JOptionPane.showMessageDialog(null, "Invalid login credentials!");
        }
    }

    private void handleReservationInsert() {
        // Get the reservation details from the form and perform reservation insert
        String trainNumber = trainNumberField.getText();
        String classType = classTypeField.getText();
        String dateOfJourney = dateOfJourneyField.getText();
        String source = sourceField.getText();
        String destination = destinationField.getText();

        // Perform reservation insertion (you can store this data in a database)
        Reservation reservation = new Reservation(trainNumber, classType, dateOfJourney, source, destination);
        reservations.add(reservation);

        JOptionPane.showMessageDialog(null, "Reservation inserted successfully!");
    }

    private void handleCancellation() {
        // Get the PNR number from the form and perform reservation cancellation
        String pnrNumber = pnrField.getText();
        int pnr = Integer.parseInt(pnrNumber);

        boolean reservationFound = false;

        for (Reservation reservation : reservations) {
            if (reservation.getPnr() == pnr) {
                reservations.remove(reservation);
                reservationFound = true;
                break;
            }
        }

        if (reservationFound) {
            JOptionPane.showMessageDialog(null, "Reservation with PNR " + pnr + " has been canceled.");
        } else {
            JOptionPane.showMessageDialog(null, "Reservation with PNR " + pnr + " not found.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new OnlineReservationSystemGUI();
            }
        });
    }
}

class Reservation {
    private static int nextPnr = 1000;

    private int pnr;
    private String trainNumber;
    private String classType;
    private String dateOfJourney;
    private String source;
    private String destination;

    public Reservation(String trainNumber, String classType, String dateOfJourney, String source, String destination) {
        this.pnr = nextPnr++;
        this.trainNumber = trainNumber;
        this.classType = classType;
        this.dateOfJourney = dateOfJourney;
        this.source = source;
        this.destination = destination;
    }

    public int getPnr() {
        return pnr;
    }
}
