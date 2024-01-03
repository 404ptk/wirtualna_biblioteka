import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class Conventer extends JFrame{
    private JPanel JPanel1;
    private JTextField textField1;
    private JButton celcjuszaButton;
    private JButton fahrenheitaButton;
    private JButton zamieńStopnieButton;
    private JLabel JLabelStopnie;
    private JLabel Wynik;
    private JButton wsteczButton;
    double value, celcjusz, fahrenheit;
    boolean check=true;
    private static final DecimalFormat df = new DecimalFormat("0.0");

    public static void main(String[] args) {
        Conventer conventer = new Conventer();
        conventer.setVisible(true);
    }

    public Conventer(){
        super("Konwenter stopniowy C/F");
        this.setContentPane(this.JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 400, height = 300;
        this.setSize(width, height);

        fahrenheitaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JLabelStopnie.setText("Podaj ilość stopni Fahrenheita");
                check = false;
            }
        });

        celcjuszaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JLabelStopnie.setText("Podaj ilość stopni Celcjusza");
                check = true;
            }
        });

        zamieńStopnieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    value = Double.parseDouble(textField1.getText());
                }catch (NumberFormatException e1){
                    System.out.println("Nie ma podanej liczby / uzyto przecinka zamiast kropki");
                    textField1.setText("Podaj liczbe");
                }
                celcjusz = (value * (9.0/5.0) + 32);
                fahrenheit = (value - 32) * (5.0/9.0);
                if (check == true){
                    Wynik.setText(value + " stopni Celcjusza wynosi " + df.format(celcjusz) + " stopni w skali Fahrenheita");
                }else{
                    Wynik.setText(value + " stopni Fahrenheita wynosi " + df.format(fahrenheit) + " stopni w skali Celcjusza");
                }

            }
        });
    }
}
