import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class menu extends JFrame{
    private JPanel JPanel1;
    private JButton kalkulatorButton;
    private JButton rejestracjaButton;
    private JButton wyjścieButton;

    public static void main(String[] args) {
        menu Menu = new menu();
        Menu.setVisible(true);
    }

    public menu(){
        super("Wirtualna Biblioteka");
        this.setContentPane(this.JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 800, height = 600;
        this.setSize(width, height);
        setLocationRelativeTo(null);

        wyjścieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        kalkulatorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

    }


}
