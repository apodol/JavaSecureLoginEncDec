
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.filechooser.FileView;

//-----------------KLASI GIA AFOU KANEI LOG IN O XRISTIS--------------------------------////
public class CryptGui extends JFrame{
    String username;                          //TO USERNAME tou xristi pou kanei login
    JFrame incomingFrameToClose_orOpen;         //to login/register frame,,gia na to kliso,,kai na to ksanaanikso an kanei logout o xristis
    private JTabbedPane Parathira;     //JTabbedPane  sto opio tha booun ta tabs prosthiki,encrypt,decrypt,open
              
    private JPanel Prosthiki,Encrypt,Decrypt,Open;          //panel gia prosthiki,encrypt.decrypt.open
    private JPanel Prosthiki_row1,Prosthiki_row2,Prosthiki_row3; //rows gia ta jcomponents,,PROSTHIKI
    private JPanel Encrypt_row1,Encrypt_row2;               //rows gia ta jcomponents,,ENCRYPT
    private JPanel Decrypt_row1,Decrypt_row2;               //rows gia ta jcomponents,,DECRYPT
    private JPanel Open_row1,Open_row2;                     //rows gia ta jcomponents,,OPEN
    
    
    //CONSTRUCTOR
    CryptGui(JFrame frame,String username){ 
        super("CryptoDecrypto");
        this.username=username;                   //usertname tou xristi
        incomingFrameToClose_orOpen=frame;          //to frame login/reigster
        incomingFrameToClose_orOpen.setVisible(false); //klino to frame login/register
        
      //-----------------ORIZOUME TA JPANELS -----------------------------------//  
        Parathira =new JTabbedPane();                       
        
        Prosthiki=new JPanel(new GridLayout(4,1,10,10));
        Encrypt=new JPanel(new GridLayout(6,1,10,10));
        Decrypt=new JPanel(new GridLayout(6,1,10,10));
        Open=new JPanel();
       
      //--------------------PROSTHIKI------------------------------------------//
        Prosthiki_row1=new JPanel(new FlowLayout());      //ftiaxnoume ta rows 
        Prosthiki_row2=new JPanel(new FlowLayout());
        Prosthiki_row3=new JPanel(new FlowLayout());
        
        JLabel fileMovedMessage=new JLabel();                   //label gia na enimerono ton xristi an egine i prosthiki
        Prosthiki_row2.add(fileMovedMessage);                   //bazoume to labbel sto row   
        
        fileMovedMessage.setForeground(Color.red);
        
        JLabel messageSelect=new JLabel("Click to select a file       ");   //label kai button gia tin prosthiki
        JButton selectFile=new JButton("Select file");
        selectFile.setBackground(Color.GRAY);
        selectFile.addActionListener(new ActionListener(){    //action listener tou koubiou gia prosthiki
            public void actionPerformed(ActionEvent e){   
                JFileChooser chooser= new JFileChooser();            //file chooser 

                int choice = chooser.showOpenDialog(CryptGui.this);        //anigoume to file chooser

                File chosenFile = chooser.getSelectedFile();      
                if (choice == JFileChooser.APPROVE_OPTION) {  //an auto p dialekse o xristis einai apodexto
                        
                     chosenFile.renameTo(new File("ProjectFilesErgasia\\usersDirectories\\"+username+"\\"+chosenFile.getName())); //metafora tou arxeiou ston fakelo tou xristi
                     fileMovedMessage.setText("To arxeio: "+chosenFile.getName()+" prostethike epitixos"); //bazoume to text na di3oume ston xristi
                } else if (choice == JFileChooser.CANCEL_OPTION) { //an patise to cancel
                    
                    }
         }});
        
        JButton logout=new JButton("Logout");  //lougout koubi
        
        logout.addActionListener(new ActionListener(){    //actionlisterne logout
            public void actionPerformed(ActionEvent e){   
               Leitourgies lei=new Leitourgies();   //klasi leitourgies
                try {
                    lei.integrityMechanism(username);   //otan o xristis kanei logout ipologizode ta HASHES ton arxeion
                } catch (NoSuchAlgorithmException | IOException | ClassNotFoundException ex) {
                    Logger.getLogger(CryptGui.class.getName()).log(Level.SEVERE, null, ex);
                }
                CryptGui.this.setVisible(false);   //klinoume to frame otan kanei logout o xristis
               
               incomingFrameToClose_orOpen.setVisible(true);   //anigoume to palio frame
                
                
             }
                
                 
                
                  
            });
        Prosthiki_row3.add(logout);      //prosthiki ton JCOMPONENTS
        Prosthiki_row1.add(messageSelect);
        Prosthiki_row1.add(selectFile);
        Prosthiki.add(Prosthiki_row1);
        Prosthiki.add(Prosthiki_row2);
        Prosthiki.add(Prosthiki_row3);
        
       
  //------------------------ENCRYPT---------------------//
        Encrypt_row1=new JPanel(new FlowLayout());   // OPOS PANO
        Encrypt_row2=new JPanel(new FlowLayout());
        Encrypt_row2=new JPanel(new FlowLayout());    // OPOS PANO
        JLabel messageSelectEn=new JLabel("Click to select file to encrypt       ");      /// OPOS PANO
        JButton selectFileEn=new JButton("Select file");
        JLabel minima=new JLabel("");
        
        selectFileEn.setBackground(Color.gray);
        selectFileEn.addActionListener(new ActionListener(){   
            public void actionPerformed(ActionEvent e){   
                

              //FILE CHOOSER ,,pou ton bazoume sto directory tou xristi pou einai loged in
                JFileChooser chooser = new JFileChooser(new File("ProjectFilesErgasia\\usersDirectories\\"+username)); 
                
                //kanoume to isTravesable false,,,etsi oste o xristis NA MIN BOREI NA ALAKSI DIRECTORY
                chooser.setFileView(new FileView() {
                    @Override
                    public Boolean isTraversable(File chosenFile) {
                        
                        return false;
                    }
                });
                
                int returnVal = chooser.showOpenDialog(CryptGui.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {       
                    
                    Leitourgies enc=new Leitourgies();     //klasi leirgourgies gia na xrisimopiiousa tis methodous
                    enc.fileToEncrypt(chooser.getSelectedFile(), username); //methodos gia ecrypt  pou pernei username k to arxio p epelse o xristis
                    minima.setBackground(Color.red);
                    minima.setForeground(Color.red);
                    minima.setText("File encrypted succesfully!!!");
                    
                    
                }
                 
                
                  
            }});
        
        
        
        
        //prosthiki ton jcomponents
        Encrypt_row1.add(messageSelectEn);
        Encrypt_row1.add(selectFileEn);
        Encrypt_row2.add(minima);
        Encrypt.add(Encrypt_row1);
        Encrypt.add(Encrypt_row2);
        
        
     //----------------------------------DECRYPT-----------------------------------------------//
        //katalila jcompoentnts PERIPOU OPOS PANO me diaforetika onoma
        Decrypt_row1=new JPanel(new FlowLayout());
        Decrypt_row2=new JPanel(new FlowLayout());
        JLabel messageDecrypt=new JLabel("Click to select file to decrypt       ");
        JButton selectFileDec=new JButton("Select file");
        JLabel minima1=new JLabel("");
        selectFileDec.setBackground(Color.gray);
        
        //akribos opos pano,,mono p kalite i methodos apo tin klasi leitourgies,,,,gia decrypt
        selectFileDec.addActionListener(new ActionListener(){    //action listener sto koubi gia decrypt
            public void actionPerformed(ActionEvent e){   
                

              
                JFileChooser chooser = new JFileChooser(new File("ProjectFilesErgasia\\usersDirectories\\"+username)); 
                
                
                chooser.setFileView(new FileView() {
                    @Override
                    public Boolean isTraversable(File chosenFile) {
                        
                        return false;
                    }
                });
                int returnVal = chooser.showOpenDialog(CryptGui.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    System.out.println("File selected "+ chooser.getSelectedFile().getName());
                    Leitourgies enc=new Leitourgies();
                    enc.fileToDecrypt(chooser.getSelectedFile(), username);
                    minima1.setText("File decrypted succesfully");
                    minima1.setBackground(Color.red);
                    minima1.setForeground(Color.red);
                }
                 
                
                  
            }});
       Decrypt_row1.add(messageDecrypt);
       Decrypt_row1.add(selectFileDec);
       Decrypt_row2.add(minima1);
       Decrypt.add(Decrypt_row1);
       Decrypt.add(Decrypt_row2);
       
     //-------------------------------------OPEN----------------------------------------//
     
     //opos pano oson afora tin epilogi tou arxeiou. alla otan epilexti to arxeio kaloume tin methodo  pou einai gia anigma arxeiou
     Open_row1=new JPanel(new FlowLayout());
     JLabel messageOpen=new JLabel("Click to open file       ");
     JButton buttonOpen=new JButton("Select file");
     buttonOpen.addActionListener(new ActionListener(){   
            public void actionPerformed(ActionEvent e){   
                

              
                JFileChooser chooser = new JFileChooser(new File("ProjectFilesErgasia\\usersDirectories\\"+username)); 
                
                
                chooser.setFileView(new FileView() {
                    @Override
                    public Boolean isTraversable(File chosenFile) {
                        
                        return false;
                    }
                });
                int returnVal = chooser.showOpenDialog(CryptGui.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    System.out.println("File selected "+ chooser.getSelectedFile().getName());
                    Leitourgies enc=new Leitourgies();
                    enc.fileToOpen(chooser.getSelectedFile(), username);  //methodos tis kklasis leitourgies gia anigma arxeiou
                }
                 
                
                  
            }});
     buttonOpen.setBackground(Color.GRAY);
     
     //prosthiki ton jcomponents
     Open_row1.add(messageOpen);
     Open_row1.add(buttonOpen);
     Open.add(Open_row1);
        
        
        Parathira.add(Prosthiki,"Prosthiki");
        Parathira.add(Encrypt,"Encrypt");
        Parathira.add(Decrypt,"Decrypt");
        Parathira.add(Open,"Open file");
        
       
        add(Parathira);
        setSize(350,300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
}
