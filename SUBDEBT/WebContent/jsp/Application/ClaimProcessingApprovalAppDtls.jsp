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
<%@page import="com.cgtsi.actionform.ClaimProcessingApprovalActionForm"%>
<SCRIPT language="JavaScript" type="text/JavaScript" src="js/CGTSI.js"></SCRIPT>

<html>
<head>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
	integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l"
	crossorigin="anonymous" />
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>claim Processing Approval Application Details</title>

<script type="text/javascript">
$(document).ready(function() {
	var selectedClaim = document.getElementById('selectedClaim').value;
	var message = document.getElementById('message').value;
	if(message != "")
	{	
	var databasemsg1="<b> "+message+" </b>";  
	$("#errormsg").html(databasemsg1).css({
            "color" : "#ff6666",
            "display" : "block"});
	setTimeout(function(){
		$("#errormsg").css({"display" : "none"});
      }, 5000);
	  $("#message").val("");
	}	

if(selectedClaim == "2"){
$('#secClmInfo tbody tr').each(function()
 {
	var sno = $(this).find("td").eq(0).html();
	var isRefund = $("#isRefund"+sno).val();
	if($("#isRefund"+sno).val() == "0"){
	$(this).css("background-color","#ffcccb");
  }
 });
}
});

function mainForm()
{
	 $("#backbutton").attr("disabled", "disabled");
		$("#backbutton")[0].innerText = 'loading';
		setTimeout(function() {
			$("#backbutton")[0].innerText = Back;
		}, 8000); 
	document.claimProcessingApprovalForm.target = "_self";
	document.claimProcessingApprovalForm.method = "POST";
	document.claimProcessingApprovalForm.action = "claimProcessingApproval.do?method=getApprovalDetails";
	document.claimProcessingApprovalForm.submit();
}

