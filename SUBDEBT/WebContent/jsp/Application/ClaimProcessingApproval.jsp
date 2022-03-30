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
<script type="text/javascript">
	function getApplicationDtls(bankId) {
		document.claimProcessingApprovalForm.target = "_self";
		document.claimProcessingApprovalForm.method = "POST";
		document.claimProcessingApprovalForm.action = "claimProcessingApproval.do?method=getClaimProcessingApproval&bankIdd="
				+ bankId;
		document.claimProcessingApprovalForm.submit();
	}
	function getSelectedClaimInfo() {
		document.claimProcessingApprovalForm.target = "_self";
		document.claimProcessingApprovalForm.method = "POST";
		document.claimProcessingApprovalForm.action = "claimProcessingApproval.do?method=getApprovalDetails";
		document.claimProcessingApprovalForm.submit();
	}
</script>
</head>
<body bgcolor="#FFFFFF" topmargin="0"
	data-new-gr-c-s-check-loaded="14.1008.0" data-gr-ext-installed="">
	<%--    <html:form name="form1"> --%>
	<html:form name="claimProcessingApprovalForm"
		type="com.cgtsi.actionform.ClaimProcessingApprovalActionForm"
		action="claimProcessingApproval.do?method=getClaimProcessingApproval"
		method="POST">
		<div class="container row">
			<!-- style="margin-left: 20px; padding-top: 10px; padding-bottom: 10px; border-top: 1px solid brown; border-left: 1px solid brown; border-right: 1px solid brown; border-bottom: 1px solid brown;" -->
			<div class="col-lg-12">
				<div>
					<div class="row" style="margin-top: 10px;">
						<div class="modal-body">

							<div class="col-md-4" style="margin-bottom: 10px">
							<label>Select Claim Type</label>
								<html:select styleClass="form-control" property="selectedClaim"
									name="claimProcessingApprovalForm" styleId="selectedClaim"
									onchange="return getSelectedClaimInfo()">
									<html:option value="1">First Claim</html:option>
									<html:option value="2">Second Claim</html:option>
								</html:select>
							</div>
							<table class="table table-striped" style="font-size: 13px;"
								border="1px">
								<thead class="alert alert-primary">
									<tr>
										<!-- <th scope="col">BankId</th> -->
										<th scope="col">Bank Name</th>
										<th scope="col">No Of Application</th>
									</tr>
								</thead>
								<tbody>
									<%
											if (request.getAttribute("approvalDtls") != null
												&& !request.getAttribute("approvalDtls").toString().equals("[]")) {
												ArrayList<ClaimProcessingApprovalActionForm> list = (ArrayList) request.getAttribute("approvalDtls");
												Iterator<ClaimProcessingApprovalActionForm> iterator = list.iterator();
												while (iterator.hasNext()) {
													ClaimProcessingApprovalActionForm data = iterator.next();
									%>
									<tr>

										<%-- <td><%=data.getBankId()%></td> --%>
										<td><%=data.getBankName()%></td>
										<td><a href="#"
											onclick="getApplicationDtls('<%=data.getBankId()%>');"><%=data.getNoOfApplication()%></a></td>

									</tr>
									<%
										}
											} else {
									%>
									<tr>
										<td colspan="2" align="center">No Data Found</td>
									</tr>
									<%
										}
									%>
								</tbody>
							</table>
						</div>

						<html:hidden property="message" name="claimProcessingApprovalForm"
							styleId="message" />
						<div class="col-md-3 " style="margin-top: 10px;">&nbsp;</div>
					</div>
				</div>
			</div>
		</div>
	</html:form>
</body>
</html>
