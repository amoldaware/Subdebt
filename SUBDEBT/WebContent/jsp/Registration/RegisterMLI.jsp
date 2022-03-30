<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<% session.setAttribute("CurrentPage","showRegisterMLI.do?method=showRegisterMLI");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>

<script type="text/javascript">
function init() {
if (!document.getElementById) return false;
var f = document.getElementById('auto_off');
var u = f.elements[0];
f.setAttribute("autocomplete", "off");
u.focus();
}
</script>
<script>

function clickIE4(){
    if (event.button==2){
                     //  alert(message);
                       return false;
                       }
         }

         function clickNS4(e){
                       if (document.layers||document.getElementById&&!document.all){
                                      if (e.which==2||e.which==3){
                                               // alert(message);
                                                return false;
                       }
               }
         }

         if (document.layers){
                       document.captureEvents(Event.MOUSEDOWN);
                       document.onmousedown=clickNS4;
         }

         else if (document.all&&!document.getElementById){
                       document.onmousedown=clickIE4;
         }   
         document.oncontextmenu=new Function(";return false;");
         
         
         function callWaiting(){
        		document.getElementById("waitId").style.display="block";
        	} 
         
   //=====================================================================
function disableselect(e) {
return false;
}

function reEnable() {
return true;
}

document.onselectstart = new Function("return false");

if (window.sidebar) {
document.onmousedown = disableselect;
document.onclick = reEnable;
}
</script>
<script src="/csrfguard"></script>
<%
String focusflag = "";
if(request.getAttribute("district")!=null && request.getAttribute("district").equals("1"))
{
	focusflag="district";
}
else{

	focusflag="bankName";
}
%>
<HTML>
	<head>
	
		<script type="text/javascript"> 
        window.history.forward(); 
        function noBack() { 
            window.history.forward(); 
        } 
    </script> 
		
	</head>
	<body onLoad='init()',onkeydown="return (event.keyCode != 116);">
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
<%
// this focusField is a variable which will point to the field which has to be focused in case of no errors.
String focusField=focusflag;
org.apache.struts.action.ActionErrors errors = (org.apache.struts.action.ActionErrors)request.getAttribute(org.apache.struts.Globals.ERROR_KEY);
if (errors!=null && !errors.isEmpty())
{
            focusField="test";
}
%>
<html:form action="showRegisterMLI.do" method="POST" focus="<%=focusField%>" styleId="auto_off">
<html:hidden name="regForm" property="test"/>
<html:errors />
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
						<DIV align="right">			
				<A HREF="javascript:submitForm('helpRegisterMLI.do?method=helpRegisterMLI')">
			    HELP</A>
			</DIV>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TD  align="left" colspan="4"><font size="2"><bold>
										Fields marked as </font><font color="#FF0000" size="2">*</font><font size="2"> are mandatory</bold></font>
								</td>
						<TR>
									<TD colspan="4"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="31%" class="Heading"><bean:message key="registerMLIHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>
								</TR>
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="mliType" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:select property="mliType" name="regForm">
											<html:option value="">Select</html:option>
											<html:options property="mliTypes" name="regForm"/>
										</html:select>
									</TD>
									<TD align="left" class="TableData"> </TD>
								</TR>

