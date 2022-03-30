<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.cgtsi.receiptspayments.DANSummary"%>
<%@ page import="com.cgtsi.receiptspayments.AllocationDetail"%>
<%@ page import="com.cgtsi.actionform.AppropriationActionForm"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="com.cgtsi.claim.ClaimConstants"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>


<%

String thiskey = "";
session.setAttribute("CurrentPage","allocatePaymentsAll.do?method=getApproPriationDetailsPage");

ArrayList disArray= new ArrayList();

disArray= (ArrayList)request.getAttribute("displayData");

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
function getvalue(val,pid){
var a = document.getElementById('id'+val).value;
document.getElementById('rpid'+val).value = pid;
document.getElementById('ids'+val).value = a;
}
function isFutureDate(val){
var idate = document.getElementById('date'+val).value;
   var today = new Date().getTime(),
       idate = idate.split("/");
   idate = new Date(idate[2], idate[1] - 1, idate[0]).getTime();
   if ((today - idate) < 0){
    alert ("You cannot enter Future Date!");
   }
}
function showDetails(val){
var myWindow = window.open("displayAppropriationDetails.do?method=displayCgpanDetails&val="+val, "_blank", "toolbar=yes,scrollbars=yes,resizable=yes,top=500,left=500,width=600,height=200");
}
//Added by Sarita on 17 FEB 2022 [START]
function setDateValue(id) {
		var start = new Date("2020-06-24"), end = new Date(), diff = new Date(
				end - start), days = diff / 1000 / 60 / 60 / 24;
		var d = "#" + id;
		$(d).datepicker({
			dateFormat : 'dd/mm/yy',
			//setStartDate:"02-05-2021",
			minDate : days * -1,
			maxDate : end
		}).attr('readonly', 'readonly').focus();
	}
//Added by Sarita on 17 FEB 2022 [END]
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
<html:form  name = "appropriationActionForm" type="com.cgtsi.actionform.AppropriationActionForm" action="displayAppropriationDetails.do?method=getApproPriationDetails" method="POST" >
	        <link rel="stylesheet" href="//code.jquery.com/ui/1.13.1/themes/base/jquery-ui.css">
  			<link rel="stylesheet" href="/resources/demos/style.css">
  			<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
  			<script src="https://code.jquery.com/ui/1.13.1/jquery-ui.js"></script>	
<TR>
<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
<TD>
<TABLE width="100%" border="0" align="left" cellpadding="1" cellspacing="1" id="danTable">
<TR>
<TD colspan="12">
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
<html:hidden property="bankId" name="appropriationActionForm"/>
<html:hidden property="zoneId" name="appropriationActionForm"/>
<html:hidden property="branchId" name="appropriationActionForm"/>
<tr>
<td colspan="12">
<table style="width:100%">
<tbody>
<TR>
<TD align="left" valign="top" class="ColumnBackground"><bean:message key="fromDate" /></TD>
<TD align="left" valign="top" class="tableData">
<div align="left">
<html:text property="dtFromDate" size="20" maxlength="10" alt="Reference" name="appropriationActionForm"/>
<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('appropriationActionForm.dtFromDate')" align="center">
</div>
</TD>
<TD align="left" valign="top" class="ColumnBackground"><bean:message key="toDate" /></TD>
<TD align="left" valign="top" class="tableData">
<div align="left">
<html:text property="dtToDate" size="20" maxlength="10" alt="Reference" name="appropriationActionForm"/>
<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('appropriationActionForm.dtToDate')" align="center">
</div>
</TD>
<td class="ColumnBackground">
<html:link href="<%//=url0%>" styleClass="btn btn-md btn-save" style="color:white;text-decoration:none;">Select DAN</html:link>
</td>
<TD align="left" valign="top" class="tableData">
<div class="form-group mb-10" style="text-align:center;">
<html:select property="mliNames" name="appropriationActionForm">
<html:option value="">Select</html:option>
<html:options property="allMliNames" name="appropriationActionForm"/>
</html:select>
</div>
</TD>
<td class="ColumnBackground">
<A href="javascript:submitForm('displayAppropriationDetails.do?method=getApproPriationDetails');"
style="background-color:white; color:rgba(70, 129, 179, 1); text-decoration:none; border-radius:5px; padding:5px 10px;">
<!-- <IMG src="images/Submit.gif" alt="Selest DAN" width="49" height="37" border="0"> -->
Submit
</A>
</td>
</TR>
</tbody>
</table>
</td>
</tr>
<TR>
<TD align="left" valign="top" class="ColumnBackground"><bean:message key="sNo" />
<%-- <html:checkbox name="rpAllocationForm" property="danChecked" value="Y" onclick="checkValidate()"/> --%>
</TD>
<TD align="left" valign="top" class="ColumnBackground"><bean:message key="mliName" /></TD>
                    <TD align="left" valign="top" class="ColumnBackground"><bean:message key="rpNumber" /></TD>
                    <TD align="left" valign="top" class="ColumnBackground"><bean:message key="pymntDate" /></TD>
                    <TD align="left" valign="top" class="ColumnBackground"><bean:message key="paymentMode" /></TD>
                    <TD align="left" valign="top" class="ColumnBackground"><bean:message key="pymntAmount" /></TD>
                    <TD align="left" valign="top" class="ColumnBackground"><bean:message key="utrno" /></TD>
