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
import java.time.temporal.Temporal;

public class Dashboard extends JFrame{
    private JPanel JPanel1;
    private JButton wypozyczButton;
    private JButton closeButton;
    private JPanel close;
    private JTable table1;
    private JLabel jDane;
    private JButton oddajButton;
    private JLabel jDane2;
    private JLabel jDane3;
    private JButton wylogujButton;

    public static User user;


    public Dashboard(User user) throws SQLException {
        Dashboard.user = user;
        setTitle("Wirtualna księgarnia");

        this.setContentPane(JPanel1);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        int width = 800, height = 600;
        setMinimumSize(new Dimension(width, height));
        setLocationRelativeTo(null);

        try {
            setIconImage(ImageIO.read(new File("src/icon.png")));
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Wystąpił błąd przy wczytywaniu icon.png.");
        }

        Connection connection = Database.getConnection();
        try{
            String sql = "SELECT * FROM users WHERE id=" + user.getId();
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rst = pst.executeQuery();
            rst.next();

            DefaultTableModel model = new DefaultTableModel(new String[]{
                    "Nazwa książki",
                    "Autor",
                    "Data wypożyczenia",
                    "Data oddania",
                    "Kwota płatności"
            }, 0);
            jDane.setText("Zalogowany jako:");
            jDane2.setText(user.getName() + " " + user.getSurname());
            jDane3.setText(user.getMail());

            table1.setModel(model);
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        wypozyczButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();

                Biblioteka biblioteka = null;
                try {
                    biblioteka = new Biblioteka(user);
                    biblioteka.setVisible(true);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        oddajButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();

                if (selectedRow != -1) {
                    String bookName = table1.getValueAt(selectedRow, 0).toString().trim(); // Pobierz nazwę książki

                    int result = JOptionPane.showConfirmDialog(null,
                            "Czy na pewno chcesz zwrócić książkę: " + bookName + "?", "Potwierdzenie", JOptionPane.YES_NO_OPTION);

                    if (result == JOptionPane.YES_OPTION) {
                        try {
                            String selectBookSql = "SELECT * FROM book WHERE LOWER(book_name) = LOWER(?)";
                            PreparedStatement selectBookStatement = connection.prepareStatement(selectBookSql);
                            selectBookStatement.setString(1, bookName);
                            ResultSet bookResultSet = selectBookStatement.executeQuery();

                            if (bookResultSet.next()) {
                                int bookId = bookResultSet.getInt("id");

                                // Usuń informację o wypożyczeniu książki dla danego użytkownika
                                String deleteSql = "DELETE FROM rent WHERE user_id = ? AND book_id = ?";
                                PreparedStatement deleteStatement = connection.prepareStatement(deleteSql);
                                deleteStatement.setInt(1, user.getId());
                                deleteStatement.setInt(2, bookId);
                                deleteStatement.executeUpdate();

                                refreshTable();

                                JOptionPane.showMessageDialog(null, "Książka: " + bookName + " została zwrócona.");
                            } else {
                                JOptionPane.showMessageDialog(null, "Nie znaleziono książki o nazwie: " + bookName, "Błąd", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Wystąpił błąd podczas oddawania książki.", "Błąd", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Nie wybrano książki do zwrotu.", "Błąd", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        wylogujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                System.out.println("Wylogowano.");
                menu Menu = new menu();
                Menu.setVisible(true);
            }
        });
        showUserRentedBooks();
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                System.out.println("Wyłączono program.");
            }
        });
    }
    private void showUserRentedBooks() {
        String sql = "SELECT b.book_name, b.book_author, r.rent_date, r.return_date, r.payment_amount FROM rent r " +
                "JOIN book b ON r.book_id = b.id WHERE r.user_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, user.getId());
            ResultSet rst = pst.executeQuery();

            DefaultTableModel model = new DefaultTableModel(new String[]{
                    "Nazwa książki",
                    "Autor",
                    "Data wypożyczenia",
                    "Data oddania",
                    "Kwota płatności"
            }, 0);

            while (rst.next()) {
                String returnDate = rst.getString("return_date");
                String paymentAmount = rst.getString("payment_amount");

                Object[] row = {
                        rst.getString("book_name"),
                        rst.getString("book_author"),
                        rst.getString("rent_date"),
                        returnDate,
                        (paymentAmount != null) ? paymentAmount : "------------"
                };

                if (returnDate != null) {
                    if (java.sql.Date.valueOf(returnDate).before(java.sql.Date.valueOf(LocalDate.now().toString()))) {
                        row[3] = "<html><font color='red'>" + returnDate + "</font></html>";
                        if (paymentAmount == null) {
                            int daysOverdue = (int) ChronoUnit.DAYS.between((Temporal) LocalDate.now(), (Temporal) Date.valueOf(returnDate));
                            row[4] = daysOverdue * 3.0; // 1 day = 3 zł
                            updatePaymentAmount(user.getId(), rst.getInt("book_id"), daysOverdue * 3.0);
                        }
                    }
                }
                model.addRow(row);
            }
            table1.setModel(model);
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void updatePaymentAmount(int userId, int bookId, double paymentAmount) {
        String updateSql = "UPDATE rent SET payment_amount = ? WHERE user_id = ? AND book_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement updateStatement = conn.prepareStatement(updateSql)) {

            updateStatement.setDouble(1, paymentAmount);
            updateStatement.setInt(2, userId);
            updateStatement.setInt(3, bookId);
            updateStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        model.setRowCount(0);

        showUserRentedBooks();
    }
    public static void main(String[] args) throws SQLException {
        Dashboard dashboard = new Dashboard(user);
        dashboard.setVisible(true);
    }
}
