<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","showInflowOutflowReportInput.do?method=showInflowOutflowReportInput");%>

<html:form action="showInvstMaturingDetails.do?method=showInvstMaturingDetails" method="POST" enctype="multipart/form-data" focus="valueDate">


  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <html:errors />
    <tr> 
      <td width="20" align="right" valign="bottom"><img src="images/TableLeftTop.gif" width="20" height="31"></td>
      <td width="353" background="images/TableBackground1.gif"><img src="images/InvestmentManagementHeading.gif" width="169" height="25"></td>
      <td align="right" background="images/TableBackground1.gif"> </td>
      <td width="23" align="left" valign="bottom"><img src="images/TableRightTop.gif" width="23" height="31"></td>
    </tr>
  <tr> 
    <td width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</td>
    <td colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
	  <TD>			
			<DIV align="right">			
				<A HREF="javascript:submitForm('helpInflowOutFlowReport.do?method=helpInflowOutFlowReport')">
			    HELP</A>
			</DIV>
		</td>
	  </tr>
        <tr>
            <td > <table border="0" cellspacing="1" cellpadding="0">
                <tr> 
                  <td colspan="4" ><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr> 
                        <td width="30%" class="Heading">
						<bean:message key="InflowOutflowDetails" /></td>
                        <td align="left" valign="bottom"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                      </tr>
                      <tr> 
                        <td colspan="4" class="Heading"><img src="images/Clear.gif" width="5" height="5"></td>
                      </tr>
                    </table></td>
                </tr>
                <tr>
                  <td width="360" class="ColumnBackground" height="25">
				  &nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="date" />
				  </td>
                  <td class="tableData" width="474" height="25" align="center"> <div align="left"> 
                      <html:text name="investmentForm" property="valueDate" size="20" maxlength="10" onkeypress="return dateOnly(this, event)" onkeyup="isValidDate(this)"/><img src="images/CalendarIcon.gif" onclick="showCalendar('investmentForm.valueDate')" align="center">
                    </div></td>
                </tr>
              </table>
      </table></td>
    <td width="23" background="images/TableVerticalRightBG.gif">&nbsp;</td>
  </tr>
  <tr> 
      <td width="20" align="right" valign="bottom"><img src="images/TableLeftBottom.gif" width="20" height="51"></td>
      <td colspan="2" valign="bottom" background="images/TableBackground3.gif"> 
        <div>
          <div align="center"><html:link href="javascript:submitForm('showInvstMaturingDetails.do?method=showInvstMaturingDetails')"> <img src="images/OK.gif" alt="Ok" width="49" height="37" border="0"></html:link><html:link href="javascript:document.investmentForm.reset()"><img src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></html:link></div>
      </div></td>
      <td width="23" align="right" valign="bottom"><img src="images/TableRightBottom.gif" width="23" height="51"></td>
  </tr>
</table>
</html:form>

