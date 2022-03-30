<%@page import="com.cgtsi.registration.MLIInfo"%>
<%@page import="java.util.Iterator"%>
<%@ page import="com.cgtsi.util.SessionConstants"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean"%>

<script src="/csrfguard"></script>
<style>
.w150{width:150px;}
</style>




<script type="text/javascript">
/* function calltotalAmt()
{
var sanctionedamt=document.getElementById('termCreditSanctioned');

if (!(isNaN(sanctionedamt.value)) && (sanctionedamt.value != ""))
	{
sanctionedamtValue=parseFloat(sanctionedamt.value);
	}
	else
	{
	sanctionedamtValue=0;
	}
} */

/* function calSecurityAvail(flag)
{
		if(flag == 'N')
		{
			alert('The scheme envisages creation of Primary Security out of the loan / credit provided to the borrower.');
		}	
}
function pmudra(flag){

    if(flag =='Y')
        {
         //alert('As the credit facility is already covered under PMMY/MUDRA , Same can not be covered under CGTMSE.');
alert('As the credit facility is already covered under NCGTC Mudra Guarantee Programme, Same cannot be covered under CGTMSE.');
        }
	
} */
//Added by DKR. for GST
 function getGSTValue()	{
	var xmlhttp;
    if (window.XMLHttpRequest){
        xmlhttp = new XMLHttpRequest(); //for IE7+, Firefox, Chrome, Opera, Safari
    } else {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP"); //for IE6, IE5
    }			
    var stateCode = document.getElementById("stateCode").value; 			   		 
    if(stateCode!=null || stateCode!='')
    {
    	xmlhttp.open("POST", "getGSTByStateCode.do?method=getGSTNO&stateCode="+stateCode, true);		    
    	xmlhttp.onreadystatechange = function() {			    	
    	if (xmlhttp.readyState == 4) 
    	{			    		
    		if (xmlhttp.status == 200)
    		{		    			         	
        	   	if(xmlhttp.responseText.trim()!="0"){      		
        	   	  document.getElementById('gstNo').value=xmlhttp.responseText.trim();
				 				    		
		    				              
		    }else{	            	
		    		alert('Invalid State. Please contact to CGTMSE@Support');
		    }
            }
            else{
            	alert("Something is wrong !! Plz contact contact NCGTC Support[itsupport@ncgtc.com] team");               
            }
          
    	  }
    	};
    	xmlhttp.send(null);
    }
    else
    {
	    alert('State not mapped with any GST No. Please contact to head office.');			    	
    }
}


function getITPANAmount()	{
	
	//alert("getITPANAmount==");
	var xmlhttp;
    if (window.XMLHttpRequest){
        xmlhttp = new XMLHttpRequest(); //for IE7+, Firefox, Chrome, Opera, Safari
    } else {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP"); //for IE6, IE5
    }			
    var firmITPAN = document.getElementById("ssiITPan").value; 	
   // alert("firmITPAN=="+firmITPAN);
    if(firmITPAN!=null || firmITPAN!='')
    {
    //	alert("firmITPAN 1st If=="+firmITPAN);
    	xmlhttp.open("POST", "getItpanAmount.do?method=getItpanAmount&ssiITPan="+firmITPAN, true);	
    	//alert("firmITPAN 2nd line=="+firmITPAN);
    	xmlhttp.onreadystatechange = function() {	
    		//alert("open=="+firmITPAN);
    	if (xmlhttp.readyState == 4) 
    	{			    		
    		//alert("firmITPAN 2nd if=="+firmITPAN);
    	/* 	if (xmlhttp.status == 200)
    		{		    		
    		//	alert("firmITPAN 3rd line=="+firmITPAN);
        	   	if(xmlhttp.responseText.trim()!="0"){    
        	   		var existAmt=xmlhttp.responseText.trim();
        	   	  document.getElementById('ExistingFacilityAmount').value=parseFloat(existAmt);
				 				    		
		    				              
		    }else{	            	
		    		alert('Invalid firmITPAN. Please contact to CGTMSE@Support');
		    }
            } */
            
            if (xmlhttp.status == 200)
    		{		    		
    		
        	   	if(xmlhttp.responseText.trim()!="0"){  
        	   		//alert("firmITPAN BureauExistingFacilityAmount line=="+xmlhttp.responseText.trim());
        	   		var existAmt=xmlhttp.responseText.trim();
        	   	  document.getElementById('ExistingFacilityAmount').value=parseFloat(existAmt);
				 				    		
		    				              
		    }else{	            	
		    		//alert('Invalid firmITPAN. Please contact to CGTMSE@Support');
		    }
            }
            else{
            	alert("Something is wrong !! Plz contact contact NCGTC Support[itsupport@ncgtc.com] team");               
            }
          
    	  }
    	};
    	xmlhttp.send(null);
    }
    else
    {
	    alert(' Please contact cgtmse office.');			    	
    }
}  

function getTotalITPANAmount()	{
	
	//alert("getTotalITPANAmount==");
	var xmlhttp;
    if (window.XMLHttpRequest){
        xmlhttp = new XMLHttpRequest(); //for IE7+, Firefox, Chrome, Opera, Safari
    } else {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP"); //for IE6, IE5
    }			
    var firmITPAN = document.getElementById("ssiITPan").value; 	
   // alert("firmITPAN=="+firmITPAN);
    if(firmITPAN!=null || firmITPAN!='')
    {
    //	alert("firmITPAN 1st If=="+firmITPAN);
    	xmlhttp.open("POST", "getTotalItpanAmount.do?method=getTotalItpanAmount&ssiITPan="+firmITPAN, true);	
    	//alert("firmITPAN 2nd line=="+firmITPAN);
    	xmlhttp.onreadystatechange = function() {	
    		//alert("open=="+firmITPAN);
    	if (xmlhttp.readyState == 4) 
    	{			    		
    		//alert("firmITPAN 2nd if=="+firmITPAN);
    		/* if (xmlhttp.status == 200)
    		{		    		
    		
        	   	if(xmlhttp.responseText.trim()!="0"){  
        	   		//alert("firmITPAN BureauExistingFacilityAmount line=="+xmlhttp.responseText.trim());
        	   		var existAmt=xmlhttp.responseText.trim();
        	   	  document.getElementById('BureauExistingFacilityAmount').value=parseFloat(existAmt);
				 				    		
		    				              
		    }else{	            	
		    		alert('Invalid firmITPAN. Please contact to CGTMSE@Support');
		    }
            } */
            
            if (xmlhttp.status == 200)
    		{		    		
    		
        	   	if(xmlhttp.responseText.trim()!="0"){  
        	   		//alert("firmITPAN BureauExistingFacilityAmount line=="+xmlhttp.responseText.trim());
        	   		var existAmt=xmlhttp.responseText.trim();
        	   	  document.getElementById('BureauExistingFacilityAmount').value=parseFloat(existAmt);
				 				    		
		    				              
		    }else{	            	
		    		//alert('Invalid firmITPAN. Please contact to CGTMSE@Support');
		    }
            }
            else{
            	alert("Something is wrong !! Plz contact contact NCGTC Support[itsupport@ncgtc.com] team");               
            }
          
    	  }
    	};
    	xmlhttp.send(null);
    }
    else
    {
	    alert(' Please contact cgtmse office.');			    	
    }
} 

