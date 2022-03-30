<%@ page import="com.cgtsi.util.SessionConstants"%>

<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7" />
 <style>
 .w150{width:180px;}
 select,input[type="text"],textarea{min-width:180px;}
 </style>
 <script type="text/javascript"> 
        window.history.forward(); 
        function noBack() { 
            window.history.forward(); 
        } 
    </script> 
    
 <script src="/csrfguard"></script>
 <script type="text/javascript">

 function callWhenPageWillLoad() {
	// alert("Dddd");
	 
	
	
	 
	 //alert("ANand=="+document.forms[0].pplOS.value);
	 var pplOSVal=parseFloat(document.forms[0].pplOS.value);
	 if(isNaN(pplOSVal)){
		// alert("1");
	    	document.getElementById("pplOS").value='0.0';
	 }else{
		 //alert("2");
	    if((pplOSVal!=null ||pplOSVal!="" )){
	    	document.getElementById("pplOS").value=pplOSVal;
	    }else{
	    	// alert("3");
	    	document.getElementById("pplOS").value='0.0';
	    }
	 }
	 
	
	 
	 
	 var osMLI=parseFloat(document.forms[0].osMLI.value);
	 if(isNaN(osMLI)){
	    	document.getElementById("osMLI").value='0.0';
	 }else{
	    if((osMLI!=null ||osMLI!="" )){
	    	document.getElementById("osMLI").value=osMLI;
	    }else{
	    	document.getElementById("osMLI").value='0.0';
	    }
	 }
	 
	 
	 var existingFacilityAmount=parseFloat(document.forms[0].existingFacilityAmount.value);
	 if(isNaN(existingFacilityAmount)){
	    	document.getElementById("ExistingFacilityAmount").value='0.0';
	 }else{
	    if((existingFacilityAmount!=null ||existingFacilityAmount!="" )){
	    	document.getElementById("ExistingFacilityAmount").value=existingFacilityAmount;
	    }else{
	    	document.getElementById("ExistingFacilityAmount").value='0.0';
	    }
	 }
	 
	 
	 
	 var totalMIcollatSecAmt1=parseFloat(document.forms[0].totalMIcollatSecAmt1.value);
	 if(isNaN(totalMIcollatSecAmt1)){
		 alert("1");
	    	document.getElementById("totalMIcollatSecAmt").value='0.0';
	 }else{
	    if((totalMIcollatSecAmt1!=null ||totalMIcollatSecAmt1!="" )){
	    	 alert("1");
	    	document.getElementById("totalMIcollatSecAmt").value=totalMIcollatSecAmt1;
	    }else{
	    	 alert("1");
	    	document.getElementById("totalMIcollatSecAmt").value='0.0';
	    }
	 }
	 
	
	 
}
	window.onload=callWhenPageWillLoad;


 
</script>
 
 
 <script type="text/javascript">
 

 function nocEnabled()
 {
	// alert("nocEnabled==");
 	var check;
 		var osMLI=findObj("osMLI");	
 		var pplOS=findObj("pplOS");
 		var creditGuaranteedVal=document.getElementById('creditGuaranteed').value;
 		var existingFacilityAmount=document.getElementById('ExistingFacilityAmount').value;
 		
 		//alert("existingFacilityAmount=="+(existingFacilityAmount));
 		var nocCheck=document.getElementById("promDirDefaltFlg").disabled = true;
 		//alert("noccheck=="+nocCheck);
 	
 	if(osMLI!=null && osMLI!="")
 	{	
 	var osMLIVal=osMLI.value;
 	
 	}
 	
 	if(pplOS!=null && pplOS!="")
 	{	
 	var pplOSVal=pplOS.value;
 	
 	}
 	
 	
 	
 	//alert("osMLIVal=="+osMLIVal);
 	
 	//alert("pplOSVal=="+pplOSVal);
 	
 	//alert("creditGuaranteedVal=="+creditGuaranteedVal);
 	
 	var logic=parseFloat(existingFacilityAmount)+ parseFloat(creditGuaranteedVal);
 	//alert("Logic=="+logic);
 	
 	check=((osMLIVal)*(20/100));
 	//alert("check=="+check);
 	var check2=((pplOSVal)*(20/100));
 	//alert("check2=="+check2);
 	if(creditGuaranteedVal>check || logic>check)
 				{
 		//alert("1st if");
 					
 						//alert("2nd if");
 						nocCheck=document.getElementById("promDirDefaltFlg").disabled = false;
 						alert("Click checkbox for NOC(for providing additional 20% fund/Credit)from all other MLI");
 					
 				}		
 	
 	

 }
 
 
 
