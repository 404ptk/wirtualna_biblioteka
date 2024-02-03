import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class menu extends JFrame{
    private JPanel JPanel1;
    private JButton logowanieButton;
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

        try {
            setIconImage(ImageIO.read(new File("src/icon.png")));
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Wystąpił błąd przy wczytywaniu ur.png.");
        }

        wyjścieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });


        logowanieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();

                LoginForm loginForm = new LoginForm();
                loginForm.setVisible(true);
            }
        });
        rejestracjaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();

                Register register = new Register();
                register.setVisible(true);
            }
        });
    }


}
