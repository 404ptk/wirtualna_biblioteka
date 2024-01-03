import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Biblioteka extends  JFrame{
    private JPanel JPanel1;
    private JTable table1;
    private JButton wypozyczButton;
    private JButton closeButton;

    public static void main(String[] args) {
        Biblioteka biblioteka = new Biblioteka();
        biblioteka.setVisible(true);
    }

    public  Biblioteka(){
        super("Dostępne książki");
        this.setContentPane(this.JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 800, height = 600;
        this.setSize(width, height);
        setLocationRelativeTo(null);
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
