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
<%DecimalFormat decimalFormat = new DecimalFormat("##########");%>
<%--<%@ include file="/jsp/SetMenuInfo.jsp" %>--%>
<% session.setAttribute("CurrentPage","getTestSSIDetailsInput.do?method=getTestSSIDetailsInput");
//ClaimActionForm claimForm = (ClaimActionForm)session.getAttribute("cpTcDetailsForm") ;
%>

<html:errors />
<html:form action="getTestSSIDetails.do?method=getTestSSIDetails" method="POST" enctype="multipart/form-data">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">    
	<tr>
        <td class="FontStyle">&nbsp;</td>
    </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr> 
		<td width="20" align="right" valign="bottom"><img src="/CGTMSE116TEST-ViewController-context-root/images/TableLeftTop.gif" width="20" height="31"></td>
		<td width="248" background="images/TableBackground1.gif"><img src="/CGTMSE116TEST-ViewController-context-root/images/ClaimsProcessingHeading.gif" width="131" height="25"></td>
		<td align="right" valign="top" background="/CGTMSE116TEST-ViewController-context-root/images/TableBackground1.gif"> 
			<div align="right"></div>
		</td>
		<td width="23" align="left" valign="bottom"><img src="/CGTMSE116TEST-ViewController-context-root/images/TableRightTop.gif" width="23" height="31"></td>
    </tr>
    <tr>
	<td class="ColumnBackground" >&nbsp;Member Id</td>
        <td class="TableData" ><html:text property="memberId" name="testSSIForm" maxlength="12"/></td>	
    </tr>
    <TR >
	<TD align="center" valign="baseline" >
	<DIV align="center">
							        <A href="javascript:submitForm('getTestSSIDetails.do?method=getTestSSIDetails')">
									<IMG src="/CGTMSE116TEST-ViewController-context-root/images/OK.gif" alt="OK" width="49" height="37" border="0"></A>
	</DIV>
						</TD>
    </TR>
    <tr>
        <td width="20" align="right" valign="bottom"><img src="/CGTMSE116TEST-ViewController-context-root/images/TableLeftBottom.gif" width="20" height="51"></td>
        <td colspan="2" valign="bottom" background="/CGTMSE116TEST-ViewController-context-root/images/TableBackground3.gif"> 
        <td width="23" align="right" valign="bottom"><img src="/CGTMSE116TEST-ViewController-context-root/images/TableRightBottom.gif" width="23" height="51"></td>
    </tr> 
	</table>
</html:form>
</body>
</html>


				


    