function saveSecondClaimData(id)
{
	var msg = "<b>Please correct the following Error:</b><ul>";
	var countErr = 0;
	var sno = "" ,secClmdecision = "", secClmCommect = "",cgpan="",claimRefNumber="",promotoerReferanceNo="";
	var final_Claim_amount = "", elegibleAmtFirstInst="",recovery_Recived="",second_installment_payable="",bankId="";
	$('#secClmInfo tbody tr').each(function()
	{
		sno = $(this).find("td").eq(0).html().trim();
		if(id == "savebutton"+sno)
		{
			secClmdecision = $("#secClmdecision"+sno).val();
			secClmCommect = $("#secClmCommect"+sno).val();
			cgpan = $("#cgpan"+sno).val();
			claimRefNumber = $("#claimRefNumber"+sno).val();
			promotoerReferanceNo = $("#promotoerReferanceNo"+sno).val();
			final_Claim_amount = $("#final_Claim_amount"+sno).val();
			elegibleAmtFirstInst = $("#elegibleAmtFirstInst"+sno).val();
			recovery_Recived = $("#recovery_Recived"+sno).val();
			second_installment_payable = $("#second_installment_payable"+sno).val();
			bankId = $("#bankId").val();
			if(secClmdecision == "0")
			{
				msg=msg+"<li>Please Select Proper Value from Dropdown!!</li>";
				countErr = 1;	
			}
			if(secClmCommect == ""){
				msg=msg+"<li>Please Enter Comment to Proceed!!</li>";
				countErr = 1; 
			}
		}
	});
	if(countErr == 1){		
        $("#errormsg").html(msg).css({
             "color" : "#ff6666",
             "display" : "block"
         });
        msg=msg+"</ul>";
        $(window).scrollTop(0);
        return;
    }
	else
	{	$("#errormsg").css({"color" : "#ff6666","display" : "none"});
		if(countErr == 0){	
		$("#"+id).attr("disabled", "disabled");
    	$("#"+id)[0].innerText = "Loading";
   		setTimeout(function() {$("#"+id)[0].innerText = "Save";}, 8000); 		
		document.claimProcessingApprovalForm.target = "_self";
   		document.claimProcessingApprovalForm.method = "POST"
   		document.claimProcessingApprovalForm.action = "claimProcessingApproval.do?method=saveSecondClaimDtls&cgpan="
   				                                      +cgpan+"&promotoerReferanceNo="+promotoerReferanceNo+"&claimRefNumber="
   				                                      +claimRefNumber+"&secClmdecision="+secClmdecision+"&secClmCommect="
   				                                      +secClmCommect+"&final_Claim_amount="+final_Claim_amount+"&elegibleAmtFirstInst="
   				                                      +elegibleAmtFirstInst+"&recovery_Recived="
   				                                      +recovery_Recived+"&second_installment_payable="+second_installment_payable
   				                                      +"&bankId="+bankId;
   		document.claimProcessingApprovalForm.submit();
		}
	}
}
function getApproveOrReturn(id){
	var msg = "<b>Please correct the following Error:</b><ul>";
	var countErr = 0;
	var cgtmscReferanceNo= "";
	var cgtmscCgpan = "";
	var claimStatus="";
	var cgtmscId="";
	var approveClaimAmount="";
	var decision="";
	var cgtmscRemarks="";
       $('#userInfo tbody tr').each(function()
		{
			var sno = $(this).find("td").eq(0).html().trim();
			//if(id.indexOf(sno) > -1)
			if(id == "savebutton"+sno)	
			{
				cgtmscReferanceNo=$("#referanceNo"+sno).val();  
				cgtmscCgpan=$("#cgpan"+sno).val();
				claimStatus=$("#claimStatus"+sno).val();
				cgtmscId=$("#userId"+sno).val();
				approveClaimAmount=$("#claimAmount"+sno).val();
				decision=$("#decision"+sno).val();
				cgtmscRemarks=$("#cgtmscRemarks"+sno).val();
				if(decision=="0" || decision=="")
				{
		            msg = msg + "<li>Please Select Decision Value from Dropdown!!</li>";
		            countErr = 1;
				}
				if(cgtmscRemarks=="")
				{
		            msg = msg+"<li>Please Enter Comment to Proceed!!</li>";
		            countErr = 1;
				}
			}
		});
       if (countErr == 1) {
			$("#errormsg").html(msg).css({
				"color" : "#ff6666",
				"display" : "block"
			});
			msg = msg + "</ul>";
			$(window).scrollTop(0);
			return;
		}
       	else 
       	{   $("#errormsg").css({"color" : "#ff6666","display" : "none"});
        	if (countErr == 0) {
        	$("#"+id).attr("disabled", "disabled");
    		$("#"+id)[0].innerText = 'loading';
    		setTimeout(function() {
    			$("#"+id)[0].innerText = "Save";
    		}, 8000); 
   		 	document.claimProcessingApprovalForm.target = "_self";
   			document.claimProcessingApprovalForm.method = "POST"
   			document.claimProcessingApprovalForm.action = "claimProcessingApproval.do?method=getClaimProcessingApproval&cgtmscReferanceNo="+cgtmscReferanceNo+"&cgtmscCgpan="+cgtmscCgpan+"&claimStatus="+claimStatus+"&cgtmscId="+cgtmscId+"&approveClaimAmount="+approveClaimAmount+"&cgtmscRemarks="+cgtmscRemarks+"&decision="+decision;
   			document.claimProcessingApprovalForm.submit();
   			} 
       }
}
	
	function getCheckList(promoterRefNo,cgpan,userId,memberId){
		
		//alert("promoterRefNo"+promoterRefNo+"cgpan"+cgpan+"userId"+userId+"memberId"+memberId);
		 $("#waitzonediv").show();
		document.claimProcessingApprovalForm.target = "_self";
		document.claimProcessingApprovalForm.method = "POST";
		document.claimProcessingApprovalForm.action = "claimProcessingApproval.do?method=getClaimProcessingApproval&promoterRefNo="+promoterRefNo+"&cgpan="+cgpan+"&userId="+userId+"&memberId="+memberId;
		document.claimProcessingApprovalForm.submit();	
	}
	
	
function getReferanceDetail(pmrReferanceNo,pcgpan,cgtmscUserId,cgpanMemberId){
	
		//alert("pmrReferanceNo"+pmrReferanceNo+"pcgpan"+pcgpan+"cgtmscUserId"+cgtmscUserId+"cgpanMemberId"+cgpanMemberId);
		 $("#waitzonediv").show();
		document.claimProcessingApprovalForm.target = "_self";
		document.claimProcessingApprovalForm.method = "POST";
		document.claimProcessingApprovalForm.action = "claimProcessingApproval.do?method=getClaimProcessingApproval&pmrReferanceNo="+pmrReferanceNo+"&pcgpan="+pcgpan+"&cgtmscUserId="+cgtmscUserId+"&cgpanMemberId="+cgpanMemberId;
		document.claimProcessingApprovalForm.submit();	 
}
	
</script>

