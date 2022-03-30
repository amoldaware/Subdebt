<%@ page language="java"%>
<%@page import ="java.text.SimpleDateFormat"%>
<%@page import ="java.util.Date"%>
<%@ page import="com.cgtsi.claim.ClaimApplication"%>
<%@ page import="com.cgtsi.actionform.ClaimActionForm"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>

<script type="text/javascript">

function submitDeclaResubmit(action)
{

	if(document.forms[0].nameOfOffi.value=="" || document.forms[0].nameOfOffi.value==null )
	{
		alert("please enter nameOfOfficial ");

		document.getElementById('nameOfOffi').focus();

	}

	if(document.forms[0].designa.value=="" || document.forms[0].designa.value==null )
	{
		alert("please enter designationOfOfficial ");
		document.getElementById('designa').focus();

	}
	if(document.forms[0].places.value=="" || document.forms[0].places.value==null )
	{
		alert("please enter place ");
		document.getElementById('places').focus();

	}




if(document.forms[0].iseligact[0].checked)
{
	 if((document.forms[0].iseligactcomm.value==null) || (document.forms[0].iseligactcomm.value==''))
	 {
		 //alert("no values");
		
		 document.forms[0].iseligactcomm.value="NA";
	 }
	 else
	 {
		 //alert("yes values");

	 var iseligactcom=document.forms[0].iseligactcomm.value;

	  var iChars = "!@$%^&*()+=-[]\\\â??;,./{}|\":?~_";
	   for (var i = 0; i < iseligactcom.length; i++) {
	   if (iChars.indexOf(iseligactcom.charAt(i)) != -1) {
	   alert("special(,!@$%^&*) characters are not allowed.");
      document.getElementById('iseligactcomm').focus();


         }
	   }
	 }
}


if(document.forms[0].whetcibildone[0].checked)
{
 if((document.forms[0].whetcibildonecomm.value==null) || (document.forms[0].whetcibildonecomm.value==''))
 {
	 //alert("no values");
	
	 document.forms[0].whetcibildonecomm.value="NA";
 }
 else
 {
	 //alert("yes values");

 var whetcibildonecom=document.forms[0].whetcibildonecomm.value;

  var iChars = "!@$%^&*()+=-[]\\\â??;,./{}|\":?~_";
   for (var i = 0; i < whetcibildonecom.length; i++) {
   if (iChars.indexOf(whetcibildonecom.charAt(i)) != -1) {
   alert("special(,!@$%^&*) characters are not allowed.");
document.getElementById('whetcibildonecomm').focus();


}
   }
 }
}








if(document.forms[0].isrataspercgs[0].checked)
{

 if((document.forms[0].isrataspercgscomm.value==null) || (document.forms[0].isrataspercgscomm.value==''))
 {
	// alert("no values");
	
	 document.forms[0].isrataspercgscomm.value="NA";
 }
 else
 {
	// alert("yes values");

 var isrataspercgscomm=document.forms[0].isrataspercgscomm.value;

  var iChars = "!@$%^&*()+=-[]\\\â??;,./{}|\":?~_";
   for (var i = 0; i < isrataspercgscomm.length; i++) {
   if (iChars.indexOf(isrataspercgscomm.charAt(i)) != -1) {
   alert("special(,!@$%^&*) characters are not allowed.");
document.getElementById('isrataspercgscomm').focus();


}
   }
 }
}

if(document.forms[0].isthirdcollattaken[1].checked)
{

 if((document.forms[0].isthirdcollattakencomm.value==null) || (document.forms[0].isthirdcollattakencomm.value==''))
 {
	 //alert("no values");
	
	 document.forms[0].isthirdcollattakencomm.value="NA";
 }
 else
 {
	// alert("yes values");

 var isthirdcollattakencomm=document.forms[0].isthirdcollattakencomm.value;

  var iChars = "!@$%^&*()+=-[]\\\â??;,./{}|\":?~_";
   for (var i = 0; i < isthirdcollattakencomm.length; i++) {
   if (iChars.indexOf(isthirdcollattakencomm.charAt(i)) != -1) {
   alert("special(,!@$%^&*) characters are not allowed.");
   document.getElementById('isthirdcollattakencomm').focus();


}
   }
 }
}

if(document.forms[0].isnpadtasperguid[1].checked || document.forms[0].isnpadtasperguid[0].checked)
{

 if((document.forms[0].isnpadtasperguidcomm.value==null) || (document.forms[0].isnpadtasperguidcomm.value==''))
 {
	 //alert("no values");
	
	 document.forms[0].isnpadtasperguidcomm.value="NA";
 }
 else
 {
	// alert("yes values");

 var isnpadtasperguidcomm=document.forms[0].isnpadtasperguidcomm.value;

  var iChars = "!@$%^&*()+=-[]\\\â??;,./{}|\":?~_";
   for (var i = 0; i < isnpadtasperguidcomm.length; i++) {
   if (iChars.indexOf(isnpadtasperguidcomm.charAt(i)) != -1) {
   alert("special(,!@$%^&*) characters are not allowed.");
   document.getElementById('isnpadtasperguidcomm').focus();


    }
   }
 }
}





if(document.forms[0].isclmoswrtnpadt[0].checked || document.forms[0].isclmoswrtnpadt[1].checked)
{
	 if((document.forms[0].isclmoswrtnpadtcomm.value==null) || (document.forms[0].isclmoswrtnpadtcomm.value==''))
	 {
		 //alert("no values");
		
		 document.forms[0].isclmoswrtnpadtcomm.value="NA";
	 }
	 else
	 {
		// alert("yes values");

	 var isclmoswrtnpadtcomm=document.forms[0].isclmoswrtnpadtcomm.value;

	  var iChars = "!@$%^&*()+=-[]\\\â??;,./{}|\":?~_";
	   for (var i = 0; i < isclmoswrtnpadtcomm.length; i++) {
	   if (iChars.indexOf(isclmoswrtnpadtcomm.charAt(i)) != -1) {
	   alert("special(,!@$%^&*) characters are not allowed.");
	   document.getElementById('isclmoswrtnpadtcomm').focus();


	    }
	   }
	 }
	}




if(document.forms[0].whetseriousdeficinvol[0].checked || document.forms[0].whetseriousdeficinvol[1].checked)
{
	 if((document.forms[0].whetseriousdeficinvolcomm.value==null) || (document.forms[0].whetseriousdeficinvolcomm.value==''))
	 {
		 //alert("no values");
		
		 document.forms[0].whetseriousdeficinvolcomm.value="NA";
	 }
	 else
	 {
		// alert("yes values");

	 var whetseriousdeficinvolcomm=document.forms[0].whetseriousdeficinvolcomm.value;

	  var iChars = "!@$%^&*()+=-[]\\\â??;,./{}|\":?~_";
	   for (var i = 0; i < whetseriousdeficinvolcomm.length; i++) {
	   if (iChars.indexOf(whetseriousdeficinvolcomm.charAt(i)) != -1) {
	   alert("special(,!@$%^&*) characters are not allowed.");
	   document.getElementById('whetseriousdeficinvolcomm').focus();


	    }
	   }
	 }
	}



if(document.forms[0].whetmajordeficinvolvd[0].checked || document.forms[0].whetmajordeficinvolvd[1].checked)
{
	if((document.forms[0].whetmajordeficinvolvdcomm.value==null) || (document.forms[0].whetmajordeficinvolvdcomm.value==''))
	 {
		 //alert("no values");
		
		 document.forms[0].whetmajordeficinvolvdcomm.value="NA";
	 }
	 else
	 {
		// alert("yes values");

	 var whetmajordeficinvolvdcomm=document.forms[0].whetmajordeficinvolvdcomm.value;

	  var iChars = "!@$%^&*()+=-[]\\\â??;,./{}|\":?~_";
	   for (var i = 0; i < whetmajordeficinvolvdcomm.length; i++) {
	   if (iChars.indexOf(whetmajordeficinvolvdcomm.charAt(i)) != -1) {
	   alert("special(,!@$%^&*) characters are not allowed.");
	   document.getElementById('whetmajordeficinvolvdcomm').focus();


	    }
	   }
	 }
	}



if(document.forms[0].whetdeficinvolbystaff[1].checked)
{

 if((document.forms[0].whetdeficinvolbystaffcomm.value==null) || (document.forms[0].whetdeficinvolbystaffcomm.value==''))
 {
	 //alert("no values");
	
	 document.forms[0].whetdeficinvolbystaffcomm.value="NA";
 }
 else
 {
	// alert("yes values");

 var whetdeficinvolbystaffcomm=document.forms[0].whetdeficinvolbystaffcomm.value;

  var iChars = "!@$%^&*()+=-[]\\\â??;,./{}|\":?~_";
   for (var i = 0; i < whetdeficinvolbystaffcomm.length; i++) {
   if (iChars.indexOf(whetdeficinvolbystaffcomm.charAt(i)) != -1) {
   alert("special(,!@$%^&*) characters are not allowed.");
   document.getElementById('whetdeficinvolbystaffcomm').focus();


}
   }
 }
}

	


if(document.forms[0].isinternratinvestgrad[0].checked)
{

 if((document.forms[0].isinternratinvestgradcomm.value==null) || (document.forms[0].isinternratinvestgradcomm.value==''))
 {
	 //alert("no values");
	
	 document.forms[0].isinternratinvestgradcomm.value="NA";
 }
 else
 {
	// alert("yes values");

 var isinternratinvestgradcomm=document.forms[0].isinternratinvestgradcomm.value;

  var iChars = "!@$%^&*()+=-[]\\\â??;,./{}|\":?~_";
   for (var i = 0; i < isinternratinvestgradcomm.length; i++) {
   if (iChars.indexOf(isinternratinvestgradcomm.charAt(i)) != -1) {
   alert("special(,!@$%^&*) characters are not allowed.");
   document.getElementById('isinternratinvestgradcomm').focus();


}
   }
 }
}
 



if(document.forms[0].isallrecinclmform[1].checked || document.forms[0].isallrecinclmform[0].checked)
{
	 if((document.forms[0].isallrecinclmformcomm.value==null) || (document.forms[0].isallrecinclmformcomm.value==''))
	 {
		 //alert("no values");
		
		 document.forms[0].isallrecinclmformcomm.value="NA";
	 }
	 else
	 {
		// alert("yes values");

	 var isallrecinclmformcomm=document.forms[0].isallrecinclmformcomm.value;

	  var iChars = "!@$%^&*()+=-[]\\\â??;,./{}|\":?~_";
	   for (var i = 0; i < isallrecinclmformcomm.length; i++) {
	   if (iChars.indexOf(isallrecinclmformcomm.charAt(i)) != -1) {
	   alert("special(,!@$%^&*) characters are not allowed.");
	   document.getElementById('isallrecinclmformcomm').focus();


	}
	   }
	 }
	}

//alert("gfgfgfgf");
document.forms[0].action=action;
document.forms[0].target="_self";
document.forms[0].method="POST";
//alert("gfgfgfgf1");
document.forms[0].submit();

//alert("gfgfgfgfend");
}


