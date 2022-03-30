<%@page import="com.cgtsi.admin.User"%>
<%@ page language="java"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.cgtsi.actionform.ReportActionForm"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="com.cgtsi.claim.ClaimConstants"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@ include file="/jsp/SetMenuInfo.jsp"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.cgtsi.actionform.BankMandateClaimList"%>
<%@page import="com.cgtsi.actionform.ClaimProcessingApprovalActionForm"%>
<html>
<head>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
	integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l"
	crossorigin="anonymous">
<link href="/SUBDEBT/css/StyleSheet.css" rel="stylesheet"
	type="text/css">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script src="https://code.jquery.com/ui/1.10.1/jquery-ui.js"></script>
<title>Emergency Credit Guarantee Fund Trust for Small
	Industries(CGTSI)</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<style type="text/css">
.ui-datepicker {
	background-color: #fff;
}
</style>
<script type="text/javascript">
$(document).ready(function() 
{
	var cgpan = document.getElementById('cgpan').value;
	var memberId = document.getElementById('memberId').value;
	
});

function mainForm()
{
	 $("#backbutton").attr("disabled", "disabled");
		$("#backbutton")[0].innerText = 'loading';
		setTimeout(function() {
			$("#backbutton")[0].innerText = Back;
		}, 8000); 
	var memberId = $("#memberId").val();
	var bankIdd = memberId.substr(0,4);
	
	document.claimProcessingApprovalForm.target = "_self";
	document.claimProcessingApprovalForm.method = "POST";
	document.claimProcessingApprovalForm.action = "claimProcessingApproval.do?method=getClaimProcessingApproval&bankIdd="+bankIdd;
	document.claimProcessingApprovalForm.submit();
}
$(document).on('click', '#downloadFile', function() {
		var cgpan1 = $("#cgpan").val();
	 	document.claimProcessingApprovalForm.target = "_self";
		document.claimProcessingApprovalForm.method = "POST";
		document.claimProcessingApprovalForm.action = "claimProcessingApproval.do?method=downloadFile&cgPan="+cgpan1;
		document.claimProcessingApprovalForm.submit();
	
});
</script>

