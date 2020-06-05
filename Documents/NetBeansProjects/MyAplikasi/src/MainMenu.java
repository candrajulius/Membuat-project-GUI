
import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.String;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.spi.DirStateFactory.Result;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static javax.swing.UIManager.getString;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import static jdk.nashorn.internal.objects.NativeString.toLowerCase;

public class MainMenu extends javax.swing.JFrame implements Runnable {
   

    /**
     * Creates new form MainMenu
     */
    int mousex,mousey;
    
    
    public MainMenu() {
        initComponents();
        ContentPanel.removeAll();
        ContentPanel.repaint();
        ContentPanel.revalidate();
        
        // add panel
        Thread t = new Thread(this);
        t.start();
        ContentPanel.add(homePanel);
        ContentPanel.repaint();
        ContentPanel.revalidate();
        
        // menampilkan data mahasiswa di tabel
        tampilkanDua();
        // menampilkan data admin di tabel
        tampilAdmin();
        // menampilkan data buku di tabel
        tampilBuku();
        
      
    }
     
   
    public boolean NamaAdmin(String nama) throws ClassNotFoundException{
        boolean check = false;
        PreparedStatement st;
        ResultSet ds;
        String query = "SELECT * FROM `admin` WHERE `nama` =?";
        
        try {
            st = koneksi.getConnection().prepareStatement(query);
            st.setString(1, nama);
            ds = st.executeQuery();
            if (ds.next()) {
                check = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
       return check;
    }
    public boolean penulis (String penulis){
       PreparedStatement st;
       ResultSet ds;
       boolean check = false;
      String query = "SELECT * FROM `buku` WHERE `penulis` =?";
        try {
            st = koneksi.getConnection().prepareStatement(query);
            st.setString(1, penulis);
            ds = st.executeQuery();
        if (ds.next()) {
            check = true;
        }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }
    public boolean Nama(String nama){
       boolean check = false;
       PreparedStatement st;
       ResultSet ds;
       String query= "SELECT * FROM `mahasiswa` WHERE `Nama` =?";
        try {
            st = koneksi.getConnection().prepareStatement(query);
            st.setString(1, nama);
            ds = st.executeQuery();
            if (ds.next()) {
                check = true;
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
       return check;
    }
  
        
    
    public boolean tabelTampil(){
        DefaultTableModel model = new DefaultTableModel();
       boolean check = false;
    model.addColumn("id");
    model.addColumn("NIS");
    model.addColumn("Nama");
    model.addColumn("Jurusan");
    model.addColumn("Jenis Kelamin");
    model.addColumn("Kelahiran");
    model.addColumn("Alamat");
        int jumlahTabel = tabelMahasiswa.getRowCount();
        for (int i = 0; i < jumlahTabel; i++) {           
        }
        String cari = cariMahasiswa.getText();
        try {
            ResultSet rs;
            String query = "SELECT * FROM mahasiswa WHERE Nama like '%"+cari+"%'";     
            PreparedStatement st = koneksi.getConnection().prepareStatement(query);
//            st.setString(1, cari + "%");
             rs = st.executeQuery(query);
             int no = 1;
            while(rs.next()){
                model.addRow(new Object[]{
                    rs.getInt("id"),rs.getString("NIS"),rs.getString("Nama"),rs.getString("Jurusan"),rs.getString("kelamin"),
                    rs.getString("kelahiran"),rs.getString("alamat")
                });
                no++;
                check = true;
            }
            tabelMahasiswa.setModel(model);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }
    public void kosongAdmin(){
        madu.setText("");
        madu.setText("");
        nomor1.setText("");
        profesi.setSelectedItem("Pilih");
        bidang.setSelectedItem("Pilih");
        telepon.setText("");
        alamat.setText("");
        kelahiran.setDate(null);
    }
    public void kosongBuku(){
        penulis1.setText("");
        judul.setText("");
        penerbit.setText("");
        kota.setText("");
        tahun.setText("");
        kode.setText("");
    }
    public void tampilBuku(){
        DefaultTableModel buku = new DefaultTableModel();
        buku.addColumn("kode buku");
        buku.addColumn("Penulis");
        buku.addColumn("Penerbit");
        buku.addColumn("Kota Terbit");
        buku.addColumn("Tahun Terbit");
        buku.addColumn("Judul Buku");
        tabelBuku.setModel(buku);
        String query = "SELECT * FROM `buku`";
        PreparedStatement st;
        ResultSet ds;
        try {
            st = koneksi.getConnection().prepareStatement(query);
            ds = st.executeQuery();
            while(ds.next()){
                buku.addRow(new Object[]{
                    ds.getInt("KodeBuku"),ds.getString("penulis"),ds.getString("penerbit"),
                    ds.getString("Kota"),ds.getInt("tahunTerbit"),ds.getString("judulBuku")
                });
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
   public void kosong(){
       id.setText("");
       Nis.setText("");
       nama.setText("");
       jurusan.setSelectedItem("Pilih");
       jk.setSelectedItem("Pilih");
       date1.setDate(null);
       alamat1.setText("");
   }
   public void tampilAdmin(){
       DefaultTableModel admin = new DefaultTableModel();
       admin.addColumn("nomor");
       admin.addColumn("Nama Dosen");
       admin.addColumn("Profesi");
       admin.addColumn("Bidang");
       admin.addColumn("No Telepon");
       admin.addColumn("Kelahiran");
       admin.addColumn("Alamat");
       tabelAdmin.setModel(admin);
       String query = "SELECT * FROM `admin`";
       PreparedStatement st;
       ResultSet rs;
        try {
            st = koneksi.getConnection().prepareStatement(query);
            rs = st.executeQuery();
            while(rs.next()){
                admin.addRow(new Object[]{
                    rs.getInt("id"),rs.getString("nama"),rs.getString("profesi"),rs.getString("bidang"),rs.getString("telepon"),
                    rs.getString("kelahiran"),rs.getString("Alamat")
                });
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
       
   }
public void tampilkanDua(){
    
    DefaultTableModel kamu = new DefaultTableModel();
    kamu.addColumn("id");
    kamu.addColumn("NIS");
    kamu.addColumn("Nama");
    kamu.addColumn("Jurusan");
    kamu.addColumn("Jenis Kelamin");
    kamu.addColumn("Kelahiran");
    kamu.addColumn("Alamat");
    tabelMahasiswa.setModel(kamu);
    String query = "SELECT * FROM `mahasiswa`";
    PreparedStatement st;
    
    ResultSet res;
        try {
            st = koneksi.getConnection().prepareStatement(query);
            res =  st.executeQuery(query);
            while(res.next()){
                kamu.addRow(new Object[]{
                    res.getInt("id"),res.getString("NIS"),res.getString("Nama"),res.getString("Jurusan"),res.getString("kelamin"),res.getString("kelahiran"),res.getString("alamat")
                });
         
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BodyPanel = new javax.swing.JPanel();
        HeaderPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        SidebarPanel = new javax.swing.JPanel();
        Mahasiswa = new javax.swing.JButton();
        Buku = new javax.swing.JButton();
        Admin = new javax.swing.JButton();
        Transaksi = new javax.swing.JButton();
        Menu = new javax.swing.JLabel();
        Login = new javax.swing.JButton();
        Mahasiswa1 = new javax.swing.JButton();
        ContentPanel = new javax.swing.JPanel();
        mahasiswaPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        hhh = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        nama = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jurusan = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jk = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        date1 = new com.toedter.calendar.JDateChooser();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        alamat1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        edit = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        id = new javax.swing.JTextField();
        NIs = new javax.swing.JLabel();
        Nis = new javax.swing.JTextField();
        cariMahasiswa = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelMahasiswa = new javax.swing.JTable();
        refresh = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        BukuPanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        penulis1 = new javax.swing.JTextField();
        judul = new javax.swing.JTextField();
        penerbit = new javax.swing.JTextField();
        kota = new javax.swing.JTextField();
        tahun = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        kode = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelBuku = new javax.swing.JTable();
        mana = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        homePanel = new javax.swing.JPanel();
        siapa = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        komentar = new javax.swing.JTextArea();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        time = new javax.swing.JLabel();
        hari = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        AdminPanel = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        madu = new javax.swing.JTextField();
        telepon = new javax.swing.JTextField();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        alamat = new javax.swing.JTextArea();
        kelahiran = new com.toedter.calendar.JDateChooser();
        profesi = new javax.swing.JComboBox<>();
        bidang = new javax.swing.JComboBox<>();
        nomor = new javax.swing.JLabel();
        nomor1 = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelAdmin = new javax.swing.JTable();
        cariData = new javax.swing.JTextField();
        jButton15 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        TransaksiPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jButton21 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        BodyPanel.setBackground(new java.awt.Color(255, 0, 51));
        BodyPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BodyPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                BodyPanelMouseDragged(evt);
            }
        });
        BodyPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BodyPanelMousePressed(evt);
            }
        });

        HeaderPanel.setBackground(new java.awt.Color(255, 204, 51));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\ASUS\\Downloads\\a5dw0-rb4dd.png")); // NOI18N
        jLabel1.setText("Perpustakaan");
        jLabel1.setIconTextGap(20);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon("C:\\Users\\ASUS\\Downloads\\aa7j2-63s9t.png")); // NOI18N
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setIcon(new javax.swing.ImageIcon("C:\\Users\\ASUS\\Downloads\\agwqp-aipp6.png")); // NOI18N
        jLabel12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("?");
        jLabel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel18MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout HeaderPanelLayout = new javax.swing.GroupLayout(HeaderPanel);
        HeaderPanel.setLayout(HeaderPanelLayout);
        HeaderPanelLayout.setHorizontalGroup(
            HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderPanelLayout.createSequentialGroup()
                .addGap(312, 312, 312)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 590, Short.MAX_VALUE)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        HeaderPanelLayout.setVerticalGroup(
            HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(HeaderPanelLayout.createSequentialGroup()
                .addGroup(HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel11)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        SidebarPanel.setBackground(new java.awt.Color(153, 153, 255));
        SidebarPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, null, java.awt.Color.white));

        Mahasiswa.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        Mahasiswa.setIcon(new javax.swing.ImageIcon("C:\\Users\\ASUS\\Downloads\\az54g-kxiii.png")); // NOI18N
        Mahasiswa.setText("Data Mahasiswa");
        Mahasiswa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Mahasiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MahasiswaActionPerformed(evt);
            }
        });

        Buku.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        Buku.setIcon(new javax.swing.ImageIcon("C:\\Users\\ASUS\\Downloads\\alu4f-mt7x0.png")); // NOI18N
        Buku.setText("Data Buku");
        Buku.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Buku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BukuActionPerformed(evt);
            }
        });

        Admin.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        Admin.setIcon(new javax.swing.ImageIcon("C:\\Users\\ASUS\\Downloads\\p3r68-cdx67\\alkaj-zda03.png")); // NOI18N
        Admin.setText("Data Admin");
        Admin.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Admin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AdminActionPerformed(evt);
            }
        });

        Transaksi.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        Transaksi.setIcon(new javax.swing.ImageIcon("C:\\Users\\ASUS\\Downloads\\a5a67-wnh1j.png")); // NOI18N
        Transaksi.setText("Transksi");
        Transaksi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Transaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TransaksiActionPerformed(evt);
            }
        });

        Menu.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Menu.setIcon(new javax.swing.ImageIcon("C:\\Users\\ASUS\\Downloads\\achx4-lyld6.png")); // NOI18N
        Menu.setText("Menu");

        Login.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        Login.setIcon(new javax.swing.ImageIcon("C:\\Users\\ASUS\\Downloads\\a3u7e-blfv2.png")); // NOI18N
        Login.setText("Login");
        Login.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginActionPerformed(evt);
            }
        });

        Mahasiswa1.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        Mahasiswa1.setIcon(new javax.swing.ImageIcon("C:\\Users\\ASUS\\Downloads\\home (2).png")); // NOI18N
        Mahasiswa1.setText("Home");
        Mahasiswa1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Mahasiswa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Mahasiswa1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout SidebarPanelLayout = new javax.swing.GroupLayout(SidebarPanel);
        SidebarPanel.setLayout(SidebarPanelLayout);
        SidebarPanelLayout.setHorizontalGroup(
            SidebarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Mahasiswa, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
            .addComponent(Buku, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Admin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Transaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Login, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(SidebarPanelLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(Menu, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(Mahasiswa1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        SidebarPanelLayout.setVerticalGroup(
            SidebarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SidebarPanelLayout.createSequentialGroup()
                .addComponent(Menu, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(Mahasiswa1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Mahasiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Buku, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Admin, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Login, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ContentPanel.setBackground(new java.awt.Color(255, 102, 102));
        ContentPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));
        ContentPanel.setLayout(new java.awt.CardLayout());

        mahasiswaPanel.setBackground(new java.awt.Color(255, 0, 51));

        jPanel2.setBackground(new java.awt.Color(153, 153, 255));

        jLabel2.setBackground(new java.awt.Color(255, 51, 153));
        jLabel2.setFont(new java.awt.Font("Yu Gothic UI", 1, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon("C:\\Users\\ASUS\\Downloads\\az54g-kxiii.png")); // NOI18N
        jLabel2.setText("Data Mahasiswa");

        hhh.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        hhh.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        hhh.setText("Id");
        hhh.setToolTipText("");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("Nama");
        jLabel7.setToolTipText("");

        nama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                namaKeyReleased(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Jurusan");
        jLabel8.setToolTipText("");

        jurusan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jurusan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Teknik Informatika", "Teknik Fisika", "Teknik Kimia", "Teknik Elektro", "Teknik Industri", "Sistem Informasi", "Pilih" }));
        jurusan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jurusanActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Jenis Kelamin");
        jLabel9.setToolTipText("");

        jk.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jk.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih", "Laki-laki", "Perempuan" }));
        jk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jkActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("tgl/bln/thn Lahir");
        jLabel10.setToolTipText("");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Alamat");
        jLabel13.setToolTipText("");

        alamat1.setColumns(20);
        alamat1.setRows(5);
        jScrollPane1.setViewportView(alamat1);

        jButton1.setIcon(new javax.swing.ImageIcon("C:\\Users\\ASUS\\Downloads\\ajcj5-7v395-001.png")); // NOI18N
        jButton1.setText("Simpan");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        edit.setIcon(new javax.swing.ImageIcon("C:\\Users\\ASUS\\Downloads\\edit.png")); // NOI18N
        edit.setText("Edit");
        edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon("C:\\Users\\ASUS\\Downloads\\a8h7p-kazyo-001.png")); // NOI18N
        jButton3.setText("Hapus");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon("C:\\Users\\ASUS\\Downloads\\Cancel.png")); // NOI18N
        jButton4.setText("Cancel");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idActionPerformed(evt);
            }
        });

        NIs.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        NIs.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        NIs.setText("NIS");
        NIs.setToolTipText("");

        Nis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NisActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(nama, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jk, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jurusan, 0, 217, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(hhh, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel13))
                        .addGap(37, 37, 37)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(date1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(18, 18, 18)
                                .addComponent(edit)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3)
                                .addGap(18, 18, 18)
                                .addComponent(jButton4)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(NIs, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Nis, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hhh, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Nis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NIs, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nama))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jurusan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(date1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(110, 110, 110))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(edit)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addGap(34, 34, 34))
        );

        cariMahasiswa.setText("Caari");
        cariMahasiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cariMahasiswaActionPerformed(evt);
            }
        });

        jButton5.setText("Cari");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        tabelMahasiswa.setBackground(new java.awt.Color(102, 102, 102));
        tabelMahasiswa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NIS", "Nama", "Jurusan", "Jenis Kelamin", "Kelahiran", "Alamat"
            }
        ));
        tabelMahasiswa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelMahasiswaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelMahasiswa);

        refresh.setText("Refresh");
        refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Untuk mengembalikan data ditabel seperti semula");

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Cari berdasarkan nama");

        javax.swing.GroupLayout mahasiswaPanelLayout = new javax.swing.GroupLayout(mahasiswaPanel);
        mahasiswaPanel.setLayout(mahasiswaPanelLayout);
        mahasiswaPanelLayout.setHorizontalGroup(
            mahasiswaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mahasiswaPanelLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mahasiswaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(mahasiswaPanelLayout.createSequentialGroup()
                        .addGroup(mahasiswaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(refresh)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 148, Short.MAX_VALUE)
                        .addGroup(mahasiswaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mahasiswaPanelLayout.createSequentialGroup()
                                .addComponent(cariMahasiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton5))
                            .addComponent(jLabel27))
                        .addContainerGap())))
        );
        mahasiswaPanelLayout.setVerticalGroup(
            mahasiswaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mahasiswaPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(mahasiswaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mahasiswaPanelLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(mahasiswaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cariMahasiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel27))
                    .addGroup(mahasiswaPanelLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(refresh)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        ContentPanel.add(mahasiswaPanel, "card2");

        jPanel4.setBackground(new java.awt.Color(255, 0, 51));
        jPanel4.setPreferredSize(new java.awt.Dimension(816, 471));
        jPanel4.setLayout(null);

        jPanel5.setBackground(new java.awt.Color(51, 255, 0));
        jPanel5.setPreferredSize(new java.awt.Dimension(320, 478));

        jLabel4.setBackground(new java.awt.Color(255, 51, 153));
        jLabel4.setFont(new java.awt.Font("Yu Gothic UI", 1, 24)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon("C:\\Users\\ASUS\\Downloads\\alu4f-mt7x0.png")); // NOI18N
        jLabel4.setText("Data Buku");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Nama Penulis");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Judul Buku");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("Penerbit");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setText("Kota Terbit");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setText("Tahun Terbit");

        tahun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tahunActionPerformed(evt);
            }
        });

        jButton6.setIcon(new javax.swing.ImageIcon("C:\\Users\\ASUS\\Downloads\\ajcj5-7v395-001.png")); // NOI18N
        jButton6.setText("Simpan");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setIcon(new javax.swing.ImageIcon("C:\\Users\\ASUS\\Downloads\\edit.png")); // NOI18N
        jButton7.setText("Edit");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setIcon(new javax.swing.ImageIcon("C:\\Users\\ASUS\\Downloads\\a8h7p-kazyo-001.png")); // NOI18N
        jButton8.setText("Hapus");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setIcon(new javax.swing.ImageIcon("C:\\Users\\ASUS\\Downloads\\Cancel.png")); // NOI18N
        jButton9.setText("Cancel");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setText("Kode Buku");

        kode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kodeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(tahun))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(penulis1, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(judul))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(penerbit))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(kota))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(kode)))
                .addContainerGap(42, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(penulis1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(judul)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(penerbit)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(kota)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tahun, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kode, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(jButton7)
                    .addComponent(jButton8)
                    .addComponent(jButton9))
                .addGap(0, 115, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel5);
        jPanel5.setBounds(0, 0, 352, 478);

        tabelBuku.setBackground(new java.awt.Color(51, 255, 0));
        tabelBuku.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Penulis", "Judul", "Penerbit", "Kota Terbit", "Tahun Terbit", "Kode Buku"
            }
        ));
        tabelBuku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelBukuMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tabelBuku);

        jPanel4.add(jScrollPane3);
        jScrollPane3.setBounds(362, 11, 780, 352);

        mana.setText("Caari");
        mana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manaActionPerformed(evt);
            }
        });
        jPanel4.add(mana);
        mana.setBounds(826, 369, 217, 35);

        jButton10.setText("Cari");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton10);
        jButton10.setBounds(1049, 375, 70, 23);

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Untuk mengembalikan data ditabel seperti semula");
        jPanel4.add(jLabel28);
        jLabel28.setBounds(350, 410, 360, 17);

        jButton2.setText("Refresh");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton2);
        jButton2.setBounds(362, 375, 100, 23);

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Cari berdasarkan nama penulis");
        jPanel4.add(jLabel29);
        jLabel29.setBounds(826, 413, 230, 17);

        javax.swing.GroupLayout BukuPanelLayout = new javax.swing.GroupLayout(BukuPanel);
        BukuPanel.setLayout(BukuPanelLayout);
        BukuPanelLayout.setHorizontalGroup(
            BukuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 1138, Short.MAX_VALUE)
        );
        BukuPanelLayout.setVerticalGroup(
            BukuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BukuPanelLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        ContentPanel.add(BukuPanel, "card3");

        homePanel.setBackground(new java.awt.Color(255, 0, 51));

        siapa.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel26.setText("Ada yang bisa kami bantu");

        komentar.setColumns(20);
        komentar.setRows(5);
        jScrollPane6.setViewportView(komentar);

        jButton16.setText("Kirim");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jButton17.setText("Hapus");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jButton18.setText("Cancel");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel32.setText("Tanggal");

        time.setBackground(new java.awt.Color(102, 255, 102));
        time.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        time.setForeground(new java.awt.Color(153, 255, 51));

        hari.setBackground(new java.awt.Color(102, 255, 102));
        hari.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        hari.setForeground(new java.awt.Color(153, 255, 51));

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel33.setText("Waktu");

        javax.swing.GroupLayout homePanelLayout = new javax.swing.GroupLayout(homePanel);
        homePanel.setLayout(homePanelLayout);
        homePanelLayout.setHorizontalGroup(
            homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(homePanelLayout.createSequentialGroup()
                        .addComponent(siapa, javax.swing.GroupLayout.PREFERRED_SIZE, 714, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(homePanelLayout.createSequentialGroup()
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(time, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hari, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(124, 124, 124))
            .addGroup(homePanelLayout.createSequentialGroup()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 996, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 71, Short.MAX_VALUE))
        );
        homePanelLayout.setVerticalGroup(
            homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homePanelLayout.createSequentialGroup()
                .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(homePanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(siapa, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(homePanelLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(hari, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(time, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)))
                .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(homePanelLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54)
                        .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(77, 77, 77)
                        .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 8, Short.MAX_VALUE))
        );

        ContentPanel.add(homePanel, "card4");

        AdminPanel.setBackground(new java.awt.Color(255, 0, 51));
        AdminPanel.setLayout(null);

        jPanel6.setBackground(new java.awt.Color(0, 0, 255));
        jPanel6.setPreferredSize(new java.awt.Dimension(320, 478));

        jLabel19.setBackground(new java.awt.Color(255, 51, 153));
        jLabel19.setFont(new java.awt.Font("Yu Gothic UI", 1, 24)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setIcon(new javax.swing.ImageIcon("C:\\Users\\ASUS\\Downloads\\p3r68-cdx67\\alkaj-zda03.png")); // NOI18N
        jLabel19.setText("Data Admin");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setText("Nama");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setText("Profesi");

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setText("Bidang");

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel23.setText("No telepon");

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel24.setText("Kelahiran");

        madu.setText("Nama Dosen");

        telepon.setText("No Telepon");

        jButton11.setIcon(new javax.swing.ImageIcon("C:\\Users\\ASUS\\Downloads\\ajcj5-7v395-001.png")); // NOI18N
        jButton11.setText("Simpan");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.setIcon(new javax.swing.ImageIcon("C:\\Users\\ASUS\\Downloads\\edit.png")); // NOI18N
        jButton12.setText("Edit");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton13.setIcon(new javax.swing.ImageIcon("C:\\Users\\ASUS\\Downloads\\a8h7p-kazyo-001.png")); // NOI18N
        jButton13.setText("Hapus");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton14.setIcon(new javax.swing.ImageIcon("C:\\Users\\ASUS\\Downloads\\Cancel.png")); // NOI18N
        jButton14.setText("Cancel");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel25.setText("Alamat");

        alamat.setColumns(20);
        alamat.setRows(5);
        jScrollPane5.setViewportView(alamat);

        profesi.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        profesi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih", "Dosen", "Guru" }));
        profesi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                profesiActionPerformed(evt);
            }
        });

        bidang.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        bidang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih", "Teknik Kimia", "Teknik Industri", "Teknik Fisika", "Teknik Informatika", "Sistem Informasi", " " }));
        bidang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bidangActionPerformed(evt);
            }
        });

        nomor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        nomor.setText("No");

        nomor1.setText("Nomor Buku Tidak Usah Diisi");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jButton11)
                .addGap(6, 6, 6)
                .addComponent(jButton12)
                .addGap(6, 6, 6)
                .addComponent(jButton13)
                .addGap(6, 6, 6)
                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addGap(70, 70, 70)
                        .addComponent(madu, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(nomor)
                        .addGap(90, 90, 90)
                        .addComponent(nomor1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(profesi, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(bidang, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(telepon, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(kelahiran, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(madu, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(11, 11, 11)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nomor, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(nomor1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(profesi, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(bidang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(telepon, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(11, 11, 11)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kelahiran, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton11)
                    .addComponent(jButton12)
                    .addComponent(jButton13)
                    .addComponent(jButton14)))
        );

        AdminPanel.add(jPanel6);
        jPanel6.setBounds(0, 0, 360, 487);

        tabelAdmin.setBackground(new java.awt.Color(0, 0, 255));
        tabelAdmin.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Nama Dosen", "Profesi", "Bidang", "No telepon", "Kelahiran", "Alamat"
            }
        ));
        tabelAdmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelAdminMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tabelAdmin);

        AdminPanel.add(jScrollPane4);
        jScrollPane4.setBounds(362, 11, 800, 352);

        cariData.setText("Caari");
        cariData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cariDataActionPerformed(evt);
            }
        });
        AdminPanel.add(cariData);
        cariData.setBounds(828, 369, 217, 35);

        jButton15.setText("Cari");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        AdminPanel.add(jButton15);
        jButton15.setBounds(1051, 375, 80, 23);

        jButton19.setText("Refresh");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });
        AdminPanel.add(jButton19);
        jButton19.setBounds(370, 380, 79, 23);

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("Cari beradasarkan nama");
        AdminPanel.add(jLabel30);
        jLabel30.setBounds(830, 410, 210, 17);

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("Untuk mengembalikan data ditabel seperti semula");
        AdminPanel.add(jLabel31);
        jLabel31.setBounds(370, 420, 350, 17);

        ContentPanel.add(AdminPanel, "card5");

        TransaksiPanel.setBackground(new java.awt.Color(255, 0, 51));

        jButton21.setBackground(new java.awt.Color(255, 255, 0));
        jButton21.setText("Transkasi Pengembalian Buku");
        jButton21.setPreferredSize(new java.awt.Dimension(286, 100));
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton21, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton21, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
        );

        jButton20.setBackground(new java.awt.Color(255, 255, 51));
        jButton20.setText("Transaksi Peminjaman Buku");
        jButton20.setPreferredSize(new java.awt.Dimension(286, 100));
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout TransaksiPanelLayout = new javax.swing.GroupLayout(TransaksiPanel);
        TransaksiPanel.setLayout(TransaksiPanelLayout);
        TransaksiPanelLayout.setHorizontalGroup(
            TransaksiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TransaksiPanelLayout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(451, Short.MAX_VALUE))
        );
        TransaksiPanelLayout.setVerticalGroup(
            TransaksiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TransaksiPanelLayout.createSequentialGroup()
                .addContainerGap(210, Short.MAX_VALUE)
                .addGroup(TransaksiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(184, 184, 184))
        );

        ContentPanel.add(TransaksiPanel, "card6");

        javax.swing.GroupLayout BodyPanelLayout = new javax.swing.GroupLayout(BodyPanel);
        BodyPanel.setLayout(BodyPanelLayout);
        BodyPanelLayout.setHorizontalGroup(
            BodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(HeaderPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(BodyPanelLayout.createSequentialGroup()
                .addComponent(SidebarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ContentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1142, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        BodyPanelLayout.setVerticalGroup(
            BodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BodyPanelLayout.createSequentialGroup()
                .addComponent(HeaderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(BodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(BodyPanelLayout.createSequentialGroup()
                        .addComponent(SidebarPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(ContentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(BodyPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(BodyPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
String date = null;
    private void LoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoginActionPerformed
        
        Login candra = new Login();
        candra.setVisible(true);
        pack();
        this.dispose();
    }//GEN-LAST:event_LoginActionPerformed

    private void BodyPanelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BodyPanelMouseDragged

        int mousea = evt.getXOnScreen();
        int mouseb = evt.getYOnScreen();
        this.setLocation(mousea-mousex, mouseb -mousey);
    }//GEN-LAST:event_BodyPanelMouseDragged

    private void BodyPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BodyPanelMousePressed
        // TODO add your handling code here:
        mousex = evt.getX();
        mousey = evt.getY();
    }//GEN-LAST:event_BodyPanelMousePressed

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        this. setState(JFrame.ICONIFIED);

    }//GEN-LAST:event_jLabel11MouseClicked

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        int buaya = JOptionPane.showConfirmDialog(this, "Apakah anda yakin ingin keluar","Peringatan",0);
        switch(buaya){
            case JOptionPane.YES_OPTION:
            this.dispose();
            System.exit(0);
            break;
            default:
        }
    }//GEN-LAST:event_jLabel12MouseClicked

    private void MahasiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MahasiswaActionPerformed
        // TODO add your handling code here:
        ContentPanel.removeAll();
        ContentPanel.repaint();
        ContentPanel.revalidate();
        
        // add panel
        ContentPanel.add(mahasiswaPanel);
        ContentPanel.repaint();
        ContentPanel.revalidate();
      
    }//GEN-LAST:event_MahasiswaActionPerformed

    private void jurusanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jurusanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jurusanActionPerformed

    private void jkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jkActionPerformed

    private void cariMahasiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cariMahasiswaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cariMahasiswaActionPerformed

    private void Mahasiswa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Mahasiswa1ActionPerformed
        // TODO add your handling code here:
        // remove all panel
        ContentPanel.removeAll();
        ContentPanel.repaint();
        ContentPanel.revalidate();
        
        // add panel
        ContentPanel.add(homePanel);
        ContentPanel.repaint();
        ContentPanel.revalidate();
    }//GEN-LAST:event_Mahasiswa1ActionPerformed

    private void tahunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tahunActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tahunActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        PreparedStatement st;
        String kode1 = kode.getText();
        String penulis = toLowerCase(penulis1.getText());
        String judul1 = toLowerCase(judul.getText());
        String penerbit1 = toLowerCase(penerbit.getText());
        String kota1 = toLowerCase(kota.getText());
        String tahun1 = tahun.getText();
        String query = "UPDATE buku SET penulis=?, penerbit=?, Kota=?, tahunTerbit=?, judulBuku=? WHERE KodeBuku="+kode1;
        try {
            if (penulis.equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(this, "penulis tidak boleh kosong");
            }else if (judul1.equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(this,"Judul buku tidak boleh kosong");
            }else if (penerbit1.equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(this,"Penerbit tidak boleh kosong tidak boleh kosong");
            }else if (kota1.equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(this,"Kota tidak boleh kosong tidak boleh kosong");
            }else if (tahun1.equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(this,"Tahun tidak  tidak boleh kosong");
            }else{
            st = koneksi.getConnection().prepareStatement(query);
            st.setString(1, penulis);
            st.setString(2, penerbit1);
            st.setString(3, kota1);
            st.setString(5, judul1);
            st.setString(4, tahun1);
            int tanya = JOptionPane.showConfirmDialog(this, "Apakah anda ingin mengubah data ","KONFIRMASI",0);
            if (tanya == 0) {
                
            
            if (st.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(this, "Data berhasil diubah");
                tampilBuku();
                kosongBuku();
            }else{
                JOptionPane.showMessageDialog(this, "Data tidak berhasil diubah");
            }
            }else{
                JOptionPane.showMessageDialog(this, "Data tidak diubah");
            }
            }
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Sistem Error");
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        PreparedStatement st;
        String query = "DELETE FROM `buku` WHERE KodeBuku=?";
        String kode1 = tabelBuku.getValueAt(tabelBuku.getSelectedRow(),0).toString();
        try {
            if (kode1.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Anda belum memilih data yang mau dihapus");
            }else{
            st = koneksi.getConnection().prepareStatement(query);
            st.setString(1, kode1);
            int tanya = JOptionPane.showConfirmDialog(this, "Apakah anda ingin menghapus data ", "KONFIRMASI", 0);
            if (tanya == 0) {
                
            
            if (st.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus");
                tampilBuku();
                kosongBuku();
            }else{
                JOptionPane.showMessageDialog(this, "Data tidak berhasil dihapus");
                tampilBuku();
                
            }
            }else{
                JOptionPane.showMessageDialog(this, "Data tidak dihapus");
            }
            }
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan pada sistem");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan pada sistem");
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here
        kosongBuku();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void kodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kodeActionPerformed

    private void manaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_manaActionPerformed

    private void BukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BukuActionPerformed
        // TODO add your handling code here:
        ContentPanel.removeAll();
        ContentPanel.repaint();
        ContentPanel.revalidate();
        
        // add panel
        ContentPanel.add(BukuPanel);
        ContentPanel.repaint();
        ContentPanel.revalidate();
    }//GEN-LAST:event_BukuActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        String id = nomor1.getText();
        String madu1 = toLowerCase(madu.getText());
        String profesi1 = (String)profesi.getSelectedItem();
        String bidang1 = (String)bidang.getSelectedItem();
        String telepon1 = telepon.getText();
        SimpleDateFormat date1 = new SimpleDateFormat();
        String date = date1.format(kelahiran.getDate());
        String alamat1 = toLowerCase(alamat.getText());
        PreparedStatement ds;
        String query = "UPDATE admin SET  nama=?, profesi=?, bidang=?, telepon=?, kelahiran=?, Alamat=? WHERE id="+id;
        try {
            ds = koneksi.getConnection().prepareStatement(query);
            ds.setString(1, madu1);
            ds.setString(2, profesi1);
            ds.setString(3, bidang1);
            ds.setString(4, telepon1);
            ds.setString(5, date);
            ds.setString(6, alamat1);
            int tanya = JOptionPane.showConfirmDialog(this, "Apakah anda ingin mengubah data?", "KONFIRMASI", 0);
            if (tanya == 0) {
                
            
            if (ds.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(this, "Data berhasil diubah");
                tampilAdmin();
                kosongAdmin();
            }else{
                JOptionPane.showMessageDialog(this, "Data tidak berhasil diubah");
                tampilAdmin();
            }
            }else {
                JOptionPane.showMessageDialog(this, "Data tidak diubah");
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        PreparedStatement st;
        String userId = tabelAdmin.getValueAt(tabelAdmin.getSelectedRow(),0).toString();
        String query = "DELETE FROM admin WHERE id=?";
        try{
        st = koneksi.getConnection().prepareStatement(query);
        st.setString(1, userId);
        int tanya = JOptionPane.showConfirmDialog(this, "Apakah anda yakin ingin menghapus data?", "KONFIRMASI", 0);
            if (tanya == 0) {
                
            
            if (st.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus");
                tampilAdmin();
                kosongAdmin();
            }else{
                JOptionPane.showMessageDialog(this, "Data tidak berhasil dihapus");
            }
            }else
            {
                JOptionPane.showMessageDialog(this, "Data tidak dihapus");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        kosongAdmin();
    }//GEN-LAST:event_jButton14ActionPerformed

    private void cariDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cariDataActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cariDataActionPerformed

    private void profesiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_profesiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_profesiActionPerformed

    private void bidangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bidangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bidangActionPerformed

    private void AdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AdminActionPerformed
        // TODO add your handling code here:
        ContentPanel.removeAll();
        ContentPanel.repaint();
        ContentPanel.revalidate();
        ContentPanel.add(AdminPanel);
        ContentPanel.repaint();
        ContentPanel.revalidate();
       
    }//GEN-LAST:event_AdminActionPerformed

    private void jLabel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseClicked
        // TODO add your handling code here:
        About about = new About();
        about.setLocationRelativeTo(null);
        about.setVisible(true);
        about.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        
        
    }//GEN-LAST:event_jLabel18MouseClicked
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        PreparedStatement st;
        String id1 = id.getText();
        String npm = toLowerCase(Nis.getText());
        String nma = toLowerCase(nama.getText());
        String jurus = (String) jurusan.getSelectedItem();
        String pk = (String)jk.getSelectedItem();
        SimpleDateFormat date1 = new SimpleDateFormat();
            date = date1.format(kelahiran.getDate());  
        String almt = toLowerCase(alamat1.getText());
        String query = "INSERT INTO `mahasiswa`(`NIS`, `Nama`, `Jurusan`, `kelamin`, `kelahiran`, `alamat`) VALUES (?,?,?,?,?,?)";
           if (npm.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Nis tidak boleh kosong");
        }else if (nma.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Nama tidak boleh kosong");
        }else if (jurus.equalsIgnoreCase("Pilih")) {
            JOptionPane.showMessageDialog(this, "Jurusan tidak boleh kosong");
        }else if (pk.equalsIgnoreCase("Pilih")) {
            JOptionPane.showMessageDialog(this, "Jenis kelamin tidak boleh kosong");    
        }else if (almt.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Alamat tidak boleh kosong");
        }else{
        try {
            if (Nama(nma) == true) {
                JOptionPane.showMessageDialog(this, "Nama sudah ada ditabel");
                int tanya = JOptionPane.showConfirmDialog(this, "Apakah anda ingin menambahkan data tersebut","KONFIRMASI",0);
                if (tanya == 0) {
                    st = koneksi.getConnection().prepareStatement(query);
            
            st.setString(1, npm);
            st.setString(2, nma);
            st.setString(3, jurus);
            st.setString(4, pk);
            st.setString(6, almt);
            st.setString(5, date);
            
            
           
             if (st.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(this, "Data berhasil disimpan");
                kosong();
            }else{
                JOptionPane.showMessageDialog(this, "Data tidak berhasil disimpan");
            }
                }else{
                    JOptionPane.showMessageDialog(this, "Data tidak ditambahkan");
                }
            }
        else {
                
            
            st = koneksi.getConnection().prepareStatement(query);
            
            st.setString(1, npm);
            st.setString(2, nma);
            st.setString(3, jurus);
            st.setString(4, pk);
            st.setString(6, almt); 
            st.setString(5, date);    
            if (st.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(this, "Data berhasil disimpan");
                kosong();
            }else{
                JOptionPane.showMessageDialog(this, "Data tidak berhasil disimpan");
            }
            }
        
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        tampilkanDua();
        }
        
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idActionPerformed
    
    private void editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editActionPerformed
       PreparedStatement st;
       ResultSet res;
        String Id = id.getText();
        String nis = toLowerCase(Nis.getText());
       String nama2 = toLowerCase(nama.getText());
       String jurus = (String) jurusan.getSelectedItem();
        String pk = (String)jk.getSelectedItem();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(date1.getDate());
        String almt = toLowerCase(alamat1.getText());
        String query = "UPDATE mahasiswa SET id=? , NIS=?, Nama=?, Jurusan =?, kelamin =?, kelahiran =?, alamat =? WHERE id="+Id;
        try {
            st = koneksi.getConnection().prepareStatement(query);
            st.setString(1, Id);
            st.setString(2, nis);
            st.setString(3, nama2);
            st.setString(4, jurus);
            st.setString(5, pk);
            st.setString(6, date);
            st.setString(7, almt);
            int tambah = st.executeUpdate();
            int tanya = JOptionPane.showConfirmDialog(this, "Apakah anda ingin mengubah data","Konfirmasi",0);
            switch(tanya){
                case JOptionPane.YES_OPTION :
            
            if (tambah > 0) {
                JOptionPane.showMessageDialog(this, "Data berhasil di ubah");
                tampilkanDua();
                kosong();
            }else{
                JOptionPane.showMessageDialog(this, "Data tidak berhasil diubah");
            }
            break;
                default:
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }//GEN-LAST:event_editActionPerformed

    private void tabelMahasiswaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelMahasiswaMouseClicked
        // menampilkan data di teks
        try{
        int i = tabelMahasiswa.getSelectedRow();
        TableModel model = tabelMahasiswa.getModel();
        id.setText(model.getValueAt(i, 0).toString());
        Nis.setText(model.getValueAt(i,1).toString());
        nama.setText(model.getValueAt(i,2).toString());
        jurusan.setSelectedItem(model.getValueAt(i,3).toString());
        jk.setSelectedItem(model.getValueAt(i,4).toString());
        alamat1.setText(model.getValueAt(i,6).toString());
        Date date;
        date = new SimpleDateFormat("yyyy-MM-dd").parse((String)model.getValueAt(i, 5).toString());
        date1.setDate(date);       
        }catch (ParseException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tabelMahasiswaMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        
        try {
            String userId = tabelMahasiswa.getValueAt(tabelMahasiswa.getSelectedRow(),0).toString();
           
            String query = "DELETE FROM mahasiswa WHERE id=?";
            if (Nis.getText().equalsIgnoreCase("")) {
               JOptionPane.showMessageDialog(this, "Pilih data yang mau di hapus");
            }else{
            PreparedStatement st = koneksi.getConnection().prepareStatement(query);
            st.setString(1, userId);
            
            int tambah = st.executeUpdate();
            int tanya = JOptionPane.showConfirmDialog(this, "Apakah anda yakin ingin menghapus data?","Konfirmasi",0);
            
            if (tanya == 0) {
                
            
                if (tambah > 0) {
                   DefaultTableModel model = (DefaultTableModel)tabelMahasiswa.getModel();
             
                model.removeRow(tabelMahasiswa.getSelectedRow());
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus");
               tampilkanDua();
               kosong();
                }
                
            }else{
                JOptionPane.showMessageDialog(this, "Data tidak dihapus ");
            }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    
    }//GEN-LAST:event_jButton3ActionPerformed

    private void NisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NisActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
       kosong();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        String penulis = toLowerCase(penulis1.getText());
        String judul1 = toLowerCase(judul.getText());
        String penerbit1 = toLowerCase(penerbit.getText());
        String kota1 = toLowerCase(kota.getText());
        String tahun1 = tahun.getText();
        PreparedStatement st;
        String query = "INSERT INTO `buku`( `penulis`, `penerbit`, `Kota`, `tahunTerbit`, `judulBuku`) VALUES (?,?,?,?,?)";
        try {
            if (penulis.equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(this, "penulis tidak boleh kosong");
            }
            else if (penulis(penulis)) {
                JOptionPane.showMessageDialog(this, "Penulis sudah ada di tabel");
                int tanya = JOptionPane.showConfirmDialog(this, "Apakah anda ingin menambah data tersebut lagi","KONFIRMASI",0);
                if (tanya == 0) {
                  st = koneksi.getConnection().prepareStatement(query);
                  st.setString(1,penulis);
                  st.setString(2, penerbit1);
                  st.setString(3, kota1);
                  st.setString(4, tahun1 );
                  st.setString(5, judul1);
            if (st.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(this, "Data buku berhasil disimpan");
                tampilBuku();
                kosongBuku();
            }else {
                JOptionPane.showMessageDialog(this, "Data buku tidak disimpan");
                tampilBuku();
            }  
                }else{
                    JOptionPane.showMessageDialog(this, "Data buku tidak disimpan");
                }
            } else if (judul1.equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(this, "Judul tidak boleh kosong");
            }else if (penerbit1.equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(this, "Penerbit tidak boleh kosong");
            }else if (kota1.equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(this, "Kota terbit tidak boleh kosong");
            }else if (tahun1.equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(this, "Tahun terbit tidak boleh kosong");
            }else
            {
  
            st = koneksi.getConnection().prepareStatement(query);
            st.setString(1,penulis);
            st.setString(2, penerbit1);
            st.setString(3, kota1);
            st.setString(4, tahun1 );
            st.setString(5, judul1);
            if (st.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(this, "Data buku berhasil disimpan");
                tampilBuku();
                kosongBuku();
            }else {
                JOptionPane.showMessageDialog(this, "Data buku tidak disimpan");
                tampilBuku();
            }
            
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        PreparedStatement st;
       String koment = komentar.getText();
       String mysql = "INSERT INTO komentar (koment)VALUES (?)";
        try {
            st = koneksi.getConnection().prepareStatement(mysql);
            st.setString(1, koment);
            if (st.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(this, "Berhasil mengirim komentar");
            }else{
                JOptionPane.showMessageDialog(this, "Gagal mengirim komentar");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        // TODO add your handling code here:
        komentar.setText("");
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        // TODO add your handling code here:
        komentar.setText("");
        JOptionPane.showMessageDialog(this, "Anda gagal mengirim pesan");
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        String date = null;
        PreparedStatement st;
        ResultSet rs;
        String id = nomor1.getText();
        String nama1 = toLowerCase(madu.getText());
        String bidang1 = (String)bidang.getSelectedItem();
        String profesi1 = (String)profesi.getSelectedItem();
        String telepon1 = toLowerCase(telepon.getText());
       SimpleDateFormat date1 = new SimpleDateFormat();
            date = date1.format(kelahiran.getDate());    
        String alamat1 = toLowerCase(alamat.getText());
        String query = "INSERT INTO `admin`(`nama`, `profesi`, `bidang`, `telepon`, `kelahiran`, `Alamat`) VALUES (?,?,?,?,?,?)";
        try {
            if (nama1.equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(this, "Nama dosen tidak boleh kosong");
            }else if (bidang1.equalsIgnoreCase("Pilih")) {
                JOptionPane.showMessageDialog(this, "Bidang  tidak boleh kosong");
            }else if (profesi1.equalsIgnoreCase("Pilih")) {
                JOptionPane.showMessageDialog(this, "Profesi tidak boleh kosong");
            }else if (telepon1.equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(this, "Nomor telepon tidak boleh kosong tidak boleh kosong");
            }else if (alamat1.equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(this, "Alamat tidak boleh kosong");
            }else {
                if (NamaAdmin(nama1) == true) {
                    JOptionPane.showMessageDialog(this, "Nama sudah ada di tabel");
                    int tanya = JOptionPane.showConfirmDialog(this, "Apakah anda ingin menambahkan data tersebut ?", "KONFIRMASI", 0);
                    if (tanya == 0) {
                        st = koneksi.getConnection().prepareStatement(query);
            st.setString(1, nama1);
            st.setString(3, bidang1);
            st.setString(2, profesi1);
            st.setString(4, telepon1);
            st.setString(5, date);
            st.setString(6, alamat1);
            if (st.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(this, "Data berhasil disimpan");
                tampilAdmin();
                kosongAdmin();
            }else{
                JOptionPane.showMessageDialog(this, "Data tidak berhasil disimpan");
                tampilAdmin();
            }
                    }else{
                        JOptionPane.showMessageDialog(this, "Data tidak berhasil ditambahkan");
                        tampilAdmin();
                        kosongAdmin();
                    }
                }
                if (NamaAdmin(nama1) == false) {
                    
                
            st = koneksi.getConnection().prepareStatement(query);
            st.setString(1, nama1);
            st.setString(3, bidang1);
            st.setString(2, profesi1);
            st.setString(4, telepon1);
            st.setString(5, date);
            st.setString(6, alamat1);
            if (st.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(this, "Data berhasil disimpan");
                tampilAdmin();
                kosongAdmin();
            }else{
                JOptionPane.showMessageDialog(this, "Data tidak berhasil disimpan");
                tampilAdmin();
            }
            }
            }
            tampilAdmin();
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }//GEN-LAST:event_jButton11ActionPerformed

    private void tabelAdminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelAdminMouseClicked
        try{
        int i = tabelAdmin.getSelectedRow();
        TableModel model = tabelAdmin.getModel();
        nomor1.setText(model.getValueAt(i, 0).toString());
        madu.setText(model.getValueAt(i,1).toString());
        telepon.setText(model.getValueAt(i,4).toString());
        profesi.setSelectedItem(model.getValueAt(i,2).toString());
        bidang.setSelectedItem(model.getValueAt(i,3).toString());
        alamat.setText(model.getValueAt(i,6).toString());
        int satu = tabelMahasiswa.getSelectedRow();
            Date date;
            date = new SimpleDateFormat("yyyy-MM-dd").parse((String)model.getValueAt(i, 5).toString());
           kelahiran.setDate(date);
            
        
        }catch (ParseException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tabelAdminMouseClicked

    private void tabelBukuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelBukuMouseClicked
        // TODO add your handling code here:
        int i = tabelBuku.getSelectedRow();
        TableModel model = tabelBuku.getModel();
        penulis1.setText(model.getValueAt(i, 1).toString());
        judul.setText(model.getValueAt(i,5).toString());
        penerbit.setText(model.getValueAt(i, 2).toString());
        kota.setText(model.getValueAt(i, 3).toString());
        tahun.setText(model.getValueAt(i, 4).toString());
        kode.setText(model.getValueAt(i, 0).toString());
    }//GEN-LAST:event_tabelBukuMouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
       
        if (tabelTampil() == true) {
            JOptionPane.showMessageDialog(this, "Data ditemukan");
        }else{
            JOptionPane.showMessageDialog(this, "Data tidak ditemukan");
        }
        
    }//GEN-LAST:event_jButton5ActionPerformed
 // method cariBuku
    public boolean cariBuku(){
        DefaultTableModel buku = new DefaultTableModel();
        boolean kamu = false;
        buku.addColumn("kode buku");
        buku.addColumn("Penulis");
        buku.addColumn("Penerbit");
        buku.addColumn("Kota Terbit");
        buku.addColumn("Tahun Terbit");
        buku.addColumn("Judul Buku");
        int jumlahTabel = tabelBuku.getRowCount();
        for (int i = 0; i < jumlahTabel; i++) {           
        }
        String cari = mana.getText();
        try {
            ResultSet rs;
            String query = "SELECT * FROM buku WHERE penulis like '%"+cari+"%'";     
            PreparedStatement st = koneksi.getConnection().prepareStatement(query);
//            st.setString(1, cari + "%");
             rs = st.executeQuery(query);
             int no = 1;
            while(rs.next()){
                buku.addRow(new Object[]{
                    rs.getInt("KodeBuku"),rs.getString("penulis"),rs.getString("penerbit"),rs.getString("Kota"),rs.getString("tahunTerbit"),
                    rs.getString("judulBuku")
                });
                no++;
                kamu = true;
            }
            tabelBuku.setModel(buku);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return kamu;
    }
    // method cariAdmin
    public boolean cariAdmin(){
        DefaultTableModel admin = new DefaultTableModel();
        boolean kamu = false;
        admin.addColumn("nomor");
        admin.addColumn("Nama Dosen");
        admin.addColumn("Profesi");
        admin.addColumn("Bidang");
        admin.addColumn("No telepon");
        admin.addColumn("Kelahiran");
        admin.addColumn("Alamat");
        int jumlahTabel = tabelBuku.getRowCount();
        for (int i = 0; i < jumlahTabel; i++) {           
        }
        String cari = cariData.getText();
        try {
            ResultSet rs;
            String query = "SELECT * FROM admin WHERE nama like '%"+cari+"%'";     
            PreparedStatement st = koneksi.getConnection().prepareStatement(query);
//            st.setString(1, cari + "%");
             rs = st.executeQuery(query);
             int no = 1;
            while(rs.next()){
                admin.addRow(new Object[]{
                    rs.getInt("id"),rs.getString("nama"),rs.getString("profesi"),rs.getString("bidang"),rs.getString("telepon"),
                    rs.getString("kelahiran"),rs.getString("Alamat")
                });
                no++;
                kamu = true;
            }
            tabelAdmin.setModel(admin);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return kamu;
    }
    private void namaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_namaKeyReleased
  
    }//GEN-LAST:event_namaKeyReleased

    private void refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(this, "Data kembali seperti semula");
        tampilkanDua();
    }//GEN-LAST:event_refreshActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        // TODO add your handling code here:
        tampilAdmin();
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        tampilBuku();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        if (cariBuku() == true) {
   JOptionPane.showMessageDialog(this, "Data ditemukan");
}else {
            JOptionPane.showMessageDialog(this, "Data tidak ditemukan");
        }

    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
        if (cariAdmin() == true) {
            JOptionPane.showMessageDialog(this, "Data ditemukan");
        }else{
            JOptionPane.showMessageDialog(this, "Data tidak ditemukan");
        }
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        // TODO add your handling code here:
        TransaksiPengembalianBuku candra;
        try {
            candra = new TransaksiPengembalianBuku();
            candra.setVisible(true);
        candra.setLocationRelativeTo(null);
          candra.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_jButton21ActionPerformed

    private void TransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TransaksiActionPerformed
        // TODO add your handling code here:
        ContentPanel.removeAll();
        ContentPanel.repaint();
        ContentPanel.revalidate();
        ContentPanel.add(TransaksiPanel);
        ContentPanel.repaint();
        ContentPanel.revalidate();
    }//GEN-LAST:event_TransaksiActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Admin;
    private javax.swing.JPanel AdminPanel;
    private javax.swing.JPanel BodyPanel;
    private javax.swing.JButton Buku;
    private javax.swing.JPanel BukuPanel;
    private javax.swing.JPanel ContentPanel;
    private javax.swing.JPanel HeaderPanel;
    private javax.swing.JButton Login;
    private javax.swing.JButton Mahasiswa;
    private javax.swing.JButton Mahasiswa1;
    private javax.swing.JLabel Menu;
    private javax.swing.JLabel NIs;
    private javax.swing.JTextField Nis;
    private javax.swing.JPanel SidebarPanel;
    private javax.swing.JButton Transaksi;
    private javax.swing.JPanel TransaksiPanel;
    private javax.swing.JTextArea alamat;
    private javax.swing.JTextArea alamat1;
    private javax.swing.JComboBox<String> bidang;
    private javax.swing.JTextField cariData;
    private javax.swing.JTextField cariMahasiswa;
    private com.toedter.calendar.JDateChooser date1;
    private javax.swing.JButton edit;
    private javax.swing.JLabel hari;
    private javax.swing.JLabel hhh;
    private javax.swing.JPanel homePanel;
    private javax.swing.JTextField id;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JComboBox<String> jk;
    private javax.swing.JTextField judul;
    private javax.swing.JComboBox<String> jurusan;
    private com.toedter.calendar.JDateChooser kelahiran;
    private javax.swing.JTextField kode;
    private javax.swing.JTextArea komentar;
    private javax.swing.JTextField kota;
    private javax.swing.JTextField madu;
    private javax.swing.JPanel mahasiswaPanel;
    private javax.swing.JTextField mana;
    private javax.swing.JTextField nama;
    private javax.swing.JLabel nomor;
    private javax.swing.JTextField nomor1;
    private javax.swing.JTextField penerbit;
    private javax.swing.JTextField penulis1;
    private javax.swing.JComboBox<String> profesi;
    private javax.swing.JButton refresh;
    public javax.swing.JLabel siapa;
    private javax.swing.JTable tabelAdmin;
    private javax.swing.JTable tabelBuku;
    private javax.swing.JTable tabelMahasiswa;
    private javax.swing.JTextField tahun;
    private javax.swing.JTextField telepon;
    private javax.swing.JLabel time;
    // End of variables declaration//GEN-END:variables
// membuat digital oclock
    @Override
    public void run() {     
        while(true){
            try{
                Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                int second = c.get(Calendar.SECOND);
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                SimpleDateFormat ds = new SimpleDateFormat("HH:mm:ss");
                SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyy");
                Date date = c.getTime();
                String timeStr = ds.format(date);
                String Day = sd.format(date);
                time.setText(timeStr);
                hari.setText(Day);    
            }catch(Exception e){
                e.printStackTrace();
            }
        }  
    }
}

