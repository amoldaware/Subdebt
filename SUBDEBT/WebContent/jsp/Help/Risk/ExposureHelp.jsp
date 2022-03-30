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
								<TR> 
									<TD colspan="8">
										<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
											<TR> 
												<TD width="25%" height="19" class="Heading">&nbsp;
													<bean:message key="exposureInputHeading" />
												</TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR> 
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>
										</TABLE>
									</TD>
								</TR>

					 <tr>
						  <TD width="30%" align="left" valign="top" class="ColumnBackground">
							<DIV align="left">
								&nbsp;Scheme
							</DIV>
						   </TD>
						   <TD align="left" valign="top" class="TableData">
								&nbsp;Select the Scheme for which the Exposure is to be calculated.
							</TD>
					</tr>
					 <tr>
						  <TD width="30%" align="left" valign="top" class="ColumnBackground">
							<DIV align="left">
								&nbsp;State
							</DIV>
						   </TD>
						   <TD align="left" valign="top" class="TableData">
								&nbsp;Select the State(s) for which the Exposure is to be calculated.
							</TD>
					</tr>
					 <tr>
						  <TD width="30%" align="left" valign="top" class="ColumnBackground">
							<DIV align="left">
								&nbsp;MLI
							</DIV>
						   </TD>
						   <TD align="left" valign="top" class="TableData">
								&nbsp;Select the MLI(s) for which the Exposure is to be calculated.
							</TD>
					</tr>
					 <tr>
						  <TD width="30%" align="left" valign="top" class="ColumnBackground">
							<DIV align="left">
								&nbsp;Industry Sector
							</DIV>
						   </TD>
						   <TD align="left" valign="top" class="TableData">
								&nbsp;Select the Industry Sector(s) for which the Exposure is to be calculated.
							</TD>
					</tr>
 					 <tr>
						  <TD width="30%" align="left" valign="top" class="ColumnBackground">
							<DIV align="left">
								&nbsp;Gender
							</DIV>
						   </TD>
						   <TD align="left" valign="top" class="TableData">
								&nbsp;Select the Gender(s) for which the Exposure is to be calculated.
							</TD>
					</tr>
 					 <tr>
						  <TD width="30%" align="left" valign="top" class="ColumnBackground">
							<DIV align="left">
								&nbsp;Social Category
							</DIV>
						   </TD>
						   <TD align="left" valign="top" class="TableData">
								&nbsp;Select the Social Category(s) for which the Exposure is to be calculated.
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
