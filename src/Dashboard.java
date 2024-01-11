import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Dashboard extends JDialog{
    private JPanel JPanel1;
    private JButton wypozyczButton;
    private JButton edytujButton;
    private JButton closeButton;
    private JPanel close;
    private JTable table1;
    private JLabel jDane;

    public static void main(String[] args) {
        Dashboard dashboard = new Dashboard();
        dashboard.setVisible(true);
    }

    public Dashboard(){
        setTitle("Menu ksiegarni");
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
