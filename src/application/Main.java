package application;
	
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
	 public static HashMap<String,HashMap<String,String>> usersmap = new HashMap<>();
	 public static double version=0.1;
	 public VBox root = new VBox();
	 public  TextField login_t=new TextField();
	 public TextField pass_t=new TextField();
	 public Button en=new Button("Ввійти");
	 public static Label token=new Label("");
	 boolean b=false;
	 public  TextField la=new TextField();
	 public TextField pa=new TextField();
	 public Button a=new Button("Додати");
	 public Label auth=new Label("Auth");
	 public Label reg=new Label("Reg");
	 
	  void findallusers() {
		 String lg=login_t.getText();
		 String pw=pass_t.getText();
		 MessageDigest m = null;
			try {
				m = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		        m.update(pw.getBytes(),0,pw.length());
		       pw=new BigInteger(1,m.digest()).toString(16);
		       System.out.println(pw);
		        
		  String urlfind;
		      urlfind = "http://vasylko.zzz.com.ua/index.php/api/finduser/";
		      usersmap.clear();
		      String [] fieldsfind = {
		    		  "login:"+lg+";"+"pass:"+pw+";"
		      };
		      ConnectionFactory connectionfind = new ConnectionFactory(fieldsfind,urlfind,version);
		     String responsefind = connectionfind.buildConnection();
		            String s=responsefind.substring(1, 33);
		            	login_t.setVisible(false);
		            	pass_t.setVisible(false);
		            	en.setVisible(false);
		            	la.setVisible(false);
		            	pa.setVisible(false);
		            	a.setVisible(false);
		            	reg.setVisible(false);
		            	auth.setVisible(false);
		            	token.setText("Ваш токен "+s);
		            	root.getChildren().add(token);
		 }
	  
	  void add() {
		  String urladd = "http://vasylko.zzz.com.ua/index.php/api/adduser/";
		  String pw=pa.getText();
		  MessageDigest m = null;
			try {
				m = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		        m.update(pw.getBytes(),0,pw.length());
		        pw=new BigInteger(1,m.digest()).toString(16);
		  String [] fieldsadd = {
		      "login:"+la.getText()+";",
		      "pass:"+pw+";"
		  }; 
		  pa.setText("");
		  la.setText("");
		  ConnectionFactory connectionadd = new ConnectionFactory(fieldsadd,urladd,version);
		  String responseadd = connectionadd.buildConnection();
		  }
	  
	@Override
	public void start(Stage primaryStage) {
		try {
			Scene scene = new Scene(root,300,300);
			en.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					// TODO Auto-generated method stub
					findallusers();
				}});
			
			a.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					// TODO Auto-generated method stub
					add();
				}});
			root.getChildren().add(auth);
			root.getChildren().add(login_t);
			root.getChildren().add(pass_t);
			root.getChildren().add(en);
			root.getChildren().add(reg);
			root.getChildren().add(la);
			root.getChildren().add(pa);
			root.getChildren().add(a);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}