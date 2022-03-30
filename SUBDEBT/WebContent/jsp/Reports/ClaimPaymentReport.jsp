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
<%@page import="com.cgtsi.actionform.ClaimPaymentReportForm"%>
<%@page import="com.cgtsi.actionform.ClaimPaymentTableData"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
	integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l"
	crossorigin="anonymous" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link href="/SUBDEBT/css/StyleSheet.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script src="https://code.jquery.com/ui/1.10.1/jquery-ui.js"></script>
<SCRIPT language="JavaScript" type="text/JavaScript" src="js/CGTSI.js"></SCRIPT>
<title>Recovery Report</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<style>
.hide {
	display: block;
}

.show {
	display: block;
}
</style>
<script type="text/javascript">
$(document).ready(function() {
	var message = document.getElementById('message').value;
	if(message != "")
	{	
		var databasemsg1="<b> "+message+" </b>";  
	    
	    $("#errormsg").html("Error :"+databasemsg1).css({
            "color" : "#ff6666",
            "display" : "block"
        });
    
	    $("#message").val("");
	}
	
	var claimPaymentLoginType = document.getElementById('claimPaymentLoginType').value; 
	if(claimPaymentLoginType != null && claimPaymentLoginType == "BRANCH")
	{
		$("#claimPaymentMLIName option:eq(1)").attr("selected","selected");
		$('#claimPaymentMLIName option:not(:selected)').prop('disabled', true);
		var claimPaymentMLIId = $("#claimPaymentMLIName option:eq(1)").val().substr(0,12).trim();
		$("#claimPaymentMLIId").val(claimPaymentMLIId).attr('readonly', 'readonly');
	}
	var claimPaymentisSearchClicked = document.getElementById('claimPaymentisSearchClicked').value;
	var claimPaymentListCount = document.getElementById('claimPaymentListCount').value;	
	if(claimPaymentisSearchClicked == "")
	{
		$("#btnExportToExcel").hide();$("#isdisplay").css("display", "none");
	}
	else
	{
		$("#btnExportToExcel").show();$("#isdisplay").css("display", "block");
		if(claimPaymentListCount == "1")
		{
			$("#btnExportToExcel").hide();
		} 
	}
	$("#claimPaymentListCount").val('0');$("#claimPaymentisSearchClicked").val('');
});
function getPromoter() {
	document.claimPaymentReportForm.target = "_self";
	document.claimPaymentReportForm.method = "POST";
	document.claimPaymentReportForm.action = "claimPaymentReport.do?method=getClaimPaymentReport";
	document.claimPaymentReportForm.submit();
}
function setDateValue(id) 
{
	var start = new Date("2020-06-24"), end = new Date(), diff = new Date(
			end - start), days = diff / 1000 / 60 / 60 / 24;

	var d = "#" + id;
	$(d).datepicker({
		dateFormat : 'dd/mm/yy',
		minDate : days * -1,
		maxDate : end
	}).attr('readonly', 'readonly').focus();
}
function getClaimPaymentDetails()
{
	var claimPaymentisSearchClicked = "Clicked";
	$("#claimPaymentisSearchClicked").val(claimPaymentisSearchClicked);

	var countErr = 0;
	var claimPaymentFromDate = $("#claimPaymentFromDate").val().trim() , claimPaymentToDate = $("#claimPaymentToDate").val().trim();
	var msg="<b>Please correct the following Error:</b><ul>";
	if ($("#claimPaymentMLIName").val() == "0") {
   	 	msg=msg+"<li>Please Select MLI Name from Dropdown!!</li>"; countErr=1; }
	if (claimPaymentFromDate == "") {
   	 	msg=msg+"<li>From Date Can't be Blank!!</li>"; countErr=1; }
	if (claimPaymentToDate == "") {
   	 	msg=msg+"<li>To Date Can't be Blank!!</li>"; countErr=1; }
    if ($("#claimPaymentClaimStatus").val() == "0") {
   	 	msg=msg+"<li>Please Select Claim Status from Dropdown!!</li>"; countErr=1; }
    if ($("#claimPaymentClaimType").val() == "0") {
   	 	msg=msg+"<li>Please Select Claim Type from Dropdown!!</li>"; countErr=1; }
    
	if(countErr==1){
		$("#claimPaymentListCount").val('0');$("#claimPaymentisSearchClicked").val('');
		$("#btnExportToExcel").hide();$("#isdisplay").css("display", "none");
        $("#errormsg").html(msg).css({
             "color" : "#ff6666",
             "display" : "block"
         });
        msg=msg+"</ul>";
        $(window).scrollTop(0);
        return;
    }
    else
    {
    	if(countErr == 0){
    		$("#btnSearch").attr("disabled", "disabled");
    		$("#btnSearch")[0].innerText = 'loading';
    		setTimeout(function() {$("#btnSearch")[0].innerText = "Search";}, 8000);
    		document.claimPaymentReportForm.target = "_self";
    		document.claimPaymentReportForm.method = "POST";
    		document.claimPaymentReportForm.action = "claimPaymentReport.do?method=getClaimPaymentDetailReport&claimPaymentFromDate="
    				+claimPaymentFromDate+"&claimPaymentToDate="+claimPaymentToDate;
    		document.claimPaymentReportForm.submit();}
   	}	
}
function clearData()
{
	$("#claimPaymentMLIName option:eq(0)").attr("selected","selected"); $("#claimPaymentPromoterName option:eq(0)").attr("selected","selected");
	$("#claimPaymentClaimStatus option:eq(0)").attr("selected","selected"); $("#claimPaymentClaimType option:eq(0)").attr("selected","selected");
	$("#claimPaymentCGPAN").val('');$("#claimPaymentMLIId").val('');$("#claimPaymentClaimRefNo").val('');$("#claimPaymentPromoterITPAN").val('');$("#claimPaymentFromDate").val('');$("#claimPaymentToDate").val('');
	$("#claimPaymentisSearchClicked").val(''); $("#btnExportToExcel").hide();$("#isdisplay").css("display", "none");$("#errormsg").css("display", "none");
}
function exportTableData()
{
	document.claimPaymentReportForm.target = "_self";
	document.claimPaymentReportForm.method = "POST";
	document.claimPaymentReportForm.action = "claimPaymentReport.do?method=claimPaymentExportToExcel";
	document.claimPaymentReportForm.submit();
}
</script>
</head>
<body>
	<html:errors />
	<html:form name="claimPaymentReportForm"
		type="com.cgtsi.actionform.ClaimPaymentReportForm"
		action="claimPaymentReport.do?method=getClaimPaymentReport"
		method="POST">
		<div class="row"
			style="margin-left: 20px; padding-top: 10px; padding-bottom: 10px; border-top: 1px solid brown; border-left: 1px solid brown; border-right: 1px solid brown; border-bottom: 1px solid brown;">
			<div class="col-md-12">
			<div class="row">
			<div class="col-md-12">
				<div class="col-md-12 alert alert-primary" role="alert">Search Details</div>
			</div>
			</div>
			<div class="row">
			<div class="col-lg-12" id="errormsg" style="display: none"></div>
			
			 <div class="col-lg-12">
				<div class="row">
					<div class="col-md-2">
						<label for="MLI Name">MLI Name</label><font color="#FF0000" size="2">*</font>
						<html:select styleClass="form-control"	property="claimPaymentMLIName" name="claimPaymentReportForm" styleId="claimPaymentMLIName">
							<html:option value="0">Select</html:option>
						    <html:optionsCollection property="claimPaymentReportList"
								name="claimPaymentReportForm" label="label" value="value" /> 
						</html:select>
					</div>
					<div class="col-md-2">
						<label for="CGPAN">CGPAN</label>
						<html:text property="claimPaymentCGPAN" styleClass="form-control" name="claimPaymentReportForm" maxlength="20"
							onchange="return getPromoter();" styleId="claimPaymentCGPAN"/>
					</div>

					<div class="col-md-2">
						<label for="Promoter Nam">Promoter Name</label>
						<html:select styleClass="form-control" property="claimPaymentPromoterName"							
									 name="claimPaymentReportForm" styleId="claimPaymentPromoterName">
						<html:option value="0">Select</html:option>
						 <html:optionsCollection property="claimPaymentPromoterValues"
								name="claimPaymentReportForm" label="label" value="value" />
						</html:select> 
					</div>
					<div class="col-md-2">
						<label for="CLAIM STATUS">Claim Status</label><font color="#FF0000" size="2">*</font>
						<html:select styleClass="form-control"	property="claimPaymentClaimStatus"							
									 name="claimPaymentReportForm" styleId="claimPaymentClaimStatus">
									<html:option value="0">Select</html:option>
									<html:option value="ALL">ALL</html:option>
									<html:option value="settled">Settled</html:option>
									<html:option value="Return">Returned</html:option>
									<html:option value="lodged">Lodged</html:option>
									<%-- <html:option value="Recovered">Recovered</html:option> --%>
						</html:select>
					</div>
					<div class="col-md-2">
						<label for="MLI_ID">MLI ID</label>
						<html:text property="claimPaymentMLIId" styleClass="form-control" name="claimPaymentReportForm" maxlength="12"
						      onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)" styleId="claimPaymentMLIId"/>
					</div>
					
					<div class="col-md-2">
						<label for="Claim Reference Number">Claim Reference Number</label>
						<html:text property="claimPaymentClaimRefNo" styleClass="form-control" name="claimPaymentReportForm" maxlength="20"
						 styleId="claimPaymentClaimRefNo"/>
					</div>

					<div class="col-md-2">
						<label for="Claim Start Date">From Date</label><font color="#FF0000" size="2">*</font>
						<%
						String claimPaymentFromDate = (String)request.getAttribute("claimPaymentFromDate");
						if (claimPaymentFromDate != null && !claimPaymentFromDate.equals("")){ %>
						<input type="text" id="claimPaymentFromDate" class="form-control clsDate"
							   name="claimPaymentFromDate" aria-describedby="emailHelp" autocomplete="off"
							   placeholder="From Date" onclick="setDateValue(id)" value="<%=claimPaymentFromDate%>"
				    		   style="background-color: #fff;">
				       <%}else { %>
				       <input type="text" id="claimPaymentFromDate" class="form-control clsDate"
							   name="claimPaymentFromDate" aria-describedby="emailHelp" autocomplete="off"
							   placeholder="From Date" onclick="setDateValue(id)"
				    		   style="background-color: #fff;">
				       <%} %> 	 	   		   
					</div>
				<div class="col-md-2">
						<label for="Claim End Date">To Date</label><font color="#FF0000" size="2">*</font>
						<%
						String claimPaymentToDate = (String)request.getAttribute("claimPaymentToDate");
						if (claimPaymentToDate != null && !claimPaymentToDate.equals("")){ %>
						<input type="text" id="claimPaymentToDate" class="form-control clsDate"
							   name="claimPaymentToDate" aria-describedby="emailHelp" autocomplete="off"
							   placeholder="To Date" onclick="setDateValue(id)" value="<%=claimPaymentToDate%>"
				    		   style="background-color: #fff;">
				       <%}else { %>	
				       <input type="text" id="claimPaymentToDate" class="form-control clsDate"
							   name="claimPaymentToDate" aria-describedby="emailHelp" autocomplete="off"
							   placeholder="To Date" onclick="setDateValue(id)"
				    		   style="background-color: #fff;">
				      <%} %>  	      
					</div>
					<div class="col-md-2">
						<label for="Promoter ITPAN">Promoter ITPAN</label>
						<html:text property="claimPaymentPromoterITPAN" styleClass="form-control" name="claimPaymentReportForm" maxlength="20"
						            styleId="claimPaymentPromoterITPAN"/>
					</div>
					<div class="col-md-2">
						<label for="CLAIM STATUS">Claim Type</label><font color="#FF0000" size="2">*</font>
						<html:select styleClass="form-control"	property="claimPaymentClaimType"							
									 name="claimPaymentReportForm" styleId="claimPaymentClaimType">
									<html:option value="0">Select</html:option>
									<html:option value="First Claim">First Claim</html:option>
									<html:option value="Second Claim">Second Claim</html:option>
						</html:select>
					</div>
					<div class="col-md-4" style="padding-top: 24px;">

						<button type="button" class="btn btn-primary" id="btnSearch" onclick="return getClaimPaymentDetails()">Search</button>
						&nbsp;&nbsp;
						<button type="button" class="btn btn-primary" id="btnClear" onclick="return clearData()">Clear</button>
						&nbsp;&nbsp;
						<button type="button" class="btn btn-primary" id="btnExportToExcel" onclick="return exportTableData()">Export to Excel</button>
					</div>
				</div>
			</div> 
			
			</div>
			
			            
						
			  <div class="row" id="isdisplay" style="display:block">
					<div class="col-lg-12" style="margin-top: 10px;overflow: auto;">
						<html:hidden property="message" name="claimPaymentReportForm" styleId="message" />
						<html:hidden property="claimPaymentLoginType" name="claimPaymentReportForm" styleId="claimPaymentLoginType" />
						<html:hidden property="claimPaymentisSearchClicked" name="claimPaymentReportForm" styleId="claimPaymentisSearchClicked" />
						<html:hidden property="claimPaymentListCount" name="claimPaymentReportForm" styleId="claimPaymentListCount" />
						<table id="userInfo" class="table table-striped" id="userInfo"
							style="font-size: 10px;">
							<thead>
								<tr>
									<th scope="col">Sr.No.</th>
									<th scope="col">Bank Name</th>
									<th scope="col">Zone Name</th>
									<th scope="col">Member ID</th>
									<th scope="col">CGPAN</th>
								    <th scope="col">Unit Name</th>
									<th scope="col">Claim Reference Number</th>
									<th scope="col">Promoter Name</th>
									<th scope="col">Promoter ITPAN</th>
									<th scope="col">Approved Amount</th>
									<th scope="col">Revised Outstanding Amount as on NPA</th>
									<th scope="col">Amount Recovered After NPA</th>
									<th scope="col">Net Outstanding Amount</th>
									<th scope="col">Amount Claimed By MLI</th>
									<th scope="col">Claim Eligible Amount</th>
									<th scope="col">Amount Payable In First Installment</th>
									<th scope="col">ASF Deducted Amount</th>
									<th scope="col">ASF Refundable Amount</th>
									<th scope="col">Net Paid Amount</th>
									<th scope="col">UTR Number</th>
									<th scope="col">Account Number</th>
									<th scope="col">Claim Approved Date</th>
									<th scope="col">Claim Payment Date</th>
									<th scope="col">Status</th>
								</tr>
							</thead>
							<tbody>
								<%
									if (request.getAttribute("claimPaymentList") != null) {
											ArrayList<ClaimPaymentTableData> list = (ArrayList)request.getAttribute("claimPaymentList");
											Iterator<ClaimPaymentTableData> iterator = list.iterator();
											while (iterator.hasNext()) {
												ClaimPaymentTableData claimPaymentData = iterator.next();
								%>
								<tr>
									<td scope="row">
										<%=claimPaymentData.getClaimPaymentSrNo()%>
										<input type="hidden" id="claimPaymentSrNo" name="claimPaymentSrNo" value="<%=claimPaymentData.getClaimPaymentSrNo()%>"/>
									</td>
									<td>
										<%=claimPaymentData.getMLIName()%>
										<input type="hidden" id="MLIName" name="MLIName" value="<%=claimPaymentData.getMLIName()%>"/>
									</td>
									<td>
										<%=claimPaymentData.getZoneName() %>
										<input type="hidden" id="zoneName" name="zoneName" value="<%=claimPaymentData.getZoneName()%>"/>
									</td>
									<td>
										<%=claimPaymentData.getMemberId() %>
										<input type="hidden" id="memberId" name="memberId" value="<%=claimPaymentData.getMemberId()%>"/>
									</td>
									<td>
										<%=claimPaymentData.getClaimPaymentCGPAN() %>
										<input type="hidden" id="claimPaymentCGPAN" name="claimPaymentCGPAN" value="<%=claimPaymentData.getClaimPaymentCGPAN()%>"/>
									</td>
									<td>
										<%=claimPaymentData.getClaimPaymentRefNumber() %>
										<input type="hidden" id="claimPaymentRefNumber" name="claimPaymentRefNumber" value="<%=claimPaymentData.getClaimPaymentRefNumber()%>"/>
									</td>
									<td>
										<%=claimPaymentData.getClaimPaymentPromoterName() %>
										<input type="hidden" id="claimPaymentPromoterName" name="claimPaymentPromoterName" value="<%=claimPaymentData.getClaimPaymentPromoterName()%>"/>
									</td>
									<td>
										<%=claimPaymentData.getClaimPaymentPromoterITPAN() %>
										<input type="hidden" id="claimPaymentPromoterITPAN" name="claimPaymentPromoterITPAN" value="<%=claimPaymentData.getClaimPaymentPromoterITPAN()%>"/>
									</td>
									<td>
										<%=claimPaymentData.getClaimPaymentSSIUnitName() %>
										<input type="hidden" id="claimPaymentSSIUnitName" name="claimPaymentSSIUnitName" value="<%=claimPaymentData.getClaimPaymentSSIUnitName()%>"/>
									</td>
									<td>
										<%=claimPaymentData.getClaimPaymentApprovedAmt()%>
										<input type="hidden" id="claimPaymentApprovedAmt" name="claimPaymentApprovedAmt" value="<%=claimPaymentData.getClaimPaymentApprovedAmt()%>"/>
									</td>
									<td>
										<%=claimPaymentData.getClaimPaymentRevisedOutstandingAmountAsOnNPA()%>
										<input type="hidden" id="claimPaymentRevisedOutstandingAmountAsOnNPA" name="claimPaymentRevisedOutstandingAmountAsOnNPA" value="<%=claimPaymentData.getClaimPaymentRevisedOutstandingAmountAsOnNPA()%>"/>
									</td>
									<td>
										<%=claimPaymentData.getClaimPaymentAmountRecoveredAfterNPA() %>
										<input type="hidden" id="claimPaymentAmountRecoveredAfterNPA" name="claimPaymentAmountRecoveredAfterNPA" value="<%=claimPaymentData.getClaimPaymentAmountRecoveredAfterNPA()%>"/>
									</td>
									<td>
										<%=claimPaymentData.getClaimPaymentNetOutstandingAmount()%>
										<input type="hidden" id="claimPaymentNetOutstandingAmount" name="claimPaymentNetOutstandingAmount" value="<%=claimPaymentData.getClaimPaymentNetOutstandingAmount()%>"/>
									</td>
									<td>
										<%=claimPaymentData.getClaimPaymentAmountClaimedByMLI() %>
										<input type="hidden" id="claimPaymentAmountClaimedByMLI" name="claimPaymentAmountClaimedByMLI" value="<%=claimPaymentData.getClaimPaymentAmountClaimedByMLI()%>"/>
									</td>
									<td>
										<%=claimPaymentData.getClaimPaymentClaimEligibleAmount() %>
										<input type="hidden" id="claimPaymentClaimEligibleAmount" name="claimPaymentClaimEligibleAmount" value="<%=claimPaymentData.getClaimPaymentClaimEligibleAmount()%>"/>
									</td>
									<td>
										<%=claimPaymentData.getClaimPaymentAmountPaybleInFirstInstalment() %>
										<input type="hidden" id="claimPaymentAmountPaybleInFirstInstalment" name="claimPaymentAmountPaybleInFirstInstalment" value="<%=claimPaymentData.getClaimPaymentAmountPaybleInFirstInstalment()%>"/>
									</td>
									<td>
										<%=claimPaymentData.getClaimPaymentASFDeductedAmount() %>
										<input type="hidden" id="claimPaymentASFDeductedAmount" name="claimPaymentASFDeductedAmount" value="<%=claimPaymentData.getClaimPaymentASFDeductedAmount()%>"/>
									</td>
									<td>
										<%=claimPaymentData.getClaimPaymentASFRefundableAmount() %>
										<input type="hidden" id="claimPaymentASFRefundableAmount" name="claimPaymentASFRefundableAmount" value="<%=claimPaymentData.getClaimPaymentASFRefundableAmount()%>"/>
									</td>
									<td>
										<%=claimPaymentData.getClaimPaymentNETPaidAmount() %>
										<input type="hidden" id="claimPaymentNETPaidAmount" name="claimPaymentNETPaidAmount" value="<%=claimPaymentData.getClaimPaymentNETPaidAmount()%>"/>
									</td>
									<td>
										<%=claimPaymentData.getClaimPaymentUTRNo() %>
										<input type="hidden" id="claimPaymentUTRNo" name="claimPaymentUTRNo" value="<%=claimPaymentData.getClaimPaymentUTRNo()%>"/>
									</td>
									<td>
										<%=claimPaymentData.getClaimPaymentAccountNo() %>
										<input type="hidden" id="claimPaymentAccountNo" name="claimPaymentAccountNo" value="<%=claimPaymentData.getClaimPaymentAccountNo()%>"/>
									</td>
									<td>
										<%=claimPaymentData.getClaimPaymentClaimApprovedDate() %>
										<input type="hidden" id="claimPaymentAccountNo" name="claimPaymentAccountNo" value="<%=claimPaymentData.getClaimPaymentClaimApprovedDate()%>"/>
									</td>
									<td>
										<%=claimPaymentData.getClaimPaymentClaimPaymentDate() %>
										<input type="hidden" id="claimPaymentClaimPaymentDate" name="claimPaymentClaimPaymentDate" value="<%=claimPaymentData.getClaimPaymentClaimPaymentDate()%>"/>
									</td>
									<td>
										<%=claimPaymentData.getClaimPaymentStatus() %>
										<input type="hidden" id="claimPaymentStatus" name="claimPaymentStatus" value="<%=claimPaymentData.getClaimPaymentStatus()%>"/>
									</td>
								</tr>
								<%
									}
										} else {
								%>
								<tr>
									<td align="center" colspan="21" style="font-size: medium;">No Data Found</td>
								</tr>
								<%
									}
								%>
							</tbody>
						</table>
					</div>
					</div>  
		</div></div>
	</html:form>
</body>
</html>
