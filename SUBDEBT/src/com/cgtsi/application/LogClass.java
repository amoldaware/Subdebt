package com.cgtsi.application;

//FrontEnd Plus GUI for JAD
//DeCompiled : LogClass.class



import java.io.*;
import java.util.Calendar;

public class LogClass
{

 static String DebbugFlag = "ON";

 public LogClass()
 {
 }

 public static void main(String args1[])
 {
	 try
	 {
	 int a=12/0;
	 }
	 catch(Exception e)
	 {
		 writeExceptionOnFile(e);
		 StepWritter("exp");
	 }
 }

 public static void writeExceptionOnFile(Exception e)
 {
     if(DebbugFlag.equals("ON"))
         try
         {
             FileWriter fstream = new FileWriter("D://exception.txt", true);
             BufferedWriter out = new BufferedWriter(fstream);
             PrintWriter pWriter = new PrintWriter(out, true);
             e.printStackTrace(pWriter);
         }
         catch(Exception exception) { }
 }

 public static void StepWritter(String str)
 {
     if(DebbugFlag.equals("ON"))
         try
         {
             File file = new File("D://StepWritter.txt");
             if(!file.exists())
                 file.createNewFile();
             java.util.Date startTime = null;
             Calendar c = Calendar.getInstance();
             startTime = c.getTime();
             FileWriter fstream1 = new FileWriter(file, true);
             BufferedWriter out = new BufferedWriter(fstream1);
             PrintWriter pw = new PrintWriter(out);
             pw.println("");
             String TimeContent = (new StringBuilder("Time :- ")).append(startTime).append(" ").append(str).toString();
             pw.println(TimeContent);
             pw.close();
         }
         catch(Exception exception) { }
 }
 
 public static void StepWritterConnIssue(String str)
 {
     if(DebbugFlag.equals("ON"))
         try
         {
             File file = new File("D://StepWritterConnIssue.txt");
             if(!file.exists())
                 file.createNewFile();
             java.util.Date startTime = null;
             Calendar c = Calendar.getInstance();
             startTime = c.getTime();
             FileWriter fstream1 = new FileWriter(file, true);
             BufferedWriter out = new BufferedWriter(fstream1);
             PrintWriter pw = new PrintWriter(out);
             pw.println("");
             String TimeContent = (new StringBuilder("Time :- ")).append(startTime).append(" ").append(str).toString();
             pw.println(TimeContent);
             pw.close();
         }
         catch(Exception exception) { }
 }
 
 public static void StepWritterForXXXModule(String str,String fileName)
 {
     if(DebbugFlag.equals("ON"))
         try
         {
             File file = new File("D://"+fileName);
             if(!file.exists())
                 file.createNewFile();
             java.util.Date startTime = null;
             Calendar c = Calendar.getInstance();
             startTime = c.getTime();
             FileWriter fstream1 = new FileWriter(file, true);
             BufferedWriter out = new BufferedWriter(fstream1);
             PrintWriter pw = new PrintWriter(out);
             pw.println("");
             String TimeContent = (new StringBuilder("Time :- ")).append(startTime).append(" ").append(str).toString();
             pw.println(TimeContent);
             pw.close();
         }
         catch(Exception exception) { }
 }

}