</head>
<body bgcolor="#FFFFFF" topmargin="0"
	data-new-gr-c-s-check-loaded="14.1008.0" data-gr-ext-installed="">
	<%--    <html:form name="form1"> --%>
	<html:form name="claimProcessingApprovalForm" 
		type="com.cgtsi.actionform.ClaimProcessingApprovalActionForm"
		action="claimProcessingApproval.do?method=getClaimProcessingApproval" method="POST">
		<div id="waitzonediv" class="loader-main" style="display:none">
		<div class="loader"></div>
		<span style="width: 500px;"><font color="blue">Please Wait.....</font></span>
		</div>
		<div class="container row">
			<div class="col-lg-12">
				<div>
					<div class="row" style="margin-top: 10px;">
					<div class="col-lg-12" id="errormsg" style="display: none"></div>
					<html:hidden property="message" name="claimProcessingApprovalForm" styleId="message" />
							<div class="modal-body">
							           <%
							           	  if((request.getAttribute("selectedClaim") != null) && "1".equals(request.getAttribute("selectedClaim")))
							           	  {
							           %>
										<h4 align="center" class="modal-title">First Claim Details</h4>
										<table id="userInfo" class="table table-striped" style="font-size: 13px;" border="1px">
											<thead class="alert alert-primary">
												<tr>
												
													<th scope="col">SrNo.</th>
													<th scope="col">Member Id</th>
													<th scope="col">Claim Ref Number</th>
													<th scope="col">Claim Status</th>
													<th scope="col">Unit Name</th>
													<th scope="col">Application Remark</th>
													<!-- <th scope="col">Return Remark</th>
													<th scope="col">Return Remark Date</th> -->
													<th scope="col">Approve Amount</th>
													<th scope="col">OSAmt As On Npa</th>
													<th scope="col">Recovery As On Npa</th>
													<th scope="col">Elegible Amt First Inst</th>
													<th scope="col">DED</th>
													<th scope="col">Refund</th>
													<th scope="col">STax</th>
													<th scope="col">Sbhcess</th>
													<th scope="col">KkCess</th>
													<th scope="col">State Code</th>
													<th scope="col">GstNo</th>
													<th scope="col">Asf Recieved Or Not</th>
													<th scope="col">Net Payble As First Inst</th>
													<th scope="col" >Decision</th>
													<th scope="col">Approved Claim Amnt</th>
													<th scope="col">DU Updated @</th>
													<th scope="col">Comments</th>
													<th scope="col">Recomendation</th>
													<th scope="col">Action</th>
												</tr>
											</thead>
											<tbody>
												<%
												if (request.getAttribute("approvalApplicationDtls") != null ) {
												ArrayList<ClaimProcessingApprovalActionForm> list = (ArrayList) request.getAttribute("approvalApplicationDtls");
												Iterator<ClaimProcessingApprovalActionForm> iterator = list.iterator();
												while (iterator.hasNext()) {
												ClaimProcessingApprovalActionForm data = iterator.next();											
												%>
												<tr>
													<td id="srNo"><%= data.getSrNo()%></td>
													<td><%=data.getMemberId()%></td>
													<td><a href="#" onclick="getReferanceDetail('<%=data.getPromotoerReferanceNo()%>','<%=data.getCgpan()%>','<%=data.getUserId()%>','<%=data.getMemberId()%>');"><%=data.getClaimRefNumber()%></a></td>
													<td><%=data.getClaimStatus()%></td>
													<td><%=data.getUnitName()%></td>
													<td><%=data.getApplicationRemark()%></td>
													<%-- <td ><%=data.getReturnRemark()%></td>	
													<td><%=data.getReturnRemarkDate()%></td> --%>
													<td><%=data.getApproveAmount()%></td>
													<td><%=data.getOsAmtAsOnNpa()%></td>
													<td><%=data.getRecoveryAsOnNpa()%></td>
													<td><%=data.getElegibleAmtFirstInst()%></td>
													<td><%=data.getDed()%></td>
													<td><%=data.getRefund()%></td>
													<td><%=data.getsTax()%></td>
													
													<td><%=data.getSbhcess()%></td>
													<td><%=data.getKkCess()%></td>	
													<td><%=data.getStateCode()%></td>
													<td><%=data.getGstNo()%></td>
													<td><%=data.getAsfRecievedOrNot()%></td>
													<td><%=data.getNetPaybleAsFirstInst()%></td>
													<td >
														<select class="form-control;width:200px" 
															name="claimProcessingApprovalForm" id="decision<%= data.getSrNo()%>">
															<option value="0">Select</option>
															<option value="Approve">Approve</option>
															<option value="Return">Return</option>
															<option value="Rejected">Rejected</option>
														</select>
													</td>
													<td ><%=data.getApprovedClaimAmnt()%></td>
													<td><a href="#" onclick="getCheckList('<%=data.getPromotoerReferanceNo()%>','<%=data.getCgpan()%>','<%=data.getUserId()%>','<%=data.getMemberId()%>');">view</a></td>
													<td><textarea  class="form-control" style="width: 250px;height:35px;" id="cgtmscRemarks<%= data.getSrNo()%>"></textarea></td>
													<td><%=data.getRecomendation()%></td>
													
    												<td><input type="button" id="savebutton<%= data.getSrNo()%>" class="btn btn-primary" onclick="getApproveOrReturn(id);" value="save"></td>
													
													
													<input type="hidden" id="cgpan<%= data.getSrNo()%>" value="<%=data.getCgpan()%>">
													<input type="hidden" id="userId<%= data.getSrNo()%>" value="<%=data.getUserId()%>">
													<input type="hidden" id="referanceNo<%= data.getSrNo()%>" value="<%=data.getPromotoerReferanceNo()%>">
													<input type="hidden" id="claimStatus<%= data.getSrNo()%>" value="<%=data.getClaimStatus()%>">
													<input type="hidden" id="claimAmount<%= data.getSrNo()%>" value="<%=data.getApproveAmount()%>">
													
												</tr>
												<%
													}
														} else {
												%>
												<tr>
													<td colspan="25" align="center">No Data Found</td>
												</tr>
												<%
													}
												%>
											</tbody>  
										</table>
										<%
							           	  }else {
										%>
											<h4 align="center" class="modal-title">Second Claim Details</h4>
											<table id="secClmInfo" class="table table-striped" style="font-size: 13px;" border="1px">
												<thead class="alert alert-primary">
												<tr>
													<th scope="col">Sr.no.</th>
													<th scope="col">Bank Name</th>
													<th scope="col">Zone Name</th>
													<th scope="col">MEMBER ID</th>
													<th scope="col">CGPAN</th>
													<th scope="col">Claim Reference no.</th>
													<th scope="col">Promoter Name</th>
													<th scope="col">Unit Name</th>
													<th scope="col">Guaranteed Amount</th>
													<th scope="col"><p style="width: 100px;">Outstanding Amt as on date of NPA or o/s derived Amt</p></th>
													<th scope="col">Recovery (after NPA)</th>
													<th scope="col">Net Outstanding[(Minimum of A/B)-C]</th>
													<th scope="col">Extent of guarantee</th>
													<th scope="col"><p style="width: 100px;">CGTMSE's Liability (90%* D) or eligi % *o/s derived Amount</p></th>
													<th scope="col"><p style="width: 100px;">First Installment Paid by CGTMSE (75% of E)</p></th>
													<th scope="col"><p style="width: 100px;">Recovery received after settlement of 1st claim</p></th>
													<th scope="col">Legal Expenses</th>
													<th scope="col"><p style="width: 100px;">Net Recovery remitted to CGTMSE (G - H)</p></th>
													<th scope="col"><p style="width: 100px;">Net Amount in Default (Final Loss) (D - I)</th>
													<th scope="col">CGTMSE liability (90% of J)</th>
													<th scope="col">Second Installment Payable by CGTMSE (K-F)</th>
													<th scope="col"><p style="width: 200px;">Final Claim amount to be remitted to MLI (`)/ Excess Amount to be refunded to the MLI (1st installment - OTS amount) (G-F)</p></th>
													<th scope="col">Decision</th>
													<th scope="col">Comments</th>
													<th scope="col">Action</th>
												</tr>
												</thead>
												
												<tbody>
												<%
												String bankId = (String)request.getAttribute("bankId");
												if (request.getAttribute("approvalApplicationDtls") != null && !(request.getAttribute("approvalApplicationDtls").toString().equals("[]"))) {
													ArrayList<ClaimProcessingApprovalActionForm> secondClaimList = (ArrayList) request.getAttribute("approvalApplicationDtls");
													Iterator<ClaimProcessingApprovalActionForm> iterator = secondClaimList.iterator();
													while (iterator.hasNext()) {
													ClaimProcessingApprovalActionForm secClmList = iterator.next();			
												%>
												<tr>
												    <input type="hidden" id="bankId"  value="<%=bankId%>">
													<td id="srNo"><%= secClmList.getSrNo()%>
													</td>
													<td>
														<%=secClmList.getBankName()%>
													</td>
													<td>
														<%=secClmList.getZoneName()%>
													</td>
													<td>
														<%=secClmList.getMemberId()%>
													</td>
													<td>
														<%=secClmList.getCgpan()%>
														<input type="hidden" id="cgpan<%= secClmList.getSrNo()%>" value="<%=secClmList.getCgpan()%>">
														
													</td>
													<td>
														<%=secClmList.getClaimRefNumber() %>
														<input type="hidden" id="claimRefNumber<%= secClmList.getSrNo()%>" value="<%=secClmList.getClaimRefNumber()%>">
														
													</td>
														<input type="hidden" id="promotoerReferanceNo<%= secClmList.getSrNo()%>" value="<%=secClmList.getPromotoerReferanceNo()%>">										
													<td>
														<%=secClmList.getPromoterName() %>
													</td>
													<td>
														<%=secClmList.getUnitName()%>
													</td>
													<td>
														<%=secClmList.getApproveAmount()%>
													</td>
													<td>
														<%=secClmList.getOsAmtAsOnNpa()%>
													</td>
													<td>
														<%=secClmList.getRecoveryAsOnNpa()%>
													</td>
													<td>
														<%=secClmList.getNet_Outstanding()%>
													</td>
													<td>
														<%=secClmList.getExtent_of_guarantee()%>
													</td>
													<td>
														<%=secClmList.getCgtmse_liability()%>
													</td>
													<td>
														<%=secClmList.getElegibleAmtFirstInst()%>
														<input type="hidden" id="elegibleAmtFirstInst<%= secClmList.getSrNo()%>" value="<%=secClmList.getElegibleAmtFirstInst()%>">
													</td>
													<td>
														<%=secClmList.getRecovery_Recived()%>
														<input type="hidden" id="recovery_Recived<%= secClmList.getSrNo()%>" value="<%=secClmList.getRecovery_Recived()%>">
													</td>
													<td>
														<%=secClmList.getLegal_Expenses()%>
													</td>
													<td>
														<%=secClmList.getNetRecovery_RemittedtoCGTMSE()%>
													</td>
													<td>
														<%=secClmList.getNet_Amount_in_Default()%>
													</td>
													<td>
														<%=secClmList.getCGTMSE_liability_II()%>
													</td>
													<td>
														<%=secClmList.getSecond_installment_payable()%>
														<input type="hidden" id="second_installment_payable<%= secClmList.getSrNo()%>" value="<%=secClmList.getSecond_installment_payable()%>">
													</td>
													<td>
														<%=secClmList.getFinal_Claim_amount()%>
														<input type="hidden" id="final_Claim_amount<%= secClmList.getSrNo()%>" value="<%=secClmList.getFinal_Claim_amount()%>">
													</td>
													<td >
														<select class="form-control;width:200px" 
															name="claimProcessingApprovalForm" id="secClmdecision<%= secClmList.getSrNo()%>">
															<option value="0">Select</option>
															<option value="Approve">Approve</option>
															<option value="Return">Return</option>
															<option value="Rejected">Rejected</option>
														</select>
													</td>
													<td><textarea  class="form-control" style="width: 250px;height:35px;" id="secClmCommect<%= secClmList.getSrNo()%>"></textarea></td>
													<td><input type="button" id="savebutton<%= secClmList.getSrNo()%>" class="btn btn-primary" value="save" onclick="saveSecondClaimData(id);"></td>
													<input type="hidden" id="isRefund<%= secClmList.getSrNo()%>" value="<%=secClmList.getIsRefund()%>">
									
												</tr>
												<%
													}
														} else {
												%>
												<tr>
													<td colspan="25" align="center">No Data Found</td>
												</tr>
												<%
													}
												%>
											</tbody> 
											</table>
										<%
							           	  }
										%>
									</div>
									
						<html:hidden property="selectedClaim" name="claimProcessingApprovalForm"
							styleId="selectedClaim" />
						<div class="col-md-3 " style="margin-top: 10px;">&nbsp;</div>
						 <div style="padding-left:500px;">
								<button type="button" id="backbutton" class="btn btn-primary" onclick="return mainForm()">Back</button>
						</div>	
					</div>
				</div>
			</div>
		</div>
	</html:form>
</body>
</html>