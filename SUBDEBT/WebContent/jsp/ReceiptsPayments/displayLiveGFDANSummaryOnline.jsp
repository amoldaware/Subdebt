<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.cgtsi.receiptspayments.DANSummary"%>
<%@ page import="com.cgtsi.receiptspayments.AllocationDetail"%>
<%@ page import="com.cgtsi.actionform.RPActionForm"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="com.cgtsi.claim.ClaimConstants"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  --%>

<%

String thiskey = "";
session.setAttribute("CurrentPage","allocatePaymentsAll.do?method=getPendingGFDANsLiveOnline");

ArrayList disArray= new ArrayList();

disArray= (ArrayList)request.getAttribute("danSummaries");

System.out.println("disArray="+ disArray);


%>

<% 
String sdanDate;
SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
String allocate="N" ;
%>

<script type="text/javascript">
	window.onload= function(){
		document.getElementById("msg").focus();
	}
</script>
<html>
	<head>
		<script type="text/javascript">
				function checkValidate(source)
				{
					alert('checkValidateaaawww');
					
					
					checkboxes = document.getElementsByName('danChecked');
					/* for (var i = 0, n = checkboxes.length; i < n; i++)
					{
		                checkboxes[i].checked = source.checked;
		            } */
		            var chks = document.getElementsByTagName("INPUT");
		            var paymentAmount=0;
		            for (var i = 0; i < checkboxes.length; i++) 
		            {
		                if (checkboxes[i].checked) 
		                {
		                    //selected.push(chks[i].value); paymentAmountRP 
		                     alert(document.getElementById("hidpaymentAmountRP"+i).value +'==========')
		                    // document.getElementById("paymentAmountRP").value=
		                     alert('checked' + document.getElementById("paymentAmountRP").value);
		                     paymentAmount = paymentAmount + document.getElementById("paymentAmountRP").value;
		                }
		            }
				}
		</script>
		
	</head>

<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form  name = "rpAllocationForm" type="com.cgtsi.actionform.RPActionForm" action="danPaymentDetails.do?method=getLiveGFDANsPaymentDetailsOnline" method="POST" >
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/ReceiptsPaymentsHeading.gif" width="121" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			
			<TD>
				<TABLE width="100%" border="0" align="left" cellpadding="1" cellspacing="1" id="danTable">
				<TR>
					<TD colspan="13"> 
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="20%" class="Heading"><bean:message key="selectDanHeader" /></TD>
							<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
						</TR>
						<TR>
							<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
						</TR>
						</TABLE>
					</TD>
				</TR>
				
				<html:hidden property="bankId" name="rpAllocationForm"/>
				<html:hidden property="zoneId" name="rpAllocationForm"/>
				<html:hidden property="branchId" name="rpAllocationForm"/>
				
				<TR>
					<TD align="left" valign="top" class="ColumnBackground"><bean:message key="danDetail" /></TD>
					<TD align="left" valign="top" class="tableData">
						<input name="danIdTxt" class="form-control" id="danIdTxt"   type="text" size="20"  value="" autocomplete="off">
						<%-- <bean:write name="rpAllocationForm" property="memberId"/> --%>
					</TD>
					<TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="fromDate" /></TD>
					<TD align="left" valign="top" class="tableData">
						<div align="left">
							<html:text property="dtFromDate" size="20" maxlength="10" alt="Reference" name="rpAllocationForm"/>
							<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rpAllocationForm.dtFromDate')" align="center">
						</div>
					</TD>
					<TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="toDate" /></TD>
					<TD align="left" valign="top" class="tableData">
						<div align="left">
							<html:text property="dtToDate" size="20" maxlength="10" alt="Reference" name="rpAllocationForm"/>
							<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rpAllocationForm.dtToDate')" align="center">
						</div>
					</TD>
					
				</TR>
				<tr>
				<TD align="left" valign="top" class="tableData">
						<div class="form-group mb-100" style="text-align:center;">
						<A href="javascript:submitForm('allocatePaymentFinal.do?method=allocatePaymentFinal');"
						style="padding:6px 13px; background:#1c4593; color:white;border-radius:4px;font-size:13px;">
								submit
						</A>
						</div>
					</TD>
				</tr>
</html:form>
</TABLE>
<table>
<tr>
				<td>
			<div id="text6"  style="display:block;">CGTMSE account details for Guarantee fee Payment:</div>
			<div id="text4"  style="display:block;">Name of the Bank: State Bank of India</div>
			<div id="text5"  style="display:block;">Name of the Branch: Bandra Kurla Complex</div>
			<div id="text1"  style="display:block;">Account Name: Credit Guarantee Fund Trust for Micro and Small Enterprises</div>
			<div id="text2" style="display:block;">Account Number: 37817825400</div>
			<div id="text3" style="display:block;">IFSC Code: SBIN0004380</div>	
				</td>
				</tr>
</table>

</html>