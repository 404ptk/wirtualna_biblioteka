import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class AdminDashboard extends JFrame{
    private JPanel JPanel1;
    private JTable table1;
    private JButton closeButton;
    private JButton usunButton;
    private JPanel close;
    private JLabel jDane;
    private JLabel jDane2;
    private JLabel jDane3;
    private JButton ksiazkiButton;
    private JButton wylogujButton;
    private JLabel jOsoba;
    private JTable ksiazkiTable;
    private User loggedInUser;

    public static void main(String[] args) throws SQLException {
        AdminDashboard admindashboard = new AdminDashboard();
        admindashboard.setVisible(true);
    }

    public AdminDashboard() throws SQLException {
        setTitle("Panel administracyjny");
        this.setContentPane(JPanel1);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        int width = 800, height = 600;
        setMinimumSize(new Dimension(width, height));
        setLocationRelativeTo(null);
        ksiazkiTable = new JTable();

        try {
            setIconImage(ImageIO.read(new File("src/icon.png")));
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Wystąpił błąd przy wczytywaniu icon.png.");
        }

        Connection connection = Database.getConnection();
        String sql = "SELECT * FROM users";
        try{
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rst = pst.executeQuery();
            rst.next();

            jDane.setText("Zalogowany jako: ");
            jDane2.setText("Administrator");
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }


        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        usunButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Pobierz zaznaczone ID użytkownika
                int selectedRow = table1.getSelectedRow();
                if (selectedRow != -1) {
                    int userId = (int) table1.getValueAt(selectedRow, 0);

                    try {
                        // Usuń użytkownika z bazy danych
                        deleteUser(userId);

                        // Komunikat o prawidłowym usunięciu użytkownika
                        JOptionPane.showMessageDialog(null, "Użytkownik został pomyślnie usunięty.", "Sukces", JOptionPane.INFORMATION_MESSAGE);

                        // Odśwież tabelę po usunięciu użytkownika
                        refreshTable();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        // Komunikat o błędzie podczas usuwania użytkownika
                        JOptionPane.showMessageDialog(null, "Błąd podczas usuwania użytkownika.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    // Komunikat gdy nie zaznaczono użytkownika do usunięcia
                    JOptionPane.showMessageDialog(null, "Nie wybrano użytkownika.", "Błąd", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        ksiazkiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AdminBiblioteka adminBiblioteka = null;
                try {
                    adminBiblioteka = new AdminBiblioteka();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                adminBiblioteka.setVisible(true);
            }
        });

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = new DefaultTableModel(new String[]{
                    "ID",
                    "Imię i nazwisko",
                    "Mail",
                    "Wypożyczone książki",
                    "Zalegające książki",
                    "Opłaty"
            }, 0);

            while (rs.next()) {
                int userId = rs.getInt("id");
                String userName = rs.getString("name") + " " + rs.getString("surname");
                String userMail = rs.getString("mail");
                String userBooks = rs.getString("books");

                // Dodaj warunek sprawdzający, czy userBooks jest null
                String[] borrowedBooks = (userBooks != null) ? userBooks.split(", ") : new String[]{};
                String overdueBooks = getOverdueBooks(userBooks);
                String overdueFees = "";

                // Dodaj warunek sprawdzający, czy borrowedBooks nie jest null
                if (borrowedBooks != null) {
                    overdueFees = String.valueOf(calculateOverdueFees(overdueBooks));
                }

                model.addRow(new String[]{
                        String.valueOf(userId),
                        userName,
                        userMail,
                        String.valueOf(borrowedBooks.length),  // Zmiana: Ilość wypożyczonych książek
                        overdueBooks,
                        overdueFees
                });
            }
            table1.setModel(model);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        wylogujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                menu Menu = new menu();
                Menu.setVisible(true);
            }
        });

    }
    private double calculateOverdueFees(String books) {
        double overdueFees = 0.0;

        if (books != null && !books.isEmpty()) {
            String[] bookList = books.split(", ");
            for (String book : bookList) {
                String[] bookInfo = book.split(":");
                if (bookInfo.length == 2) {
                    String returnDate = bookInfo[1];

                    // Sprawdź, czy książka jest zaległa
                    if (returnDate != null && !returnDate.isEmpty()) {
                        try {
                            LocalDate currentDate = LocalDate.now();
                            LocalDate dueDate = LocalDate.parse(returnDate.trim());

                            if (currentDate.isAfter(dueDate)) {
                                long daysOverdue = ChronoUnit.DAYS.between(dueDate, currentDate);
                                overdueFees += daysOverdue * 3.0; // Przyjęta stawka za opóźnienie (1 dzień = 3 zł)
                            }
                        } catch (DateTimeParseException e) {
                            // Dodaj obsługę błędnych dat (jeśli występują)
                            System.err.println("Błąd podczas parsowania daty: " + e.getMessage());
                        }
                    }
                }
            }
        }

        return overdueFees;
    }
    private void deleteUser(int userId) throws SQLException {
        Connection connection = Database.getConnection();
        String deleteSql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(deleteSql)) {
            pst.setInt(1, userId);
            pst.executeUpdate();
        }
    }

    private void refreshTable() {

    }
    private String getOverdueBooks(String books) {
        StringBuilder overdueBooks = new StringBuilder();

        if (books != null && !books.isEmpty()) {
            String[] bookList = books.split(", ");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            for (String book : bookList) {
                String[] bookInfo = book.split(":");
                if (bookInfo.length == 2) {
                    String bookName = bookInfo[0];
                    String returnDate = bookInfo[1];

                    // Sprawdź, czy książka jest zaległa
                    if (returnDate != null && !returnDate.isEmpty()) {
                        try {
                            LocalDate currentDate = LocalDate.now();
                            LocalDate dueDate = LocalDate.parse(returnDate.trim(), formatter);

                            if (currentDate.isAfter(dueDate)) {
                                if (overdueBooks.length() > 0) {
                                    overdueBooks.append(", ");
                                }
                                overdueBooks.append(bookName).append(":").append(returnDate);
                            }
                        } catch (DateTimeParseException e) {
                            // Dodaj obsługę błędnych dat (jeśli występują)
                            System.err.println("Błąd podczas parsowania daty: " + e.getMessage());
                        }
                    }
                }
            }
        }

        return overdueBooks.toString();
    }
}

