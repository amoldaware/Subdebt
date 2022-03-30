<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Iterator"%>
<%@page import="com.cgtsi.registration.MLIInfo"%>
<%@page import="com.cgtsi.actionform.GMActionForm"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>

<% 

session.setAttribute("CurrentPage","getUnitForTenureRequest.do?method=getUnitForTenureRequest"); %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%
String focusField="";
String app_Status="";
%>


<HTML>
<head>
<SCRIPT language="JavaScript" type="text/JavaScript" src="js/messages.js">
</SCRIPT>
<LINK href="css/messages.css" rel="stylesheet" type="text/css">
 <script type="text/JavaScript">
 function remakrsValidation(remaksValue)
 {
	 
	 if(remaksValue=="Reschedulement_Rephasement")
	 {
		 var appStatus=document.forms[0].appStatus.value;
		 if(appStatus=='AP')
		 {
			 document.forms[0].chkTermsAndCondition.checked = false;
			 document.forms[0].chkTermsAndCondition.disabled = true;		 		
		 }	 
	 }
	 else
	 {
		 document.forms[0].chkTermsAndCondition.disabled = false;
	 }
 }
        function submitForm22(action)
        {
    
        var str1 = document.forms[0].lastDateOfPayment.value;
        var existingTenure = document.forms[0].tenure.value;
         var reviseOfTenure = document.forms[0].reviseOfTenure.value;
         var modificationOfRemarks = document.forms[0].modificationOfRemarks.value;
         var IsValid = true; //bool
		 var message=''; 
        var appStatus=document.forms[0].appStatus.value;
        
         
         if(reviseOfTenure=='' && IsValid==true )
         {
        	 message="Revise Tenure is required";
        	 
         IsValid=false;
          }
        
         
         if(str1=='' && IsValid==true)
         {
        	 message="LastDate Of Payment is required";
         IsValid=false;
         }
         
         if(modificationOfRemarks=='' && IsValid==true)
         {
        	 message="Modification Remarks is required";
         IsValid=false;
          }

         if(modificationOfRemarks=="Reschedulement_Rephasement" && appStatus=="AP")
         {	         
         } else {
        	 if(document.forms[0].chkTermsAndCondition.checked==false && IsValid==true)
	         {
	        	 message="Please select Account STANDARD and REGULAR decision";
	         	 IsValid=false;
	         }
         }

         if(document.forms[0].chkTermsAndCondition1.checked==false && IsValid==true)
         {
        	 message="Please select the changes made are as per the record available with the bank and are duly approved by the delegated authority decision.";
         	 IsValid=false;
         }
            

		 if(message!='')
		 {
         	document.getElementById("errorsMessage").innerHTML ='';
         	document.getElementById("errorsMessage").innerHTML = message;
		
         	hideMessage();
		 }
         
          if(IsValid==true)
         {
        	
       
var validDateFormat =validate(str1);

if(validDateFormat)
{
      var sysDate=new Date();
      var dt2 = sysDate.getDate();
      var mon2 = (sysDate.getMonth());
      var yr2 = sysDate.getFullYear();
    var dt1  = parseInt(str1.substring(0,2),10); 
    var mon1 = parseInt(str1.substring(3,5),10)-1;
    var yr1  = parseInt(str1.substring(6,10),10); 
    var date1 = new Date(yr1, mon1, dt1); 
          
    var date2 = new Date(yr2, mon2, dt2); 

   if(date2 > date1)
    {
      
        document.getElementById("errorsMessage").innerHTML ='';
     	document.getElementById("errorsMessage").innerHTML = "LastDate of Payment cannot be less than System Date";
     
     	hideMessage();
    } 
   else 
    { 
     
      document.forms[0].action=action;
	document.forms[0].target="_self";
	document.forms[0].method="POST";
	document.forms[0].submit();
    } 
   }
else
{
	
document.getElementById("errorsMessage").innerHTML ='';
	document.getElementById("errorsMessage").innerHTML = "Date Format should be in dd/mm/yyyy";
	hideMessage();
	
}
        
        }
        
              
    
}

    function isValidDate2(sText) {
    
        
         var reDate = /(?:0[1-9]|[12][0-9]|3[01])\/(?:0[1-9]|1[0-2])\/(?:19|20\d{2})/;
        return reDate.test(sText);
    }
    function validate(oInput1) {
    
    
    
        if (isValidDate2(oInput1)) {
            return true;
            
        } else {
           return false;
            
        }

    }

    function calulateLastDateOFPayment(LastDateOFPayment)
    {      	  	 

    	   var existingTenure = document.forms[0].tenure.value;
    	    var reviseOfTenure = document.forms[0].reviseOfTenure.value;

    	    if(reviseOfTenure!='')
            {    
    	    	var intExistingTenure = parseInt(existingTenure);
    	    	var intReviseOfTenure = parseInt(reviseOfTenure);       
    	    	if(intReviseOfTenure <=120)
    	    	{  
                if(intExistingTenure >= intReviseOfTenure)
                {
            		
            		document.forms[0].reviseOfTenure.value='';
            		document.forms[0].lastDateOfPayment.value='';
            		document.getElementById("errorsMessage").innerHTML ='';
                 	document.getElementById("errorsMessage").innerHTML = "Revise Tenure should be greater than existing tenure["+existingTenure+"] ";
                 
            		IsValid=false;
            		hideMessage();
                }
                else
                {
                	var xmlhttp;
        		    if (window.XMLHttpRequest){
        		        xmlhttp = new XMLHttpRequest(); //for IE7+, Firefox, Chrome, Opera, Safari
        		    } else {
        		        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP"); //for IE6, IE5
        		    }
        		    
        		    xmlhttp.open("POST", "TenureModificationLastPayDateCalcAction.do?method=tenureModificationLastPayDateCalcMethod&tenure="+document.forms[0].reviseOfTenure.value+"&expiryDate="+document.forms[0].expiryDate.value+"&existingTenure="+document.forms[0].tenure.value, true);        		      
        		    xmlhttp.onreadystatechange = function() {
        		    if (xmlhttp.readyState == 4) 
        			{
        		            if (xmlhttp.status == 200)
        		            {
        		            	  var temp = new Array();
        			              temp =xmlhttp.responseText;	
        			              if(temp.length <= 10 )
        			              {
        			              	document.forms[0].lastDateOfPayment.value=temp;
        			              } else
        			              {
        			            		document.forms[0].reviseOfTenure.value='';
        			            		document.forms[0].lastDateOfPayment.value='';
        			            		document.getElementById("errorsMessage").innerHTML =temp;
        			              }
        		            }
        		            else
        		            {
        		                alert('Something is wrong !!');
        		            }
        		        }
        		    };
        		    xmlhttp.send(null);
                }
    	    	}
    	    	else
    	    	{
    	    		
					 	document.forms[0].reviseOfTenure.value='';
            		document.forms[0].lastDateOfPayment.value='';
    	    	
		             document.getElementById("errorsMessage").innerHTML ='Revice tenure should not greater than 120 months';
		             hideMessage();
    	    	}
            }     		
    	 
    	
    }
    function showReviseTenureToolTip()
    {
    	 document.getElementById("errorsMessage").innerHTML ='Revised tenure = Current Tenure + Incremental Tenure';
    	 hideMessage();
    
    }
    
   function hideMessage()
   {
	 
	    var started = Date.now();
        var interval = setInterval(function(){

           
            if (Date.now() - started > 8500) {
              document.getElementById("errorsMessage").innerHTML ='';
              clearInterval(interval);
           
            } else {
            	document.getElementById("errorsMessage").innerHTML ='';
            }
          }, 8000);
   }
   
 //Added by Kailash for GST
