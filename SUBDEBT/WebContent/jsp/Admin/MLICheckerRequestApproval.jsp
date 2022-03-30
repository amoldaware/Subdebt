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
<%@page import="com.cgtsi.actionform.DisbursementActionForm"%>
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

$(document).ready(function() {
	var message = document.getElementById('message').value;
	//alert("121"+message);
	
	if((message != "" && !(message.indexOf('succes')> -1)))
	{	
		var databasemsg="<b>Please correct the following Error:</b><ul><li>";
	    databasemsg=databasemsg + message +"</li></ul>";   
	    
	    $("#errormsg").html(databasemsg).css({
            "color" : "#ff6666",
            "display" : "block"
        });
    
	    $("#message").val("");
	}
	
	if((message != "" && (message.indexOf('succes')> -1)))
	{	
		var databasemsg1="<b> "+message+" </b>";  
	    
	    $("#errormsg").html(databasemsg1).css({
            "color" : "#ff6666",
            "display" : "block"
        });
    
	    $("#message").val("");
	} 
});

	var checkVal = "";
	function getCheck(id) {
		//alert("function getCheck called.." + id);
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
	
	function getData(){
		document.checkmliUserNewForm.target = "_self";
		document.checkmliUserNewForm.method = "POST";
		document.checkmliUserNewForm.action = "checkMLIUserNew.do?method=checkMLIUserNew";
		document.checkmliUserNewForm.submit();
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
			//alert("cgtms_status" + cgtms_status);
			var approval_remark = $("#approval_remark"+sno).val();
			
			finalData.push({
				"cgtms_status":cgtms_status,
				"approval_remark":approval_remark,
			});
		}); 
		jQuery.each(finalData,function(i, val) 
		{
			var cgtms_statusval = val.cgtms_status;
			var approval_remarkval = val.approval_remark;
			
			if((cgtms_statusval == "Y"))
			{
				countData = 1;		
			}
			if((cgtms_statusval == "Y") && (approval_remarkval == ""))
			{
				count = 1;
			}
			if((cgtms_statusval != "Y") && (approval_remarkval != ""))
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
				
			document.checkmliUserNewForm.target = "_self";
			document.checkmliUserNewForm.method = "POST";
			document.checkmliUserNewForm.action = "checkMLIUserNew.do?method=savecheckerMLIUserNew";
			document.checkmliUserNewForm.submit();
		}
	}

	function RejectData() {
		var userId = $("#userId").val();
		var employeeId = $("#employeeId").val();
		//alert(userId + "\t" + employeeId);
	}
</script>
<style>

