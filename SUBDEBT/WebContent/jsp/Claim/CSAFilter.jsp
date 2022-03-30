<%@ page language="java"%>
<%@ page import="com.cgtsi.claim.ClaimConstants"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","displaySettlementAdviceFilter.do?method=displaySettlementAdviceFilter");%>

<html:errors/>
<html:form action="getSettlementAdviceFilterDtl.do?method=getSettlementAdviceFilterDtl" method="POST" enctype="multipart/form-data">
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
                  <td width="35%" class="Heading">&nbsp;Generate Claim Settlement&nbsp;Advice</td>
                  <td colspan="3"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
                </tr>
                <tr> 
                  <td colspan="4" class="Heading"><img src="images/Clear.gif" width="5" height="5"></td>
                </tr>
              </table></td>
          </tr>
	  <tr>
	  <td class="ColumnBackground">Member Id</td>
	  <td class="TableData">
	  <html:radio property="memberIdFlag" name="cpTcDetailsForm" value="<%=ClaimConstants.CLM_STLMNT_MEMBER_ALL%>" onclick="disableMember(this)"><bean:message key="adviceforall"/></html:radio>
	  <html:radio property="memberIdFlag" name="cpTcDetailsForm" value="<%=ClaimConstants.CLM_STLMNT_MEMBER_SPECIFIC%>" onclick="enableMember(this)"><bean:message key="adviceforspecific"/></html:radio>
	  <html:text property="memberId" maxlength="12" name="cpTcDetailsForm" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>
	  </td>
          </tr>
          <tr align="center"> 
            <td colspan="4" valign="bottom"> <div align="center">
            <a href="javascript:document.cpTcDetailsForm.reset()"><img src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></a>
            <A href="javascript:submitForm('getSettlementAdviceFilterDtl.do?method=getSettlementAdviceFilterDtl')"><img src="images/OK.gif" alt="Save" width="49" height="37" border="0"></a>
            <a href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>"><img src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></a></div></td>
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
