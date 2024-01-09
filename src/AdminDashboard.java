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
    private JButton zmienosobeButton;
    private JButton oddajButton;

    public static void main(String[] args) throws SQLException {
        AdminDashboard admindashboard = new AdminDashboard();
        admindashboard.setVisible(true);
    }

    public AdminDashboard() throws SQLException {
        setTitle("Dashboard");
        this.setContentPane(JPanel1);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        int width = 800, height = 600;
        setMinimumSize(new Dimension(width, height));
        setLocationRelativeTo(null);

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
                AdminBiblioteka adminBiblioteka = new AdminBiblioteka();
                adminBiblioteka.setVisible(true);
            }
        });

        Connection connection = Database.getConnection();

        String zapytanie = "SELECT * FROM users";
        try{
            PreparedStatement ps = connection.prepareStatement(zapytanie);
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = new DefaultTableModel(new String[]{
                    "Nazwa użytkownika",
                    "Hasło"
            }, 0);

            while(rs.next()){
                model.addRow(new String[]{
                        rs.getString("name"),
                        rs.getString("password")
                });
            }
            table1.setModel(model);
        }catch(Exception ex){
            System.out.println("Error: " + ex.getMessage());
        }
        //String zapytanie = "SELECT * FROM "
    }
}

