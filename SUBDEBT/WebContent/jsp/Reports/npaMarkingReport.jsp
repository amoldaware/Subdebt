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
<%@page import="com.cgtsi.actionform.NpaMarkingActionForm"%>
<%@page import="com.cgtsi.actionform.NPAReportTableData"%>
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
<title>NPA Marking Report</title>
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
	var loginType = document.getElementById('loginType').value; 
	if(loginType != null && loginType == "BRANCH")
	{
		$("#mliName option:eq(1)").attr("selected","selected");
		$('#mliName option:not(:selected)').prop('disabled', true);
		var mliId = $("#mliName option:eq(1)").val().substr(0,12).trim();
		$("#mliId").val(mliId).attr('readonly', 'readonly');
	}
	var isSearchClicked = document.getElementById('isSearchClicked').value;
	var listCount = document.getElementById('listCount').value;
	
	if(isSearchClicked == "")
	{
		$("#btnExportToExcel").hide();$("#isdisplay").css("display", "none");
	}
	else
	{
		$("#btnExportToExcel").show();$("#isdisplay").css("display", "block");
		if(listCount == "1")
		{
			$("#btnExportToExcel").hide();
		} 
	}
	$("#listCount").val('0');$("#isSearchClicked").val('');
});
function getPromoter() {
	document.npaMarkingReportForm.target = "_self";
	document.npaMarkingReportForm.method = "POST";
	document.npaMarkingReportForm.action = "npaClaimSummaryReport.do?method=getNPAMarkingDetailSummaryReport";
	document.npaMarkingReportForm.submit();
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
function getGuaranteeDetails()
{
	var countErr = 0;
	var guaranteeStartDate = $("#guaranteeStartDate").val().trim() , guaranteeEndDate = $("#guaranteeEndDate").val().trim();
	var isSearchClicked = "Clicked";
	$("#isSearchClicked").val(isSearchClicked);
	
	var msg="<b>Please correct the following Error:</b><ul>";
	if ($("#mliName").val() == "0") {
   	 	msg=msg+"<li>Please Select MLI Name from Dropdown!!</li>"; countErr=1; }
	if (guaranteeStartDate == "") {
   	 	msg=msg+"<li>From Date Can't be Blank!!</li>"; countErr=1; }
	if (guaranteeEndDate == "") {
   	 	msg=msg+"<li>To Date Can't be Blank!!</li>"; countErr=1; }
	if ($("#npaStatus").val() == "0") {
   	 	msg=msg+"<li>Please Select NPA Status from Dropdown!!</li>"; countErr=1; }
	if(countErr==1){
		$("#listCount").val('0');$("#isSearchClicked").val('');
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
    		document.npaMarkingReportForm.target = "_self";
    		document.npaMarkingReportForm.method = "POST";
    		document.npaMarkingReportForm.action = "npaClaimSummaryReport.do?method=getGuaranteeDetailSummaryReport&guaranteeStartDate="
    				+guaranteeStartDate+"&guaranteeEndDate="+guaranteeEndDate;
    		document.npaMarkingReportForm.submit();}
   	}	
}

function clearData()
{
	$("#mliName option:eq(0)").attr("selected","selected");
	$("#promoterName option:eq(0)").attr("selected","selected");
	$("#guranteeStatus option:eq(0)").attr("selected","selected");
	$("#npaStatus option:eq(0)").attr("selected","selected");
	$("#cgpan").val('');$("#mliId").val('');$("#promoter_itpan").val('');$("#guaranteeStartDate").val('');$("#guaranteeEndDate").val('');
	$("#isSearchClicked").val(''); $("#btnExportToExcel").hide();$("#isdisplay").css("display", "none");$("#errormsg").css("display", "none");
}

