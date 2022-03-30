// Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine
// Available From: http://www.reflections.ath.cx
// Decompiler options: packimports(3) 
// Source File Name:   RpProcessor.java

package com.cgtsi.receiptspayments;

import com.cgtsi.actionform.RPActionForm;
import com.cgtsi.admin.Administrator;
import com.cgtsi.admin.ParameterMaster;
import com.cgtsi.admin.User;
import com.cgtsi.application.Application;
import com.cgtsi.application.BorrowerDetails;
import com.cgtsi.application.NoApplicationFoundException;
import com.cgtsi.application.ProjectOutlayDetails;
import com.cgtsi.application.SSIDetails;
import com.cgtsi.claim.CPDAO;
import com.cgtsi.common.DatabaseException;
import com.cgtsi.common.Log;
import com.cgtsi.common.MessageException;
import com.cgtsi.common.NoUserFoundException;
import com.cgtsi.guaranteemaintenance.Recovery;
import com.cgtsi.risk.RiskManagementProcessor;
import com.cgtsi.risk.SubSchemeValues;
import com.cgtsi.util.DBConnection;
import com.cgtsi.util.DateHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

// Referenced classes of package com.cgtsi.receiptspayments:
//            RpDAO, MissingPaymentDetailsException, DemandAdvice, MissingInstrumentException, 
//            MissingDANDetailsException, RpHelper, MissingCGPANsException, NoShortAmountDANsException, 
//            ShortExceedsLimitException, ExcessExceedsLimitException, DANNotGeneratedException, MissingServiceFeeRateException, 
//            MissingCardRateException, VoucherDetail, PaymentDetails, AllocationDetail, 
//            RealisationDetail, PendingPayments, GuaranteeFee

public class RpProcessor {

    public RpProcessor() {
        parameterMaster = null;
    }

    public String insertVoucherDetails(VoucherDetail voucherDetail, 
                                       String userId) throws MessageException, DatabaseException {
        RpDAO rpDAO = new RpDAO();
        return rpDAO.insertVoucherDetails(voucherDetail, userId, null);
    }

    public double getRealisationAmount() {
        return realisationAmount;
    }

    public void setRealisationAmount(double aRealisationAmount) {
        realisationAmount = aRealisationAmount;
    }

    public String getSsiRefnoForCgpan(String cgpan) throws DatabaseException {
        Log.log(4, "RPprocessor", "getSsiRefnoForCgpan", "Entered");
        String ssiRef = null;
        PreparedStatement pStmt = null;
        ArrayList aList = new ArrayList();
        ResultSet rsSet = null;
        Connection connection = DBConnection.getConnection();
        try {
            String query ="SELECT SSI_REFERENCE_NUMBER FROM APPLICATION_DETAIL WHERE CGPAN=?";
            pStmt = connection.prepareStatement(query);
            pStmt.setString(1, cgpan);
            for (rsSet = pStmt.executeQuery(); rsSet.next(); )
                ssiRef = rsSet.getString(1);

            rsSet.close();
            pStmt.close();
        } catch (Exception exception) {
            Log.logException(exception);
            throw new DatabaseException(exception.getMessage());
        } finally {
            DBConnection.freeConnection(connection);
        }
        return ssiRef;
    }

