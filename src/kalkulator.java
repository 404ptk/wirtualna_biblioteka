import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class kalkulator extends JFrame{
    private JPanel JPanel1;
    private JTextField textFieldA;
    private JTextField textFieldB;
    private JButton sumaButton;
    private JButton ilorazButton;
    private JButton roznicaButton;
    private JButton iloczynButton;
    private JButton clearButton;
    private JButton exitButton;
    private JLabel JLabelScore;

    double valueA, valueB, score;

    public static void main(String[] args) {
        kalkulator Kalkulator = new kalkulator();
        Kalkulator.setVisible(true);
    }
    public kalkulator() {
        super("Moje pierwsze GUI");
        this.setContentPane(this.JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 400, height = 300;
        this.setSize(width, height);

        sumaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                valueA = Double.parseDouble(textFieldA.getText());
                valueB = Double.parseDouble(textFieldB.getText());
                score = valueA + valueB;
                JLabelScore.setText("Wynik " + valueA + " + " + valueB + " = " + score);
            }
        });
        roznicaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                valueA = Double.parseDouble(textFieldA.getText());
                valueB = Double.parseDouble(textFieldB.getText());
                score = valueA - valueB;
                JLabelScore.setText("Wynik " + valueA + " - " + valueB + " = " + score);
            }
        });
        iloczynButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                valueA = Double.parseDouble(textFieldA.getText());
                valueB = Double.parseDouble(textFieldB.getText());
                score = valueA * valueB;
                JLabelScore.setText("Wynik " + valueA + " * " + valueB + " = " + score);
            }
        });
        ilorazButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                valueA = Double.parseDouble(textFieldA.getText());
                valueB = Double.parseDouble(textFieldB.getText());
                score = valueA / valueB;
                if (valueB != 0){
                    JLabelScore.setText("Wynik " + valueA + " / " + valueB + " = " + score);
                }else{
                    JLabelScore.setText("Wynik " + valueA + " / " + valueB + " = Nie mozna dzielic przez zero!");
                }

            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JLabelScore.setText("Wynik dzialania ....");
                textFieldA.setText("");
                textFieldB.setText("");
            }
        });
    }


}
