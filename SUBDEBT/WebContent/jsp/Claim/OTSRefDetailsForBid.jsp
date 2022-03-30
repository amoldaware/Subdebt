<%@ page language="java"%>
<%@ page import="com.cgtsi.claim.ClaimConstants"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%session.setAttribute("CurrentPage","displayOTSReferenceDetails.do?method=displayOTSReferenceDetails");%>

<%
String cgpan = "";
String totalApprovedAmnt = "";
String tcSanctionedAmnt = "";
String wcFBSanctionedAmnt = "";
String wcNFBSanctionedAmnt = "";
String osAmntOnDtForOTS = "";
String prpsdAmntPaidByBId = "";
String prpsdAmntSacrificed = "";
%>

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
                        <td width="25%" height="20" class="Heading">&nbsp;<bean:message key="otsrefdtls"/></td>
                        <td align="left" valign="bottom"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
                        <td>&nbsp;</td>
                      </tr>
                      <tr> 
                        <td colspan="4" class="Heading"><img src="images/Clear.gif" width="5" height="5"></td>
                      </tr>
                    </table></td>
                </tr>

                <tr>
					<td class="ColumnBackground">&nbsp;<bean:message key="borrowerID"/></td>
					<td class="TableData"><div align="center"><bean:write property="borrowerID" name="cpTcDetailsForm"/>
					</div></td>
                </tr>
				<tr>
					<td class="ColumnBackground">&nbsp;<bean:message key="reasonforots"/></td>
					
					<td class="TableData"><div align="center">
					<bean:write property="reasonForOTS" name="cpTcDetailsForm"/>					
					</div></td>
				</tr>
                <tr> 
                  <td class="ColumnBackground">&nbsp;<bean:message key="wilfullDefaulter"/></td>
                  <td class="TableData"><div align="center">
                  <bean:write property="wilfullDefaulter" name="cpTcDetailsForm"/></div>					                  
                  </td>
                </tr>
                     <tr>
			<td><br></td>
		     </tr>                       
				<tr class="SubHeading">
					<td><bean:message key="amountproposedforotsandamountsacrificed"/></td>
				</tr>
				<tr>
					<td colspan="9"><table width="100%" border="0" cellspacing="1" cellpadding="0">
						<tr class="HeadingBg">
							<td rowspan="2"><div align="center">CGPAN</div></td>
							<td colspan="3"><div align="center">Sanctioned Amount<br><bean:message key="inRs" /></div></td>
							<td rowspan="2"><div align="center">Guarantee Issued Amount<br><bean:message key="inRs" /></div></td>
							<td rowspan="2"><div align="center"><font color="#FF0000" size="2">*</font><bean:message key="osamountasondate"/><br><bean:message key="inRs" /></div></td>
							<td rowspan="2"><div align="center"><font color="#FF0000" size="2">*</font><bean:message key="proposedamnttobepaidbyborrower"/><br><bean:message key="inRs" /></div></td>
							<td rowspan="2"><div align="center"><font color="#FF0000" size="2">*</font><bean:message key="proposedamnttobesacrificed"/><br><bean:message key="inRs" /></div></td>							
						</tr>
						<tr class="HeadingBg">
							<td><div align="center">TC</div></td>
							<td><div align="center">FB WC</div></td>
							<td><div align="center">NFB WC</div></td>
							<!-- <td><div align="center">Fund Based Sanctioned Amount</div></td> -->
							<!-- <td><div align="center">Non Fund Based Sanctioned Amount</div></td> -->
							
						</tr>
						
						<%int i=0;%>
						<logic:iterate property="otsReferenceDetails" name="cpTcDetailsForm" id="object">
						<% 
						     java.util.HashMap hashmap = (java.util.HashMap)object;
						     cgpan = (String)hashmap.get(com.cgtsi.claim.ClaimConstants.CLM_CGPAN);
						     totalApprovedAmnt = ((Double)hashmap.get(ClaimConstants.CLM_APPLICATION_APPRVD_AMNT)).toString();
						     tcSanctionedAmnt = ((Double)hashmap.get(ClaimConstants.CLM_TC_SANCTIONED_AMNT)).toString();
						     wcFBSanctionedAmnt = ((Double)hashmap.get(ClaimConstants.CLM_WC_FB_SANCTIONED_AMNT)).toString();
						     wcNFBSanctionedAmnt = ((Double)hashmap.get(com.cgtsi.claim.ClaimConstants.CLM_WC_NFB_SANCTIONED_AMNT)).toString();
						     osAmntOnDtForOTS = ((Double)hashmap.get(ClaimConstants.CLM_OTS_TOTAL_OS_AMNT)).toString();
						     prpsdAmntPaidByBId = ((Double)hashmap.get(ClaimConstants.CLM_OTS_TOTAL_BORROWER_PROPOSED_AMNT)).toString();
						     prpsdAmntSacrificed = ((Double)hashmap.get(ClaimConstants.CLM_OTS_TOTAL_PROPOSED_SCRFCE_AMNT)).toString();
						     double totalWCSnctedAmnt = Double.parseDouble(wcFBSanctionedAmnt) + Double.parseDouble(wcNFBSanctionedAmnt); 
						     String totalWCSnctedAmntStr = new Double(totalWCSnctedAmnt).toString();						     
						%>
						<tr class="TableData">
							<td><div align="center"><%=cgpan%></div></td>
							<td><div align="right"><%=tcSanctionedAmnt%></div></td>							
							<td><div align="right"><%=wcFBSanctionedAmnt%></div></td>
							<td><div align="right"><%=wcNFBSanctionedAmnt%></div></td>
							<td><div align="right"><%=totalApprovedAmnt%></div></td>
							
							<td><div align="center">
							<%=osAmntOnDtForOTS%>
							</div></td>														
							<td><div align="center">
							<%=prpsdAmntPaidByBId%>
							</div></td>							
							<td><div align="center">
							<%=prpsdAmntSacrificed%>
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
		<A href="displayOTSProcessInfo.do?method=displayOTSProcessInfo"><img src="images/OK.gif" alt="Apply" width="49" height="37" border="0"></a>		
      </div></td>
      <td width="23" align="right" valign="top"><img src="images/TableRightBottom.gif" width="23" height="51"></td>
  </tr>
</table>
</body>
</html>