function checkbox() {
    var checkBox = document.getElementById("exposureId");
   // var text = document.getElementById("text");
    if (checkBox.checked == true){
     //alert('true');
    //   document.forms[0].exposureFbId.value='Y';
    //   document.forms[0].exposureFbIdY='Y';
     //   document.getElementById('checkVal').value = 'V'; 
      
    } else {
     //alert('false');
     //document.forms[0].exposureFbIdY='N';
    
     //  text.style.display = "none";
    }
}




	/* function validateInterestRateTC(rateType){
		var plr = document.forms[0].plr.value;
		var interest = document.forms[0].interestRate.value;
		if(rateType === 'interest'){
			if(!(parseFloat(plr) > 0)){
				alert('Please enter PLR rate first.');
				document.forms[0].interestRate.value='0.0';
				return false;
			}
			if(parseFloat(interest) < parseFloat(plr)){				
				alert('Interest rate can not be less than PLR rate.');
				document.forms[0].interestRate.value='0.0';
				return false;
			}
			if(parseFloat(interest) > (parseFloat(plr) + 4)){
				alert('Difference between plr and interest can not be more than 4%.');
				document.forms[0].interestRate.value='0.0';				
				return false;
			}
		}
		if(rateType === 'plr'){
			if(parseFloat(interest) > (parseFloat(plr) + 4)){				
				alert('Difference between plr and interest can not be more than 4%.');
				document.forms[0].interestRate.value='0.0';
				return false;
			}
		}
		
	} */
</script>
	 <table width="100%" border="0" cellspacing="1" cellpadding="0">
		   <tr>
	 <td colspan="12">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tbody><tr>
					<td width="31%" class="Heading"><bean:message key="promoterHeader" /> </td>
					<td><img width="19" height="19" src="images/TriangleSubhead.gif"></td>
				</tr>
				<tr>
					<td class="Heading" colspan="6"><img width="5" height="5" src="images/Clear.gif"></td>
				</tr>
			</tbody>
			</table>
		</td>
	 </tr> 
	<%-- <TR align="left">
		<TD align="left" valign="top" class="ColumnBackground" colspan="6">
			<bean:message key="chiefInfo" />
		</TD>
	</TR> --%>

	 
<html:hidden property="cpTitle" name="appForm" />
	 
		<html:hidden property="cpFirstName" name="appForm" />
		 
		<html:hidden property="cpMiddleName" name="appForm" />
		 
		<html:hidden property="cpLastName" name="appForm" />
		 
 <tr align="left">
		<td colspan="12" class="SubHeading" height="28" width="843"><br>
			Details of Promoter 
			&nbsp;</td>
	 </tr> 
