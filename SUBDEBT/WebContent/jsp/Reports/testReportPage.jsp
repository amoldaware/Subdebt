<%@ page language="java"%>
<%@ page import = "com.cgtsi.claim.ClaimConstants"%>
<%@ page import="com.cgtsi.actionform.ClaimActionForm"%>
<%@ page import = "com.cgtsi.claim.TermLoanCapitalLoanDetail"%>
<%@ page import = "com.cgtsi.claim.WorkingCapitalDetail"%>
<%@ page import = "com.cgtsi.claim.RecoveryDetails"%>
<%@ page import = "com.cgtsi.claim.ClaimSummaryDtls"%>
<%@ page import = "com.cgtsi.claim.ClaimApplication"%>
<%@ page import = "com.cgtsi.claim.SecurityAndPersonalGuaranteeDtls"%>
<%@ page import = "com.cgtsi.claim.DtlsAsOnDateOfSanction"%>
<%@ page import = "com.cgtsi.claim.DtlsAsOnDateOfNPA"%>
<%@ page import = "com.cgtsi.claim.DtlsAsOnLogdementOfClaim"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@page import ="java.text.DecimalFormat"%>
<%@page import ="java.lang.String"%>
<%@page import ="java.util.*"%>
<%DecimalFormat decimalFormat = new DecimalFormat("##########");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","getTestSSIDetailsInput.do?method=getTestSSIDetailsInput");
//ClaimActionForm claimForm = (ClaimActionForm)session.getAttribute("cpTcDetailsForm") ;
%>

<html:errors />
<html:form action="getTestSSIDetailsInput.do?method=getTestSSIDetailsInput" method="POST" enctype="multipart/form-data">
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
			<div align="right"></div>
		</td>
		<td width="23" align="left" valign="bottom"><img src="images/TableRightTop.gif" width="23" height="31"></td>
    </tr>
	<tr>
	<td>
	<table>
		<tr>
			<td class="ColumnBackground" colspan="2">&nbsp;SSI Name</td>
			<td class="ColumnBackground" colspan="2">&nbsp;Micro Flag</td>
			<td class="ColumnBackground" colspan="2">&nbsp;Women Operated</td>
			<td class="ColumnBackground" colspan="2">&nbsp;Defaulter Flag</td>
		</tr>
	<logic:iterate property="ssiList" name="testSSIForm" id="object">
	<%
        
		java.util.Map map = (java.util.Map)object;
		java.lang.String unitName = (String)map.get("UNITNAME");
		java.lang.String microFlag = (String)map.get("MICROFLAG");
		java.lang.String womenOperated = (String)map.get("WOMENOPERATED");
		java.lang.String defaultFlag = (String)map.get("DEFAULTERFLAG");
	%>
		<tr>
			<td class="TableData" colspan="8"><%=unitName%></td>
			<%if("Y".equals(microFlag)){%>
			<td class="TableData" colspan="8"><input type="checkbox" name="microflag" disabled="true" value="Y"/></td>
			<%}else{%>
			<td class="TableData" colspan="8"><input type="checkbox" name="microflag" /></td>
			<%}%>
			<%if("Y".equals(womenOperated)){%>
			<td class="TableData" colspan="8"><input type="checkbox" name="womenoperated" disabled="true" value="Y"/></td>
			<%}else{%>
			<td class="TableData" colspan="8"><input type="checkbox" name="womenoperated" /></td>
			<%}%>
			<%if("Y".equals(defaultFlag)){%>
			<td class="TableData" colspan="8"><input type="checkbox" name="defaulterFlag" disabled="true" value="Y"/></td>
			<%}else{%>
			<td class="TableData" colspan="8"><input type="checkbox" name="defaulterFlag" /></td>
			<%}%>
		</tr>
	</logic:iterate>
	</table>
	</td>
	</tr>
	<tr>
		<td width="20" align="right" valign="bottom"><img src="images/TableLeftBottom.gif" width="20" height="51"></td>
        <td colspan="2" valign="bottom" background="images/TableBackground3.gif"> 
        <td width="23" align="right" valign="bottom"><img src="images/TableRightBottom.gif" width="23" height="51"></td>
	</tr> 
	</table>
</html:form>
</body>
</html>

			


    
