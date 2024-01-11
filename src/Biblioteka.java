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

public class Biblioteka extends JFrame{
    private JPanel JPanel1;
    private JTable table1;
    private JButton wypozyczButton;
    private JButton closeButton;
    private JButton wsteczButton;
    public User user;

    public static void main(String[] args) throws SQLException {
        Biblioteka biblioteka = new Biblioteka();
        biblioteka.setVisible(true);
    }

    public Biblioteka() throws SQLException {
        super("Dostępne książki");
        this.setContentPane(this.JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 800, height = 600;
        this.setSize(width, height);
        setLocationRelativeTo(null);

        try {
            setIconImage(ImageIO.read(new File("src/icon.png")));
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Wystąpił błąd przy wczytywaniu ur.png.");
        }

        Connection connection = Database.getConnection();
        String sql = "SELECT * FROM book";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = new DefaultTableModel(new String[]{
                    "Nazwa książki",
                    "Autor",
                    "Ilość książek"
            }, 0);
            while(rs.next()){
                model.addRow(new String[]{
                        rs.getString("book_name"),
                        rs.getString("book_author"),
                        rs.getString("book_amount")
                });
            }
            table1.setModel(model);
        }catch(Exception e){
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
    }
}
