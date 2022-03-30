<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@page import ="java.text.SimpleDateFormat"%>
<%@page import ="java.text.DecimalFormat"%>

<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>
<%DecimalFormat decimalFormat = new DecimalFormat("##########.##");%>
<% session.setAttribute("CurrentPage","securitizationReportDetails.do?method=securitizationReportDetails ");%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="securitizationReportDetailsForCgpan.do?method=securitizationReportDetailsForCgpan" method="POST" enctype="multipart/form-data">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/ReportsHeading.gif" width="121" height="25"></TD>
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
									<TD colspan="16"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="40%" class="Heading"><bean:message key="selectionCriteria" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>
									<%
									String strState = "";
									String strSector = "";
									String strMli = "";
									%>
									<logic:iterate name="rsForm" property="securitizationReport1" id="object">
									<%
									com.cgtsi.reports.Securitization size =  (com.cgtsi.reports.Securitization)object;
										strMli = strMli + ", " + size.getMemberid();
									%>
									</logic:iterate>
									<%
									if (strMli.length()>0)
									{
										strMli=strMli.substring(2);
									}
									%>
									<logic:iterate id="object"  name="rsForm"  property="securitizationReport2">  
										<%
											 com.cgtsi.reports.Securitization size =  (com.cgtsi.reports.Securitization)object;
											strState = strState + ", " + size.getState();
										%>
									</logic:iterate>
									<%
									if (strState.length()>0)
									{
										strState=strState.substring(2);
									}
									%>
									<logic:iterate id="object"  name="rsForm"  property="securitizationReport3">  
										<%
											 com.cgtsi.reports.Securitization size =  (com.cgtsi.reports.Securitization)object;
											 strSector = strSector + ", " + size.getSector();
										%>
									</logic:iterate>
									<%
									if (strSector.length()>0)
									{
									strSector=strSector.substring(2);
									}
									%>
									<TR>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="mli" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground1">
										<%=strMli%>
									</TD>
									</TR>	

									<TR>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="state" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground1">
										<%=strState%>
									</TD>
									</TR>
		
									<TR>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="sector" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground1">
										<%=strSector%>
									</TD>
									</TR>
									<%
									com.cgtsi.reports.Securitization size =null;
									%>
									<logic:iterate id="object"  name="rsForm"  property="securitizationReport4">  
										<%
											 size =  (com.cgtsi.reports.Securitization)object;
										%>
									</logic:iterate>
									<TR>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="loanTenure" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
									<%=size.getTenure()%>
									</TD>
									</TR>
									<TR>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="effectiveFrom"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground1">
									<%   java.util.Date utilDate=size.getTenureDate();
									String formatedDate = null;
									if(utilDate != null)
									{
									formatedDate=dateFormat.format(utilDate);
									}
									else
									{
									formatedDate = "";
									}
									%>
									<%=formatedDate%>
									</TD>
									</TR>
									<TR>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="loanType"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
									<%=size.getLoanType()%>
									</TD>
									</TR>
									<TR>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="loanMin"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground1">			
									<div align="left">
									<%=size.getMinLoan()%>
									</div>
									</TD>
									</TR>
									<TR>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="loanMax"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
									<div align="left">
									<%double maxLoan=size.getMaxLoan();%>
									<%=decimalFormat.format(maxLoan)%>
									</div>
									</TD>
									</TR>
									<TR>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="interestType"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
									<%=size.getInterestType()%>
									</TD>
									</TR>
									<TR>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="interestMin"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
									<%=size.getMinInterest()%>
									</TD>
									</TR>
									<TR>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="interestMax"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
									<%=size.getMaxInterest()%>
									</TD>
									</TR>
									<TR>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="trackRecord"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
									<%=size.getTrackRecord()%>
									</TD>
									</TR>
							</TABLE>
								<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="16"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="40%" class="Heading"><bean:message key="loanPoolDetails" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>

											
									<TR>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="slpName" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="spvName" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="rating" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="ratingName" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="amount"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="interest"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="securitizationDate"/>
									</TD>
									</TR>	

									<tr>
									<logic:iterate id="object"  name="rsForm"  property="securitizationReport5">  
										<%
											 size =  (com.cgtsi.reports.Securitization)object;
										%>

										<TR>
										<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
										<%=size.getSlpname()%>
										</TD>
										<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
										<%=size.getSpvName()%>
										</TD>
										<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
										<%=size.getRating()%>
										</TD>
										<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
										<%=size.getRatingAgency()%>
										</TD>
										<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
										<div align="right">
										<%=size.getAmount()%>
										</div>
										</TD>
										<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
										<div align="right">
										<%=size.getInterest()%>
										</div>
										</TD>
										<TD width="10%" align="left" valign="top" class="ColumnBackground1">		
									<%   java.util.Date utilDate1=size.getSecuritizationDate();
										String formatedDate1 = null;
										if(utilDate1 != null)
										{
										formatedDate1=dateFormat.format(utilDate1);
										}
										else
										{
										formatedDate1 = "";
										}
										%>
										<%=formatedDate1%>										
										</TD>
										</TR>
											</logic:iterate>

							</TABLE>
		
								<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="16"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="40%" class="Heading"><bean:message key="applicationDetails" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>
								</TR>

											
									<TR>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="memberId" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">		<bean:message key="cgpan" />
									</TD>
									</TR>	
									<logic:iterate id="object"  name="rsForm"  property="securitizationReport6">  
										<%
											 size =  (com.cgtsi.reports.Securitization)object;
										%>
											<TR>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%=size.getMemberid()%>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%String mli =size.getCgpan();
											  String url1 = "securitizationReportDetailsForCgpan.do?method=securitizationReportDetailsForCgpan&number="+mli;%>
											  <html:link href="<%=url1%>"><%=mli%></html:link>
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
						<TD align="center" valign="baseline" >
							<DIV align="center">
						
								<A href="javascript:submitForm('securitizationReport.do?method=securitizationReport')">
									<IMG src="images/Back.gif" alt="Back" width="49" height="37" border="0"></A>
									
									<A href="javascript:printpage()">
									<IMG src="images/Print.gif" alt="Print" width="49" height="37" border="0"></A>
								
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
