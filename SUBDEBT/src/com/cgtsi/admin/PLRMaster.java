/*
 * Created on Nov 19, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cgtsi.admin;
import com.cgtsi.common.DatabaseException;
import java.util.Date;
import java.util.StringTokenizer;
import com.cgtsi.common.Log;
import java.util.Vector;
import java.util.ArrayList;
// import java.util.Calendar;

/**
 * @author RP14480
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PLRMaster {

	private String bankName;
	private String newBankName;
	private String shortNameMemId;
	private Date startDate;
	private Date endDate;
	private double shortTermPLR;
	private double mediumTermPLR;
	private double longTermPLR;
	private int shortTermPeriod;
	private int mediumTermPeriod;
	private int longTermPeriod;
	private String createdBy;
	private String PLR;  //Type of PLR.    (rp14480)
	private double BPLR; //Bench Mark PLR. (rp14480)
	private String bankId;
	private int plrId=0;
	public PLRMaster() {

	}


	/**
	  * Updates the state details by calling the DBClass in AdminDAO
	  */
	   public void updateMaster() throws DatabaseException, InvalidDataException
	   {
			AdminDAO adminDAO=new AdminDAO();
			StringTokenizer tokenizer = new StringTokenizer(this.shortNameMemId,")");
			Vector tokens = new Vector();
			String nextToken = null;
			String memberId = null;
			String memberIdToken = null;
			while(tokenizer.hasMoreElements())
			{
				nextToken = (String)tokenizer.nextElement();
				Log.log(Log.INFO,"AdministrationAction","getPlrBankDetails","**************");
				Log.log(Log.INFO,"AdministrationAction","getPlrBankDetails","Next Token :" + nextToken);
				Log.log(Log.INFO,"AdministrationAction","getPlrBankDetails","**************");
				tokens.addElement(nextToken);
			}
			if((tokens != null) && (tokens.size() > 0))
			{
				 memberIdToken = (String)tokens.elementAt(0);
			}
			else
			{
				Log.log(Log.INFO,"PLRMaster","updateMaster()","Member Id could not be parsed.");
			}
			if(memberIdToken != null)
			{
				memberId = memberIdToken.substring(1,memberIdToken.length());
			}
			else
			{
				Log.log(Log.INFO,"PLRMaster","updateMaster()","Member Id could not be parsed.");
			}
			String bankId = memberId.substring(0,4);
			// String zoneId = memberId.substring(4,8);
			// String branchId = memberId.substring(8,12);
			setBankId(bankId);
			Administrator admin = new Administrator();
			ArrayList plrMasters= admin.getPlrDetails(bankId);
			boolean flag = admin.validatePLRDetails(this,plrMasters,getCreatedBy());
			if(flag)
			{
				adminDAO.insertPLRMaster(this,getCreatedBy());
			}

		}

	/**
	 * @return
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param string
	 */
	public void setCreatedBy(String string) {
		createdBy = string;
	}

	/**
	 * @return
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @return
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @return
	 */
	public int getLongTermPeriod() {
		return longTermPeriod;
	}

	/**
	 * @return
	 */
	public double getLongTermPLR() {
		return longTermPLR;
	}

	/**
	 * @return
	 */
	public int getMediumTermPeriod() {
		return mediumTermPeriod;
	}

	/**
	 * @return
	 */
	public double getMediumTermPLR() {
		return mediumTermPLR;
	}

	/**
	 * @return
	 */
	public int getShortTermPeriod() {
		return shortTermPeriod;
	}

	/**
	 * @return
	 */
	public double getShortTermPLR() {
		return shortTermPLR;
	}

	/**
	 * @return
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param string
	 */
	public void setBankName(String string) {
		bankName = string;
	}

	/**
	 * @param date
	 */
	public void setEndDate(Date date) {
		endDate = date;
	}

	/**
	 * @param i
	 */
	public void setLongTermPeriod(int i) {
		longTermPeriod = i;
	}

	/**
	 * @param d
	 */
	public void setLongTermPLR(double d) {
		longTermPLR = d;
	}

	/**
	 * @param i
	 */
	public void setMediumTermPeriod(int i) {
		mediumTermPeriod = i;
	}

	/**
	 * @param d
	 */
	public void setMediumTermPLR(double d) {
		mediumTermPLR = d;
	}

	/**
	 * @param i
	 */
	public void setShortTermPeriod(int i) {
		shortTermPeriod = i;
	}

	/**
	 * @param d
	 */
	public void setShortTermPLR(double d) {
		shortTermPLR = d;
	}

	/**
	 * @param date
	 */
	public void setStartDate(Date date) {
		startDate = date;
	}

	/**
	 * @return
	 */
	public String getNewBankName() {
		return newBankName;
	}

	/**
	 * @param string
	 */
	public void setNewBankName(String string) {
		newBankName = string;
	}

	/**
	 * @return
	 */
	public double getBPLR() {
		return BPLR;
	}

	/**
	 * @return
	 */
	public String getPLR() {
		return PLR;
	}

	/**
	 * @param d
	 */
	public void setBPLR(double d) {
		BPLR = d;
	}

	/**
	 * @param string
	 */
	public void setPLR(String string) {
		PLR = string;
	}

    public String getShortNameMemId()
    {
		return this.shortNameMemId;
	}

	public void setShortNameMemId(String id)
	{
		this.shortNameMemId = id;
	}

	public String getBankId()
	{
		return this.bankId;
	}

	public void setBankId(String id)
	{
		this.bankId = id;
	}

	/**
	 * @return
	 */
	public int getPlrId() {
		return plrId;
	}

	/**
	 * @param i
	 */
	public void setPlrId(int i) {
		plrId = i;
	}
}