function exportTableData()
{
	document.npaMarkingReportForm.target = "_self";
	document.npaMarkingReportForm.method = "POST";
	document.npaMarkingReportForm.action = "npaClaimSummaryReport.do?method=getSummaryReport";
	document.npaMarkingReportForm.submit();
}
</script>
</head>
<body>
	<html:errors />
	<html:form name="npaMarkingReportForm"
		type="com.cgtsi.actionform.NPAReportForm"
		action="npaClaimSummaryReport.do?method=getNPAMarkingDetailSummaryReport"
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
						<html:select styleClass="form-control"	property="mliName" name="npaMarkingReportForm" styleId="mliName">
							<html:option value="0">Select</html:option>
							<html:optionsCollection property="mliDetals"
								name="npaMarkingReportForm" label="label" value="value" />
						</html:select>
					</div>
					<div class="col-md-2">
						<label for="CGPAN">CGPAN</label>
						<html:text property="cgpan" styleClass="form-control" name="npaMarkingReportForm" maxlength="20"
							onchange="return getPromoter();" styleId="cgpan"/>
					</div>

					<div class="col-md-2">
						<label for="Promoter Nam">Promoter Name</label>
						<html:select styleClass="form-control" property="promoterName"							
									 name="npaMarkingReportForm" styleId="promoterName">
						<html:option value="0">Select</html:option>
						<html:optionsCollection property="promoterValues"
								name="npaMarkingReportForm" label="label" value="value" />
						</html:select>
					</div>
					<div class="col-md-2">
						<label for="GUARANTEE  STATUS">Guarantee Status</label>
						<html:select styleClass="form-control"	property="guranteeStatus"							
									 name="npaMarkingReportForm" styleId="guranteeStatus">
									<html:option value="0">Select</html:option>
									<html:option value="NE">Application Lodge</html:option>
									<html:option value="AP">Approve</html:option>
									<html:option value="IP">Initiate Payment</html:option>
								</html:select>
					</div>
					<div class="col-md-2">
						<label for="Promoter ITPAN">Promoter ITPAN </label>
						<html:text property="promoter_itpan" styleClass="form-control" name="npaMarkingReportForm" maxlength="20"
						 styleId="promoter_itpan"/>
					</div>

					<div class="col-md-2">
						<label for="MLI_ID">MLI ID</label>
						<html:text property="mliId" styleClass="form-control" name="npaMarkingReportForm" maxlength="12"
						      onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)" styleId="mliId"/>
					</div>
					
					<div class="col-md-2">
						<label for="LOAN ACCOUNT NO">Loan Account Number</label>
						<html:text property="loanAccountNo" styleClass="form-control" name="npaMarkingReportForm" maxlength="20"
						 styleId="loanAccountNo"/>
					</div>

					<div class="col-md-2">
						<label for="Guarantee Start Date">From Date</label><font color="#FF0000" size="2">*</font>
						<%
						String guaranteeStartDate = (String)request.getAttribute("guaranteeStartDate");
						if (guaranteeStartDate != null && !guaranteeStartDate.equals("")){ %>
						<input type="text" id="guaranteeStartDate" class="form-control clsDate"
							   name="guaranteeStartDate" aria-describedby="emailHelp" autocomplete="off"
							   placeholder="From Date" onclick="setDateValue(id)" value="<%=guaranteeStartDate%>"
				    		   style="background-color: #fff;">
				       <%}else { %>
				       <input type="text" id="guaranteeStartDate" class="form-control clsDate"
							   name="guaranteeStartDate" aria-describedby="emailHelp" autocomplete="off"
							   placeholder="From Date" onclick="setDateValue(id)"
				    		   style="background-color: #fff;">
				       <%} %> 		   		   
					</div>
				<div class="col-md-2">
						<label for="Guarantee End Date">To Date</label><font color="#FF0000" size="2">*</font>
						<%
						String guaranteeEndDate = (String)request.getAttribute("guaranteeEndDate");
						if (guaranteeEndDate != null && !guaranteeEndDate.equals("")){ %>
						<input type="text" id="guaranteeEndDate" class="form-control clsDate"
							   name="guaranteeEndDate" aria-describedby="emailHelp" autocomplete="off"
							   placeholder="To Date" onclick="setDateValue(id)" value="<%=guaranteeEndDate%>"
				    		   style="background-color: #fff;">
				       <%}else { %>	
				       <input type="text" id="guaranteeEndDate" class="form-control clsDate"
							   name="guaranteeEndDate" aria-describedby="emailHelp" autocomplete="off"
							   placeholder="To Date" onclick="setDateValue(id)"
				    		   style="background-color: #fff;">	
				      <%} %> 		      
					</div>
					<div class="col-md-2">
						<label for="NPA  STATUS">NPA Status</label><font color="#FF0000" size="2">*</font>
						<html:select styleClass="form-control" property="npaStatus"							
									 name="npaMarkingReportForm" styleId="npaStatus">
									<html:option value="0">Select</html:option>
									<html:option value="All">All</html:option>
									<html:option value="NPA">NPA Only</html:option>
					   </html:select>
					</div>
					<div class="col-md-4" style="padding-top: 24px;">

						<button type="button" class="btn btn-primary" id="btnSearch" onclick="return getGuaranteeDetails()">Search</button>
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
						<html:hidden property="message" name="npaMarkingReportForm" styleId="message" />
						<html:hidden property="loginType" name="npaMarkingReportForm" styleId="loginType" />
						<html:hidden property="isSearchClicked" name="npaMarkingReportForm" styleId="isSearchClicked" />
						<html:hidden property="listCount" name="npaMarkingReportForm" styleId="listCount" />
						<table id="userInfo" class="table table-striped" id="userInfo"
							style="font-size: 10px;">
							<thead>
								<tr>
									<th scope="col">Sr.No.</th>
									<th scope="col">MLI Name</th>
									<th scope="col">Member ID</th>
									<th scope="col">Zone Name</th>
									<th scope="col">SSI Unit Name</th>
									<th scope="col">Loan Account Number</th>
									<th scope="col">Loan Sanction Date</th>
									<th scope="col">Promoter Name</th>
									<th scope="col">Promoter ITPAN</th>
									<th scope="col">Guarantee Amount</th>
									<th scope="col">Guarantee Start Date</th>
									<th scope="col">Expiry Date</th>
									<th scope="col">Guarantee Status</th>
									<th scope="col">NPA Date</th>
									<th scope="col">NPA Marked Date</th>
									<th scope="col">NPA Upgraded On</th>
									<th scope="col">NPA Reason</th>
									<th scope="col">NPA Outstanding Principal Amount</th>
									<th scope="col">Claim Lodged Date</th>
									<th scope="col">Claim Status</th>
									<th scope="col">Claim Approved Date</th>
								</tr>
							</thead>
							<tbody>
								<%
									if (request.getAttribute("mliReportList") != null ) {
											ArrayList<NPAReportTableData> list = (ArrayList)request.getAttribute("mliReportList");
											Iterator<NPAReportTableData> iterator = list.iterator();
											while (iterator.hasNext()) {
												NPAReportTableData npaReportData = iterator.next();
								%>
								<tr>
									<td scope="row">
										<%=npaReportData.getSrNo()%>
										<input type="hidden" id="SrNo" name="SrNo" value="<%=npaReportData.getSrNo()%>"/>
									</td>
									<td>
										<%=npaReportData.getMLIName()%>
										<input type="hidden" id="mliName" name="mliName" value="<%=npaReportData.getMLIName()%>"/>
									</td>
									<td>
										<%=npaReportData.getMemberId() %>
										<input type="hidden" id="memberId" name="memberId" value="<%=npaReportData.getMemberId()%>"/>
									</td>
									<td>
										<%=npaReportData.getZoneName() %>
										<input type="hidden" id="zoneName" name="zoneName" value="<%=npaReportData.getZoneName()%>"/>
									</td>
									<td>
										<%=npaReportData.getSSIUnitName() %>
										<input type="hidden" id="SSIUnitName" name="SSIUnitName" value="<%=npaReportData.getSSIUnitName()%>"/>
									</td>
									<td>
										<%=npaReportData.getLoanAccountNo() %>
										<input type="hidden" id="LoanAccountNo" name="LoanAccountNo" value="<%=npaReportData.getLoanAccountNo()%>"/>
									</td>
									<td>
										<%=npaReportData.getLoanSanctionDate() %>
										<input type="hidden" id="LoanSanctionDate" name="LoanSanctionDate" value="<%=npaReportData.getLoanSanctionDate()%>"/>
									</td>
									<td>
										<%=npaReportData.getPromoterName() %>
										<input type="hidden" id="promoterName" name="promoterName" value="<%=npaReportData.getPromoterName()%>"/>
									</td>
									<td>
										<%=npaReportData.getPromoterITPAN() %>
										<input type="hidden" id="promoterITPAN" name="promoterITPAN" value="<%=npaReportData.getPromoterITPAN()%>"/>
									</td>
									<td>
										<%=npaReportData.getGuaranteeAmt() %>
										<input type="hidden" id="guaranteeAmt" name="guaranteeAmt" value="<%=npaReportData.getGuaranteeAmt()%>"/>
									</td>
									<td>
										<%=npaReportData.getGuaranteeStartDate()%>
										<input type="hidden" id="guaranteeStartDate" name="guaranteeStartDate" value="<%=npaReportData.getGuaranteeStartDate()%>"/>
									</td>
									<td>
										<%=npaReportData.getExpiryDate()%>
										<input type="hidden" id="expiryDate" name="expiryDate" value="<%=npaReportData.getExpiryDate()%>"/>
									</td>
									<td>
										<%=npaReportData.getGuaranteeStatus() %>
										<input type="hidden" id="guaranteeStatus" name="guaranteeStatus" value="<%=npaReportData.getGuaranteeStatus()%>"/>
									</td>
									<td>
										<%=npaReportData.getNPADate()%>
										<input type="hidden" id="NPADate" name="NPADate" value="<%=npaReportData.getNPADate()%>"/>
									</td>
									<td>
										<%=npaReportData.getNPAMarkedDate() %>
										<input type="hidden" id="NPAMarkedDate" name="NPAMarkedDate" value="<%=npaReportData.getNPAMarkedDate()%>"/>
									</td>
									<td>
										<%=npaReportData.getNPAUpgradedOn() %>
										<input type="hidden" id="NPAUpgradedOn" name="NPAUpgradedOn" value="<%=npaReportData.getNPAUpgradedOn()%>"/>
									</td>
									<td>
										<%=npaReportData.getNPAReason() %>
										<input type="hidden" id="NPAReason" name="NPAReason" value="<%=npaReportData.getNPAReason()%>"/>
									</td>
									<td>
										<%=npaReportData.getNPAOutstandingPrincipalAmount() %>
										<input type="hidden" id="NPAOutstandingPrincipalAmount" name="NPAOutstandingPrincipalAmount" value="<%=npaReportData.getNPAOutstandingPrincipalAmount()%>"/>
									</td>
									<td>
										<%=npaReportData.getCLAIMLodgedDate() %>
										<input type="hidden" id="CLAIMLodgedDate" name="CLAIMLodgedDate" value="<%=npaReportData.getCLAIMLodgedDate()%>"/>
									</td>
									<td>
										<%=npaReportData.getCLAIMStatus() %>
										<input type="hidden" id="CLAIMStatus" name="CLAIMStatus" value="<%=npaReportData.getCLAIMStatus()%>"/>
									</td>
									<td>
										<%=npaReportData.getCLAIMApprovedDate()%>
										<input type="hidden" id="CLAIMApprovedDate" name="CLAIMApprovedDate" value="<%=npaReportData.getCLAIMStatus()%>"/>
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