function getPplosAmount()	{
	
	//alert("getPplosAmount==");
	//var pplosValue=document.getElementById("pplOS").disabled = true;
	//alert(pplosValue);
	var xmlhttp;
    if (window.XMLHttpRequest){
        xmlhttp = new XMLHttpRequest(); //for IE7+, Firefox, Chrome, Opera, Safari
    } else {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP"); //for IE6, IE5
    }			
    var firmITPAN = document.getElementById("ssiITPan").value; 	
   // alert("firmITPAN=="+firmITPAN);
    if(firmITPAN!=null || firmITPAN!='')
    {
    	//alert("firmITPAN 1st If=="+firmITPAN);
    	xmlhttp.open("POST", "getPplosItpanAmount.do?method=getPplosItpanAmount&ssiITPan="+firmITPAN, true);	
    	//alert("firmITPAN 2nd line=="+firmITPAN);
    	xmlhttp.onreadystatechange = function() {	
    		//alert("open=="+firmITPAN);
    	if (xmlhttp.readyState == 4) 
    	{			    		
    		//alert("firmITPAN 2nd if=="+firmITPAN);
    		/* if (xmlhttp.status == 200)
    		{		    		
    			//alert("xmlhttp.responseText==getPplosItpanAmount=="+xmlhttp.responseText);
        	   	if(xmlhttp.responseText!="0.0" ){    
        	   		var existAmt=xmlhttp.responseText.trim();
        	   	  document.getElementById('pplOS').value=parseFloat(existAmt);
        	   //	document.getElementById('pplOShid').value=parseFloat(existAmt);
				 				    		
		    				              
		    }else{	            	
		    	alert('Invalid firmITPAN. ');
		    }
            } */
            if (xmlhttp.status == 200)
    		{		    		
    		
        	   	if(xmlhttp.responseText.trim()!="0"){  
        	   		//alert("firmITPAN BureauExistingFacilityAmount line=="+xmlhttp.responseText.trim());
        	   		var existAmt=xmlhttp.responseText.trim();
        	   	  document.getElementById('pplOS').value=parseFloat(existAmt);
				 				    		
		    				              
		    }else{	            	
		    		//alert('Invalid firmITPAN. Please contact to CGTMSE@Support');
		    }
            }
            else{
            	alert("Something is wrong !! Plz contact contact NCGTC Support[itsupport@ncgtc.com] team");               
            }
          
    	  }
    	};
    	xmlhttp.send(null);
    }
    else
    {
	    alert(' Please contact cgtmse office.');			    	
    }
} 

function getDPDValue()	{
	
	//alert("getPplosAmount==");
	var xmlhttp;
	//var DPDStatusVal=document.getElementById("DPDStatus").disabled = true;
	//alert(DPDStatusVal);
    if (window.XMLHttpRequest){
        xmlhttp = new XMLHttpRequest(); //for IE7+, Firefox, Chrome, Opera, Safari
    } else {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP"); //for IE6, IE5
    }			
    var firmITPAN = document.getElementById("ssiITPan").value; 	
   // alert("firmITPAN=="+firmITPAN);
    if(firmITPAN!=null || firmITPAN!='')
    {
    //	alert("firmITPAN 1st If=="+firmITPAN);
    	xmlhttp.open("POST", "getDPDValue.do?method=getDPDValue&ssiITPan="+firmITPAN, true);	
    	//alert("firmITPAN 2nd line=="+firmITPAN);
    	xmlhttp.onreadystatechange = function() {	
    		//alert("open=="+firmITPAN);
    	if (xmlhttp.readyState == 4) 
    	{			    		
    		//alert("firmITPAN 2nd if=="+firmITPAN);
    		/* if (xmlhttp.status == 200)
    		{		    		
    		//	alert("firmITPAN 3rd line=="+firmITPAN);
        	   	if(xmlhttp.responseText.trim()!="0"){    
        	   		//var existAmt=xmlhttp.responseText.trim();
        	   	  document.getElementById('DPDStatus').value=xmlhttp.responseText.trim();
				 				    		
		    				              
		    }else{	            	
		    	alert('Invalid firmITPAN. ');
		    }
            } */
            
            if (xmlhttp.status == 200)
    		{		    		
    		
        	   	if(xmlhttp.responseText.trim()!="0"){  
        	   		//alert("firmITPAN BureauExistingFacilityAmount line=="+xmlhttp.responseText.trim());
        	   		var existAmt=xmlhttp.responseText.trim();
        	   	  document.getElementById('DPDStatus').value=xmlhttp.responseText.trim();				 				    		
		    				              
		    }else{	            	
		    		//alert('Invalid firmITPAN. Please contact to CGTMSE@Support');
		    }
            }
            else{
            	alert("Something is wrong !! Plz contact NCGTC Support[itsupport@ncgtc.com] team");               
            }
          
    	  }
    	};
    	xmlhttp.send(null);
    }
    else
    {
	    alert(' Please contact cgtmse office.');			    	
    }
} 
     
/*     function getGSTValue()	{
    		//alert("getGSTValue called!!!");
        var activityConfirm = document.getElementById("activityConfirm").value; 
        //alert("activityConfirm=="+activityConfirm);
       // if(activityConfirm==null || activityConfirm=='')
    	    if (activityConfirm.checked == false)
        {
        	  var gstNo=document.getElementById('gstNo').value;
        	 // alert("gstNo=="+gstNo);
        	  {
    			if(gstNo==null || gstNo=='')  
    				{
    		    		alert("Please enter GST Number");
    				}
                }
               
        }
}  */
/*     
    function calculateLoanType()	
    {
		//alert("calculateLoanType called!!!");
    var TypeLoanTC = document.getElementById("TypeLoanTC").value; 
    
  //  alert("TypeLoanTC=="+TypeLoanTC);
    
 var TypeLoanWC = document.getElementById("TypeLoanWC").value; 
    
  //  alert("TypeLoanWC=="+TypeLoanWC);
    
    var a=parseInt(TypeLoanTC)+parseInt(TypeLoanWC);
    
    if(TypeLoanTC==0 && TypeLoanWC==0 || TypeLoanTC!=null && TypeLoanWC!=null)
    	{
    	  alert("Loan Type TC/WC Ratio can not null");
    	}
    
   // alert("a=="+a);
   // if(activityConfirm==null || activityConfirm=='')
	    if (a>100)
    {
    	  
    	  alert("Loan Type TC/WC can not more than 100%");
    	  
    }
}  */
    
