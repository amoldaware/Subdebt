<%@page import="com.cgtsi.actionform.NPAUpgradePopulateData"%>
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
<%@page import="com.cgtsi.actionform.MLIClaimRecoveryForm"%>
<%@page import="com.cgtsi.actionform.MLIClaimRecoveryData"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Insert title here</title>
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
<style>
input[type=number]::-webkit-inner-spin-button, input[type=number]::-webkit-outer-spin-button
	{
	-webkit-appearance: none;
	margin: 0;
}

select {
	width: 120px;
	max-width: 100%;
}

option {
	-moz-white-space: pre-wrap;
	-o-white-space: pre-wrap;
	white-space: pre-wrap;
	overflow: hidden;
	text-overflow: ellipsis;
	border-bottom: 1px solid #DDD;
}

.rowStyle {
	text-align: right;
	height: 20px;
	width: 100px;
	font-size: 13px;
}

.ui-datepicker {
	background-color: #fff;
}
</style>

<script type="text/javascript">
	$(document).ready(function() {
		
		$("#claimRefNo").attr("placeholder", "Enter Claim Reference Number");
		if ($("#claimRefNo").val() == "") {
			$("#mainTbl").css({
				"display" : "none"
			});
		}
		var message = document.getElementById('message').value;
		if (message != "") {
			var databasemsg1 = "<b> " + message + " </b>";

			$("#errormsg").html(databasemsg1).css({
				"color" : "#ff6666",
				"display" : "block"
			});

			$("#message").val("");
		}
	});
	var checkVal = "";
	function getCheck(id) {
		var sno = $("#" + id).val();
		var value = sno.split("-");
		var chk = value[1];
		if ($("#" + id).prop("checked") == true) {
			checkVal = "Y-" + (parseInt(chk));
			$("#" + id).attr("value", checkVal);
		} else {
			checkVal = "N-" + (parseInt(chk));
			$("#" + id).attr("value", checkVal);
		}
	}
	function setDateValue(id) {
		var d = "#" + id;
		$(d).datepicker({
			dateFormat : 'dd/mm/yy',
		}).attr('readonly', 'readonly').focus();
	}

	function setLegalValue() {
		var TotalAmount = 0;
		var totAmt = [];
		$('#userInfo tbody tr ')
				.each(
						function() {
							var msg = "<b>Please correct the following Error:</b><ul>";
							var sno = $(this).find("td").eq(0).html().trim();
							if (($("#type_of_Recovery" + sno).val() == "0")
									&& ($("#updateRecovery" + sno).val() == "Y-"
											+ sno)) {
								msg = msg
										+ "<li>Please Select Recovery Type First to Proceed!!</li>";
								$("#errormsg").html(msg).css({
									"color" : "#ff6666",
									"display" : "block"
								});
								msg = msg + "</ul>";

								$(window).scrollTop(0);
								$("#additional_Recovery" + sno).val("0.0");
								$("#legal_Expense_Advocate_Fee" + sno).val("0.0");
								$("#legal_Expense_Court_Fee" + sno).val("0.0");
								return;
							} else {
								$("#errormsg").css({
									"color" : "#ff6666",
									"display" : "none"
								});

								var addtionalRecovery = parseFloat($("#additional_Recovery" + sno).val());
								var adVocateFee = parseFloat($("#legal_Expense_Advocate_Fee" + sno).val());
								var courtFee = parseFloat($("#legal_Expense_Court_Fee" + sno).val());
								var legalCharge = parseFloat(adVocateFee + courtFee);
								var recAmtCGTMSE = parseFloat(addtionalRecovery - legalCharge);
								$("#total_Legal_Expenses" + sno).val(
										legalCharge);
								$("#recoveryAmounttoberemittedtoCGTMSE" + sno)
										.val(recAmtCGTMSE);
								$("#totalAmount" + sno).val(recAmtCGTMSE);
							}

							var eligibl_Amt_First_Inst = $(
									"#eligibl_Amt_First_Inst" + sno).text();
							var optionVal = $("#type_of_Recovery" + sno).find(
									'option:selected').text();
							if (optionVal.indexOf("Refund") > -1) {
								$("#legal_Expense_Advocate_Fee" + sno).append(
										":input").val('0.0').attr('readonly',
										true);
								$("#legal_Expense_Court_Fee" + sno).append(
										":input").val('0.0').attr('readonly',
										true);
								$("#other_Recovery_Expenses" + sno).append(
										":input").val('0.0').attr('readonly',
										true);
								$("#additional_Recovery" + sno).val(
										eligibl_Amt_First_Inst).attr(
										'readonly', true);
								$("#total_Legal_Expenses" + sno).append(
										":input").val('0.0');
								$("#recoveryAmounttoberemittedtoCGTMSE" + sno)
										.val(eligibl_Amt_First_Inst);
								$("#totalAmount" + sno).val(
										eligibl_Amt_First_Inst);
							} else {
								$("#legal_Expense_Advocate_Fee" + sno).append(
										":input").attr('readonly', false);
								$("#legal_Expense_Court_Fee" + sno).append(
										":input").attr('readonly', false);
								$("#other_Recovery_Expenses" + sno).append(
										":input").attr('readonly', false);
								$("#additional_Recovery" + sno)
										.append(":input").attr('readonly',
												false);
								$("#total_Legal_Expenses" + sno).append(
										":input");
								$("#recoveryAmounttoberemittedtoCGTMSE" + sno)
										.append(":input");
								$("#totalAmount" + sno).append(":input");
								if (optionVal.indexOf("Select") > -1) {
									$("#additional_Recovery" + sno).val('0.0');
									$("#legal_Expense_Advocate_Fee" + sno).val(
											'0.0');
									$("#legal_Expense_Court_Fee" + sno).val(
											'0.0');
									$("#other_Recovery_Expenses" + sno).val(
											'0.0');
									$("#total_Legal_Expenses" + sno).val('0.0');
									$(
											"#recoveryAmounttoberemittedtoCGTMSE"
													+ sno).val('0.0');
									$("#totalAmount" + sno).val('0.0');
								}
							}
							totAmt.push({
								"amt" : $("#totalAmount" + sno).val()
							});
						});
		jQuery.each(totAmt, function(i, val) {
			var amount = val.amt;
			TotalAmount = (parseFloat(TotalAmount) + parseFloat(amount));
		});
		$("#Total")[0].innerText = TotalAmount;
	}
	function getData() {
		var claimRefNo = $("#claimRefNo").val();
		if (claimRefNo == "") {
			var msg = "<b>Please correct the following Error:</b><ul>";
			msg = msg + "<li>Please Enter Valid Claim Reference Number!!!</li>";
			$("#errormsg").html(msg).css({
				"color" : "#ff6666",
				"display" : "block"
			});
			msg = msg + "</ul>";
			$(window).scrollTop(0);
		} else {
			document.mliRecoveryForm.target = "_self";
			document.mliRecoveryForm.method = "POST";
			document.mliRecoveryForm.action = "mliClaimRecovery.do?method=getClaimRecoveryDataSave&claimRefNo="
					+ claimRefNo;
			document.mliRecoveryForm.submit();
		}
	}
	function CancelData() {
		document.mliRecoveryForm.target = "_self";
		document.mliRecoveryForm.method = "POST";
		document.mliRecoveryForm.action = "mliClaimRecovery.do?method=cancelData";
		document.mliRecoveryForm.submit();
	}
	function SaveData() {
		var finalData = [];
		var countdata = 0;
		var rec_typecount = 0, legalCount = 0, totLegExpense = 0, recDateCount = 0, legalCountClaim = 0 , additionalRecoveryCount = 0;
		var msg = "<b>Please correct the following Error:</b><ul>";
		var msg1 = "";
		$('#userInfo tbody tr')
				.each(
						function() {
							var sno = $(this).find("td").eq(0).html().trim();

							var type_of_Recovery = $("#type_of_Recovery" + sno)
									.val();
							var updateRecovery = $("#updateRecovery" + sno)
									.val();
							var additional_Recovery = $(
									"#additional_Recovery" + sno).val();
							var legal_Expense_Advocate_Fee = $(
									"#legal_Expense_Advocate_Fee" + sno).val();
							var legal_Expense_Court_Fee = $(
									"#legal_Expense_Court_Fee" + sno).val();
							var other_Recovery_Expenses = $(
									"#other_Recovery_Expenses" + sno).val();
							var total_Legal_Expenses = $(
									"#total_Legal_Expenses" + sno).val();
							var recoveryAmounttoberemittedtoCGTMSE = $(
									"recoveryAmounttoberemittedtoCGTMSE" + sno)
									.val();
							var dateOfrecovery = $("#dateOfrecovery" + sno)
									.val();

							finalData
									.push({
										"type_of_Recovery" : type_of_Recovery,
										"updateRecovery" : updateRecovery,
										"additional_Recovery" : additional_Recovery,
										"legal_Expense_Advocate_Fee" : legal_Expense_Advocate_Fee,
										"legal_Expense_Court_Fee" : legal_Expense_Court_Fee,
										"other_Recovery_Expenses" : other_Recovery_Expenses,
										"total_Legal_Expenses" : total_Legal_Expenses,
										"recoveryAmounttoberemittedtoCGTMSE" : recoveryAmounttoberemittedtoCGTMSE,
										"dateOfrecovery" : dateOfrecovery
									});
						});
		jQuery
				.each(
						finalData,
						function(i, val) {
							i++;
							var updateRecoveryVal = val.updateRecovery;
							var type_of_RecoveryVal = val.type_of_Recovery;
							var additional_RecoveryVal = val.additional_Recovery;
							var legal_Expense_Advocate_FeeVal = val.legal_Expense_Advocate_Fee;
							var legal_Expense_Court_FeeVal = val.legal_Expense_Court_Fee;
							var other_Recovery_ExpensesVal = val.other_Recovery_Expenses;
							var total_Legal_ExpensesVal = val.total_Legal_Expenses;
							var recoveryAmounttoberemittedtoCGTMSEVal = val.recoveryAmounttoberemittedtoCGTMSE;
							var dateOfrecoveryVal = val.dateOfrecovery;

							if (updateRecoveryVal == "Y-" + i) {
								countdata = 1;
							}
							if ((updateRecoveryVal == "Y-" + i)
									&& (type_of_RecoveryVal == "0")) {
								rec_typecount = 1;
							}
							if ((updateRecoveryVal == "Y-" + i) && (parseFloat(additional_RecoveryVal) <= 0)) {
								additionalRecoveryCount = 1;
							}
							if ((updateRecoveryVal == "Y-" + i)
									&& (type_of_RecoveryVal != "Second/Final claim" && type_of_RecoveryVal != "Refund of First Installment of Claim")
									&& ((parseFloat(legal_Expense_Advocate_FeeVal) < 0)
										|| (parseFloat(legal_Expense_Court_FeeVal) < 0) 
										|| (parseFloat(other_Recovery_ExpensesVal) < 0))) {
								legalCount = 1;
							}
							if ((updateRecoveryVal == "Y-" + i)
									&& (type_of_RecoveryVal == "Second/Final claim" && type_of_RecoveryVal == "Refund of First Installment of Claim")
									&& ((parseFloat(legal_Expense_Advocate_FeeVal) < 0)
										|| (parseFloat(legal_Expense_Court_FeeVal) < 0) 
										|| (parseFloat(other_Recovery_ExpensesVal) < 0))) {
								legalCountClaim = 1;
							}
							if ((updateRecoveryVal == "Y-" + i)
									&& (total_Legal_ExpensesVal == "" || parseFloat(total_Legal_ExpensesVal) < 0)
									|| (recoveryAmounttoberemittedtoCGTMSEVal == "" || parseFloat(recoveryAmounttoberemittedtoCGTMSEVal) < 0)) {
								totLegExpense = 1;
							}
							if ((updateRecoveryVal == "Y-" + i)
									&& (dateOfrecoveryVal == "")) {
								recDateCount = 1;
							}
						});
		if (countdata == 0) {
			msg = msg
					+ "<li>Please Select alteast one record to Proceed!!</li>";
		}
		if (rec_typecount == 1) {
			msg = msg + "<li>Please Select Recovery Type to Proceed!!</li>";
		}
		if(additionalRecoveryCount == 1){
			msg = msg + "<li>Additional Recovery Amount can not be 0 or Negative!!</li>";
		}
		if (legalCount == 1) {
			msg = msg
					+ "<li> Advocate Fee / Court Fee / Other Recovery Expenses can not be Negative!!</li>";
		}
		if (legalCountClaim == 1) {
			msg = msg
					+ "<li> Advocate Fee / Court Fee / Other Recovery Expenses can not be Negative!!</li>";
		}
		if (totLegExpense == 1) {
			msg = msg
					+ "<li>Total Legal Expenses / Recovery Amount remitted to CGTMSE can not be blank or Negative!!</li>";
		}
		if (recDateCount == 1) {
			msg = msg + "<li>Recovery Date can not be blank!!</li>";
		}
		if (countdata == 0 || rec_typecount == 1 || legalCount == 1
				|| legalCountClaim == 1 || totLegExpense == 1
				|| recDateCount == 1) {
			$("#errormsg").html(msg).css({
				"color" : "#ff6666",
				"display" : "block"
			});
			msg = msg + "</ul>";
			$(window).scrollTop(0);
			return;
		} else {
			$("#errormsg").css({
				"color" : "#ff6666",
				"display" : "none"
			});
		}
		if (countdata == 1 && legalCount == 0 && totLegExpense == 0
				&& recDateCount == 0) {
			$("#save").attr("disabled", "disabled");
			$("#save")[0].innerText = 'loading';
			setTimeout(function() {
				$("#save")[0].innerText = "SAVE";
			}, 8000);
			var flag = 2;
			document.mliRecoveryForm.target = "_self";
			document.mliRecoveryForm.method = "POST";
			document.mliRecoveryForm.action = "mliClaimRecovery.do?method=saveClaimRecoveryData&flag="+flag;
			document.mliRecoveryForm.submit();
		}
	}
