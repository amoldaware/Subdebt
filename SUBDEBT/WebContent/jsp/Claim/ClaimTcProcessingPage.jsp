<%@ page language="java"%>
<%@ page import="com.cgtsi.claim.ClaimConstants"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@page import="java.util.Date"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
 <% session.setAttribute("CurrentPage","displayClaimApproval.do?method=displayClaimApproval");%>
 
 
<%-- <% session.setAttribute("CurrentPage","saveTcClaimProcessDetails.do?method=saveTcClaimProcessDetailsNew");%> --%>
<%
String k1 = ClaimConstants.CLM_APPROVAL_STATUS;
String k2 = ClaimConstants.CLM_REJECT_STATUS;
String k3 = ClaimConstants.CLM_HOLD_STATUS;
String k4 = ClaimConstants.CLM_FORWARD_STATUS;
String k5 = ClaimConstants.CLM_TEMPORARY_CLOSE;
String k6 = ClaimConstants.CLM_TEMPORARY_REJECT;

int i=1;
int j=1;
int k=1;
int l=1;
int m=1;
int n=1;

String q1 = "N";
String q2 = "N";
String q3 = "N";
String q4 = "N";
String q5 = "N";
String q6 = "N";
String q7 = "N";
String q8 = "N";
String q9 = "N";
String q10 = "N";
String q11 = "N";
String q12 = "N";
String q13 = "N";
String q14 = "N";

String ltrRefNo = "";
String ltrDate = "";


String claimRefSet = null;


String k1property = "claimSummaries.".trim() + k1;
String k2property = "claimSummaries.".trim() + k2;
String k3property = "claimSummaries.".trim() + k3;
String k4property = "claimSummaries.".trim() + k4;
String k5property = "claimSummaries.".trim() + k5;
String k6property = "claimSummaries.".trim() + k6;



String fclaimrefnumber = "";
String fcgclan = "";
String sclaimrefnumber = "";
String scgclan = "";
%>
<html:errors />
<html:form action="saveClaimProcessDetails.do?method=saveClaimProcessDetails" method="POST" enctype="multipart/form-data">
<table width="100%" border="0" cellspacing="0" cellpadding="0">