</script>
<!--start for app details-->
<TABLE width="100%" border="0" cellspacing="1" cellpadding="0">
	<tr>
		<TD align="left" colspan="4"><font size="2"><bold>
				Fields marked as </font><font color="#FF0000" size="2">*</font><font
			size="2"> are mandatory</bold></font></td>
	</tr>
	<TR>
		<TD colspan="4">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
				<TR>
					<TD width="31%" class="Heading"><bean:message
							key="applicationHeader" /></TD>
					<TD><IMG src="images/TriangleSubhead.gif" width="19"
						height="19"></TD>
				</TR>
				<TR>
					<TD colspan="4" class="Heading"><IMG src="images/Clear.gif"
						width="5" height="5"> <input type="hidden"
						name="hiddenGstNo" id="hiddenGstNo"></TD>
				</TR>
			</TABLE>
		</TD>
	</TR>
	
	<%-- <TR> 				
		 <td class="ColumnBackground" style="width:50%" colspan="2">	
			 <bean:message key="TC" />
			 <html:text property="TypeLoanTC" size="20" styleId="TypeLoanTC" alt="TypeLoanTC" name="appForm" maxlength="3"  onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/> in %
			 </td>
			 <td class="TableData"  style="width:50%" colspan="2">
			<bean:message key="WC" />
			
			
			  <html:text property="TypeLoanWC" size="20" styleId="TypeLoanWC" alt="loanType" name="appForm" maxlength="3"  onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/> in %
			<!--<input type="checkbox" name="activityConfirm" value="N" >No -->
			</TD>  			
	</TR>  --%>
	
	
	<!-- added by dkr 35 -->
	
	
	<html:hidden property="hybridSecurity" name="appForm" />
	 
	<html:hidden property="mliRefNo" name="appForm" />
	 
	<html:hidden property="mliBranchName" name="appForm" />
	 

		<%--<TR align="left">
		<TD style="width:25%" align="left" valign="top" class="ColumnBackground"><bean:message
				key="branchState" /></td>
		 <TD style="width:25%" align="left" valign="top" class="tableData">
			<%
			String appCommonFlag = (String) session
			.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG);
										if ((appCommonFlag.equals("1")) || (appCommonFlag.equals("11"))
												|| (appCommonFlag.equals("12"))
												|| (appCommonFlag.equals("13"))) {
									%><!--
										<bean:write name="appForm" property="state"/>
									--> 
									
		 <html:text property="branchStateList" styleId="stateCode" name="appForm"  scope="session"></html:text>
				<html:option value="">-- Branch State --</html:option>
				<html:optionsCollection name="appForm" property="branchStateList"
					label="stateName" value="stateCode" />
			</html:select> <%
										} else {
									%> <html:select property="gstState" styleId="stateCode"
				onchange="return getGSTValue();">
				<html:option value=""> Branch State</html:option>
				<html:optionsCollection name="appForm" property="branchStateList"
					label="stateName" value="stateCode" />
			</html:select> <%
																					}
																				%>
		</TD> --%>
		<tr>
		
		
				
					
					
		<TD style="width:25%" align="left" valign="top" class="ColumnBackground">&nbsp;<bean:message
				key="branchState" /></td>
		<TD style="width:25%" align="left" valign="top" class="tableData">
			<%String appCommonFlag = (String) session
					.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG);
										if ((appCommonFlag.equals("1")) || (appCommonFlag.equals("11"))
												|| (appCommonFlag.equals("12"))
												|| (appCommonFlag.equals("13"))) {
									%> <input type="text" id="gstState" name="gstState"
			value="<bean:write name="appForm" property="branchState" scope="session"/>"
			readonly style="color: red; background: #C4DEE6"> <%
										} else {
									%> <input type="text" id="gstState" name="gstState"
			value="<bean:write name="appForm" property="branchState" scope="session"/>"
			readonly style="color: red; background: #C4DEE6"> <%
											}
										%>
		</TD>
		<TD style="width:25%" align="left" valign="top" class="ColumnBackground">&nbsp;<bean:message
				key="gstlable" /></td>
		<TD style="width:25%" align="left" valign="top" class="tableData">
			<%
										if ((appCommonFlag.equals("1")) || (appCommonFlag.equals("11"))
												|| (appCommonFlag.equals("12"))
												|| (appCommonFlag.equals("13"))) {
									%> <input type="text" id="gstNo" name="gstNo"
			value="<bean:write name="appForm" property="gstNo" scope="session"/>"
			readonly style="color: red; background: #C4DEE6"> <%
										} else {
									%> <input type="text" id="gstNo" name="gstNo"
			value="<bean:write name="appForm" property="gstNo" scope="session"/>"
			readonly style="color: red; background: #C4DEE6"> <%
											}
										%>
		</TD>
	</TR>
	 
</TABLE> 
<!--closing for app details-->


<!--table starting -->
<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
	<TR>
		<TD colspan="12">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
				<TR>
					<TD width="31%" class="Heading"><bean:message
							key="borrowerHeader" /></TD>
					<TD><IMG src="images/TriangleSubhead.gif" width="19"
						height="19"></TD>
				</TR>
				<TR>
					<TD colspan="6" class="Heading"><IMG src="images/Clear.gif"
						width="5" height="5"></TD>
				</TR>
			</TABLE>
		</TD>
	</TR>


	 
	<html:hidden property="npa" name="appForm" />
	<html:hidden property="termCreditSanctioned" name="appForm" />
	<html:hidden property="wcFundBasedSanctioned" name="appForm" />
	<html:hidden property="wcNonFundBasedSanctioned" name="appForm" />
	<html:hidden property="wcPromoterContribution" name="appForm" />
	
	
	 
	<%-- <html:hidden property="previouslyCovered" name="appForm" /> --%>
	
		
	 
	 	<TR>
		<TD colspan="9">
			<TABLE width="100%" border="0" cellpadding="1" cellspacing="1">
				 
 
				
	<TR>
		<TD colspan="12">
			<TABLE width="100%" border="0" cellpadding="1" cellspacing="1">
			 <TR align="left">
		<TD align="left" valign="top" class="ColumnBackground" width="16.6%">
			<bean:message key="coveredByCGTSI" />
		</TD>
		<TD align="left" class="TableData" width="25%">
			<%
									 	if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
									 			|| (appCommonFlag.equals("2"))
									 			|| (appCommonFlag.equals("3"))
									 			|| (appCommonFlag.equals("4"))
									 			|| (appCommonFlag.equals("5"))
									 			|| (appCommonFlag.equals("6"))
									 			|| (appCommonFlag.equals("11"))
									 			|| (appCommonFlag.equals("12"))
									 			|| (appCommonFlag.equals("13"))
									 			|| (appCommonFlag.equals("14"))
									 			|| (appCommonFlag.equals("17"))
									 			|| (appCommonFlag.equals("18"))
									 			|| (appCommonFlag.equals("19"))) {
									 %> <html:radio name="appForm" value="Y"
				property="previouslyCovered" disabled="true"></html:radio> <bean:message
				key="yes" />&nbsp;&nbsp; <html:radio name="appForm" value="N"
				property="previouslyCovered" disabled="true"></html:radio> <bean:message
				key="no" /> <%
										} else {
									%> <html:radio name="appForm" value="Y"
				property="previouslyCovered" onclick="enableNone()"></html:radio> <bean:message
				key="yes" />&nbsp;&nbsp; <html:radio name="appForm" value="N"
				property="previouslyCovered" onclick="enableNone()"></html:radio> <bean:message
				key="no" /> <%
											}
										%>

		</TD>
		<TD align="left" valign="top" class="ColumnBackground" width="16.6%"><%-- <html:radio
							name="appForm" value="cgpan" property="none" disabled="false">
							
						</html:radio> --%>
						<bean:message key="cgpan" />
						
		<TD align="left" valign="top" class="TableData" width="16.6%" >
			 <script>
									booleanVal=false;
								</script> <html:text property="unitValue" size="15" alt="Value"
				name="appForm" maxlength="15" disabled="false" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		
		</TD>
		<TD align="left" valign="top" class="ColumnBackground" width="16.6%">&nbsp;<bean:message
							key="balanceApprovedAmt" />
					</TD>
					<TD align="left" valign="top" class="TableData" width="16.6%">
						<html:text property="balanceApprovedAmt" size="15" alt="Value"
							name="appForm" maxlength="15" />
					</TD>
	</TR>
