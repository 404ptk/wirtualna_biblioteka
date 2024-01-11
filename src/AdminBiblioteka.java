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

public class AdminBiblioteka extends JFrame{
    private JPanel JKsiazki;
    private JTable table1;
    private JButton closeButton;
    private JButton wsteczButton;
    private JButton jDodaj;
    private JButton jUsun;

    public static void main(String[] args) throws SQLException {
        AdminBiblioteka adminBiblioteka = new AdminBiblioteka();
        adminBiblioteka.setVisible(true);
    }

    Connection connection = Database.getConnection();
    public AdminBiblioteka() throws SQLException {
        setTitle("Księgarnia");
        this.setContentPane(this.JKsiazki);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        int width = 800, height = 600;
        setMinimumSize(new Dimension(width, height));
        setLocationRelativeTo(null);
        try {
            setIconImage(ImageIO.read(new File("src/icon.png")));
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Wystąpił błąd przy wczytywaniu ur.png.");
        }


        String sql = "SELECT * FROM book";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = new DefaultTableModel(new String[]{
                    "Id",
                    "Nazwa książki",
                    "Autor",
                    "Ilość książek"
            }, 0);
            while(rs.next()){
                model.addRow(new String[]{
                        rs.getString("id"),
                        rs.getString("book_name"),
                        rs.getString("book_author"),
                        rs.getString("book_amount")
                });
            }
            table1.setModel(model);
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();

            }
        });
        wsteczButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();

                AdminDashboard adminDashboard = null;
                try {
                    adminDashboard = new AdminDashboard();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                adminDashboard.setVisible(true);
            }
        });
        jDodaj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    dispose();
                    BookAdd bookAdd = new BookAdd();
                    bookAdd.setVisible(true);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        //table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jUsun.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table1.getSelectedRow();
                //System.out.println(row);
                String cell = table1.getModel().getValueAt(row, 0).toString();
                //String ksiazka = "SELECT FROM book WHERE id=" + cell + " SELECT book_name";
                String sql = "DELETE FROM book WHERE id=" + cell;
                try{
                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.execute();

                    updateTable();
                    JOptionPane.showMessageDialog(null, "Usunięto.");
                    System.out.println("Usunięto książkę ");


                } catch (SQLException ex) {
                    System.out.println("Error: " + ex);
                }
//                finally {
//                    try{
//
//                    }catch(Exception e){
//
//                    }
//                }
            }
        });

    }
    private void updateTable(){
        String sql = "SELECT * FROM book";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
//        finally {
//            try{
//
//            }catch (Exception e){
//
//            }
//        }
    }
}