<TD align="left" valign="top" class="ColumnBackground"><bean:message key="virtualAcNumber" /></TD>
<TD align="left" valign="top" class="ColumnBackground"><bean:message key="paymentRcvdDate" /></TD>
<TD align="left" valign="top" class="ColumnBackground"><bean:message key="returnLov" /></TD>
<%-- <TD align="left" valign="top" class="ColumnBackground"><bean:message key="guaranteeFee" /></TD>
 --%><!-- <TD align="left" valign="top" class="ColumnBackground">Select All</TD>
 --> </TR>
<% int i = 0;
String cgpan="";
%>
<%
if(disArray!=null && disArray.size()>0)
{
for(int ar=0;ar<disArray.size();ar++)
{
AppropriationActionForm summary =(AppropriationActionForm)disArray.get(ar) ;
%>
<TR>
<TD align="left" valign="top" class="tableData"><%=(i+1)%></TD>
<%-- <TD align="left" valign="top" class="tableData"><%//=danSummary.getBranchName()%></TD> --%>
<TD align="left" valign="top" class="tableData"><%=summary.getMliName()%></TD>
<html:hidden property="mliName" name="appropriationActionForm"/>
                    <TD align="left" valign="top" class="tableData"><a onclick="showDetails('<%=summary.getPaymentId() %>');" href="javascript:void(0);"><%=summary.getPaymentId()%> </a></TD>
                    <html:hidden property="paymentId" name="appropriationActionForm"/>
                    <TD align="left" valign="top" class="tableData"><%=summary.getPaymentDate()%></TD>
                    <html:hidden property="paymentDate" name="appropriationActionForm"/>
                    <TD align="left" valign="top" class="tableData"><%=summary.getPaymentMode()%></TD>
                    <html:hidden property="paymentMode" name="appropriationActionForm"/>
                    <TD align="left" valign="top" class="tableData"><%=summary.getPaymentAmount()%></TD>
                    <TD align="left" valign="top" class="tableData"><%=summary.getUtrNo()%></TD>
<TD align="left" valign="top" class="tableData"><%=summary.getVirtualAcNumber()%>
<input type="hidden" name="virtualAcNumber" value="<%= summary.getVirtualAcNumber() %>" />
</TD>
<%-- <html:hidden property="virtualAcNumber" name="appropriationActionForm"/>
 --%>
<TD align="left" valign="top" class="tableData">
<div align="left">
<%-- <input type="date" id="date<%=(i+1)%>" size="20" maxlength="10" alt="Reference" name="paymentRcvdDate" onblur="isFutureDate(<%=(i+1)%>)" />
  <IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar()"  align="center"> --%>
  <%-- <html:text property="paymentRcvdDate" styleId="date<%=(i+1)%>" size="20" maxlength="10" onblur="isFutureDate(<%=(i+1)%>)" alt="Reference"/> --%>
  <input type="text" name="paymentRcvdDate"  autocomplete="off"  id="date<%=(i+1)%>" 
                               placeholder="DD/MM/YYYY" onclick="setDateValue(id)"/>
  </div>
</TD>
<TD align="left" valign="top" class="tableData">
<%-- <html:select property="status" styleId="{ids<%=(i+1)%>" name="appropriationActionForm" onchange="getvalue()">
<html:option value="">Select</html:option>
<html:option value="Approve">Approve</html:option>
<html:option value="Reject">Reject</html:option>
</html:select>
<html:hidden property="status" name="appropriationActionForm"/> --%>
<select id="id<%=(i+1)%>" name="appropriationActionForm"   onchange="getvalue(<%=(i+1)%>,'<%=summary.getPaymentId()%>')">
  <option value="">Select</option>
  <option value="Approve">Approve</option>
  <option value="Reject">Return for UTR</option>
  <option value="Reject">Return for Amount</option>
 
</select>
<input type="hidden" id="ids<%=(i+1)%>" name="status" value=""/>
<input type="hidden" id="rpid<%=(i+1)%>" name="rpID" value=""/>
<%-- <html:hidden property="status" name="appropriationActionForm" styleId="ids<%=(i+1)%>"/>
 --%> </TD>
<%-- <TD align="left" valign="top" class="tableData">
<input type="hidden" id="hidpaymentAmountRP<%=(i+1)%>" value="<%=danSummary.getGuaranteeFee()%>"/>
<input type="checkbox" name="danChecked" value="" id="danChecked<%=(i+1)%>" onclick="checkValidate(this)"/>
<html:checkbox name="rpAllocationForm" property="danChecked" value="Y" onclick="checkValidate()"/>
</TD> --%>
<%--%><TD align="left" valign="top" class="tableData"> --%>
<%/* balance = amountDue - amountPaid;
String balanceValue=df.format(balance); */%>
<%//=balanceValue%>
<%-- </TD>
<TD align="left" valign="top" class="tableData"><%//=amountBeingPaid%></TD>                    
                    <TD align="left" valign="top" class="tableData"><%//=df.format(danSummary.getInclSTaxAmnt())%></TD>
                    <TD align="left" valign="top" class="tableData"><%//=df.format(danSummary.getInclECESSAmnt())%></TD>
                    <TD align="left" valign="top" class="tableData"><%//=df.format(danSummary.getInclHECESSAmnt())%></TD>                                        
<TD align="left" valign="top" class="tableData"> --%>
<%
                        /*  thiskey=danNoKey+ClaimConstants.CLM_DELIMITER1+balanceValue;
           checkboxKey="appropriatedFlags("+thiskey+")";
           String jsMethodDef="calcOnlineAllocatePayment("+balanceValue+","+(i+4)+")"; */
                    %>
<%-- <html:checkbox name="rpAllocationForm" property="<%//=checkboxKey%>" value="Y" onclick="<%//=jsMethodDef%>"/> --%>
<!-- </TD> -->
<!--<TD align="left" valign="top" class="tableData"></TD>-->
</TR>
<%-- </c:forEach> --%>
<%
++i;
}
}
//++i;
//}
%>
                   
             
<TR>
<TD align="center" valign="baseline" colspan="10">
<DIV align="center">
<A href="javascript:submitForm('displayAppropriationDetails.do?method=saveApproPriationDetails')">
<IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0">
</A>
<A href="javascript:document.appropriationActionForm.reset()">
<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0">
</A>
<A href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>">
<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0">
</A>
</DIV>
</TD>
</TR>
</TABLE>
</TD>

<TD width="20" background="images/TableVerticalRightBG.gif">&nbsp;</TD>
</TR>

<TR>
<TD width="20" align="right" valign="top">
<IMG src="images/TableLeftBottom1.gif" width="20" height="15">
</TD>
<TD background="images/TableBackground2.gif">
&nbsp;
</TD>
<TD width="20" align="left" valign="top">
<IMG src="images/TableRightBottom1.gif" width="23" height="15">
</TD>
</TR>
</html:form>
</TABLE>
</html>
