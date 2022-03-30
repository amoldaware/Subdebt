<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@page import ="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar" %>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","gfallocatePayments.do?method=submitGFDANPayments");
String danNo ;
int danIndex = 0;
String hiddenFieldName;
String remarks ;
%>

<script>

function typeOfPayment()
{
var modPaymentValue=findObj("modeOfPayment").value;

//alert(modPaymentValue);
if(modPaymentValue=="NEFT/RTGS")
{

//alert("ddfd");

document.forms[0].instrumentNo.disabled=true;
document.forms[0].paymentDate.disabled=true;
document.forms[0].drawnAtBank.disabled=true;
document.forms[0].drawnAtBranch.disabled=true;
document.forms[0].payableAt.disabled=true;
document.forms[0].instrumentDate.disabled=true;

//document.forms[0].paymentDate.value="";
//document.forms[0].drawnAtBank.value="";
//document.forms[0].drawnAtBranch.value="";
//document.forms[0].payableAt.value="";
//document.forms[0].instrumentDate.value="";
//document.forms[0].instrumentNo.value="";


}
else
{

document.forms[0].instrumentNo.disabled=false;
document.forms[0].paymentDate.disabled=false;
document.forms[0].drawnAtBank.disabled=false;
document.forms[0].drawnAtBranch.disabled=false;
document.forms[0].payableAt.disabled=false;
document.forms[0].instrumentDate.disabled=false;



}

}

function validPaymentDetails()

