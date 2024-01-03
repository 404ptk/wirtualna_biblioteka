import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class counter extends JFrame{
    private JPanel JPanel1;
    private JButton liczButton;
    private JButton wsteczButton;
    private JTextField aTextField;
    private JLabel JLabelLitery;
    private JLabel JLabelSlowa;

    public static void main(String[] args) {
        counter Counter = new counter();
        Counter.setVisible(true);
    }

    public counter(){
        super("Licznik słów oraz liter");
        this.setContentPane(this.JPanel1);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        int width = 600, height = 400;
        this.setSize(width, height);

        liczButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int len = aTextField.getText().length();
                JLabelLitery.setText("Ilość liter w tekście: " + len);

                String text = aTextField.getText();
                String[] words = text.split("\\s+");
                JLabelSlowa.setText("Ilość słów w tekście: " + words.length);
            }
        });

        wsteczButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