	public double calculateGuaranteeFee(Application application)
			throws DatabaseException {
		String methodName = "calculateGuaranteeFee";
		Log.log(4, "RpProcessor", methodName, "Entered");
		double guaranteeFeeCardRate = 0.0D;
		double cumulativeClaims = 0.0D;
		double cumulativeGuaranteeCover = 0.0D;
		double moderationFactor = 0.0D;
		double defaultRate = 0.0D;
		double guaranteeAmount = 0.0D;
		double sanctionedAmount = 0.0D;
		String sex = "";
		String socialStatus = "";
		String state = "";
		String district = "";
		String scheme = "";
		double enhancementAmount = application.getEnhancementAmount();
		double reapprovedAmount = application.getReapprovedAmount();
		try {
			sex = application.getBorrowerDetails().ssiDetails.getCpGender();
			socialStatus = application.getBorrowerDetails().ssiDetails
					.getSocialCategory();
			state = application.getBorrowerDetails().ssiDetails.getState();
			district = application.getBorrowerDetails().ssiDetails
					.getDistrict();
			scheme = application.getScheme();
		} catch (Exception ex) {
			sex = "";
			socialStatus = "";
			state = "";
			district = "";
		}
		Administrator administrator = new Administrator();
		ParameterMaster parameterMaster = administrator.getParameter();
		double approvedAmount;
		if (application.getReapprovedAmount() == (double) 0)
			approvedAmount = application.getApprovedAmount();
		else
			approvedAmount = application.getReapprovedAmount();
		Log.log(4, "RpProcessor", methodName,
				"scheme " + application.getScheme());
		if (application.getScheme().equalsIgnoreCase("MCGS")) {
			double mcgfGFee = parameterMaster.getMcgfGuaranteeFeePercentage();
			approvedAmount = (approvedAmount * mcgfGFee) / (double) 100;
		}
		Log.log(4, "RpProcessor", methodName, "approvedAmount "
				+ approvedAmount);
		String cgpan = "";
		String subSchemeName = "";
		String ssiRef = "";
		RpDAO rpDAO = new RpDAO();
		CPDAO cpDAO = new CPDAO();
		RiskManagementProcessor rmProcessor = new RiskManagementProcessor();
		SubSchemeValues subSchemeValues = null;
		if (application == null) {
			Log.log(3, "RpProcessor", methodName,
					"Application object passed as parameter is null");
			return -1D;
		}
		subSchemeName = application.getSubSchemeName();
		Log.log(5, "RpProcessor", methodName, "subSchemeName " + subSchemeName);
		if (application.getLoanType().equals("TC"))
			sanctionedAmount = application.getProjectOutlayDetails()
					.getTermCreditSanctioned();
		else if (application.getLoanType().equals("CC"))
			sanctionedAmount = application.getProjectOutlayDetails()
					.getTermCreditSanctioned()
					+ application.getProjectOutlayDetails()
							.getWcFundBasedSanctioned()
					+ application.getProjectOutlayDetails()
							.getWcNonFundBasedSanctioned();
		else if (application.getLoanType().equals("WC"))
			sanctionedAmount = application.getProjectOutlayDetails()
					.getWcFundBasedSanctioned()
					+ application.getProjectOutlayDetails()
							.getWcNonFundBasedSanctioned();
		else if (application.getLoanType().equals("R1"))
			sanctionedAmount = application.getProjectOutlayDetails()
					.getWcFundBasedSanctioned()
					+ application.getProjectOutlayDetails()
							.getWcNonFundBasedSanctioned();
		cgpan = application.getCgpan();
		ssiRef = getSsiRefnoForCgpan(cgpan);
		double tempSanctionedAmount = rpDAO.getTotalSanctionedAmountNew(ssiRef);
		if (reapprovedAmount == (double) 0)
			tempSanctionedAmount += enhancementAmount;
		else
			tempSanctionedAmount = (tempSanctionedAmount + enhancementAmount + reapprovedAmount)
					- approvedAmount;
		Log.log(4, "RpProcessor", methodName, "sanctionedAmount "
				+ sanctionedAmount);
		if (subSchemeName.equals("GLOBAL")) {
			Log.log(5, "RpProcessor", methodName, "Global ");
			defaultRate = parameterMaster.getDefaultRate();
			moderationFactor = parameterMaster.getModerationFactor();
			double guaranteeParam = 0.0D;
			if (scheme.equals("RSF"))
				guaranteeParam = getCardRate1(socialStatus, sex, district,
						state, tempSanctionedAmount, scheme);
			else
				guaranteeParam = getCardRate(socialStatus, sex, district,
						state, tempSanctionedAmount);
			Log.log(5, "RpProcessor", methodName, "guaranteeParam"
					+ guaranteeParam);
			if (guaranteeParam != (double) 0) {
				guaranteeFeeCardRate = guaranteeParam;
				guaranteeAmount = (guaranteeFeeCardRate * approvedAmount
						* ((double) 1 + defaultRate / (double) 100) * moderationFactor)
						/ (double) 100;
			} else {
				Log.log(5, "RpProcessor", methodName, "guaranteeParam is zero");
				guaranteeFeeCardRate = 1.5D;
				if (scheme.equals("RSF"))
					guaranteeFeeCardRate = 0.0D;
				guaranteeAmount = (guaranteeFeeCardRate * approvedAmount
						* ((double) 1 + defaultRate / (double) 100) * moderationFactor)
						/ (double) 100;
			}
		} else {
			subSchemeValues = rmProcessor.getSubSchemeValues(subSchemeName);
			if (subSchemeValues != null) {
				guaranteeFeeCardRate = getCardRate(socialStatus, sex, district,
						state, tempSanctionedAmount);
				defaultRate = subSchemeValues.getDefaultRate();
				if (defaultRate == (double) 0)
					defaultRate = 0.0D;
				moderationFactor = subSchemeValues.getModerationFactor();
				guaranteeAmount = (guaranteeFeeCardRate * approvedAmount
						* ((double) 1 + defaultRate / (double) 100) * moderationFactor)
						/ (double) 100;
			} else if (subSchemeValues == null) {
				defaultRate = 0.0D;
				moderationFactor = 1.0D;
				double guaranteeParam = getCardRate1(socialStatus, sex,
						district, state, tempSanctionedAmount, scheme);
				Log.log(5, "RpProcessor", methodName, "guaranteeParam"
						+ guaranteeParam);
				if (guaranteeParam != (double) 0) {
					guaranteeFeeCardRate = guaranteeParam;
					guaranteeAmount = (guaranteeFeeCardRate * approvedAmount
							* ((double) 1 + defaultRate / (double) 100) * moderationFactor)
							/ (double) 100;
				} else {
					Log.log(5, "RpProcessor", methodName,
							"guaranteeParam is zero");
					guaranteeFeeCardRate = 2.5D;
					guaranteeAmount = (guaranteeFeeCardRate * approvedAmount
							* ((double) 1 + defaultRate / (double) 100) * moderationFactor)
							/ (double) 100;
				}
			}
		}
		Log.log(5, "RpProcessor", methodName,
				"guaranteeFeeCardRate,defaultRate moderationFactor "
						+ guaranteeFeeCardRate + "," + defaultRate + ","
						+ moderationFactor);
		if (defaultRate < (double) 0) {
			Log.log(5, "RpProcessor", methodName, "defaultRate less than zero");
			cumulativeClaims = cpDAO.getCumulativeClaims(application);
			cumulativeGuaranteeCover = rpDAO
					.getCumulativeGuaranteeCover(application);
			Log.log(5, "RpProcessor", methodName,
					"cumulativeClaims,cumulativeGuaranteeCover "
							+ cumulativeClaims + "," + cumulativeGuaranteeCover);
			if (cumulativeGuaranteeCover != (double) 0)
				defaultRate = cumulativeClaims / cumulativeGuaranteeCover;
			else
				defaultRate = 0.0D;
			guaranteeAmount = (guaranteeFeeCardRate * approvedAmount
					* ((double) 1 + defaultRate / (double) 100) * moderationFactor)
					/ (double) 10000;
			Log.log(5, "RpProcessor", methodName, "guaranteeAmount "
					+ guaranteeAmount);
		}
		Log.log(5, "RpProcessor", methodName, "guaranteeAmount "
				+ guaranteeAmount);
		Log.log(4, "RpProcessor", methodName, "Exited");
		return guaranteeAmount;
	}

	public PaymentDetails displayPayInSlip(String paymentId)
			throws MissingPaymentDetailsException, DatabaseException {
		RpDAO rpDAO = new RpDAO();
		PaymentDetails paymentDetails = null;
		if (paymentId == null)
			return null;
		paymentDetails = rpDAO.getPayInSlipDetails(paymentId);
		if (paymentDetails == null)
			throw new MissingPaymentDetailsException(
					"Payment Details are Missing");
		else
			return paymentDetails;
	}

    public String allocateCGDAN(PaymentDetails paymentDetails, 
        Map appropriatedFlags, Map cgpans,Map danCgpanDetails, User user) 
             throws MissingCGPANsException, DatabaseException {
        RpDAO rpDAO = new RpDAO();
        String paymentId = rpDAO.allocateCGDAN(paymentDetails,
        		appropriatedFlags, cgpans, danCgpanDetails, user);
        return paymentId;
    }

    //Diksha 24/05/2017
    
	// rajuk
	
