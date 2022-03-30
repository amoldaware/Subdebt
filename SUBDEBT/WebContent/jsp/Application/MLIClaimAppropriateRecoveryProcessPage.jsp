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
<%@page import="com.cgtsi.actionform.MLIClaimAppropriateRecoveryForm"%>
<%@page import="com.cgtsi.actionform.MLIAppropriateClaimRecoveryData"%>
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
	var isSearchClicked;


	$(document).ready(function() {
		var claimType = document.getElementById('claimRefNo').value;

		var isSearchClicked = document.getElementById('isSearchClicked').value;

		var message = document.getElementById('message').value;

		if ((claimType == "" && isSearchClicked == "")) {
			$("#isDisable").css("display", "none");
		} else if ((claimType != "" && isSearchClicked == "")) {
			$("#isDisable").css("display", "none");
		} else {
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

	});

	var checkVal = "";
	function getCheck(id) {
		var sno = $("#" + id).val();
		var value = sno.split("-");
		var chk = value[1];

		if ($("#" + id).prop("checked") == true) {
			checkVal = "Y-" + (parseInt(chk));
			$("#" + id).attr("value", checkVal);
			setAmount(checkVal);

		} else {
			checkVal = "N-" + (parseInt(chk));
			$("#" + id).attr("value", checkVal);
			setAmount(checkVal);
		}
	}
	function setDateValue(id) {
		var d = "#" + id;
		$(d).datepicker({
			dateFormat : 'dd/mm/yy',
		}).attr('readonly', 'readonly').focus();
	}
    function setAmount(checkVal)
    {
    	var finalData = [];
    	var TotalAmount = 0 , totalAmount = 0;
    	$('#userInfo tbody tr').each(function() {
    		var sno = $(this).find("td").eq(0).html().trim();
    		var updateRecovery = $("#updateRecovery" + sno).val();
    		if(updateRecovery.indexOf("Y-") > -1){
    		    totalAmount = parseFloat($("#totalAmount" + sno).val());
    		}
    		else{
    			totalAmount = 0;
    		}
    		finalData.push({
				"totalAmount" : totalAmount,
			});
    	});	
    	jQuery.each(finalData, function(i, val) {
			var amount = val.totalAmount;
				TotalAmount = (parseFloat(TotalAmount) + parseFloat(amount));
			});
			$("#Total")[0].innerText = TotalAmount;
    }
	function SaveData() {
		var finalData = [];
		var countdata = 0, rec_typecount = 0, recDateCount = 0;
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
							var dateOfrecovery = $("#dateOfrecovery" + sno)
									.val();

							finalData
									.push({
										"type_of_Recovery" : type_of_Recovery,
										"updateRecovery" : updateRecovery,
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
							var dateOfrecoveryVal = val.dateOfrecovery;

							if (updateRecoveryVal == "Y-" + i) {
								countdata = 1;
							}
							if ((updateRecoveryVal == "Y-" + i)
									&& (type_of_RecoveryVal == "0")) {
								rec_typecount = 1;
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
			msg = msg + "<li>Please Select Status to Proceed!!</li>";
		}
		if (recDateCount == 1) {
			msg = msg + "<li>Payment Receive Date can not be blank!!</li>";
		}
		if (countdata == 0 || rec_typecount == 1 || recDateCount == 1) {
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
		if (countdata == 1 && rec_typecount == 0 && recDateCount == 0) {
			$("#save").attr("disabled", "disabled");
			$("#save")[0].innerText = 'loading';
			setTimeout(function() {
				$("#save")[0].innerText = "SAVE";
			}, 8000);

			document.mliAppropriateRecoveryForm.target = "_self";
			document.mliAppropriateRecoveryForm.method = "POST";
			document.mliAppropriateRecoveryForm.action = "mliAppropriateRecovery.do?method=saveClaimRecoveryData";
			document.mliAppropriateRecoveryForm.submit();
		}
	}

	function CancelData() {
		document.mliAppropriateRecoveryForm.target = "_self";
		document.mliAppropriateRecoveryForm.method = "POST";
		document.mliAppropriateRecoveryForm.action = "mliAppropriateRecovery.do?method=cancelData";
		document.mliAppropriateRecoveryForm.submit();
	}

	$(function() {
		$("#btnSearch")
				.bind(
						"click",
						function() {

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

							if ($("#claimRefNo").val() == "") {
								msg = msg
										+ "<li>Please Enter Valid Claim Reference Number!!!</li>";
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
							if (val == 0) {
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

	});

	function getpaymentDetails() {
		var claimRefNo = $("#claimRefNo").val();

		document.mliAppropriateRecoveryForm.target = "_self";
		document.mliAppropriateRecoveryForm.method = "POST";
		document.mliAppropriateRecoveryForm.action = "mliAppropriateRecovery.do?method=getAppropriateClaimRecoveryData&claimRefNo="
				+ claimRefNo;
		document.mliAppropriateRecoveryForm.submit();

	}
</script>
<title>Claim Recovery</title>
</head>
<body bgcolor="#FFFFFF" topmargin="0"
	data-new-gr-c-s-check-loaded="14.1008.0" data-gr-ext-installed="">

	<html:form name="mliAppropriateRecoveryForm"
		type="com.cgtsi.actionform.MLIClaimAppropriateRecoveryForm"
		action="mliAppropriateRecovery.do?method=getAppropriateClaimRecoveryData"
		method="POST">

		<div class="row"
			style="width: 2054px; margin-left: 20px; padding-top: 10px; padding-bottom: 10px; border-top: 1px solid brown; border-left: 1px solid brown; border-right: 1px solid brown; border-bottom: 1px solid brown;">
			<div class="container row">
				<div class="col-md-12">
					<div class="row" style="margin-top: 10px;">
						<div class="col-md-12"
							style='font-weight: 800; margin-top: 15px; margin-bottom: 16px; text-align: center;'>
							<h4 class="modal-title">Appropriate Recovery Details</h4>
						</div>

						<!-- <div class="col-md-2">
							<button type="button" class="btn btn-primary"
								data-dismiss="modal" onclick="getData()">Refresh</button>
						</div> -->
					</div>

					<div class="row" style="margin-top: 10px;">
						<div class="col-md-12" id="errormsg" style="display: none"></div>
						<div class="col-md-1">&nbsp;</div>
						<div class="col-md-4">
							<label for="claimRefNo" style="font-weight: 600;">Enter
								Claim Reference Number:</label><font color="#FF0000" size="2">*</font>
							<html:text styleClass="form-control" property="claimRefNo"
								name="mliAppropriateRecoveryForm" styleId="claimRefNo">
							</html:text>
						</div>
						<div class="col-md-1" style="margin-top: 32px;">
							<button type="button" class="btn btn-primary" id="btnSearch">Search</button>
							<html:hidden property="isSearchClicked"
								name="mliAppropriateRecoveryForm" styleId="isSearchClicked" />

							<html:hidden property="checkerReturn"
								name="mliAppropriateRecoveryForm" styleId="checkerReturn" />
						</div>
						<div class="col-md-6">&nbsp;</div>
					</div>

					<div class=" row" id="isDisable">
						<div class="col-md-12" style="margin-left: 13px;">

							<table id="userInfo" class="table table-striped"
								style="font-size: 13px; margin-left: 14px; margin-top: 10px;">
								<thead class="alert alert-primary">
									<tr>

										<th scope="col">Sr No.</th>
										<th scope="col">Update Recovery<font color="#FF0000"
											size="2">*</font></th>
										<th scope="col">CLAIM REFERENCE NO.<font color="#FF0000"
											size="2">*</font></th>
										<th scope="col">CGPAN</th>
										<th scope="col">BANK NAME</th>
										<th scope="col">Promoter's Name</th>
										<th scope="col">UNIT NAME</th>
										<th scope="col">Total Amount to be Paid</th>
										<th scope="col">UTR Number</th>
										<th scope="col">UTR Date</th>
										<th scope="col">Payment Receive Date</th>
										<th scope="col">Status</th>
										<th scope="col" style="display: none">Rec id</th>
									</tr>
								</thead>
								<tbody>
									<%
										if (request.getAttribute("mliRecoveryList") != null) {
												ArrayList<MLIAppropriateClaimRecoveryData> list = (ArrayList) request
														.getAttribute("mliRecoveryList");
												Iterator<MLIAppropriateClaimRecoveryData> iterator = list.iterator();
												while (iterator.hasNext()) {
													MLIAppropriateClaimRecoveryData recoveryData = iterator.next();
									%>
									<tr>

										<td scope="row"><%=recoveryData.getSrNo()%></td>

										<td id="updateRecovery" align="center"><input
											name="updateRecovery"
											id="updateRecovery<%=recoveryData.getSrNo()%>"
											type="checkbox" value="N-<%=recoveryData.getSrNo()%>"
											onClick="return getCheck(id)" /></td>
										<td id="claim_ref_number"><input type="hidden"
											id="pmr_ref_No" name="pmr_ref_No"
											value="<%=recoveryData.getPmr_ref_No()%>"> <input
											type="hidden" id="claim_ref_number" name="claim_ref_number"
											value="<%=recoveryData.getClaim_ref_number()%>"> <%=recoveryData.getClaim_ref_number()%>
										</td>
										<td id="cgpan"><input type="hidden" id="cgpan"
											name="cgpan" value="<%=recoveryData.getCgpan()%>"> <%=recoveryData.getCgpan()%>
										</td>
										<td id="bankName"><input type="hidden" id="bankName"
											name="bankName" value="<%=recoveryData.getBankName()%>">
											<%=recoveryData.getBankName()%></td>
										<td id="promoterName"><input type="hidden"
											id="promoterName" name="promoterName"
											value="<%=recoveryData.getPromoterName()%>"> <%=recoveryData.getPromoterName()%>
										</td>
										<td id="unitName"><input type="hidden" id="unitName"
											name="unitName" value="<%=recoveryData.getUnitName()%>">
											<%=recoveryData.getUnitName()%></td>

										<td id="totalAmount"><input type="hidden"
											class="rowStyle" id="totalAmount<%=recoveryData.getSrNo()%>"
											step='0.01' name="totalAmount" readonly="readonly"
											value="<%=recoveryData.getTotalAmount()%>" /><%=recoveryData.getTotalAmount()%>
										</td>
										<td id="utrNumber"><input type="hidden" id="utrNumber"
											name="utrNumber" value="<%=recoveryData.getUtrNumber()%>">
											<%=recoveryData.getUtrNumber()%></td>


										<td id="utrDate"><input type="hidden" id="utrDate"
											name="utrDate" value="<%=recoveryData.getUtrDate()%>">
											<%=recoveryData.getUtrDate()%></td>

										<td id="dateOfrecovery"><input type="text"
											name="dateOfrecovery" Class="form-control"
											style="height: 20px; width: 80px; font-size: 13px"
											id="dateOfrecovery<%=recoveryData.getSrNo()%>"
											placeholder="Enter Date" onclick="setDateValue(id)"
											style="background-color: #fff;" /></td>

										<td id="type_of_Recovery"><select Class="form-control"
											style="width: 150px;"
											id="type_of_Recovery<%=recoveryData.getSrNo()%>"
											name="type_of_Recovery">
										    <!-- onchange="return setLegalValue()" name="type_of_Recovery"> -->
										    
												<option value="0">Select</option>
												<option value="Approve">Approve</option>
												<option value="Return For UTR">Return For UTR</option>
												<option value="Return For Amount">Return For Amount</option>
										</select></td>
										<td id="recId" style="display: none"><input type="text"
											id="recId<%=recoveryData.getSrNo()%>" name="recId"
											value="<%=recoveryData.getRecId()%>"></td>

									</tr>
									<%
										}
											} else {
									%>
									<tr>
										<td colspan="12" align="center">No Data Found</td>
									</tr>
									<%
										}
									%>
								</tbody>
							</table>
						</div>
						
						<%
							if (request.getAttribute("mliRecoveryList") != null) {
									ArrayList<MLIAppropriateClaimRecoveryData> list = (ArrayList) request.getAttribute("mliRecoveryList");
									if (list.size() > 0) {
						%>
						<div class="col-md-4" style="display: flex; font-weight: 800;">
							<input type="hidden" id="hdnVal" value="1" /> Total Amount:
							<span id="Total" style="color: brown; margin-left: 12px;">0.00
							</span>
							
						</div>
						
						<div class="col-md-8" style="text-align: center;float: left;">
							<button type="button" class="btn btn-primary"
								data-dismiss="modal" value="save" id="save" onclick="SaveData()">SAVE</button>
							<button type="button" class="btn btn-primary"
								data-dismiss="modal" value="cancel" id="cancel"
								onclick="CancelData()">CANCEL</button>
						
						</div>
						<%
							}
								} else {
						%>
						<div class="col-md-8"></div>
						<%
							}
						%>

					<html:hidden property="message" name="mliAppropriateRecoveryForm"
							styleId="message" />	
					</div>
				</div>

			</div>
		</div>
	</html:form>
</body>
</html>