<tr align="left">
	<td colspan="12" >
		<table width="100%">
			<tbody>
				<tr>
					<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
					<font color="#FF0000" size="2">*</font>&nbsp;First Name
				</TD>
				<td align="left" valign="top" class="TableData" width="16.6%"> <html:text property="cpFirstName" size="20" alt="firstName"
				name="appForm" maxlength="20" /> 
 				</TD> 
				<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
					<font color="#FF0000" size="2">*</font>&nbsp;Last Name
				</TD>
			<TD align="left" valign="top" class="TableData" width="16.6%">
			 <html:text property="cpLastName" size="20" alt="lastName"
				name="appForm" maxlength="20" /> </TD>
			<TD align="left" valign="top" class="ColumnBackground">&nbsp;<bean:message
				key="chiefItpan" />
		</TD>
		<TD align="left" valign="top" class="TableData" width="16.6%">
		 <html:text property="cpITPAN" size="15" alt="chiefItpan"
				name="appForm" maxlength="10" /> 
		</TD>	
				
				</tr>	
				<tr>
				<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
					<bean:message key="dob" />
				</TD>
				<TD align="left" valign="top" class="TableData"  width="16.6%">
				 <html:text property="cpDOB" size="15" alt="dob"
						name="appForm" maxlength="10" /> <IMG src="images/CalendarIcon.gif"
					width="20" onClick="showCalendar('appForm.cpDOB')" align="center">
					</TD>
				<TD align="left" valign="top" class="ColumnBackground"  width="16.6%"><font
					color="#FF0000" size="2">*</font>&nbsp;<bean:message key="gender" />
				</TD>
				<TD align="left" valign="top" class="TableData"  width="16.6%">
					 <html:radio name="appForm" value="M" property="cpGender">
					</html:radio> <bean:message key="male" />&nbsp;&nbsp;&nbsp; <html:radio
						name="appForm" value="F" property="cpGender"></html:radio> <bean:message
						key="female" />  
			
				</TD>
				<TD align="left" valign="top" class="ColumnBackground" width="16.6%"> Promoter Category </TD>
			<TD align="left" valign="top" class="TableData"  width="16.6%">
			 <html:select property="socialCategory" name="appForm">
					<html:option value="">Select</html:option>
					<html:options name="appForm" property="socialCategoryList"></html:options>
		
				</html:select>
			</TD>
				</tr>	
			 <tr>
					<TD align="left" valign="top" class="ColumnBackground" width="16.6%"> 
					Email Id </TD>
			<TD align="left" valign="top" class="TableData"  width="16.6%">
				<input type="email" id="">
			</TD>
			<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
			<font color="#FF0000" size="2">*</font>&nbsp;Mobile No</td>
		<TD align="left" valign="top" class="TableData" width="16.6%"><html:text property="proMobileNo" size="20"
				alt="proMobileNo" name="appForm" maxlength="10"
				onkeypress="return numbersOnly(this, event)"
				onkeyup="isValidNumber(this)" />
				</td>
				<TD align="left" valign="top" class="ColumnBackground" width="16.6%">&nbsp;
				<bean:message key="promoterAdharNo" /></td>
		<TD align="left" valign="top" class="TableData" width="16.6%" ><html:text
				property="adhar" size="20" alt="adhar" name="appForm" maxlength="12"
				onkeypress="return numbersOnly(this, event)"
				onkeyup="isValidNumber(this)" /></td>
				
				</tr>	
				<tr>
					<td align="left" valign="top" class="ColumnBackground" width="16.6%">
						Investment as equity in the MSME unit
					</td>
					<td align="left" valign="top" class="TableData" width="16.6%" >
						<input type="number" id="">
					</td>
					<td align="left" valign="top" class="ColumnBackground" width="16.6%">
						Investment as debt/loan in the MSME unit
					</td>
					<td align="left" valign="top" class="TableData" width="16.6%" >
						<input type="number" id="">
					</td>
					<td align="left" valign="top" class="ColumnBackground" width="16.6%">
						 
					</td>
					<td align="left" valign="top" class="TableData" width="16.6%" >
						 
					</td>
				</tr>	
				<tr>
					<td>	
					<button type="button" style="padding:5px 10px;border:none;background-color:green;
					color:white; border-radius:5px;">
					Add</button></td>
				</tr>
			</tbody>
		</table>
	</td>

</tr> 	 
 
  
		<html:hidden property="physicallyHandicapped" name="appForm" />
	 

