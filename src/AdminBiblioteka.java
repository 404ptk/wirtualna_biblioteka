import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminBiblioteka extends JDialog{
    private JPanel JPanel1;
    private JTable table1;
    private JButton closeButton;
    private JButton wsteczButton;

    public static void main(String[] args) {
        AdminDashboard admindashboard = new AdminDashboard();
        admindashboard.setVisible(true);
    }

    public AdminBiblioteka(){
        setTitle("Księgarnia");
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
        wsteczButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();

                AdminDashboard adminDashboard = new AdminDashboard();
                adminDashboard.setVisible(true);
            }
        });
    }
}



