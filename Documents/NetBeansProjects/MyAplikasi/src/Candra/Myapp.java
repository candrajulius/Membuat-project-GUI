/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyApp.Candra;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author ASUS
 */
public class Myapp extends JFrame {
    public static void main(String[] args) {
        JFrame candra = new Myapp();
        candra.setTitle("Kalkulator By Candra Julius Sinaga ");
        candra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        candra.setVisible(true);
    }
   private final int Frame_Width = 600;
    private final int Frame_Height = 600;
    private JLabel bilangan1;
    private JLabel bilangan2;
    private JLabel operator;
    private JLabel hasil;
    private JPanel candra;
    private JTextField text1;
    private JTextField text2;
    private JTextField Operator1;
    private JTextField hasilText;
    private JButton tombol;
    private JPanel panel;
    
    public Myapp(){
        BuatField();
        BuatTombol();
        BuatPanel();
        setSize(Frame_Width,Frame_Width);
    }
    private  void BuatField(){
        bilangan1 = new JLabel("Bilangan 1 ");
        operator = new JLabel("*,/,+,-");
        bilangan2 = new JLabel("Bilangan 2 ");
        hasil = new JLabel("Hasil ");
         final int Width = 10;
        text1 = new JTextField(Width);
        Operator1 = new JTextField(Width);
        text2 = new JTextField(Width);
        hasilText = new JTextField(Width);
    }
    private void BuatTombol(){
        tombol = new JButton("Tekant tombol ");
        tombol.setSize(10,20);

         class listener implements ActionListener {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
                double candra = Double.valueOf(text1.getText());
                String operator = String.valueOf(Operator1.getText());
                double julius = Double.valueOf(text2.getText());
                    if (operator.equalsIgnoreCase("*")) {
                        double hasil = candra * julius;
                        hasilText.setText(hasil + "");
                    } else if (operator.equalsIgnoreCase("+")) {
                        double hasil = candra + julius;
                        hasilText.setText(hasil + "");
                    } else if (operator.equalsIgnoreCase("-")) {
                        double hasil = candra - julius;
                        hasilText.setText(hasil + "");
                    } else if (operator.equalsIgnoreCase("/")) {
                        double hasil = candra / julius;
                        hasilText.setText(hasil + "");
                    } else {
                        JOptionPane.showMessageDialog(null, "Anda Salah Input Operator \n " +
                                "Form anda ada yang kosong ");
                    }
            }
        }
        ActionListener candra = new listener();
         tombol.addActionListener(candra);
    }
    private void BuatPanel(){
        panel = new JPanel();
        panel.add(bilangan1);
        panel.add(text1);
        panel.add(operator);
        panel.add(Operator1);
        panel.add(bilangan2);
        panel.add(text2);
        panel.add(hasil);
        panel.add(hasilText);
        panel.add(tombol);
        add(panel);
    }
    
}
