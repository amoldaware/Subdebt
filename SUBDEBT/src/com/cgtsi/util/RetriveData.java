package com.cgtsi.util;

import java.io.FileWriter;
import java.io.Reader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class RetriveData {  
	public static void main(String[] args) {  
	try{  
		
		System.out.println("load driver");
	Class.forName("oracle.jdbc.driver.OracleDriver");
	System.out.println("conn before");
//	Connection con=DriverManager.getConnection("jdbc:oracle:thin:@158.100.60.116:1521:CGFSIDB","CGTSITEMPUSER","CGTSITEMPUSER");
	System.out.println("con before");
	Connection connection = DBConnection.getConnection();
	
	System.out.println("con get ");
	PreparedStatement ps=connection.prepareStatement("select * from RECOVRY_AFTER_BEFORE_FST_CLAIM");
	System.out.println(" ps ready ");
	ResultSet rs=ps.executeQuery();  
	System.out.println(" rs ");
	rs.next();//now on 1st row  
	              
	Clob c=rs.getClob(2);  
	Reader r=c.getCharacterStream();              
	              
	FileWriter fw=new FileWriter("d:\\App.txt");  
	              
	int i;  
	while((i=r.read())!=-1)  
	fw.write((char)i);  
	              
	fw.close();  
	connection.close();  
	              
	System.out.println("success");  
	}catch (Exception e) {e.printStackTrace();  }  
	}  
	}  