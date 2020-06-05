/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ASUS
 */
public class mahasiswa {
    private String NIS;
    private String Nama;
    private String Jurusan;
    private String kelamin;
    private String kelahiran;
    private String alamat;
    private int id;
    
    public mahasiswa(String nis, String nama, String jurusan,String Kelamin,String Kelahiran, String Alamat,Integer Id){
        this.id = Id;
        this.NIS = nis;
        this.Nama = nama;
        this.Jurusan = jurusan;
        this.kelamin = Kelamin;
        this.kelahiran = Kelahiran;
        this.alamat = Alamat;
        
    }
     public int getId(){
        return id;
    }
    public String getNis(){
    return NIS;
}
    public String getNama(){
        return Nama;
    }
    public String getJurusan()
    {
        return Jurusan;
    }
    public String getKelamin()
    {
        return kelamin;
    }
    public String getKelahiran()
    {
        return kelahiran;
    }
    public String getAlamat()
    {
        return alamat;
    }
  
}
