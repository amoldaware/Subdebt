<%@ page language="java"%>
<%@page import ="java.text.SimpleDateFormat"%>
<%@page import ="java.util.Date"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","saveRecoverydataAction.do?method=saveRecoverydata");%>



<body onload="recoveryOnloadMethod()">



<html:form action="saveRecoverydataAction.do?method=saveRecoverydata" method="POST"  >
<head>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page"> 

</head>
<html:errors/>

<html:hidden property="count" name="claimRecoveryForm" styleId="count" value="0" />
  <marquee scrollamount="3" style="background:#F6D8CE" >1) If bymistake you have made entry of incorrect claim ref no. then do not select checkbox of that claim ref no. , System will auto ignore it. 2)If you are facing any challange in this recovery module then kindly mail on support@cgtmse.in. </marquee>
    <TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
   
    <TR >
					<td ><div id="errorsMessage" class="errorsMessage"></div>  </td>
					
						<TD height="20" >
							&nbsp;
						</TD>
					</TR>
    <tr>
    <td>
    	<TABLE id="recoverydetailTableForHeading" width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
    	<TR align="left" valign="top" >
<!--    	<td colspan="7" >Total Remmited Amount :- <html:text name="claimRecoveryForm" property="totalAmountToDisplay"  />-->
<!--    	<html:button styleClass="button"  property="totalAmountToDisplayBtn" value="Show Total Remmitted Amount" onclick="calculateTotalRemmitedAmount()"/> </td>-->

				
    	</TR>
    	
    	<TR align="left" valign="top" >
			
			<Th width="9%" align="center" valign="top" class="HeadingRecovery">CLAIM REF NO.</Th>
			<Th width="9%"  align="center" valign="top" class="HeadingRecovery">CGPAN</Th>
			<Th width="9%"  align="center" valign="top" class="HeadingRecovery">UNIT NAME</Th>
			<Th  width="9%" align="center" valign="top" class="HeadingRecovery">FIRST INSTALLMENT AMT</Th>
			<Th width="9%" align="center" valign="top" class="HeadingRecovery">OLD RECOVRED AMT</Th>
			<Th width="14%" align="center" valign="top" class="HeadingRecovery">TYPE OF RECOVERY</Th>
			<Th  width="9%" align="center" valign="top" class="HeadingRecovery">RECOVERED AMT</Th>
			<Th  width="9%" align="center" valign="top" class="HeadingRecovery">LEGAL EXPENSES</Th>
			<Th  width="9%" align="center" valign="top" class="HeadingRecovery">AMT REMITTED</Th>
			<Th  width="3%" align="center" valign="top" class="HeadingRecovery">PAY</Th>
			
		</TR>
<!--		<tr><html:button  property="typeOfRecoveryName" value="Add Field (+)" onclick="addTextBox()"/></tr>-->
<!--		<tr><td><html:submit property="submit"> Submit</html:submit></td></tr>-->
	
 
    	</TABLE>
    	</td>
  </tr>
  <tr>
  <td>
    <table id="tblRecoveryDetails" width="100%" border="0" align="left" cellpadding="0" cellspacing="0" >
	</table>
 <table id="tblRecoveryDetails1" width="100%" border="0" align="left" cellpadding="0" cellspacing="0" >
	<tr>
	<td>
   	<html:button styleClass="button"  property="addNewRows" value="ADD FIELDS(+)" onclick="addTableRowForRecovery()"/></td>
   	
		<td align="right"">
			
    	<html:button styleClass="button"  property="totalAmountToDisplayBtn" value="Show Total Remmitted Amount" onclick="calculateTotalRemmitedAmount()"/>
    	Total Remmited Amount :- <html:text name="claimRecoveryForm" property="totalAmountToDisplay"  />          ---
    	</td>
    	
	</tr>
</table>

</td>
   </tr>
   <tr>
   <td>
  </td>
   
   </tr>
    </TABLE>
    <table>
    <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
     <tr></tr>
      <tr></tr>
    </table>
    <table align="center">
      
 	  	<tr >
 	  	
 	  	<td>
 	  	<html:button styleClass="button" style="width:110px;"  property="NextButton" value="GO FOR PAYMENT" onclick="goForRecoveryPayment()"/></td>    		
   		<td><html:button value="          RESET   " style="width:110px;"  styleClass="button" property="resetRecoveryDetails"  onclick="resetRecoveryDetails1()" /></td> 
   		<td><html:button value="        CANCEL   " style="width:110px" styleClass="button" property="cancelRecoveryDetails" onclick="cancelRecoveryDetails1()"  /></td> </tr>
 
</table>

 <!-- <table>
      
 	  	<tr><td><html:button styleClass="button" property="NextButton" value="GO FOR PAYMENT" onclick="goForRecoveryPayment()"/></td> </tr>
   		<tr><td><html:button styleClass="button" property="backButton" value="BACK" onclick="recoveryFormBackButton()"/></td> </tr>
   		<tr><td><html:button value="RESET" styleClass="button" property="resetRecoveryDetails"  onclick="resetRecoveryDetails1()" /></td> </tr>
   		<tr><td><html:button value="CANCEL" styleClass="button" property="cancelRecoveryDetails" onclick="cancelRecoveryDetails1()"  /></td> </tr>
 
</table> -->
   
 <table id="tblPaymentInfo" width="100%" border="0" align="left" cellpadding="0" cellspacing="0" >
 <TR >
					<td><div id="errorsMessage" class="errorsMessage"></div>  </td>
						<TD height="20" >
							&nbsp;
						</TD>
					</TR>
  
