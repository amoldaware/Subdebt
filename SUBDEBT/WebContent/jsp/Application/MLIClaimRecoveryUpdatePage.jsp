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
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<style type="text/css">
.ui-datepicker {
	background-color: #fff;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
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
			document.mliRecoveryForm.action = "mliClaimRecovery.do?method=getClaimRecoveryDataUpdate";
			document.mliRecoveryForm.submit();
		}
	}
	function CancelData() {
		document.mliRecoveryForm.target = "_self";
		document.mliRecoveryForm.method = "POST";
		document.mliRecoveryForm.action = "mliClaimRecovery.do?method=cancelData";
		document.mliRecoveryForm.submit();
	}
	function setDateValue(id) {
		var d = "#" + id;
		$(d).datepicker({
			dateFormat : 'dd/mm/yy',
		}).attr('readonly', 'readonly').focus();
	}
	function SaveData() {
		var count = 0;
		var countUtr_number = 0;
		var countUtr_date = 0;

		var msg = "<b>Please correct the following Error:</b><ul>";
		var finalData = [];

		$('#userInfo tbody tr').each(function() {
			var sno = $(this).find("td").eq(0).html();

			var utr_number = $("#utr_number" + sno).val();
			var utr_date = $("#utr_date" + sno).val();
			var status = $("#status" + sno).val();

			finalData.push({
				"utr_number" : utr_number,
				"utr_date" : utr_date,
				"status" : status,
			});
		});
		jQuery.each(finalData, function(i, val) {
			i++;
			var utr_numberVal = val.utr_number;
			var utr_dateVal = val.utr_date;
			var statusVal = val.status;

			if ((statusVal == "Y-" + i)) {
				count = 1;
			}
			if (((statusVal == "Y-" + i) && (utr_numberVal == ""))
					|| ((statusVal != "Y-" + i) && (utr_numberVal != ""))) {
				countUtr_number = 1;
			}
			if (((statusVal == "Y-" + i) && (utr_dateVal == ""))
					|| ((statusVal != "Y-" + i) && (utr_dateVal != ""))) {
				countUtr_date = 1;
			}
		});
		if (count == 0) {
			msg = msg
					+ "<li>Please Select alteast one record to Proceed!!</li>";
		}
		if (countUtr_number == 1) {
			msg = msg
					+ "<li>Please Enter Valid UTR Number!! OR Please Select Checkbox for Entered UTR Number</li>";
		}
		if (countUtr_date == 1) {
			msg = msg
					+ "<li>Please Enter Valid Utr Date!! OR Please Select Checkbox for Entered UTR Date</li>";
		}

		if (count == 0 || countUtr_number == 1 || countUtr_date == 1) {
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

		if (count == 1 && countUtr_number == 0 && countUtr_date == 0) {
			$("#Save").attr("disabled", "disabled");
			$("#Save")[0].innerText = 'loading';
			setTimeout(function() {
				$("#Save")[0].innerText = "Save";
			}, 8000);

			document.mliRecoveryForm.target = "_self";
			document.mliRecoveryForm.method = "POST";
			document.mliRecoveryForm.action = "mliClaimRecovery.do?method=updateClaimRecoveryData";
			document.mliRecoveryForm.submit();

		}
	}
</script>
<body>

	<html:form name="mliRecoveryForm"
		type="com.cgtsi.actionform.MLIClaimRecoveryForm"
		action="mliClaimRecovery.do?method=updateClaimRecoveryData"
		method="POST">

		<div class="row"
			style="margin-left: 20px; padding-top: 10px; padding-bottom: 10px; border-top: 1px solid brown; border-left: 1px solid brown; border-right: 1px solid brown; border-bottom: 1px solid brown;">
			<div class="container row">
				<div class="col-md-12">
				<%
					if(request.getAttribute("claimRefNo")!= null && !request.getAttribute("claimRefNo").equals(""))
					{
				%>
					<div class="col-sm-10"
						style='font-weight: 800; margin-top: 15px; margin-bottom: 16px; text-align: center;'>
						<h4 class="modal-title">Claim Recovery Details</h4>
					</div>
			    <%
					} else {
			    %>
			    	<div class="col-sm-10"
						style='font-weight: 800; margin-top: 15px; margin-bottom: 16px; text-align: center;'>
						<h4 class="modal-title">UTR Update Details</h4>
					</div>
			    <%
					}
			    %>
					<!-- <div class="col-sm-2">
				<button type="button" class="btn btn-primary" data-dismiss="modal"
					onclick="getData()">Refresh</button>
			</div> -->
				
				
				<div class="row" style="margin-top: 10px;">
						<div class="col-md-12" id="errormsg" style="display: none"></div>
						<div class="col-md-1">&nbsp;</div>
						<div class="col-md-4">
							<label for="claimRefNo" style="font-weight: 600;">Enter
								Claim Reference Number:</label><font color="#FF0000" size="2">*</font>
							<html:text styleClass="form-control" property="claimRefNo"
								maxlength="12" name="mliRecoveryForm" styleId="claimRefNo">
							</html:text>
						</div>
						<div class="col-md-1" style="margin-top: 32px;">
							<button type="button" class="btn btn-primary" id="btnSearch"
								onclick="return getData()">Search</button>
						</div>
						<div class="col-md-6">&nbsp;</div>
					</div>
				
					<div class="row">
					<div class="col-lg-12" style="margin-top: 10px;">
						<html:hidden property="message" name="mliRecoveryForm"
							styleId="message" />

						<table id="userInfo" class="table table-striped" id="userInfo"
							style="font-size: 13px;">
							<thead>
								<tr>
									<th scope="col">S.No.</th>
									<!-- <th scope="col">REC ID</th> -->
									<th scope="col">PMR REFERENCE NO</th>
									<th scope="col">CGPAN</th>
									<th scope="col">MEMBER ID</th>
									<th scope="col">CLAIM REFERENCE NO</th>
									<th scope="col">PROMOTER NAME</th>
									<th scope="col">UNIT NAME</th>
									<th scope="col">TOTAL AMOUNT</th>
									<th scope="col">UTR NUMBER</th>
									<th scope="col">UTR DATE</th>
									<th scope="col">UPDATE</th>
								</tr>
							</thead>
							<tbody>
								<%
									if (request.getAttribute("mliRecoveryList") != null) {
											ArrayList<MLIClaimRecoveryData> list = (ArrayList) request.getAttribute("mliRecoveryList");
											Iterator<MLIClaimRecoveryData> iterator = list.iterator();
											while (iterator.hasNext()) {
												MLIClaimRecoveryData recoveryData = iterator.next();
								%>
								<tr>
									<td scope="row"><%=recoveryData.getSrNo()%></td>
									<%-- <td id="rec_id"><%=recoveryData.getRec_id()%> </td> --%>
									<input type="hidden" id="rec_id" name="rec_id"
										value="<%=recoveryData.getRec_id()%>"/>
									<td id="pmr_ref_No"><%=recoveryData.getPmr_ref_No()%></td>
									<td id="cgpan"><%=recoveryData.getCgpan()%></td>
									<td id="memberId"><%=recoveryData.getMemberId()%></td>
									<td id="claim_ref_number"><%=recoveryData.getClaim_ref_number()%>
										<input type="hidden" id="claim_ref_number"
										name="claim_ref_number"
										value="<%=recoveryData.getClaim_ref_number()%>"></td>
									<td id="promoterName"><%=recoveryData.getPromoterName()%></td>
									<td id="unitName"><%=recoveryData.getUnitName()%></td>
									<td id="totalAmount"><%=recoveryData.getTotalAmount()%></td>

									<td id="utr_number">
									<input type="text" name="utr_number" Class="form-control"
									style="height: 25px; width:180px; font-size: 13px" maxlength="20"
										id="utr_number<%=recoveryData.getSrNo()%>" />
									</td>
									<td id="utr_date"><input type="text" name="utr_date" 
										style="height: 25px; width:120px; font-size: 13px"
										id="utr_date<%=recoveryData.getSrNo()%>"
										placeholder="Enter Date" onclick="setDateValue(id)" style="background-color: #fff;"/>
									</td>
									<td id="status" align="center"><input name="status"
										id="status<%=recoveryData.getSrNo()%>" type="checkbox"
										value="N-<%=recoveryData.getSrNo()%>"
										onClick="return getCheck(id)" /></td>
								</tr>
								<%
									}
										} else {
								%>
								<tr>
									<td align="center" colspan="12" style="font-size: medium;">No
										Data Found</td>
								</tr>
								<%
									}
								%>
							</tbody>
						</table>
					</div>
					</div>
					<%
						ArrayList<MLIClaimRecoveryData> list1 = (ArrayList) request.getAttribute("mliRecoveryList");
							if (request.getAttribute("mliRecoveryList") != null && (list1 != null && list1.size() > 0)) {
					%>
					<div class="col-lg-4" style="margin-left: 550px;">
						<button type="button" class="btn btn-primary" data-dismiss="modal"
							value="Save" id="Save" onclick="SaveData()">Save</button>
						<button type="button" class="btn btn-primary" data-dismiss="modal"
							value="cancel" id="cancel" onclick="CancelData()">CANCEL</button>
					</div>
					<%
						} else {
					%>
					<div class="col-lg-4"></div>
					<%
						}
					%>
				</div>
				<div class="col-lg-4"></div>
			</div>
		</div>
	</html:form>
</body>
</html>