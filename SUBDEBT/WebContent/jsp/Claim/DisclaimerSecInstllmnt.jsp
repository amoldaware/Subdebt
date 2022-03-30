<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","displayDisclaimerForSecInstallment.do?method=displayDisclaimerForSecInstallment");%>

<%
String focusField="nameOfOfficial";
org.apache.struts.action.ActionErrors errors = (org.apache.struts.action.ActionErrors)request.getAttribute(org.apache.struts.Globals.ERROR_KEY);
if (errors!=null && !errors.isEmpty())
{
    focusField="test";
}
%>

<html:form action="saveClaimApplication.do?method=saveClaimApplication" focus="<%=focusField%>" method="POST" enctype="multipart/form-data">
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
      <td width="248" background="images/TableBackground1.gif"><img src="images/ClaimsProcessingHeading.gif" width="131" height="25"></td>
    <td align="right" valign="top" background="images/TableBackground1.gif"> 
      <div align="right"></div></td>
    <td width="23" align="left" valign="bottom"><img src="images/TableRightTop.gif" width="23" height="31"></td>
  </tr>
  <tr> 
    <td width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</td>
    <td colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0">
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
					<td class="ColumnBackground">&nbsp;<bean:message key="nameofofficial"/></td>
					<td class="TableData"><html:text property="nameOfOfficial" name="cpTcDetailsForm" maxlength="100"/></td>
					</tr>
					<tr>
					<td class="ColumnBackground">&nbsp;<bean:message key="designationOfOfficial"/></td>
					<td class="TableData"><html:text property="designationOfOfficial" name="cpTcDetailsForm" maxlength="50"/></td>
					</tr>
					<tr>
					<td class="ColumnBackground">&nbsp;<bean:message key="mliName"/></td>
					<td class="TableData"><bean:write property="memberDetails.memberBankName" name="cpTcDetailsForm"/>, <bean:write property="memberDetails.memberBranchName" name="cpTcDetailsForm"/></td>
					</tr>
					<tr>
					<td class="ColumnBackground">&nbsp;<bean:message key="dateofclaimfiling"/></td>
					<td class="TableData" align="center"><html:text property="claimSubmittedDate" name="cpTcDetailsForm" maxlength="10"/><img src="images/CalendarIcon.gif" width="20" onClick="showCalendar('cpTcDetailsForm.claimSubmittedDate')" align="center"></td>
					</tr>
					<tr>
					<td class="ColumnBackground">&nbsp;<bean:message key="place"/></td>
					<td class="TableData"><html:text property="place"  name="cpTcDetailsForm" maxlength="100"/></td>
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
          <A href="javascript:submitForm('saveApplicationForSecInstlmnt.do?method=saveApplicationForSecInstlmnt')"><img src="images/Save.gif" alt="Accept" width="49" height="37" border="0"></a>
          <a href="javascript:document.form1.reset()"><img src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></a>
          <a href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>"><img src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></a><div>
      </div></td>
      <td width="23" align="right" valign="bottom"><img src="images/TableRightBottom.gif" width="23" height="51"></td>
  </tr>
</table>
</html:form>
</body>
</html>
