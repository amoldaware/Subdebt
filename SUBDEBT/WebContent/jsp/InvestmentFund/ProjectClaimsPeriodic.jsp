<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","showPeriodicProjection.do?method=showPeriodicProjection");%>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
	<html:errors />
	<html:form action="getPeriodicProjectionDetails.do?method=getPeriodicProjectionDetails" method="POST" enctype="multipart/form-data" focus="startDate">
	<tr>
		<td><br></td>
	</tr>
    <tr>
      <td width="20" align="right" valign="bottom"><img src="images/TableLeftTop.gif" width="20" height="31"></td>
      <td background="images/TableBackground1.gif"><img src="images/InvestmentManagementHeading.gif" width="169" height="25"></td>
      <td width="20" align="left" valign="bottom"><img src="images/TableRightTop.gif" width="23" height="31"></td>
    </tr>
    <tr>
      <td width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</td>
      <td><table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
	  <tr>
	  <TD>			
			<DIV align="right">			
				<A HREF="javascript:submitForm('helpPeriodicProjectClaimDetails.do?method=helpPeriodicProjectClaimDetails')">
			    HELP</A>
			</DIV>
		</td>
	  </tr>
          <tr>
            <td><table width="100%" border="0" cellspacing="1" cellpadding="1">
                <tr >
                  <td colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr> 
                        <td width="25%" class="Heading">
						<bean:message key="PeriodicProjection" /></td>
                        <td align="left" valign="bottom"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                      </tr>
                      <tr> 
                        <td colspan="4" class="Heading"><img src="images/Clear.gif" width="5" height="5"></td>
                      </tr>
                    </table></td>
                </tr>
                <tr align="left" valign="top">
                  <td width="20%" valign="top" class="ColumnBackground"> <div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;
				  <bean:message key="startDate" />
				  </div></td>
                  <td align="left" valign="top" class="tableData" align="center">
					<html:text  property="startDate" maxlength="10"/><img src="images/CalendarIcon.gif" onclick="showCalendar('ifForm.startDate')" align="center">
                  </td>
                </tr>
                <tr align="left" valign="top">
                  <td width="20%" valign="top" class="ColumnBackground"> <div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;
				   <bean:message key="endDate" />
				  </div></td>
                  <td align="left" valign="top" class="tableData" align="center">
					<html:text  property="endDate" maxlength="10"/><img src="images/CalendarIcon.gif" onclick="showCalendar('ifForm.endDate')" align="center">
                  </td>
                </tr>
              </table></td>
          </tr>
          <tr >
            <td height="20" >&nbsp;</td>
          </tr>
          <tr >
            <td align="center" valign="baseline"><div align="center"><html:link href="javascript:submitForm('getPeriodicProjectionDetails.do?method=getPeriodicProjectionDetails')">
			<img src="images/OK.gif" alt="OK" width="49" height="37" border="0"></html:link>
			<html:link href="javascript:document.ifForm.reset()"><img src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></html:link>
              </div></td>
          </tr>
        </table></td>
      <td width="20" background="images/TableVerticalRightBG.gif">&nbsp;</td>
    </tr>
    <tr>
      <td width="20" align="right" valign="top"><img src="images/TableLeftBottom1.gif" width="20" height="15"></td>
      <td background="images/TableBackground2.gif">&nbsp;</td>
      <td width="20" align="left" valign="top"><img src="images/TableRightBottom1.gif" width="23" height="15"></td>
    </tr>
  </table>

  <br>
  <p>&nbsp;</p>
</html:form>
