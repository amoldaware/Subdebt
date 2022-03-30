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
<%@page import="com.cgtsi.actionform.MLIRecoveryReportForm"%>
<%@page import="com.cgtsi.actionform.MLIRecoveryTableData"%>
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
	var claimLoginType = document.getElementById('claimLoginType').value; 
	if(claimLoginType != null && claimLoginType == "BRANCH")
	{
		$("#claimMLIName option:eq(1)").attr("selected","selected");
		$('#claimMLIName option:not(:selected)').prop('disabled', true);
		var claimMLIId = $("#claimMLIName option:eq(1)").val().substr(0,12).trim();
		$("#claimMLIId").val(claimMLIId).attr('readonly', 'readonly');
	}
	var claimisSearchClicked = document.getElementById('claimisSearchClicked').value;
	var claimListCount = document.getElementById('claimListCount').value;	
	
	if(claimisSearchClicked == "")
	{
		$("#btnExportToExcel").hide();$("#isdisplay").css("display", "none");
	}
	else
	{
		$("#btnExportToExcel").show();$("#isdisplay").css("display", "block");
		if(claimListCount == "1")
		{
			$("#btnExportToExcel").hide();
		} 
	}
	$("#claimListCount").val('0');$("#claimisSearchClicked").val('');
});
function getPromoter() {
	document.claimReportForm.target = "_self";
	document.claimReportForm.method = "POST";
	document.claimReportForm.action = "claimReport.do?method=getClaimReport";
	document.claimReportForm.submit();
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
function getClaimDetails()
{
	var claimisSearchClicked = "Clicked";
	$("#claimisSearchClicked").val(claimisSearchClicked);

	var countErr = 0;
	var claimFromDate = $("#claimFromDate").val().trim() , claimToDate = $("#claimToDate").val().trim();
	var msg="<b>Please correct the following Error:</b><ul>";
	if ($("#claimMLIName").val() == "0") {
   	 	msg=msg+"<li>Please Select MLI Name from Dropdown!!</li>"; countErr=1; }
	if (claimFromDate == "") {
   	 	msg=msg+"<li>From Date Can't be Blank!!</li>"; countErr=1; }
	if (claimToDate == "") {
   	 	msg=msg+"<li>To Date Can't be Blank!!</li>"; countErr=1; }
    if ($("#claimClaimStatus").val() == "0") {
   	 	msg=msg+"<li>Please Select Claim Status from Dropdown!!</li>"; countErr=1; }
	if(countErr==1){
		$("#claimListCount").val('0');$("#claimisSearchClicked").val('');
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
    		document.claimReportForm.target = "_self";
    		document.claimReportForm.method = "POST";
    		document.claimReportForm.action = "claimReport.do?method=getClaimDetailReport&claimFromDate="
    				+claimFromDate+"&claimToDate="+claimToDate;
    		document.claimReportForm.submit();}
   	}	
}
function clearData()
{
	$("#claimMLIName option:eq(0)").attr("selected","selected");
	$("#claimPromoterName option:eq(0)").attr("selected","selected");
	$("#claimClaimStatus option:eq(0)").attr("selected","selected");
	$("#claimCGPAN").val('');$("#claimMLIId").val('');$("#claimClaimRefNo").val('');$("#claimPromoterITPAN").val('');$("#claimFromDate").val('');$("#claimToDate").val('');
	$("#claimisSearchClicked").val(''); $("#btnExportToExcel").hide();$("#isdisplay").css("display", "none");$("#errormsg").css("display", "none");
}
function exportTableData()
{
	document.claimReportForm.target = "_self";
	document.claimReportForm.method = "POST";
	document.claimReportForm.action = "claimReport.do?method=claimExportToExcel";
	document.claimReportForm.submit();
}
</script>
</head>
<body>
	<html:errors />
	<html:form name="claimReportForm"
		type="com.cgtsi.actionform.MLIRecoveryReportForm"
		action="claimReport.do?method=getClaimReport"
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
						<html:select styleClass="form-control"	property="claimMLIName" name="claimReportForm" styleId="claimMLIName">
							<html:option value="0">Select</html:option>
						    <html:optionsCollection property="claimReportList"
								name="claimReportForm" label="label" value="value" /> 
						</html:select>
					</div>
					<div class="col-md-2">
						<label for="CGPAN">CGPAN</label>
						<html:text property="claimCGPAN" styleClass="form-control" name="claimReportForm" maxlength="20"
							onchange="return getPromoter();" styleId="claimCGPAN"/>
					</div>

					<div class="col-md-2">
						<label for="Promoter Nam">Promoter Name</label>
						<html:select styleClass="form-control" property="claimPromoterName"							
									 name="claimReportForm" styleId="claimPromoterName">
						<html:option value="0">Select</html:option>
						 <html:optionsCollection property="promoterValues"
								name="claimReportForm" label="label" value="value" />
						</html:select> 
					</div>
					<div class="col-md-2">
						<label for="CLAIM STATUS">Claim Status</label><font color="#FF0000" size="2">*</font>
						<html:select styleClass="form-control"	property="claimClaimStatus"							
									 name="claimReportForm" styleId="claimClaimStatus">
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
						<html:text property="claimMLIId" styleClass="form-control" name="claimReportForm" maxlength="12"
						      onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)" styleId="claimMLIId"/>
					</div>
					
					<div class="col-md-2">
						<label for="Claim Reference Number">Claim Reference Number</label>
						<html:text property="claimClaimRefNo" styleClass="form-control" name="claimReportForm" maxlength="20"
						 styleId="claimClaimRefNo"/>
					</div>

					<div class="col-md-2">
						<label for="Claim Start Date">From Date</label><font color="#FF0000" size="2">*</font>
						<%
						String claimFromDate = (String)request.getAttribute("claimFromDate");
						if (claimFromDate != null && !claimFromDate.equals("")){ %>
						<input type="text" id="claimFromDate" class="form-control clsDate"
							   name="claimFromDate" aria-describedby="emailHelp" autocomplete="off"
							   placeholder="From Date" onclick="setDateValue(id)" value="<%=claimFromDate%>"
				    		   style="background-color: #fff;">
				       <%}else { %>
				       <input type="text" id="claimFromDate" class="form-control clsDate"
							   name="claimFromDate" aria-describedby="emailHelp" autocomplete="off"
							   placeholder="From Date" onclick="setDateValue(id)"
				    		   style="background-color: #fff;">
				       <%} %> 	 	   		   
					</div>
				<div class="col-md-2">
						<label for="Claim End Date">To Date</label><font color="#FF0000" size="2">*</font>
						<%
						String claimToDate = (String)request.getAttribute("claimToDate");
						if (claimToDate != null && !claimToDate.equals("")){ %>
						<input type="text" id="claimToDate" class="form-control clsDate"
							   name="claimToDate" aria-describedby="emailHelp" autocomplete="off"
							   placeholder="To Date" onclick="setDateValue(id)" value="<%=claimToDate%>"
				    		   style="background-color: #fff;">
				       <%}else { %>	
				       <input type="text" id="claimToDate" class="form-control clsDate"
							   name="claimToDate" aria-describedby="emailHelp" autocomplete="off"
							   placeholder="To Date" onclick="setDateValue(id)"
				    		   style="background-color: #fff;">
				      <%} %>  	      
					</div>
					<div class="col-md-2">
						<label for="Promoter ITPAN">Promoter ITPAN</label>
						<html:text property="claimPromoterITPAN" styleClass="form-control" name="claimReportForm" maxlength="20"
						            styleId="claimPromoterITPAN"/>
					</div>
					<div class="col-md-4" style="padding-top: 24px;">

						<button type="button" class="btn btn-primary" id="btnSearch" onclick="return getClaimDetails()">Search</button>
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
						<html:hidden property="message" name="claimReportForm" styleId="message" />
						<html:hidden property="claimLoginType" name="claimReportForm" styleId="claimLoginType" />
						<html:hidden property="claimisSearchClicked" name="claimReportForm" styleId="claimisSearchClicked" />
						<html:hidden property="claimListCount" name="claimReportForm" styleId="claimListCount" />
						<table id="userInfo" class="table table-striped" id="userInfo"
							style="font-size: 10px;">
							<thead>
								<tr>
									<th scope="col">Sr.No.</th>
									<th scope="col">Bank Name</th>
									<th scope="col">Zone Name</th>
									<th scope="col">Member ID</th>
									<th scope="col">CGPAN</th>
									<th scope="col">Claim Reference Number</th>
									<th scope="col">Promoter Name</th>
									<th scope="col">Promoter ITPAN</th>
									<th scope="col">Unit Name</th>
									<th scope="col">Date of Claim Initiation</th>
									<th scope="col">Guarantee Application Status</th>
									<th scope="col">Guarantee Amount</th>
									<th scope="col">Outstanding Amount as on NPA Date</th>
									<th scope="col">Outstanding Amount as on Claim Date</th>
									<th scope="col">Claim Status</th>
									<th scope="col">Claim return date</th>
									<th scope="col">Claim return reason</th>
									<th scope="col">Claim Lodgement Date</th>
									<th scope="col">MLI Checker Remark</th>
									<th scope="col">CGTMSE Checker Remark</th>
								</tr>
							</thead>
							<tbody>
								<%
									if (request.getAttribute("claimReportList") != null) {
											ArrayList<MLIRecoveryTableData> list = (ArrayList)request.getAttribute("claimReportList");
											Iterator<MLIRecoveryTableData> iterator = list.iterator();
											while (iterator.hasNext()) {
												MLIRecoveryTableData claimReportData = iterator.next();
								%>
								<tr>
									<td scope="row">
										<%=claimReportData.getClaimSrNo()%>
										<input type="hidden" id="recSrNo" name="recSrNo" value="<%=claimReportData.getClaimSrNo()%>"/>
									</td>
									<td>
										<%=claimReportData.getClaimMLIName()%>
										<input type="hidden" id="recMLIName" name="recMLIName" value="<%=claimReportData.getClaimMLIName()%>"/>
									</td>
									<td>
										<%=claimReportData.getClaimZoneName() %>
										<input type="hidden" id="recMemberId" name="recMemberId" value="<%=claimReportData.getClaimZoneName()%>"/>
									</td>
									<td>
										<%=claimReportData.getClaimMemberId() %>
										<input type="hidden" id="recCgpan" name="recCgpan" value="<%=claimReportData.getClaimMemberId()%>"/>
									</td>
									<td>
										<%=claimReportData.getClaimCGPAN() %>
										<input type="hidden" id="recClaimRefNumber" name="recClaimRefNumber" value="<%=claimReportData.getClaimCGPAN()%>"/>
									</td>
									<td>
										<%=claimReportData.getClaimRefNumber() %>
										<input type="hidden" id="recPromoterName" name="recPromoterName" value="<%=claimReportData.getClaimRefNumber()%>"/>
									</td>
									<td>
										<%=claimReportData.getClaimPromoterName() %>
										<input type="hidden" id="recPromoterITPAN" name="recPromoterITPAN" value="<%=claimReportData.getClaimPromoterName()%>"/>
									</td>
									<td>
										<%=claimReportData.getClaimPromoterITPAN() %>
										<input type="hidden" id="recSSIUnitName" name="recSSIUnitName" value="<%=claimReportData.getClaimPromoterITPAN()%>"/>
									</td>
									<td>
										<%=claimReportData.getClaimSSIUnitName() %>
										<input type="hidden" id="recPayId" name="recPayId" value="<%=claimReportData.getClaimSSIUnitName()%>"/>
									</td>
									<td>
										<%=claimReportData.getClaimDateOfClaimIntiation()%>
										<input type="hidden" id="recAmount" name="recAmount" value="<%=claimReportData.getClaimDateOfClaimIntiation()%>"/>
									</td>
									<td>
										<%=claimReportData.getClaimGuaranteeStatus1()%>
										<input type="hidden" id="recPaymentInitiateDate" name="recPaymentInitiateDate" value="<%=claimReportData.getClaimGuaranteeStatus1()%>"/>
									</td>
									<td>
										<%=claimReportData.getClaimGuaranteeAmount() %>
										<input type="hidden" id="recPaymentDate" name="recPaymentDate" value="<%=claimReportData.getClaimGuaranteeAmount()%>"/>
									</td>
									<td>
										<%=claimReportData.getClaimOutstandingAmountAsOnNPADate()%>
										<input type="hidden" id="recPaymentCreditedDate" name="recPaymentCreditedDate" value="<%=claimReportData.getClaimOutstandingAmountAsOnNPADate()%>"/>
									</td>
									<td>
										<%=claimReportData.getClaimOutstandingAmountAsOnClaimDate() %>
										<input type="hidden" id="recAppropereatedStatus" name="recAppropereatedStatus" value="<%=claimReportData.getClaimOutstandingAmountAsOnClaimDate()%>"/>
									</td>
									<td>
										<%=claimReportData.getClaimStatus() %>
										<input type="hidden" id="recTotalRemettedAmount" name="recTotalRemettedAmount" value="<%=claimReportData.getClaimStatus()%>"/>
									</td>
									<td>
										<%=claimReportData.getClaimReturnDate() %>
										<input type="hidden" id="recTotalRemettedAmount" name="recTotalRemettedAmount" value="<%=claimReportData.getClaimReturnDate()%>"/>
									</td>
									<td>
										<%=claimReportData.getClaimReturnReason() %>
										<input type="hidden" id="recTotalRemettedAmount" name="recTotalRemettedAmount" value="<%=claimReportData.getClaimReturnReason()%>"/>
									</td>
									<td>
										<%=claimReportData.getClaimLodgementDate() %>
										<input type="hidden" id="recTotalRemettedAmount" name="recTotalRemettedAmount" value="<%=claimReportData.getClaimLodgementDate()%>"/>
									</td>
									<td>
										<%=claimReportData.getClaimMLICheckerRemark() %>
										<input type="hidden" id="recTotalRemettedAmount" name="recTotalRemettedAmount" value="<%=claimReportData.getClaimMLICheckerRemark()%>"/>
									</td>
									<td>
										<%=claimReportData.getCGTMSECheckerRemark() %>
										<input type="hidden" id="recTotalRemettedAmount" name="recTotalRemettedAmount" value="<%=claimReportData.getCGTMSECheckerRemark()%>"/>
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
