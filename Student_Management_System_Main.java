package application;
	
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


								//>>JAVA OOP
								//STUDENT CLASS
class Student
{
          public String getStdNam() {
		return stdNam;
	}
	public void setStdNam(String stdNam) {
		this.stdNam = stdNam;
	}
	public int getStdRolNo() {
		return stdRolNo;
	}
	public void setStdRolNo(int stdRolNo) {
		this.stdRolNo = stdRolNo;
	}
	public String getStdGrade() {
		return stdGrade;
	}
	public void setStdGrade(String stdGrade) {
		this.stdGrade = stdGrade;
	}
	public String getStdClass() {
		return stdClass;
	}
	public void setStdClass(String stdClass) {
		this.stdClass = stdClass;
	}
			
          @Override
		public String toString() {
			return "Student [stdNam=" + stdNam + ", stdRolNo=" + stdRolNo + ", stdGrade=" + stdGrade + ", stdClass="
					+ stdClass + "]";
		}
          public Student(String a , int b , String c , String d)
          {
        	 this.stdNam = a;
        	 this.stdRolNo = b;
        	 this.stdGrade = c;
        	 this.stdClass = d;
          }
          
          private String stdNam;  
		private int stdRolNo;
          private String stdGrade;
          private String stdClass;
          
          
}
									// STUDENT MANAGEMENT SYSTEM CLASS

class StudentManagementSystem
{
	
						//>> JAVA JDBC TO STORE STUDENT DATA in MySQL database
	                           //*REGISTER DRIVER
	                            //**CREATE CONNECTION
	                             //**CREATE STATEMENT
	                               //**EXECUTE QUERIES -- INSERT, DELETE, SEARCH, 
	                                                  //    UPDATE
	                                //CLOSE CONNECTION
	 
  private Connection con;
   StudentManagementSystem()
   {
       try {
           //  *REGISTER DRIVER
      	 Class.forName("com.mysql.cj.jdbc.Driver");
      	    String url = "jdbc:mysql://127.0.0.1/db";
      	  //**CREATE CONNECTIONs
      	    con=DriverManager.getConnection(url,"root","root");
           if (con != null) {
               System.out.println("Connected to the database!");
             // Close the connection when done
               
           } else {
               System.out.println("Failed to connect to the database!");
           }
       }catch(SQLException seer)
       {
      	 System.out.println(seer);
       }
       catch(Exception etrr)
       {
      	 System.out.println(etrr);
       }
   }
   
   public int InsertStudent(Student s)throws Exception
   {
	   //**CREATE STATEMENT
	   
	   Statement st=con.createStatement();
       
       // EXECUTE QUERIES 
       String query="insert into Student(stdNam,stdRolNo,stdGrade,stdClass) values('"+s.getStdNam()+"','"+s.getStdRolNo()+"','"+s.getStdGrade()+"','"+s.getStdClass()+"' )";
        int rs = st.executeUpdate( query );
       
       
  	 System.out.println(rs);   
  	 return rs;
   }
   
   public ResultSet displayStudent() throws Exception
   {
	   //**CREATE STATEMENT
	   Statement statement = con.createStatement();
	   ResultSet rse = null;
       // EXECUTE QUERIES 
       String query = "SELECT * FROM Student";
       rse = statement.executeQuery(query);
       
       return rse;
   }
   
   public int executUpdate(int r , String g , String c , int id)
   {
	   //**CREATE STATEMENT
	   Statement stt;
	   int rowsUpdated=-1;
	   try {
		
		   stt = con.createStatement();
		   //EXECUTE QUERIES 
		   String updateQuery = "UPDATE Student " +
                   "SET stdRolNo = '" + r + "', " +
                   "stdGrade = '" + g + "', " +
                   "stdClass = '" + c + "' " +
                   "WHERE stdId = " + id;

           // Execute the update query
            rowsUpdated = stt.executeUpdate(updateQuery);
           
		   
	   }catch(Exception e)
	   {
		   System.out.println(e);
	   }
	   return rowsUpdated; 
   }
   
   
   public ResultSet searchStudent(int rn , String cls) throws Exception
   {
	   //**CREATE STATEMENT
	   Statement statement = con.createStatement();
	   ResultSet rse = null;
       // EXECUTE QUERY
       String query = "SELECT * FROM Student where stdRolNo = ? and stdClass = ?";
       PreparedStatement preparedStatement = con.prepareStatement(query);
       
       // Set the parameters for the PreparedStatement
       preparedStatement.setInt(1, rn);
       preparedStatement.setString(2, cls);
   	 rse = preparedStatement.executeQuery();   
//       rse = statement.executeQuery(query);
       
       return rse;
   }
   
