<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.lang.Double"%>
<%@page import ="java.text.SimpleDateFormat"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="com.cgtsi.investmentfund.ChequeDetails"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","showBankRecon.do?method=showBankRecon");%>
<% 
	String focusObj="cgtsiBalance";
	org.apache.struts.action.ActionErrors errors = (org.apache.struts.action.ActionErrors)request.getAttribute(org.apache.struts.Globals.ERROR_KEY);
	if (errors!=null && !errors.isEmpty())
	{
		focusObj="test";
	}
%>
<%
DecimalFormat df= new DecimalFormat("######################.##");
df.setDecimalSeparatorAlwaysShown(false);
%>
<body onload="displayBankReconTotal()">
<html:form action="updateBankRecon.do?method=updateBankRecon" method="POST" enctype="multipart/form-data">
<html:hidden name="ifForm" property="test"/>
<html:errors />
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/InvestmentManagementHeading.gif" width="175" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
							<tr>
							  <TD>			
									<DIV align="right">			
										<A HREF="javascript:submitForm('helpBankRecon.do')">
										HELP</A>
									</DIV>
								</td>
							  </tr>
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="10"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="27%" class="Heading"><bean:message key="bankReconHeading" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>

										<TR>
									<TD align="left" valign="top" class="ColumnBackground">
										  &nbsp;<bean:message key="balanceAsPerCGTSI" /> 
									</TD>
									   <TD  align="left" valign="center" class="tableData">
										  <html:text property="cgtsiBalance" size="20"  alt="Reference" name="ifForm" maxlength="16"  onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onblur="displayBankReconTotal()"/>
									  </TD>

									  </TR>
										<bean:define id="chqVal" name="ifForm" property="chequeIssuedAmount"/>
										<%
											double chq=((Double)chqVal).doubleValue();
											String strChq=df.format(chq);
										%>

										<TR>
									<TD align="left" valign="top" class="ColumnBackground">
										  &nbsp;<bean:message key="chqIssuedAmount" /> 
									</TD>
									   <TD  align="left" valign="center" class="tableData">
										  <html:text property="chequeIssuedAmount" size="20"  alt="Reference" name="ifForm" maxlength="16"  onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onblur="displayBankReconTotal()" value="<%=strChq%>"/>
									  </TD>

									  </TR>
										<TR>
									<TD align="left" valign="top" class="ColumnBackground">
										  &nbsp;<bean:message key="directCredit" /> 
									</TD>
									   <TD  align="left" valign="center" class="tableData">
										  <html:text property="directCredit" size="20"  alt="Reference" name="ifForm" maxlength="16"  onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onblur="displayBankReconTotal()"/>
									  </TD>

									  </TR>
										<TR>
									<TD align="left" valign="top" class="ColumnBackground">
										  &nbsp;<bean:message key="directDebit" /> 
									</TD>
									   <TD  align="left" valign="center" class="tableData">
										  <html:text property="directDebit" size="20"  alt="Reference" name="ifForm" maxlength="16"  onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" onblur="displayBankReconTotal()"/>
									  </TD>

									  </TR>
										<TR>
									<TD align="left" valign="top" class="ColumnBackground">
										  &nbsp;<bean:message key="total" /> 
									</TD>
									   <TD  align="left" valign="center" class="tableData" id="total">

									  </TD>

									  </TR>
										<TR>
									<TD align="left" valign="top" class="ColumnBackground">
										  &nbsp;<bean:message key="closingBalanceAsPerIDBI" /> 
									</TD>
										<bean:define id="closeVal" name="ifForm" property="closingBalanceIDBI"/>
										<%
											double close=((Double)closeVal).doubleValue();
											String strClose=df.format(close);
										%>
									   <TD  align="left" valign="center" class="tableData">
										  <html:text property="closingBalanceIDBI" size="20"  alt="Reference" name="ifForm" maxlength="16"  onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" value="<%=strClose%>"/>
									  </TD>
									  </TR>
									<html:hidden name="ifForm" property="reconDate"/>
							</TABLE>
						</TD>
					</TR>

					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<tr>
									<td class="SubHeading" colspan="6">
									<bean:message key="PendingChequeDetails"/> &nbsp; <bean:write name="ifForm" property="reconDate"/></td>
								</tr>
								<TR>
									<TD align="center" valign="top" class="ColumnBackground">
									<div align="center">
										&nbsp;&nbsp;<bean:message key="SlNo" />
										</div>
									</TD>
									<TD align="left" valign="top" class="ColumnBackground">
									<div align="center">
										&nbsp;&nbsp;<bean:message key="DateOfDep" />
										</div>
									</TD>
									<TD align="left" valign="top" class="ColumnBackground">
									<div align="center">
										&nbsp;&nbsp;<bean:message key="chqNo" />
										</div>
									</TD>
									<TD align="left" valign="top" class="ColumnBackground">
									<div align="center">
										&nbsp;&nbsp;<bean:message key="chqDate" />
										</div>
									</TD>
									<TD align="left" valign="top" class="ColumnBackground">
									<div align="center">
										&nbsp;&nbsp;<bean:message key="amount" />
										&nbsp;<bean:message key="inRs" />
										</div>
									</TD>
									<TD align="left" valign="top" class="ColumnBackground">
									<div align="center">
										&nbsp;&nbsp;<bean:message key="remarks" />
										</div>
									</TD>
								</tr>
								<%
									int counter=0;
									SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
									DecimalFormat decimalFormat = new DecimalFormat("############.##");
								%>
								<logic:iterate id="cd" name="ifForm" property="chequeDetails">
								<%
									ChequeDetails chequeDetails = (ChequeDetails)cd;
									Date dtPrDate = chequeDetails.getPresentedDate();
									String prDate = "";
									if (dtPrDate!= null && !dtPrDate.toString().equals(""))
									{
										prDate=dateFormat.format(dtPrDate);
									}
									String remarks = "";
									if (chequeDetails.getChequeRemarks()!=null && !chequeDetails.getChequeRemarks().equalsIgnoreCase("null"))
									{
										remarks=chequeDetails.getChequeRemarks();
									}
								%>
								<TR>
									<TD align="center" valign="top" class="tableData">
									<div align="center">
										&nbsp;&nbsp;<%=counter+1%>
										</div>
									</TD>
									<TD align="left" valign="top" class="tableData">
									<div align="center">
										&nbsp;&nbsp;<%= prDate%>
										</div>
									</TD>
									<TD align="left" valign="top" class="tableData">
									<div align="center">
										&nbsp;&nbsp;<%= chequeDetails.getChequeNumber()%>
										</div>
									</TD>
									<TD align="left" valign="top" class="tableData">
									<div align="center">
										&nbsp;&nbsp;<%= dateFormat.format(chequeDetails.getChequeDate())%>
										</div>
									</TD>
									<TD align="left" valign="top" class="tableData">
									<div align="center">
										&nbsp;&nbsp;<%= decimalFormat.format(chequeDetails.getChequeAmount())%>
										</div>
									</TD>
									<TD align="left" valign="top" class="tableData">
									<div align="center">
										&nbsp;&nbsp;<%= remarks%>
										</div>
									</TD>
								</tr>
								<%counter++;%>
								</logic:iterate>
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
							        <A href="javascript:submitForm('updateBankRecon.do?method=updateBankRecon')">
									<IMG src="images/OK.gif" alt="OK" width="49" height="37" border="0"></A>
								<A href="javascript:document.ifForm.reset()">
									<IMG src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></A>
									
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

