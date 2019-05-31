
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;



public class Leitourgies {
    
    Leitourgies(){
    
    }
    
    //SINARTISI poy kalite kathe fora p kapios paei na kanei register,gia na doume an iparxi idio username
    //epistrefi true an iparxi,,false an dn iparxi.  
    public boolean checkUsernameDuplicate(String usernameToSearch){
        
        try {
            
            ObjectInputStream in=new ObjectInputStream(new FileInputStream("ProjectFilesErgasia\\xristesDatabase.dat")); //anigoume to stream
            
            
            Object obj=in.readObject();    //diabazoume to proto adikimeno
               //metabliti gia na kseroumee an brethike,,arxikopiite me false gt an brethi ginete true
            
            while(obj!=null){           //oso dn einai null auto p diabazoume
                
                if (((Xristis)obj).getUsername().equals(usernameToSearch)){   //elegxos username tou arxeio me to orisma tis klasis
                    
                                             //alagi s true an brethike
                    
                    
                    return true;
                    }
                
                obj=in.readObject();              //diabazoume ksana adikimeno
                
            
            }
            in.close();                      //klinoume to stream
            return false;       //epistrefoume to an brethike i oxi
            
            
        } catch (IOException | ClassNotFoundException ex) {
           
           
        }

        return false;
    }
    
    //----------SINARTISI POU ELEGXEI TO LOGIN---------------//
    //pernoume to username kai to password apou erxete apo to GUI
    public String chechPasswordLogin(String username,String password) throws ClassNotFoundException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        
        try {
            //anigoume stream sto arxeio pou exoume olous tous xristes
            ObjectInputStream in=new ObjectInputStream(new FileInputStream("ProjectFilesErgasia\\xristesDatabase.dat"));
            Xristis user= new Xristis();
           
            //diabazoume enan enan ton xristi apo to arxio
            while((user=(Xristis)in.readObject())!=null){
                
                
                
                //pernoume to uesrname
                if (username.equals(user.getUsername())){
                     
                    //pernoume to salt tou xristi (apo to arxio database)
                    byte [] salt=user.getSalt();
                    //pernoume ton encrypted password
                    byte [] encrypted=user.getEncrypted();
                    
                    //dialegoume sha-512
                    MessageDigest md = MessageDigest.getInstance("SHA-512");
                    //bazoume to salt gia na gini to hash
                    md.update(salt);
                    
                    //ftiaxnoume to hash se sindiasmo me to salt
                    byte[] loginUserHash = md.digest(password.getBytes());
                    //metatrepoume to hash se string
                    String hash = Base64.getEncoder().encodeToString(loginUserHash);
                    
                    
                    //diabazoume to private key apo to adistixo arxio
                    ObjectInputStream readPrivateKey = new ObjectInputStream(new FileInputStream("ProjectFilesErgasia\\privateKey.key"));
                    PrivateKey privateKey=(PrivateKey)readPrivateKey.readObject();
                    
                    //dialeksame RSA
                    Cipher cipher = Cipher.getInstance("RSA");  
                    
                    //kanoume decrypt ton kodiko pou labame apo to GUI
                    cipher.init(Cipher.DECRYPT_MODE, privateKey); 
                    byte[] decrypted=cipher.doFinal(encrypted);
                    
                    //metatrepoume se string to decrypted hash
                    String readedDecryptedHash=Base64.getEncoder().encodeToString(decrypted);
                    
                    //an teriazoun ta hash apo to GUI..me kapio hash apo tous xristes
                    if (hash.equals(readedDecryptedHash)){
                        
                        //epistrephoume to username tou xristi gia na tou kanoume login
                        //sto gui exoume elegxo opou an auto to username p epistrafike einai idio me auto p ebale o xristis
                        //to anigoume to deutero gui pou einai gia prosthiki/encrypt/decrypt/open
                        return username;}
                    else   {
                        //alios epistrepfoume ena alo string
                        return "xd";}

                }
            
            }
            
        } catch (IOException ex) {}
        
