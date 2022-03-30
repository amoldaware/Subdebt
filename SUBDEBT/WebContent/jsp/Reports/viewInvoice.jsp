<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>

 <SCRIPT language="JavaScript" type="text/JavaScript" src="js/CGTSI.js">
</SCRIPT>
<SCRIPT language="JavaScript" type="text/JavaScript" src="js/selectdate.js">
</SCRIPT>
<LINK href="css/StyleSheet.css" rel="stylesheet" type="text/css">
 <script src="/csrfguard"></script>
<script>
	function validate() {
		var invoiceNumber = document.getElementById('invoicenumber').value;
		var invoiceNumber = document.getElementById('enterCgpan').value;
		return false;

	}
</script>

<%
	session.setAttribute("CurrentPage", "viewInvoice.do?method=viewInvoice");
%>

<table border="1" align="center">
	<html:form
		action="applicationHistoryReportDetails.do?method=applicationHistoryReportDetails"
		method="POST" enctype="multipart/form-data"
		onsubmit="return validate()">
		<tr>
			<TD align="left" valign="top" class="ColumnBackground">MIL ID:<html:text property="MLI_id" styleId="MLI_id"
					size="20" alt="MLI_id" maxlength="15" name="rsForm" /></td>
			<TD align="left" valign="top" class="ColumnBackground">
				<DIV align="center">
					<font color="#FF0000" size="2">*</font>
					<bean:message key="fromdate" />
				</DIV>
			</TD>
			<TD align="left" valign="middle" class="TableData">
				<DIV align="left">
					<%-- <html:text property="dateOfTheDocument26" size="15" maxlength="10"
						alt="Reference" name="rsForm" /> --%>
						<html:text property="invoiceFromDate" size="15" maxlength="10"
						alt="Reference" name="rsForm" />
					<IMG src="images/CalendarIcon.gif" width="20"
						onClick="showCalendar('rsForm.invoiceFromDate')"
						align="middle" alt="">
					<DIV align="left"></DIV>
				</DIV>
			</TD>

			<TD align="left" valign="top" class="ColumnBackground"><font
				color="#FF0000" size="2">*</font> <bean:message key="toDate" /></TD>

			<TD align="left" valign="middle" class="TableData">
				<DIV align="left">
					<%-- <html:text property="dateOfTheDocument27" size="15" maxlength="10"
						alt="Reference" name="rsForm" /> --%>
						<html:text property="invoiceToDate" size="15" maxlength="10"
						alt="Reference" name="rsForm" />
					<IMG src="images/CalendarIcon.gif" width="20"
						onClick="showCalendar('rsForm.invoiceToDate')"
						align="middle" alt="">
					<DIV align="left"></DIV>
				</DIV>
			</TD>

			<TD align="left" valign="top" class="ColumnBackground">Invoice
				Number:<html:text property="invoicenumber" styleId="invoicenumber"
					size="20" alt="InvoiceNumber" maxlength="15" name="rsForm" />
			</td>
			<TD align="left" valign="top" class="ColumnBackground">CGPAN:<html:text
					property="enterCgpan" styleId="enterCgpan" size="20" alt="Cgpan"
					maxlength="15" name="rsForm" /></td>
			<td><A
				href="javascript:submitForm('serchInvoice.do?method=getInvoiceDetails')">
					<IMG src="images/OK.gif" alt="OK" width="49" height="37" border="0">
			</A></td>
		</tr>
	</html:form>
</table>
