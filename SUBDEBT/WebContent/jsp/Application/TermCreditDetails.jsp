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
/*  function setHiddenSelectedVal(){
	
	var e = document.getElementById("socialCategory");
	var strUser = e.options[e.selectedIndex].text;
	document.getElementById('socialCategory1').selectedIndex=strUser;
 } */
 function calExistTotalOs()
 {
 	//alert("calExistTotalOs");
 	var totalOutstandingAmtVal=document.getElementById('totalOutstandingAmt').value;
 	//alert(totalOutstandingAmtVal);
 	var otherBanktotalOutstandingAmtVal=document.getElementById('otherBanktotalOutstandingAmt').value;
 	//alert(otherBanktotalOutstandingAmtVal);
 	var exitingTotalOS=Number(totalOutstandingAmtVal)+Number(otherBanktotalOutstandingAmtVal);
 	
 	document.getElementById('projectedExports2').value=parseFloat(exitingTotalOS);
 	document.getElementById('projectedExports1').value=parseFloat(exitingTotalOS);
 	
 	
 	
 }


 
 
 function callWhenPageWillLoad() {
 //  alert("page load");
	
	 
	
	var firstName1= document.getElementById("cpFirstName1").value;
	
	var cpLastName1= document.getElementById("cpLastName1").value;
   	//alert("2"+cpLastName1);
     	var cpITPAN1= document.getElementById("cpITPAN1").value;
     	
     	
     	
	//alert(firstName1);
	//alert("firstName1.length=="+firstName1.length);
	if(firstName1.length>0 || cpLastName1.length>0 || cpITPAN1.length>0 )
		{
		//alert("if loop="+firstName1);
		 document.getElementById("p2").style.display = "block";  
		}
	
	
	var firstName2= document.getElementById("cpFirstName2").value;
	
	  var cpLastName2= document.getElementById("cpLastName2").value;
    	//alert("2"+cpLastName1);
   var cpITPAN2= document.getElementById("cpITPAN2").value;

	//alert(firstName1);
	//alert("firstName1.length=="+firstName4.length);
	if(firstName2.length>0 || cpLastName2.length>0 || cpITPAN2.length>0 )
		{
		//alert("if loop="+firstName1);
		 document.getElementById("p3").style.display = "block";  
		}
	
 	
    var cpLastName3= document.getElementById("cpLastName3").value;
          	//alert("2"+cpLastName1);
    var cpITPAN3= document.getElementById("cpITPAN3").value;
	
	var firstName3= document.getElementById("cpFirstName3").value;
	//alert(firstName5);
	//alert("firstName1.length=="+firstName1.length);
	if(firstName3.length>0 || cpLastName3.length>0 || cpITPAN3.length>0 )
		{
		//alert("if loop="+firstName1);
		 document.getElementById("p4").style.display = "block";  
		}
	
	
	var firstName4= document.getElementById("cpFirstName4").value;
	
	 var cpLastName4= document.getElementById("cpLastName4").value;
   	//alert("2"+cpLastName1);
    var cpITPAN4= document.getElementById("cpITPAN4").value;

	//alert(firstName6);
	//alert("firstName1.length=="+firstName6.length);
	if(firstName4.length>0 || cpLastName4.length>0 || cpITPAN4.length>0)
		{
		//alert("if loop="+firstName1);
		 document.getElementById("p5").style.display = "block";  
		}
	
	var firstName5= document.getElementById("cpFirstName5").value;
	//alert(firstName7);
	//alert("firstName1.length=="+firstName7.length);
	if(firstName5.length>0)
		{
		//alert("if loop="+firstName7);
		 document.getElementById("p6").style.display = "block";  
		}
	
	var firstName6= document.getElementById("cpFirstName6").value;
	////alert(firstName8);
	//alert("firstName1.length=="+firstName8.length);
	if(firstName6.length>0)
		{
		//alert("if loop="+firstName8);
		 document.getElementById("p7").style.display = "block";  
		}
	
	
	var firstName6= document.getElementById("cpFirstName6").value;
	////alert(firstName8);
	//alert("firstName1.length=="+firstName8.length);
	if(firstName6.length>0)
		{
		//alert("if loop="+firstName8);
		 document.getElementById("p7").style.display = "block";  
		}
	
	
	var firstName7= document.getElementById("cpFirstName7").value;
	////alert(firstName8);
	//alert("firstName1.length=="+firstName8.length);
	if(firstName7.length>0)
		{
		//alert("if loop="+firstName8);
		 document.getElementById("p8").style.display = "block";  
		}
	
	
	
	var firstName8= document.getElementById("cpFirstName8").value;
	////alert(firstName8);
	//alert("firstName1.length=="+firstName8.length);
	if(firstName8.length>0)
		{
		//alert("if loop="+firstName8);
		 document.getElementById("p9").style.display = "block";  
		}
	
	
	var firstName9= document.getElementById("cpFirstName9").value;
	////alert(firstName8);
	//alert("firstName1.length=="+firstName8.length);
	if(firstName9.length>0)
		{
		//alert("if loop="+firstName8);
		 document.getElementById("p10").style.display = "block";  
		}
	
	 
	 var osMLI=parseFloat(document.forms[0].projectedSalesTurnover.value);
	 if(isNaN(osMLI)){
	    	document.getElementById("projectedSalesTurnover").value='0.0';
	 }else{
	    if((osMLI!=null ||osMLI!="" )){
	    	document.getElementById("projectedSalesTurnover").value=osMLI;
	    }else{
	    	document.getElementById("projectedSalesTurnover").value='0.0';
	    }
	 }
	 
	 var presentSalesTurnoverval=parseFloat(document.forms[0].presentSalesTurnover.value);
	 if(isNaN(presentSalesTurnoverval)){
	    	document.getElementById("presentSalesTurnover").value='0.0';
	 }else{
	    if((presentSalesTurnoverval!=null ||presentSalesTurnoverval!="" )){
	    	document.getElementById("presentSalesTurnover").value=presentSalesTurnoverval;
	    }else{
	    	document.getElementById("presentSalesTurnover").value='0.0';
	    }
	 }
	 
	 
	 if(isNaN(existingFacilityAmount)){
	    	document.getElementById("pmr15Percent").value='0.0';
	 }else{
	    if((existingFacilityAmount!=null ||existingFacilityAmount!="" )){
	    	document.getElementById("pmr15Percent").value=existingFacilityAmount;
	    }
	 }
	 
	  
	 
	 var totalMIcollatSecAmt1=parseFloat(document.forms[0].psTotal.value);
	 if(isNaN(totalMIcollatSecAmt1)){
		// alert("1");
	    	document.getElementById("psTotal").value='0.0';
	 }else{
	    if((totalMIcollatSecAmt1!=null ||totalMIcollatSecAmt1!="" )){
	    //	 alert("1");
	    	document.getElementById("psTotal").value=totalMIcollatSecAmt1;
	    }else{
	    	// alert("1");
	    	document.getElementById("psTotal").value='0.0';
	    }
	 }
	
	 
	 //alert("ANand=="+document.forms[0].pplOS.value);
	 var pplOSVal=parseFloat(document.forms[0].projectedExports.value);
	 if(isNaN(pplOSVal)){
		// alert("1");
	    	document.getElementById("projectedExports").value='0.0';
	 }else{
		 //alert("2");
	    if((pplOSVal!=null ||pplOSVal!="" )){
	    	document.getElementById("projectedExports").value=pplOSVal;
	    }else{
	    	// alert("3");
	    	document.getElementById("projectedExports").value='0.0';
	    }
	 }
	 
	
	 
	 
	 
	
	 
}
	window.onload=callWhenPageWillLoad;


 
