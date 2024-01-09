import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class Register extends JDialog{
    private JPanel JPanel1;
    private JPanel TextFields;
    private JPanel Buttons;
    private JTextField tfName;
    private JPasswordField JPassword;
    private JButton cancelButton;
    private JButton registerButton;
    private JPanel JPasswor;
    private JPasswordField JPasswordConfirm;

    public static void main(String[] args) {
        Register myForm = new Register();
        myForm.setVisible(true);
        User user = myForm.user;
        if (user != null){
            System.out.println("Udana rejestracja użytkownika: " + user.name);
        }else{
            System.out.println("Rejestracja przerwana.");
        }
    }

    public Register(){
        //super(parent);
        setTitle("Stwórz konto");
        setContentPane(JPanel1);
        int width = 400, height = 500;
        setMinimumSize(new Dimension(width, height));
        setModal(true);
        //setLocationRelativeTo(parent);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        try {
            setIconImage(ImageIO.read(new File("src/icon.png")));
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Wystąpił błąd przy wczytywaniu ur.png.");
        }

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
                dispose();

                LoginForm loginForm = new LoginForm();
                loginForm.setVisible(true);
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void registerUser() {
        String name = tfName.getText();
//        String email = tfEmail.getText();
//        String phone = tfPhone.getText();
//        String address = tfAddress.getText();
        String password = String.valueOf(JPassword.getPassword());
        String confirmpassword = String.valueOf(JPasswordConfirm.getPassword());

        if (name.isEmpty() || password.isEmpty()){
            JOptionPane.showMessageDialog(this,
                    "Uzupełnij wszystkie pola",
                    "Spróbuj ponownie",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!password.equals(confirmpassword)){
            JOptionPane.showMessageDialog(this,
                    "Uzupełnij wszystkie pola",
                    "Spróbuj ponownie",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        user = addUserToDatabase(name, password);
        if (user != null){
            dispose();
        }else{
            JOptionPane.showMessageDialog(this,
                    "Niepowodzenie w rejestracji użytkownika",
                    "Spróbuj ponownie",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public User user;
    private User addUserToDatabase(String name, String password) {
        User user = null;

        // sprawdzenie polaczenia do bazy danych
        final String DB_URL = "jdbc:mysql://localhost/MyStore?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO users (name, password) VALUES (?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);

            //insert row into the table
            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0){
                user = new User();
                user.name = name;
                user.password = password;
            }
            //close connection
            stmt.close();
            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return user;
    }
}