<%-- <tr>

	<td  align="left" valign="top" class="ColumnBackground" width="25%">
						&nbsp;<bean:message
							key="ExistingFacilityAmountKey" />
						<input type="text" id="ExistingFacilityAmount" name="ExistingFacilityAmount"
			value="<bean:write name="appForm" property="ExistingFacilityAmount" scope="session"/>"
			readonly style="color: red; background: #C4DEE6">
					</td>
					
					<!-- <td  align="left" valign="top" class="ColumnBackground" width="25%">
						&nbsp;Net Outstanding:
						<input type="text" id="NetOs" name="NetOs"
			value=""
			readonly style="color: red; background: #C4DEE6">
					</td> -->

					
					
				</TR>   --%>
				
			

				<TR align="left">
					<TD align="left" valign="top" class="ColumnBackground" width="16.6%"><font
						color="#FF0000" size="2">*</font>&nbsp;<bean:message
							key="unitName" /></TD>
					<TD align="left" valign="top" class="TableData" width="16.6%">
						<%
						if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
								|| (appCommonFlag.equals("2"))
								|| (appCommonFlag.equals("3"))
								|| (appCommonFlag.equals("4"))
								|| (appCommonFlag.equals("5"))
								|| (appCommonFlag.equals("6"))
								|| (appCommonFlag.equals("11"))
								|| (appCommonFlag.equals("12"))
								|| (appCommonFlag.equals("13"))
								|| (appCommonFlag.equals("14"))
								|| (appCommonFlag.equals("17"))
								|| (appCommonFlag.equals("18"))
								|| (appCommonFlag.equals("19"))) {
					%>   <bean:write name="appForm"
							property="ssiName" /> <%
						} else {
							%> <html:text property="ssiName" size="30" alt="unitName"
	name="appForm" maxlength="100" /> <%
									}
								 %>
					</td>
					 
					<TD align="left" valign="top" class="ColumnBackground" width="16.6%"><font
						color="#FF0000" size="2">*</font>&nbsp;<bean:message
							key="ssiType" /></TD>
							<TD align="left" valign="top" class="TableData" width="16.6%"
						 >
						<%
								if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
										|| (appCommonFlag.equals("2"))
										|| (appCommonFlag.equals("3"))
										|| (appCommonFlag.equals("4"))
										|| (appCommonFlag.equals("5"))
										|| (appCommonFlag.equals("6"))
										|| (appCommonFlag.equals("11"))
										|| (appCommonFlag.equals("12"))
										|| (appCommonFlag.equals("13"))
										|| (appCommonFlag.equals("14"))
										|| (appCommonFlag.equals("17"))
										|| (appCommonFlag.equals("18"))
										|| (appCommonFlag.equals("19"))) {
							%> 
					<bean:write property="ssiType" name="appForm" /> <%
							} else {
						%> <html:select property="ssiType" name="appForm">
							<html:option value="">Select</html:option>
							<html:option value="Micro">Micro</html:option>
							<html:option value="Small">Small</html:option>
							<html:option value="Medium">Medium</html:option>
							<html:option value="Other Business Enterprises">Other Business Enterprises</html:option>
						</html:select>&nbsp;&nbsp;&nbsp;&nbsp; <%
													}
												%></TD>
				<TD align="left" valign="top" class="ColumnBackground" width="16.6%"><font
						color="#FF0000" size="2">*</font>&nbsp;<bean:message
							key="unitAddress" /></td>
					<TD align="left" valign="top" class="TableData" width="16.6%">
						<%
							if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
									|| (appCommonFlag.equals("2"))
									|| (appCommonFlag.equals("3"))
									|| (appCommonFlag.equals("4"))
									|| (appCommonFlag.equals("5"))
									|| (appCommonFlag.equals("6"))
									|| (appCommonFlag.equals("11"))
									|| (appCommonFlag.equals("12"))
									|| (appCommonFlag.equals("13"))
									|| (appCommonFlag.equals("14"))
									|| (appCommonFlag.equals("17"))
									|| (appCommonFlag.equals("18"))
									|| (appCommonFlag.equals("19")))
	
							{
							%> <bean:write name="appForm" property="address" /> <%
								} else {
							%> <html:textarea property="address" cols="30" alt="address"
									name="appForm" rows="3" /> <%
										}
						 %>
					</td>
				</tr>
							 
				<TR align="left">
					
					<TD align="left" valign="top" class="ColumnBackground" width="16.6%"><font
						color="#FF0000" size="2">*</font>&nbsp;<bean:message key="state" />
					</TD>
					<TD align="left" valign="top" class="TableData" width="16.6%" >
						<%
							if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
									|| (appCommonFlag.equals("2"))
									|| (appCommonFlag.equals("3"))
									|| (appCommonFlag.equals("4"))
									|| (appCommonFlag.equals("5"))
									|| (appCommonFlag.equals("6"))
									|| (appCommonFlag.equals("11"))
									|| (appCommonFlag.equals("12"))
									|| (appCommonFlag.equals("13"))
									|| (appCommonFlag.equals("14"))
									|| (appCommonFlag.equals("17"))
									|| (appCommonFlag.equals("18"))
									|| (appCommonFlag.equals("19")))

							{
						%> <bean:write name="appForm" property="state" /> <%
								} else {
							%> <html:select property="state" name="appForm"
							onchange="javascript:submitForm('afterTcMli.do?method=getDistricts')">
							<html:option value="">Select</html:option>
							<html:options name="appForm" property="statesList"></html:options>
						</html:select> <%
								}
							%>

					</td>
					<TD align="left" valign="top" class="ColumnBackground" width="16.6%"><font
						color="#FF0000" size="2">*</font>
					<bean:message key="district" /></TD>
					<TD align="left" valign="top" class="TableData" width="16.6%">
						<%
							if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
									|| (appCommonFlag.equals("2"))
									|| (appCommonFlag.equals("3"))
									|| (appCommonFlag.equals("4"))
									|| (appCommonFlag.equals("5"))
									|| (appCommonFlag.equals("6"))
									|| (appCommonFlag.equals("11"))
									|| (appCommonFlag.equals("12"))
									|| (appCommonFlag.equals("13"))
									|| (appCommonFlag.equals("14"))
									|| (appCommonFlag.equals("17"))
									|| (appCommonFlag.equals("18"))
									|| (appCommonFlag.equals("19")))

							{
						%> <bean:write name="appForm" property="district" /> <%
								} else {
							%> <html:select property="district" name="appForm">
							<html:option value="">Select</html:option>
							<html:options name="appForm" property="districtList" />
						</html:select>
					</TD> 
					<%
					 	}
					 %>
					<TD align="left" valign="top" class="ColumnBackground" width="16.6%"><font
						color="#FF0000" size="2">*</font>
					<bean:message key="city" /></td>
					<TD align="left" valign="top" class="TableData" width="16.6%">
						<%
							if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
									|| (appCommonFlag.equals("2"))
									|| (appCommonFlag.equals("3"))
									|| (appCommonFlag.equals("4"))
									|| (appCommonFlag.equals("5"))
									|| (appCommonFlag.equals("6"))
									|| (appCommonFlag.equals("11"))
									|| (appCommonFlag.equals("12"))
									|| (appCommonFlag.equals("13"))
									|| (appCommonFlag.equals("14"))
									|| (appCommonFlag.equals("17"))
									|| (appCommonFlag.equals("18"))
									|| (appCommonFlag.equals("19")))

							{
					%> <bean:write name="appForm" property="city" /> <%
							} else {
						%> <html:text property="city" size="20" alt="city"
							name="appForm" maxlength="100" /> <%
							}
						%>
					</td>
				</tr> 
				 

				<TR align="left">
					<TD align="left" valign="top" class="ColumnBackground" width="16.6%"><font
						color="#FF0000" size="2">*</font>&nbsp;<bean:message key="pinCode" />
					</td>
					<TD align="left" valign="top" class="TableData" width="16.6%" >
						<%
							if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
									|| (appCommonFlag.equals("2"))
									|| (appCommonFlag.equals("3"))
									|| (appCommonFlag.equals("4"))
									|| (appCommonFlag.equals("5"))
									|| (appCommonFlag.equals("6"))
									|| (appCommonFlag.equals("11"))
									|| (appCommonFlag.equals("12"))
									|| (appCommonFlag.equals("13"))
									|| (appCommonFlag.equals("14"))
									|| (appCommonFlag.equals("17"))
									|| (appCommonFlag.equals("18"))
									|| (appCommonFlag.equals("19")))

							{
						%> <bean:write name="appForm" property="pincode" /> <%
								} else {
							%> <html:text property="pincode" size="20" alt="pinCode"
									name="appForm" maxlength="6"
							onkeypress="return numbersOnly(this, event)"
							onkeyup="isValidNumber(this)" /> <%
														}
													%>
					</td>
					<TD align="left" valign="top" class="ColumnBackground" width="16.6%">&nbsp;<font color="#FF0000" size="2">*</font><bean:message
							key="firmItpan" />
					</td>
					<TD align="left" valign="top" class="TableData" width="16.6%" >
						<%
													if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
															|| (appCommonFlag.equals("2"))
															|| (appCommonFlag.equals("3"))
															|| (appCommonFlag.equals("4"))
															|| (appCommonFlag.equals("5"))
															|| (appCommonFlag.equals("6"))
															|| (appCommonFlag.equals("11"))
															|| (appCommonFlag.equals("12"))
															|| (appCommonFlag.equals("13"))
															|| (appCommonFlag.equals("14"))
															|| (appCommonFlag.equals("17"))
															|| (appCommonFlag.equals("18"))
															|| (appCommonFlag.equals("19")))

													{
												%> <bean:write name="appForm" property="ssiITPan" /> <%
													} else {
												%><html:text property="ssiITPan" size="20" alt="ITPAN" styleId="ssiITPan" 
							name="appForm" maxlength="10" onblur="getITPANAmount(),getTotalITPANAmount(),getPplosAmount(),getDPDValue();"/>  
							
							<%-- <%
							session.setAttribute("ssiITPan",${appForm.ssiITPan});
							%> --%>
							<!-- <input type="text"  size="20" alt="ITPAN" id="ssiITPan"
							name="appForm" maxlength="10" onBlur="getITPANAmount();"/> --> <%
														}
													%>
					</td>
					<html:hidden property="regNo" name="appForm" />
					<TD align="left" valign="top" class="ColumnBackground" width="16.6%"><font
						color="#FF0000" size="2">*</font>&nbsp;<bean:message
							key="industryNature" /><%-- <span class="dkrtooltiptext"> <bean:message
								key="industrySectorTooltipdkr" />
					</span> --%></TD>
					<TD align="left" valign="top" class="TableData" width="16.6%">
						<%
												if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
														|| (appCommonFlag.equals("2"))
														|| (appCommonFlag.equals("3"))
														|| (appCommonFlag.equals("4"))
														|| (appCommonFlag.equals("5"))
														|| (appCommonFlag.equals("6"))
														|| (appCommonFlag.equals("11"))
														|| (appCommonFlag.equals("12"))
														|| (appCommonFlag.equals("13"))
														|| (appCommonFlag.equals("14"))
														|| (appCommonFlag.equals("17"))
														|| (appCommonFlag.equals("18"))
														|| (appCommonFlag.equals("19")))
							
												{
											%> <bean:write name="appForm" property="industryNature" /> <%
							 	} else {
							 %> <html:select style="min-width:50%" property="industryNature" name="appForm"
							styleId="industryNature" styleClass="w150"
							onchange="javascript:submitForm('afterTcMli.do?method=getIndustrySector');">
							<html:option value="">Select</html:option>
							<html:options name="appForm" property="industryNatureList"></html:options>
						</html:select> <%
							 	}
							 %>
					</td> 
					
				</TR> 
				
				 <TR align="left" class="dkrtooltip">
					
					<TD align="left" valign="top" class="ColumnBackground" width="16.6%"><font color="#FF0000" size="2">*</font><bean:message
							key="industrySector" /></td>
					<TD align="left" valign="top" class="TableData" width="16.6%">
						<%
												if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
														|| (appCommonFlag.equals("2"))
														|| (appCommonFlag.equals("3"))
														|| (appCommonFlag.equals("4"))
														|| (appCommonFlag.equals("5"))
														|| (appCommonFlag.equals("6"))
														|| (appCommonFlag.equals("11"))
														|| (appCommonFlag.equals("12"))
														|| (appCommonFlag.equals("13"))
														|| (appCommonFlag.equals("14"))
														|| (appCommonFlag.equals("17"))
														|| (appCommonFlag.equals("18"))
														|| (appCommonFlag.equals("19")))
							
												{
											%> <bean:write name="appForm" property="industrySector" /> <%
							 	} else {
							 %> <html:select property="industrySector" name="appForm" styleClass="w150"
							onchange="javascript:submitForm('afterTcMli.do?method=getTypeOfActivity')">
							<html:option value="">Select</html:option>
							<html:options name="appForm" property="industrySectorList"></html:options>
							<!-- <html:option value="Others">Others</html:option>-->
						</html:select> <%
							 	}
							 %>
					</td>
					<TD align="left" valign="top" class="ColumnBackground" width="16.6%">&nbsp;<!--<font
						color="#FF0000" size="2">*</font>
					--><bean:message key="activitytype" />
					</TD>
						<TD align="left" valign="top" class="TableData" width="16.6%"
						>
						<%
								if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
										|| (appCommonFlag.equals("2"))
										|| (appCommonFlag.equals("3"))
										|| (appCommonFlag.equals("4"))
										|| (appCommonFlag.equals("5"))
										|| (appCommonFlag.equals("6"))
										|| (appCommonFlag.equals("11"))
										|| (appCommonFlag.equals("12"))
										|| (appCommonFlag.equals("13"))
										|| (appCommonFlag.equals("14"))
										|| (appCommonFlag.equals("17"))
										|| (appCommonFlag.equals("18"))
										|| (appCommonFlag.equals("19"))) {
							%> 
					<bean:write property="activityType" name="appForm" /> <%
							} else {
						%> <html:select property="activityType" name="appForm">
							<html:option value="">Select</html:option>
							<html:option value="Manufacturing">Manufacturing</html:option>
							<html:option value="Services">Services</html:option>
							<html:option value="Retail Trade">Retail Trade</html:option>
						</html:select>&nbsp;&nbsp;&nbsp;&nbsp; <%
													}
												%></TD>
				<%-- 	<TD align="left" valign="top" class="TableData"
						onmouseover="getSelectIndustryRetail();">
						<%
												if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
														|| (appCommonFlag.equals("2"))
														|| (appCommonFlag.equals("3"))
														|| (appCommonFlag.equals("4"))
														|| (appCommonFlag.equals("5"))
														|| (appCommonFlag.equals("6"))
														|| (appCommonFlag.equals("11"))
														|| (appCommonFlag.equals("12"))
														|| (appCommonFlag.equals("13"))
														|| (appCommonFlag.equals("14"))
														|| (appCommonFlag.equals("17"))
														|| (appCommonFlag.equals("18"))
														|| (appCommonFlag.equals("19")))
							
												{
											%> <html:text property="activityType" styleId="activityType"
							size="20" alt="Activity Type" name="appForm" maxlength="75" /> <%
							 	} else {
							 %> <html:text property="activityType" size="20"
							styleId="activityType" alt="Activity Type" name="appForm"
							maxlength="75" /> <%
							 	}
							 %>
					</td> --%>
					<TD align="left" valign="top" class="ColumnBackground" width="16.6%"><font
						color="#FF0000" size="3">*</font>
					<bean:message key="noOfEmployees" /></td>
					<TD align="left" valign="top" class="TableData" width="16.6%" >
						<%
								if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
										|| (appCommonFlag.equals("2"))
										|| (appCommonFlag.equals("3"))
										|| (appCommonFlag.equals("4"))
										|| (appCommonFlag.equals("5"))
										|| (appCommonFlag.equals("6"))
										|| (appCommonFlag.equals("11"))
										|| (appCommonFlag.equals("12"))
										|| (appCommonFlag.equals("13"))
										|| (appCommonFlag.equals("14"))
										|| (appCommonFlag.equals("17"))
										|| (appCommonFlag.equals("18"))
										|| (appCommonFlag.equals("19")))

								{
							%> <html:text property="employeeNos" size="10"
							alt="No Of employees" name="appForm" maxlength="5" /> <%
						} else {
					%><html:text property="employeeNos" size="10"
							alt="No Of employees" name="appForm"
							onkeypress="return numbersOnly(this, event)"
							onkeyup="isValidNumber(this)" maxlength="5" /> <%
																						}
																					%>
					</td>
				</tr>

				<%-- <TR align="left">
					
				</tr>
				
				  <TR>
				 
					
					<TD align="left" valign="top" class="ColumnBackground" colspan="4">
					
					 
						  <input type="checkbox" name="activityConfirm"  id="activityConfirm" >
						 <bean:message key="GSTConfirm" />
					 
			</TD>		