	public String allocateCGDANonlinelaf(PaymentDetails paymentDetails,
			Map appropriatedFlags, Map cgpans, Map danCgpanDetails, User user)
			throws MissingCGPANsException, DatabaseException {
		RpDAO rpDAO = new RpDAO();
		String paymentId = rpDAO.allocateCGDANonlinelaf(paymentDetails,
				appropriatedFlags, cgpans, danCgpanDetails, user);
		return paymentId;
	}
	public String allocateCGDANonlineeaf(PaymentDetails paymentDetails,
			Map appropriatedFlags, Map cgpans, Map danCgpanDetails, User user)
			throws MissingCGPANsException, DatabaseException {
		RpDAO rpDAO = new RpDAO();
		String paymentId = rpDAO.allocateCGDANonlineeaf(paymentDetails,
				appropriatedFlags, cgpans, danCgpanDetails, user);
		return paymentId;
	}
	public String allocateCGDANonlinelsf(PaymentDetails paymentDetails,
			Map appropriatedFlags, Map cgpans, Map danCgpanDetails, User user)
			throws MissingCGPANsException, DatabaseException {
		RpDAO rpDAO = new RpDAO();
		String paymentId = rpDAO.allocateCGDANonlinelsf(paymentDetails,
				appropriatedFlags, cgpans, danCgpanDetails, user);
		return paymentId;
	}
	public String allocateCGDANonlineesf(PaymentDetails paymentDetails,
			Map appropriatedFlags, Map cgpans, Map danCgpanDetails, User user)
			throws MissingCGPANsException, DatabaseException {
		RpDAO rpDAO = new RpDAO();
		String paymentId = rpDAO.allocateCGDANonlineesf(paymentDetails,
				appropriatedFlags, cgpans, danCgpanDetails, user);
		return paymentId;
	}

	public String allocateCGDANonline(PaymentDetails paymentDetails,
			Map appropriatedFlags, Map cgpans, Map danCgpanDetails, User user)
			throws MissingCGPANsException, DatabaseException {
		RpDAO rpDAO = new RpDAO();
		String paymentId = rpDAO.allocateCGDANonline(paymentDetails,
				appropriatedFlags, cgpans, danCgpanDetails, user);
		return paymentId;
	}// rajuk

	public String allocateNEFTCGDAN(PaymentDetails paymentDetails,
			Map appropriatedFlags, Map cgpans, Map danCgpanDetails, User user)
			throws MissingCGPANsException, DatabaseException {
		RpDAO rpDAO = new RpDAO();
		String paymentId = rpDAO.allocateNEFTCGDAN(paymentDetails,
				appropriatedFlags, cgpans, danCgpanDetails, user);
		return paymentId;
	}

	public String allocateNEFTCGDANOld(PaymentDetails paymentDetails,
			ArrayList danSummaries, Map allocationFlags, Map cgpans,
			Map danCgpanDetails, User user) throws MissingCGPANsException,
			DatabaseException {
		RpDAO rpDAO = new RpDAO();
		String paymentId = rpDAO.allocateNEFTCGDANOld(paymentDetails,
				danSummaries, allocationFlags, cgpans, danCgpanDetails, user);
		return paymentId;
	}

	public String allocateASFDAN(PaymentDetails paymentDetails,
			ArrayList danSummaries, Map allocationFlags, Map cgpans,
			Map danCgpanDetails, User user) throws MissingCGPANsException,
			DatabaseException {
		RpDAO rpDAO = new RpDAO();
		String paymentId = rpDAO.allocateASFDAN(paymentDetails, danSummaries,
				allocationFlags, cgpans, danCgpanDetails, user);
		return paymentId;
	}

	public String allocateDAN(PaymentDetails paymentDetails,
			ArrayList danSummaries, Map allocationFlags, Map cgpans,
			Map danCgpanDetails, User user) throws MissingCGPANsException,
			DatabaseException {
		RpDAO rpDAO = new RpDAO();
		String paymentId = rpDAO.allocateDAN(paymentDetails, danSummaries,
				allocationFlags, cgpans, danCgpanDetails, user);
		return paymentId;
	}

	public String appropriateallocateDAN(PaymentDetails paymentDetails,
			ArrayList danSummaries, Map allocationFlags, Map cgpans,
			Map danCgpanDetails, User user) throws MissingCGPANsException,
			DatabaseException {
		RpDAO rpDAO = new RpDAO();
		String paymentId = rpDAO.appropriateallocateDAN(paymentDetails,
				danSummaries, allocationFlags, cgpans, danCgpanDetails, user);
		return paymentId;
	}

	public void deAllocatePayments(String paymentId, String userId)
			throws MissingCGPANsException, DatabaseException {
		Log.log(4, "RPProcessor", "deAllocatePayments", "Entered");
		RpDAO rpDAO = new RpDAO();
		rpDAO.deAllocatePayments(paymentId, userId);
		Log.log(4, "RPProcessor", "deAllocatePayments", "Exited");
	}

