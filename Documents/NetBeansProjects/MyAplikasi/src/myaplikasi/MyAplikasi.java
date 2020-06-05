/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myaplikasi;

import javax.swing.JFrame;

/**
 *
 * @author ASUS
 */
public class MyAplikasi extends JFrame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        JFrame julius = new Biasa();
        julius.setTitle("\t Kalkulator By Candra Julius Sinaga");
        julius.setVisible(true);
        julius.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        julius.setSize(400,400);
    }
    
}
