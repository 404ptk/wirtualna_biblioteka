
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.*;

public class LoginForm extends JFrame{
    private JPanel JPanel1;
    private JPanel Buttons;
    private JPanel Login;
    private JTextField tfEmail;
    private JPasswordField tfPassword;
    private JButton okButton;
    private JButton cancelButton;
    private JPanel Email;
    private JPanel Password;

    public static void main(String[] args) {
        LoginForm loginForm = new LoginForm();
        loginForm.setVisible(true);

        User user = loginForm.user;
        if (user != null){
            System.out.println("Udana autoryzacja użytkownika: " + user.name + " " + user.surname);
        }else{
            System.out.println("Logowanie przerwane!");
        }
    }

    LoginForm(){
        setTitle("Logowanie");
        this.setContentPane(JPanel1);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        int width = 800, height = 600;
        setMinimumSize(new Dimension(width, height));
        setLocationRelativeTo(null);

        try {
            setIconImage(ImageIO.read(new File("src/icon.png")));
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Wystąpił błąd przy wczytywaniu icon.png.");
        }

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mail = tfEmail.getText();
                String password = String.valueOf(tfPassword.getPassword());

                user = getAutenticateUser(mail, password);

                if (user != null){
                    if (user.mail.equals("root") && user.password.equals("root")){
                        dispose();
                        try {
                            AdminDashboard adminDashboard = new AdminDashboard();
                            adminDashboard.setVisible(true);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }else{
                        dispose();
                        Dashboard dashboard = null;
                        try {
                            System.out.println("Zalogowano jako: " + user.name + " " + user.surname);
                            dashboard = new Dashboard(user);
                            dashboard.setVisible(true);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(LoginForm.this,
                            "Niepoprawny adres email lub hasło",
                            "Spróbuj ponownie",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                menu Menu = new menu();
                Menu.setVisible(true);
            }
        });

        getRootPane().setDefaultButton(okButton);
    }

    public User user;
    private User getAutenticateUser(String mail, String password) {
        User user = null;

        final String DB_URL = "jdbc:mysql://localhost/MyStore?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM users WHERE mail=? AND password=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, mail);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                user = new User(
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("mail"),
                        resultSet.getString("password"),
                        resultSet.getInt("id")
                );
            }
            stmt.close();
            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }
}