	public String insertPaymentDetails(PaymentDetails paymentDetails)
			throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		String paymentId = "";
		paymentId = rpDAO.insertInstrumentDetails(paymentDetails, null);
		return paymentId;
	}

	public String insertPaymentByCGTSI(PaymentDetails paymentDetails)
			throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		String paymentId = "";
		paymentId = rpDAO.insertPaymentByCGTSI(paymentDetails);
		return paymentId;
	}

	public void insertAllocationDetails(String danNo,
			AllocationDetail allocationDetail, User user, String paymentId)
			throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		rpDAO.insertAllocationDetails(danNo, allocationDetail, user, paymentId,
				null);
	}

	public void submitReAllocationDetails(RPActionForm actionForm,
			HttpServletRequest request, User user, String payId)
			throws MessageException, NoApplicationFoundException,
			NoUserFoundException, ShortExceedsLimitException,
			ExcessExceedsLimitException, DatabaseException {
		RpDAO rpDAO = new RpDAO();
		rpDAO.submitReAllocationDetails(actionForm, request, user, payId);
	}

	public void allocatePayments(ArrayList demandAdvices)
			throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		DemandAdvice demandAdvice = null;
		ParameterMaster parameterMaster = null;
		int sizeOfDemandAdvices = 0;
		double amount = 0.0D;
		double totalReallocatedAmount = 0.0D;
		double receivedAmount = 0.0D;
		double shortOrExcess = 0.0D;
		String paymentId = "";
		String allocated = "";
		if (demandAdvices == null)
			return;
		sizeOfDemandAdvices = demandAdvices.size();
		for (int i = 0; i < sizeOfDemandAdvices; i++) {
			demandAdvice = (DemandAdvice) demandAdvices.get(i);
			rpDAO.reallocateAmount(demandAdvice);
			amount = demandAdvice.getAmountRaised();
			paymentId = demandAdvice.getPaymentId();
			allocated = demandAdvice.getAllocated();
			if (allocated.equalsIgnoreCase("Y"))
				totalReallocatedAmount += amount;
		}

		receivedAmount = rpDAO.getReceivedAmount(paymentId);
		shortOrExcess = totalReallocatedAmount - receivedAmount;
		if (shortOrExcess != (double) 0)
			rpDAO.updateShortAmountDetails(paymentId, shortOrExcess);
	}

	public ArrayList displayPaymentsReceived() throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		ArrayList paymentLists = rpDAO.getReceivedPayments();
		return paymentLists;
	}

	public ArrayList displayPaymentsReceivedForGF() throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		ArrayList paymentLists = rpDAO.getReceivedPaymentsForGF();
		return paymentLists;
	}

	public ArrayList displayBatchPaymentsReceivedForGF()
			throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		ArrayList paymentLists = rpDAO.getBatchPaymentReceivedForGF();
		return paymentLists;
	}

	public ArrayList daywiseBatchPaymentsReceivedForGF(Date dateofRealisation)
			throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		ArrayList paymentLists = rpDAO
				.daywiseBatchPaymentsReceivedForGF(dateofRealisation);
		return paymentLists;
	}

	public ArrayList daywiseBatchPaymentsInwardedForGF(Date inwardDate)
			throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		ArrayList paymentLists = rpDAO
				.daywiseBatchPaymentsInwardedForGF(inwardDate);
		return paymentLists;
	}

	public ArrayList displayPaymentsReceivedForASF() throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		ArrayList paymentLists = rpDAO.getReceivedPaymentsForASF();
		return paymentLists;
	}

	public ArrayList displayPaymentsReceivedForCLAIM() throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		ArrayList paymentLists = rpDAO.getReceivedPaymentsForCLAIM();
		return paymentLists;
	}

	public ArrayList displayPaymentsForReallocation() throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		ArrayList paymentLists = rpDAO.getPaymentsForReallocation();
		return paymentLists;
	}

	public ArrayList getPaymentDetails(String paymentId)
			throws MissingDANDetailsException, MissingInstrumentException,
			DatabaseException {
		RpDAO rpDAO = new RpDAO();
		PaymentDetails paymentDetails = null;
		ArrayList danDetails = null;
		ArrayList instrumentAndDANDetails = new ArrayList();
		int sizeOfDANDetails = 0;
		DemandAdvice danDetail = null;
		if (paymentId == null)
			return null;
		Log.log(4, "RpProcessor", "getPaymentDetails",
				"Before calling Instrument details");
		paymentDetails = rpDAO.getInstrumentDetails(paymentId);
		Log.log(4, "RpProcessor", "getPaymentDetails",
				"After calling Instrument details");
		if (paymentDetails == null)
			throw new MissingInstrumentException(
					"Instrument Details are missing");
		instrumentAndDANDetails.add(paymentDetails);
		Log.log(4, "RpProcessor", "getPaymentDetails",
				"Before calling get DAN details");
		danDetails = rpDAO.getDANDetails(paymentId);
		Log.log(4, "RpProcessor", "getPaymentDetails",
				"After calling get DAN details");
		if (danDetails == null)
			throw new MissingDANDetailsException(
					"No DANs available for the given Payment Details");
		sizeOfDANDetails = danDetails.size();
		for (int i = 0; i < sizeOfDANDetails; i++) {
			danDetail = (DemandAdvice) danDetails.get(i);
			instrumentAndDANDetails.add(danDetail);
		}

		return instrumentAndDANDetails;
	}

	public void updateRealisationDetails(String paymentId,
			Date realisationDate, double realisationAmount)
			throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		if (paymentId == null) {
			return;
		} else {
			rpDAO.updateRealisationDetails(paymentId, realisationDate,
					realisationAmount);
			return;
		}
	}

	public int aftergfbatchappropriatePayments(String paymentId,
			String appropriationBy) throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		if (paymentId == null) {
			throw new DatabaseException("No Appropriation Made.");
		} else {
			int i = rpDAO.aftergfbatchappropriatePayments(paymentId,
					appropriationBy);
			return i;
		}
	}

	public int aftergfdaywisebatchappropriatePayments(String paymentId,
			String appropriationBy, Date dateofRealisation)
			throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		if (paymentId == null) {
			throw new DatabaseException("No Appropriation Made.");
		} else {
			int i = rpDAO.aftergfdaywisebatchappropriatePayments(paymentId,
					appropriationBy, dateofRealisation);
			return i;
		}
	}

	public int dayWiseddMarkedForDepositedSummary(String inwardId,
			String instrumentNo, String userId, Date depositDate)
			throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		if (inwardId == null) {
			throw new DatabaseException("No Selection Made.");
		} else {
			int i = rpDAO.dayWiseddMarkedForDepositedSummary(inwardId,
					instrumentNo, userId, depositDate);
			return i;
		}
	}

	public double appropriatePayment(ArrayList demandAdvices,
			RealisationDetail realisationDetail, String contextPath)
			throws MessageException, NoApplicationFoundException,
			NoUserFoundException, ExcessExceedsLimitException,
			ShortExceedsLimitException, DatabaseException {
		RpDAO rpDAO = new RpDAO();
		double retValue = rpDAO.appropriatePayment(demandAdvices,
				realisationDetail, false, contextPath);
		return retValue;
	}

	public ArrayList reapproveLoanAmount(Application application,
			double reapprovedAmt, User user, String contextPath)
			throws MessageException, NoApplicationFoundException,
			DANNotGeneratedException, MissingServiceFeeRateException,
			MissingCardRateException, DatabaseException {
		Log.log(4, "RpProcessor", "reapproveLoanAmount", "Entered");
		RpDAO rpDAO = new RpDAO();
		ArrayList shortExcessAmounts = rpDAO.reapproveLoanAmount(application,
				reapprovedAmt, user, contextPath);
		Log.log(4, "RpProcessor", "reapproveLoanAmount", "Exited");
		return shortExcessAmounts;
	}

	public void enterRecoveryDetails(Recovery recovery)
			throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		CPDAO cpDAO = new CPDAO();
		DemandAdvice demandAdvice = null;
		RpHelper rpHelper = new RpHelper();
		String borrowerId = "";
		boolean isSettlementmade = false;
		String cldan = "";
		if (recovery == null)
			return;
		rpDAO.updateRecoveryDetails(recovery);
		borrowerId = recovery.getCgbid();
		isSettlementmade = cpDAO.isClaimSettlementMade(borrowerId);
		if (isSettlementmade) {
			cldan = rpHelper.generateCLDANId();
			demandAdvice = calculateCLDANAmount(recovery, cldan);
			rpDAO.updateCLDANDetails(demandAdvice);
		}
	}

	public DemandAdvice calculateCLDANAmount(Recovery recovery, String cldan)
			throws DatabaseException {
		double amountRecovered = 0.0D;
		double courtCharges = 0.0D;
		double lawyerCharges = 0.0D;
		double insurance = 0.0D;
		double administrativeCharges = 0.0D;
		double cldanAmount = 0.0D;
		if (recovery == null) {
			return null;
		} else {
			amountRecovered = recovery.getAmountRecovered();
			cldanAmount = amountRecovered
					- (courtCharges + lawyerCharges + insurance + administrativeCharges);
			DemandAdvice demandAdvice = new DemandAdvice(cldanAmount, cldan);
			return demandAdvice;
		}
	}

	public ArrayList displayCGPANs(String danNo) throws MissingCGPANsException,
			DatabaseException {
		RpDAO rpDAO = new RpDAO();
		ArrayList panDetails = null;
		if (danNo.equals(""))
			return null;
		panDetails = rpDAO.getCGPANsForDAN(danNo);
		if (panDetails == null)
			throw new MissingCGPANsException("CGPANs are missing");
		else
			return panDetails;
	}

	public ArrayList displaySFCGPANs(String danNo)
			throws MissingCGPANsException, DatabaseException {
		RpDAO rpDAO = new RpDAO();
		ArrayList panDetails = null;
		if (danNo.equals(""))
			return null;
		panDetails = rpDAO.getSFCGPANsForDAN(danNo);
		if (panDetails == null)
			throw new MissingCGPANsException("CGPANs are missing");
		else
			return panDetails;
	}

	public ArrayList viewShortAmountDetails(String memberId)
			throws NoShortAmountDANsException, DatabaseException {
		RpDAO rpDAO = new RpDAO();
		ArrayList shortAmountDetails = null;
		if (memberId.equals(""))
			return null;
		shortAmountDetails = rpDAO.getShortAmountDANs(memberId);
		if (shortAmountDetails == null)
			throw new NoShortAmountDANsException("No Short amount dans found");
		else
			return shortAmountDetails;
	}

	public void waiveShortDANs(String shdan) throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		rpDAO.waiveShortDANs(shdan);
	}

	public ArrayList displayDANs(String bankId, String zoneId, String branchId)
			throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		ArrayList danDetails = rpDAO.getDANSummaryForMember(bankId, zoneId,
				branchId);
		return danDetails;
	}

	public ArrayList displayASFDANs(String bankId, String zoneId,
			String branchId) throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		ArrayList danDetails = rpDAO.getASFDANSummaryForMember(bankId, zoneId,
				branchId);
		return danDetails;
	}

	public ArrayList displayASFDANsforExpired(String bankId, String zoneId,
			String branchId) throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		ArrayList danDetails = rpDAO.getExpASFDANSummaryForMember(bankId,
				zoneId, branchId);
		return danDetails;
	}

	public ArrayList displayGFDANs(String bankId, String zoneId, String branchId)
			throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		ArrayList danDetails = rpDAO.getCGDANSummaryForMember(bankId, zoneId,
				branchId);
		return danDetails;
	}

	public ArrayList displayTextileGFDANs() throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		ArrayList danDetails = rpDAO.getCGDANSummaryForTextiles();
		return danDetails;
	}

	public ArrayList displaySFDANs(String bankId, String zoneId, String branchId)
			throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		ArrayList danDetails = rpDAO.getSFDANSummaryForMember(bankId, zoneId,
				branchId);
		return danDetails;
	}

	public void addToArrayList(PendingPayments pendingPayments) {
		ArrayList pendingPaymentsList = new ArrayList();
		if (pendingPayments == null) {
			return;
		} else {
			pendingPaymentsList.add(pendingPayments);
			return;
		}
	}

	public boolean generateVoucher(String memberId, String voucherType)
			throws DatabaseException {
		return false;
	}

	public void interfaceWithBAS() {
	}

	public void calculatePenaltyForOverdueDANs(User user)
			throws DatabaseException {
		String methodName = "calculatePenaltyForOverdueDANs";
		RpDAO rpDAO = new RpDAO();
		Administrator administrator = new Administrator();
		double penalty = 0.0D;
		double penaltyRate = 0.0D;
		ParameterMaster parameterMaster = administrator.getParameter();
		Date dueDate = null;
		long noOfMonths = 0L;
		double existingPenalty = 0.0D;
		double amountRaised = 0.0D;
		double calculatedPenalty = 0.0D;
		ArrayList dansBeyondDueDate = rpDAO.getDANsBeyondDueDate();
		if (dansBeyondDueDate != null) {
			int noOfDANsBeyondDueDate = dansBeyondDueDate.size();
			for (int i = 0; i < noOfDANsBeyondDueDate; i++) {
				DemandAdvice demandAdvice = (DemandAdvice) dansBeyondDueDate
						.get(i);
				calculatedPenalty = calculatePenalty(demandAdvice);
				if (calculatedPenalty != existingPenalty) {
					demandAdvice.setPenalty(calculatedPenalty);
					rpDAO.updatePenalty(demandAdvice, user);
				}
			}

		}
	}

	public void generateCGDAN(User user, String memberId)
			throws NoApplicationFoundException, DatabaseException {
		RpDAO rpDAO = new RpDAO();
		rpDAO.generateCGDAN(user, memberId);
		Log.log(4, "RpProcessor", "generateCGDAN", "Exited");
	}

	public void generateCGDAN(User user, String memberId, java.sql.Date fromDt,
			java.sql.Date toDt) throws NoApplicationFoundException,
			DatabaseException {
		RpDAO rpDAO = new RpDAO();
		rpDAO.generateCGDAN(user, memberId, fromDt, toDt);
		Log.log(4, "RpProcessor", "generateCGDAN", "Exited");
	}

	public void generateSFDAN(User user, String bnkId, String zneId,
			String brnId) throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		rpDAO.generateSFDAN(user, bnkId, zneId, brnId);
	}

	public void generateBatchSFDAN(User user, String bnkId)
			throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		rpDAO.generateBatchSFDAN(user, bnkId);
	}

	public void generateSFDANEXP(User user, String bnkId, String zneId,
			String brnId) throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		rpDAO.generateSFDANEXP(user, bnkId, zneId, brnId);
	}

	public void generateSHDAN(User user, String memberId)
			throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		rpDAO.generateSHDAN(user, memberId);
	}

	public Date getPANDueDate(DemandAdvice demandAdvice,
			Date firstDisbursementDate) throws DatabaseException {
		String methodName = "getPANDueDate";
		Log.log(4, "RpProcessor", methodName, "Entered");
		int noOfDaysDue = 0;
		Calendar calendar = Calendar.getInstance();
		Date startDate = null;
		Date panDueDate = null;
		Administrator administrator = new Administrator();
		parameterMaster = administrator.getParameter();
		Date fDisbursementDate = null;
		RpDAO rpDAO = new RpDAO();
		String cgpan = demandAdvice.getCgpan();
		String danType = demandAdvice.getDanType();
		Log.log(5, "RpProcessor", methodName, "Dan Type = " + danType);
		Date danGeneratedDate = demandAdvice.getDanGeneratedDate();
		String typeOfPAN = cgpan.substring(11, 12);
		Log.log(5, "RpProcessor", methodName, "Type of PAN,danGeneratedDate: "
				+ typeOfPAN + "," + danGeneratedDate);
		if (typeOfPAN.equals("T")) {
			Log.log(5, "RpProcessor", methodName,
					"Entering condition for Term Credit application");
			if (danType.equals("CG")) {
				Log.log(5, "RpProcessor", methodName,
						"Entering if loop for CGDAN");
				Log.log(5, "RpProcessor", methodName,
						"First Disbursement Date: " + firstDisbursementDate);
				if (firstDisbursementDate == null) {
					Log.log(5, "RpProcessor", methodName,
							"Getting First Disbursement Date from the database");
					fDisbursementDate = rpDAO.getFirstDisbursementDate(cgpan);
					Log.log(5, "RpProcessor", methodName,
							"After Getting First Disbursement Date from the database:"
									+ fDisbursementDate);
					if (fDisbursementDate == null)
						firstDisbursementDate = danGeneratedDate;
					else
						firstDisbursementDate = fDisbursementDate;
				}
				Log.log(5, "RpProcessor", methodName,
						"Before checking latest of the dates");
				Log.log(5, "RpProcessor", methodName, "DAN generated Date = "
						+ danGeneratedDate);
				Log.log(5, "RpProcessor", methodName,
						"First Disbursement Date = " + firstDisbursementDate);
				if (firstDisbursementDate.after(danGeneratedDate)) {
					Log.log(5, "RpProcessor", methodName,
							"First disbursement date greater than generated date");
					startDate = firstDisbursementDate;
				} else {
					Log.log(5, "RpProcessor", methodName,
							"generated date greater than or equals First disbursement date");
					startDate = danGeneratedDate;
				}
				noOfDaysDue = parameterMaster.getGuaranteeFeeWithoutPenalty();
				Log.log(5, "RpProcessor", methodName,
						"No. of days Guarantee Fee Due = " + noOfDaysDue);
			} else if (danType.equals("SF")) {
				Log.log(5, "RpProcessor", methodName,
						"Entering if loop for SFDAN");
				startDate = danGeneratedDate;
				noOfDaysDue = parameterMaster.getServiceFeeWithoutPenalty();
			} else if (danType.equals("SH")) {
				Log.log(5, "RpProcessor", methodName,
						"Entering if loop for SHDAN");
				startDate = danGeneratedDate;
				noOfDaysDue = parameterMaster.getServiceFeeWithoutPenalty();
			} else if (danType.equals("CL")) {
				Log.log(5, "RpProcessor", methodName,
						"Entering if loop for CLDAN");
				startDate = danGeneratedDate;
				noOfDaysDue = parameterMaster.getServiceFeeWithoutPenalty();
			}
		} else {
			Log.log(5, "RpProcessor", methodName,
					"Entering condition for Working capital application");
			startDate = danGeneratedDate;
			if (danType.equals("CG"))
				noOfDaysDue = parameterMaster.getGuaranteeFeeWithoutPenalty();
			else if (danType.equals("SF"))
				noOfDaysDue = parameterMaster.getServiceFeeWithoutPenalty();
			else if (danType.equals("SH"))
				noOfDaysDue = parameterMaster.getServiceFeeWithoutPenalty();
			else if (danType.equals("CL"))
				noOfDaysDue = parameterMaster.getServiceFeeWithoutPenalty();
		}
		Log.log(5, "RpProcessor", methodName, "Start Date: " + startDate);
		calendar.setTime(startDate);
		calendar.add(5, noOfDaysDue);
		panDueDate = calendar.getTime();
		Log.log(5, "RpProcessor", methodName, "Due Date: " + panDueDate);
		Log.log(5, "RpProcessor", methodName, "Exited");
		return panDueDate;
	}

	public boolean isServiceFeeCalculated(String cgpan, Date asOnDate)
			throws DatabaseException {
		String methodName = "isServiceFeeCalculated";
		RpDAO rpDAO = new RpDAO();
		boolean feeCalculated = false;
		int serviceFeeCount = 0;
		Log.log(4, "RpProcessor", methodName, "Entering");
		serviceFeeCount = rpDAO.getServiceFeeCount(cgpan, asOnDate);
		Log.log(4, "RpProcessor", methodName, "Service fee count for CGPAN = "
				+ cgpan + " as on " + asOnDate);
		if (serviceFeeCount > 0)
			feeCalculated = true;
		Log.log(4, "RpProcessor", methodName, "Return value = " + feeCalculated);
		return feeCalculated;
	}

	public double calculatePenalty(DemandAdvice demandAdvice)
			throws DatabaseException {
		String methodName = "calculatePenalty";
		RpDAO rpDAO = new RpDAO();
		Administrator administrator = new Administrator();
		parameterMaster = administrator.getParameter();
		int noOfDaysDue = 0;
		Date penaltyDate = new Date();
		Date currentDate = new Date();
		Calendar date1 = Calendar.getInstance();
		date1.setTime(currentDate);
		Calendar date2 = Calendar.getInstance();
		String cgpan = demandAdvice.getCgpan();
		String danNo = demandAdvice.getDanNo();
		String danType = demandAdvice.getDanType();
		Date dueDate = demandAdvice.getDanDueDate();
		double amountRaised = demandAdvice.getAmountRaised();
		Log.log(4, "RpProcessor", methodName, "amountRaised :" + amountRaised);
		double existingPenalty = demandAdvice.getPenalty();
		Date disDate = rpDAO.getFirstDisbursementDate(cgpan);
		Log.log(4, "RpProcessor", methodName, "disDate :" + disDate);
		if (disDate != null && !disDate.toString().equals("")) {
			Log.log(4, "RpProcessor", methodName, "disDate not null");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(disDate);
			if (danType.equals("SF")) {
				Log.log(5, "RpProcessor", methodName,
						"Entering if loop for SFDAN");
				noOfDaysDue = parameterMaster.getServiceFeeWithoutPenalty();
			} else {
				noOfDaysDue = parameterMaster.getGuaranteeFeeWithoutPenalty();
			}
			Log.log(5, "RpProcessor", methodName, "noOfDaysDue :" + noOfDaysDue);
			calendar.add(5, noOfDaysDue);
			Date fDisDate = calendar.getTime();
			Log.log(5, "RpProcessor", methodName, "fDisDate :" + fDisDate);
			if (fDisDate.after(dueDate))
				penaltyDate = fDisDate;
			else
				penaltyDate = dueDate;
		} else {
			penaltyDate = dueDate;
		}
		Log.log(5, "RpProcessor", methodName, "penaltyDate :" + penaltyDate);
		date2.setTime(penaltyDate);
		long noOfMonths = DateHelper.getMonthDifference(date2, date1);
		Log.log(4, "RpProcessor", methodName,
				"No. of months between 2 dates = " + noOfMonths);
		double penaltyRate;
		if (danType.equals("SF"))
			penaltyRate = parameterMaster.getServiceFeePenaltyRate();
		else
			penaltyRate = parameterMaster.getGuaranteeFeePenaltyRate();
		double bankRate = parameterMaster.getBankRate();
		double totalPenaltyRate = penaltyRate + bankRate;
		Log.log(5, "RpProcessor", methodName, "penaltyRate :" + penaltyRate);
		Log.log(5, "RpProcessor", methodName, "bankRate :" + bankRate);
		Log.log(5, "RpProcessor", methodName, "totalPenaltyRate :"
				+ totalPenaltyRate);
		double calculatedPenalty = (totalPenaltyRate * amountRaised * (double) noOfMonths)
				/ (double) 1200;
		return calculatedPenalty;
	}

	public void updateGuaranteeFee(GuaranteeFee guaranteeFee, String userId)
			throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		rpDAO.updateGuaranteeFee(guaranteeFee, userId, null);
	}

	public ArrayList getAllRefundAdvices() throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		return rpDAO.getAllRefundAdvices();
	}

	public ArrayList getDANDetails(String paymentId) throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		return rpDAO.getDANDetails(paymentId);
	}

	public ArrayList getDANDetailsForReallocation(String paymentId,
			String memberId) throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		return rpDAO.getDANDetailsForReallocation(paymentId, memberId);
	}

	public ArrayList getAllocatedCGPANsForDAN(String danNo)
			throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		return rpDAO.getAllocatedCGPANsForDAN(danNo);
	}

	public ArrayList getPANDetailsForReallocation(DemandAdvice demandAdvice)
			throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		return rpDAO.getPANDetailsForReallocation(demandAdvice);
	}

	public Vector getDateWiseDANDetails(java.sql.Date fromDate,
			java.sql.Date toDate) throws MessageException, DatabaseException {
		RpDAO rpDAO = new RpDAO();
		return rpDAO.getDateWiseDANDetails(fromDate, toDate);
	}

	public HashMap getMLIWiseDANDetails(String memberId)
			throws MessageException, DatabaseException {
		RpDAO rpDAO = new RpDAO();
		return rpDAO.getMLIWiseDANDetails(memberId);
	}

	public void generateCLDAN(User user, String bankId, String zoneId,
			String branchId) throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		rpDAO.generateCLDAN(user, bankId, zoneId, branchId);
	}

	public ArrayList getGLHeads() throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		return rpDAO.getGLHeads();
	}

	public ArrayList showShortDansForWaive(String bankId, String zoneId,
			String branchId) throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		ArrayList shortDansList = rpDAO.showShortDansForWaive(bankId, zoneId,
				branchId);
		if (shortDansList == null || shortDansList.size() == 0)
			shortDansList = null;
		return shortDansList;
	}

	public double getRefundAmountForMember(String mliId)
			throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		return rpDAO.getRefundAmountForMember(mliId);
	}

	public String generateRefundAdvice(String mliId, String userId)
			throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		return rpDAO.generateRefundAdvice(mliId, userId);
	}

	public ArrayList getPaymentList(Date fromDate, Date toDate,
			String dateType, String memberId) throws MessageException,
			DatabaseException {
		RpDAO rpDAO = new RpDAO();
		Log.log(4, "RpProcessor", "getPaymentList", " from date " + fromDate);
		Log.log(4, "RpProcessor", "getPaymentList", " to date " + toDate);
		return rpDAO.getPaymentList(fromDate, toDate, dateType, memberId);
	}

	public String getGLName(String glCode) throws MessageException,
			DatabaseException {
		RpDAO rpDAO = new RpDAO();
		return rpDAO.getGLName(glCode);
	}

	public ArrayList getMemberIdsForSHDAN() throws MessageException,
			DatabaseException {
		RpDAO rpDAO = new RpDAO();
		return rpDAO.getMemberIdsForSHDAN();
	}

	public ArrayList getMemberIdsForExcess() throws MessageException,
			DatabaseException {
		RpDAO rpDAO = new RpDAO();
		return rpDAO.getMemberIdsForExcess();
	}

	public double getAmountForExcess(String memberId) throws MessageException,
			DatabaseException {
		RpDAO rpDAO = new RpDAO();
		return rpDAO.getAmountForExcess(memberId);
	}

	public void updateIdForExcess(String memberId, String voucherId)
			throws MessageException, DatabaseException {
		RpDAO rpDAO = new RpDAO();
		rpDAO.updateIdForExcess(memberId, voucherId);
	}

	public ArrayList getAllCardRates() throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		ArrayList cardRateList = rpDAO.getAllCardRates();
		return cardRateList;
	}

	public void updateCardRate(int cardId, double cardRate, String userId)
			throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		rpDAO.updateCardRate(cardId, cardRate, userId);
	}

	public double getCardRate(double sanctionedAmount) throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		double gfCardRate = rpDAO.getCardRate(sanctionedAmount);
		return gfCardRate;
	}

	public double getCardRate(String socialStatus, String sex, String district,
			String state, double sanctionedAmount) throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		double gfCardRate = rpDAO.getCardRate(socialStatus, sex, district,
				state, sanctionedAmount);
		return gfCardRate;
	}

	public double getCardRate1(String socialStatus, String sex,
			String district, String state, double sanctionedAmount,
			String scheme) throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		double gfCardRate = rpDAO.getCardRate1(socialStatus, sex, district,
				state, sanctionedAmount, scheme);
		return gfCardRate;
	}

	private final String className = "RpProcessor";
	private double realisationAmount;
	private ParameterMaster parameterMaster;

	public ArrayList displayLiveAFDANs(String bankId, String zoneId,
			String branchId) throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		ArrayList danDetails = rpDAO.getLiveAFDANSummaryForMember(bankId,
				zoneId, branchId);
		return danDetails;
	}
