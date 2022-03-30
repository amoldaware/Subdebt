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

<% session.setAttribute("CurrentPage","updateClaimApplication.do?method=updateClaimApplication");%>
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


<html:form action="updateClaimApplication.do?method=updateClaimApplication" method="POST" focus="<%=focusField%>" enctype="multipart/form-data">
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
             <td align="left" width="80%">&nbsp;Ltr Ref No:---------------------------</td>
             <td align="right">&nbsp;Date:---------------</td></tr>
             <tr><td>&nbsp;</td>
         </tr>
		<tr>
                       <td>To,</td> </tr>
                      <tr> <td>Dy. General Manager,</td></TR>
                       <tr><td>CGTMSE,</Td></tr>
                       <tr><td> 1002 & 1003, Naman Centre, 10th floor,</td></TR>
                       <tr><td>Plot No. C-31, G - Block,</td></tr>
                       <tr><td>Bandra Kurla Complex, Bandra (East),</td></tr>
                       <tr><td><u>MUMBAI - 400051</u></td></tr>
                       <!-- <tr><td>&nbsp;</td></tr>
                       <tr><td>&nbsp;</td></tr> -->
      <tr> 
           <td class="SubHeading" colspan="4" align="center"> &nbsp;Claim Application details: </td>
      </tr>
      </TABLE>      
      <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
      <!-- <tr> 
           <td class="SubHeading" colspan="4"> &nbsp;Claim Application details: </td>
      </tr> -->
      <tr><td>&nbsp;</td></tr>
      <tr><td>Application for First Claim Installment for &nbsp;<bean:write property="borrowerDetails.borrowerName" name="cpTcDetailsForm"/>&nbsp;has been saved successfully.</td></tr>
      <tr><td>&nbsp;</td></tr>
       <tr>
          <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="memberId"/></div></td>
          <td class="TableData"><div align="left">&nbsp;<bean:write property="memberId" name="cpTcDetailsForm"/></div></td>
       <!-- </tr>
       <tr> -->
          <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="cpclaimrefnumber"/></div></td>
          <td class="TableData"><div align="left">&nbsp;<bean:write property="clmRefNumber" name="cpTcDetailsForm"/></div></td>
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
					  <% int j=1;%>
  					  <logic:iterate property="wcCgpansVector" id="object" name="cpTcDetailsForm" scope="session">			  					  
  					  <% java.util.HashMap mp = (java.util.HashMap)object;  					  
  					     cgpan = (java.lang.String)mp.get(ClaimConstants.CLM_CGPAN); 
  					     hiddencgpan = "cgpandetails(key-"+j+")";					  					  
  					  %>
  					 <html:hidden property="<%=hiddencgpan%>" name="cpTcDetailsForm" value="<%=cgpan%>"/>
  					  <tr>					  
  						  <td class="TableData"><bean:message key="cgpan"/>&nbsp;</td>
                <td class="TableData"><%=cgpan%></td>
  					  </tr>
  					  <%j++;%>
  					  </logic:iterate>
  					  </table></td></tr>
					  
      </TABLE>      
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
     <tr>
          <td><table width="100%" border="0" cellspacing="1" cellpadding="0">
            <tr><td><font color="blue">&nbsp;Please send declaration and Undertaking signed by Officer not below the rank of Asst.General Manager of bank or of equivalent rank</font></td></tr>
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
				  </td>
                </tr>
                <tr> 
                  <td class="TableData" >&nbsp;<bean:message key="para2"/></td>
				  </td>
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
					<td class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">Signature </font></td>
					<td class="TableData"></td>
					</tr>
					<tr>
					<td class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="nameofofficial"/></td>
					<td class="TableData"><html:text property="nameOfOfficial" name="cpTcDetailsForm" maxlength="100"/></td>
					</tr>
					<tr>
					<td class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="designationOfOfficial"/></td>
					<td class="TableData"><html:text property="designationOfOfficial" name="cpTcDetailsForm" maxlength="50"/></td>
					</tr>
					<tr>
					<td class="ColumnBackground">&nbsp;<!--<bean:message key="mliName"/> --> MLI(Bank) Name & Seal </td>
					<td class="TableData"><bean:write property="memberDetails.memberBankName" name="cpTcDetailsForm"/>, <bean:write property="memberDetails.memberBranchName" name="cpTcDetailsForm"/></td>
					</tr>
					<tr>
					<td class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="dateofclaimfiling"/></td>
					<td class="TableData" align="center"><!-- <html:text property="claimSubmittedDate" name="cpTcDetailsForm" maxlength="10"/><img src="images/CalendarIcon.gif" width="20" onClick="showCalendar('cpTcDetailsForm.claimSubmittedDate')" align="center">--> <bean:write property="claimSubmittedDate" name="cpTcDetailsForm"/> </td>
					</tr>
					<tr>
					<td class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="place"/></td>
					<td class="TableData"><bean:write property="memberDetails.city" name="cpTcDetailsForm"/><%-- <html:text property="place"  name="cpTcDetailsForm" maxlength="100"/> --%></td>
					</tr>
					<tr>
					<td><br><br></td>
					</tr>
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
          <A href="javascript:printpage()"><IMG src="images/Print.gif" alt="Print" width="49" height="37" border="0"></A>
          </div></td>
      <td width="23" align="right" valign="bottom"><img src="images/TableRightBottom.gif" width="23" height="51"></td>
  </tr>
</table>
</html:form>
</body>
</html>
