//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\common\\FileUploader.java

package com.cgtsi.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import com.cgtsi.admin.AdminConstants;
import com.cgtsi.application.Application;
import com.cgtsi.application.ApplicationProcessor;
import com.cgtsi.application.InvalidApplicationException;
import com.cgtsi.application.NoApplicationFoundException;

import com.cgtsi.claim.ClaimsProcessor;
import com.cgtsi.claim.ClaimApplication;

import com.cgtsi.guaranteemaintenance.GMProcessor;
import com.cgtsi.guaranteemaintenance.PeriodicInfo;
import com.cgtsi.guaranteemaintenance.Disbursement;



/**
 * This class is used to upload the given file and decrypt it, finds the correct
 * format .
 * If the format is valid, it extracts the content and forwards to the respective
 * modules for further processing.
 */
public class FileUploader
{

   /**
	* @roseuid 39B875CE02BC
	*/
   public FileUploader()
   {

   }

   /**
	* This method is used to decrypt the file.
	* @param file
	* @return File
	* @roseuid 3972EFA103A9
	*/
   public File decryptFile(File file)
   {
	return null;
   }

   /**
	* This method is used to check whether given file is in valid format or not.
	* @param file
	* @param type
	* @return Boolean
	* @roseuid 3972EFBD01FB
	*/
   public Boolean checkFormat(File file, Integer type)
   {
	return null;
   }

   /**
	* @param files
	* @param type
	* @return ArrayList
	* @throws UploadFailedException
	* @throws InvalidFileFormatException
 * @throws MessageException 
	* @roseuid 3972F04D0035
	*/
   public ArrayList uploadFiles(File file, int type,String userId,String bankId,String zoneId,String branchId) throws UploadFailedException, InvalidFileFormatException,InvalidApplicationException,
																	DatabaseException,SQLException,ClassCastException,NoApplicationFoundException, NoUserFoundException, MessageException
   {
		Log.log(Log.INFO,"FileUploader","uploadFiles","Entered");
   		
		ArrayList errorMessages=new ArrayList();

		try
		{
			//Read the contents from the file.
   			
			if(file.getName().endsWith(".ARC"))
			{
				FileInputStream fileInput=new FileInputStream(file);
				ObjectInputStream objInput=new ObjectInputStream(fileInput);
				Hashtable fileDetails=(Hashtable)objInput.readObject();

				objInput.close();
				objInput=null;
				fileInput.close();
				fileInput=null;

				if(fileDetails==null || fileDetails.size()==0)
				{
					throw new InvalidFileFormatException("No Files are available");
				}

		

				
				//Process the contents of the file.
				
				errorMessages=processFiles(fileDetails,type,userId,bankId,zoneId,branchId);
   				
			}
			else{
   				
				throw new ClassCastException("Not a valid for Upload");
			}			
		}
		catch (InvalidClassException e)
		{
			Log.log(Log.ERROR,"FileUploader","uploadFiles",e.getMessage());
			Log.logException(e);
			errorMessages.add("ThinClient version has been changed. Please dowonload the latest version.");
		}
		catch (FileNotFoundException e)
		{
			Log.log(Log.ERROR,"FileUploader","uploadFiles",e.getMessage());
			Log.logException(e);
			errorMessages.add("File is not uploaded");
			//throw new UploadFailedException("File is not uploaded.");
			
		}
		catch (IOException e)
		{
			Log.log(Log.ERROR,"FileUploader","uploadFiles",e.getMessage());
			Log.logException(e);
			errorMessages.add("Unable to read file");

			//throw new UploadFailedException("Unable to read file");
		}
		catch (ClassNotFoundException e)
		{
			Log.log(Log.ERROR,"FileUploader","uploadFiles",e.getMessage());
			Log.logException(e);
			errorMessages.add("File Format is invalid");

			//throw new InvalidFileFormatException("File Format is invalid");
		}
	catch (ClassCastException e)
	{
		Log.log(Log.ERROR,"FileUploader","uploadFiles",e.getMessage());
		Log.logException(e);
		errorMessages.add("Not a valid File for Upload");

		//throw new InvalidFileFormatException("File Format is invalid");
	}


		Log.log(Log.INFO,"FileUploader","uploadFiles","Exited");
		
		return errorMessages;
   }

