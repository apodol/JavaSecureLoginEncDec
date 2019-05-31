
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.Border;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;


public class Login_Register_Gui extends JFrame{
    private JTabbedPane Parathira;     //JTabbedPane  sto opio tha booun ta panel login k register 
    private JPanel Login;          //panel gia login
    private JPanel Register;          //panel gia register
    private JPanel Register_row1,Register_row2,Register_row3,Register_row4,Register_row5,Register_row6; //rows gia register
    private JPanel Login_row1,Login_row2,Login_row3,Login_row4;   //rows gia login
    
    Login_Register_Gui(){
        
        super("Super Crypt");
        setLayout(new BorderLayout());
        setSize(350,300);
        
        Parathira = new JTabbedPane();
        
        Login = new JPanel(new GridLayout(5,1,10,10));
        Register=new JPanel(new GridLayout(6,1,1,1));
     
  //---------------------REGISTER------------------------------------//
  
        //dimoiorgume labels kai koubia gia to register
        Register_row1=new JPanel(new FlowLayout());              //row 1 onoma xristi
        JLabel onomaxristi = new JLabel("Onomateponimo:    ");
        JTextField onomaxristiF=new JTextField(15);
        Register_row1.add(onomaxristi);
        Register_row1.add(onomaxristiF);
        
        Register_row2=new JPanel(new FlowLayout());                   //row 2 username
        JLabel sinthimatiko=new JLabel("Username:               ");
        JTextField sinthimatikoF=new JTextField(15);
        Register_row2.add(sinthimatiko);
        Register_row2.add(sinthimatikoF);
        
         
        Register_row3=new JPanel(new FlowLayout());        //row 3 password
        JLabel password=new JLabel("Password:                ");
        JTextField passwordF=new JTextField(15);
        Register_row3.add(password);
        Register_row3.add(passwordF);
        
        
        
        Register_row4=new JPanel(new FlowLayout());               //row 4 verify password
        JLabel verifyPassword=new JLabel("Verify Password:    ");
        JTextField verifyPasswordF= new JTextField(15);
        Register_row4.add(verifyPassword);
        Register_row4.add(verifyPasswordF);
        
        
        
        Register_row5=new JPanel(new FlowLayout());                    //row 5 gia koubia 
        JButton clear=new JButton("Clear");
        clear.setBackground(Color.GRAY);           //koubi opou sbini ola ta pedia
        clear.addActionListener(new ActionListener(){   
            public void actionPerformed(ActionEvent e){   
                    verifyPasswordF.setText("");   
                    passwordF.setText("");
                    sinthimatikoF.setText("");
                    onomaxristiF.setText("");
                    
            }});
        
        JButton sumbit=new JButton("Sumbit");
        sumbit.setBackground(Color.GRAY);
        Register_row5.add(clear);
        Register_row5.add(sumbit);
        
        Register_row6=new JPanel(new FlowLayout());
        JLabel statusMessage=new JLabel();
        statusMessage.setBackground(Color.red);    //status message gia na enimeronoume ton xristi an egine i oxi to register
        statusMessage.setForeground(Color.red);
        Register_row6.add(statusMessage);
        
         sumbit.addActionListener(new ActionListener(){         ///koubi  gia sumbit
            public void actionPerformed(ActionEvent e){   
                    Leitourgies lei=new Leitourgies(); //dimiourgia klassi gia tis methodous
                    
                    
                    //if gia na doume an oi o kodikos,,kai o verify kodikos einai idios
                    if(passwordF.getText().equals(verifyPasswordF.getText())){
                        
                        
                            //arxika kaloume tin methodo gia na dei an iparxi idio username sto arxio me tous xristes
                            boolean found=lei.checkUsernameDuplicate(sinthimatikoF.getText());

                            //an epistrafike false ,,diladi dn brethike idio username
                            if (found==false){
                                //dimiourgoume enan neo xristi ,opou tou pername ta pedia tou register
                                Xristis newUser=new Xristis(onomaxristiF.getText(),sinthimatikoF.getText(),passwordF.getText());

                                try {

                                    //kaloume tin sinartisi autin,,gia na oloklirosi to register
                                    //-perisoteres leptomeries ,sta sxolia tis sinartisis---///
                                    lei.completeRegister(newUser);
                                } catch (ClassNotFoundException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException ex) {
                                    Logger.getLogger(Login_Register_Gui.class.getName()).log(Level.SEVERE, null, ex);
                                }

                                statusMessage.setText("EGRAFI ME USERNAME: <  "+sinthimatikoF.getText()+"  > EPITIXIS !!!");

                            }
                            else   {
                               statusMessage.setText("TO USERNAME: <  "+sinthimatikoF.getText()+"  > IPARXI !!!");


                            }}
                    else
                        statusMessage.setText("OI KODIKI POU EBALES DN TERIAZOUN !!!");
            }});    
        
        Register.add(Register_row1);
        Register.add(Register_row2);
        Register.add(Register_row3);
        Register.add(Register_row4);
        Register.add(Register_row5);
        Register.add(Register_row6);
        
        
    
 //---------------------LOGIN------------------------------//
        Login_row1=new JPanel();                            //row 1 gia username
        Login_row1.setLayout(new FlowLayout());
        JLabel username = new JLabel("Username:                   ");
        JTextField usernameF=new JTextField(15);
        Login_row1.add(username);
        Login_row1.add(usernameF);
         
        Login_row2=new JPanel();                             //row 2 gia password
        Login_row2.setLayout(new FlowLayout());
        JLabel kodikosLogin=new JLabel("Password:                   ");
        JTextField kodikosLoginF=new JTextField(15);
        Login_row2.add(kodikosLogin);
        Login_row2.add(kodikosLoginF);
        
        
        
        Login_row3=new JPanel();                                 //ROW 3 GIA KOUBIA
        Login_row3.setLayout(new FlowLayout());
        JButton clearLogin=new JButton("Clear");
        JLabel minima=new JLabel("");
        minima.setBackground(Color.red);
        minima.setForeground(Color.red);
        clearLogin.setBackground(Color.gray);            //button gia na katharisoume ta pedia
        clearLogin.addActionListener(new ActionListener(){   
            public void actionPerformed(ActionEvent e){   
                    usernameF.setText("");   
                    kodikosLoginF.setText("");
            }});
        
        
        JButton sumbitLogin=new JButton("Sumbit");                 //koubi gia sumbit
        sumbitLogin.addActionListener(new ActionListener(){   
            public void actionPerformed(ActionEvent e){   
                    Leitourgies lei=new Leitourgies();
                try {
                    //kaloume tin methodo checkpasswordlogin
                    //i opia epistrefi enas string
                    //to string einai to username pou dosame an einai epitixis
                    //alios einai ena oti na ne string,,,auto to kanoume gia ton elegxo pio kato
                   String incomingUsername = lei.chechPasswordLogin(usernameF.getText(),kodikosLoginF.getText());
                   
                   //elegxos an to username p edose o xristis einai idio me auto pou epistrafike
                   //diladi to login prepei na ginei epitixos
                   if (incomingUsername.equals(usernameF.getText())){
                       
                       //kaloume tin methodo gia to integrity twn arxeion molis kanei login o xristis
                       
                       lei.integrityMechanismREVERSE(Login_Register_Gui.this,incomingUsername);
                       
                       //kai energopoioume to EPOMENO GUI
                       CryptGui crypt=new CryptGui(Login_Register_Gui.this,usernameF.getText());}
                   else
                       minima.setText("LATHOS CREDENTIALS!!!");
                   
                } catch (ClassNotFoundException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
                }
                    
            }});
        sumbitLogin.setBackground(Color.gray);
        Login_row3.add(clearLogin);
        Login_row3.add(sumbitLogin);
        
        
        Login_row4=new JPanel(new FlowLayout());
        Login_row4.add(minima);
        
        
        
        
        
        
        Login.add(Login_row1);
        Login.add(Login_row2);
        Login.add(Login_row3);
        Login.add(Login_row4);
        
        
        
        
        
        
        Parathira.add("Login",Login);
        Parathira.add("Register",Register);
        add(Parathira);
        
        
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        
    }
}
