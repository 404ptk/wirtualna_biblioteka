import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDashboard extends JDialog{
    private JPanel JPanel1;
    private JButton wypozyczButton;
    private JButton edytujButton;
    private JTable table1;
    private JPanel close;
    private JButton closeButton;

    public static void main(String[] args) {
        AdminDashboard admindashboard = new AdminDashboard(null);
        admindashboard.setVisible(true);
    }

    public AdminDashboard(JFrame parent){
        super(parent);
        setTitle("Dashboard");
        this.setContentPane(JPanel1);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        int width = 800, height = 600;
        setModal(true);
        setMinimumSize(new Dimension(width, height));
        setLocationRelativeTo(parent);

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginForm loginForm = new LoginForm(null);

            }
        });
    }
}

