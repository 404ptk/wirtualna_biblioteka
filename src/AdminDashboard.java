import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.*;

public class AdminDashboard extends JFrame{
    private JPanel JPanel1;
    private JTable table1;
    private JButton closeButton;
    private JButton edytujButton;
    private JButton jOddaj;
    private JPanel close;
    private JLabel jDane;
    private JLabel jDane2;
    private JLabel jDane3;
    private JButton ksiazkiButton;
    private JButton wylogujButton;
    private JLabel jOsoba;

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

        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = new DefaultTableModel(new String[]{
                    "Imię i nazwisko",
                    "Mail",
                    "Wypożyczone książki",
                    "Zalegające książki"
            }, 0);

            while(rs.next()){
                model.addRow(new String[]{
                        rs.getString("name"),
                        rs.getString("mail"),
                        rs.getString("books")
                });
            }
            table1.setModel(model);
        }catch(Exception ex){
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
}