<html:hidden property="cpDOB" name="appForm" />
	 
		<html:hidden property="socialCategory" name="appForm" />
		 
	<tr>
		<td colspan="12">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tbody><tr>
							<td width="31%" class="Heading">Calculated Eligibility Details (For Reference) </td>
							<td><img width="19" height="19" src="images/TriangleSubhead.gif"></td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td class="Heading" colspan="4"><img width="5" height="5" src="images/Clear.gif"></td>
						</tr>
					</tbody>
				</table>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="16.6%" align="left" class="ColumnBackground" valign="top">
			<font color="#ff0000" size="2">*</font>&nbsp;15% of Promoter's stake in MSME unit</td>
			<td width="16.6%" align="left" class="TableData" valign="top">
				<html:text  name="appForm" property="pmr15Percent" maxlength="9"   size="25"  styleId="pmr15Percent" alt="pmr15Percent" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />
			</td>
			
			<td width="16.6%" align="left" class="ColumnBackground" valign="top">
			<font color="#ff0000" size="2">*</font>&nbsp;Existing Total o/s amount</td>
			<td width="16.6%" align="left" class="TableData" valign="top">
				<input name="totalOutstandingAmt" id="outstandingAmt" onkeyup="isValidDecimal(this)" onkeypress="return decimalOnly(this, event,13)" type="text" size="25" maxlength="9" alt="outstandingAmt" value="">
			</td>
			
			<td width="16.6%" align="left" class="ColumnBackground" valign="top">
			<font color="#ff0000" size="2">*</font>&nbsp;Total Sub-debt scheme limit  </td>
			<td width="16.6%" align="left" class="TableData" valign="top">
				<html:text  name="appForm" property="pmrMinTotal" maxlength="9"   size="25"  styleId="pmrMinTotal" alt="pmrMinTotal" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />
			</td>
			
				</tr>
			</table>	
		</td>
	</tr> 
	   
			 
		 	 <!--  <tr>
				<td colspan="4" class="SubHeading">
				<br> Sub-Debt Details <br></td>
			</tr>
			
			
				 <tr>
				  <td  align="left" valign="top" class="ColumnBackground" width="25%">
						&nbsp;<bean:message key="pmrEquity" /> 
							</td>	
			 <td style="width:25% !important" class="TableData">
			 <html:text  name="appForm" property="pmrEquity" maxlength="9"   size="25"  styleId="pmrEquity" alt="pmrEquity" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />   
			</td>
			  </tr>
			<tr>
			
			  <td  align="left" valign="top" class="ColumnBackground" width="25%">
						&nbsp;<bean:message key="pmrDebt" /> 
							</td>	
			 <td style="width:25% !important" class="TableData">
			 <html:text  name="appForm" property="pmrDebt" maxlength="9"   size="25"  styleId="pmrDebt" alt="pmrDebt" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />   
			</td>
			
			</tr>
			<tr>
			 <td  align="left" valign="top" class="ColumnBackground" width="25%">
						&nbsp;<bean:message key="pmrStakeTotal" /> 
							</td>	
			 <td style="width:25% !important" class="TableData">
			 <html:text  name="appForm" property="pmrStakeTotal" maxlength="9"   size="25"  styleId="pmrStakeTotal" alt="pmrStakeTotal" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />   
			</td>
			
			</tr>
			<tr>
			 <td  align="left" valign="top" class="ColumnBackground" width="25%">
						&nbsp;<bean:message key="pmr15Percent" /> 
							</td>	
			 <td style="width:25% !important" class="TableData">
			    
			</td>
			
			</tr>
			<tr>
			
			 <td  align="left" valign="top" class="ColumnBackground" width="25%">
						&nbsp;<bean:message key="pmrMinTotal" /> 
							</td>	
			 <td style="width:25% !important" class="TableData">
			    
			</td> 
				 </tr> -->
<tr><td colspan="12">				 
<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tbody><tr>
			<td width="31%" class="Heading">  Guarantee Details  </td>
			<td><img width="19" height="19" src="images/TriangleSubhead.gif"></td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td class="Heading" colspan="4"><img width="5" height="5" src="images/Clear.gif"></td>
		</tr>
	</tbody>