{
 //alert(check);
var modPaymentValue=findObj("modeOfPayment").value
//alert(modPaymentValue);

if(modPaymentValue=="NEFT/RTGS")
{
 document.rpAllocationForm.target ="_self";
                 document.rpAllocationForm.method="POST";
                 document.rpAllocationForm.action="gfallocatePayments.do?method=gfallocatePayments";
                 document.rpAllocationForm.submit();
				return true;

}

var payDate=findObj("paymentDate").value;
var instrumentNo=findObj("instrumentNo").value;
var drawnAtBank=findObj("drawnAtBank").value;
var drawnAtBranch=findObj("drawnAtBranch").value;
var payableAt=findObj("payableAt").value;
var instrumentDate=findObj("instrumentDate").value;

//alert(payDate);

if((payDate=="") || (payDate==null))

{
//alert("payment date should not be empty");
 //document.forms[0].paymentDate.style.backgroundColor = 'blue';
			// document.forms[0].paymentDate.focus();
				//return false;		

alert("payment date should not be empty");
document.forms[0].paymentDate.style.backgroundColor= 'orange';
document.forms[0].paymentDate.focus();
//document.forms[0].paymentDate.style.backgroundColor= '';
return false;

}
if((instrumentNo=="") || (instrumentNo==null))

{

alert("InstrumentNo  should not be empty");
document.forms[0].instrumentNo.style.backgroundColor= 'orange';
document.forms[0].instrumentNo.focus();
return false;

}
if((instrumentDate=="") || (instrumentDate==null))

{

alert("Instrument Date  should not be empty");
document.forms[0].instrumentDate.style.backgroundColor= 'orange';
document.forms[0].instrumentDate.focus();
return false;
}

if((drawnAtBank=="") || (drawnAtBank==null))

{

alert("Drawn At Bank  should not be empty");
document.forms[0].drawnAtBank.style.backgroundColor= 'orange';
document.forms[0].drawnAtBank.focus();
return false;

}

if((drawnAtBranch=="") || (drawnAtBranch==null))

{

alert("Drawn At Branch  should not be empty");
document.forms[0].drawnAtBranch.style.backgroundColor= 'orange';
document.forms[0].drawnAtBranch.focus();
return false;
}





if((payableAt=="") || (payableAt==null))

{

alert("payable At  should not be empty");
document.forms[0].payableAt.style.backgroundColor= 'orange';
document.forms[0].payableAt.focus();
return false;
}
//alert("payable At  should not be emptys");

  document.rpAllocationForm.target ="_self";
                 document.rpAllocationForm.method="POST";
                 document.rpAllocationForm.action="gfallocatePayments.do?method=gfallocatePayments";
                 document.rpAllocationForm.submit();
				return true;



}
function validateInstrument()
{
//alert("Validating Instrument Number Entered");
var instrumentNo = findObj("instrumentNo").value;
//alert("instrumentNo"+instrumentNo);
if(instrumentNo.value=""){
alert("Instrument Number is required");
return false;
}
//alert(instrumentNo.length);
if(instrumentNo.length < 6){
alert("Invalid Instrument Number " +instrumentNo +"  , the length of instrument number should be 6");
return false;
}
//alert(instrumentNo.value.length);
//alert("Validating Instrument Number Exited");
return true;
}
</script>
<script type="text/javascript">
function chkDate(){
		var paymentDate = document.forms[0].paymentDate.value;
		var currentdate = document.getElementById("currentDate").value;
		var pymtDayStr;
			  var pymtMonStr;
			  var pymtYearStr;
		var currDayStr;
		var currMonStr;
		var currYearStr;		
		var elem1 = paymentDate.split('/');  
		var elem2 = currentdate.split('/');		 
				
				pymtDayStr = elem1[0];  
				pymtMonStr = elem1[1];  
				pymtYearStr = elem1[2];
				currDayStr = elem2[0]; 
				currMonStr = elem2[1]; 
				currYearStr = elem2[2]; 
				
				/* if(memberid.isNaN()){
					alert("");
				} */
				
			if((pymtYearStr > currYearStr) || ((pymtYearStr == currYearStr) && (pymtMonStr > currMonStr)) || ((pymtYearStr == currYearStr) && (pymtMonStr == currMonStr) && (pymtDayStr > currDayStr))){

							alert("payment date can not be greater than current date");
							document.forms[0].paymentDate.value = '';
							document.forms[0].paymentDate.style.backgroundColor = 'yellow';
							document.forms[0].paymentDate.focus();
							return false;
				
			}
}
</script>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <html:errors />
<html:form action="gfallocatePayments.do?method=gfallocatePayments" method="POST" focus="modeOfPayment">
  
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/ReceiptsPaymentsHeading.gif" width="121" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			
			<TD>
				<TABLE width="100%" border="0" align="left" cellpadding="1" cellspacing="1">
				<TR>
					<TD colspan="10"> 
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="20%" class="Heading"><bean:message key="paymentDetails" /></TD>
							<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
						</TR>
						<TR>
							<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
						</TR>
						</TABLE>
					</TD>
				</TR>
				
				<%
					java.util.Calendar cal = java.util.Calendar.getInstance();
						int day = cal.get(Calendar.DATE);
						int month = cal.get(Calendar.MONTH)+1;
						int year = cal.get(Calendar.YEAR);
						
						String currentDate = day+"/"+month+"/"+year;
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						java.util.Date cdate = sdf.parse(currentDate);
						currentDate = sdf.format(cdate);
				%>
				<input type="hidden" name="currDate" id="currentDate" value="<%=currentDate%>" />
        <tr>
          <td><table width="100%" border="0" cellspacing="1" cellpadding="0">
		  <tr>
              <TD align="left" valign="top" class="ColumnBackground">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="modeOfPayment" /></td>
              <td class="tableData">
				  <html:select property="modeOfPayment" name="rpAllocationForm" onchange="typeOfPayment()">
						<html:option value="DD">DD</html:option>
						
						
						<%-- <html:options name="rpAllocationForm" property="instruments" /> --%>
				  </html:select>
			  </td>
            </tr>
            <tr>
              <TD align="left" valign="top" class="ColumnBackground"><bean:message key="collectingBankName"/></td>
              <td class="tableData"><bean:write name="rpAllocationForm" property="collectingBankName"/></td>
            </tr>
            <tr>
              <TD align="left" valign="top" class="ColumnBackground"><bean:message key="collectingBankBranch"/></td>
              <td class="tableData"><bean:write property="collectingBankBranch" name="rpAllocationForm"/></td>
            </tr>
            <tr>
              <TD align="left" valign="top" class="ColumnBackground"><bean:message key="CGTSIAccountHoldingBranch"/></td>
              <td class="tableData"><bean:write name="rpAllocationForm" property="accountNumber"/></td>
            </tr>
            <tr>
              <TD align="left" valign="top" class="ColumnBackground">&nbsp;<span id="instdate" name="instdate">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="paymentDate"/></span>
