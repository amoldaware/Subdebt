<%@ page language="java"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<% session.setAttribute("CurrentPage","getHomogenousLoans.do?method=getHomogenousLoans");%>
<HTML>
	<BODY>
		<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
			<html:errors />
			<html:form action="showPoolDetails.do" method="POST">
				<TR>
					<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
					<TD background="images/TableBackground1.gif">
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
											<TD colspan="5">
												<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
													<TR>
														<TD width="31%" class="Heading"><bean:message key="loanPool"/></TD>
														<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
													</TR>
													<TR>
														<TD colspan="4" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
													</TR>

												</TABLE>
											</TD>
										</TR>
										<TR>
											<TD class="ColumnBackground">
												<bean:message key="state"/>
											</TD>
											<TD class="ColumnBackground">
												<bean:message key="mli"/>
											</TD>
											<TD class="ColumnBackground">
												<bean:message key="noOfLoans"/>
											</TD>
											<TD class="ColumnBackground">
												<bean:message key="totalSanctionedAmt"/>(<bean:message key="inRs"/>)
											</TD>
											<TD class="ColumnBackground">
												<bean:message key="totalGuaranteedAmount"/>(<bean:message key="inRs"/>)
											</TD>
										</TR>
											<bean:define id="stateWiseDtls" name="securitizationForm" property="stateWise"/>
											<%
												com.cgtsi.securitization.StateWise stateWiseData=(com.cgtsi.securitization.StateWise)stateWiseDtls;

												String state=stateWiseData.getState();
												java.util.ArrayList mliWiseData=stateWiseData.getMliWise();
												pageContext.setAttribute("mliWiseData",mliWiseData);
												double totalGC=0;
												double totalSanctioned=0;

											%>
											<logic:iterate id="mliWise" name="mliWiseData" >
											<%
												com.cgtsi.securitization.MLIWise mliWiseDtls=(com.cgtsi.securitization.MLIWise)mliWise;
												totalGC+=mliWiseDtls.getTotalGC();

												totalSanctioned+=mliWiseDtls.getTotalSanctionedAmt();
											%>
											<TR>
												<TD class="ColumnBackground">
													<%=state%>
													<% state=""; %>
												</TD>
												<TD class="ColumnBackground">
													<bean:write property="mliName" name="mliWise"/>
												</TD>
												<TD class="ColumnBackgroundRight">
													<bean:write property="noOfLoans" name="mliWise"/>
												</TD>
												<TD class="ColumnBackgroundRight">
													<bean:write property="totalSanctionedAmt" name="mliWise"/>
												</TD>
												<TD class="ColumnBackgroundRight">
													<bean:write property="totalGC" name="mliWise"/>
												</TD>
											</TR>
											</logic:iterate>
											<TR>
												<TD colspan="3" class="HeadingBg">
													Grand Total
												</TD>
												<TD class="HeadingBgRight">
													<%=totalSanctioned%>
												</TD>
												<TD  class="HeadingBgRight">
													<%=totalGC%>
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
										<A href="javascript:history.back()">
										<IMG src="images/Back.gif" alt="Cancel" width="49" height="37" border="0"></A>
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
	</BODY>
</HTML>



