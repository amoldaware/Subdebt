<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>
<%@ page import="com.cgtsi.receiptspayments.PaymentList"%>
<%@ page import="com.cgtsi.actionform.RPActionForm"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","daywiseddmarkedforDepositedCases.do?method=daywiseddmarkedforDepositedCases");%>


<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="daywiseddmarkedforDepositedCases.do?method=dayWiseddMarkedForDepositedSummary" method="POST" enctype="multipart/form-data">
			<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<DIV align="right">			
					
				</DIV>

				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
							<table width="100%" border="0" cellspacing="1" cellpadding="0">
							<tr> 
							  <td colspan="2">
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR>
								<TD width="31%" class="Heading"><bean:message key="paymentDetails"/></TD>
                <TD width="69%"><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
									</TR>
									<TR>
										<TD colspan="2" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
									</TR>
								</TABLE>
							  </td>
							</tr>
							<tr align="left"> 
								<td  class="ColumnBackground"> 
									&nbsp;									
									<font color="#FF0000" size="2">*</font>	&nbsp; Confirm Deposit Date &nbsp;
								</td>
									<td  class="TableData">
										<html:text property="dateOfTheDocument24" size="15" alt="dateOfTheDocument24" name="rpAllocationForm" maxlength="10"/> 								
									  <IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rpAllocationForm.dateOfTheDocument24')" align="center">
										<DIV align="left">
                  </td>
								 </tr>
						  </table>									
						</td>
					</tr>

					
					<TR >
						<TD align="center" valign="baseline">
							<DIV align="center">
								<A href="javascript:submitForm('daywiseddmarkedforDepositedCases.do?method=dayWiseddMarkedForDepositedSummary')"><IMG src="images/OK.gif" alt="Save" width="49" height="37" border="0"></A>	
								<A href="javascript:document.appForm.reset()">
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







					