</style>
</head>
<body bgcolor="#FFFFFF" topmargin="0"
	data-new-gr-c-s-check-loaded="14.1008.0" data-gr-ext-installed="">
	<html:form name="checkmliUserNewForm"
		type="com.cgtsi.actionform.MLIUserActionForm"
		action="checkMLIUserNew.do?method=checkMLIUserNew" method="POST">

		
		<div class="row" style="margin-left: 20px; padding-top: 10px; padding-bottom: 10px; border-top: 1px solid brown; border-left: 1px solid brown; border-right: 1px solid brown; border-bottom: 1px solid brown;">
		<div class="col-sm-10" style='font-weight: 800;margin-top: 15px;
    margin-bottom: 16px;
    text-align: center;'>
							<h4 class="modal-title">Checker Request Approval Details</h4>
						</div>
						<div class="col-sm-2">
							<button type="button" class="btn btn-primary" data-dismiss="modal"
							onclick="getData()">Refresh</button>
						</div>
						<div class="col-lg-12">
						<div class="col-lg-12" id="errormsg" style="display: none"></div>
						<html:hidden property="userDetailData" styleClass="form-control" styleId="userDetailData" name="checkmliUserNewForm" />
						<html:hidden property="message" name="checkmliUserNewForm" styleId="message" />
						<table class="table table-striped" id="userInfo"
							style="font-size: 13px;">
							<thead>
								<tr>
									<th scope="col">S.No.</th>
									<th scope="col">User Id</th>
									<th scope="col">Employee Name</th>
									<th scope="col">Designation</th>
									<th scope="col">Employee Id</th>
									<th scope="col">MLI Id</th>
									<th scope="col">Email Id</th>
									<th scope="col">Mobile Number</th>
									<th scope="col">Approved</th>
									<th scope="col">Remark</th>
								</tr>
							</thead>
	<tbody>
							<%
								if (request.getAttribute("mliUserData") != null) {
										ArrayList<User> list = (ArrayList) request.getAttribute("mliUserData");
										Iterator<User> iterator = list.iterator();
										while (iterator.hasNext()) {
											User data = iterator.next();
											//System.out.println("121" + data.getIsAdmin() + "\t" + data.getIsAdmin().contains("Y"));
											if ((data.getIsAdmin() != null && data.getIsAdmin().contains("Y"))) {
							%>
							
								<tr>
									<td scope="row"><%=data.getSrlId()%></td>
									<td id="userId"><%=data.getUserId()%>
										<input type="hidden" id="userId" name="userId" value="<%=data.getUserId()%>">
									</td>
									<td id="userName" name="userName"><%=data.getUserName()%></td>
									<td id="designation" name="designation"><%=data.getDesignation()%></td>
									<td id="employeeId" name="employeeId"><%=data.getEmployee_id()%></td>
									<td id="mliId" name="mliId"><%=data.getMliId()%></td>
									<td id="emailId" name="emailId"><%=data.getEmailId()%></td>
									<td id="mobileNo" name="mobileNo"><%=data.getMobileNo()%></td>
									<td align="center" id="cgtms_status"> 
									<input name="cgtms_status"
										class="form-check-input cgtms_status"
										id="cgtms_status<%=data.getSrlId()%>" type="checkbox"
										onClick="getCheck(id)" value="N"/> 
									</td>
									<td id="approval_remark">
									 <input type="text" name="approval_remark" class="form-control"
										id="approval_remark<%=data.getSrlId()%>" />
									</td>
								</tr>
							

							<%
								} else {
							%>
							
								<tr>
									<td scope="row"><%=data.getSrlId()%></td>
									<td><%=data.getUserId()%></td>
									<td><%=data.getUserName()%></td>
									<td><%=data.getDesignation()%></td>
									<td><%=data.getEmployee_id()%></td>
									<td><%=data.getMliId()%></td>
									<td><%=data.getEmailId()%></td>
									<td><%=data.getMobileNo()%></td>
									<td><%=data.getCgtms_status()%></td>
									<td><%=data.getApproval_remark()%></td>
								</tr>
							

							<%
								}
										}
									} 
							%>
							</tbody>
							<%
							ArrayList<User> list = (ArrayList) request.getAttribute("mliUserData");
							 if((list != null) && (list.size() == 0)){
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
					<div class="col-lg-4">
					</div>
					<%
					//System.out.println("data is ::::::123"+request.getAttribute("mliUserData") == "[]");
					//System.out.println("memberId :::" +request.getAttribute("memberInfo"));
					if ((request.getAttribute("mliUserData") != null) && 
						(request.getAttribute("memberInfo") != null && request.getAttribute("memberInfo").equals("000000000000"))) 
					{
						ArrayList<User> list1 = (ArrayList) request.getAttribute("mliUserData");
						
						if(list1.size() > 0){
					%>
					<div class="col-lg-4">
						<button type="button" class="btn btn-primary" data-dismiss="modal" value="Approve" id="Approve"
							onclick="SaveData(id)">Approve</button>
						<button type="button" class="btn btn-danger" data-dismiss="modal" value="Reject" id="Reject"
							onclick="SaveData(id)">Reject</button>
					</div>
					<%
						} }else { 
					%>
						<div class="col-lg-4"></div>
					<%
						}
					%>
					<div class="col-lg-4">
					</div>
				</div>
		
	</html:form>
</body>
</html>