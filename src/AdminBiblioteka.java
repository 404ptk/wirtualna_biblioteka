import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AdminBiblioteka extends JFrame{
    private JPanel JPanel1;
    private JTable table1;
    private JButton closeButton;
    private JButton wsteczButton;

    public static void main(String[] args) throws SQLException {
        AdminDashboard admindashboard = new AdminDashboard();
        admindashboard.setVisible(true);
    }

    public AdminBiblioteka(){
        setTitle("Księgarnia");
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
    }
}