&nbsp;&nbsp;<i>(Date should be in DD/MM/YYYY format.)</i></td>
              <td class="tableData"><table cellpadding="0" cellspacing="0">
                <tr>
                  <td><html:text property="paymentDate" size="20" alt="Payment Date" name="rpAllocationForm" maxlength="10" onchange="chkDate();"/></td>
                  <td><img src="images/CalendarIcon.gif" width="20" align="center"
                  onClick="showCalendar('rpAllocationForm.paymentDate')"></td>
                </tr>

              </table>
              </td>
            </tr>
            <tr>
              <td colspan="2"><img src="images/clear.gif" width="5" height="15"></td>
            </tr>
            <tr>
              <td colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="35%" class="Heading"><bean:message key="instrumentDetails"/></td>
                  <td><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
                </tr>
                <tr>
                  <td colspan="2" class="Heading"><img src="images/clear.gif" width="5" height="5"></td>
                </tr>
              </table>
              </td>
            </tr>
            <tr>
              <TD align="left" valign="top" class="ColumnBackground">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="instrumentNumber"/></td>
              <td class="tableData"><html:text property="instrumentNo" size="20" alt="Instrument Number" name="rpAllocationForm" maxlength="6" onchange="validateInstrument()" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)" /></td>
            </tr>
            <tr>
              <TD align="left" valign="top" class="ColumnBackground">&nbsp;<span id="instdate" name="instdate">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="instrumentDate"/></span>
&nbsp;&nbsp;<i>(Date should be in DD/MM/YYYY format.)</i></td>
              <td class="tableData"><table cellpadding="0" cellspacing="0">
                <tr>
                  <td><html:text property="instrumentDate" size="20" alt="Instrument Date" name="rpAllocationForm" maxlength="10"/></td>
                  <td><img src="images/CalendarIcon.gif" width="20" align="center"
                  onClick="showCalendar('rpAllocationForm.instrumentDate')"></td>
                </tr>
              </table>
              </td>
            </tr>
            <tr>
              <TD align="left" valign="top" class="ColumnBackground"><bean:message key="instrumentAmount"/></td>
              <td class="tableData"><bean:write name="rpAllocationForm" property="instrumentAmount"/></td>
            </tr>
            <tr>
              <TD align="left" valign="top" class="ColumnBackground">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="drawnAtBank"/></td>
              <td class="tableData"><html:text property="drawnAtBank" size="20" alt="Drawn at Bank" name="rpAllocationForm" maxlength="100"/></td>
            </tr>
            <tr>
              <TD align="left" valign="top" class="ColumnBackground">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="drawnAtBranch"/></td>
              <td class="tableData"><html:text property="drawnAtBranch" size="20" alt="Drawn at Branch" name="rpAllocationForm" maxlength="50"/></td>
            </tr>
            <tr><td><font color="#FF0000" size="2">The Instrument should be Payable At Mumbai&nbsp;</font></td></tr>
            <tr>
              <TD align="left" valign="top" class="ColumnBackground">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="payableAt"/></td>
              <td class="tableData"><html:text property="payableAt" size="20" alt="Payable at" name="rpAllocationForm" maxlength="50"/></td>
            </tr>
          </table>
          </td>
        </tr>
        
        <tr>
        <td>
        <table width="100%" border="0" cellspacing="1" cellpadding="0">
        <TR >
						<TD align="left" colspan="4" valign="top" class="SubHeading">
							Service Category : Other Taxable Services - Other Than The 119 LISTED
						</TD>
					</TR>
					<TR >
						
                                                <td align="left" valign="top" class="ColumnBackground"><div align="leftt">&nbsp;Service Tax Number:</div></td>
						
						<TD align="left" valign="top" class="ColumnBackground"><div align="leftt">&nbsp;AAATC2613DSD001</div></td>
						
						<td align="left" valign="top" class="ColumnBackground"><div align="leftt">&nbsp;PAN Number:</div></td>
										
						<td align="left" valign="top" class="ColumnBackground"><div align="leftt">&nbsp;AAATC2613D</div></TD>
						
	</TR>
        </table>
        </td>
        </tr>
        
        
        <tr>
          <td><img src="clear.gif" width="5" height="15"></td>
        </tr>
        <tr align="center" valign="baseline">
          <td>
			<a href="#" onclick="validPaymentDetails();"><img src="images/Save.gif" alt="Save"
            width="49" height="37" border="0"></a>
		<%--	<a href="gfallocatePayments.do?method=gfallocatePayments"><img src="images/Save.gif" alt="Save"
            width="49" height="37" border="0"></a> --%>
		  <a href="javascript:document.rpAllocationForm.reset()">
          <img src="images/Reset.gif" alt="Reset" width="49" height="37"
            border="0"></a>
          </td>
        </tr>
      </table>
      </td>
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
  </table>