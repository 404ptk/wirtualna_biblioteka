import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class Example extends JFrame{
    private JPanel JPanel1;
    private JButton okokokButton;
    private JButton wyjscieButton;
    private JLabel JLabelText1;
    private JLabel JLabelData;
    private JButton klikButton;

    public static void main(String[] args) {
        Example example = new Example();
        example.setVisible(true);
    }

    public Example(){
        super("Moje pierwsze GUI");
        this.setContentPane(this.JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 400, height = 300;
        this.setSize(width,height);
//        this.pack();

        okokokButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = JOptionPane.showInputDialog("Podaj imię");

                //JOptionPane.showMessageDialog(null, "Witaj " + text);
                JOptionPane.showMessageDialog(null, "Witaj " + text, "Uwaga", JOptionPane.YES_NO_OPTION);
            }
        });

        wyjscieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        klikButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JLabelText1.setText("to jest tekst po zmianie");
                JLabelData.setText(new Date().toString());
            }
        });
    }

}
