
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
                            dashboard = new Dashboard();
                            dashboard.setVisible(true);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        System.out.println("Zalogowano jako: " + user.name + " " + user.surname);
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
            }
        });

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
                user = new User();
                user.mail = resultSet.getString("mail");
                user.password = resultSet.getString("password");
                user.id = resultSet.getString("id");
                user.setId(user.id);
            }
            stmt.close();
            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }
}