</table>			  
 </td>
 </tr>
			
<tr>
<td colspan="12">				 
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td style="width:16.6% !important"  class="ColumnBackground" height="28" style="width:25% !important">
			<font color="#FF0000" size="2">*</font>&nbsp;<!-- <bean:message key="creditGuaranteed" /> -->
		<B>Sub-debt Guarantee Amount <bean:message key="inRs" />  </B>	
		</td>
		 <td style="width:16.6%"  class="TableData" height="28" >

			    <html:hidden property="exposurelmtAmt" name="appForm"/>	
			    	<!-- <input type="text"  size="20" alt="ITPAN" id="ssiITPan" scope="session"
				name="appForm" maxlength="10" onBlur="getITPANAmount();"/>	 -->		
				<html:text property="creditGuaranteed" styleId="creditGuaranteed" size="20" alt="creditGuaranteed" name="appForm" maxlength="16" onblur="nocEnabled();"    onkeyup="isValidNumber(this);"/>&nbsp;
				
				
				
		     	<html:hidden property="creditGuaranteed" styleId="creditGuaranteedhid" name="appForm"/>									     	
		     	 <span id="FBerrorsMessage" class="errorsMessage"></span> 									
		   <%-- 	<%}%>
			 --%>
		</td>
		<td class="ColumnBackground" height="28" style="width:16.6%">&nbsp;
			<font color="#FF0000" size="2">*</font>&nbsp;Tenure of Sub-debt [in Months]         &nbsp;
		</td>
		<td class="TableData" height="28" style="width:16.6%">	
		
			<html:text property="tenure" size="20" alt="tenure" name="appForm" maxlength="3" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/> 
		
		</td>
		
		<td style="width:16.6%" class="ColumnBackground" > <div align="left">&nbsp;
			<font color="#FF0000" size="2">*</font>&nbsp;Sub-Debt Interest Rate  <bean:message key="inPa" />      </div>
		</td>
		<td class="TableData" style="width:16.6%">
			<div align="left"> 
		
				<html:text property="interestRate" size="5" alt="interestRate" name="appForm"  maxlength="5" onkeypress="return decimalOnly(this, event,2)" onkeyup="isValidDecimal(this)"/>&nbsp;
			
			
			</div>
		</td>
		
	</tr>
	<tr>
		<td style="width:16.6%" class="ColumnBackground" height="18"><span style="font-size: 9pt; font-weight: 700">
			<font color="#FF0000" size="2">*</font>Moratorium [in Months] </span>
		</td>
		<td style="width:16.6%" class="TableData" height="18" >
		
			<span style="font-size: 9pt; font-weight: 700"> 
			
			<html:text property="repaymentMoratorium" size="5" alt="repaymentMoratorium" name="appForm" maxlength="3" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>
			</span>
			 </td>
		
		<TD style="width:16.6%" align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>
					&nbsp;Loan Account number</TD>
		<TD style="width:16.6%" align="left" valign="top" class="TableData" >&nbsp;<html:text
				property="bankAcNo" size="20" name="appForm" maxlength="25" />
		</TD>
		<TD style="width:16.6%" align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>
					&nbsp;Disbursement amount </TD>
		<TD style="width:16.6%" align="left" valign="top" class="TableData" >&nbsp;<html:text
				property="bankAcNo" size="20" name="appForm" maxlength="25" />
		</TD> 	
	</tr>
	<tr>
		<TD style="width:16.6%" align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>
					&nbsp;Disbursement date </TD>
		<TD style="width:16.6%" align="left" valign="top" class="TableData" >&nbsp;<html:text
				property="bankAcNo" size="20" name="appForm" maxlength="25" />
		</TD>
		<TD style="width:16.6%" align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>
					&nbsp;No of Instalment             </TD>
		<TD style="width:16.6%" align="left" valign="top" class="TableData" >&nbsp;<html:text
				property="bankAcNo" size="20" name="appForm" maxlength="25" />
		</TD>
		<td style="width:16.6%" class="ColumnBackground"> </td>
		<td style="width:16.6%" class="TableData"> </td>
		<td style="width:16.6%" class="ColumnBackground"> </td>
		<td style="width:16.6%" class="TableData"> </td>
	</tr>
