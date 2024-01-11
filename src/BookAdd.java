import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class BookAdd extends JFrame{
    private JPanel JKsiazki;
    private JTable table1;
    private JButton closeButton;
    private JButton wsteczButton;
    private JButton jDodaj;
    private JTextField tfBookAmount;
    private JTextField tfBookAuthor;
    private JTextField tfBookName;
    private JButton jWyjdz;
    private JPanel JBookAdd;

    public static void main(String[] args) throws SQLException {
        BookAdd bookAdd = new BookAdd();
        bookAdd.setVisible(true);
        BookA book = bookAdd.book;
        if (book != null){
            System.out.println("Dodano książke: " + book.book_name);
        }else{
            System.out.println("Przerwano dodawanie książki.");
        }
    }

    public BookAdd() throws SQLException {
        setTitle("Dodawanie książki");
        this.setContentPane(this.JBookAdd);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        int width = 800, height = 600;
        setMinimumSize(new Dimension(width, height));
        setLocationRelativeTo(null);

        try {
            setIconImage(ImageIO.read(new File("src/icon.png")));
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Wystąpił błąd przy wczytywaniu ur.png.");
        }


        jDodaj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBook();
                System.out.println("Dodano ksiazke");
                dispose();

                AdminBiblioteka adminBiblioteka = null;
                try {
                    adminBiblioteka = new AdminBiblioteka();
                    adminBiblioteka.setVisible(true);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        jWyjdz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    private void addBook(){
        String book_name = tfBookName.getText();
        String book_author = tfBookAuthor.getText();
        int book_amount = Integer.parseInt(tfBookAmount.getText().trim());

        if (book_name.isEmpty() || book_author.isEmpty()){
            JOptionPane.showMessageDialog(this,
                    "Uzupełnij wszystkie pola," +
                            "Spróbuj ponownie");
            return;
        }
        if (book_amount == 0){
            book_amount = 1;
        }

        book = addBookToDatabase(book_name, book_author, book_amount);
        if (book != null){
            dispose();
        }else{
            JOptionPane.showMessageDialog(this,
                    "Niepowodzenie w dodawaniu książki," +
                            "Spróbuj ponownie");
        }

    }

    public BookA book;
    private BookA addBookToDatabase(String book_name, String book_author, int book_amount){
        BookA book = null;

        try{
            Connection connection = Database.getConnection();
            String sql = "INSERT INTO book (book_name, book_author, book_amount) VALUES (?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, book_name);
            ps.setString(2, book_author);
            ps.setString(3, String.valueOf(book_amount));

            int addedRows = ps.executeUpdate();
            if (addedRows > 0){
                book = new BookA();
                book.book_name = book_name;
                book.book_author = book_author;
                book.book_amount = book_amount;
            }
            connection.close();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return book;
    }
}