<!-- ashutosh  -->
                                   <TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="regFees" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="regFees" size="20" alt="Registration Fees" name="regForm" value="1.5%" maxlength="100" disabled="true"/>
									</TD>
                                   <TD align="left" class="TableData"> </TD>
								</TR>
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="bankName" />
									</TD>
									
									<TD align="left" class="TableData"> 
										<html:text property="bankName" size="20" alt="Bank name" name="regForm" maxlength="100"/>
									</TD>
									<TD align="left" class="TableData"> </TD>
								</TR>
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="shortName" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="shortName" size="20" alt="short name" name="regForm" maxlength="10"/>
									</TD>
									<TD align="left" class="TableData"> </TD>
								</TR>		

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="bankAddress" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:textarea property="address" cols="20" alt="Bank Address" name="regForm"/>
									</TD>
									<TD align="left" class="TableData"> </TD>
								</TR>

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="city" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="city" size="20" alt="City" name="regForm" maxlength="20"/>
									</TD>
									<TD align="left" class="TableData"> </TD>
								</TR>

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="state" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:select property="state" name="regForm" onchange="javascript:submitForm('showRegisterMLI.do?method=getDistricts')">
											<html:option value="">Select</html:option>
											<html:options property="states" name="regForm"/>				
										</html:select>
									</TD>
									<TD align="left" class="TableData"> </TD>
								</TR>

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="district" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:select property="district" name="regForm">
											<html:option value="">Select</html:option>
											<html:options property="districts" name="regForm"/>
										</html:select>
									</TD>
									<TD align="left" class="TableData"> </TD>
								</TR>
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="pinCode" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="pin" size="5" maxlength="6" alt="pinCode" name="regForm" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>
									</TD>
									
									<TD align="left" class="TableData"> </TD>
								</TR>	

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="phoneNo" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="phoneStdCode" size="10" alt="Phone No" name="regForm" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)" maxlength="5"/>&nbsp;&nbsp;-&nbsp;&nbsp;
										<html:text property="phone" size="10" alt="Phone No" name="regForm"  onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)" maxlength="15"/>
									</TD>
									<TD align="left" class="TableData"> </TD>
								</TR>

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="faxNo" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="faxStdCode" size="10" alt="Fax No" name="regForm" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)" maxlength="10"/>&nbsp;&nbsp;-&nbsp;&nbsp;
										<html:text property="fax" size="10" alt="Fax No" name="regForm"  onkeyup="isValidNumber(this)" maxlength="15"/>
									</TD>
									<TD align="left" class="TableData"> </TD>
								</TR>								

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="emailId" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="emailId" size="20" alt="Email Address" styleId="emailId" name="regForm" maxlength="50"/>
									</TD>
									<TD align="left" class="TableData"> </TD>

								<!--<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="deliveryOfDAN"/>
									</TD>
									<TD align="left" valign="top" class="TableData">
										<html:multibox name="regForm" value="Mail" property="danDelivery"/><bean:message key="mail"/>
										<html:multibox name="regForm" value="EMail" property="danDelivery"/><bean:message key="eMail"/>
										<html:multibox name="regForm" value="HardCopy" property="danDelivery"/><bean:message key="hardCopy"/>	
									</TD>
									<TD align="left" class="TableData"> </TD>
								</TR>
								
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="supportMCGF"/>							
									</TD>
									<TD align="left" valign="top" class="TableData">
										<html:radio name="regForm" value="Y" property="supportMCGF" ><bean:message key="yes" /></html:radio>&nbsp;&nbsp;
										<html:radio name="regForm" value="N" property="supportMCGF" ><bean:message key="no" /></html:radio>
									</TD>
									<TD align="left" class="TableData"> </TD>
								</TR>-->
							
							</TABLE>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TD  align="left" colspan="4"><font size="2"><bold></td>
								<TR>
									<TD colspan="4"> </TD>
								</TR>
								<!-- Start Avanish -->
								 <%-- <TR align="left" id="hideTable">
									<TD align="center" valign="top" class="ColumnBackground" >
										&nbsp;
									</TD>
									<TD align="center" valign="top" class="ColumnBackground_center_MLI_registration" colspan="2"  >
										&nbsp;<b>Working Capital</b>
									</TD>
									<TD align="left" valign="top" class="ColumnBackground_center_MLI_registration" colspan="2">
										&nbsp;<b>Term Loan/TC</b>
									</TD>
									
									<TD align="left" valign="top" class="ColumnBackground_center_MLI_registration" colspan="2">
										&nbsp;<b>WCTL OR WCTC</b>
									</TD>
									
								</TR>
								
								<TR align="left" id="hideTable1">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;Slab (Amount in Rs) Outstanding as on 29.2.2020
									</TD>
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font>Number of Borrowers (ony commercial; no individual loans )
									</TD>
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font>Total Amount
									</TD>
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font>Number of Borrowers (ony commercial; no individual loans )
									</TD>
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font>Total Amount
									</TD>
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font>Number of Borrowers (ony commercial; no individual loans )
									</TD>
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font>Total Amount
									</TD>
									
								</TR>
								
								<TR align="left" id="hideTable2">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font>0-10 Lacs
									</TD>
									
									<TD align="left" class="TableData"> 
										<html:hidden property="zeroToTenLakhNoOfBorrowrs"  value="0" name="regForm"  onkeyup="isValidNumber(this)"/>
										
									</TD>
									<TD align="left" class="TableData"> 
										<html:hidden property="zeroToTenLakhTotAmt"  name="regForm" value="0"  onkeyup="isValidNumber(this)" />
									</TD>
									
									<TD align="left" class="TableData"> 
										<html:hidden property="zeroToTenLakhNoOfBorrowrsTC" value="0"   name="regForm"  onkeyup="isValidNumber(this)"/>
										
									</TD>
									<TD align="left" class="TableData"> 
										<html:hidden property="zeroToTenLakhTotAmtTC" value="0"  name="regForm"  onkeyup="isValidNumber(this)" />
									</TD>
									
									<TD align="left" class="TableData"> 
										<html:hidden property="zeroToTenLakhNoOfBorrowrsWCTL"  value="0" name="regForm"  onkeyup="isValidNumber(this)"/>
										
									</TD>
									<TD align="left" class="TableData"> 
										<html:hidden property="zeroToTenLakhTotAmtWCTL"  name="regForm" value="0"  onkeyup="isValidNumber(this)"/>
									</TD>
									
								</TR>
								
								<TR align="left" id="hideTable3">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font>10 Lacs-50 Lacs
									</TD>
									
									<TD align="left" class="TableData"> 
										<html:hidden property="tenToFiftyLakhNoOfBorrowrs" value="0"  name="regForm"  onkeyup="isValidNumber(this)" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:hidden property="tenToFiftyLakhTotAmt" value="0"  name="regForm"  onkeyup="isValidNumber(this)" />
									</TD>
									
									<TD align="left" class="TableData"> 
										<html:hidden property="tenToFiftyLakhNoOfBorrowrsTC" value="0"  name="regForm"  onkeyup="isValidNumber(this)" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:hidden property="tenToFiftyLakhTotAmtTC"  value="0" name="regForm"  onkeyup="isValidNumber(this)" />
									</TD>
									
									<TD align="left" class="TableData"> 
										<html:hidden property="tenToFiftyLakhNoOfBorrowrsWCTL" value="0"  name="regForm"  onkeyup="isValidNumber(this)" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:hidden property="tenToFiftyLakhTotAmtWCTL" value="0" name="regForm"  onkeyup="isValidNumber(this)" />
									</TD>
								</TR>
								
								<TR align="left" id="hideTable4">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font>50 Lacs- 1 Crore
									</TD>
									
									<TD align="left" class="TableData"> 
										<html:hidden property="fiftyLakhToOneCrNoOfBorrowrs"  value="0" name="regForm" onkeyup="isValidNumber(this)" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:hidden property="fiftyLakhToOneCrTotAmt" value="0"  name="regForm"  onkeyup="isValidNumber(this)" />
									</TD>
									
									<TD align="left" class="TableData"> 
										<html:hidden property="fiftyLakhToOneCrNoOfBorrowrsTC"  value="0" name="regForm" onkeyup="isValidNumber(this)" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:hidden property="fiftyLakhToOneCrTotAmtTC" value="0"   name="regForm"  onkeyup="isValidNumber(this)" />
									</TD>
									
									<TD align="left" class="TableData"> 
										<html:hidden property="fiftyLakhToOneCrNoOfBorrowrsWCTL" value="0" name="regForm" onkeyup="isValidNumber(this)" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:hidden property="fiftyLakhToOneCrTotAmtWCTL" value="0"  name="regForm"  onkeyup="isValidNumber(this)" />
									</TD>
								</TR> 
								
								<TR align="left" id="hideTable5">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font>1 Crore-5 Crores
									</TD>
									
									<TD align="left" class="TableData"> 
										<html:hidden property="oneCrToFiveCrNoOfBorrowrs"  value="0" name="regForm"  onkeyup="isValidNumber(this)" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:hidden property="oneCrToFiveCrTotAmt" value="0"  name="regForm"  onkeyup="isValidNumber(this)" />
									</TD>
								
									
									<TD align="left" class="TableData"> 
										<html:hidden property="oneCrToFiveCrNoOfBorrowrsTC"  value="0" name="regForm"  onkeyup="isValidNumber(this)" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:hidden property="oneCrToFiveCrTotAmtTC" value="0"  name="regForm"  onkeyup="isValidNumber(this)" />
									</TD>
								
									
									<TD align="left" class="TableData"> 
										<html:hidden property="oneCrToFiveCrNoOfBorrowrsWCTL" value="0" name="regForm"  onkeyup="isValidNumber(this)" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:hidden property="oneCrToFiveCrTotAmtWCTL"  value="0" name="regForm"  onkeyup="isValidNumber(this)" />
									</TD>
								</TR>
								  <TR align="left" id="hideTable6">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font>5 Crores-15 Crores
									</TD>
									
									<TD align="left" class="TableData"> 
										<html:hidden property="fiveCrToFifteenCrNoOfBorrowrs" value="0" name="regForm"  onkeyup="isValidNumber(this)" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:hidden property="fiveCrToFifteenCrTotAmt" value="0"  name="regForm"  onkeyup="isValidNumber(this)" />
									</TD>
									
									<TD align="left" class="TableData"> 
										<html:hidden property="fiveCrToFifteenCrNoOfBorrowrsTC"  value="0" name="regForm"  onkeyup="isValidNumber(this)" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:hidden property="fiveCrToFifteenCrTotAmtTC"  value="0" name="regForm"  onkeyup="isValidNumber(this)" />
									</TD>
									
									
									<TD align="left" class="TableData"> 
										<html:hidden property="fiveCrToFifteenCrNoOfBorrowrsWCTL" value="0" name="regForm"  onkeyup="isValidNumber(this)" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:hidden property="fiveCrToFifteenCrTotAmtWCTL" value="0"  name="regForm"  onkeyup="isValidNumber(this)" />
									</TD>
								</TR>
								<TR align="left" id="hideTable7">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font>15 Crores-25 Crores
									</TD>
									<TD align="left" class="TableData"> 
										<html:hidden property="fifteenCrToTwentyFiveCrNoOfBorrowrs" value="0" name="regForm" onkeyup="isValidNumber(this)" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:hidden property="fifteenCrToTwentyFiveCrTotAmt"  value="0" name="regForm"  onkeyup="isValidNumber(this)" />
									</TD>
									
									<TD align="left" class="TableData"> 
										<html:hidden property="fifteenCrToTwentyFiveCrNoOfBorrowrsTC" value="0" name="regForm" onkeyup="isValidNumber(this)" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:hidden property="fifteenCrToTwentyFiveCrTotAmtTC" value="0"   name="regForm"  onkeyup="isValidNumber(this)" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:hidden property="fifteenCrToTwentyFiveCrNoOfBorrowrsWCTL" value="0" name="regForm" onkeyup="isValidNumber(this)" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:hidden property="fifteenCrToTwentyFiveCrTotAmtWCTL" value="0"   name="regForm"  onkeyup="isValidNumber(this)" />
									</TD>
									
								</TR> 
						 --%>		<!-- End Avanish  -->
						 <!-- Start:Avanish Code-->
						 <html:hidden property="fifteenCrToTwentyFiveCrTotAmtWCTL" value="0"   name="regForm"  onkeyup="isValidNumber(this)" />
