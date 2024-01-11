import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Dashboard extends JDialog{
    private JPanel JPanel1;
    private JButton wypozyczButton;
    private JButton edytujButton;
    private JButton closeButton;
    private JPanel close;
    private JTable table1;
    private JLabel jDane;

    public User user;
    public static void main(String[] args) throws SQLException {
        Dashboard dashboard = new Dashboard();
        dashboard.setVisible(true);

    }

    public Dashboard() throws SQLException {
        setTitle("Menu ksiegarni");
        this.setContentPane(JPanel1);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        int width = 800, height = 600;
        setModal(true);
        setMinimumSize(new Dimension(width, height));
        setLocationRelativeTo(null);

        Connection connection = Database.getConnection();
        try{
            String sql = "SELECT * FROM users WHERE id=" + user.getId();
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rst = pst.executeQuery();
            rst.next();

            jDane.setText("Zalogowany jako: " + user.getName() + " " + user.getSurname());
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }

        // pokaz ksiazki aktualnego zalogowanego


        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginForm loginForm = new LoginForm();

            }
        });
        wypozyczButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();

                Biblioteka biblioteka = null;
                try {
                    biblioteka = new Biblioteka();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                biblioteka.setVisible(true);
            }
        });
    }
}
