<%@ page language="java"%>
<%@ page import="com.cgtsi.risk.ExposureDetails"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","showExposureReport.do?method=showExposureReport");%>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
	<html:errors />
	<html:form action="showExposureReport.do?method=showExposureReport" method="POST">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/RiskManagementHeading.gif" width="121" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR> 
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<TABLE width="100%" align="left" border="0" cellspacing="0" cellpadding="0">
					<TR>
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
								<%
								String strState = "";
								String strMli = "";
								String strIndustry = "";
								String strGender = "";
								String strSocialCat = "";
								%>
								<TR> 
									<TD class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="scheme" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" colspan="3"> &nbsp;
										<DIV align="left">
											<bean:write name="rmForm" property="scheme" />
						                </DIV>
									</TD>
									<TD class="ColumnBackground"> &nbsp;
										<bean:message key="state" name="rmForm" />
									</TD>
									<logic:iterate name="rmForm" property="state" id="object">
									<%
										strState = strState + ", " + (String)object;
									%>
									</logic:iterate>
									<%
									strState=strState.substring(2);
									%> 
									<TD class="TableData" colspan="3"> &nbsp;
										<%=strState%>
									</TD>
								</TR>
								<TR> 
									<TD class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="mli" name="rmForm" />
										</DIV>
									</TD>
									<logic:iterate name="rmForm" property="mli" id="object">
									<%
										strMli = strMli + ", " + (String)object;
									%>
									</logic:iterate>
									<%
									strMli=strMli.substring(2);
									%>
									<TD class="TableData" colspan="3"> &nbsp;
										<DIV align="left">
											<%=strMli%>
						                </DIV>
									</TD>
									<TD class="ColumnBackground"> &nbsp;
										<bean:message key="industry" name="rmForm" />
									</TD>
									<logic:iterate name="rmForm" property="industry" id="object">
									<%
										strIndustry = strIndustry + ", " + (String)object;
									%>
									</logic:iterate>
									<%
									strIndustry=strIndustry.substring(2);
									%>
									<TD class="TableData" colspan="3"> &nbsp;
										<%=strIndustry%>
									</TD>
								</TR>
								<TR> 
									<TD class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="gender" name="rmForm" />
										</DIV>
									</TD>
									<logic:iterate name="rmForm" property="gender" id="object">
									<%
										strGender = strGender + ", " + (String)object;
									%>
									</logic:iterate>
									<%
									strGender=strGender.substring(2);
									%>
									<TD class="TableData" colspan="3"> &nbsp;
										<DIV align="left">
											<%=strGender%>
						                </DIV>
									</TD>
									<TD class="ColumnBackground"> &nbsp;
										<bean:message key="socialCategory" name="rmForm" />
									</TD>
									<logic:iterate name="rmForm" property="socialCategory" id="object">
									<%
										strSocialCat = strSocialCat + ", " + (String)object;
									%>
									</logic:iterate>
									<%
									strSocialCat=strSocialCat.substring(2);
									%>
									<TD class="TableData" colspan="3"> &nbsp;
										<%=strSocialCat%>
									</TD>
								</TR>
								<TR>
									<TD>
									</TD>
								</TR>
								<TR>
									<TD class="HeadingBg"> &nbsp;
										<bean:message key="state" name="rmForm" />
									</TD>
									<TD class="HeadingBg"> &nbsp;
										<bean:message key="memberId" name="rmForm" />
									</TD>
									<TD class="HeadingBg"> &nbsp;
										<bean:message key="borrowerId" name="rmForm" />
									</TD>
									<TD class="HeadingBg"> &nbsp;
										<bean:message key="cgpan" name="rmForm" />
									</TD>
									<TD class="HeadingBg"> &nbsp;
										<bean:message key="sancAmt" name="rmForm" />
									</TD>
									<TD class="HeadingBg"> &nbsp;
										<bean:message key="outstandingAmt" name="rmForm" />
									</TD>
									<TD class="HeadingBg"> &nbsp;
										<bean:message key="noOfClaims" name="rmForm" />
									</TD>
									<TD class="HeadingBg"> &nbsp;
										<bean:message key="totalClaim" name="rmForm" />
									</TD>
								</TR>
								<%
									DecimalFormat df= new DecimalFormat("######################.##");
									df.setDecimalSeparatorAlwaysShown(false);

									int i=0;
									String oldState="";
									String oldMemberId="";
									String oldBid="";
								%>
								<logic:equal property="facilityType" value="TC" name="rmForm">
								<TR>
									<TD class="HeadingBg" colspan="8"> &nbsp;
										<bean:message key="termLoan" name="rmForm" />
									</TD>									
								</TR>
								</logic:equal>
								<logic:equal property="facilityType" value="BO" name="rmForm">
								<TR>
									<TD class="HeadingBg" colspan="8"> &nbsp;
										<bean:message key="termLoan" name="rmForm" />
									</TD>									
								</TR>
								</logic:equal>
								<logic:iterate name="rmForm" property="tcExposureDetails" id="object">
								<%
									ExposureDetails exposureDetails=(ExposureDetails) object;

									String memberId;
									String state;
									String bid;
									String cgpan;
									double sancAmt;
									double osAmt;
									int noOfClaims;
									double totalClaim;
									String strSancAmt;
									String strOsAmt;
									String strTotalClaim;

									memberId = exposureDetails.getMemberId();
									state = exposureDetails.getState();
									bid = exposureDetails.getBid();
									cgpan = exposureDetails.getCgpan();
									sancAmt=exposureDetails.getSancAmt();
									osAmt=exposureDetails.getOsAmt();
									noOfClaims=exposureDetails.getNoOfClaims();
									totalClaim=exposureDetails.getTotalClaim();

									strSancAmt = df.format(sancAmt);
									strOsAmt = df.format(osAmt);
									strTotalClaim = df.format(totalClaim);
								%>
								<TR>
										<%
										if (! state.equals(oldState))
										{
											oldState = state;
										%>
									<TD class="TableData"> &nbsp;
										<%=state%>
									</TD>
										<%}
										else
										{%>
									<TD class="TableData"> &nbsp;
									</TD>
										<%}%>
										<%
										if (! memberId.equals(oldMemberId))
										{
											oldMemberId = memberId;
										%>
									<TD class="TableData"> &nbsp;
										<%=memberId%>
									</TD>
										<%}
										else
										{%>
									<TD class="TableData"> &nbsp;
									</TD>
										<%}%>
										<%
										if (! bid.equals(oldBid))
										{
											oldBid = bid;
										%>
									<TD class="TableData"> &nbsp;
										<%=bid%>
									</TD>
										<%}
										else
										{%>
									<TD class="TableData"> &nbsp;
									</TD>
										<%}%>
									<TD class="TableData"> &nbsp;
										<%=cgpan%>
									</TD>
									<TD class="TableData"> &nbsp;
											<%= strSancAmt%>
									</TD>
									<TD class="TableData"> &nbsp;
											<%= strOsAmt%>
									</TD>
									<TD class="TableData"> &nbsp;
											<%= noOfClaims%>
									</TD>
									<TD class="TableData"> &nbsp;
											<%= strTotalClaim%>
									</TD>
								</TR>
								</logic:iterate>
								<logic:equal property="facilityType" value="WC" name="rmForm">
								<TR>
									<TD class="HeadingBg" colspan="8"> &nbsp;
										<bean:message key="workingCapital" name="rmForm" />
									</TD>									
								</TR>
								</logic:equal>
								<logic:equal property="facilityType" value="BO" name="rmForm">
								<TR>
									<TD class="HeadingBg" colspan="8"> &nbsp;
										<bean:message key="workingCapital" name="rmForm" />
									</TD>									
								</TR>
								</logic:equal>
								<%
									oldMemberId="";
									oldState="";
									oldBid="";
								%>
								<logic:iterate name="rmForm" property="wcExposureDetails" id="object">
								<%
									ExposureDetails exposureDetails=(ExposureDetails) object;

									String memberId;
									String state;
									String bid;
									String cgpan;
									double sancAmt;
									double osAmt;
									int noOfClaims;
									double totalClaim;
									String strSancAmt;
									String strOsAmt;
									String strTotalClaim;

									memberId = exposureDetails.getMemberId();
									state = exposureDetails.getState();
									bid = exposureDetails.getBid();
									cgpan = exposureDetails.getCgpan();
									sancAmt=exposureDetails.getSancAmt();
									osAmt=exposureDetails.getOsAmt();
									noOfClaims=exposureDetails.getNoOfClaims();
									totalClaim=exposureDetails.getTotalClaim();

									strSancAmt = df.format(sancAmt);
									strOsAmt = df.format(osAmt);
									strTotalClaim = df.format(totalClaim);
								%>
								<TR>
										<%
										if (! state.equals(oldState))
										{
											oldState = state;
										%>
									<TD class="TableData"> &nbsp;
										<%=state%>
									</TD>
										<%}
										else
										{%>
									<TD class="TableData"> &nbsp;
									</TD>
										<%}%>
										<%
										if (! memberId.equals(oldMemberId))
										{
											oldMemberId = memberId;
										%>
									<TD class="TableData"> &nbsp;
										<%=memberId%>
									</TD>
										<%}
										else
										{%>
									<TD class="TableData"> &nbsp;
									</TD>
										<%}%>
										<%
										if (! bid.equals(oldBid))
										{
											oldBid = bid;
										%>
									<TD class="TableData"> &nbsp;
										<%=bid%>
									</TD>
										<%}
										else
										{%>
									<TD class="TableData"> &nbsp;
									</TD>
										<%}%>
									<TD class="TableData"> &nbsp;
										<%=cgpan%>
									</TD>
									<TD class="TableData"> &nbsp;
											<%= strSancAmt%>
									</TD>
									<TD class="TableData"> &nbsp;
											<%= strOsAmt%>
									</TD>
									<TD class="TableData"> &nbsp;
											<%= noOfClaims%>
									</TD>
									<TD class="TableData"> &nbsp;
											<%= strTotalClaim%>
									</TD>
								</TR>
								</logic:iterate>
							</TABLE>
						</TD>
					</TR>
					<TR >
						<TD align="center" valign="baseline" >
							<DIV align="center">
								<A href="javascript:history.back()">
									<IMG src="images/Back.gif" alt="Back" width="49" height="37" border="0"></A>
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