<html:hidden property="fifteenCrToTwentyFiveCrNoOfBorrowrsWCTL" value="0" name="regForm" onkeyup="isValidNumber(this)" />
<html:hidden property="fifteenCrToTwentyFiveCrTotAmtTC" value="0"   name="regForm"  onkeyup="isValidNumber(this)" />
<html:hidden property="fifteenCrToTwentyFiveCrNoOfBorrowrsTC" value="0" name="regForm" onkeyup="isValidNumber(this)" />
<html:hidden property="fifteenCrToTwentyFiveCrTotAmt"  value="0" name="regForm"  onkeyup="isValidNumber(this)" />
<html:hidden property="fifteenCrToTwentyFiveCrNoOfBorrowrs" value="0" name="regForm" onkeyup="isValidNumber(this)" />
<html:hidden property="fiveCrToFifteenCrTotAmtWCTL" value="0"  name="regForm"  onkeyup="isValidNumber(this)" />
<html:hidden property="fiveCrToFifteenCrNoOfBorrowrsWCTL" value="0" name="regForm"  onkeyup="isValidNumber(this)" />
<html:hidden property="fiveCrToFifteenCrTotAmtTC"  value="0" name="regForm"  onkeyup="isValidNumber(this)" />
<html:hidden property="fiveCrToFifteenCrNoOfBorrowrsTC"  value="0" name="regForm"  onkeyup="isValidNumber(this)" />
<html:hidden property="fiveCrToFifteenCrTotAmt" value="0"  name="regForm"  onkeyup="isValidNumber(this)" />
<html:hidden property="fiveCrToFifteenCrNoOfBorrowrs" value="0" name="regForm"  onkeyup="isValidNumber(this)" />
<html:hidden property="oneCrToFiveCrTotAmtWCTL"  value="0" name="regForm"  onkeyup="isValidNumber(this)" />
<html:hidden property="oneCrToFiveCrNoOfBorrowrsWCTL" value="0" name="regForm"  onkeyup="isValidNumber(this)" />
<html:hidden property="oneCrToFiveCrTotAmtTC" value="0"  name="regForm"  onkeyup="isValidNumber(this)" />
<html:hidden property="oneCrToFiveCrNoOfBorrowrsTC"  value="0" name="regForm"  onkeyup="isValidNumber(this)" />
<html:hidden property="oneCrToFiveCrTotAmt" value="0"  name="regForm"  onkeyup="isValidNumber(this)" />
<html:hidden property="oneCrToFiveCrNoOfBorrowrs"  value="0" name="regForm"  onkeyup="isValidNumber(this)" />
<html:hidden property="fiftyLakhToOneCrTotAmtWCTL" value="0"  name="regForm"  onkeyup="isValidNumber(this)" />
<html:hidden property="fiftyLakhToOneCrNoOfBorrowrsWCTL" value="0" name="regForm" onkeyup="isValidNumber(this)" />
<html:hidden property="fiftyLakhToOneCrTotAmtTC" value="0"   name="regForm"  onkeyup="isValidNumber(this)" />
<html:hidden property="fiftyLakhToOneCrNoOfBorrowrsTC"  value="0" name="regForm" onkeyup="isValidNumber(this)" />
<html:hidden property="fiftyLakhToOneCrTotAmt" value="0"  name="regForm"  onkeyup="isValidNumber(this)" />
<html:hidden property="fiftyLakhToOneCrNoOfBorrowrs"  value="0" name="regForm" onkeyup="isValidNumber(this)" />
<html:hidden property="tenToFiftyLakhTotAmtWCTL" value="0" name="regForm"  onkeyup="isValidNumber(this)" />
<html:hidden property="tenToFiftyLakhNoOfBorrowrsWCTL" value="0"  name="regForm"  onkeyup="isValidNumber(this)" />
<html:hidden property="tenToFiftyLakhTotAmtTC"  value="0" name="regForm"  onkeyup="isValidNumber(this)" />
<html:hidden property="tenToFiftyLakhNoOfBorrowrsTC" value="0"  name="regForm"  onkeyup="isValidNumber(this)" />
<html:hidden property="tenToFiftyLakhTotAmt" value="0"  name="regForm"  onkeyup="isValidNumber(this)" />
<html:hidden property="tenToFiftyLakhNoOfBorrowrs" value="0"  name="regForm"  onkeyup="isValidNumber(this)" />
<html:hidden property="zeroToTenLakhTotAmtWCTL"  name="regForm" value="0"  onkeyup="isValidNumber(this)"/>
<html:hidden property="zeroToTenLakhNoOfBorrowrsWCTL"  value="0" name="regForm"  onkeyup="isValidNumber(this)"/>
<html:hidden property="zeroToTenLakhTotAmtTC" value="0"  name="regForm"  onkeyup="isValidNumber(this)" />
<html:hidden property="zeroToTenLakhNoOfBorrowrsTC" value="0"   name="regForm"  onkeyup="isValidNumber(this)"/>
<html:hidden property="zeroToTenLakhTotAmt"  name="regForm" value="0"  onkeyup="isValidNumber(this)" />
<html:hidden property="zeroToTenLakhNoOfBorrowrs"  value="0" name="regForm"  onkeyup="isValidNumber(this)"/>

						 
						 <!-- End Avanish Code -->
						 
								</TABLE>
						</TD>
					</TR>
					<TR >
						<TD height="20" >
							&nbsp;
						</TD>
					</TR>
					<TR >
						<TD align="center" valign="baseline" >
							<DIV align="center">
							<A href="javascript:submitForm('registerMLI.do?method=registerMLI'),checkEmailFormatValid();"><IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>
								<A href="javascript:document.regForm.reset()">
									<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>
                                                        <A href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>"><IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>									
																
							</DIV>
						</TD>
					</TR>
				</TABLE>
			</TD>
			<TD width="20" background="images/TableVerticalRightBG.gif">
				&nbsp;
			</TD>
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
		</TABLE>
	</html:form>
</BODY>
</HTML>





						
