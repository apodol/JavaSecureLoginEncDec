
import java.io.Serializable;

//-------KLASI STIN OPIA KRATAO <FILENAME, DIGEST>
public class File_Digest implements Serializable{
    //stixia to onoma tou arxiou
    private String fileName;
    
    //kai to hash tou arxiou
    private String fileHash;
     File_Digest(){
    }   
    File_Digest(String fileName,String fileHash){
        this.fileName=fileName;
        this.fileHash=fileHash;
    }   
    
    public String getfileName(){
        return fileName;}
    
    public String getfileHash(){
        return fileHash;
    }
    
    public void setfileName(String fileName){
        this.fileName=fileName;}
    
    public void setfileHash(String fileHash){
        this.fileHash=fileHash;
    }
    
}