function functest(id)
{

	if(document.forms[0].iseligact[0].checked && (id=='a'))
	{
		document.forms[0].iseligactcomm.value="";
		document.forms[0].iseligactcomm.disabled = false;
		return true;
	}

	if(document.forms[0].iseligact[1].checked && (id=='b'))
	{
	
		
		document.forms[0].iseligactcomm.disabled = false;
  
     return true;
	}
	
    if(document.forms[0].whetcibildone[0].checked && (id=='c'))
	{
		document.forms[0].whetcibildonecomm.value="";
		document.forms[0].whetcibildonecomm.disabled = false;
		return true;
	}

	if(document.forms[0].whetcibildone[1].checked && (id=='d'))
	{
	
   document.forms[0].whetcibildonecomm.disabled = false;
   
     return true;
	}
	

	 if(document.forms[0].isrataspercgs[0].checked && (id=='e'))
		{
			document.forms[0].isrataspercgscomm.value="";
			document.forms[0].isrataspercgscomm.disabled = false;
			return true;
		}

		if(document.forms[0].isrataspercgs[1].checked && (id=='f'))
		{
		
			
		document.forms[0].isrataspercgscomm.disabled = false;
	   
	     return true;
		}


		 if(document.forms[0].isthirdcollattaken[0].checked && (id=='g'))
			{
				
				document.forms[0].isthirdcollattakencomm.disabled = false;
				return true;
			}

			if(document.forms[0].isthirdcollattaken[1].checked && (id=='h'))
			{
			
			document.forms[0].isthirdcollattakencomm.value="";
			document.forms[0].isthirdcollattakencomm.disabled = false;
		  
		     return true;
			}



			 if(document.forms[0].isnpadtasperguid[0].checked && (id=='i'))
				{
					document.forms[0].isnpadtasperguidcomm.value="";
					document.forms[0].isnpadtasperguidcomm.disabled = false;
					return true;
				}

				if(document.forms[0].isnpadtasperguid[1].checked && (id=='j'))
				{
				
					
				document.forms[0].isnpadtasperguidcomm.disabled = false;
			  
			     return true;
				}


				 if(document.forms[0].isclmoswrtnpadt[0].checked && (id=='k'))
					{
						document.forms[0].isclmoswrtnpadtcomm.value="";
						document.forms[0].isclmoswrtnpadtcomm.disabled = false;
						return true;
					}

					if(document.forms[0].isclmoswrtnpadt[1].checked && (id=='l'))
					{
					
						
					document.forms[0].isclmoswrtnpadtcomm.disabled = false;
				   
				     return true;
					}



					 if(document.forms[0].whetseriousdeficinvol[0].checked && (id=='m'))
						{
							
							document.forms[0].whetseriousdeficinvolcomm.disabled = false;
							return false;
						}

						if(document.forms[0].whetseriousdeficinvol[1].checked && (id=='n'))
						{
						
						document.forms[0].whetseriousdeficinvolcomm.value="";
						document.forms[0].whetseriousdeficinvolcomm.disabled = false;
					   
					     return true;
						}

						 if(document.forms[0].whetmajordeficinvolvd[0].checked && (id=='o'))
							{
								
								document.forms[0].whetmajordeficinvolvdcomm.disabled = false;
								return true;
							}

							if(document.forms[0].whetmajordeficinvolvd[1].checked && (id=='p'))
							{
							
							document.forms[0].whetmajordeficinvolvdcomm.value="";	
							document.forms[0].whetmajordeficinvolvdcomm.disabled = false;
						  
						     return true;
							}

							 if(document.forms[0].whetdeficinvolbystaff[0].checked && (id=='q'))
								{
									
									document.forms[0].whetdeficinvolbystaffcomm.disabled = false;
									return true;
								}

								if(document.forms[0].whetdeficinvolbystaff[1].checked && (id=='r'))
								{
								
								document.forms[0].whetdeficinvolbystaffcomm.value="";
								document.forms[0].whetdeficinvolbystaffcomm.disabled = false;
							   
							     return true;
								}


								 if(document.forms[0].isinternratinvestgrad[0].checked && (id=='s'))
									{
										
										document.forms[0].isinternratinvestgradcomm.disabled = false;
										return true;
									}

									if(document.forms[0].isinternratinvestgrad[1].checked && (id=='t'))
									{
									
									document.forms[0].isinternratinvestgradcomm.value="";
									document.forms[0].isinternratinvestgradcomm.disabled = false;
								  
								     return true;
									}


									 if(document.forms[0].isallrecinclmform[0].checked && (id=='u'))
										{
											document.forms[0].isallrecinclmformcomm.value="";
											document.forms[0].isallrecinclmformcomm.disabled = false;
											return true;
										}

										if(document.forms[0].isallrecinclmform[1].checked && (id=='v'))
										{
										
										document.forms[0].isallrecinclmformcomm.disabled = false;
									 
									     return true;
										}

		
}
	</script>
	