</script>
<title>Claim Recovery</title>
</head>
<body bgcolor="#FFFFFF" topmargin="0"
	data-new-gr-c-s-check-loaded="14.1008.0" data-gr-ext-installed="">

	<html:form name="mliRecoveryForm"
		type="com.cgtsi.actionform.MLIClaimRecoveryForm"
		action="mliClaimRecovery.do?method=getClaimRecoveryDataSave" method="POST">

		<div class="row"
			style="width: 2054px; margin-left: 20px; padding-top: 10px; padding-bottom: 10px; border-top: 1px solid brown; border-left: 1px solid brown; border-right: 1px solid brown; border-bottom: 1px solid brown;">
			<div class="container row">
				<div class="col-md-12">
			<div class="col-sm-10"
				style='font-weight: 800; margin-top: 15px; margin-bottom: 16px; text-align: center;'>
				<h4 class="modal-title">Claim Recovery Details</h4>
			</div>

			

			<div class="row">
				<div class="col-lg-12" id="errormsg" style="display: none"></div>
				<div class="col-lg-12">
					<div>
						<div class="row" style="margin-top: 10px;">
							<div class="row" style="margin-left: 10px;">
								<div class="col-md-10">
									<html:text styleClass="form-control" property="claimRefNo"
										maxlength="12" name="mliRecoveryForm" styleId="claimRefNo">
									</html:text>
								</div>
								<div class="col-md-1">
									<button type="button" class="btn btn-primary" id="btnSearch"
										onclick="return getData()">Search</button>
								</div>
							</div>

							<div class="modal-body" id="mainTbl">
								<table id="userInfo" class="table table-striped"
									style="font-size: 13px;" >
									<thead class="alert alert-primary">
										<tr>
											<th scope="col">Sr No.</th>
											<!-- <th scope="col">REC ID</th> -->
											<th scope="col">CLAIM REFERENCE NO.<font color="#FF0000"
												size="2">*</font></th>
											<th scope="col">CGPAN</th>
											<th scope="col">Promoter's Name</th>
											<th scope="col">UNIT NAME</th>
											<th scope="col">1st Installment Claim Amount</th>
											<th scope="col">Previous Recovery already remitted to
												CGTMSE</th>
											<th scope="col">TYPE OF RECOVERY<font color="#FF0000"
												size="2">*</font></th>
											<th scope="col">New / Additional Recovery(A)<font
												color="#FF0000" size="2">*</font></th>
											<th scope="col">Legal Expense Advocate Fee(if Any)</th>
											<th scope="col">Legal Expense Court Fee(if Any)</th>
											<th scope="col">Description of Other Recovery
												Expenses(if Any)</th>
											<th scope="col">Other Recovery Expenses Amount(if Any)</th>
											<th scope="col">Total Legal Expenses (B)</th>
											<th scope="col">Recovery Amount to be remitted to CGTMSE
												now (A)-(B)</th>
											<th scope="col">Date on which Recovery was received by
												MLI<font color="#FF0000" size="2">*</font>
											</th>
											<th scope="col">Total Amount to be Paid</th>
											<th scope="col">Update Recovery<font color="#FF0000"
												size="2">*</font></th>
										</tr>
									</thead>
									<tbody>
										<%
											if (request.getAttribute("mliRecoveryList") != null) 
											{
													ArrayList<MLIClaimRecoveryData> list = (ArrayList) request.getAttribute("mliRecoveryList");
													Iterator<MLIClaimRecoveryData> iterator = list.iterator();
													while (iterator.hasNext()) 
													{
														MLIClaimRecoveryData recoveryData = iterator.next();
										%>
										<tr>
											<td scope="row"><%=recoveryData.getSrNo()%></td>
											<%-- <td id="rec_id"><%=recoveryData.getRec_id()%> --%>
												<input type="hidden" id="rec_id" name="rec_id" value="<%=recoveryData.getRec_id()%>">
											<!-- </td> -->
											<td id="claim_ref_number"><input type="hidden"
												id="pmr_ref_No" name="pmr_ref_No"
												value="<%=recoveryData.getPmr_ref_No()%>"> <input
												type="hidden" id="claim_ref_number" name="claim_ref_number"
												value="<%=recoveryData.getClaim_ref_number()%>"> <%=recoveryData.getClaim_ref_number()%>
											</td>
											<td id="cgpan"><input type="hidden" id="cgpan"
												name="cgpan" value="<%=recoveryData.getCgpan()%>"> <%=recoveryData.getCgpan()%></td>
											<td id="promoterName"><input type="hidden"
												id="promoterName" name="promoterName"
												value="<%=recoveryData.getPromoterName()%>"> <%=recoveryData.getPromoterName()%>
											</td>
											<td id="unitName"><input type="hidden" id="unitName"
												name="unitName" value="<%=recoveryData.getUnitName()%>">
												<%=recoveryData.getUnitName()%></td>
											<td id="eligibl_Amt_First_Inst" style="text-align: right;">
												<input type="hidden" id="eligibl_Amt_First_Inst"
												name="eligibl_Amt_First_Inst"
												value="<%=recoveryData.getEligibl_Amt_First_Inst()%>">
												<div id="eligibl_Amt_First_Inst<%=recoveryData.getSrNo()%>"><%=recoveryData.getEligibl_Amt_First_Inst()%></div>
											</td>
											<td id="previous_Recovery" style="text-align: right;"><%=recoveryData.getPrevious_Recovery()%>
											</td>
											<td id="type_of_Recovery">
											<select
												id="type_of_Recovery<%=recoveryData.getSrNo()%>" 
												onchange="return setLegalValue()" name="type_of_Recovery">
												  <%   
												  if(recoveryData.getType_of_Recovery().equals(""))   
												    {
												  %>
												  		<option value="0" selected="selected">Select</option>
												  <%  
												    }else{
												  %>
												        <option value="<%=recoveryData.getType_of_Recovery() %>" selected="selected"><%=recoveryData.getType_of_Recovery()%></option>   
												  <%
												    }
												  %>
													<option value="OTS - Partial Amount">OTS - Partial Amount</option>
													<option value="OTS - Final Amount">OTS - Final Amount</option>
													<option value="Partial Recovery">Partial Recovery</option>
													<option value="Recovery Amount of Inspection By CGTMSE">Recovery Amount of Inspection By CGTMSE</option>
													<option value="Refund of First Installment of Claim">Refund of First Installment of Claim</option>
													<option value="Second/Final claim">Second/Final claim</option>
												</select>
											</td>
											
											<td id="additional_Recovery"><input type="number"
												class="rowStyle"
												id="additional_Recovery<%=recoveryData.getSrNo()%>"
												step='0.01' onchange="return setLegalValue()"
												name="additional_Recovery"
												value="<%=recoveryData.getAdditional_Recovery()%>" />
											</td>
												
											<td id="legal_Expense_Advocate_Fee"><input type="number"
												class="rowStyle"
												id="legal_Expense_Advocate_Fee<%=recoveryData.getSrNo()%>"
												step='0.01' onchange="return setLegalValue()"
												name="legal_Expense_Advocate_Fee"
												value="<%=recoveryData.getLegal_Expense_Advocate_Fee()%>" />
											</td>
											
											<td id="legal_Expense_Court_Fee"><input type="number"
												class="rowStyle"
												id="legal_Expense_Court_Fee<%=recoveryData.getSrNo()%>"
												step='0.01' onchange="return setLegalValue()"
												name="legal_Expense_Court_Fee"
												value="<%=recoveryData.getLegal_Expense_Court_Fee()%>" /></td>
												
											<td id="description_of_Other_Recovery">
											    <textarea style="height: 30px; width: 130px; font-size: 13px;"
													name="description_of_Other_Recovery"
													id="description_of_Other_Recovery<%=recoveryData.getSrNo()%>">
													<%=recoveryData.getDescription_of_Other_Recovery()%>	
											   </textarea>
											</td>
														
											<td id="other_Recovery_Expenses"><input type="number"
												class="rowStyle"
												id="other_Recovery_Expenses<%=recoveryData.getSrNo()%>"
												step='0.01' name="other_Recovery_Expenses"
												value="<%=recoveryData.getOther_Recovery_Expenses()%>" /></td>
												
											<td id="total_Legal_Expenses"><input type="number"
												class="rowStyle"
												id="total_Legal_Expenses<%=recoveryData.getSrNo()%>"
												step='0.01' name="total_Legal_Expenses" readonly="readonly"
												value="<%=recoveryData.getTotal_Legal_Expenses()%>" /></td>
												
											<td id="recoveryAmounttoberemittedtoCGTMSE"><input
												type="number" class="rowStyle"
												id="recoveryAmounttoberemittedtoCGTMSE<%=recoveryData.getSrNo()%>"
												step='0.01' name="recoveryAmounttoberemittedtoCGTMSE"
												readonly="readonly"
												value="<%=recoveryData.getRecoveryAmounttoberemittedtoCGTMSE()%>" />
											</td>
											
											<td id="dateOfrecovery"><input type="text"
												name="dateOfrecovery"
												style="height: 20px; width: 80px; font-size: 13px"
												id="dateOfrecovery<%=recoveryData.getSrNo()%>"
												placeholder="Enter Date" onclick="setDateValue(id)" 
												style="background-color: #fff;" value="<%=recoveryData.getDateOfrecovery()%>"/>
												</td>

											<td id="totalAmount"><input type="number"
												class="rowStyle" id="totalAmount<%=recoveryData.getSrNo()%>"
												step='0.01' name="totalAmount" readonly="readonly"
												value="<%=recoveryData.getTotalAmount()%>" /></td>
											<html:hidden property="message" name="mliRecoveryForm"
												styleId="message" />
											<html:hidden property="flag" name="mliRecoveryForm" styleId="flag" />	
											<html:hidden property="claimRefNo" name="mliRecoveryForm"
												styleId="claimRefNo" />
											<td id="updateRecovery" align="center"><input
												name="updateRecovery"
												id="updateRecovery<%=recoveryData.getSrNo()%>"
												type="checkbox" value="N-<%=recoveryData.getSrNo()%>"
												onClick="return getCheck(id)" /></td>
										</tr>
										<%
											}
												} 
											else {
										%>
										<tr>
											<td align="center" colspan="18" style="font-size: medium;">No Data Found</td>
										</tr>
										<%
											}
										%>
									</tbody>
								</table>
								<div class="col-lg-4"
									style="display: flex; font-weight: 800; margin-left: 1750px;">
									<input type="hidden" id="hdnVal" value="1" /> Total Amount:
									<div id="Total" style="color: brown; margin-left: 12px;">0.00</div>
								</div>
								<div class="col-md-12" style="margin-bottom: 5px;">
									<b>After Updating the recovery details, you are requested
										to remit the recovery amount in order to submit the 2nd /
										final claim. Account details are mentioned below:</b>
								</div>
								<div class="col-md-12" style="margin-bottom: 5px;">
									CGTMSE account details for Guarantee fee Payment:<br /> Name of
									the Bank : <b>State Bank of India</b><br /> Name of the Branch
									: <b>Bandra Kurla Complex</b><br /> Account Name : <b>Credit
										Guarantee Fund Trust for Micro and Small Enterprises</b><br />
									Account Number : <b>37817825400</b><br /> IFSC Code : <b>SBIN0004380</b><br />
								</div>
								<div class="col-md-3 " style="margin-top: 10px;">&nbsp;</div>
								<%
									if (request.getAttribute("mliRecoveryList") != null) {
											ArrayList<MLIClaimRecoveryData> list = (ArrayList) request.getAttribute("mliRecoveryList");
											if (list.size() > 0) {
								%>
								<div class="col-lg-4" style="margin-left: 550px;">
									<button type="button" class="btn btn-primary"
										data-dismiss="modal" value="save" id="save"
										onclick="SaveData()">SAVE</button>
									<button type="button" class="btn btn-primary"
										data-dismiss="modal" value="cancel" id="cancel"
										onclick="CancelData()">CANCEL</button>
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
						</div>
					</div>
				</div>
			</div>
		</div>
		</div>
		</div>
	</html:form>
</body>
</html>