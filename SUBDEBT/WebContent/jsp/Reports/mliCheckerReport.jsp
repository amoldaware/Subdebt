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
	var mliCheckerisSearchClicked = document.getElementById('mliCheckerisSearchClicked').value;
	var mliCheckerlistCount = document.getElementById('mliCheckerlistCount').value;
	
	if(mliCheckerisSearchClicked == "")
	{
		$("#btnExportToExcel").hide();$("#isdisplay").css("display", "none");
	}
	else
	{
		$("#btnExportToExcel").show();$("#isdisplay").css("display", "block");
		if(mliCheckerlistCount == "1")
		{
			$("#btnExportToExcel").hide();
		} 
	}
	$("#mliCheckerlistCount").val('0');$("#mliCheckerisSearchClicked").val('');
});
function getMLICheckerDetails()
{
	var countErr = 0;
	var mliCheckerisSearchClicked = "Clicked";
	$("#mliCheckerisSearchClicked").val(mliCheckerisSearchClicked);
	
	var msg="<b>Please correct the following Error:</b><ul>";
	if ($("#checkerMLIName").val() == "0") 
	{
		$("#mliCheckerlistCount").val('0');$("#mliCheckerisSearchClicked").val('');
		$("#btnExportToExcel").hide();$("#isdisplay").css("display", "none");
   	 	msg=msg+"<li>Please Select MLI Name from Dropdown!!</li>"; countErr=1; 
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
    	document.mliCheckerReportForm.target = "_self";
    	document.mliCheckerReportForm.method = "POST";
    	document.mliCheckerReportForm.action = "mliCheckerReport.do?method=getNPAMLICheckerList";
    	document.mliCheckerReportForm.submit();}
   	}	
}