</script>
 
 
 <script type="text/javascript">
 
 function CalSecurityTotalAmt()
 {
	//alert("CalSecurityTotalAmt");
	 var landVal=document.getElementById('landValue').value;
	// alert(landVal);
	 var mVal=document.getElementById('machineValue').value;
	 var bVal=document.getElementById('bldgValue').value;
	 var cVal=document.getElementById('currentAssetsValue').value;
	 var aVal=document.getElementById('assetsValue').value;
	 var oVal=document.getElementById('othersValue').value;
	 
	
	 
	 
	 
	 var psTotalVal=Number(landVal)+Number(mVal)+Number(bVal)+Number(cVal)+Number(aVal)+Number(oVal);
	
	 document.getElementById('psTotal2').value=parseFloat(psTotalVal);
	 
	 document.getElementById('psTotal1').value=parseFloat(psTotalVal);
	 
	 
	
 }
 

 function calSubDebtEquity()
 {
	 
	
	 var pmrEquityVal=document.getElementById('pmrEquity').value;
	//alert("pmrEquityVal="+pmrEquityVal);
	 var pmrDebtVal=document.getElementById('pmrDebt').value;
	 //alert("pmrDebtVal="+pmrDebtVal);
	 
	  var pmrEquityVal1=document.getElementById('pmrEquity1').value;
	  //alert("pmrEquityVal1="+pmrEquityVal1);
	 var pmrDebtVal1=document.getElementById('pmrDebt1').value;
	 //alert("pmrDebtVal1="+pmrDebtVal1);
	 
	 var pmrEquityVal2=document.getElementById('pmrEquity2').value;
	 //alert("pmrEquityVal2="+pmrEquityVal2);
	 var pmrDebtVal2=document.getElementById('pmrDebt2').value;
	 //alert("pmrDebtVal2="+pmrDebtVal2);
	 
	 
	 var pmrEquityVal3=document.getElementById('pmrEquity3').value;
	 //alert("pmrEquityVal3="+pmrEquityVal3);
	 var pmrDebtVal3=document.getElementById('pmrDebt3').value;
	 //alert("pmrDebtVal3="+pmrDebtVal3);
	 
	  var pmrEquityVal4=document.getElementById('pmrEquity4').value;
	 //alert("pmrEquityVal4="+pmrEquityVal4);
	 var pmrDebtVal4=document.getElementById('pmrDebt4').value;
	 //alert("pmrDebtVal4="+pmrDebtVal4);
	
	 var pmrEquityVal5=document.getElementById('pmrEquity5').value;
	// alert("pmrEquityVal5="+pmrEquityVal5);
	 var pmrDebtVal5=document.getElementById('pmrDebt5').value;
	 //alert("pmrDebtVal5="+pmrDebtVal5);
	 
	  var pmrEquityVal6=document.getElementById('pmrEquity6').value;
	// alert("pmrEquityVal6="+pmrEquityVal6);
	 var pmrDebtVal6=document.getElementById('pmrDebt6').value;
	 //alert("pmrDebtVal6="+pmrDebtVal6);
	 
	 var pmrEquityVal7=document.getElementById('pmrEquity7').value;
	// alert("pmrEquityVal7="+pmrEquityVal7);
	 var pmrDebtVal7=document.getElementById('pmrDebt7').value;
	 //alert("pmrDebtVal7="+pmrDebtVal7);
	 
	 var pmrEquityVal8=document.getElementById('pmrEquity8').value;
	// alert("pmrEquityVal8="+pmrEquityVal8);
	 var pmrDebtVal8=document.getElementById('pmrDebt8').value;
	// alert("pmrDebtVal8="+pmrDebtVal8);
	 
	 var pmrEquityVal9=document.getElementById('pmrEquity9').value;
	 //alert("pmrEquityVal9="+pmrEquityVal9);
	 var pmrDebtVal9=document.getElementById('pmrDebt9').value;
	 //alert("pmrDebtVal9="+pmrDebtVal9);
	 
	 var pmr15Percent=Number(pmrEquityVal)+Number(pmrDebtVal)+Number(pmrEquityVal1)+Number(pmrDebtVal1)+Number(pmrEquityVal2)+Number(pmrDebtVal2)
	 +Number(pmrEquityVal3)+Number(pmrDebtVal3)
	 +Number(pmrEquityVal4)+Number(pmrDebtVal4)+Number(pmrEquityVal5)+Number(pmrDebtVal5)+Number(pmrEquityVal6)+Number(pmrDebtVal6)+
	 Number(pmrEquityVal7)+Number(pmrDebtVal7)+Number(pmrEquityVal8)+Number(pmrDebtVal8)
	 Number(pmrEquityVal9)+Number(pmrDebtVal9); 
	// alert(pmr15Percent);
	// var pmr15Percent=Number(pmrEquityVal)+Number(pmrDebtVal)+Number(pmrEquityVal1)+Number(pmrDebtVal1);
	 var pmr15PercentVal=(pmr15Percent)*(15/100);
	 document.getElementById('pmr15Percent').value=parseFloat(pmr15PercentVal); 
	 document.getElementById('pmr15Percent1').value=parseFloat(pmr15PercentVal);
	 
 }
 

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

	 

		 
 <tr align="left">
		<td colspan="12" class="SubHeading" height="28" width="843"><br>
			Details of Promoter 1
			&nbsp;</td>
	 </tr> 