<body bgcolor="#FFFFFF" topmargin="0"
	data-new-gr-c-s-check-loaded="14.1008.0" data-gr-ext-installed="">
	<%
	if (request.getAttribute("bankClaimDataList") != null) {
		ArrayList<BankMandateClaimList> claimList = (ArrayList) request.getAttribute("bankClaimDataList");
		Iterator<BankMandateClaimList> list = claimList.iterator();
		if (list.hasNext()) {
			BankMandateClaimList claimListData = list.next();
	%>
	<html:form name="claimProcessingApprovalForm"
		type="com.cgtsi.actionform.ClaimProcessingApprovalActionForm"
		action="claimProcessingApproval.do?method=getClaimProcessingApproval" method="POST">
		<div class="container row">
			<div id="isDisable">

				<!-- ===============Promoter Section==========Start============== -->
				 <html:hidden property="cgpan" name="claimProcessingApprovalForm" styleId="cgpan" />
				 <html:hidden property="memberId" name="claimProcessingApprovalForm" styleId="memberId" />
				 
				 <%-- <html:hidden property="pmrReferanceNo" name="claimProcessingApprovalForm" styleId="pmrReferanceNo" />
				 <html:hidden property="pcgpan" name="claimProcessingApprovalForm" styleId="pcgpan" /> --%>
				 
				<div class="row" style="margin-top: 10px;">
					<div class="col-md-12">
						<div class="col-md-12 alert alert-primary" role="alert">PromoterData</div>
					</div>
					
					<div class="col-md-2" style="font-weight: bold;">
						<label for="CGPAN">Promoter Name</label>
					</div>
					<div class="col-md-2"><%=claimListData.getPromoterNameValuee()%></div>
					<div class="col-md-2" style="font-weight: bold;">
						<label for="Unit Name">Unit Name</label>
					</div>
					<div class="col-md-2"><%=claimListData.getPromoterUnitName()%></div>
					<div class="col-md-2" style="font-weight: bold;">
						<label for="Guarantee amount">Guarantee amount</label>
					</div>
					<div class="col-md-2"><%=claimListData.getPromoterGuranteeAmount() %></div>
					<div class="col-md-2" style="font-weight: bold;">
						<label for="Sanction date">Sanction date</label>
					</div>
					<div class="col-md-2"><%=claimListData.getPromoterSactionDate()%></div>
					<div class="col-md-2" style="font-weight: bold;">
						<label for="Guarantee Start Date">Guarantee Start Date</label>
					</div>
					<div class="col-md-2"><%=claimListData.getPromoterGuaranteeStartDate()%></div>

				</div>
				<!-- ===============Promoter Section==========End============== -->
				
				 <!-- ===============Branch Section==========Start============== -->
					<div class="row" style="margin-top: 10px;">
						<div class="col-md-12">
							<div class="col-md-12 alert alert-primary" role="alert"> Details of Operating Office and Lending Branch</div>
						</div>
							<div class="col-md-2" style="font-weight: bold;">
								<label for="Member Id">Member Id</label>
							</div>
							<div class="col-md-2"><%= claimListData.getMemberId()%></div>
							
							<div class="col-md-2" style="font-weight: bold;">
								<label for="MLI Name">MLI Name</label>
							</div>
							<div class="col-md-2"><%= claimListData.getBranchMliName()%></div>
							
							<div class="col-md-2" style="font-weight: bold;">
								<label for="City">City</label>
							</div>
							<div class="col-md-2"><%= claimListData.getBranchCity()%></div>
							
							<div class="col-md-2" style="font-weight: bold;">
								<label for="District">District</label>
							</div>
							<div class="col-md-2"><%= claimListData.getBranchDistrict()%></div>
							
							<div class="col-md-2" style="font-weight: bold;">
								<label for="State">State</label>
							</div>
							<div class="col-md-2"><%= claimListData.getBranchState()%></div>
							
							<div class="col-md-2" style="font-weight: bold;">
								<label for="Dealing Officer Name">Dealing Officer Name</label>
							</div>
							<div class="col-md-2"><%= claimListData.getBranchDealingOfficerName()%></div>
							
							<div class="col-md-2" style="font-weight: bold;">
								<label for="GSTIN No">GSTIN No</label>
							</div>
							<div class="col-md-2"><%= claimListData.getBranchGstNo()%></div>
							
					</div>	
					<!-- ===============Promoter Section==========End============== -->


					<!-- ===============Borrower's Section==========End============== -->
					<div class="row" style="margin-top: 10px;">
						<div class="col-md-12">
							<div class="col-md-12 alert alert-primary" role="alert">Borrower's/Unit's
								Details</div>
						</div>
						
						<div class="col-md-2" style="font-weight: bold;">
							<label for="Complete Address">Complete Address</label>
						</div>
						<div class="col-md-2"><%= claimListData.getBorrowerCompleteAddress()%></div>
						
						<div class="col-md-2" style="font-weight: bold;">
							<label for="City">City</label>
						</div>
						<div class="col-md-2"><%= claimListData.getBorrowerCity()%></div>
						
						<div class="col-md-2" style="font-weight: bold;">
							<label for="District">District</label>
						</div>
						<div class="col-md-2"><%= claimListData.getBorrowerDistrict()%></div>
						
						<div class="col-md-2" style="font-weight: bold;">
							<label for="State">State</label>
						</div>
						<div class="col-md-2"><%= claimListData.getBorrowerState()%></div>
						
						<div class="col-md-2" style="font-weight: bold;">
							<label for="Pin Code">Pin Code</label>
						</div>
						<div class="col-md-3"><%= claimListData.getBorrowerPinCode()%></div>
					</div>	
					<!-- ===============Borrower's Section==========End============== -->


					<!-- ===============Account Section==========Start============== -->

					<div class="row" style="margin-top: 10px;">
						<div class="col-md-12">
							<div class="col-md-12 alert alert-primary" role="alert">Status of Account(s)</div>
						</div>
						
						<div class="col-md-3" style="font-weight: bold;">
							<label for="getnPADate">Date on which Account was Classified as NPA</label>
						</div>
						<div class="col-md-3"><%= claimListData.getnPADate()%></div>
						
						<div class="col-md-3" style="font-weight: bold;">
							<label for="getAccountsWilFulDefaulter">Wilful defaulter</label>
						</div>
						<div class="col-md-3"><%= claimListData.getAccountsWilFulDefaulter()%></div>
						
						<div class="col-md-3" style="font-weight: bold;">
							<label for="getAccountsClassified">Has the account been classified as fraud</label>
						</div>
						<div class="col-md-3"><%= claimListData.getAccountsClassified()%></div>
						
						<div class="col-md-3" style="font-weight: bold;">
							<label for="getAccountsEnquiry">Internal and/or external enquiry has been concluded</label>
						</div>
						<div class="col-md-3"><%= claimListData.getAccountsEnquiry()%></div>
						
						<div class="col-md-3" style="font-weight: bold;">
							<label for="getAccountsMliReported">Involvement of staff of MLI has been reported</label>
						</div>
						<div class="col-md-3"><%= claimListData.getAccountsMliReported()%></div>
						
						<div class="col-md-3" style="font-weight: bold;">
							<label for="getAccountReasonTurning">Reasons for Account turning</label>
						</div>
						<div class="col-md-3"><%= claimListData.getAccountReasonTurning()%></div>
						
						<div class="col-md-3" style="font-weight: bold;">
							<label for="getDateofIssueofRecallNotice">Date of issue of Recall Notice</label>
						</div>
						<div class="col-md-3"><%= claimListData.getDateofIssueofRecallNotice()%></div>
						
						<div class="col-md-3" style="font-weight: bold;">
							<label for="getAccountSatisfactoryReason">Provide satisfactory reason for issuing recall notice prior to NPA date</label>
						</div>
						<div class="col-md-3"><%= claimListData.getAccountSatisfactoryReason()%></div>
					</div>	 

						<!-- ===============Account Section==========Start============== -->

						<!-- ===============Legal Section==========Start============== -->
						<div class="row" style="margin-top: 10px;">
							<div class="col-md-12">
								<div class="col-md-12 alert alert-primary" role="alert">Details of Legal Proceedings</div>
							</div>
							
							<%
								if(claimListData.getLegalProceedings() != null && claimListData.getLegalProceedings() != ""){
									
							%>
							<div class="col-md-3" style="font-weight: bold;">
								<label for="getLegalProceedings">Forum through which legal proceedings were initiated 
								against borrower</label>
							</div>
							<div class="col-md-3"><%= claimListData.getLegalProceedings()%></div>
							<%
								}
							%>
							
							<%
								if(claimListData.getLegalOtherForms() != null && claimListData.getLegalOtherForms() != ""){
							%>
							<div class="col-md-3" style="font-weight: bold;">
								<label for="getLegalOtherForms">Other Forum(s)</label>
							</div>
							<div class="col-md-3"><%= claimListData.getLegalOtherForms()%></div>
							<%
								}
							%>
							
							<%
								if(claimListData.getLegalRegistration() != null && claimListData.getLegalRegistration() != ""){
							%>
							<div class="col-md-3" style="font-weight: bold;">
								<label for="getLegalRegistration">Suit / Case Registration No.</label>
							</div>
							<div class="col-md-3"><%= claimListData.getLegalRegistration()%></div>
							<%
								}
							%>
							
							<%
								if(claimListData.getLegalsuitFilingDate() != null && claimListData.getLegalsuitFilingDate() != "" ){
							%>
							<div class="col-md-3" style="font-weight: bold;">
								<label for="getLegalsuitFilingDate">Suit Filing Date</label>
							</div>
							<div class="col-md-3"><%= claimListData.getLegalsuitFilingDate()%></div>
							<%
								}
							%>
							
							<%
								if(claimListData.getLegalSatisfactoryRreason() != null && claimListData.getLegalSatisfactoryRreason() != ""){
							%>
							<div class="col-md-3" style="font-weight: bold;">
								<label for="getLegalsuitFilingDate">Provide satisfactory reason for filing suit 
								before NPA date</label>
							</div>
							<div class="col-md-3"><%= claimListData.getLegalSatisfactoryRreason()%></div>
							<%
								}
							%>
							
							<%
								if(claimListData.getDateofpossessionofassets() != null && claimListData.getDateofpossessionofassets() != "" && !claimListData.getDateofpossessionofassets().contains("")){	
							%>
							<div class="col-md-3"  style="font-weight: bold;" >
								<label for="getDateofpossessionofassets">Date of possession of assets under sarfaesi act</label>
							</div> 
							<div class="col-md-3"><%= claimListData.getDateofpossessionofassets()%></div>
							<%
								}
							%>
							
							<%
								if(claimListData.getLegalLocation() != null && claimListData.getLegalLocation() != ""){
							%>
							<div class="col-md-3" style="font-weight: bold;">
								<label for="getLegalLocation">Location</label>
							</div>
							<div class="col-md-3"><%= claimListData.getLegalLocation()%></div>
							<%
								}
							%>
							
							<%
								if(claimListData.getLegalAmountClaimed() != null){
							%>
							<div class="col-md-3" style="font-weight: bold;">
								<label for="getLegalAmountClaimed">Amount Claimed in the Suit in Rs.</label>
							</div>
							<div class="col-md-3"><%= claimListData.getLegalAmountClaimed()%></div>
							<%
								}
							%>
						</div>
						
						<div class="row" style="margin-top: 10px;">
							<div class="col-md-12">
								<div class="col-md-12 alert alert-primary" role="alert">Attachments</div>
							</div>	
							<div class="col-md-8">
							<a href="#" id="downloadFile"><%=claimListData.getFilenameName()%></a>
							</div>
						</div>
						
						<!-- ===============Legal Section==========End============== -->
		
						<!-- ===============TC Section==========Start============== -->
						<div class="row" style="margin-top: 10px;">
							<div class="col-md-12">
								<div class="col-md-12 alert alert-primary" role="alert">Term
									Loans (TC) limit Details</div>
							</div>
							
							<div class="col-md-3" style="font-weight: bold;">
								<label for="getTcTotalDisbursement">Total Disbursement Amount</label>
							</div>
							<div class="col-md-3"><%=claimListData.getTcTotalDisbursement()%></div>
							
							<div class="col-md-3" style="font-weight: bold;">
								<label for="getTcDateofLastDisbursement">Date of Last Disbursement</label>
							</div>
							<div class="col-md-3"><%=claimListData.getTcDateofLastDisbursement()%></div>
							
							<div class="col-md-3" style="font-weight: bold;">
								<label for="getTcPrincipalAmount">Repayments Principal Amount(Rs.)</label>
							</div>
							<div class="col-md-3"><%=claimListData.getTcPrincipalAmount()%></div>
							
							<div class="col-md-3" style="font-weight: bold;">
								<label for="getTcRepaymentsInterest">Repayments Interest and Other Charges</label>
							</div>
							<div class="col-md-3"><%=claimListData.getTcRepaymentsInterest()%></div>
							
							<div class="col-md-3" style="font-weight: bold;">
								<label for="getTcOutstandingAmount">NPA Outstanding Principal Amount(Rs.)</label>
							</div>
							<div class="col-md-3"><%=claimListData.getTcOutstandingAmount()%></div>
							
							<div class="col-md-3" style="font-weight: bold;">
								<label for="getTcOutstandingInterest">NPA Outstanding Interest and Other Charges</label>
							</div>
							<div class="col-md-3"><%=claimListData.getTcOutstandingInterest()%></div>
							
							<div class="col-md-3" style="font-weight: bold;">
								<label for="getTcOutstandingStatedCivil">Outstanding stated in the Civil Suit /Case filed(Rs.)</label>
							</div>
							<div class="col-md-3"><%=claimListData.getTcOutstandingStatedCivil()%></div>
							
							<div class="col-md-3" style="font-weight: bold;">
								<label for="getTcOutstandingLodgement">Outstanding As On Date of Lodgement of Claim </label>
							</div>
							<div class="col-md-3"><%=claimListData.getTcOutstandingLodgement()%></div>
							
							<div class="col-md-3" style="font-weight: bold;">
								<label for="getTcAccountRestructed">Account Restructed or not</label>
							</div>
							<div class="col-md-3"><%=claimListData.getTcAccountRestructed()%></div>
						</div>	
						<!-- ===============TC Section==========End============== -->

						<!-- =========Recovery======Borrower Section==========Start======== -->
						<div class="row" style="margin-top: 10px;">
							<div class="col-md-12">
								<div class="col-md-12 alert alert-primary" role="alert"> Recovery
									made from Borrower / Unit after account classified as NPA
								</div>
							</div>
							<div class="col-md-12">
								<div style="font-weight: bold;">
									<label for="termLoan">Term Loan / Composite Loan </label>
								</div>
							</div>
							<div class="col-md-3" style="font-weight: bold;">
									<label for="getRecoveryPrincipal">Principal(Rs)</label>
							</div>
							<div class="col-md-3"><%=claimListData.getRecoveryPrincipal()%></div>
							
							<div class="col-md-3" style="font-weight: bold;">
								<label for="getRecoveryInterest">Interest and Other Charges</label>
							</div>
							<div class="col-md-3"><%=claimListData.getRecoveryInterest()%></div>
							
							<div class="col-md-3" style="font-weight: bold;">
								<label for="getRecoveryMode">Mode of Recovery</label>
							</div>
							<div class="col-md-3"><%=claimListData.getRecoveryMode()%></div>
						</div>
						<!-- ===============Borrower Section==========End======== -->
						
						<!-- ===============Security Section==========Start======== -->
						<div class="row" style="margin-top: 10px;">
							<div class="col-md-12">
								<div class="col-md-12 alert alert-primary" role="alert"> Security
									and Personal Guarantee Details</div>
							</div>

							<div class="col-md-10" style="font-weight: bold;">
								<label for="getRecoveryafterNPAindicated">Have you ensured inclusion
									of unappropriated receipts also in the amount of recovery after
									NPA indicated above?</label>
							</div>
							
							<div class="col-md-2"><%=claimListData.getRecoveryafterNPAindicated()%></div>
							
							<div class="col-md-10" style="font-weight: bold;">
								<label for="getValueasrecoveriesafterNPA"> Do you confirm feeding of correct
									value as recoveries after NPA?</label>
							</div>
							<div class="col-md-2"><%=claimListData.getValueasrecoveriesafterNPA()%></div>
							
							<div class="col-md-10" style="font-weight: bold;">
								<label for="getPersonalAmountclaim"> Total amount for which
									guarantee claim is being preferred (Amount to be claimed):</label>
							</div>
							<div class="col-md-2"><%=claimListData.getPersonalAmountclaim()%></div>
						</div>
						
						<!-- ===============Security Section==========End======== -->
                        
						<!-- ===========MLI====General inforamtion Section==Start======== -->
						<div class="row" style="margin-top: 10px;">
							<div class="col-md-12">
								<div class="col-md-12 alert alert-primary" role="alert"> General
									information - to be filled by the MLI</div>
							</div>

							<div class="col-md-10" style="font-weight: bold;">
								<label for="getGeneralMLIComment">MLI's Comment on financial
									position of Borrower/Unit</label>
							</div>
							<div class="col-md-2"><%=claimListData.getGeneralMLIComment()%></div>
							
							<div class="col-md-10" style="font-weight: bold;">
								<label for="getGeneralMLIFinancial">Details of Financial Assistance
									provided/being considered by MLI to minimize default </label>
							</div>
							<div class="col-md-2"><%=claimListData.getGeneralMLIFinancial()%></div>
							
							<div class="col-md-10" style="font-weight: bold;">
								<label for="getCreditsupportforanyotherproject">Does the MLI propose to
									provide credit support to Chief Promoter/Borrower for any other
									project</label>
							</div>
							<div class="col-md-2"><%=claimListData.getCreditsupportforanyotherproject()%></div>
							
							<div class="col-md-10" style="font-weight: bold;">
								<label for="getGeneralMLIBank">Details Of Bank Facility
									already provided to Borrower</label>
							</div>
							<div class="col-md-2"><%=claimListData.getGeneralMLIBank()%></div>
							
							<div class="col-md-10" style="font-weight: bold;">
								<label for="getPromoterunderwatchListofCGTMSE">Does the MLI advise placing
									the Borrower and/or Chief Promoter under watch-List of CGTMSE</label>
							</div>
							<div class="col-md-2"><%=claimListData.getPromoterunderwatchListofCGTMSE()%></div>
							
							<div class="col-md-3" style="font-weight: bold;">
								<label for="Remark">Remark</label>
							</div>
							<div class="col-md-9"><%=claimListData.getGeneralMLIRemark()%></div>
					    </div>		

						<!-- ==========Value=of Securities available==Start======== -->
						<div class="row" style="margin-top: 10px;">
							<div class="col-md-12">
								<div class="col-md-12 alert alert-primary" role="alert">Value
									of Securities available</div>
							</div>

							<div class="col-md-2 alert alert-secondary" role="alert"
								style="margin-top: 10px;">Nature</div>
							<div class="col-md-2 alert alert-secondary" role="alert"
								style="margin-top: 10px;">As on date of Sanction</div>
							<div class="col-md-2 alert alert-secondary" role="alert"
								style="margin-top: 10px;">As on date of NPA</div>
							<div class="col-md-2 alert alert-secondary" role="alert"
								style="margin-top: 10px;">As on Date of Preferrment of
								Claim</div>
							<div class="col-md-2 alert alert-secondary" role="alert"
								style="margin-top: 10px;">Networth of guarantor /
								Promoter(in Rs.)</div>
							<div class="col-md-2 alert alert-secondary" role="alert"
								style="margin-top: 10px;">Reasons for Reduction in the
								value of Security, if any</div>
						</div>

						<div class="row" style="margin-top: 10px; margin-left: 2px;">
							<div class="col-md-2" style="margin-top: 5px;">
								Land<font color="#FF0000" size="2">*</font>
							</div>

							<div class="col-md-2" style="margin-top: 5px;">
								<div class="col-md-2"><%=claimListData.getLandSection() %></div>
							</div>

							<div class="col-md-2" style="margin-top: 5px;">
								
									<div class="col-md-2"><%=claimListData.getLandDataOfNPA() %></div>
							</div>

							<div class="col-md-2" style="margin-top: 5px;">
								
									<div class="col-md-2"><%=claimListData.getLandDataOfClaim()%></div>
							</div>

							<div class="col-md-2" style="margin-top: 5px;">
								
									<div class="col-md-2"><%=claimListData.getLandNetWorth()%></div>
							</div>
							<div class="col-md-2" style="margin-top: 5px;">
								
							 <%=claimListData.getLandReason()%> 
								<%-- <html:select styleClass="form-control" property="landReason" 
									name="claimProcessingApprovalForm" styleId="ReasonsforReduction0">
									<html:option value="0"><%=claimListData.getLandReason()%></html:option>
								</html:select> --%>
							</div>
						</div>

						<div class="row" style="margin-top: 10px; margin-left: 2px;">
							<div class="col-md-2" style="margin-top: 5px;">
								Building<font color="#FF0000" size="2">*</font>
							</div>
							<div class="col-md-2" style="margin-top: 5px;">
								
									<div class="col-md-2"><%=claimListData.getBuildingSection()%></div>
							</div>
							<div class="col-md-2" style="margin-top: 5px;">
								
									<div class="col-md-2"><%=claimListData.getBuildingDateOfNpa()%></div>
							</div>


							<div class="col-md-2" style="margin-top: 5px;">
								
									<div class="col-md-2"><%=claimListData.getBuildingDateOfClaim()%></div>
							</div>



							<div class="col-md-2" style="margin-top: 5px;">
								
									<div class="col-md-2"><%=claimListData.getBuildingNetWorth()%></div>
							</div>


							<div class="col-md-2" style="margin-top: 5px;">
							
								<%=claimListData.getBuildingReason()%>
								<%-- <html:select styleClass="form-control" property="buildingReason" 
									name="claimProcessingApprovalForm" styleId="ReasonsforReduction0">
									<html:option value="0"><%=claimListData.getBuildingReason()%></html:option>
								</html:select> --%>
							</div>
						</div>

						<div class="row" style="margin-top: 10px; margin-left: 2px;">
							<div class="col-md-2" style="margin-top: 5px;">
								Plant and Machinery<font color="#FF0000" size="2">*</font>
							</div>
							<div class="col-md-2" style="margin-top: 5px;">
								
									<div class="col-md-2"><%=claimListData.getPlantMachinerySection()%></div>
							</div>
							<div class="col-md-2" style="margin-top: 5px;">
								
									<div class="col-md-2"><%=claimListData.getPlantMachineryDateNpa()%></div>

							</div>
							<div class="col-md-2" style="margin-top: 5px;">
								
									<div class="col-md-2"><%=claimListData.getPlantMachineryClaim()%></div>
							</div>

							<div class="col-md-2" style="margin-top: 5px;">
								
									<div class="col-md-2"><%=claimListData.getPlantMachineryNetWorth()%></div>
							</div>

							<div class="col-md-2" style="margin-top: 5px;">
								
								<%=claimListData.getPlantMachineryReason()%>
								<%-- <html:select styleClass="form-control" property="plantMachineryReason" 
									name="claimProcessingApprovalForm" styleId="ReasonsforReduction0">
									<html:option value="0"><%=claimListData.getPlantMachineryReason()%></html:option>
								</html:select> --%>
							</div>
						</div>

						<div class="row" style="margin-top: 10px; margin-left: 2px;">
							<div class="col-md-2" style="margin-top: 5px;">
								Other Fixed /Movable Assets<font color="#FF0000" size="2">*</font>
							</div>
							<div class="col-md-2" style="margin-top: 5px;">
								
									<div class="col-md-2"><%=claimListData.getOtherFixedMovableSection()%></div>
							</div>
							<div class="col-md-2" style="margin-top: 5px;">
								
									<div class="col-md-2"><%=claimListData.getOtherFixedMovableDateOfNpa()%></div>

							</div>
							<div class="col-md-2" style="margin-top: 5px;">
								
									<div class="col-md-2"><%=claimListData.getOtherFixedMovableClaim()%></div>

							</div>
							<div class="col-md-2" style="margin-top: 5px;">
							
									<div class="col-md-2"><%=claimListData.getOtherFixedMovableNetWorth()%></div>
							</div>
							<div class="col-md-2" style="margin-top: 5px;">
								
								<%=claimListData.getOtherFixedReason()%>
								<%-- <html:select styleClass="form-control" property="otherFixedReason" 
									name="claimProcessingApprovalForm" styleId="ReasonsforReduction0">
									<html:option value="0"><%=claimListData.getOtherFixedReason()%></html:option>
								</html:select> --%>
							</div>
						</div>
						<div class="row" style="margin-top: 10px; margin-left: 2px;">
							<div class="col-md-2" style="margin-top: 5px;">
								Current Assets<font color="#FF0000" size="2">*</font>
							</div>
							<div class="col-md-2" style="margin-top: 5px;">
								
									<div class="col-md-2"><%=claimListData.getCurrentAssetSection()%></div>
							</div>
							<div class="col-md-2" style="margin-top: 5px;">
								
									<div class="col-md-2"><%=claimListData.getCurrentAssetDateOfNpa()%></div>

							</div>
							<div class="col-md-2" style="margin-top: 5px;">
								
									<div class="col-md-2"><%=claimListData.getCurrentAssetClaim()%></div>
							</div>

							<div class="col-md-2" style="margin-top: 5px;">
								
									<div class="col-md-2"><%=claimListData.getCurrentAssetNetWorth()%></div>
							</div>

							<div class="col-md-2" style="margin-top: 5px;">
								
							<%=claimListData.getCurrentAssetReson()%>
								<%-- <html:select styleClass="form-control" property="currentAssetReson" 
									name="claimProcessingApprovalForm" styleId="ReasonsforReduction0">
									<html:option value="0"><%=claimListData.getCurrentAssetReson()%></html:option>
								</html:select> --%>
							</div>
						</div>
						<div class="row" style="margin-top: 10px; margin-left: 2px;">
							<div class="col-md-2" style="margin-top: 5px;">
								Others<font color="#FF0000" size="2">*</font>
							</div>
							<div class="col-md-2" style="margin-top: 5px;">
								
									<div class="col-md-2"><%=claimListData.getOtherSection()%></div>
							</div>
							<div class="col-md-2" style="margin-top: 5px;">
								
									<div class="col-md-2"><%=claimListData.getOtherNpa()%></div>
							</div>
							<div class="col-md-2" style="margin-top: 5px;">
								
									<div class="col-md-2"><%=claimListData.getOtherClaim()%></div>
							</div>

							<div class="col-md-2" style="margin-top: 5px;">
								
									<div class="col-md-2"><%=claimListData.getOtherNetWorth()%></div>
							</div>

							<div class="col-md-2" style="margin-top: 5px;">
								
								<%=claimListData.getOtherReason()%>
								<%-- <html:select styleClass="form-control" property="otherReason" 
									name="claimProcessingApprovalForm" styleId="ReasonsforReduction0">
									<html:option value="0"><%=claimListData.getOtherReason()%></html:option>
								</html:select> --%>
							</div>
						</div>


						<div class="row" style="margin-top: 10px; margin-left: 2px;">
							<div class="col-md-2" style="margin-top: 5px; font-weight: 800;">Total</div>

							<div class="col-md-2 " style="margin-top: 5px;">
								
									<div class="col-md-2"><%=claimListData.getDateOfSectionTotal()%></div>
							</div>

							<div class="col-md-2 " style="margin-top: 5px;">
								
									<div class="col-md-2"><%=claimListData.getDateOfNpaTotal()%></div>
							</div>

							<div class="col-md-2 " style="margin-top: 5px;">
								
									<div class="col-md-2"><%=claimListData.getDateOfClaimTotal()%></div>
							</div>

							<div class="col-md-2" style="margin-top: 5px;">
								
									<div class="col-md-2"><%=claimListData.getNetWorthTotal()%></div>
							</div>
						</div>
					
			<div class="row" style="margin-top: 10px; BACKGROUND-COLOR: GRAY; COLOR: WHITE; FONT-WEIGHT: 700; TEXT-ALIGN: center;">
						<div class="col-md-1">S.No.</div>
						<div class="col-md-7"> Description</div>
						<div class="col-md-4">Yes/No</div>
					</div>
				   

					<div class="row" style="margin-top: 10px;">
						<div class="col-md-1"
							style="TEXT-ALIGN: center; FONT-WEIGHT: BOLD;">1</div>
						<div class="col-md-7">Activity is eligible as per Credit
							Guarantee Scheme(CGS)</div>
						<div class="col-md-4">
							<div style="text-align:center;font-weight:bold;"><%=claimListData.getCheckList1()%></div>
						</div>
					</div>
					
					<div class="row" style="margin-top: 10px;">
						<div class="col-md-1"
							style="TEXT-ALIGN: center; FONT-WEIGHT: BOLD;">2</div>
						<div class="col-md-7">Wheather CIBIL done/CIR/KYC obtained
							and findings are satisfactory.</div>
						<div class="col-md-4">
							<div style="text-align:center;font-weight:bold;"><%=claimListData.getCheckList2()%></div>
						</div>
					</div>
					
					<div class="row" style="margin-top: 10px;">
						<div class="col-md-1"
							style="TEXT-ALIGN: center; FONT-WEIGHT: BOLD;">3</div>
						<div class="col-md-7">Rate charged on loan is as per CGS
							guidelines.</div>
						<div class="col-md-4">
							<div style="text-align:center;font-weight:bold;"><%=claimListData.getCheckList3()%></div>
						</div>
					</div>

					<div class="row" style="margin-top: 10px;">
						<div class="col-md-1"
							style="TEXT-ALIGN: center; FONT-WEIGHT: BOLD;">4</div>
						<div class="col-md-7">Third party gaurantee/collateral
							security stipulated.</div>
						<div class="col-md-4">
							<div style="text-align:center;font-weight:bold;"><%=claimListData.getCheckList4()%></div>
						</div>
					</div>

					<div class="row" style="margin-top: 10px;">
						<div class="col-md-1"
							style="TEXT-ALIGN: center; FONT-WEIGHT: BOLD;">5</div>
						<div class="col-md-7">Date of NPA as fed in the system is as
							per RBI guidelines.</div>
						<div class="col-md-4">
							<div style="text-align:center;font-weight:bold;"><%=claimListData.getCheckList5()%></div>
						</div>
					</div>
				
					<div class="row" style="margin-top: 10px;">
						<div class="col-md-1"
							style="TEXT-ALIGN: center; FONT-WEIGHT: BOLD;">6</div>
						<div class="col-md-7">Wheather outstanding amount mentioned
							in the claim application form is with respect to the NPA date as
							reported in claim form.</div>
						<div class="col-md-4">
							<div style="text-align:center;font-weight:bold;"><%=claimListData.getCheckList6()%></div>
						</div>
					</div>
					
					<div class="row" style="margin-top: 10px;">
						<div class="col-md-1"
							style="TEXT-ALIGN: center; FONT-WEIGHT: BOLD;">7</div>
						<div class="col-md-7">Whether serious deficiencies have been
							observed in the matter of
							appraisal/renewal/disbursement/followup/conduct of the account.</div>
						<div class="col-md-4">
							<div style="text-align:center;font-weight:bold;"><%=claimListData.getCheckList7()%></div>
						</div>
					</div>
					
					<div class="row" style="margin-top: 10px;">
						<div class="col-md-1"
							style="TEXT-ALIGN: center; FONT-WEIGHT: BOLD;">8</div>
						<div class="col-md-7">Major deficiencies observed in Pre
							sanction/Post disbursement inspections</div>
						<div class="col-md-4">
							<div style="text-align:center;font-weight:bold;"><%=claimListData.getCheckList8()%></div>
						</div>
					</div>
					
					<div class="row" style="margin-top: 10px;">
						<div class="col-md-1"
							style="TEXT-ALIGN: center; FONT-WEIGHT: BOLD;">9</div>
						<div class="col-md-7">Wheather deficiencies observed on the
							part of internal staff as per the Staff Accountability exercise
							carried out.</div>
						<div class="col-md-4">
							<div style="text-align:center;font-weight:bold;"><%=claimListData.getCheckList9()%></div>
						</div>
					</div>
					
					<div class="row" style="margin-top: 10px;">
						<div class="col-md-1"
							style="TEXT-ALIGN: center; FONT-WEIGHT: BOLD;">10</div>
						<div class="col-md-7">Internal rating was carried out and
							the proposal was found of Investment Grade.(applicable for loans
							sanctioned above 50 lakh)</div>
						<div class="col-md-4">
							<div style="text-align:center;font-weight:bold;"><%=claimListData.getCheckList10()%></div>
						</div>
					</div>
					
					<div class="row" style="margin-top: 10px;">
						<div class="col-md-1"
							style="TEXT-ALIGN: center; FONT-WEIGHT: BOLD;">11</div>
						<div class="col-md-7">Wheather all the recoveries pertaining
							to the account after the date of NPA and before the claim
							lodgement have been duly incorporated in the claim form.</div>
						<div class="col-md-4">
							<div style="text-align:center;font-weight:bold;"><%=claimListData.getCheckList11()%></div>
						</div>
					</div>
			</div>
						<!-- ==========Value=of Securities available==End======== -->
			<div class="col-md-4" >&nbsp;</div>			
		    <div style="padding-left:100px;">
				<button type="button" id="backbutton" class="btn btn-primary" onclick="return mainForm()">Back</button>
			</div>				
			</div>		 
		
	</html:form>
	<%
		}
	}
	%>
</body>
</html>