   /**
	* This method extract the contents from the file and makes appropriate objects,
	* passes these objects for processing.
	* These processing will happen in the respective modules.
	* @param file
	* @param type
 * @throws MessageException 
	* @roseuid 3972F3020321
	*/
   public ArrayList processFiles(Hashtable fileDetails, int type,String userId,String bankId,String zoneId,String branchId) throws InvalidApplicationException,
																		DatabaseException, SQLException,ClassCastException,NoApplicationFoundException, InvalidClassException, 
																		FileNotFoundException, IOException, NoUserFoundException, MessageException
   {
	ArrayList errorMessages=new ArrayList();
		Log.log(Log.INFO,"FileUploader","processFiles","Entered");
		

			fileDetails.remove("version");

			switch(type)
			{
				case AdminConstants.APPLICATION_FILES :
					{
						Log.log(Log.DEBUG,"FileUploader","processFiles","applications ");
	
						Set keySet=fileDetails.keySet();
						Iterator iterator=keySet.iterator();
						ArrayList applications=new ArrayList();
	
						while(iterator.hasNext())
						{
							Object key=iterator.next();
	
							Log.log(Log.DEBUG,"FileUploader","processFiles","key is "+key);
	
							Application application=(Application)fileDetails.get(key);
	
							Log.log(Log.DEBUG,"FileUploader","processFiles","bank id "+application.getBankId());
							Log.log(Log.DEBUG,"FileUploader","processFiles","ITPAN "+application.getITPAN());
	
							if(application.getBorrowerDetails()!=null)
							{
								Log.log(Log.DEBUG,"FileUploader","processFiles","Os Amt "+application.getBorrowerDetails().getOsAmt());
	
								if(application.getBorrowerDetails().getSsiDetails()!=null)
								{
									Log.log(Log.DEBUG,"FileUploader","processFiles","Address "+application.getBorrowerDetails().getSsiDetails().getAddress());
									Log.log(Log.DEBUG,"FileUploader","processFiles","City "+application.getBorrowerDetails().getSsiDetails().getCity());
									Log.log(Log.DEBUG,"FileUploader","processFiles","Constituition "+application.getBorrowerDetails().getSsiDetails().getConstitution());
									Log.log(Log.DEBUG,"FileUploader","processFiles","First Name "+application.getBorrowerDetails().getSsiDetails().getCpFirstName());
									Log.log(Log.DEBUG,"FileUploader","processFiles","SSI Name "+application.getBorrowerDetails().getSsiDetails().getSsiName());
									Log.log(Log.INFO,"FileUploader","processFiles","Borrower Previously Covered :" + application.getBorrowerDetails().getPreviouslyCovered());
								}
	
							}
							
	/*						Log.log(Log.DEBUG,"FileUploader","processFiles","SSI Name "+application.getTermLoan().getAmountSanctioned());
							Log.log(Log.DEBUG,"FileUploader","processFiles","Credit"+application.getTermLoan().getCreditGuaranteed());
							Log.log(Log.DEBUG,"FileUploader","processFiles","Interest Rate"+application.getTermLoan().getInterestRate());
							Log.log(Log.DEBUG,"FileUploader","processFiles","No of Installments"+application.getTermLoan().getNoOfInstallments());
							Log.log(Log.INFO,"FileUploader","processFiles","Borrower Previously Covered :" + application.getBorrowerDetails().getPreviouslyCovered());
	*/
							Log.log(Log.DEBUG,"FileUploader","processFiles","Project Outlay details"+application.getProjectOutlayDetails());						
							applications.add(application);
	
	
						}
						Log.log(Log.DEBUG,"FileUploader","processFiles","Application Size :" + applications.size());
						//Process the applications.
	
						ApplicationProcessor applicationProcessor=new ApplicationProcessor();
						Log.log(Log.DEBUG,"FileUploader","processFiles","Before Uploading..");
						try {
							errorMessages=applicationProcessor.uploadApplication(applications,userId,bankId,zoneId,branchId);
							Log.log(Log.DEBUG,"FileUploader","processFiles","After Uploading..");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						
						
	
						break;
					}
	
				case AdminConstants.CLAIM_FILES :
					{
					String memberId=bankId + zoneId + branchId;

					Set keySet=fileDetails.keySet();
					Iterator iterator=keySet.iterator();
					ArrayList applications=new ArrayList();
	
					while(iterator.hasNext())
					{
						Object key=iterator.next();
	
						Log.log(Log.DEBUG,"FileUploader","processFiles","key is "+key);
	
						ClaimApplication claimApplication=(ClaimApplication)fileDetails.get(key);
						Log.log(Log.DEBUG,"FileUploader","processFiles","Date of Recall Notice "+ claimApplication.getDateOfIssueOfRecallNotice());
						Log.log(Log.DEBUG,"FileUploader","processFiles","Borrower Id "+ claimApplication.getBorrowerId());
						Log.log(Log.DEBUG,"FileUploader","processFiles","Reasons for turning NPA"+ claimApplication.getReasonsForAccountTurningNPA());
						Log.log(Log.DEBUG,"FileUploader","processFiles","Willful Defaulter"+ claimApplication.getWhetherBorrowerIsWilfulDefaulter());
						Log.log(Log.DEBUG,"FileUploader","processFiles","Willful Defaulter"+ claimApplication.getLegalProceedingsDetails().getForumRecoveryProceedingsInitiated());
						Log.log(Log.DEBUG,"FileUploader","processFiles","Willful Defaulter"+ claimApplication.getLegalProceedingsDetails().getLocation());
						Log.log(Log.DEBUG,"FileUploader","processFiles","Willful Defaulter"+ claimApplication.getLegalProceedingsDetails().getNameOfForum());
						Log.log(Log.DEBUG,"FileUploader","processFiles","Willful Defaulter"+ claimApplication.getLegalProceedingsDetails().getFilingDate());
						Log.log(Log.DEBUG,"FileUploader","processFiles","Willful Defaulter"+ claimApplication.getLegalProceedingsDetails().getAreLegalProceedingsDetailsAvl());
					}

						ClaimsProcessor cpProcessor=new ClaimsProcessor();
						errorMessages=cpProcessor.updateClaimApplication(fileDetails,memberId,userId);
						for(int i=0;i<errorMessages.size();i++)
						{
							String errorMessage=(String)errorMessages.get(i);
							Log.log(Log.DEBUG,"FileUploader","processFiles","error Messages :" + errorMessage);
							
							
						}
						
							
						
						break;
					}
	
				case AdminConstants.PERIODIC_FILES :
					{
						GMProcessor gmProcessor=new GMProcessor();
						errorMessages=gmProcessor.uploadGmApplication(fileDetails,userId,bankId,zoneId,branchId);
						
						Set keySet=fileDetails.keySet();
						Iterator iterator=keySet.iterator();
						ArrayList applications=new ArrayList();
	
						while(iterator.hasNext())
						{
							Object key=iterator.next();
							
							PeriodicInfo periodicInfo=(PeriodicInfo)fileDetails.get(key);
							Log.log(Log.DEBUG,"FileUploader","processFiles","BID"+ periodicInfo.getBorrowerId());
							Log.log(Log.DEBUG,"FileUploader","processFiles","SSi Name"+ periodicInfo.getBorrowerName());
							ArrayList dbrDtl=periodicInfo.getDisbursementDetails();
							int dbrSize=dbrDtl.size();
							for(int i=0;i<dbrSize;i++)
							{
								Disbursement dbr=(Disbursement)dbrDtl.get(i);
								Log.log(Log.DEBUG,"FileUploader","processFiles","Sanctioned Amount"+ dbr.getSanctionedAmount());							
								
							}
							
						}		
					
	
						break;
					}
				default:
					{
						//do nothing.
					}
			}

		Log.log(Log.INFO,"FileUploader","processFiles","Exited");
		for(int i=0;i<errorMessages.size();i++)
		{
			String errorMessage=(String)errorMessages.get(i);
			Log.log(Log.DEBUG,"FileUploader","processFiles","error Messages :" + errorMessage);
							
							
		}
		
		
		return errorMessages;		
   }
}
