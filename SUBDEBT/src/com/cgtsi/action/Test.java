package com.cgtsi.action;

import java.text.ParseException;
import java.util.Date;
import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.cgtsi.common.Log;
import com.cgtsi.common.MailerException;

public class Test {

	/**
	 * @param args
	 * @throws ParseException
	 * @throws MailerException 
	 */
	public static void main(String[] args) throws ParseException, MailerException {
		
		final String username = "registration@cgtmse.in";
        final String passwd = "Password$2020";
        final String from = "registration@cgtmse.in";
        Session session = null;
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.office365.com");
        props.put("mail.smtp.port", "25");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
       props.put("mail.smtp.username", "registration@cgtmse.in");
       props.put("mail.smtp.password", "Password$2020");
       
        //props.put("mail.smtp.ssl.trust", "smtp.office365.com");
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                 return new PasswordAuthentication(username, passwd);
            }
       };

       session = Session.getDefaultInstance(props, auth);
		
		//Session session = Session.getDefaultInstance(props, null);
		session.setDebug(true);


        try {
        	Transport transport = session.getTransport("smtp"); 
        	transport.connect();
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(javax.mail.Message.RecipientType.TO,
                    "abhishek.sikarwar@pathinfotech.com");
            msg.setSubject("Testing SMTP using ["+ from + "]");
            msg.setSentDate(new Date());
            msg.setText("Hey, this is a test from [" + from + "]");
            transport.sendMessage(msg, msg.getAllRecipients());

        } catch (SendFailedException sfe) 
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
						
		  
		  
        System.out.println("Sent Ok") ;
    }


}