</TR> --%>
				 <tr>
						<TD align="left" valign="top" class="ColumnBackground" style="width:16.6% !important">&nbsp;<bean:message
				key="BorrowergstNo" /></td>
			<td class="TableData" height="28" style="width:16.6% !important">	
									<%				
										if((appCommonFlag.equals("11"))||(appCommonFlag.equals("13")))
										

										{
										%>
										<bean:write name="appForm" property="BorrowergstNo"/>
										<%}
										else
										{ %>
									
										<html:text property="BorrowergstNo" size="20" alt="BorrowergstNo" name="appForm" maxlength="15" /> 
									<%}%>
									
								
		</TD>
<%-- 		<TD align="left" valign="top" class="ColumnBackground" width="30%"><font
						color="#FF0000" size="2">*</font>
					<bean:message key="turnover" /></TD>
					<TD align="left" valign="top" class="TableData">
						<%
													if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
															|| (appCommonFlag.equals("2"))
															|| (appCommonFlag.equals("3"))
															|| (appCommonFlag.equals("4"))
															|| (appCommonFlag.equals("5"))
															|| (appCommonFlag.equals("6"))
															|| (appCommonFlag.equals("11"))
															|| (appCommonFlag.equals("12"))
															|| (appCommonFlag.equals("13"))
															|| (appCommonFlag.equals("14"))
															|| (appCommonFlag.equals("17"))
															|| (appCommonFlag.equals("18"))
															|| (appCommonFlag.equals("19"))) {
												%> <html:text property="projectedSalesTurnover" size="10"
							alt="turnover" name="appForm" maxlength="10" /> <%
													} else {
												%> <html:text property="projectedSalesTurnover" size="20"
							alt="turnover" name="appForm" maxlength="10" styleId="projectedSalesTurnover"
							onkeypress="return decimalOnly(this, event,13)"
							onkeyup="isValidDecimal(this);" /> <%
														}
													%> <bean:message key="inRs" />
					</td>
					
		</TR>  --%>
		<TD align="left" valign="top" class="ColumnBackground" style="width:16.6% "><font
			color="#FF0000" size="2"></font> &nbsp;<bean:message
				key="udyogAdharNo" /></TD>
		<TD align="left" valign="top" class="TableData" style="width:16.6%  "><html:text
				property="udyogAdharNo" size="12" name="appForm" maxlength="12" /></TD>
		
					<TD align="left" valign="top" class="ColumnBackground" width="16.6%"><font
						color="#FF0000" size="2">*</font>&nbsp;<bean:message
							key="constitution" /></TD>
					<TD align="left" valign="top" class="TableData" width="16.6%">
						<%
						
													if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
															|| (appCommonFlag.equals("2"))
															|| (appCommonFlag.equals("3"))
															|| (appCommonFlag.equals("4"))
															|| (appCommonFlag.equals("5"))
															|| (appCommonFlag.equals("6"))
															|| (appCommonFlag.equals("11"))
															|| (appCommonFlag.equals("12"))
															|| (appCommonFlag.equals("13"))
															|| (appCommonFlag.equals("14"))
															|| (appCommonFlag.equals("17"))
															|| (appCommonFlag.equals("18"))
															|| (appCommonFlag.equals("19"))) {
												%> <html:select property="constitution" name="appForm"
							disabled="true">
							<html:option value="">Select</html:option>
							<html:option value="Proprietary/Individual">Proprietary/Individual</html:option>
							<html:option value="Partnership">Partnership</html:option>
							<html:option value="Limited liability Partnership">Limited liability Partnership</html:option>
							<html:option value="private">
								<bean:message key="private" />
							</html:option>

							<html:option value="public">
								<bean:message key="public" />
							</html:option>
							<html:option value="HUF">HUF</html:option>
							<html:option value="Trust">Trust</html:option>
							<html:option value="Society/Co op">Society/Co op Society</html:option>
							<!--	<html:option value="Others"><bean:message key="others" /></html:option> -->
						</html:select>   <%
													} else {
												%> <html:select property="constitution" name="appForm"
							onchange="setConstEnabled()">
							<html:option value="">Select</html:option>
							<html:option value="Proprietary/Individual">Proprietary/Individual</html:option>
							<html:option value="Partnership">Partnership</html:option>
							<html:option value="Limited liability Partnership">Limited liability Partnership</html:option>
							<html:option value="private">
								<bean:message key="private" />
							</html:option>

							<html:option value="public">
								<bean:message key="public" />
							</html:option>
							<html:option value="HUF">HUF</html:option>
							<html:option value="Trust">Trust</html:option>
							<html:option value="Society/Co op">Society/Co op Society</html:option>
							</html:select>
							 <%
													} %>
							</TD>
		</TR>
 

			</TABLE>
		</TD>
	</TR>
	<tr>
		<td colspan="12">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tbody><tr>
					<td width="31%" class="Heading">Existing Facility Details </td>
					<td><img width="19" height="19" src="images/TriangleSubhead.gif"></td>
				</tr>
				<tr>
					<td class="Heading" colspan="6"><img width="5" height="5" src="images/Clear.gif"></td>
				</tr>
			
			</table>
		</td>		
	</tr>
	 	 <%-- 
				<%
				  String loanTypd_d = session.getAttribute("APPLICATION_LOAN_TYPE").toString();
								System.out.println("loanTypd_d :"+loanTypd_d);
				String appTCFlag=session.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG).toString();
				if (appTCFlag.equals("0") || appTCFlag.equals("1") || appTCFlag.equals("2"))
				{
				%>
				<tr align="left">
					<td class="ColumnBackground">
						<bean:message key="facilityRehabilitation" />
					</td>
					<td class="TableData" colspan="9">
					
							<html:radio name="appForm" value="Y" property="rehabilitation" ></html:radio>											
							<bean:message key="yes" />											
							<html:radio name="appForm" value="N" property="rehabilitation" ></html:radio>											
							<bean:message key="no" />
					</td>
				</tr>
					<%}%>
					  --%>
					
					<!-- <tr align="left"> 
				<td colspan="12" class="SubHeading" height="28" width="843"><br> &nbsp;
					<bean:message key="termCreditDetails" />
				</td>
			</tr>
			 -->
		<tr>
		<td width="843" height="28" class="SubHeading" colspan="12"><br>
			Exposure with Applicant Bank                                             
			&nbsp;</td>
		</tr>
	<tr>
		<td colspan="12">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
				<tr>
					<th class="ColumnBackground" width="25%" style="border:1px solid white;"></th>
					<th class="ColumnBackground" width="25%" style="border:1px solid white;"><bean:message key="sancAmt" /> </th>
					<th class="ColumnBackground" width="25%" style="border:1px solid white;"><bean:message key="outstandingAmt" />       </th>
					<th class="ColumnBackground" width="25%" style="border:1px solid white;">Account Status </th> 
				</tr>
			</thead>
			<tbody>
				 <tr>
				 	<td class="ColumnBackground" width="25%" style="border:1px solid white;"><bean:message key="termLoan" /> </td>
				 	<td class="TableData" width="25%" style="border:1px solid white;"><html:text  name="appForm" property="sancAmt" maxlength="9"   size="25"  styleId="sancAmt" alt="sancAmt" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" /></td>
				 	<td class="TableData" width="25%" style="border:1px solid white;"><html:text  name="appForm" property="outstandingAmt" maxlength="9"   size="25"  styleId="outstandingAmt" alt="outstandingAmt" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" /></td>
				 	<td class="TableData" width="25%" style="border:1px solid white;"><html:select property="accStatus" name="appForm">
							<html:option value="">Select</html:option>
							<html:option value="NPA">SMA2</html:option>
							<html:option value="NPA">NPA</html:option>
						</html:select></td>
				 </tr>
				 <tr>
				 	<td class="ColumnBackground" width="25%" style="border:1px solid white;"><bean:message key="workingCapital" /> </td>
				 	<td class="TableData" width="25%" style="border:1px solid white;"><html:text  name="appForm" property="wcSancAmt" maxlength="9"   size="25"  styleId="wcSancAmt" alt="wcSancAmt" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" /></td>
				 	<td class="TableData" width="25%" style="border:1px solid white;"><html:text  name="appForm" property="wcOutstandingAmt" maxlength="9"   size="25"  styleId="wcOutstandingAmt" alt="wcOutstandingAmt" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" /></td>
				 	<td class="TableData" width="25%" style="border:1px solid white;"><html:select property="wcAccStatus" name="appForm">
							<html:option value="">Select</html:option>
							<html:option value="NPA">SMA2</html:option>
							<html:option value="NPA">NPA</html:option>
						</html:select></td>
				 </tr>
				  <tr>
				 	<td class="ColumnBackground" width="25%" style="border:1px solid white;"><bean:message key="other" />  </td>
				 	<td class="TableData" width="25%" style="border:1px solid white;"><html:text  name="appForm" property="otherSancAmt" maxlength="9"   size="25"  styleId="otherSancAmt" alt="otherSancAmt" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" /></td>
				 	<td class="TableData" width="25%" style="border:1px solid white;"> <html:text  name="appForm" property="otherOutstandingAmt" maxlength="9"   size="25"  styleId="otherOutstandingAmt" alt="otherOutstandingAmt" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" /></td>
				 	<td class="TableData" width="25%" style="border:1px solid white;"><html:select property="otherAccStatus" name="appForm">
							<html:option value="">Select</html:option>
							<html:option value="NPA">SMA2</html:option>
							<html:option value="NPA">NPA</html:option>
						</html:select></td>
				 </tr>
				 <tr>
				 	<td class="ColumnBackground" width="25%"  style="border:1px solid white;"><bean:message key="total" />  </td>
				 	<td class="TableData" width="25%" style="border:1px solid white;"><html:text  name="appForm" property="totalSancAmt" maxlength="9"   size="25"  styleId="outstandingAmt" alt="outstandingAmt" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" /></td>
				 	<td class="TableData" width="25%" style="border:1px solid white;"><html:text  name="appForm" property="totalOutstandingAmt" maxlength="9"   size="25"  styleId="outstandingAmt" alt="outstandingAmt" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" /></td>
				 	<td class="TableData" width="25%" style="border:1px solid white;"> </td>
				 </tr>
				 
			</tbody>
			</table>
		</td>		
	</tr>	
		 
		<tr	align="left">  
			  <td  align="left" valign="top" class="ColumnBackground" width="16.6%">
						&nbsp;<bean:message key="facilityOthrtBnk" /> 
							</td>	
							
							
			<TD align="left" valign="top" class="TableData" width="16.6%">
		 <html:radio name="appForm" value="Y" property="mSE">YES
												</html:radio> 
			<html:radio name="appForm" value="Y" property="mSE">NO</html:radio></TD>
