import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminBiblioteka extends JFrame{
    private JPanel JKsiazki;
    private JTable table1;
    private JButton closeButton;
    private JButton wsteczButton;
    private JButton jDodaj;
    private JButton jUsun;

    public static void main(String[] args) throws SQLException {
        AdminBiblioteka adminBiblioteka = new AdminBiblioteka();
        adminBiblioteka.setVisible(true);
    }

    public AdminBiblioteka() throws SQLException {
        setTitle("Księgarnia");
        this.setContentPane(this.JKsiazki);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        int width = 800, height = 600;
        setMinimumSize(new Dimension(width, height));
        setLocationRelativeTo(null);

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

                AdminDashboard adminDashboard = null;
                try {
                    adminDashboard = new AdminDashboard();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                adminDashboard.setVisible(true);
            }
        });
        jDodaj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    BookAdd bookAdd = new BookAdd();
                    bookAdd.setVisible(true);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}



