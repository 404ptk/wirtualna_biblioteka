import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard extends JDialog{
    private JPanel JPanel1;
    private JButton wypozyczButton;
    private JButton edytujButton;
    private JButton closeButton;
    private JPanel close;
    private JTable table1;

    public static void main(String[] args) {
        Dashboard dashboard = new Dashboard();
        dashboard.setVisible(true);
    }

    public Dashboard(){
        //super(parent);
        setTitle("Menu biblioteki");
        this.setContentPane(JPanel1);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        int width = 800, height = 600;
        setModal(true);
        setMinimumSize(new Dimension(width, height));
        //setLocationRelativeTo(parent);
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

                Biblioteka biblioteka = new Biblioteka();
                biblioteka.setVisible(true);
            }
        });
    }
}
