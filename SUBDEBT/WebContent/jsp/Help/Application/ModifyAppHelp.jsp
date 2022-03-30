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
						  <TD width="20%" align="left" valign="top" class="ColumnBackground" colspan="2">
								<DIV align="left">
									&nbsp;
							This screen is to view and modify any application based on different combinations of input fields entered.
								</DIV>
							</TD>
					</tr>

					 <tr>
						  <TD width="20%" align="left" valign="top" class="ColumnBackground">
														<DIV align="left">
															&nbsp;
													Member ID
														</DIV>
													</TD>
						   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
								Any application belonging to the entered Member ID and any other input parameter can be viewed.This field is mandatory.
							</TD>
					</tr>
					 <tr>
						  <TD width="20%" align="left" valign="top" class="ColumnBackground">
														<DIV align="left">
															&nbsp;
													Borrower ID
														</DIV>
													</TD>
						   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
								For the entered Member ID and Borrower ID, the list of CGPANs and Application Reference Numbers links are listed.On clicking any of the links, application can be viewed and modified.
							</TD>
					</tr>
					 <tr>
						  <TD width="20%" align="left" valign="top" class="ColumnBackground">
														<DIV align="left">
															&nbsp;
														CGPAN
														</DIV>
													</TD>
						   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
								For the entered Member ID and CGPAN,the application details can be directly viewed and modified.

							</TD>
					</tr>
					 <tr>
						  <TD width="20%" align="left" valign="top" class="ColumnBackground">
														<DIV align="left">
															&nbsp;
													Application Reference No.
														</DIV>
													</TD>
						   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
								For the entered Member ID and Application Reference Number,the application details can be directly viewed and modified.
							</TD>
					</tr>
					 <tr>
						  <TD width="20%" align="left" valign="top" class="ColumnBackground">
														<DIV align="left">
															&nbsp;
													Borrower Name
														</DIV>
													</TD>
						   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
							For the entered Member ID and Borrower Name, the list of CGPANs and Application Reference Numbers links are listed.On clicking any of the links, application can be viewed and modified.

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
