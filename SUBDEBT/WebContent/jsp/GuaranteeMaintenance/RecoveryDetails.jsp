<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@ page import="com.cgtsi.admin.MenuOptions"%>

<% session.setAttribute("CurrentPage","showRecoveryDetails.do?method=showRecoveryDetails");%>
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="saveRecoveryDetails.do?method=saveRecoveryDetails" method="POST" enctype="multipart/form-data" focus="dateOfRecovery">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/GuaranteeMaintenanceHeading.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
			<DIV align="right">			
				<A HREF="javascript:submitForm('helpRecoveryDetails.do?method=helpRecoveryDetails')">
			    HELP</A>
			</DIV>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="4"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="31%" class="Heading"><bean:message key="recoveryHeader"/><bean:write name="gmPeriodicInfoForm" property="borrowerId"/></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<%
												String srcMenu = (String)session.getAttribute("mainMenu");
												String srcSubMenu = (String)session.getAttribute("subMenuItem");
												if((srcMenu != null) && (srcSubMenu != null) || srcMenu != null)
												{
												   if(srcMenu.equals(MenuOptions.getMenu(MenuOptions.GM_PERIODIC_INFO)))
												   {
												%>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5">
													<A href="javascript:submitForm('showOutstandingDetailsLink.do?method=showOutstandingDetailsLink')">Outstanding Details</A>|
													<A href="javascript:submitForm('showDisbursementDetailsLink.do?method=showDisbursementDetailsLink')">Disbursement Details</A> | 
													<A href="javascript:submitForm('showRepaymentDetailsLink.do?method=showRepaymentDetailsLink')">Repayment Details</A>| 
												<!--	<A href="javascript:submitForm('showNPADetailsLink.do?method=showNPADetailsLink')">NPA Details</A> -->
												</TD>
												<%
												   }
												}
												%>
											</TR>

										</TABLE>
									</TD>
								</TR>

			<tr>
              <td class="ColumnBackground" width="146" height="25">&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="recoveryDate"/></td>
              <td class="TableData" width="90"><table cellpadding="0" cellspacing="0">
                <tr>
                  <td><html:text property="dateOfRecovery" size="8" alt="Recovery Date" maxlength="10" name="gmPeriodicInfoForm"/></td>
                  <td><img src="images/CalendarIcon.gif" width="20" align="center"onClick="showCalendar('gmPeriodicInfoForm.dateOfRecovery')"></td>
                </tr>
              </table>
              </td>
              <td class="ColumnBackground" width="227" height="25"><font color="#FF0000" size="2">*</font><bean:message key="amountRecovered" /></td>
              <td class="ColumnBackground" width="210" height="25">&nbsp;<html:text property="amountRecovered"  size="10" name="gmPeriodicInfoForm" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" maxlength="16"/><bean:message key="inRs"/>
              </td>
            </tr>
            <tr>
              <td width="240" class="ColumnBackground" height="28" colspan="2"><bean:message key="legalCharges"/></td>
              <td width="441" class="ColumnBackground" height="28" colspan="2"><html:text property="legalCharges" size="10" name="gmPeriodicInfoForm" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" maxlength="16"/><bean:message key="inRs"/></td>
            </tr>
            <tr>
              <td class="ColumnBackground" width="240" colspan="2" height="43"><bean:message key="remarks"/></td>
              <td class="ColumnBackground" width="441" colspan="2" height="43">&nbsp;<html:textarea property="remarks" cols="40" alt="Remarks" name="gmPeriodicInfoForm"/></td>
            </tr>
            <tr>
              <td colspan="2" align="left" class="ColumnBackground" width="240" height="21">&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="isRecoveryByOts"/></td>
              <td colspan="2" align="left" class="ColumnBackground" width="441" height="21">&nbsp;<html:radio name="gmPeriodicInfoForm"value="Y" property="isRecoveryByOTS"><bean:message key="yes"/></html:radio>
              &nbsp;<html:radio name="gmPeriodicInfoForm"value="N" property="isRecoveryByOTS"><bean:message key="no"/></html:radio></td>
            </tr>
            <tr>
              <td class="ColumnBackground" width="240" colspan="2" height="21">&nbsp;<bean:message key="isRecoveryByAssets"/></td>
              <td class="ColumnBackground" width="441" colspan="2" height="21">&nbsp;&nbsp;<html:radio name="gmPeriodicInfoForm"value="Y" property="isRecoveryBySaleOfAsset"><bean:message key="yes"/></html:radio>
              &nbsp;<html:radio name="gmPeriodicInfoForm"value="N" property="isRecoveryBySaleOfAsset"><bean:message key="no"/></html:radio></td>
            </tr>
            <tr>
              <td class="ColumnBackground" width="240" colspan="2" height="43">&nbsp;<bean:message key="detailsOfAsset"/></td>
              <td class="ColumnBackground" width="441" colspan="2" height="43">&nbsp;<html:textarea property="detailsOfAssetSold"cols="40" alt="detailsOfAsset"name="gmPeriodicInfoForm"/>
              </td>
            </tr>  
         


		 </TABLE>
						</TD>
					</TR>
					<TR >
						<TD height="20" >
							&nbsp;
						</TD>
					</TR>
					<TR >
						<TD align="center" valign="baseline" >
							<DIV align="center">
								
									<A href="javascript:submitForm('saveRecoveryDetails.do?method=saveRecoveryDetails')">
									<IMG src="images/Save.gif" alt="OK" width="49" height="37" border="0"></A>
									<A href="javascript:document.gmPeriodicInfoForm.reset()">
									<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>
								<A href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
								<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>
							</DIV>
						</TD>
					</TR>
				</TABLE>
			</TD>
			<TD width="20" background="images/TableVerticalRightBG.gif">
				&nbsp;
			</TD>
		</TR>
		<TR>
			<TD width="20" align="right" valign="top">
				<IMG src="images/TableLeftBottom1.gif" width="20" height="15">
			</TD>
			<TD background="images/TableBackground2.gif">
				&nbsp;
			</TD>
			<TD width="20" align="left" valign="top">
				<IMG src="images/TableRightBottom1.gif" width="23" height="15">
			</TD>
		</TR>
	</html:form>
</TABLE>

