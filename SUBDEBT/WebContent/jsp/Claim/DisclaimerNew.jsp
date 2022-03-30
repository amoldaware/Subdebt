<%@ page language="java"%>
<%@ page import = "com.cgtsi.claim.ClaimConstants"%>
<%@ page import = "com.cgtsi.actionform.ClaimActionForm"%>
<%@ page import = "java.util.HashMap"%>
<%@page import ="java.text.SimpleDateFormat"%>
<%@page import ="java.util.Date"%>
<%@ page import="com.cgtsi.actionform.ClaimActionForm"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>

<% session.setAttribute("CurrentPage","addFirstClaimsPageDetails.do?method=addFirstClaimsPageDetails");%>
<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>
<%
String cgpan = "";
String hiddencgpan = "";
String dsbrsmntdt = "";
String principal = "";
String interestCharges = "";
String osAsOnNpa = "";
String osAsStatedinCivilSuit = "";
String osAsOnLodgementOfClm = "";
String wccgpan = "";
String hidencgpan = "";
String wcAsOnNPA = "";
String cgpantodisplay = "";
String wcAmount1 = "";
String wcOtherCharges1 = "";
String recMode = "";
String cgpn = "";
String amountClaimed = "";
java.util.HashMap hashmap = null;
java.util.Date dsbrsDt = null;
String repaidStr = "";
String tcfield = "TC".trim();
String wcfield = "WC".trim();
%>

<body onload="setCPOthersEnabled()"/>
<%
String focusField="nameOfOfficial";
org.apache.struts.action.ActionErrors errors = (org.apache.struts.action.ActionErrors)request.getAttribute(org.apache.struts.Globals.ERROR_KEY);
if (errors!=null && !errors.isEmpty())
{
    focusField="test";
}
 Date systemDate  = new Date();
 String sysDate = dateFormat.format(systemDate);
 ClaimActionForm claimForm = (ClaimActionForm)session.getAttribute("cpTcDetailsForm") ;
 claimForm.setClaimSubmittedDate(sysDate);
%>


<html:form action="saveClaimApplication.do?method=saveClaimApplication" method="POST" focus="<%=focusField%>" enctype="multipart/form-data">
<html:errors/>
<html:hidden name="cpTcDetailsForm" property="test"/>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    
    <tr>
      <td class="FontStyle">&nbsp;</td>
    </tr>
  </table>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr> 
    <td width="20" align="right" valign="bottom"><img src="images/TableLeftTop.gif" width="20" height="31"></td>
      <td width="248" background="images/TableBackground1.gif"><!--<img src="images/ClaimsProcessingHeading.gif" width="131" height="25">--></td>
    <td align="right" valign="top" background="images/TableBackground1.gif"> 
      <div align="right"></div></td>
    <td width="23" align="left" valign="bottom"><img src="images/TableRightTop.gif" width="23" height="31"></td>
  </tr>
  <tr> 
    <td width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</td>
    <td colspan="2">
      <DIV align="right">			
      	<!--	<A HREF="javascript:submitForm('helpDisclaimer.do')">
      	        HELP</A> -->
      </DIV> 
      <TABLE> <tr>
             <td align="left" width="80%">&nbsp;</td>
             <td align="right">&nbsp;</td></tr>
             <tr><td>&nbsp;</td>
         </tr>
		
      <tr> 
           <td class="SubHeading" colspan="4" align="center"> &nbsp;Claim Application details: </td>
      </tr>
      </TABLE>      
      <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
      <!-- <tr> 
           <td class="SubHeading" colspan="4"> &nbsp;Claim Application details: </td>
      </tr> -->
      <tr><td>&nbsp;</td></tr>
      <tr><td>Application for First Claim Installment for &nbsp;<bean:write property="borrowerDetails.borrowerName" name="cpTcDetailsForm"/>&nbsp;has been forwarded to Checker for approval.</td></tr>
      <tr><td>&nbsp;</td></tr>
       <tr>
          <td class="ColumnBackground"> &nbsp;<bean:message key="memberId"/></td>
          <td class="TableData">&nbsp;<bean:write property="memberId" name="cpTcDetailsForm"/></td>
      </tr>
       <tr> 
          <td class="ColumnBackground"> &nbsp;<bean:message key="cpclaimrefnumber"/></td>
          <td class="TableData">&nbsp;<bean:write property="clmRefNumber" name="cpTcDetailsForm"/></td>
       </tr>



    <!-- <tr>
          <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="cpssiunitname"/></div></td>
          <td class="TableData"><div align="left">&nbsp;<bean:write property="borrowerDetails.borrowerName" name="cpTcDetailsForm"/></div></td>
       </tr> -->
     <tr><td colspan="8" class="SubHeading"><table width="100%" border="0" cellspacing="1" cellpadding="0">
         		  <% int i=1;%>
  					  <logic:iterate property="tcCgpansVector" id="object" name="cpTcDetailsForm" scope="session">			  					  
  					  <% java.util.HashMap mp = (java.util.HashMap)object;  					  
  					     cgpan = (java.lang.String)mp.get(ClaimConstants.CLM_CGPAN); 
  					     hiddencgpan = "cgpandetails(key-"+i+")";					  					  
  					  %>
  					 <html:hidden property="<%=hiddencgpan%>" name="cpTcDetailsForm" value="<%=cgpan%>"/>
  					  <tr>					  
  						  <td class="TableData"><bean:message key="cgpan"/>&nbsp;</td>
                <td class="TableData"><%=cgpan%></td>
  					  </tr>
  					  <%i++;%>
  					  </logic:iterate>
  					  </table></td></tr> 
      </TABLE>      
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
     <tr>
          <td><table width="100%" border="0" cellspacing="1" cellpadding="0">
            <tr><td><font color="blue">&nbsp; </font></td></tr>
               <tr> 
                  <td colspan="8"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                       
                      <tr> 
                        <td colspan="4" class="Heading"><img src="images/Clear.gif" width="5" height="5"></td>
                      </tr>                      
                    </table></td>
                </tr>                                
               
				</table></td>
			</tr>
			<tr> 
                  <td colspan="4"><img src="images/Clear.gif" width="5" height="15"></td>
                </tr>
		<tr> 
                  <td colspan="4"><table width="100%" border="0" cellspacing="1">
                                        
					
						<tr>
					<td class="ColumnBackground">&nbsp;<!--<bean:message key="mliName"/>--> MLI(Bank) Name & Seal </td>
					<td class="TableData"><bean:write property="memberDetails.memberBankName" name="cpTcDetailsForm"/>, <bean:write property="memberDetails.memberBranchName" name="cpTcDetailsForm"/></td>
					</tr>
					<tr>
					<td class="ColumnBackground">&nbsp;Date of claim Intiation</td>
					<td class="TableData" align="center"><!-- <html:text property="claimSubmittedDate" name="cpTcDetailsForm" maxlength="10"/><img src="images/CalendarIcon.gif" width="20" onClick="showCalendar('cpTcDetailsForm.claimSubmittedDate')" align="center">--> <bean:write property="claimSubmittedDate" name="cpTcDetailsForm"/> </td>
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
         <a href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>"><img src="images/OK.gif" width="49" height="37" border="0"></a>
          </div></td>
      <td width="23" align="right" valign="bottom"><img src="images/TableRightBottom.gif" width="23" height="51"></td>
  </tr>
</table>
</html:form>
</body>
</html>
