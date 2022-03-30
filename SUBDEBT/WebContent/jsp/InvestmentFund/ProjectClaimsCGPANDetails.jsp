<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@ page import="java.text.DecimalFormat"%>
<%@page import = "com.cgtsi.investmentfund.ProjectExpectedClaimDetail"%>
<% 
	String flag = (String)session.getAttribute("projectionFlag");
	String action = "";
	if (flag.equals("1"))
	{
		session.setAttribute("CurrentPage", "getPeriodicProjectionDetails.do?method=getPeriodicProjectionDetails");
		action = "updatePeriodicProjectionDetails.do?method=updatePeriodicProjectionDetails";

	}
	else if (flag.equals("2"))
	{
		session.setAttribute("CurrentPage", "getCumulativeProjectionDetails.do?method=getCumulativeProjectionDetails");
		action = "updateCumulativeProjectionDetails.do?method=updateCumulativeProjectionDetails";
	}
%>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
	<html:errors />
	<html:form action="<%=action%>" method="POST" enctype="multipart/form-data">

    <tr>
      <td width="20" align="right" valign="bottom"><img src="images/TableLeftTop.gif" width="20" height="31"></td>
      <td background="images/TableBackground1.gif"><img src="images/InvestmentManagementHeading.gif" width="169" height="25"></td>
      <td width="20" align="left" valign="bottom"><img src="images/TableRightTop.gif" width="23" height="31"></td>
    </tr>
    <tr>
      <td width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</td>
      <td><table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
          <tr>
            <td><table width="100%" border="0" cellspacing="1" cellpadding="1">
                <tr >
                  <td colspan="2"></td>
                </tr>
                <tr align="left" valign="top" class="ColumnBackground">
					<td align="center"><bean:message key="cgpan"/> </td>
					<td align="center"><bean:message key="outstandingAsOn"/></td>
					<td align="center"><bean:message key="projectedClaimAmount"/></td>
                </tr>
				<%
					double osSum=0;
					double claimSum=0;
					DecimalFormat df= new DecimalFormat("######################.##");
					df.setDecimalSeparatorAlwaysShown(false);
					String strOsSum="";
					String strClaimSum="";
				%>

                <logic:iterate id="object" property="projections" name="ifForm" >
                 <%
                     ProjectExpectedClaimDetail pecd = (ProjectExpectedClaimDetail)object;
                     String cgpn = (String)pecd.getCgpan();
                     String url = "showCgpanDetails.do?method=showCgpanDetails&cgpanDetail=" + cgpn;
                 %>
                <tr align="left" valign="top" class="tableData">
					<td align="center"><html:link href="<%=url%>"><bean:write property="cgpan" name="object"/></html:link></td>
					<td align="right"><bean:write property="outstandingAmount" name="object"/></td>
					<td align="right"><bean:write property="projectedClaimAmount" name="object"/></td>
                </tr>
                <%
                osSum+= ((com.cgtsi.investmentfund.ProjectExpectedClaimDetail)object).getOutstandingAmount();
                claimSum+= ((com.cgtsi.investmentfund.ProjectExpectedClaimDetail)object).getProjectedClaimAmount();
				strOsSum = df.format(osSum);
				strClaimSum = df.format(claimSum);

                %>
                </logic:iterate>

                <tr align="left" valign="top" class="ColumnBackground">
					<td align="center"><bean:message key="psTotalWorth"/> </td>
					<td align="right"><%=strOsSum%></td>
					<td align="right"><%=strClaimSum%></td>
                </tr>

              </table></td>
          </tr>
          <tr >
            <td height="20" >&nbsp;</td>
          </tr>
          <tr >
            <td align="center" valign="baseline" > <div align="center">			<%
			if (flag.equals("1"))
			{
			%>
			<html:link
			href="javascript:submitForm('updatePeriodicProjectionDetails.do?method=updatePeriodicProjectionDetails')">
			<img src="images/Save.gif" alt="Ok" width="49" height="37" border="0"></html:link>
			<%
			}
			else if (flag.equals("2"))
			{
			%>
			<html:link
			href="javascript:submitForm('updateCumulativeProjectionDetails.do?method=updateCumulativeProjectionDetails')">
			<img src="images/Save.gif" alt="Ok" width="49" height="37" border="0"></html:link>
			<%}%>
			<html:link
			href="javascript:window.history.back()">
			<img src="images/Back.gif" alt="Back" width="49" height="37" border="0"></html:link>
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

