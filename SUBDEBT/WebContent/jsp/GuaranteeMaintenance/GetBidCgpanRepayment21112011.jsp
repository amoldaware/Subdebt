<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>

<% session.setAttribute("CurrentPage","GetBidCgpanRepayment.do?method=GetBidCgpanRepayment");%>

<%
String focusField = "";
%>
<logic:equal property="periodicBankId" value="0000" name="gmPeriodicInfoForm">
<%focusField = "memberId";%>
</logic:equal>

<logic:notEqual property="periodicBankId" value="0000" name="gmPeriodicInfoForm">
<%focusField = "borrowerId";%>
</logic:notEqual>

	<html:errors />
	<html:form action="showRepaymentDetails.do?method=showRepaymentDetails" method="POST" focus="<%=focusField%>">


<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
		<TR>
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/GuaranteeMaintenanceHeading.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
			<DIV align="right">
				<A HREF="javascript:submitForm('helpRepaymentDetailsFilter.do?method=helpRepaymentDetailsFilter')">
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
												<TD width="31%" class="Heading"><bean:message key="modifyBorrowerHeader"/></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>
								</TR>
					 <logic:equal property="periodicBankId" value="0000" name="gmPeriodicInfoForm">
								<TR align="left" valign="top">
									<TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font><bean:message key="MemberID"/>
									</TD>
									<TD align="left" valign="top" class="TableData" colspan="4">
										<html:text property="memberId" size="20" alt="MemberID" maxlength ="12" name="gmPeriodicInfoForm"/>
									</TD>
								</TR>
					</logic:equal>
					<logic:notEqual property="periodicBankId" value="0000" name="gmPeriodicInfoForm">
						 		<TR align="left" valign="top">
									<TD align="left" valign="top" class="ColumnBackground"><bean:message key="MemberID"/>
									</TD>
									<TD align="left" valign="top" class="TableData" colspan="4">
										<bean:write property="memberId" name="gmPeriodicInfoForm"/>
									</TD>
								</TR>
					</logic:notEqual>
								 <TR align="left" valign="top">
									<TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font><bean:message key="BorrowerID"/>
									</TD>
									<TD align="left" valign="top" class="TableData">
										<html:text property="borrowerId" size="20" maxlength ="9"  name="gmPeriodicInfoForm"/>
										&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="or"/>
									</TD>
									<TD align="left" valign="top" class="ColumnBackground"><bean:message key="cgpan"/>
									</TD>
									<TD align="left" valign="top" class="TableData">
									 <html:text property="cgpan" size="20" alt="CGPAN" maxlength ="13"  name="gmPeriodicInfoForm"/>
									</TD>
				</TR>

				<TR align="left" valign="top">
					<TD align="left" valign="top" 								  class="ColumnBackground"><bean:message key="ssiName"/></TD>
					<TD align="left" valign="top" class="TableData" colspan="4">
					<html:text property="borrowerName" size="30" alt="SSI Name" name="gmPeriodicInfoForm"/>
					</TD>
				</TR>




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

									<A href="javascript:submitForm('showRepaymentDetails.do?method=showRepaymentDetails')">
									<IMG src="images/OK.gif" alt="OK" width="49" height="37" border="0"></A>
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
