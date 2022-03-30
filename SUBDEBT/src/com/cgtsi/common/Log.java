//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\common\\Log.java

package com.cgtsi.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.cgtsi.util.PropertyLoader;


/**
 * This class is primarily used to log the details.Which can be used to debug the
 * application.
 */
public class Log
{
   public static final int DEBUG = 5;
   public static final int INFO = 4;
   public static final int WARNING = 3;
   public static final int ERROR = 2;
   public static final int CRITICAL = 1;
   public static final String LOG_FILE_NAME="DebugLog";
   private static File file= null;
   private static BufferedWriter bufferOut=null;
   private static int configLogLevel=0;
   private static String contextPath=null;
   private static String realPath=null;

	static 
	{
//		Get the path where this log file would be placed.		
		contextPath=PropertyLoader.getValue("contextpath");

//		Get the OS related path.		
		try
		{
			realPath=PropertyLoader.changeToOSpath(contextPath);
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		//creates the log file.
		
		file=new File(realPath+PropertyLoader.CONFIG_DIRECTORY,
						LOG_FILE_NAME+".log");
		try {
			bufferOut=new BufferedWriter(
							new FileWriter(file,true));
			initialize();							
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
   

	/**
	 * This method is used to initialize the loglevel obtained from debug logger.
	 * 
	 */
	public static void initialize()
	{
		try
		{
//			get the log level maintained in the config.properties file.			
			configLogLevel=Integer.parseInt(PropertyLoader.getValue("loglevel"));
			//If invalid entries are set in the debug log file, 
			//by default it is assigned to 0.
			if(configLogLevel<CRITICAL || configLogLevel>DEBUG)
			{
				configLogLevel=0;
			}
			
			//System.out.println("configLogLevel : "+configLogLevel);
		}
		catch(NumberFormatException invalidNumber)
		{
			configLogLevel=0;
		}
	}
	
		
	
	
   /**
    * Default Contructor.
    */
   private Log()
   {
   }

   /**
    * @param loginLevel	The level at which logging should be done.
    * @param className	The class name from where this method is invoked.
    * @param methodName	The method name from where this method is invoked.
    * @param message	The message to be logged.
    */
   public static synchronized void log(int loginLevel, String className, 
   									String methodName, String message)
   {
	   	//Logging level requested should not be greater or lesser than the 
		//minimum and maximum allowed by the system.
		if(loginLevel<CRITICAL || loginLevel>DEBUG)
		{
			return;
		}
		//System.out.println("Log Level maintained in config file is "+configLogLevel);
		
		//If the logging level maintained in config file is smaller than the log level
		//requested no need to log the details.
		if(loginLevel>configLogLevel)
		{
			//Check whether log level is updated or not.(Update will reflect after one day)
			/*
			 *  As per discussion with Client, Checking for updation of
			 * Log level is not required. If log level changes, user will
			 * restart the web server.
			 * Date : 10/10/2004
			 * Done By: Veldurai
			 */
			//System.out.println("initializing the log level...");
			//initialize();
			/*
			 * Changes completed.
			 */
			return;	
		}
		
	
		try {
				
				
				
				//Check the file size whether it exceeded the set size.
				checkFileSize();
				
				String logMessage=loginLevel+" , "+getTime()+" , "+
						className+" , "+methodName+" , "+message;
				
				bufferOut.write(logMessage);
				bufferOut.newLine();
				bufferOut.flush();
			}
			catch (IOException e)
			{
				e.printStackTrace();
				try
				 {
					bufferOut.close();
				 }
				 catch (IOException e1)
				 {
					e1.printStackTrace();
				 }
			}
   }
   
   /**
    * This method checks whether the log file size is greater than 1 MB or not. 
    * If the log file is greater than 1 MB, the file is renamed. The current time 
    * is appended to the file name. A new File with the name DebugLog.log is created and 
    * used for further logging.
 	*/
   private static void checkFileSize()
   {
   		//System.out.println("file size is "+file.length());
		if(file.length() >=1024*1024)
		{
			System.out.println("greater than 1 MB ");
			try
			{
				
				bufferOut.flush();
				bufferOut.close();
				//bufferOut=null;
				
				//System.out.println("File Name before " +file.getName());
									
				boolean isRenamed=file.renameTo(new File(realPath+PropertyLoader.CONFIG_DIRECTORY,
						LOG_FILE_NAME+(new Date().getTime())+".log"));
				
				if(!isRenamed)
				{
					//could not rename the file. so, clear the file and move the content
					// to the desired file.
					
					FileOutputStream fileOut=new FileOutputStream(new File(realPath+PropertyLoader.CONFIG_DIRECTORY,
								LOG_FILE_NAME+(new Date().getTime())+".log"));
					
					byte[] data=new byte[1024];
					
					FileInputStream fileIn=new FileInputStream(file);
					int readBytes=0;
					while((readBytes=fileIn.read(data,0,data.length))!=-1)
					{
						fileOut.write(data,0,readBytes);
					}
					
					fileIn.close();
					fileOut.close();
					fileIn=null;
					fileOut=null;
					
					fileOut=new FileOutputStream(file);
					
					fileOut.close();
					fileOut=null;
				}
				//System.out.println("read: "+file.canRead());
				//System.out.println("write: "+file.canWrite());
				
				//System.out.println("File Name after " +file.getName()+" isRenamed "+isRenamed);
			
				file=new File(realPath+PropertyLoader.CONFIG_DIRECTORY,LOG_FILE_NAME+".log");             
				
				//System.out.println("File Name ....Done " +file.getName());
				
				bufferOut=new BufferedWriter(new FileWriter(file,true));
			}
			catch(IOException ioe)
			{
				ioe.printStackTrace();
			}
		}
		
   }
   
   /**
    * This method formats the current time into a specified format and returns
    * the String version of it. This is used while logging.
 	* @return	String version of the current formated date.
 	*/
   private static String getTime()
   {
		java.util.Date   loCurrentTime = new java.util.Date();
		SimpleDateFormat loSdf         = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SSS");
		return  loSdf.format(loCurrentTime);   	
   }
   /**
    * This method is used to log the exception in the log file.
    * @param exception	The Exception thrown in a specified location
    * of a program.
    */
   public synchronized static void logException(Throwable exception)
   {

	   try
	   {
			exception.printStackTrace(new PrintWriter(bufferOut));
			bufferOut.flush();
	   }
	   catch(Exception ignore) {}
   }
   
   
   private static String getChangedFileName(String fileName)
   {
		if(fileName.length()>12)
		{
			String substr=fileName.substring(12,fileName.length());
			int lastValue=Integer.parseInt(substr);
	
			lastValue++;
	
			String lastStr=String.valueOf(lastValue); 
	
			int length=lastStr.length();
	
			String newStr=fileName.substring(0,fileName.length()-length)+lastStr;
	
			return newStr;
		}
		else
		{
			return fileName+"001";   	
		}
   }
   
   public static boolean isDebugEnabled()
   {
   		return configLogLevel<DEBUG?false:true;
   }
   
}
