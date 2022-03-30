<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.cgtsi.actionform.APForm"%>
<%@ page import="com.cgtsi.investmentfund.ChequeDetails"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","showUnUtilizedChqDetails.do?method=showUnUtilizedChqDetails");%>
<%
String name="";
%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="saveCancelledCheques.do?method=saveCancelledCheques" method="POST" enctype="multipart/form-data">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/InvestmentManagementHeading.gif" width="180" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="7"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="24%" class="Heading"><bean:message key="ChequeInsertDetails" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>
								</TR>

								<tr>
									<td colspan="3">

									<TABLE width="100%" border="0" cellpadding="1" cellspacing="1">
										<tr> 
										  <td align="center" class="HeadingBg" ><div align="center"><bean:message key = "srNo"/></div>
										  </td>

										  <td align="center" class="HeadingBg" ><div align="center"><bean:message key = "chequeNumber"/></div>
										  </td>
										  <td class="HeadingBg" align="center"><div align="center"><bean:message key="chequeCancellation"/></div>
										  </td>								  
									  </tr>				  

									  <%int k=0;%>

										<logic:iterate id="object" name="investmentForm" property="unutilizedChqDetails">
										   <%
										   com.cgtsi.investmentfund.ChequeDetails chequeDetails = (com.cgtsi.investmentfund.ChequeDetails)object;

										   int chqIdInt = chequeDetails.getChqNumber();
											
											String chqId = (new Integer(chqIdInt)).toString();
										   %>

										   <tr align="center">
										  
												<td class="tableData" align="center">&nbsp;<%=(k+1)%>
												</td>
												<td class="tableData" align="center" >&nbsp;
													<%name="chequeId(key-"+k+")";%>
													<div align="center">
														<%=chqId%>
														<html:hidden property = "<%=name%>" name="investmentForm" value="<%=chqId%>"/>
													</div>
												</td>
												<td class="tableData" align="center">&nbsp;
												<%name="cancelledChq(key-"+k+")";%>
														<html:checkbox property="<%=name%>" alt="Close" name="investmentForm" value="Y" />

												</td>

											</tr>

											<%++k;%>
										</logic:iterate>
								</TABLE>


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
								<A href="javascript:submitForm('saveCancelledCheques.do?method=saveCancelledCheques')">
									<IMG src="images/Save.gif" alt="Back" width="49" height="37" border="0"></A>
								<A href="javascript:document.investmentForm.reset()">
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