<% session.setAttribute("CurrentPage","updateFirstClaimsPageDetails.do?method=addFirstClaimsPageDetails");%>
<% SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>

<html>
<head>
<LINK href="<%=request.getContextPath()%>/css/StyleSheet.css" rel="stylesheet" type="text/css">
</head>
<body>
<script type="text/javascript" ></script>

<%

 Date systemDate  = new Date();
 String sysDate = dateFormat.format(systemDate);
 ClaimActionForm claimForm = (ClaimActionForm)session.getAttribute("cpTcDetailsForm") ;

 //System.out.println("cheklist"+claimForm.getClaimapplication().getIseligact());
claimForm.setClaimSubmittedDate(sysDate);
%>
<%
			
String thiskey2="CL12MGMGMMG";
String reasData = "reasonData("+thiskey2+")";
%>
<html:form action="updateClaimApplication.do?method=updateClaimApplication" method="POST">
<html:errors/>
<html:hidden name="cpTcDetailsForm" property="test"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
rajukkkkkk

    <tr>
   <td class="FontStyle">&nbsp;</td>
    </tr>
  </table>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr> 
    <td width="20" align="right" valign="bottom"><img src="images/TableLeftTop.gif" width="20" height="31"></td>
      <td width="248" background="images/TableBackground1.gif"><img src="images/ClaimsProcessingHeading.gif" width="131" height="25"></td>
    <td align="right" valign="top" background="images/TableBackground1.gif"> 
      <div align="right"></div></td>
    <td width="23" align="left" valign="bottom"><img src="images/TableRightTop.gif" width="23" height="31"></td>
  </tr>
  <tr> 
    <td width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</td>
    <td colspan="2">
      <DIV align="right">			
      		<A HREF="javascript:submitForm('helpDisclaimer.do')">
      	        HELP</A>
      </DIV>        
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="100%" border="0" cellspacing="1" cellpadding="0">
                <tr> 
                  <td colspan="8"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr> 
                        <td width="35%" height="20" class="Heading">&nbsp;<bean:message key="declarationandundertakingbymli"/></td>
                        <td align="left" valign="bottom"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
                        <td>&nbsp;</td>
                      </tr>                      
                      <tr> 
                        <td colspan="4" class="Heading"><img src="images/Clear.gif" width="5" height="5"></td>
                      </tr>
                    </table></td>
                </tr>                                
                <tr> 
                  <td colspan="4" class="SubHeading"><br> &nbsp;<bean:message key="declaration"/></td>
                </tr>
                <tr> 
                  <td class="TableData" >&nbsp; <bean:message key="para1"/></td>
				 
                </tr>
                <tr> 
                  <td class="TableData" >&nbsp;<bean:message key="para2"/></td>
				  
                </tr>
                <tr> 
                  <td colspan="4" class="SubHeading"><br> &nbsp;<bean:message key="undertaking"/></td>
                </tr>
				<tr>
				<td class="TableData"> &nbsp;<bean:message key="undertaking1"/></td>
                </tr> 
				<tr>
				<td class="TableData"> &nbsp;<bean:message key="undertaking2"/></td>
                </tr> 
				<tr>
				<td class="TableData"> &nbsp;<bean:message key="undertaking3"/></td>
                </tr> 
				<tr>
				<td class="TableData"> &nbsp;<bean:message key="undertaking4"/></td>
                </tr> 
				</table></td>
			</tr>
			<tr> 
                  <td colspan="4"><img src="images/Clear.gif" width="5" height="15"></td>
                </tr>
				<tr> 
                  <td colspan="4"><table width="100%" border="0" cellspacing="1">
					<tr>
					<td class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="nameofofficial"/></td>
					<td class="TableData"><html:text property="nameOfOffi" name="cpTcDetailsForm" maxlength="100"/></td>
					 
					</tr>
					<tr>
					<td class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="designationOfOfficial"/></td>
					<td class="TableData"><html:text property="designa" name="cpTcDetailsForm" maxlength="50"/></td>
					</tr>
					<tr>
					<td class="ColumnBackground">&nbsp;<bean:message key="mliName"/></td>
					<td class="TableData"><bean:write property="memberDetails.memberBankName" name="cpTcDetailsForm"/>, <bean:write property="memberDetails.memberBranchName" name="cpTcDetailsForm"/></td>
					</tr>
					<tr>
					<td class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="dateofclaimfiling"/></td>
					<td class="TableData" align="center"><!--<html:text property="claimSubmittedDate" name="cpTcDetailsForm" maxlength="10"/><img src="images/CalendarIcon.gif" width="20" onClick="showCalendar('cpTcDetailsForm.claimSubmittedDate')" align="center">--><bean:write property="claimSubmittedDate" name="cpTcDetailsForm"/></td>
					</tr>
					<tr>
					<td class="ColumnBackground">&nbsp;<bean:message key="place"/></td>
					<td class="TableData"><html:text property="places"  name="cpTcDetailsForm" maxlength="100"/></td>
					</tr>
						<tr align="center">
						<TD class="SubHeading" align="left">CheckList to be submitted by mli alongwith claim lodgement</TD>
						</TR>
                        
                        <tr>
                      <td colspan="4">
                       <table width="100%" border="0" cellspacing="1" cellpadding="0">
                        <tr class="ColumnBackground">
                       <td>
						<div align="center">S.No.</div>
						</td>
						 <td class="ColumnBackground">
						<div align="center">Description</div>
						</td>
						 <td class="ColumnBackground">
						<div align="center">Yes/No</div>
						</td>
						 <td class="ColumnBackground">
						<div align="center">Comments</div>
						</td>
                      							
						</TR>
				
                      <TR>
                      <TD align="left" class="ColumnBackground" >1.</TD>
					 <TD align="left" valign="top" class="ColumnBackground">&nbsp;Activity is eligible as per Credit Guarantee Scheme(CGS)</TD>
					 <td width="10%" align="left" valign="top" class="ColumnBackground">
					 
					  
					 <html:radio name="cpTcDetailsForm" value="Y"  property="iseligact"   onclick="functest('a')"  ><bean:message key="yes"/></html:radio>
					<html:radio name="cpTcDetailsForm"  value="N" property="iseligact"   onclick="functest('b')"   ><bean:message key="no"/></html:radio>
					</td>
					 <td class="TableData"><html:text property="iseligactcomm"  size="60" alt="iseligactcomm" name="cpTcDetailsForm" maxlength="60" /></td>
					      
					</TR>
					<TR>
						<TD align="left" class="ColumnBackground" >2.</TD>
							<TD align="left" valign="top" class="ColumnBackground">&nbsp;Whether CIBIL done/CIR/KYC obtained and findings are satisfactory. </TD>
							 <td width="10%" align="left" valign="top" class="ColumnBackground">
					 <html:radio name="cpTcDetailsForm" property="whetcibildone" value="Y"  onclick="functest('c')" ><bean:message key="yes"/></html:radio>
					<html:radio name="cpTcDetailsForm" property="whetcibildone" value="N" onclick="functest('d')" ><bean:message key="no"/></html:radio>
					</td>
					    
					   <td class="TableData"><html:text property="whetcibildonecomm"  size="60" alt="iseligactcomm" name="cpTcDetailsForm" maxlength="60" /></td>
						</TR>
						
						<TR>
						<TD align="left" class="ColumnBackground" >3.</TD>
					   <TD align="left" valign="top" class="ColumnBackground">&nbsp;Rate charged on loan is as per CGS guidelines. </TD>
					    <td width="10%" align="left" valign="top" class="ColumnBackground">
					 <html:radio name="cpTcDetailsForm" property="isrataspercgs" value="Y" onclick="functest('e')"  ><bean:message key="yes"/></html:radio>
					<html:radio name="cpTcDetailsForm" property="isrataspercgs" value="N" onclick="functest('f')"  ><bean:message key="no"/></html:radio>
					</td>
						 
					     <td class="TableData"><html:text property="isrataspercgscomm"  size="60" alt="isrataspercgscomm" name="cpTcDetailsForm" maxlength="60" /></td>
						</TR>
						<TR>
						<TD align="left" class="ColumnBackground" >4.</TD>
							<TD align="left" valign="top" class="ColumnBackground">&nbsp;Third party gaurantee/collateral security stipulated. </TD>
						<td width="10%" align="left" valign="top" class="ColumnBackground">
					 <html:radio name="cpTcDetailsForm" property="isthirdcollattaken" value="Y" onclick="functest('g')"  ><bean:message key="yes"/></html:radio>
					<html:radio name="cpTcDetailsForm" property="isthirdcollattaken" value="N" onclick="functest('h')" ><bean:message key="no"/></html:radio>
					</td>
							  
					     <td class="TableData"><html:text property="isthirdcollattakencomm"  size="60" alt="isthirdcollattakencomm" name="cpTcDetailsForm" maxlength="60" /></td>
						</TR>
						<TR>
						<TD align="left" class="ColumnBackground" >5.</TD>
                      <TD align="left" valign="top" class="ColumnBackground">&nbsp;Date of NPA as fed in the system is as per RBI guidelines.</TD>
                      <td width="10%" align="left" valign="top" class="ColumnBackground">
					 <html:radio name="cpTcDetailsForm" property="isnpadtasperguid" value="Y" onclick="functest('i')" ><bean:message key="yes"/></html:radio>
					<html:radio name="cpTcDetailsForm" property="isnpadtasperguid" value="N" onclick="functest('j')" ><bean:message key="no"/></html:radio>
					</td>
							  
					     <td class="TableData"><html:text property="isnpadtasperguidcomm"  size="60" alt="isnpadtasperguidcomm" name="cpTcDetailsForm" maxlength="60" /></td>
						</TR>
						<TR>
						<TD align="left" class="ColumnBackground" >6.</TD>
							<TD align="left" valign="top" class="ColumnBackground">&nbsp;Whether outstanding amount mentioned in the claim application form is with respect to the NPA date as reported in claim form. </TD>
							 <td width="10%" align="left" valign="top" class="ColumnBackground">
					 <html:radio name="cpTcDetailsForm" property="isclmoswrtnpadt" value="Y" onclick="functest('k')" ><bean:message key="yes"/></html:radio>
					<html:radio name="cpTcDetailsForm" property="isclmoswrtnpadt" value="N" onclick="functest('l')" ><bean:message key="no"/></html:radio>
					</td>
							  
					     <td class="TableData"><html:text property="isclmoswrtnpadtcomm"  size="60" alt="isclmoswrtnpadtcomm" name="cpTcDetailsForm" maxlength="60" /></td>
						</TR>
						<TR>
						<TD align="left" class="ColumnBackground" >7.</TD>
							<TD align="left" valign="top" class="ColumnBackground">&nbsp;Whether serious deficiencies have been observed in the matter of appraisal/renewal/disbursement/followup/conduct of the account. </TD>
							<td width="10%" align="left" valign="top" class="ColumnBackground">
					 <html:radio name="cpTcDetailsForm" property="whetseriousdeficinvol" value="Y" onclick="functest('m')" ><bean:message key="yes"/></html:radio>
					<html:radio name="cpTcDetailsForm" property="whetseriousdeficinvol" value="N" onclick="functest('n')" ><bean:message key="no"/></html:radio>
					</td>
							  
					     <td class="TableData"><html:text property="whetseriousdeficinvolcomm"  size="60" alt="whetseriousdeficinvolcomm" name="cpTcDetailsForm" maxlength="60" /></td>
						</TR>
						<TR><TD align="left" class="ColumnBackground" >8.</TD>
							<TD align="left" valign="top" class="ColumnBackground">&nbsp;Major deficiencies observed in Pre sanction/Post disbursement inspections </TD>
							<td width="10%" align="left" valign="top" class="ColumnBackground">
					 <html:radio name="cpTcDetailsForm" property="whetmajordeficinvolvd" value="Y" onclick="functest('o')" ><bean:message key="yes"/></html:radio>
					<html:radio name="cpTcDetailsForm" property="whetmajordeficinvolvd" value="N" onclick="functest('p')" ><bean:message key="no"/></html:radio>
					</td>
							 
					     <td class="TableData"><html:text property="whetmajordeficinvolvdcomm"  size="60" alt="whetmajordeficinvolvdcomm" name="cpTcDetailsForm" maxlength="60" /></td>
						</TR>
						<TR>
						<TD align="left" class="ColumnBackground" >9.</TD>
							<TD align="left" valign="top" class="ColumnBackground">&nbsp;Whether deficiencies observed on the part of internal staff as per the Staff Accountability exercise carried out. </TD>
						<td width="10%" align="left" valign="top" class="ColumnBackground">
					 <html:radio name="cpTcDetailsForm" property="whetdeficinvolbystaff" value="Y" onclick="functest('q')"  ><bean:message key="yes"/></html:radio>
					<html:radio name="cpTcDetailsForm" property="whetdeficinvolbystaff" value="N" onclick="functest('r')" ><bean:message key="no"/></html:radio>
					</td>
							  
					     <td class="TableData"><html:text property="whetdeficinvolbystaffcomm"  size="60" alt="whetdeficinvolbystaffcomm" name="cpTcDetailsForm" maxlength="60" /></td>
						</TR>
						<TR>
						<TD align="left" class="ColumnBackground" >10.</TD>
                      <TD align="left" valign="top" class="ColumnBackground">&nbsp;Internal rating was carried out and the proposal was found of Investment Grade.(applicable for loans sanctioned above 50 lakh) </TD>
                     <td width="10%" align="left" valign="top" class="ColumnBackground">
					 <html:radio name="cpTcDetailsForm" property="isinternratinvestgrad" value="Y" onclick="functest('s')" ><bean:message key="yes"/></html:radio>
					<html:radio name="cpTcDetailsForm" property="isinternratinvestgrad" value="N" onclick="functest('t')" ><bean:message key="no"/></html:radio>
					</td>
							  
					     <td class="TableData"><html:text property="isinternratinvestgradcomm"  size="60" alt="isinternratinvestgradcomm" name="cpTcDetailsForm" maxlength="60" /></td>
						</TR>
						<TR>
						<TD align="left" class="ColumnBackground" >11.</TD>
							<TD align="left" valign="top" class="ColumnBackground">&nbsp;Whether all the recoveries pertaining to the account after the date of NPA and before the claim lodgement have been duly incorporated in the claim form. </TD>
							<td width="10%" align="left" valign="top" class="ColumnBackground">
					 <html:radio name="cpTcDetailsForm" property="isallrecinclmform" value="Y" onclick="functest('u')" ><bean:message key="yes"/></html:radio>
					<html:radio name="cpTcDetailsForm" property="isallrecinclmform" value="N" onclick="functest('v')" ><bean:message key="no"/></html:radio>
					</td>
						 
					     <td class="TableData"><html:text property="isallrecinclmformcomm"  size="60" alt="isallrecinclmformcomm" name="cpTcDetailsForm" maxlength="60" /></td>
						</TR>
					
					
					</table>
				<tr>
				<td class="SubHeading"> &nbsp;<bean:message key="cgtsinote1"/></td>
                </tr> 
				<tr>
				<td class="SubHeading"> &nbsp;<bean:message key="cgtsinote2"/></td>
                </tr> 
              </table>
            </td>
        </tr>
      </table></td>
    <td width="23" background="images/TableVerticalRightBG.gif">&nbsp;</td>
  </tr>
  <tr> 
      <td width="20" align="right" valign="bottom"><img src="images/TableLeftBottom.gif" width="20" height="51"></td>
      <td colspan="2" valign="bottom" background="images/TableBackground3.gif"> 
        <div>
          <div align="center">          
          <A href="javascript:submitDeclaResubmit('updateClaimApplicationforResub.do?method=updateClaimApplicationforResub')"><img src="images/Save.gif" alt="Accept" width="49" height="37" border="0"></a>
          <a href="javascript:document.form1.reset()"><img src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></a>
          <a href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>"><img src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></a><div>
      </div></td>
      <td width="23" align="right" valign="bottom"><img src="images/TableRightBottom.gif" width="23" height="51"></td>
  </tr>
</table>
</html:form>
</body>
</html>
