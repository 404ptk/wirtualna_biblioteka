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

public class Register extends JFrame{
    private JPanel JPanel1;
    private JPanel TextFields;
    private JPanel Buttons;
    private JTextField tfImie;
    private JPasswordField JPassword;
    private JButton cancelButton;
    private JButton registerButton;
    private JPanel JPasswor;
    private JPasswordField JPasswordConfirm;
    private JTextField tfNazwisko;
    private JTextField tfMail;

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
        setTitle("Rejestracja");
        this.setContentPane(this.JPanel1);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        int width = 800, height = 600;
        setMinimumSize(new Dimension(width, height));
        setLocationRelativeTo(null);

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
                menu Menu = new menu();
                Menu.setVisible(true);
            }
        });
        getRootPane().setDefaultButton(registerButton);
    }

    private void registerUser() {
        String name = tfImie.getText();
        String surname = tfNazwisko.getText();
        String mail = tfMail.getText();
        String password = String.valueOf(JPassword.getPassword());
        String confirmpassword = String.valueOf(JPasswordConfirm.getPassword());

        if (name.isEmpty() || surname.isEmpty() || mail.isEmpty() || password.isEmpty() || confirmpassword.isEmpty()){
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

        user = addUserToDatabase(name, surname, mail, password);
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
    private User addUserToDatabase(String name, String surname, String mail, String password) {
        User user = null;

        // sprawdzenie polaczenia do bazy danych
        final String DB_URL = "jdbc:mysql://localhost/MyStore?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO users (name, surname, mail, password) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, mail);
            preparedStatement.setString(4, password);

            //insert row into the table
            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0){
                user = new User();
                user.name = name;
                user.surname = surname;
                user.mail = mail;
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
