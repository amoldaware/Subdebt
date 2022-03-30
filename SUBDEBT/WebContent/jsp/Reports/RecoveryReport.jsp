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
	var recLoginTypel = document.getElementById('recLoginTypel').value; 
	if(recLoginTypel != null && recLoginTypel == "BRANCH")
	{
		$("#recMLIName option:eq(1)").attr("selected","selected");
		$('#recMLIName option:not(:selected)').prop('disabled', true);
		var recMLIId = $("#recMLIName option:eq(1)").val().substr(0,12).trim();
		$("#recMLIId").val(recMLIId).attr('readonly', 'readonly');
	}
	var isSearchClicked = document.getElementById('isSearchClicked').value;
	var recListCount = document.getElementById('recListCount').value;	
	if(isSearchClicked == "")
	{
		$("#btnExportToExcel").hide();$("#isdisplay").css("display", "none");
	}
	else
	{
		$("#btnExportToExcel").show();$("#isdisplay").css("display", "block");
		if(recListCount == "1")
		{
			$("#btnExportToExcel").hide();
		} 
	}
	$("#recListCount").val('0');$("#isSearchClicked").val('');
});
function getPromoter() {
	document.recoveryReportForm.target = "_self";
	document.recoveryReportForm.method = "POST";
	document.recoveryReportForm.action = "recoveryReport.do?method=getRecoveryReport";
	document.recoveryReportForm.submit();
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
function getRecoveryDetails()
{
	var isSearchClicked = "Clicked";
	$("#isSearchClicked").val(isSearchClicked);

	var countErr = 0;
	var recFromDate = $("#recFromDate").val().trim() , recToDate = $("#recToDate").val().trim();
	var msg="<b>Please correct the following Error:</b><ul>";
	if ($("#recMLIName").val() == "0") {
   	 	msg=msg+"<li>Please Select MLI Name from Dropdown!!</li>"; countErr=1; }
	if (recFromDate == "") {
   	 	msg=msg+"<li>From Date Can't be Blank!!</li>"; countErr=1; }
	if (recToDate == "") {
   	 	msg=msg+"<li>To Date Can't be Blank!!</li>"; countErr=1; }
    if ($("#recClaimStatus").val() == "0") {
   	 	msg=msg+"<li>Please Select Claim Status from Dropdown!!</li>"; countErr=1; }
	if(countErr==1){
		$("#recListCount").val('0');$("#isSearchClicked").val('');
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
    		document.recoveryReportForm.target = "_self";
    		document.recoveryReportForm.method = "POST";
    		document.recoveryReportForm.action = "recoveryReport.do?method=getClaimRecoveryDetailReport&recFromDate="
    				+recFromDate+"&recToDate="+recToDate;
    		document.recoveryReportForm.submit();}
   	}	
}
function clearData()
{
	$("#recMLIName option:eq(0)").attr("selected","selected");
	$("#recPromoterName option:eq(0)").attr("selected","selected");
	$("#recClaimStatus option:eq(0)").attr("selected","selected");
	$("#recCGPAN").val('');$("#recMLIId").val('');$("#recClaimRefNo").val('');$("#recPromoterITPAN").val('');$("#recFromDate").val('');$("#recToDate").val('');
	$("#isSearchClicked").val(''); $("#btnExportToExcel").hide();$("#isdisplay").css("display", "none");$("#errormsg").css("display", "none");
}
function exportTableData()
{
	document.recoveryReportForm.target = "_self";
	document.recoveryReportForm.method = "POST";
	document.recoveryReportForm.action = "recoveryReport.do?method=getClaimRecoveryExportToExcel";
	document.recoveryReportForm.submit();
} 
</script>
</head>
<body>
	<html:errors />
	<html:form name="recoveryReportForm"
		type="com.cgtsi.actionform.MLIRecoveryReportForm"
		action="recoveryReport.do?method=getRecoveryReport"
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
						<html:select styleClass="form-control"	property="recMLIName" name="recoveryReportForm" styleId="recMLIName">
							<html:option value="0">Select</html:option>
						    <html:optionsCollection property="mliRecList"
								name="recoveryReportForm" label="label" value="value" /> 
						</html:select>
					</div>
					<div class="col-md-2">
						<label for="CGPAN">CGPAN</label>
						<html:text property="recCGPAN" styleClass="form-control" name="recoveryReportForm" maxlength="20"
							onchange="return getPromoter();" styleId="recCGPAN"/>
					</div>

					<div class="col-md-2">
						<label for="Promoter Nam">Promoter Name</label>
						<html:select styleClass="form-control" property="recPromoterName"							
									 name="recoveryReportForm" styleId="recPromoterName">
						<html:option value="0">Select</html:option>
						 <html:optionsCollection property="promoterValues"
								name="recoveryReportForm" label="label" value="value" />
						</html:select> 
					</div>
					<div class="col-md-2">
						<label for="CLAIM STATUS">Claim Status</label><font color="#FF0000" size="2">*</font>
						<html:select styleClass="form-control"	property="recClaimStatus"							
									 name="recoveryReportForm" styleId="recClaimStatus">
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
						<html:text property="recMLIId" styleClass="form-control" name="recoveryReportForm" maxlength="12"
						      onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)" styleId="recMLIId"/>
					</div>
					
					<div class="col-md-2">
						<label for="Claim Reference Number">Claim Reference Number</label>
						<html:text property="recClaimRefNo" styleClass="form-control" name="recoveryReportForm" maxlength="20"
						 styleId="recClaimRefNo"/>
					</div>

					<div class="col-md-2">
						<label for="Claim Start Date">From Date</label><font color="#FF0000" size="2">*</font>
						<%
						String recFromDate = (String)request.getAttribute("recFromDate");
						if (recFromDate != null && !recFromDate.equals("")){ %>
						<input type="text" id="recFromDate" class="form-control clsDate"
							   name="recFromDate" aria-describedby="emailHelp" autocomplete="off"
							   placeholder="From Date" onclick="setDateValue(id)" value="<%=recFromDate%>"
				    		   style="background-color: #fff;">
				       <%}else { %>
				       <input type="text" id="recFromDate" class="form-control clsDate"
							   name="recFromDate" aria-describedby="emailHelp" autocomplete="off"
							   placeholder="From Date" onclick="setDateValue(id)"
				    		   style="background-color: #fff;">
				       <%} %> 	 	   		   
					</div>
				<div class="col-md-2">
						<label for="Claim End Date">To Date</label><font color="#FF0000" size="2">*</font>
						<%
						String recToDate = (String)request.getAttribute("recToDate");
						if (recToDate != null && !recToDate.equals("")){ %>
						<input type="text" id="recToDate" class="form-control clsDate"
							   name="recToDate" aria-describedby="emailHelp" autocomplete="off"
							   placeholder="To Date" onclick="setDateValue(id)" value="<%=recToDate%>"
				    		   style="background-color: #fff;">
				       <%}else { %>	
				       <input type="text" id="recToDate" class="form-control clsDate"
							   name="recToDate" aria-describedby="emailHelp" autocomplete="off"
							   placeholder="To Date" onclick="setDateValue(id)"
				    		   style="background-color: #fff;">
				      <%} %>  	      
					</div>
					<div class="col-md-2">
						<label for="Promoter ITPAN">Promoter ITPAN</label>
						<html:text property="recPromoterITPAN" styleClass="form-control" name="recoveryReportForm" maxlength="20"
						           styleId="recPromoterITPAN"/>
					</div>
					<div class="col-md-4" style="padding-top: 24px;">

						<button type="button" class="btn btn-primary" id="btnSearch" onclick="return getRecoveryDetails()">Search</button>
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
						<html:hidden property="message" name="recoveryReportForm" styleId="message" />
						<html:hidden property="recLoginTypel" name="recoveryReportForm" styleId="recLoginTypel" />
						<html:hidden property="isSearchClicked" name="recoveryReportForm" styleId="isSearchClicked" />
						<html:hidden property="recListCount" name="recoveryReportForm" styleId="recListCount" />
						<table id="userInfo" class="table table-striped" id="userInfo"
							style="font-size: 10px;">
							<thead>
								<tr>
									<th scope="col">Sr.No.</th>
									<th scope="col">MLI Name</th>
									<th scope="col">Member ID</th>
									<th scope="col">CGPAN</th>
									<th scope="col">Claim Reference Number</th>
									<th scope="col">Promoter Name</th>
									<th scope="col">Promoter ITPAN</th>
									<th scope="col">Unit Name</th>
									<th scope="col">Pay ID</th>
									<th scope="col">Amount</th>
									<th scope="col">Payment Initiate Date</th>
									<th scope="col">Payment Date</th>
									<th scope="col">Payment Credited Date</th>
									<th scope="col">Appropriated Status</th>
									<th scope="col">Total Remetted Amount</th>
								</tr>
							</thead>
							<tbody>
								<%
									if (request.getAttribute("recReportList") != null) {
											ArrayList<MLIRecoveryTableData> list = (ArrayList)request.getAttribute("recReportList");
											Iterator<MLIRecoveryTableData> iterator = list.iterator();
											while (iterator.hasNext()) {
												MLIRecoveryTableData recReportData = iterator.next();
								%>
								<tr>
									<td scope="row">
										<%=recReportData.getRecSrNo()%>
										<input type="hidden" id="recSrNo" name="recSrNo" value="<%=recReportData.getRecSrNo()%>"/>
									</td>
									<td>
										<%=recReportData.getRecMLIName()%>
										<input type="hidden" id="recMLIName" name="recMLIName" value="<%=recReportData.getRecMLIName()%>"/>
									</td>
									<td>
										<%=recReportData.getRecMemberId() %>
										<input type="hidden" id="recMemberId" name="recMemberId" value="<%=recReportData.getRecMemberId()%>"/>
									</td>
									<td>
										<%=recReportData.getRecCgpan() %>
										<input type="hidden" id="recCgpan" name="recCgpan" value="<%=recReportData.getRecCgpan()%>"/>
									</td>
									<td>
										<%=recReportData.getRecClaimRefNumber() %>
										<input type="hidden" id="recClaimRefNumber" name="recClaimRefNumber" value="<%=recReportData.getRecClaimRefNumber()%>"/>
									</td>
									<td>
										<%=recReportData.getRecPromoterName() %>
										<input type="hidden" id="recPromoterName" name="recPromoterName" value="<%=recReportData.getRecPromoterName()%>"/>
									</td>
									<td>
										<%=recReportData.getRecPromoterITPAN() %>
										<input type="hidden" id="recPromoterITPAN" name="recPromoterITPAN" value="<%=recReportData.getRecPromoterITPAN()%>"/>
									</td>
									<td>
										<%=recReportData.getRecSSIUnitName() %>
										<input type="hidden" id="recSSIUnitName" name="recSSIUnitName" value="<%=recReportData.getRecSSIUnitName()%>"/>
									</td>
									<td>
										<%=recReportData.getRecPayId() %>
										<input type="hidden" id="recPayId" name="recPayId" value="<%=recReportData.getRecPayId()%>"/>
									</td>
									<td>
										<%=recReportData.getRecAmount()%>
										<input type="hidden" id="recAmount" name="recAmount" value="<%=recReportData.getRecAmount()%>"/>
									</td>
									<td>
										<%=recReportData.getRecPaymentInitiateDate()%>
										<input type="hidden" id="recPaymentInitiateDate" name="recPaymentInitiateDate" value="<%=recReportData.getRecPaymentInitiateDate()%>"/>
									</td>
									<td>
										<%=recReportData.getRecPaymentDate() %>
										<input type="hidden" id="recPaymentDate" name="recPaymentDate" value="<%=recReportData.getRecPaymentDate()%>"/>
									</td>
									<td>
										<%=recReportData.getRecPaymentCreditedDate()%>
										<input type="hidden" id="recPaymentCreditedDate" name="recPaymentCreditedDate" value="<%=recReportData.getRecPaymentCreditedDate()%>"/>
									</td>
									<td>
										<%=recReportData.getRecAppropereatedStatus() %>
										<input type="hidden" id="recAppropereatedStatus" name="recAppropereatedStatus" value="<%=recReportData.getRecAppropereatedStatus()%>"/>
									</td>
									<td>
										<%=recReportData.getRecTotalRemettedAmount() %>
										<input type="hidden" id="recTotalRemettedAmount" name="recTotalRemettedAmount" value="<%=recReportData.getRecTotalRemettedAmount()%>"/>
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
