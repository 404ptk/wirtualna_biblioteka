import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AdminDashboard extends JFrame{
    private JPanel JPanel1;
    private JButton wypozyczButton;
    private JButton edytujButton;
    private JTable table1;
    private JPanel close;
    private JButton closeButton;
    private JButton ksiazkiButton;
    private JButton usunButton;
    private JButton oddajButton;
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

        Connection connection = Database.getConnection();
        String sql = "SELECT * FROM users";
        try{
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rst = pst.executeQuery();
            rst.next();

            String imie = rst.getString("name");
            String nazwisko = rst.getString("surname");
            jOsoba.setText("Zalogowany jako: " + imie + " " + nazwisko);
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
                    "Wypożyczone książki"
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
    }
}

