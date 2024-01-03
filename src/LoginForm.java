
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginForm extends JDialog{
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
        LoginForm loginForm = new LoginForm(null);
        loginForm.setVisible(true);

        User user = loginForm.user;
        if (user != null){
            System.out.println("Successful Authentication of " + user.name);
            System.out.println("\t\tEmail: " + user.email);
            System.out.println("\t\tPhone: " + user.phone);
            System.out.println("\t\tAddress: " + user.address);
        }else{
            System.out.println("Authentication canceled");
        }
    }

    LoginForm(JFrame parent){
        super(parent);
        setTitle("Login form");
        this.setContentPane(JPanel1);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        int width = 800, height = 600;
        setModal(true);
        setMinimumSize(new Dimension(width, height));
        setLocationRelativeTo(parent);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = tfEmail.getText();
                String password = String.valueOf(tfPassword.getPassword());

                user = getAutenticateUer(email, password);

                if (user != null){
                    dispose();
                    Dashboard dashboard = new Dashboard(null);
                    dashboard.setVisible(true);
                }else{
                    JOptionPane.showMessageDialog(LoginForm.this,
                            "Email or password invalid",
                            "Try again",
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
    private User getAutenticateUer(String email, String password) {
        User user = null;

        final String DB_URL = "jdbc:mysql://localhost/MyStore?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM users WHERE email=? AND password=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                user = new User();
                user.name = resultSet.getString("name");
                user.email = resultSet.getString("email");
                user.phone = resultSet.getString("phone");
                user.address = resultSet.getString("address");
                user.password = resultSet.getString("password");
            }
            stmt.close();
            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }
}
