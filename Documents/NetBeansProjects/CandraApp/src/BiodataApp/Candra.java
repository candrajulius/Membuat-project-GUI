/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BiodataApp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author ASUS
 */
public class Candra extends JFrame {

private static final int Frame_Width = 400;
private static final int Frame_Height = 400;
private JLabel label1;
private JLabel label2;
private JLabel label3;
private JTextField text1;
private JTextField text2;
private JTextField text3;
private JPanel panel;
private JButton tombol;
    private JButton tombol1;
    public Candra(){
        BuatField();
        BuatTombol();
        BuatPanel();
    }
    private void BuatField(){
       label1 = new JLabel();
       label1.setText("Nilai A ");
       label2 = new JLabel();
       label2.setText("Nilai B ");
       label3 = new JLabel("Hasil ");
       label3.setText("Hasil ");
       final int Width = 10;
       text1 = new JTextField(10);
       text2 = new JTextField(10);
    }
    private void BuatTombol(){
        tombol = new JButton("Hasil");
        tombol1 = new JButton("Hapus");
        class listener1 implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent event)
            {
               text1.setText("");
               text2.setText("");
               label3.setText("");
               
            }
        }
        ActionListener lst = new listener1();
            tombol1.addActionListener(lst);
            
        class AddInterestListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent event)
            {
                if (!text1.getText().isEmpty() && !text2.getText().isEmpty()) {
                    
                
               double candra =  Double.valueOf(text1.getText());
               double julius = Double.valueOf(text2.getText());
               double sinaga = candra * julius;
             label3.setText("Hasil " + sinaga);
                }else{
                    
                }
            }
        }
        ActionListener listener = new AddInterestListener();
        tombol.addActionListener(listener);
    }
    private void BuatPanel(){
        panel = new JPanel();
        panel.add(label1);
        panel.add(text1);
        panel.add(label2);
        panel.add(text2);
        panel.add(label3);
        panel.add(tombol);
        panel.add(tombol1);
        add(panel);
    }
}

