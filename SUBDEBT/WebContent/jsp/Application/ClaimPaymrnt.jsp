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
<%@page import="com.cgtsi.actionform.ClaimPaymentActionForm"%>
<%@page import="com.cgtsi.actionform.NPAMarkingPopulateData"%>

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
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link href="/SUBDEBT/css/StyleSheet.css" rel="stylesheet"
	type="text/css">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script src="https://code.jquery.com/ui/1.10.1/jquery-ui.js"></script>
<title>Claim Processing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>claim Processing Approval Application Details</title>

<script type="text/javascript">
var isSearchClicked;


 $(document).ready(
		function() {//alert("okkk");
			var claimType = document.getElementById('claimType').value;
			
			var isSearchClicked = document.getElementById('isSearchClicked').value;
			//alert("Server isSearchClicked ::" + isSearchClicked); 
			var message = document.getElementById('message').value;
			//alert("message"+message);
			 var iSDISABLE="";
			
			
			if ((claimType == "" && isSearchClicked == "")) {
				$("#isDisable").css("display", "none");
			}else if ((claimType != "" && isSearchClicked == "")) {
				$("#isDisable").css("display", "none");
			}   else {
				$("#isDisable").css("display", "block");
				
			} 
			
			
			if ((message != "" && !(message.indexOf('Success') > -1))) {
				//$("#isDisable").css("display", "none");
				var databasemsg = "<b></b><ul><li>";
				databasemsg = databasemsg + message + "</li></ul>";

				$("#errormsg").html(databasemsg).css({
					"color" : "#ff6666",
					"display" : "block"
				});

				$("#message").val("");
			}
			if ((message != "" && (message.indexOf('Success') > -1))) {
				//$("#isDisable").css("display", "none");
				var databasemsg1 = "<b>" + message + "</b>";

				$("#errormsg").html(databasemsg1).css({
					"color" : "#ff6666",
					"display" : "block"
				});

				$("#message").val("");

			}
			
			
			
			
			
			
			var sno = $(this).find("td").eq(0).html();
			 if($("#iSDISABLE"+sno).val()==0){
				$("#claimPayment"+sno).attr("style", "pointer-events: none;background-color: #eee;");	
				
			} 
			
			
		}); 
 
 function CancelData()
 {
 	document.claimPaymentForm.target = "_self";
 	document.claimPaymentForm.method = "POST";
 	document.claimPaymentForm.action = "claimPayment.do?method=cancelData";
 	document.claimPaymentForm.submit();
 } 
