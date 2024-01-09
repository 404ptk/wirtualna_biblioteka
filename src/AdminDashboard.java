import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class AdminDashboard extends JDialog{
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
        setModal(true);
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

        Database.getConnection();
    }
}

