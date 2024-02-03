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
import java.time.temporal.ChronoUnit;

public class AdminDashboard extends JFrame{
    private JPanel JPanel1;
    private JTable table1;
    private JButton closeButton;
    private JButton edytujButton;
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
            System.out.println("Wystąpił błąd przy wczytywaniu ur.png.");
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
        edytujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();
                if (selectedRow != -1) {
                    int userId = (int) table1.getValueAt(selectedRow, 0);

                    try {
                        String books = getBooksForUser(userId);
                        showBooksDialog(books);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Błąd podczas pobierania danych.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Nie wybrano użytkownika.", "Błąd", JOptionPane.WARNING_MESSAGE);
                }
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
                String overdueBooks = getOverdueBooks(userBooks);
                String overdueFees = calculateOverdueFees(overdueBooks);

                model.addRow(new String[]{
                        String.valueOf(userId),
                        userName,
                        userMail,
                        userBooks,
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
    private double calculateOverdueFees(int userId) {
        double overdueFees = 0.0;

        final String DB_URL = "jdbc:mysql://localhost/MyStore?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            String overdueSql = "SELECT * FROM rent WHERE user_id=? AND return_date < current_date";
            try (PreparedStatement overduePs = conn.prepareStatement(overdueSql)) {
                overduePs.setInt(1, userId);
                ResultSet overdueRs = overduePs.executeQuery();

                while (overdueRs.next()) {
                    LocalDate returnDate = overdueRs.getDate("return_date").toLocalDate();
                    LocalDate currentDate = LocalDate.now();
                    long daysOverdue = ChronoUnit.DAYS.between(returnDate, currentDate);

                    // Oblicz opłaty za opóźnienie (1 dzień = 3 zł)
                    overdueFees += daysOverdue * 3;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
    private String getBooksForUser(int userId) throws SQLException {
        Connection connection = Database.getConnection();
        String sql = "SELECT books FROM users WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getString("books");
            }
        }
        return "";
    }
    private void showBooksDialog(String books) {
        DefaultTableModel model = new DefaultTableModel(new String[]{"Książki użytkownika"}, 0);
        String[] bookList = books.split(", ");  // Zakładam, że książki są oddzielone przecinkiem
        for (String book : bookList) {
            model.addRow(new String[]{book});
        }
        ksiazkiTable.setModel(model);

        // Utwórz nowy dialog z tabelą książek użytkownika
        JDialog dialog = new JDialog(this, "Książki użytkownika", true);
        dialog.setLayout(new BorderLayout());
        dialog.add(new JScrollPane(ksiazkiTable), BorderLayout.CENTER);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    private String getOverdueBooks(String books) {
        return books;
    }

    private String calculateOverdueFees(String overdueBooks) {
        return overdueBooks.length() * 3 + "zł";
    }
}