var xmlhttp;
function getGSTValueForTenure()	{

    var stateCode=document.getElementById("stateCode").value; 
    
    var url = "getGSTByStateCodeForTenure.do?method=getGSTNOTenure&stateCode="+stateCode;
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
	    if (xhr.readyState == XMLHttpRequest.DONE) {
	        document.getElementById('gstNo').value=xhr.responseText;
	        //document.getElementById('hiddenGstNo').value=xhr.responseText;
	    }
	}
	xhr.open('GET', url, true);
	xhr.send(null);
}
        </script>
</head>
	<BODY>
<SCRIPT language="JavaScript" type="text/JavaScript" src="<%=request.getContextPath()%>/js/CGTSI.js">
	</SCRIPT>
     <SCRIPT language="JavaScript" type="text/JavaScript" src="<%=request.getContextPath()%>/js/selectdate.js">
	</SCRIPT> 
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="getUnitForTenureRequest.do?method=getUnitForTenureRequest" method="POST"  >
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/GuaranteeMaintenanceHeading.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
			<DIV align="right">			
	
			</DIV>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="4"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="31%" class="Heading"><bean:message key="modifyBorrowerHeader"/></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>
								</TR>
                <tr>
			<TD  align="left" colspan="4"><font size="2"><bold>
				Fields marked as </font><font color="#FF0000" size="2">*</font><font size="2"> are mandatory</bold></font>
			</td>
		</tr>
						<TR align="left" valign="top">
									<TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2"></font><bean:message key="bankName"/>
									</TD>
									<TD align="left" valign="top" class="TableData">
										 <bean:write property="bankName" name="gmClosureForm"/>
									</TD>
                  <TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2"></font><bean:message key="zoneName"/>
									</TD>
									<TD align="left" valign="top" class="TableData">
									  <bean:write property="zoneName" name="gmClosureForm"/>
									</TD>
								</TR>
            

								<TR align="left" valign="top">
									<TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2"></font><bean:message key="MemberID"/>
									</TD>
									<TD align="left" valign="top" class="TableData">
										<bean:write property="memberIdForClosure" name="gmClosureForm"/>
									</TD>
                  <TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2"></font><bean:message key="cgpan"/>
									</TD>
									<TD align="left" valign="top" class="TableData">
									 <bean:write property="cgpanForClosure" name="gmClosureForm"/>
									</TD>
								</TR>
                                     <TR align="left" valign="top">
									<TD align="left" valign="top" class="ColumnBackground"><bean:message key="branchName"/>
									</TD>
									<TD align="left" valign="top" class="TableData">
									  <bean:write property="branchName" name="gmClosureForm"/>                                                                               
									</TD>
                  					<TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2"></font><bean:message key="tenure"/>[In months]
									</TD>
									<TD align="left" valign="top" class="TableData">
									 <bean:write property="tenure" name="gmClosureForm"/>
									 <html:hidden property="tenure"  name="gmClosureForm"   />
									</TD>
								</TR>
                                                                <TR align="left" valign="top">
									<TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2"></font>Expiry Date
									</TD>
									<TD align="left" valign="top" class="TableData">
										 <bean:write property="expiryDate" name="gmClosureForm"/>
										  <html:hidden property="expiryDate"  name="gmClosureForm"   />
									</TD>
                  					<TD align="left" valign="top" class="ColumnBackground"><bean:message key="branchState"/>
									</TD>
									<TD align="left" valign="top" class="TableData">
									 <html:select property="gstState" styleId="stateCode" onchange="return getGSTValueForTenure();">
				                            <html:option value="">-- Select MLI State --</html:option>
				                            <html:optionsCollection name="gmClosureForm" property="branchStateList" label="stateName" value="stateCode" />
				                        </html:select>	
									</TD>
									
								</TR>
						 		<TR align="left" valign="top">
									<TD align="left" valign="top" class="ColumnBackground"><bean:message key="MemberID"/>
									</TD>
									<TD align="left" valign="top" class="TableData">
										<bean:write property="memberIdForClosure" name="gmClosureForm"/>
									</TD>
                  					<TD align="left" valign="top" class="ColumnBackground"><bean:message key="gst"/>
									</TD>
									<TD align="left" valign="top" class="TableData">
									 	<input type="text" id="gstNo"  name="gstNo" name="gstNo" readonly style="color: red; background:#C4DEE6">  
									 	
									</TD>
								</TR>
								<TR align="left" valign="top">
									<TD align="left" valign="top" class="ColumnBackground"></font><bean:message key="ssiName"/>
									</TD>
									<TD align="left" valign="top" class="TableData">
										<bean:write property="unitName" name="gmClosureForm"/>
									</TD>
                  					
								</TR>
             					 
								<TR align="left" valign="top">
									<TD align="left" valign="top"   class="ColumnBackground"><font color="#FF0000" size="2">*</font>Revised Tenure [In months]
									</TD>
									<TD align="left" valign="top" class="TableData" colspan="4"><!--
             
								<html:text property="reviseOfTenure"  size="20" alt="closure Remarks" name="gmClosureForm" onmouseover="showReviseTenureToolTip(this)" onkeypress="return numbersOnly(this, event)" onblur="calulateLastDateOFPayment(this.value)"   />
								-->
								<html:text property="reviseOfTenure"  size="20" alt="closure Remarks" name="gmClosureForm"  />

						      </TD>
								</TR>  	
								<TR>                								
								  <TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>Revised Expiry Date
									</TD>
									<TD align="left" valign="top" class="TableData" colspan="4"><!--
										<html:text property="lastDateOfPayment" size="20" name="gmClosureForm" maxlength ="10" readonly="true"  />-->
										<html:text property="lastDateOfPayment" size="20" name="gmClosureForm" maxlength ="10"   />
										

									</TD>
								</TR>
               
								<TR align="left" valign="top">
									<TD align="left" valign="top"   class="ColumnBackground"><font color="#FF0000" size="2">*</font>Modification Remarks
									</TD>
									<TD align="left" valign="top" class="TableData" colspan="4">
          