<td  align="left" valign="top" class="ColumnBackground" width="16.6%">
						&nbsp;Whether NOC taken from other Banks      
							</td>	
			<TD align="left" valign="top" class="TableData" width="16.6%"  >
		 <html:radio name="appForm" value="Y" property="mSE">YES
												</html:radio> 
			<html:radio name="appForm" value="Y" property="mSE">NO</html:radio></TD>
			  
		</tr>	  
			   <tr align="left">
		<td colspan="12" class="SubHeading" height="28" width="843"><br>
			Exposure with Other Banks 
			&nbsp;</td>
			</tr>
		<tr>
		<td colspan="12">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
				<tr>
					<th class="ColumnBackground" width="25%" style="border:1px solid white;"></th>
					<th class="TableData" width="25%" style="border:1px solid white;"><bean:message key="sancAmt" /> </th>
					<th class="TableData" width="25%" style="border:1px solid white;"><bean:message key="outstandingAmt" />       </th>
					<th class="TableData" width="25%" style="border:1px solid white;">Account Status </th> 
				</tr>
			</thead>
			<tbody>
				 
				 <tr>
				 	<td class="ColumnBackground" width="25%" style="border:1px solid white;"><bean:message key="termLoan" /> </td>
				 	<td class="TableData" width="25%" style="border:1px solid white;"><html:text  name="appForm" property="wcSancAmt" maxlength="9"   size="25"  styleId="wcSancAmt" alt="wcSancAmt" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" /></td>
				 	<td class="TableData" width="25%" style="border:1px solid white;"><html:text  name="appForm" property="wcOutstandingAmt" maxlength="9"   size="25"  styleId="wcOutstandingAmt" alt="wcOutstandingAmt" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" /></td>
				 	<td class="TableData" width="25%" style="border:1px solid white;"><html:select property="wcAccStatus" name="appForm">
							<html:option value="">Select</html:option>
							<html:option value="NPA">SMA2</html:option>
							<html:option value="NPA">NPA</html:option>
						</html:select></td>
				 </tr>
				  <tr>
				 	<td class="ColumnBackground" width="25%" style="border:1px solid white;"><bean:message key="workingCapital" />  </td>
				 	<td class="TableData" width="25%" style="border:1px solid white;">
				 		<html:text  name="appForm" property="otherBankwcSancAmt" maxlength="9"   size="25"  styleId="otherBankwcSancAmt" alt="otherBankwcSancAmt" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />
				 	</td>
				 	<td class="TableData" width="25%" style="border:1px solid white;"> 
				 		<html:text  name="appForm" property="otherBankwcOutstandingAmt" maxlength="9"   size="25"  styleId="otherBankwcOutstandingAmt" alt="otherBankwcOutstandingAmt" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />
				 	</td>
				 	<td class="TableData" width="25%" style="border:1px solid white;">
						<html:select property="otherBankwcAccStatus" name="appForm">
							<html:option value="">Select</html:option>
							<html:option value="NPA">SMA2</html:option>
							<html:option value="NPA">NPA</html:option>
						</html:select>
						</td>
				 </tr>
				 <tr>
				 	<td class="ColumnBackground" width="25%"  style="border:1px solid white;"><bean:message key="other" />   </td>
				 	<td class="TableData" width="25%" style="border:1px solid white;">
				 		<html:text  name="appForm" property="otherBankotherSancAmt" maxlength="9"   size="25"  styleId="otherBankotherSancAmt" alt="otherBankotherSancAmt" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />
					</td>
				 	<td class="TableData" width="25%" style="border:1px solid white;">
				 		<html:text  name="appForm" property="otherBankotherOutstandingAmt" maxlength="9"   size="25"  styleId="otherBankotherOutstandingAmt" alt="otherBankotherOutstandingAmt" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />
					</td>
				 	<td class="TableData" width="25%" style="border:1px solid white;">
				 		<html:select property="otherBankotherAccStatus" name="appForm">
							<html:option value="">Select</html:option>
							<html:option value="NPA">SMA2</html:option>
							<html:option value="NPA">NPA</html:option>
						</html:select>
				 	 </td>
				 </tr>
				 <tr>
				 	<td class="ColumnBackground" width="25%" style="border:1px solid white;"> <bean:message key="total" />  </td>
				 	<td class="TableData" width="25%" style="border:1px solid white;">
				 	 <html:text  name="appForm" property="otherBanktotalSancAmt" maxlength="9"   size="25"  styleId="otherBanktotalSancAmt" alt="otherBanktotalSancAmt" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />
				 	 </td>
				 	<td class="TableData" width="25%" style="border:1px solid white;">
				 		<html:text  name="appForm" property="otherBanktotalOutstandingAmt" maxlength="9"   size="25"  styleId="otherBanktotalOutstandingAmt" alt="otherBanktotalOutstandingAmt" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />
				 	</td>
				 	<td class="TableData" width="25%" style="border:1px solid white;"> 
						 </td>
				 </tr>
				 
			</tbody>
			</table>
		</td>		
	</tr>		
					<%--    <tr align="left">
		<td  align="left" valign="top" class="ColumnBackground" width="25%"><br>
			1.Name Of Bank
			&nbsp;</td>
			<td  align="left" valign="top" class="ColumnBackground" width="25%">
			 <html:text  name="appForm" property="otherBankName" maxlength="9"   size="25"  styleId="otherBankName" alt="otherBankName" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />   
			</td>	
			  <td  align="left" valign="top" class="ColumnBackground" width="25%">
							</td>	
							
							  <td  align="left" valign="top" class="ColumnBackground" width="25%">
							</td>
							  <td  align="left" valign="top" class="ColumnBackground" width="25%">
							</td>		
							
	 </tr> 	  --%>
			   
	  
			  
	 <tr>
	   <td colspan="12">
			  	<table style="width:100%;border:0;">
			  		<tbody>
			  		<tr>
			  			<td  align="left" valign="top" class="ColumnBackground" width="16.6%">
						&nbsp;&nbsp; NPA Date
							</td>	
		<td style="width:16.6% !important" class="TableData">
			 <html:text  name="appForm" property="npaDate" maxlength="9"   size="25"  styleId="npaDate" alt="npaDate" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />   
			</td>

  <td  align="left" valign="top" class="ColumnBackground" width="16.6%">
						&nbsp;&nbsp; NPA Reason
							</td>	
		<td style="width:16.6% !important" class="TableData">
			 <html:text  name="appForm" property="npaReason" maxlength="9"   size="25"  styleId="npaReason" alt="npaReason" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />   
			</td>

  <td  align="left" valign="top" class="ColumnBackground" width="16.6%">
						&nbsp;<bean:message key="isAcctReconstructed" /> 
							</td>	
			<TD align="left" valign="top" class="ColumnBackground"  width="16.6%">
		 <html:radio name="appForm" value="Y" property="isAcctReconstructed">YES
												</html:radio> </TD></tr>
			  		</tbody>
			  	</table>
		</td>
			  	 
		</tr>  
	
	<tr>
				<td colspan="12">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<TD width="31%" class="Heading">Financial Parameters </TD>
							<TD><IMG src="images/TriangleSubhead.gif" width="19"
								height="19"></TD>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
						<TR>
							<TD colspan="4" class="Heading"><IMG src="images/Clear.gif"
								width="5" height="5"></TD>
						</TR>
					</table>
				</td>
			</tr>
	<tr>
	<td colspan="12">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tbody>
					<tr>
		<TD align="left" valign="top" class="ColumnBackground"  width="25%"><font
				color="#FF0000" size="2">* </font>Present Sales / Turnover 
			 </TD>
			<TD align="left" valign="top" class="TableData" width="25%">
			<html:text property="projectedSalesTurnover" size="20"
					alt="turnover" name="appForm" maxlength="10" styleId="projectedSalesTurnover"
					onkeypress="return decimalOnly(this, event,13)"
					onkeyup="isValidDecimal(this);" />  <bean:message key="inRs" />
			</td>
			<TD align="left" valign="top" class="ColumnBackground"  width="25%"><font
				color="#FF0000" size="2">* </font>Projected Sales / Turnover  
			 </TD>
			<TD align="left" valign="top" class="TableData"  width="25%">
			<html:text property="projectedSalesTurnover" size="20"
					alt="turnover" name="appForm" maxlength="10" styleId="projectedSalesTurnover"
					onkeypress="return decimalOnly(this, event,13)"
					onkeyup="isValidDecimal(this);" />  <bean:message key="inRs" />
			</td>
			</tr>
			</tbody>
		</table>
		</td>	
	</tr>		
			
