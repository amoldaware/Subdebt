<%@ page language="java"%>
<%@ page import="com.cgtsi.claim.ClaimConstants"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% 
  session.setAttribute("CurrentPage","displayListOfClaimRefNumbers.do?method=displayListOfClaimRefNumbers");
%>
  
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="applicationStatusWiseReportDetails.do?method=applicationStatusWiseReportDetails" method="POST" enctype="multipart/form-data">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/ReportsHeading.gif" width="121" height="25"></TD>
			<TD width="20"  valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="12"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="28%" class="Heading"><bean:message key="statusWiseHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>

									<TR>
									<TD width="10%" valign="top" class="ColumnBackground">
										Member Id
									</TD>
									<TD width="10%" valign="top" class="ColumnBackground">
										Member Name
									</TD>
									<TD width="10%"  valign="top" class="ColumnBackground">
									<div align="center">	Amount Settled </div><div align="center"><bean:message key="inRs"/></div>
									</TD>
									</TR>	
					
											<tr>
											<logic:iterate name="cpTcDetailsForm" id="object" property="settledClms">
											<%
											     java.util.HashMap map = (java.util.HashMap)object;
											     String memberId = (String)map.get(ClaimConstants.CLM_MEMBER_ID);
											     String url = "displaySettlementDetails.do?method=displaySettlementDetails&"+ ClaimConstants.CLM_MEMBER_ID+"=" + memberId; 	
											     String memberName = (String)map.get(ClaimConstants.CLM_MEMBER_NAME);
											     String totalSettlementStr = ((Double)map.get(ClaimConstants.CLM_TOTAL_SETTLMNT_AMNT)).toString();
											%>
													
											<TR>
												<TD width="10%"   class="ColumnBackground1">																	
												<html:link href="<%=url%>"><%=memberId%></html:link>
												</TD>
												<TD width="10%"   class="ColumnBackground1">					
												<%=memberName%>				
												</TD>
												<TD width="10%"   class="ColumnBackground1">					
												<div align="right">
                                                                                                <%=totalSettlementStr%></div>
												</TD>												
											</TR>
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
						<TD align="center" valign="baseline">
							<DIV align="center">
							<A href="javascript:submitForm('showFilterForSettlementReport.do?method=showFilterForSettlementReport')">
									<IMG src="images/Back.gif" alt="OK" width="49" height="37" border="0"></A>
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
			<TD width="20"  valign="top">
				<IMG src="images/TableRightBottom1.gif" width="23" height="15">
			</TD>
		</TR>
	</html:form>
</TABLE>       