<html:select property="modificationOfRemarks" name="gmClosureForm" onchange="remakrsValidation(this.value);">
										<html:option value="">Select</html:option>
										<html:option value="Reschedulement_Rephasement">Reschedulement/Rephasement of the account</html:option>
										<html:option value="Slow down in the business">Slow down in Business/Economy</html:option>
										<html:option value="High competition">High competition</html:option>
										<html:option value="Death of the borrower">Death of the borrower</html:option>
										<html:option value="Natural Calamities">Natural Calamity</html:option>
										<html:option value="Typing Error">Typing Error</html:option>
										</html:select>	
						      </TD>
								</TR>  			
								
								<TR align="left" valign="top" >
									<TD align="left" valign="top"   class="ColumnBackground" colspan="5" >
									 &nbsp;&nbsp; <input type="checkbox" value="Account is Standard and Regular" id="chkTermsAndCondition"> &nbsp;&nbsp; Account is Standard and Regular
									</TD>
									
									
								</TR>
								 <bean:define id="app_status" name="gmClosureForm" property = "appStatus"/>
									<%
									 app_Status=(String)app_status;
									System.out.println("app_Status "+app_Status);
									%>	  			
								<TR align="left" valign="top" >
	 								<TD align="left" valign="top"   class="ColumnBackground" colspan="5" >
									 &nbsp;&nbsp; <input type="checkbox" value="Account is Standard and Regular" id="chkTermsAndCondition1"> &nbsp;&nbsp; The changes made are as per the record available with the bank and are duly approved by the delegated authority.
									</TD>	
									
																						
								</TR> 
										<html:hidden property="appStatus" name="gmClosureForm" value="<%=app_Status%>"/>		
				</TABLE>
						</TD>
					</TR>
					<TR >
					
					</TR>
					<TR >
						<TD align="center" valign="baseline" >
						<div id="errorsMessage" class="errorsMessage"></div>
							<DIV align="center">
								
									<A href="javascript:submitForm22('submitSSIDetailForTenure.do?method=submitSSIDetailForTenure')">
									<IMG src="images/OK.gif" alt="OK" width="49" height="37" border="0"></A>
									<A href="javascript:document.gmClosureForm.reset()">
									<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>

								<A href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
								<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>
                                                                

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
	</html:form>
</TABLE>


	</BODY>
</HTML>