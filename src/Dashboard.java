import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Dashboard extends JDialog{
    private JPanel JPanel1;
    private JButton wypozyczButton;
    private JButton edytujButton;
    private JButton closeButton;
    private JPanel close;
    private JTable table1;
    private JLabel jDane;
    private JButton jOddaj;
    private JLabel jDane2;
    private JLabel jDane3;
    private JButton wylogujButton;

    public static User user;


    public Dashboard(User user) throws SQLException {
        Dashboard.user = user;
        setTitle("Wirtualna księgarnia");

        this.setContentPane(JPanel1);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        int width = 800, height = 600;
        setModal(true);
        setMinimumSize(new Dimension(width, height));
        setLocationRelativeTo(null);

        try {
            setIconImage(ImageIO.read(new File("src/icon.png")));
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Wystąpił błąd przy wczytywaniu ur.png.");
        }

        Connection connection = Database.getConnection();
        try{
            String sql = "SELECT * FROM users WHERE id=" + user.getId();
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rst = pst.executeQuery();
            rst.next();

            DefaultTableModel model = new DefaultTableModel(new String[]{
                    "Nazwa książki",
                    "Autor",
                    "Data wypożyczenia",
                    "Data oddania"
            }, 0);
//            while(rst.next()){
//                model.addRow(new String[]{
//                        rst.getString("id")
//                });
//            }

            jDane.setText("Zalogowany jako:");
            jDane2.setText(user.getName() + " " + user.getSurname());
            jDane3.setText(user.getMail());
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }

        // pokaz ksiazki aktualnego zalogowanego


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
                    biblioteka.setVisible(true);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        jOddaj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        wylogujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                menu Menu = new menu();
                Menu.setVisible(true);
            }
        });
    }
    public static void main(String[] args) throws SQLException {
        Dashboard dashboard = new Dashboard(user);
        dashboard.setVisible(true);
    }
}
