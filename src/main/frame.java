package main;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;




public class frame
{
	private static Connection con = null;
	private static String dbHost = "localhost"; // Hostname
	private static String dbPort = "3306";      // Port -- Standard: 3306
	private static String dbName = "skypvp";   // Datenbankname
	private static String dbUser = "root";     // Datenbankuser
	private static String dbPass = "123456";   // Datenbankpasswort
	//test
	

   public static void main (String[] args)
   {
	   getInstance();
	   update("CREATE TABLE IF NOT EXISTS mysql(name varchar(100) UNIQUE, email varchar(100), password varchar(100))");
      JFrame frame = new JFrame ("Rechner");
      frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
      frame.setLayout(null);
      frame.setSize(500,300);
      Color cl = Color.getHSBColor(508, 463, 320);
      frame.getContentPane().setBackground(cl);
      //Name
      JTextField field1 = new JTextField(16);  
      frame.add(field1);
      field1.setBounds(160,40,160,30);
	
      JLabel label = new JLabel("Name");
      label.setBounds(120, 30, 300, 50);
      label.setText("<html>Name:</html>");
      frame.getContentPane().add(label);
      
      //E-Mail
      JPasswordField field2 = new JPasswordField(16);
      frame.add(field2);
      field2.setBounds(160, 80, 160, 30);
      
      JLabel label2 = new JLabel("Password");
      label2.setBounds(95, 70, 300, 50);
      label2.setText("<html>Passwort:</html>");
      frame.getContentPane().add(label2);
      
      //Button
      JButton button = new JButton ("Anmelden");
      button.setBounds(160,120,160,40);
      button.addActionListener (new ActionListener() {
         
		@SuppressWarnings("deprecation")
		@Override
         public void actionPerformed (ActionEvent e)
         {
        	 String eingabePassword = field2.getText();
        	 String eingabeName = field1.getText();
        	 if(getInstance() == null){
        		 Object[] options= { "Ok" };
     			JOptionPane.showOptionDialog(null, "Verbindung konnte nicht aufgebaut werden", "ERROR", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,null,options,options[0]);
     			return;
        	 } else {
            if(getName(eingabeName) == true && eingabePassword.equals(getPassword(eingabeName))){

        		 JFrame menu = new JFrame("Menu");
            	menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            	menu.setSize(1240, 800);
            	menu.setLayout(null);
            	menu.setLocationRelativeTo(null);
            	menu.getContentPane().setBackground(Color.DARK_GRAY);
            	frame.dispose();
            	
            	
            	menu.setVisible(true);
            	
            
            } else {
            	Object[] options= { "Ok" };
    			JOptionPane.showOptionDialog(null, "Passwort oder Name falsch", "ERROR", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,null,options,options[0]);
            }
        	 
         }
         }
      });
      
      
      JButton button2 = new JButton("Registrieren");
      button2.setBounds(160, 170, 160, 40);
      button2.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JFrame register = new JFrame("Registrieren");
			register.setLayout(null);
			register.setSize(400, 500);
			
				Color cl = Color.getHSBColor(5208, 463, 320);
				register.getContentPane().setBackground(cl);
		      //Name
		      JTextField field3 = new JTextField(16);  
		      register.add(field3);
		      field3.setBounds(120,90,160,30);
				
		      JLabel label3 = new JLabel("Name");
		      label3.setBounds(80, 80, 300, 50);
		      label3.setText("<html>Name:</html>");
		      register.getContentPane().add(label3);
		      
		    //Password
		      JTextField field4 = new JTextField(16);
		      register.add(field4);
		      field4.setBounds(120, 130, 160, 30);
		      
		      JLabel label4 = new JLabel("email");
		      label4.setBounds(77, 120, 300, 50);
		      label4.setText("<html>E-Mail:</html>");
		      register.getContentPane().add(label4);
		      
		      //Password
		      JPasswordField field5 = new JPasswordField(16);
		      register.add(field5);
		      field5.setBounds(120, 170, 160, 30);
		      
		      JLabel label5 = new JLabel("password");
		      label5.setBounds(55, 160, 300, 50);
		      label5.setText("<html>Passwort:</html>");
		      register.getContentPane().add(label5);
		      
		      JButton buttonRegister = new JButton("Registrieren");
		      buttonRegister.setBounds(120, 210, 160, 40);
		      buttonRegister.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String eingabeName = field3.getText();
					String eingabeEmail = field4.getText();
					String eingabePW = field5.getText();
					boolean name = false;
					boolean email = false;
					
					if(eingabeEmail != null || eingabeName != null || eingabePW != null){
					
					if(getName(eingabeName) == true){
						name = true;
					}
					
					if(getEmail(eingabeEmail) == true){
						email = true;
					}
					
					if(email == true || name == true){
						Object[] options= { "Ok" };
		    			JOptionPane.showOptionDialog(null, "Name oder Email wird schon verwendet", "ERROR", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,null,options,options[0]);
						return;
					} else {
						if(eingabePW.length() >=8){
							EmailValidator emailValidator = new EmailValidator();
							   if(!emailValidator.validate(field4.getText().trim())) {
							        System.out.print("Invalid Email ID");
							        field4.setBackground(Color.red);
							        Object[] options= { "Ok" };
					    			JOptionPane.showOptionDialog(null, "Invalid E-Mail", "ERROR", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,null,options,options[0]);
					    			return;
							   }
							register(eingabeName, eingabeEmail, eingabePW);
							System.out.println(eingabeName + " wurde mit der E-Mail " + eingabeEmail + " registriert");
							register.hide();
							Object[] options= { "Ok" };
			    			JOptionPane.showOptionDialog(null, "Erfolgreich registriet", "ERROR", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,null,options,options[0]);
						} else {
							Object[] options= { "Ok" };
			    			JOptionPane.showOptionDialog(null, "Passwort ist zu kurz", "ERROR", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,null,options,options[0]);
						}
						
					}
					
					}
					
				}
			});
			
			
			
			register.setVisible(true);
			register.add(buttonRegister);
		}
	});
    	  
      
      frame.add(button2);
      frame.add(button);
      frame.setVisible (true);
   }
	
    
   private frame(){
       try {
           Class.forName("com.mysql.jdbc.Driver"); // Datenbanktreiber für JDBC Schnittstellen laden.
    
           // Verbindung zur JDBC-Datenbank herstellen.
           con = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+ dbPort+"/"+dbName+"?"+"user="+dbUser+"&"+"password="+dbPass);
           System.out.println("Verbindung erfolgreich");
       } catch (ClassNotFoundException e) {
           System.out.println("Treiber nicht gefunden");
           e.printStackTrace();
       } catch (SQLException e) {
           System.out.println("Verbindung nicht moglich");
           System.out.println("SQLException: " + e.getMessage());
           System.out.println("SQLState: " + e.getSQLState());
           System.out.println("VendorError: " + e.getErrorCode());
       }
     }
    
   private static Connection getInstance(){
       if(con == null)
           new frame();
       return con;
   }
   
   public static void update(String qry) {
	    try {
	      Statement st = (Statement) getInstance().createStatement();
	      st.executeUpdate(qry);
	      st.close();
	    } catch (Exception localException) {
	    }
	  }
   
	  public static ResultSet query(String qry) {

		    ResultSet rs = null;

		    try {
		      Statement st = (Statement) getInstance().createStatement();
		      rs = st.executeQuery(qry);
		    } catch (Exception localException) {
		    }
		    return rs;
		  }
   
	public static boolean getName(String name) {

		ResultSet rs = query("SELECT * FROM mysql WHERE name = '" + name + "' LIMIT 1");
		try {
			return rs.next();
		} catch (SQLException e) {
		}
		return false;
	}
	
	public static boolean getEmail(String email) {

		ResultSet rs = query("SELECT * FROM mysql WHERE email = '" + email + "' LIMIT 1");
		try {
			return rs.next();
		} catch (SQLException e) {
		}
		return false;
	}
	
	public static String getPassword(String name) {

		ResultSet rs = query("SELECT password FROM mysql WHERE name = '" + name + "' LIMIT 1");
		String array = new String();
		try {
			while(rs.next()) {
				array = rs.getString("password");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return array;
		}
		return array;
	}
	
	public static void register(String name, String email, String pw){
		update("INSERT INTO mysql (name, email, password) VALUES ('" + name + "','" + email + "', '" + pw + "')");
	}
	
}
