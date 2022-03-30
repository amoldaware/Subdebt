<%@ page language="java"%>
<%@ page import="com.cgtsi.util.SessionConstants"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<form>
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<TABLE width="100%" border="0" cellspacing="1" cellpadding="0">
					 <tr>
						  <TD width="20%" align="left" valign="top" class="ColumnBackground">
														<DIV align="left">
															&nbsp;
													Decision
														</DIV>
													</TD>
						   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
								Select a decision from the combo box.			
							</TD>
					</tr>
					 <tr>
						  <TD width="20%" align="left" valign="top" class="ColumnBackground">
														<DIV align="left">
															&nbsp;
													Approved Amount
														</DIV>
													</TD>
						   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
								Enter the amount for which the application has to be approved.
								Approved Amount has to be entered when the decision for an application is chosen as 'Accept'.The amount should not be greater than the 'credit to be guaranteed amount' for the application.
							</TD>
					</tr>
					 <tr>
						  <TD width="20%" align="left" valign="top" class="ColumnBackground">
														<DIV align="left">
															&nbsp;
													Comments
														</DIV>
													</TD>
						   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
								Enter the comments for the application.Comments is mandatory if decision taken is 'Hold','Reject' or 'Pending'.
							</TD>
					</tr>
				</table>
			<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD align="center" valign="baseline" colspan="7">
							<DIV align="center">
							<A href="javascript:history.back()">
									<IMG src="images/Back.gif" alt="Back" width="49" height="37" border="0"></A>
							</DIV>
						</TD>
					</TR>	
			</table>
				
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
	</form>
</TABLE>
</body>