<%-- <TR align="left"> 

		<TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>
			&nbsp;Loan Account Number</TD>
		<TD align="left" valign="top" class="ColumnBackground">&nbsp;<html:text
				property="bankAcNo" size="20" name="appForm" maxlength="16" />
		</TD>
	</TR> --%>

		<html:hidden property="socialCategory" name="appForm" />
		 
		<html:hidden property="firstItpan" name="appForm" />
		 
		<html:hidden property="firstDOB" name="appForm" />
		 
		<html:hidden property="secondName" name="appForm" />
		 
		<html:hidden property="secondItpan" name="appForm" />
		 
		<html:hidden property="secondDOB" name="appForm" />
		 
		<html:hidden property="thirdName" name="appForm" />
		 
		<html:hidden property="thirdItpan" name="appForm" />
		 
		<html:hidden property="thirdDOB" name="appForm" />
	  
	<!-- <tr align="left">
		<td colspan="6"><img src="../images/clear.gif" width="5"
			height="15"></td>
	</tr> -->
</TABLE>
<!--table closing for borrower details-->


<!--start for project-->
<table width="100%" cellspacing="1" cellpadding="0">
	<%-- <tr>
		<td colspan="12">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<TD width="31%" class="Heading"><bean:message
							key="projectHeader" /></TD>
					<TD><IMG src="images/TriangleSubhead.gif" width="19"
						height="19"></TD>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<TR>
					<TD colspan="4" class="Heading"><IMG src="images/Clear.gif"
						width="5" height="5"></TD>
				</TR>
			</table>
		</td>
	</tr> --%>
	  
	<TR align="left">
		<%
			if (session.getAttribute("expoid")!=null && !session.getAttribute("expoid").equals("")) {
		%>
		<TD align="left" valign="top" class="ColumnBackground" colspan="3">&nbsp;<bean:message
				key="fbschemechk" /></td>
		<TD align="left" valign="top" class="ColumnBackground" colspan="5">
			<html:radio name="appForm" value="Y1" property="exposureFbId"></html:radio>
			<bean:message key="Y1" />&nbsp;&nbsp; <html:radio name="appForm"
				value="N1" property="exposureFbId"></html:radio> <bean:message
				key="N1" />

		</TD>
		<%
						 }
						 %>
	</TR>
	<html:hidden property="pSecurity" name="appForm" />
	 
		<html:hidden property="collateralSecurityTaken" name="appForm" />
		 
			<html:hidden property="thirdPartyGuaranteeTaken" name="appForm" />
	 
		<html:hidden property="jointFinance" name="appForm" />
		 
		<html:hidden property="handiCraftsStatus" name="appForm" />
		 
		<html:hidden property="dcHandicraftsStatus" name="appForm" />
		  
	<%-- <tr align="left">
		<td colspan="12" class="SubHeading" height="28" width="843"><br>
			4.1  Credit Exposure Details as on 29.2.2020 
			&nbsp; <bean:message key="meansOfFinance" /></td>
	</tr> 	 --%>	
		<html:hidden property="tcPromoterContribution" name="appForm" />
		 
					<html:hidden property="tcSubsidyOrEquity" name="appForm" />
		 
					<html:hidden property="wcSubsidyOrEquity" name="appForm" />
		 
					<html:hidden property="tcOthers" name="appForm" />
		 
					<html:hidden property="wcOthers" name="appForm" />
		 
					<html:hidden property="projectCost" name="appForm" />
		 
					<html:hidden property="wcAssessed" name="appForm" />
		 
					<html:hidden property="projectOutlay" name="appForm" />
		 
		<%--   <tr>	<td class="ColumnBackground" colspan="1"><bean:message
							key="inRs" /> <html:hidden property="projectOutlay"
							name="appForm" /></td>
		  </tr>  --%>
				<html:hidden property="status" name="appForm" />  
				 <html:hidden property="expiryDate" name="appForm" />
		  		
			</table>
			
	 











