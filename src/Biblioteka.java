import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Biblioteka extends JFrame {
    private JPanel JPanel1;
    private JTable table1;
    private JButton closeButton;
    private JButton wsteczButton;
    private JPanel JKsiazki;
    private JButton wypozyczButton;
    private JButton oddajButton;
    private JLabel jDane;
    private JLabel jDane2;
    private JLabel jDane3;
    public static User user;

    public static void main(String[] args) throws SQLException {
        Biblioteka biblioteka = new Biblioteka(user);
        biblioteka.setVisible(true);
    }

    public Biblioteka(User user) throws SQLException {
        Biblioteka.user = user;
        setTitle("Dostępne książki");
        this.setContentPane(this.JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 800, height = 600;
        this.setSize(width, height);
        setLocationRelativeTo(null);

        jDane.setText("Zalogowany jako:");
        jDane2.setText(user.getName() + " " + user.getSurname());
        jDane3.setText(user.getMail());

        try {
            setIconImage(ImageIO.read(new File("src/icon.png")));
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Wystąpił błąd przy wczytywaniu ur.png.");
        }

        Connection connection = Database.getConnection();
        String sql = "SELECT * FROM book";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = new DefaultTableModel(new String[]{
                    "Id",
                    "Nazwa książki",
                    "Autor",
                    "Ilość książek"
            }, 0);
            while (rs.next()) {
                model.addRow(new String[]{
                        rs.getString("id"),
                        rs.getString("book_name"),
                        rs.getString("book_author"),
                        rs.getString("book_amount")
                });
            }
            table1.setModel(model);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        wsteczButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();

                Dashboard dashboard = null;
                try {
                    dashboard = new Dashboard(user);
                    dashboard.setVisible(true);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        wypozyczButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table1.getSelectedRow();
                String cell = table1.getModel().getValueAt(row, 0).toString();
                int bookId = Integer.parseInt(cell);

                String rentSql = "INSERT INTO rent (user_id, book_id, rent_date, return_date) VALUES (?, ?, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 14 DAY))";
                String updateBookSql = "UPDATE book SET book_amount = book_amount - 1 WHERE id = ?";
                String updateUserBooksSql = "UPDATE users SET books = CONCAT(IFNULL(books, ''), ?) WHERE id = ?";

                try (Connection conn = Database.getConnection();
                     PreparedStatement rentStatement = conn.prepareStatement(rentSql);
                     PreparedStatement updateBookStatement = conn.prepareStatement(updateBookSql);
                     PreparedStatement updateUserBooksStatement = conn.prepareStatement(updateUserBooksSql)) {

                    rentStatement.setInt(1, user.getId());
                    rentStatement.setInt(2, bookId);
                    rentStatement.executeUpdate();

                    updateBookStatement.setInt(1, bookId);
                    updateBookStatement.executeUpdate();

                    // Dodaj informację o wypożyczeniu książki do kolumny "books" w tabeli "users"
                    String bookInfo = bookId + ": " + java.sql.Date.valueOf(LocalDate.now().plusDays(14));
                    updateUserBooksStatement.setString(1, ", " + bookInfo);
                    updateUserBooksStatement.setInt(2, user.getId());
                    updateUserBooksStatement.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Wypożyczono książkę");
                } catch (SQLException ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
            }
        });
    }
}