        return "xd";
    }
    
    
    ////-------SINARTISI GIA  TO REGISTER--------//
    public void completeRegister(Xristis user) throws ClassNotFoundException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException{
        
        try {
            try {
                //-----gia na ftasi kapios edw,,prepei prota na perasi apo to checkUsernameDuplicate,
                //opote pleon boroume na oloklirosoume to register
                
                //--kaloume tin sinartisi gia to salt-hash-encrypt
                //(perisoteres leptomeries stin sinartisi//)
                generateSaltedHash_PlusEnrypt(user);
            } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException | ClassNotFoundException ex) {
            }
            
            
            
            //ARRAYLIST OPOU KATHE FORA PERNAME OLOUS TOUS XRISTES POU IPARXOUN IIDI STO ARXIO.
            //KAI STO TELOS TOU ARXEIOU PERNAME TON KAINOURGIO XRISTI
            //KAI META GRAFOUME TOUS XRISTES POU BALAME STIN IDIA LISTA,,,STO ARXEIO
            //AUTO TO KANOUME ETSI OSTE NA MIN ADIKATHISATE TO ARXIO ME ENAN XRISTI KATHE FORA POU KALITE
            
            ArrayList<Xristis> listaXriston=new ArrayList();
            try{
            ObjectInputStream in=new ObjectInputStream(new FileInputStream("ProjectFilesErgasia\\xristesDatabase.dat"));
            Xristis tempXristis=new Xristis();
                

                    while((tempXristis=(Xristis)in.readObject())!=null){
                        listaXriston.add(tempXristis);}
                    in.close();

                 
            } catch (IOException ex) {
                
               

            }
            listaXriston.add(user);
            
            
            //GRAFOUME TOUS XRISTES TOU ARRAYLIST POU ANAFERTHIKE PIO PANO
            ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream("ProjectFilesErgasia\\xristesDatabase.dat"));
           
            for (Xristis temp : listaXriston) {
                    out.writeObject(temp);
                    out.flush();
		}
            
            
            
            
            //ftiaxnoume strings ta opia tha ta xrisimopoiousoume gia na dimiourgisoume tous fakelous twn xristwn
            String folder ="ProjectFilesErgasia\\usersDirectories\\"+user.getUsername();
            String folderkey ="ProjectFilesErgasia\\usersKeys\\"+user.getUsername();
            
            
            //dimiourgioume tous fakelous ton xristwn simfona me ta parapano strings
            File newFolder = new File(folder);
            File newFolderkey=new File(folderkey);
            boolean created =  newFolder.mkdir();
            newFolderkey.mkdir();
            
         
           
            
            
            //stream gia na grapsoume to simetriko klidi tou xristi
            ObjectOutputStream outkey  = new ObjectOutputStream(new FileOutputStream("ProjectFilesErgasia\\usersKeys\\"+user.getUsername()+"\\"+user.getUsername()+".key"));
            
            
            //xrisimopoioume key generator me algorithmo AES
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            
            //kanoume generate to simetriko klidi
            SecretKey  secretKey = keyGenerator.generateKey();
            
            try {
                ///pernoume to klidi kai to grafoume ston arxio
                Cipher cipherTest=Cipher.getInstance("AES");
                outkey.writeObject(secretKey);
                outkey.flush();
  
                outkey.close();
                
                
            } catch (NoSuchAlgorithmException | NoSuchPaddingException ex) { 
            }
            out.close();
            
            

            } catch (IOException ex) {
               System.out.println("File open error!!!");

            }
        
        //o xristis,,tin proti fora pou kanei register,,prepei na dimiourgisoume ena arxio gia to FILE_DIGEST.
        //Auto giati otan o xristis kanei login proti fora,,,petaei error giati to arxio dn iparxi.
        //oopote prepei na to dimiorgisoume me to pou kanei o xristis register
        try {
            //stream gia na dimiourgi to arxio filedigest
            FileOutputStream f=new FileOutputStream("ProjectFilesErgasia\\usersDirectories\\"+user.getUsername()+"\\filedigest.dat");
            Leitourgies lei=new Leitourgies();
            
            //kai kaloume tin sinartisi gia file_digest
            //stin ousia,tha dimiourgithi arxio to opio tha periexei to zeugari <filename_digest>,,gia to idio to arxio
            lei.integrityMechanism(user.getUsername());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Leitourgies.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Leitourgies.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    
    /////------------SINARTISI GIA SALT-HASH-ENCRYPT
    //orisma : ton xristi p kanei register
    public void generateSaltedHash_PlusEnrypt(Xristis xristis) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, ClassNotFoundException{
        
        
        //Secure random gia na ftiaksoume to salt
         Random randomSalt = new SecureRandom();
         
         //dimiourgia pinaka gia salt
         byte[] salt=new byte[20];
         
         //kanoume generate to salt
         randomSalt.nextBytes(salt);
         
         //bazoume to salt sto pedio tis klasis gia ton Xristi
         xristis.setSalt(salt);
         
       
         
         
         //message digest gia na bgaoume to hash
         MessageDigest md = MessageDigest.getInstance("SHA-512");
         
         //prosthetoume to hash sto message digest
         md.update(salt);
         
         //kanoume digest kai dimiourgoume pinaka apo bytes pou einai to saltedhash
         byte[] saltedHashedPass = md.digest(xristis.getPassword().getBytes());
         
         
        
         
          //stream gia na diabazoume to public key
          ObjectInputStream in1 = new ObjectInputStream(new FileInputStream("ProjectFilesErgasia\\publicKey.key"));
          
          //diabazoume sto dimosio klidi
          PublicKey publicKey=(PublicKey)in1.readObject();
         
          in1.close();
         
         //thetoume RSA algorithmo
         Cipher cipher = Cipher.getInstance("RSA");  
         //enmcrypt mode me to dimosio klidi
         cipher.init(Cipher.ENCRYPT_MODE, publicKey); 
         
         //kriptografisi tou salted hash me to dimosio klidi tis efarmogis
         byte[]encrypted=cipher.doFinal(saltedHashedPass);
         
         //bazoume to encrypted password sto pedio tou xristi
         xristis.setEncrypted(encrypted);
         
         
         
         
         
         
         
        
         
         
         
         
         
         
         
         
         
    
    }
    
    //---------------SINARITSI GIA TIN KRIPTOGRAFISI ARXEIOU---------------------//
    //orismata : to arxio pou irthe apo JFILECHOOSER sto GUI
     //         :  to uesrname tou xristi 
    public void fileToEncrypt(File file,String username){
        
      
           
        try {
            
            //stream: userKeys einai fakelos me ta klidia olon ton xriston
            //      : username ,,,, o fakelos tou xristi
            //      :: username.key .... o arxio me to klidi tou xristi
            ObjectInputStream  in = new ObjectInputStream(new FileInputStream("ProjectFilesErgasia\\usersKeys\\"+username+"\\"+username+".key"));
            //diabazoume to klidi apo to stream
            SecretKey key=(SecretKey)in.readObject();
            
            
            //ftiaxnoume to chipher me AES se enecrypt mode
            Cipher cipher = Cipher.getInstance("AES");  
            cipher.init(Cipher.ENCRYPT_MODE, key); 
            
            //anigoume stream sto file pou irthe apo to GUI
            FileInputStream fileToEncrypt = new FileInputStream(file);
            
            //pernoume ta bytes
            byte[] inputBytes = new byte[(int) file.length()];
            fileToEncrypt.read(inputBytes);
            
            //kriptografoume ta bytes pou diabasame
            byte[] outputBytes = cipher.doFinal(inputBytes);
            
            //anigoume stream gia na grapsoume to kriptografimenpo arxio
            FileOutputStream out = new FileOutputStream("ProjectFilesErgasia\\usersDirectories\\"+username+"\\"+file.getName());
	    out.write(outputBytes);
            out.flush();

	    in.close();
	    out.close();
            
            
        
        } catch (IOException |IllegalBlockSizeException|BadPaddingException| ClassNotFoundException | NoSuchAlgorithmException |NoSuchPaddingException |InvalidKeyException ex) {
        System.out.println("Wtf");
        
        }
           
        
        
    }
    
    
    
    //-------------------SINARTISI GIA DECRYPT-----------------//
    //Oopos tin sinartisi gia encrypt MONO POU ALAZOUME TO MODE S DECRYPT MODE//
    public void fileToDecrypt(File file,String username){
    try {
        
            ObjectInputStream  in = new ObjectInputStream(new FileInputStream("ProjectFilesErgasia\\usersKeys\\"+username+"\\"+username+".key"));
            SecretKey key=(SecretKey)in.readObject();
            
            Cipher cipher = Cipher.getInstance("AES");  
            cipher.init(Cipher.DECRYPT_MODE, key); 
            
            FileInputStream fileToDecrypt = new FileInputStream(file);
            byte[] inputBytes = new byte[(int) file.length()];
            fileToDecrypt.read(inputBytes);
            
            byte[] outputBytes = cipher.doFinal(inputBytes);
            
            FileOutputStream out = new FileOutputStream("ProjectFilesErgasia\\usersDirectories\\"+username+"\\"+file.getName());
	    out.write(outputBytes);
            out.flush();

	    in.close();
	    out.close();
            
            
        
        } catch (IOException |IllegalBlockSizeException|BadPaddingException| ClassNotFoundException | NoSuchAlgorithmException |NoSuchPaddingException |InvalidKeyException ex) {
        System.out.println("Wtf");
        
        }
           
    }
    
    
    
    ////------SINARTISI GIA ANIGMA ARXIOU AFOU APOKRIPTOGRAFITHI-------------------------
    public void fileToOpen(File file,String username){
        try {
            ObjectInputStream  in = new ObjectInputStream(new FileInputStream("ProjectFilesErgasia\\usersKeys\\"+username+"\\"+username+".key"));
            SecretKey key=(SecretKey)in.readObject();
            
            Cipher cipher = Cipher.getInstance("AES");  
            cipher.init(Cipher.DECRYPT_MODE, key); 
            
            FileInputStream fileToDecrypt = new FileInputStream(file);
            byte[] inputBytes = new byte[(int) file.length()];
            fileToDecrypt.read(inputBytes);
            
            byte[] outputBytes = cipher.doFinal(inputBytes);
            
            FileOutputStream out = new FileOutputStream("ProjectFilesErgasia\\usersDirectories\\"+username+"\\"+file.getName());
	       out.write(outputBytes);
               out.flush();

	       in.close();
	       out.close();
               
            
        
        Desktop desktop = Desktop.getDesktop();
        desktop.open(file);
        
            
        
        } catch (IOException |IllegalBlockSizeException|BadPaddingException| ClassNotFoundException | NoSuchAlgorithmException |NoSuchPaddingException |InvalidKeyException ex) {
        System.out.println("File is not ecrypted");
        
        }
        
    }
    
    
    ///----------------------SINARTISI GIA INTEGRITIY---------------//
    public void integrityMechanism(String username) throws NoSuchAlgorithmException, IOException, ClassNotFoundException{
        
        //stream gia to armodio arxio pou tha graftoun ta hashes kai ta filenames
        //(sto arxio grafoume mia lista pou periexei ola ta zeugarakia filename-digest
        
        ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream("ProjectFilesErgasia\\usersDirectories\\"+username+"\\filedigest.dat"));
        
        //orizoume to directory tou LOGED IN xristi
        File folder = new File("ProjectFilesErgasia\\usersDirectories\\"+username+"\\");
        
        //pernoume ola ta arxia sto directory k ta bazoume s mia lista
        File[] listOfFiles = folder.listFiles();
        
        //dimiourgoume mia lista i opia tha periexei zeugarakia filename-digest
        ArrayList<File_Digest> lista=new ArrayList();
        ///bazoume ton algorithmo digest ( SHA1 adi gia SHA3 giati i torini version tis java mas,,dn ipostirize SHA3)
        MessageDigest md = MessageDigest.getInstance("SHA1");
        
        //gia ola ta arxia ston fakelo tou xristi
        for (File file : listOfFiles) {
            //an to file einai arxio
            if (file.isFile()) {
                //dimiourgoume kainourgio zeugaraki
                File_Digest fileD=new File_Digest();
                
                //diabazoume ta bytes tou arxiou
                byte[] fileBytes = Files.readAllBytes(file.toPath());
                
                //ftiaxnoume ta encrypted bytes
                byte[] fileHash = md.digest(fileBytes);
                
                // metatrepoume ta hashed byttes s string
                String hash = Base64.getEncoder().encodeToString(fileHash);
              
                //kanoume set ta pedia apo to zeugaraki
                fileD.setfileName(file.getName());
                fileD.setfileHash(hash);
                
                //bazoume stin lista to zeugaraki
                lista.add(fileD);
                
                
                
            }
        }
        
        //grafoume tin lista me ta zeugaraki filename-digest
        out.writeObject(lista);
        out.flush();
            
            
        
        
        
    }
    
    
    //----------------SINARTISI GIA ADISTROFI INTEGRITY----------///////////
    public void integrityMechanismREVERSE(JFrame frame,String username){
     
        try {
        /////-------------ARXIKA IPOLOGIZOUME TA HASH TWN TORINON ARXEION KAI TA BAZOUME S MIA LISTA-------------------//  
            //pernoume ton fakelo me to username tou xristi p erxete (stin ousia einai o loged in xristis)
            File folder = new File("ProjectFilesErgasia\\usersDirectories\\"+username+"\\");
            
            //dimiourgxoume lista me arxeia,,stin opia bazoume ola ta arxia pou brethika ston parapano fakelo
            File[] listOfFiles = folder.listFiles();
            
            //dimiourgouma lista File_Digest
            ArrayList<File_Digest> currentFileHashes=new ArrayList();
            
            //algorithmo orisame to sha1,,,dioti exoume java 8 kai dn ipostirizete SHA3
            MessageDigest md = MessageDigest.getInstance("SHA1");
            
            //gia ola ta arxia pou iparxoun stin lista
            for (File file : listOfFiles) {
                //an to file einai arxio
                if (file.isFile()) {
                    //ftiaxnoume kainourgio File_Digest (pou sitn ousia einai klasi p eexi 2 orismata,,1 to onoma tou arxiou,kai 2 to hash>)
                    File_Digest fileD=new File_Digest();
                    
                    //diabazoume to arxio
                    byte[] fileBytes = Files.readAllBytes(file.toPath());
                    
                    //pernoume to hash tou arxeiou
                    byte[] fileHash = md.digest(fileBytes);
                    
                    //kanoume string to hash tou arxiou pio pano
                    String hash = Base64.getEncoder().encodeToString(fileHash);
                    
                    
                    //kai bazoume sta analoga pedio sto File_Digest
                    fileD.setfileName(file.getName());
                    fileD.setfileHash(hash);
                    
                    //kai bazoume to File_Digest stin lista 
                    currentFileHashes.add(fileD);
                    
                    
                    
                }
            }
            
            //--------STIN sinexia diabazoume to arxio filedigest.dat pou einai to arxio pou egine update kata tin eksodo tou xristi-------//
            //sto arxio auto grafoume mia arraylist pou exei ta <FILE_DIGEST> twn arxion
            ObjectInputStream in=new ObjectInputStream(new FileInputStream("ProjectFilesErgasia\\usersDirectories\\"+username+"\\filedigest.dat"));
           
            //opote dimiorugoume mia lista File_Digest kai diabazoume to adikimeno tou arxiou
            ArrayList<File_Digest> previousFileHashes=new ArrayList();
            previousFileHashes=(ArrayList<File_Digest>)in.readObject();
            
            
            //elegxos gia to an eginan alages sto arxio
            //me liga logia:  
            //for gia oso einai to mikos tis lista me ta file_digest
            //elegxoume gia kathe stixia an teriazi to file name
            //efoson teriazi elegxoume an ta hash einai idia
           
            
            String chang=" ";
            boolean found=false;
            for (int i=0;i<previousFileHashes.size();i++){
                    if (previousFileHashes.get(i).getfileName().equals(currentFileHashes.get(i).getfileName())){
                        if (previousFileHashes.get(i).getfileName().equals("filedigest.dat")){
                        }
                        else if (previousFileHashes.get(i).getfileHash().equals(currentFileHashes.get(i).getfileHash())){
                            
                        }
                        else{
                            
                                found=true;
                             chang=chang + "CHANGE IN FILE :   "+currentFileHashes.get(i).getfileName()+"\n";
                             System.out.println(chang);
                            
                        }
                    }
            
            }
          if (found==true)
                JOptionPane.showMessageDialog(frame, chang);
            
      
        } catch (IOException | NoSuchAlgorithmException | ClassNotFoundException ex) {
            Logger.getLogger(Leitourgies.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    //--SINARTISI pou tin treksame mia fora stin proti ekinisi tou programatos
    //gia na dimiourgisoume to zeugos private/public keys
    public void firstRun() throws NoSuchAlgorithmException, FileNotFoundException, IOException{
         
	
	 
        //pair generator
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
	//megethos klidiion
        keyGen.initialize(1024);
        //generate tou pair
        KeyPair pair = keyGen.generateKeyPair();
        
        //pernoume to private klidi kai to public
	PrivateKey privateKey = pair.getPrivate();
	PublicKey publicKey = pair.getPublic();
        
        
        
        ///ftiaxnoume adistixa arxeia opou bazoume ta kleidia
        ObjectOutputStream out  = new ObjectOutputStream(new FileOutputStream("ProjectFilesErgasia\\privateKey.key"));
        out.writeObject((PrivateKey)privateKey);
        
        out.flush();
        
        
        
        ObjectOutputStream out1  = new ObjectOutputStream(new FileOutputStream("ProjectFilesErgasia\\publicKey.key"));
        out1.writeObject(publicKey);
        out1.flush();
  

        
        
        out.close();
        out1.close();
        
        
        
        
    }
        
   
    
}
