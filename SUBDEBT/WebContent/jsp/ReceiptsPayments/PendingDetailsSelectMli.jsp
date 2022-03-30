<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","displayPPMLIWiseFilter.do?method=displayPPMLIWiseFilter");%>

<html:errors />
<html:form action="getPPMLIWiseDetails.do?method=getPPMLIWiseDetails" method="POST" enctype="multipart/form-data">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td class="FontStyle">&nbsp;</td>
    </tr>
  </table>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr> 
    <td width="20" align="right" valign="bottom"><img src="images/TableLeftTop.gif" width="20" height="31"></td>
      <td width="248" background="images/TableBackground1.gif"><img src="images/ReceiptsPaymentsHeading.gif" width="142" height="25"></td>
    <td align="right" valign="top" background="images/TableBackground1.gif"> </td>
    <td width="23" align="left" valign="bottom"><img src="images/TableRightTop.gif" width="23" height="31"></td>
  </tr>
  <tr> 
    <td width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</td>
    <td colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td> <table width="100%" border="0" cellspacing="1" cellpadding="0">
                <tr> 
                  <td colspan="6"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr> 
                        <td width="25%" class="Heading"><a name="AD" id="AD"></a>&nbsp;Pending Payment Details</td>
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
                  <td class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;Member Id</td>
                  <td class="TableData">
				  <html:text property="memberId" maxlength="12" name="rpAllocationForm"/>
				  </td>
				</tr>
                <tr> 
                  <td colspan="4"><img src="images/Clear.gif" width="5" height="15"></td>
                </tr>
              </table>
      </table></td>
    <td width="23" background="images/TableVerticalRightBG.gif">&nbsp;</td>
  </tr>
  <tr> 
      <td width="20" align="right" valign="bottom"><img src="images/TableLeftBottom.gif" width="20" height="51"></td>
      <td colspan="2" valign="bottom" background="images/TableBackground3.gif"> 
        <div>
          <div align="center">
          <A href="javascript:submitForm('getPPMLIWiseDetails.do?method=getPPMLIWiseDetails')"><IMG src="images/OK.gif" alt="OK" width="49" height="37" border="0"></A>
          <A href="javascript:document.rpAllocationForm.reset()">
	  <IMG src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></A>
          <A href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>"><img src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A></div>
      </div></td>
      <td width="23" align="right" valign="bottom"><img src="images/TableRightBottom.gif" width="23" height="51"></td>
  </tr>
</table>
</html:form>
</body>
</html>
