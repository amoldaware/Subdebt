package com.cgtsi.action;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cgtsi.actionform.NPAMarkingPopulateData;
import com.cgtsi.actionform.NpaMarkingActionForm;
import com.cgtsi.admin.User;
import com.cgtsi.common.Constants;
import com.cgtsi.common.Log;
import com.cgtsi.util.DBConnection;
import com.mysql.cj.protocol.Resultset;

public class NpaMarkingAction extends BaseAction{

	public ActionForward getNpaMarking(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		Resultset rs = null;
		String message = null;
		NpaMarkingActionForm form1 = (NpaMarkingActionForm) form;
		String cgpan = form1.getCgpan1();
		ArrayList promoter = new ArrayList();
		ArrayList reason = new ArrayList();
		ArrayList creditRiskvalue = new ArrayList();
		ArrayList reproductionValue = new ArrayList();
		User user = getUserInformation(request);
		String bankId="";
		try {
			if(conn==null){
				conn = DBConnection.getConnection(false);
			}
		if(cgpan!=null && !cgpan.isEmpty())
		{
			try{
			    bankId = user.getBankId();
			    promoter = getPromoterDetails(cgpan, conn,bankId,user.getBankId()+user.getZoneId()+user.getBranchId());
				//System.out.println("Promoter Size :::" + promoter.size());
				if(promoter.size()==0){
					message="Either CGPAN not pertaining to logged in User,Bank,MLI or No Promoter Details Found!!";
				}
				//System.out.println("HashMap Contains Values ::" + promoter + "JSON Data ::::" + promoter);
				form1.setPromoterValues(promoter);
				form1.setCgpan1(cgpan);
				form1.setIsSearchClicked("");
			}
			catch(Exception err){
				
				Log.log(2, "NpaMarkingAction", "WriteToFile", err.getMessage());
			}
		}
		else{
			Map option = new HashMap();
			option.put("label", "");
			option.put("value", "Select");
			promoter.add(option);
			form1.setPromoterValues(promoter);
			form1.setIsSearchClicked("");
			
		}
		reason = getReason(conn);
		 if(reason!=null)
		 {
			 	form1.setReasonvalue(reason);
		 }else
		 {
			Map option = new HashMap();
			option.put("label", "");
			option.put("value", "Select");
			reason.add(option);
			form1.setReasonvalue(reason);
		 }
		 
		 creditRiskvalue = getCreditRisk(conn);
		 if(creditRiskvalue!=null)
		 {
			 	form1.setCreditRiskvalue(creditRiskvalue);
		 }else
		 {
			Map option = new HashMap();
			option.put("label", "");
			option.put("value", "Select");
			creditRiskvalue.add(option);
			form1.setReasonvalue(creditRiskvalue);
		 }
		 reproductionValue = getReduction(conn);
		 if(reproductionValue!=null)
		 {
			 	form1.setReproductionValue(reproductionValue);
		 }else
		 {
			Map option = new HashMap();
			option.put("label", "");
			option.put("value", "Select");
			reproductionValue.add(option);
			form1.setReasonvalue(reproductionValue);
		 }
		 String checkBoxVal="N";
		 if((checkBoxVal.equals("Y"))){
				
				request.setAttribute("check", checkBoxVal);
			}
			else{
				request.setAttribute("check",checkBoxVal);
			}
		 
		} catch (Exception err) {
			try {
				conn.rollback();
			} catch (SQLException ignore) {}
			Log.log(2, "NpaMarkingAction", "WriteToFile", err.getMessage());
			form1.setMessage(err.getMessage());
			err.printStackTrace();
		}
		finally
		{

			try {
				if (conn != null) {
					DBConnection.freeConnection(conn);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		}
		if(message!=null){
			form1.setMessage(message);
		}
		 
		return mapping.findForward("npaMarkingPage");
	} 
	
	public ActionForward getNpaMarkingSearchDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{

		NpaMarkingActionForm form1 = (NpaMarkingActionForm) form;
		ArrayList promoter = new ArrayList();
		ArrayList reason = new ArrayList();
		Connection conn=null;
		CallableStatement callableStmt = null;
		ResultSet rs = null;
		User user = getUserInformation(request);
		ArrayList creditRiskvalue = new ArrayList();
		ArrayList reproductionValue = new ArrayList();
		String errorCode = "", message = "" , bankId = "";
		int status = -1;
		try
		{
			String cgpan = form1.getCgpan1();
			String promoterName = form1.getPromoterName1();
			String isSearchClicked = form1.getIsSearchClicked();
			
		
			if((cgpan!=null && !cgpan.isEmpty()) && (promoterName!=null && !promoterName.isEmpty()))
			{
				if(conn==null){
					conn = DBConnection.getConnection(false);
				}
				
				
				
				bankId = user.getBankId();
				String zoneId = user.getZoneId();
				String branchId = user.getBranchId();
				String memberId=bankId+zoneId+branchId;
				System.out.println("Member id===66==="+memberId);
				
				promoter = getPromoterDetails(cgpan, conn,bankId,user.getBankId()+user.getZoneId()+user.getBranchId());
				reason = getReason(conn);
				reproductionValue = getReduction(conn); 
				form1.setPromoterValues(promoter);
				
				callableStmt = conn.prepareCall("{call FUNC_GET_NPA_DETAILS(?,?,?,?,?)}");
				//callableStmt = conn.prepareCall("{call FUNC_GET_PROMOTER_DETAILS(?,?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, Types.VARCHAR);
				callableStmt.setString(2,cgpan );
				callableStmt.setString(3, promoterName);
				callableStmt.setString(4, memberId);
				//callableStmt.setInt(5, 1);
				callableStmt.registerOutParameter(5, Types.VARCHAR);
				callableStmt.execute();
				
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(5);
				creditRiskvalue = getCreditRisk(conn);
				//System.out.println("status::" + status + "\t errorCode" + errorCode);

					if (status == Constants.FUNCTION_FAILURE) {
					
					Log.log(Log.ERROR, "CPDAO", "getNpaMarkingSearchDetail()",
							"getNpaMarkingSearchDetail returns a 1. Error code is :" + errorCode);
					message =  errorCode;
					if (callableStmt != null) {
						callableStmt.close();
					}
					form1.setMessage(message);
				} else if (status == Constants.FUNCTION_SUCCESS) {

					rs = callableStmt.executeQuery();
				}
				if(rs.next())
				{	
					
					String roleName = rs.getString("ROLENAME");
					request.setAttribute("role", roleName);
					
					String referanceNo = rs.getString("APP_REF_NO");
					String cgpansearch = rs.getString("CGPAN");
					String pmarCgpan = rs.getString("PMR_CGPAN");
					String unitName = rs.getString("SSI_UNIT_NAME");
					String typeofEntity = rs.getString("UNIT_TYPE");
					String contribution = rs.getString("CONSTITUTION");
					double guaranteeAmount = rs.getDouble("GUARANTEE_AMT");
					String guaratneeStartDate = rs.getString("GUARATNEE_START_DATE");
					String lastDisbursementDate = rs.getString("LAST_DISBURSEMENT_DATE");
					String sanctionAmount = rs.getString("SANCTION_AMOUNT");
					String sanctionDate = rs.getString("PMR_CGPAN");
					String interestRate = rs.getString("INTEREST_RATE");
					
				
					
					
					String promoterNamee = rs.getString("PROMOTER_NAME");
					String itPAN = rs.getString("ITPAN");
					String adharNumber = rs.getString("ADHAR_NUMBER");
					String investedEquity = rs.getString("INVESTED_EQUITY");
					String investedasdebtunsecuredloan = rs.getString("INVESTED_DEBT_LOAN");
					Float total = rs.getFloat("TOTAL");
					Long totaldata = total.longValue();
					//convert(decimal(total), cast( '3.1641E7' as float) )
					//BigDecimal dateOfSectionTotal=rs.getBigDecimal("DATEOFSANCTIONTOTAL");
					double guaranteeAmount1 = rs.getDouble("PMR_GUARANTEE_AMT");
					String guaratneeStartDate1 = rs.getString("PMR_GUARATNEE_START_DATE");
					
					
					String npaDate = rs.getString("NPA_DATE");
					request.setAttribute("date", npaDate);
					
					String reasons = rs.getString("NPA_REASON");
					String creditRisk = rs.getString("ENUMERATE_EFFORTS");//understand from krishana
					
					double prinicipleRepayment = rs.getDouble("PRINICIPLE_REPAYMENT_BEFORE_NPA");
					String interestRepayment = rs.getString("INTEREST_REPAYMENT_BEFORE_NPA");
					String principleOutstanding =  rs.getString("PRINCIPLE_OUTSTANDING_ASONNPADATE");
					String disbursementAmount =  rs.getString("DISBURSEMENTAMOUNT");
					String interestOutstanding =  rs.getString("INTEREST_OUTSTANDING_ASONNPADATE");
					String checkBoxVal =  rs.getString("CHECKBOXVAL");
					if((checkBoxVal.equals("Y"))){
						
						request.setAttribute("check", checkBoxVal);
					}
					else{
						request.setAttribute("check","N");
					}
					
					//=====================land=================
					
					 BigDecimal landSection=rs.getBigDecimal("LANDDATEOFSANCTION");
					 String landDataOfNPA=rs.getString("LANDDATEOFNPA");
					 String landNetWorth=rs.getString("LANDNETWORTH");
					 String landReason=rs.getString("LANDREASONS"); 
					
					 //reason = getReason(conn);
					 if(reason!=null)
					 {
					 form1.setReasonvalue(reason);
					 }else{
						Map option = new HashMap();
						option.put("label", "");
						option.put("value", "Select");
						reason.add(option);
						form1.setReasonvalue(reason);
					 }
					// creditRiskvalue = getCreditRisk(conn);
					 if(creditRiskvalue!=null)
					 {
						 	form1.setCreditRiskvalue(creditRiskvalue);
					 }else
					 {
						Map option = new HashMap();
						option.put("label", "");
						option.put("value", "Select");
						creditRiskvalue.add(option);
						form1.setReasonvalue(creditRiskvalue);
					 }
					 
					 
					// reproductionValue = getReduction(conn);
					 if(reproductionValue!=null)
					 {
						 	form1.setReproductionValue(reproductionValue);
					 }else
					 {
						Map option = new HashMap();
						option.put("label", "");
						option.put("value", "Select");
						reproductionValue.add(option);
						form1.setReasonvalue(reproductionValue);
					 }
					//=====bulding============================== 
					 
					 BigDecimal buildingSection=rs.getBigDecimal("BUILDINGDATEOFSANCTION");
					 String buildingDateOfNpa=rs.getString("BUILDINGDATEOFNPA");
					 String buildingNetWorth=rs.getString("BUILDINGNETWORTH");
					 String buildingReason=rs.getString("BUILDINGREASONS");// BUILDINGREASONS=left
					 
					 
					
					 
					 
					 //=======Plant and Machinery==========================
					 
					 String plantMachinerySection=rs.getString("PLANTMACHINERYDATEOFSANCTION");
					 String plantMachineryDateNpa=rs.getString("PLANTMACHINERYDATEOFNPA");
					 String plantMachineryNetWorth=rs.getString("PLANTMACHINERYNETWORTH");
					 String plantMachineryReason=rs.getString("PLANTMACHINERYREASONS");// PLANTMACHINERYREASONS left
				
					 
					 //========other fixed
					 
					 String otherFixedMovableSection=rs.getString("OTHERFIXEDMOVABLEASSETSDATEOFSANCTION");
					 String otherFixedMovableDateOfNpa=rs.getString("OTHERFIXEDMOVABLEASSETSDATEOFNPA");
					 String otherFixedMovableNetWorth=rs.getString("OTHERFIXEDMOVABLEASSETSNETWORTH");
					 String otherFixedReason=rs.getString("OTHERFIXEDMOVABLEASSETSREASONS"); //OTHERFIXEDMOVABLEASSETSREASONS left
						
					  
					 //================current Asset
					
					  String currentAssetSection=rs.getString("CURRENTASSETSDATEOFSANCTION");
					  String currentAssetDateOfNpa=rs.getString("CURRENTASSETSDATEOFNPA");
					  String currentAssetNetWorth=rs.getString("CURRENTASSETSNETWORTH");
					  String currentAssetReson=rs.getString("CURRENTASSETSREASONS");// CURRENTASSETSREASONS left
					
					  //=================other section
						
					  String otherSection=rs.getString("OTHERSDATEOFSANCTION");
					  String otherNpa=rs.getString("OTHERSDATEOFNPA");
					  String otherNetWorth=rs.getString("OTHERSNETWORTH");
					  String otherReason=rs.getString("OTHERSREASONS");// left
					 
					 /* DATEOFSANCTIONTOTAL
					  DATEOFNPATOTAL
					  NETWORTHTOTAL*/
					  
				     BigDecimal dateOfSectionTotal=rs.getBigDecimal("DATEOFSANCTIONTOTAL");
				     BigDecimal dateOfNpaTotal=rs.getBigDecimal("DATEOFNPATOTAL");
				     BigDecimal netWorthTotal=rs.getBigDecimal("NETWORTHTOTAL");
					  
					  
					//promoter = getPromoterDetails(cgpan, conn);
					 if(promoter!=null)
					 {
						 form1.setPromoterValues(promoter);
					 }else
					 {
						Map option = new HashMap();
						option.put("label", "");
						option.put("value", "Select");
						promoter.add(option);
						form1.setPromoterValues(promoter);
					 }
					
					/*ArrayList npaMarkingData= getNpaMarkingData(mapping,form,  request,response);
					request.setAttribute("npaMarkingData", npaMarkingData);
*/					form1.setIsSearchClicked(isSearchClicked);
					form1.setRole(roleName);
					form1.setReferanceNo(referanceNo);
					form1.setCgpansearch(cgpansearch);
					form1.setPmarCgpan(pmarCgpan);
					form1.setUnitName(unitName);
					form1.setTypeofEntity(typeofEntity);
					form1.setContribution(contribution);
					form1.setGuaranteeAmount(guaranteeAmount);
					form1.setGuaratneeStartDate(guaratneeStartDate);
					form1.setLastDisbursementDate(lastDisbursementDate);
					form1.setSanctionAmount(sanctionAmount);
					form1.setSanctionDate(sanctionDate);
					form1.setInterestRate(interestRate);
					
					
					form1.setPromoterNamee(promoterNamee);
					form1.setItPAN(itPAN);
					form1.setAdharNumber(adharNumber);
					form1.setInvestedEquity(investedEquity);
					form1.setInvestedasdebtunsecuredloan(investedasdebtunsecuredloan);
					form1.setTotal(totaldata);
					form1.setGuaranteeAmount1(guaranteeAmount1);
					form1.setGuaratneeStartDate1(guaratneeStartDate1);
					form1.setCheckBoxVal(checkBoxVal);
					
					 
					 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
					//form1.setNpaDate(npaDate);
					/* String npaDatee=simpleDateFormat.format(npaDate);
					 
					 if(npaDatee!=null){*/
						//request.setAttribute("date", npaDate);
					/* }
					 else{
						 request.setAttribute("date", "selectDate");
					 }*/
					 
					 
					 
					//left date and reason
					form1.setPrinicipleRepayment(prinicipleRepayment);
					form1.setInterestRepayment(interestRepayment);
					form1.setPrincipleOutstanding(principleOutstanding);
					form1.setDisbursementAmount(Double.valueOf(disbursementAmount));
					form1.setInterestOutstanding(interestOutstanding);
					form1.setReason(reasons);
					form1.setCreditRisk(creditRisk);
					
					//land section 
					form1.setLandSection(landSection);
					form1.setLandNetWorth(landNetWorth);
					form1.setLandDataOfNPA(landDataOfNPA);
					form1.setLandReason(landReason);
					
					//building section
					form1.setBuildingSection(buildingSection);
					form1.setBuildingDateOfNpa(buildingDateOfNpa);
					form1.setBuildingNetWorth(buildingNetWorth);
					form1.setBuildingReason(buildingReason);
					
					//plant and machinery
					
					form1.setPlantMachinerySection(plantMachinerySection);
					form1.setPlantMachineryDateNpa(plantMachineryDateNpa);
					form1.setPlantMachineryNetWorth(plantMachineryNetWorth);
					form1.setPlantMachineryReason(plantMachineryReason);
					
					//other fixed movable
					
					form1.setOtherFixedMovableSection(otherFixedMovableSection);
					form1.setOtherFixedMovableDateOfNpa(otherFixedMovableDateOfNpa);
					form1.setOtherFixedMovableNetWorth(otherFixedMovableNetWorth);
					form1.setOtherFixedReason(otherFixedReason);
					
					//current asset
					
					form1.setCurrentAssetSection(currentAssetSection);
					form1.setCurrentAssetDateOfNpa(currentAssetDateOfNpa);
					form1.setCurrentAssetNetWorth(currentAssetNetWorth);
					form1.setCurrentAssetReson(currentAssetReson);
					
					//other
					
					form1.setOtherSection(otherSection);
					form1.setOtherNpa(otherNpa);
					form1.setOtherNetWorth(otherNetWorth);
					form1.setOtherReason(otherReason);
					
					//total
					form1.setDateOfSectionTotal(dateOfSectionTotal);
					form1.setDateOfNpaTotal(dateOfNpaTotal);
					form1.setNetWorthTotal(netWorthTotal);
					
					 form1.setPromoterValues(promoter);
					 form1.setReasonvalue(reason);
					 form1.setCreditRiskvalue(creditRiskvalue);
					 form1.setReproductionValue(reproductionValue);
					 request.setAttribute("check", "N");
					 
					 
					 ArrayList<NPAMarkingPopulateData> npaPopulateData = getNPAPopulatedData(mapping, form, request,
								response);
					request.setAttribute("npaPopulateData", npaPopulateData);
					
					System.out.println("NPA Date IS :::" + form1.getNpaDate());
					if((form1.getNpaDate() != "") || (form1.getNpaDate()!=null && !form1.getNpaDate().isEmpty()))
					{
						form1.setPrinicipleRepayment(Double.valueOf(request.getParameter("PrinicipleRepayment")));
						form1.setPrincipleOutstanding(request.getParameter("PrincipleOutstanding"));
						form1.setInterestRepayment(request.getParameter("interestRepayment"));
						form1.setInterestOutstanding(request.getParameter("InterestOutstanding"));
						form1.setReason(request.getParameter("NPAReason"));
						form1.setCreditRisk(request.getParameter("creditrisk"));
						//message = doDateValidation(memberId , form1.getNpaDate(),conn);
					}
					request.setAttribute("npadate", form1.getNpaDate());
					 
					/*form1.setPromoterValues(promoter);
					form1.setPromoterName(promoterName);
					form1.setIsSearchClicked(isSearchClicked);*/
					
					
					//request.setAttribute("disurseData", disurseData);
					 
					 
					/*
					 if((checkBoxVal.equals("Y"))){
							
							request.setAttribute("check", checkBoxVal);
						}
						else{
							request.setAttribute("check",checkBoxVal);
						}*/
					
				}
			}
			else
			{
				form1.setReferanceNo("1234567");
				form1.setPromoterValues(promoter);
				form1.setReasonvalue(reason);
				form1.setReproductionValue(reproductionValue);
				form1.setCreditRiskvalue(creditRiskvalue);
				 request.setAttribute("check", "N");
				//promoter = getPromoterDetails(cgpan, conn);
				/*form1.setPromoterValues(promoter);
				form1.setPromoterName(promoterName);
				form1.setIsSearchClicked(isSearchClicked);*/
			}
			
		}
		catch(Exception err)
		{
			try {
				conn.rollback();
			} catch (SQLException ignore) {}
			Log.log(2, "NpaMarkingAction", "WriteToFile", err.getMessage());
			Map option = new HashMap();
			option.put("label", "");
			option.put("value", "Select");
			reason.add(option);
			form1.setReasonvalue(reason);
			form1.setPromoterValues(promoter);
			form1.setCreditRiskvalue(creditRiskvalue);
			form1.setReproductionValue(reproductionValue);
			 request.setAttribute("check", "N");
			request.setAttribute("dMessage", err.getMessage());
		}
		finally {
			try {
				if (conn != null) {
					DBConnection.freeConnection(conn);
				}
				if (rs != null) {
					((ResultSet) rs).close();
					rs = null;
				}
				if (callableStmt != null) {
					callableStmt.close();
					callableStmt = null;
				}
			} catch (Exception err) {
				System.out.println(err.getMessage());
				err.printStackTrace();
			}
		}
		if(message!=null){
			form1.setMessage(message);
		}
		return mapping.findForward("npaMarkingPage");
	}
	
/*	public ArrayList getNpaMarkingData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	  {

		NpaMarkingActionForm form1 = (NpaMarkingActionForm) form;
		ArrayList<PopulateNpaMarkingData> napaData = new ArrayList<PopulateNpaMarkingData>();
		
		
		Connection conn=null;
		CallableStatement callableStmt = null;
		ResultSet rs = null;
		User user = getUserInformation(request);
		try
		{
			String cgpan = form1.getCgpan();
			String promoterName = form1.getPromoterName();
			String isSearchClicked = form1.getIsSearchClicked();			
			
			if((cgpan!=null && !cgpan.isEmpty()) && (promoterName!=null && !promoterName.isEmpty()))
			{
				if(conn==null){
					conn = DBConnection.getConnection();
				}
				
				String bankId = user.getBankId();
				String zoneId = user.getZoneId();
				String branchId = user.getBranchId();
				String memberId=bankId+zoneId+branchId;
				System.out.println("Member id===65==="+memberId);

				callableStmt = conn.prepareCall("{call FUNC_GET_NPA_DETAILS(?,?,?,?,?)}");
				callableStmt.registerOutParameter(1, Types.VARCHAR);
				callableStmt.setString(2,cgpan );
				callableStmt.setString(3, promoterName);
				callableStmt.setString(4, memberId);
				callableStmt.registerOutParameter(5, Types.VARCHAR);

				rs = callableStmt.executeQuery();
				while(rs.next())
				{
					String Disburse_date = rs.getString("Disburse_date");
					String Disburse_amount = String.valueOf(rs.getDouble("Disburse_amount"));
					String IsFinaldisbursed = rs.getString("IsFinaldisbursed");
					
					String checkBoxVal =  rs.getString("CHECKBOXVAL");
					PopulateNpaMarkingData data = new PopulateNpaMarkingData();
					
					if(checkBoxVal.equals("Y")){
						data.setCheckBoxVal("checked");
					}
					else{
						data.setCheckBoxVal("");
					}
					napaData.add(data);
				}
				
			}
			
			
		}
		catch(Exception err)
		{
			Log.log(2, "DisbursementAction", "WriteToFile", err.getMessage());
			request.setAttribute("dMessage", err.getMessage());
		}
		finally
		{
			try
			{
				if(rs !=null){
					((ResultSet) rs).close();
					rs = null;
				}
				if(callableStmt != null){
					callableStmt.close();
					callableStmt = null;
				}
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
		}
		return napaData;
	}*/

	
	public ArrayList getPromoterDetails(String cgpan , Connection conn, String bankId,String mliDi)
	{
		ArrayList promoter = new ArrayList();
		PreparedStatement pstmt = null;
		Resultset rs = null;
		try
		{
			String query="";
			if(mliDi.equalsIgnoreCase("000000000000")){
				query = "SELECT B.PMR_REF_NO,CONCAT(CPFIRSTNAME,' ',CPLASTNAME) "
						+ "PROMOTERNAME FROM SUBDEBT_APPLICATION_DETAIL A LEFT JOIN "
						+ "SUBDEBT_PROMOTER_DETAIL B ON A.APP_REF_NO=B.APP_REF_NO WHERE CGPAN=?";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, cgpan);
				
			}
			else
			{
				query = "SELECT B.PMR_REF_NO,CONCAT(CPFIRSTNAME,' ',CPLASTNAME) "
						+ "PROMOTERNAME FROM SUBDEBT_APPLICATION_DETAIL A LEFT JOIN "
						+ "SUBDEBT_PROMOTER_DETAIL B ON A.APP_REF_NO=B.APP_REF_NO WHERE CGPAN=? and MEM_BNK_ID=?";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, cgpan);
				pstmt.setString(2, bankId);
			}
			
			
			rs=(Resultset) pstmt.executeQuery();  
			while(((ResultSet) rs).next())
			{
				{
					String pmr_refNo = ((ResultSet) rs).getString("PMR_REF_NO");
					String promoterName = ((ResultSet) rs).getString("PROMOTERNAME");
					Map option = new HashMap();
					option.put("label", promoterName);
					option.put("value", pmr_refNo);
					promoter.add(option);
				}
			}
		}
		catch(Exception err)
		{
			try {
				conn.rollback();
			} catch (SQLException ignore) {}
			Log.log(2, "NpaMarkingAction", "WriteToFile", err.getMessage());
			err.printStackTrace();
		}
		finally
		{
			try
			{
				
				if(rs !=null){
					((ResultSet) rs).close();
					rs = null;
				}
				if(pstmt != null){
					pstmt.close();
					pstmt = null;
				}
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
		}
		return promoter;
	}
	
	public ArrayList getReason(Connection conn){
		
		ArrayList reasons = new ArrayList();
		CallableStatement callableStmt = null;
		ResultSet rs = null;
		String reason="NPA Reason";
		int status = -1;
		String errorCode = null;
		String message = "";
		try
		{
			
				callableStmt = conn.prepareCall("{call FUNC_GET_NPA_MASTER_DETAILS(?,?,?)}");
				callableStmt.registerOutParameter(1, Types.VARCHAR);
				callableStmt.setString(2,reason );
				callableStmt.registerOutParameter(3, Types.VARCHAR);
				
				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(3);
				//System.out.println("status::" + status + "\t errorCode" + errorCode);

				if (status == Constants.FUNCTION_FAILURE) {
					
					Log.log(Log.ERROR, "CPDAO", "getReason()",
							"getReason returns a 1. Error code is :" + errorCode);
					message = errorCode;
					if (callableStmt != null) {
						callableStmt.close();
					}
				} else if (status == Constants.FUNCTION_SUCCESS) {

					rs = callableStmt.executeQuery();
				}
					while(((ResultSet) rs).next())
					{
						{
						String getreason = rs.getString("npa_mst_description");
						//String promoterName = ((ResultSet) rs).getString("PROMOTERNAME");
						Map option = new HashMap();
						option.put("label", getreason);
						option.put("value", getreason);
						reasons.add(option);
						}
					
				}	
			
		}
		catch(Exception err)
		{
			try {
				conn.rollback();
			} catch (SQLException ignore) {}
			Log.log(2, "NpaMarkingAction", "WriteToFile", err.getMessage());
			err.printStackTrace();
		}
		finally
		{
			try
			{
				if (conn != null) {
					DBConnection.freeConnection(conn);
				}
				if(rs !=null){
					((ResultSet) rs).close();
					rs = null;
				}
				if(callableStmt != null){
					callableStmt.close();
					callableStmt = null;
				}
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
		}
		return reasons;
	}
	
	
	
	
public ArrayList getCreditRisk(Connection conn){
		
		ArrayList creditRisks = new ArrayList();
		CallableStatement callableStmt = null;
		ResultSet rs = null;
		String creditRisk="credit risk";
		int status = -1;
		String errorCode = null;
		String message = "";
		try
		{
			
				callableStmt = conn.prepareCall("{call FUNC_GET_NPA_MASTER_DETAILS(?,?,?)}");
				callableStmt.registerOutParameter(1, Types.VARCHAR);
				callableStmt.setString(2,creditRisk );
				callableStmt.registerOutParameter(3, Types.VARCHAR);
			
				callableStmt.execute();
				status = callableStmt.getInt(1);
				errorCode = callableStmt.getString(3);
				//System.out.println("status::" + status + "\t errorCode" + errorCode);

				if (status == Constants.FUNCTION_FAILURE) {
					
					Log.log(Log.ERROR, "CPDAO", "getCreditRisk",
							"getCreditRisk returns a 1. Error code is :" + errorCode);
					message =  errorCode;
					if (callableStmt != null) {
						callableStmt.close();
					}
				} else if (status == Constants.FUNCTION_SUCCESS) {

					rs = callableStmt.executeQuery();
				}
					while(((ResultSet) rs).next())
					{
						{
							String getcreditRisks = rs.getString("npa_mst_description");
							//String promoterName = ((ResultSet) rs).getString("PROMOTERNAME");
							Map option = new HashMap();
							option.put("label", getcreditRisks);
							option.put("value", getcreditRisks);
							creditRisks.add(option);
						}
					
				}	
			
		}
		catch(Exception err)
		{
			try {
				conn.rollback();
			} catch (SQLException ignore) {}
			Log.log(2, "NpaMarkingAction", "WriteToFile", err.getMessage());
			err.printStackTrace();
		}
		finally
		{
			try
			{
				if (conn != null) {
					DBConnection.freeConnection(conn);
				}
				
				if(rs !=null){
					((ResultSet) rs).close();
					rs = null;
				}
				if(callableStmt != null){
					callableStmt.close();
					callableStmt = null;
				}
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
		}
		return creditRisks;
	}


public ArrayList getReduction(Connection conn){
	
	ArrayList reductions = new ArrayList();
	CallableStatement callableStmt = null;
	ResultSet rs = null;
	String reduction="Reasons for Reduction";
	int status = -1;
	String errorCode = null;
	String message = "";
	try
	{
		
			callableStmt = conn.prepareCall("{call FUNC_GET_NPA_MASTER_DETAILS(?,?,?)}");
			callableStmt.registerOutParameter(1, Types.VARCHAR);
			callableStmt.setString(2,reduction );
			callableStmt.registerOutParameter(3, Types.VARCHAR);
			rs = callableStmt.executeQuery();

			if (status == Constants.FUNCTION_FAILURE) {
				
				Log.log(Log.ERROR, "CPDAO", "getReduction",
						"getReduction returns a 1. Error code is :" + errorCode);
				message =  errorCode;
				if (callableStmt != null) {
					callableStmt.close();
				}
			} else if (status == Constants.FUNCTION_SUCCESS) {

				rs = callableStmt.executeQuery();
			}
				while(((ResultSet) rs).next())
				{
					{
						String getreductions = rs.getString("npa_mst_description");
						//String promoterName = ((ResultSet) rs).getString("PROMOTERNAME");
						Map option = new HashMap();
						option.put("label", getreductions);
						option.put("value", getreductions);
						reductions.add(option);
					}
				
			}	
		
	}
	catch(Exception err)
	{
		try {
			conn.rollback();
		} catch (SQLException ignore) {}
		Log.log(2, "NpaMarkingAction", "WriteToFile", err.getMessage());
		err.printStackTrace();
	}
	finally
	{
		try
		{
			if (conn != null) {
				DBConnection.freeConnection(conn);
			}
			if(rs !=null){
				((ResultSet) rs).close();
				rs = null;
			}
			if(callableStmt != null){
				callableStmt.close();
				callableStmt = null;
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	return reductions;
}



public ActionForward getNpaDetail(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws Exception
{
	NpaMarkingActionForm form1 = (NpaMarkingActionForm) form;
	String message = "";
	Connection conn = null;
	try {		
		
			User user = getUserInformation(request);
			if(conn==null){
				conn = DBConnection.getConnection(false);
			}
		
			String userId = user.getUserId();
			message=saveNpaDataValues(userId,conn,request,form1,mapping,response);
			
			getNpaMarkingSearchDetail(mapping,form,request,response);
	
	} catch (Exception err) {
		try {
			conn.rollback();
		} catch (SQLException ignore) {}
		Log.log(2, "NpaMarkingAction", "WriteToFile", err.getMessage());
		err.printStackTrace();
	}	
	finally
	{
		try {
			if (conn != null) {
				DBConnection.freeConnection(conn);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	
	}
	
	form1.setMessage(message);
	return mapping.findForward("npaMarkingPage");
}


public String saveNpaDataValues(String user,Connection conn,HttpServletRequest request,NpaMarkingActionForm form1,
		ActionMapping mapping,HttpServletResponse response)
{
	String sqlQuery="";
	CallableStatement callableStmt = null;
	ResultSet rs = null;
 	String date ;
    String amount;
    String[] check;
    int status = -1;
	String errorCode = null;
	String message = "";
	String npaDate="";		
    String cgpan = "" , pmr_refNo = "",npaReason="",enumerateEfforts="",interestRepayment="",principleOutstanding="",
    		interestOutstanding="",checkBoxVal="",landReason="",buildingReason="",plantMachineryReason="",
    		otherFixedReason="",currentAssetReson="",otherReason="";
    
    double prinicipleRepayment=0.0;
    String landDataOfNPA,landNetWorth,buildingDateOfNpa,buildingNetWorth,plantMachineryDateNpa,plantMachineryNetWorth,
    otherFixedMovableDateOfNpa,otherFixedMovableNetWorth,
    currentAssetDateOfNpa,currentAssetNetWorth,otherNpa,otherNetWorth;
   
    try
	{
    	
    	date = request.getParameter("NPADate");
    	 
    //	amount = request.getParameterValues("amount");
    	String check1=request.getParameter("check");
    	//System.out.println(cgpan + "\t" + pmr_refNo + "\t" + date[0] + "\t" + check[0]);
    	principleOutstanding=request.getParameter("PrincipleOutstanding");
    	interestOutstanding=request.getParameter("InterestOutstanding");
		
		cgpan = form1.getCgpan1();
		pmr_refNo = form1.getPromoterName1();
		//date = request.getParameter("NPADate");
		npaReason=form1.getReason();
		enumerateEfforts=form1.getCreditRisk();
		prinicipleRepayment=form1.getPrinicipleRepayment();
		interestRepayment=form1.getInterestRepayment();
		//principleOutstanding=form1.getPrincipleOutstanding();
		//interestOutstanding=form1.getInterestOutstanding();
		checkBoxVal=form1.getCheckBoxVal();
		
		landDataOfNPA=form1.getLandDataOfNPA();
		landNetWorth=form1.getLandNetWorth();
		landReason=form1.getLandReason();
		
		buildingDateOfNpa=form1.getBuildingDateOfNpa();
		buildingNetWorth=form1.getBuildingNetWorth();
		buildingReason=form1.getBuildingReason();
		
		plantMachineryDateNpa=form1.getPlantMachineryDateNpa();
		plantMachineryNetWorth=form1.getPlantMachineryNetWorth();
		plantMachineryReason=form1.getPlantMachineryReason();
		
		otherFixedMovableDateOfNpa=form1.getOtherFixedMovableDateOfNpa();
		otherFixedMovableNetWorth=form1.getOtherFixedMovableNetWorth();
		otherFixedReason=form1.getOtherFixedReason();
		
		currentAssetDateOfNpa=form1.getCurrentAssetDateOfNpa();
		currentAssetNetWorth=form1.getCurrentAssetNetWorth();
		currentAssetReson=form1.getCurrentAssetReson();
		
			otherNpa=form1.getOtherNpa();
			otherNetWorth=form1.getOtherNetWorth();
			otherReason=form1.getOtherReason();
		

			callableStmt = conn.prepareCall("{call FUNC_INSERT_NPA_DETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, Types.VARCHAR);
			callableStmt.setString(2,cgpan );
			callableStmt.setString(3, pmr_refNo);
			
			java.util.Date date1= new SimpleDateFormat("dd/MM/yyyy").parse(date);  
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateVal = formatter.format(date1);
			callableStmt.setString(4, dateVal);
			
			callableStmt.setString(5, npaReason);
			callableStmt.setString(6, enumerateEfforts );
			//callableStmt.setString(7, prinicipleRepayment);
			callableStmt.setDouble(7, prinicipleRepayment);
			callableStmt.setString(8, interestRepayment);
			callableStmt.setString(9, principleOutstanding);
			callableStmt.setString(10,interestOutstanding );
			
			callableStmt.setString(11,check1);
			
			callableStmt.setString(12, landDataOfNPA);
			callableStmt.setString(13,landNetWorth );
			callableStmt.setString(14, landReason);
			
			callableStmt.setString(15, buildingDateOfNpa);
			callableStmt.setString(16,buildingNetWorth );
			callableStmt.setString(17, buildingReason);
			
			callableStmt.setString(18, plantMachineryDateNpa);
			callableStmt.setString(19,plantMachineryNetWorth );
			callableStmt.setString(20, plantMachineryReason);
			
			callableStmt.setString(21, otherFixedMovableDateOfNpa);
			callableStmt.setString(22, otherFixedMovableNetWorth );
			callableStmt.setString(23, otherFixedReason);
			
			callableStmt.setString(24, currentAssetDateOfNpa);
			callableStmt.setString(25, currentAssetNetWorth);
			callableStmt.setString(26, currentAssetReson );
			
			callableStmt.setString(27, otherNpa);
			callableStmt.setString(28, otherNetWorth);
			callableStmt.setString(29, otherReason );
			
			callableStmt.setString(30, user);
			
			callableStmt.registerOutParameter(31, Types.VARCHAR);
			callableStmt.execute();
			
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(31);
			if (status == Constants.FUNCTION_FAILURE) {
				Log.log(Log.ERROR, "CPDAO", "saveNpaDataValues()",
						"saveNpaDataValues returns a 1. Error code is :" + errorCode);
				message = errorCode;
				
				if (callableStmt != null) {
					callableStmt.close();
					callableStmt = null;
				}
			} else if (status == Constants.FUNCTION_SUCCESS) {
				
				ArrayList<NPAMarkingPopulateData> npaPopulateData = getNPAPopulatedData(mapping, form1, request,response);
				request.setAttribute("npaPopulateData", npaPopulateData);
				message = "Data Inserted Successfully!!";
			}
	
	}
	catch(Exception e)
	{
		try {
			conn.rollback();
		} catch (SQLException ignore) {}
		e.printStackTrace();
		System.out.println(e.getMessage());
	}
    
    
    finally {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (callableStmt != null) {
				callableStmt.close();
				callableStmt = null;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }		
	//return mapping.findForward("disbursementDetailPage");
	return message;
}


public String doDateValidation(String memberId, String npadate,Connection conn)throws Exception
{
	String message = "";
	int status = -1;
	String errorCode = null;
	CallableStatement callableStmt = null;
	ResultSet rs = null;
	
	try
	{
		callableStmt = conn.prepareCall("{call FUNC_NPA_DATE_VALIDATE(?,?,?,?)}");
		callableStmt.registerOutParameter(1, Types.VARCHAR);
		callableStmt.setString(2,memberId );
		java.util.Date date1= new SimpleDateFormat("dd/MM/yyyy").parse(npadate);  
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateVal = formatter.format(date1);
		callableStmt.setString(3,dateVal );
		callableStmt.registerOutParameter(4, Types.VARCHAR);
		
		callableStmt.execute();
		status = callableStmt.getInt(1);
		errorCode = callableStmt.getString(4);
		if (status == Constants.FUNCTION_FAILURE) {
			message = errorCode;
			
			if (callableStmt != null) {
				callableStmt.close();
				callableStmt = null;}
				if(conn != null){
					conn.rollback();
				}
		} else if (status == Constants.FUNCTION_SUCCESS) {
			//System.out.println("Success...");
			message = "";
		}
	}
	catch(Exception e)
	{
		try {
			conn.rollback();
		} catch (SQLException ignore) {}
		message = e.getMessage();
		e.printStackTrace();
	}
	finally
	{
		try
		{
			if (conn != null) {
				DBConnection.freeConnection(conn);
			}
			if (callableStmt != null) {
				callableStmt.close();
				callableStmt = null;}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	return message;
}

public ArrayList<NPAMarkingPopulateData> getNPAPopulatedData(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response)

{
    String sqlQuery = "";
    CallableStatement callableStmt = null;
    ResultSet rs = null;
    NpaMarkingActionForm form1 = (NpaMarkingActionForm) form;
    ArrayList<NPAMarkingPopulateData> npaPopulateData = new ArrayList<NPAMarkingPopulateData>();
    int status = -1;
    String errorCode = "", message = "";
    Connection conn = null;
    try {
        //System.out.println("Inside method.... getNPAPopulatedData :: CGPAN" + form1.getCgpan1() + "\t member is ::"+ form1.getPromoterName1());
        if (conn == null) {
            conn = DBConnection.getConnection(false);
        }
        callableStmt = conn.prepareCall("{call FUNC_Get_SUBDEBT_AUDIT_HISTORY(?,?,?,?)}");
        callableStmt.registerOutParameter(1, Types.VARCHAR);
        callableStmt.setString(2, form1.getCgpan1());
        callableStmt.setString(3, form1.getPromoterName1());
        callableStmt.registerOutParameter(4, Types.VARCHAR);



        callableStmt.execute();
        status = callableStmt.getInt(1);
        errorCode = callableStmt.getString(4);
        //System.out.println("status::" + status + "\t errorCode" + errorCode);



        if (status == Constants.FUNCTION_FAILURE) {
            Log.log(Log.ERROR, "CPDAO", "getDisbursementSearchDetail()",
                    "getDisbursementSearchDetail returns a 1. Error code is :" + errorCode);

            //System.out.println("Getting Error....");

            if (callableStmt != null) {
                callableStmt.close();
            }
            conn.rollback();
            message = errorCode;
        } else if (status == Constants.FUNCTION_SUCCESS) {
        	
        	//System.out.println("Inside Successs" );
        	
            rs = callableStmt.executeQuery();
            		
            int i = rs.getRow();
            //System.out.println("I is " + i);
           /* if(i == 0)
            {
            	NPAMarkingPopulateData data = new NPAMarkingPopulateData();
            	
                data.setClm_au_id(0);
                data.setClm_LVEL("");
                data.setUser_id("");
                data.setClm_dttime("");
                data.setClm_status("");
                data.setClm_remark("");
                
                //npaPopulateData.add(data);
            }
            else
            {*/
            	while (rs.next()) {
                	NPAMarkingPopulateData data = new NPAMarkingPopulateData();
                    data.setClm_au_id(rs.getInt("clm_au_id"));
                    data.setClm_LVEL(rs.getString("Clm_LVEL"));
                    data.setUser_id(rs.getString("User_id"));
                    data.setClm_dttime(rs.getString("clm_dttime"));
                    data.setClm_status(rs.getString("clm_status"));
                    data.setClm_remark(rs.getString("clm_remark"));



                    npaPopulateData.add(data);
                }
            	
            	npaPopulateData.forEach((d) -> {
                    //System.out.println("Data is ::::" + d.getClm_au_id() + "\t" + d.getClm_au_id() + "\t" + d.getClm_LVEL());
                });
                message = "";
           // }

            
            
        }
    } catch (Exception err) {
        Log.log(2, "DisbursementAction", "WriteToFile", err.getMessage());
        form1.setMessage(message);
        err.printStackTrace();
        try {
            conn.rollback();
        } catch (SQLException ignore) {
        }
    } finally {
        try {
        	if (conn != null) {
				DBConnection.freeConnection(conn);
			}
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (callableStmt != null) {
                callableStmt.close();
                callableStmt.close();
            }
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    return npaPopulateData;
}

}
