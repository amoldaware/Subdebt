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

    <TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
<!--      <marquee scrollamount="3" style="background:#F6D8CE" >1) If bymistake you have made entry of incorrect claim ref no. then do not select checkbox of that claim ref no. , System will auto ignore it. 2)If you are facing any challange in this recovery module then kindly mail on support@cgtmse.in. </marquee>-->
    <TR align="left" valign="top" >
			
			<Th width="20%" height="20%" align="center" valign="top" class="HeadingRecovery"></Th>
		
			
	</TR>
    
    <tr>
	    <td>
	    	<TABLE id="recoverydetailTableForHeading" width="100%" border="0" align="left" cellpadding="0" cellspacing="1">
	    		
	    		<tr>
					<td >
						<html:button styleClass="button"  property="addNewRows" value="ADD FIELDS(+)" onclick="addTableRowForRecovery()"/>
					</td>
					<td colspan="7" align="right">
						<div id="errorsMessage" class="errorsMessage">
						</div>
					</td>		
				</tr>
				<TR>	
					 <td width="25%" colspan="2" class="Heading">&nbsp;Claims Recovery Data</td>
			 <td align="left" valign="bottom"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
			 <td>&nbsp;</td>
			 <td>&nbsp;</td>	
	    		</TR>
	    		
	    	<TR align="left" valign="top"  >				
	    	
				<Th  width="10%" align="center" valign="top" class="HeadingRecovery">CLAIM REF NO.</Th>
				<Th width="9%"  align="center" valign="top" class="HeadingRecovery">CGPAN</Th>
				<Th width="9%"  align="center" valign="top" class="HeadingRecovery">UNIT NAME</Th>
				<Th  width="9%" align="center" valign="top" class="HeadingRecovery">FIRST INSTALLMENT AMT</Th>
				<Th width="9%" align="center" valign="top" class="HeadingRecovery">OLD RECOVRED AMT</Th>
				<Th width="14%" align="center" valign="top" class="HeadingRecovery">TYPE OF RECOVERY</Th>
				<Th  width="8%" align="center" valign="top" class="HeadingRecovery">RECOVERED AMT</Th>
				<Th  width="10%" align="center" valign="top" class="HeadingRecovery">LEGAL EXPENSES</Th>
				<Th  width="10%" align="center" valign="top" class="HeadingRecovery">AMT REMITTED</Th>
				<Th  width="1%" align="center" valign="top" class="HeadingRecovery">PAY</Th>				
			</TR>
	 	    </TABLE>
	    </td>
 	</tr>
  
  
  <tr>
	  <td>
	    <table id="tblRecoveryDetails" width="100%" border="0" align="left" cellpadding="0" cellspacing="0" >    
		</table>
	  </td>
  </tr>
  
  
   <tr>  
	   <td>
	   		<table id="tblRecoveryDetails11" width="100%" border="0" align="left" cellpadding="0" cellspacing="0" >
	    	<tr>
	   		 	<td align="right"">				
	    			<html:button styleClass="button"  property="totalAmountToDisplayBtn" value="Show Total Remmitted Amount" onclick="calculateTotalRemmitedAmount()"/>
	    			Total Remmited Amount :- <html:text name="claimRecoveryForm" property="totalAmountToDisplay"  />          ---
	    		</td>	    	 
			</tr>
			</table>
	  </td>   
   </tr>
   
   
    <TR align="left" valign="top" >			
		<Th width="20%" height="20%" align="center" valign="top" class="HeadingRecovery"></Th>
	</TR>
	
    <tr ></tr>
    <tr ></tr>
    <tr ></tr>
   
    <tr >  
   		<td align ="center" >
		   <table id="tblRecoveryDetails22" width="40%" border="0"  cellpadding="0" cellspacing="0" align ="center">
		   		<tr > 	  	
		 	  		<td ><html:button styleClass="button" style="width:110px;"  property="NextButton" value="PAYMENT / SAVE" onclick="goForRecoveryPayment()"/></td>    		
		   			<td><html:button value="          RESET   " style="width:110px;"  styleClass="button" property="resetRecoveryDetails"  onclick="resetRecoveryDetails1()" /></td> 
		   			<td><html:button value="        CANCEL   " style="width:110px" styleClass="button" property="cancelRecoveryDetails" onclick="cancelRecoveryDetails1()"  /></td>
		   			<td><html:hidden property="tableRowCount" name="claimRecoveryForm" /></td> 
		   		</tr>
		   		
			</table>
  		</td>   
   </tr>
   
    
    <tr >  
    	<td align ="center" >
     		<table id="tblPaymentInfo" width="100%" border="0" align="left" cellpadding="0" cellspacing="0" >  
				<tr>
					<td>
						<html:button styleClass="button" property="backButton" value="BACK" onclick="recoveryFormBackButton()"/>
					</td>
					<td>
						Mode of Payment
					</td>
					<td>
						<html:select property="modeOfPayment" name="claimRecoveryForm" onchange="typeOfPaymentRecovery()">
							<html:option value="">Select Payment Mode</html:option>
							<html:option value="DD">Demant Draft</html:option>
							<html:option value="RTGS_NEFT">RTGS/NEFT</html:option>					
				  		</html:select>
				  	</td>
			  </tr>
			</table>
		</td>
	</tr>
	
	<tr>
		<td>
  			<table id="RTGS_NEFT" width="80%" border="0" align="center" cellpadding="0" cellspacing="0" >
	  			<TR >
					<td align="center" colspan="2"><div id="rtgserrorsMessage" class="errorsMessage"></div>  
					</td>
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
		</td>
	</tr>


<tr>
	<td>
		<table id="DDTable"  width="80%" border="0" align="center" cellpadding="0" cellspacing="0" >
			 <TR >
				<td align="center" colspan="2" ><div id="dderrorsMessage" class="errorsMessage"></div> 
			    </td>
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
	</td>
</tr>


<tr>
	<td>
		<TABLE align="center" id="tblButton" >
			<tr>
				<td><html:button value="SAVE" styleClass="button" property="submitRecoveryDetails"  onclick="saveRecoveryRequest('saveRecoverydataAction.do?method=saveRecoverydata')" /></td>
				<td><html:button value="RESET" styleClass="button" property="resetRecoveryDetailsPayment"  onclick="resetRecoveryPaymentDetails()" /></td>
				<td><html:button value="CANCEL" styleClass="button" property="cancelRecoveryDetailsPayment" onclick="cancelRecoveryDetails1()"  /></td>
			</tr>
		</table>
   </td>
</tr>


<tr>
	<td>
		<TABLE align="center" id="tblInstructions" >
		<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>	<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
		<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>	<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
		<tr>
		<td> <font color=""><b>Instructions : -</b></font></td>
		</tr>
			<tr>
			
				<td> <font color=""><b>1.</b></font> <font color="red"> If by mistake you have entered a wrong claim reference number then do not select the 'pay' check box for that unit. System will not save the data for that particular claim reference number. 
		   			</font></td>  	
		   			
			</tr>
			<tr>
			<td> <font color=""><b>2.</b></font> <font color="red">For cases where claim has not been settled, while updating the Recovery amount, put the 'Total Recovery amount' (previous recovery fed + new recovery). Tick the 'pay' checkbox and save the data. 
		   			</font></td>
			</tr>
		</table>
   </td>
</tr>
  
</TABLE>



</html:form>
</body>