function clearData()
{
	$("#checkerMLIName option:eq(0)").attr("selected","selected");
	$("#checkerMLIId").val('');
	$("#mliCheckerisSearchClicked").val('');
	$("#approvalStatus option:eq(0)").attr("selected","selected");
	$("#btnExportToExcel").hide();$("#isdisplay").css("display", "none");$("#errormsg").css("display", "none");
}
function exportTableData()
{
	document.mliCheckerReportForm.target = "_self";
	document.mliCheckerReportForm.method = "POST";
	document.mliCheckerReportForm.action = "mliCheckerReport.do?method=getMLISummaryReport";
	document.mliCheckerReportForm.submit();
} 
</script>
</head>
<body>
	<html:errors />
	<html:form name="mliCheckerReportForm"
		type="com.cgtsi.actionform.MLICheckerReportForm"
		action="mliCheckerReport.do?method=getNPAMLICheckerSummaryReport"
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
						<html:select styleClass="form-control"	property="checkerMLIName" name="mliCheckerReportForm" styleId="checkerMLIName">
							<html:option value="0">Select</html:option>
							<html:optionsCollection property="mliDetails"
								name="mliCheckerReportForm" label="label" value="value" />
						</html:select>
					</div>

					<div class="col-md-2">
						<label for="MLI_ID">MLI ID</label>
						<html:text property="checkerMLIId" styleClass="form-control" name="mliCheckerReportForm" maxlength="12"
						 onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)" styleId="checkerMLIId"/>
					</div>
					
					<div class="col-md-2">
						<label for="Approval Status">Approval Status</label>
						<html:select styleClass="form-control" property="approvalStatus"							
									 name="mliCheckerReportForm" styleId="approvalStatus">
									<html:option value="0">Select</html:option>
									<html:option value="Approve">Approve</html:option>
									<html:option value="Reject">Reject</html:option>
					   </html:select>
					</div>
					<div class="col-md-4" style="padding-top: 24px;">

						<button type="button" class="btn btn-primary" id="btnSearch" onclick="return getMLICheckerDetails()">Search</button>
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
					    <html:hidden property="message" name="mliCheckerReportForm" styleId="message" />
						<html:hidden property="mliCheckerisSearchClicked" name="mliCheckerReportForm" styleId="mliCheckerisSearchClicked" />
			            <html:hidden property="mliCheckerlistCount" name="mliCheckerReportForm" styleId="mliCheckerlistCount" />
			            
						<table id="userInfo" class="table table-striped" id="userInfo"
							style="font-size: 10px;">
							<thead>
								<tr>
									<th scope="col">Sr.No.</th>
									<th scope="col">Bank Name</th>
									<th scope="col">Member ID</th>
									<th scope="col">Zone Name</th>
									<th scope="col">Checker First Name</th>
									<th scope="col">Checker Middle Name</th>
									<th scope="col">Checker Last Name</th>
									<th scope="col">Checker Employee Id</th>
									<th scope="col">Checker Designation</th>
									<th scope="col">Checker Phone Number</th>
									<th scope="col">Checker Email</th>
									<th scope="col">Checker User ID</th>
									<th scope="col">Hint Answer</th>
									<th scope="col">Status</th>
									<th scope="col">CGTMSE Checker Remark</th>
								</tr>
							</thead>
							<tbody>
								<%
									if (request.getAttribute("mliCheckerList") != null && !request.getAttribute("mliCheckerList").equals("[]")) {
											ArrayList<MliCheckerTableData> list = (ArrayList)request.getAttribute("mliCheckerList");
											Iterator<MliCheckerTableData> iterator = list.iterator();
											while (iterator.hasNext()) {
												MliCheckerTableData mliReportData = iterator.next();
								%>
								<tr>
									<td scope="row">
										<%=mliReportData.getSrNo()%>
										<input type="hidden" id="SrNo" name="SrNo" value="<%=mliReportData.getSrNo()%>"/>
									</td>
									<td>
										<%=mliReportData.getMLIName()%>
										<input type="hidden" id="mliName" name="mliName" value="<%=mliReportData.getMLIName()%>"/>
									</td>
									<td>
										<%=mliReportData.getMemberId() %>
										<input type="hidden" id="memberId" name="memberId" value="<%=mliReportData.getMemberId()%>"/>
									</td>
									<td>
										<%=mliReportData.getZoneName() %>
										<input type="hidden" id="zoneName" name="zoneName" value="<%=mliReportData.getZoneName()%>"/>
									</td>
									<td>
										<%=mliReportData.getCheckerFirstName() %>
										<input type="hidden" id="checkerFirstName" name="checkerFirstName" value="<%=mliReportData.getCheckerFirstName()%>"/>
									</td>
									<td>
										<%=mliReportData.getCheckerMiddleName() %>
										<input type="hidden" id="checkerMiddleName" name="checkerMiddleName" value="<%=mliReportData.getCheckerMiddleName()%>"/>
									</td>
									<td>
										<%=mliReportData.getCheckerLastName() %>
										<input type="hidden" id="checkerLastName" name="checkerLastName" value="<%=mliReportData.getCheckerLastName()%>"/>
									</td>
									<td>
										<%=mliReportData.getCheckerEmployeeId() %>
										<input type="hidden" id="checkerEmployeeId" name="checkerEmployeeId" value="<%=mliReportData.getCheckerEmployeeId()%>"/>
									</td>
									<td>
										<%=mliReportData.getCheckerDesignation() %>
										<input type="hidden" id="checkerDesignation" name="checkerDesignation" value="<%=mliReportData.getCheckerDesignation()%>"/>
									</td>
									<td>
										<%=mliReportData.getCheckerPhoneNumber() %>
										<input type="hidden" id="checkerPhoneNumber" name="checkerPhoneNumber" value="<%=mliReportData.getCheckerPhoneNumber()%>"/>
									</td>
									<td>
										<%=mliReportData.getCheckerEmail() %>
										<input type="hidden" id="checkerEmail" name="checkerEmail" value="<%=mliReportData.getCheckerEmail()%>"/>
									</td>
									<td>
										<%=mliReportData.getCheckerUserId()%>
										<input type="hidden" id="checkerUserId" name="checkerUserId" value="<%=mliReportData.getCheckerUserId()%>"/>
									</td>
									<td>
										<%=mliReportData.getHintAnswer()%>
										<input type="hidden" id="hintAnswer" name="hintAnswer" value="<%=mliReportData.getHintAnswer()%>"/>
									</td>
									<td>
										<%=mliReportData.getStatus() %>
										<input type="hidden" id="status" name="status" value="<%=mliReportData.getStatus()%>"/>
									</td>
									<td>
										<%=mliReportData.getCGTMSECheckerRemark()%>
										<input type="hidden" id="CGTMSECheckerRemark" name="CGTMSECheckerRemark" value="<%=mliReportData.getCGTMSECheckerRemark()%>"/>
									</td>
								</tr>
								<%
									}
										} else {
								%>
								<tr>
									<td align="center" colspan="15" style="font-size: medium;">No Data Found</td>
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