<tr>
	<td><html:button styleClass="button" property="backButton" value="BACK" onclick="recoveryFormBackButton()"/></td>
	<td>Mode of Payment</td>
	<td><html:select property="modeOfPayment" name="claimRecoveryForm" onchange="typeOfPaymentRecovery()">
	<html:option value="">Select payment Mode</html:option>
						<html:option value="DD">Demant Draft</html:option>
						<html:option value="RTGS_NEFT">RTGS/NEFT</html:option>
					
				  </html:select></td>
</tr>
</table>
 <table id="RTGS_NEFT" width="100%" border="0" align="left" cellpadding="0" cellspacing="0" >
  <TR >
					<td><div id="rtgserrorsMessage" class="errorsMessage"></div>  </td>
						<TD height="20" >
							&nbsp;
						</TD>
					</TR>
   
 <tr>
	<th class="Heading" colspan="2" align="center"  >RTGS/NEFT Payment Details </th>
	
</tr>
<tr>
	<td class="TableDataRecovery" >Collecting Bank Name : </td>
	<td class="TableDataRecovery" align="left" ><bean:write name="claimRecoveryForm" property="bankName"/></td>
</tr>
<tr>
	<td class="TableDataRecovery" >Collecting Bank Branch Name</td>
	<td class="TableDataRecovery" align="left" ><bean:write name="claimRecoveryForm" property="branchName"/></td>
</tr>
<tr>
	<td class="TableDataRecovery" ><font color="#FF0000" size="2">*</font>Payment Date</td>
	<td class="TableDataRecovery" align="left" ><html:text name="claimRecoveryForm" property="rtgsPaymentDate"  /><img src="images/CalendarIcon.gif" width="20" 
                  onClick="showCalendar('claimRecoveryForm.rtgsPaymentDate')"></td>
</tr>
<tr>
	<td class="TableDataRecovery" >RTGS Amount</td>
	<td class="TableDataRecovery" align="left"><html:text name="claimRecoveryForm" property="rtgsAmount" readonly="true" /></td>
</tr>
<tr>
	<td class="TableDataRecovery" ><font color="#FF0000" size="2">*</font>UTR Number</td>
	<td class="TableDataRecovery" ><html:text name="claimRecoveryForm" property="utrNumber"  /></td>
</tr>

</table>
<table id="DDTable"  width="100%" border="0" align="left" cellpadding="0" cellspacing="0" >
 <TR >
					<td colspan="2" ><div id="dderrorsMessage" class="errorsMessage"></div>  </td>
						<TD height="20" >
							&nbsp;
						</TD>
</TR>
   
 <tr>
	<th class="Heading" colspan="2" align="center" valign="bottom" >Demant Draft Payment Details </th>
	
</tr>
<tr>
	<td class="TableDataRecovery" >Collecting Bank Name : </td>
	<td class="TableDataRecovery" align="left" ><bean:write name="claimRecoveryForm" property="bankName"/></td>
</tr>
<tr>
	<td class="TableDataRecovery" >Collecting Bank Branch Name</td>
	<td align="left" class="TableDataRecovery" ><bean:write name="claimRecoveryForm" property="branchName"/></td>
</tr>
<tr>
	<td class="TableDataRecovery" ><font color="#FF0000" size="2">*</font>Payment Date</td>
	<td class="TableDataRecovery" align="left" ><html:text name="claimRecoveryForm" property="ddPaymentDate"  /><img src="images/CalendarIcon.gif" width="20" 
                  onClick="showCalendar('claimRecoveryForm.ddPaymentDate')"></td>
</tr>
<tr>
	<td class="TableDataRecovery" ><font color="#FF0000" size="2">*</font>Instrument Number</td>
	<td class="TableDataRecovery" align="left" ><html:text name="claimRecoveryForm" property="instrumentNumber" /></td>
</tr>
<tr>
	<td class="TableDataRecovery" ><font color="#FF0000" size="2">*</font>Instrument Date</td>
	<td class="TableDataRecovery" align="left" ><html:text name="claimRecoveryForm" property="instrumentDate"  /><img src="images/CalendarIcon.gif" width="20" 
                  onClick="showCalendar('claimRecoveryForm.instrumentDate')"></td>
</tr>
<tr>
	<td class="TableDataRecovery" >Instrument Amount</td>
	<td class="TableDataRecovery" align="left"><html:text name="claimRecoveryForm" property="totalInstrumentAmount" readonly="true" /></td>
</tr>
<tr>
	<td class="TableDataRecovery" ><font color="#FF0000" size="2">*</font>Drawn At(Bank)</td>
	<td class="TableDataRecovery" align="left" ><html:text name="claimRecoveryForm" property="drawnAtBank" /></td>
</tr>
<tr>
	<td class="TableDataRecovery" ><font color="#FF0000" size="2">*</font>Drawn At(branch)</td>
	<td class="TableDataRecovery" align="left"><html:text name="claimRecoveryForm" property="drawnAtBranch"  /></td>
</tr>
<tr>
	<td class="TableDataRecovery" ><font color="#FF0000" size="2">*</font>Instrumental Should be Payable at Mumbai</td>
	<td class="TableDataRecovery" align="left"><html:text name="claimRecoveryForm" property="instrumentpaybleAt"  /></td>
</tr>
</table>

<TABLE align="center" id="tblButton" >

<tr>

<td><html:hidden property="tableRowCount" name="claimRecoveryForm" /></td>
<td><html:button value="SAVE" styleClass="button" property="submitRecoveryDetails"  onclick="saveRecoveryRequest('saveRecoverydataAction.do?method=saveRecoverydata')" /></td>
<td><html:button value="RESET" styleClass="button" property="resetRecoveryDetailsPayment"  onclick="resetRecoveryPaymentDetails()" /></td>
<td><html:button value="CANCEL" styleClass="button" property="cancelRecoveryDetailsPayment" onclick="cancelRecoveryDetails1()"  />
</td>
</tr>

</table>


</html:form>
</body>

