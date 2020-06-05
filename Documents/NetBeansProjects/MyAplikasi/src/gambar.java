
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ASUS
 */
public class gambar extends JPanel{
    Image bayangan;
    public gambar(){
        bayangan = new ImageIcon(getClass().getResource("/Image/gambar.png")).getImage();
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D d = (Graphics2D)g.create();
        d.drawImage(bayangan, 0, 0, getWidth(),getHeight(),null);
        d.dispose();
    }
}