//Diksha 24/05/2017
	public ArrayList displayOnlineLiveAFDANs(String bankId, String zoneId,
			String branchId) throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		ArrayList danDetails = rpDAO.getOnlineLiveAFDANSummaryForMember(bankId,
				zoneId, branchId);
		return danDetails;
	}
	public ArrayList displayOnlineExpiredAFDANs(String bankId, String zoneId,
			String branchId) throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		ArrayList danDetails = rpDAO.getOnlineExpiredAFDANSummaryForMember(bankId,
				zoneId, branchId);
		return danDetails;
	}
	public ArrayList displayOnlineLiveSFDANs(String bankId, String zoneId,
			String branchId) throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		ArrayList danDetails = rpDAO.getOnlineLiveSFDANSummaryForMember(bankId,
				zoneId, branchId);
		return danDetails;
	}
	public ArrayList displayOnlineExpiredSFDANs(String bankId, String zoneId,
			String branchId) throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		ArrayList danDetails = rpDAO.getOnlineExpiredSFDANSummaryForMember(bankId,
				zoneId, branchId);
		return danDetails;
	}

	// rajuk

	public ArrayList displayLiveGFDANs(String bankId, String zoneId,
			String branchId) throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		ArrayList danDetails = rpDAO.getLiveGFDANSummaryForMember(bankId,
				zoneId, branchId);
		return danDetails;
	}

	
	public ArrayList displayGFDANData(String danIdTxt,String memberId,Date dtFromDate,Date dtToDate)throws DatabaseException 
	{
		RpDAO rpDAO = new RpDAO();
		ArrayList danDetails = rpDAO.getLiveGFDANSummaryData(danIdTxt,memberId,dtFromDate,dtToDate);
		return danDetails;
	}
	
	public ArrayList displayPaymentData(String paymentModeRP)throws DatabaseException 
	{
		RpDAO rpDAO = new RpDAO();
		ArrayList danDetails = rpDAO.getPaymentData(paymentModeRP);
		return danDetails;
	}
	
	//end by archana
	
	public ArrayList displayExpiredAFDANs(String bankId, String zoneId,
			String branchId) throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		ArrayList danDetails = rpDAO.getExpiredAFDANSummaryForMember(bankId,
				zoneId, branchId);
		return danDetails;
	}

	public ArrayList displayLiveASFDANs(String bankId, String zoneId,
			String branchId) throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		ArrayList danDetails = rpDAO.getLiveASFDANSummaryForMember(bankId,
				zoneId, branchId);
		return danDetails;
	}

	public ArrayList displayExpiredASFDANs(String bankId, String zoneId,
			String branchId) throws DatabaseException {
		RpDAO rpDAO = new RpDAO();
		ArrayList danDetails = rpDAO.getExpiredASFDANSummaryForMember(bankId,
				zoneId, branchId);
		return danDetails;
	}
	
	public ArrayList displayDetails(String fromDate,String toDate,String mliName)throws DatabaseException
	{
	RpDAO rpDAO = new RpDAO();
	ArrayList details = rpDAO.getdisplayData(fromDate,toDate,mliName);
	return details;
	}

}
//24/05/2017 end
//Diksha 24/05/2017
/*
    public ArrayList displayExpiredAFDANs(String bankId, String zoneId, 
                                          String branchId) throws DatabaseException {
        RpDAO rpDAO = new RpDAO();
        ArrayList danDetails = 
            rpDAO.getExpiredAFDANSummaryForMember(bankId, zoneId, branchId);
        return danDetails;
    }

    public ArrayList displayLiveASFDANs(String bankId, String zoneId, 
                                        String branchId) throws DatabaseException {
        RpDAO rpDAO = new RpDAO();
        ArrayList danDetails = 
            rpDAO.getLiveASFDANSummaryForMember(bankId, zoneId, branchId);
        return danDetails;
    }

    public ArrayList displayExpiredASFDANs(String bankId, String zoneId, 
                                           String branchId) throws DatabaseException {
        RpDAO rpDAO = new RpDAO();
        ArrayList danDetails = 
            rpDAO.getExpiredASFDANSummaryForMember(bankId, zoneId, branchId);
        return danDetails;
    }

    
    public ArrayList displayLiveGFDANs(String bankId, String zoneId, 
                String branchId) throws DatabaseException {
    RpDAO rpDAO = new RpDAO();
    ArrayList danDetails = 
    rpDAO.getLiveGFDANSummaryForMember(bankId, zoneId, branchId);
    return danDetails;
    }
    
    public String allocateCGDANonline(PaymentDetails paymentDetails, 
            Map appropriatedFlags, Map cgpans, 
            Map danCgpanDetails, 
            User user) throws MissingCGPANsException, 
                              DatabaseException {
             RpDAO rpDAO = new RpDAO();
            String paymentId = rpDAO.allocateCGDANonline(paymentDetails, appropriatedFlags, cgpans, danCgpanDetails, user);
            return paymentId;
}//rajuk
}
*/
//24/05/2017 end