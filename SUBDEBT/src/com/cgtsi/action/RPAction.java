package com.cgtsi.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.Vector;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.cgtsi.actionform.RPActionForm;
import com.cgtsi.actionform.ReportActionForm;
import com.cgtsi.admin.ExcelCreator;
import com.cgtsi.admin.MenuOptions;
import com.cgtsi.admin.User;
import com.cgtsi.application.LogClass;
import com.cgtsi.claim.ClaimsProcessor;
import com.cgtsi.common.Constants;
import com.cgtsi.common.DatabaseException;
import com.cgtsi.common.Log;
import com.cgtsi.common.MessageException;
import com.cgtsi.common.NoDataException;
import com.cgtsi.investmentfund.IFProcessor;
import com.cgtsi.receiptspayments.AllocationDetail;
import com.cgtsi.receiptspayments.DANSummary;
import com.cgtsi.receiptspayments.DemandAdvice;
import com.cgtsi.receiptspayments.MissingDANDetailsException;
import com.cgtsi.receiptspayments.PaymentDetails;
import com.cgtsi.receiptspayments.RealisationDetail;
import com.cgtsi.receiptspayments.RpDAO;
import com.cgtsi.receiptspayments.RpProcessor;
import com.cgtsi.receiptspayments.ShortExceedsLimitException;
import com.cgtsi.receiptspayments.Voucher;
import com.cgtsi.receiptspayments.VoucherDetail;
import com.cgtsi.registration.CollectingBank;
import com.cgtsi.registration.MLIInfo;
import com.cgtsi.registration.NoMemberFoundException;
import com.cgtsi.registration.Registration;
import com.cgtsi.reports.GeneralReport;
import com.cgtsi.reports.ReportManager;
import com.cgtsi.util.DBConnection;

public class RPAction extends BaseAction {
	private static final String className = "RPAction";
	Registration registration;

	private void $init$() {
		this.registration = new Registration();
	}

	public ActionForward exportExcelNew(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ArrayList abc = new ArrayList();
		abc.add("milind");
		abc.add("joshi");

		HttpSession session = request.getSession();
		RPActionForm actionFormobj = (RPActionForm) form;
		PreparedStatement makePaymentfinalStmt;
		ResultSet makePaymentFinalResult;
		Double amt = 100.0;
		String ifscCode = "CORP0000633";
		String paymentID = request.getParameter("paymentIds");
		String filetype = request.getParameter("fileType");
		System.out.println("filetypes" + filetype);
		Map approveFlags1 = (Map) session.getAttribute("approvedData");
		Map approveFlags = approveFlags1;
		// session.setAttribute("approvedData",null);
		System.out.println("payid " + approveFlags);

		System.out.println("payid value approveFlags " + approveFlags.size());
		Set keys = approveFlags.keySet();
		System.out.println("diksha keys====" + keys);

		User user = getUserInformation(request);
		String userid = user.getUserId();
		String bankid = (user.getBankId()).trim();
		String zoneid = (user.getZoneId()).trim();
		String branchid = (user.getBranchId()).trim();
		String memberId = bankid + zoneid + branchid;

		CallableStatement cStmt = null;
		String errorCode = "";
		ArrayList rpArray = new ArrayList();
		Iterator PaymentIterate = keys.iterator();
		int insdanstatus = 0;
		RPActionForm actionForm = null;

		while (PaymentIterate.hasNext())

		{
			actionForm = new RPActionForm();
			String payids = (String) PaymentIterate.next();

			System.out.println("keys are" + payids);

			String arr[] = payids.split("@");
			System.out.println("Payid=== " + arr[0]);
			actionForm.setPaymentId1(arr[0]);

			actionForm.setAmmount2(Integer.parseInt(arr[1]));
			// actionForm.setAmmount1(amt);
			// System.out.println("Amount=== "+amt);
			actionForm.setVaccno(arr[2]);
			System.out.println("vaccNo=== " + arr[2]);

			actionForm.setIfscCode(ifscCode);
			// actionForm.setPayidcreateddate(new Date().toString());

			rpArray.add(actionForm);
		}

		ExcelCreator excelCreator = new ExcelCreator();
		HSSFWorkbook workbook = excelCreator.createWorkbook(rpArray);
		response.setHeader("Content-Disposition", "attachment; filename=PaymentDetails." + filetype);

		ServletOutputStream out = response.getOutputStream();
		workbook.write(out);
		out.flush();
		out.close();
		return mapping.findForward("success");
	}

	// rajuk

	public ActionForward exportCsvNew(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ArrayList abc = new ArrayList();
		abc.add("milind");
		abc.add("joshi");

		HttpSession session = request.getSession();
		RPActionForm actionFormobj = (RPActionForm) form;
		PreparedStatement makePaymentfinalStmt;
		ResultSet makePaymentFinalResult;
		Double amt = 100.0;
		String ifscCode = "CORP0000633";
		String paymentID = request.getParameter("paymentIds");
		String filetype = request.getParameter("fileType");
		System.out.println("filetypes" + filetype);
		Map approveFlags1 = (Map) session.getAttribute("approvedData");
		Map approveFlags = approveFlags1;
		// session.setAttribute("approvedData",null);
		System.out.println("payid " + approveFlags);

		System.out.println("payid value approveFlags " + approveFlags.size());
		Set keys = approveFlags.keySet();
		System.out.println("konkati" + keys);

		User user = getUserInformation(request);
		String userid = user.getUserId();
		String bankid = (user.getBankId()).trim();
		String zoneid = (user.getZoneId()).trim();
		String branchid = (user.getBranchId()).trim();
		String memberId = bankid + zoneid + branchid;

		CallableStatement cStmt = null;
		String errorCode = "";
		ArrayList rpArray = new ArrayList();
		Iterator PaymentIterate = keys.iterator();
		int insdanstatus = 0;
		RPActionForm actionForm = null;

		while (PaymentIterate.hasNext())

		{
			actionForm = new RPActionForm();
			String payids = (String) PaymentIterate.next();

			System.out.println("keys are" + payids);

			String arr[] = payids.split("@");
			System.out.println("Payid=== " + arr[0]);
			actionForm.setPaymentId1(arr[0]);

			actionForm.setAmmount2(Integer.parseInt(arr[1]));
			// actionForm.setAmmount1(amt);
			// System.out.println("Amount=== "+amt);
			actionForm.setVaccno(arr[2]);
			System.out.println("vaccNo=== " + arr[2]);

			actionForm.setIfscCode(ifscCode);
			// actionForm.setPayidcreateddate(new Date().toString());

			rpArray.add(actionForm);
		}

		ExcelCreator excelCreator = new ExcelCreator();
		HSSFWorkbook workbook = excelCreator.createWorkbook(rpArray);
		response.setHeader("Content-Disposition", "attachment; filename=PaymentDetails." + filetype);

		ServletOutputStream out = response.getOutputStream();
		workbook.write(out);
		out.flush();
		out.close();
		return mapping.findForward("success");
	}

	public ActionForward showPaymentFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "showPaymentFilter", "Entered");
		Log.log(4, "RPAction", "showPaymentFilter", "Exited");

		RPActionForm rpActionForm = (RPActionForm) form;

		rpActionForm.resetWhenRequired();
		rpActionForm.setPaymentId(null);

		User user = getUserInformation(request);
		String bankId = user.getBankId();
		rpActionForm.setBankId(user.getBankId());
		rpActionForm.setZoneId(user.getZoneId());
		rpActionForm.setBranchId(user.getBranchId());
		if (bankId.equals("0000")) {
			user = null;
			rpActionForm.setSelectMember("");
		} else {
			MLIInfo mliInfo = getMemberInfo(request);
			bankId = mliInfo.getBankId();
			String branchId = mliInfo.getBranchId();
			String zoneId = mliInfo.getZoneId();
			String memberId = bankId + zoneId + branchId;
			rpActionForm.setSelectMember(memberId);
		}

		return mapping.findForward("success");
	}

	public ActionForward generateClaimSFDAN(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "generateClaimSFDAN", "Entered");

		RPActionForm rpActionForm = (RPActionForm) form;

		User user = getUserInformation(request);
		String bankId = user.getBankId();
		rpActionForm.setBankId(user.getBankId());
		rpActionForm.setZoneId(user.getZoneId());
		rpActionForm.setBranchId(user.getBranchId());
		if (bankId.equals("0000")) {
			user = null;
			rpActionForm.setSelectMember("");
		} else {
			MLIInfo mliInfo = getMemberInfo(request);
			bankId = mliInfo.getBankId();
			String branchId = mliInfo.getBranchId();
			String zoneId = mliInfo.getZoneId();
			String memberId = bankId + zoneId + branchId;
			rpActionForm.setSelectMember(memberId);
		}

		return mapping.findForward("generateSFDAN");
	}

	public ActionForward generateClaimASFDAN(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "generateClaimASFDAN", "Entered");

		RPActionForm rpActionForm = (RPActionForm) form;
		RpDAO rpDao = new RpDAO();
		User user = getUserInformation(request);
		String cgpan = rpActionForm.getCgpan().toUpperCase();
		int serviceFee = rpActionForm.getDanAmt();
		String remarks = rpActionForm.getApplRemarks().toUpperCase();
		String danType = rpActionForm.getDanType();

		rpDao.generateASFDANforClaimSettled(cgpan, serviceFee, remarks, danType);
		rpActionForm.setCgpan(null);
		rpActionForm.setApplRemarks(null);
		rpActionForm.setDanAmt(0);
		rpActionForm.setDanType(null);
		request.setAttribute("message", "ASF DAN generated for entered CGPAN No - " + cgpan + " Successfully");

		return mapping.findForward("success");
	}

	public ActionForward getPaymentDetailsForPayInSlip(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "getPaymentDetailsForPayInSlip", "Entered");

		String paymentId = request.getParameter("payId");

		RPActionForm rpActionForm = (RPActionForm) form;

		Log.log(5, "RPAction", "getPaymentDetailsForPayInSlip", "paymentId " + paymentId);

		RpProcessor rpProcessor = new RpProcessor();

		PaymentDetails paymentDetails = rpProcessor.displayPayInSlip(paymentId);
		paymentDetails.setPaymentId(paymentId);
		BeanUtils.copyProperties(rpActionForm, paymentDetails);
		rpActionForm.setAccountNumber(paymentDetails.getCgtsiAccNumber());

		String bankName = rpActionForm.getPayInSlipFormat();
		String retPath = "";
		if (bankName.equalsIgnoreCase("IDBI")) {
			retPath = "idbi";
		} else if (bankName.equalsIgnoreCase("PNB")) {
			retPath = "pnb";
		} else if (bankName.equalsIgnoreCase("HDFC")) {
			retPath = "hdfc";
		}

		Log.log(4, "RPAction", "getPaymentDetailsForPayInSlip", "Exited");

		return mapping.findForward(retPath);
	}

	public ActionForward showJournalVoucherDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "showJournalVoucherDetails", "Entered");

		RPActionForm actionForm = (RPActionForm) form;

		actionForm.resetWhenRequired();

		IFProcessor ifProcessor = new IFProcessor();

		ArrayList instruments = ifProcessor.getInstrumentTypes("G");

		actionForm.setInstruments(instruments);

		request.setAttribute("IsRequired", Boolean.valueOf(true));

		Log.log(4, "RPAction", "showJournalVoucherDetails", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward addMoreJournalVoucherDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "addMoreJournalVoucherDetails", "Entered");

		RPActionForm rpForm = (RPActionForm) form;

		Map voucherDetails = rpForm.getVoucherDetails();

		Set voucherDetailsSet = voucherDetails.keySet();

		Iterator voucherDetailsIterator = voucherDetailsSet.iterator();
		String count = null;
		int counter = 0;

		while (voucherDetailsIterator.hasNext()) {
			String key = (String) voucherDetailsIterator.next();

			Log.log(5, "RPAction", "addMoreJournalVoucherDetails", " key " + key);

			count = key.substring(key.indexOf("-") + 1, key.length());

			Log.log(5, "RPAction", "addMoreJournalVoucherDetails", " count " + count);
		}

		Log.log(5, "RPAction", "addMoreJournalVoucherDetails", " counter " + counter);

		request.setAttribute("IsRequired", Boolean.valueOf(true));

		Log.log(4, "RPAction", "addMoreJournalVoucherDetails", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward insertJournalVoucherDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "insertJournalVoucherDetails", "Entered");

		RPActionForm rpForm = (RPActionForm) form;

		Map voucherDetails = rpForm.getVoucherDetails();

		Set voucherSet = voucherDetails.keySet();

		Iterator voucherIterator = voucherSet.iterator();
		ArrayList vouchers = new ArrayList();

		double dbtAmt = 0.0D;
		double cdtAmt = 0.0D;

		while (voucherIterator.hasNext()) {
			String key = (String) voucherIterator.next();

			Log.log(5, "RPAction", "insertJournalVoucherDetails", "key " + key);

			Voucher voucher = (Voucher) voucherDetails.get(key);

			vouchers.add(voucher);

			Log.log(4, "RPAction", "insertJournalVoucherDetails", " Ac code " + voucher.getAcCode());
			Log.log(4, "RPAction", "insertJournalVoucherDetails", " adv date " + voucher.getAdvDate());
			Log.log(5, "RPAction", "insertJournalVoucherDetails", "adv no " + voucher.getAdvNo());
			Log.log(5, "RPAction", "insertJournalVoucherDetails", "amount is rs " + voucher.getAmountInRs());
			Log.log(5, "RPAction", "insertJournalVoucherDetails", " debit or credir " + voucher.getDebitOrCredit());
			Log.log(5, "RPAction", "insertJournalVoucherDetails", "instrument date " + voucher.getInstrumentDate());
			Log.log(5, "RPAction", "insertJournalVoucherDetails", " instrument no " + voucher.getInstrumentNo());

			if (voucher.getDebitOrCredit().equalsIgnoreCase("D")) {
				dbtAmt += Double.parseDouble(voucher.getAmountInRs());
			} else if (voucher.getDebitOrCredit().equalsIgnoreCase("C")) {
				cdtAmt += Double.parseDouble(voucher.getAmountInRs());
				voucher.setAmountInRs("-" + voucher.getAmountInRs());
			}
		}

		VoucherDetail voucherDetail = new VoucherDetail();

		BeanUtils.copyProperties(voucherDetail, rpForm);

		Log.log(5, "RPAction", "insertJournalVoucherDetails", " amount " + voucherDetail.getAmount());
		Log.log(5, "RPAction", "insertJournalVoucherDetails", " figure " + voucherDetail.getAmountInFigure());
		Log.log(5, "RPAction", "insertJournalVoucherDetails", " GL code " + voucherDetail.getBankGLCode());
		Log.log(5, "RPAction", "insertJournalVoucherDetails", " GL name" + voucherDetail.getBankGLName());
		Log.log(5, "RPAction", "insertJournalVoucherDetails", "dept code " + voucherDetail.getDeptCode());
		voucherDetail.setAmount(cdtAmt - dbtAmt);
		Log.log(5, "RPAction", "insertJournalVoucherDetails", " amount " + voucherDetail.getAmount());

		voucherDetail.setVouchers(vouchers);

		RpProcessor rpProcessor = new RpProcessor();
		User user = getUserInformation(request);
		voucherDetail.setVoucherType("JOURNAL VOUCHER");
		String voucherId = rpProcessor.insertVoucherDetails(voucherDetail, user.getUserId());

		String message = "Journal Voucher details stored successfull. Voucher number is " + voucherId;

		request.setAttribute("message", message);

		Log.log(4, "RPAction", "insertJournalVoucherDetails", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward showPaymentVoucherDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "showPaymentVoucherDetails", "Entered");

		RPActionForm actionForm = (RPActionForm) form;

		actionForm.resetWhenRequired();

		IFProcessor ifProcessor = new IFProcessor();

		ArrayList instruments = ifProcessor.getInstrumentTypes("G");

		RpProcessor rpProcessor = new RpProcessor();
		ArrayList glHeads = rpProcessor.getGLHeads();

		actionForm.setGlHeads(glHeads);

		actionForm.setInstruments(instruments);

		request.setAttribute("IsRequired", Boolean.valueOf(true));

		HttpSession session = request.getSession(false);
		session.setAttribute("VOUCHER_FLAG", "1");

		Log.log(4, "RPAction", "showPaymentVoucherDetails", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward insertPaymentVoucherDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "insertPaymentVoucherDetails", "Entered");

		RPActionForm rpForm = (RPActionForm) form;

		Map voucherDetails = rpForm.getVoucherDetails();

		Set voucherSet = voucherDetails.keySet();

		Iterator voucherIterator = voucherSet.iterator();
		ArrayList vouchers = new ArrayList();

		double dbtAmt = 0.0D;
		double cdtAmt = 0.0D;

		while (voucherIterator.hasNext()) {
			String key = (String) voucherIterator.next();

			Log.log(5, "RPAction", "insertPaymentVoucherDetails", "key " + key);

			Voucher voucher = (Voucher) voucherDetails.get(key);

			vouchers.add(voucher);

			Log.log(5, "RPAction", "insertPaymentVoucherDetails", " Ac code " + voucher.getAcCode());
			Log.log(5, "RPAction", "insertPaymentVoucherDetails", " adv date " + voucher.getAdvDate());
			Log.log(5, "RPAction", "insertPaymentVoucherDetails", "adv no " + voucher.getAdvNo());
			Log.log(5, "RPAction", "insertPaymentVoucherDetails", "amount is rs " + voucher.getAmountInRs());
			Log.log(5, "RPAction", "insertPaymentVoucherDetails", " debit or credir " + voucher.getDebitOrCredit());
			Log.log(5, "RPAction", "insertPaymentVoucherDetails", "instrument date " + voucher.getInstrumentDate());
			Log.log(5, "RPAction", "insertPaymentVoucherDetails", " instrument no " + voucher.getInstrumentNo());

			if (voucher.getDebitOrCredit().equalsIgnoreCase("D")) {
				dbtAmt += Double.parseDouble(voucher.getAmountInRs());
			} else if (voucher.getDebitOrCredit().equalsIgnoreCase("C")) {
				cdtAmt += Double.parseDouble(voucher.getAmountInRs());
				if (cdtAmt > 0.0D) {
					voucher.setAmountInRs("-" + voucher.getAmountInRs());
				}
			}
		}

		VoucherDetail voucherDetail = new VoucherDetail();

		BeanUtils.copyProperties(voucherDetail, rpForm);

		Log.log(5, "RPAction", "insertPaymentVoucherDetails", " amount " + rpForm.getAmount());
		Log.log(5, "RPAction", "insertPaymentVoucherDetails", " amount " + voucherDetail.getAmount());
		Log.log(5, "RPAction", "insertPaymentVoucherDetails", " figure " + voucherDetail.getAmountInFigure());
		Log.log(5, "RPAction", "insertPaymentVoucherDetails", " GL code " + voucherDetail.getBankGLCode());
		Log.log(5, "RPAction", "insertPaymentVoucherDetails", " GL name" + voucherDetail.getBankGLName());
		Log.log(5, "RPAction", "insertPaymentVoucherDetails", "dept code " + voucherDetail.getDeptCode());
		voucherDetail.setAmount(cdtAmt - dbtAmt);
		Log.log(5, "RPAction", "insertPaymentVoucherDetails", " amount " + voucherDetail.getAmount());

		voucherDetail.setVouchers(vouchers);

		RpProcessor rpProcessor = new RpProcessor();
		User user = getUserInformation(request);
		voucherDetail.setVoucherType("PAYMENT VOUCHER");
		String voucherId = rpProcessor.insertVoucherDetails(voucherDetail, user.getUserId());

		String message = "Voucher details stored successfull. Voucher number is " + voucherId;

		request.setAttribute("message", message);

		Log.log(4, "RPAction", "insertPaymentVoucherDetails", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward addMorePaymentVoucherDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "addMorePaymentVoucherDetails", "Entered");

		RPActionForm rpForm = (RPActionForm) form;

		Map voucherDetails = rpForm.getVoucherDetails();

		Set voucherDetailsSet = voucherDetails.keySet();

		Iterator voucherDetailsIterator = voucherDetailsSet.iterator();
		String count = null;
		int counter = 0;

		while (voucherDetailsIterator.hasNext()) {
			String key = (String) voucherDetailsIterator.next();

			Log.log(5, "RPAction", "addMorePaymentVoucherDetails", " key " + key);

			count = key.substring(key.indexOf("-") + 1, key.length());

			Log.log(5, "RPAction", "addMorePaymentVoucherDetails", " count " + count);
		}

		Log.log(5, "RPAction", "addMorePaymentVoucherDetails", " counter " + counter);

		request.setAttribute("IsRequired", Boolean.valueOf(true));

		Log.log(4, "RPAction", "addMorePaymentVoucherDetails", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward showReceiptVoucherDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "showReceiptVoucherDetails", "Entered");

		RPActionForm actionForm = (RPActionForm) form;

		actionForm.resetWhenRequired();

		IFProcessor ifProcessor = new IFProcessor();

		ArrayList instruments = ifProcessor.getInstrumentTypes("G");

		actionForm.setInstruments(instruments);

		RpProcessor rpProcessor = new RpProcessor();
		ArrayList glHeads = rpProcessor.getGLHeads();

		actionForm.setGlHeads(glHeads);

		HttpSession session = request.getSession(false);
		session.setAttribute("VOUCHER_FLAG", "1");

		request.setAttribute("IsRequired", Boolean.valueOf(true));

		Log.log(4, "RPAction", "showReceiptVoucherDetails", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward insertReceiptVoucherDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "insertReceiptVoucherDetails", "Entered");

		RPActionForm rpForm = (RPActionForm) form;

		Map voucherDetails = rpForm.getVoucherDetails();

		Set voucherSet = voucherDetails.keySet();

		Iterator voucherIterator = voucherSet.iterator();
		ArrayList vouchers = new ArrayList();

		double dbtAmt = 0.0D;
		double cdtAmt = 0.0D;

		while (voucherIterator.hasNext()) {
			String key = (String) voucherIterator.next();

			Log.log(5, "RPAction", "insertReceiptVoucherDetails", "key " + key);

			Voucher voucher = (Voucher) voucherDetails.get(key);

			vouchers.add(voucher);

			Log.log(5, "RPAction", "insertReceiptVoucherDetails", " Ac code " + voucher.getAcCode());
			Log.log(5, "RPAction", "insertReceiptVoucherDetails", " adv date " + voucher.getAdvDate());
			Log.log(5, "RPAction", "insertReceiptVoucherDetails", "adv no " + voucher.getAdvNo());
			Log.log(5, "RPAction", "insertReceiptVoucherDetails", "amount is rs " + voucher.getAmountInRs());
			Log.log(5, "RPAction", "insertReceiptVoucherDetails", " debit or credir " + voucher.getDebitOrCredit());
			Log.log(5, "RPAction", "insertReceiptVoucherDetails", "instrument date " + voucher.getInstrumentDate());
			Log.log(5, "RPAction", "insertReceiptVoucherDetails", " instrument no " + voucher.getInstrumentNo());

			if (voucher.getDebitOrCredit().equalsIgnoreCase("D")) {
				dbtAmt += Double.parseDouble(voucher.getAmountInRs());
			} else if (voucher.getDebitOrCredit().equalsIgnoreCase("C")) {
				cdtAmt += Double.parseDouble(voucher.getAmountInRs());
				voucher.setAmountInRs("-" + voucher.getAmountInRs());
			}
		}

		VoucherDetail voucherDetail = new VoucherDetail();

		BeanUtils.copyProperties(voucherDetail, rpForm);

		Log.log(5, "RPAction", "insertReceiptVoucherDetails", " amount " + voucherDetail.getAmount());
		Log.log(5, "RPAction", "insertReceiptVoucherDetails", " figure " + voucherDetail.getAmountInFigure());
		Log.log(5, "RPAction", "insertReceiptVoucherDetails", " GL code " + voucherDetail.getBankGLCode());
		Log.log(5, "RPAction", "insertReceiptVoucherDetails", " GL name" + voucherDetail.getBankGLName());
		Log.log(5, "RPAction", "insertReceiptVoucherDetails", "dept code " + voucherDetail.getDeptCode());
		voucherDetail.setAmount(dbtAmt - cdtAmt);
		Log.log(5, "RPAction", "insertReceiptVoucherDetails", " amount " + voucherDetail.getAmount());

		voucherDetail.setVouchers(vouchers);

		RpProcessor rpProcessor = new RpProcessor();
		User user = getUserInformation(request);
		voucherDetail.setVoucherType("RECEIPT VOUCHER");

		String voucherId = rpProcessor.insertVoucherDetails(voucherDetail, user.getUserId());

		String message = "Voucher details stored successfull. Voucher number is " + voucherId;

		request.setAttribute("message", message);

		Log.log(4, "RPAction", "insertReceiptVoucherDetails", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward addMoreReceiptVoucherDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "addMoreReceiptVoucherDetails", "Entered");

		RPActionForm rpForm = (RPActionForm) form;

		Map voucherDetails = rpForm.getVoucherDetails();

		Set voucherDetailsSet = voucherDetails.keySet();

		Iterator voucherDetailsIterator = voucherDetailsSet.iterator();
		String count = null;
		int counter = 0;

		while (voucherDetailsIterator.hasNext()) {
			String key = (String) voucherDetailsIterator.next();

			Log.log(5, "RPAction", "addMoreReceiptVoucherDetails", " key " + key);

			count = key.substring(key.indexOf("-") + 1, key.length());

			Log.log(5, "RPAction", "addMoreReceiptVoucherDetails", " count " + count);
		}

		Log.log(5, "RPAction", "addMoreReceiptVoucherDetails", " counter " + counter);

		request.setAttribute("IsRequired", Boolean.valueOf(true));

		Log.log(4, "RPAction", "addMoreReceiptVoucherDetails", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward getPendingDANsFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User user = getUserInformation(request);
		String bankId = user.getBankId();

		RPActionForm rpActionForm = (RPActionForm) form;

		HttpSession session = request.getSession(false);
		rpActionForm.setAllocationType("F");
		if (bankId.equalsIgnoreCase("0000")) {
			rpActionForm.setSelectMember("");

			session.setAttribute("TARGET_URL", "selectMember1.do?method=getPendingDANs");

			return mapping.findForward("memberInfo");
		}

		request.setAttribute("pageValue", "1");

		getPendingDANs(mapping, form, request, response);

		return mapping.findForward("danSummary");
	}

	public ActionForward getPendingASFDANsFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User user = getUserInformation(request);
		String bankId = user.getBankId();

		RPActionForm rpActionForm = (RPActionForm) form;

		HttpSession session = request.getSession(false);

		rpActionForm.setAllocationType("A");

		if (bankId.equalsIgnoreCase("0000")) {
			rpActionForm.setSelectMember("");

			session.setAttribute("TARGET_URL", "selectASFMember.do?method=getPendingASFDANs");

			return mapping.findForward("memberInfo");
		}

		request.setAttribute("message",
				"<b> In terms of Circular No.59/2009-10 dated March 11,2010, it is mandatory for all MLIs <br>  to make ASF 2011 payment through a single payment from the Head Office.");

		return mapping.findForward("success");
	}

	public ActionForward getPendingExpiredASFDANsFilter(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = getUserInformation(request);
		String bankId = user.getBankId();

		RPActionForm rpActionForm = (RPActionForm) form;

		HttpSession session = request.getSession(false);

		if (bankId.equalsIgnoreCase("0000")) {
			rpActionForm.setSelectMember("");

			session.setAttribute("TARGET_URL", "selectASFMemberForExpired.do?method=getPendingExpiredASFDANs");

			return mapping.findForward("memberInfo");
		}

		request.setAttribute("message",
				"<b> In terms of Circular No.59/2009-10 dated March 11,2010, it is mandatory for all MLIs <br>  to make ASF 2011 payment through a single payment from the Head Office.");

		return mapping.findForward("success");
	}

	public ActionForward getPendingGFDANsFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User user = getUserInformation(request);
		String bankId = user.getBankId();

		RPActionForm rpActionForm = (RPActionForm) form;

		rpActionForm.setAllocationType("G");

		HttpSession session = request.getSession(false);

		if (bankId.equalsIgnoreCase("0000")) {
			rpActionForm.setSelectMember("");

			session.setAttribute("TARGET_URL", "selectGFMember.do?method=getPendingGFDANs");

			return mapping.findForward("memberInfo");
		}

		request.setAttribute("pageValue", "1");

		getPendingGFDANs(mapping, form, request, response);

		return mapping.findForward("danSummary");
	}

	public ActionForward getPendingGFDANsFilterNew(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User user = getUserInformation(request);
		String bankId = user.getBankId();

		RPActionForm rpActionForm = (RPActionForm) form;

		rpActionForm.setAllocationType("G");

		HttpSession session = request.getSession(false);

		if (bankId.equalsIgnoreCase("0000")) {
			rpActionForm.setSelectMember("");

			session.setAttribute("TARGET_URL", "selectGFMemberNew.do?method=getNEFTPendingGFDANs");

			return mapping.findForward("memberInfoNew");
		}

		request.setAttribute("pageValue", "1");
		getNEFTPendingGFDANs(mapping, form, request, response);

		return mapping.findForward("neftdanSummary");
	}

	public ActionForward getPendingTextileGFDANsFilter(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = getUserInformation(request);
		String bankId = user.getBankId();

		RPActionForm rpActionForm = (RPActionForm) form;

		HttpSession session = request.getSession(false);

		rpActionForm.setAllocationType("H");

		request.setAttribute("pageValue", "1");

		getPendingTextileGFDANs(mapping, form, request, response);

		return mapping.findForward("danSummary4");
	}

	public ActionForward getPendingTextileGFDANs(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "getPendingTextileGFDANs", "Entered");

		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = "";

		RPActionForm actionForm = (RPActionForm) form;

		HttpSession session = request.getSession(false);

		if (actionForm.getDanSummaries() != null) {
			actionForm.getDanSummaries().clear();
		}
		if (actionForm.getDanPanDetails() != null) {
			actionForm.getDanPanDetails().clear();
		}

		if (actionForm.getCgpans() != null) {
			actionForm.getCgpans().clear();
		}

		if (actionForm.getAllocatedFlags() != null) {
			actionForm.getAllocatedFlags().clear();
		}

		if (actionForm.getFirstDisbursementDates() != null) {
			actionForm.getFirstDisbursementDates().clear();
		}

		if (actionForm.getNotAllocatedReasons() != null) {
			actionForm.getNotAllocatedReasons().clear();
		}

		Log.log(5, "RPAction", "getPendingTextileGFDANs", "Bank Id : " + bankId);
		Log.log(5, "RPAction", "getPendingTextileGFDANs", "Zone Id : " + zoneId);
		Log.log(5, "RPAction", "getPendingTextileGFDANs", "Branch Id : " + branchId);

		bankId = "0019";
		zoneId = "0001";
		Log.log(5, "RPAction", "getPendingGFDANs",
				"Selected Bank Id,zone and branch ids : " + bankId + "," + zoneId + "," + branchId);

		RpProcessor rpProcessor = new RpProcessor();
		ArrayList danSummaries = rpProcessor.displayTextileGFDANs();

		Log.log(5, "RPAction", "getPendingGFDANs", "dan summary size : " + danSummaries.size());

		if (danSummaries.size() == 0) {
			actionForm.setSelectMember(null);

			throw new MissingDANDetailsException("No DANs available for Allocation");
		}

		boolean isDanAvailable = false;
		for (int i = 0; i < danSummaries.size(); i++) {
			DANSummary danSummary = (DANSummary) danSummaries.get(i);
			if (danSummary.getAmountDue() != danSummary.getAmountPaid()) {
				isDanAvailable = true;
				break;
			}
		}
		if (!isDanAvailable) {
			actionForm.setSelectMember(null);

			throw new MissingDANDetailsException("No DANs available for Allocation");
		}
		actionForm.setDanSummaries(danSummaries);

		actionForm.setBankId(bankId);
		actionForm.setZoneId(zoneId);
		actionForm.setBranchId(branchId);

		Log.log(4, "RPAction", "getPendingTextileGFDANs", "Exited");
		if (actionForm.getSelectMember() != null) {
			actionForm.setMemberId(actionForm.getSelectMember());
		} else {
			actionForm.setMemberId(bankId + zoneId + branchId);
		}

		actionForm.setSelectMember(null);

		return mapping.findForward("danSummary4");
	}

	public ActionForward submitTextileGFDANPayments(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RPActionForm actionForm = (RPActionForm) form;
		double totalAmount = 0.0D;

		Map danIds = actionForm.getDanIds();
		Map allocatedFlags = actionForm.getAllocatedFlags();

		Map cgpans = actionForm.getCgpans();

		int allocatedcount = 0;
		int testallocatecount = 0;

		Set danIdSet = danIds.keySet();

		Log.log(5, "RPAction", "submitTextileGFDANPayments", "Checkbox size = " + allocatedFlags.size());

		Set cgpansSet = cgpans.keySet();
		Iterator cgpanIterator = cgpansSet.iterator();

		Log.log(5, "RPAction", "submitTextileGFDANPayments", "Checkbox size = " + cgpans.size());
		while (cgpanIterator.hasNext()) {
			String key = (String) cgpanIterator.next();
			String value = (String) cgpans.get(key);

			Log.log(5, "RPAction", "submitTextileGFDANPayments", "cgpan key = " + key);
			Log.log(5, "RPAction", "submitTextileGFDANPayments", "cgpan value = " + value);
		}
		cgpanIterator = cgpansSet.iterator();
		ArrayList danSummaries = actionForm.getDanSummaries();

		boolean isAllocated = false;

		for (int i = 0; i < danSummaries.size(); i++) {
			DANSummary danSummary = (DANSummary) danSummaries.get(i);
			String danId = danSummary.getDanId();

			Log.log(5, "RPAction", "submitTextileGFDANPayments", "danId= " + danId);
			String danIdKey = danId.replace('.', '_');

			if ((allocatedFlags.containsKey(danIdKey))
					&& (request.getParameter("allocatedFlag(" + danIdKey + ")") != null)) {
				allocatedcount += 1;

				Log.log(5, "RPAction", "submitTextileGFDANPayments", "danSummaries= " + danSummaries.size());
				isAllocated = true;

				totalAmount += danSummary.getAmountDue() - danSummary.getAmountPaid();

				Log.log(5, "RPAction", "submitTextileGFDANPayments",
						"due amount " + (danSummary.getAmountDue() - danSummary.getAmountPaid()));
			} else {
				Log.log(5, "RPAction", "submitTextileGFDANPayments", "CGPANS are allocated ");

				ArrayList panDetails = (ArrayList) actionForm.getDanPanDetail(danId);

				while (cgpanIterator.hasNext()) {
					String key = (String) cgpanIterator.next();
					String value = (String) cgpans.get(key);

					String cgpanPart = value.substring(value.indexOf("-") + 1, value.length());
					String tempKey = value.replace('.', '_');

					Log.log(5, "RPAction", "submitTextileGFDANPayments", "key " + key);
					Log.log(5, "RPAction", "submitTextileGFDANPayments", "value " + value);
					Log.log(5, "RPAction", "submitTextileGFDANPayments", "tempKey " + tempKey);

					if ((value.startsWith(danId)) && (allocatedFlags.get(tempKey) != null)
							&& (((String) allocatedFlags.get(tempKey)).equals("Y"))) {
						testallocatecount += 1;
						cgpanPart = value.substring(value.indexOf("-") + 1, value.length());
						isAllocated = true;
						for (int j = 0; j < panDetails.size(); j++) {
							AllocationDetail allocation = (AllocationDetail) panDetails.get(j);
							Log.log(5, "RPAction", "submitTextileGFDANPayments",
									"amount for CGPAN " + allocation.getCgpan() + "," + allocation.getAmountDue());

							if (cgpanPart.equals(allocation.getCgpan())) {
								totalAmount += allocation.getAmountDue();
								break;
							}
						}
					}
				}
				cgpanIterator = cgpansSet.iterator();
			}

		}

		if (!isAllocated) {
			throw new MissingDANDetailsException("No Allocation made.");
		}

		Registration registration = new Registration();

		Log.log(5, "RPAction", "submitTextileGFDANPayments", "member id " + actionForm.getMemberId());

		CollectingBank collectingBank = registration.getCollectingBank("(" + actionForm.getMemberId() + ")");

		Log.log(5, "RPAction", "submitTextileGFDANPayments", "collectingBank " + collectingBank);

		actionForm.setModeOfPayment("");
		actionForm.setPaymentDate(null);
		actionForm.setInstrumentNo("");
		actionForm.setInstrumentDate(null);
		actionForm.setDrawnAtBank("");
		actionForm.setDrawnAtBranch("");
		actionForm.setPayableAt("");

		IFProcessor ifProcessor = new IFProcessor();

		ArrayList instruments = ifProcessor.getInstrumentTypes("G");

		actionForm.setInstruments(instruments);
		RpDAO rpDAO = new RpDAO();
		actionForm.setInstrumentNo(rpDAO.getInstrumentSeq());

		actionForm.setCollectingBank(collectingBank.getCollectingBankId());
		actionForm.setCollectingBankName(collectingBank.getCollectingBankName());
		actionForm.setCollectingBankBranch(collectingBank.getBranchName());
		actionForm.setAccountNumber(collectingBank.getAccNo());

		actionForm.setInstrumentAmount(totalAmount);

		return mapping.findForward("gfpaymentDetails");
	}

	public ActionForward getTextileGFPANDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "getGFPANDetails", "Entered");
		RPActionForm actionForm = (RPActionForm) form;

		Map cgpans = actionForm.getCgpans();

		Set cgpanSet = cgpans.keySet();

		Iterator cgpanIterator = cgpanSet.iterator();

		Log.log(5, "RPAction", "getGFPANDetails", "CGPANS selected ");

		while (cgpanIterator.hasNext()) {
			String key = (String) cgpanIterator.next();
			Log.log(5, "RPAction", "getGFPANDetails", "key,value " + key + "," + cgpans.get(key));
		}

		Log.log(5, "RPAction", "getGFPANDetails", "Cgpan map size " + cgpans.size());

		String danNo = actionForm.getDanNo();
		Log.log(4, "RPAction", "getGFPANDetails", "On entering, DAN no: " + danNo);

		Log.log(4, "RPAction", "getGFPANDetails", "No Session: DAN no : " + danNo);
		RpProcessor rpProcessor = new RpProcessor();

		ArrayList returnList = rpProcessor.displayCGPANs(danNo);
		ArrayList panDetails = (ArrayList) returnList.get(0);
		ArrayList allocatedPanDetails = (ArrayList) returnList.get(1);
		Log.log(4, "RPAction", "getGFPANDetails", "No Session: No. of PAN details : " + panDetails.size());

		String allocatedFlag = (String) actionForm.getAllocatedFlag(danNo.replace('.', '_'));
		Log.log(4, "RPAction", "getGFPANDetails", "flag " + allocatedFlag);
		Map allocatedFlags = actionForm.getAllocatedFlags();
		if ((allocatedFlag != null) && (allocatedFlag.equalsIgnoreCase(danNo))) {
			for (int i = 0; i < panDetails.size(); i++) {
				AllocationDetail allocationDetail = (AllocationDetail) panDetails.get(i);
				String key = danNo.replace('.', '_') + "-" + allocationDetail.getCgpan();
				allocatedFlags.put(key, "Y");
			}
		}
		actionForm.setAllocatedFlags(allocatedFlags);

		Log.log(4, "RPAction", "getGFPANDetails", "After session validation : " + panDetails.size());
		actionForm.setDanPanDetail(danNo, panDetails);
		actionForm.setPanDetails(panDetails);
		actionForm.setAllocatedPanDetails(allocatedPanDetails);

		return mapping.findForward("gfpanDetails");
	}

	public ActionForward gfallocatePaymentsforTextile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		RPActionForm actionForm = (RPActionForm) form;
		RpProcessor rpProcessor = new RpProcessor();
		RpDAO rpDAO = new RpDAO();
		String paymentId = "";
		String methodName = "gfallocatePaymentsforTextile";

		Log.log(5, "RPAction", methodName, "Entered");

		User user = getUserInformation(request);

		PaymentDetails paymentDetails = new PaymentDetails();

		String allocationType4 = actionForm.getAllocationType();

		paymentDetails.setAllocationType1(allocationType4);

		String modeOfPayment = actionForm.getModeOfPayment();

		double tempamounttobeallocated = rpDAO.getBalancePaymentFromOtherFacility(modeOfPayment);

		String collectingBranch = actionForm.getCollectingBankBranch();
		java.util.Date paymentDate = actionForm.getPaymentDate();
		String instrumentNumber = actionForm.getInstrumentNo();
		java.util.Date instrumentDate = actionForm.getInstrumentDate();
		String modeOfDelivery = actionForm.getModeOfDelivery();
		double instrumentAmount = actionForm.getInstrumentAmount();

		String drawnAtBank = actionForm.getDrawnAtBank();
		String drawnAtBranch = actionForm.getDrawnAtBranch();
		String payableAt = actionForm.getPayableAt();
		String accNumber = actionForm.getAccountNumber();

		Log.log(5, "RPAction", methodName, "collecting bank " + actionForm.getCollectingBankName());
		paymentDetails.setCollectingBank(actionForm.getCollectingBankName());
		Log.log(5, "RPAction", methodName, "mode of payment " + modeOfPayment);
		paymentDetails.setModeOfPayment(modeOfPayment);
		Log.log(5, "RPAction", methodName, "collecting branch " + collectingBranch);
		paymentDetails.setCollectingBankBranch(collectingBranch);
		Log.log(5, "RPAction", methodName, "payment date " + paymentDate);
		paymentDetails.setPaymentDate(paymentDate);
		Log.log(5, "RPAction", methodName, "instrument number " + instrumentNumber);
		paymentDetails.setInstrumentNo(instrumentNumber);
		Log.log(5, "RPAction", methodName, "instrument date " + instrumentDate);
		paymentDetails.setInstrumentDate(instrumentDate);
		Log.log(5, "RPAction", methodName, "mode of delivery " + modeOfDelivery);
		paymentDetails.setModeOfDelivery(modeOfDelivery);
		Log.log(5, "RPAction", methodName, "instrument amount " + instrumentAmount);
		paymentDetails.setInstrumentAmount(instrumentAmount);
		Log.log(5, "RPAction", methodName, "drawn at bank " + drawnAtBank);
		paymentDetails.setDrawnAtBank(drawnAtBank);
		Log.log(5, "RPAction", methodName, "drawn at branch " + drawnAtBranch);
		paymentDetails.setDrawnAtBranch(drawnAtBranch);
		Log.log(5, "RPAction", methodName, "payable at " + payableAt);
		paymentDetails.setPayableAt(payableAt);
		Log.log(5, "RPAction", methodName, "acc num " + accNumber);
		paymentDetails.setCgtsiAccNumber(accNumber);

		if (tempamounttobeallocated - instrumentAmount < 0.0D) {
			throw new ShortExceedsLimitException(
					"Sufficient fund not available. Short by - " + (tempamounttobeallocated - instrumentAmount));
		}
		if (tempamounttobeallocated - instrumentAmount >= 0.0D) {
			rpDAO.updateTempUtilForOtherFacility(modeOfPayment, instrumentAmount);
		}

		Map allocationFlags = actionForm.getAllocatedFlags();
		ArrayList danSummaries = actionForm.getDanSummaries();

		Map cgpans = actionForm.getCgpans();

		Set cgpansSet = cgpans.keySet();

		Map danCgpanDetails = actionForm.getDanPanDetails();

		Map notAllocatedReasons = actionForm.getNotAllocatedReasons();

		for (int i = 0; i < danSummaries.size(); i++) {
			DANSummary danSummary = (DANSummary) danSummaries.get(i);

			String danId = danSummary.getDanId();
			Log.log(5, "RPAction", methodName, "danId " + danId);
			String shiftDanId = danId.replace('.', '_');

			Log.log(5, "RPAction", methodName, "contains " + danCgpanDetails.containsKey(danId));
			if (danCgpanDetails.containsKey(danId)) {
				ArrayList panAllocationDetails = (ArrayList) danCgpanDetails.get(danId);

				if (panAllocationDetails == null) {
					Log.log(5, "RPAction", methodName, "CGPAN details are not available. get them.");
					ArrayList totalList = rpProcessor.displayCGPANs(danId);
					panAllocationDetails = (ArrayList) totalList.get(0);
				}
				for (int j = 0; j < panAllocationDetails.size(); j++) {
					AllocationDetail allocationDetail = (AllocationDetail) panAllocationDetails.get(j);
					Log.log(5, "RPActionForm", "validate", " cgpan from danpandetails " + allocationDetail.getCgpan());
					if (allocationDetail.getAllocatedFlag().equals("N")) {
						Log.log(5, "RPActionForm", "validate", " not allocated ");
						String reasons = (String) notAllocatedReasons
								.get(shiftDanId + "-" + allocationDetail.getCgpan());
						Log.log(5, "RPActionForm", "validate", " reason for not allocated " + reasons);
						allocationDetail.setNotAllocatedReason(reasons);
					} else {
						allocationDetail.setNotAllocatedReason("");
					}
					panAllocationDetails.set(j, allocationDetail);
				}
				danCgpanDetails.put(danId, panAllocationDetails);
			}

		}

		request.setAttribute("message", "Payment Allocated Successfully.<BR>Payment ID : " + paymentId);

		Log.log(5, "RPAction", methodName, "Exited");

		return mapping.findForward("success");
	}

	public ActionForward getPendingSFDANsFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User user = getUserInformation(request);
		String bankId = user.getBankId();

		RPActionForm rpActionForm = (RPActionForm) form;

		HttpSession session = request.getSession(false);

		if (bankId.equalsIgnoreCase("0000")) {
			rpActionForm.setSelectMember("");

			session.setAttribute("TARGET_URL", "selectSFMember.do?method=getPendingSFDANs");

			return mapping.findForward("memberInfo");
		}

		request.setAttribute("pageValue", "1");

		getPendingDANs(mapping, form, request, response);

		return mapping.findForward("danSummary");
	}

	public ActionForward getPendingDANs(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "getPendingDANs", "Entered");

		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = "";

		RPActionForm actionForm = (RPActionForm) form;

		HttpSession session = request.getSession(false);

		if (actionForm.getDanSummaries() != null) {
			actionForm.getDanSummaries().clear();
		}
		if (actionForm.getDanPanDetails() != null) {
			actionForm.getDanPanDetails().clear();
		}

		if (actionForm.getCgpans() != null) {
			actionForm.getCgpans().clear();
		}

		if (actionForm.getAllocatedFlags() != null) {
			actionForm.getAllocatedFlags().clear();
		}

		if (actionForm.getFirstDisbursementDates() != null) {
			actionForm.getFirstDisbursementDates().clear();
		}

		if (actionForm.getNotAllocatedReasons() != null) {
			actionForm.getNotAllocatedReasons().clear();
		}

		Log.log(5, "RPAction", "getPendingDANs", "Bank Id : " + bankId);
		Log.log(5, "RPAction", "getPendingDANs", "Zone Id : " + zoneId);
		Log.log(5, "RPAction", "getPendingDANs", "Branch Id : " + branchId);

		if (bankId.equals("0000")) {
			memberId = actionForm.getSelectMember();

			if ((memberId == null) || (memberId.equals(""))) {
				memberId = actionForm.getMemberId();
			}

			Log.log(5, "RPAction", "getPendingDANs", "mliId = " + memberId);

			if ((memberId == null) || (memberId.equals(""))) {
				session.setAttribute("TARGET_URL", "selectMember1.do?method=getPendingDANs");

				return mapping.findForward("memberInfo");
			}

			bankId = memberId.substring(0, 4);
			zoneId = memberId.substring(4, 8);
			branchId = memberId.substring(8, 12);

			ClaimsProcessor cpProcessor = new ClaimsProcessor();
			Vector memberIds = cpProcessor.getAllMemberIds();
			if (!memberIds.contains(memberId)) {
				throw new NoMemberFoundException("The Member ID does not exist");
			}

		}

		Log.log(5, "RPAction", "getPendingDANs",
				"Selected Bank Id,zone and branch ids : " + bankId + "," + zoneId + "," + branchId);

		RpProcessor rpProcessor = new RpProcessor();
		ArrayList danSummaries = rpProcessor.displayDANs(bankId, zoneId, branchId);

		Log.log(5, "RPAction", "getPendingDANs", "dan summary size : " + danSummaries.size());

		if (danSummaries.size() == 0) {
			actionForm.setSelectMember(null);

			throw new MissingDANDetailsException("No DANs available for Allocation");
		}

		boolean isDanAvailable = false;
		for (int i = 0; i < danSummaries.size(); i++) {
			DANSummary danSummary = (DANSummary) danSummaries.get(i);
			if (danSummary.getAmountDue() != danSummary.getAmountPaid()) {
				isDanAvailable = true;
				break;
			}
		}
		if (!isDanAvailable) {
			actionForm.setSelectMember(null);

			throw new MissingDANDetailsException("No DANs available for Allocation");
		}
		actionForm.setDanSummaries(danSummaries);

		actionForm.setBankId(bankId);
		actionForm.setZoneId(zoneId);
		actionForm.setBranchId(branchId);

		Log.log(4, "RPAction", "getPendingDANs", "Exited");
		if (actionForm.getSelectMember() != null) {
			actionForm.setMemberId(actionForm.getSelectMember());
		} else {
			actionForm.setMemberId(bankId + zoneId + branchId);
		}

		actionForm.setSelectMember(null);

		return mapping.findForward("danSummary");
	}

	public ActionForward getPendingASFDANs(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "getPendingASFDANs", "Entered");

		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = "";

		RPActionForm actionForm = (RPActionForm) form;

		HttpSession session = request.getSession(false);

		if (actionForm.getDanSummaries() != null) {
			actionForm.getDanSummaries().clear();
		}
		if (actionForm.getDanPanDetails() != null) {
			actionForm.getDanPanDetails().clear();
		}

		if (actionForm.getCgpans() != null) {
			actionForm.getCgpans().clear();
		}

		if (actionForm.getAllocatedFlags() != null) {
			actionForm.getAllocatedFlags().clear();
		}

		if (actionForm.getFirstDisbursementDates() != null) {
			actionForm.getFirstDisbursementDates().clear();
		}

		if (actionForm.getNotAllocatedReasons() != null) {
			actionForm.getNotAllocatedReasons().clear();
		}

		Log.log(5, "RPAction", "getPendingASFDANs", "Bank Id : " + bankId);
		Log.log(5, "RPAction", "getPendingASFDANs", "Zone Id : " + zoneId);
		Log.log(5, "RPAction", "getPendingASFDANs", "Branch Id : " + branchId);

		if (bankId.equals("0000")) {
			memberId = actionForm.getSelectMember();

			if ((memberId == null) || (memberId.equals(""))) {
				memberId = actionForm.getMemberId();
			}

			Log.log(5, "RPAction", "getPendingASFDANs", "mliId = " + memberId);

			if ((memberId == null) || (memberId.equals(""))) {
				Log.log(5, "RPAction", "getPendingASFDANs",
						"Menu Target = " + MenuOptions.getMenuAction("RP_ALLOCATE_PAYMENTS"));

				session.setAttribute("TARGET_URL", "selectASFMember.do?method=getPendingASFDANs");

				return mapping.findForward("memberInfo");
			}

			bankId = memberId.substring(0, 4);
			zoneId = memberId.substring(4, 8);
			branchId = memberId.substring(8, 12);

			ClaimsProcessor cpProcessor = new ClaimsProcessor();
			Vector memberIds = cpProcessor.getAllMemberIds();
			if (!memberIds.contains(memberId)) {
				throw new NoMemberFoundException("The Member ID does not exist");
			}

		}

		Log.log(5, "RPAction", "getPendingASFDANs",
				"Selected Bank Id,zone and branch ids : " + bankId + "," + zoneId + "," + branchId);

		RpProcessor rpProcessor = new RpProcessor();
		ArrayList danSummaries = rpProcessor.displayASFDANs(bankId, zoneId, branchId);

		Log.log(5, "RPAction", "getPendingASFDANs", "dan summary size : " + danSummaries.size());

		if (danSummaries.size() == 0) {
			actionForm.setSelectMember(null);

			throw new MissingDANDetailsException("No DANs available for Allocation");
		}

		boolean isDanAvailable = false;
		for (int i = 0; i < danSummaries.size(); i++) {
			DANSummary danSummary = (DANSummary) danSummaries.get(i);
			if (danSummary.getAmountDue() != danSummary.getAmountPaid()) {
				isDanAvailable = true;
				break;
			}
		}
		if (!isDanAvailable) {
			actionForm.setSelectMember(null);

			throw new MissingDANDetailsException("No DANs available for Allocation");
		}
		actionForm.setDanSummaries(danSummaries);

		actionForm.setBankId(bankId);
		actionForm.setZoneId(zoneId);
		actionForm.setBranchId(branchId);

		Log.log(4, "RPAction", "getPendingASFDANs", "Exited");
		if (actionForm.getSelectMember() != null) {
			actionForm.setMemberId(actionForm.getSelectMember());
		} else {
			actionForm.setMemberId(bankId + zoneId + branchId);
		}

		actionForm.setSelectMember(null);

		return mapping.findForward("danSummary3");
	}

	public ActionForward getPendingExpiredASFDANs(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "getPendingExpiredASFDANs", "Entered");

		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = "";

		RPActionForm actionForm = (RPActionForm) form;

		HttpSession session = request.getSession(false);

		if (actionForm.getDanSummaries() != null) {
			actionForm.getDanSummaries().clear();
		}
		if (actionForm.getDanPanDetails() != null) {
			actionForm.getDanPanDetails().clear();
		}

		if (actionForm.getCgpans() != null) {
			actionForm.getCgpans().clear();
		}

		if (actionForm.getAllocatedFlags() != null) {
			actionForm.getAllocatedFlags().clear();
		}

		if (actionForm.getFirstDisbursementDates() != null) {
			actionForm.getFirstDisbursementDates().clear();
		}

		if (actionForm.getNotAllocatedReasons() != null) {
			actionForm.getNotAllocatedReasons().clear();
		}

		Log.log(5, "RPAction", "getPendingExpiredASFDANs", "Bank Id : " + bankId);
		Log.log(5, "RPAction", "getPendingExpiredASFDANs", "Zone Id : " + zoneId);
		Log.log(5, "RPAction", "getPendingExpiredASFDANs", "Branch Id : " + branchId);

		if (bankId.equals("0000")) {
			memberId = actionForm.getSelectMember();

			if ((memberId == null) || (memberId.equals(""))) {
				memberId = actionForm.getMemberId();
			}

			Log.log(5, "RPAction", "getPendingExpiredASFDANs", "mliId = " + memberId);

			if ((memberId == null) || (memberId.equals(""))) {
				Log.log(5, "RPAction", "getPendingExpiredASFDANs",
						"Menu Target = " + MenuOptions.getMenuAction("RP_ALLOCATE_PAYMENTS"));

				session.setAttribute("TARGET_URL", "selectASFMemberForExpired.do?method=getPendingExpiredASFDANs");

				return mapping.findForward("memberInfo");
			}

			bankId = memberId.substring(0, 4);
			zoneId = memberId.substring(4, 8);
			branchId = memberId.substring(8, 12);

			ClaimsProcessor cpProcessor = new ClaimsProcessor();
			Vector memberIds = cpProcessor.getAllMemberIds();
			if (!memberIds.contains(memberId)) {
				throw new NoMemberFoundException("The Member ID does not exist");
			}

		}

		Log.log(5, "RPAction", "getPendingExpiredASFDANs",
				"Selected Bank Id,zone and branch ids : " + bankId + "," + zoneId + "," + branchId);

		RpProcessor rpProcessor = new RpProcessor();
		ArrayList danSummaries = rpProcessor.displayASFDANsforExpired(bankId, zoneId, branchId);

		Log.log(5, "RPAction", "getPendingExpiredASFDANs", "dan summary size : " + danSummaries.size());

		if (danSummaries.size() == 0) {
			actionForm.setSelectMember(null);

			throw new MissingDANDetailsException("No DANs available for Allocation");
		}

		boolean isDanAvailable = false;
		for (int i = 0; i < danSummaries.size(); i++) {
			DANSummary danSummary = (DANSummary) danSummaries.get(i);
			if (danSummary.getAmountDue() != danSummary.getAmountPaid()) {
				isDanAvailable = true;
				break;
			}
		}
		if (!isDanAvailable) {
			actionForm.setSelectMember(null);

			throw new MissingDANDetailsException("No DANs available for Allocation");
		}
		actionForm.setDanSummaries(danSummaries);

		actionForm.setBankId(bankId);
		actionForm.setZoneId(zoneId);
		actionForm.setBranchId(branchId);

		Log.log(4, "RPAction", "getPendingExpiredASFDANs", "Exited");
		if (actionForm.getSelectMember() != null) {
			actionForm.setMemberId(actionForm.getSelectMember());
		} else {
			actionForm.setMemberId(bankId + zoneId + branchId);
		}

		actionForm.setSelectMember(null);

		return mapping.findForward("danSummary3");
	}

	// Added By Rajuk
	public ActionForward displayallocatePaymentModifySubmit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Log.log(Log.INFO, "ClaimAction", "displayClaimProcessingInput", "Entered");
		Connection connection = DBConnection.getConnection();
		// System.out.println("connection success fully");
		HttpSession session = request.getSession();
		PreparedStatement allocateModifyStmt;
		ResultSet allocateModifyResult;
		User user = (User) getUserInformation(request);
		// System.out.println("user"+user);
		String bankid = (user.getBankId()).trim();
		String zoneid = (user.getZoneId()).trim();
		String branchid = (user.getBranchId()).trim();

		String memberId = bankid + zoneid + branchid;
		// System.out.println("memberId"+memberId);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Log.log(Log.INFO, "ClaimAction", "displayClaimProcessingInput", "Exited");
		ArrayList rpArray = new ArrayList();
		RPActionForm actionFormobj = (RPActionForm) form;

		try {

			// String
			// query="select PAY_ID, VIRTUAL_ACCOUNT_NO, AMOUNT,
			// TO_CHAR(Pay_ID_CREAted_date, 'DD-MM-YYYY HH24:MI:SS') from
			// online_payment_detail where PAYMENT_STATUS ='N' ";
			// old
			String query = "select PAY_ID, VIRTUAL_ACCOUNT_NO, AMOUNT, Pay_ID_CREAted_date from online_payment_detail where PAYMENT_STATUS ='N'and mem_bnk_id||mem_zne_id||mem_brn_id = '"
					+ memberId + "'";
			// System.out.println("query"+query);

			allocateModifyStmt = connection.prepareStatement(query);
			allocateModifyResult = allocateModifyStmt.executeQuery();

			while (allocateModifyResult.next()) {

				RPActionForm actionForm = new RPActionForm();

				actionForm.setPaymentId1(allocateModifyResult.getString(1));

				actionForm.setVaccno(allocateModifyResult.getString(2));
				actionForm.setAmmount1(allocateModifyResult.getDouble(3));
				actionForm.setPayidcreateddate(allocateModifyResult.getString(4));

				String paymentIds = allocateModifyResult.getString(1);
				actionForm.setPaymentIds("paymentIds");

				rpArray.add(actionForm);

			}
			actionFormobj.setAllocatepaymentmodify(rpArray);
		} catch (Exception exception) {
			Log.logException(exception);
			throw new DatabaseException(exception.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		return mapping.findForward("displayallocatePayments");

	}

	// ended By Rajuk

	// added by rajuk
	public ActionForward displayallocatePaymentModifySubmitDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		RPActionForm actionFormobj = (RPActionForm) form;

		String paymentID = request.getParameter("paymentIds");

		Map approveFlags = actionFormobj.getAllocationPaymentYes();

		if (approveFlags.size() == 0) {
			throw new NoMemberFoundException("Please select atleast one PAYMENT ID to Approve.");
		}
		/*
		 * if(approveFlags.size()>=50) { throw new NoMemberFoundException(
		 * "Please select Below  50 FLAGS For PAYMENT ID to Approve."); }
		 */

		System.out.println("payid " + approveFlags);
		System.out.println("payid value approveFlags " + approveFlags.size());
		Set keys = approveFlags.keySet();

		User user = getUserInformation(request);
		String userid = user.getUserId();
		String bankid = (user.getBankId()).trim();
		String zoneid = (user.getZoneId()).trim();
		String branchid = (user.getBranchId()).trim();
		String memberId = bankid + zoneid + branchid;
		Connection connection = DBConnection.getConnection();
		connection.setAutoCommit(false);
		Statement str1 = connection.createStatement();

		try {

			Iterator clmInterat = keys.iterator();
			int insdanstatus = 0;
			int deldanstatus = 0;
			int deldanstatus1 = 0;

			int inspaystatus = 0;

			int delpaystatus = 0;

			while (clmInterat.hasNext())

			{

				String payids = (String) clmInterat.next();

				String arr[] = payids.split("@");
				System.out.println("PayID " + arr[0]);

				String decision = (String) approveFlags.get(payids);

				System.out.println("keys are" + payids);

				System.out.println("values  are" + decision);

				java.util.Date todaydate = new Date();

				// if(decision.equals("Y"))

				{

					String quryforSelect = "insert into dan_cgpan_info_temp_canc select *  from dan_cgpan_info_temp where pay_id ='"
							+ arr[0] + "' ";
					insdanstatus = str1.executeUpdate(quryforSelect);
					System.out.println("testing1" + quryforSelect);

					String quryforSelect3 = "insert into payment_detail_temp_canc  select * from  payment_detail_temp where pay_id ='"
							+ arr[0] + "' ";
					System.out.println("testing1" + quryforSelect3);
					inspaystatus = str1.executeUpdate((quryforSelect3));

					String quryforSelect4 = "delete from payment_detail_temp where pay_id ='" + arr[0] + "'";
					System.out.println("testing1" + quryforSelect4);
					delpaystatus = str1.executeUpdate((quryforSelect4));

					String quryforSelect2 = "delete from dan_cgpan_info_temp where pay_id ='" + arr[0] + "'";
					System.out.println("testing2" + quryforSelect2);
					deldanstatus = str1.executeUpdate((quryforSelect2));

					String quryforSelect5 = "update  online_payment_detail set PAYMENT_STATUS ='C' where PAY_ID  ='"
							+ arr[0] + "'";
					System.out.println("testing3" + quryforSelect5);
					deldanstatus1 = str1.executeUpdate((quryforSelect5));

				}
				System.out.println("insdanstatus " + insdanstatus + "deldanstatus " + deldanstatus + "inspaystatus "
						+ inspaystatus + "delpaystatus " + delpaystatus + "deldanstatus1 " + deldanstatus1);
				if ((insdanstatus != 0) && (deldanstatus != 0) && (inspaystatus != 0) && (delpaystatus != 0)
						&& (deldanstatus1 != 0)) {

					connection.commit();
				} else {
					connection.rollback();
					throw new MissingDANDetailsException("not able to deallocate pay id. problem in  '" + arr[0] + "'");
				}

			}

		}

		catch (SQLException sqlexception) {
			// sqlexception.printStackTrace();
			connection.rollback();
			throw new DatabaseException(sqlexception.getMessage());

		} finally {
			Connection conn = null;
			DBConnection.freeConnection(conn);
		}

		connection.commit();

		request.setAttribute("message", " RP Modified / Cancelled successfully !!!");
		return mapping.findForward("success");

	}

	// ended by rajuk

	// /added by rajuk
	public ActionForward displayPaymentIdDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception

	{
		ReportManager manager;
		RPActionForm rappform;
		String clmApplicationStatus;
		String memberId;
		String cgpanno;
		String PaymentId = "";

		Log.log(4, "RPAction", "displayPaymentIdDetail", "Entered");
		manager = new ReportManager();
		rappform = (RPActionForm) form;
		clmApplicationStatus = "";
		Log.log(4, "RPAction", "displayPaymentIdDetail", (new StringBuilder())
				.append("Claim Application Status being queried :").append(clmApplicationStatus).toString());
		User user = getUserInformation(request);
		String bankid = user.getBankId().trim();
		String zoneid = user.getZoneId().trim();
		String branchid = user.getBranchId().trim();
		memberId = (new StringBuilder()).append(bankid).append(zoneid).append(branchid).toString();
		request.setAttribute("PaymentId", request.getParameter("PaymentId"));

		PaymentId = request.getParameter("PaymentId");
		System.out.println(PaymentId);
		ArrayList rpArray = new ArrayList();
		RPActionForm actionFormobj = (RPActionForm) form;

		PreparedStatement pstmt = null;
		ResultSet rst = null;
		String PayId = "";
		Connection connection = DBConnection.getConnection();

		Statement allocateStmt = connection.createStatement();

		ResultSet allocateModifyResult1 = null;

		try {
			String query = "select cgpan,dan_id,dci_amount_raised,to_char(DCI_ALLOCATION_DT, 'DD-MM-YYYY HH24:MI:SS') from dan_cgpan_info_temp where pay_id ='"
					+ PaymentId + "' ";
			System.out.println("query" + query);

			allocateModifyResult1 = allocateStmt.executeQuery(query);

			RPActionForm actionForm = null;

			while (allocateModifyResult1.next()) {

				actionForm = new RPActionForm();
				actionForm.setCgpan1(allocateModifyResult1.getString(1));
				actionForm.setDanid1(allocateModifyResult1.getString(2));
				actionForm.setAmmount2(allocateModifyResult1.getDouble(3));
				actionForm.setDandate1(allocateModifyResult1.getString(4));

				String allocatedanIds = allocateModifyResult1.getString(2);
				System.out.println("displayPaymentIdDetail allocatedanIds" + allocatedanIds);
				actionForm.setAllocatedanIds("allocatedanIds");
				actionForm.setPaymentId(request.getParameter("PaymentId"));
				rpArray.add(actionForm);

			}
			actionFormobj.setAllocatepayIDdetails(rpArray);
		} catch (Exception exception) {
			Log.logException(exception);
			throw new DatabaseException(exception.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}
		return mapping.findForward("success");
	}

	// end by rajuk
	// added by raju
	public ActionForward submitDanWiseDeallocation(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "generateClaimSFDAN", "Entered");

		HttpSession session = request.getSession();
		RPActionForm actionFormobj = (RPActionForm) form;

		String danID = request.getParameter("allocatedanIds");
		System.out.println("danID " + danID);
		Map approveFlags = actionFormobj.getAllocationPaymentDans();
		System.out.println("danID " + approveFlags);
		request.setAttribute("PaymentId", request.getParameter("PaymentId"));
		String PaymentId = "";
		PaymentId = request.getParameter("PaymentId");
		System.out.println(PaymentId);

		int size = 0;
		if (approveFlags.size() == 0) {
			throw new NoMemberFoundException("Please select atleast one DAN ID to Approve.");
		}

		System.out.println("danID " + approveFlags);
		System.out.println("danID value approveFlags " + approveFlags.size());
		Set keys = approveFlags.keySet();

		System.out.println("keys size " + keys.size());

		size = approveFlags.size();

		User user = getUserInformation(request);
		String userid = user.getUserId();
		String bankid = (user.getBankId()).trim();
		String zoneid = (user.getZoneId()).trim();
		String branchid = (user.getBranchId()).trim();
		String memberId = bankid + zoneid + branchid;
		Connection connection = DBConnection.getConnection();
		connection.setAutoCommit(false);
		Statement str1 = connection.createStatement();

		try {

			Iterator danIterat = keys.iterator();
			int insdanstatus1 = 0;
			int deldanstatus1 = 0;

			int inspaystatus1[] = new int[4];
			int delpaystatus1[] = new int[5];
			int count = 0;
			ResultSet rs = null;

			while (danIterat.hasNext()) {
				String danids = (String) danIterat.next();
				System.out.println("submitDanWiseDeallocation danids " + danids);
				String danIDPaymentID[] = danids.split("@");
				System.out.println("dan id== " + danIDPaymentID[0]);
				System.out.println("payment id==111 " + danIDPaymentID[1]);
				String quryforSelect4 = "select count(*) from dan_cgpan_info_temp where pay_id=(select pay_id from dan_cgpan_info_temp where dan_id='"
						+ danIDPaymentID[0] + "') ";
				System.out.println("testing quryforSelect4 " + quryforSelect4);
				rs = str1.executeQuery(quryforSelect4);

				while (rs.next()) {
					count = rs.getInt(1);
				}
				System.out.println("size " + size + " count " + count);
				if (size == count) {

					System.out.println("size==count");

					// for online_payment_detail

					/*
					 * String quryforSelect10=
					 * "update online_payment_detail set PAYMENT_STATUS ='C'  where pay_id=(select pay_id from dan_cgpan_info_temp where  dan_id ='"
					 * +danIDPaymentID[0]+"') ";
					 * System.out.println("quryforSelect10"+quryforSelect10); delpaystatus1[4] =
					 * str1.executeUpdate((quryforSelect10));
					 * 
					 * String quryforSelect6=
					 * "insert into payment_detail_temp_canc  select * from payment_detail_temp where pay_id=(select pay_id from dan_cgpan_info_temp where  dan_id ='"
					 * +danIDPaymentID[0]+"') ";
					 * System.out.println("quryforSelect6"+quryforSelect6); delpaystatus1[0] =
					 * str1.executeUpdate((quryforSelect6));
					 * 
					 * 
					 * String quryforSelect8=
					 * "delete from payment_detail_temp  where pay_id=(select pay_id from dan_cgpan_info_temp where  dan_id ='"
					 * +danIDPaymentID[0]+"') ";
					 * System.out.println("quryforSelect8"+quryforSelect8); delpaystatus1[1] =
					 * str1.executeUpdate((quryforSelect8));
					 * 
					 * String quryforSelect7=
					 * "insert into dan_cgpan_info_temp_canc  select * from dan_cgpan_info_temp where dan_id ='"
					 * +danIDPaymentID[0]+"'"; System.out.println("quryforSelect7"+quryforSelect7);
					 * delpaystatus1[2] = str1.executeUpdate((quryforSelect7));
					 * 
					 * String quryforSelect9= "delete from dan_cgpan_info_temp where dan_id ='"
					 * +danIDPaymentID[0]+"'"; System.out.println("quryforSelect9"+quryforSelect9);
					 * delpaystatus1[3] = str1.executeUpdate((quryforSelect9));
					 */

					String quryforSelect10 = "update online_payment_detail set PAYMENT_STATUS ='C'  where pay_id='"
							+ danIDPaymentID[1] + "'";
					System.out.println("quryforSelect10" + quryforSelect10);
					delpaystatus1[4] = str1.executeUpdate((quryforSelect10));

					String quryforSelect6 = "insert into payment_detail_temp_canc  select * from payment_detail_temp where pay_id='"
							+ danIDPaymentID[1] + "'";
					System.out.println("quryforSelect6" + quryforSelect6);
					delpaystatus1[0] = str1.executeUpdate((quryforSelect6));

					String quryforSelect8 = "delete from payment_detail_temp  where pay_id='" + danIDPaymentID[1] + "'";
					System.out.println("quryforSelect8" + quryforSelect8);
					delpaystatus1[1] = str1.executeUpdate((quryforSelect8));

					String quryforSelect7 = "insert into dan_cgpan_info_temp_canc  select * from dan_cgpan_info_temp where pay_id='"
							+ danIDPaymentID[1] + "'";
					System.out.println("quryforSelect7" + quryforSelect7);
					delpaystatus1[2] = str1.executeUpdate((quryforSelect7));

					String quryforSelect9 = "delete from dan_cgpan_info_temp where pay_id='" + danIDPaymentID[1] + "'";
					System.out.println("quryforSelect9" + quryforSelect9);
					delpaystatus1[3] = str1.executeUpdate((quryforSelect9));

					System.out.println("delpaystatus1[0] " + delpaystatus1[0]);
					System.out.println("delpaystatus1[1] " + delpaystatus1[1]);
					System.out.println("delpaystatus1[2] " + delpaystatus1[2]);
					System.out.println("delpaystatus1[3] " + delpaystatus1[3]);
					System.out.println("delpaystatus1[4] " + delpaystatus1[4]);
					// connection.commit();

					if ((delpaystatus1[0] != 0) && (delpaystatus1[1] != 0) && (delpaystatus1[2] != 0)
							&& (delpaystatus1[3] != 0) && (delpaystatus1[4] != 0)) {

						connection.commit();

						break;

					} else {

						connection.rollback();
						throw new MissingDANDetailsException(
								"Not able to deallocate dan id. problem in  '" + danIDPaymentID[0] + "'");
					}

				}

				else {

					// String quryforSelect3=
					// "update payment_detail_temp set PAY_AMOUNT = (select sum(DCI_AMOUNT_RAISED)
					// from dan_cgpan_info_temp where PAY_ID= "+danids+"') where PAY_ID=
					// "+danids+"'";

					String quryforSelect3 = " update payment_detail_temp  set PAY_AMOUNT=PAY_AMOUNT-(select dci_amount_raised from "
							+ "dan_cgpan_info_temp  where  dan_id='" + danIDPaymentID[0]
							+ "') where pay_id=(select a.pay_id from dan_cgpan_info_temp a where   dan_id='"
							+ danIDPaymentID[0] + "') ";
					System.out.println("quryforSelect3 " + quryforSelect3);
					inspaystatus1[2] = str1.executeUpdate((quryforSelect3));

					/*
					 * long var_dci_amount_raised=0;
					 * 
					 * String quryforSelect3=
					 * " update payment_detail_temp  set PAY_AMOUNT=PAY_AMOUNT- '"
					 * +'"+var_dci_amount_raised +"'" where dan_id='"+danIDPaymentID[0]+"') where
					 * pay_id=(select a.pay_id from dan_cgpan_info_temp a where
					 * dan_id='"+danIDPaymentID[0]+"') ";
					 * System.out.println("testing quryforSelect3 " +quryforSelect3);
					 * inspaystatus1[2] = str1.executeUpdate((quryforSelect3));
					 */

					String quryforSelect6 = " update online_payment_detail  set AMOUNT=AMOUNT-(select dci_amount_raised from "
							+ "dan_cgpan_info_temp  where  dan_id='" + danIDPaymentID[0]
							+ "') where pay_id=(select a.pay_id from dan_cgpan_info_temp a where   dan_id='"
							+ danIDPaymentID[0] + "') ";
					System.out.println(" quryforSelect6" + quryforSelect6);
					inspaystatus1[3] = str1.executeUpdate((quryforSelect6));

					// before data deletion inserting data in canc table
					String quryforSelect = "insert into dan_cgpan_info_temp_canc  select * from  dan_cgpan_info_temp where  dan_id = '"
							+ danIDPaymentID[0] + "' ";
					inspaystatus1[0] = str1.executeUpdate(quryforSelect);
					System.out.println("quryforSelect" + quryforSelect);

					// data deletion from dan_cgpan_info_temp
					String quryforSelect2 = "delete from dan_cgpan_info_temp where dan_id ='" + danIDPaymentID[0] + "'";
					System.out.println("quryforSelect" + quryforSelect2);
					inspaystatus1[1] = str1.executeUpdate((quryforSelect2));

					System.out.println("inspaystatus1[0] " + inspaystatus1[0]);
					System.out.println("inspaystatus1[1] " + inspaystatus1[1]);
					System.out.println("inspaystatus1[2] " + inspaystatus1[2]);
					System.out.println("inspaystatus1[3] " + inspaystatus1[3]);

					if ((inspaystatus1[0] != 0) && (inspaystatus1[1] != 0) && (inspaystatus1[2] != 0)
							&& (inspaystatus1[3] != 0)) {

						connection.commit();
					}

					else {
						connection.rollback();
						throw new MissingDANDetailsException(
								" 22 Not able to deallocate dan id. problem in  '" + danIDPaymentID[0] + "'");
					}

				}

				// if((insdanstatus1==0) && (deldanstatus1==0) &&
				// (inspaystatus1==0) && (delpaystatus1==0)){

				// connection.commit();
				// }
				// else
				// {
				// throw new
				// MissingDANDetailsException("not able to deallocate dan id. problem in
				// '"+danids+"'");
				// }

				// connection.rollback();
			}

		}

		catch (SQLException sqlexception)

		{
			connection.rollback();
			throw new DatabaseException(sqlexception.getMessage());

		} finally {

			DBConnection.freeConnection(connection);
		}

		request.setAttribute("message", "Selected RPs Modified/ Cancelled Successfully!!!");

		return mapping.findForward("success");

	}

	public ActionForward allocatePaymentFinal(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(Log.INFO, "ClaimAction", "displayClaimProcessingInput", "Entered");
		Connection connection = DBConnection.getConnection();

		HttpSession session = request.getSession();
		PreparedStatement allocatePaymentfinalStmt;
		ResultSet allocatePaymentFinalResult;
		User user = (User) getUserInformation(request);
		System.out.println("user" + user);
		String bankid = (user.getBankId()).trim();
		String zoneid = (user.getZoneId()).trim();
		String branchid = (user.getBranchId()).trim();
		String memberId = bankid + zoneid + branchid;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Log.log(Log.INFO, "ClaimAction", "displayClaimProcessingInput", "Exited");
		ArrayList rpArray = new ArrayList();
		RPActionForm actionFormobj = (RPActionForm) form;
		
		String danIdTxt=actionFormobj.getDanIdTxt();
		Date dtFromDate=actionFormobj.getDtFromDate();
		Date dtToDate  =actionFormobj.getDtToDate();
		
		System.out.println("danIdTxt="+ danIdTxt);
		System.out.println("dtFromDate="+ dtFromDate);
		System.out.println("dtToDate="+ dtToDate);
		
		Connection connection1 = null;
		 ResultSet 	rsDanDetails = null;
		 CallableStatement getDanDetailsStmt;
		 ArrayList danDetails = null;
		 int getSchemesStatus = 0;
		 String getDanDetailsErr = "";

		try {
			
		/*	if(dtFromDate.toString().length()>0||dtToDate.toString().length()>0 ||danIdTxt!=null||danIdTxt!="")
			{
			RPActionForm actionForm = new RPActionForm();
		
			
			 danDetails = new ArrayList();
		 	  connection = DBConnection.getConnection(false);
			  getDanDetailsStmt = connection1.prepareCall("{call funcGetGFDanDetailsModified(?,?,?,?,?,?)}");

			  getDanDetailsStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			  
			  getDanDetailsStmt.setString(2, danIdTxt); 
			  getDanDetailsStmt.setString(3, memberId);
			  getDanDetailsStmt.setString(4, dtFromDate.toString());
			  getDanDetailsStmt.setString(5, dtToDate.toString());
			  
			
			  getDanDetailsStmt.registerOutParameter(6, java.sql.Types.VARCHAR);
			  
			  getDanDetailsStmt.execute();
			  
			  getSchemesStatus = getDanDetailsStmt.getInt(1); 
			  if(getSchemesStatus==0) 
			  {
				 
				 rsDanDetails=getDanDetailsStmt.executeQuery();
				 while(rsDanDetails.next()) 
					
					 while(rsDanDetails.next()) 
					 {
						RPActionForm rpactionform=new RPActionForm();
						rpactionform.setCgpan(rsDanDetails.getString("CGPAN"));
						rpactionform.setPaymentIdF(rsDanDetails.getString("DAN_ID"));
						rpactionform.setRPDATEF(rsDanDetails.getString("DAN_Date"));
						rpactionform.setAmtF(rsDanDetails.getDouble("DCI_GFEE_AMT"));

						rpArray.add(actionForm);
					 }
					 rsDanDetails.close();
					 rsDanDetails=null;
				  }
				  else 
				  {
					  getDanDetailsErr = getDanDetailsStmt.getString(6);
					  getDanDetailsStmt.close();
					  getDanDetailsStmt = null;
					  connection.rollback();
					  throw new DatabaseException(getDanDetailsErr);
				   }
					
					
			
				actionFormobj.setAllocatepaymentFinal(rpArray);
			  }
				else
			 	{*/
			String query = null;
			if(memberId.equals("000000000000")){
				 query = "select DAN_ID, CGPAN, Round(DCI_GFEE_AMT,0) DCI_GFEE_AMT, IFNULL(DATE_FORMAT(DAN_Date, '%d/%m/%Y'),'')DAN_Date,M.MEM_BANK_NAME from DAN_CGPAN_INFO A INNER JOIN member_info M ON A.MEM_BNK_ID=M.MEM_BNK_ID where M.MEM_ZNE_ID='0000' AND DAN_STATUS='NE'";
			}
			else{
				 query = "select DAN_ID, CGPAN, Round(DCI_GFEE_AMT,0) DCI_GFEE_AMT, IFNULL(DATE_FORMAT(DAN_Date, '%d/%m/%Y'),'')DAN_Date,M.MEM_BANK_NAME from DAN_CGPAN_INFO A INNER JOIN member_info M ON A.MEM_BNK_ID=M.MEM_BNK_ID where M.MEM_ZNE_ID='0000' AND DAN_STATUS='NE' and CONCAT(A.MEM_BNK_ID,A.MEM_ZNE_ID,A.MEM_BRN_ID) = '"+ memberId + "'";
			}

			allocatePaymentfinalStmt = connection.prepareStatement(query);
			allocatePaymentFinalResult = allocatePaymentfinalStmt.executeQuery();

			while (allocatePaymentFinalResult.next()) {

				RPActionForm actionForm = new RPActionForm();

				actionForm.setPaymentIdF(allocatePaymentFinalResult.getString(1));

				actionForm.setCgpan(allocatePaymentFinalResult.getString(2));
				actionForm.setAmtF(allocatePaymentFinalResult.getDouble(3));
				actionForm.setRPDATEF(allocatePaymentFinalResult.getString(4));
				actionForm.setBankName(allocatePaymentFinalResult.getString(5));

				String paymentIds = allocatePaymentFinalResult.getString(1);
				actionForm.setPaymentIds("paymentIds");

				rpArray.add(actionForm);

			}
			actionFormobj.setAllocatepaymentFinal(rpArray);
			//	}
		} catch (Exception exception) {
			Log.logException(exception);
			throw new DatabaseException(exception.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		return mapping.findForward("allocatePaymentFinal");
	}

	
	public ActionForward allocatePaymentFinalSubmit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		RPActionForm actionFormobj = (RPActionForm) form;
		PreparedStatement makePaymentfinalStmt;
		ResultSet makePaymentFinalResult;
		
		
		String paymentID = request.getParameter("paymentIds");
		
		
		
		//String paymentMode = request.getParameter("paymentModeRP");
		// List approveFlags1 = actionFormobj.getMakepaymentList();
		//System.out.println("paymentMode=="+paymentMode);
		String paymentMode=actionFormobj.getPaymentModeRP();
		System.out.println("paymentModeRP=="+paymentMode);
		
		
		

		Map approveFlags = actionFormobj.getAllocationPaymentFinalSubmit();
		session.setAttribute("approvedData", approveFlags);

		System.out.println("payid " + approveFlags);

		if (approveFlags.size() == 0) {
			throw new NoMemberFoundException("Please select atleast one DAN-ID to MAKE PAYMENT.");
		}

		// System.out.println("payid "+approveFlags);
		System.out.println("payid value approveFlags " + approveFlags.size());
		Set keys = approveFlags.keySet();

		System.out.println("konkati" + keys);

		User user = getUserInformation(request);
		String userid = user.getUserId();
		String bankid = (user.getBankId()).trim();
		String zoneid = (user.getZoneId()).trim();
		String branchid = (user.getBranchId()).trim();
		String memberId = bankid + zoneid + branchid;
		// Connection connection = DBConnection.getConnection();
		// Statement str1 = connection.createStatement();

		Connection connection = DBConnection.getConnection();
		CallableStatement cStmt = null;
		
		String errorCode = "";
		ArrayList rpArray = new ArrayList();
		Iterator PaymentIterate = keys.iterator();
		int insdanstatus = 0;
		
		String rpNumber = null;

		while (PaymentIterate.hasNext())

		{
			RPActionForm actionForm = new RPActionForm();
			String payids = (String) PaymentIterate.next();

			System.out.println("keys are" + payids);

			String arr[] = payids.split("@");
			System.out.println("PayID " + arr[0]);
			actionForm.setPaymentIdR(arr[0]);
			
			actionForm.setAmmount2(Integer.parseInt(arr[1]));
			actionForm.setCgpan(arr[2]);
			

			actionForm.setPaymentInitiateDate(new Date());
			

			try {
				String query = "select DAN_STATUS  from DAN_CGPAN_INFO where DAN_ID='" + arr[0]
						+ "' and DAN_STATUS='IP'";

				makePaymentfinalStmt = connection.prepareStatement(query);
				makePaymentFinalResult = makePaymentfinalStmt.executeQuery();

				if (makePaymentFinalResult.next()) {

					throw new NoMemberFoundException("Payment already done");
				}

				try {

					cStmt = connection.prepareCall("{ call FUN_UPDATE_DAN_DETAIL(?,?,?,?)}");

					System.out.println("proc_dan_deallocation  633" + cStmt);

					cStmt.setString(1, arr[0]);
					cStmt.setString(2, rpNumber);
					cStmt.registerOutParameter(3, java.sql.Types.VARCHAR);

					cStmt.registerOutParameter(4, java.sql.Types.VARCHAR);

					cStmt.execute();
					// status = callableStmt.getInt(1);

					String error = cStmt.getString(4);
					rpNumber = cStmt.getString(3);
					actionForm.setRPNumber(rpNumber);

					Log.log(5, "RPAction", "cancelRpAppropriation",
							"Error code and error are " + errorCode + " " + error);

					if (error != null) {
						connection.rollback();
						throw new DatabaseException(error);

					}
					
				//}
			} catch (SQLException e) {
				try {
					connection.rollback();
				} catch (SQLException ignore) {
					Log.log(2, "RPAction", "cancelRpAppropriation", ignore.getMessage());
				}

				Log.log(2, "RPAction", "cancelRpAppropriation", e.getMessage());
				Log.logException(e);

				throw new DatabaseException(e.getMessage());
			
		}
				}
				finally {
					DBConnection.freeConnection(connection);
				}
			rpArray.add(actionForm);
		}	

		
		String pmtId = cStmt.getString(3);
		
		
		
		
		System.out.println("pmtId"+pmtId);

		Connection connection1 = DBConnection.getConnection();
		CallableStatement cStmt1 = null;
		try{
			
		
		cStmt1 = connection1.prepareCall("{call FUN_INSERT_PAYMENT_DETAIL(?,?,?,?,?,?)}");

		cStmt1.registerOutParameter(1, java.sql.Types.INTEGER);
		  
		cStmt1.setString(2, memberId); 
		cStmt1.setString(3, paymentMode);
		cStmt1.setString(4, userid);
		cStmt1.setString(5, pmtId);
		cStmt1.registerOutParameter(6, java.sql.Types.VARCHAR);
		
		cStmt1.execute();
		// status = callableStmt.getInt(1);
		System.out.println("cStmt1==="+cStmt1);

		String error1 = cStmt1.getString(6);

		Log.log(5, "RPAction", "cancelRpAppropriation",
				"Error code and error are " + errorCode + " " + error1);

		if (error1 != null) {
			
			connection1.rollback();
			throw new DatabaseException(error1);

		}
			} catch (SQLException e) {
				try {
					connection1.rollback();
				} catch (SQLException ignore) {
					Log.log(2, "RPAction", "cancelRpAppropriation", ignore.getMessage());
				}

				Log.log(2, "RPAction", "cancelRpAppropriation", e.getMessage());
				Log.logException(e);

				throw new DatabaseException(e.getMessage());
			
		}
		//} 
		finally {
			DBConnection.freeConnection(connection1);
		}
		actionFormobj.setMakepaymentFinal(rpArray);

		return mapping.findForward("displayallocatePaymentFinalsubmit");
	}


	public ActionForward displayallocatePaymentFinal(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(Log.INFO, "ClaimAction", "displayClaimProcessingInput", "Entered");
		Connection connection = DBConnection.getConnection();

		HttpSession session = request.getSession();
		PreparedStatement allocatePaymentfinalStmt;
		ResultSet allocatePaymentFinalResult;
		User user = (User) getUserInformation(request);
		System.out.println("user" + user);
		String bankid = (user.getBankId()).trim();
		String zoneid = (user.getZoneId()).trim();
		String branchid = (user.getBranchId()).trim();
		String memberId = bankid + zoneid + branchid;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Log.log(Log.INFO, "ClaimAction", "displayClaimProcessingInput", "Exited");
		ArrayList rpArray = new ArrayList();
		RPActionForm actionFormobj = (RPActionForm) form;

		try {
			String query = null;
			if(memberId.equals("000000000000")){
				query = "select distinct PMT_ID, utr_no, PMT_Amount, IFNULL(DATE_FORMAT(utr_date, '%d/%m/%Y'),'')utr_date,PMT_PaymentMode,PMT_APPROPRIATION_Status,M.MEM_BANK_NAME from subdebt_payment_info a inner join DAN_CGPAN_INFO c on a.PMT_ID=c.Payment_ID INNER JOIN member_info M ON C.MEM_BNK_ID=M.MEM_BNK_ID where M.MEM_ZNE_ID='0000' AND (PMT_APPROPRIATION_Status='IP' or PMT_APPROPRIATION_Status='RP')";
			}
			else{
				query = "select distinct PMT_ID, utr_no, PMT_Amount, IFNULL(DATE_FORMAT(utr_date, '%d/%m/%Y'),'')utr_date,PMT_PaymentMode,PMT_APPROPRIATION_Status,M.MEM_BANK_NAME from subdebt_payment_info a inner join DAN_CGPAN_INFO c on a.PMT_ID=c.Payment_ID INNER JOIN member_info M ON C.MEM_BNK_ID=M.MEM_BNK_ID where M.MEM_ZNE_ID='0000' AND (PMT_APPROPRIATION_Status='IP' or PMT_APPROPRIATION_Status='RP') and MLI_ID='"
						+ memberId + "'";
			}
			 

			allocatePaymentfinalStmt = connection.prepareStatement(query);
			allocatePaymentFinalResult = allocatePaymentfinalStmt.executeQuery();

			while (allocatePaymentFinalResult.next()) {

				RPActionForm actionForm = new RPActionForm();

				actionForm.setPaymentIdF(allocatePaymentFinalResult.getString(1));

				actionForm.setVitrualAcF(allocatePaymentFinalResult.getString(2));
				actionForm.setAmtF(allocatePaymentFinalResult.getDouble(3));
				actionForm.setRPDATEF(allocatePaymentFinalResult.getString(4));
				actionForm.setPaymentMode(allocatePaymentFinalResult.getString(5));
				if(allocatePaymentFinalResult.getString(6).equalsIgnoreCase("IP")){
					actionForm.setStatus("Payment Initiated");
				}
				else if (allocatePaymentFinalResult.getString(6).equalsIgnoreCase("RP")){
					actionForm.setStatus("Rejected due to UTR/Amount");
				}
				actionForm.setBankName(allocatePaymentFinalResult.getString(7));

				String paymentIds = allocatePaymentFinalResult.getString(1);
				actionForm.setPaymentIds("paymentIds");

				rpArray.add(actionForm);

			}
			actionFormobj.setAllocatepaymentFinal(rpArray);
		} catch (Exception exception) {
			Log.logException(exception);
			throw new DatabaseException(exception.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		return mapping.findForward("displayallocatePaymentFinal");
	}

	
	public ActionForward displayallocatePaymentFinalSubmit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		RPActionForm actionFormobj = (RPActionForm) form;
		PreparedStatement makePaymentfinalStmt;
		ResultSet makePaymentFinalResult;
		
		String[] utrno = request.getParameterValues("UTRNo");
		
		String[] utrdate = request.getParameterValues("UTRDate");		
		
		String[] amount = request.getParameterValues("amountVal");
		System.out.println(amount.length);
		
		String[] rpid = request.getParameterValues("rpIdVal");

		User user = getUserInformation(request);
		String userid = user.getUserId();
		String bankid = (user.getBankId()).trim();
		String zoneid = (user.getZoneId()).trim();
		String branchid = (user.getBranchId()).trim();
		String memberId = bankid + zoneid + branchid;
		
		Connection connection = DBConnection.getConnection();
		CallableStatement cStmt = null;
		String errorCode = "";
		ArrayList rpArray = new ArrayList();
		int insdanstatus = 0;
		

		for(int i = 0; i< amount.length;i++)
		{
			RPActionForm actionForm = new RPActionForm();
			if(utrno[i]!=null && !utrno[i].isEmpty() && utrdate[i]!=null && !utrdate[i].isEmpty()){
			actionForm.setPaymentIdR(rpid[i]);
			actionForm.setAmmount2(Double.parseDouble(amount[i]));
			actionForm.setVaccno(utrno[i]);
			actionForm.setPaymentInitiateDate(new Date (utrdate[i]));
			rpArray.add(actionForm);

			try {
				String query = "select PMT_APPROPRIATION_Status  from subdebt_payment_info where PMT_ID='" + rpid[i]+ "' and PMT_APPROPRIATION_Status='AP'";

				makePaymentfinalStmt = connection.prepareStatement(query);
				makePaymentFinalResult = makePaymentfinalStmt.executeQuery();

				if (makePaymentFinalResult.next()) {

					throw new NoMemberFoundException("Payment already done");
				}
				//strInput
				/*SimpleDateFormat smd = new SimpleDateFormat("yyyy-MM-dd");
				String dateVal = smd.format(actionForm.getPaymentInitiateDate());*/
				SimpleDateFormat sdfmt1 = new SimpleDateFormat("dd/MM/yyyy");
				SimpleDateFormat sdfmt2= new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date dDate = sdfmt1.parse(utrdate[i]);
				String strOutput = sdfmt2.format( dDate );
				
				System.out.println("strOutput==="+strOutput);
				try {

					cStmt = connection.prepareCall("{ call FUN_UPDATE_UTR_DETAIL(?,?,?,?)}");

					System.out.println("proc_dan_deallocation  633==" + cStmt);

					cStmt.setString(1, rpid[i]);
					
					cStmt.setString(2, utrno[i]);
					
					cStmt.setString(3, strOutput);

					cStmt.registerOutParameter(4, java.sql.Types.VARCHAR);

					cStmt.execute();
					// status = callableStmt.getInt(1);
					 
				System.out.println("cStmt=="+cStmt);

					String error = cStmt.getString(4);

					Log.log(5, "RPAction", "cancelRpAppropriation",
							"Error code and error are " + errorCode + " " + error);

					if (error != null) {
						connection.rollback();
						throw new DatabaseException(error);

					}
				} catch (SQLException e) {
					try {
						connection.rollback();
					} catch (SQLException ignore) {
						Log.log(2, "RPAction", "cancelRpAppropriation", ignore.getMessage());
					}

					
					Log.log(2, "RPAction", "cancelRpAppropriation", e.getMessage());
					Log.logException(e);

					throw new DatabaseException(e.getMessage());
				}
				//
			} finally {
				DBConnection.freeConnection(connection);
			}
			}
		}

		actionFormobj.setMakepaymentFinal(rpArray);

		return mapping.findForward("displayallocatePaymentFinalsubmit");
	}

	private HSSFWorkbook createWorkbook(ArrayList makepaymentFinal) {
		// TODO Auto-generated method stub
		return null;
	}

	public ActionForward getPendingGFDANs(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "getPendingGFDANs", "Entered");
		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = "";

		RPActionForm actionForm = (RPActionForm) form;
		HttpSession session = request.getSession(false);
		if (actionForm.getDanSummaries() != null) {
			actionForm.getDanSummaries().clear();
		}
		if (actionForm.getDanPanDetails() != null) {
			actionForm.getDanPanDetails().clear();
		}
		if (actionForm.getCgpans() != null) {
			actionForm.getCgpans().clear();
		}
		if (actionForm.getAllocatedFlags() != null) {
			actionForm.getAllocatedFlags().clear();
		}
		if (actionForm.getFirstDisbursementDates() != null) {
			actionForm.getFirstDisbursementDates().clear();
		}
		if (actionForm.getNotAllocatedReasons() != null) {
			actionForm.getNotAllocatedReasons().clear();
		}
		if (actionForm.getAppropriatedFlags() != null) {
			actionForm.getAppropriatedFlags().clear();
		}

		Log.log(5, "RPAction", "getPendingGFDANs", "Bank Id : " + bankId);
		Log.log(5, "RPAction", "getPendingGFDANs", "Zone Id : " + zoneId);
		Log.log(5, "RPAction", "getPendingGFDANs", "Branch Id : " + branchId);

		if (bankId.equals("0000")) {
			memberId = actionForm.getSelectMember();

			if ((memberId == null) || (memberId.equals(""))) {
				memberId = actionForm.getMemberId();
			}

			Log.log(5, "RPAction", "getPendingGFDANs", "mliId = " + memberId);

			if ((memberId == null) || (memberId.equals(""))) {
				session.setAttribute("TARGET_URL", "selectGFMember.do?method=getPendingGFDANs");

				return mapping.findForward("memberInfo");
			}

			bankId = memberId.substring(0, 4);
			zoneId = memberId.substring(4, 8);
			branchId = memberId.substring(8, 12);

			ClaimsProcessor cpProcessor = new ClaimsProcessor();
			Vector memberIds = cpProcessor.getAllMemberIds();
			if (!memberIds.contains(memberId)) {
				throw new NoMemberFoundException("The Member ID does not exist");
			}

		}

		Log.log(5, "RPAction", "getPendingGFDANs",
				"Selected Bank Id,zone and branch ids : " + bankId + "," + zoneId + "," + branchId);

		RpProcessor rpProcessor = new RpProcessor();
		ArrayList danSummaries = rpProcessor.displayGFDANs(bankId, zoneId, branchId);

		Log.log(5, "RPAction", "getPendingGFDANs", "dan summary size : " + danSummaries.size());

		if (danSummaries.size() == 0) {
			actionForm.setSelectMember(null);

			throw new MissingDANDetailsException("No DANs available for Allocation");
		}

		boolean isDanAvailable = false;
		for (int i = 0; i < danSummaries.size(); i++) {
			DANSummary danSummary = (DANSummary) danSummaries.get(i);
			if (danSummary.getAmountDue() != danSummary.getAmountPaid()) {
				isDanAvailable = true;
				break;
			}
		}
		if (!isDanAvailable) {
			actionForm.setSelectMember(null);

			throw new MissingDANDetailsException("No DANs available for Allocation");
		}
		actionForm.setDanSummaries(danSummaries);

		actionForm.setBankId(bankId);
		actionForm.setZoneId(zoneId);
		actionForm.setBranchId(branchId);

		Log.log(4, "RPAction", "getPendingGFDANs", "Exited");
		if (actionForm.getSelectMember() != null) {
			actionForm.setMemberId(actionForm.getSelectMember());
		} else {
			actionForm.setMemberId(bankId + zoneId + branchId);
		}

		actionForm.setSelectMember(null);

		return mapping.findForward("danSummary2");
	}

	// rajuk

	public ActionForward getPendingGFDANsOnline(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "getPendingGFDANs", "Entered");
		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = "";

		RPActionForm actionForm = (RPActionForm) form;
		HttpSession session = request.getSession(false);
		if (actionForm.getDanSummaries() != null) {
			actionForm.getDanSummaries().clear();
		}
		if (actionForm.getDanPanDetails() != null) {
			actionForm.getDanPanDetails().clear();
		}
		if (actionForm.getCgpans() != null) {
			actionForm.getCgpans().clear();
		}
		if (actionForm.getAllocatedFlags() != null) {
			actionForm.getAllocatedFlags().clear();
		}
		if (actionForm.getFirstDisbursementDates() != null) {
			actionForm.getFirstDisbursementDates().clear();
		}
		if (actionForm.getNotAllocatedReasons() != null) {
			actionForm.getNotAllocatedReasons().clear();
		}
		if (actionForm.getAppropriatedFlags() != null) {
			actionForm.getAppropriatedFlags().clear();
		}

		Log.log(5, "RPAction", "getPendingGFDANs", "Bank Id : " + bankId);
		Log.log(5, "RPAction", "getPendingGFDANs", "Zone Id : " + zoneId);
		Log.log(5, "RPAction", "getPendingGFDANs", "Branch Id : " + branchId);

		if (bankId.equals("0000")) {
			memberId = actionForm.getSelectMember();

			if ((memberId == null) || (memberId.equals(""))) {
				memberId = actionForm.getMemberId();
			}

			Log.log(5, "RPAction", "getPendingGFDANs", "mliId = " + memberId);

			if ((memberId == null) || (memberId.equals(""))) {
				session.setAttribute("TARGET_URL", "selectGFMember.do?method=getPendingGFDANs");

				return mapping.findForward("memberInfo");
			}

			bankId = memberId.substring(0, 4);
			zoneId = memberId.substring(4, 8);
			branchId = memberId.substring(8, 12);

			ClaimsProcessor cpProcessor = new ClaimsProcessor();
			Vector memberIds = cpProcessor.getAllMemberIds();
			if (!memberIds.contains(memberId)) {
				throw new NoMemberFoundException("The Member ID does not exist");
			}

		}

		Log.log(5, "RPAction", "getPendingGFDANs",
				"Selected Bank Id,zone and branch ids : " + bankId + "," + zoneId + "," + branchId);

		RpProcessor rpProcessor = new RpProcessor();
		ArrayList danSummaries = rpProcessor.displayGFDANs(bankId, zoneId, branchId);

		Log.log(5, "RPAction", "getPendingGFDANs", "dan summary size : " + danSummaries.size());

		if (danSummaries.size() == 0) {
			actionForm.setSelectMember(null);

			throw new MissingDANDetailsException("No DANs available for Allocation");
		}

		boolean isDanAvailable = false;
		for (int i = 0; i < danSummaries.size(); i++) {
			DANSummary danSummary = (DANSummary) danSummaries.get(i);
			if (danSummary.getAmountDue() != danSummary.getAmountPaid()) {
				isDanAvailable = true;
				break;
			}
		}
		if (!isDanAvailable) {
			actionForm.setSelectMember(null);

			throw new MissingDANDetailsException("No DANs available for Allocation");
		}
		actionForm.setDanSummaries(danSummaries);

		actionForm.setBankId(bankId);
		actionForm.setZoneId(zoneId);
		actionForm.setBranchId(branchId);

		Log.log(4, "RPAction", "getPendingGFDANs", "Exited");
		if (actionForm.getSelectMember() != null) {
			actionForm.setMemberId(actionForm.getSelectMember());
		} else {
			actionForm.setMemberId(bankId + zoneId + branchId);
		}

		actionForm.setSelectMember(null);

		return mapping.findForward("danSummary2");
	}

	// rajuk

	public ActionForward getNEFTPendingGFDANs(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "getNEFTPendingGFDANs", "Entered");

		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = "";
		RPActionForm actionForm = (RPActionForm) form;
		HttpSession session = request.getSession(false);
		if (actionForm.getDanSummaries() != null) {
			actionForm.getDanSummaries().clear();
		}
		if (actionForm.getDanPanDetails() != null) {
			actionForm.getDanPanDetails().clear();
		}
		if (actionForm.getCgpans() != null) {
			actionForm.getCgpans().clear();
		}
		if (actionForm.getAllocatedFlags() != null) {
			actionForm.getAllocatedFlags().clear();
		}
		if (actionForm.getFirstDisbursementDates() != null) {
			actionForm.getFirstDisbursementDates().clear();
		}
		if (actionForm.getNotAllocatedReasons() != null) {
			actionForm.getNotAllocatedReasons().clear();
		}
		if (actionForm.getAppropriatedFlags() != null) {
			actionForm.getAppropriatedFlags().clear();
		}
		Log.log(5, "RPAction", "getNEFTPendingGFDANs", "Bank Id : " + bankId);
		Log.log(5, "RPAction", "getNEFTPendingGFDANs", "Zone Id : " + zoneId);
		Log.log(5, "RPAction", "getNEFTPendingGFDANs", "Branch Id : " + branchId);

		if (bankId.equals("0000")) {
			memberId = actionForm.getSelectMember();

			if ((memberId == null) || (memberId.equals(""))) {
				memberId = actionForm.getMemberId();
			}

			Log.log(5, "RPAction", "getNEFTPendingGFDANs", "mliId = " + memberId);

			if ((memberId == null) || (memberId.equals(""))) {
				session.setAttribute("TARGET_URL", "selectGFMemberNew.do?method=getNEFTPendingGFDANs");

				return mapping.findForward("memberInfoNew");
			}

			bankId = memberId.substring(0, 4);
			zoneId = memberId.substring(4, 8);
			branchId = memberId.substring(8, 12);

			ClaimsProcessor cpProcessor = new ClaimsProcessor();
			Vector memberIds = cpProcessor.getAllMemberIds();
			if (!memberIds.contains(memberId)) {
				throw new NoMemberFoundException("The Member ID does not exist");
			}

		}

		Log.log(5, "RPAction", "getNEFTPendingGFDANs",
				"Selected Bank Id,zone and branch ids : " + bankId + "," + zoneId + "," + branchId);

		RpProcessor rpProcessor = new RpProcessor();
		ArrayList danSummaries = rpProcessor.displayGFDANs(bankId, zoneId, branchId);

		Log.log(5, "RPAction", "getNEFTPendingGFDANs", "dan summary size : " + danSummaries.size());

		if (danSummaries.size() == 0) {
			actionForm.setSelectMember(null);

			throw new MissingDANDetailsException("No DANs available for Allocation");
		}

		boolean isDanAvailable = false;
		for (int i = 0; i < danSummaries.size(); i++) {
			DANSummary danSummary = (DANSummary) danSummaries.get(i);
			if (danSummary.getAmountDue() != danSummary.getAmountPaid()) {
				isDanAvailable = true;
				break;
			}
		}
		if (!isDanAvailable) {
			actionForm.setSelectMember(null);

			throw new MissingDANDetailsException("No DANs available for Allocation");
		}
		actionForm.setDanSummaries(danSummaries);

		actionForm.setBankId(bankId);
		actionForm.setZoneId(zoneId);
		actionForm.setBranchId(branchId);

		Log.log(4, "RPAction", "getNEFTPendingGFDANs", "Exited");
		if (actionForm.getSelectMember() != null) {
			actionForm.setMemberId(actionForm.getSelectMember());
		} else {
			actionForm.setMemberId(bankId + zoneId + branchId);
		}

		actionForm.setSelectMember(null);

		return mapping.findForward("neftdanSummary");
	}

	public ActionForward getPendingSFDANs(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "getPendingDANs", "Entered");

		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = "";

		RPActionForm actionForm = (RPActionForm) form;

		HttpSession session = request.getSession(false);

		if (actionForm.getDanSummaries() != null) {
			actionForm.getDanSummaries().clear();
		}
		if (actionForm.getDanPanDetails() != null) {
			actionForm.getDanPanDetails().clear();
		}

		if (actionForm.getCgpans() != null) {
			actionForm.getCgpans().clear();
		}

		if (actionForm.getAllocatedFlags() != null) {
			actionForm.getAllocatedFlags().clear();
		}

		if (actionForm.getFirstDisbursementDates() != null) {
			actionForm.getFirstDisbursementDates().clear();
		}

		if (actionForm.getNotAllocatedReasons() != null) {
			actionForm.getNotAllocatedReasons().clear();
		}

		Log.log(5, "RPAction", "getPendingDANs", "Bank Id : " + bankId);
		Log.log(5, "RPAction", "getPendingDANs", "Zone Id : " + zoneId);
		Log.log(5, "RPAction", "getPendingDANs", "Branch Id : " + branchId);

		if (bankId.equals("0000")) {
			memberId = actionForm.getSelectMember();

			if ((memberId == null) || (memberId.equals(""))) {
				memberId = actionForm.getMemberId();
			}

			Log.log(5, "RPAction", "getPendingSFDANs", "mliId = " + memberId);

			if ((memberId == null) || (memberId.equals(""))) {
				session.setAttribute("TARGET_URL", "selectMember1.do?method=getPendingSFDANs");

				return mapping.findForward("memberInfo");
			}

			bankId = memberId.substring(0, 4);
			zoneId = memberId.substring(4, 8);
			branchId = memberId.substring(8, 12);

			ClaimsProcessor cpProcessor = new ClaimsProcessor();
			Vector memberIds = cpProcessor.getAllMemberIds();
			if (!memberIds.contains(memberId)) {
				throw new NoMemberFoundException("The Member ID does not exist");
			}

		}

		Log.log(5, "RPAction", "getPendingDANs",
				"Selected Bank Id,zone and branch ids : " + bankId + "," + zoneId + "," + branchId);

		RpProcessor rpProcessor = new RpProcessor();
		ArrayList danSummaries = rpProcessor.displaySFDANs(bankId, zoneId, branchId);

		Log.log(5, "RPAction", "getPendingDANs", "dan summary size : " + danSummaries.size());

		if (danSummaries.size() == 0) {
			actionForm.setSelectMember(null);

			throw new MissingDANDetailsException("No DANs available for Allocation");
		}

		boolean isDanAvailable = false;
		for (int i = 0; i < danSummaries.size(); i++) {
			DANSummary danSummary = (DANSummary) danSummaries.get(i);
			if (danSummary.getAmountDue() != danSummary.getAmountPaid()) {
				isDanAvailable = true;
				break;
			}
		}
		if (!isDanAvailable) {
			actionForm.setSelectMember(null);

			throw new MissingDANDetailsException("No DANs available for Allocation");
		}
		actionForm.setDanSummaries(danSummaries);

		actionForm.setBankId(bankId);
		actionForm.setZoneId(zoneId);
		actionForm.setBranchId(branchId);

		Log.log(4, "RPAction", "getPendingDANs", "Exited");
		if (actionForm.getSelectMember() != null) {
			actionForm.setMemberId(actionForm.getSelectMember());
		} else {
			actionForm.setMemberId(bankId + zoneId + branchId);
		}

		actionForm.setSelectMember(null);

		return mapping.findForward("danSummary");
	}

	public ActionForward getASFPANDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "getASFPANDetails", "Entered");
		RPActionForm actionForm = (RPActionForm) form;

		Map cgpans = actionForm.getCgpans();

		Set cgpanSet = cgpans.keySet();

		Iterator cgpanIterator = cgpanSet.iterator();

		Log.log(5, "RPAction", "getASFPANDetails", "CGPANS selected ");

		while (cgpanIterator.hasNext()) {
			String key = (String) cgpanIterator.next();
			Log.log(5, "RPAction", "getASFPANDetails", "key,value " + key + "," + cgpans.get(key));
		}

		Log.log(5, "RPAction", "getASFPANDetails", "Cgpan map size " + cgpans.size());

		String danNo = actionForm.getDanNo();
		Log.log(4, "RPAction", "getASFPANDetails", "On entering, DAN no: " + danNo);

		Log.log(4, "RPAction", "getASFPANDetails", "No Session: DAN no : " + danNo);
		RpProcessor rpProcessor = new RpProcessor();

		ArrayList returnList = rpProcessor.displayCGPANs(danNo);
		ArrayList panDetails = (ArrayList) returnList.get(0);
		ArrayList allocatedPanDetails = (ArrayList) returnList.get(1);
		Log.log(4, "RPAction", "getASFPANDetails", "No Session: No. of PAN details : " + panDetails.size());

		String allocatedFlag = (String) actionForm.getAllocatedFlag(danNo.replace('.', '_'));
		Log.log(4, "RPAction", "getASFPANDetails", "flag " + allocatedFlag);
		Map allocatedFlags = actionForm.getAllocatedFlags();
		if ((allocatedFlag != null) && (allocatedFlag.equalsIgnoreCase(danNo))) {
			for (int i = 0; i < panDetails.size(); i++) {
				AllocationDetail allocationDetail = (AllocationDetail) panDetails.get(i);
				String key = danNo.replace('.', '_') + "-" + allocationDetail.getCgpan();
				allocatedFlags.put(key, "Y");
			}
		}
		actionForm.setAllocatedFlags(allocatedFlags);

		Log.log(4, "RPAction", "getASFPANDetails", "After session validation : " + panDetails.size());
		actionForm.setDanPanDetail(danNo, panDetails);
		actionForm.setPanDetails(panDetails);
		actionForm.setAllocatedPanDetails(allocatedPanDetails);

		return mapping.findForward("asfpanDetails");
	}

	public ActionForward getExpiredASFPANDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "getExpiredASFPANDetails", "Entered");
		RPActionForm actionForm = (RPActionForm) form;

		Map cgpans = actionForm.getCgpans();

		Set cgpanSet = cgpans.keySet();

		Iterator cgpanIterator = cgpanSet.iterator();

		Log.log(5, "RPAction", "getExpiredASFPANDetails", "CGPANS selected ");

		while (cgpanIterator.hasNext()) {
			String key = (String) cgpanIterator.next();
			Log.log(5, "RPAction", "getExpiredASFPANDetails", "key,value " + key + "," + cgpans.get(key));
		}

		Log.log(5, "RPAction", "getExpiredASFPANDetails", "Cgpan map size " + cgpans.size());

		String danNo = actionForm.getDanNo();
		Log.log(4, "RPAction", "getExpiredASFPANDetails", "On entering, DAN no: " + danNo);

		Log.log(4, "RPAction", "getExpiredASFPANDetails", "No Session: DAN no : " + danNo);
		RpProcessor rpProcessor = new RpProcessor();

		ArrayList returnList = rpProcessor.displayCGPANs(danNo);
		ArrayList panDetails = (ArrayList) returnList.get(0);
		ArrayList allocatedPanDetails = (ArrayList) returnList.get(1);
		Log.log(4, "RPAction", "getExpiredASFPANDetails", "No Session: No. of PAN details : " + panDetails.size());

		String allocatedFlag = (String) actionForm.getAllocatedFlag(danNo.replace('.', '_'));
		Log.log(4, "RPAction", "getExpiredASFPANDetails", "flag " + allocatedFlag);
		Map allocatedFlags = actionForm.getAllocatedFlags();
		if ((allocatedFlag != null) && (allocatedFlag.equalsIgnoreCase(danNo))) {
			for (int i = 0; i < panDetails.size(); i++) {
				AllocationDetail allocationDetail = (AllocationDetail) panDetails.get(i);
				String key = danNo.replace('.', '_') + "-" + allocationDetail.getCgpan();
				allocatedFlags.put(key, "Y");
			}
		}
		actionForm.setAllocatedFlags(allocatedFlags);

		Log.log(4, "RPAction", "getExpiredASFPANDetails", "After session validation : " + panDetails.size());
		actionForm.setDanPanDetail(danNo, panDetails);
		actionForm.setPanDetails(panDetails);
		actionForm.setAllocatedPanDetails(allocatedPanDetails);

		return mapping.findForward("asfpanDetails");
	}

	public ActionForward getGFPANDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "getGFPANDetails", "Entered");
		RPActionForm actionForm = (RPActionForm) form;

		Map cgpans = actionForm.getCgpans();

		Set cgpanSet = cgpans.keySet();

		Iterator cgpanIterator = cgpanSet.iterator();

		Log.log(5, "RPAction", "getGFPANDetails", "CGPANS selected ");

		while (cgpanIterator.hasNext()) {
			String key = (String) cgpanIterator.next();
			Log.log(5, "RPAction", "getGFPANDetails", "key,value " + key + "," + cgpans.get(key));
		}

		Log.log(5, "RPAction", "getGFPANDetails", "Cgpan map size " + cgpans.size());

		String danNo = actionForm.getDanNo();
		Log.log(4, "RPAction", "getGFPANDetails", "On entering, DAN no: " + danNo);

		Log.log(4, "RPAction", "getGFPANDetails", "No Session: DAN no : " + danNo);
		RpProcessor rpProcessor = new RpProcessor();

		ArrayList returnList = rpProcessor.displayCGPANs(danNo);
		ArrayList panDetails = (ArrayList) returnList.get(0);
		ArrayList allocatedPanDetails = (ArrayList) returnList.get(1);
		Log.log(4, "RPAction", "getGFPANDetails", "No Session: No. of PAN details : " + panDetails.size());

		String allocatedFlag = (String) actionForm.getAllocatedFlag(danNo.replace('.', '_'));
		Log.log(4, "RPAction", "getGFPANDetails", "flag " + allocatedFlag);
		Map allocatedFlags = actionForm.getAllocatedFlags();
		if ((allocatedFlag != null) && (allocatedFlag.equalsIgnoreCase(danNo))) {
			for (int i = 0; i < panDetails.size(); i++) {
				AllocationDetail allocationDetail = (AllocationDetail) panDetails.get(i);
				String key = danNo.replace('.', '_') + "-" + allocationDetail.getCgpan();
				allocatedFlags.put(key, "Y");
			}
		}
		actionForm.setAllocatedFlags(allocatedFlags);

		Log.log(4, "RPAction", "getGFPANDetails", "After session validation : " + panDetails.size());
		actionForm.setDanPanDetail(danNo, panDetails);
		actionForm.setPanDetails(panDetails);
		actionForm.setAllocatedPanDetails(allocatedPanDetails);

		return mapping.findForward("gfpanDetails");
	}

	public ActionForward getPANDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "getPANDetails", "Entered");
		RPActionForm actionForm = (RPActionForm) form;

		Map cgpans = actionForm.getCgpans();

		Set cgpanSet = cgpans.keySet();

		Iterator cgpanIterator = cgpanSet.iterator();

		Log.log(5, "RPAction", "getPANDetails", "CGPANS selected ");

		while (cgpanIterator.hasNext()) {
			String key = (String) cgpanIterator.next();
			Log.log(5, "RPAction", "getPANDetails", "key,value " + key + "," + cgpans.get(key));
		}

		Log.log(5, "RPAction", "getPANDetails", "Cgpan map size " + cgpans.size());

		String danNo = actionForm.getDanNo();
		Log.log(4, "RPAction", "getPANDetails", "On entering, DAN no: " + danNo);

		Log.log(4, "RPAction", "getPANDetails", "No Session: DAN no : " + danNo);
		RpProcessor rpProcessor = new RpProcessor();

		ArrayList returnList = rpProcessor.displayCGPANs(danNo);
		ArrayList panDetails = (ArrayList) returnList.get(0);
		ArrayList allocatedPanDetails = (ArrayList) returnList.get(1);
		Log.log(4, "RPAction", "getPANDetails", "No Session: No. of PAN details : " + panDetails.size());

		String allocatedFlag = (String) actionForm.getAllocatedFlag(danNo.replace('.', '_'));
		Log.log(4, "RPAction", "getPANDetails", "flag " + allocatedFlag);
		Map allocatedFlags = actionForm.getAllocatedFlags();
		if ((allocatedFlag != null) && (allocatedFlag.equalsIgnoreCase(danNo))) {
			for (int i = 0; i < panDetails.size(); i++) {
				AllocationDetail allocationDetail = (AllocationDetail) panDetails.get(i);
				String key = danNo.replace('.', '_') + "-" + allocationDetail.getCgpan();
				allocatedFlags.put(key, "Y");
			}
		}
		actionForm.setAllocatedFlags(allocatedFlags);

		Log.log(4, "RPAction", "getPANDetails", "After session validation : " + panDetails.size());
		actionForm.setDanPanDetail(danNo, panDetails);
		actionForm.setPanDetails(panDetails);
		actionForm.setAllocatedPanDetails(allocatedPanDetails);

		return mapping.findForward("panDetails");
	}

	public ActionForward getSFPANDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "getSFPANDetails", "Entered");
		RPActionForm actionForm = (RPActionForm) form;

		Map cgpans = actionForm.getCgpans();

		Set cgpanSet = cgpans.keySet();

		Iterator cgpanIterator = cgpanSet.iterator();

		Log.log(5, "RPAction", "getSFPANDetails", "CGPANS selected ");

		while (cgpanIterator.hasNext()) {
			String key = (String) cgpanIterator.next();
			Log.log(5, "RPAction", "getPANDetails", "key,value " + key + "," + cgpans.get(key));
		}

		Log.log(5, "RPAction", "getPANDetails", "Cgpan map size " + cgpans.size());

		String danNo = actionForm.getDanNo();
		Log.log(4, "RPAction", "getPANDetails", "On entering, DAN no: " + danNo);

		Log.log(4, "RPAction", "getPANDetails", "No Session: DAN no : " + danNo);
		RpProcessor rpProcessor = new RpProcessor();

		ArrayList returnList = rpProcessor.displaySFCGPANs(danNo);
		ArrayList panDetails = (ArrayList) returnList.get(0);
		ArrayList allocatedPanDetails = (ArrayList) returnList.get(1);
		Log.log(4, "RPAction", "getPANDetails", "No Session: No. of PAN details : " + panDetails.size());

		String allocatedFlag = (String) actionForm.getAllocatedFlag(danNo.replace('.', '_'));
		Log.log(4, "RPAction", "getPANDetails", "flag " + allocatedFlag);
		Map allocatedFlags = actionForm.getAllocatedFlags();
		if ((allocatedFlag != null) && (allocatedFlag.equalsIgnoreCase(danNo))) {
			for (int i = 0; i < panDetails.size(); i++) {
				AllocationDetail allocationDetail = (AllocationDetail) panDetails.get(i);
				String key = danNo.replace('.', '_') + "-" + allocationDetail.getCgpan();
				allocatedFlags.put(key, "Y");
			}
		}
		actionForm.setAllocatedFlags(allocatedFlags);

		Log.log(4, "RPAction", "getPANDetails", "After session validation : " + panDetails.size());
		actionForm.setDanPanDetail(danNo, panDetails);
		actionForm.setPanDetails(panDetails);
		actionForm.setAllocatedPanDetails(allocatedPanDetails);

		return mapping.findForward("panDetails");
	}

	public ActionForward deAllocatePayments(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "deAllocatePayments", "Entered");

		RPActionForm actionForm = (RPActionForm) form;
		RpProcessor rpProcessor = new RpProcessor();
		User user = getUserInformation(request);
		String userId = user.getUserId();

		String paymentId = null;
		paymentId = actionForm.getPaymentId();

		if (paymentId != null) {
			rpProcessor.deAllocatePayments(paymentId, userId);

			actionForm.setPaymentId(null);
		}

		request.setAttribute("message", "Payment ID : " + paymentId + " Cancelled Successfully:");

		Log.log(4, "RPAction", "deAllocatePayments", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward deAllocatePaymentsInput(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "deAllocatePaymentsInput", "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;

		Log.log(4, "RPAction", "deAllocatePaymentsInput", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward neftAllocatePaymentsInput(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(Log.INFO, "RPAction", "neftAllocatePaymentsInput", "Entered");
		// System.out.println("neftAllocatePaymentsInput Entered");
		RPActionForm dynaForm = (RPActionForm) form;
		String paymentId = null;
		dynaForm.setPaymentId("");
		dynaForm.setMemberId("");
		//
		// Calendar cal = Calendar.getInstance();
		// cal.set(0,0,0);
		// Date emptyDate = cal.getTime();
		dynaForm.setPaymentDate(null);
		dynaForm.setNeftCode("");

		Log.log(Log.INFO, "RPAction", "neftAllocatePaymentsInput", "Exited");

		return mapping.findForward("success");
	}

	/*
	 * public ActionForward neftAllocatePayments( ActionMapping mapping, ActionForm
	 * form, HttpServletRequest request, HttpServletResponse response) throws
	 * Exception{
	 * 
	 * Log.log(Log.INFO,"RPAction","neftAllocatePayments","Entered"); RPActionForm
	 * actionForm = (RPActionForm)form; RpDAO rpDAO = new RpDAO(); RpProcessor
	 * rpProcessor = new RpProcessor(); User user = getUserInformation(request);
	 * String userId = user.getUserId(); String bankId = user.getBankId(); String
	 * zoneId = user.getZoneId(); String branchId = user.getBranchId();
	 * 
	 * String loginMemberId = bankId.concat(zoneId).concat(branchId);
	 * 
	 * String paymentId = null; PaymentDetails paymentDtls = null; paymentId =
	 * actionForm.getPaymentId();
	 * 
	 * // System.out.println("userId:"+user.getUserId()); //
	 * System.out.println(" paymentId:"+ paymentId);
	 * 
	 * if(paymentId != null || paymentId != "" ) {
	 * 
	 * paymentId = paymentId.trim().toUpperCase(); String memberId =
	 * rpDAO.getMemberId(paymentId); if(memberId != null){
	 * if(loginMemberId.equals(memberId)){
	 * 
	 * paymentDtls = rpDAO.getPayInSlipDetailsForEPAY(paymentId,memberId);
	 * 
	 * }else{ throw new DatabaseException("RP Number not relevant with member id-"
	 * +loginMemberId+".Please check RP number."); } }else{ throw new
	 * DatabaseException("Please check RP number."); } double allocatedAmt =
	 * paymentDtls.getInstrumentAmount();
	 * 
	 * actionForm.setMemberId(memberId); actionForm.setPaymentId(paymentId);
	 * actionForm.setAllocatedAmt(allocatedAmt);
	 * 
	 * actionForm.setBankName(paymentDtls.getDrawnAtBank());
	 * //actionForm.setZoneName(paymentDtls.getdr;
	 * 
	 * actionForm.setBranchName(paymentDtls.getDrawnAtBranch());
	 * 
	 * actionForm.setBankId(bankId); actionForm.setZoneId(zoneId);
	 * actionForm.setBranchId(branchId); return mapping.findForward("insertPayId");
	 * // return mapping.findForward("success");
	 * 
	 * }
	 * 
	 * 
	 * Log.log(Log.INFO,"RPAction","neftAllocatePayments","Exited");
	 * 
	 * return mapping.findForward("success"); // return
	 * mapping.findForward("insertPayId"); }
	 */

	public ActionForward neftAllocatePayments(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "neftAllocatePayments", "Entered");

		RPActionForm actionForm = (RPActionForm) form;
		RpDAO rpDAO = new RpDAO();
		RpProcessor rpProcessor = new RpProcessor();
		User user = getUserInformation(request);
		String userId = user.getUserId();
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String paymentId = null;
		paymentId = actionForm.getPaymentId();
		String loginMemberId = bankId.concat(zoneId).concat(branchId);
		PaymentDetails paymentDtls = null;
		if (paymentId != null) {
			String memberId = rpDAO.getMemberId(paymentId);
			if (memberId != null) {
				if (loginMemberId.equals(memberId)) {
					paymentDtls = rpDAO.getPayInSlipDetails(paymentId);
					double allocatedAmt = paymentDtls.getInstrumentAmount();

					actionForm.setAllocatedAmt(allocatedAmt);
					actionForm.setPaymentId(paymentId);

					actionForm.setMemberId(memberId);

					actionForm.setBankId(bankId);
					actionForm.setZoneId(zoneId);
					actionForm.setBranchId(branchId);
				} else {
					throw new DatabaseException(
							"RP Number not relevant with member id-" + loginMemberId + ".Please check RP number.");
				}
			} else {
				throw new DatabaseException("Please check RP number.");
			}
			return mapping.findForward("insertPayId");
		}

		Log.log(4, "RPAction", "neftAllocatePayments", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward SubmitMappingRPandNEFT(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RPActionForm actionForm = (RPActionForm) form;
		RpDAO rpDAO = new RpDAO();
		RpProcessor rpProcessor = new RpProcessor();

		User user = getUserInformation(request);
		String userId = user.getUserId();
		String paymentId = actionForm.getPaymentId();
		String memberId = actionForm.getMemberId();

		double allocatedAmt = actionForm.getAllocatedAmt();
		String neftCode = actionForm.getNeftCode();
		String bankName = actionForm.getBankName();
		String zoneName = actionForm.getZoneName();
		String branchName = actionForm.getBranchName();
		String ifscCode = actionForm.getIfscCode();
		java.util.Date paymentDate = actionForm.getPaymentDate();

		rpDAO.afterMapRPwithNEFTDtls(memberId, paymentId, allocatedAmt, neftCode, bankName, zoneName, branchName,
				ifscCode, paymentDate, userId);

		request.setAttribute("message",
				"<b> Mapping Payment Id " + paymentId + " with NEFT transaction " + neftCode + " Successful");

		return mapping.findForward("success");
	}

	public ActionForward modifyAllocatePaymentDetailInput(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "modifyAllocatePaymentDetailInput", "Entered");

		RPActionForm rpAllocationForm = (RPActionForm) form;
		String paymentId = "";
		rpAllocationForm.setPaymentId(paymentId);

		Log.log(4, "RPAction", "modifyAllocatePaymentDetailInput", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward modifyAllocatePaymentDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "modifyAllocatePaymentDetail", "Entered");

		RPActionForm rpActionForm = (RPActionForm) form;
		String paymentId = rpActionForm.getPaymentId().toUpperCase();

		Log.log(5, "RPAction", "getPaymentDetailsForPayInSlip", "paymentId " + paymentId);

		RpProcessor rpProcessor = new RpProcessor();

		PaymentDetails paymentDetails = rpProcessor.displayPayInSlip(paymentId);
		paymentDetails.setPaymentId(paymentId);
		paymentDetails.setNewInstrumentNo(paymentDetails.getInstrumentNo());
		paymentDetails.setNewInstrumentDt(paymentDetails.getInstrumentDate());
		BeanUtils.copyProperties(rpActionForm, paymentDetails);

		paymentDetails = null;
		Log.log(4, "RPAction", "modifyAllocatePaymentDetail", "Exited");

		return mapping.findForward("insertPayId");
	}

	public ActionForward afterModifyAllocatePaymentDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "afterModifyAllocatePaymentDetail", "Entered");

		RPActionForm rpActionForm = (RPActionForm) form;
		RpProcessor rpProcessor = new RpProcessor();
		RpDAO rpDAO = new RpDAO();

		User user = getUserInformation(request);

		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = bankId.concat(zoneId).concat(branchId);

		rpDAO.afterModifyAllocatePaymentDetail(rpActionForm, user.getUserId());
		request.setAttribute("message",
				"Allocated Payment Id - " + rpActionForm.getPaymentId() + " Details Modified Successfully ");
		Log.log(4, "RPAction", "afterModifyAllocatePaymentDetail", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward asfallocatePayments(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RPActionForm actionForm = (RPActionForm) form;
		RpProcessor rpProcessor = new RpProcessor();
		String paymentId = "";
		String methodName = "asfallocatePayments";

		Log.log(5, "RPAction", methodName, "Entered");

		User user = getUserInformation(request);

		PaymentDetails paymentDetails = new PaymentDetails();

		String allocationType2 = actionForm.getAllocationType();

		paymentDetails.setAllocationType1(allocationType2);

		String modeOfPayment = actionForm.getModeOfPayment();
		String collectingBranch = actionForm.getCollectingBankBranch();
		java.util.Date paymentDate = actionForm.getPaymentDate();
		String instrumentNumber = actionForm.getInstrumentNo();
		java.util.Date instrumentDate = actionForm.getInstrumentDate();
		String modeOfDelivery = actionForm.getModeOfDelivery();
		double instrumentAmount = actionForm.getInstrumentAmount();
		String drawnAtBank = actionForm.getDrawnAtBank();
		String drawnAtBranch = actionForm.getDrawnAtBranch();
		String payableAt = actionForm.getPayableAt();
		String accNumber = actionForm.getAccountNumber();

		Log.log(5, "RPAction", methodName, "collecting bank " + actionForm.getCollectingBankName());
		paymentDetails.setCollectingBank(actionForm.getCollectingBankName());
		Log.log(5, "RPAction", methodName, "mode of payment " + modeOfPayment);
		paymentDetails.setModeOfPayment(modeOfPayment);
		Log.log(5, "RPAction", methodName, "collecting branch " + collectingBranch);
		paymentDetails.setCollectingBankBranch(collectingBranch);
		Log.log(5, "RPAction", methodName, "payment date " + paymentDate);
		paymentDetails.setPaymentDate(paymentDate);
		Log.log(5, "RPAction", methodName, "instrument number " + instrumentNumber);
		paymentDetails.setInstrumentNo(instrumentNumber);
		Log.log(5, "RPAction", methodName, "instrument date " + instrumentDate);
		paymentDetails.setInstrumentDate(instrumentDate);
		Log.log(5, "RPAction", methodName, "mode of delivery " + modeOfDelivery);
		paymentDetails.setModeOfDelivery(modeOfDelivery);
		Log.log(5, "RPAction", methodName, "instrument amount " + instrumentAmount);
		paymentDetails.setInstrumentAmount(instrumentAmount);
		Log.log(5, "RPAction", methodName, "drawn at bank " + drawnAtBank);
		paymentDetails.setDrawnAtBank(drawnAtBank);
		Log.log(5, "RPAction", methodName, "drawn at branch " + drawnAtBranch);
		paymentDetails.setDrawnAtBranch(drawnAtBranch);
		Log.log(5, "RPAction", methodName, "payable at " + payableAt);
		paymentDetails.setPayableAt(payableAt);
		Log.log(5, "RPAction", methodName, "acc num " + accNumber);
		paymentDetails.setCgtsiAccNumber(accNumber);

		Map allocationFlags = actionForm.getAllocatedFlags();

		ArrayList danSummaries = actionForm.getDanSummaries();

		Map cgpans = actionForm.getCgpans();

		Set cgpansSet = cgpans.keySet();

		Map danCgpanDetails = actionForm.getDanPanDetails();

		Map notAllocatedReasons = actionForm.getNotAllocatedReasons();

		for (int i = 0; i < danSummaries.size(); i++) {
			DANSummary danSummary = (DANSummary) danSummaries.get(i);

			String danId = danSummary.getDanId();
			Log.log(5, "RPAction", methodName, "danId " + danId);
			String shiftDanId = danId.replace('.', '_');

			Log.log(5, "RPAction", methodName, "contains " + danCgpanDetails.containsKey(danId));
			if (danCgpanDetails.containsKey(danId)) {
				ArrayList panAllocationDetails = (ArrayList) danCgpanDetails.get(danId);

				if (panAllocationDetails == null) {
					Log.log(5, "RPAction", methodName, "CGPAN details are not available. get them.");
					ArrayList totalList = rpProcessor.displayCGPANs(danId);
					panAllocationDetails = (ArrayList) totalList.get(0);
				}
				for (int j = 0; j < panAllocationDetails.size(); j++) {
					AllocationDetail allocationDetail = (AllocationDetail) panAllocationDetails.get(j);
					Log.log(5, "RPActionForm", "validate", " cgpan from danpandetails " + allocationDetail.getCgpan());
					if (allocationDetail.getAllocatedFlag().equals("N")) {
						Log.log(5, "RPActionForm", "validate", " not allocated ");
						String reasons = (String) notAllocatedReasons
								.get(shiftDanId + "-" + allocationDetail.getCgpan());
						Log.log(5, "RPActionForm", "validate", " reason for not allocated " + reasons);
						allocationDetail.setNotAllocatedReason(reasons);
					} else {
						allocationDetail.setNotAllocatedReason("");
					}
					panAllocationDetails.set(j, allocationDetail);
				}
				danCgpanDetails.put(danId, panAllocationDetails);
			}

		}

		paymentId = rpProcessor.allocateASFDAN(paymentDetails, danSummaries, allocationFlags, cgpans, danCgpanDetails,
				user);

		request.setAttribute("message", "Payment Allocated Successfully.<BR>Payment ID : " + paymentId);

		Log.log(5, "RPAction", methodName, "Exited");

		return mapping.findForward("success");
	}

	public ActionForward expiredasfallocatePayments(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RPActionForm actionForm = (RPActionForm) form;
		RpProcessor rpProcessor = new RpProcessor();
		String paymentId = "";
		String methodName = "expiredasfallocatePayments";

		Log.log(5, "RPAction", methodName, "Entered");

		User user = getUserInformation(request);

		PaymentDetails paymentDetails = new PaymentDetails();

		String modeOfPayment = actionForm.getModeOfPayment();
		String collectingBranch = actionForm.getCollectingBankBranch();
		java.util.Date paymentDate = actionForm.getPaymentDate();
		String instrumentNumber = actionForm.getInstrumentNo();
		java.util.Date instrumentDate = actionForm.getInstrumentDate();
		String modeOfDelivery = actionForm.getModeOfDelivery();
		double instrumentAmount = actionForm.getInstrumentAmount();
		String drawnAtBank = actionForm.getDrawnAtBank();
		String drawnAtBranch = actionForm.getDrawnAtBranch();
		String payableAt = actionForm.getPayableAt();
		String accNumber = actionForm.getAccountNumber();

		Log.log(5, "RPAction", methodName, "collecting bank " + actionForm.getCollectingBankName());
		paymentDetails.setCollectingBank(actionForm.getCollectingBankName());
		Log.log(5, "RPAction", methodName, "mode of payment " + modeOfPayment);
		paymentDetails.setModeOfPayment(modeOfPayment);
		Log.log(5, "RPAction", methodName, "collecting branch " + collectingBranch);
		paymentDetails.setCollectingBankBranch(collectingBranch);
		Log.log(5, "RPAction", methodName, "payment date " + paymentDate);
		paymentDetails.setPaymentDate(paymentDate);
		Log.log(5, "RPAction", methodName, "instrument number " + instrumentNumber);
		paymentDetails.setInstrumentNo(instrumentNumber);
		Log.log(5, "RPAction", methodName, "instrument date " + instrumentDate);
		paymentDetails.setInstrumentDate(instrumentDate);
		Log.log(5, "RPAction", methodName, "mode of delivery " + modeOfDelivery);
		paymentDetails.setModeOfDelivery(modeOfDelivery);
		Log.log(5, "RPAction", methodName, "instrument amount " + instrumentAmount);
		paymentDetails.setInstrumentAmount(instrumentAmount);
		Log.log(5, "RPAction", methodName, "drawn at bank " + drawnAtBank);
		paymentDetails.setDrawnAtBank(drawnAtBank);
		Log.log(5, "RPAction", methodName, "drawn at branch " + drawnAtBranch);
		paymentDetails.setDrawnAtBranch(drawnAtBranch);
		Log.log(5, "RPAction", methodName, "payable at " + payableAt);
		paymentDetails.setPayableAt(payableAt);
		Log.log(5, "RPAction", methodName, "acc num " + accNumber);
		paymentDetails.setCgtsiAccNumber(accNumber);

		Map allocationFlags = actionForm.getAllocatedFlags();

		ArrayList danSummaries = actionForm.getDanSummaries();

		Map cgpans = actionForm.getCgpans();

		Set cgpansSet = cgpans.keySet();

		Map danCgpanDetails = actionForm.getDanPanDetails();

		Map notAllocatedReasons = actionForm.getNotAllocatedReasons();

		for (int i = 0; i < danSummaries.size(); i++) {
			DANSummary danSummary = (DANSummary) danSummaries.get(i);

			String danId = danSummary.getDanId();
			Log.log(5, "RPAction", methodName, "danId " + danId);
			String shiftDanId = danId.replace('.', '_');

			Log.log(5, "RPAction", methodName, "contains " + danCgpanDetails.containsKey(danId));
			if (danCgpanDetails.containsKey(danId)) {
				ArrayList panAllocationDetails = (ArrayList) danCgpanDetails.get(danId);

				if (panAllocationDetails == null) {
					Log.log(5, "RPAction", methodName, "CGPAN details are not available. get them.");
					ArrayList totalList = rpProcessor.displayCGPANs(danId);
					panAllocationDetails = (ArrayList) totalList.get(0);
				}
				for (int j = 0; j < panAllocationDetails.size(); j++) {
					AllocationDetail allocationDetail = (AllocationDetail) panAllocationDetails.get(j);
					Log.log(5, "RPActionForm", "validate", " cgpan from danpandetails " + allocationDetail.getCgpan());
					if (allocationDetail.getAllocatedFlag().equals("N")) {
						Log.log(5, "RPActionForm", "validate", " not allocated ");
						String reasons = (String) notAllocatedReasons
								.get(shiftDanId + "-" + allocationDetail.getCgpan());
						Log.log(5, "RPActionForm", "validate", " reason for not allocated " + reasons);
						allocationDetail.setNotAllocatedReason(reasons);
					} else {
						allocationDetail.setNotAllocatedReason("");
					}
					panAllocationDetails.set(j, allocationDetail);
				}
				danCgpanDetails.put(danId, panAllocationDetails);
			}

		}

		paymentId = rpProcessor.allocateASFDAN(paymentDetails, danSummaries, allocationFlags, cgpans, danCgpanDetails,
				user);

		request.setAttribute("message", "Payment Allocated Successfully.<BR>Payment ID : " + paymentId);

		Log.log(5, "RPAction", methodName, "Exited");

		return mapping.findForward("success");
	}

	public ActionForward gfallocatePayments(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RPActionForm actionForm = (RPActionForm) form;

		RpProcessor rpProcessor = new RpProcessor();
		String paymentId = "";
		String methodName = "gfallocatePayments";

		Log.log(5, "RPAction", methodName, "Entered");

		User user = getUserInformation(request);

		PaymentDetails paymentDetails = new PaymentDetails();

		String allocationType = actionForm.getAllocationType();

		paymentDetails.setAllocationType1(allocationType);
		String modeOfPayment = actionForm.getModeOfPayment();
		String collectingBranch = actionForm.getCollectingBankBranch();
		java.util.Date paymentDate = actionForm.getPaymentDate();
		String instrumentNumber = actionForm.getInstrumentNo();
		java.util.Date instrumentDate = actionForm.getInstrumentDate();
		String modeOfDelivery = actionForm.getModeOfDelivery();
		double instrumentAmount = actionForm.getInstrumentAmount();
		String drawnAtBank = actionForm.getDrawnAtBank();
		String drawnAtBranch = actionForm.getDrawnAtBranch();
		String payableAt = actionForm.getPayableAt();
		String accNumber = actionForm.getAccountNumber();

		Log.log(5, "RPAction", methodName, "collecting bank " + actionForm.getCollectingBankName());
		paymentDetails.setCollectingBank(actionForm.getCollectingBankName());
		Log.log(5, "RPAction", methodName, "mode of payment " + modeOfPayment);
		paymentDetails.setModeOfPayment(modeOfPayment);
		Log.log(5, "RPAction", methodName, "collecting branch " + collectingBranch);
		paymentDetails.setCollectingBankBranch(collectingBranch);
		Log.log(5, "RPAction", methodName, "payment date " + paymentDate);
		paymentDetails.setPaymentDate(paymentDate);
		Log.log(5, "RPAction", methodName, "instrument number " + instrumentNumber);
		paymentDetails.setInstrumentNo(instrumentNumber);
		Log.log(5, "RPAction", methodName, "instrument date " + instrumentDate);
		paymentDetails.setInstrumentDate(instrumentDate);
		Log.log(5, "RPAction", methodName, "mode of delivery " + modeOfDelivery);
		paymentDetails.setModeOfDelivery(modeOfDelivery);
		Log.log(5, "RPAction", methodName, "instrument amount " + instrumentAmount);
		paymentDetails.setInstrumentAmount(instrumentAmount);
		Log.log(5, "RPAction", methodName, "drawn at bank " + drawnAtBank);
		paymentDetails.setDrawnAtBank(drawnAtBank);
		Log.log(5, "RPAction", methodName, "drawn at branch " + drawnAtBranch);
		paymentDetails.setDrawnAtBranch(drawnAtBranch);
		Log.log(5, "RPAction", methodName, "payable at " + payableAt);
		paymentDetails.setPayableAt(payableAt);
		Log.log(5, "RPAction", methodName, "acc num " + accNumber);
		paymentDetails.setCgtsiAccNumber(accNumber);

		Map appropriatedFlags = actionForm.getAppropriatedFlags();

		Map cgpans = actionForm.getCgpans();

		Set cgpansSet = cgpans.keySet();

		Map danCgpanDetails = actionForm.getDanPanDetails();

		paymentId = rpProcessor.allocateCGDAN(paymentDetails, appropriatedFlags, cgpans, danCgpanDetails, user);

		HttpSession session = request.getSession(false);

		request.setAttribute("message", "Payment Allocated Successfully.<BR>Payment ID : " + paymentId);

		Log.log(5, "RPAction", methodName, "Exited");

		return mapping.findForward("success");
	}

	public ActionForward gfallocatePaymentsold(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RPActionForm actionForm = (RPActionForm) form;
		RpProcessor rpProcessor = new RpProcessor();
		String paymentId = "";
		String methodName = "gfallocatePayments";

		Log.log(5, "RPAction", methodName, "Entered");

		User user = getUserInformation(request);

		PaymentDetails paymentDetails = new PaymentDetails();

		String allocationType = actionForm.getAllocationType();

		paymentDetails.setAllocationType1(allocationType);
		String modeOfPayment = actionForm.getModeOfPayment();
		String collectingBranch = actionForm.getCollectingBankBranch();
		java.util.Date paymentDate = actionForm.getPaymentDate();
		String instrumentNumber = actionForm.getInstrumentNo();
		java.util.Date instrumentDate = actionForm.getInstrumentDate();
		String modeOfDelivery = actionForm.getModeOfDelivery();
		double instrumentAmount = actionForm.getInstrumentAmount();
		String drawnAtBank = actionForm.getDrawnAtBank();
		String drawnAtBranch = actionForm.getDrawnAtBranch();
		String payableAt = actionForm.getPayableAt();
		String accNumber = actionForm.getAccountNumber();

		Log.log(5, "RPAction", methodName, "collecting bank " + actionForm.getCollectingBankName());
		paymentDetails.setCollectingBank(actionForm.getCollectingBankName());
		Log.log(5, "RPAction", methodName, "mode of payment " + modeOfPayment);
		paymentDetails.setModeOfPayment(modeOfPayment);
		Log.log(5, "RPAction", methodName, "collecting branch " + collectingBranch);
		paymentDetails.setCollectingBankBranch(collectingBranch);
		Log.log(5, "RPAction", methodName, "payment date " + paymentDate);
		paymentDetails.setPaymentDate(paymentDate);
		Log.log(5, "RPAction", methodName, "instrument number " + instrumentNumber);
		paymentDetails.setInstrumentNo(instrumentNumber);
		Log.log(5, "RPAction", methodName, "instrument date " + instrumentDate);
		paymentDetails.setInstrumentDate(instrumentDate);
		Log.log(5, "RPAction", methodName, "mode of delivery " + modeOfDelivery);
		paymentDetails.setModeOfDelivery(modeOfDelivery);
		Log.log(5, "RPAction", methodName, "instrument amount " + instrumentAmount);
		paymentDetails.setInstrumentAmount(instrumentAmount);
		Log.log(5, "RPAction", methodName, "drawn at bank " + drawnAtBank);
		paymentDetails.setDrawnAtBank(drawnAtBank);
		Log.log(5, "RPAction", methodName, "drawn at branch " + drawnAtBranch);
		paymentDetails.setDrawnAtBranch(drawnAtBranch);
		Log.log(5, "RPAction", methodName, "payable at " + payableAt);
		paymentDetails.setPayableAt(payableAt);
		Log.log(5, "RPAction", methodName, "acc num " + accNumber);
		paymentDetails.setCgtsiAccNumber(accNumber);

		Map allocationFlags = actionForm.getAllocatedFlags();

		ArrayList danSummaries = actionForm.getDanSummaries();

		Map cgpans = actionForm.getCgpans();

		Set cgpansSet = cgpans.keySet();

		Map danCgpanDetails = actionForm.getDanPanDetails();

		Map notAllocatedReasons = actionForm.getNotAllocatedReasons();

		for (int i = 0; i < danSummaries.size(); i++) {
			DANSummary danSummary = (DANSummary) danSummaries.get(i);

			String danId = danSummary.getDanId();
			Log.log(5, "RPAction", methodName, "danId " + danId);
			String shiftDanId = danId.replace('.', '_');

			Log.log(5, "RPAction", methodName, "contains " + danCgpanDetails.containsKey(danId));
			if (danCgpanDetails.containsKey(danId)) {
				ArrayList panAllocationDetails = (ArrayList) danCgpanDetails.get(danId);

				if (panAllocationDetails == null) {
					Log.log(5, "RPAction", methodName, "CGPAN details are not available. get them.");
					ArrayList totalList = rpProcessor.displayCGPANs(danId);
					panAllocationDetails = (ArrayList) totalList.get(0);
				}
				for (int j = 0; j < panAllocationDetails.size(); j++) {
					AllocationDetail allocationDetail = (AllocationDetail) panAllocationDetails.get(j);
					Log.log(5, "RPActionForm", "validate", " cgpan from danpandetails " + allocationDetail.getCgpan());
					if (allocationDetail.getAllocatedFlag().equals("N")) {
						Log.log(5, "RPActionForm", "validate", " not allocated ");
						String reasons = (String) notAllocatedReasons
								.get(shiftDanId + "-" + allocationDetail.getCgpan());
						Log.log(5, "RPActionForm", "validate", " reason for not allocated " + reasons);
						allocationDetail.setNotAllocatedReason(reasons);
					} else {
						allocationDetail.setNotAllocatedReason("");
					}
					panAllocationDetails.set(j, allocationDetail);
				}
				danCgpanDetails.put(danId, panAllocationDetails);
			}

		}

		request.setAttribute("message", "Payment Allocated Successfully.<BR>Payment ID : " + paymentId);

		Log.log(5, "RPAction", methodName, "Exited");

		return mapping.findForward("success");
	}

	public ActionForward gfallocatePaymentsNew(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RPActionForm actionForm = (RPActionForm) form;
		RpProcessor rpProcessor = new RpProcessor();
		String paymentId = "";
		String methodName = "gfallocatePaymentsNew";

		Log.log(5, "RPAction", methodName, "Entered");

		User user = getUserInformation(request);

		PaymentDetails paymentDetails = new PaymentDetails();

		String allocationType = actionForm.getAllocationType();

		paymentDetails.setAllocationType1(allocationType);

		String modeOfPayment = actionForm.getModeOfPayment();
		String collectingBank = actionForm.getCollectingBank();
		String collectingBranch = actionForm.getCollectingBankBranch();
		String accountName = actionForm.getAccountName();
		String accNumber = actionForm.getAccountNumber();
		String ifscCode = actionForm.getIfscCode();
		java.util.Date paymentDate = actionForm.getPaymentDate();
		double allocatedAmt = actionForm.getInstrumentAmount();
		java.util.Date instrumentDate = actionForm.getPaymentDate();

		String instrumentNumber = actionForm.getInstrumentNo();

		String modeOfDelivery = actionForm.getModeOfDelivery();

		String drawnAtBank = actionForm.getDrawnAtBank();
		String drawnAtBranch = actionForm.getDrawnAtBranch();
		String payableAt = actionForm.getPayableAt();

		Log.log(5, "RPAction", methodName, "collecting bank " + actionForm.getCollectingBank());
		paymentDetails.setCollectingBank(actionForm.getCollectingBank());

		Log.log(5, "RPAction", methodName, "mode of payment " + modeOfPayment);
		paymentDetails.setModeOfPayment(modeOfPayment);

		Log.log(5, "RPAction", methodName, "collecting branch " + collectingBranch);
		paymentDetails.setCollectingBankBranch(collectingBranch);

		Log.log(5, "RPAction", methodName, "payment date " + paymentDate);
		paymentDetails.setPaymentDate(paymentDate);

		Log.log(5, "RPAction", methodName, "instrument number " + instrumentNumber);
		paymentDetails.setInstrumentNo(instrumentNumber);
		Log.log(5, "RPAction", methodName, "instrument date " + instrumentDate);
		paymentDetails.setInstrumentDate(instrumentDate);
		Log.log(5, "RPAction", methodName, "mode of delivery " + modeOfDelivery);
		paymentDetails.setModeOfDelivery(modeOfDelivery);
		Log.log(5, "RPAction", methodName, "instrument amount " + allocatedAmt);
		paymentDetails.setInstrumentAmount(allocatedAmt);
		Log.log(5, "RPAction", methodName, "drawn at bank " + drawnAtBank);
		paymentDetails.setDrawnAtBank(drawnAtBank);
		Log.log(5, "RPAction", methodName, "drawn at branch " + drawnAtBranch);
		paymentDetails.setDrawnAtBranch(drawnAtBranch);
		Log.log(5, "RPAction", methodName, "payable at " + payableAt);
		paymentDetails.setPayableAt(payableAt);
		Log.log(5, "RPAction", methodName, "acc num " + accNumber);
		paymentDetails.setCgtsiAccNumber(accNumber);

		Map appropriatedFlags = actionForm.getAppropriatedFlags();

		Map cgpans = actionForm.getCgpans();

		Set cgpansSet = cgpans.keySet();

		Map danCgpanDetails = actionForm.getDanPanDetails();

		Map notAllocatedReasons = actionForm.getNotAllocatedReasons();
		ArrayList danSummaries = actionForm.getDanSummaries();
		for (int i = 0; i < danSummaries.size(); i++) {
			DANSummary danSummary = (DANSummary) danSummaries.get(i);

			String danId = danSummary.getDanId();
			Log.log(5, "RPAction", methodName, "danId " + danId);
			String shiftDanId = danId.replace('.', '_');

			Log.log(5, "RPAction", methodName, "contains " + danCgpanDetails.containsKey(danId));
			if (danCgpanDetails.containsKey(danId)) {
				ArrayList panAllocationDetails = (ArrayList) danCgpanDetails.get(danId);

				if (panAllocationDetails == null) {
					Log.log(5, "RPAction", methodName, "CGPAN details are not available. get them.");
					ArrayList totalList = rpProcessor.displayCGPANs(danId);
					panAllocationDetails = (ArrayList) totalList.get(0);
				}
				for (int j = 0; j < panAllocationDetails.size(); j++) {
					AllocationDetail allocationDetail = (AllocationDetail) panAllocationDetails.get(j);
					Log.log(5, "RPActionForm", "validate", " cgpan from danpandetails " + allocationDetail.getCgpan());
					if (allocationDetail.getAllocatedFlag().equals("N")) {
						Log.log(5, "RPActionForm", "validate", " not allocated ");
						String reasons = (String) notAllocatedReasons
								.get(shiftDanId + "-" + allocationDetail.getCgpan());
						Log.log(5, "RPActionForm", "validate", " reason for not allocated " + reasons);
						allocationDetail.setNotAllocatedReason(reasons);
					} else {
						allocationDetail.setNotAllocatedReason("");
					}
					panAllocationDetails.set(j, allocationDetail);
				}
				danCgpanDetails.put(danId, panAllocationDetails);
			}

		}

		paymentId = rpProcessor.allocateNEFTCGDAN(paymentDetails, appropriatedFlags, cgpans, danCgpanDetails, user);

		request.setAttribute("message",
				"Payment Allocated Successfully.<BR>Payment ID : " + paymentId + " of Rs." + allocatedAmt);

		Log.log(5, "RPAction", methodName, "Exited");

		return mapping.findForward("success");
	}

	public ActionForward gfallocatePaymentsNewOld(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RPActionForm actionForm = (RPActionForm) form;
		RpProcessor rpProcessor = new RpProcessor();
		String paymentId = "";
		String methodName = "gfallocatePaymentsNew";

		Log.log(5, "RPAction", methodName, "Entered");

		User user = getUserInformation(request);

		PaymentDetails paymentDetails = new PaymentDetails();

		String allocationType = actionForm.getAllocationType();

		paymentDetails.setAllocationType1(allocationType);

		String modeOfPayment = actionForm.getModeOfPayment();
		String collectingBank = actionForm.getCollectingBank();
		String collectingBranch = actionForm.getCollectingBankBranch();
		String accountName = actionForm.getAccountName();
		String accNumber = actionForm.getAccountNumber();
		String ifscCode = actionForm.getIfscCode();
		java.util.Date paymentDate = actionForm.getPaymentDate();
		double allocatedAmt = actionForm.getInstrumentAmount();
		java.util.Date instrumentDate = actionForm.getPaymentDate();

		String instrumentNumber = actionForm.getInstrumentNo();

		String modeOfDelivery = actionForm.getModeOfDelivery();

		String drawnAtBank = actionForm.getDrawnAtBank();
		String drawnAtBranch = actionForm.getDrawnAtBranch();
		String payableAt = actionForm.getPayableAt();

		Log.log(5, "RPAction", methodName, "collecting bank " + actionForm.getCollectingBank());
		paymentDetails.setCollectingBank(actionForm.getCollectingBank());

		Log.log(5, "RPAction", methodName, "mode of payment " + modeOfPayment);
		paymentDetails.setModeOfPayment(modeOfPayment);

		Log.log(5, "RPAction", methodName, "collecting branch " + collectingBranch);
		paymentDetails.setCollectingBankBranch(collectingBranch);

		Log.log(5, "RPAction", methodName, "payment date " + paymentDate);
		paymentDetails.setPaymentDate(paymentDate);

		Log.log(5, "RPAction", methodName, "instrument number " + instrumentNumber);
		paymentDetails.setInstrumentNo(instrumentNumber);
		Log.log(5, "RPAction", methodName, "instrument date " + instrumentDate);
		paymentDetails.setInstrumentDate(instrumentDate);
		Log.log(5, "RPAction", methodName, "mode of delivery " + modeOfDelivery);
		paymentDetails.setModeOfDelivery(modeOfDelivery);
		Log.log(5, "RPAction", methodName, "instrument amount " + allocatedAmt);
		paymentDetails.setInstrumentAmount(allocatedAmt);
		Log.log(5, "RPAction", methodName, "drawn at bank " + drawnAtBank);
		paymentDetails.setDrawnAtBank(drawnAtBank);
		Log.log(5, "RPAction", methodName, "drawn at branch " + drawnAtBranch);
		paymentDetails.setDrawnAtBranch(drawnAtBranch);
		Log.log(5, "RPAction", methodName, "payable at " + payableAt);
		paymentDetails.setPayableAt(payableAt);
		Log.log(5, "RPAction", methodName, "acc num " + accNumber);
		paymentDetails.setCgtsiAccNumber(accNumber);

		Map allocationFlags = actionForm.getAllocatedFlags();
		ArrayList danSummaries = actionForm.getDanSummaries();

		Map cgpans = actionForm.getCgpans();

		Set cgpansSet = cgpans.keySet();

		Map danCgpanDetails = actionForm.getDanPanDetails();

		Map notAllocatedReasons = actionForm.getNotAllocatedReasons();

		for (int i = 0; i < danSummaries.size(); i++) {
			DANSummary danSummary = (DANSummary) danSummaries.get(i);

			String danId = danSummary.getDanId();
			Log.log(5, "RPAction", methodName, "danId " + danId);
			String shiftDanId = danId.replace('.', '_');

			Log.log(5, "RPAction", methodName, "contains " + danCgpanDetails.containsKey(danId));
			if (danCgpanDetails.containsKey(danId)) {
				ArrayList panAllocationDetails = (ArrayList) danCgpanDetails.get(danId);

				if (panAllocationDetails == null) {
					Log.log(5, "RPAction", methodName, "CGPAN details are not available. get them.");
					ArrayList totalList = rpProcessor.displayCGPANs(danId);
					panAllocationDetails = (ArrayList) totalList.get(0);
				}
				for (int j = 0; j < panAllocationDetails.size(); j++) {
					AllocationDetail allocationDetail = (AllocationDetail) panAllocationDetails.get(j);
					Log.log(5, "RPActionForm", "validate", " cgpan from danpandetails " + allocationDetail.getCgpan());
					if (allocationDetail.getAllocatedFlag().equals("N")) {
						Log.log(5, "RPActionForm", "validate", " not allocated ");
						String reasons = (String) notAllocatedReasons
								.get(shiftDanId + "-" + allocationDetail.getCgpan());
						Log.log(5, "RPActionForm", "validate", " reason for not allocated " + reasons);
						allocationDetail.setNotAllocatedReason(reasons);
					} else {
						allocationDetail.setNotAllocatedReason("");
					}
					panAllocationDetails.set(j, allocationDetail);
				}
				danCgpanDetails.put(danId, panAllocationDetails);
			}

		}
		Map appropriatedFlags = actionForm.getAppropriatedFlags();
		// paymentId = rpProcessor.allocateNEFTCGDAN(paymentDetails,
		// danSummaries, allocationFlags, cgpans, danCgpanDetails, user);

		request.setAttribute("message",
				"Payment Allocated Successfully.<BR>Payment ID : " + paymentId + " of Rs." + allocatedAmt);

		Log.log(5, "RPAction", methodName, "Exited");

		return mapping.findForward("success");
	}

	public ActionForward allocatePayments(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RPActionForm actionForm = (RPActionForm) form;
		RpProcessor rpProcessor = new RpProcessor();
		String paymentId = "";
		String methodName = "allocatePayments";

		Log.log(5, "RPAction", methodName, "Entered");

		User user = getUserInformation(request);

		PaymentDetails paymentDetails = new PaymentDetails();

		String allocationType3 = actionForm.getAllocationType();

		paymentDetails.setAllocationType1(allocationType3);
		String modeOfPayment = actionForm.getModeOfPayment();
		String collectingBranch = actionForm.getCollectingBankBranch();
		java.util.Date paymentDate = actionForm.getPaymentDate();
		String instrumentNumber = actionForm.getInstrumentNo();
		java.util.Date instrumentDate = actionForm.getInstrumentDate();
		String modeOfDelivery = actionForm.getModeOfDelivery();
		double instrumentAmount = actionForm.getInstrumentAmount();
		String drawnAtBank = actionForm.getDrawnAtBank();
		String drawnAtBranch = actionForm.getDrawnAtBranch();
		String payableAt = actionForm.getPayableAt();
		String accNumber = actionForm.getAccountNumber();

		Log.log(5, "RPAction", methodName, "collecting bank " + actionForm.getCollectingBankName());
		paymentDetails.setCollectingBank(actionForm.getCollectingBankName());
		Log.log(5, "RPAction", methodName, "mode of payment " + modeOfPayment);
		paymentDetails.setModeOfPayment(modeOfPayment);
		Log.log(5, "RPAction", methodName, "collecting branch " + collectingBranch);
		paymentDetails.setCollectingBankBranch(collectingBranch);
		Log.log(5, "RPAction", methodName, "payment date " + paymentDate);
		paymentDetails.setPaymentDate(paymentDate);
		Log.log(5, "RPAction", methodName, "instrument number " + instrumentNumber);
		paymentDetails.setInstrumentNo(instrumentNumber);
		Log.log(5, "RPAction", methodName, "instrument date " + instrumentDate);
		paymentDetails.setInstrumentDate(instrumentDate);
		Log.log(5, "RPAction", methodName, "mode of delivery " + modeOfDelivery);
		paymentDetails.setModeOfDelivery(modeOfDelivery);
		Log.log(5, "RPAction", methodName, "instrument amount " + instrumentAmount);
		paymentDetails.setInstrumentAmount(instrumentAmount);
		Log.log(5, "RPAction", methodName, "drawn at bank " + drawnAtBank);
		paymentDetails.setDrawnAtBank(drawnAtBank);
		Log.log(5, "RPAction", methodName, "drawn at branch " + drawnAtBranch);
		paymentDetails.setDrawnAtBranch(drawnAtBranch);
		Log.log(5, "RPAction", methodName, "payable at " + payableAt);
		paymentDetails.setPayableAt(payableAt);
		Log.log(5, "RPAction", methodName, "acc num " + accNumber);
		paymentDetails.setCgtsiAccNumber(accNumber);

		Map allocationFlags = actionForm.getAllocatedFlags();

		ArrayList danSummaries = actionForm.getDanSummaries();

		Map cgpans = actionForm.getCgpans();

		Set cgpansSet = cgpans.keySet();

		Map danCgpanDetails = actionForm.getDanPanDetails();

		Map notAllocatedReasons = actionForm.getNotAllocatedReasons();

		for (int i = 0; i < danSummaries.size(); i++) {
			DANSummary danSummary = (DANSummary) danSummaries.get(i);

			String danId = danSummary.getDanId();
			Log.log(5, "RPAction", methodName, "danId " + danId);
			String shiftDanId = danId.replace('.', '_');

			Log.log(5, "RPAction", methodName, "contains " + danCgpanDetails.containsKey(danId));
			if (danCgpanDetails.containsKey(danId)) {
				ArrayList panAllocationDetails = (ArrayList) danCgpanDetails.get(danId);

				if (panAllocationDetails == null) {
					Log.log(5, "RPAction", methodName, "CGPAN details are not available. get them.");
					ArrayList totalList = rpProcessor.displayCGPANs(danId);
					panAllocationDetails = (ArrayList) totalList.get(0);
				}
				for (int j = 0; j < panAllocationDetails.size(); j++) {
					AllocationDetail allocationDetail = (AllocationDetail) panAllocationDetails.get(j);
					Log.log(5, "RPActionForm", "validate", " cgpan from danpandetails " + allocationDetail.getCgpan());
					if (allocationDetail.getAllocatedFlag().equals("N")) {
						Log.log(5, "RPActionForm", "validate", " not allocated ");
						String reasons = (String) notAllocatedReasons
								.get(shiftDanId + "-" + allocationDetail.getCgpan());
						Log.log(5, "RPActionForm", "validate", " reason for not allocated " + reasons);
						allocationDetail.setNotAllocatedReason(reasons);
					} else {
						allocationDetail.setNotAllocatedReason("");
					}
					panAllocationDetails.set(j, allocationDetail);
				}
				danCgpanDetails.put(danId, panAllocationDetails);
			}

		}

		paymentId = rpProcessor.allocateDAN(paymentDetails, danSummaries, allocationFlags, cgpans, danCgpanDetails,
				user);

		request.setAttribute("message", "Payment Allocated Successfully.<BR>Payment ID : " + paymentId);

		Log.log(5, "RPAction", methodName, "Exited");

		return mapping.findForward("success");
	}

	public ActionForward appropriateallocatePayments(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RPActionForm actionForm = (RPActionForm) form;
		RpProcessor rpProcessor = new RpProcessor();
		String paymentId = "";
		String methodName = "appropriateallocatePayments";

		Log.log(5, "RPAction", methodName, "Entered");

		User user = getUserInformation(request);

		PaymentDetails paymentDetails = new PaymentDetails();

		String modeOfPayment = actionForm.getModeOfPayment();
		String collectingBranch = actionForm.getCollectingBankBranch();
		java.util.Date paymentDate = actionForm.getPaymentDate();
		String instrumentNumber = actionForm.getInstrumentNo();
		java.util.Date instrumentDate = actionForm.getInstrumentDate();
		String modeOfDelivery = actionForm.getModeOfDelivery();
		double instrumentAmount = actionForm.getInstrumentAmount();
		String drawnAtBank = actionForm.getDrawnAtBank();
		String drawnAtBranch = actionForm.getDrawnAtBranch();
		String payableAt = actionForm.getPayableAt();
		String accNumber = actionForm.getAccountNumber();
		double realisationAmount = actionForm.getReceivedAmount();

		java.util.Date realisationDate = actionForm.getDateOfRealisation();

		String remarksforAppropriation = actionForm.getremarksforAppropriation();

		paymentDetails.setReceivedAmount(realisationAmount);
		paymentDetails.setRealisationDate(realisationDate);
		paymentDetails.setRemarksforAppropriation(remarksforAppropriation);
		Log.log(5, "RPAction", methodName, "collecting bank " + actionForm.getCollectingBankName());
		paymentDetails.setCollectingBank(actionForm.getCollectingBankName());
		Log.log(5, "RPAction", methodName, "mode of payment " + modeOfPayment);
		paymentDetails.setModeOfPayment(modeOfPayment);
		Log.log(5, "RPAction", methodName, "collecting branch " + collectingBranch);
		paymentDetails.setCollectingBankBranch(collectingBranch);
		Log.log(5, "RPAction", methodName, "payment date " + paymentDate);
		paymentDetails.setPaymentDate(paymentDate);
		Log.log(5, "RPAction", methodName, "instrument number " + instrumentNumber);
		paymentDetails.setInstrumentNo(instrumentNumber);
		Log.log(5, "RPAction", methodName, "instrument date " + instrumentDate);
		paymentDetails.setInstrumentDate(instrumentDate);
		Log.log(5, "RPAction", methodName, "mode of delivery " + modeOfDelivery);
		paymentDetails.setModeOfDelivery(modeOfDelivery);
		Log.log(5, "RPAction", methodName, "instrument amount " + instrumentAmount);
		paymentDetails.setInstrumentAmount(instrumentAmount);
		Log.log(5, "RPAction", methodName, "drawn at bank " + drawnAtBank);
		paymentDetails.setDrawnAtBank(drawnAtBank);
		Log.log(5, "RPAction", methodName, "drawn at branch " + drawnAtBranch);
		paymentDetails.setDrawnAtBranch(drawnAtBranch);
		Log.log(5, "RPAction", methodName, "payable at " + payableAt);
		paymentDetails.setPayableAt(payableAt);
		Log.log(5, "RPAction", methodName, "acc num " + accNumber);
		paymentDetails.setCgtsiAccNumber(accNumber);

		Map allocationFlags = actionForm.getAllocatedFlags();

		ArrayList danSummaries = actionForm.getDanSummaries();

		Map cgpans = actionForm.getCgpans();

		Set cgpansSet = cgpans.keySet();

		Map danCgpanDetails = actionForm.getDanPanDetails();

		Map notAllocatedReasons = actionForm.getNotAllocatedReasons();

		for (int i = 0; i < danSummaries.size(); i++) {
			DANSummary danSummary = (DANSummary) danSummaries.get(i);

			String danId = danSummary.getDanId();
			Log.log(5, "RPAction", methodName, "danId " + danId);
			String shiftDanId = danId.replace('.', '_');

			Log.log(5, "RPAction", methodName, "contains " + danCgpanDetails.containsKey(danId));
			if (danCgpanDetails.containsKey(danId)) {
				ArrayList panAllocationDetails = (ArrayList) danCgpanDetails.get(danId);
				if (panAllocationDetails == null) {
					Log.log(5, "RPAction", methodName, "CGPAN details are not available. get them.");
					ArrayList totalList = rpProcessor.displaySFCGPANs(danId);
					panAllocationDetails = (ArrayList) totalList.get(0);
				}
				for (int j = 0; j < panAllocationDetails.size(); j++) {
					AllocationDetail allocationDetail = (AllocationDetail) panAllocationDetails.get(j);
					Log.log(5, "RPActionForm", "validate", " cgpan from danpandetails " + allocationDetail.getCgpan());
					if (allocationDetail.getAllocatedFlag().equals("N")) {
						Log.log(5, "RPActionForm", "validate", " not allocated ");
						String reasons = (String) notAllocatedReasons
								.get(shiftDanId + "-" + allocationDetail.getCgpan());
						Log.log(5, "RPActionForm", "validate", " reason for not allocated " + reasons);
						allocationDetail.setNotAllocatedReason(reasons);
					} else {
						allocationDetail.setNotAllocatedReason("");
					}
					panAllocationDetails.set(j, allocationDetail);
				}
				danCgpanDetails.put(danId, panAllocationDetails);
			}

		}

		paymentDetails.setRealisationDate(actionForm.getDateOfRealisation());

		paymentId = rpProcessor.appropriateallocateDAN(paymentDetails, danSummaries, allocationFlags, cgpans,
				danCgpanDetails, user);

		request.setAttribute("message", "Payment Allocated & Appropriated Successfully.<BR>Payment ID : " + paymentId);

		Log.log(5, "RPAction", methodName, "Exited");

		return mapping.findForward("success");
	}

	public ActionForward submitASFDANPayments(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RPActionForm actionForm = (RPActionForm) form;
		double totalAmount = 0.0D;

		Map danIds = actionForm.getDanIds();
		Map allocatedFlags = actionForm.getAllocatedFlags();

		Map cgpans = actionForm.getCgpans();

		int allocatedcount = 0;
		int testallocatecount = 0;

		Set danIdSet = danIds.keySet();

		Log.log(5, "RPAction", "submitASFDANPayments", "Checkbox size = " + allocatedFlags.size());

		Set cgpansSet = cgpans.keySet();
		Iterator cgpanIterator = cgpansSet.iterator();

		Log.log(5, "RPAction", "submitASFDANPayments", "Checkbox size = " + cgpans.size());
		while (cgpanIterator.hasNext()) {
			String key = (String) cgpanIterator.next();
			String value = (String) cgpans.get(key);

			Log.log(5, "RPAction", "submitASFDANPayments", "cgpan key = " + key);
			Log.log(5, "RPAction", "submitASFDANPayments", "cgpan value = " + value);
		}
		cgpanIterator = cgpansSet.iterator();
		ArrayList danSummaries = actionForm.getDanSummaries();

		boolean isAllocated = false;

		for (int i = 0; i < danSummaries.size(); i++) {
			DANSummary danSummary = (DANSummary) danSummaries.get(i);
			String danId = danSummary.getDanId();

			Log.log(5, "RPAction", "submitASFDANPayments", "danId= " + danId);
			String danIdKey = danId.replace('.', '_');

			if ((allocatedFlags.containsKey(danIdKey))
					&& (request.getParameter("allocatedFlag(" + danIdKey + ")") != null)) {
				allocatedcount += 1;

				Log.log(5, "RPAction", "submitASFDANPayments", "danSummaries= " + danSummaries.size());
				isAllocated = true;

				totalAmount += danSummary.getAmountDue() - danSummary.getAmountPaid();

				Log.log(5, "RPAction", "submitASFDANPayments",
						"due amount " + (danSummary.getAmountDue() - danSummary.getAmountPaid()));
			} else {
				Log.log(5, "RPAction", "submitASFDANPayments", "CGPANS are allocated ");

				ArrayList panDetails = (ArrayList) actionForm.getDanPanDetail(danId);

				while (cgpanIterator.hasNext()) {
					String key = (String) cgpanIterator.next();
					String value = (String) cgpans.get(key);

					String cgpanPart = value.substring(value.indexOf("-") + 1, value.length());
					String tempKey = value.replace('.', '_');

					Log.log(5, "RPAction", "submitASFDANPayments", "key " + key);
					Log.log(5, "RPAction", "submitASFDANPayments", "value " + value);
					Log.log(5, "RPAction", "submitASFDANPayments", "tempKey " + tempKey);

					if ((value.startsWith(danId)) && (allocatedFlags.get(tempKey) != null)
							&& (((String) allocatedFlags.get(tempKey)).equals("Y"))) {
						testallocatecount += 1;
						cgpanPart = value.substring(value.indexOf("-") + 1, value.length());
						isAllocated = true;
						for (int j = 0; j < panDetails.size(); j++) {
							AllocationDetail allocation = (AllocationDetail) panDetails.get(j);
							Log.log(5, "RPAction", "submitASFDANPayments",
									"amount for CGPAN " + allocation.getCgpan() + "," + allocation.getAmountDue());

							if (cgpanPart.equals(allocation.getCgpan())) {
								totalAmount += allocation.getAmountDue();
								break;
							}
						}
					}
				}
				cgpanIterator = cgpansSet.iterator();
			}

		}

		if (!isAllocated) {
			throw new MissingDANDetailsException("No Allocation made.");
		}

		Registration registration = new Registration();

		Log.log(5, "RPAction", "submitASFDANPayments", "member id " + actionForm.getMemberId());

		CollectingBank collectingBank = registration.getCollectingBank("(" + actionForm.getMemberId() + ")");

		Log.log(5, "RPAction", "submitASFDANPayments", "collectingBank " + collectingBank);

		actionForm.setModeOfPayment("");
		actionForm.setPaymentDate(null);
		actionForm.setInstrumentNo("");
		actionForm.setInstrumentDate(null);
		actionForm.setDrawnAtBank("");
		actionForm.setDrawnAtBranch("");
		actionForm.setPayableAt("");

		IFProcessor ifProcessor = new IFProcessor();

		ArrayList instruments = ifProcessor.getInstrumentTypes("G");

		actionForm.setInstruments(instruments);

		actionForm.setCollectingBank(collectingBank.getCollectingBankId());
		actionForm.setCollectingBankName(collectingBank.getCollectingBankName());
		actionForm.setCollectingBankBranch(collectingBank.getBranchName());
		actionForm.setAccountNumber(collectingBank.getAccNo());

		actionForm.setInstrumentAmount(totalAmount);

		return mapping.findForward("asfpaymentDetails");
	}

	public ActionForward submitExpiredASFDANPayments(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RPActionForm actionForm = (RPActionForm) form;
		double totalAmount = 0.0D;

		Map danIds = actionForm.getDanIds();
		Map allocatedFlags = actionForm.getAllocatedFlags();

		Map cgpans = actionForm.getCgpans();

		int allocatedcount = 0;
		int testallocatecount = 0;

		Set danIdSet = danIds.keySet();

		Log.log(5, "RPAction", "submitExpiredASFDANPayments", "Checkbox size = " + allocatedFlags.size());

		Set cgpansSet = cgpans.keySet();
		Iterator cgpanIterator = cgpansSet.iterator();

		Log.log(5, "RPAction", "submitExpiredASFDANPayments", "Checkbox size = " + cgpans.size());
		while (cgpanIterator.hasNext()) {
			String key = (String) cgpanIterator.next();
			String value = (String) cgpans.get(key);

			Log.log(5, "RPAction", "submitExpiredASFDANPayments", "cgpan key = " + key);
			Log.log(5, "RPAction", "submitExpiredASFDANPayments", "cgpan value = " + value);
		}
		cgpanIterator = cgpansSet.iterator();
		ArrayList danSummaries = actionForm.getDanSummaries();

		boolean isAllocated = false;

		for (int i = 0; i < danSummaries.size(); i++) {
			DANSummary danSummary = (DANSummary) danSummaries.get(i);
			String danId = danSummary.getDanId();

			Log.log(5, "RPAction", "submitExpiredASFDANPayments", "danId= " + danId);
			String danIdKey = danId.replace('.', '_');

			if ((allocatedFlags.containsKey(danIdKey))
					&& (request.getParameter("allocatedFlag(" + danIdKey + ")") != null)) {
				allocatedcount += 1;

				Log.log(5, "RPAction", "submitExpiredASFDANPayments", "danSummaries= " + danSummaries.size());
				isAllocated = true;

				totalAmount += danSummary.getAmountDue() - danSummary.getAmountPaid();

				Log.log(5, "RPAction", "submitExpiredASFDANPayments",
						"due amount " + (danSummary.getAmountDue() - danSummary.getAmountPaid()));
			} else {
				Log.log(5, "RPAction", "submitExpiredASFDANPayments", "CGPANS are allocated ");

				ArrayList panDetails = (ArrayList) actionForm.getDanPanDetail(danId);

				while (cgpanIterator.hasNext()) {
					String key = (String) cgpanIterator.next();
					String value = (String) cgpans.get(key);

					String cgpanPart = value.substring(value.indexOf("-") + 1, value.length());
					String tempKey = value.replace('.', '_');

					Log.log(5, "RPAction", "submitExpiredASFDANPayments", "key " + key);
					Log.log(5, "RPAction", "submitExpiredASFDANPayments", "value " + value);
					Log.log(5, "RPAction", "submitExpiredASFDANPayments", "tempKey " + tempKey);

					if ((value.startsWith(danId)) && (allocatedFlags.get(tempKey) != null)
							&& (((String) allocatedFlags.get(tempKey)).equals("Y"))) {
						testallocatecount += 1;
						cgpanPart = value.substring(value.indexOf("-") + 1, value.length());
						isAllocated = true;
						for (int j = 0; j < panDetails.size(); j++) {
							AllocationDetail allocation = (AllocationDetail) panDetails.get(j);
							Log.log(5, "RPAction", "submitExpiredASFDANPayments",
									"amount for CGPAN " + allocation.getCgpan() + "," + allocation.getAmountDue());

							if (cgpanPart.equals(allocation.getCgpan())) {
								totalAmount += allocation.getAmountDue();
								break;
							}
						}
					}
				}
				cgpanIterator = cgpansSet.iterator();
			}

		}

		if (!isAllocated) {
			throw new MissingDANDetailsException("No Allocation made.");
		}

		Registration registration = new Registration();

		Log.log(5, "RPAction", "submitExpiredASFDANPayments", "member id " + actionForm.getMemberId());

		CollectingBank collectingBank = registration.getCollectingBank("(" + actionForm.getMemberId() + ")");

		Log.log(5, "RPAction", "submitExpiredASFDANPayments", "collectingBank " + collectingBank);

		actionForm.setModeOfPayment("");
		actionForm.setPaymentDate(null);
		actionForm.setInstrumentNo("");
		actionForm.setInstrumentDate(null);
		actionForm.setDrawnAtBank("");
		actionForm.setDrawnAtBranch("");
		actionForm.setPayableAt("");

		IFProcessor ifProcessor = new IFProcessor();

		ArrayList instruments = ifProcessor.getInstrumentTypes("G");

		actionForm.setInstruments(instruments);

		actionForm.setCollectingBank(collectingBank.getCollectingBankId());
		actionForm.setCollectingBankName(collectingBank.getCollectingBankName());
		actionForm.setCollectingBankBranch(collectingBank.getBranchName());
		actionForm.setAccountNumber(collectingBank.getAccNo());

		actionForm.setInstrumentAmount(totalAmount);

		return mapping.findForward("asfpaymentDetails");
	}

	public ActionForward submitGFDANPayments(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RPActionForm actionForm = (RPActionForm) form;
		double totalAmount = 0.0D;
		StringTokenizer tokenizer = null;
		double tot = 0.0D;
		Map appropriatedCases = actionForm.getAppropriatedFlags();
		if (appropriatedCases.size() < 1)
			throw new MissingDANDetailsException("Please select atleast one dan for allocation .");
		Set appropriatedCasesSet = appropriatedCases.keySet();
		Iterator appropriatedCasesIterator = appropriatedCasesSet.iterator();
		String token = null;
		String token1 = null;
		float total = 0.0F;
		float total2 = 0.0F;
		while (appropriatedCasesIterator.hasNext()) {
			String key = (String) appropriatedCasesIterator.next();
			for (tokenizer = new StringTokenizer(key, "#"); tokenizer.hasMoreTokens(); System.out.println(total2)) {
				token = tokenizer.nextToken();
				token1 = tokenizer.nextToken();
				total = Integer.parseInt(token1);
				total2 += total;
			}

		}
		Registration registration = new Registration();
		Log.log(5, "RPAction", "submitGFDANPayments",
				(new StringBuilder()).append("member id ").append(actionForm.getMemberId()).toString());
		CollectingBank collectingBank = registration.getCollectingBank(
				(new StringBuilder()).append("(").append(actionForm.getMemberId()).append(")").toString());
		Log.log(5, "RPAction", "submitGFDANPayments",
				(new StringBuilder()).append("collectingBank ").append(collectingBank).toString());
		actionForm.setModeOfPayment("");
		actionForm.setPaymentDate(null);
		actionForm.setInstrumentNo("");
		actionForm.setInstrumentDate(null);
		actionForm.setDrawnAtBank("");
		actionForm.setDrawnAtBranch("");
		actionForm.setPayableAt("");
		IFProcessor ifProcessor = new IFProcessor();
		ArrayList instruments = ifProcessor.getInstrumentTypes("G");
		actionForm.setInstruments(instruments);
		actionForm.setCollectingBank(collectingBank.getCollectingBankId());
		actionForm.setCollectingBankName(collectingBank.getCollectingBankName());
		actionForm.setCollectingBankBranch(collectingBank.getBranchName());
		actionForm.setAccountNumber(collectingBank.getAccNo());
		actionForm.setInstrumentAmount(total2);
		return mapping.findForward("gfpaymentDetails");
	}

	// rajuk
	public ActionForward submitGFDANPaymentsOnlione(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RPActionForm actionForm = (RPActionForm) form;
		double totalAmount = 0.0D;
		StringTokenizer tokenizer = null;
		double tot = 0.0D;
		Map appropriatedCases = actionForm.getAppropriatedFlags();
		if (appropriatedCases.size() < 1)
			throw new MissingDANDetailsException("Please select atleast one dan for allocation .");
		Set appropriatedCasesSet = appropriatedCases.keySet();
		Iterator appropriatedCasesIterator = appropriatedCasesSet.iterator();
		String token = null;
		String token1 = null;
		float total = 0.0F;
		float total2 = 0.0F;
		while (appropriatedCasesIterator.hasNext()) {
			String key = (String) appropriatedCasesIterator.next();
			for (tokenizer = new StringTokenizer(key, "#"); tokenizer.hasMoreTokens(); System.out.println(total2)) {
				token = tokenizer.nextToken();
				token1 = tokenizer.nextToken();
				total = Integer.parseInt(token1);
				total2 += total;
			}

		}
		Registration registration = new Registration();
		Log.log(5, "RPAction", "submitGFDANPayments",
				(new StringBuilder()).append("member id ").append(actionForm.getMemberId()).toString());
		CollectingBank collectingBank = registration.getCollectingBank(
				(new StringBuilder()).append("(").append(actionForm.getMemberId()).append(")").toString());
		Log.log(5, "RPAction", "submitGFDANPayments",
				(new StringBuilder()).append("collectingBank ").append(collectingBank).toString());
		actionForm.setModeOfPayment("");
		actionForm.setPaymentDate(null);
		actionForm.setInstrumentNo("");
		actionForm.setInstrumentDate(null);
		actionForm.setDrawnAtBank("");
		actionForm.setDrawnAtBranch("");
		actionForm.setPayableAt("");
		IFProcessor ifProcessor = new IFProcessor();
		ArrayList instruments = ifProcessor.getInstrumentTypes("G");
		actionForm.setInstruments(instruments);
		actionForm.setCollectingBank(collectingBank.getCollectingBankId());
		actionForm.setCollectingBankName(collectingBank.getCollectingBankName());
		actionForm.setCollectingBankBranch(collectingBank.getBranchName());
		actionForm.setAccountNumber(collectingBank.getAccNo());
		actionForm.setInstrumentAmount(total2);
		return mapping.findForward("gfpaymentDetails");
	}

	// end raju

	public ActionForward submitGFDANPaymentsold(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RPActionForm actionForm = (RPActionForm) form;
		double totalAmount = 0.0D;

		Map allocatedFlags = actionForm.getAllocatedFlags();

		Map cgpans = actionForm.getCgpans();

		int allocatedcount = 0;
		int testallocatecount = 0;

		Log.log(5, "RPAction", "submitGFDANPayments", "Checkbox size = " + allocatedFlags.size());

		Set cgpansSet = cgpans.keySet();
		Iterator cgpanIterator = cgpansSet.iterator();

		Log.log(5, "RPAction", "submitGFDANPayments", "Checkbox size = " + cgpans.size());
		while (cgpanIterator.hasNext()) {
			String key = (String) cgpanIterator.next();
			String value = (String) cgpans.get(key);

			Log.log(5, "RPAction", "submitGFDANPayments", "cgpan key = " + key);
			Log.log(5, "RPAction", "submitGFDANPayments", "cgpan value = " + value);
		}
		cgpanIterator = cgpansSet.iterator();
		ArrayList danSummaries = actionForm.getDanSummaries();

		boolean isAllocated = false;

		for (int i = 0; i < danSummaries.size(); i++) {
			DANSummary danSummary = (DANSummary) danSummaries.get(i);
			String danId = danSummary.getDanId();

			Log.log(5, "RPAction", "submitGFDANPayments", "danId= " + danId);
			String danIdKey = danId.replace('.', '_');

			if ((allocatedFlags.containsKey(danIdKey))
					&& (request.getParameter("allocatedFlag(" + danIdKey + ")") != null)) {
				allocatedcount += 1;

				Log.log(5, "RPAction", "submitGFDANPayments", "danSummaries= " + danSummaries.size());
				isAllocated = true;

				totalAmount += danSummary.getAmountDue() - danSummary.getAmountPaid();

				Log.log(5, "RPAction", "submitGFDANPayments",
						"due amount " + (danSummary.getAmountDue() - danSummary.getAmountPaid()));
			} else {
				Log.log(5, "RPAction", "submitGFDANPayments", "CGPANS are allocated ");

				ArrayList panDetails = (ArrayList) actionForm.getDanPanDetail(danId);

				while (cgpanIterator.hasNext()) {
					String key = (String) cgpanIterator.next();
					String value = (String) cgpans.get(key);

					String cgpanPart = value.substring(value.indexOf("-") + 1, value.length());
					String tempKey = value.replace('.', '_');

					Log.log(5, "RPAction", "submitGFDANPayments", "key " + key);
					Log.log(5, "RPAction", "submitGFDANPayments", "value " + value);
					Log.log(5, "RPAction", "submitGFDANPayments", "tempKey " + tempKey);

					if ((value.startsWith(danId)) && (allocatedFlags.get(tempKey) != null)
							&& (((String) allocatedFlags.get(tempKey)).equals("Y"))) {
						testallocatecount += 1;
						cgpanPart = value.substring(value.indexOf("-") + 1, value.length());
						isAllocated = true;
						for (int j = 0; j < panDetails.size(); j++) {
							AllocationDetail allocation = (AllocationDetail) panDetails.get(j);
							Log.log(5, "RPAction", "submitGFDANPayments",
									"amount for CGPAN " + allocation.getCgpan() + "," + allocation.getAmountDue());

							if (cgpanPart.equals(allocation.getCgpan())) {
								totalAmount += allocation.getAmountDue();
								break;
							}
						}
					}
				}
				cgpanIterator = cgpansSet.iterator();
			}

		}

		if (!isAllocated) {
			throw new MissingDANDetailsException("No Allocation made.");
		}

		Registration registration = new Registration();

		Log.log(5, "RPAction", "submitGFDANPayments", "member id " + actionForm.getMemberId());

		CollectingBank collectingBank = registration.getCollectingBank("(" + actionForm.getMemberId() + ")");

		Log.log(5, "RPAction", "submitGFDANPayments", "collectingBank " + collectingBank);

		actionForm.setModeOfPayment("");
		actionForm.setPaymentDate(null);
		actionForm.setInstrumentNo("");
		actionForm.setInstrumentDate(null);
		actionForm.setDrawnAtBank("");
		actionForm.setDrawnAtBranch("");
		actionForm.setPayableAt("");

		IFProcessor ifProcessor = new IFProcessor();

		ArrayList instruments = ifProcessor.getInstrumentTypes("G");

		actionForm.setInstruments(instruments);

		actionForm.setCollectingBank(collectingBank.getCollectingBankId());
		actionForm.setCollectingBankName(collectingBank.getCollectingBankName());
		actionForm.setCollectingBankBranch(collectingBank.getBranchName());
		actionForm.setAccountNumber(collectingBank.getAccNo());

		actionForm.setInstrumentAmount(totalAmount);

		return mapping.findForward("gfpaymentDetails");
	}

	public ActionForward submitNEFTGFDANPayments(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RPActionForm actionForm = (RPActionForm) form;
		StringTokenizer tokenizer = null;
		double tot = 0.0D;
		// Map allocatedFlags = actionForm.getAllocatedFlags();
		Map appropriatedCases = actionForm.getAppropriatedFlags();
		if (appropriatedCases.size() < 1) {
			throw new MissingDANDetailsException("Please select atleast one dan for allocation.");
		}
		Set appropriatedCasesSet = appropriatedCases.keySet();
		Iterator appropriatedCasesIterator = appropriatedCasesSet.iterator();
		String token = null;
		String token2 = null;
		float total = 0.0F;
		float total2 = 0.0F;
		while (appropriatedCasesIterator.hasNext()) {
			String key = (String) appropriatedCasesIterator.next();
			for (tokenizer = new StringTokenizer(key, "#"); tokenizer.hasMoreElements();) {
				token = tokenizer.nextToken();
				token2 = tokenizer.nextToken();
				total = Integer.parseInt(token2);
				total2 += total;
			}
		}
		Registration registration = new Registration();
		Log.log(5, "RPAction", "submitNEFTGFDANPayments", "member id " + actionForm.getMemberId());
		CollectingBank collectingBank = registration.getCollectingBank("(" + actionForm.getMemberId() + ")");
		Log.log(5, "RPAction", "submitNEFTGFDANPayments", "collectingBank " + collectingBank);
		actionForm.setModeOfPayment("");
		actionForm.setPaymentDate(null);
		actionForm.setInstrumentNo("");
		actionForm.setInstrumentDate(null);
		actionForm.setDrawnAtBank("");
		actionForm.setDrawnAtBranch("");
		actionForm.setPayableAt("");
		IFProcessor ifProcessor = new IFProcessor();
		actionForm.setCollectingBank("IDBI BANK LTD");
		actionForm.setCollectingBankBranch("CHEMBUR");
		actionForm.setAccountNumber("018102000014951");
		actionForm.setAccountName("Credit Guarantee Fund Trust for Micro And Small Enterprises");
		actionForm.setIfscCode("IBKL0000018");
		actionForm.setModeOfPayment("NEFT");
		actionForm.setInstrumentAmount(total2);
		return mapping.findForward("gfpaymentDetailsNew");
	}

	public ActionForward submitNEFTGFDANPaymentsOld(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RPActionForm actionForm = (RPActionForm) form;
		double totalAmount = 0.0D;

		Map danIds = actionForm.getDanIds();
		Map allocatedFlags = actionForm.getAllocatedFlags();

		Map cgpans = actionForm.getCgpans();

		int allocatedcount = 0;
		int testallocatecount = 0;

		Set danIdSet = danIds.keySet();

		Log.log(5, "RPAction", "submitNEFTGFDANPayments", "Checkbox size = " + allocatedFlags.size());

		Set cgpansSet = cgpans.keySet();
		Iterator cgpanIterator = cgpansSet.iterator();

		Log.log(5, "RPAction", "submitNEFTGFDANPayments", "Checkbox size = " + cgpans.size());
		while (cgpanIterator.hasNext()) {
			String key = (String) cgpanIterator.next();
			String value = (String) cgpans.get(key);

			Log.log(5, "RPAction", "submitNEFTGFDANPayments", "cgpan key = " + key);
			Log.log(5, "RPAction", "submitNEFTGFDANPayments", "cgpan value = " + value);
		}
		cgpanIterator = cgpansSet.iterator();
		ArrayList danSummaries = actionForm.getDanSummaries();

		boolean isAllocated = false;

		for (int i = 0; i < danSummaries.size(); i++) {
			DANSummary danSummary = (DANSummary) danSummaries.get(i);
			String danId = danSummary.getDanId();

			Log.log(5, "RPAction", "submitNEFTGFDANPayments", "danId= " + danId);
			String danIdKey = danId.replace('.', '_');

			if ((allocatedFlags.containsKey(danIdKey))
					&& (request.getParameter("allocatedFlag(" + danIdKey + ")") != null)) {
				allocatedcount += 1;

				Log.log(5, "RPAction", "submitNEFTGFDANPayments", "danSummaries= " + danSummaries.size());
				isAllocated = true;

				totalAmount += danSummary.getAmountDue() - danSummary.getAmountPaid();

				Log.log(5, "RPAction", "submitNEFTGFDANPayments",
						"due amount " + (danSummary.getAmountDue() - danSummary.getAmountPaid()));
			} else {
				Log.log(5, "RPAction", "submitNEFTGFDANPayments", "CGPANS are allocated ");

				ArrayList panDetails = (ArrayList) actionForm.getDanPanDetail(danId);

				while (cgpanIterator.hasNext()) {
					String key = (String) cgpanIterator.next();
					String value = (String) cgpans.get(key);

					String cgpanPart = value.substring(value.indexOf("-") + 1, value.length());
					String tempKey = value.replace('.', '_');

					Log.log(5, "RPAction", "submitNEFTGFDANPayments", "key " + key);
					Log.log(5, "RPAction", "submitNEFTGFDANPayments", "value " + value);
					Log.log(5, "RPAction", "submitNEFTGFDANPayments", "tempKey " + tempKey);

					if ((value.startsWith(danId)) && (allocatedFlags.get(tempKey) != null)
							&& (((String) allocatedFlags.get(tempKey)).equals("Y"))) {
						testallocatecount += 1;
						cgpanPart = value.substring(value.indexOf("-") + 1, value.length());
						isAllocated = true;
						for (int j = 0; j < panDetails.size(); j++) {
							AllocationDetail allocation = (AllocationDetail) panDetails.get(j);
							Log.log(5, "RPAction", "submitNEFTGFDANPayments",
									"amount for CGPAN " + allocation.getCgpan() + "," + allocation.getAmountDue());

							if (cgpanPart.equals(allocation.getCgpan())) {
								totalAmount += allocation.getAmountDue();
								break;
							}
						}
					}
				}
				cgpanIterator = cgpansSet.iterator();
			}

		}

		if (!isAllocated) {
			throw new MissingDANDetailsException("No Allocation made.");
		}

		Registration registration = new Registration();

		Log.log(5, "RPAction", "submitNEFTGFDANPayments", "member id " + actionForm.getMemberId());

		CollectingBank collectingBank = registration.getCollectingBank("(" + actionForm.getMemberId() + ")");

		Log.log(5, "RPAction", "submitNEFTGFDANPayments", "collectingBank " + collectingBank);

		actionForm.setModeOfPayment("");
		actionForm.setPaymentDate(null);
		actionForm.setInstrumentNo("");
		actionForm.setInstrumentDate(null);
		actionForm.setDrawnAtBank("");
		actionForm.setDrawnAtBranch("");
		actionForm.setPayableAt("");

		IFProcessor ifProcessor = new IFProcessor();

		actionForm.setCollectingBank("IDBI BANK LTD");
		actionForm.setCollectingBankBranch("CHEMBUR");
		actionForm.setAccountNumber("018102000014951");
		actionForm.setAccountName("Credit Guarantee Fund Trust for Micro And Small Enterprises");
		actionForm.setIfscCode("IBKL0000018");
		actionForm.setModeOfPayment("NEFT");

		actionForm.setInstrumentAmount(totalAmount);

		return mapping.findForward("gfpaymentDetailsNew");
	}

	public ActionForward submitDANPayments(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RPActionForm actionForm = (RPActionForm) form;
		double totalAmount = 0.0D;

		Map danIds = actionForm.getDanIds();
		Map allocatedFlags = actionForm.getAllocatedFlags();

		Map cgpans = actionForm.getCgpans();

		int allocatedcount = 0;
		int testallocatecount = 0;

		Set danIdSet = danIds.keySet();

		Log.log(5, "RPAction", "submitDANPayments", "Checkbox size = " + allocatedFlags.size());

		Set cgpansSet = cgpans.keySet();
		Iterator cgpanIterator = cgpansSet.iterator();

		Log.log(5, "RPAction", "submitDANPayments", "Checkbox size = " + cgpans.size());
		while (cgpanIterator.hasNext()) {
			String key = (String) cgpanIterator.next();
			String value = (String) cgpans.get(key);

			Log.log(5, "RPAction", "submitDANPayments", "cgpan key = " + key);
			Log.log(5, "RPAction", "submitDANPayments", "cgpan value = " + value);
		}
		cgpanIterator = cgpansSet.iterator();
		ArrayList danSummaries = actionForm.getDanSummaries();

		boolean isAllocated = false;

		for (int i = 0; i < danSummaries.size(); i++) {
			DANSummary danSummary = (DANSummary) danSummaries.get(i);
			String danId = danSummary.getDanId();

			Log.log(5, "RPAction", "submitDANPayments", "danId= " + danId);
			String danIdKey = danId.replace('.', '_');

			if ((allocatedFlags.containsKey(danIdKey))
					&& (request.getParameter("allocatedFlag(" + danIdKey + ")") != null)) {
				allocatedcount += 1;

				Log.log(5, "RPAction", "submitDANPayments", "danSummaries= " + danSummaries.size());
				isAllocated = true;

				totalAmount += danSummary.getAmountDue() - danSummary.getAmountPaid();

				Log.log(5, "RPAction", "submitDANPayments",
						"due amount " + (danSummary.getAmountDue() - danSummary.getAmountPaid()));
			} else {
				Log.log(5, "RPAction", "submitDANPayments", "CGPANS are allocated ");

				ArrayList panDetails = (ArrayList) actionForm.getDanPanDetail(danId);

				while (cgpanIterator.hasNext()) {
					String key = (String) cgpanIterator.next();
					String value = (String) cgpans.get(key);

					String cgpanPart = value.substring(value.indexOf("-") + 1, value.length());
					String tempKey = value.replace('.', '_');

					Log.log(5, "RPAction", "submitDANPayments", "key " + key);
					Log.log(5, "RPAction", "submitDANPayments", "value " + value);
					Log.log(5, "RPAction", "submitDANPayments", "tempKey " + tempKey);

					if ((value.startsWith(danId)) && (allocatedFlags.get(tempKey) != null)
							&& (((String) allocatedFlags.get(tempKey)).equals("Y"))) {
						testallocatecount += 1;
						cgpanPart = value.substring(value.indexOf("-") + 1, value.length());
						isAllocated = true;
						for (int j = 0; j < panDetails.size(); j++) {
							AllocationDetail allocation = (AllocationDetail) panDetails.get(j);
							Log.log(5, "RPAction", "submitDANPayments",
									"amount for CGPAN " + allocation.getCgpan() + "," + allocation.getAmountDue());

							if (cgpanPart.equals(allocation.getCgpan())) {
								totalAmount += allocation.getAmountDue();
								break;
							}
						}
					}
				}
				cgpanIterator = cgpansSet.iterator();
			}

		}

		if (!isAllocated) {
			throw new MissingDANDetailsException("No Allocation made.");
		}

		Registration registration = new Registration();

		Log.log(5, "RPAction", "submitDANPayments", "member id " + actionForm.getMemberId());

		CollectingBank collectingBank = registration.getCollectingBank("(" + actionForm.getMemberId() + ")");

		Log.log(5, "RPAction", "submitDANPayments", "collectingBank " + collectingBank);

		actionForm.setModeOfPayment("");
		actionForm.setPaymentDate(null);
		actionForm.setInstrumentNo("");
		actionForm.setInstrumentDate(null);
		actionForm.setDrawnAtBank("");
		actionForm.setDrawnAtBranch("");
		actionForm.setPayableAt("");

		IFProcessor ifProcessor = new IFProcessor();

		ArrayList instruments = ifProcessor.getInstrumentTypes("G");

		actionForm.setInstruments(instruments);

		actionForm.setCollectingBank(collectingBank.getCollectingBankId());
		actionForm.setCollectingBankName(collectingBank.getCollectingBankName());
		actionForm.setCollectingBankBranch(collectingBank.getBranchName());
		actionForm.setAccountNumber(collectingBank.getAccNo());

		actionForm.setInstrumentAmount(totalAmount);

		return mapping.findForward("paymentDetails");
	}

	public ActionForward submitSFDANPayments(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RPActionForm actionForm = (RPActionForm) form;
		double totalAmount = 0.0D;

		Map danIds = actionForm.getDanIds();
		Map allocatedFlags = actionForm.getAllocatedFlags();
		Map cgpans = actionForm.getCgpans();

		Set danIdSet = danIds.keySet();

		Log.log(5, "RPAction", "submitSFDANPayments", "Checkbox size = " + allocatedFlags.size());

		Set cgpansSet = cgpans.keySet();
		Iterator cgpanIterator = cgpansSet.iterator();

		Log.log(5, "RPAction", "submitSFDANPayments", "Checkbox size = " + cgpans.size());
		while (cgpanIterator.hasNext()) {
			String key = (String) cgpanIterator.next();
			String value = (String) cgpans.get(key);

			Log.log(5, "RPAction", "submitSFDANPayments", "cgpan key = " + key);
			Log.log(5, "RPAction", "submitSFDANPayments", "cgpan value = " + value);
		}
		cgpanIterator = cgpansSet.iterator();
		ArrayList danSummaries = actionForm.getDanSummaries();

		boolean isAllocated = false;

		for (int i = 0; i < danSummaries.size(); i++) {
			DANSummary danSummary = (DANSummary) danSummaries.get(i);
			String danId = danSummary.getDanId();

			Log.log(5, "RPAction", "submitSFDANPayments", "danId= " + danId);
			String danIdKey = danId.replace('.', '_');

			if ((allocatedFlags.containsKey(danIdKey))
					&& (request.getParameter("allocatedFlag(" + danIdKey + ")") != null)) {
				Log.log(5, "RPAction", "submitSFDANPayments", "danSummaries= " + danSummaries.size());
				isAllocated = true;

				totalAmount += danSummary.getAmountDue() - danSummary.getAmountPaid();

				Log.log(5, "RPAction", "submitSFDANPayments",
						"due amount " + (danSummary.getAmountDue() - danSummary.getAmountPaid()));
			} else {
				Log.log(5, "RPAction", "submitSFDANPayments", "CGPANS are allocated ");

				ArrayList panDetails = (ArrayList) actionForm.getDanPanDetail(danId);

				while (cgpanIterator.hasNext()) {
					String key = (String) cgpanIterator.next();
					String value = (String) cgpans.get(key);

					String cgpanPart = value.substring(value.indexOf("-") + 1, value.length());
					String tempKey = value.replace('.', '_');

					Log.log(5, "RPAction", "submitSFDANPayments", "key " + key);
					Log.log(5, "RPAction", "submitSFDANPayments", "value " + value);
					Log.log(5, "RPAction", "submitSFDANPayments", "tempKey " + tempKey);

					if ((value.startsWith(danId)) && (allocatedFlags.get(tempKey) != null)
							&& (((String) allocatedFlags.get(tempKey)).equals("Y"))) {
						cgpanPart = value.substring(value.indexOf("-") + 1, value.length());
						isAllocated = true;
						for (int j = 0; j < panDetails.size(); j++) {
							AllocationDetail allocation = (AllocationDetail) panDetails.get(j);
							Log.log(5, "RPAction", "submitSFDANPayments",
									"amount for CGPAN " + allocation.getCgpan() + "," + allocation.getAmountDue());

							if (cgpanPart.equals(allocation.getCgpan())) {
								totalAmount += allocation.getAmountDue();
								break;
							}
						}
					}
				}
				cgpanIterator = cgpansSet.iterator();
			}

		}

		if (!isAllocated) {
			throw new MissingDANDetailsException("No Allocation made.");
		}

		Registration registration = new Registration();

		Log.log(5, "RPAction", "submitSFDANPayments", "member id " + actionForm.getMemberId());

		CollectingBank collectingBank = registration.getCollectingBank("(" + actionForm.getMemberId() + ")");

		Log.log(5, "RPAction", "submitSFDANPayments", "collectingBank " + collectingBank);

		actionForm.setModeOfPayment("");
		actionForm.setPaymentDate(null);
		actionForm.setInstrumentNo("");
		actionForm.setInstrumentDate(null);
		actionForm.setDrawnAtBank("");
		actionForm.setDrawnAtBranch("");
		actionForm.setPayableAt("");

		IFProcessor ifProcessor = new IFProcessor();

		ArrayList instruments = ifProcessor.getInstrumentTypes("G");

		actionForm.setInstruments(instruments);

		actionForm.setCollectingBank(collectingBank.getCollectingBankId());
		actionForm.setCollectingBankName(collectingBank.getCollectingBankName());
		actionForm.setCollectingBankBranch(collectingBank.getBranchName());
		actionForm.setAccountNumber(collectingBank.getAccNo());

		actionForm.setInstrumentAmount(totalAmount);

		return mapping.findForward("sfpaymentDetails");
	}

	public ActionForward getPaymentsMade(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RpProcessor rpProcessor = new RpProcessor();

		ArrayList paymentDetails = rpProcessor.displayPaymentsReceived();

		RPActionForm actionForm = (RPActionForm) form;
		actionForm.getCgpans().clear();
		actionForm.setPaymentDetails(paymentDetails);

		return mapping.findForward("paymentsSummary");
	}

	public ActionForward getPaymentsMadeForGF(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RpProcessor rpProcessor = new RpProcessor();

		ArrayList paymentDetails = rpProcessor.displayPaymentsReceivedForGF();

		RPActionForm actionForm = (RPActionForm) form;
		actionForm.getCgpans().clear();
		actionForm.setPaymentDetails(paymentDetails);

		return mapping.findForward("paymentsSummary");
	}

	public ActionForward gfbatchappropriatePayments(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RpProcessor rpProcessor = new RpProcessor();

		ArrayList paymentDetails = rpProcessor.displayBatchPaymentsReceivedForGF();

		RPActionForm actionForm = (RPActionForm) form;
		actionForm.getCgpans().clear();
		actionForm.setPaymentDetails(paymentDetails);

		return mapping.findForward("paymentsSummary");
	}

	public ActionForward daywisegfbatchappropriation(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RPActionForm actionForm = (RPActionForm) form;

		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument24(date);
		BeanUtils.copyProperties(actionForm, generalReport);

		return mapping.findForward("inputDate");
	}

	public ActionForward daywiseddmarkedforDeposited(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RPActionForm actionForm = (RPActionForm) form;

		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		GeneralReport generalReport = new GeneralReport();
		generalReport.setDateOfTheDocument24(date);
		BeanUtils.copyProperties(actionForm, generalReport);

		return mapping.findForward("inputDate");
	}

	public ActionForward daywisegfbatchappropriatePayments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		RpProcessor rpProcessor = new RpProcessor();
		RPActionForm actionForm = (RPActionForm) form;
		java.util.Date dateofRealisation = actionForm.getDateOfTheDocument24();

		ArrayList paymentDetails = rpProcessor.daywiseBatchPaymentsReceivedForGF(dateofRealisation);

		actionForm.getCgpans().clear();
		actionForm.setPaymentDetails(paymentDetails);

		return mapping.findForward("paymentsSummary");
	}

	public ActionForward daywiseddmarkedforDepositedCases(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		RpProcessor rpProcessor = new RpProcessor();
		RPActionForm actionForm = (RPActionForm) form;
		java.util.Date inwardDate = actionForm.getDateOfTheDocument24();

		ArrayList paymentDetails = rpProcessor.daywiseBatchPaymentsInwardedForGF(inwardDate);

		actionForm.getCgpans().clear();
		actionForm.setPaymentDetails(paymentDetails);

		return mapping.findForward("paymentsSummary");
	}

	public ActionForward dayWiseddMarkedForDepositedDate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "dayWiseddMarkedForDepositedDate", "Entered");

		RPActionForm actionForm = (RPActionForm) form;
		RpProcessor rpProcessor = new RpProcessor();

		java.util.Date dateofDeposit = actionForm.getDateOfTheDocument24();

		User user = getUserInformation(request);
		String userId = user.getUserId();

		Log.log(4, "RPAction", "dayWiseddMarkedForDepositedDate", "Exited");

		return mapping.findForward("deposited");
	}

	public ActionForward aftergfbatchappropriatePayments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "aftergfbatchappropriatePayments", "Entered");

		RPActionForm actionForm = (RPActionForm) form;
		RpProcessor rpProcessor = new RpProcessor();

		User user = getUserInformation(request);
		String userId = user.getUserId();
		int appropriatedCount = 0;
		Map appropriatedCases = actionForm.getAppropriatedFlags();
		Set appropriatedCasesSet = appropriatedCases.keySet();
		Iterator appropriatedCasesIterator = appropriatedCasesSet.iterator();
		boolean appropriatedFlag = false;
		while (appropriatedCasesIterator.hasNext()) {
			String key = (String) appropriatedCasesIterator.next();
			String decision = (String) appropriatedCases.get(key);
			if (decision.equals("Y")) {
				appropriatedCount += rpProcessor.aftergfbatchappropriatePayments(key, userId);
				appropriatedFlag = true;
			}
		}
		if (!appropriatedFlag) {
			throw new MissingDANDetailsException("No Appropriation Made.");
		}
		System.out.println("Appropriated Count:" + appropriatedCount);
		actionForm.setPaymentDetails(null);
		appropriatedCases.clear();
		request.setAttribute("message", "No.of Appropriations done are: " + appropriatedCount);
		Log.log(4, "RPAction", "aftergfbatchappropriatePayments", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward aftergfdaywisebatchappropriatePayments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "aftergfdaywisebatchappropriatePayments", "Entered");

		RPActionForm actionForm = (RPActionForm) form;
		RpProcessor rpProcessor = new RpProcessor();

		java.util.Date dateofRealisation = actionForm.getDateOfTheDocument24();

		User user = getUserInformation(request);
		String userId = user.getUserId();
		int appropriatedCount = 0;
		Map appropriatedCases = actionForm.getAppropriatedFlags();

		Set appropriatedCasesSet = appropriatedCases.keySet();
		Iterator appropriatedCasesIterator = appropriatedCasesSet.iterator();
		boolean appropriatedFlag = false;
		while (appropriatedCasesIterator.hasNext()) {
			String key = (String) appropriatedCasesIterator.next();
			String decision = (String) appropriatedCases.get(key);
			if (decision.equals("Y")) {
				appropriatedCount += rpProcessor.aftergfdaywisebatchappropriatePayments(key, userId, dateofRealisation);
				appropriatedFlag = true;
			}
		}
		if (!appropriatedFlag) {
			throw new MissingDANDetailsException("No Appropriation Made.");
		}
		System.out.println("Appropriated Count:" + appropriatedCount);
		actionForm.setPaymentDetails(null);
		appropriatedCases.clear();
		request.setAttribute("message", "No.of Appropriations done are: " + appropriatedCount);
		Log.log(4, "RPAction", "aftergfdaywisebatchappropriatePayments", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward dayWiseddMarkedForDepositedSummary(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "dayWiseddMarkedForDepositedSummary", "Entered");

		RPActionForm actionForm = (RPActionForm) form;
		RpProcessor rpProcessor = new RpProcessor();

		java.util.Date dateofDeposit = actionForm.getDateOfTheDocument24();
		System.out.println("After Confirm date of Deposit:" + dateofDeposit);

		User user = getUserInformation(request);
		String userId = user.getUserId();

		int depositedCount = 0;
		StringTokenizer tokenizer = null;
		String instrumentNo = null;
		String inwardId = null;
		Map appropriatedCases = actionForm.getAppropriatedFlags();
		Set appropriatedCasesSet = appropriatedCases.keySet();
		Iterator appropriatedCasesIterator = appropriatedCasesSet.iterator();

		boolean appropriatedFlag = false;
		while (appropriatedCasesIterator.hasNext()) {
			String key = (String) appropriatedCasesIterator.next();
			String decision = (String) appropriatedCases.get(key);
			if (decision.equals("Y")) {
				String token = null;
				tokenizer = new StringTokenizer(key, "#");
				boolean isInstrumentNoRead = false;
				boolean isInwardIdRead = false;
				while (tokenizer.hasMoreTokens()) {
					token = tokenizer.nextToken();

					if (!isInwardIdRead) {
						if (!isInstrumentNoRead) {
							instrumentNo = token;

							isInstrumentNoRead = true;
						} else {
							inwardId = token;

							isInwardIdRead = true;
						}
					}
				}
				depositedCount += rpProcessor.dayWiseddMarkedForDepositedSummary(inwardId, instrumentNo, userId,
						dateofDeposit);
				appropriatedFlag = true;
			}
		}

		if (!appropriatedFlag) {
			throw new MissingDANDetailsException("No Selection Made.");
		}
		System.out.println("No.of dds Marked for Deposited:" + depositedCount);
		actionForm.setPaymentDetails(null);
		appropriatedCases.clear();
		request.setAttribute("message", "No.of dds Marked for Deposited are: " + depositedCount);
		Log.log(4, "RPAction", "dayWiseddMarkedForDepositedSummary", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward getPaymentsMadeForASF(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RpProcessor rpProcessor = new RpProcessor();

		ArrayList paymentDetails = rpProcessor.displayPaymentsReceivedForASF();

		RPActionForm actionForm = (RPActionForm) form;
		actionForm.getCgpans().clear();
		actionForm.setPaymentDetails(paymentDetails);

		return mapping.findForward("paymentsSummary");
	}

	public ActionForward getPaymentsMadeForCLAIM(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RpProcessor rpProcessor = new RpProcessor();

		ArrayList paymentDetails = rpProcessor.displayPaymentsReceivedForCLAIM();

		RPActionForm actionForm = (RPActionForm) form;
		actionForm.getCgpans().clear();
		actionForm.setPaymentDetails(paymentDetails);

		return mapping.findForward("paymentsSummary");
	}

	public ActionForward getPaymentsForReallocation(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "getPaymentsForReallocation", "Entered");

		RpProcessor rpProcessor = new RpProcessor();

		ArrayList paymentDetails = rpProcessor.displayPaymentsForReallocation();

		Log.log(5, "RPAction", "getPaymentsForReallocation", "paymentDetails " + paymentDetails);

		RPActionForm actionForm = (RPActionForm) form;

		Log.log(5, "RPAction", "getPaymentsForReallocation", "actionForm " + actionForm);

		actionForm.setPaymentDetails(paymentDetails);

		Log.log(4, "RPAction", "getPaymentsForReallocation", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward submitReallocationPayments(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "submitReallocationPayments", "Entered");

		RPActionForm actionForm = (RPActionForm) form;

		String payId = actionForm.getPaymentId();

		Log.log(5, "RPAction", "submitReallocationPayments", "Pay id from form is " + payId);

		RpProcessor rpProcessor = new RpProcessor();
		User user = getUserInformation(request);
		rpProcessor.submitReAllocationDetails(actionForm, request, user, payId);

		request.setAttribute("message", "Reallocation details are updated.");
		Log.log(4, "RPAction", "submitReallocationPayments", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward submitASFPANPayments(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "submitASFPANPayments", "Entered");
		RPActionForm actionForm = (RPActionForm) form;

		String danNo = actionForm.getDanNo();
		Log.log(5, "RPAction", "submitASFPANPayments", "danNo " + danNo);

		ArrayList panAllocationDetails = (ArrayList) actionForm.getDanPanDetail(danNo);
		String strDanNo = danNo.replace('.', '_');
		Map allocatedFlags = actionForm.getAllocatedFlags();
		Map cgpans = actionForm.getCgpans();

		Set cgpanSet = cgpans.keySet();

		Iterator cgpanIterator = cgpanSet.iterator();

		Log.log(5, "RPAction", "submitASFPANPayments", "CGPANS selected " + cgpans.size());

		while (cgpanIterator.hasNext()) {
			String key = (String) cgpanIterator.next();
			Log.log(5, "RPAction", "submitASFPANPayments", "key,value " + key + "," + cgpans.get(key));

			Log.log(5, "RPAction", "submitASFPANPayments", "From request "
					+ request.getParameter(new StringBuilder().append("cgpan(").append(key).append(")").toString()));
		}
		cgpanIterator = cgpanSet.iterator();

		String cgpanPart = null;
		Log.log(5, "RPAction", "submitASFPANPayments", "browsing through the pan list");
		boolean isAvl = false;

		String value = null;

		Log.log(5, "RPAction", "submitASFPANPayments", "Cgpan map size " + cgpans.size());

		for (int i = 0; i < panAllocationDetails.size(); i++) {
			AllocationDetail allocationDetail = (AllocationDetail) panAllocationDetails.get(i);

			Log.log(5, "RPAction", "submitASFPANPayments", "cgpan frm array " + allocationDetail.getCgpan());
			Log.log(5, "RPAction", "submitASFPANPayments", "flag frm array " + allocationDetail.getAllocatedFlag());

			while (cgpanIterator.hasNext()) {
				Object key = cgpanIterator.next();
				value = (String) cgpans.get(key);
				Log.log(5, "RPAction", "submitASFPANPayments", "key " + key);
				Log.log(5, "RPAction", "submitASFPANPayments", "value " + value);

				cgpanPart = value.substring(value.indexOf("-") + 1, value.length());

				Log.log(5, "RPAction", "submitASFPANPayments", "cgpanPart " + cgpanPart);

				if ((value.startsWith(danNo)) && (cgpanPart.equals(allocationDetail.getCgpan()))
						&& (allocatedFlags.get(key) != null) && (((String) allocatedFlags.get(key)).equals("Y"))) {
					Log.log(5, "RPAction", "submitASFPANPayments", "amount due  " + allocationDetail.getAmountDue());

					allocationDetail.setAllocatedFlag("Y");

					isAvl = true;
					break;
				}
			}

			if (!isAvl) {
				Object removed = cgpans.remove(strDanNo + "-" + allocationDetail.getCgpan());

				Log.log(5, "RPAction", "submitASFPANPayments", "Removed element" + removed);
			}

			isAvl = false;
			cgpanIterator = cgpanSet.iterator();
		}
		Log.log(5, "RPAction", "submitASFPANPayments", "Cgpan map size " + cgpans.size());

		return mapping.findForward("danSummary");
	}

	public ActionForward submitExpiredASFPANPayments(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "submitExpiredASFPANPayments", "Entered");
		RPActionForm actionForm = (RPActionForm) form;

		String danNo = actionForm.getDanNo();
		Log.log(5, "RPAction", "submitExpiredASFPANPayments", "danNo " + danNo);

		ArrayList panAllocationDetails = (ArrayList) actionForm.getDanPanDetail(danNo);
		String strDanNo = danNo.replace('.', '_');
		Map allocatedFlags = actionForm.getAllocatedFlags();
		Map cgpans = actionForm.getCgpans();

		Set cgpanSet = cgpans.keySet();

		Iterator cgpanIterator = cgpanSet.iterator();

		Log.log(5, "RPAction", "submitExpiredASFPANPayments", "CGPANS selected " + cgpans.size());

		while (cgpanIterator.hasNext()) {
			String key = (String) cgpanIterator.next();
			Log.log(5, "RPAction", "submitExpiredASFPANPayments", "key,value " + key + "," + cgpans.get(key));

			Log.log(5, "RPAction", "submitExpiredASFPANPayments", "From request "
					+ request.getParameter(new StringBuilder().append("cgpan(").append(key).append(")").toString()));
		}
		cgpanIterator = cgpanSet.iterator();

		String cgpanPart = null;
		Log.log(5, "RPAction", "submitExpiredASFPANPayments", "browsing through the pan list");
		boolean isAvl = false;

		String value = null;

		Log.log(5, "RPAction", "submitExpiredASFPANPayments", "Cgpan map size " + cgpans.size());

		for (int i = 0; i < panAllocationDetails.size(); i++) {
			AllocationDetail allocationDetail = (AllocationDetail) panAllocationDetails.get(i);

			Log.log(5, "RPAction", "submitExpiredASFPANPayments", "cgpan frm array " + allocationDetail.getCgpan());
			Log.log(5, "RPAction", "submitExpiredASFPANPayments",
					"flag frm array " + allocationDetail.getAllocatedFlag());

			while (cgpanIterator.hasNext()) {
				Object key = cgpanIterator.next();
				value = (String) cgpans.get(key);
				Log.log(5, "RPAction", "submitExpiredASFPANPayments", "key " + key);
				Log.log(5, "RPAction", "submitExpiredASFPANPayments", "value " + value);

				cgpanPart = value.substring(value.indexOf("-") + 1, value.length());

				Log.log(5, "RPAction", "submitExpiredASFPANPayments", "cgpanPart " + cgpanPart);

				if ((value.startsWith(danNo)) && (cgpanPart.equals(allocationDetail.getCgpan()))
						&& (allocatedFlags.get(key) != null) && (((String) allocatedFlags.get(key)).equals("Y"))) {
					Log.log(5, "RPAction", "submitExpiredASFPANPayments",
							"amount due  " + allocationDetail.getAmountDue());

					allocationDetail.setAllocatedFlag("Y");

					isAvl = true;
					break;
				}
			}

			if (!isAvl) {
				Object removed = cgpans.remove(strDanNo + "-" + allocationDetail.getCgpan());

				Log.log(5, "RPAction", "submitExpiredASFPANPayments", "Removed element" + removed);
			}

			isAvl = false;
			cgpanIterator = cgpanSet.iterator();
		}
		Log.log(5, "RPAction", "submitExpiredASFPANPayments", "Cgpan map size " + cgpans.size());

		return mapping.findForward("danSummary");
	}

	public ActionForward submitGFPANPayments(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "submitGFPANPayments", "Entered");
		RPActionForm actionForm = (RPActionForm) form;

		String danNo = actionForm.getDanNo();
		Log.log(5, "RPAction", "submitGFPANPayments", "danNo " + danNo);

		ArrayList panAllocationDetails = (ArrayList) actionForm.getDanPanDetail(danNo);
		String strDanNo = danNo.replace('.', '_');
		Map allocatedFlags = actionForm.getAllocatedFlags();
		Map cgpans = actionForm.getCgpans();

		Set cgpanSet = cgpans.keySet();

		Iterator cgpanIterator = cgpanSet.iterator();

		Log.log(5, "RPAction", "submitGFPANPayments", "CGPANS selected " + cgpans.size());

		while (cgpanIterator.hasNext()) {
			String key = (String) cgpanIterator.next();
			Log.log(5, "RPAction", "submitGFPANPayments", "key,value " + key + "," + cgpans.get(key));

			Log.log(5, "RPAction", "submitGFPANPayments", "From request "
					+ request.getParameter(new StringBuilder().append("cgpan(").append(key).append(")").toString()));
		}
		cgpanIterator = cgpanSet.iterator();

		String cgpanPart = null;
		Log.log(5, "RPAction", "submitGFPANPayments", "browsing through the pan list");
		boolean isAvl = false;

		String value = null;

		Log.log(5, "RPAction", "submitGFPANPayments", "Cgpan map size " + cgpans.size());

		for (int i = 0; i < panAllocationDetails.size(); i++) {
			AllocationDetail allocationDetail = (AllocationDetail) panAllocationDetails.get(i);

			Log.log(5, "RPAction", "submitGFPANPayments", "cgpan frm array " + allocationDetail.getCgpan());
			Log.log(5, "RPAction", "submitGFPANPayments", "flag frm array " + allocationDetail.getAllocatedFlag());

			while (cgpanIterator.hasNext()) {
				Object key = cgpanIterator.next();
				value = (String) cgpans.get(key);
				Log.log(5, "RPAction", "submitGFPANPayments", "key " + key);
				Log.log(5, "RPAction", "submitGFPANPayments", "value " + value);

				cgpanPart = value.substring(value.indexOf("-") + 1, value.length());

				Log.log(5, "RPAction", "submitGFPANPayments", "cgpanPart " + cgpanPart);

				if ((value.startsWith(danNo)) && (cgpanPart.equals(allocationDetail.getCgpan()))
						&& (allocatedFlags.get(key) != null) && (((String) allocatedFlags.get(key)).equals("Y"))) {
					Log.log(5, "RPAction", "submitGFPANPayments", "amount due  " + allocationDetail.getAmountDue());

					allocationDetail.setAllocatedFlag("Y");

					isAvl = true;
					break;
				}
			}

			if (!isAvl) {
				Object removed = cgpans.remove(strDanNo + "-" + allocationDetail.getCgpan());

				Log.log(5, "RPAction", "submitGFPANPayments", "Removed element" + removed);
			}

			isAvl = false;
			cgpanIterator = cgpanSet.iterator();
		}
		Log.log(5, "RPAction", "submitGFPANPayments", "Cgpan map size " + cgpans.size());

		return mapping.findForward("danSummary");
	}

	public ActionForward submitPANPayments(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "submitPANPayments", "Entered");
		RPActionForm actionForm = (RPActionForm) form;

		String danNo = actionForm.getDanNo();
		Log.log(5, "RPAction", "submitPANPayments", "danNo " + danNo);

		ArrayList panAllocationDetails = (ArrayList) actionForm.getDanPanDetail(danNo);
		String strDanNo = danNo.replace('.', '_');
		Map allocatedFlags = actionForm.getAllocatedFlags();
		Map cgpans = actionForm.getCgpans();

		Set cgpanSet = cgpans.keySet();

		Iterator cgpanIterator = cgpanSet.iterator();

		Log.log(5, "RPAction", "submitPANPayments", "CGPANS selected " + cgpans.size());

		while (cgpanIterator.hasNext()) {
			String key = (String) cgpanIterator.next();
			Log.log(5, "RPAction", "submitPANPayments", "key,value " + key + "," + cgpans.get(key));

			Log.log(5, "RPAction", "submitPANPayments", "From request "
					+ request.getParameter(new StringBuilder().append("cgpan(").append(key).append(")").toString()));
		}
		cgpanIterator = cgpanSet.iterator();

		String cgpanPart = null;
		Log.log(5, "RPAction", "submitPANPayments", "browsing through the pan list");
		boolean isAvl = false;

		String value = null;

		Log.log(5, "RPAction", "submitPANPayments", "Cgpan map size " + cgpans.size());

		for (int i = 0; i < panAllocationDetails.size(); i++) {
			AllocationDetail allocationDetail = (AllocationDetail) panAllocationDetails.get(i);

			Log.log(5, "RPAction", "submitPANPayments", "cgpan frm array " + allocationDetail.getCgpan());
			Log.log(5, "RPAction", "submitPANPayments", "flag frm array " + allocationDetail.getAllocatedFlag());

			while (cgpanIterator.hasNext()) {
				Object key = cgpanIterator.next();
				value = (String) cgpans.get(key);
				Log.log(5, "RPAction", "submitPANPayments", "key " + key);
				Log.log(5, "RPAction", "submitPANPayments", "value " + value);

				cgpanPart = value.substring(value.indexOf("-") + 1, value.length());

				Log.log(5, "RPAction", "submitPANPayments", "cgpanPart " + cgpanPart);

				if ((value.startsWith(danNo)) && (cgpanPart.equals(allocationDetail.getCgpan()))
						&& (allocatedFlags.get(key) != null) && (((String) allocatedFlags.get(key)).equals("Y"))) {
					Log.log(5, "RPAction", "submitPANPayments", "amount due  " + allocationDetail.getAmountDue());

					isAvl = true;
					break;
				}
			}

			if (!isAvl) {
				Object removed = cgpans.remove(strDanNo + "-" + allocationDetail.getCgpan());

				Log.log(5, "RPAction", "submitPANPayments", "Removed element" + removed);
			}

			isAvl = false;
			cgpanIterator = cgpanSet.iterator();
		}
		Log.log(5, "RPAction", "submitPANPayments", "Cgpan map size " + cgpans.size());

		return mapping.findForward("danSummary");
	}

	public ActionForward submitSFPANPayments(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "submitsfPANPayments", "Entered");
		RPActionForm actionForm = (RPActionForm) form;

		String danNo = actionForm.getDanNo();
		Log.log(5, "RPAction", "submitPANPayments", "danNo " + danNo);

		ArrayList panAllocationDetails = (ArrayList) actionForm.getDanPanDetail(danNo);
		String strDanNo = danNo.replace('.', '_');
		Map allocatedFlags = actionForm.getAllocatedFlags();
		Map cgpans = actionForm.getCgpans();

		Set cgpanSet = cgpans.keySet();

		Iterator cgpanIterator = cgpanSet.iterator();

		Log.log(5, "RPAction", "submitPANPayments", "CGPANS selected " + cgpans.size());

		while (cgpanIterator.hasNext()) {
			String key = (String) cgpanIterator.next();
			Log.log(5, "RPAction", "submitPANPayments", "key,value " + key + "," + cgpans.get(key));

			Log.log(5, "RPAction", "submitPANPayments", "From request "
					+ request.getParameter(new StringBuilder().append("cgpan(").append(key).append(")").toString()));
		}

		cgpanIterator = cgpanSet.iterator();

		String cgpanPart = null;
		Log.log(5, "RPAction", "submitPANPayments", "browsing through the pan list");
		boolean isAvl = false;

		String value = null;

		Log.log(5, "RPAction", "submitPANPayments", "Cgpan map size " + cgpans.size());

		for (int i = 0; i < panAllocationDetails.size(); i++) {
			AllocationDetail allocationDetail = (AllocationDetail) panAllocationDetails.get(i);

			Log.log(5, "RPAction", "submitPANPayments", "cgpan frm array " + allocationDetail.getCgpan());

			Log.log(5, "RPAction", "submitPANPayments", "flag frm array " + allocationDetail.getAllocatedFlag());

			while (cgpanIterator.hasNext()) {
				Object key = cgpanIterator.next();
				value = (String) cgpans.get(key);

				Log.log(5, "RPAction", "submitPANPayments", "key " + key);

				Log.log(5, "RPAction", "submitPANPayments", "value " + value);

				cgpanPart = value.substring(value.indexOf("-") + 1, value.length());

				Log.log(5, "RPAction", "submitPANPayments", "cgpanPart " + cgpanPart);

				if ((value.startsWith(danNo)) && (cgpanPart.equals(allocationDetail.getCgpan()))
						&& (allocatedFlags.get(key) != null) && (((String) allocatedFlags.get(key)).equals("Y"))) {
					Log.log(5, "RPAction", "submitPANPayments", "amount due  " + allocationDetail.getAmountDue());

					allocationDetail.setAllocatedFlag("Y");

					isAvl = true;
					break;
				}
			}

			if (!isAvl) {
				Object removed = cgpans.remove(strDanNo + "-" + allocationDetail.getCgpan());

				Log.log(5, "RPAction", "submitPANPayments", "Removed element" + removed);
			}

			isAvl = false;
			cgpanIterator = cgpanSet.iterator();
		}

		Log.log(5, "RPAction", "submitPANPayments", "Cgpan map size " + cgpans.size());

		return mapping.findForward("danSummary");
	}

	public ActionForward getAllocatedPaymentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String methodName = "getAllocatedPaymentDetails";
		RPActionForm actionForm = (RPActionForm) form;
		Log.log(4, "RPAction", methodName, "Entering");

		actionForm.getCgpans().clear();

		String payId = request.getParameter("payId");
		String memberId = request.getParameter("memberId");
		actionForm.setPaymentId(payId);
		actionForm.setMemberId(memberId);

		Log.log(5, "RPAction", methodName, "Got paymentId " + payId);
		Log.log(5, "RPAction", methodName, "Got memberId " + memberId);

		RpProcessor rpProcessor = new RpProcessor();

		ArrayList paymentDetails = null;
		Log.log(5, "RPAction", methodName, "Before calling payment details ");
		paymentDetails = rpProcessor.getDANDetailsForReallocation(payId, memberId);

		Map danCgpanInfo = new HashMap();

		Map cgpans = actionForm.getCgpans();
		ArrayList panDetails = null;
		String tempDanNo = "";
		for (int i = 0; i < paymentDetails.size(); i++) {
			panDetails = null;
			AllocationDetail allocationDtl = (AllocationDetail) paymentDetails.get(i);
			Log.log(4, "RPAction", methodName, "dan no " + tempDanNo);
			Log.log(4, "RPAction", methodName, "cgpan " + allocationDtl.getCgpan());
			if (allocationDtl.getAllocatedFlag().equals("Y")) {
				cgpans.put(allocationDtl.getDanNo().replace('.', '_') + "-" + allocationDtl.getCgpan(),
						allocationDtl.getCgpan());
			}

			if (danCgpanInfo.containsKey(allocationDtl.getDanNo())) {
				panDetails = (ArrayList) danCgpanInfo.get(allocationDtl.getDanNo());
			} else {
				panDetails = new ArrayList();
			}
			panDetails.add(allocationDtl);
			danCgpanInfo.put(allocationDtl.getDanNo(), panDetails);
		}

		Log.log(5, "RPAction", methodName, "After calling payment details");

		Log.log(5, "RPAction", methodName, "Before dynaForm set in RPAction::getPaymentDetails");

		actionForm.setDanPanDetails(danCgpanInfo);
		actionForm.setCgpans(cgpans);

		Log.log(5, "RPAction", methodName, "After dynaForm set in RPAction::getPaymentDetails");

		return mapping.findForward("paymentDetails");
	}

	public ActionForward getPaymentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String methodName = "getPaymentDetails";
		RPActionForm actionForm = (RPActionForm) form;
		Log.log(4, "RPAction", methodName, "Entering");
		String paymentId = actionForm.getPaymentId();
		Log.log(4, "RPAction", methodName, "Got paymentId");
		actionForm.getCgpans().clear();
		RpProcessor rpProcessor = new RpProcessor();

		ArrayList paymentDetails = null;
		Log.log(4, "RPAction", methodName, "Before calling payment details");
		paymentDetails = rpProcessor.getPaymentDetails(paymentId);
		HashMap bankIds = new HashMap();
		HashMap zoneIds = new HashMap();
		HashMap branchIds = new HashMap();

		PaymentDetails payDetails = (PaymentDetails) paymentDetails.get(0);
		actionForm.setInstrumentDate(payDetails.getInstrumentDate());

		for (int i = 1; i < paymentDetails.size(); i++) {
			DemandAdvice demandAdvice = (DemandAdvice) paymentDetails.get(i);
			demandAdvice.setAppropriated(demandAdvice.getAllocated());

			actionForm.setAppropriatedFlag("key-" + (i - 1), demandAdvice.getAllocated());
			bankIds.put("key-" + (i - 1), demandAdvice.getBankId());
			zoneIds.put("key-" + (i - 1), demandAdvice.getZoneId());
			branchIds.put("key-" + (i - 1), demandAdvice.getBranchId());
		}

		Log.log(4, "RPAction", methodName, "After calling payment details");

		Log.log(4, "RPAction", methodName, "Before dynaForm set in RPAction::getPaymentDetails");

		actionForm.setPaymentDetails(paymentDetails);
		actionForm.setDateOfRealisation(null);
		actionForm.setReceivedAmount(0.0D);
		actionForm.setPaymentId(paymentId);
		actionForm.setBankIds(bankIds);
		actionForm.setBranchIds(branchIds);
		actionForm.setZoneIds(zoneIds);

		Log.log(4, "RPAction", methodName, "After dynaForm set in RPAction::getPaymentDetails");

		return mapping.findForward("paymentDetails");
	}

	public ActionForward getPaymentDetailsForGF(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String methodName = "getPaymentDetails";
		RPActionForm actionForm = (RPActionForm) form;
		Log.log(4, "RPAction", methodName, "Entering");
		String paymentId = actionForm.getPaymentId();
		Log.log(4, "RPAction", methodName, "Got paymentId");
		actionForm.getCgpans().clear();
		RpProcessor rpProcessor = new RpProcessor();

		ArrayList paymentDetails = null;
		Log.log(4, "RPAction", methodName, "Before calling payment details");
		paymentDetails = rpProcessor.getPaymentDetails(paymentId);
		HashMap bankIds = new HashMap();
		HashMap zoneIds = new HashMap();
		HashMap branchIds = new HashMap();

		PaymentDetails payDetails = (PaymentDetails) paymentDetails.get(0);
		actionForm.setInstrumentDate(payDetails.getInstrumentDate());

		for (int i = 1; i < paymentDetails.size(); i++) {
			DemandAdvice demandAdvice = (DemandAdvice) paymentDetails.get(i);
			demandAdvice.setAppropriated(demandAdvice.getAllocated());

			actionForm.setAppropriatedFlag("key-" + (i - 1), demandAdvice.getAllocated());
			bankIds.put("key-" + (i - 1), demandAdvice.getBankId());
			zoneIds.put("key-" + (i - 1), demandAdvice.getZoneId());
			branchIds.put("key-" + (i - 1), demandAdvice.getBranchId());
		}

		Log.log(4, "RPAction", methodName, "After calling payment details");

		Log.log(4, "RPAction", methodName, "Before dynaForm set in RPAction::getPaymentDetails");

		actionForm.setPaymentDetails(paymentDetails);
		actionForm.setDateOfRealisation(null);
		actionForm.setReceivedAmount(0.0D);
		actionForm.setPaymentId(paymentId);
		actionForm.setBankIds(bankIds);
		actionForm.setBranchIds(branchIds);
		actionForm.setZoneIds(zoneIds);

		Log.log(4, "RPAction", methodName, "After dynaForm set in RPAction::getPaymentDetails");

		return mapping.findForward("paymentDetails");
	}

	public ActionForward getPaymentDetailsForASF(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String methodName = "getPaymentDetails";
		RPActionForm actionForm = (RPActionForm) form;
		Log.log(4, "RPAction", methodName, "Entering");
		String paymentId = actionForm.getPaymentId();
		Log.log(4, "RPAction", methodName, "Got paymentId");
		actionForm.getCgpans().clear();
		RpProcessor rpProcessor = new RpProcessor();

		ArrayList paymentDetails = null;
		Log.log(4, "RPAction", methodName, "Before calling payment details");
		paymentDetails = rpProcessor.getPaymentDetails(paymentId);
		HashMap bankIds = new HashMap();
		HashMap zoneIds = new HashMap();
		HashMap branchIds = new HashMap();

		PaymentDetails payDetails = (PaymentDetails) paymentDetails.get(0);
		actionForm.setInstrumentDate(payDetails.getInstrumentDate());

		for (int i = 1; i < paymentDetails.size(); i++) {
			DemandAdvice demandAdvice = (DemandAdvice) paymentDetails.get(i);
			demandAdvice.setAppropriated(demandAdvice.getAllocated());

			actionForm.setAppropriatedFlag("key-" + (i - 1), demandAdvice.getAllocated());
			bankIds.put("key-" + (i - 1), demandAdvice.getBankId());
			zoneIds.put("key-" + (i - 1), demandAdvice.getZoneId());
			branchIds.put("key-" + (i - 1), demandAdvice.getBranchId());
		}

		Log.log(4, "RPAction", methodName, "After calling payment details");

		Log.log(4, "RPAction", methodName, "Before dynaForm set in RPAction::getPaymentDetails");

		actionForm.setPaymentDetails(paymentDetails);
		actionForm.setDateOfRealisation(null);
		actionForm.setReceivedAmount(0.0D);
		actionForm.setPaymentId(paymentId);
		actionForm.setBankIds(bankIds);
		actionForm.setBranchIds(branchIds);
		actionForm.setZoneIds(zoneIds);

		Log.log(4, "RPAction", methodName, "After dynaForm set in RPAction::getPaymentDetails");

		return mapping.findForward("paymentDetails");
	}

	public ActionForward getPaymentDetailsForCLAIM(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String methodName = "getPaymentDetails";
		RPActionForm actionForm = (RPActionForm) form;
		Log.log(4, "RPAction", methodName, "Entering");
		String paymentId = actionForm.getPaymentId();
		Log.log(4, "RPAction", methodName, "Got paymentId");
		actionForm.getCgpans().clear();
		RpProcessor rpProcessor = new RpProcessor();

		ArrayList paymentDetails = null;
		Log.log(4, "RPAction", methodName, "Before calling payment details");
		paymentDetails = rpProcessor.getPaymentDetails(paymentId);
		HashMap bankIds = new HashMap();
		HashMap zoneIds = new HashMap();
		HashMap branchIds = new HashMap();

		PaymentDetails payDetails = (PaymentDetails) paymentDetails.get(0);
		actionForm.setInstrumentDate(payDetails.getInstrumentDate());

		for (int i = 1; i < paymentDetails.size(); i++) {
			DemandAdvice demandAdvice = (DemandAdvice) paymentDetails.get(i);
			demandAdvice.setAppropriated(demandAdvice.getAllocated());

			actionForm.setAppropriatedFlag("key-" + (i - 1), demandAdvice.getAllocated());
			bankIds.put("key-" + (i - 1), demandAdvice.getBankId());
			zoneIds.put("key-" + (i - 1), demandAdvice.getZoneId());
			branchIds.put("key-" + (i - 1), demandAdvice.getBranchId());
		}

		Log.log(4, "RPAction", methodName, "After calling payment details");

		Log.log(4, "RPAction", methodName, "Before dynaForm set in RPAction::getPaymentDetails");

		actionForm.setPaymentDetails(paymentDetails);
		actionForm.setDateOfRealisation(null);
		actionForm.setReceivedAmount(0.0D);
		actionForm.setPaymentId(paymentId);
		actionForm.setBankIds(bankIds);
		actionForm.setBranchIds(branchIds);
		actionForm.setZoneIds(zoneIds);

		Log.log(4, "RPAction", methodName, "After dynaForm set in RPAction::getPaymentDetails");

		return mapping.findForward("paymentDetails");
	}

	public ActionForward getClaimPaymentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String methodName = "getClaimPaymentDetails";

		RPActionForm actionForm = (RPActionForm) form;
		actionForm.resetWhenRequired();

		Log.log(4, "RPAction", methodName, "Entering");

		Log.log(5, "RPAction", methodName, "actionForm.getSelectMember() " + actionForm.getSelectMember());

		IFProcessor ifProcessor = new IFProcessor();

		ArrayList instruments = ifProcessor.getInstrumentTypes("G");

		actionForm.setInstruments(instruments);

		Log.log(4, "RPAction", methodName, "Exited");

		return mapping.findForward("success");
	}

	public ActionForward insertPaymentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String methodName = "insertPaymentDetails";

		RPActionForm actionForm = (RPActionForm) form;

		Log.log(4, "RPAction", methodName, "Entering");

		PaymentDetails paymentDetails = new PaymentDetails();

		Log.log(5, "RPAction", methodName, "actionForm.getModeOfPayment() " + actionForm.getModeOfPayment());

		BeanUtils.copyProperties(paymentDetails, actionForm);
		paymentDetails.setCollectingBank(actionForm.getCollectingBankName());

		Log.log(5, "RPAction", methodName, "paymentDetails.getModeOfPayment() " + paymentDetails.getModeOfPayment());

		Log.log(5, "RPAction", methodName, "actionForm.getModeOfPayment() " + actionForm.getModeOfPayment());

		RpProcessor rpProcessor = new RpProcessor();
		String paymentId = rpProcessor.insertPaymentByCGTSI(paymentDetails);

		Log.log(5, "RPAction", methodName, "paymentId " + paymentId);
		String message = "Payment details stored successfull. Payment id is " + paymentId;

		request.setAttribute("message", message);

		Log.log(4, "RPAction", methodName, "Exited");

		return mapping.findForward("success");
	}

	public ActionForward appropriatePayments(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RPActionForm actionForm = (RPActionForm) form;
		RpProcessor rpProcessor = new RpProcessor();
		ArrayList demandAdvices = new ArrayList();

		String methodName = "appropriatePayments";
		String danId = "";
		String cgpan = "";
		String allocatedFlag = "";
		String appropriatedFlag = "";
		String remark = "";
		String paymentId = "";

		DemandAdvice demandAdvice = null;

		Log.log(4, "RPAction", methodName, "Entered");
		Map danIds = actionForm.getDanIds();
		Map cgpans = actionForm.getCgpans();
		Map remarks = actionForm.getRemarks();
		Map allocatedFlags = actionForm.getAllocatedFlags();
		Map appropriatedFlags = actionForm.getAppropriatedFlags();
		Map amounts = actionForm.getAmountsRaised();
		Map penalties = actionForm.getPenalties();
		Map bankIds = actionForm.getBankIds();
		Map zoneIds = actionForm.getZoneIds();
		Map branchIds = actionForm.getBranchIds();

		Log.log(4, "RPAction", methodName, "Assigned CGPAN details to hashmap");

		Set danIdSet = danIds.keySet();
		Set allocatedFlagSet = allocatedFlags.keySet();
		Set appropriatedFlagSet = appropriatedFlags.keySet();
		Set cgpanSet = cgpans.keySet();
		Set remarksSet = remarks.keySet();
		Set amountsSet = amounts.keySet();
		Set penaltySet = penalties.keySet();
		Set bankIdSet = bankIds.keySet();
		Set zoneIdSet = zoneIds.keySet();
		Set branchIdSet = branchIds.keySet();

		Log.log(4, "RPAction", methodName, "Assigned CGPAN details to Set");

		Iterator cgpanIterator = cgpanSet.iterator();
		Iterator bankIdIterator = bankIdSet.iterator();
		Iterator zoneIdIterator = zoneIdSet.iterator();
		Iterator branchIdIterator = branchIdSet.iterator();

		Log.log(4, "RPAction", methodName, "Assigned CGPAN details to Iterator");

		User user = getUserInformation(request);
		String userId = user.getUserId();
		paymentId = actionForm.getPaymentId();

		double appropriatedAmount = 0.0D;

		while (cgpanIterator.hasNext()) {
			String cgpanKey = (String) cgpanIterator.next();
			danId = (String) danIds.get(cgpanKey);
			cgpan = (String) cgpans.get(cgpanKey);
			allocatedFlag = (String) allocatedFlags.get(cgpanKey);
			appropriatedFlag = (String) appropriatedFlags.get(cgpanKey);
			remark = (String) remarks.get(cgpanKey);
			double amount = Double.parseDouble((String) amounts.get(cgpanKey));
			double penalty = Double.parseDouble((String) penalties.get(cgpanKey));

			Log.log(4, "RPAction", methodName, " inside iterator - dan id - " + danId);
			Log.log(4, "RPAction", methodName, " inside iterator - cgpan - " + cgpan);
			Log.log(4, "RPAction", methodName, " inside iterator - allocated flag - " + allocatedFlag);
			Log.log(4, "RPAction", methodName, " inside iterator - appropriated flag - " + appropriatedFlag);
			Log.log(4, "RPAction", methodName, " inside iterator - amount - " + amount);
			Log.log(4, "RPAction", methodName, " inside iterator - penalty - " + penalty);

			demandAdvice = new DemandAdvice();
			demandAdvice.setDanNo(danId);
			demandAdvice.setCgpan(cgpan);
			demandAdvice.setReason(remark);
			demandAdvice.setAmountRaised(amount);
			demandAdvice.setPenalty(penalty);
			demandAdvice.setPaymentId(paymentId);

			demandAdvice.setAllocated(appropriatedFlag);
			demandAdvice.setAppropriated(appropriatedFlag);
			demandAdvice.setUserId(userId);
			demandAdvice.setBankId((String) bankIds.get(bankIdIterator.next()));
			demandAdvice.setZoneId((String) zoneIds.get(zoneIdIterator.next()));
			demandAdvice.setBranchId((String) branchIds.get(branchIdIterator.next()));

			Log.log(4, "RPAction", methodName, " inside iterator - cgpan - " + demandAdvice.getCgpan());
			Log.log(4, "RPAction", methodName, " inside iterator - allocated flag - " + demandAdvice.getAllocated());
			Log.log(4, "RPAction", methodName,
					" inside iterator - appropriated flag - " + demandAdvice.getAppropriated());
			if (appropriatedFlag.equals("Y")) {
				appropriatedAmount += amount;
			}

			demandAdvices.add(demandAdvice);
			Log.log(4, "RPAction", methodName, " inside iterator - adding cgpan to demand advice list - " + cgpan);

			Log.log(4, "RPAction", methodName, "DemandAdvices added to ArrayList");
		}

		java.util.Date realisationDate = actionForm.getDateOfRealisation();
		double receivedAmount = actionForm.getReceivedAmount();

		RealisationDetail realisationDetail = new RealisationDetail();
		realisationDetail.setPaymentId(paymentId);
		realisationDetail.setRealisationAmount(receivedAmount);
		realisationDetail.setRealisationDate(realisationDate);

		if (receivedAmount < appropriatedAmount) {
			double shortLimit = appropriatedAmount - receivedAmount;
			throw new ShortExceedsLimitException("Received Amount is less than Allocated Amount by Rs." + shortLimit);
		}
		if (receivedAmount > appropriatedAmount) {
			double excessLimit = receivedAmount - appropriatedAmount;
			throw new ShortExceedsLimitException(
					"Received Amount is greater than Allocated Amount by Rs." + excessLimit);
		}
		double shortOrExcess = rpProcessor.appropriatePayment(demandAdvices, realisationDetail,
				request.getSession(false).getServletContext().getRealPath(""));

		request.setAttribute("message",
				"Payment Amount Appropriated Successfully.<BR><BR>Total Received Amount : " + receivedAmount
						+ "<BR>Total Appropriated Amount : " + appropriatedAmount + "<BR>Short / Excess : "
						+ shortOrExcess);

		return mapping.findForward("success");
	}

	public ActionForward appropriatePaymentsForGF(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RPActionForm actionForm = (RPActionForm) form;
		RpProcessor rpProcessor = new RpProcessor();
		ArrayList demandAdvices = new ArrayList();

		String methodName = "appropriatePayments";
		String danId = "";
		String cgpan = "";
		String allocatedFlag = "";
		String appropriatedFlag = "";
		String remark = "";
		String paymentId = "";

		DemandAdvice demandAdvice = null;

		Log.log(4, "RPAction", methodName, "Entered");
		Map danIds = actionForm.getDanIds();
		Map cgpans = actionForm.getCgpans();
		Map remarks = actionForm.getRemarks();
		Map allocatedFlags = actionForm.getAllocatedFlags();
		Map appropriatedFlags = actionForm.getAppropriatedFlags();
		Map amounts = actionForm.getAmountsRaised();
		Map penalties = actionForm.getPenalties();
		Map bankIds = actionForm.getBankIds();
		Map zoneIds = actionForm.getZoneIds();
		Map branchIds = actionForm.getBranchIds();

		Log.log(4, "RPAction", methodName, "Assigned CGPAN details to hashmap");

		Set danIdSet = danIds.keySet();
		Set allocatedFlagSet = allocatedFlags.keySet();
		Set appropriatedFlagSet = appropriatedFlags.keySet();
		Set cgpanSet = cgpans.keySet();
		Set remarksSet = remarks.keySet();
		Set amountsSet = amounts.keySet();
		Set penaltySet = penalties.keySet();
		Set bankIdSet = bankIds.keySet();
		Set zoneIdSet = zoneIds.keySet();
		Set branchIdSet = branchIds.keySet();

		Log.log(4, "RPAction", methodName, "Assigned CGPAN details to Set");

		Iterator cgpanIterator = cgpanSet.iterator();
		Iterator bankIdIterator = bankIdSet.iterator();
		Iterator zoneIdIterator = zoneIdSet.iterator();
		Iterator branchIdIterator = branchIdSet.iterator();

		Log.log(4, "RPAction", methodName, "Assigned CGPAN details to Iterator");

		User user = getUserInformation(request);
		String userId = user.getUserId();
		paymentId = actionForm.getPaymentId();

		double appropriatedAmount = 0.0D;

		while (cgpanIterator.hasNext()) {
			String cgpanKey = (String) cgpanIterator.next();
			danId = (String) danIds.get(cgpanKey);
			cgpan = (String) cgpans.get(cgpanKey);
			allocatedFlag = (String) allocatedFlags.get(cgpanKey);
			appropriatedFlag = (String) appropriatedFlags.get(cgpanKey);
			remark = (String) remarks.get(cgpanKey);
			double amount = Double.parseDouble((String) amounts.get(cgpanKey));
			double penalty = Double.parseDouble((String) penalties.get(cgpanKey));

			Log.log(4, "RPAction", methodName, " inside iterator - dan id - " + danId);
			Log.log(4, "RPAction", methodName, " inside iterator - cgpan - " + cgpan);
			Log.log(4, "RPAction", methodName, " inside iterator - allocated flag - " + allocatedFlag);
			Log.log(4, "RPAction", methodName, " inside iterator - appropriated flag - " + appropriatedFlag);
			Log.log(4, "RPAction", methodName, " inside iterator - amount - " + amount);
			Log.log(4, "RPAction", methodName, " inside iterator - penalty - " + penalty);

			demandAdvice = new DemandAdvice();
			demandAdvice.setDanNo(danId);
			demandAdvice.setCgpan(cgpan);
			demandAdvice.setReason(remark);
			demandAdvice.setAmountRaised(amount);
			demandAdvice.setPenalty(penalty);
			demandAdvice.setPaymentId(paymentId);

			demandAdvice.setAllocated(appropriatedFlag);
			demandAdvice.setAppropriated(appropriatedFlag);
			demandAdvice.setUserId(userId);
			demandAdvice.setBankId((String) bankIds.get(bankIdIterator.next()));
			demandAdvice.setZoneId((String) zoneIds.get(zoneIdIterator.next()));
			demandAdvice.setBranchId((String) branchIds.get(branchIdIterator.next()));

			Log.log(4, "RPAction", methodName, " inside iterator - cgpan - " + demandAdvice.getCgpan());
			Log.log(4, "RPAction", methodName, " inside iterator - allocated flag - " + demandAdvice.getAllocated());
			Log.log(4, "RPAction", methodName,
					" inside iterator - appropriated flag - " + demandAdvice.getAppropriated());
			if (appropriatedFlag.equals("Y")) {
				appropriatedAmount += amount;
			}

			demandAdvices.add(demandAdvice);
			Log.log(4, "RPAction", methodName, " inside iterator - adding cgpan to demand advice list - " + cgpan);

			Log.log(4, "RPAction", methodName, "DemandAdvices added to ArrayList");
		}

		java.util.Date realisationDate = actionForm.getDateOfRealisation();
		double receivedAmount = actionForm.getReceivedAmount();

		RealisationDetail realisationDetail = new RealisationDetail();
		realisationDetail.setPaymentId(paymentId);
		realisationDetail.setRealisationAmount(receivedAmount);
		realisationDetail.setRealisationDate(realisationDate);

		if (receivedAmount < appropriatedAmount) {
			double shortLimit = appropriatedAmount - receivedAmount;
			throw new ShortExceedsLimitException("Received Amount is less than Allocated Amount by Rs." + shortLimit);
		}
		if (receivedAmount > appropriatedAmount) {
			double excessLimit = receivedAmount - appropriatedAmount;
			throw new ShortExceedsLimitException(
					"Received Amount is greater than Allocated Amount by Rs." + excessLimit);
		}
		double shortOrExcess = rpProcessor.appropriatePayment(demandAdvices, realisationDetail,
				request.getSession(false).getServletContext().getRealPath(""));

		request.setAttribute("message",
				"Payment Amount Appropriated Successfully.<BR><BR>Total Received Amount : " + receivedAmount
						+ "<BR>Total Appropriated Amount : " + appropriatedAmount + "<BR>Short / Excess : "
						+ shortOrExcess);

		return mapping.findForward("success");
	}

	public ActionForward appropriatePaymentsForASF(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RPActionForm actionForm = (RPActionForm) form;
		RpProcessor rpProcessor = new RpProcessor();
		ArrayList demandAdvices = new ArrayList();

		String methodName = "appropriatePayments";
		String danId = "";
		String cgpan = "";
		String allocatedFlag = "";
		String appropriatedFlag = "";
		String remark = "";
		String paymentId = "";

		DemandAdvice demandAdvice = null;

		Log.log(4, "RPAction", methodName, "Entered");
		Map danIds = actionForm.getDanIds();
		Map cgpans = actionForm.getCgpans();
		Map remarks = actionForm.getRemarks();
		Map allocatedFlags = actionForm.getAllocatedFlags();
		Map appropriatedFlags = actionForm.getAppropriatedFlags();
		Map amounts = actionForm.getAmountsRaised();
		Map penalties = actionForm.getPenalties();
		Map bankIds = actionForm.getBankIds();
		Map zoneIds = actionForm.getZoneIds();
		Map branchIds = actionForm.getBranchIds();

		Log.log(4, "RPAction", methodName, "Assigned CGPAN details to hashmap");

		Set danIdSet = danIds.keySet();
		Set allocatedFlagSet = allocatedFlags.keySet();
		Set appropriatedFlagSet = appropriatedFlags.keySet();
		Set cgpanSet = cgpans.keySet();
		Set remarksSet = remarks.keySet();
		Set amountsSet = amounts.keySet();
		Set penaltySet = penalties.keySet();
		Set bankIdSet = bankIds.keySet();
		Set zoneIdSet = zoneIds.keySet();
		Set branchIdSet = branchIds.keySet();

		Log.log(4, "RPAction", methodName, "Assigned CGPAN details to Set");

		Iterator cgpanIterator = cgpanSet.iterator();
		Iterator bankIdIterator = bankIdSet.iterator();
		Iterator zoneIdIterator = zoneIdSet.iterator();
		Iterator branchIdIterator = branchIdSet.iterator();

		Log.log(4, "RPAction", methodName, "Assigned CGPAN details to Iterator");

		User user = getUserInformation(request);
		String userId = user.getUserId();
		paymentId = actionForm.getPaymentId();

		double appropriatedAmount = 0.0D;

		while (cgpanIterator.hasNext()) {
			String cgpanKey = (String) cgpanIterator.next();
			danId = (String) danIds.get(cgpanKey);
			cgpan = (String) cgpans.get(cgpanKey);
			allocatedFlag = (String) allocatedFlags.get(cgpanKey);
			appropriatedFlag = (String) appropriatedFlags.get(cgpanKey);
			remark = (String) remarks.get(cgpanKey);
			double amount = Double.parseDouble((String) amounts.get(cgpanKey));
			double penalty = Double.parseDouble((String) penalties.get(cgpanKey));

			Log.log(4, "RPAction", methodName, " inside iterator - dan id - " + danId);
			Log.log(4, "RPAction", methodName, " inside iterator - cgpan - " + cgpan);
			Log.log(4, "RPAction", methodName, " inside iterator - allocated flag - " + allocatedFlag);
			Log.log(4, "RPAction", methodName, " inside iterator - appropriated flag - " + appropriatedFlag);
			Log.log(4, "RPAction", methodName, " inside iterator - amount - " + amount);
			Log.log(4, "RPAction", methodName, " inside iterator - penalty - " + penalty);

			demandAdvice = new DemandAdvice();
			demandAdvice.setDanNo(danId);
			demandAdvice.setCgpan(cgpan);
			demandAdvice.setReason(remark);
			demandAdvice.setAmountRaised(amount);
			demandAdvice.setPenalty(penalty);
			demandAdvice.setPaymentId(paymentId);

			demandAdvice.setAllocated(appropriatedFlag);
			demandAdvice.setAppropriated(appropriatedFlag);
			demandAdvice.setUserId(userId);
			demandAdvice.setBankId((String) bankIds.get(bankIdIterator.next()));
			demandAdvice.setZoneId((String) zoneIds.get(zoneIdIterator.next()));
			demandAdvice.setBranchId((String) branchIds.get(branchIdIterator.next()));

			Log.log(4, "RPAction", methodName, " inside iterator - cgpan - " + demandAdvice.getCgpan());
			Log.log(4, "RPAction", methodName, " inside iterator - allocated flag - " + demandAdvice.getAllocated());
			Log.log(4, "RPAction", methodName,
					" inside iterator - appropriated flag - " + demandAdvice.getAppropriated());
			if (appropriatedFlag.equals("Y")) {
				appropriatedAmount += amount;
			}

			demandAdvices.add(demandAdvice);
			Log.log(4, "RPAction", methodName, " inside iterator - adding cgpan to demand advice list - " + cgpan);

			Log.log(4, "RPAction", methodName, "DemandAdvices added to ArrayList");
		}

		java.util.Date realisationDate = actionForm.getDateOfRealisation();
		double receivedAmount = actionForm.getReceivedAmount();

		RealisationDetail realisationDetail = new RealisationDetail();
		realisationDetail.setPaymentId(paymentId);
		realisationDetail.setRealisationAmount(receivedAmount);
		realisationDetail.setRealisationDate(realisationDate);

		if (receivedAmount < appropriatedAmount) {
			double shortLimit = appropriatedAmount - receivedAmount;
			throw new ShortExceedsLimitException("Received Amount is less than Allocated Amount by Rs." + shortLimit);
		}
		if (receivedAmount > appropriatedAmount) {
			double excessLimit = receivedAmount - appropriatedAmount;
			throw new ShortExceedsLimitException(
					"Received Amount is greater than Allocated Amount by Rs." + excessLimit);
		}
		double shortOrExcess = rpProcessor.appropriatePayment(demandAdvices, realisationDetail,
				request.getSession(false).getServletContext().getRealPath(""));

		request.setAttribute("message",
				"Payment Amount Appropriated Successfully.<BR><BR>Total Received Amount : " + receivedAmount
						+ "<BR>Total Appropriated Amount : " + appropriatedAmount + "<BR>Short / Excess : "
						+ shortOrExcess);

		return mapping.findForward("success");
	}

	public ActionForward generateCGDAN(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaActionForm dynaForm = (DynaActionForm) form;
		String mliName = (String) dynaForm.get("selectMember");

		java.sql.Date startDate = null;
		java.sql.Date endDate = null;

		java.util.Date sDate = (java.util.Date) dynaForm.get("fromdt");
		java.util.Date eDate = (java.util.Date) dynaForm.get("todt");

		String stDate = String.valueOf(sDate);
		String estDate = String.valueOf(eDate);

		if ((stDate == null) || (stDate.equals(""))) {
			startDate = null;
		} else if (stDate != null) {
			startDate = new java.sql.Date(sDate.getTime());
		}

		if ((estDate == null) || (estDate.equals(""))) {
			endDate = null;
		} else if (estDate != null) {
			endDate = new java.sql.Date(eDate.getTime());
		}

		Log.log(4, "RPAction", "generateCGDAN", "Selected Member Id : " + mliName);
		String forwardPage = "";

		User user = getUserInformation(request);
		Log.log(4, "RPAction", "generateCGDAN", "Logged in user: " + user.getUserId());

		if (mliName.equals("")) {
			Log.log(4, "RPAction", "generateCGDAN", "Fetching Member Details for whom CGDAN has to be generated");
			ArrayList memberNames = this.registration.getAllMembers();
			ArrayList memberDetails = new ArrayList(memberNames.size());

			for (int i = 0; i < memberNames.size(); i++) {
				MLIInfo mliInfo = (MLIInfo) memberNames.get(i);
				String mli = "";

				mli = "(" + mliInfo.getBankId() + mliInfo.getZoneId() + mliInfo.getBranchId() + ")"
						+ mliInfo.getShortName();

				if ((mliInfo.getBranchName() != null) && (!mliInfo.getBranchName().equals(""))
						&& ((mliInfo.getZoneName() == null) || (mliInfo.getZoneName().equals("")))) {
					mli = mli + "," + mliInfo.getBranchName();
				} else if (((mliInfo.getBranchName() == null) || (mliInfo.getBranchName().equals("")))
						&& (mliInfo.getZoneName() != null) && (!mliInfo.getZoneName().equals(""))) {
					mli = mli + "," + mliInfo.getZoneName();
				} else if ((mliInfo.getBranchName() != null) && (!mliInfo.getBranchName().equals(""))
						&& (mliInfo.getZoneName() != null) && (!mliInfo.getZoneName().equals(""))) {
					mli = mli + "," + mliInfo.getZoneName() + "," + mliInfo.getBranchName();
				}

				memberDetails.add(mli);
			}
			Log.log(4, "RPAction", "generateCGDAN", "Fetched Member Details for whom CGDAN has to be generated");
			dynaForm.set("mliNames", memberDetails);
			request.setAttribute("TARGET_URL", MenuOptions.getMenuAction("RP_GENERATE_CGDAN"));
			HttpSession session = request.getSession(false);
			session.setAttribute("DAN_TYPE", "CGDAN");
			forwardPage = "memberInfo";
		} else {
			if (!mliName.equals("All")) {
				mliName = mliName.substring(1, 13);
			}
			Log.log(4, "RPAction", "generateCGDAN", "mli name " + mliName);

			RpProcessor rpProcessor = new RpProcessor();
			try {
				String message = "";

				if (((startDate == null) || (startDate.equals(""))) && ((endDate == null) || (endDate.equals("")))) {
					rpProcessor.generateCGDAN(user, mliName);
				} else {
					rpProcessor.generateCGDAN(user, mliName, startDate, endDate);
				}
				message = "CGDAN generated Successfully";
				request.setAttribute("message", message);
				forwardPage = "success";
			} catch (Exception exp) {
				forwardPage = "success1";
				request.setAttribute("message", "No Applications Available For CGDAN Generation");
			}

		}

		return mapping.findForward(forwardPage);
	}

	public ActionForward generateSFDAN(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String methodName = "generateSFDAN";
		Log.log(4, "RPAction", methodName, "Entered");
		DynaActionForm dynaForm = (DynaActionForm) form;
		String mliName = (String) dynaForm.get("selectMember");
		Log.log(4, "RPAction", methodName, "Selected Member Id : " + mliName);
		String forwardPage = "";

		User user = getUserInformation(request);
		Log.log(4, "RPAction", "generateCGDAN", "Logged in user: " + user.getUserId());

		if (mliName.equals("")) {
			Log.log(5, "RPAction", methodName, "Fetching Member Details for whom SFDAN has to be generated");
			ArrayList memberNames = this.registration.getAllMembers();
			ArrayList memberDetails = new ArrayList(memberNames.size());

			for (int i = 0; i < memberNames.size(); i++) {
				MLIInfo mliInfo = (MLIInfo) memberNames.get(i);
				String mli = "";

				mli = "(" + mliInfo.getBankId() + mliInfo.getZoneId() + mliInfo.getBranchId() + ")"
						+ mliInfo.getShortName();

				if ((mliInfo.getBranchName() != null) && (!mliInfo.getBranchName().equals(""))
						&& ((mliInfo.getZoneName() == null) || (mliInfo.getZoneName().equals("")))) {
					mli = mli + "," + mliInfo.getBranchName();
				} else if (((mliInfo.getBranchName() == null) || (mliInfo.getBranchName().equals("")))
						&& (mliInfo.getZoneName() != null) && (!mliInfo.getZoneName().equals(""))) {
					mli = mli + "," + mliInfo.getZoneName();
				} else if ((mliInfo.getBranchName() != null) && (!mliInfo.getBranchName().equals(""))
						&& (mliInfo.getZoneName() != null) && (!mliInfo.getZoneName().equals(""))) {
					mli = mli + "," + mliInfo.getZoneName() + "," + mliInfo.getBranchName();
				}

				memberDetails.add(mli);
			}
			Log.log(5, "RPAction", methodName, "Fetched Member Details for whom SFDAN has to be generated");
			dynaForm.set("mliNames", memberDetails);
			request.setAttribute("TARGET_URL", MenuOptions.getMenuAction("RP_GENERATE_SFDAN"));
			HttpSession session = request.getSession(false);
			session.setAttribute("DAN_TYPE", "SFDAN");
			forwardPage = "memberInfo";
		} else {
			forwardPage = "";
			String message = "";
			try {
				Log.log(5, "RPAction", methodName, "Entering routine to generate SFDAN for all members");
				RpProcessor rpProcessor = new RpProcessor();

				if (mliName.equalsIgnoreCase("All")) {
					rpProcessor.generateSFDAN(user, null, null, null);
				} else {
					mliName = mliName.substring(1, 13);
					rpProcessor.generateSFDAN(user, mliName.substring(0, 4), mliName.substring(4, 8),
							mliName.substring(8, 12));
				}

				request.setAttribute("message", "SFDAN generated successfully");
				forwardPage = "success";
			} catch (Exception exp) {
				forwardPage = "success1";
				request.setAttribute("message", " No Applications Available For SFDAN Generation");
			}
		}
		Log.log(4, "RPAction", methodName, "Exited");

		return mapping.findForward(forwardPage);
	}

	public ActionForward generateSFDANEXP(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String methodName = "generateSFDANEXP";
		Log.log(4, "RPAction", methodName, "Entered");
		DynaActionForm dynaForm = (DynaActionForm) form;
		String mliName = (String) dynaForm.get("selectMember");
		Log.log(4, "RPAction", methodName, "Selected Member Id : " + mliName);
		String forwardPage = "";

		User user = getUserInformation(request);
		Log.log(4, "RPAction", "generateCGDAN", "Logged in user: " + user.getUserId());

		if (mliName.equals("")) {
			Log.log(5, "RPAction", methodName, "Fetching Member Details for whom SFDANEXP has to be generated");

			ArrayList memberNames = this.registration.getAllMembers();
			ArrayList memberDetails = new ArrayList(memberNames.size());

			for (int i = 0; i < memberNames.size(); i++) {
				MLIInfo mliInfo = (MLIInfo) memberNames.get(i);
				String mli = "";

				mli = "(" + mliInfo.getBankId() + mliInfo.getZoneId() + mliInfo.getBranchId() + ")"
						+ mliInfo.getShortName();

				if ((mliInfo.getBranchName() != null) && (!mliInfo.getBranchName().equals(""))
						&& ((mliInfo.getZoneName() == null) || (mliInfo.getZoneName().equals("")))) {
					mli = mli + "," + mliInfo.getBranchName();
				} else if (((mliInfo.getBranchName() == null) || (mliInfo.getBranchName().equals("")))
						&& (mliInfo.getZoneName() != null) && (!mliInfo.getZoneName().equals(""))) {
					mli = mli + "," + mliInfo.getZoneName();
				} else if ((mliInfo.getBranchName() != null) && (!mliInfo.getBranchName().equals(""))
						&& (mliInfo.getZoneName() != null) && (!mliInfo.getZoneName().equals(""))) {
					mli = mli + "," + mliInfo.getZoneName() + "," + mliInfo.getBranchName();
				}

				memberDetails.add(mli);
			}
			Log.log(5, "RPAction", methodName, "Fetched Member Details for whom SFDANEXP has to be generated");

			dynaForm.set("mliNames", memberDetails);
			request.setAttribute("TARGET_URL", MenuOptions.getMenuAction("RP_GENERATE_SFDAN"));
			HttpSession session = request.getSession(false);
			session.setAttribute("DAN_TYPE", "SFDAN");
			forwardPage = "memberInfo";
		} else {
			forwardPage = "";
			String message = "";
			try {
				Log.log(5, "RPAction", methodName, "Entering routine to generate SFDANEXP for all members");

				RpProcessor rpProcessor = new RpProcessor();

				if (mliName.equalsIgnoreCase("All")) {
					rpProcessor.generateSFDAN(user, null, null, null);
				} else {
					mliName = mliName.substring(1, 13);

					rpProcessor.generateSFDANEXP(user, mliName.substring(0, 4), mliName.substring(4, 8),
							mliName.substring(8, 12));
				}

				request.setAttribute("message", "SFDAN for Expired Cases generated successfully");
				forwardPage = "success";
			} catch (Exception exp) {
				forwardPage = "success1";
				request.setAttribute("message", " No Applications Available For SFDAN for Expired Cases Generation");
			}
		}
		Log.log(4, "RPAction", methodName, "Exited");

		return mapping.findForward(forwardPage);
	}

	public ActionForward generateSHDAN(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String methodName = "generateSHDAN";
		Log.log(4, "RPAction", methodName, "Entered");

		DynaActionForm dynaForm = (DynaActionForm) form;
		String mliName = (String) dynaForm.get("selectMember");
		Log.log(5, "RPAction", methodName, "Selected Member Id : " + mliName);
		String forwardPage = "";

		User user = getUserInformation(request);
		Log.log(5, "RPAction", methodName, "Logged in user: " + user.getUserId());

		RpProcessor rpProcessor = new RpProcessor();

		if (mliName.equals("")) {
			Log.log(5, "RPAction", methodName, "Fetching Member Details for whom CGDAN has to be generated");

			ArrayList memberIds = rpProcessor.getMemberIdsForSHDAN();
			if ((memberIds != null) && (memberIds.size() != 0)) {
				ArrayList memberDetails = new ArrayList(memberIds.size());
				for (int i = 0; i < memberIds.size(); i++) {
					String memberId = (String) memberIds.get(i);
					MLIInfo mliInfo = this.registration.getMemberDetails(memberId.substring(0, 4),
							memberId.substring(4, 8), memberId.substring(8, 12));
					String mli = "";
					mli = "(" + memberId.substring(0, 4) + memberId.substring(4, 8) + memberId.substring(8, 12) + ")"
							+ mliInfo.getShortName();

					if ((mliInfo.getBranchName() != null) && (!mliInfo.getBranchName().equals(""))
							&& ((mliInfo.getZoneName() == null) || (mliInfo.getZoneName().equals("")))) {
						mli = mli + "," + mliInfo.getBranchName();
					} else if (((mliInfo.getBranchName() == null) || (mliInfo.getBranchName().equals("")))
							&& (mliInfo.getZoneName() != null) && (!mliInfo.getZoneName().equals(""))) {
						mli = mli + "," + mliInfo.getZoneName();
					} else if ((mliInfo.getBranchName() != null) && (!mliInfo.getBranchName().equals(""))
							&& (mliInfo.getZoneName() != null) && (!mliInfo.getZoneName().equals(""))) {
						mli = mli + "," + mliInfo.getZoneName() + "," + mliInfo.getBranchName();
					}

					memberDetails.add(mli);
				}

				dynaForm.set("mliNames", memberDetails);
				request.setAttribute("TARGET_URL", MenuOptions.getMenuAction("RP_GENERATE_DAN_FOR_SHORT"));
				HttpSession session = request.getSession(false);
				session.setAttribute("DAN_TYPE", "SHDAN");
				forwardPage = "memberInfo";
			} else {
				request.setAttribute("message", "No Members available for SHDAN Generation");
				forwardPage = "success";
			}

		} else {
			Log.log(5, "RPAction", methodName, "Entering routine to generate CGDAN for all members");

			rpProcessor.generateSHDAN(user, mliName);

			String message = "Short DAN generated successfully";

			request.setAttribute("message", message);
			forwardPage = "success";
		}

		Log.log(5, "RPAction", methodName, "Exited");

		return mapping.findForward(forwardPage);
	}

	public ActionForward generateCLDAN(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String methodName = "generateCLDAN";
		Log.log(4, "RPAction", methodName, "Entered");
		DynaActionForm dynaForm = (DynaActionForm) form;
		String mliName = (String) dynaForm.get("selectMember");
		Log.log(4, "RPAction", methodName, "Selected Member Id : " + mliName);
		String forwardPage = "";
		User user = getUserInformation(request);
		Log.log(4, "RPAction", "generateCLDAN", "Logged in user: " + user.getUserId());
		if (mliName.equals("")) {
			Log.log(5, "RPAction", methodName, "Fetching Member Details for whom CLDAN has to be generated");
			ArrayList memberNames = this.registration.getAllMembers();
			ArrayList memberDetails = new ArrayList(memberNames.size());
			for (int i = 0; i < memberNames.size(); i++) {
				MLIInfo mliInfo = (MLIInfo) memberNames.get(i);
				String mli = "";
				mli = "(" + mliInfo.getBankId() + mliInfo.getZoneId() + mliInfo.getBranchId() + ")"
						+ mliInfo.getShortName();

				if ((mliInfo.getBranchName() != null) && (!mliInfo.getBranchName().equals(""))
						&& ((mliInfo.getZoneName() == null) || (mliInfo.getZoneName().equals("")))) {
					mli = mli + "," + mliInfo.getBranchName();
				} else if (((mliInfo.getBranchName() == null) || (mliInfo.getBranchName().equals("")))
						&& (mliInfo.getZoneName() != null) && (!mliInfo.getZoneName().equals(""))) {
					mli = mli + "," + mliInfo.getZoneName();
				} else if ((mliInfo.getBranchName() != null) && (!mliInfo.getBranchName().equals(""))
						&& (mliInfo.getZoneName() != null) && (!mliInfo.getZoneName().equals(""))) {
					mli = mli + "," + mliInfo.getZoneName() + "," + mliInfo.getBranchName();
				}

				memberDetails.add(mli);
			}
			Log.log(5, "RPAction", methodName, "Fetched Member Details for whom CLDAN has to be generated");
			dynaForm.set("mliNames", memberDetails);
			request.setAttribute("TARGET_URL", MenuOptions.getMenuAction("RP_GENERATE_CLDAN"));
			HttpSession session = request.getSession(false);
			session.setAttribute("DAN_TYPE", "CLDAN");
			forwardPage = "memberInfo";
		} else {
			forwardPage = "";
			String message = "";
			try {
				Log.log(5, "RPAction", methodName, "Entering routine to generate CGDAN for all members");
				RpProcessor rpProcessor = new RpProcessor();
				if (mliName.equalsIgnoreCase("All")) {
					String bankId = null;
					String zoneId = null;
					String branchId = null;
					rpProcessor.generateCLDAN(user, bankId, zoneId, branchId);
				} else {
					mliName = mliName.substring(1, 13);
					rpProcessor.generateCLDAN(user, mliName.substring(0, 4), mliName.substring(4, 8),
							mliName.substring(8, 12));
				}
				request.setAttribute("message", "CLDAN generated successfully");
				forwardPage = "success";
			} catch (Exception exp) {
				forwardPage = "success1";
				request.setAttribute("message", " No Applications Available For CLDAN Generation");
			}
		}
		Log.log(4, "RPAction", methodName, "Exited");

		return mapping.findForward(forwardPage);
	}

	public ActionForward displayPPMLIWiseFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User user = getUserInformation(request);
		String bankId = user.getBankId();
		RPActionForm rpActionForm = (RPActionForm) form;

		RpProcessor processor = new RpProcessor();

		String fwdPage = "";
		if (bankId.equalsIgnoreCase("0000")) {
			rpActionForm.setMemberId("");
			fwdPage = "displayFilter";
		} else {
			rpActionForm.setMemberId(user.getBankId() + user.getZoneId() + user.getBranchId());
			HashMap details = processor.getMLIWiseDANDetails(user.getBankId() + user.getZoneId() + user.getBranchId());
			Vector mliWiseDanDetails = (Vector) details.get("pending_dtls");
			rpActionForm.setMliWiseDanDetails(mliWiseDanDetails);

			fwdPage = "getDetails";
		}

		return mapping.findForward(fwdPage);
	}

	public ActionForward displayPPDateWiseFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RPActionForm rpActionForm = (RPActionForm) form;
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		int day = calendar.get(5);
		month -= 1;
		day += 1;
		calendar.set(2, month);
		calendar.set(5, day);
		java.util.Date prevDate = calendar.getTime();
		rpActionForm.setFromDate(prevDate);
		rpActionForm.setToDate(date);

		return mapping.findForward("displayFilter");
	}

	public ActionForward getPPMLIWiseDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "getPPMLIWiseDetails", "Entered");
		RpProcessor processor = new RpProcessor();
		RPActionForm rpActionForm = (RPActionForm) form;
		String memberId = rpActionForm.getMemberId().trim();

		ClaimsProcessor cpProcessor = new ClaimsProcessor();
		Vector memberIds = cpProcessor.getAllMemberIds();
		if (!memberIds.contains(memberId)) {
			throw new NoMemberFoundException("The Member ID does not exist");
		}
		String bankId = memberId.substring(0, 4);
		String zoneId = memberId.substring(4, 8);
		String branchId = memberId.substring(8, 12);

		MLIInfo mliInfo = this.registration.getMemberDetails(bankId, zoneId, branchId);

		if (mliInfo != null) {
			Log.log(5, "ApplicationProcessingAction", "getApps", "mli Info.. :" + mliInfo);

			String statusFlag = mliInfo.getStatus();
			if (statusFlag.equals("I")) {
				throw new NoDataException("Member Id:" + memberId + "  has been deactivated.");
			}

		}

		HashMap details = processor.getMLIWiseDANDetails(memberId);
		Vector mliWiseDanDetails = (Vector) details.get("pending_dtls");
		rpActionForm.setMliWiseDanDetails(mliWiseDanDetails);
		Log.log(4, "RPAction", "getPPMLIWiseDetails", "Exited");

		return mapping.findForward("getDetails");
	}

	public ActionForward getPPDateWiseDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "getPPDateWiseDetails", "Entered");
		RpProcessor processor = new RpProcessor();
		RPActionForm rpActionForm = (RPActionForm) form;
		Vector dateWiseDANDetails = null;
		java.util.Date fromDate = rpActionForm.getFromDate();

		java.util.Date toDate = rpActionForm.getToDate();
		if (toDate.toString().equals("")) {
			toDate = new java.util.Date(System.currentTimeMillis());
		}

		java.sql.Date sqlFromDate = null;
		if (fromDate.toString().equals("")) {
			dateWiseDANDetails = processor.getDateWiseDANDetails(null, new java.sql.Date(toDate.getTime()));
		} else {
			sqlFromDate = new java.sql.Date(fromDate.getTime());
			dateWiseDANDetails = processor.getDateWiseDANDetails(sqlFromDate, new java.sql.Date(toDate.getTime()));
		}
		rpActionForm.setDateWiseDANDetails(dateWiseDANDetails);
		Log.log(4, "RPAction", "getPPDateWiseDetails", "Exited");

		return mapping.findForward("getDetails");
	}

	public ActionForward afterMemberInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RPActionForm rpAllocationForm = (RPActionForm) form;
		RpProcessor rpProcessor = new RpProcessor();
		ArrayList demandAdviceList = new ArrayList();

		String forward = "";

		String mliId = rpAllocationForm.getSelectMember();
		String bankId = mliId.substring(0, 4);
		String zoneId = mliId.substring(4, 8);
		String branchId = mliId.substring(8, 12);

		ClaimsProcessor cpProcessor = new ClaimsProcessor();
		Vector memberIds = cpProcessor.getAllMemberIds();
		if (!memberIds.contains(mliId)) {
			throw new NoMemberFoundException("The Member ID does not exist");
		}

		demandAdviceList = rpProcessor.showShortDansForWaive(bankId, zoneId, branchId);

		if ((demandAdviceList == null) || (demandAdviceList.size() == 0)) {
			request.setAttribute("message", "No Applications For Waive Short DAN Amounts");
			forward = "success";
		} else {
			rpAllocationForm.setPaymentDetails(demandAdviceList);
			forward = "waiveShortAmntsDisplay";
		}

		return mapping.findForward(forward);
	}

	public ActionForward waiveShortDans(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(5, "RPAction", "waiveShortDANs", "entered");
		RPActionForm rpAllocationForm = (RPActionForm) form;
		RpProcessor rpProcessor = new RpProcessor();

		Map danNoMap = rpAllocationForm.getDanIds();

		Map waivedFlagMap = rpAllocationForm.getWaivedFlags();
		Set waivedFlagSet = waivedFlagMap.keySet();
		Iterator waivedFlagIterator = waivedFlagSet.iterator();

		while (waivedFlagIterator.hasNext()) {
			String key = (String) waivedFlagIterator.next();
			Log.log(5, "RPAction", "waiveShortDANs", "key :" + key);
			String shdan = (String) danNoMap.get(key);
			Log.log(5, "RPAction", "waiveShortDANs", "key :" + shdan);
			rpProcessor.waiveShortDANs(key.replace('_', '.'));
		}

		request.setAttribute("message", "Short DANs Waived Sucessfully.");

		return mapping.findForward("success");
	}

	public ActionForward afterShowMliForRefAdv(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RPActionForm rpAllocationForm = (RPActionForm) form;
		RpProcessor rpProcessor = new RpProcessor();

		String mliId = rpAllocationForm.getSelectMember();

		ClaimsProcessor cpProcessor = new ClaimsProcessor();
		Vector memberIds = cpProcessor.getAllMemberIds();
		if (!memberIds.contains(mliId)) {
			throw new NoMemberFoundException("The Member ID does not exist");
		}

		double refAmount = rpProcessor.getRefundAmountForMember(mliId);

		rpAllocationForm.setRefundAmount(refAmount);

		return mapping.findForward("success");
	}

	public ActionForward generateRefAdv(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RPActionForm rpAllocationForm = (RPActionForm) form;
		RpProcessor rpProcessor = new RpProcessor();

		String mliId = rpAllocationForm.getSelectMember();

		User user = getUserInformation(request);
		String userId = user.getUserId();

		String refAdvNumber = rpProcessor.generateRefundAdvice(mliId, userId);

		String message = "Refund Advice Generated. Refund Advice Number: " + refAdvNumber;
		request.setAttribute("message", message);

		return mapping.findForward("success");
	}

	public ActionForward getPaymentList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RPActionForm rpAllocationForm = (RPActionForm) form;
		RpProcessor rpProcessor = new RpProcessor();

		java.util.Date fromDate = rpAllocationForm.getFromDate();
		java.util.Date toDate = rpAllocationForm.getToDate();
		Log.log(4, "RPAction", "getPaymentList", " from date " + fromDate);
		Log.log(4, "RPAction", "getPaymentList", " to date " + toDate);
		String dateType = rpAllocationForm.getDateType();
		User user = getUserInformation(request);
		String memberId = "";
		if (user.getBankId().equals("0000")) {
			memberId = rpAllocationForm.getSelectMember();
			ClaimsProcessor cpProcessor = new ClaimsProcessor();
			Vector memberIds = cpProcessor.getAllMemberIds();
			if (!memberIds.contains(memberId)) {
				throw new NoMemberFoundException("Member Id does not exist");
			}

		} else {
			memberId = rpAllocationForm.getSelectMember();
			Log.log(4, "RPAction", "getPaymentList", " member id " + memberId);
		}
		ArrayList paymentIds = rpProcessor.getPaymentList(fromDate, toDate, dateType, memberId);

		rpAllocationForm.setPaymentList(paymentIds);

		return mapping.findForward("success");
	}

	public ActionForward showCGDANGenFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaActionForm rpAllocationForm = (DynaActionForm) form;
		rpAllocationForm.initialize(mapping);
		ArrayList memberNames = this.registration.getAllMembers();
		ArrayList memberDetails = new ArrayList(memberNames.size());

		for (int i = 0; i < memberNames.size(); i++) {
			MLIInfo mliInfo = (MLIInfo) memberNames.get(i);
			String mli = "";
			mli = "(" + mliInfo.getBankId() + mliInfo.getZoneId() + mliInfo.getBranchId() + ")"
					+ mliInfo.getShortName();

			if ((mliInfo.getBranchName() != null) && (!mliInfo.getBranchName().equals(""))
					&& ((mliInfo.getZoneName() == null) || (mliInfo.getZoneName().equals("")))) {
				mli = mli + "," + mliInfo.getBranchName();
			} else if (((mliInfo.getBranchName() == null) || (mliInfo.getBranchName().equals("")))
					&& (mliInfo.getZoneName() != null) && (!mliInfo.getZoneName().equals(""))) {
				mli = mli + "," + mliInfo.getZoneName();
			} else if ((mliInfo.getBranchName() != null) && (!mliInfo.getBranchName().equals(""))
					&& (mliInfo.getZoneName() != null) && (!mliInfo.getZoneName().equals(""))) {
				mli = mli + "," + mliInfo.getZoneName() + "," + mliInfo.getBranchName();
			}

			memberDetails.add(mli);
		}
		Log.log(4, "RPAction", "generateCGDAN", "Fetched Member Details for whom CGDAN has to be generated");
		rpAllocationForm.set("mliNames", memberDetails);
		HttpSession session = request.getSession(false);
		session.setAttribute("TARGET_URL", "generateCGDAN.do?method=generateCGDAN");

		session.setAttribute("DAN_TYPE", "CGDAN");

		return mapping.findForward("memberInfo");
	}

	public ActionForward showSFDANGenFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaActionForm rpAllocationForm = (DynaActionForm) form;
		rpAllocationForm.initialize(mapping);

		ArrayList memberNames = this.registration.getAllMembers();
		ArrayList memberDetails = new ArrayList(memberNames.size());

		for (int i = 0; i < memberNames.size(); i++) {
			MLIInfo mliInfo = (MLIInfo) memberNames.get(i);
			String mli = "";

			mli = "(" + mliInfo.getBankId() + mliInfo.getZoneId() + mliInfo.getBranchId() + ")"
					+ mliInfo.getShortName();

			if ((mliInfo.getBranchName() != null) && (!mliInfo.getBranchName().equals(""))
					&& ((mliInfo.getZoneName() == null) || (mliInfo.getZoneName().equals("")))) {
				mli = mli + "," + mliInfo.getBranchName();
			} else if (((mliInfo.getBranchName() == null) || (mliInfo.getBranchName().equals("")))
					&& (mliInfo.getZoneName() != null) && (!mliInfo.getZoneName().equals(""))) {
				mli = mli + "," + mliInfo.getZoneName();
			} else if ((mliInfo.getBranchName() != null) && (!mliInfo.getBranchName().equals(""))
					&& (mliInfo.getZoneName() != null) && (!mliInfo.getZoneName().equals(""))) {
				mli = mli + "," + mliInfo.getZoneName() + "," + mliInfo.getBranchName();
			}

			memberDetails.add(mli);
		}
		Log.log(4, "RPAction", "generateCGDAN", "Fetched Member Details for whom CGDAN has to be generated");
		rpAllocationForm.set("mliNames", memberDetails);
		HttpSession session = request.getSession(false);
		session.setAttribute("TARGET_URL", "generateSFDAN.do?method=generateSFDAN");

		session.setAttribute("DAN_TYPE", "SFDAN");

		return mapping.findForward("memberInfo");
	}

	public ActionForward showBatchSFDANGenFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaActionForm rpAllocationForm = (DynaActionForm) form;
		rpAllocationForm.initialize(mapping);

		ArrayList memberNames = this.registration.getAllHOMembers();
		ArrayList memberDetails = new ArrayList(memberNames.size());

		for (int i = 0; i < memberNames.size(); i++) {
			MLIInfo mliInfo = (MLIInfo) memberNames.get(i);
			String mli = "";

			mli = "(" + mliInfo.getBankId() + mliInfo.getZoneId() + mliInfo.getBranchId() + ")" + mliInfo.getBankName();

			if ((mliInfo.getBranchName() != null) && (!mliInfo.getBranchName().equals(""))
					&& ((mliInfo.getZoneName() == null) || (mliInfo.getZoneName().equals("")))) {
				mli = mli + "," + mliInfo.getBranchName();
			} else if (((mliInfo.getBranchName() == null) || (mliInfo.getBranchName().equals("")))
					&& (mliInfo.getZoneName() != null) && (!mliInfo.getZoneName().equals(""))) {
				mli = mli + "," + mliInfo.getZoneName();
			} else if ((mliInfo.getBranchName() != null) && (!mliInfo.getBranchName().equals(""))
					&& (mliInfo.getZoneName() != null) && (!mliInfo.getZoneName().equals(""))) {
				mli = mli + "," + mliInfo.getZoneName() + "," + mliInfo.getBranchName();
			}

			memberDetails.add(mli);
		}
		Log.log(4, "RPAction", "showBatchSFDANGenFilter", "Fetched Member Details for whom CGDAN has to be generated");
		rpAllocationForm.set("mliNames", memberDetails);
		HttpSession session = request.getSession(false);
		session.setAttribute("TARGET_URL", "generateBatchSFDAN.do?method=generateBatchSFDAN");

		session.setAttribute("DAN_TYPE", "BATCHSFDAN");

		return mapping.findForward("memberInfo");
	}

	public ActionForward generateBatchSFDAN(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String methodName = "generateBatchSFDAN";
		Log.log(4, "RPAction", methodName, "Entered");
		DynaActionForm dynaForm = (DynaActionForm) form;
		String mliName = (String) dynaForm.get("selectMember");
		Log.log(4, "RPAction", methodName, "Selected Member Id : " + mliName);
		String forwardPage = "";

		User user = getUserInformation(request);
		Log.log(4, "RPAction", "generateBatchSFDAN", "Logged in user: " + user.getUserId());

		if (mliName.equals("")) {
			Log.log(5, "RPAction", methodName, "Fetching Member Details for whom SFDAN has to be generated");
			ArrayList memberNames = this.registration.getAllMembers();
			ArrayList memberDetails = new ArrayList(memberNames.size());

			for (int i = 0; i < memberNames.size(); i++) {
				MLIInfo mliInfo = (MLIInfo) memberNames.get(i);
				String mli = "";

				mli = "(" + mliInfo.getBankId() + mliInfo.getZoneId() + mliInfo.getBranchId() + ")"
						+ mliInfo.getShortName();

				if ((mliInfo.getBranchName() != null) && (!mliInfo.getBranchName().equals(""))
						&& ((mliInfo.getZoneName() == null) || (mliInfo.getZoneName().equals("")))) {
					mli = mli + "," + mliInfo.getBranchName();
				} else if (((mliInfo.getBranchName() == null) || (mliInfo.getBranchName().equals("")))
						&& (mliInfo.getZoneName() != null) && (!mliInfo.getZoneName().equals(""))) {
					mli = mli + "," + mliInfo.getZoneName();
				} else if ((mliInfo.getBranchName() != null) && (!mliInfo.getBranchName().equals(""))
						&& (mliInfo.getZoneName() != null) && (!mliInfo.getZoneName().equals(""))) {
					mli = mli + "," + mliInfo.getZoneName() + "," + mliInfo.getBranchName();
				}

				memberDetails.add(mli);
			}
			Log.log(5, "RPAction", methodName, "Fetched Member Details for whom SFDAN has to be generated");
			dynaForm.set("mliNames", memberDetails);
			request.setAttribute("TARGET_URL", MenuOptions.getMenuAction("RP_GENERATE_BATCH_SFDAN"));
			HttpSession session = request.getSession(false);
			session.setAttribute("DAN_TYPE", "BATCHSFDAN");
			forwardPage = "memberInfo";
		} else {
			forwardPage = "";
			String message = "";
			try {
				Log.log(5, "RPAction", methodName, "Entering routine to generate Batch SFDAN for all members");
				RpProcessor rpProcessor = new RpProcessor();

				if (mliName.equalsIgnoreCase("All")) {
					request.setAttribute("message", " No Applications Available For SFDAN Generation");
				} else {
					mliName = mliName.substring(1, 13);
					System.out.println("MLI Name:" + mliName);
					rpProcessor.generateBatchSFDAN(user, mliName.substring(0, 4));
					message = "Batch SFDAN generated successfully";
				}
				request.setAttribute("message", "Batch SFDAN generated successfully");
				forwardPage = "success";
			} catch (Exception exp) {
				forwardPage = "success1";
				request.setAttribute("message", " No Applications Available For SFDAN Generation");
			}
		}
		Log.log(4, "RPAction", methodName, "Exited");

		return mapping.findForward(forwardPage);
	}

	public ActionForward showSFDANGenFilterForExpired(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		DynaActionForm rpAllocationForm = (DynaActionForm) form;
		rpAllocationForm.initialize(mapping);

		ArrayList memberNames = this.registration.getAllMembers();
		ArrayList memberDetails = new ArrayList(memberNames.size());

		for (int i = 0; i < memberNames.size(); i++) {
			MLIInfo mliInfo = (MLIInfo) memberNames.get(i);
			String mli = "";

			mli = "(" + mliInfo.getBankId() + mliInfo.getZoneId() + mliInfo.getBranchId() + ")"
					+ mliInfo.getShortName();

			if ((mliInfo.getBranchName() != null) && (!mliInfo.getBranchName().equals(""))
					&& ((mliInfo.getZoneName() == null) || (mliInfo.getZoneName().equals("")))) {
				mli = mli + "," + mliInfo.getBranchName();
			} else if (((mliInfo.getBranchName() == null) || (mliInfo.getBranchName().equals("")))
					&& (mliInfo.getZoneName() != null) && (!mliInfo.getZoneName().equals(""))) {
				mli = mli + "," + mliInfo.getZoneName();
			} else if ((mliInfo.getBranchName() != null) && (!mliInfo.getBranchName().equals(""))
					&& (mliInfo.getZoneName() != null) && (!mliInfo.getZoneName().equals(""))) {
				mli = mli + "," + mliInfo.getZoneName() + "," + mliInfo.getBranchName();
			}

			memberDetails.add(mli);
		}
		Log.log(4, "RPAction", "showSFDANGenFilterForExpired",
				"Fetched Member Details for whom showSFDANGenFilterForExpired has to be generated");
		rpAllocationForm.set("mliNames", memberDetails);
		HttpSession session = request.getSession(false);
		session.setAttribute("TARGET_URL", "generateSFDANEXP.do?method=generateSFDANEXP");
		session.setAttribute("DAN_TYPE", "SFDANEXP");

		return mapping.findForward("memberInfo");
	}

	public ActionForward showSHDANGenFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaActionForm rpAllocationForm = (DynaActionForm) form;
		rpAllocationForm.initialize(mapping);

		RpProcessor rpProcessor = new RpProcessor();

		String forwardPage = "";

		ArrayList memberIds = rpProcessor.getMemberIdsForSHDAN();
		if ((memberIds != null) && (memberIds.size() != 0)) {
			ArrayList memberDetails = new ArrayList(memberIds.size());
			for (int i = 0; i < memberIds.size(); i++) {
				String memberId = (String) memberIds.get(i);
				MLIInfo mliInfo = this.registration.getMemberDetails(memberId.substring(0, 4), memberId.substring(4, 8),
						memberId.substring(8, 12));
				String mli = "";
				mli = "(" + memberId.substring(0, 4) + memberId.substring(4, 8) + memberId.substring(8, 12) + ")"
						+ mliInfo.getShortName();

				if ((mliInfo.getBranchName() != null) && (!mliInfo.getBranchName().equals(""))
						&& ((mliInfo.getZoneName() == null) || (mliInfo.getZoneName().equals("")))) {
					mli = mli + "," + mliInfo.getBranchName();
				} else if (((mliInfo.getBranchName() == null) || (mliInfo.getBranchName().equals("")))
						&& (mliInfo.getZoneName() != null) && (!mliInfo.getZoneName().equals(""))) {
					mli = mli + "," + mliInfo.getZoneName();
				} else if ((mliInfo.getBranchName() != null) && (!mliInfo.getBranchName().equals(""))
						&& (mliInfo.getZoneName() != null) && (!mliInfo.getZoneName().equals(""))) {
					mli = mli + "," + mliInfo.getZoneName() + "," + mliInfo.getBranchName();
				}

				memberDetails.add(mli);
			}

			rpAllocationForm.set("mliNames", memberDetails);
			forwardPage = "memberInfo";
			HttpSession session = request.getSession(false);
			session.setAttribute("TARGET_URL", "generateSHDAN.do?method=generateSHDAN");

			session.setAttribute("DAN_TYPE", "SHDAN");
		} else {
			request.setAttribute("message", "No Members available for SHDAN Generation");
			forwardPage = "success";
		}

		Log.log(4, "RPAction", "generateCGDAN", "Fetched Member Details for whom CGDAN has to be generated");

		return mapping.findForward(forwardPage);
	}

	public ActionForward showCLDANGenFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String methodName = "showCLDANGenFilter";
		Log.log(4, "RPAction", methodName, "Entered");
		String forwardPage = "";

		User user = getUserInformation(request);
		Log.log(4, "RPAction", "generateCLDAN", "Logged in user: " + user.getUserId());

		DynaActionForm rpAllocationForm = (DynaActionForm) form;
		rpAllocationForm.initialize(mapping);

		Log.log(5, "RPAction", methodName, "Fetching Member Details for whom CLDAN has to be generated");
		ArrayList memberNames = this.registration.getAllMembers();
		ArrayList memberDetails = new ArrayList(memberNames.size());

		for (int i = 0; i < memberNames.size(); i++) {
			MLIInfo mliInfo = (MLIInfo) memberNames.get(i);
			String mli = "";

			mli = "(" + mliInfo.getBankId() + mliInfo.getZoneId() + mliInfo.getBranchId() + ")"
					+ mliInfo.getShortName();

			if ((mliInfo.getBranchName() != null) && (!mliInfo.getBranchName().equals(""))
					&& ((mliInfo.getZoneName() == null) || (mliInfo.getZoneName().equals("")))) {
				mli = mli + "," + mliInfo.getBranchName();
			} else if (((mliInfo.getBranchName() == null) || (mliInfo.getBranchName().equals("")))
					&& (mliInfo.getZoneName() != null) && (!mliInfo.getZoneName().equals(""))) {
				mli = mli + "," + mliInfo.getZoneName();
			} else if ((mliInfo.getBranchName() != null) && (!mliInfo.getBranchName().equals(""))
					&& (mliInfo.getZoneName() != null) && (!mliInfo.getZoneName().equals(""))) {
				mli = mli + "," + mliInfo.getZoneName() + "," + mliInfo.getBranchName();
			}

			memberDetails.add(mli);
		}
		Log.log(5, "RPAction", methodName, "Fetched Member Details for whom CLDAN has to be generated");

		rpAllocationForm.set("mliNames", memberDetails);
		HttpSession session = request.getSession(false);
		session.setAttribute("TARGET_URL", "generateCLDAN.do?method=generateCLDAN");

		session.setAttribute("DAN_TYPE", "CLDAN");
		forwardPage = "memberInfo";
		Log.log(4, "RPAction", methodName, "Exited");

		return mapping.findForward(forwardPage);
	}

	public ActionForward getGLName(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "getGLName", "Entered");

		RPActionForm actionForm = (RPActionForm) form;

		String glCode = actionForm.getBankGLCode();
		Log.log(4, "RPAction", "getGLName", "code " + glCode);

		String glName = "";
		if (!glCode.equals("")) {
			RpProcessor rpProcessor = new RpProcessor();
			glName = rpProcessor.getGLName(glCode);
		}

		request.setAttribute("IsRequired", null);

		actionForm.setBankGLName(glName);

		HttpSession session = request.getSession(false);
		session.setAttribute("VOUCHER_FLAG", "2");

		Log.log(4, "RPAction", "getGLName", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward generateExcessVoucherFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String methodName = "generateExcessVoucherFilter";
		Log.log(4, "RPAction", methodName, "Entered");
		String forwardPage = "";

		User user = getUserInformation(request);
		Log.log(5, "RPAction", methodName, "Logged in user: " + user.getUserId());

		RpProcessor rpProcessor = new RpProcessor();
		DynaActionForm rpAllocationForm = (DynaActionForm) form;
		rpAllocationForm.initialize(mapping);

		ArrayList memberIds = rpProcessor.getMemberIdsForExcess();
		if ((memberIds != null) && (memberIds.size() != 0)) {
			ArrayList memberDetails = new ArrayList(memberIds.size());
			for (int i = 0; i < memberIds.size(); i++) {
				String memberId = (String) memberIds.get(i);
				MLIInfo mliInfo = this.registration.getMemberDetails(memberId.substring(0, 4), memberId.substring(4, 8),
						memberId.substring(8, 12));
				String mli = "";
				mli = "(" + memberId.substring(0, 4) + memberId.substring(4, 8) + memberId.substring(8, 12) + ")"
						+ mliInfo.getShortName();

				if ((mliInfo.getBranchName() != null) && (!mliInfo.getBranchName().equals(""))
						&& ((mliInfo.getZoneName() == null) || (mliInfo.getZoneName().equals("")))) {
					mli = mli + "," + mliInfo.getBranchName();
				} else if (((mliInfo.getBranchName() == null) || (mliInfo.getBranchName().equals("")))
						&& (mliInfo.getZoneName() != null) && (!mliInfo.getZoneName().equals(""))) {
					mli = mli + "," + mliInfo.getZoneName();
				} else if ((mliInfo.getBranchName() != null) && (!mliInfo.getBranchName().equals(""))
						&& (mliInfo.getZoneName() != null) && (!mliInfo.getZoneName().equals(""))) {
					mli = mli + "," + mliInfo.getZoneName() + "," + mliInfo.getBranchName();
				}

				memberDetails.add(mli);
			}

			rpAllocationForm.set("mliNames", memberDetails);
			forwardPage = "memberInfo";
		} else {
			request.setAttribute("message", "No Members available for Voucher Generation");
			forwardPage = "success";
		}

		HttpSession session = request.getSession(false);
		session.setAttribute("TARGET_URL", "generateExcessVoucher.do?method=generateExcessVoucher");

		session.setAttribute("DAN_TYPE", "EXCESS");

		return mapping.findForward(forwardPage);
	}

	public ActionForward generateExcessVoucher(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RpProcessor rpProcessor = new RpProcessor();

		DynaActionForm rpGenerateDANForm = (DynaActionForm) form;
		String mliName = (String) rpGenerateDANForm.get("selectMember");
		Log.log(4, "RPAction", "generateCGDAN", "Selected Member Id : " + mliName);

		User user = getUserInformation(request);

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		Properties accCodes = new Properties();
		String contextPath = request.getSession(false).getServletContext().getRealPath("");
		Log.log(5, "RPAction", "getPaymentsMade", "path " + contextPath);
		File tempFile = new File(contextPath + "\\WEB-INF\\classes", "AccountCodes.properties");
		Log.log(5, "RPAction", "getPaymentsMade", "file opened ");
		File accCodeFile = new File(tempFile.getAbsolutePath());
		try {
			FileInputStream fin = new FileInputStream(accCodeFile);
			accCodes.load(fin);
		} catch (FileNotFoundException fe) {
			throw new MessageException("Could not load Account Codes.");
		} catch (IOException ie) {
			throw new MessageException("Could not load Account Codes.");
		}
		double voucherAmount;
		if (!mliName.equals("All")) {
			VoucherDetail voucherDetail = new VoucherDetail();
			ArrayList vouchers = new ArrayList();

			mliName = mliName.substring(1, 13);

			voucherAmount = rpProcessor.getAmountForExcess(mliName);

			voucherDetail.setBankGLName("");
			voucherDetail.setAmount(voucherAmount);
			voucherDetail.setBankGLCode(accCodes.getProperty("bank_ac"));
			voucherDetail.setDeptCode("CG");
			voucherDetail.setVoucherType("PAYMENT VOUCHER");
			Voucher voucher = new Voucher();
			voucher.setAcCode(accCodes.getProperty("excess_ac"));
			voucher.setPaidTo("CGTSI");
			voucher.setDebitOrCredit("C");
			voucher.setInstrumentDate(dateFormat.format(new java.util.Date()));
			voucher.setInstrumentNo(null);
			voucher.setInstrumentType(null);
			voucher.setAmountInRs("-" + voucherAmount);
			vouchers.add(voucher);

			String narration = "";

			narration = narration + " Member Id: " + mliName;
			narration = narration + " Voucher Amount: " + voucherAmount;

			voucherDetail.setNarration(narration);
			voucherDetail.setVouchers(vouchers);

			String voucherId = rpProcessor.insertVoucherDetails(voucherDetail, user.getUserId());
			rpProcessor.updateIdForExcess(mliName, voucherId);
			vouchers.clear();
			voucherDetail = null;
		} else if (mliName.equals("All")) {
			ArrayList memberIds = rpProcessor.getMemberIdsForExcess();
			for (int i = 0; i < memberIds.size(); i++) {
				VoucherDetail voucherDetail = new VoucherDetail();
				ArrayList vouchers = new ArrayList();

				mliName = (String) memberIds.get(i);
				voucherAmount = rpProcessor.getAmountForExcess(mliName);

				voucherDetail.setBankGLName("");
				voucherDetail.setAmount(voucherAmount);
				voucherDetail.setBankGLCode(accCodes.getProperty("bank_ac"));
				voucherDetail.setDeptCode("CG");
				voucherDetail.setVoucherType("PAYMENT VOUCHER");
				Voucher voucher = new Voucher();
				voucher.setAcCode(accCodes.getProperty("excess_ac"));
				voucher.setPaidTo("CGTSI");
				voucher.setDebitOrCredit("C");
				voucher.setInstrumentDate(dateFormat.format(new java.util.Date()));
				voucher.setInstrumentNo(null);
				voucher.setInstrumentType(null);
				voucher.setAmountInRs("-" + voucherAmount);
				vouchers.add(voucher);

				String narration = "";

				narration = narration + " Member Id: " + mliName;
				narration = narration + " Voucher Amount: " + voucherAmount;

				voucherDetail.setNarration(narration);
				voucherDetail.setVouchers(vouchers);

				String voucherId = rpProcessor.insertVoucherDetails(voucherDetail, user.getUserId());
				rpProcessor.updateIdForExcess(mliName, voucherId);

				vouchers.clear();
				voucherDetail = null;
			}

		}

		request.setAttribute("message", "Voucher details are stored successfully");

		return mapping.findForward("success");
	}

	public ActionForward showAllCardRates(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RpProcessor rpProcessor = new RpProcessor();

		RPActionForm actionForm = (RPActionForm) form;

		ArrayList cardRateList = rpProcessor.getAllCardRates();
		actionForm.setGfCardRateList(cardRateList);

		return mapping.findForward("success");
	}

	public ActionForward saveGFCardRates(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "saveGFCardRates", "Entered");
		RpProcessor rpProcessor = new RpProcessor();

		RPActionForm actionForm = (RPActionForm) form;

		User user = getUserInformation(request);

		Map rateId = new TreeMap();
		Map gfLowAmount = new TreeMap();
		Map gfLowHigh = new TreeMap();
		Map gfCardRate = new TreeMap();

		rateId = actionForm.getRateId();
		gfLowAmount = actionForm.getLowAmount();
		gfLowHigh = actionForm.getHighAmount();
		gfCardRate = actionForm.getGfRate();

		Set rateIdSet = rateId.keySet();
		Iterator rateIdIterator = rateIdSet.iterator();

		while (rateIdIterator.hasNext()) {
			String key = (String) rateIdIterator.next();
			int id = Integer.parseInt((String) rateId.get(key));

			double cardRate = Double.parseDouble((String) gfCardRate.get(key));

			Log.log(4, "RPAction", "saveGFCardRates", "cardRate ;" + cardRate);
			Log.log(4, "RPAction", "saveGFCardRates", "id :" + id);

			rpProcessor.updateCardRate(id, cardRate, user.getUserId());
		}

		request.setAttribute("message", "Guarantee Fee Card Rates Updated Successfully");

		Log.log(4, "RPAction", "saveGFCardRates", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward rpCancelPayments(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "ReportsAction", "rpCancelPayments", "Entered");
		RPActionForm actionForm = (RPActionForm) form;

		Log.log(4, "ReportsAction", "rpCancelPayments", "Exited");

		return mapping.findForward("success");
	}

	public ActionForward cancelRpAppropriation(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "cancelRpAppropriation", "Entered");
		ReportManager manager = new ReportManager();
		RPActionForm rpForm = (RPActionForm) form;

		String clmApplicationStatus = "";
		Log.log(4, "ReportsAction", "displayTCQueryDetail", "Claim Application Status being queried ");

		User user = getUserInformation(request);
		String userid = user.getUserId().trim();
		String bankid = user.getBankId().trim();
		String zoneid = user.getZoneId().trim();
		String branchid = user.getBranchId().trim();
		String memberId = bankid + zoneid + branchid;
		String paymentId = null;
		String instrumentNo = null;
		String remarks = null;

		paymentId = request.getParameter("paymentId");
		remarks = request.getParameter("remarksforAppropriation");
		instrumentNo = request.getParameter("instrumentNo");

		if ((paymentId != null) && (instrumentNo != null)) {
			Connection connection = DBConnection.getConnection(false);
			try {
				CallableStatement callable = connection.prepareCall("{?=call funccancelappropriation(?,?,?,?,?)}");

				callable.registerOutParameter(1, 4);
				callable.setString(2, paymentId);
				callable.setString(3, instrumentNo);
				callable.setString(4, userid);
				callable.setString(5, remarks);
				callable.registerOutParameter(6, 12);
				callable.execute();
				int errorCode = callable.getInt(1);
				String error = callable.getString(6);
				Log.log(5, "RPAction", "cancelRpAppropriation", "Error code and error are " + errorCode + " " + error);

				if (errorCode == 1) {
					connection.rollback();
					callable.close();
					callable = null;
					throw new DatabaseException(error);
				}
				callable.close();
				callable = null;
				connection.commit();
			} catch (SQLException e) {
				try {
					connection.rollback();
				} catch (SQLException ignore) {
					Log.log(2, "RPAction", "cancelRpAppropriation", ignore.getMessage());
				}

				Log.log(2, "RPAction", "cancelRpAppropriation", e.getMessage());
				Log.logException(e);
			} finally {
				DBConnection.freeConnection(connection);
			}

		}

		return mapping.findForward("success");
	}

	// added by upchar@path on 07-05-2014 for payment allocation

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPendingAFDANsLive(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RPActionForm rpForm = (RPActionForm) form;
		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = "";

		if (rpForm.getDanSummaries() != null) {
			rpForm.getDanSummaries().clear();
		}
		if (rpForm.getDanPanDetails() != null) {
			rpForm.getDanPanDetails().clear();
		}
		if (rpForm.getCgpans() != null) {
			rpForm.getCgpans().clear();
		}
		if (rpForm.getAllocatedFlags() != null) {
			rpForm.getAllocatedFlags().clear();
		}
		if (rpForm.getFirstDisbursementDates() != null) {
			rpForm.getFirstDisbursementDates().clear();
		}
		if (rpForm.getNotAllocatedReasons() != null) {
			rpForm.getNotAllocatedReasons().clear();
		}
		if (rpForm.getAllocatedFlags() != null) {
			rpForm.getAllocatedFlags().clear();
		}
		if (rpForm.getAppropriatedFlags() != null) {
			rpForm.getAppropriatedFlags().clear();
		}
		if (rpForm.getAmountsRaised() != null) {
			rpForm.getAmountsRaised().clear();
		}
		if (rpForm.getAmountBeingPaid() != null) {
			rpForm.getAmountBeingPaid().clear();
		}

		if (bankId.equals("0000")) {
			memberId = rpForm.getSelectMember();

			// if ((memberId == null) || (memberId.equals(""))) {
			// memberId = rpForm.getMemberId();
			// }

			Log.log(5, "RPAction", "getNEFTPendingGFDANs", "mliId = " + memberId);

			if ((memberId == null) || (memberId.equals(""))) {
				// session.setAttribute("TARGET_URL",
				// "allocatePaymentsAll.do?method=getPendingAFDANsLive");
				return mapping.findForward("liveafmember");
			}
			bankId = memberId.substring(0, 4);
			zoneId = memberId.substring(4, 8);
			branchId = memberId.substring(8, 12);

			ClaimsProcessor cpProcessor = new ClaimsProcessor();
			Vector memberIds = cpProcessor.getAllMemberIds();
			if (!memberIds.contains(memberId)) {
				throw new NoMemberFoundException("The Member ID does not exist");
			}
		}
		RpProcessor rpProcessor = new RpProcessor();
		ArrayList danSummaries = rpProcessor.displayLiveAFDANs(bankId, zoneId, branchId);

		Log.log(5, "RPAction", "getNEFTPendingGFDANs", "dan summary size : " + danSummaries.size());

		if (danSummaries.size() == 0) {
			rpForm.setSelectMember(null);
			throw new MissingDANDetailsException("No DANs available for Allocation");
		}

		boolean isDanAvailable = false;
		for (int i = 0; i < danSummaries.size(); i++) {
			DANSummary danSummary = (DANSummary) danSummaries.get(i);
			if (danSummary.getAmountDue() != danSummary.getAmountPaid()) {
				isDanAvailable = true;
				break;
			}
		}
		if (!isDanAvailable) {
			rpForm.setSelectMember(null);
			throw new MissingDANDetailsException("No DANs available for Allocation");
		}
		rpForm.setDanSummaries(danSummaries);
		rpForm.setBankId(bankId);
		rpForm.setZoneId(zoneId);
		rpForm.setBranchId(branchId);

		Log.log(4, "RPAction", "getNEFTPendingGFDANs", "Exited");
		if (rpForm.getSelectMember() != null) {
			rpForm.setMemberId(rpForm.getSelectMember());
		} else {
			rpForm.setMemberId(bankId + zoneId + branchId);
		}
		rpForm.setSelectMember(null);
		return mapping.findForward("liveafdansummary");
	}


	public ActionForward getPendingGFDANsLiveOnline(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("getPendingGFDANsLiveOnline ");
		RPActionForm rpForm = (RPActionForm) form;
		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();

		String mem2 = bankId.concat(zoneId).concat(branchId);
		System.out.println("getPendingGFDANsLiveOnline mem2" + mem2);
		String memberId = "";

		if (rpForm.getDanSummaries() != null) {
			rpForm.getDanSummaries().clear();
		}
		if (rpForm.getDanPanDetails() != null) {
			rpForm.getDanPanDetails().clear();
		}
		if (rpForm.getCgpans() != null) {
			rpForm.getCgpans().clear();
		}
		if (rpForm.getAllocatedFlags() != null) {
			rpForm.getAllocatedFlags().clear();
		}
		if (rpForm.getFirstDisbursementDates() != null) {
			rpForm.getFirstDisbursementDates().clear();
		}
		if (rpForm.getNotAllocatedReasons() != null) {
			rpForm.getNotAllocatedReasons().clear();
		}
		if (rpForm.getAllocatedFlags() != null) {
			rpForm.getAllocatedFlags().clear();
		}
		if (rpForm.getAppropriatedFlags() != null) {
			rpForm.getAppropriatedFlags().clear();
		}
		if (rpForm.getAmountsRaised() != null) {
			rpForm.getAmountsRaised().clear();
		}
		if (rpForm.getAmountBeingPaid() != null) {
			rpForm.getAmountBeingPaid().clear();
		}

		if (bankId.equals("0000")) {
			memberId = rpForm.getSelectMember();
			memberId = mem2;
			// if ((memberId == null) || (memberId.equals(""))) {
			// memberId = rpForm.getMemberId();
			// }

			Log.log(5, "RPAction", "getNEFTPendingGFDANs", "mliId = " + memberId);
			System.out.println("getPendingGFDANsLiveOnline memberId " + memberId);

			if ((memberId == null) || (memberId.equals(""))) {
				// session.setAttribute("TARGET_URL",
				// "allocatePaymentsAll.do?method=getPendingGFDANsLiveOnline");
				return mapping.findForward("liveafmemberOnline");
			}

			System.out.println("getPendingGFDANsLiveOnline memberId after liveafmemberOnline " + memberId);
			bankId = memberId.substring(0, 4);
			zoneId = memberId.substring(4, 8);
			branchId = memberId.substring(8, 12);
			
			//SimpleDateFormat sm = new SimpleDateFormat("dd/mm/yyyy");
			//SimpleDateFormat sm1 = new SimpleDateFormat("dd/mm/yyyy");
			java.util.Date date = new java.util.Date();
			//System.out.println(" $$$$$$$$$$$$" + sm.format(date));
			rpForm.setDtpaymentDate((date));
			 
			

			//ClaimsProcessor cpProcessor = new ClaimsProcessor();
			//Vector memberIds = cpProcessor.getAllMemberIds();
			//if (!memberIds.contains(memberId)) 
			//{
				//throw new NoMemberFoundException("The Member ID does not exist");
			//}
		}
		/*
		 * RpProcessor rpProcessor = new RpProcessor(); ArrayList danSummaries =
		 * rpProcessor.displayLiveGFDANs(bankId, zoneId, branchId);
		 * 
		 * 
		 * Log.log(5, "RPAction", "getNEFTPendingGFDANs", "dan summary size : " +
		 * danSummaries.size());
		 * 
		 * if (danSummaries.size() == 0) { rpForm.setSelectMember(null); throw new
		 * MissingDANDetailsException("No DANs available for Allocation");
		 * 
		 * }
		 * 
		 * boolean isDanAvailable = false; for (int i = 0; i < danSummaries.size(); i++)
		 * { DANSummary danSummary = (DANSummary) danSummaries.get(i); if
		 * (danSummary.getAmountDue() != danSummary.getAmountPaid()) { isDanAvailable =
		 * true; break; } } if (!isDanAvailable) { rpForm.setSelectMember(null); throw
		 * new MissingDANDetailsException("No DANs available for Allocation"); }
		 * rpForm.setDanSummaries(danSummaries); rpForm.setBankId(bankId);
		 * rpForm.setZoneId(zoneId); rpForm.setBranchId(branchId);
		 * 
		 * Log.log(4, "RPAction", "getNEFTPendingGFDANs", "Exited"); if
		 * (rpForm.getSelectMember() != null) {
		 * rpForm.setMemberId(rpForm.getSelectMember()); } else {
		 * rpForm.setMemberId(bankId + zoneId + branchId); }
		 * rpForm.setSelectMember(null);
		 */
		return mapping.findForward("liveafdansummaryOnline");
	}


	public ActionForward getDisplayDAN(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception 
	{
		RPActionForm rpForm = (RPActionForm) form;
		
		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();

		String memberId = bankId.concat(zoneId).concat(branchId);
		System.out.println("memberId==" + memberId);
		//String memberId = "";
		
		String danIdTxt=rpForm.getDanIdTxt();
		Date dtFromDate=rpForm.getDtFromDate();
		Date dtToDate  =rpForm.getDtToDate();
		
		System.out.println("danIdTxt="+ danIdTxt);
		System.out.println("dtFromDate="+ dtFromDate);
		System.out.println("dtToDate="+ dtToDate);
		
	    RpProcessor rpProcessor = new RpProcessor(); 
	    ArrayList danSummaries =  rpProcessor.displayGFDANData(danIdTxt,memberId,dtFromDate,dtToDate);
	    
	    rpForm.setDanSummaries(danSummaries);
	    
	    System.out.println("danSummaries="+ danSummaries.size());
	    request.setAttribute("danSummaries", danSummaries);
	    return mapping.findForward("displayDAN");
	}
	
	public ActionForward savePayment(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception 
	{
		RPActionForm rpForm = (RPActionForm) form;
		
		String paymentModeRP=rpForm.getPaymentModeRP();
		Date paymentInitiateDate=rpForm.getDtpaymentDate();
		
		System.out.println("paymentModeRP="+ paymentModeRP);
		System.out.println("paymentInitiateDate="+ paymentInitiateDate);
		
		//String[] checkedDan=rpForm.getDanChecked();
		
		
		RpProcessor rpProcessor = new RpProcessor(); 
	    ArrayList paymentList =  rpProcessor.displayPaymentData(paymentModeRP);
	    
	    return mapping.findForward("savePayment");
	}
	//end by archana
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getLiveAFDANsPaymentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RPActionForm actionForm = (RPActionForm) form;
		double totalAmount = 0.0D;
		StringTokenizer tokenizer = null;
		Map appropriatedCases = actionForm.getAppropriatedFlags();
		if (appropriatedCases.size() < 1)
			throw new MissingDANDetailsException("Please select atleast one dan for allocation.");
		Set appropriatedCasesSet = appropriatedCases.keySet();
		Iterator appropriatedCasesIterator = appropriatedCasesSet.iterator();
		String token = null;
		String token1 = null;
		float total = 0.0F;
		float total2 = 0.0F;

		while (appropriatedCasesIterator.hasNext()) {
			String key = (String) appropriatedCasesIterator.next();
			for (tokenizer = new StringTokenizer(key, "#"); tokenizer.hasMoreTokens(); System.out.println(total2)) {
				token = tokenizer.nextToken();
				token1 = tokenizer.nextToken();
				total = Integer.parseInt(token1);
				total2 += total;
			}
		}

		Registration registration = new Registration();
		CollectingBank collectingBank = registration.getCollectingBank(actionForm.getMemberId());

		actionForm.setModeOfPayment("");
		actionForm.setPaymentDate(null);
		actionForm.setInstrumentNo("");
		actionForm.setInstrumentDate(null);
		actionForm.setDrawnAtBank("");
		actionForm.setDrawnAtBranch("");
		actionForm.setPayableAt("");
		IFProcessor ifProcessor = new IFProcessor();
		ArrayList instruments = ifProcessor.getInstrumentTypes("G");
		actionForm.setInstruments(instruments);
		actionForm.setCollectingBank(collectingBank.getCollectingBankId());
		actionForm.setCollectingBankName(collectingBank.getCollectingBankName());
		actionForm.setCollectingBankBranch(collectingBank.getBranchName());
		actionForm.setAccountNumber(collectingBank.getAccNo());

		actionForm.setInstrumentAmount(total2);

		return mapping.findForward("liveafdanpaymentdetails");
	}

	// rajuk
	public ActionForward getLiveGFDANsPaymentDetailsOnline(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		RPActionForm actionForm = (RPActionForm) form;
		double totalAmount = 0.00D;
		StringTokenizer tokenizer = null;
		Map appropriatedCases = actionForm.getAppropriatedFlags();
		if (appropriatedCases.size() < 1)
			throw new MissingDANDetailsException("Please select atleast one dan for allocation.");

		if (appropriatedCases.size() > 50)
			throw new MissingDANDetailsException("Maximum 50 allocation is only possible. ");

		Set appropriatedCasesSet = appropriatedCases.keySet();
		Iterator appropriatedCasesIterator = appropriatedCasesSet.iterator();
		String token = null;
		String token1 = null;

		float total = 0.00F;
		float total2 = 0.00F;
		double d2 = (double) total;

		String paymentId = "";
		String ifscCode = "CORP0000633";
		User user = getUserInformation(request);
		PaymentDetails paymentDetails = new PaymentDetails();
		RpProcessor rpProcessor = new RpProcessor();
		while (appropriatedCasesIterator.hasNext()) {
			String key = (String) appropriatedCasesIterator.next();
			for (tokenizer = new StringTokenizer(key, "#"); tokenizer.hasMoreTokens(); System.out.println(total2)) {
				token = tokenizer.nextToken();
				token1 = tokenizer.nextToken();
				total = Integer.parseInt(token1);
				total2 += total;

			}
		}

		/*
		 * double total4 =Math.round(total2); System.out.println("rajukonkati2"+total4);
		 * 
		 * double total5 =Math.round(total*100.0)/100.0;
		 * System.out.println("rajukonkati2"+total5);
		 * 
		 * double total6 =Math.round(total2*100.0)/100.0;
		 * 
		 * System.out.println("rajukonkati2"+total6);
		 * 
		 * String k=total2+"."; System.out.println("rajukonkati2"+k);
		 */
		paymentDetails.setInstrumentAmount(total2);
		Registration registration = new Registration();
		paymentDetails.setAllocationType1("G");
		Map appropriatedFlags = actionForm.getAppropriatedFlags();
		Map cgpans = actionForm.getCgpans();
		Map danCgpanDetails = actionForm.getDanPanDetails();

		paymentId = rpProcessor.allocateCGDANonline(paymentDetails, appropriatedFlags, cgpans, danCgpanDetails, user);

		System.out.println("rajukonkati2" + paymentId);

		StringTokenizer tok = new StringTokenizer(paymentId, "()- ");
		StringBuilder br = new StringBuilder();
		String a = tok.nextToken();
		String b = tok.nextToken();
		String c = tok.nextToken();
		String d = tok.nextToken();
		String e = tok.nextToken();
		System.out.println("rajukonkati" + a);
		System.out.println("rajukonkati" + b);
		System.out.println("rajukonkati" + c);
		System.out.println("rajukonkati" + d);
		System.out.println("rajukonkati" + e);
		String f = "16666";
		String vaccno = f.concat(b).concat(c).concat(d).concat(e);
		System.out.println(vaccno);

		paymentDetails.setIfscCode(ifscCode);

		actionForm.setModeOfPayment("");
		actionForm.setPaymentDate(null);
		actionForm.setInstrumentNo("");
		actionForm.setInstrumentDate(null);
		actionForm.setDrawnAtBank("");
		actionForm.setDrawnAtBranch("");
		actionForm.setPayableAt("");
		IFProcessor ifProcessor = new IFProcessor();
		ArrayList instruments = ifProcessor.getInstrumentTypes("G");
		actionForm.setInstruments(instruments);

		actionForm.setInstrumentAmount(total2);
		actionForm.setPaymentId(paymentId);
		actionForm.setVaccno(vaccno);
		actionForm.setIfscCode(ifscCode);
		return mapping.findForward("liveafdanpaymentdetailsonline");
	}

	// rajuk

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	// added by rajuk
	

	public ActionForward submitAllocatePaymentOnline(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RPActionForm actionForm = (RPActionForm) form;
		RpProcessor rpProcessor = new RpProcessor();
		String paymentId = "";
		String methodName = "submitAFDANsPaymentDetails";
		Log.log(5, "RPAction", methodName, "Entered");
		User user = getUserInformation(request);
		PaymentDetails paymentDetails = new PaymentDetails();
		String allocationType = actionForm.getAllocationType();
		paymentDetails.setAllocationType1("S");
		String modeOfPayment = actionForm.getModeOfPayment();
		String collectingBranch = actionForm.getCollectingBankBranch();
		java.util.Date paymentDate = actionForm.getPaymentDate();
		String instrumentNumber = actionForm.getInstrumentNo();
		java.util.Date instrumentDate = actionForm.getInstrumentDate();
		String modeOfDelivery = actionForm.getModeOfDelivery();
		double instrumentAmount = actionForm.getInstrumentAmount();
		String drawnAtBank = actionForm.getDrawnAtBank();
		String drawnAtBranch = actionForm.getDrawnAtBranch();
		String payableAt = actionForm.getPayableAt();
		String accNumber = actionForm.getAccountNumber();

		paymentDetails.setCollectingBank(actionForm.getCollectingBankName());

		paymentDetails.setModeOfPayment(modeOfPayment);

		paymentDetails.setCollectingBankBranch(collectingBranch);

		paymentDetails.setPaymentDate(paymentDate);

		paymentDetails.setInstrumentNo(instrumentNumber);

		paymentDetails.setInstrumentDate(instrumentDate);

		paymentDetails.setModeOfDelivery(modeOfDelivery);

		paymentDetails.setInstrumentAmount(instrumentAmount);

		paymentDetails.setDrawnAtBank(drawnAtBank);

		paymentDetails.setDrawnAtBranch(drawnAtBranch);

		paymentDetails.setPayableAt(payableAt);

		paymentDetails.setCgtsiAccNumber(accNumber);
		Map appropriatedFlags = actionForm.getAppropriatedFlags();
		Map cgpans = actionForm.getCgpans();
		Map danCgpanDetails = actionForm.getDanPanDetails();

		CallableStatement callableStmt = null;
		Connection conn = null;
		boolean hasExceptionOccured = false;
		int status = -1;
		String errorCode = null;
		conn = DBConnection.getConnection(false);
		try {

			callableStmt = conn.prepareCall("{?=call funcInsertClaimCheckList(?,?,?,?,?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);

			callableStmt.registerOutParameter(29, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			// errorCode = callableStmt.getString(44);
			errorCode = callableStmt.getString(29);
		} catch (Exception exception) {
			throw new DatabaseException(exception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}

		return mapping.findForward("success");
	}

	// end by rajuk
	public ActionForward submitAFDANsPaymentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RPActionForm actionForm = (RPActionForm) form;
		RpProcessor rpProcessor = new RpProcessor();
		String paymentId = "";
		String methodName = "submitAFDANsPaymentDetails";
		Log.log(5, "RPAction", methodName, "Entered");
		User user = getUserInformation(request);
		PaymentDetails paymentDetails = new PaymentDetails();
		String allocationType = actionForm.getAllocationType();
		paymentDetails.setAllocationType1("S");
		String modeOfPayment = actionForm.getModeOfPayment();
		String collectingBranch = actionForm.getCollectingBankBranch();
		java.util.Date paymentDate = actionForm.getPaymentDate();
		String instrumentNumber = actionForm.getInstrumentNo();
		java.util.Date instrumentDate = actionForm.getInstrumentDate();
		String modeOfDelivery = actionForm.getModeOfDelivery();
		double instrumentAmount = actionForm.getInstrumentAmount();
		String drawnAtBank = actionForm.getDrawnAtBank();
		String drawnAtBranch = actionForm.getDrawnAtBranch();
		String payableAt = actionForm.getPayableAt();
		String accNumber = actionForm.getAccountNumber();
		Log.log(5, "RPAction", methodName, "collecting bank " + actionForm.getCollectingBankName());
		paymentDetails.setCollectingBank(actionForm.getCollectingBankName());
		Log.log(5, "RPAction", methodName, "mode of payment " + modeOfPayment);
		paymentDetails.setModeOfPayment(modeOfPayment);
		Log.log(5, "RPAction", methodName, "collecting branch " + collectingBranch);
		paymentDetails.setCollectingBankBranch(collectingBranch);
		Log.log(5, "RPAction", methodName, "payment date " + paymentDate);
		paymentDetails.setPaymentDate(paymentDate);
		Log.log(5, "RPAction", methodName, "instrument number " + instrumentNumber);
		paymentDetails.setInstrumentNo(instrumentNumber);
		Log.log(5, "RPAction", methodName, "instrument date " + instrumentDate);
		paymentDetails.setInstrumentDate(instrumentDate);
		Log.log(5, "RPAction", methodName, "mode of delivery " + modeOfDelivery);
		paymentDetails.setModeOfDelivery(modeOfDelivery);
		Log.log(5, "RPAction", methodName, "instrument amount " + instrumentAmount);
		paymentDetails.setInstrumentAmount(instrumentAmount);
		Log.log(5, "RPAction", methodName, "drawn at bank " + drawnAtBank);
		paymentDetails.setDrawnAtBank(drawnAtBank);
		Log.log(5, "RPAction", methodName, "drawn at branch " + drawnAtBranch);
		paymentDetails.setDrawnAtBranch(drawnAtBranch);
		Log.log(5, "RPAction", methodName, "payable at " + payableAt);
		paymentDetails.setPayableAt(payableAt);
		Log.log(5, "RPAction", methodName, "acc num " + accNumber);
		paymentDetails.setCgtsiAccNumber(accNumber);
		Map appropriatedFlags = actionForm.getAppropriatedFlags();
		Map cgpans = actionForm.getCgpans();
		Map danCgpanDetails = actionForm.getDanPanDetails();

		paymentId = rpProcessor.allocateCGDAN(paymentDetails, appropriatedFlags, cgpans, danCgpanDetails, user);

		request.setAttribute("message", "Payment Allocated Successfully.<BR>Payment ID : " + paymentId + " for Rs."
				+ paymentDetails.getInstrumentAmount());
		Log.log(5, "RPAction", methodName, "Exited");

		return mapping.findForward("success");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPendingAFDANsExpired(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RPActionForm rpForm = (RPActionForm) form;
		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = "";

		if (rpForm.getDanSummaries() != null) {
			rpForm.getDanSummaries().clear();
		}
		if (rpForm.getDanPanDetails() != null) {
			rpForm.getDanPanDetails().clear();
		}
		if (rpForm.getCgpans() != null) {
			rpForm.getCgpans().clear();
		}
		if (rpForm.getAllocatedFlags() != null) {
			rpForm.getAllocatedFlags().clear();
		}
		if (rpForm.getFirstDisbursementDates() != null) {
			rpForm.getFirstDisbursementDates().clear();
		}
		if (rpForm.getNotAllocatedReasons() != null) {
			rpForm.getNotAllocatedReasons().clear();
		}
		if (rpForm.getAllocatedFlags() != null) {
			rpForm.getAllocatedFlags().clear();
		}
		if (rpForm.getAppropriatedFlags() != null) {
			rpForm.getAppropriatedFlags().clear();
		}
		if (rpForm.getAmountsRaised() != null) {
			rpForm.getAmountsRaised().clear();
		}
		if (rpForm.getAmountBeingPaid() != null) {
			rpForm.getAmountBeingPaid().clear();
		}

		if (bankId.equals("0000")) {
			memberId = rpForm.getSelectMember();

			// if ((memberId == null) || (memberId.equals(""))) {
			// memberId = rpForm.getMemberId();
			// }
			Log.log(5, "RPAction", "getNEFTPendingGFDANs", "mliId = " + memberId);

			if ((memberId == null) || (memberId.equals(""))) {
				// session.setAttribute("TARGET_URL",
				// "allocatePaymentsAll.do?method=getPendingAFDANsLive");
				return mapping.findForward("expiredafmember");
			}
			bankId = memberId.substring(0, 4);
			zoneId = memberId.substring(4, 8);
			branchId = memberId.substring(8, 12);

			ClaimsProcessor cpProcessor = new ClaimsProcessor();
			Vector memberIds = cpProcessor.getAllMemberIds();
			if (!memberIds.contains(memberId)) {
				throw new NoMemberFoundException("The Member ID does not exist");
			}
		}
		RpProcessor rpProcessor = new RpProcessor();
		ArrayList danSummaries = rpProcessor.displayExpiredAFDANs(bankId, zoneId, branchId);
		Log.log(5, "RPAction", "getNEFTPendingGFDANs", "dan summary size : " + danSummaries.size());

		if (danSummaries.size() == 0) {
			rpForm.setSelectMember(null);
			throw new MissingDANDetailsException("No DANs available for Allocation");
		}

		boolean isDanAvailable = false;
		for (int i = 0; i < danSummaries.size(); i++) {
			DANSummary danSummary = (DANSummary) danSummaries.get(i);
			if (danSummary.getAmountDue() != danSummary.getAmountPaid()) {
				isDanAvailable = true;
				break;
			}
		}
		if (!isDanAvailable) {
			rpForm.setSelectMember(null);
			throw new MissingDANDetailsException("No DANs available for Allocation");
		}
		rpForm.setDanSummaries(danSummaries);
		rpForm.setBankId(bankId);
		rpForm.setZoneId(zoneId);
		rpForm.setBranchId(branchId);

		Log.log(4, "RPAction", "getNEFTPendingGFDANs", "Exited");
		if (rpForm.getSelectMember() != null) {
			rpForm.setMemberId(rpForm.getSelectMember());
		} else {
			rpForm.setMemberId(bankId + zoneId + branchId);
		}
		rpForm.setSelectMember(null);
		return mapping.findForward("expiredafdansummary");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getExpiredAFDANsPaymentDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		RPActionForm actionForm = (RPActionForm) form;
		double totalAmount = 0.0D;
		StringTokenizer tokenizer = null;
		Map appropriatedCases = actionForm.getAppropriatedFlags();
		if (appropriatedCases.size() < 1)
			throw new MissingDANDetailsException("Please select atleast one dan for allocation .");
		Set appropriatedCasesSet = appropriatedCases.keySet();
		Iterator appropriatedCasesIterator = appropriatedCasesSet.iterator();
		String token = null;
		String token1 = null;
		float total = 0.0F;
		float total2 = 0.0F;
		while (appropriatedCasesIterator.hasNext()) {
			String key = (String) appropriatedCasesIterator.next();
			for (tokenizer = new StringTokenizer(key, "#"); tokenizer.hasMoreTokens(); System.out.println(total2)) {
				token = tokenizer.nextToken();
				token1 = tokenizer.nextToken();
				total = Integer.parseInt(token1);
				total2 += total;
			}
		}
		Registration registration = new Registration();
		Log.log(5, "RPAction", "getExpiredAFDANsPaymentDetails",
				(new StringBuilder()).append("member id ").append(actionForm.getMemberId()).toString());
		CollectingBank collectingBank = registration.getCollectingBank(
				(new StringBuilder()).append("(").append(actionForm.getMemberId()).append(")").toString());
		Log.log(5, "RPAction", "getExpiredAFDANsPaymentDetails",
				(new StringBuilder()).append("collectingBank ").append(collectingBank).toString());
		actionForm.setModeOfPayment("");
		actionForm.setPaymentDate(null);
		actionForm.setInstrumentNo("");
		actionForm.setInstrumentDate(null);
		actionForm.setDrawnAtBank("");
		actionForm.setDrawnAtBranch("");
		actionForm.setPayableAt("");
		IFProcessor ifProcessor = new IFProcessor();
		ArrayList instruments = ifProcessor.getInstrumentTypes("G");
		actionForm.setInstruments(instruments);
		actionForm.setCollectingBank(collectingBank.getCollectingBankId());
		actionForm.setCollectingBankName(collectingBank.getCollectingBankName());
		actionForm.setCollectingBankBranch(collectingBank.getBranchName());
		actionForm.setAccountNumber(collectingBank.getAccNo());
		actionForm.setInstrumentAmount(total2);
		return mapping.findForward("expiredafdanpaymentdetails");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPendingASFDANsLive(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RPActionForm rpForm = (RPActionForm) form;
		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = "";

		if (rpForm.getDanSummaries() != null) {
			rpForm.getDanSummaries().clear();
		}
		if (rpForm.getDanPanDetails() != null) {
			rpForm.getDanPanDetails().clear();
		}
		if (rpForm.getCgpans() != null) {
			rpForm.getCgpans().clear();
		}
		if (rpForm.getAllocatedFlags() != null) {
			rpForm.getAllocatedFlags().clear();
		}
		if (rpForm.getFirstDisbursementDates() != null) {
			rpForm.getFirstDisbursementDates().clear();
		}
		if (rpForm.getNotAllocatedReasons() != null) {
			rpForm.getNotAllocatedReasons().clear();
		}
		if (rpForm.getAllocatedFlags() != null) {
			rpForm.getAllocatedFlags().clear();
		}
		if (rpForm.getAppropriatedFlags() != null) {
			rpForm.getAppropriatedFlags().clear();
		}
		if (rpForm.getAmountsRaised() != null) {
			rpForm.getAmountsRaised().clear();
		}
		if (rpForm.getAmountBeingPaid() != null) {
			rpForm.getAmountBeingPaid().clear();
		}

		if (bankId.equals("0000")) {
			memberId = rpForm.getSelectMember();

			// if ((memberId == null) || (memberId.equals(""))) {
			// memberId = rpForm.getMemberId();
			// }

			Log.log(5, "RPAction", "getNEFTPendingGFDANs", "mliId = " + memberId);

			if ((memberId == null) || (memberId.equals(""))) {
				// session.setAttribute("TARGET_URL",
				// "allocatePaymentsAll.do?method=getPendingAFDANsLive");
				return mapping.findForward("liveasfmember");
			}
			bankId = memberId.substring(0, 4);
			zoneId = memberId.substring(4, 8);
			branchId = memberId.substring(8, 12);

			ClaimsProcessor cpProcessor = new ClaimsProcessor();
			Vector memberIds = cpProcessor.getAllMemberIds();
			if (!memberIds.contains(memberId)) {
				throw new NoMemberFoundException("The Member ID does not exist");
			}
		}
		RpProcessor rpProcessor = new RpProcessor();
		ArrayList danSummaries = rpProcessor.displayLiveASFDANs(bankId, zoneId, branchId);
		Log.log(5, "RPAction", "getNEFTPendingGFDANs", "dan summary size : " + danSummaries.size());

		if (danSummaries.size() == 0) {
			rpForm.setSelectMember(null);
			throw new MissingDANDetailsException("No DANs available for Allocation");
		}

		boolean isDanAvailable = false;
		for (int i = 0; i < danSummaries.size(); i++) {
			DANSummary danSummary = (DANSummary) danSummaries.get(i);

			System.out.println("danSummary.getDanId():" + danSummary.getDanId() + "--danSummary.getAmountDue():"
					+ danSummary.getAmountDue() + "--danSummary.getAmountPaid():" + danSummary.getAmountPaid());
			if (danSummary.getAmountDue() != danSummary.getAmountPaid()) {
				isDanAvailable = true;
				break;
			}
		}
		if (!isDanAvailable) {
			rpForm.setSelectMember(null);
			throw new MissingDANDetailsException("No DANs available for Allocation");
		}
		rpForm.setDanSummaries(danSummaries);
		rpForm.setBankId(bankId);
		rpForm.setZoneId(zoneId);
		rpForm.setBranchId(branchId);

		Log.log(4, "RPAction", "getNEFTPendingGFDANs", "Exited");
		if (rpForm.getSelectMember() != null) {
			rpForm.setMemberId(rpForm.getSelectMember());
		} else {
			rpForm.setMemberId(bankId + zoneId + branchId);
		}
		rpForm.setSelectMember(null);
		return mapping.findForward("liveasfdansummary");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getLiveASFDANsPaymentDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		RPActionForm actionForm = (RPActionForm) form;
		double totalAmount = 0.0D;
		StringTokenizer tokenizer = null;
		Map appropriatedCases = actionForm.getAppropriatedFlags();
		if (appropriatedCases.size() < 1)
			throw new MissingDANDetailsException("Please select atleast one dan for allocation .");
		Set appropriatedCasesSet = appropriatedCases.keySet();
		Iterator appropriatedCasesIterator = appropriatedCasesSet.iterator();
		String token = null;
		String token1 = null;
		float total = 0.0F;
		float total2 = 0.0F;
		while (appropriatedCasesIterator.hasNext()) {
			String key = (String) appropriatedCasesIterator.next();
			for (tokenizer = new StringTokenizer(key, "#"); tokenizer.hasMoreTokens(); System.out.println(total2)) {
				token = tokenizer.nextToken();
				token1 = tokenizer.nextToken();
				total = Integer.parseInt(token1);
				total2 += total;
			}
		}
		Registration registration = new Registration();
		Log.log(5, "RPAction", "getLiveASFDANsPaymentDetails",
				(new StringBuilder()).append("member id ").append(actionForm.getMemberId()).toString());
		CollectingBank collectingBank = registration.getCollectingBank(
				(new StringBuilder()).append("(").append(actionForm.getMemberId()).append(")").toString());
		Log.log(5, "RPAction", "getLiveASFDANsPaymentDetails",
				(new StringBuilder()).append("collectingBank ").append(collectingBank).toString());
		actionForm.setModeOfPayment("");
		actionForm.setPaymentDate(null);
		actionForm.setInstrumentNo("");
		actionForm.setInstrumentDate(null);
		actionForm.setDrawnAtBank("");
		actionForm.setDrawnAtBranch("");
		actionForm.setPayableAt("");
		IFProcessor ifProcessor = new IFProcessor();
		ArrayList instruments = ifProcessor.getInstrumentTypes("G");
		actionForm.setInstruments(instruments);
		actionForm.setCollectingBank(collectingBank.getCollectingBankId());
		actionForm.setCollectingBankName(collectingBank.getCollectingBankName());
		actionForm.setCollectingBankBranch(collectingBank.getBranchName());
		actionForm.setAccountNumber(collectingBank.getAccNo());
		actionForm.setInstrumentAmount(total2);
		return mapping.findForward("liveasfdanpaymentdetails");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitASFDANsPaymentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RPActionForm actionForm = (RPActionForm) form;
		RpProcessor rpProcessor = new RpProcessor();
		String paymentId = "";
		String methodName = "submitLiveASFDANsPaymentDetails";
		Log.log(5, "RPAction", methodName, "Entered");
		User user = getUserInformation(request);
		PaymentDetails paymentDetails = new PaymentDetails();
		String allocationType = actionForm.getAllocationType();
		paymentDetails.setAllocationType1("A");
		String modeOfPayment = actionForm.getModeOfPayment();
		String collectingBranch = actionForm.getCollectingBankBranch();
		java.util.Date paymentDate = actionForm.getPaymentDate();
		String instrumentNumber = actionForm.getInstrumentNo();
		java.util.Date instrumentDate = actionForm.getInstrumentDate();
		String modeOfDelivery = actionForm.getModeOfDelivery();
		double instrumentAmount = actionForm.getInstrumentAmount();
		String drawnAtBank = actionForm.getDrawnAtBank();
		String drawnAtBranch = actionForm.getDrawnAtBranch();
		String payableAt = actionForm.getPayableAt();
		String accNumber = actionForm.getAccountNumber();
		Log.log(5, "RPAction", methodName, "collecting bank " + actionForm.getCollectingBankName());
		paymentDetails.setCollectingBank(actionForm.getCollectingBankName());
		Log.log(5, "RPAction", methodName, "mode of payment " + modeOfPayment);
		paymentDetails.setModeOfPayment(modeOfPayment);
		Log.log(5, "RPAction", methodName, "collecting branch " + collectingBranch);
		paymentDetails.setCollectingBankBranch(collectingBranch);
		Log.log(5, "RPAction", methodName, "payment date " + paymentDate);
		paymentDetails.setPaymentDate(paymentDate);
		Log.log(5, "RPAction", methodName, "instrument number " + instrumentNumber);
		paymentDetails.setInstrumentNo(instrumentNumber);
		Log.log(5, "RPAction", methodName, "instrument date " + instrumentDate);
		paymentDetails.setInstrumentDate(instrumentDate);
		Log.log(5, "RPAction", methodName, "mode of delivery " + modeOfDelivery);
		paymentDetails.setModeOfDelivery(modeOfDelivery);
		Log.log(5, "RPAction", methodName, "instrument amount " + instrumentAmount);
		paymentDetails.setInstrumentAmount(instrumentAmount);
		Log.log(5, "RPAction", methodName, "drawn at bank " + drawnAtBank);
		paymentDetails.setDrawnAtBank(drawnAtBank);
		Log.log(5, "RPAction", methodName, "drawn at branch " + drawnAtBranch);
		paymentDetails.setDrawnAtBranch(drawnAtBranch);
		Log.log(5, "RPAction", methodName, "payable at " + payableAt);
		paymentDetails.setPayableAt(payableAt);
		Log.log(5, "RPAction", methodName, "acc num " + accNumber);
		paymentDetails.setCgtsiAccNumber(accNumber);
		Map appropriatedFlags = actionForm.getAppropriatedFlags();
		Map cgpans = actionForm.getCgpans();
		Map danCgpanDetails = actionForm.getDanPanDetails();

		paymentId = rpProcessor.allocateCGDAN(paymentDetails, appropriatedFlags, cgpans, danCgpanDetails, user);
		request.setAttribute("message", "Payment Allocated Successfully.<BR>Payment ID : " + paymentId + " for Rs."
				+ paymentDetails.getInstrumentAmount());
		Log.log(5, "RPAction", methodName, "Exited");
		return mapping.findForward("success");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPendingASFDANsExpired(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RPActionForm rpForm = (RPActionForm) form;
		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = "";

		if (rpForm.getDanSummaries() != null) {
			rpForm.getDanSummaries().clear();
		}
		if (rpForm.getDanPanDetails() != null) {
			rpForm.getDanPanDetails().clear();
		}
		if (rpForm.getCgpans() != null) {
			rpForm.getCgpans().clear();
		}
		if (rpForm.getAllocatedFlags() != null) {
			rpForm.getAllocatedFlags().clear();
		}
		if (rpForm.getFirstDisbursementDates() != null) {
			rpForm.getFirstDisbursementDates().clear();
		}
		if (rpForm.getNotAllocatedReasons() != null) {
			rpForm.getNotAllocatedReasons().clear();
		}
		if (rpForm.getAllocatedFlags() != null) {
			rpForm.getAllocatedFlags().clear();
		}
		if (rpForm.getAppropriatedFlags() != null) {
			rpForm.getAppropriatedFlags().clear();
		}
		if (rpForm.getAmountsRaised() != null) {
			rpForm.getAmountsRaised().clear();
		}
		if (rpForm.getAmountBeingPaid() != null) {
			rpForm.getAmountBeingPaid().clear();
		}

		if (bankId.equals("0000")) {
			memberId = rpForm.getSelectMember();

			// if ((memberId == null) || (memberId.equals(""))) {
			// memberId = rpForm.getMemberId();
			// }
			Log.log(5, "RPAction", "getNEFTPendingGFDANs", "mliId = " + memberId);

			if ((memberId == null) || (memberId.equals(""))) {
				// session.setAttribute("TARGET_URL",
				// "allocatePaymentsAll.do?method=getPendingAFDANsLive");
				return mapping.findForward("expiredasfmember");
			}

			bankId = memberId.substring(0, 4);
			zoneId = memberId.substring(4, 8);
			branchId = memberId.substring(8, 12);

			ClaimsProcessor cpProcessor = new ClaimsProcessor();
			Vector memberIds = cpProcessor.getAllMemberIds();
			if (!memberIds.contains(memberId)) {
				throw new NoMemberFoundException("The Member ID does not exist");
			}
		}
		RpProcessor rpProcessor = new RpProcessor();
		ArrayList danSummaries = rpProcessor.displayExpiredASFDANs(bankId, zoneId, branchId);

		Log.log(5, "RPAction", "getNEFTPendingGFDANs", "dan summary size : " + danSummaries.size());

		if (danSummaries.size() == 0) {
			rpForm.setSelectMember(null);
			throw new MissingDANDetailsException("No DANs available for Allocation");
		}

		boolean isDanAvailable = false;
		for (int i = 0; i < danSummaries.size(); i++) {
			DANSummary danSummary = (DANSummary) danSummaries.get(i);
			if (danSummary.getAmountDue() != danSummary.getAmountPaid()) {
				isDanAvailable = true;
				break;
			}
		}
		if (!isDanAvailable) {
			rpForm.setSelectMember(null);

			throw new MissingDANDetailsException("No DANs available for Allocation.");
		}
		rpForm.setDanSummaries(danSummaries);
		rpForm.setBankId(bankId);
		rpForm.setZoneId(zoneId);
		rpForm.setBranchId(branchId);

		Log.log(4, "RPAction", "getNEFTPendingGFDANs", "Exited");
		if (rpForm.getSelectMember() != null) {
			rpForm.setMemberId(rpForm.getSelectMember());
		} else {
			rpForm.setMemberId(bankId + zoneId + branchId);
		}
		rpForm.setSelectMember(null);
		return mapping.findForward("expiredasfdansummary");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getExpiredASFDANsPaymentDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		RPActionForm actionForm = (RPActionForm) form;
		double totalAmount = 0.0D;
		StringTokenizer tokenizer = null;
		double tot = 0.0D;
		Map appropriatedCases = actionForm.getAppropriatedFlags();
		if (appropriatedCases.size() < 1)
			throw new MissingDANDetailsException("Please select atleast one dan for allocation .");
		Set appropriatedCasesSet = appropriatedCases.keySet();
		Iterator appropriatedCasesIterator = appropriatedCasesSet.iterator();
		String token = null;
		String token1 = null;
		float total = 0.0F;
		float total2 = 0.0F;
		while (appropriatedCasesIterator.hasNext()) {
			String key = (String) appropriatedCasesIterator.next();
			for (tokenizer = new StringTokenizer(key, "#"); tokenizer.hasMoreTokens(); System.out.println(total2)) {
				token = tokenizer.nextToken();
				token1 = tokenizer.nextToken();
				total = Integer.parseInt(token1);
				total2 += total;
			}
		}
		Registration registration = new Registration();
		Log.log(5, "RPAction", "getExpiredASFDANsPaymentDetails",
				(new StringBuilder()).append("member id ").append(actionForm.getMemberId()).toString());
		CollectingBank collectingBank = registration.getCollectingBank(
				(new StringBuilder()).append("(").append(actionForm.getMemberId()).append(")").toString());
		Log.log(5, "RPAction", "getExpiredASFDANsPaymentDetails",
				(new StringBuilder()).append("collectingBank ").append(collectingBank).toString());
		actionForm.setModeOfPayment("");
		actionForm.setPaymentDate(null);
		actionForm.setInstrumentNo("");
		actionForm.setInstrumentDate(null);
		actionForm.setDrawnAtBank("");
		actionForm.setDrawnAtBranch("");
		actionForm.setPayableAt("");
		IFProcessor ifProcessor = new IFProcessor();
		ArrayList instruments = ifProcessor.getInstrumentTypes("G");
		actionForm.setInstruments(instruments);
		actionForm.setCollectingBank(collectingBank.getCollectingBankId());
		actionForm.setCollectingBankName(collectingBank.getCollectingBankName());
		actionForm.setCollectingBankBranch(collectingBank.getBranchName());
		actionForm.setAccountNumber(collectingBank.getAccNo());
		actionForm.setInstrumentAmount(total2);
		return mapping.findForward("expiredasfdanpaymentdetails");
	}

	// koteswar payment gateway start

	public ActionForward getPendingGFDANsFilterForPaymentGateway(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = getUserInformation(request);
		String bankId = user.getBankId();

		RPActionForm rpActionForm = (RPActionForm) form;

		rpActionForm.setAllocationType("G");

		HttpSession session = request.getSession(false);

		if (bankId.equalsIgnoreCase("0000")) {
			rpActionForm.setSelectMember("");

			session.setAttribute("TARGET_URL", "selectGFMember.do?method=getPendingGFDANsForPaymentGateway");

			return mapping.findForward("memberInfo");
		}

		request.setAttribute("pageValue", "1");

		getPendingGFDANs(mapping, form, request, response);

		return mapping.findForward("danSummary");
	}

	public ActionForward getPendingGFDANsForPaymentGateway(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "getPendingGFDANs", "Entered");
		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = "";

		RPActionForm actionForm = (RPActionForm) form;
		HttpSession session = request.getSession(false);
		if (actionForm.getDanSummaries() != null) {
			actionForm.getDanSummaries().clear();
		}
		if (actionForm.getDanPanDetails() != null) {
			actionForm.getDanPanDetails().clear();
		}
		if (actionForm.getCgpans() != null) {
			actionForm.getCgpans().clear();
		}
		if (actionForm.getAllocatedFlags() != null) {
			actionForm.getAllocatedFlags().clear();
		}
		if (actionForm.getFirstDisbursementDates() != null) {
			actionForm.getFirstDisbursementDates().clear();
		}
		if (actionForm.getNotAllocatedReasons() != null) {
			actionForm.getNotAllocatedReasons().clear();
		}
		if (actionForm.getAppropriatedFlags() != null) {
			actionForm.getAppropriatedFlags().clear();
		}

		Log.log(5, "RPAction", "getPendingGFDANs", "Bank Id : " + bankId);
		Log.log(5, "RPAction", "getPendingGFDANs", "Zone Id : " + zoneId);
		Log.log(5, "RPAction", "getPendingGFDANs", "Branch Id : " + branchId);

		if (bankId.equals("0000")) {
			memberId = actionForm.getSelectMember();

			if ((memberId == null) || (memberId.equals(""))) {
				memberId = actionForm.getMemberId();
			}

			Log.log(5, "RPAction", "getPendingGFDANs", "mliId = " + memberId);

			if ((memberId == null) || (memberId.equals(""))) {
				session.setAttribute("TARGET_URL", "selectGFMember.do?method=getPendingGFDANs");

				return mapping.findForward("memberInfo");
			}

			bankId = memberId.substring(0, 4);
			zoneId = memberId.substring(4, 8);
			branchId = memberId.substring(8, 12);

			ClaimsProcessor cpProcessor = new ClaimsProcessor();
			Vector memberIds = cpProcessor.getAllMemberIds();
			if (!memberIds.contains(memberId)) {
				throw new NoMemberFoundException("The Member ID does not exist");
			}

		}

		Log.log(5, "RPAction", "getPendingGFDANs",
				"Selected Bank Id,zone and branch ids : " + bankId + "," + zoneId + "," + branchId);

		RpProcessor rpProcessor = new RpProcessor();
		ArrayList danSummaries = rpProcessor.displayGFDANs(bankId, zoneId, branchId);

		Log.log(5, "RPAction", "getPendingGFDANs", "dan summary size : " + danSummaries.size());

		if (danSummaries.size() == 0) {
			actionForm.setSelectMember(null);

			throw new MissingDANDetailsException("No DANs available for Allocation");
		}

		boolean isDanAvailable = false;
		for (int i = 0; i < danSummaries.size(); i++) {
			DANSummary danSummary = (DANSummary) danSummaries.get(i);
			if (danSummary.getAmountDue() != danSummary.getAmountPaid()) {
				isDanAvailable = true;
				break;
			}
		}
		if (!isDanAvailable) {
			actionForm.setSelectMember(null);

			throw new MissingDANDetailsException("No DANs available for Allocation");
		}
		actionForm.setDanSummaries(danSummaries);

		actionForm.setBankId(bankId);
		actionForm.setZoneId(zoneId);
		actionForm.setBranchId(branchId);

		Log.log(4, "RPAction", "getPendingGFDANs", "Exited");
		if (actionForm.getSelectMember() != null) {
			actionForm.setMemberId(actionForm.getSelectMember());
		} else {
			actionForm.setMemberId(bankId + zoneId + branchId);
		}

		actionForm.setSelectMember(null);

		return mapping.findForward("danSummary2");
	}

	/*
	 * public ActionForward submitGFDANPaymentsPaymentGateway(ActionMapping mapping,
	 * ActionForm form, HttpServletRequest request, HttpServletResponse response)
	 * throws Exception { RPActionForm actionForm = (RPActionForm)form; double
	 * totalAmount = 0.0D; StringTokenizer tokenizer = null; double tot = 0.0D; Map
	 * appropriatedCases = actionForm.getAppropriatedFlags(); if
	 * (appropriatedCases.size() < 1) throw new
	 * MissingDANDetailsException("Please select atleast one dan for allocation ."
	 * ); Set appropriatedCasesSet = appropriatedCases.keySet(); Iterator
	 * appropriatedCasesIterator = appropriatedCasesSet.iterator(); String token =
	 * null; String token1 = null; float total = 0.0F; float total2 = 0.0F; while
	 * (appropriatedCasesIterator.hasNext()) { String key =
	 * (String)appropriatedCasesIterator.next(); for (tokenizer = new
	 * StringTokenizer(key, "#"); tokenizer.hasMoreTokens();
	 * System.out.println(total2)) { token = tokenizer.nextToken(); token1 =
	 * tokenizer.nextToken(); total = Integer.parseInt(token1); total2 += total; }
	 * 
	 * } Registration registration = new Registration(); Log.log(5, "RPAction",
	 * "submitGFDANPayments", (new
	 * StringBuilder()).append("member id ").append(actionForm
	 * .getMemberId()).toString()); CollectingBank collectingBank =
	 * registration.getCollectingBank((new
	 * StringBuilder()).append("(").append(actionForm
	 * .getMemberId()).append(")").toString()); Log.log(5, "RPAction",
	 * "submitGFDANPayments", (new StringBuilder()).append("collectingBank ").append
	 * (collectingBank).toString()); actionForm.setModeOfPayment("");
	 * actionForm.setPaymentDate(null); actionForm.setInstrumentNo("");
	 * actionForm.setInstrumentDate(null); actionForm.setDrawnAtBank("");
	 * actionForm.setDrawnAtBranch(""); actionForm.setPayableAt(""); IFProcessor
	 * ifProcessor = new IFProcessor(); ArrayList instruments =
	 * ifProcessor.getInstrumentTypes("G"); actionForm.setInstruments(instruments);
	 * actionForm.setCollectingBank(collectingBank.getCollectingBankId());
	 * actionForm.setCollectingBankName(collectingBank.getCollectingBankName());
	 * actionForm.setCollectingBankBranch(collectingBank.getBranchName());
	 * actionForm.setAccountNumber(collectingBank.getAccNo());
	 * actionForm.setInstrumentAmount(total2); return
	 * mapping.findForward("gfpaymentDetails"); }
	 */

	/*
	 * public ActionForward gfallocatePaymentsforPaymentGate(ActionMapping mapping,
	 * ActionForm form, HttpServletRequest request, HttpServletResponse response)
	 * throws Exception { RPActionForm actionForm = (RPActionForm)form;
	 * 
	 * CheckSumCaluclation checkgen = new CheckSumCaluclation(); RpProcessor
	 * rpProcessor = new RpProcessor(); String paymentId = ""; String methodName =
	 * "gfallocatePayments";
	 * 
	 * Log.log(5, "RPAction", methodName, "Entered");
	 * 
	 * User user = getUserInformation(request);
	 * 
	 * PaymentDetails paymentDetails = new PaymentDetails();
	 * 
	 * String allocationType = actionForm.getAllocationType();
	 * 
	 * paymentDetails.setAllocationType1(allocationType); String modeOfPayment =
	 * actionForm.getModeOfPayment(); String collectingBranch =
	 * actionForm.getCollectingBankBranch(); java.util.Date paymentDate =
	 * actionForm.getPaymentDate(); String instrumentNumber =
	 * actionForm.getInstrumentNo(); java.util.Date instrumentDate =
	 * actionForm.getInstrumentDate(); String modeOfDelivery =
	 * actionForm.getModeOfDelivery(); double instrumentAmount =
	 * actionForm.getInstrumentAmount(); String drawnAtBank =
	 * actionForm.getDrawnAtBank(); String drawnAtBranch =
	 * actionForm.getDrawnAtBranch(); String payableAt = actionForm.getPayableAt();
	 * String accNumber = actionForm.getAccountNumber();
	 * 
	 * 
	 * Log.log(5, "RPAction", methodName, "collecting bank " +
	 * actionForm.getCollectingBankName());
	 * paymentDetails.setCollectingBank(actionForm.getCollectingBankName());
	 * Log.log(5, "RPAction", methodName, "mode of payment " + modeOfPayment);
	 * paymentDetails.setModeOfPayment(modeOfPayment); Log.log(5, "RPAction",
	 * methodName, "collecting branch " + collectingBranch);
	 * paymentDetails.setCollectingBankBranch(collectingBranch); Log.log(5,
	 * "RPAction", methodName, "payment date " + paymentDate);
	 * paymentDetails.setPaymentDate(paymentDate); Log.log(5, "RPAction",
	 * methodName, "instrument number " + instrumentNumber);
	 * paymentDetails.setInstrumentNo(instrumentNumber); Log.log(5, "RPAction",
	 * methodName, "instrument date " + instrumentDate);
	 * paymentDetails.setInstrumentDate(instrumentDate); Log.log(5, "RPAction",
	 * methodName, "mode of delivery " + modeOfDelivery);
	 * paymentDetails.setModeOfDelivery(modeOfDelivery); Log.log(5, "RPAction",
	 * methodName, "instrument amount " + instrumentAmount);
	 * paymentDetails.setInstrumentAmount(instrumentAmount); Log.log(5, "RPAction",
	 * methodName, "drawn at bank " + drawnAtBank);
	 * paymentDetails.setDrawnAtBank(drawnAtBank); Log.log(5, "RPAction",
	 * methodName, "drawn at branch " + drawnAtBranch);
	 * paymentDetails.setDrawnAtBranch(drawnAtBranch); Log.log(5, "RPAction",
	 * methodName, "payable at " + payableAt);
	 * paymentDetails.setPayableAt(payableAt); Log.log(5, "RPAction", methodName,
	 * "acc num " + accNumber); paymentDetails.setCgtsiAccNumber(accNumber);
	 * 
	 * Map appropriatedFlags = actionForm.getAppropriatedFlags();
	 * 
	 * Map cgpans = actionForm.getCgpans();
	 * 
	 * Set cgpansSet = cgpans.keySet();
	 * 
	 * Map danCgpanDetails = actionForm.getDanPanDetails();
	 * 
	 * // paymentId = // rpProcessor.allocateCGDAN(paymentDetails,
	 * appropriatedFlags, // cgpans, danCgpanDetails, user); String
	 * payid="RP-0002-17-08-15"; actionForm.setPaymentId(payid);
	 * paymentDetails.setPaymentId(actionForm.getPaymentId()); HttpSession session =
	 * request.getSession(false);
	 * 
	 * 
	 * 
	 * String merchantId="XBCMECGTMS"; String securityId="xbcmecgtms";
	 * 
	 * String ChecksumKey="zN4PwSCV4lSX";
	 * 
	 * 
	 * String
	 * checksumCalMessage=merchantId+"|"+securityId+"|"+actionForm.getMemberId ()+
	 * "|"+paymentDetails.getPaymentId()+"|"+paymentDetails.getInstrumentAmount ();
	 * 
	 * 
	 * 
	 * 
	 * String chckcalucl=checkgen.HmacSHA256(checksumCalMessage, ChecksumKey);
	 * 
	 * String responspag=
	 * "http://www.cgtmse.in/jsp/ReceiptsPayments/PaymentGatewayReturn.jsp";
	 * 
	 * Date d=new Date();
	 * 
	 * Date date = new Date(); Calendar calendar = Calendar.getInstance();
	 * calendar.setTime(date); int month = calendar.get(2); int day =
	 * calendar.get(5); month++; int year = calendar.get(1); Date ddd=null;
	 * 
	 * Connection connection = DBConnection.getConnection(); Statement
	 * str=connection.createStatement(); String query = "select sysdate from dual";
	 * ResultSet rsforavailmemid = str.executeQuery(query);
	 * if(rsforavailmemid.next()){ //
	 * System.out.println(rsforavailmemid.getDate(0));
	 * System.out.println(rsforavailmemid.getDate(1));
	 * ddd=rsforavailmemid.getDate(1); // d=ddd.getDate(); } String
	 * dd=(String)ddd.toString();
	 * 
	 * String dates[]=dd.split("-");
	 * 
	 * String yyyy=dates[0]; String moon=dates[1]; String dddd=dates[2];
	 * 
	 * String txnNum="";
	 * 
	 * Connection connection1 = DBConnection.getConnection(); Statement
	 * str2=connection1.createStatement();
	 * 
	 * String query1=
	 * " select  PAY_TXN_CUST_ID from PAYMENT_TRANSACTION_DETAIL  WHERE  PTD_SEC_NO IN (SELECT  MAX(PTD_SEC_NO)  FROM PAYMENT_TRANSACTION_DETAIL)"
	 * ; // String query1 =
	 * " select PAY_TXN_CUST_ID from PAYMENT_TRANSACTION_DETAIL "; //String query1 =
	 * " select PAY_TXN_CUST_ID  from PAYMENT_TRANSACTION_DETAIL  where PTD_SEC_NO=(select max(PTD_SEC_NO) from PAYMENT_TRANSACTION_DETAIL)  and PAY_TXN_MLI_MEM_ID='"
	 * +actionForm.getMemberId()+"' "; // String query1 =
	 * "select sysdate from dual"; System.out.println(query1); ResultSet
	 * rsforavailmemid1 = str2.executeQuery(query1); System.out.println(query1);
	 * if(rsforavailmemid1.next()){ //
	 * System.out.println(rsforavailmemid.getDate(0));
	 * System.out.println(rsforavailmemid1.getString(1));
	 * txnNum=rsforavailmemid1.getString(1); // d=ddd.getDate(); }
	 * 
	 * String custIdtoInsert=""; int intTemp=0;
	 * 
	 * String custIdFinal="";
	 * 
	 * int inttempvalt=000001; String dateFrmDb=txnNum.substring(6,14); //String
	 * dateFrmDb=txnNum.substring(0,15);
	 * 
	 * String datefrmfrontedn="CGTMSE"+dddd+moon+yyyy;
	 * 
	 * String datefrntendtemp=datefrmfrontedn.substring(6);
	 * 
	 * String newtempvalforbelowten="00000"; String
	 * newtempvalforbelowhundred="0000"; String newtempvalforbelowthous="000";
	 * String newtempvalforbelowtenthous="00"; String newtempvalforbelowlakh="0";
	 * 
	 * if(dateFrmDb.equals(datefrntendtemp)) {
	 * 
	 * String temVal=txnNum.substring(14); intTemp=Integer.parseInt(temVal);
	 * 
	 * intTemp++;
	 * 
	 * if(intTemp<10) {
	 * custIdtoInsert=newtempvalforbelowten.concat(String.valueOf(intTemp)); }
	 * 
	 * if(intTemp>=10 && intTemp<100 ) {
	 * custIdtoInsert=newtempvalforbelowhundred.concat(String.valueOf(intTemp)); }
	 * 
	 * if(intTemp>=100 && intTemp<1000 ) {
	 * custIdtoInsert=newtempvalforbelowhundred.concat(String.valueOf(intTemp)); }
	 * 
	 * if(intTemp>=1000 && intTemp<10000 ) {
	 * custIdtoInsert=newtempvalforbelowhundred.concat(String.valueOf(intTemp)); }
	 * 
	 * if(intTemp>=10000 && intTemp<100000 ) {
	 * custIdtoInsert=newtempvalforbelowhundred.concat(String.valueOf(intTemp)); }
	 * 
	 * System.out.println("CGTMSE"+dddd+moon+yyyy+custIdtoInsert);
	 * 
	 * custIdFinal="CGTMSE"+dddd+moon+yyyy+custIdtoInsert;
	 * 
	 * 
	 * 
	 * }
	 * 
	 * else //(dateFrmDb.equals(datefrntendtemp)) {
	 * 
	 * custIdFinal="CGTMSE"+dddd+moon+yyyy+"000001";
	 * 
	 * System.out.println("CGTMSE"+dddd+moon+yyyy+"000001");
	 * 
	 * }
	 * 
	 * 
	 * 
	 * int ddd3=0;
	 * 
	 * Connection connection6 = DBConnection.getConnection(); Statement
	 * str6=connection.createStatement(); String query6 =
	 * "select max(PTD_SEC_NO) from PAYMENT_TRANSACTION_DETAIL"; ResultSet
	 * rsforavailmemid6 = str.executeQuery(query6); if(rsforavailmemid6.next()){ //
	 * System.out.println(rsforavailmemid.getDate(0));
	 * System.out.println(rsforavailmemid6.getInt(1));
	 * ddd3=rsforavailmemid6.getInt(1); ddd3++; // d=ddd.getDate(); }
	 * 
	 * 
	 * 
	 * Connection connection3 = DBConnection.getConnection(); Statement
	 * str3=connection1.createStatement();
	 * 
	 * String query3 = " insert into  PAYMENT_TRANSACTION_DETAIL  values ('"+ddd3
	 * +"','"+paymentDetails
	 * .getPaymentId()+"','"+paymentDetails.getInstrumentAmount
	 * ()+"','"+actionForm.getMemberId()+"','"+custIdFinal+"','I',sysdate)";
	 * 
	 * System.out.println(query1); int resValue = str3.executeUpdate(query3);
	 * System.out.println(resValue);
	 * 
	 * 
	 * 
	 * 
	 * String inttempval=String.valueOf(intTemp);
	 * 
	 * System.out.println("CGTMSE"+dddd+moon+yyyy+inttempval);
	 * 
	 * 
	 * 
	 * System.out.println(calendar.get(1));
	 * 
	 * System.out.println("CGTMSE"+day+month+year);
	 * 
	 * 
	 * 
	 * // String
	 * checksumCalMessageForSend=merchantId+"|"+custIdFinal+"|NA|"+paymentDetails .
	 * getInstrumentAmount()+"|"+"NA|NA|NA|INR|NA|R|xbcmecgtms|NA|NA|F|"+actionForm
	 * .getMemberId()+"|"+paymentDetails.getPaymentId()+
	 * "|NA|NA|NA|NA|NA|http://www.cgtmse.in/jsp/ReceiptsPayments/PaymentGatewayReturn.jsp|"
	 * +chckcalucl;
	 * 
	 * String checksumCalMessageForSend=merchantId+"|"+paymentDetails.getPaymentId
	 * ()+"|NA|"+paymentDetails.getInstrumentAmount()+"|"+
	 * "NA|NA|NA|INR|NA|R|xbcmecgtms|NA|NA|F|"+actionForm.getMemberId()+
	 * "|NA|NA|NA|NA|NA|NA|http://www.cgtmse.in/jsp/ReceiptsPayments/PaymentGatewayReturn.jsp|"
	 * +chckcalucl;
	 * 
	 * request.setAttribute("memId",actionForm.getMemberId() );
	 * 
	 * request.setAttribute("payid",paymentDetails.getPaymentId() );
	 * 
	 * request.setAttribute("instruAmt",paymentDetails.getInstrumentAmount() );
	 * 
	 * request.setAttribute("checksumcaluclatedval",chckcalucl );
	 * 
	 * request.setAttribute("checksumCalMessageForSending",checksumCalMessageForSend
	 * );
	 * 
	 * 
	 * 
	 * // request.setAttribute("message", //
	 * "Payment Allocated Successfully.<BR>Payment ID : " + // paymentId);
	 * 
	 * Log.log(5, "RPAction", methodName, "Exited");
	 * 
	 * return mapping.findForward("success"); }
	 */

	// koteswar payment gateway end

	public RPAction() {
		$init$();
	}

	/* Added by Kailash */

	public ActionForward getOnlineAFDANsLive(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RPActionForm rpForm = (RPActionForm) form;
		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = "";

		if (rpForm.getDanSummaries() != null) {
			rpForm.getDanSummaries().clear();
		}
		if (rpForm.getDanPanDetails() != null) {
			rpForm.getDanPanDetails().clear();
		}
		if (rpForm.getCgpans() != null) {
			rpForm.getCgpans().clear();
		}
		if (rpForm.getAllocatedFlags() != null) {
			rpForm.getAllocatedFlags().clear();
		}
		if (rpForm.getFirstDisbursementDates() != null) {
			rpForm.getFirstDisbursementDates().clear();
		}
		if (rpForm.getNotAllocatedReasons() != null) {
			rpForm.getNotAllocatedReasons().clear();
		}
		if (rpForm.getAllocatedFlags() != null) {
			rpForm.getAllocatedFlags().clear();
		}
		if (rpForm.getAppropriatedFlags() != null) {
			rpForm.getAppropriatedFlags().clear();
		}
		if (rpForm.getAmountsRaised() != null) {
			rpForm.getAmountsRaised().clear();
		}
		if (rpForm.getAmountBeingPaid() != null) {
			rpForm.getAmountBeingPaid().clear();
		}

		if (bankId.equals("0000")) {
			memberId = rpForm.getSelectMember();

			// if ((memberId == null) || (memberId.equals(""))) {
			// memberId = rpForm.getMemberId();
			// }

			Log.log(5, "RPAction", "getOnlineAFDANsLive", "mliId = " + memberId);

			if ((memberId == null) || (memberId.equals(""))) {
				// session.setAttribute("TARGET_URL",
				// "allocatePaymentsAll.do?method=getPendingAFDANsLive");
				return mapping.findForward("OnlineLiveAFInfo");
			}
			bankId = memberId.substring(0, 4);
			zoneId = memberId.substring(4, 8);
			branchId = memberId.substring(8, 12);

			ClaimsProcessor cpProcessor = new ClaimsProcessor();
			Vector memberIds = cpProcessor.getAllMemberIds();
			if (!memberIds.contains(memberId)) {
				throw new NoMemberFoundException("The Member ID does not exist");
			}
		}
		RpProcessor rpProcessor = new RpProcessor();
		ArrayList danSummaries = rpProcessor.displayOnlineLiveAFDANs(bankId, zoneId, branchId);

		Log.log(5, "RPAction", "getOnlineAFDANsLive", "dan summary size : " + danSummaries.size());

		if (danSummaries.size() == 0) {
			rpForm.setSelectMember(null);
			throw new MissingDANDetailsException("No DANs available for Allocation");
		}

		boolean isDanAvailable = false;
		for (int i = 0; i < danSummaries.size(); i++) {
			DANSummary danSummary = (DANSummary) danSummaries.get(i);
			if (danSummary.getAmountDue() != danSummary.getAmountPaid()) {
				isDanAvailable = true;
				break;
			}
		}
		if (!isDanAvailable) {
			rpForm.setSelectMember(null);
			throw new MissingDANDetailsException("No DANs available for Allocation");
		}
		rpForm.setDanSummaries(danSummaries);
		rpForm.setBankId(bankId);
		rpForm.setZoneId(zoneId);
		rpForm.setBranchId(branchId);

		Log.log(4, "RPAction", "getOnlineAFDANsLive", "Exited");
		if (rpForm.getSelectMember() != null) {
			rpForm.setMemberId(rpForm.getSelectMember());
		} else {
			rpForm.setMemberId(bankId + zoneId + branchId);
		}
		rpForm.setSelectMember(null);

		return mapping.findForward("OnlineLiveAFSummary");

	}

	public ActionForward getOnlineAFDANsExpired(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		RPActionForm rpForm = (RPActionForm) form;
		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = "";

		if (rpForm.getDanSummaries() != null) {
			rpForm.getDanSummaries().clear();
		}
		if (rpForm.getDanPanDetails() != null) {
			rpForm.getDanPanDetails().clear();
		}
		if (rpForm.getCgpans() != null) {
			rpForm.getCgpans().clear();
		}
		if (rpForm.getAllocatedFlags() != null) {
			rpForm.getAllocatedFlags().clear();
		}
		if (rpForm.getFirstDisbursementDates() != null) {
			rpForm.getFirstDisbursementDates().clear();
		}
		if (rpForm.getNotAllocatedReasons() != null) {
			rpForm.getNotAllocatedReasons().clear();
		}
		if (rpForm.getAllocatedFlags() != null) {
			rpForm.getAllocatedFlags().clear();
		}
		if (rpForm.getAppropriatedFlags() != null) {
			rpForm.getAppropriatedFlags().clear();
		}
		if (rpForm.getAmountsRaised() != null) {
			rpForm.getAmountsRaised().clear();
		}
		if (rpForm.getAmountBeingPaid() != null) {
			rpForm.getAmountBeingPaid().clear();
		}

		if (bankId.equals("0000")) {
			memberId = rpForm.getSelectMember();

			// if ((memberId == null) || (memberId.equals(""))) {
			// memberId = rpForm.getMemberId();
			// }

			Log.log(5, "RPAction", "getOnlineAFDANsExpired", "mliId = " + memberId);

			if ((memberId == null) || (memberId.equals(""))) {
				// session.setAttribute("TARGET_URL",
				// "allocatePaymentsAll.do?method=getPendingAFDANsLive");
				return mapping.findForward("OnlineExpiredAFInfo");
			}
			bankId = memberId.substring(0, 4);
			zoneId = memberId.substring(4, 8);
			branchId = memberId.substring(8, 12);

			ClaimsProcessor cpProcessor = new ClaimsProcessor();
			Vector memberIds = cpProcessor.getAllMemberIds();
			if (!memberIds.contains(memberId)) {
				throw new NoMemberFoundException("The Member ID does not exist");
			}
		}
		RpProcessor rpProcessor = new RpProcessor();
		ArrayList danSummaries = rpProcessor.displayOnlineExpiredAFDANs(bankId, zoneId, branchId);

		Log.log(5, "RPAction", "getOnlineAFDANsExpired", "dan summary size : " + danSummaries.size());

		if (danSummaries.size() == 0) {
			rpForm.setSelectMember(null);
			throw new MissingDANDetailsException("No DANs available for Allocation");
		}

		boolean isDanAvailable = false;
		for (int i = 0; i < danSummaries.size(); i++) {
			DANSummary danSummary = (DANSummary) danSummaries.get(i);
			if (danSummary.getAmountDue() != danSummary.getAmountPaid()) {
				isDanAvailable = true;
				break;
			}
		}
		if (!isDanAvailable) {
			rpForm.setSelectMember(null);
			throw new MissingDANDetailsException("No DANs available for Allocation");
		}
		rpForm.setDanSummaries(danSummaries);
		rpForm.setBankId(bankId);
		rpForm.setZoneId(zoneId);
		rpForm.setBranchId(branchId);

		Log.log(4, "RPAction", "getOnlineAFDANsExpired", "Exited");
		if (rpForm.getSelectMember() != null) {
			rpForm.setMemberId(rpForm.getSelectMember());
		} else {
			rpForm.setMemberId(bankId + zoneId + branchId);
		}
		rpForm.setSelectMember(null);

		return mapping.findForward("OnlineExpiredAFSummary");

	}

	public ActionForward getOnlineSFDANsLive(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RPActionForm rpForm = (RPActionForm) form;
		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = "";

		if (rpForm.getDanSummaries() != null) {
			rpForm.getDanSummaries().clear();
		}
		if (rpForm.getDanPanDetails() != null) {
			rpForm.getDanPanDetails().clear();
		}
		if (rpForm.getCgpans() != null) {
			rpForm.getCgpans().clear();
		}
		if (rpForm.getAllocatedFlags() != null) {
			rpForm.getAllocatedFlags().clear();
		}
		if (rpForm.getFirstDisbursementDates() != null) {
			rpForm.getFirstDisbursementDates().clear();
		}
		if (rpForm.getNotAllocatedReasons() != null) {
			rpForm.getNotAllocatedReasons().clear();
		}
		if (rpForm.getAllocatedFlags() != null) {
			rpForm.getAllocatedFlags().clear();
		}
		if (rpForm.getAppropriatedFlags() != null) {
			rpForm.getAppropriatedFlags().clear();
		}
		if (rpForm.getAmountsRaised() != null) {
			rpForm.getAmountsRaised().clear();
		}
		if (rpForm.getAmountBeingPaid() != null) {
			rpForm.getAmountBeingPaid().clear();
		}

		if (bankId.equals("0000")) {
			memberId = rpForm.getSelectMember();

			// if ((memberId == null) || (memberId.equals(""))) {
			// memberId = rpForm.getMemberId();
			// }

			Log.log(5, "RPAction", "getOnlineSFDANsLive", "mliId = " + memberId);

			if ((memberId == null) || (memberId.equals(""))) {
				// session.setAttribute("TARGET_URL",
				// "allocatePaymentsAll.do?method=getPendingAFDANsLive");
				return mapping.findForward("OnlineLiveSFInfo");
			}
			bankId = memberId.substring(0, 4);
			zoneId = memberId.substring(4, 8);
			branchId = memberId.substring(8, 12);

			ClaimsProcessor cpProcessor = new ClaimsProcessor();
			Vector memberIds = cpProcessor.getAllMemberIds();
			if (!memberIds.contains(memberId)) {
				throw new NoMemberFoundException("The Member ID does not exist");
			}
		}
		RpProcessor rpProcessor = new RpProcessor();
		ArrayList danSummaries = rpProcessor.displayOnlineLiveSFDANs(bankId, zoneId, branchId);
		Log.log(5, "RPAction", "getOnlineSFDANsLive", "dan summary size : " + danSummaries.size());

		if (danSummaries.size() == 0) {
			rpForm.setSelectMember(null);
			throw new MissingDANDetailsException("No DANs available for Allocation");
		}

		boolean isDanAvailable = false;
		for (int i = 0; i < danSummaries.size(); i++) {
			DANSummary danSummary = (DANSummary) danSummaries.get(i);

			System.out.println("danSummary.getDanId():" + danSummary.getDanId() + "--danSummary.getAmountDue():"
					+ danSummary.getAmountDue() + "--danSummary.getAmountPaid():" + danSummary.getAmountPaid());
			if (danSummary.getAmountDue() != danSummary.getAmountPaid()) {
				isDanAvailable = true;
				break;
			}
		}
		if (!isDanAvailable) {
			rpForm.setSelectMember(null);
			throw new MissingDANDetailsException("No DANs available for Allocation");
		}
		rpForm.setDanSummaries(danSummaries);
		rpForm.setBankId(bankId);
		rpForm.setZoneId(zoneId);
		rpForm.setBranchId(branchId);

		Log.log(4, "RPAction", "getOnlineSFDANsLive", "Exited");
		if (rpForm.getSelectMember() != null) {
			rpForm.setMemberId(rpForm.getSelectMember());
		} else {
			rpForm.setMemberId(bankId + zoneId + branchId);
		}
		rpForm.setSelectMember(null);
		return mapping.findForward("OnlineLiveSFSummary");
	}

	public ActionForward getOnlineSFDANsExpired(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RPActionForm rpForm = (RPActionForm) form;
		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = "";

		if (rpForm.getDanSummaries() != null) {
			rpForm.getDanSummaries().clear();
		}
		if (rpForm.getDanPanDetails() != null) {
			rpForm.getDanPanDetails().clear();
		}
		if (rpForm.getCgpans() != null) {
			rpForm.getCgpans().clear();
		}
		if (rpForm.getAllocatedFlags() != null) {
			rpForm.getAllocatedFlags().clear();
		}
		if (rpForm.getFirstDisbursementDates() != null) {
			rpForm.getFirstDisbursementDates().clear();
		}
		if (rpForm.getNotAllocatedReasons() != null) {
			rpForm.getNotAllocatedReasons().clear();
		}
		if (rpForm.getAllocatedFlags() != null) {
			rpForm.getAllocatedFlags().clear();
		}
		if (rpForm.getAppropriatedFlags() != null) {
			rpForm.getAppropriatedFlags().clear();
		}
		if (rpForm.getAmountsRaised() != null) {
			rpForm.getAmountsRaised().clear();
		}
		if (rpForm.getAmountBeingPaid() != null) {
			rpForm.getAmountBeingPaid().clear();
		}

		if (bankId.equals("0000")) {
			memberId = rpForm.getSelectMember();

			// if ((memberId == null) || (memberId.equals(""))) {
			// memberId = rpForm.getMemberId();
			// }
			Log.log(5, "RPAction", "getOnlineSFDANsExpired", "mliId = " + memberId);

			if ((memberId == null) || (memberId.equals(""))) {
				// session.setAttribute("TARGET_URL",
				// "allocatePaymentsAll.do?method=getPendingAFDANsLive");
				return mapping.findForward("OnlineExpiredSFInfo");
			}

			bankId = memberId.substring(0, 4);
			zoneId = memberId.substring(4, 8);
			branchId = memberId.substring(8, 12);

			ClaimsProcessor cpProcessor = new ClaimsProcessor();
			Vector memberIds = cpProcessor.getAllMemberIds();
			if (!memberIds.contains(memberId)) {
				throw new NoMemberFoundException("The Member ID does not exist");
			}
		}
		RpProcessor rpProcessor = new RpProcessor();
		ArrayList danSummaries = rpProcessor.displayOnlineExpiredSFDANs(bankId, zoneId, branchId);

		Log.log(5, "RPAction", "getOnlineSFDANsExpired", "dan summary size : " + danSummaries.size());

		if (danSummaries.size() == 0) {
			rpForm.setSelectMember(null);
			throw new MissingDANDetailsException("No DANs available for Allocation");
		}

		boolean isDanAvailable = false;
		for (int i = 0; i < danSummaries.size(); i++) {
			DANSummary danSummary = (DANSummary) danSummaries.get(i);
			if (danSummary.getAmountDue() != danSummary.getAmountPaid()) {
				isDanAvailable = true;
				break;
			}
		}
		if (!isDanAvailable) {
			rpForm.setSelectMember(null);

			throw new MissingDANDetailsException("No DANs available for Allocation.");
		}
		rpForm.setDanSummaries(danSummaries);
		rpForm.setBankId(bankId);
		rpForm.setZoneId(zoneId);
		rpForm.setBranchId(branchId);

		Log.log(4, "RPAction", "getOnlineSFDANsExpired", "Exited");
		if (rpForm.getSelectMember() != null) {
			rpForm.setMemberId(rpForm.getSelectMember());
		} else {
			rpForm.setMemberId(bankId + zoneId + branchId);
		}
		rpForm.setSelectMember(null);
		return mapping.findForward("OnlineExpiredSFSummary");
	}

	/// rajuk

	public ActionForward allocategetLiveAFDANsPaymentDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		RPActionForm actionForm = (RPActionForm) form;
		double totalAmount = 0.0D;
		StringTokenizer tokenizer = null;
		Map appropriatedCases = actionForm.getAppropriatedFlags();
		if (appropriatedCases.size() < 1)
			throw new MissingDANDetailsException("Please select atleast one dan for allocation.");

		if (appropriatedCases.size() >= 200) {
			throw new NoMemberFoundException("Please select maximum  200 dans for Allocation");

		}

		Set appropriatedCasesSet = appropriatedCases.keySet();
		Iterator appropriatedCasesIterator = appropriatedCasesSet.iterator();
		String token = null;
		String token1 = null;
		float total = 0.0F;
		float total2 = 0.0F;

		while (appropriatedCasesIterator.hasNext()) {
			String key = (String) appropriatedCasesIterator.next();
			for (tokenizer = new StringTokenizer(key, "#"); tokenizer.hasMoreTokens(); System.out.println(total2)) {
				token = tokenizer.nextToken();
				token1 = tokenizer.nextToken();
				total = Integer.parseInt(token1);
				total2 += total;
			}
		}

		User user = getUserInformation(request);
		// User user = getUserInformation(request);
		String bankid = (user.getBankId()).trim();
		String zoneid = (user.getZoneId()).trim();
		String branchid = (user.getBranchId()).trim();
		String memberId = bankid + zoneid + branchid;

		Registration registration = new Registration();
		LogClass.StepWritterForXXXModule("RPAction member=" + memberId + "=", "OnlineASF.txt");
		CollectingBank collectingBank = registration.getCollectingBank(memberId);

		actionForm.setModeOfPayment("");
		actionForm.setPaymentDate(null);
		actionForm.setInstrumentNo("");
		actionForm.setInstrumentDate(null);
		actionForm.setDrawnAtBank("");
		actionForm.setDrawnAtBranch("");
		actionForm.setPayableAt("");
		IFProcessor ifProcessor = new IFProcessor();
		ArrayList instruments = ifProcessor.getInstrumentTypes("G");
		actionForm.setInstruments(instruments);
		actionForm.setCollectingBank(collectingBank.getCollectingBankId());
		actionForm.setCollectingBankName(collectingBank.getCollectingBankName());
		actionForm.setCollectingBankBranch(collectingBank.getBranchName());
		actionForm.setAccountNumber(collectingBank.getAccNo());

		actionForm.setInstrumentAmount(total2);

		return mapping.findForward("allocateliveafdanpaymentdetails");
	}

	public ActionForward allocategetExpiredAFDANsPaymentDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		RPActionForm actionForm = (RPActionForm) form;
		double totalAmount = 0.0D;
		StringTokenizer tokenizer = null;
		Map appropriatedCases = actionForm.getAppropriatedFlags();
		if (appropriatedCases.size() < 1)
			throw new MissingDANDetailsException("Please select atleast one dan for allocation .");

		if (appropriatedCases.size() >= 200) {
			throw new NoMemberFoundException("Please select maximum  200 dans for Allocation");

		}
		Set appropriatedCasesSet = appropriatedCases.keySet();
		Iterator appropriatedCasesIterator = appropriatedCasesSet.iterator();
		String token = null;
		String token1 = null;
		float total = 0.0F;
		float total2 = 0.0F;
		while (appropriatedCasesIterator.hasNext()) {
			String key = (String) appropriatedCasesIterator.next();
			for (tokenizer = new StringTokenizer(key, "#"); tokenizer.hasMoreTokens(); System.out.println(total2)) {
				token = tokenizer.nextToken();
				token1 = tokenizer.nextToken();
				total = Integer.parseInt(token1);
				total2 += total;
			}
		}

		User user = getUserInformation(request);
		// User user = getUserInformation(request);
		String bankid = (user.getBankId()).trim();
		String zoneid = (user.getZoneId()).trim();
		String branchid = (user.getBranchId()).trim();
		String memberId = bankid + zoneid + branchid;
		Registration registration = new Registration();

		CollectingBank collectingBank = registration.getCollectingBank(memberId);
		Log.log(5, "RPAction", "getExpiredAFDANsPaymentDetails",
				(new StringBuilder()).append("collectingBank ").append(collectingBank).toString());
		actionForm.setModeOfPayment("");
		actionForm.setPaymentDate(null);
		actionForm.setInstrumentNo("");
		actionForm.setInstrumentDate(null);
		actionForm.setDrawnAtBank("");
		actionForm.setDrawnAtBranch("");
		actionForm.setPayableAt("");
		IFProcessor ifProcessor = new IFProcessor();
		ArrayList instruments = ifProcessor.getInstrumentTypes("G");
		actionForm.setInstruments(instruments);
		actionForm.setCollectingBank(collectingBank.getCollectingBankId());
		actionForm.setCollectingBankName(collectingBank.getCollectingBankName());
		actionForm.setCollectingBankBranch(collectingBank.getBranchName());
		actionForm.setAccountNumber(collectingBank.getAccNo());
		actionForm.setInstrumentAmount(total2);
		return mapping.findForward("allocateexpiredafdanpaymentdetails");
	}

	// For live SF DANS
	public ActionForward allocategetLiveASFDANsPaymentDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		RPActionForm actionForm = (RPActionForm) form;
		double totalAmount = 0.0D;
		StringTokenizer tokenizer = null;
		Map appropriatedCases = actionForm.getAppropriatedFlags();
		if (appropriatedCases.size() < 1)
			throw new MissingDANDetailsException("Please select atleast one dan for allocation .");

		if (appropriatedCases.size() >= 200) {
			throw new NoMemberFoundException("Please select maximum  200 dans for Allocation");

		}
		Set appropriatedCasesSet = appropriatedCases.keySet();
		Iterator appropriatedCasesIterator = appropriatedCasesSet.iterator();
		String token = null;
		String token1 = null;
		float total = 0.0F;
		float total2 = 0.0F;
		while (appropriatedCasesIterator.hasNext()) {
			String key = (String) appropriatedCasesIterator.next();
			for (tokenizer = new StringTokenizer(key, "#"); tokenizer.hasMoreTokens(); System.out.println(total2)) {
				token = tokenizer.nextToken();
				token1 = tokenizer.nextToken();
				total = Integer.parseInt(token1);
				total2 += total;
			}
		}

		User user = getUserInformation(request);
		// User user = getUserInformation(request);
		String bankid = (user.getBankId()).trim();
		String zoneid = (user.getZoneId()).trim();
		String branchid = (user.getBranchId()).trim();
		String memberId = bankid + zoneid + branchid;
		Registration registration = new Registration();
		Log.log(5, "RPAction", "getLiveASFDANsPaymentDetails",
				(new StringBuilder()).append("member id ").append(actionForm.getMemberId()).toString());
		CollectingBank collectingBank = registration.getCollectingBank(memberId);
		Log.log(5, "RPAction", "getLiveASFDANsPaymentDetails",
				(new StringBuilder()).append("collectingBank ").append(collectingBank).toString());
		actionForm.setModeOfPayment("");
		actionForm.setPaymentDate(null);
		actionForm.setInstrumentNo("");
		actionForm.setInstrumentDate(null);
		actionForm.setDrawnAtBank("");
		actionForm.setDrawnAtBranch("");
		actionForm.setPayableAt("");
		IFProcessor ifProcessor = new IFProcessor();
		ArrayList instruments = ifProcessor.getInstrumentTypes("G");
		actionForm.setInstruments(instruments);
		actionForm.setCollectingBank(collectingBank.getCollectingBankId());
		actionForm.setCollectingBankName(collectingBank.getCollectingBankName());
		actionForm.setCollectingBankBranch(collectingBank.getBranchName());
		actionForm.setAccountNumber(collectingBank.getAccNo());
		actionForm.setInstrumentAmount(total2);
		return mapping.findForward("allocateliveasfdanpaymentdetails");
	}

	// FOR Expired SF Dans
	public ActionForward allocategetExpiredASFDANsPaymentDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		RPActionForm actionForm = (RPActionForm) form;
		double totalAmount = 0.0D;
		StringTokenizer tokenizer = null;
		double tot = 0.0D;
		Map appropriatedCases = actionForm.getAppropriatedFlags();
		if (appropriatedCases.size() < 1)
			throw new MissingDANDetailsException("Please select atleast one dan for allocation .");

		if (appropriatedCases.size() >= 200) {
			throw new NoMemberFoundException("Please select maximum  200 dans for Allocation");

		}
		Set appropriatedCasesSet = appropriatedCases.keySet();
		Iterator appropriatedCasesIterator = appropriatedCasesSet.iterator();
		String token = null;
		String token1 = null;
		float total = 0.0F;
		float total2 = 0.0F;
		while (appropriatedCasesIterator.hasNext()) {
			String key = (String) appropriatedCasesIterator.next();
			for (tokenizer = new StringTokenizer(key, "#"); tokenizer.hasMoreTokens(); System.out.println(total2)) {
				token = tokenizer.nextToken();
				token1 = tokenizer.nextToken();
				total = Integer.parseInt(token1);
				total2 += total;
			}
		}

		User user = getUserInformation(request);
		// User user = getUserInformation(request);
		String bankid = (user.getBankId()).trim();
		String zoneid = (user.getZoneId()).trim();
		String branchid = (user.getBranchId()).trim();
		String memberId = bankid + zoneid + branchid;
		Registration registration = new Registration();
		Log.log(5, "RPAction", "getExpiredASFDANsPaymentDetails",
				(new StringBuilder()).append("member id ").append(actionForm.getMemberId()).toString());
		CollectingBank collectingBank = registration.getCollectingBank(memberId);
		Log.log(5, "RPAction", "getExpiredASFDANsPaymentDetails",
				(new StringBuilder()).append("collectingBank ").append(collectingBank).toString());
		actionForm.setModeOfPayment("");
		actionForm.setPaymentDate(null);
		actionForm.setInstrumentNo("");
		actionForm.setInstrumentDate(null);
		actionForm.setDrawnAtBank("");
		actionForm.setDrawnAtBranch("");
		actionForm.setPayableAt("");
		IFProcessor ifProcessor = new IFProcessor();
		ArrayList instruments = ifProcessor.getInstrumentTypes("G");
		actionForm.setInstruments(instruments);
		actionForm.setCollectingBank(collectingBank.getCollectingBankId());
		actionForm.setCollectingBankName(collectingBank.getCollectingBankName());
		actionForm.setCollectingBankBranch(collectingBank.getBranchName());
		actionForm.setAccountNumber(collectingBank.getAccNo());
		actionForm.setInstrumentAmount(total2);
		return mapping.findForward("allocateexpiredasfdanpaymentdetails");
	}

	public ActionForward asfdisplayallocatePaymentFinal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Log.log(Log.INFO, "ClaimAction", "displayClaimProcessingInput", "Entered");
		Connection connection = DBConnection.getConnection();
		String dantype = request.getParameter("mode").substring(0, 2);
		String danLiveType = request.getParameter("mode").substring(2, 5);
		System.out.println("dantype===" + dantype);
		System.out.println("danLiveType===" + danLiveType);
		HttpSession session = request.getSession();
		PreparedStatement allocatePaymentfinalStmt;
		ResultSet allocatePaymentFinalResult;
		User user = (User) getUserInformation(request);
		System.out.println("user" + user);
		String bankid = (user.getBankId()).trim();
		String zoneid = (user.getZoneId()).trim();
		String branchid = (user.getBranchId()).trim();
		String memberId = bankid + zoneid + branchid;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Log.log(Log.INFO, "ClaimAction", "displayClaimProcessingInput", "Exited");
		ArrayList rpArray = new ArrayList();
		RPActionForm actionFormobj = (RPActionForm) form;
//if (dantype="AF")
		try {

			String query = "select PAY_ID, VIRTUAL_ACCOUNT_NO, AMOUNT, Pay_ID_CREAted_date from subdebt_payment_info where DAN_TYPE='"
					+ dantype + "'  and  LIVE_EXPRIED='" + danLiveType
					+ "'  and PAYMENT_STATUS='N' and mem_bnk_id||mem_zne_id||mem_brn_id = '" + memberId + "'";

			System.out.println("query==is===" + query);

			allocatePaymentfinalStmt = connection.prepareStatement(query);
			allocatePaymentFinalResult = allocatePaymentfinalStmt.executeQuery();

			while (allocatePaymentFinalResult.next()) {

				RPActionForm actionForm = new RPActionForm();

				actionForm.setPaymentIdF(allocatePaymentFinalResult.getString(1));

				actionForm.setVitrualAcF(allocatePaymentFinalResult.getString(2));
				actionForm.setAmtF(allocatePaymentFinalResult.getDouble(3));
				actionForm.setRPDATEF(allocatePaymentFinalResult.getString(4));

				String paymentIds = allocatePaymentFinalResult.getString(1);
				actionForm.setPaymentIds("paymentIds");

				rpArray.add(actionForm);

			}
			actionFormobj.setAllocatepaymentFinal(rpArray);
		} catch (Exception exception) {
			Log.logException(exception);
			throw new DatabaseException(exception.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}
		return mapping.findForward("asfdisplayallocatePaymentFinal");
	}

	// for Intiate Final
	public ActionForward asfdisplayallocatePaymentFinalSubmit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("asfdisplayallocatePaymentFinalSubmit===11210");
		HttpSession session = request.getSession();
		RPActionForm actionFormobj = (RPActionForm) form;
		PreparedStatement makePaymentfinalStmt;
		ResultSet makePaymentFinalResult;

		String paymentID = request.getParameter("paymentIds");

		// List approveFlags1 = actionFormobj.getMakepaymentList();

		Map approveFlags = actionFormobj.getAllocationPaymentFinalSubmit();
		session.setAttribute("approvedData", approveFlags);

		System.out.println("payid " + approveFlags);

		if (approveFlags.size() == 0) {
			throw new NoMemberFoundException("Please select atleast one PAYMENT-ID to MAKE PAYMENT.");
		}

		System.out.println("payid " + approveFlags);
		System.out.println("payid value approveFlags " + approveFlags.size());
		Set keys = approveFlags.keySet();

		System.out.println("konkati" + keys);

		User user = getUserInformation(request);
		String userid = user.getUserId();
		String bankid = (user.getBankId()).trim();
		String zoneid = (user.getZoneId()).trim();
		String branchid = (user.getBranchId()).trim();
		String memberId = bankid + zoneid + branchid;
		// Connection connection = DBConnection.getConnection();
		// Statement str1 = connection.createStatement();

		Connection connection = DBConnection.getConnection();
		CallableStatement cStmt = null;
		String errorCode = "";
		ArrayList rpArray = new ArrayList();
		Iterator PaymentIterate = keys.iterator();
		int insdanstatus = 0;
		RPActionForm actionForm = null;

		while (PaymentIterate.hasNext())

		{
			actionForm = new RPActionForm();
			String payids = (String) PaymentIterate.next();

			System.out.println("keys are" + payids);

			String arr[] = payids.split("@");
			System.out.println("PayID " + arr[0]);
			actionForm.setPaymentIdR(arr[0]);

			actionForm.setAmmount2(Integer.parseInt(arr[1]));
			actionForm.setVaccno(arr[2]);

			actionForm.setPaymentInitiateDate(new Date());
			rpArray.add(actionForm);

			try {
				String query = "select PAYMENT_STATUS  from online_payment_detail where PAY_ID='" + arr[0]
						+ "' and PAYMENT_STATUS='X'";

				makePaymentfinalStmt = connection.prepareStatement(query);
				makePaymentFinalResult = makePaymentfinalStmt.executeQuery();

				if (makePaymentFinalResult.next()) {

					throw new NoMemberFoundException("Payment already done");
				}

				try {

					cStmt = connection.prepareCall("{ call PACK_ONLINE_PAYMENT_DETAIL.PROC_XML_GENRATE(?,?)}");

					System.out.println("proc_dan_deallocation  line 11288===" + cStmt);

					cStmt.setString(1, arr[0]);

					cStmt.registerOutParameter(2, java.sql.Types.VARCHAR);

					cStmt.execute();
					// status = callableStmt.getInt(1);

					String error = cStmt.getString(2);

					Log.log(5, "RPAction", "cancelRpAppropriation",
							"Error code and error are " + errorCode + " " + error);

					if (error != null) {
						connection.rollback();
						throw new DatabaseException(error);

					}
				} catch (SQLException e) {
					try {
						connection.rollback();
					} catch (SQLException ignore) {
						Log.log(2, "RPAction", "cancelRpAppropriation", ignore.getMessage());
					}

					Log.log(2, "RPAction", "cancelRpAppropriation", e.getMessage());
					Log.logException(e);

					throw new DatabaseException(e.getMessage());
				}
				//
			} finally {
				DBConnection.freeConnection(connection);
			}

		}

		actionFormobj.setMakepaymentFinal(rpArray);

		return mapping.findForward("asfdisplayallocatePaymentFinalSubmitonline");
	}

	// rajuk
	public ActionForward asfmodifyonline(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.initialize(mapping);
		return mapping.findForward("success");
	}

	public ActionForward asfintiateyonline(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.initialize(mapping);
		return mapping.findForward("success");
	}

	public ActionForward asfdisplayallocatePaymentModifySubmit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Log.log(Log.INFO, "ClaimAction", "displayClaimProcessingInput", "Entered");
		String dantype = request.getParameter("mode").substring(0, 2);
		String danLiveType = request.getParameter("mode").substring(2, 5);
		System.out.println("dantype===" + dantype);
		System.out.println("danLiveType===" + danLiveType);

		Connection connection = DBConnection.getConnection();
		// System.out.println("connection success fully");
		HttpSession session = request.getSession();
		PreparedStatement allocateModifyStmt;
		ResultSet allocateModifyResult;
		User user = (User) getUserInformation(request);
		// System.out.println("user"+user);
		String bankid = (user.getBankId()).trim();
		String zoneid = (user.getZoneId()).trim();
		String branchid = (user.getBranchId()).trim();

		String memberId = bankid + zoneid + branchid;
		// System.out.println("memberId"+memberId);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Log.log(Log.INFO, "ClaimAction", "displayClaimProcessingInput", "Exited");
		ArrayList rpArray = new ArrayList();
		RPActionForm actionFormobj = (RPActionForm) form;

		try {

			// String
			// query="select PAY_ID, VIRTUAL_ACCOUNT_NO, AMOUNT,
			// TO_CHAR(Pay_ID_CREAted_date, 'DD-MM-YYYY HH24:MI:SS') from
			// online_payment_detail where PAYMENT_STATUS ='N' ";
			// old
			String query = "select PAY_ID, VIRTUAL_ACCOUNT_NO, AMOUNT, TO_CHAR(Pay_ID_CREAted_date, 'DD-MM-YYYY HH24:MI:SS') from online_payment_detail where DAN_TYPE='"
					+ dantype + "' and LIVE_EXPRIED='" + danLiveType
					+ "' and  PAYMENT_STATUS ='N'and mem_bnk_id||mem_zne_id||mem_brn_id = '" + memberId + "'";
			System.out.println("query" + query);

			allocateModifyStmt = connection.prepareStatement(query);
			allocateModifyResult = allocateModifyStmt.executeQuery();

			while (allocateModifyResult.next()) {

				RPActionForm actionForm = new RPActionForm();

				actionForm.setPaymentId1(allocateModifyResult.getString(1));

				actionForm.setVaccno(allocateModifyResult.getString(2));
				actionForm.setAmmount1(allocateModifyResult.getDouble(3));
				actionForm.setPayidcreateddate(allocateModifyResult.getString(4));

				String paymentIds = allocateModifyResult.getString(1);
				actionForm.setPaymentIds("paymentIds");

				rpArray.add(actionForm);

			}
			actionFormobj.setAllocatepaymentmodify(rpArray);
		} catch (Exception exception) {
			Log.logException(exception);
			throw new DatabaseException(exception.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		return mapping.findForward("asfdisplayallocatePaymentModifySubmit");

	}

	// rajuk
	// for all
	public ActionForward allocatesubmitLAFDANsPaymentDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		RPActionForm actionForm = (RPActionForm) form;
		RpProcessor rpProcessor = new RpProcessor();
		String paymentId = "";
		String methodName = "submitLiveASFDANsPaymentDetails";
		Log.log(5, "RPAction", methodName, "Entered");
		User user = getUserInformation(request);
		PaymentDetails paymentDetails = new PaymentDetails();
		String allocationType = actionForm.getAllocationType();
		paymentDetails.setAllocationType1("A");
		String modeOfPayment = actionForm.getModeOfPayment();
		String collectingBranch = actionForm.getCollectingBankBranch();
		java.util.Date paymentDate = actionForm.getPaymentDate();
		String instrumentNumber = actionForm.getInstrumentNo();
		java.util.Date instrumentDate = actionForm.getInstrumentDate();
		String modeOfDelivery = actionForm.getModeOfDelivery();
		double instrumentAmount = actionForm.getInstrumentAmount();
		String drawnAtBank = actionForm.getDrawnAtBank();
		String drawnAtBranch = actionForm.getDrawnAtBranch();
		String payableAt = actionForm.getPayableAt();
		String accNumber = actionForm.getAccountNumber();
		Log.log(5, "RPAction", methodName, "collecting bank " + actionForm.getCollectingBankName());
		paymentDetails.setCollectingBank(actionForm.getCollectingBankName());
		Log.log(5, "RPAction", methodName, "mode of payment " + modeOfPayment);
		paymentDetails.setModeOfPayment(modeOfPayment);
		Log.log(5, "RPAction", methodName, "collecting branch " + collectingBranch);
		paymentDetails.setCollectingBankBranch(collectingBranch);
		Log.log(5, "RPAction", methodName, "payment date " + paymentDate);
		paymentDetails.setPaymentDate(paymentDate);
		Log.log(5, "RPAction", methodName, "instrument number " + instrumentNumber);
		paymentDetails.setInstrumentNo(instrumentNumber);
		Log.log(5, "RPAction", methodName, "instrument date " + instrumentDate);
		paymentDetails.setInstrumentDate(instrumentDate);
		Log.log(5, "RPAction", methodName, "mode of delivery " + modeOfDelivery);
		paymentDetails.setModeOfDelivery(modeOfDelivery);
		Log.log(5, "RPAction", methodName, "instrument amount " + instrumentAmount);
		paymentDetails.setInstrumentAmount(instrumentAmount);
		Log.log(5, "RPAction", methodName, "drawn at bank " + drawnAtBank);
		paymentDetails.setDrawnAtBank(drawnAtBank);
		Log.log(5, "RPAction", methodName, "drawn at branch " + drawnAtBranch);
		paymentDetails.setDrawnAtBranch(drawnAtBranch);
		Log.log(5, "RPAction", methodName, "payable at " + payableAt);
		paymentDetails.setPayableAt(payableAt);
		Log.log(5, "RPAction", methodName, "acc num " + accNumber);
		paymentDetails.setCgtsiAccNumber(accNumber);
		Map appropriatedFlags = actionForm.getAppropriatedFlags();
		Map cgpans = actionForm.getCgpans();
		Map danCgpanDetails = actionForm.getDanPanDetails();

		paymentId = rpProcessor.allocateCGDANonlinelaf(paymentDetails, appropriatedFlags, cgpans, danCgpanDetails,
				user);

		request.setAttribute("paymentId", paymentId);
		request.setAttribute("intrumentAmount", paymentDetails.getInstrumentAmount());

		return mapping.findForward("allocatesuccess");
	}

	public ActionForward allocatesubmitEAFDANsPaymentDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		RPActionForm actionForm = (RPActionForm) form;
		RpProcessor rpProcessor = new RpProcessor();
		String paymentId = "";
		String methodName = "submitLiveASFDANsPaymentDetails";
		Log.log(5, "RPAction", methodName, "Entered");
		User user = getUserInformation(request);
		PaymentDetails paymentDetails = new PaymentDetails();
		String allocationType = actionForm.getAllocationType();
		paymentDetails.setAllocationType1("A");
		String modeOfPayment = actionForm.getModeOfPayment();
		String collectingBranch = actionForm.getCollectingBankBranch();
		java.util.Date paymentDate = actionForm.getPaymentDate();
		String instrumentNumber = actionForm.getInstrumentNo();
		java.util.Date instrumentDate = actionForm.getInstrumentDate();
		String modeOfDelivery = actionForm.getModeOfDelivery();
		double instrumentAmount = actionForm.getInstrumentAmount();
		String drawnAtBank = actionForm.getDrawnAtBank();
		String drawnAtBranch = actionForm.getDrawnAtBranch();
		String payableAt = actionForm.getPayableAt();
		String accNumber = actionForm.getAccountNumber();
		Log.log(5, "RPAction", methodName, "collecting bank " + actionForm.getCollectingBankName());
		paymentDetails.setCollectingBank(actionForm.getCollectingBankName());
		Log.log(5, "RPAction", methodName, "mode of payment " + modeOfPayment);
		paymentDetails.setModeOfPayment(modeOfPayment);
		Log.log(5, "RPAction", methodName, "collecting branch " + collectingBranch);
		paymentDetails.setCollectingBankBranch(collectingBranch);
		Log.log(5, "RPAction", methodName, "payment date " + paymentDate);
		paymentDetails.setPaymentDate(paymentDate);
		Log.log(5, "RPAction", methodName, "instrument number " + instrumentNumber);
		paymentDetails.setInstrumentNo(instrumentNumber);
		Log.log(5, "RPAction", methodName, "instrument date " + instrumentDate);
		paymentDetails.setInstrumentDate(instrumentDate);
		Log.log(5, "RPAction", methodName, "mode of delivery " + modeOfDelivery);
		paymentDetails.setModeOfDelivery(modeOfDelivery);
		Log.log(5, "RPAction", methodName, "instrument amount " + instrumentAmount);
		paymentDetails.setInstrumentAmount(instrumentAmount);
		Log.log(5, "RPAction", methodName, "drawn at bank " + drawnAtBank);
		paymentDetails.setDrawnAtBank(drawnAtBank);
		Log.log(5, "RPAction", methodName, "drawn at branch " + drawnAtBranch);
		paymentDetails.setDrawnAtBranch(drawnAtBranch);
		Log.log(5, "RPAction", methodName, "payable at " + payableAt);
		paymentDetails.setPayableAt(payableAt);
		Log.log(5, "RPAction", methodName, "acc num " + accNumber);
		paymentDetails.setCgtsiAccNumber(accNumber);
		Map appropriatedFlags = actionForm.getAppropriatedFlags();
		Map cgpans = actionForm.getCgpans();
		Map danCgpanDetails = actionForm.getDanPanDetails();

		paymentId = rpProcessor.allocateCGDANonlineeaf(paymentDetails, appropriatedFlags, cgpans, danCgpanDetails,
				user);

		request.setAttribute("paymentId", paymentId);
		request.setAttribute("intrumentAmount", paymentDetails.getInstrumentAmount());

		return mapping.findForward("allocatesuccess");
	}

	public ActionForward allocatesubmitLSFDANsPaymentDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		RPActionForm actionForm = (RPActionForm) form;
		RpProcessor rpProcessor = new RpProcessor();
		String paymentId = "";
		String methodName = "submitLiveASFDANsPaymentDetails";
		Log.log(5, "RPAction", methodName, "Entered");
		User user = getUserInformation(request);
		PaymentDetails paymentDetails = new PaymentDetails();
		String allocationType = actionForm.getAllocationType();
		paymentDetails.setAllocationType1("A");
		String modeOfPayment = actionForm.getModeOfPayment();
		String collectingBranch = actionForm.getCollectingBankBranch();
		java.util.Date paymentDate = actionForm.getPaymentDate();
		String instrumentNumber = actionForm.getInstrumentNo();
		java.util.Date instrumentDate = actionForm.getInstrumentDate();
		String modeOfDelivery = actionForm.getModeOfDelivery();
		double instrumentAmount = actionForm.getInstrumentAmount();
		String drawnAtBank = actionForm.getDrawnAtBank();
		String drawnAtBranch = actionForm.getDrawnAtBranch();
		String payableAt = actionForm.getPayableAt();
		String accNumber = actionForm.getAccountNumber();
		Log.log(5, "RPAction", methodName, "collecting bank " + actionForm.getCollectingBankName());
		paymentDetails.setCollectingBank(actionForm.getCollectingBankName());
		Log.log(5, "RPAction", methodName, "mode of payment " + modeOfPayment);
		paymentDetails.setModeOfPayment(modeOfPayment);
		Log.log(5, "RPAction", methodName, "collecting branch " + collectingBranch);
		paymentDetails.setCollectingBankBranch(collectingBranch);
		Log.log(5, "RPAction", methodName, "payment date " + paymentDate);
		paymentDetails.setPaymentDate(paymentDate);
		Log.log(5, "RPAction", methodName, "instrument number " + instrumentNumber);
		paymentDetails.setInstrumentNo(instrumentNumber);
		Log.log(5, "RPAction", methodName, "instrument date " + instrumentDate);
		paymentDetails.setInstrumentDate(instrumentDate);
		Log.log(5, "RPAction", methodName, "mode of delivery " + modeOfDelivery);
		paymentDetails.setModeOfDelivery(modeOfDelivery);
		Log.log(5, "RPAction", methodName, "instrument amount " + instrumentAmount);
		paymentDetails.setInstrumentAmount(instrumentAmount);
		Log.log(5, "RPAction", methodName, "drawn at bank " + drawnAtBank);
		paymentDetails.setDrawnAtBank(drawnAtBank);
		Log.log(5, "RPAction", methodName, "drawn at branch " + drawnAtBranch);
		paymentDetails.setDrawnAtBranch(drawnAtBranch);
		Log.log(5, "RPAction", methodName, "payable at " + payableAt);
		paymentDetails.setPayableAt(payableAt);
		Log.log(5, "RPAction", methodName, "acc num " + accNumber);
		paymentDetails.setCgtsiAccNumber(accNumber);
		Map appropriatedFlags = actionForm.getAppropriatedFlags();
		Map cgpans = actionForm.getCgpans();
		Map danCgpanDetails = actionForm.getDanPanDetails();

		paymentId = rpProcessor.allocateCGDANonlinelsf(paymentDetails, appropriatedFlags, cgpans, danCgpanDetails,
				user);

		request.setAttribute("paymentId", paymentId);
		request.setAttribute("intrumentAmount", paymentDetails.getInstrumentAmount());

		return mapping.findForward("allocatesuccess");
	}

	public ActionForward allocatesubmitESFDANsPaymentDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		RPActionForm actionForm = (RPActionForm) form;
		RpProcessor rpProcessor = new RpProcessor();
		String paymentId = "";
		String methodName = "submitLiveASFDANsPaymentDetails";
		Log.log(5, "RPAction", methodName, "Entered");
		User user = getUserInformation(request);
		PaymentDetails paymentDetails = new PaymentDetails();
		String allocationType = actionForm.getAllocationType();
		paymentDetails.setAllocationType1("A");
		String modeOfPayment = actionForm.getModeOfPayment();
		String collectingBranch = actionForm.getCollectingBankBranch();
		java.util.Date paymentDate = actionForm.getPaymentDate();
		String instrumentNumber = actionForm.getInstrumentNo();
		java.util.Date instrumentDate = actionForm.getInstrumentDate();
		String modeOfDelivery = actionForm.getModeOfDelivery();
		double instrumentAmount = actionForm.getInstrumentAmount();
		String drawnAtBank = actionForm.getDrawnAtBank();
		String drawnAtBranch = actionForm.getDrawnAtBranch();
		String payableAt = actionForm.getPayableAt();
		String accNumber = actionForm.getAccountNumber();
		Log.log(5, "RPAction", methodName, "collecting bank " + actionForm.getCollectingBankName());
		paymentDetails.setCollectingBank(actionForm.getCollectingBankName());
		Log.log(5, "RPAction", methodName, "mode of payment " + modeOfPayment);
		paymentDetails.setModeOfPayment(modeOfPayment);
		Log.log(5, "RPAction", methodName, "collecting branch " + collectingBranch);
		paymentDetails.setCollectingBankBranch(collectingBranch);
		Log.log(5, "RPAction", methodName, "payment date " + paymentDate);
		paymentDetails.setPaymentDate(paymentDate);
		Log.log(5, "RPAction", methodName, "instrument number " + instrumentNumber);
		paymentDetails.setInstrumentNo(instrumentNumber);
		Log.log(5, "RPAction", methodName, "instrument date " + instrumentDate);
		paymentDetails.setInstrumentDate(instrumentDate);
		Log.log(5, "RPAction", methodName, "mode of delivery " + modeOfDelivery);
		paymentDetails.setModeOfDelivery(modeOfDelivery);
		Log.log(5, "RPAction", methodName, "instrument amount " + instrumentAmount);
		paymentDetails.setInstrumentAmount(instrumentAmount);
		Log.log(5, "RPAction", methodName, "drawn at bank " + drawnAtBank);
		paymentDetails.setDrawnAtBank(drawnAtBank);
		Log.log(5, "RPAction", methodName, "drawn at branch " + drawnAtBranch);
		paymentDetails.setDrawnAtBranch(drawnAtBranch);
		Log.log(5, "RPAction", methodName, "payable at " + payableAt);
		paymentDetails.setPayableAt(payableAt);
		Log.log(5, "RPAction", methodName, "acc num " + accNumber);
		paymentDetails.setCgtsiAccNumber(accNumber);
		Map appropriatedFlags = actionForm.getAppropriatedFlags();
		Map cgpans = actionForm.getCgpans();
		Map danCgpanDetails = actionForm.getDanPanDetails();

		paymentId = rpProcessor.allocateCGDANonlineesf(paymentDetails, appropriatedFlags, cgpans, danCgpanDetails,
				user);

		request.setAttribute("paymentId", paymentId);
		request.setAttribute("intrumentAmount", paymentDetails.getInstrumentAmount());

		return mapping.findForward("allocatesuccess");
	}

	// Initiate code
	public ActionForward asfdisplayPaymentIdDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception

	{
		ReportManager manager;
		RPActionForm rappform;
		String clmApplicationStatus;
		String memberId;
		String cgpanno;
		String PaymentId = "";

		Log.log(4, "RPAction", "displayPaymentIdDetail", "Entered");
		manager = new ReportManager();
		rappform = (RPActionForm) form;
		clmApplicationStatus = "";
		Log.log(4, "RPAction", "displayPaymentIdDetail", (new StringBuilder())
				.append("Claim Application Status being queried :").append(clmApplicationStatus).toString());
		User user = getUserInformation(request);
		String bankid = user.getBankId().trim();
		String zoneid = user.getZoneId().trim();
		String branchid = user.getBranchId().trim();
		memberId = (new StringBuilder()).append(bankid).append(zoneid).append(branchid).toString();
		request.setAttribute("PaymentId", request.getParameter("PaymentId"));

		PaymentId = request.getParameter("PaymentId");
		System.out.println(PaymentId);
		ArrayList rpArray = new ArrayList();
		RPActionForm actionFormobj = (RPActionForm) form;

		PreparedStatement pstmt = null;
		ResultSet rst = null;
		String PayId = "";
		Connection connection = DBConnection.getConnection();

		Statement allocateStmt = connection.createStatement();

		ResultSet allocateModifyResult1 = null;

		try {
			String query = "select cgpan,dan_id,dci_amount_raised,to_char(DCI_ALLOCATION_DT, 'DD-MM-YYYY HH24:MI:SS') from dan_cgpan_info_temp where pay_id ='"
					+ PaymentId + "' ";
			System.out.println("query" + query);

			allocateModifyResult1 = allocateStmt.executeQuery(query);

			RPActionForm actionForm = null;

			while (allocateModifyResult1.next()) {

				actionForm = new RPActionForm();
				actionForm.setCgpan1(allocateModifyResult1.getString(1));
				actionForm.setDanid1(allocateModifyResult1.getString(2));
				actionForm.setAmmount2(allocateModifyResult1.getDouble(3));
				actionForm.setDandate1(allocateModifyResult1.getString(4));

				String allocatedanIds = allocateModifyResult1.getString(2);
				System.out.println("displayPaymentIdDetail allocatedanIds" + allocatedanIds);
				actionForm.setAllocatedanIds("allocatedanIds");
				actionForm.setPaymentId(request.getParameter("PaymentId"));
				rpArray.add(actionForm);

			}
			actionFormobj.setAllocatepayIDdetails(rpArray);
		} catch (Exception exception) {
			Log.logException(exception);
			throw new DatabaseException(exception.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}
		return mapping.findForward("success");
	}

	public ActionForward asfdisplayallocatePaymentModifySubmitDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		RPActionForm actionFormobj = (RPActionForm) form;

		String paymentID = request.getParameter("paymentIds");

		Map approveFlags = actionFormobj.getAllocationPaymentYes();

		if (approveFlags.size() == 0) {
			throw new NoMemberFoundException("Please select atleast one PAYMENT ID to Approve.");
		}
		/*
		 * if(approveFlags.size()>=50) { throw new NoMemberFoundException(
		 * "Please select Below  50 FLAGS For PAYMENT ID to Approve."); }
		 */

		System.out.println("payid " + approveFlags);
		System.out.println("payid value approveFlags " + approveFlags.size());
		Set keys = approveFlags.keySet();

		User user = getUserInformation(request);
		String userid = user.getUserId();
		String bankid = (user.getBankId()).trim();
		String zoneid = (user.getZoneId()).trim();
		String branchid = (user.getBranchId()).trim();
		String memberId = bankid + zoneid + branchid;
		Connection connection = DBConnection.getConnection();
		connection.setAutoCommit(false);
		Statement str1 = connection.createStatement();

		try {

			Iterator clmInterat = keys.iterator();
			int insdanstatus = 0;
			int deldanstatus = 0;
			int deldanstatus1 = 0;

			int inspaystatus = 0;

			int delpaystatus = 0;

			while (clmInterat.hasNext())

			{

				String payids = (String) clmInterat.next();

				String arr[] = payids.split("@");
				System.out.println("PayID " + arr[0]);

				String decision = (String) approveFlags.get(payids);

				System.out.println("keys are" + payids);

				System.out.println("values  are" + decision);

				java.util.Date todaydate = new Date();

				// if(decision.equals("Y"))

				{

					String quryforSelect = "insert into dan_cgpan_info_temp_canc select *  from dan_cgpan_info_temp where pay_id ='"
							+ arr[0] + "' ";
					insdanstatus = str1.executeUpdate(quryforSelect);
					System.out.println("testing1" + quryforSelect);

					String quryforSelect3 = "insert into payment_detail_temp_canc  select * from  payment_detail_temp where pay_id ='"
							+ arr[0] + "' ";
					System.out.println("testing1" + quryforSelect3);
					inspaystatus = str1.executeUpdate((quryforSelect3));

					String quryforSelect4 = "delete from payment_detail_temp where pay_id ='" + arr[0] + "'";
					System.out.println("testing1" + quryforSelect4);
					delpaystatus = str1.executeUpdate((quryforSelect4));

					String quryforSelect2 = "delete from dan_cgpan_info_temp where pay_id ='" + arr[0] + "'";
					System.out.println("testing2" + quryforSelect2);
					deldanstatus = str1.executeUpdate((quryforSelect2));

					String quryforSelect5 = "update  online_payment_detail set PAYMENT_STATUS ='C' where PAY_ID  ='"
							+ arr[0] + "'";
					System.out.println("testing3" + quryforSelect5);
					deldanstatus1 = str1.executeUpdate((quryforSelect5));

				}
				System.out.println("insdanstatus " + insdanstatus + "deldanstatus " + deldanstatus + "inspaystatus "
						+ inspaystatus + "delpaystatus " + delpaystatus + "deldanstatus1 " + deldanstatus1);
				if ((insdanstatus != 0) && (deldanstatus != 0) && (inspaystatus != 0) && (delpaystatus != 0)
						&& (deldanstatus1 != 0)) {

					connection.commit();
				} else {
					connection.rollback();
					throw new MissingDANDetailsException("not able to deallocate pay id. problem in  '" + arr[0] + "'");
				}

			}

		}

		catch (SQLException sqlexception) {
			// sqlexception.printStackTrace();
			connection.rollback();
			throw new DatabaseException(sqlexception.getMessage());

		} finally {
			Connection conn = null;
			DBConnection.freeConnection(conn);
		}

		connection.commit();

		request.setAttribute("message", " RP Modified / Cancelled successfully !!!");
		return mapping.findForward("success");

	}

	public ActionForward asfsubmitDanWiseDeallocation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Log.log(4, "RPAction", "generateClaimSFDAN", "Entered");

		HttpSession session = request.getSession();
		RPActionForm actionFormobj = (RPActionForm) form;

		String danID = request.getParameter("allocatedanIds");
		System.out.println("danID " + danID);
		Map approveFlags = actionFormobj.getAllocationPaymentDans();
		System.out.println("danID " + approveFlags);
		request.setAttribute("PaymentId", request.getParameter("PaymentId"));
		String PaymentId = "";
		PaymentId = request.getParameter("PaymentId");
		System.out.println(PaymentId);

		int size = 0;
		if (approveFlags.size() == 0) {
			throw new NoMemberFoundException("Please select atleast one DAN ID to Approve.");
		}

		System.out.println("danID " + approveFlags);
		System.out.println("danID value approveFlags " + approveFlags.size());
		Set keys = approveFlags.keySet();

		System.out.println("keys size " + keys.size());

		size = approveFlags.size();

		User user = getUserInformation(request);
		String userid = user.getUserId();
		String bankid = (user.getBankId()).trim();
		String zoneid = (user.getZoneId()).trim();
		String branchid = (user.getBranchId()).trim();
		String memberId = bankid + zoneid + branchid;
		Connection connection = DBConnection.getConnection();
		connection.setAutoCommit(false);
		Statement str1 = connection.createStatement();

		try {

			Iterator danIterat = keys.iterator();
			int insdanstatus1 = 0;
			int deldanstatus1 = 0;

			int inspaystatus1[] = new int[4];
			int delpaystatus1[] = new int[5];
			int count = 0;
			ResultSet rs = null;

			while (danIterat.hasNext()) {
				String danids = (String) danIterat.next();
				System.out.println("submitDanWiseDeallocation danids " + danids);
				String danIDPaymentID[] = danids.split("@");
				System.out.println("dan id== " + danIDPaymentID[0]);
				System.out.println("payment id==111 " + danIDPaymentID[1]);
				String quryforSelect4 = "select count(*) from dan_cgpan_info_temp where pay_id=(select pay_id from dan_cgpan_info_temp where dan_id='"
						+ danIDPaymentID[0] + "') ";
				System.out.println("testing quryforSelect4 " + quryforSelect4);
				rs = str1.executeQuery(quryforSelect4);

				while (rs.next()) {
					count = rs.getInt(1);
				}
				System.out.println("size " + size + " count " + count);
				if (size == count) {

					System.out.println("size==count");

					// for online_payment_detail

					/*
					 * String quryforSelect10=
					 * "update online_payment_detail set PAYMENT_STATUS ='C'  where pay_id=(select pay_id from dan_cgpan_info_temp where  dan_id ='"
					 * +danIDPaymentID[0]+"') ";
					 * System.out.println("quryforSelect10"+quryforSelect10); delpaystatus1[4] =
					 * str1.executeUpdate((quryforSelect10));
					 * 
					 * String quryforSelect6=
					 * "insert into payment_detail_temp_canc  select * from payment_detail_temp where pay_id=(select pay_id from dan_cgpan_info_temp where  dan_id ='"
					 * +danIDPaymentID[0]+"') ";
					 * System.out.println("quryforSelect6"+quryforSelect6); delpaystatus1[0] =
					 * str1.executeUpdate((quryforSelect6));
					 * 
					 * 
					 * String quryforSelect8=
					 * "delete from payment_detail_temp  where pay_id=(select pay_id from dan_cgpan_info_temp where  dan_id ='"
					 * +danIDPaymentID[0]+"') ";
					 * System.out.println("quryforSelect8"+quryforSelect8); delpaystatus1[1] =
					 * str1.executeUpdate((quryforSelect8));
					 * 
					 * String quryforSelect7=
					 * "insert into dan_cgpan_info_temp_canc  select * from dan_cgpan_info_temp where dan_id ='"
					 * +danIDPaymentID[0]+"'"; System.out.println("quryforSelect7"+quryforSelect7);
					 * delpaystatus1[2] = str1.executeUpdate((quryforSelect7));
					 * 
					 * String quryforSelect9= "delete from dan_cgpan_info_temp where dan_id ='"
					 * +danIDPaymentID[0]+"'"; System.out.println("quryforSelect9"+quryforSelect9);
					 * delpaystatus1[3] = str1.executeUpdate((quryforSelect9));
					 */

					String quryforSelect10 = "update online_payment_detail set PAYMENT_STATUS ='C'  where pay_id='"
							+ danIDPaymentID[1] + "'";
					System.out.println("quryforSelect10" + quryforSelect10);
					delpaystatus1[4] = str1.executeUpdate((quryforSelect10));

					String quryforSelect6 = "insert into payment_detail_temp_canc  select * from payment_detail_temp where pay_id='"
							+ danIDPaymentID[1] + "'";
					System.out.println("quryforSelect6" + quryforSelect6);
					delpaystatus1[0] = str1.executeUpdate((quryforSelect6));

					String quryforSelect8 = "delete from payment_detail_temp  where pay_id='" + danIDPaymentID[1] + "'";
					System.out.println("quryforSelect8" + quryforSelect8);
					delpaystatus1[1] = str1.executeUpdate((quryforSelect8));

					String quryforSelect7 = "insert into dan_cgpan_info_temp_canc  select * from dan_cgpan_info_temp where pay_id='"
							+ danIDPaymentID[1] + "'";
					System.out.println("quryforSelect7" + quryforSelect7);
					delpaystatus1[2] = str1.executeUpdate((quryforSelect7));

					String quryforSelect9 = "delete from dan_cgpan_info_temp where pay_id='" + danIDPaymentID[1] + "'";
					System.out.println("quryforSelect9" + quryforSelect9);
					delpaystatus1[3] = str1.executeUpdate((quryforSelect9));

					System.out.println("delpaystatus1[0] " + delpaystatus1[0]);
					System.out.println("delpaystatus1[1] " + delpaystatus1[1]);
					System.out.println("delpaystatus1[2] " + delpaystatus1[2]);
					System.out.println("delpaystatus1[3] " + delpaystatus1[3]);
					System.out.println("delpaystatus1[4] " + delpaystatus1[4]);
					// connection.commit();

					if ((delpaystatus1[0] != 0) && (delpaystatus1[1] != 0) && (delpaystatus1[2] != 0)
							&& (delpaystatus1[3] != 0) && (delpaystatus1[4] != 0)) {

						connection.commit();

						break;

					} else {

						connection.rollback();
						throw new MissingDANDetailsException(
								"Not able to deallocate dan id. problem in  '" + danIDPaymentID[0] + "'");
					}

				}

				else {

					// String quryforSelect3=
					// "update payment_detail_temp set PAY_AMOUNT = (select sum(DCI_AMOUNT_RAISED)
					// from dan_cgpan_info_temp where PAY_ID= "+danids+"') where PAY_ID=
					// "+danids+"'";

					String quryforSelect3 = " update payment_detail_temp  set PAY_AMOUNT=PAY_AMOUNT-(select dci_amount_raised from "
							+ "dan_cgpan_info_temp  where  dan_id='" + danIDPaymentID[0]
							+ "') where pay_id=(select a.pay_id from dan_cgpan_info_temp a where   dan_id='"
							+ danIDPaymentID[0] + "') ";
					System.out.println("quryforSelect3 " + quryforSelect3);
					inspaystatus1[2] = str1.executeUpdate((quryforSelect3));

					/*
					 * long var_dci_amount_raised=0;
					 * 
					 * String quryforSelect3=
					 * " update payment_detail_temp  set PAY_AMOUNT=PAY_AMOUNT- '"
					 * +'"+var_dci_amount_raised +"'" where dan_id='"+danIDPaymentID[0]+"') where
					 * pay_id=(select a.pay_id from dan_cgpan_info_temp a where
					 * dan_id='"+danIDPaymentID[0]+"') ";
					 * System.out.println("testing quryforSelect3 " +quryforSelect3);
					 * inspaystatus1[2] = str1.executeUpdate((quryforSelect3));
					 */

					String quryforSelect6 = " update online_payment_detail  set AMOUNT=AMOUNT-(select dci_amount_raised from "
							+ "dan_cgpan_info_temp  where  dan_id='" + danIDPaymentID[0]
							+ "') where pay_id=(select a.pay_id from dan_cgpan_info_temp a where   dan_id='"
							+ danIDPaymentID[0] + "') ";
					System.out.println(" quryforSelect6" + quryforSelect6);
					inspaystatus1[3] = str1.executeUpdate((quryforSelect6));

					// before data deletion inserting data in canc table
					String quryforSelect = "insert into dan_cgpan_info_temp_canc  select * from  dan_cgpan_info_temp where  dan_id = '"
							+ danIDPaymentID[0] + "' ";
					inspaystatus1[0] = str1.executeUpdate(quryforSelect);
					System.out.println("quryforSelect" + quryforSelect);

					// data deletion from dan_cgpan_info_temp
					String quryforSelect2 = "delete from dan_cgpan_info_temp where dan_id ='" + danIDPaymentID[0] + "'";
					System.out.println("quryforSelect" + quryforSelect2);
					inspaystatus1[1] = str1.executeUpdate((quryforSelect2));

					System.out.println("inspaystatus1[0] " + inspaystatus1[0]);
					System.out.println("inspaystatus1[1] " + inspaystatus1[1]);
					System.out.println("inspaystatus1[2] " + inspaystatus1[2]);
					System.out.println("inspaystatus1[3] " + inspaystatus1[3]);

					if ((inspaystatus1[0] != 0) && (inspaystatus1[1] != 0) && (inspaystatus1[2] != 0)
							&& (inspaystatus1[3] != 0)) {

						connection.commit();
					}

					else {
						connection.rollback();
						throw new MissingDANDetailsException(
								" 22 Not able to deallocate dan id. problem in  '" + danIDPaymentID[0] + "'");
					}

				}

				// if((insdanstatus1==0) && (deldanstatus1==0) &&
				// (inspaystatus1==0) && (delpaystatus1==0)){

				// connection.commit();
				// }
				// else
				// {
				// throw new
				// MissingDANDetailsException("not able to deallocate dan id. problem in
				// '"+danids+"'");
				// }

				// connection.rollback();
			}

		}

		catch (SQLException sqlexception)

		{
			connection.rollback();
			throw new DatabaseException(sqlexception.getMessage());

		} finally {

			DBConnection.freeConnection(connection);
		}

		request.setAttribute("message", "Selected RPs Modified/ Cancelled Successfully!!!");

		return mapping.findForward("success");

	}

	public ActionForward searchHistory(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Log.log(Log.INFO, "ReportsAction", "applicationReport", "Entered");
		ReportActionForm dynaForm = (ReportActionForm) form;

		Log.log(Log.INFO, "ReportsAction", "applicationReport", "Exited");
		return mapping.findForward("success");
	}
	// vivek

	public ActionForward cumulativeReport(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)

			throws Exception

	{

		ReportActionForm dynaForm = (ReportActionForm) form;

		Log.log(Log.INFO, "ReportsAction", "cumulativeReport", "Entered");

		HttpSession session = request.getSession();

		User user = getUserInformation(request);

		String userid = user.getUserId();

		// System.out.println("userid===="+userid);

		String bankid = (user.getBankId()).trim();

		String zoneid = (user.getZoneId()).trim();

		String branchid = (user.getBranchId()).trim();

		String memberId = bankid + zoneid + branchid;

		System.out.println("memberId====" + memberId);

		Connection connection = null;

		// String itpan=dynaForm.getItpan();

		// System.out.println("itpan===="+itpan);

		List list = new ArrayList();

		if (memberId.equals("000000000000"))

		{

			System.out.println("memberId====" + memberId);

			list = cumulativeReportAllData(connection, memberId, userid);

		}

		else

		{

			list = cumulativeReportData(connection, memberId, userid);

		}

		dynaForm.setColletralCoulmnName((ArrayList) list.get(0));

		dynaForm.setColletralCoulmnValue((ArrayList) list.get(1));

		return mapping.findForward("colletralReport");

	}

	private List cumulativeReportAllData(Connection conn, String memberId, String userid)

			throws DatabaseException {

		Log.log(Log.INFO, "reportaction", "cumulativeReportData()", "Entered!");

		CallableStatement callableStmt = null;

		// Connection conn = null;

		ResultSet resultset = null;

		ResultSetMetaData resultSetMetaData = null;

		ArrayList coulmName = new ArrayList();

		ArrayList nestData = new ArrayList();

		ArrayList colletralData = new ArrayList();

		int status = -1;

		String errorCode = null;

		try {

			conn = DBConnection.getConnection();

			callableStmt = conn

					.prepareCall("{call FUNCCGTSICUMULATIVE_REPORT_ALL(?,?,?,?)}");

			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);

			callableStmt.setString(2, memberId);

			callableStmt.setString(3, userid);

            callableStmt.registerOutParameter(4, java.sql.Types.VARCHAR);

			callableStmt.execute();

			status = callableStmt.getInt(1);

			errorCode = callableStmt.getString(4);

			if (status == Constants.FUNCTION_FAILURE) {

				Log.log(Log.ERROR, "CPDAO", "colletralHybridRetailReport()",

						"SP returns a 1. Error code is :" + errorCode);

				callableStmt.close();

				throw new DatabaseException(errorCode);

			} else if (status == Constants.FUNCTION_SUCCESS) {

				resultset = callableStmt.executeQuery();

				resultSetMetaData = resultset.getMetaData();

				int coulmnCount = resultSetMetaData.getColumnCount();

				for (int i = 1; i <= coulmnCount; i++) {

					coulmName.add(resultSetMetaData.getColumnName(i));

				}

				while (resultset.next()) {

					ArrayList columnValue = new ArrayList();

					for (int i = 1; i <= coulmnCount; i++) {

						columnValue.add(resultset.getString(i));

					}

					nestData.add(columnValue);

				}

				// System.out.println("list data " + nestData);

				colletralData.add(0, coulmName);

				colletralData.add(1, nestData);

				resultset.close();

				resultset = null;

				callableStmt.close();

				callableStmt = null;

				resultSetMetaData = null;

			}

		} catch (SQLException sqlexception) {

			Log.log(Log.ERROR, "CPDAO", "colletralHybridRetailReport()",

					"Error retrieving all colletral data!");

			throw new DatabaseException(sqlexception.getMessage());

		} finally {

			DBConnection.freeConnection(conn);

		}

		return colletralData;

	}

	private List cumulativeReportData(Connection conn, String memberId, String userid)

			throws DatabaseException {

		Log.log(Log.INFO, "reportaction", "cumulativeReportData()", "Entered!");

		CallableStatement callableStmt = null;

		// Connection conn = null;

		ResultSet resultset = null;

		ResultSetMetaData resultSetMetaData = null;

		ArrayList coulmName = new ArrayList();

		ArrayList nestData = new ArrayList();

		ArrayList colletralData = new ArrayList();

		int status = -1;

		String errorCode = null;

		try {

			conn = DBConnection.getConnection();

			callableStmt = conn

					.prepareCall("{call FUNCCGTSICUMULATIVE_REPORT(?,?,?,?)}");

			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);

			callableStmt.setString(2, memberId);

			callableStmt.setString(3, userid);

           callableStmt.registerOutParameter(4, java.sql.Types.VARCHAR);

			callableStmt.execute();

			status = callableStmt.getInt(1);

			errorCode = callableStmt.getString(4);

			if (status == Constants.FUNCTION_FAILURE) {

				Log.log(Log.ERROR, "CPDAO", "colletralHybridRetailReport()",

						"SP returns a 1. Error code is :" + errorCode);

				callableStmt.close();

				throw new DatabaseException(errorCode);

			} else if (status == Constants.FUNCTION_SUCCESS) {

				resultset = callableStmt.executeQuery();

				resultSetMetaData = resultset.getMetaData();

				int coulmnCount = resultSetMetaData.getColumnCount();

				for (int i = 1; i <= coulmnCount; i++) {

					coulmName.add(resultSetMetaData.getColumnName(i));

				}

				while (resultset.next()) {

					ArrayList columnValue = new ArrayList();

					for (int i = 1; i <= coulmnCount; i++) {

						columnValue.add(resultset.getString(i));

					}

					nestData.add(columnValue);

				}

				// System.out.println("list data " + nestData);

				colletralData.add(0, coulmName);

				colletralData.add(1, nestData);

				resultset.close();

				resultset = null;

				callableStmt.close();

				callableStmt = null;

				resultSetMetaData = null;

			}

		} catch (SQLException sqlexception) {

			Log.log(Log.ERROR, "CPDAO", "colletralHybridRetailReport()",

					"Error retrieving all colletral data!");

			throw new DatabaseException(sqlexception.getMessage());

		} finally {

			DBConnection.freeConnection(conn);

		}

		return colletralData;

	}

	public ActionForward showSearchHistory(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ReportActionForm dynaForm = (ReportActionForm) form;
		Log.log(Log.INFO, "ReportsAction", "showSearchHistory", "Entered");
		HttpSession session = request.getSession();
		User user = getUserInformation(request);
		String userid = user.getUserId();
		// System.out.println("userid===="+userid);
		String bankid = (user.getBankId()).trim();
		String zoneid = (user.getZoneId()).trim();
		String branchid = (user.getBranchId()).trim();
		String memberId = bankid + zoneid + branchid;
		// System.out.println("memberId===="+memberId);
		Connection connection = null;

		String itpan = dynaForm.getItpan();
		// System.out.println("itpan===="+itpan);

		List list = new ArrayList();

		if (itpan == null && itpan == null) {
			throw new MessageException("Please Enter ITPAN.");
		} else {
			list = searchITPANHistoryDetails(connection, memberId, itpan, userid);

		}

		dynaForm.setColletralCoulmnName((ArrayList) list.get(0));
		dynaForm.setColletralCoulmnValue((ArrayList) list.get(1));

		return mapping.findForward("colletralReport");

	}

	private List searchITPANHistoryDetails(Connection conn, String memberId, String itpan, String userid)
			throws DatabaseException {
		Log.log(Log.INFO, "reportaction", "actionHistoryDetailReport()", "Entered!");
		CallableStatement callableStmt = null;
		// Connection conn = null;
		ResultSet resultset = null;
		ResultSetMetaData resultSetMetaData = null;
		ArrayList coulmName = new ArrayList();
		ArrayList nestData = new ArrayList();
		ArrayList colletralData = new ArrayList();
		int status = -1;
		String errorCode = null;
		try {
			conn = DBConnection.getConnection();
			callableStmt = conn.prepareCall("{call FUN_ITPAN_HISTORY_REPORT(?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);

			callableStmt.setString(2, memberId);
			callableStmt.setString(3, itpan);
			callableStmt.setString(4, userid);
			callableStmt.registerOutParameter(5, java.sql.Types.VARCHAR);

			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(5);

			if (status == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "CPDAO", "colletralHybridRetailReport()",
						"SP returns a 1. Error code is :" + errorCode);
				callableStmt.close();
				throw new DatabaseException(errorCode);
			} else if (status == Constants.FUNCTION_SUCCESS) {
				resultset = (ResultSet) callableStmt.executeQuery();

				resultSetMetaData = resultset.getMetaData();
				int coulmnCount = resultSetMetaData.getColumnCount();
				for (int i = 1; i <= coulmnCount; i++) {
					coulmName.add(resultSetMetaData.getColumnName(i));
				}

				while (resultset.next()) {

					ArrayList columnValue = new ArrayList();
					for (int i = 1; i <= coulmnCount; i++) {
						columnValue.add(resultset.getString(i));
					}

					nestData.add(columnValue);

				}
				// System.out.println("list data " + nestData);
				colletralData.add(0, coulmName);
				colletralData.add(1, nestData);
			}
			resultset.close();
			resultset = null;
			callableStmt.close();
			callableStmt = null;
			resultSetMetaData = null;
		} catch (SQLException sqlexception) {
			Log.log(Log.ERROR, "CPDAO", "colletralHybridRetailReport()", "Error retrieving all colletral data!");
			throw new DatabaseException(sqlexception.getMessage());
		} finally {
			DBConnection.freeConnection(conn);
		}
		return colletralData;
	}
	
	public ActionForward displayCgpanDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		RPActionForm actionFormobj = (RPActionForm) form;
		PreparedStatement allocatePaymentfinalStmt;
		ResultSet allocatePaymentFinalResult;
		Connection connection = DBConnection.getConnection();
		ArrayList rpArray = new ArrayList();
		
		
		try {
			String rpID = request.getParameter("val");
			System.out.println("value:" +rpID);
			String query = "select a.CGPAN,DAN_ID,DCI_GFEE_AMT,SSI_UNIT_NAME from subdebt_application_detail a inner join dan_cgpan_info b on trim(a.CGPAN)=trim(b.CGPAN) where Payment_ID='"
					+ rpID + "'";

			allocatePaymentfinalStmt = connection.prepareStatement(query);
			allocatePaymentFinalResult = allocatePaymentfinalStmt.executeQuery();
		
			while (allocatePaymentFinalResult.next()) {

				RPActionForm actionForm = new RPActionForm();

				actionForm.setCgpan(allocatePaymentFinalResult.getString(1));

				actionForm.setDanid1(allocatePaymentFinalResult.getString(2));
				actionForm.setGuaranteeFee(allocatePaymentFinalResult.getDouble(3));
				actionForm.setUnitNameRP(allocatePaymentFinalResult.getString(4));
				rpArray.add(actionForm);

			}
			actionFormobj.setAllocatepaymentFinal(rpArray);
	}
		catch (Exception exception) {
			Log.logException(exception);
			throw new DatabaseException(exception.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}
		return mapping.findForward("popup");

}
}