   public int deleteStudent(int rn , String cls) 
   {
	   
	   int ans=-1;
	   String deleteSQL = "DELETE FROM Student WHERE stdRolNo = "+rn+" and stdClass = "+cls;
	   try {
		   //**CREATE STATEMENT
		   Statement statement = con.createStatement();
		    statement = con.createStatement();
		    ans = statement.executeUpdate(deleteSQL);
		    System.out.println(ans + " row(s) deleted successfully.");
		} catch (SQLException e) {
		    e.printStackTrace();
		}
	   return ans;
   }
   
   public void closeCon()
   {
	
	   //CLOSE CONNECTION
	   try {
		con.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   
   }
   
   
	
	
	
}


                               //JAVAFX TO BUILD GUI

public class Main extends Application {

	  public static void main(String[] args) {
	        launch(args);
	    }

	    @Override
	    public void start(Stage primaryStage)  {
	        primaryStage.setTitle("Main Window");

	        // Create a button
	        Text labelText = new Text("Student Management System");
	        Button button = new Button("ADD A STUDENT");
	        Button button1 = new Button("EDIT STUDENT RECORD");
	        Button button2 = new Button("SEARCH STUDENT RECORD");
	        Button button3 = new Button("DISPLAY STUDENT RECORDS");
	        
	        Button button4 = new Button("EXIT APPLICATION");
	        Button button5 = new Button("DELETE STUDENT RECORD");

	        // Define an action for the button
	        button.setOnAction(e -> openInsertWindow(primaryStage));
	        
	            //ONCLICKS
	        button1.setOnAction(e -> {
				try {
					openEditWindow(primaryStage);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});

	        
	        button2.setOnAction(e->openSearchWindow(primaryStage));
	        
	        button3.setOnAction(e->displayWindow(primaryStage));
	        
	        button4.setOnAction(e->exitApplication(primaryStage));
	        
	        button5.setOnAction(e->deleteWindow(primaryStage));
	        
	        // Create a layout for the scene
	        // Create a VBox layout to stack elements vertically
	        VBox layout = new VBox(70); // 10 is the spacing between elements

	        // Add the Text and Button to the layout
	        labelText.setFont(new Font(24));
	       layout.getChildren().add(labelText);
	        layout.getChildren().addAll( button,button1,button2,button3,button5,button4);
	        layout.setAlignment(Pos.CENTER);
	        
	        // Create the scene
	        Scene scene = new Scene(layout, 800, 700);

	        // Set the scene on the primary stage
	        primaryStage.setScene(scene);

	        // Show the primary stage
	        primaryStage.show();
	    }
	    
	    
	    						// DELETE STUDENT MODULE
	    private void deleteWindow(Stage w)
	    {
	    	w.close();
	    	Stage ng = new Stage();
	    	GridPane gP = new GridPane();
	    	gP.setPadding(new Insets(10,10,10,10));
	    	 gP.setVgap(20);
	            gP.setHgap(20);
	    	ResultSet rsm=null;
	    	StudentManagementSystem sms = new StudentManagementSystem();
	    	try{rsm = sms.displayStudent();}catch(Exception t)
	    	{
	    		System.out.println(t);
	    	}
	    	try {
	    	if(!rsm.next())
	    	{
	    		 Label labelnm = new Label("( ^_-_^ ) NO Student found"       );
	    		 Button backButton = new Button("Go Back");
                 backButton.setOnAction(er -> {
                	 ng.close(); // Close the second window
                     w.show(); // Show the main window again
                 });
	    		 gP.add(labelnm, 2, 3);
	    		 gP.add(backButton, 2, 5);
	    			Scene bsc = new Scene(gP,800,600);
	    		    ng.setScene(bsc);
	    		    ng.show();
	    		    return;
	    		 
	    	}
	    	}catch(Exception tre) {System.out.print(tre);}
	    	
	    	Label lab1 = new Label("Enter Student Roll No i.e 1,2,3... \n");
            TextField nameTextFielda = new TextField();
            
            Label lab2 = new Label("\nEnter Student Class e.g nineth or 9\n");
            TextField nameTextFieldb = new TextField();
            
            
            
            
            
           
            
            Label lnb = new Label("");
            Label lnba = new Label("");
            lnba.setVisible(false);
            
            
            lnb.setVisible(false);
            gP.add(lnb, 6, 0);
            gP.add(lnba, 0, 0);
            gP.add(lab1, 1, 1);
            gP.add(nameTextFielda, 3, 1);
            
            gP.add(lab2, 1, 3);
            gP.add(nameTextFieldb, 3, 3);
            
            Button backButtonM = new Button("Go Back");
            backButtonM.setOnAction(er -> {
                ng.close(); // Close the second window
                w.show(); // Show the main window again
            });
            
            
            
            Button nmnee = new Button("Delete Student Record");
            gP.add(backButtonM, 4, 6);
            gP.add(nmnee, 6, 6);
            
            nmnee.setOnAction(e->{
            	
            	String mk = nameTextFielda.getText();
            	String mka = nameTextFieldb.getText();
            	if(mk == "" || mka == "")
            	{
            		lnb.setText("Please enter both fields");
            		lnb.setVisible(true);
            		Scene sc = new Scene(gP);
            		ng.setScene(sc);
            		ng.show();            		
            		return;
            		
            		
            	}
            	else
            	{
            		int rn=-1;
            	if(!nameTextFielda.getText().isEmpty())
            	{
            		rn = Integer.parseInt(nameTextFielda.getText());
            		sms.closeCon();
            		StudentManagementSystem smsd = new StudentManagementSystem();
            		
            		int ans = smsd.deleteStudent(rn, mka);
            		
            		if(ans>0)
            		{
            			
            			lnba.setText("RECORD DELETED SUCCESSFULLY");
            			nmnee.setVisible(false);	
                		lnba.setVisible(true);
                		  
                      
                		
            		}
            		else
            		{
            			lnb.setText("RECORD NOT FOUND");
                		lnb.setVisible(true);
                		  
                      
            		}
            		
            	}
            	}
            	
            	
            	
            });
            
            Scene bsc = new Scene(gP,800,600);
		    ng.setScene(bsc);
		    ng.show();
         
            
	    	
	    }
	    
	    							//EXIT APPLICATION MODULE
	    private void exitApplication(Stage z)
	    {
	    	StudentManagementSystem sm = new StudentManagementSystem();
	    	sm.closeCon();
	    	z.close();
	    }

	    
	    
	    					//DISPLAY STUDENTS MODULE
	    private void displayWindow(Stage v)
	    {
	    	
	    	
	    	v.close();
	    	int a=1;
	    	Stage srest = new Stage();
	    	srest.setTitle("Display Window");
	    	ResultSet rsm=null;
	    	StudentManagementSystem sms = new StudentManagementSystem();
	    	try{rsm = sms.displayStudent();}catch(Exception t)
	    	{
	    		System.out.println(t);
	    	}
	    	GridPane gp = new GridPane();
	    	gp.setPadding(new Insets(10,10,10,10));
	    	gp.setHgap(20);
	    	gp.setVgap(20);
	    	
	    	int studentId;
	    	String studentN;
	    	int studentR;
	    	String studentGrade;
	    	String studentClas;
	    	try {
	    	if(!rsm.next())
	    	{
	    		 Label labelnm = new Label("( ^_-_^ ) NO Student found"       );
	    		 Button backButton = new Button("Go Back");
                 backButton.setOnAction(er -> {
                     srest.close(); // Close the second window
                     v.show(); // Show the main window again
                 });
	    		 gp.add(labelnm, 2, 3);
	    		 gp.add(backButton, 2, 5);
	    		 sms.closeCon();
	    			Scene bsc = new Scene(gp,800,600);
	    		    srest.setScene(bsc);
	    		    srest.show();
	    		    return;
	    		 
	    	}
	    	else
	    	{
	    		do{
	    	    	 studentId = rsm.getInt("stdId");
	    	         studentN = rsm.getString("stdNam");
	    	         studentR = rsm.getInt("stdRolNo");
	    	         studentGrade = rsm.getString("stdGrade");
	    	         studentClas = rsm.getString("stdClass");
	    	         Label labelnm = new Label("\nStudent ID: " + studentId +
	    	                 "\t Name: " + studentN +
	    	                 "\t Roll No: " + studentR +
	    	                 "\t Grade: " + studentGrade+
	    	                 "\t Class: " + studentClas+"\n"
	    	                 );
	    	         gp.add(labelnm, 2,a );
	    	         a++;
	    	    			}while(rsm.next());
	    	}
	    	}
	    	catch(Exception e) 
	    	{System.out.println(e);}
	    	
	    	
	    	 Button backButton = new Button("Go Back");
             backButton.setOnAction(er -> {
                 srest.close(); // Close the second window
                 v.show(); // Show the main window again
             });
    		 gp.add(backButton, 2, a);
	    	
	    	Scene bsc = new Scene(gp,800,600);
	    srest.setScene(bsc);
	    srest.show();
	    }
	    
	         						//SEARCH STUDENT MODULE
	    private void openSearchWindow(Stage u)
	    {
	    	u.close();
	    	GridPane gp = new GridPane();
	    	gp.setPadding(new Insets(10,10,10,10));
	    	   
	    	gp.setVgap(20);
	    	gp.setHgap(20);
	    	Stage sress = new Stage();
	    	sress.setTitle("Search Window");
	    	ResultSet rsm=null;
	    	StudentManagementSystem sms = new StudentManagementSystem();
	    	try{rsm = sms.displayStudent();}catch(Exception t)
	    	{
	    		System.out.println(t);
	    	}
	    	try {
	    	if(!rsm.next())
	    	{
	    		 Label labelnm = new Label("( ^_-_^ ) NO Student found"       );
	    		 Button backButton = new Button("Go Back");
                 backButton.setOnAction(er -> {
                	 sress.close(); // Close the second window
                     u.show(); // Show the main window again
                 });
	    		 gp.add(labelnm, 2, 3);
	    		 gp.add(backButton, 2, 5);
	    			Scene bsc = new Scene(gp,800,600);
	    		    sress.setScene(bsc);
	    		    sress.show();
	    		    return;
	    		 
	    	}
	    	}catch(Exception tre) {System.out.print(tre);}
	    	
//	    	HBox hb = new HBox(10);
	    	
	    	
	    	
	    	
	    	
	    	
	    	Button nmn = new Button("Search Student");
	    	
	    	Label lablma = new Label("Enter Student Roll No i.e 1,2,3... \n");
            TextField nameTextFieelda = new TextField();
            
            Label lablmaaa = new Label("\nEnter Student Class e.g nineth or 9\n");
            TextField nameTextFieeldaaa = new TextField();
            
            Button backButtonaan = new Button("Go Back");
            backButtonaan.setOnAction(er -> {
                sress.close(); // Close the second window
                u.show(); // Show the main window again
            });
            
           
            gp.add(lablma,0,1);
            
            gp.add(nameTextFieelda,2,1);
            
            gp.add(lablmaaa,0,3);
            
            gp.add(nameTextFieeldaaa,2,3);
            
            gp.add( backButtonaan,2,5);
            
            gp.add(nmn,2,6);
            
         
            
            nmn.setOnAction(e->{
            	String mk = nameTextFieelda.getText();
            	
            	int RnO =-1;
            	if(!nameTextFieelda.getText().isEmpty())
            		{ RnO = Integer.parseInt(mk);}
            	String mka = nameTextFieeldaaa.getText();
            	if(mk=="" || mka == "")
            	{
            		Label lablemaaa = new Label("\nPlz fill all fields\n");
            		gp.add(lablemaaa, 2, 4);
            		return;
            	}
            	
            	sms.closeCon();
            	ResultSet ren = null;
            	StudentManagementSystem a = new StudentManagementSystem();
            	
            	try{
            		ren = a.searchStudent(RnO,mka);
            		if(!ren.next())
            		{
            			 Label label = new Label("(^-^)Student Not Found");
                	     Button backButton = new Button("Go Back");
                         backButton.setOnAction(er -> {
                             sress.close(); // Close the second window
                             u.show(); // Show the main window again
                         });
                	     gp.add(label,2 ,4 );
                	     gp.add(backButton, 4, 4);
//                
                         return;
                	     
            		}
            		else
            		{
            		

        // Add the label to the layout
        
      
            			nmn.setVisible(false);
                		nameTextFieeldaaa.setVisible(false);
                		lablmaaa.setVisible(false);
                		nameTextFieelda.setVisible(false);
                		lablma.setVisible(false);
                		
                		 {
//                	    	 studentId = rsm.getInt("stdId");
//                	         studentN = rsm.getString("stdNam");
//                	         studentR = rsm.getInt("stdRolNo");
//                	         studentGrade = rsm.getString("stdGrade");
//                	         studentClas = rsm.getString("stdClass");
                			 do {
                			System.out.println("KASDASDASDA");
                	         Label labelnm = new Label("\n( ^ v ^ ) STUDENT FOUND\nStudent ID: " + ren.getInt("stdId") +
                	                 "\t Name: " + ren.getString("stdNam") +
                	                 "\t Roll No: " + ren.getInt("stdRolNo") +
                	                 "\t Grade: " + ren.getString("stdGrade")+
                	                 "\t Class: " + ren.getString("stdClass")+"\n"
                	                 );
                	         gp.add(labelnm, 2, 3 );
                			 }while(ren.next());
                	         
                	    			}
                		
                			
            		}
            		}catch(Exception bnbb){System.out.println(bnbb);}
            	
            
            		 
            	
            	
            });
            
            Scene hcs = new Scene(gp,800,700);
            
            sress.setScene(hcs);
            
            sress.show();
            
            
            
	    	
	    	
	    	
	    }
	    
	    
	    					//EDIT SUTDENT RECORD MODULE
	    
	    private void openEditWindow(Stage t) throws Exception
	    {
	    	t.close();
	    	Stage sres = new Stage();
	    	sres.setTitle("Edit Window");
	    	VBox layout = new VBox(10);
	    	
	    	ResultSet rs ;
	    	
	    	StudentManagementSystem smt = new StudentManagementSystem();
	    	
	    	rs = smt.displayStudent();
	    	if(!rs.next())
	    	{
	    		Label lbnlm = new Label("\t\t\t\tNo Records of Students stored\n\n\nYou cant edit students please enter students first\n\n");
	    		layout.getChildren().add(lbnlm);
	    		
	    		 Button backButton = new Button("Go Back");
                 backButton.setOnAction(er -> {
                     sres.close(); // Close the second window
                     t.show(); // Show the main window again
                 });
                 
                 
                 layout.getChildren().add(backButton);
               
                 Scene scene = new Scene(layout, 800, 700);
                 sres.setScene(scene);

                 // Show the stage
                 sres.show();
                 return;
	    		
	    	}
	    	else
	    	{
	    		Label lbnl = new Label("Current Stored Records of Students");
		    	
		    	layout.getChildren().add(lbnl);
	    	      do{
	                 // Retrieve data from the result set
	                 int studentId = rs.getInt("stdId");
	                 String studentN = rs.getString("stdNam");
	                 int studentR = rs.getInt("stdRolNo");
	                 String studentGrade = rs.getString("stdGrade");
	                 String studentClas = rs.getString("stdClass");

	                 // Create a label to display each student record
	                 Label labelnnn = new Label("Student ID: " + studentId +
	                                         "\t Name: " + studentN +
	                                         "\t Roll No: " + studentR +
	                                         "\t Grade: " + studentGrade+
	                                         "\t Class: " + studentClas+"\n"
	                                         );

	                 // Add the label to the layout
	                 layout.getChildren().add(labelnnn);
	             }while (rs.next());	
	    	}
	    	
	    	
	    	
	    	 Label ghd = new Label("PLEASE fill all FIELDS\n");
	    	 HBox hb = new HBox(ghd);
	    	 hb.setAlignment(Pos.CENTER);
        	 layout.getChildren().add(hb);
        	 ghd.setVisible(false);
	    	
	    	
//            while (rs.next()) {
//                // Retrieve data from the result set
//                int studentId = rs.getInt("stdId");
//                String studentN = rs.getString("stdNam");
//                int studentR = rs.getInt("stdRolNo");
//                String studentGrade = rs.getString("stdGrade");
//                String studentClas = rs.getString("stdClass");
//
//                // Create a label to display each student record
//                Label labelnnn = new Label("Student ID: " + studentId +
//                                        "\t Name: " + studentN +
//                                        "\t Roll No: " + studentR +
//                                        "\t Grade: " + studentGrade+
//                                        "\t Class: " + studentClas+"\n"
//                                        );
//
//                // Add the label to the layout
//                layout.getChildren().add(labelnnn);
//            }
            
	   
            
            Button sbm = new Button("Change Record");
            
            Label lablm = new Label("\nEnter stdId whom u want to edit\n");
            TextField nameTextFieeld = new TextField();
            
            String nameTextFieeldStr = nameTextFieeld.getText();
            
            
            Label lablma = new Label("\nEnter New Student Roll No\n");
            TextField nameTextFieelda = new TextField();
            
            Label lablmaa = new Label("\nEnter New Student Grade\n");
            TextField nameTextFieeldaa = new TextField();
            
            Label lablmaaa = new Label("\nEnter New Student Class\n");
            TextField nameTextFieeldaaa = new TextField();
         
            
            layout.getChildren().add(lablm);
            layout.getChildren().add(nameTextFieeld);
            
            layout.getChildren().add(lablma);
            layout.getChildren().add(nameTextFieelda);
            
            layout.getChildren().add(lablmaa);
            layout.getChildren().add(nameTextFieeldaa);
            
            layout.getChildren().add(lablmaaa);
            layout.getChildren().add(nameTextFieeldaaa);
            
            Button backButtonet = new Button("Go Back");
            backButtonet.setOnAction(er -> {
                sres.close(); // Close the second window
                t.show(); // Show the main window again
            });
            layout.getChildren().add(backButtonet);
            
            HBox hbox = new HBox(sbm);
            // Create an HBox containing only the button
            
            hbox.setAlignment(Pos.CENTER); // Center the button horizontally within the HBox

            layout.getChildren().add(hbox);
            
           
            
            sbm.setOnAction(e->{
            	
            	int idr=-1,rolNO=0;
            	 String newR=null,newG=null,newC=null;
            	  if (!nameTextFieeld.getText().isEmpty()) {
            	        try {
            	            idr = Integer.parseInt(nameTextFieeld.getText());
            	            rolNO = Integer.parseInt(nameTextFieelda.getText());
            	        } catch (NumberFormatException ex) {
            	            System.out.println("Invalid input for ID: " + ex.getMessage());
            	        }
            	    }
               
               
               newR = nameTextFieelda.getText();
                
                 newG = nameTextFieeldaa.getText();
                
                 newC = nameTextFieeldaaa.getText();
                 
                 if(newR=="" || newG == "" || newC == "")
                 {
                	 ghd.setVisible(true);
                	 Button backButton = new Button("Go Back");
                   
                     layout.getChildren().add(backButton);
                     Scene scene = new Scene(layout, 800, 700);
                     sres.setScene(scene);

                     // Show the stage
                     sres.show();
                     return;
                	 
                 }
               
            	
            	StudentManagementSystem smse = new StudentManagementSystem();
            	int ans = smse.executUpdate(rolNO,newG,newC,idr);
            	
            	 if (ans > 0) {
                     System.out.println("Student record updated successfully.");
                     layout.getChildren().clear();
                     VBox layouut = new VBox(10);
                     Label lnm = new Label("REcord Edited Successfully, \nUpdated records are :(__--__)");
                     layouut.getChildren().add(lnm);
                     ResultSet ghh = null;
                     try{
                    	 ghh = smse.displayStudent();
                    	 while (ghh.next()) {
                             // Retrieve data from the result set
                             int studentId = ghh.getInt("stdId");
                             String studentN = ghh.getString("stdNam");
                             int studentR = ghh.getInt("stdRolNo");
                             String studentGrade = ghh.getString("stdGrade");
                             String studentClas = ghh.getString("stdClass");

                             // Create a label to display each student record
                             Label label = new Label("Student ID: " + studentId +
                                                     "\t Name: " + studentN +
                                                     "\t Roll No: " + studentR +
                                                     "\t Grade: " + studentGrade+
                                                     "\t Class: " + studentClas+"\n"
                                                     );

                             // Add the label to the layout
                             layouut.getChildren().add(label);
                         }
                    	 }catch(Exception ejhh) {System.out.println(ejhh);}
                     Button backButton = new Button("Go Back");
                     backButton.setOnAction(er -> {
                         sres.close(); // Close the second window
                         t.show(); // Show the main window again
                     });
                     VBox jkrt = new VBox(200);
                     jkrt.getChildren().add(backButton);
                     
                     jkrt.setAlignment(Pos.CENTER);
//                  
                     layouut.getChildren().add(jkrt);
                     Scene newScene = new Scene(layouut, 800, 700); // Create a new scene with the updated layout
                     sres.setScene(newScene); // Set the new scene
                     sres.show();
                 } else {
                     System.out.println("No records were updated. Check your criteria.");
                 }
            	
            	
            });
            
            
            Scene scene = new Scene(layout, 800, 700);
            sres.setScene(scene);

            // Show the stage
            sres.show();
            
            
            
	    	
	    }
	    
	    
	    							//INSERT STUDENT MODULE
	    
	    //INSERT MODULE
	    private void openInsertWindow(Stage s) {
	        // Create a new stage for the new window
	    	s.close();
	    	
	    	Stage primaryStage = new Stage();
	    	
	    	primaryStage.setTitle("ADD Window");
	    	
	    	Label nameLabl0 = new Label("");
	    	nameLabl0.setVisible(false);
	    	
	    	Label nameLabel = new Label("Enter Name:");
	        TextField nameTextField = new TextField();
	        Button submitButton = new Button("Submit");
	        
	        Label nameLabel1 = new Label("Enter Roll No:");
	        TextField nameTextField1 = new TextField();
	        
	        Label nameLabel2= new Label("Enter Class:");
	        TextField nameTextField2 = new TextField();
	        
	        Label nameLabel3 = new Label("Enter Grade:");
	        TextField nameTextField3 = new TextField();
	        
	        Button submitButton1 = new Button("Submit");
	        Button backButton = new Button("Go Back");
            backButton.setOnAction(er -> {
                primaryStage.close(); // Close the second window
                s.show(); // Show the main window again
            });
	        
	        GridPane gridPane = new GridPane();
	        gridPane.setPadding(new Insets(10,10, 10, 10));
	        gridPane.setHgap(20);
	        gridPane.setVgap(20);

	        // Add form components to the grid pane
	        
	        gridPane.add(nameLabl0, 0, 0);
	        
	        
	        gridPane.add(nameLabel, 0, 1);
	        gridPane.add(nameTextField, 1, 1);
	        
	        gridPane.add(nameLabel1, 0,2);
	        gridPane.add(nameTextField1, 1, 2);
	        
	        gridPane.add(nameLabel2, 0,3);
	        gridPane.add(nameTextField2, 1, 3);
	        
	        gridPane.add(nameLabel3, 0,4);
	        gridPane.add(nameTextField3, 1, 4);
	        gridPane.add(backButton, 2, 5, 2, 1);
	        gridPane.add(submitButton, 5, 5, 2, 1);


	        // Create a scene and set it on the new stage
	        Scene secondScene = new Scene(gridPane, 800, 600);
	        primaryStage.setScene(secondScene);

	        
	        
	        submitButton.setOnAction(e -> {
	        	String enteredName = nameTextField.getText();
	        	String enteredRollNo = nameTextField1.getText();
	        	int enteredRolNo=0;
	        	if(!nameTextField1.getText().isEmpty())
	        	{
	        		enteredRolNo = Integer.parseInt(enteredRollNo);	
	        	}
	        	String enteredClass = nameTextField2.getText();
	        	String enteredGrade = nameTextField3.getText();
	        	
	        	
	        	 if (enteredName.isEmpty() || enteredRollNo.isEmpty() || enteredClass.isEmpty() || enteredGrade.isEmpty()) {
	                 // If the text field is empty, display a validation message
	        		 nameLabl0.setVisible(true);
	        		 nameLabl0.setText("Please fill in the field");
	             }
	        	 else if(!(enteredName.matches("[a-zA-Z]+")))
	        	 {
	        		 nameLabl0.setVisible(true);
	        		 nameLabl0.setText("Please enter alphabetic Name"); 
	        	 }
	        	 else if(!(enteredGrade.matches("[A-F]{1}[\\+\\-]?")))
	        	 {
	        		 nameLabl0.setVisible(true);
	        		 nameLabl0.setText("Please enter grade as A or A+ or A-");  
	        	 }
	        	 
	        	 else if(!(enteredRollNo.matches("[a-zA-Z0-9]+")))
	        	 {
	        		 nameLabl0.setVisible(true);
	        		 nameLabl0.setText("Please enter alpha numeric rollno"); 
	        	 }
	        	 else if(!(enteredClass.matches("^([A-Za-z]+|[1-9]+)$")))
	        	 {
	        		 nameLabl0.setVisible(true);
	        		 nameLabl0.setText("Please enter class as ninth or 9"); 
	        	 }
	        	 else {
	                 // Perform the desired action when the field is not empty
	                 System.out.println("Name submitted: " + enteredName);
	                 System.out.println("Name submitted: " + enteredRollNo);
	                 
	                 System.out.println("Name submitted: " + enteredClass);
	                 System.out.println("Name submitted: " + enteredGrade);
	                 
	                 Student stnt = new Student(enteredName,enteredRolNo,enteredGrade,enteredClass);
	                 
	    
	                	 
	                	 
	                     
	                     
	                     
	                         StudentManagementSystem sms = new StudentManagementSystem();
	                     
	                         int rs=-1;
	                     try {
	                          rs = sms.InsertStudent(stnt);}catch(Exception tre) {
	                        	  System.out.println(tre);
	                          }
	                         
	                         
	                    	 
	                       
	                         if(rs > 0){
	                        	 primaryStage.close();
	                        	 Stage secondStage = new Stage();
	                        	   secondStage.setTitle("Second Window");

	                               // Create a label to display text
	                               Label label = new Label("Congratulations(^-^)/ record of Student\nStudent Name\t"+enteredName+"\nStudent Rol\t"+enteredRollNo+"\nStudent Grade\t"+enteredGrade+"\nStudent Class\t"+enteredClass+"\n has recorded");

	                               // Create a button to go back to the main window
	                               Button backButtonol = new Button("Go Back");
	                               backButtonol.setOnAction(er -> {
	                                   secondStage.close(); // Close the second window
	                                   s.show(); // Show the main window again
	                               });

	                               // Create a layout for the second window
	                               VBox layout = new VBox(10);
	                               layout.setPadding(new Insets(20));
	                               layout.getChildren().addAll(label, backButtonol);

	                               Scene scene = new Scene(layout, 800, 600);
	                               secondStage.setScene(scene);

	                               // Show the second window
	                               secondStage.show();
	                    	      
	                    	    	
	                    	    	
;	                    	    System.out.println("Record inserted successfully.");
	                    	  }
	                         
	                         else{
	                        	 System.out.println("Record could not inserted.");
	                             }
	                         
	               
	             }
	        });
	        
	        // Show the new stage (window)
	        primaryStage.show();
	    }
	
	
	
}

