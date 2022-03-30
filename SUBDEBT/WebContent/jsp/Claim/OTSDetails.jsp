<%@ page language="java"%>
<%@ page import="com.cgtsi.claim.ClaimConstants	"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%session.setAttribute("CurrentPage","forwardToNextPage.do?method=forwardToNextPage");%>

<%
String focusField="reasonForOTS";
org.apache.struts.action.ActionErrors errors = (org.apache.struts.action.ActionErrors)request.getAttribute(org.apache.struts.Globals.ERROR_KEY);
if (errors!=null && !errors.isEmpty())
{
    focusField="test";
}
%>
<html:form action="saveOTSReqDetails.do?method=saveOTSReqDetails" method="POST" focus="<%=focusField%>" enctype="multipart/form-data">
<html:errors />
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
    <td colspan="2">
      <DIV align="right">			
      		<A HREF="javascript:submitForm('helpEnterOTSDetails.do')">
      	        HELP</A>
      </DIV>        
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="100%" border="0" cellspacing="1" cellpadding="0">
                <tr> 
                  <td colspan="8"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr> 
                        <td width="25%" height="20" class="Heading">&nbsp;OTS Details</td>
                        <td align="left" valign="bottom"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
                        <td>&nbsp;</td>
                      </tr>
                      <tr> 
                        <td colspan="4" class="Heading"><img src="images/Clear.gif" width="5" height="5"></td>
                      </tr>
                    </table></td>
                </tr>
                <tr>
					<td class="ColumnBackground">&nbsp;Member Id</td>
					<td class="TableData"><div align="left"><bean:write property="memberId" name="cpTcDetailsForm"/>
					</div></td>
                </tr>

                <tr>
					<td class="ColumnBackground">&nbsp;CGBID</td>
					<td class="TableData"><div align="left"><bean:write property="borrowerID" name="cpTcDetailsForm"/>
					</div></td>
                </tr>
				<tr>
					<td class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="reasonforots"/></td>
					<td class="TableData"><div align="left">
					<html:textarea property="reasonForOTS" name="cpTcDetailsForm" cols="40" rows="4"/>						
					</div></td>
				</tr>
                <tr> 
                  <td class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="wilfullDefaulter"/></td>
                  <td class="TableData">
                  <html:radio property="wilfullDefaulter" name="cpTcDetailsForm" value="<%=ClaimConstants.DISBRSMNT_YES_FLAG%>"><bean:message key="otsyes"/></html:radio>
                  <html:radio property="wilfullDefaulter" name="cpTcDetailsForm" value="<%=ClaimConstants.DISBRSMNT_NO_FLAG%>"><bean:message key="otsno"/></html:radio>					                  
                  </td>
                </tr>
				<tr class="SubHeading">
					<td>Amount Proposed for OTS and Amount Sacrificed</td>
				</tr>
				<tr>
					<td colspan="9"><table width="100%" border="0" cellspacing="1" cellpadding="0">
						<tr class="HeadingBg">
							<td rowspan="2"><div align="center">CGPAN</div></td>
							<td colspan="3"><div align="center">Sanctioned Amount<br><bean:message key="inRs" /></div></td>
							<td rowspan="2"><div align="center">Guarantee Issued Amount<br><bean:message key="inRs" /></div></td>
							<td rowspan="2"><div align="center"><bean:message key="osamountasondate"/><br><bean:message key="inRs" /></div></td>
							<td rowspan="2"><div align="center"><bean:message key="proposedamnttobepaidbyborrower"/><br><bean:message key="inRs" /></div></td>
							<td rowspan="2"><div align="center"><bean:message key="proposedamnttobesacrificed"/><br><bean:message key="inRs" /></div></td>							
						</tr>
						<tr class="HeadingBg">	
							<td><div align="center">TC</div></td>
							<td><div align="center">FB WC</div></td>
							<td><div align="center">NFB WC</div></td>
							<!--
							<td>
							    <table width="100%" border="0" cellspacing="1" cellpadding="1">
							       <tr >
							         <td class="HeadingBg">FBWC</td>
							         <td class="HeadingBg">NFBWC</td>
							       </tr>
							    </table>
							</td>
							-->
							<!-- <td><div align="center">Fund Based Sanctioned Amount</div></td> -->
							<!-- <td><div align="center">Non Fund Based Sanctioned Amount</div></td> -->
							
						</tr>
						
						<%int i=1;%>
						<logic:iterate property="otsRequestDtls" name="cpTcDetailsForm" id="object">
						<% 
						     java.util.Hashtable ht =(java.util.Hashtable)object;
						     String cgpan = (String)ht.get(com.cgtsi.claim.ClaimConstants.CLM_CGPAN);
						     String totalApprovedAmnt = (String)ht.get(com.cgtsi.claim.ClaimConstants.CLM_APPLICATION_APPRVD_AMNT);
						     String tcSanctionedAmnt = (String)ht.get(com.cgtsi.claim.ClaimConstants.CLM_TC_SANCTIONED_AMNT);
						     String wcFBSanctionedAmnt = (String)ht.get(com.cgtsi.claim.ClaimConstants.CLM_WC_FB_SANCTIONED_AMNT);
						     String wcNFBSanctionedAmnt = (String)ht.get(com.cgtsi.claim.ClaimConstants.CLM_WC_NFB_SANCTIONED_AMNT);
						     double totalWCSnctedAmnt = Double.parseDouble(wcFBSanctionedAmnt) + Double.parseDouble(wcNFBSanctionedAmnt); 
						     String totalWCSnctedAmntStr = new Double(totalWCSnctedAmnt).toString();
						%>
						<tr class="TableData">
							<td><div align="center"><%=cgpan%></div></td>
							<td><div align="right"><%=tcSanctionedAmnt%></div></td>							
							<td><div align="right"><%=wcFBSanctionedAmnt%></div></td>
							<td><div align="right"><%=wcNFBSanctionedAmnt%></div></td>
							<td><div align="right"><%=totalApprovedAmnt%></div></td>
							
                                                        <%
							String osAmntOnDtForOTS="osAmntOnDateForOTS("+cgpan+")";
							%>
							<td><div align="right">
							<html:text property="<%=osAmntOnDtForOTS%>" name="cpTcDetailsForm" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)"/>								
							</div></td>							
							<%
							String prpsdAmntPaidByBId ="proposedAmntPaidByBorrower("+cgpan+")";
							%>
							<td><div align="right">
							<html:text property="<%=prpsdAmntPaidByBId%>" name="cpTcDetailsForm" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)"/>
							</div></td>
							<%
							String prpsdAmntSacrificed="proposedAmntSacrificed("+cgpan+")";
							%>
							<td><div align="right">
							<html:text property="<%=prpsdAmntSacrificed%>" name="cpTcDetailsForm" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)"/>
							</div></td>													
						</tr>
						<%i++;%>
						</logic:iterate>
					</table></td>
				</tr>
             </table>
            </td>
        </tr>
      </table></td>
    <td width="23" background="images/TableVerticalRightBG.gif">&nbsp;</td>
  </tr>
  <tr> 
      <td width="20" align="right" valign="top"><img src="images/TableLeftBottom.gif" width="20" height="51"></td>
      <td colspan="2" align="center" valign="middle" background="images/TableBackground3.gif"> 
        <div> 
		<div align="center">		
		<A href="javascript:submitForm('saveOTSReqDetails.do?method=saveOTSReqDetails')"><img src="images/Save.gif" alt="Apply" width="49" height="37" border="0"></a>
		<a href="javascript:document.cpTcDetailsForm.reset()"><img src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></a>
		<a href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>"><img src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></a></div>
      </div></td>
      <td width="23" align="right" valign="top"><img src="images/TableRightBottom.gif" width="23" height="51"></td>
  </tr>
</table>
</html:form>
</body>
</html>