<tr align="left">
	<td colspan="12" >
		<table width="100%" id="p1"><input type="hidden"  id="block" />
			<tbody>
				<tr>
					<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
					<font color="#FF0000" size="2">*</font>&nbsp;<bean:message
				key="cpFirstName" />
				</TD>
				<td align="left" valign="top" class="TableData" width="16.6%"> <html:text property="cpFirstName" size="20" alt="cpFirstName"
				styleId="cpFirstName" name="appForm" maxlength="20" /> 
 				</TD> 
				<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
					<font color="#FF0000" size="2">*</font>&nbsp;<bean:message
				key="cpLastName" />
				</TD>
			<TD align="left" valign="top" class="TableData" width="16.6%">
			 <html:text property="cpLastName" size="20" alt="cpLastName"
			 styleId="cpLastName"	name="appForm" maxlength="20" /> </TD>
			<TD align="left" valign="top" class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font><bean:message
				key="chiefItpan" />
		</TD>
		<TD align="left" valign="top" class="TableData" width="16.6%">
		 <html:text property="cpITPAN" size="15" alt="cpITPAN"
			 styleId="cpITPAN"	name="appForm" maxlength="10" /> 
		</TD>	
				
				</tr>	
				<tr>
				<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
					<bean:message key="dob" />
				</TD>
				<TD align="left" valign="top" class="TableData"  width="16.6%">
				 <html:text property="cpDOB" size="15" alt="dob"
						name="appForm" maxlength="10" /> <IMG src="images/CalendarIcon.gif"
				styleId="cpDOB"	width="20" onClick="showCalendar('appForm.cpDOB')" align="center">
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
				<TD align="left" valign="top" class="ColumnBackground" width="16.6%"><font color="#FF0000" size="2">*</font> Promoter Category </TD>
			<TD align="left" valign="top" class="TableData"  width="16.6%">
			 <html:select property="socialCategory" name="appForm" styleId="socialCategory" >
					<html:option value="">Select</html:option>
					<html:options name="appForm" property="socialCategoryList"></html:options>
		
				</html:select>
				
					<!-- <html:text property="socialCategory" styleId="socialCategory1"/> -->
			</TD>
				</tr>	
			 <tr>
					<TD align="left" valign="top" class="ColumnBackground" width="16.6%"> 
				<font color="#FF0000" size="2">*</font>&nbsp;	<bean:message key="email" /></TD>
			<TD align="left" valign="top" class="TableData"  width="16.6%">
				
				<html:text property="email" size="20"
				styleId="email" alt="email" name="appForm" maxlength="50"/>
			</TD>
			<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
			<font color="#FF0000" size="2">*</font>&nbsp;Mobile No</td>
		<TD align="left" valign="top" class="TableData" width="16.6%"><html:text property="proMobileNo" size="20"
				styleId="proMobileNo" alt="proMobileNo" name="appForm" maxlength="10"
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
						<font color="#FF0000" size="2">*</font>&nbsp;
						<bean:message key="pmrEquity" />
					</td>
					<td align="left" valign="top" class="TableData" width="16.6%" >
						 <html:text  name="appForm" property="pmrEquity" maxlength="9"   size="25"  styleId="pmrEquity" alt="pmrEquity" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onblur="calSubDebtEquity();"/>
					</td>
					<td align="left" valign="top" class="ColumnBackground" width="16.6%">
						<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="pmrDebt" />
					</td>
					<td align="left" valign="top" class="TableData" width="16.6%" >
						 <html:text  name="appForm" property="pmrDebt" maxlength="9"   size="25"  styleId="pmrDebt" alt="pmrDebt" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onblur="calSubDebtEquity();"/>
					</td>
					<td align="left" valign="top" class="ColumnBackground" width="16.6%">
						 
					</td>
					<td align="left" valign="top" class="TableData" width="16.6%" >
						 
					</td>
				</tr>	
				<tr>
					<td>	
					<button type="button" id="addbtn" class="btn-add" onclick="addBlock(),btnA2function()" >
					Add</button></td>
				</tr>
			</tbody>
		</table>
		<table width="100%" id="p2" style="display:none">
			<tbody>
			<tr>
				<td colspan="12" class="SubHeading" height="28" width="843"><br>
			Details of Promoter 2
			&nbsp;</td>
			</tr>
				<tr>
					<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
					<font color="#FF0000" size="2">*</font>&nbsp;First Name
				</TD>
				<td align="left" valign="top" class="TableData" width="16.6%"> <html:text property="cpFirstName1" size="20" alt="firstName1"
				styleId="cpFirstName1" name="appForm" maxlength="20" /> 
			
 				</TD> 
				<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
					<font color="#FF0000" size="2">*</font>&nbsp;Last Name
				</TD>
			<TD align="left" valign="top" class="TableData" width="16.6%">
			 <html:text property="cpLastName1" size="20" alt="lastName1"
			styleId="cpLastName1"	name="appForm" maxlength="20" /> </TD>
			<TD align="left" valign="top" class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="chiefItpan" />
		</TD>
		<TD align="left" valign="top" class="TableData" width="16.6%">
		 <html:text property="cpITPAN1" size="15" alt="chiefItpan1" styleId="cpITPAN1"
				name="appForm" maxlength="10" onblur="promterITPANChk1();" /> 
				<html:hidden property="cpITPAN1" />
		</TD>	
				
				</tr>	
				<tr>
				<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
					<bean:message key="dob" />
				</TD>
				<TD align="left" valign="top" class="TableData"  width="16.6%">
				 <html:text property="cpDOB1" size="15" alt="dob"
						name="appForm" maxlength="10" /> <IMG src="images/CalendarIcon.gif"
					width="20" onClick="showCalendar('appForm.cpDOB')" align="center">
					</TD>
				<TD align="left" valign="top" class="ColumnBackground"  width="16.6%"><font
					color="#FF0000" size="2">*</font>&nbsp;<bean:message key="gender" />
				</TD>
				<TD align="left" valign="top" class="TableData"  width="16.6%">
					 <html:radio name="appForm" value="M" property="cpGender1">
					</html:radio> <bean:message key="male" />&nbsp;&nbsp;&nbsp; <html:radio
						name="appForm" value="F" property="cpGender1"></html:radio> <bean:message
						key="female" />  
			
				</TD>
				<TD align="left" valign="top" class="ColumnBackground" width="16.6%"><font color="#FF0000" size="2">*</font> Promoter Category </TD>
			<TD align="left" valign="top" class="TableData"  width="16.6%">
			 <html:select property="socialCategory1" name="appForm" styleId="socialCategory1">
					<html:option value="">Select</html:option>
					<html:options name="appForm" property="socialCategoryList"></html:options>
		
				</html:select>
			</TD>
				</tr>	
			 <tr>
					<TD align="left" valign="top" class="ColumnBackground" width="16.6%"> 
					Email Id </TD>
			<TD align="left" valign="top" class="TableData"  width="16.6%">
				
				<html:text property="email1" size="20"
				styleId="email1" alt="email" name="appForm" maxlength="50" />
				
				
			</TD>
			<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
			<font color="#FF0000" size="2">*</font>&nbsp;Mobile No</td>
		<TD align="left" valign="top" class="TableData" width="16.6%"><html:text property="proMobileNo1" size="20"
				styleId="proMobileNo1" alt="proMobileNo1" name="appForm" maxlength="10"
				onkeypress="return numbersOnly(this, event)"
				onkeyup="isValidNumber(this)" />
				</td>
				<TD align="left" valign="top" class="ColumnBackground" width="16.6%">&nbsp;
				<bean:message key="promoterAdharNo" /></td>
		<TD align="left" valign="top" class="TableData" width="16.6%" ><html:text
				property="adhar1" size="20" alt="adhar1" name="appForm" maxlength="12"
				onkeypress="return numbersOnly(this, event)"
				onkeyup="isValidNumber(this)" /></td>
				
				</tr>	
				<tr>
					<td align="left" valign="top" class="ColumnBackground" width="16.6%">
						Investment as equity in the MSME unit
					</td>
					<td align="left" valign="top" class="TableData" width="16.6%" >
						 <html:text  name="appForm" property="pmrEquity1" maxlength="9"   size="25"  styleId="pmrEquity1" alt="pmrEquity1" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onblur="calSubDebtEquity();"/>
					</td>
					<td align="left" valign="top" class="ColumnBackground" width="16.6%">
						Investment as debt/loan in the MSME unit
					</td>
					<td align="left" valign="top" class="TableData" width="16.6%" >
						 <html:text  name="appForm" property="pmrDebt1" maxlength="9"   size="25"  styleId="pmrDebt1" alt="pmrDebt1" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onblur="calSubDebtEquity();"/>
					</td>
					<td align="left" valign="top" class="ColumnBackground" width="16.6%">
						 
					</td>
					<td align="left" valign="top" class="TableData" width="16.6%" >
						 
					</td>
				</tr>	
				<tr>
					<td>	
					<button type="button" class="btn-add" id="btn2" onclick="btnA3function()">
					Add</button>
					<button type="button" class="btn-remove" onclick="btnR2function()" onblur="calSubDebtEquity();">
					Remove</button>
					</td>
				</tr>
			</tbody>
		</table>
		<table width="100%" id="p3" style="display:none">
			<tbody>
			<tr>
				<td colspan="12" class="SubHeading" height="28" width="843"><br>
			Details of Promoter 3
			&nbsp;</td>
			</tr>
				<tr>
					<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
					<font color="#FF0000" size="2">*</font>&nbsp;First Name
				</TD>
				<td align="left" valign="top" class="TableData" width="16.6%"> <html:text property="cpFirstName2" size="20" alt="firstName"
				styleId="cpFirstName2" name="appForm" maxlength="20" /> 
 				</TD> 
				<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
					<font color="#FF0000" size="2">*</font>&nbsp;Last Name
				</TD>
			<TD align="left" valign="top" class="TableData" width="16.6%">
			 <html:text property="cpLastName2" size="20" alt="lastName"
			styleId="cpLastName2"	name="appForm" maxlength="20" /> </TD>
			<TD align="left" valign="top" class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font><bean:message
				key="chiefItpan" />
		</TD>
		<TD align="left" valign="top" class="TableData" width="16.6%">
		 <html:text property="cpITPAN2" size="15" alt="chiefItpan"
			styleId="cpITPAN2"	name="appForm" maxlength="10"  onblur="promterITPANChk2();"/> 	
		</TD>	
				
				</tr>	
				<tr>
				<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
					<bean:message key="dob" />
				</TD>
				<TD align="left" valign="top" class="TableData"  width="16.6%">
				 <html:text property="cpDOB2" size="15" alt="dob"
						name="appForm" maxlength="10" /> <IMG src="images/CalendarIcon.gif"
					width="20" onClick="showCalendar('appForm.cpDOB')" align="center">
					</TD>
				<TD align="left" valign="top" class="ColumnBackground"  width="16.6%"><font
					color="#FF0000" size="2">*</font>&nbsp;<bean:message key="gender" />
				</TD>
				<TD align="left" valign="top" class="TableData"  width="16.6%">
					 <html:radio name="appForm" value="M" property="cpGender2">
					</html:radio> <bean:message key="male" />&nbsp;&nbsp;&nbsp; <html:radio
						name="appForm" value="F" property="cpGender2"></html:radio> <bean:message
						key="female" />  
			
				</TD>
				<TD align="left" valign="top" class="ColumnBackground" width="16.6%"><font color="#FF0000" size="2">*</font> Promoter Category </TD>
			<TD align="left" valign="top" class="TableData"  width="16.6%">
			 <html:select property="socialCategory2" name="appForm" styleId="socialCategory2">
					<html:option value="">Select</html:option>
					<html:options name="appForm" property="socialCategoryList"></html:options>
		
				</html:select>
			</TD>
				</tr>	
			 <tr>
					<TD align="left" valign="top" class="ColumnBackground" width="16.6%"> 
					Email Id </TD>
			<TD align="left" valign="top" class="TableData"  width="16.6%">
				<html:text property="email2" size="20"
			styleId="email2" alt="email" name="appForm" maxlength="50" />
			</TD>
			<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
			<font color="#FF0000" size="2">*</font>&nbsp;Mobile No</td>
		<TD align="left" valign="top" class="TableData" width="16.6%"><html:text property="proMobileNo2" size="20"
				styleId="proMobileNo2" alt="proMobileNo" name="appForm" maxlength="10"
				onkeypress="return numbersOnly(this, event)"
				onkeyup="isValidNumber(this)" />
				</td>
				<TD align="left" valign="top" class="ColumnBackground" width="16.6%">&nbsp;
				<bean:message key="promoterAdharNo" /></td>
		<TD align="left" valign="top" class="TableData" width="16.6%" ><html:text
				property="adhar2" size="20" alt="adhar" name="appForm" maxlength="12"
				onkeypress="return numbersOnly(this, event)"
				onkeyup="isValidNumber(this)" /></td>
				
				</tr>	
				<tr>
					<td align="left" valign="top" class="ColumnBackground" width="16.6%">
						Investment as equity in the MSME unit
					</td>
					<td align="left" valign="top" class="TableData" width="16.6%" >
						 <html:text  name="appForm" property="pmrEquity2" maxlength="9"   size="25"  styleId="pmrEquity2" alt="pmrEquity" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onblur="calSubDebtEquity();"/>
					</td>
					<td align="left" valign="top" class="ColumnBackground" width="16.6%">
						Investment as debt/loan in the MSME unit
					</td>
					<td align="left" valign="top" class="TableData" width="16.6%" >
						 <html:text  name="appForm" property="pmrDebt2" maxlength="9"   size="25"  styleId="pmrDebt2" alt="pmrDebt2" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onblur="calSubDebtEquity();"/>
					</td>
					<td align="left" valign="top" class="ColumnBackground" width="16.6%">
						 
					</td>
					<td align="left" valign="top" class="TableData" width="16.6%" >
						 
					</td>
				</tr>	
				<tr>
					<td>	
					<button type="button" class="btn-add" id="btn3" onclick="btnA4function()">
					Add</button>
					<button type="button" class="btn-remove" onclick="btnR3function()" onblur="calSubDebtEquity();">
					Remove</button>
					</td>
				</tr>
			</tbody>
		</table>
		<table width="100%" id="p4" style="display:none">
			<tbody>
			<tr>
				<td colspan="12" class="SubHeading" height="28" width="843"><br>
			Details of Promoter 4
			&nbsp;</td>
			</tr>
				<tr>
					<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
					<font color="#FF0000" size="2">*</font>&nbsp;First Name
				</TD>
				<td align="left" valign="top" class="TableData" width="16.6%"> <html:text property="cpFirstName3" size="20" alt="firstName"
				styleId="cpFirstName3" name="appForm" maxlength="20" /> 
 				</TD> 
				<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
					<font color="#FF0000" size="2">*</font>&nbsp;Last Name
				</TD>
			<TD align="left" valign="top" class="TableData" width="16.6%">
			 <html:text property="cpLastName3" size="20" alt="lastName"
			styleId="cpLastName3"	name="appForm" maxlength="20" /> </TD>
			<TD align="left" valign="top" class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font><bean:message
				key="chiefItpan" />
		</TD>
		<TD align="left" valign="top" class="TableData" width="16.6%">
		 <html:text property="cpITPAN3" size="15" alt="chiefItpan"
			styleId="cpITPAN3"	name="appForm" maxlength="10" onblur="promterITPANChk3();"/> 
		</TD>	
				
				</tr>	
				<tr>
				<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
					<bean:message key="dob" />
				</TD>
				<TD align="left" valign="top" class="TableData"  width="16.6%">
				 <html:text property="cpDOB3" size="15" alt="dob"
						name="appForm" maxlength="10" /> <IMG src="images/CalendarIcon.gif"
					width="20" onClick="showCalendar('appForm.cpDOB')" align="center">
					</TD>
				<TD align="left" valign="top" class="ColumnBackground"  width="16.6%"><font
					color="#FF0000" size="2">*</font>&nbsp;<bean:message key="gender" />
				</TD>
				<TD align="left" valign="top" class="TableData"  width="16.6%">
					 <html:radio name="appForm" value="M" property="cpGender3">
					</html:radio> <bean:message key="male" />&nbsp;&nbsp;&nbsp; <html:radio
						name="appForm" value="F" property="cpGender3"></html:radio> <bean:message
						key="female" />  
			
				</TD>
				<TD align="left" valign="top" class="ColumnBackground" width="16.6%"><font color="#FF0000" size="2">*</font> Promoter Category </TD>
			<TD align="left" valign="top" class="TableData"  width="16.6%">
			 <html:select property="socialCategory3" name="appForm" styleId="socialCategory3">
					<html:option value="">Select</html:option>
					<html:options name="appForm" property="socialCategoryList"></html:options>
		
				</html:select>
			</TD>
				</tr>	
			 <tr>
					<TD align="left" valign="top" class="ColumnBackground" width="16.6%"> 
					Email Id </TD>
			<TD align="left" valign="top" class="TableData"  width="16.6%">
			<html:text property="email3" size="20"
			styleId="email3"	alt="email3" name="appForm" maxlength="50"/>
			</TD>
			
			<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
			<font color="#FF0000" size="2">*</font>&nbsp;Mobile No</td>
		<TD align="left" valign="top" class="TableData" width="16.6%"><html:text property="proMobileNo3" size="20"
				alt="proMobileNo3" name="appForm" maxlength="10"
				onkeypress="return numbersOnly(this, event)"
				onkeyup="isValidNumber(this)" />
				</td>
				
				<TD align="left" valign="top" class="ColumnBackground" width="16.6%">&nbsp;
				<bean:message key="promoterAdharNo" /></td>
		<TD align="left" valign="top" class="TableData" width="16.6%" ><html:text
				property="adhar3" size="20" alt="adhar3" name="appForm" maxlength="12"
				onkeypress="return numbersOnly(this, event)"
				onkeyup="isValidNumber(this)" /></td>
				
				</tr>	
				<tr>
					<td align="left" valign="top" class="ColumnBackground" width="16.6%">
						Investment as equity in the MSME unit
					</td>
					<td align="left" valign="top" class="TableData" width="16.6%" >
						 <html:text  name="appForm" property="pmrEquity3" maxlength="9"   size="25"  styleId="pmrEquity3" alt="pmrEquity" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onblur="calSubDebtEquity();"/>
					</td>
					<td align="left" valign="top" class="ColumnBackground" width="16.6%">
						Investment as debt/loan in the MSME unit
					</td>
					<td align="left" valign="top" class="TableData" width="16.6%" >
						 <html:text  name="appForm" property="pmrDebt3" maxlength="9"   size="25"  styleId="pmrDebt3" alt="pmrDebt" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onblur="calSubDebtEquity();"/>
					</td>
					<td align="left" valign="top" class="ColumnBackground" width="16.6%">
						 
					</td>
					<td align="left" valign="top" class="TableData" width="16.6%" >
						 
					</td>
				</tr>	
				<tr>
					<td>	
					<button type="button" class="btn-add" id="btn4" onclick="btnA5function()">
					Add</button>
					<button type="button" class="btn-remove" onclick="btnR4function()" onblur="calSubDebtEquity();">
					Remove</button>
					</td>
				</tr>
			</tbody>
		</table>
		<table width="100%" id="p5" style="display:none">
			<tbody>
			<tr>
				<td colspan="12" class="SubHeading" height="28" width="843"><br>
			Details of Promoter 5
			&nbsp;</td>
			</tr>
				<tr>
					<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
					<font color="#FF0000" size="2">*</font>&nbsp;First Name
				</TD>
				<td align="left" valign="top" class="TableData" width="16.6%"> <html:text property="cpFirstName4" size="20" alt="firstName"
				styleId="cpFirstName4" name="appForm" maxlength="20" /> 
 				</TD> 
				<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
					<font color="#FF0000" size="2">*</font>&nbsp;Last Name
				</TD>
			<TD align="left" valign="top" class="TableData" width="16.6%">
			 <html:text property="cpLastName4" size="20" alt="lastName"
			styleId="cpLastName4"	name="appForm" maxlength="20" /> </TD>
			<TD align="left" valign="top" class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font> <bean:message
				key="chiefItpan" />
		</TD>
		<TD align="left" valign="top" class="TableData" width="16.6%">
		 <html:text property="cpITPAN4" size="15" alt="chiefItpan"
			styleId="cpITPAN4"	name="appForm" maxlength="10" onblur="promterITPANChk4();"/> 
		</TD>	
				
				</tr>	
				<tr>
				<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
					<bean:message key="dob" />
				</TD>
				<TD align="left" valign="top" class="TableData"  width="16.6%">
				 <html:text property="cpDOB4" size="15" alt="dob"
						name="appForm" maxlength="10" /> <IMG src="images/CalendarIcon.gif"
					width="20" onClick="showCalendar('appForm.cpDOB')" align="center">
					</TD>
				<TD align="left" valign="top" class="ColumnBackground"  width="16.6%"><font
					color="#FF0000" size="2">*</font>&nbsp;<bean:message key="gender" />
				</TD>
				<TD align="left" valign="top" class="TableData"  width="16.6%">
					 <html:radio name="appForm" value="M" property="cpGender4">
					</html:radio> <bean:message key="male" />&nbsp;&nbsp;&nbsp; <html:radio
						name="appForm" value="F" property="cpGender4"></html:radio> <bean:message
						key="female" />  
			
				</TD>
				<TD align="left" valign="top" class="ColumnBackground" width="16.6%"><font color="#FF0000" size="2">*</font> Promoter Category </TD>
			<TD align="left" valign="top" class="TableData"  width="16.6%">
			 <html:select property="socialCategory4" name="appForm" styleId="socialCategory4">
					<html:option value="">Select</html:option>
					<html:options name="appForm" property="socialCategoryList"></html:options>
		
				</html:select>
			</TD>
				</tr>	
			 <tr>
					<TD align="left" valign="top" class="ColumnBackground" width="16.6%"> 
					Email Id </TD>
			<TD align="left" valign="top" class="TableData"  width="16.6%">
				
				
				<html:text property="email4" size="20"
				styleId="email4" alt="email4" name="appForm" maxlength="50" />
				
			</TD>
			<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
			<font color="#FF0000" size="2">*</font>&nbsp;Mobile No</td>
		<TD align="left" valign="top" class="TableData" width="16.6%"><html:text property="proMobileNo4" size="20"
				styleId="proMobileNo4" alt="proMobileNo4" name="appForm" maxlength="10"
				onkeypress="return numbersOnly(this, event)"
				onkeyup="isValidNumber(this)" />
				</td>
				<TD align="left" valign="top" class="ColumnBackground" width="16.6%">&nbsp;
				<bean:message key="promoterAdharNo" /></td>
		<TD align="left" valign="top" class="TableData" width="16.6%" ><html:text
				property="adhar4" size="20" alt="adhar4" name="appForm" maxlength="12"
				onkeypress="return numbersOnly(this, event)"
				onkeyup="isValidNumber(this)" /></td>
				
				</tr>	
				<tr>
					<td align="left" valign="top" class="ColumnBackground" width="16.6%">
						Investment as equity in the MSME unit
					</td>
					<td align="left" valign="top" class="TableData" width="16.6%" >
						 <html:text  name="appForm" property="pmrEquity4" maxlength="9"   size="25"  styleId="pmrEquity4" alt="pmrEquity" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onblur="calSubDebtEquity();"/>
					</td>
					<td align="left" valign="top" class="ColumnBackground" width="16.6%">
						Investment as debt/loan in the MSME unit
					</td>
					<td align="left" valign="top" class="TableData" width="16.6%" >
						 <html:text  name="appForm" property="pmrDebt4" maxlength="9"   size="25"  styleId="pmrDebt4" alt="pmrDebt" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onblur="calSubDebtEquity();"/>
					</td>
					<td align="left" valign="top" class="ColumnBackground" width="16.6%">
						 
					</td>
					<td align="left" valign="top" class="TableData" width="16.6%" >
						 
					</td>
				</tr>	
				<tr>
					<td>	
					<button type="button" class="btn-add" icpFirstName4d="btn5" onclick="btnA6function()">
					Add</button>
					<button type="button" class="btn-remove" onclick="btnR5function()" onblur="calSubDebtEquity();">
					Remove</button>
					</td>
				</tr>
			</tbody>
		</table>
		<table width="100%" id="p6" style="display:none">
			<tbody>
			<tr>
				<td colspan="12" class="SubHeading" height="28" width="843"><br>
			Details of Promoter 6
			&nbsp;</td>
			</tr>
				<tr>
					<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
					<font color="#FF0000" size="2">*</font>&nbsp;First Name
				</TD>
				<td align="left" valign="top" class="TableData" width="16.6%"> <html:text property="cpFirstName5" size="20" alt="firstName"
				styleId="cpFirstName5" name="appForm" maxlength="20" /> 
 				</TD> 
				<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
					<font color="#FF0000" size="2">*</font>&nbsp;Last Name
				</TD>
			<TD align="left" valign="top" class="TableData" width="16.6%">
			 <html:text property="cpLastName5" size="20" alt="lastName"
			styleId="cpLastName5"	name="appForm" maxlength="20" /> </TD>
			<TD align="left" valign="top" class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font><bean:message
				key="chiefItpan" />
		</TD>
		<TD align="left" valign="top" class="TableData" width="16.6%">
		 <html:text property="cpITPAN5" size="15" alt="chiefItpan"
				styleId="cpITPAN5" name="appForm" maxlength="10" onblur="promterITPANChk5();"/> 
		</TD>	
				
				</tr>	
				<tr>
				<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
					<bean:message key="dob" />
				</TD>
				<TD align="left" valign="top" class="TableData"  width="16.6%">
				 <html:text property="cpDOB5" size="15" alt="dob"
						name="appForm" maxlength="10" /> <IMG src="images/CalendarIcon.gif"
					width="20" onClick="showCalendar('appForm.cpDOB')" align="center">
					</TD>
				<TD align="left" valign="top" class="ColumnBackground"  width="16.6%"><font
					color="#FF0000" size="2">*</font>&nbsp;<bean:message key="gender" />
				</TD>
				<TD align="left" valign="top" class="TableData"  width="16.6%">
					 <html:radio name="appForm" value="M" property="cpGender5">
					</html:radio> <bean:message key="male" />&nbsp;&nbsp;&nbsp; <html:radio
						name="appForm" value="F" property="cpGender5"></html:radio> <bean:message
						key="female" />  
			
				</TD>
				<TD align="left" valign="top" class="ColumnBackground" width="16.6%"><font color="#FF0000" size="2">*</font> Promoter Category </TD>
			<TD align="left" valign="top" class="TableData"  width="16.6%">
			 <html:select property="socialCategory5" name="appForm" styleId="socialCategory5" >
					<html:option value="">Select</html:option>
					<html:options name="appForm" property="socialCategoryList"></html:options>
		
				</html:select>
			</TD>
				</tr>	
			 <tr>
					<TD align="left" valign="top" class="ColumnBackground" width="16.6%"> 
					Email Id </TD>
			<TD align="left" valign="top" class="TableData"  width="16.6%">
				<html:text property="email5" size="20"
			styleId="email5"	alt="email5" name="appForm" maxlength="50"/>
			</TD>
			
			<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
			<font color="#FF0000" size="2">*</font>&nbsp;Mobile No</td>
		<TD align="left" valign="top" class="TableData" width="16.6%"><html:text property="proMobileNo5" size="20"
			styleId="proMobileNo5"	alt="proMobileNo5" name="appForm" maxlength="10"
				onkeypress="return numbersOnly(this, event)"
				onkeyup="isValidNumber(this)" />
				</td>
				<TD align="left" valign="top" class="ColumnBackground" width="16.6%">&nbsp;
				<bean:message key="promoterAdharNo" /></td>
		<TD align="left" valign="top" class="TableData" width="16.6%" ><html:text
				property="adhar5" size="20" alt="adhar5" name="appForm" maxlength="12"
				onkeypress="return numbersOnly(this, event)"
				onkeyup="isValidNumber(this)" /></td>
				
				</tr>	
				<tr>
					<td align="left" valign="top" class="ColumnBackground" width="16.6%">
						Investment as equity in the MSME unit
					</td>
					<td align="left" valign="top" class="TableData" width="16.6%" >
						 <html:text  name="appForm" property="pmrEquity5" maxlength="9"   size="25"  styleId="pmrEquity5" alt="pmrEquity" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onblur="calSubDebtEquity();"/>
					</td>
					<td align="left" valign="top" class="ColumnBackground" width="16.6%">
						Investment as debt/loan in the MSME unit
					</td>
					<td align="left" valign="top" class="TableData" width="16.6%" >
						 <html:text  name="appForm" property="pmrDebt5" maxlength="9"   size="25"  styleId="pmrDebt5" alt="pmrDebt" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onblur="calSubDebtEquity();"/>
					</td>
					<td align="left" valign="top" class="ColumnBackground" width="16.6%">
						 
					</td>
					<td align="left" valign="top" class="TableData" width="16.6%" >
						 
					</td>
				</tr>	
				<tr>
					<td>	
					<button type="button" class="btn-add" id="btn6" onclick="btnA7function()">
					Add</button>
					<button type="button" class="btn-remove" onclick="btnR6function()" onblur="calSubDebtEquity();">
					Remove</button>
					</td>
				</tr>
			</tbody>
		</table>
		<table width="100%" id="p7" style="display:none">
			<tbody>
			<tr>
				<td colspan="12" class="SubHeading" height="28" width="843"><br>
			Details of Promoter 7
			&nbsp;</td>
			</tr>
				<tr>
					<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
					<font color="#FF0000" size="2">*</font>&nbsp;First Name
				</TD>
				<td align="left" valign="top" class="TableData" width="16.6%"> <html:text property="cpFirstName6" size="20" alt="firstName"
				styleId="cpFirstName6" name="appForm" maxlength="20" /> 
 				</TD> 
				<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
					<font color="#FF0000" size="2">*</font>&nbsp;Last Name
				</TD>
			<TD align="left" valign="top" class="TableData" width="16.6%">
			 <html:text property="cpLastName6" size="20" alt="lastName"
			styleId="cpLastName6"		name="appForm" maxlength="20" /> </TD>
			<TD align="left" valign="top" class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font><bean:message
				key="chiefItpan" />
		</TD>
		<TD align="left" valign="top" class="TableData" width="16.6%">
		 <html:text property="cpITPAN6" size="15" alt="chiefItpan"
		styleId="cpITPAN6"		name="appForm" maxlength="10" onblur="promterITPANChk6();"/> 
		</TD>	
				
				</tr>	
				<tr>
				<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
					<bean:message key="dob" />
				</TD>
				<TD align="left" valign="top" class="TableData"  width="16.6%">
				 <html:text property="cpDOB6" size="15" alt="dob"
						name="appForm" maxlength="10" /> <IMG src="images/CalendarIcon.gif"
					width="20" onClick="showCalendar('appForm.cpDOB')" align="center">
					</TD>
				<TD align="left" valign="top" class="ColumnBackground"  width="16.6%"><font
					color="#FF0000" size="2">*</font>&nbsp;<bean:message key="gender" />
				</TD>
				<TD align="left" valign="top" class="TableData"  width="16.6%">
					 <html:radio name="appForm" value="M" property="cpGender6">
					</html:radio> <bean:message key="male" />&nbsp;&nbsp;&nbsp; <html:radio
						name="appForm" value="F" property="cpGender6"></html:radio> <bean:message
						key="female" />  
			
				</TD>
				<TD align="left" valign="top" class="ColumnBackground" width="16.6%"><font color="#FF0000" size="2">*</font> Promoter Category </TD>
			<TD align="left" valign="top" class="TableData"  width="16.6%">
			 <html:select property="socialCategory6" name="appForm" styleId="socialCategory6">
					<html:option value="">Select</html:option>
					<html:options name="appForm" property="socialCategoryList"></html:options>
		
				</html:select>
			</TD>
				</tr>	
			 <tr>
					<TD align="left" valign="top" class="ColumnBackground" width="16.6%"> 
					Email Id </TD>
			<TD align="left" valign="top" class="TableData"  width="16.6%">
				<html:text property="email6" size="20"
			styleId="email6"	alt="email6" name="appForm" maxlength="50"/>
			</TD>
			<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
			<font color="#FF0000" size="2">*</font>&nbsp;Mobile No</td>
		<TD align="left" valign="top" class="TableData" width="16.6%"><html:text property="proMobileNo6" size="20"
			styleId="proMobileNo6"	alt="proMobileNo" name="appForm" maxlength="10"
				onkeypress="return numbersOnly(this, event)"
				onkeyup="isValidNumber(this)" />
				</td>
				<TD align="left" valign="top" class="ColumnBackground" width="16.6%">&nbsp;
				<bean:message key="promoterAdharNo" /></td>
		<TD align="left" valign="top" class="TableData" width="16.6%" ><html:text
				property="adhar6" size="20" alt="adhar" name="appForm" maxlength="12"
				onkeypress="return numbersOnly(this, event)"
				onkeyup="isValidNumber(this)" /></td>
				
				</tr>	
				<tr>
					<td align="left" valign="top" class="ColumnBackground" width="16.6%">
						Investment as equity in the MSME unit
					</td>
					<td align="left" valign="top" class="TableData" width="16.6%" >
						 <html:text  name="appForm" property="pmrEquity6" maxlength="9"   size="25"  styleId="pmrEquity6" alt="pmrEquity" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onblur="calSubDebtEquity();"/>
					</td>
					<td align="left" valign="top" class="ColumnBackground" width="16.6%">
						Investment as debt/loan in the MSME unit
					</td>
					<td align="left" valign="top" class="TableData" width="16.6%" >
						 <html:text  name="appForm" property="pmrDebt6" maxlength="9"   size="25"  styleId="pmrDebt6" alt="pmrDebt" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onblur="calSubDebtEquity();"/>
					</td>
					<td align="left" valign="top" class="ColumnBackground" width="16.6%">
						 
					</td>
					<td align="left" valign="top" class="TableData" width="16.6%" >
						 
					</td>
				</tr>	
				<tr>
					<td>	
					<button type="button" class="btn-add" id="btn7" onclick="btnA8function()">
					Add</button>
					<button type="button" class="btn-remove" onclick="btnR7function()" onblur="calSubDebtEquity();">
					Remove</button>
					</td>
				</tr>
			</tbody>
		</table>
		<table width="100%" id="p8" style="display:none">
			<tbody>
			<tr>
				<td colspan="12" class="SubHeading" height="28" width="843"><br>
			Details of Promoter 8
			&nbsp;</td>
			</tr>
				<tr>
					<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
					<font color="#FF0000" size="2">*</font>&nbsp;First Name
				</TD>
				<td align="left" valign="top" class="TableData" width="16.6%"> <html:text property="cpFirstName7" size="20" alt="firstName"
				styleId="cpFirstName7" name="appForm" maxlength="20" /> 
 				</TD> 
				<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
					<font color="#FF0000" size="2">*</font>&nbsp;Last Name
				</TD>
			<TD align="left" valign="top" class="TableData" width="16.6%">
			 <html:text property="cpLastName7" size="20" alt="lastName"
			styleId="cpLastName7" 	name="appForm" maxlength="20" /> </TD>
			<TD align="left" valign="top" class="ColumnBackground">&nbsp;<bean:message
				key="chiefItpan" />
		</TD>
		<TD align="left" valign="top" class="TableData" width="16.6%">
		 <html:text property="cpITPAN7" size="15" alt="chiefItpan"
			styleId="cpITPAN7"	name="appForm" maxlength="10" onblur="promterITPANChk7();"/> 
		</TD>	
				
				</tr>	
				<tr>
				<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
					<bean:message key="dob" />
				</TD>
				<TD align="left" valign="top" class="TableData"  width="16.6%">
				 <html:text property="cpDOB7" size="15" alt="dob"
						name="appForm" maxlength="10" /> <IMG src="images/CalendarIcon.gif"
					width="20" onClick="showCalendar('appForm.cpDOB')" align="center">
					</TD>
				<TD align="left" valign="top" class="ColumnBackground"  width="16.6%"><font
					color="#FF0000" size="2">*</font>&nbsp;<bean:message key="gender" />
				</TD>
				<TD align="left" valign="top" class="TableData"  width="16.6%">
					 <html:radio name="appForm" value="M" property="cpGender7">
					</html:radio> <bean:message key="male" />&nbsp;&nbsp;&nbsp; <html:radio
						name="appForm" value="F" property="cpGender7"></html:radio> <bean:message
						key="female" />  
			
				</TD>
				<TD align="left" valign="top" class="ColumnBackground" width="16.6%"> Promoter Category </TD>
			<TD align="left" valign="top" class="TableData"  width="16.6%">
			 <html:select property="socialCategory7" name="appForm" styleId="socialCategory7">
					<html:option value="">Select</html:option>
					<html:options name="appForm" property="socialCategoryList"></html:options>
		
				</html:select>
			</TD>
				</tr>	
			 <tr>
					<TD align="left" valign="top" class="ColumnBackground" width="16.6%"> 
					Email Id </TD> 
			<TD align="left" valign="top" class="TableData"  width="16.6%">
			
				
				<html:text property="email7" size="20"
				styleId="email7" alt="email7" name="appForm" maxlength="50"/>
				
			</TD>
			<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
			<font color="#FF0000" size="2">*</font>&nbsp;Mobile No</td>
		<TD align="left" valign="top" class="TableData" width="16.6%"><html:text property="proMobileNo7" size="20"
			styleId="proMobileNo7"	alt="proMobileNo7" name="appForm" maxlength="10"
				onkeypress="return numbersOnly(this, event)"
				onkeyup="isValidNumber(this)" />
				</td>
				<TD align="left" valign="top" class="ColumnBackground" width="16.6%">&nbsp;
				<bean:message key="promoterAdharNo" /></td>
		<TD align="left" valign="top" class="TableData" width="16.6%" ><html:text
				property="adhar7" size="20" alt="adhar7" name="appForm" maxlength="12"
				onkeypress="return numbersOnly(this, event)"
				onkeyup="isValidNumber(this)" /></td>
				
				</tr>	
				<tr>
					<td align="left" valign="top" class="ColumnBackground" width="16.6%">
						Investment as equity in the MSME unit
					</td>
					<td align="left" valign="top" class="TableData" width="16.6%" >
						 <html:text  name="appForm" property="pmrEquity7" maxlength="9"   size="25"  styleId="pmrEquity7" alt="pmrEquity" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onblur="calSubDebtEquity();"/>
					</td>
					<td align="left" valign="top" class="ColumnBackground" width="16.6%">
						Investment as debt/loan in the MSME unit
					</td>
					<td align="left" valign="top" class="TableData" width="16.6%" >
						 <html:text  name="appForm" property="pmrDebt7" maxlength="9"   size="25"  styleId="pmrDebt7" alt="pmrDebt" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onblur="calSubDebtEquity();"/>
					</td>
					<td align="left" valign="top" class="ColumnBackground" width="16.6%">
						 
					</td>
					<td align="left" valign="top" class="TableData" width="16.6%" >
						 
					</td>
				</tr>	
				<tr>
					<td>	
					<button type="button" class="btn-add" id="btn8" onclick="btnA9function()">
					Add</button>
					<button type="button" class="btn-remove" onclick="btnR8function()" onblur="calSubDebtEquity();">
					Remove</button>
					</td>
				</tr>
			</tbody>
		</table>
		<table width="100%" id="p9" style="display:none">
			<tbody>
			<tr>
				<td colspan="12" class="SubHeading" height="28" width="843"><br>
			Details of Promoter 9
			&nbsp;</td>
			</tr>
				<tr>
					<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
					<font color="#FF0000" size="2">*</font>&nbsp;First Name
				</TD>
				<td align="left" valign="top" class="TableData" width="16.6%"> <html:text property="cpFirstName8" size="20" alt="firstName"
				styleId="cpFirstName8" name="appForm" maxlength="20" /> 
 				</TD> 
				<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
					<font color="#FF0000" size="2">*</font>&nbsp;Last Name
				</TD>
			<TD align="left" valign="top" class="TableData" width="16.6%">
			 <html:text property="cpLastName8" size="20" alt="lastName"
			styleId="cpLastName8"	name="appForm" maxlength="20" /> </TD>
			<TD align="left" valign="top" class="ColumnBackground">&nbsp;<bean:message
				key="chiefItpan" />
		</TD>
		<TD align="left" valign="top" class="TableData" width="16.6%">
		 <html:text property="cpITPAN8" size="15" alt="chiefItpan"
			styleId="cpITPAN8"	name="appForm" maxlength="10" onblur="promterITPANChk8();"/> 
		</TD>	
				
				</tr>	
				<tr>
				<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
					<bean:message key="dob" />
				</TD>
				<TD align="left" valign="top" class="TableData"  width="16.6%">
				 <html:text property="cpDOB8" size="15" alt="dob"
						name="appForm" maxlength="10" /> <IMG src="images/CalendarIcon.gif"
					width="20" onClick="showCalendar('appForm.cpDOB')" align="center">
					</TD>
				<TD align="left" valign="top" class="ColumnBackground"  width="16.6%"><font
					color="#FF0000" size="2">*</font>&nbsp;<bean:message key="gender" />
				</TD>
				<TD align="left" valign="top" class="TableData"  width="16.6%">
					 <html:radio name="appForm" value="M" property="cpGender8">
					</html:radio> <bean:message key="male" />&nbsp;&nbsp;&nbsp; <html:radio
						name="appForm" value="F" property="cpGender8"></html:radio> <bean:message
						key="female" />  
			
				</TD>
				<TD align="left" valign="top" class="ColumnBackground" width="16.6%"> Promoter Category </TD>
			<TD align="left" valign="top" class="TableData"  width="16.6%">
			 <html:select property="socialCategory8" name="appForm" styleId="socialCategory8">
					<html:option value="">Select</html:option>
					<html:options name="appForm" property="socialCategoryList"></html:options>
		
				</html:select>
			</TD>
				</tr>	
			 <tr>
					<TD align="left" valign="top" class="ColumnBackground" width="16.6%"> 
					Email Id </TD>
			<TD align="left" valign="top" class="TableData"  width="16.6%">
				<html:text property="email8" size="20"
				styleId="email8" alt="email8" name="appForm" maxlength="50"/>
			</TD>
			<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
			<font color="#FF0000" size="2">*</font>&nbsp;Mobile No</td>
		<TD align="left" valign="top" class="TableData" width="16.6%"><html:text property="proMobileNo8" size="20"
				styleId="proMobileNo8" alt="proMobileNo8" name="appForm" maxlength="10"
				onkeypress="return numbersOnly(this, event)"
				onkeyup="isValidNumber(this)" />
				</td>
				<TD align="left" valign="top" class="ColumnBackground" width="16.6%">&nbsp;
				<bean:message key="promoterAdharNo" /></td>
		<TD align="left" valign="top" class="TableData" width="16.6%" ><html:text
				property="adhar8" size="20" alt="adhar8" name="appForm" maxlength="12"
				onkeypress="return numbersOnly(this, event)"
				onkeyup="isValidNumber(this)" /></td>
				
				</tr>	
				<tr>
					<td align="left" valign="top" class="ColumnBackground" width="16.6%">
						Investment as equity in the MSME unit
					</td>
					<td align="left" valign="top" class="TableData" width="16.6%" >
						 <html:text  name="appForm" property="pmrEquity8" maxlength="9"   size="25"  styleId="pmrEquity8" alt="pmrEquity" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onblur="calSubDebtEquity();"/>
					</td>
					<td align="left" valign="top" class="ColumnBackground" width="16.6%">
						Investment as debt/loan in the MSME unit
					</td>
					<td align="left" valign="top" class="TableData" width="16.6%" >
						 <html:text  name="appForm" property="pmrDebt8" maxlength="9"   size="25"  styleId="pmrDebt8" alt="pmrDebt" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onblur="calSubDebtEquity();"/>
					</td>
					<td align="left" valign="top" class="ColumnBackground" width="16.6%">
						 
					</td>
					<td align="left" valign="top" class="TableData" width="16.6%" >
						 
					</td>
				</tr>	
				<tr>
					<td>	
					<button type="button" class="btn-add" id="btn9" onclick="btnA10function()">
					Add</button>
					<button type="button" class="btn-remove" onclick="btnR9function()" onblur="calSubDebtEquity();">
					Remove</button>
					</td>
				</tr>
			</tbody>
		</table>
		<table width="100%" id="p10" style="display:none">
			<tbody>
			<tr>
				<td colspan="12" class="SubHeading" height="28" width="843"><br>
			Details of Promoter 10
			&nbsp;</td>
			</tr>
				<tr>
					<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
					<font color="#FF0000" size="2">*</font>&nbsp;First Name
				</TD>
				<td align="left" valign="top" class="TableData" width="16.6%"> <html:text property="cpFirstName9" size="20" alt="firstName"
				styleId="cpFirstName9" name="appForm" maxlength="20" /> 
 				</TD> 
				<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
					<font color="#FF0000" size="2">*</font>&nbsp;Last Name
				</TD>
			<TD align="left" valign="top" class="TableData" width="16.6%">
			 <html:text property="cpLastName9" size="20" alt="lastName"
				name="appForm" maxlength="20" /> </TD>
			<TD align="left" valign="top" class="ColumnBackground">&nbsp;<bean:message
				key="chiefItpan" />
		</TD>
		<TD align="left" valign="top" class="TableData" width="16.6%">
		 <html:text property="cpITPAN9" size="15" alt="chiefItpan"
				name="appForm" maxlength="10" onblur="promterITPANChk9();"/> 
		</TD>	
				
				</tr>	
				<tr>
				<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
					<bean:message key="dob" />
				</TD>
				<TD align="left" valign="top" class="TableData"  width="16.6%">
				 <html:text property="cpDOB9" size="15" alt="dob"
						name="appForm" maxlength="10" /> <IMG src="images/CalendarIcon.gif"
					width="20" onClick="showCalendar('appForm.cpDOB')" align="center">
					</TD>
				<TD align="left" valign="top" class="ColumnBackground"  width="16.6%"><font
					color="#FF0000" size="2">*</font>&nbsp;<bean:message key="gender" />
				</TD>
				<TD align="left" valign="top" class="TableData"  width="16.6%">
					 <html:radio name="appForm" value="M" property="cpGender9">
					</html:radio> <bean:message key="male" />&nbsp;&nbsp;&nbsp; <html:radio
						name="appForm" value="F" property="cpGender9"></html:radio> <bean:message
						key="female" />  
			
				</TD>
				<TD align="left" valign="top" class="ColumnBackground" width="16.6%"> Promoter Category </TD>
			<TD align="left" valign="top" class="TableData"  width="16.6%">
			 <html:select property="socialCategory9" name="appForm">
					<html:option value="">Select</html:option>
					<html:options name="appForm" property="socialCategoryList"></html:options>
		
				</html:select>
			</TD>
				</tr>	
			 <tr>
					<TD align="left" valign="top" class="ColumnBackground" width="16.6%"> 
					Email Id </TD>
			<TD align="left" valign="top" class="TableData"  width="16.6%">
				
			</TD>
			<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
			<font color="#FF0000" size="2">*</font>&nbsp;Mobile No</td>
		<TD align="left" valign="top" class="TableData" width="16.6%"><html:text property="proMobileNo9" size="20"
				styleId="proMobileNo9" alt="proMobileNo9" name="appForm" maxlength="10"
				onkeypress="return numbersOnly(this, event)"
				onkeyup="isValidNumber(this)" />
				</td>
				<TD align="left" valign="top" class="ColumnBackground" width="16.6%">&nbsp;
				<bean:message key="promoterAdharNo" /></td>
		<TD align="left" valign="top" class="TableData" width="16.6%" ><html:text
				property="adhar9" size="20" alt="adhar" name="appForm" maxlength="12"
				onkeypress="return numbersOnly(this, event)"
				onkeyup="isValidNumber(this)" /></td>
				
				</tr>	
				<tr>
					<td align="left" valign="top" class="ColumnBackground" width="16.6%">
						Investment as equity in the MSME unit
					</td>
					<td align="left" valign="top" class="TableData" width="16.6%" >
						 <html:text  name="appForm" property="pmrEquity9" maxlength="9"   size="25"  styleId="pmrEquity9" alt="pmrEquity" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onblur="calSubDebtEquity();"/>
					</td>
					<td align="left" valign="top" class="ColumnBackground" width="16.6%">
						Investment as debt/loan in the MSME unit
					</td>
					<td align="left" valign="top" class="TableData" width="16.6%" >
						 <html:text  name="appForm" property="pmrDebt9" maxlength="9"   size="25"  styleId="pmrDebt9" alt="pmrDebt" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onblur="calSubDebtEquity();"/>
					</td>
					<td align="left" valign="top" class="ColumnBackground" width="16.6%">
						 
					</td>
					<td align="left" valign="top" class="TableData" width="16.6%" >
						 
					</td>
				</tr>	
				<tr>
					<td>	 
					<button type="button" class="btn-remove" onclick="btnR10function()" onblur="calSubDebtEquity();">
					Remove</button>
					</td>
				</tr>
			</tbody>
		</table>
		
	</td>

