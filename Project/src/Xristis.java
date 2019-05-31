
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;


///--------------Klasi gia xristi me-----------//
public class Xristis implements Serializable{
    private String name;
    private String username;
    private String password;
    private byte []salt;
    private byte []encrypted;
    private ArrayList<File_Digest> file_digestArray;
    
    Xristis(){}
    Xristis(String name,String username,String password){
        
        this.name=name;
        this.username=username;
        this.password=password;
    
    }
    public String getName(){
        return name;}
    
    public String getUsername(){
        return username;}
    
    public String getPassword(){
        return password;}
    
    public void setSalt(byte[]salt){
        this.salt=salt;}
    
    public byte[] getSalt(){
        return salt;}
    
    public void setEncrypted(byte[] encrypted){
        this.encrypted=encrypted;}
    
    public byte [] getEncrypted(){
        return encrypted;}
    
    public String toString(){
        String saltString=Base64.getEncoder().encodeToString(salt);
        return "Name: "+name+"\nUsername: "+username+"\nPassword: "+password+"\nSalt: "+saltString;
    }
    
    
}