</table>
</td>
</tr>

<tr>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tbody><tr>
			<td width="31%" class="Heading"> Security Details available with the Applicant Bank (Existing)  </td>
			<td><img width="19" height="19" src="images/TriangleSubhead.gif"></td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td class="Heading" colspan="4"><img width="5" height="5" src="images/Clear.gif"></td>
		</tr>
	</tbody>
</table>
</tr>		
 <tr align="left">
		<td width="843" height="28" class="SubHeading" colspan="12"><br>
			Value of Securities available as on date of Sanction of sub-debt
			&nbsp;</td>
	 </tr>
<tr>
	<td colspan="12">
		<table width="100%" >
			<thead>
				<tr>
					<th class="ColumnBackground" style="width:25%">Security Nature</th>
					<th class="ColumnBackground" style="width:25%"> Security Value (in Rs )</th>
					<th class="ColumnBackground" style="width:25%">Security Nature</th>
					<th class="ColumnBackground" style="width:25%"> Security Value (in Rs )</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td class="ColumnBackground" >Land</td>
					<td class="TableData"><input type="text"></td>
						<td class="ColumnBackground">Other Fixed / Movable Assets</td>
					<td class="TableData"><input type="text"></td>
				</tr>
				<tr>
					<td class="ColumnBackground">Building</td>
					<td class="TableData"><input type="text"></td>
					<td class="ColumnBackground">Current Assets*</td>
					<td class="TableData"><input type="text"></td>
				</tr>
				<tr>
					<td class="ColumnBackground">Plant and Machinery / Equipment*</td>
					<td class="TableData"><input type="text"></td>
					<td class="ColumnBackground">Others</td>
					<td class="TableData"><input type="text"></td>
				</tr> 
				<tr>
					<td class="ColumnBackground">Total</td>
					<td class="TableData"> <input type="text"></td>
					<td class="ColumnBackground">*Remarks on Total Value of Security</td>
					<td class="TableData"><input type="text"></td>
				</tr>
				 
			</tbody>
			
		</table>
	</td>
</tr>	 
		<html:hidden property="TCTYPE" name="appForm"></html:hidden>
			<html:hidden property="WCTYPE" name="appForm"></html:hidden>  	
				<html:hidden property="sanctionedDate" name="appForm"/>	
				<html:hidden property="sanctionTenure" name="appForm"/>	
				<html:hidden property="firstDisbursementDate" name="appForm"/>			
					
				<!-- <html:hidden property="amtDisbursed" name="appForm"/>	 -->
				  					 
				<!--  <tr align="left">
					  <TD align="left" valign="top" class="ColumnBackground" colspan="4">
					<input type="checkbox" name="promDirDefaltFlg"  id="promDirDefaltFlg" disabled="disabled" >
					<bean:message key="osNOC" />
				 </tr>	 -->
			  
		<!-- 	<tr>
			
			<td colspan="2" class="ColumnBackground">
				<font color="#FF0000" size="2">*</font><label>Type of Emergency Funding Sanctioned </label></td>	
				<td width="25%" class="TableData"> <bean:message key="TCTYPE" />
				 <html:text property="TCTYPE"  name="appForm"   onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/> in %
			 </td>
			 <td width="25%" class="TableData">
				<bean:message key="WCTYPE" />
				 <html:text property="WCTYPE"   name="appForm"  onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/> in %
				</TD>  
			
				
			</tr>	 -->
			
			
			 	
					 
					</table>
			 
		  
			 
						 <!-- Diksha -->
		  
 	                  
