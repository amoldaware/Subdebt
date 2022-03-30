package com.cgtsi.common;
import javax.mail.*;
import javax.mail.internet.*;

//import java.net.ConnectException;
import java.util.*;

import com.cgtsi.actionform.AdministrationActionForm;
import com.cgtsi.admin.AdminDAO;

//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\common\\Mailer.java



import com.cgtsi.admin.Message;
import com.cgtsi.util.PropertyLoader;

/**
 * This class used to send emails to users. This class encapsulates all the email
 * related details.
 */
public class Mailer
{
   /**
	* @roseuid 39B875CE030C
	*/
   public Mailer()
   {

   }
   
   
   

   
   
   
   

   /**
	* This method sends the message to the intented email ids.
	*
	* @param message
	* @return Boolean
	* @throws MailerException
	* @roseuid 3973E7110028
	*/
   public boolean sendEmail(Message message) throws MailerException
	  {
	   
		Log.log(Log.INFO,"Mailer","sendEmail","Entered");
		
		boolean sendMailVal=false;
		ArrayList smtpDetails;
		//get the user id, password, and smtp host names;
		// String userId="";
		 //String password="";
		//String smtpHost="";
		//final String port="";
		Session session = null;
		
		try
		{
			AdminDAO admindao=new AdminDAO();
			AdministrationActionForm adForm=new AdministrationActionForm();
			adForm=admindao.getSmtpDetails();
		
			
			final String smtpHost = adForm.getSmtpHost();
			System.out.println("smtpHost=="+smtpHost);
			final String userId = adForm.getSmtpUserId();
			System.out.println("userId=="+userId);
			final String password = adForm.getSmtpPassword();
			System.out.println("password=="+password);
			final String smtpPort = adForm.getSmtpPort();
			System.out.println("smtpPort=="+smtpPort);
			 
			
			
			
			/*
			 * final String smtpHost=PropertyLoader.getValue("smtphost"); final String port
			 * = PropertyLoader.getValue("smtpport"); //
			 * System.out.println("Mailer.java-smtpHost:"+smtpHost); final String
			 * userId=PropertyLoader.getValue("emailUserId"); final String
			 * password=PropertyLoader.getValue("emailPassword");
			 */
			
			//24052020
		//	String host = PropertyLoader.getValue("host");//resource.getString("host");
	         

			
			
          System.out.println("userId:"+userId+" Password:"+password);
			Properties props = new Properties();
			props.put("mail.smtp.host", smtpHost);
			Log.log(Log.DEBUG,"Mailer","sendEmail","smtpHost "+smtpHost);
			//props.put("mail.smtp.auth", "true");
			
			props.put("mail.smtp.port", smtpPort); // TLS Port
            props.put("mail.smtp.auth", smtpHost);
            props.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS
            Authenticator auth = new Authenticator() {
                 protected PasswordAuthentication getPasswordAuthentication() {
                      return new PasswordAuthentication(userId, password);
                 }
            };

			
			//Authenticator auth = new SMTPAuthenticator(userId ,password);
			System.out.println("user id "+userId+" " + password);
			session = Session.getDefaultInstance(props, auth);
			
			//Session session = Session.getDefaultInstance(props, null);
			session.setDebug(false);
			
			// create a message
			javax.mail.Message msg = new MimeMessage(session);
			
			//String from=message.getFrom();
			String from = "registration@cgtmse.in";
			Log.log(Log.DEBUG,"Mailer","sendEmail","from "+from);
			System.out.println("from Mail::::::::::"+from);
			// set the from and to address
			InternetAddress addressFrom = new InternetAddress(from);
			
			msg.setFrom(addressFrom);
			
			ArrayList to=message.getTo();
      System.out.println("To Email Id:"+to);
    
     //  to.add("it@cgtmse.com");
			
			//Transport transport = session.getTransport("smtp"); 
			//transport.connect("mail.ncgtc.in",25,"eclgs@ncgtc.in","NCGTC#12345");
			
			//This mail integrated only for To Addresses. If required, CC, BCC can be implemented.
			for(int i=0;i<to.size();i++)
			{
				try
				{
				InternetAddress[] addressTo = new InternetAddress[1];
				addressTo[0]=new InternetAddress((String)to.get(i));
				Log.log(Log.DEBUG,"Mailer","sendEmail","to "+addressTo[0]);
              System.out.println("To Mail Ids:"+addressTo[0]);
        
				msg.setRecipients(javax.mail.Message.RecipientType.TO, addressTo);
				
      
        
        
                msg.setSubject(message.getSubject());
				msg.setContent(message.getMessage(), "text/plain");
				
				System.out.println("loop no"+i); 
				Transport.send(msg);
				}

			
				catch(AddressException invalidAddress)
						{
							Log.log(Log.ERROR,"Mailer","sendEmail",invalidAddress.getMessage());
					//		Log.logException(invalidAddress);
							System.out.println("invalidAddress==="+invalidAddress.getMessage());
							throw new MailerException("sending E-mail failed. Invalid E-Mail address");
				
						}

				catch (SendFailedException sfe) 
				{
					Log.log(Log.ERROR,"Mailer","sendEmail",sfe.getMessage());
					//Log.logException(sfe);
					System.out.println("SendFailedException==="+sfe.getMessage());
					//.out.println("PATH inside send failed");
					
					throw new MailerException("sending E-mail failed.");
				}

				catch(AuthenticationFailedException AuthenticationFailed)
							{
								Log.log(Log.ERROR,"Mailer","sendEmail",AuthenticationFailed.getMessage());
					//			Log.logException(AuthenticationFailed);
								System.out.println("AuthenticationFailed==="+AuthenticationFailed.getMessage());
								//System.out.println("inside authenticatiobn failed");
								throw new MailerException("sending E-mail failed.");
							}

				
				catch (MessagingException me)
								{
									Log.log(Log.ERROR,"Mailer","sendEmail",me.getMessage());
					//				Log.logException(me);
									System.out.println("MessagingException==="+me.getMessage());
									System.out.println("inside messaging exception");
									throw new MailerException("sending E-mail failed.");
								}
								
				  }
				  

			
			sendMailVal=true;
		}
	/*	catch(java.net.ConnectException ce){
			Log.log(Log.ERROR,"Mailer","sendEmail",ce.getMessage());
			Log.logException(ce);
			System.out.println(ce.getStackTrace());
			throw new MailerException("sending E-mail failed.");
		}
		catch (java.lang.reflect.UndeclaredThrowableException e) {

			throw new MailerException("sending E-mail failed.");
			// get the reason of the exception and throw it again
			//throw e.getUndeclaredThrowable();
		}*/


	catch(Exception e)
			{
				Log.log(Log.ERROR,"Mailer","sendEmail",e.getMessage());
			//	Log.logException(e);
			//	e.printStackTrace();
				throw new MailerException("sending E-mail failed.");
			}
				/*finally{
					session=null;
					}*/
				 

		// Setting the Subject and Content Type
		
		Log.log(Log.INFO,"Mailer","sendEmail","Exited");
			
		return sendMailVal;
   }





public void postMail( String recipients[ ], String subject, String message , String from) throws MessagingException
{
    boolean debug = false;
    String userId="";
		String password="";
		String smtpHost="";
     //Set the host smtp address
     Properties props = new Properties();
     smtpHost=PropertyLoader.getValue("smtphost");
     props.put("mail.smtp.host", smtpHost);
     
     
     

    // create some properties and get the default Session
    Session session = Session.getDefaultInstance(props, null);
    session.setDebug(debug);

    // create a message
      javax.mail.Message msg = new MimeMessage(session);

    	InternetAddress addressFrom = new InternetAddress(from);
			
			 msg.setFrom(addressFrom);
	

    InternetAddress[] addressTo = new InternetAddress[recipients.length]; 
    for (int i = 0; i < recipients.length; i++)
    {
        addressTo[i] = new InternetAddress(recipients[i]);
    }
    msg.setRecipients(javax.mail.Message.RecipientType.TO, addressTo);
   

    // Optional : You can also set your custom headers in the Email if you Want
    msg.addHeader("MyHeaderName", "myHeaderValue");

    // Setting the Subject and Content Type
    msg.setSubject(subject);
    msg.setContent(message, "text/plain");
    Transport.send(msg);
}




/**
* SimpleAuthenticator is used to do simple authentication
* when the SMTP server requires it.
*/
private class SMTPAuthenticator extends javax.mail.Authenticator
{
	private String userId="";
	private String password="";

	public SMTPAuthenticator(String userId, String password)
	{
		this.userId=userId;
		this.password=password;
	}

	public PasswordAuthentication getPasswordAuthentication()
	{
		return new PasswordAuthentication(userId, password);
	}
}


}