</tr> 	 
 
  
		<html:hidden property="physicallyHandicapped" name="appForm" />
	 


		 
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
				<html:text  name="appForm" property="pmr15Percent" maxlength="9"   size="25"  styleId="pmr15Percent" alt="pmr15Percent" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" disabled="true"/>
				<html:hidden property="pmr15Percent"  styleId="pmr15Percent1"/>
			</td>
			
			<td width="16.6%" align="left" class="ColumnBackground" valign="top">
			<font color="#ff0000" size="2">*</font>&nbsp;Existing Total o/s amount</td>
			<td width="16.6%" align="left" class="TableData" valign="top">
			<html:text  name="appForm" property="projectedExports" maxlength="9"   size="25"  styleId="projectedExports2" alt="projectedExports" disabled="true" />
			<html:hidden property="projectedExports"  styleId="projectedExports1"/>
				<!-- <input name="totalOutstandingAmt" id="outstandingAmt" onkeyup="isValidDecimal(this)" onkeypress="return decimalOnly(this, event,13)" type="text" size="25" maxlength="9" alt="outstandingAmt" value=""> -->
			</td>
			
			<td width="16.6%" align="left" class="ColumnBackground" valign="top">
			<font color="#ff0000" size="2">*</font>&nbsp;Total Sub-debt scheme limit  </td>
			<td width="16.6%" align="left" class="TableData" valign="top">
				<html:text  name="appForm" property="pmrMinTotal" maxlength="9"   size="25"  styleId="pmrMinTotal" alt="pmrMinTotal" value="7500000" disabled="true"/>
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
				<html:text property="creditGuaranteed" styleId="creditGuaranteed" size="20" alt="creditGuaranteed" name="appForm" maxlength="16"  onkeyup="isValidNumber(this)"/>&nbsp;
				
				
				
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
				property="disbursementAmt" size="20" name="appForm" maxlength="25" onkeypress="return numbersOnly(this, event)" />
		</TD> 	
	</tr>
	<tr>
		<TD style="width:16.6%" align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>
					&nbsp;Sub-debt Sanctioned Date</TD>
		<TD style="width:16.6%" align="left" valign="top" class="TableData" >&nbsp;<html:text
				property="sanctionDate" size="20" name="appForm" maxlength="25" /><IMG src="images/CalendarIcon.gif"
					width="20" onClick="showCalendar('appForm.sanctionDate')" align="center"> 
		</TD>
		<TD style="width:16.6%" align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>
					&nbsp;No of Principal Installments             </TD>
		<TD style="width:16.6%" align="left" valign="top" class="TableData" >&nbsp;<html:text
				property="noOfInstallment" size="20" name="appForm" maxlength="25" onkeypress="return numbersOnly(this, event)"/>
		</TD>
		<TD style="width:16.6%" align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>
					&nbsp;<bean:message key="pplOS"/> </TD>
		<TD style="width:16.6%" align="left" valign="top" class="TableData" >&nbsp;<html:text
				property="pplOS" size="20" name="appForm" maxlength="25" onkeypress="return numbersOnly(this, event)" />
		</TD> 	
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
					<td class="TableData"><html:text name="appForm" 
				property="landValue" size="20"  styleId="landValue"  alt="landValue" maxlength="25" onkeypress="return numbersOnly(this, event)" onblur="CalSecurityTotalAmt();"/></td>
						<td class="ColumnBackground">Other Fixed / Movable Assets</td>
					<td class="TableData"><html:text name="appForm" 
				property="assetsValue" size="20"  styleId="assetsValue"  alt="assetsValue" maxlength="25" onkeypress="return numbersOnly(this, event)" onblur="CalSecurityTotalAmt();"/></td>
				</tr>
				<tr>
					<td class="ColumnBackground">Building</td>
					<td class="TableData"><html:text name="appForm" 
				property="bldgValue" size="20"  styleId="bldgValue"  maxlength="25" onkeypress="return numbersOnly(this, event)" onblur="CalSecurityTotalAmt();" /></td>
					<td class="ColumnBackground">Current Assets*</td>
					<td class="TableData"><html:text name="appForm" 
				property="currentAssetsValue" size="20" styleId="currentAssetsValue"   maxlength="25" onkeypress="return numbersOnly(this, event)" onblur="CalSecurityTotalAmt();"/></td>
				</tr>
				<tr>
					<td class="ColumnBackground">Plant and Machinery / Equipment*</td>
					<td class="TableData"><html:text
				property="machineValue" size="20" name="appForm" styleId="machineValue"  maxlength="25" onkeypress="return numbersOnly(this, event)" onblur="CalSecurityTotalAmt();"/></td>
					<td class="ColumnBackground">Others</td>
					<td class="TableData"><html:text name="appForm" 
				property="othersValue" size="20"  styleId="othersValue"  maxlength="25" onkeypress="return numbersOnly(this, event)" onblur="CalSecurityTotalAmt();"/></td>
				</tr> 
				<tr>
					<td class="ColumnBackground">Total</td>
					<td class="TableData"> <html:text name="appForm"  property="psTotal" styleId="psTotal2" size="20"  maxlength="25" disabled="true"/></td>
					<html:hidden property="psTotal" styleId="psTotal1"/>
					<td class="ColumnBackground">*Remarks on Total Value of Security</td>
					<td class="TableData"><html:text property="securityRemarks" size="20" name="appForm" maxlength="2000" /></td>
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
		  
 	                  