function SaveData(id){
	
	var fromeDate = document.getElementById('fromeDate').value;
	var toDate = document.getElementById('toDate').value;
	var claimType = document.getElementById('claimType').value;
	var memberId = document.getElementById('memberId').value;
	
	var msg = "<b>Please correct the following Error:</b><ul>";
	var countErr = 0;
	var cgtmscReferanceNo= "";
	var cgtmscCgpan = "";
	var claimStatus="";
	var cgtmscId="";
	var netPaidClaimAmount="";
	var finalData = [];
 	var countdata = 0;
	var returnForm = $("#"+id).val();
	//alert(returnForm);
	$("#checkerReturn").val(returnForm);
       $('#userInfo tbody tr').each(function()
		{
    	   var sno = $(this).find("td").eq(0).html().trim(); 
    		var claimPayment = $("#claimPayment"+sno).val();

    		
    		finalData.push({
    			"claimPayment":claimPayment,
    			
    		}); 
			
			});
    	 jQuery.each(finalData,function(i, val) 
  		 	{
  		 		i++;
  		 		var updateRecoveryVal = val.claimPayment;
  		 		
  		 		
  		 		if(updateRecoveryVal == "Y-"+i)
  		 		{
  		 			countdata = 1;	
  		 		}
  		 	});
       if(countdata == 0)
    	{
    		msg=msg+"<li>Please Select alteast one record to Proceed!!</li>";		
    	}    
       
       if(countdata==0)
    	{		
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
        if (countdata == 1) {
        	
        	 $("#"+id).attr("disabled", "disabled");
    		$("#"+id)[0].innerText = 'loading';
    		setTimeout(function() {
    			$("#"+id)[0].innerText = "Save";
    		}, 8000);  
   		document.claimPaymentForm.target = "_self";
   		document.claimPaymentForm.method = "POST";
   		document.claimPaymentForm.action = "claimPayment.do?method=saveDamentData&fromeDate="+fromeDate+"&toDate="+toDate+"&claimType="+claimType+"&memberId="+memberId;
   		document.claimPaymentForm.submit();
   		} 
}
	
function FromDate() {
	var start = new Date("2020-06-24"), end = new Date(), diff = new Date(
			end - start), days = diff / 1000 / 60 / 60 / 24;
	$("#fromeDate").datepicker({
		dateFormat : 'dd/mm/yy',
		minDate : days * -1,
		maxDate : end
	}).attr('readonly', 'readonly').focus();
	
	
}
function ToDate() {
	var start = new Date("2020-06-24"), end = new Date(), diff = new Date(
			end - start), days = diff / 1000 / 60 / 60 / 24;
	$("#toDate").datepicker({
		dateFormat : 'dd/mm/yy',
		minDate : days * -1,
		maxDate : end
	}).attr('readonly', 'readonly').focus();
	
	
}


$(document).ready(function() {

	rolenAME=$("#rolenAME").val();
	//alert(rolenAME);
	if (rolenAME == "MAKER") {
		$("#btnAdd1").show();
		$("#btnReturn").hide();
		$("#btnapprove").hide();
		$("#cancel").show();
	} 
}); 


$(document).ready(function() {

	rolenAME=$("#rolenAME").val();
//alert(rolenAME);
	if (rolenAME == "CHECKER") {
		$("#btnAdd1").hide();
		$("#btnReturn").show();
		$("#btnapprove").show();
		$("#cancel").hide();
		
	} 
});


$(function() {
	$("#btnSearch").bind("click", function() {

		
		$("#btnSearch").data('clicked', true);
		if ($("#btnSearch").data('clicked')) {
			var isSearchClicked = "Clicked";
			$("#isSearchClicked").val(isSearchClicked);
		} else {
			var isSearchClicked = "";
			$("#isSearchClicked").val(isSearchClicked);
		}

		var msg = "<b>Please correct the following Error:</b><ul>";
		var val = 0;
		if ($("#fromeDate").val() == "") {
			msg = msg + "<li>From Date can not be black!!</li>";
			val = 1;
		}
		if ($("#toDate").val() == "") {
			msg = msg + "<li>To Date can not be black!!</li>";
			val = 1;

		}
		if ($("#claimType").val() == "") {
			msg = msg + "<li>claim Type can not be black!!</li>";
			val = 1;

		}
		//claimType
		if (val == 1) {
			$("#errormsg").html(msg).css({
				"color" : "#ff6666",
				"display" : "block"
			});
			msg = msg + "</ul>";
			$(window).scrollTop(0);
			return;
		}
		if (val == 0) {//alert("okk");
			$("#btnSearch")[0].innerText = 'loading';
			$("#btnSearch").prop('disabled', true);
			setTimeout(function() {
				$("#btnSearch")[0].innerText = 'Search';
				$("#btnSearch").prop('disabled', false);
			}, 8000);

			getpaymentDetails();

		} else {
			$("#btnSearch").prop('disabled', true);
		}
	});

	 $("#btnClear").bind("click", function() {
		$("#fromeDate").val('');
		$("#toDate").val('');
		$("#claimType").val('');
		$("#memberId").val('');
		getpaymentDetails();
	}); 
});
var checkVal = "";
function getCheck(id) 
{
	var sno = $("#"+id).val();
	var value = sno.split("-");
	var chk = value[1];
	//alert("sno ::" +sno + "value ::"+value + "chk ::" +chk);
	if ($("#"+id).prop("checked") == true)
	{
		checkVal = "Y-"+(parseInt(chk));
		$("#"+id).attr("value",checkVal); 
		//alert("Selected Yes::" + checkVal);
	}
	else
	{
		checkVal = "N-"+(parseInt(chk));
		$("#"+id).attr("value",checkVal); 
		//alert("Selected NO::" + checkVal);
	}
}
function setDateValue(id) 
{
	var d = "#" + id;
	$(d).datepicker({
		dateFormat : 'dd/mm/yy',
	}).attr('readonly', 'readonly').focus();
}



function getpaymentDetails() {
	var fromeDate = document.getElementById('fromeDate').value;
	var toDate = document.getElementById('toDate').value;
	var claimType = document.getElementById('claimType').value;
	var memberId = document.getElementById('memberId').value;
	

	document.claimPaymentForm.target = "_self";
	document.claimPaymentForm.method = "POST";
	document.claimPaymentForm.action = "claimPayment.do?method=getClaimPayment&fromeDate="+fromeDate+"&toDate="+toDate+"&claimType="+claimType+"&memberId="+memberId;
	document.claimPaymentForm.submit();

}



</script>

</head>
<body bgcolor="#FFFFFF" topmargin="0"
	data-new-gr-c-s-check-loaded="14.1008.0" data-gr-ext-installed="">
	<%--    <html:form name="form1"> --%>
	<html:form name="claimPaymentForm" 
		type="com.cgtsi.actionform.ClaimPaymentActionForm"
		action="claimPayment.do?method=getClaimPayment" method="POST">
		<div id="waitzonediv" class="loader-main" style="display:none">
		<div class="loader"></div>
		<span style="width: 500px;"><font color="blue">Please Wait.....</font></span>
		</div>
		<div class="container row">
			<div class="col-lg-12">
				<div>
					<div class="row" style="margin-top: 10px;">
					<div class="col-lg-12" id="errormsg" style="display: none"></div>
							<div class="modal-body">
							<div class="col-md-12"></div>
							<div class="col-lg-12">
								<div class="row">
											<%
											String fromeDate = "";
											//ArrayList<ClaimPaymentActionForm> list1 = (ArrayList) request.getAttribute("paymentDtls");
											if ((String) request.getAttribute("fromeDate") != null )
											{
												fromeDate = (String) request.getAttribute("fromeDate");
												//System.out.println("fromeDate============"+fromeDate);
											}
											%>
									<%if((fromeDate!=null) && !(fromeDate.equals(""))){ %>				
									<div class="col-md-3"> 
										<label>From Date</label> <font color="#FF0000"
											size="2">*</font><input type="text"
											class="form-control" autocomplete="off"
											style="background-color: #fff;" id="fromeDate"
											onclick="FromDate();"
											 value="<%=fromeDate %>" />
									</div>
										<%}else{ %>
										<div class="col-md-3"> 
										<label>From Date</label> <font color="#FF0000"
											size="2">*</font><input type="text"
											class="form-control" autocomplete="off"
											style="background-color: #fff;" id="fromeDate"
											onclick="FromDate();"
											 />
									</div>
										
										<%} %>
											<%
											String toDate = "";
											if ((String) request.getAttribute("toDate") != null)
											{
												toDate = (String) request.getAttribute("toDate");
												//System.out.println("toDate============"+toDate);
											}
											%>
								  		  <%if((toDate!=null) && !(toDate.equals(""))){ %>			
										<div class="col-md-3">
											<label for="To Date">To Date</label> <font color="#FF0000"
												size="2">*</font> <input type="text" name=""
												class="form-control" autocomplete="off"
												style="background-color: #fff;" id="toDate"
												onclick="ToDate();"
												 value="<%=toDate%>" />
										</div>
										<%}else{ %>
										<div class="col-md-3">
											<label for="To Date">To Date</label> <font color="#FF0000"
												size="2">*</font> <input type="text" name=""
												class="form-control" autocomplete="off"
												style="background-color: #fff;" id="toDate"
												onclick="ToDate();"
												 value="" />
										</div>
										<%} %>



										<%
											String firstClaim = "";
											if ((String) request.getAttribute("firstClaim") != null)
											{
												firstClaim = (String) request.getAttribute("firstClaim");
												//System.out.println("firstClaim============"+firstClaim);
											}
											%>
											
											<%
											String secondClaim = "";
											if ((String) request.getAttribute("secondClaim") != null)
											{
												secondClaim = (String) request.getAttribute("secondClaim");
												//System.out.println("secondClaim============"+secondClaim);
											}
											%>
									 
									<div class="col-md-3">
										<label for="claim type">Claim Type</label> <font
											color="#FF0000" size="2">*</font>
										<%if((firstClaim!=null) && !(firstClaim.equals(""))){ %>					
										<html:select property="claimType" name="claimPaymentForm"
											styleClass="form-control" styleId="claimType">
											<option value="" >Select</option>
											<option value="FirstClaim" selected="selected" >First Claim</option>
											<option value="SecondClaim">Second Claim<option>
										</html:select>
										<%}else if((secondClaim!=null) && !(secondClaim.equals(""))) {%>
										<html:select property="claimType" name="claimPaymentForm"
											styleClass="form-control" styleId="claimType">
											<option value="" >Select</option>
											<option value="FirstClaim"  >First Claim</option>
											<option value="SecondClaim" selected="selected">Second Claim<option>
										</html:select>
										<%}else{%>
										<html:select property="claimType" name="claimPaymentForm"
											styleClass="form-control" styleId="claimType">
											<option value="" selected="selected">Select</option>
											<option value="FirstClaim" >First Claim</option>
											<option value="SecondClaim">Second Claim</option>
										</html:select>
										
										<%} %>
									</div>

									<div class="col-md-3">
										<label for="Member Id">Member Id</label> 
										<html:text property="memberId" styleClass="form-control"
											name="claimPaymentForm" maxlength="12" styleId="memberId" />
									</div>
				
											<div class="col-md-4" style="padding-top: 24px;">
				
										<button type="button" class="btn btn-primary" id="btnSearch">Search</button>
										&nbsp;&nbsp;
										<button type="button" class="btn btn-primary" id="btnClear">Clear</button>
									</div>

								</div>
									<html:hidden property="isSearchClicked"
									name="claimPaymentForm" styleId="isSearchClicked" />
									
								<html:hidden property="checkerReturn" name="claimPaymentForm"
									styleId="checkerReturn" />
									
									
											<%
											String userId = "";
											if ((String) request.getAttribute("userId") != null)
											{
												userId = (String) request.getAttribute("userId");
												//System.out.println("userId============"+userId);
											}
											%>
										<input type="hidden" name="claimPaymentForm" id="usereId"  value="<%=userId%>"/>					
								<br>
						
						
							<div id="isDisable" >
								<table id="userInfo" class="table table-striped"
									style="font-size: 13px;" border="1px">
									<thead class="alert alert-primary">
										<tr>

											<th scope="col">SrNo.</th>
											<th scope="col">Bank Name</th>
											<th scope="col">CGPAN</th>
											<th scope="col">Unit Name</th>
											<th scope="col">Promoter's Name</th>
											 <th scope="col">Claim approved Date</th>
											<th scope="col">Approve Amount</th>
											<th scope="col">Revised NPA Approved Amount</th>
											<th scope="col">Revised NPA Recovered Amount</th>
											<th scope="col">Net Outstanding Amount</th>
											<th scope="col">Claim Applied Amount</th>
											<th scope="col">Claim Eligible Amount</th>
											<th scope="col">First Installment Pay Amount</th>
											<th scope="col">ASF Deductable/Refundable</th>
											<th scope="col">Net Paid Amount</th>
											<th scope="col">beneficiary bank name</th>
											<th scope="col">Account type</th>
											<th scope="col">Account Number</th>
											<th scope="col">MARK All </th>
										</tr>
									</thead>
										<tbody>
												<%
												
												if (request.getAttribute("paymentDtls") != null) {
															ArrayList<ClaimPaymentActionForm> list = (ArrayList) request.getAttribute("paymentDtls");
															//System.out.println(list.size());
															if(list.size()>0){
															
															Iterator<ClaimPaymentActionForm> iterator = list.iterator();
															while (iterator.hasNext()) {
																ClaimPaymentActionForm data = iterator.next();
																
												%>
												<tr>
													<%-- <td id="srNo"><%=data.getSrNo()%></td> --%>
													 <td scope="row"><%= data.getSrNo()%></td>
													<td><%=data.getBankName()%></td>
													
													<td id="ccgpan"><%=data.getcGPAN()%>
														<input type="hidden" name="ccgpan" id="ccgpan" value="<%= data.getcGPAN()%>"/>
														
													</td>
													<td><%=data.getUnitName()%></td>
													<td><%=data.getPromoterName()%></td>
													<td ><%=data.getClaimApprovedDate()%></td>	
													<td><%=data.getApproveAmount()%></td>
													<td><%=data.getRevisedNPAApprovedAmount()%></td>
													<td><%=data.getRevisedNPARecoveredAmount()%></td>
													<td><%=data.getNetOutstandingAmount()%></td>
													<td><%=data.getClaimAppliedAmount()%></td>
													<td><%=data.getClaimeligibleAmount()%></td>
													<td><%=data.getFirstInstallmentPayAmount()%></td>
													<td><%=data.getaSFDeductableRefundable()%></td>
													
													<td><%=data.getNetPaidAmount()%></td>
													<td><%=data.getBeneficiaryBankName()%></td>	
													<td><%=data.getAccounttype()%></td>
													<td><%=data.getAccountNumber()%></td>
													<%-- <td><input type="checkbox" name="checkbox" id="checkbox" value="<%= data.getSrNo()%>"></td> --%>
													<td id="claimPayment">
														<input name="claimPayment"
															id="claimPayment<%= data.getSrNo()%>" type="checkbox" value="N-<%= data.getSrNo()%>"
													        onClick="return getCheck(id)"/> 
													    	
														</td>
													
													
													<td  style="display: none"> <input  type="hidden" id="iSDISABLE<%= data.getSrNo()%>" value="<%=data.getiSDISABLE()%>"></td>
													<td id="referanceNumber"  style="display: none">
													<input type="hidden" name="referanceNumber" id="referanceNumber" value="<%= data.getRefNumber()%>" /></td>
													<td  style="display: none" id="claimTypee"><input type="hidden" name="claimTypee" id="claimTypee"  value="<%= data.getClaimType()%>"/></td>
													<td  style="display: none" id="netPaidAmountt"><input type="hidden" name="netPaidAmountt" id="netPaidAmountt"  value="<%= data.getNetPaidAmount()%>" /></td>
													<td style="display: none" id="memberIdd"><input type="hidden" name="memberIdd" id="memberIdd" value="<%= data.getMemberId()%>" /></td> 
													<td  style="display: none" id="role"><input type="hidden" name="role" id="rolenAME"  value="<%=data.getRole()%>"></td>
											  					
														
												</tr>
												<%
													}
													  }
												
															else {//System.out.println("Insde Else!!!!");
															%>
															<tr>
																<td  align="center" colspan="19">No Data Found</td>
															</tr>
														<%
															}
												}
														%>
											</tbody> 
													
								</table>
									<div class="col-md-3 " style="margin-top: 10px;">&nbsp;</div>
									<%
										if (request.getAttribute("paymentDtls") != null) {
												ArrayList<ClaimPaymentActionForm> list = (ArrayList) request.getAttribute("paymentDtls");
												if (list.size() > 0) {
									%>
									<div align="center">
									<button type="button" class="btn btn-primary" 
									 id="btnAdd1" onclick="SaveData(id);">Save</button>
										<button type="button" class="btn btn-primary" data-dismiss="modal" value="cancel" id="cancel"
							onclick="CancelData()">Cancel</button>					 
									<button id="btnapprove" type="button" onclick="SaveData(id);"
									class="btn btn-primary" value="Approve">Approve</button>
									<button type="button" class="btn btn-Danger" 
									value="Return" id="btnReturn" onclick="SaveData(id);">Return</button>
							
								</div>		
									<%
										}
											} else {
									%>
									<div class="col-lg-4"></div>
									<%
								}
							%>
									<!-- <div align="center">
								<button type="button" class="btn btn-primary" value="NW"
								 id="btnAdd1" onclick="SaveData(id);">Save</button>
													 
								<button id="btnapprove" type="button" onclick="SaveData(id);"
								class="btn btn-primary" value="Approve">Approve</button>
								<button type="button" class="btn btn-Danger" 
								value="Return" id="btnReturn" onclick="SaveData(id);">Return</button>
							
								</div>					 
								 -->
							</div>		
						</div>

							<html:hidden property="message" name="claimPaymentForm"
							styleId="message" />
						<div class="col-md-3 " style="margin-top: 10px;">&nbsp;</div>

						
					</div>
				</div>
			</div>
		</div>
	</html:form>
</body>
</html>