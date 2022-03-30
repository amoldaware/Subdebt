<%@ page language="java"%>
<%@ page import="java.util.*"%>
<%@ page import="com.cgtsi.action.CaptchaSession"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean"%>
<SCRIPT language="JavaScript" type="text/JavaScript" src="js/CGTSI.js"></SCRIPT>
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
<title>Bank Mandate Approval</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
<style type="text/css">
.ui-datepicker {
	background-color: #fff;
}
</style>
<script type="text/javascript">
$(document).ready(function() {
	var message = document.getElementById('message').value;
	if(message != "")
	{	
		var databasemsg1="<b> "+message+" </b>";  
	    
	    $("#errormsg").html(databasemsg1).css({
            "color" : "#ff6666",
            "display" : "block"
        });
    
	    $("#message").val("");
	} 
});
function getData(){
	document.bankMandateForm.target = "_self";
	document.bankMandateForm.method = "POST";
	document.bankMandateForm.action = "bankMandate.do?method=getBankMandateApprovalData";
	document.bankMandateForm.submit();
}

var checkVal = "";
function getCheck(id) {
	if ($("#"+id).prop("checked") == true)  
	{
		checkVal = "Y";
		$("#"+id).attr("value",checkVal);
	} else 
	{
		checkVal = "N";
		$("#"+id).attr("value",checkVal);
	}
}
function SaveData(id) {
	var count = 0;
	var countData = 0;
	var countData_remark = 0;
	var buttonVal = $("#"+id).val();
	$("#userDetailData").val(buttonVal);
	
	var msg="<b>Please correct the following Error:</b><ul>";
	var finalData = [];
	
	
	 $('#userInfo tbody tr').each(function()
	 {
		var sno = $(this).find("td").eq(0).html();
		
		var cgtms_status = $("#cgtms_status"+sno).val();
		var cgtms_remark = $("#cgtms_remark"+sno).val();
		
		finalData.push({
			"cgtms_status":cgtms_status,
			"cgtms_remark":cgtms_remark,
		}); 
	}); 
	jQuery.each(finalData,function(i, val) 
	{
		var cgtms_statusval = val.cgtms_status;
		var cgtms_remarkval = val.cgtms_remark;
		
		if((cgtms_statusval == "Y"))
		{
			countData = 1;		
		}
		if((cgtms_statusval == "Y") && (cgtms_remarkval == ""))
		{
			count = 1;
		}
		if((cgtms_statusval != "Y") && (cgtms_remarkval != ""))
		{
			countData_remark = 1;	
		}
	});
	if(countData == 0)
	{
		msg=msg+"<li>Please Select alteast one record to Proceed!!</li>";
	} 
	if(countData_remark == 1)
	{
		msg=msg+"<li>Checkbox can't be unselected for entered remarks!!</li>";
	} 
	if(count == 1)
	{
		msg=msg+"<li>Remark is Mandatory for Selected Checkbox!!</li>";
	}
	
	if(count==1 || countData == 0 || countData_remark==1){		
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
		$("#errormsg").css({"color" : "#ff6666","display" : "none"});
	}
	if(count == 0)
	{    
		  $("#"+buttonVal).attr("disabled", "disabled");
			$("#"+buttonVal)[0].innerText = 'loading';
			setTimeout(function() {
				$("#"+buttonVal)[0].innerText = buttonVal;
			}, 8000); 
			
		document.bankMandateForm.target = "_self";
		document.bankMandateForm.method = "POST";
		document.bankMandateForm.action = "bankMandate.do?method=saveBankMandateApprovalData";
		document.bankMandateForm.submit();
	} 
}
</script>
<body>
	<html:form name="bankMandateForm"
		type="com.cgtsi.actionform.BankMandateActionForm"
		action="bankMandate.do?method=getBankMandateApprovalData" method="POST">
	 	<div class="row" style="margin-left: 20px; padding-top: 10px; padding-bottom: 10px; border-top: 1px solid brown; border-left: 1px solid brown; border-right: 1px solid brown; border-bottom: 1px solid brown;">
		<div class="col-sm-10" style='font-weight: 800;margin-top: 15px;
    margin-bottom: 16px;
    text-align: center;'>
							<h4 class="modal-title">Bank Mandate Approval Details</h4>
						</div>
						<div class="col-sm-2">
							<button type="button" class="btn btn-primary" data-dismiss="modal"
							onclick="getData()">Refresh</button>
						</div>
						<div class="col-lg-12">
						<div class="col-lg-12" id="errormsg" style="display: none"></div>
						<html:hidden property="userDetailData" styleClass="form-control" styleId="userDetailData" name="bankMandateForm" />
						<html:hidden property="cgtms_userId" styleClass="form-control" styleId="cgtms_userId" name="bankMandateForm" />						
						<html:hidden property="message" name="bankMandateForm" styleId="message" />
						<table id="userInfo" class="table table-striped" id="userInfo"
							style="font-size: 13px;">
							<thead>
								<tr>
									<th scope="col">S.No.</th>
									<th scope="col">MLI Id</th>
									<th scope="col">MLI Name</th>
									<th scope="col">Beneficiary's Name</th>
									<th scope="col">Beneficiary's Bank Name</th>
									<th scope="col">MEMBER ACCOUNT NO</th>
									<th scope="col">MEMBER RTGS NO</th>
									<th scope="col">REMARKS</th>
									<th scope="col">Bank Mandate Accepted/Return</th>
									<th scope="col">Remarks</th>
								</tr>
							</thead>
	                       <tbody>
							   <%
								if (request.getAttribute("bankMandateList") != null) {
									LinkedHashMap<Integer, ArrayList<String>> bankDtlList = (LinkedHashMap) request.getAttribute("bankMandateList");
								for(ArrayList<String> key1 : bankDtlList.values())
								{		
								%>
									<tr>
									<td scope="row"><%=key1.get(0)%></td>
									<td id="memberId"><%=key1.get(1)%>
										<input type="hidden" id="memberId" name="memberId" value="<%=key1.get(1)%>">
									</td>
									<td id="mliName"><%=key1.get(2)%></td>
									<td id="nameOfBeneficiary"><%=key1.get(3)%></td>
									<td id="beneficiaryBankName"><%=key1.get(4)%></td>
									<td id="accountNo"><%=key1.get(5)%></td>
									<td id="rtgsNo"><%=key1.get(6)%></td>
									<td id="remarks"><%=key1.get(7)%></td>
									<td align="center" id="cgtms_status">
										<input name="cgtms_status"
										class="form-check-input cgtms_status"
										id="cgtms_status<%=key1.get(0)%>" type="checkbox"
										onClick="getCheck(id)" value="N"/> 
									</td>	
									<td id="cgtms_remark" >
										<textarea id="cgtms_remark<%=key1.get(0)%>" name="cgtms_remark" class="form-control cgtms_remark" style="width:200px;height:35px;"></textarea>
									</td>
								</tr>
								<%	
									}
								}
								else
								{
							   %>
							  <tr>
									<th scope="col">S.No.</th>
									<th scope="col">MLI Id</th>
									<th scope="col">MLI Name</th>
									<th scope="col">Beneficiary's Name</th>
									<th scope="col">Beneficiary's Bank Name</th>
									<th scope="col">MEMBER ACCOUNT NO</th>
									<th scope="col">MEMBER RTGS NO</th>
									<th scope="col">REMARKS</th>
									<th scope="col">Bank Mandate Accepted/Return</th>
									<th scope="col">Emp Comment</th>
								</tr>
							<%
								}
							%>
							</tbody>
							<%
							LinkedHashMap<Integer, ArrayList<String>> bankDtlList1 = (LinkedHashMap) request.getAttribute("bankMandateList");
							if(bankDtlList1.size() == 0){
							%>
							<tbody>
								<tr>	
									<td colspan="10" align="center">No Data Found</td>
								</tr>
							</tbody>
							<%
									}
							%>
						</table>
					</div>
					<%
						if (request.getAttribute("bankMandateList") != null) {
						LinkedHashMap<Integer, ArrayList<String>> bankDtlList = (LinkedHashMap) request.getAttribute("bankMandateList");
						if(bankDtlList.size() > 0){
					%>
					<div class="col-lg-4" style="margin-left: 550px;">
						<button type="button" class="btn btn-primary" data-dismiss="modal" value="Approve" id="Approve"
							onclick="SaveData(id)">Approve</button>
						<button type="button" class="btn btn-danger" data-dismiss="modal" value="Return" id="Return"
							onclick="SaveData(id)">Return</button>
					</div>
					<%
						}
						} else { 
					%>
						<div class="col-lg-4"></div>
					<%
						}
					%>
				</div>	
			<div class="col-lg-4"></div>
	  </html:form>
</body>
</html>