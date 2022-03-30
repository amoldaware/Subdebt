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
<%@page import="com.cgtsi.actionform.MLICheckerReportForm"%>
<%@page import="com.cgtsi.actionform.MliCheckerTableData"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
	integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l"
	crossorigin="anonymous" />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link href="/SUBDEBT/css/StyleSheet.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script src="https://code.jquery.com/ui/1.10.1/jquery-ui.js"></script>
<SCRIPT language="JavaScript" type="text/JavaScript" src="js/CGTSI.js"></SCRIPT>
<title>NPA Marking Report</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<style>
.hide { display: block;}
.show { display: block;}
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
    
    var bankMandateLoginType = document.getElementById('bankMandateLoginType').value; 
	if(bankMandateLoginType != null && bankMandateLoginType == "BRANCH")
	{
		$("#bankMandateMLIName option:eq(1)").attr("selected","selected");
		$('#bankMandateMLIName option:not(:selected)').prop('disabled', true);
		var bankMandateMLIId = $("#bankMandateMLIName option:eq(1)").val().substr(0,12).trim();
		$("#bankMandateMLIId").val(bankMandateMLIId).attr('readonly', 'readonly');
	}
	
	var bankMandateisSearchClicked = document.getElementById('bankMandateisSearchClicked').value;
	var bankMandatelistCount = document.getElementById('bankMandatelistCount').value;
	
	if(bankMandateisSearchClicked == "")
	{ $("#btnExportToExcel").hide();$("#isdisplay").css("display", "none");}
	else
	{
		$("#btnExportToExcel").show();$("#isdisplay").css("display", "block");
		if(bankMandatelistCount == "1")
		{ $("#btnExportToExcel").hide();} 
	}
	$("#bankMandatelistCount").val('0');$("#bankMandateisSearchClicked").val('');
});
function getBankMandateDetails()
{
	var countErr = 0;
	var bankMandateisSearchClicked = "Clicked"; $("#bankMandateisSearchClicked").val(bankMandateisSearchClicked);
	var msg="<b>Please correct the following Error:</b><ul>";
	if ($("#bankMandateMLIName").val() == "0") 
	{
		$("#bankMandatelistCount").val('0');$("#bankMandateisSearchClicked").val('');
		$("#btnExportToExcel").hide();$("#isdisplay").css("display", "none");
   	 	msg=msg+"<li>Please Select MLI Name from Dropdown!!</li>"; countErr=1; 
        $("#errormsg").html(msg).css({
             "color" : "#ff6666",
             "display" : "block"
         });
        msg=msg+"</ul>";
        $(window).scrollTop(0);
        return;
    }else
    {   
    	if(countErr == 0){
    	$("#btnSearch").attr("disabled", "disabled");
		$("#btnSearch")[0].innerText = 'loading';
		setTimeout(function() {$("#btnSearch")[0].innerText = "Search";}, 8000); 		
    	document.bankMandateReportForm.target = "_self";
    	document.bankMandateReportForm.method = "POST";
    	document.bankMandateReportForm.action = "bankMandateReport.do?method=getBankMandateList";
    	document.bankMandateReportForm.submit(); }
    }	
}
function clearData()
{   $("#bankMandateMLIName option:eq(0)").attr("selected","selected");
	$("#bankMandateMLIId").val('');
	$("#bankMandateisSearchClicked").val('');
	$("#btnExportToExcel").hide();$("#isdisplay").css("display", "none");$("#errormsg").css("display", "none");
}
function exportTableData()
{   document.bankMandateReportForm.target = "_self";
	document.bankMandateReportForm.method = "POST";
	document.bankMandateReportForm.action = "bankMandateReport.do?method=getBankMandateEXLReport";
	document.bankMandateReportForm.submit();
}  
</script>
</head>
<body>
	<html:errors />
	<html:form name="bankMandateReportForm"
		type="com.cgtsi.actionform.MLICheckerReportForm"
		action="bankMandateReport.do?method=getBankMandateSummaryReport"
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
						<html:select styleClass="form-control"	property="bankMandateMLIName" name="bankMandateReportForm" styleId="bankMandateMLIName">
							<html:option value="0">Select</html:option>
							<html:optionsCollection property="bankMandateDtls" name="bankMandateReportForm" label="label" value="value" />
						</html:select>
					</div>

					<div class="col-md-2">
						<label for="MLI_ID">MLI ID</label>
						<html:text property="bankMandateMLIId" styleClass="form-control" name="bankMandateReportForm" maxlength="12"
						 onkeypress="return numbersOnly(this,event)" onkeyup="isValidNumber(this)" styleId="bankMandateMLIId"/>
					</div>
					
					<div class="col-md-4" style="padding-top: 24px;">

						<button type="button" class="btn btn-primary" id="btnSearch" onclick="return getBankMandateDetails()">Search</button>
						&nbsp;&nbsp;
						<button type="button" class="btn btn-primary" id="btnClear" onclick="return clearData()">Clear</button>
						&nbsp;&nbsp;
						<button type="button" class="btn btn-primary" id="btnExportToExcel" onclick="return exportTableData()">Export to Excel</button>
					</div>
				</div>
			</div> 
			
			</div>
		    <div class="row" id="isdisplay" style="display:none">
					<div class="col-lg-12" style="margin-top: 10px;overflow: auto;">
					    <html:hidden property="message" name="bankMandateReportForm" styleId="message" />
					    <html:hidden property="bankMandateLoginType" name="bankMandateReportForm" styleId="bankMandateLoginType" />
						<html:hidden property="bankMandateisSearchClicked" name="bankMandateReportForm" styleId="bankMandateisSearchClicked" />
			            <html:hidden property="bankMandatelistCount" name="bankMandateReportForm" styleId="bankMandatelistCount" />
			            
						<table id="userInfo" class="table table-striped" id="userInfo"
							style="font-size: 10px;">
							<thead>
								<tr>
									<th scope="col">Sr.No.</th>
									<th scope="col">Member ID</th>
									<th scope="col">MLI Name</th>
									<th scope="col">Zone Name</th>
									<th scope="col">Contact Number</th>
									<th scope="col">Mobile Number</th>
									<th scope="col">Email ID</th>
									<th scope="col">Name of Beneficiary</th>
									<th scope="col">Beneficiary Bank Name</th>
									<th scope="col">Account Type</th>
									<th scope="col">Branch Code</th>
									<th scope="col">MICR Code</th>
									<th scope="col">Account Number</th>
									<th scope="col">RTGS Number</th>
									<th scope="col">NEFT Number</th>
									<th scope="col">Checker Status</th>
									<th scope="col">CGTMSC Status</th>
									<th scope="col">CGTMSC Remark</th>
									
								</tr>
							</thead>
							<tbody>
								<%
									if (request.getAttribute("bankMandateList") != null && !request.getAttribute("bankMandateList").equals("[]")) {
											ArrayList<MliCheckerTableData> list = (ArrayList)request.getAttribute("bankMandateList");
											Iterator<MliCheckerTableData> iterator = list.iterator();
											while (iterator.hasNext()) {
												MliCheckerTableData bankMandateReportData = iterator.next();
								%>
								<tr>
									<td scope="row">
										<%=bankMandateReportData.getBankMandateSrNo()%>
										<input type="hidden" id="bankMandateSrNo" name="bankMandateSrNo" value="<%=bankMandateReportData.getBankMandateSrNo()%>"/>
									</td>
									<td>
										<%=bankMandateReportData.getBankMandateMemberId()%>
										<input type="hidden" id="bankMandateMemberId" name="bankMandateMemberId" value="<%=bankMandateReportData.getBankMandateMemberId()%>"/>
									</td>
									<td>
										<%=bankMandateReportData.getBankMandateMLIName()%>
										<input type="hidden" id="bankMandateMLIName" name="bankMandateMLIName" value="<%=bankMandateReportData.getBankMandateMLIName()%>"/>
									</td>
									<td>
										<%=bankMandateReportData.getBankMandateZoneName()%>
										<input type="hidden" id="bankMandateZoneName" name="bankMandateZoneName" value="<%=bankMandateReportData.getBankMandateZoneName()%>"/>
									</td>
									<td>
										<%=bankMandateReportData.getBankMandateContactNo()%>
										<input type="hidden" id="bankMandateContactNo" name="bankMandateContactNo" value="<%=bankMandateReportData.getBankMandateContactNo()%>"/>
									</td>
									<td>
										<%=bankMandateReportData.getBankMandateMobileNo()%>
										<input type="hidden" id="bankMandateMobileNo" name="bankMandateMobileNo" value="<%=bankMandateReportData.getBankMandateMobileNo()%>"/>
									</td>
									<td>
										<%=bankMandateReportData.getBankMandateEmailId()%>
										<input type="hidden" id="bankMandateEmailId" name="bankMandateEmailId" value="<%=bankMandateReportData.getBankMandateEmailId()%>"/>
									</td>
									<td>
										<%=bankMandateReportData.getBankMandateNameOfBeneficiary()%>
										<input type="hidden" id="bankMandateNameOfBeneficiary" name="bankMandateNameOfBeneficiary" value="<%=bankMandateReportData.getBankMandateNameOfBeneficiary()%>"/>
									</td>
									<td>
										<%=bankMandateReportData.getBankMandateBeneficiaryBankName()%>
										<input type="hidden" id="bankMandateBeneficiaryBankName" name="bankMandateBeneficiaryBankName" value="<%=bankMandateReportData.getBankMandateBeneficiaryBankName()%>"/>
									</td>
									<td>
										<%=bankMandateReportData.getBankMandateAccountType()%>
										<input type="hidden" id="bankMandateAccountType" name="bankMandateAccountType" value="<%=bankMandateReportData.getBankMandateAccountType()%>"/>
									</td>
									<td>
										<%=bankMandateReportData.getBankMandateBranchCode()%>
										<input type="hidden" id="bankMandateBranchCode" name="bankMandateBranchCode" value="<%=bankMandateReportData.getBankMandateBranchCode()%>"/>
									</td>
									<td>
										<%=bankMandateReportData.getBankMandateMICRCode()%>
										<input type="hidden" id="bankMandateMICRCode" name="bankMandateMICRCode" value="<%=bankMandateReportData.getBankMandateMICRCode()%>"/>
									</td>
									<td>
										<%=bankMandateReportData.getBankMandateAccountNo()%>
										<input type="hidden" id="bankMandateAccountNo" name="bankMandateAccountNo" value="<%=bankMandateReportData.getBankMandateAccountNo()%>"/>
									</td>
									<td>
										<%=bankMandateReportData.getBankMandateRTGSNo()%>
										<input type="hidden" id="bankMandateRTGSNo" name="bankMandateRTGSNo" value="<%=bankMandateReportData.getBankMandateRTGSNo()%>"/>
									</td>
									<td>
										<%=bankMandateReportData.getBankMandateNEFTNo()%>
										<input type="hidden" id="bankMandateNEFTNo" name="bankMandateNEFTNo" value="<%=bankMandateReportData.getBankMandateNEFTNo()%>"/>
									</td>
									<td>
										<%=bankMandateReportData.getBankMandateCheckerStatus()%>
										<input type="hidden" id="bankMandateCheckerStatus" name="bankMandateCheckerStatus" value="<%=bankMandateReportData.getBankMandateCheckerStatus()%>"/>
									</td>
									<td>
										<%=bankMandateReportData.getBankMandateCGTMSCStatus()%>
										<input type="hidden" id="bankMandateCGTMSCStatus" name="bankMandateCGTMSCStatus" value="<%=bankMandateReportData.getBankMandateCGTMSCStatus()%>"/>
									</td>
									<td>
										<%=bankMandateReportData.getBankMandateCGTMSCRemark()%>
										<input type="hidden" id="bankMandateCGTMSCRemark" name="bankMandateCGTMSCRemark" value="<%=bankMandateReportData.getBankMandateCGTMSCRemark()%>"/>
									</td>
								</tr>
								<%
									}
										} else {
								%>
								<tr>
									<td align="center" colspan="18" style="font-size: medium;">No Data Found</td>
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