<tr>
<td class="FontStyle">&nbsp;</td>
</tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
<td align="right" valign="bottom"><img src="images/TableLeftTop.gif" width="20" height="31"></td>
<td background="images/TableBackground1.gif"><img src="images/ClaimsProcessingHeading.gif" width="131" height="25"></td>
<td width="20" align="left" valign="bottom"><img src="images/TableRightTop.gif" width="23" height="31"></td>
</tr>
<tr>
<td background="images/TableVerticalLeftBG.gif">&nbsp;</td>
<td><table width="100%" border="0" cellspacing="1" cellpadding="0">
  <tr>
    <td colspan="4"><table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
	  <td width="35%" class="Heading">&nbsp;Approval</td>
	  <td colspan="3"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
	</tr>
	<tr>
	  <td colspan="4" class="Heading"><img src="images/Clear.gif" width="5" height="5"></td>
	</tr>
      </table></td>
  </tr>
  <logic:notEqual property="apprvdVectorSize" value="0" name="cpTcDetailsForm">
  <tr>
    <td colspan="4" class="SubHeading"><br> &nbsp;<bean:message key="apprvdClaims"/></td>
  </tr>
  <tr>
    <td colspan="4"><table width="100%" border="0" cellspacing="1" cellpadding="0">
			<tr align="center" valign="middle" class="HeadingBg">
	  <td>&nbsp;<strong>Sr.No.</strong><br> &nbsp; </td>
	  <td colspan="2">
	  <div align="center">&nbsp;<bean:message key="firstclaim"/>
	  </div></td>
	  <td colspan="3">
	  <div align="center">&nbsp;<bean:message key="secondclaim"/>
	  </div></td>
	</tr>
	<tr class="TableData">
	  <td>&nbsp;</td>
	  <td><div align="center"><bean:message key="clmrefnumber"/></div></td>

	  <td><div align="center">&nbsp;<bean:message key="cgclannumber"/></div></td>
	  <td><div align="center"><bean:message key="clmrefnumber"/></div></td>

	  <td><div align="center"><strong>CGCLAN<br>
	      Number </strong></div></td>
	</tr>
        <logic:iterate property="<%=k1property%>" name="cpTcDetailsForm" id="object">
        <%
        java.util.HashMap hashmp = (java.util.HashMap)object;
        if((hashmp.get(ClaimConstants.CLM_FLAG)).equals(ClaimConstants.CLM_APPROVAL_STATUS + ClaimConstants.FIRST_INSTALLMENT))     
        {
           fclaimrefnumber = (String)hashmp.get(ClaimConstants.CLM_CLAIM_REF_NUMBER);           
           fcgclan = (String)hashmp.get(ClaimConstants.CLM_CGCLAN);
        }
        else if((hashmp.get(ClaimConstants.CLM_FLAG)).equals(ClaimConstants.CLM_APPROVAL_STATUS + ClaimConstants.SECOND_INSTALLMENT))     
	{
	   sclaimrefnumber = (String)hashmp.get(ClaimConstants.CLM_CLAIM_REF_NUMBER);
	   scgclan = (String)hashmp.get(ClaimConstants.CLM_CGCLAN);
        }
        %>
	<tr class="TableData">
	  <td><div align="center">&nbsp;<%=i%></div></td>

	  <td><div align="center">&nbsp;<%=fclaimrefnumber%></div></td>

	  <td><div align="center">&nbsp;<%=fcgclan%></div></td>
	  <td><div align="center">&nbsp;<%=sclaimrefnumber%></div></td>

	  <td><div align="center">&nbsp;<%=scgclan%></div></td>
	</tr>
	<%
	i++;
	fclaimrefnumber="";
	fcgclan="";
	sclaimrefnumber="";
	scgclan="";
	%>
        </logic:iterate>
      </table></td>
  </tr>
  </logic:notEqual>
  <logic:notEqual property="rejectdVectorSize" value="0" name="cpTcDetailsForm">
  <tr>
    <td colspan="4" class="SubHeading"><br> &nbsp;<bean:message key="rejectedClaims"/></td>
  </tr>
  <tr>
    <td colspan="4"><table width="100%" border="0" cellspacing="1" cellpadding="0">
	<tr class="HeadingBg">
	  <td><strong>Sr.No.</strong></td>
	  <td> <div align="center">&nbsp;<bean:message key="firstclaim"/></div></td>
	  <td> <div align="center">&nbsp;<bean:message key="secondclaim"/></div></td>
	</tr>
	<logic:iterate property="<%=k2property%>" name="cpTcDetailsForm" id="object">
	<%
	java.util.HashMap hashmp = (java.util.HashMap)object;
	if((hashmp.get(ClaimConstants.CLM_FLAG)).equals(ClaimConstants.CLM_REJECT_STATUS + ClaimConstants.FIRST_INSTALLMENT))     
	{
	   fclaimrefnumber = (String)hashmp.get(ClaimConstants.CLM_CLAIM_REF_NUMBER);
	}
	else if((hashmp.get(ClaimConstants.CLM_FLAG)).equals(ClaimConstants.CLM_REJECT_STATUS + ClaimConstants.SECOND_INSTALLMENT))     
	{
	   sclaimrefnumber = (String)hashmp.get(ClaimConstants.CLM_CLAIM_REF_NUMBER);
	}
	%>
	<tr class="TableData">
	  <td><div align="center"><%=j%></div></td>
	  <td><div align="center">&nbsp;<%=fclaimrefnumber%></div></td>
	  <td><div align="center">&nbsp;<%=sclaimrefnumber%></div></td>
	</tr>
	<%
	j++;
	fclaimrefnumber="";
	sclaimrefnumber="";
	%>
	</logic:iterate>
      </table></td>
  </tr>
  </logic:notEqual>
  <logic:notEqual property="heldVectorSize" value="0" name="cpTcDetailsForm">
  <tr>
    <td colspan="4" class="SubHeading"><br>
      &nbsp;<bean:message key="holdClaims"/></td>
  </tr>
  <tr>
    <td colspan="4" class="SubHeading"><table width="100%" border="0" cellspacing="1" cellpadding="0">
	<tr class="HeadingBg">
	  <td><strong>Sr.No.</strong></td>
	  <td> <div align="center">&nbsp;<bean:message key="firstclaim"/></div></td>
	  <td> <div align="center">&nbsp;<bean:message key="secondclaim"/></div></td>
	</tr>
	<logic:iterate property="<%=k3property%>" name="cpTcDetailsForm" id="object">
	<%
	java.util.HashMap hashmp = (java.util.HashMap)object;
	if((hashmp.get(ClaimConstants.CLM_FLAG)).equals(ClaimConstants.CLM_HOLD_STATUS + ClaimConstants.FIRST_INSTALLMENT))     
	{
	   fclaimrefnumber = (String)hashmp.get(ClaimConstants.CLM_CLAIM_REF_NUMBER);
	}
	else if((hashmp.get(ClaimConstants.CLM_FLAG)).equals(ClaimConstants.CLM_HOLD_STATUS + ClaimConstants.SECOND_INSTALLMENT))     
	{
	   sclaimrefnumber = (String)hashmp.get(ClaimConstants.CLM_CLAIM_REF_NUMBER);
	}
	%>
	<tr class="TableData">
	  <td><div align="center">&nbsp;<%=k%></div></td>
	  <td><div align="center">&nbsp;<%=fclaimrefnumber%></div></td>
	  <td><div align="center">&nbsp;<%=sclaimrefnumber%></div></td>
	</tr>
	<%
	k++;
	fclaimrefnumber="";
	sclaimrefnumber="";
	%>
	</logic:iterate>
      </table></td>
  </tr>
  </logic:notEqual>
  <logic:notEqual property="forwardedVectorSize" value="0" name="cpTcDetailsForm">
  <tr>
    <td colspan="4" class="SubHeading"><br>
      &nbsp;<bean:message key="forwardedClaims"/></td>
  </tr>
  <tr>
    <td colspan="4"><table width="100%" border="0" cellspacing="1" cellpadding="0">
	<tr class="HeadingBg">
	  <td><strong>Sr.No.</strong></td>
	  <td> <div align="center">&nbsp;<bean:message key="firstclaim"/></div></td>
	  <td> <div align="center">&nbsp;<bean:message key="secondclaim"/></td>
	</tr>
	<logic:iterate property="<%=k4property%>" name="cpTcDetailsForm" id="object">
	<%
	java.util.HashMap hashmp = (java.util.HashMap)object;
	if((hashmp.get(ClaimConstants.CLM_FLAG)).equals(ClaimConstants.CLM_FORWARD_STATUS + ClaimConstants.FIRST_INSTALLMENT))     
	{
	   fclaimrefnumber = (String)hashmp.get(ClaimConstants.CLM_CLAIM_REF_NUMBER);
	}
	else if((hashmp.get(ClaimConstants.CLM_FLAG)).equals(ClaimConstants.CLM_FORWARD_STATUS + ClaimConstants.SECOND_INSTALLMENT))     
	{
	   sclaimrefnumber = (String)hashmp.get(ClaimConstants.CLM_CLAIM_REF_NUMBER);
	}
	%>	
	<tr class="TableData">
	  <td><div align="center">&nbsp;<%=l%></div></td>
	  <td><div align="center">&nbsp;<%=fclaimrefnumber%></div></td>
	  <td><div align="center">&nbsp;<%=sclaimrefnumber%></div></td>
	</tr>
	<%
	l++;
	fclaimrefnumber="";
	sclaimrefnumber="";
	%>
	</logic:iterate>
      </table></td>
  </tr>
  </logic:notEqual>
  <tr><td>
 <table width="100%" border="0" cellspacing="1" cellpadding="0">
 <tr>
				<td class="TableData"> &nbsp;<font color="red">Q1 :</font>&nbsp;<bean:message key="question1"/></td>
 </tr> 
				<tr>
				<td class="TableData"> &nbsp;<font color="red">Q2:</font>&nbsp;<bean:message key="question2"/></td>
        </tr> 
				<tr>
				<td class="TableData"> &nbsp;<font color="red">Q3:</font>&nbsp;<bean:message key="question3"/></td>
        </tr> 
				<tr>
				<td class="TableData"> &nbsp;<font color="red">Q4:</font>&nbsp;<bean:message key="question4"/></td>
        </tr> 
        <tr>
        <td class="TableData"> &nbsp;<font color="red">Q5:</font>&nbsp;<bean:message key="question5"/></td>
        </tr> 
        <tr>
         <td class="TableData"> &nbsp;<font color="red">Q6:</font>&nbsp;<bean:message key="question6"/></td>
        </tr> 
        <tr>
         <td class="TableData"> &nbsp;<font color="red">Q7:</font>&nbsp;<bean:message key="question7"/></td>
        </tr>
        <tr>
         <td class="TableData"> &nbsp;<font color="red">Q8:</font>&nbsp;<bean:message key="question8"/></td>
        </tr>
        <tr>
         <td class="TableData"> &nbsp;<font color="red">Q9:</font>&nbsp;<bean:message key="question9"/></td>
        </tr>
        <tr>
         <td class="TableData"> &nbsp;<font color="red">Q10:</font>&nbsp;<bean:message key="question10"/></td>
       </tr>
       <tr>
         <td class="TableData"> &nbsp;<font color="red">Q11:</font>&nbsp;<bean:message key="question11"/></td>
       </tr>
       <tr>
         <td class="TableData"> &nbsp;<font color="red">Q12:</font>&nbsp;<bean:message key="question12"/></td>
       </tr>
       <tr>
         <td class="TableData"> &nbsp;<font color="red">Q13:</font>&nbsp;<bean:message key="question13"/></td>
       </tr>
       <tr>
         <td class="TableData"> &nbsp;<font color="red">Q14:</font>&nbsp;<bean:message key="question14"/></td>
       </tr>
 </table>
 </td></tr>
  
   <logic:notEqual property="tempclosedVectorSize" value="0" name="cpTcDetailsForm">
  <tr>
    <td colspan="18" class="SubHeading"><br>
      &nbsp;Temporary Closed Claims</td>
  </tr>
  <tr>
    <td colspan="18"><table width="100%" border="0" cellspacing="1" cellpadding="0">
    
	<tr class="HeadingBg">
	  <td><strong>Sr.No.</strong></td>
	  <td> <div align="center">&nbsp;<bean:message key="firstclaim"/></div></td>
    <td> <div align="center">&nbsp;Q1&nbsp;</div></td>
    <td> <div align="center">&nbsp;Q2&nbsp;</div></td>
    <td> <div align="center">&nbsp;Q3&nbsp;</div></td>
    <td> <div align="center">&nbsp;Q4&nbsp;</div></td>
    <td> <div align="center">&nbsp;Q5&nbsp;</div></td>
    <td> <div align="center">&nbsp;Q6&nbsp;</div></td>
    <td> <div align="center">&nbsp;Q7&nbsp;</div></td> 	
    <td> <div align="center">&nbsp;Q8&nbsp;</div></td> 	
    <td> <div align="center">&nbsp;Q9&nbsp;</div></td> 	
    <td> <div align="center">&nbsp;Q10&nbsp;</div></td> 	
    <td> <div align="center">&nbsp;Q11&nbsp;</div></td> 	
    <td> <div align="center">&nbsp;Q12&nbsp;</div></td> 	
    <td> <div align="center">&nbsp;Q13&nbsp;</div></td> 	
    <td> <div align="center">&nbsp;Q14&nbsp;</div></td> 
    <td> <div align="center">&nbsp;Ltr Ref No&nbsp;</div></td>
    <td> <div align="center">&nbsp;<bean:message key="referenceDate"/></div></td>
    
	</tr>
  
	<logic:iterate property="<%=k5property%>" name="cpTcDetailsForm" id="object">
	<%
	java.util.HashMap hashmp = (java.util.HashMap)object;  
  	if((hashmp.get(ClaimConstants.CLM_FLAG)).equals(ClaimConstants.CLM_TEMPORARY_CLOSE + ClaimConstants.FIRST_INSTALLMENT))     
	{
	   fclaimrefnumber = (String)hashmp.get(ClaimConstants.CLM_CLAIM_REF_NUMBER);
	}
	else if((hashmp.get(ClaimConstants.CLM_FLAG)).equals(ClaimConstants.CLM_TEMPORARY_CLOSE + ClaimConstants.SECOND_INSTALLMENT))     
	{
	   sclaimrefnumber = (String)hashmp.get(ClaimConstants.CLM_CLAIM_REF_NUMBER);
	}
	%>	
 
	<tr class="TableData">
	  <td><div align="center">&nbsp;<%=m%></div></td>
    <% 
        claimRefSet="claimRefSet("+fclaimrefnumber+")";
        q1="q1Flags("+fclaimrefnumber+")";
        q2="q2Flags("+fclaimrefnumber+")";
        q3="q3Flags("+fclaimrefnumber+")";
        q4="q4Flags("+fclaimrefnumber+")";
        q5="q5Flags("+fclaimrefnumber+")";
        q6="q6Flags("+fclaimrefnumber+")";
        q7="q7Flags("+fclaimrefnumber+")";
        q8="q8Flags("+fclaimrefnumber+")";
        q9="q9Flags("+fclaimrefnumber+")";
        q10="q10Flags("+fclaimrefnumber+")";
        q11="q11Flags("+fclaimrefnumber+")";
        q12="q12Flags("+fclaimrefnumber+")";
        q13="q13Flags("+fclaimrefnumber+")";
        q14="q14Flags("+fclaimrefnumber+")";
        ltrRefNo="ltrRefNoSet("+fclaimrefnumber+")";
        ltrDate="ltrDtSet("+fclaimrefnumber+")";
        
      
     %>
     <html:hidden property="<%=claimRefSet%>" alt="claimRefNum" name="cpTcDetailsForm" />
	  <td><div align="center">&nbsp;<%=fclaimrefnumber%></div></td>
	  <td><div align="center">&nbsp;<html:checkbox name="cpTcDetailsForm" value="Y" property="<%=q1%>"></html:checkbox>
              &nbsp;&nbsp;</div></td>
              <td><div align="center">&nbsp;<html:checkbox name="cpTcDetailsForm" value="Y" property="<%=q2%>"></html:checkbox>
              &nbsp;&nbsp;</div></td>
              <td><div align="center">&nbsp;<html:checkbox name="cpTcDetailsForm" value="Y" property="<%=q3%>"></html:checkbox>
              &nbsp;&nbsp;</div></td>
              <td><div align="center">&nbsp;<html:checkbox name="cpTcDetailsForm" value="Y" property="<%=q4%>"></html:checkbox>
              &nbsp;&nbsp;</div></td>
              <td><div align="center">&nbsp;<html:checkbox name="cpTcDetailsForm" value="Y" property="<%=q5%>"></html:checkbox>
              &nbsp;&nbsp;</div></td>
              <td><div align="center">&nbsp;<html:checkbox name="cpTcDetailsForm" value="Y" property="<%=q6%>"></html:checkbox>
              &nbsp;&nbsp;</div></td>
              <td><div align="center">&nbsp;<html:checkbox name="cpTcDetailsForm" value="Y" property="<%=q7%>"></html:checkbox>
              &nbsp;&nbsp;</div></td>
              <td><div align="center">&nbsp;<html:checkbox name="cpTcDetailsForm" value="Y" property="<%=q8%>"></html:checkbox>
              &nbsp;&nbsp;</div></td>
              <td><div align="center">&nbsp;<html:checkbox name="cpTcDetailsForm" value="Y" property="<%=q9%>"></html:checkbox>
              &nbsp;&nbsp;</div></td>
              <td><div align="center">&nbsp;<html:checkbox name="cpTcDetailsForm" value="Y" property="<%=q10%>"></html:checkbox>
              &nbsp;&nbsp;</div></td>
              <td><div align="center">&nbsp;<html:checkbox name="cpTcDetailsForm" value="Y" property="<%=q11%>"></html:checkbox>
              &nbsp;&nbsp;</div></td>      
              <td><div align="center">&nbsp;<html:checkbox name="cpTcDetailsForm" value="Y" property="<%=q12%>"></html:checkbox>
              &nbsp;&nbsp;</div></td>
              <td><div align="center">&nbsp;<html:checkbox name="cpTcDetailsForm" value="Y" property="<%=q13%>"></html:checkbox>
              &nbsp;&nbsp;</div></td>
              <td><div align="center">&nbsp;<html:checkbox name="cpTcDetailsForm" value="Y" property="<%=q14%>"></html:checkbox>
              &nbsp;&nbsp;</div></td>
              <td><div align="center">&nbsp;<html:text property="<%=ltrRefNo%>" name="cpTcDetailsForm" maxlength="200"/>
              &nbsp;&nbsp;</div></td>
          <%--  <td><div align="center">&nbsp;<html:text property="<%=ltrDate%>" size="20" alt="ltrDtSet" name="cpTcDetailsForm" maxlength="10"/><img src="images/CalendarIcon.gif" width="20" align="center"  onClick="showCalendar('cpTcDetailsForm.ltrDtSet(<%=fclaimrefnumber%>)')"> 
              &nbsp;&nbsp;</div></td>  --%>
              <td><div align="center">&nbsp;<html:text property="<%=ltrDate%>" name="cpTcDetailsForm" size="20" alt="ltrDtSet"  maxlength="10"/><%--<img src="images/CalendarIcon.gif" width="20" align="center"
                  onClick="showCalendar('cpTcDetailsForm.ltrDate')"> --%> </div></td>
              
               
              
	</tr>
  
	<%
	m++;
	fclaimrefnumber="";
	sclaimrefnumber="";
	%>
 
	</logic:iterate> 

</table></td>
  </tr>
  </logic:notEqual>
  
  
  <tr align="center" valign="baseline">
    <td colspan="4"> 
            <div align="center">  
            <A href="javascript:submitForm('saveTcClaimProcessDetails.do?method=saveTcClaimProcessDetailsNew')"><img src="images/Save.gif" alt="Save"   width="49" height="37" border="0"></a>
            <a href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>"><img src="images/OK.gif" width="49" height="37" border="0"></a></div></td>
  </tr>
</table></td>
<td background="images/TableVerticalRightBG.gif">&nbsp;</td>
</tr>
<tr>
<td width="20" align="right" valign="top"><img src="images/TableLeftBottom1.gif" width="20" height="15"></td>
<td background="images/TableBackground2.gif"><div align="center"></div></td>
<td align="left" valign="top"><img src="images/TableRightBottom1.gif" width="23" height="15"></td>
</tr>
</table>
</html:form>
</body>
</html>
