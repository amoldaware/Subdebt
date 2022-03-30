<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@page import ="java.text.SimpleDateFormat"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@page import ="java.text.DecimalFormat"%>
<%DecimalFormat decimalFormat = new DecimalFormat("##########.##");%>
<% session.setAttribute("CurrentPage","applicationRecievedReportDetails.do?method=applicationRecievedReportDetails");
String date = request.getParameter("applicationDate");
String promoter = request.getParameter("promoter");
String itpan =request.getParameter("itpan");
String ssiDetails = request.getParameter("ssiDetails");
String industryType = request.getParameter("industryType");
String tcSanctioned =request.getParameter("termCreditSanctioned");
String tcInterest = request.getParameter("tcInterest");
String tcTenure = request.getParameter("tcTenure");
String tcPlr =request.getParameter("tcPlr");
String tcOutlay = request.getParameter("tcOutlay");
String wcSanctioned = request.getParameter("workingCapitalSanctioned");
String wcPlr = request.getParameter("wcPlr");
String wcOutlay = request.getParameter("wcOutlay");
String remarks = request.getParameter("rejection");
%>
<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="applicationRecievedReportDetails.do?method=applicationRecievedReportDetails" method="POST" enctype="multipart/form-data">
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
									<TD colspan="14"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="28%" class="Heading"><bean:message key="recievedApplicationReportHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>

									<TR>
									<% if  (date != null)  
									{
										
									%>
										<TD align="left" valign="top" class="ColumnBackground">
										<bean:message key="applicationDate"/>
										</TD>
									<%
									}
									%>
								
									<% if  (promoter != null)
									{
										
									%>
									<TD align="left" valign="top" class="ColumnBackground">
										<bean:message key="promoter"/>
									</TD>
									<%
									}
									%>

									<% if  (itpan != null)
									{
										
									%>
									<TD  align="left" valign="top" class="ColumnBackground">
										<bean:message key="itpan"/>
									</TD>
									<%
									}
									%>

									<% if  (ssiDetails != null)
									{
										
									%>
									<TD  align="left" valign="top" class="ColumnBackground">
										<bean:message key="ssi"/>
									</TD>
									<%
									}
									%>

									<% if  (industryType != null)
									{
										
									%>
									<TD  align="left" valign="top" class="ColumnBackground">
										<bean:message key="industryType"/>
									</TD>
									<%
									}
									%>

									<% if  (tcSanctioned != null)
									{
										
									%>
									<TD  align="left" valign="top" class="ColumnBackground">
										<bean:message key="termCreditSanctioned"/>
									</TD>
									<%
									}
									%>

									<% if  (tcInterest != null)
									{
										
									%>
									<TD  align="left" valign="top" class="ColumnBackground">
										<bean:message key="tcInterest"/>
									</TD>
									<%
									}
									%>

									<% if  (tcPlr != null)
									{
										
									%>
									<TD align="left" valign="top" class="ColumnBackground">
										<bean:message key="tcPlr"/>
									</TD>
									<%
									}
									%>

									<% if  (tcTenure != null)
									{
										
									%>
									<TD  align="left" valign="top" class="ColumnBackground">
										<bean:message key="tcTenure"/>
									</TD>
									<%
									}
									%>

									<% if  (tcOutlay != null)
									{
										
									%>
									<TD  align="left" valign="top" class="ColumnBackground">
										<bean:message key="tcOutlay"/>
									</TD>
									<%
									}
									%>

									<% if  (wcSanctioned != null)
									{
										
									%>
									<TD  align="left" valign="top" class="ColumnBackground">
										<bean:message key="workingCapitalSanctioned"/>
									</TD>
									<%
									}
									%>

									<% if  (wcPlr != null)
									{
										
									%>
									<TD  align="left" valign="top" class="ColumnBackground">
										<bean:message key="wcPlr"/>
									</TD>
									<%
									}
									%>

									<% if  (wcOutlay != null)
									{
										
									%>
									<TD  align="left" valign="top" class="ColumnBackground">
										<bean:message key="wcOutlay"/>
									</TD>
									<%
									}
									%>

									<% if  (remarks != null)
									{
										
									%>
									<TD  align="left" valign="top" class="ColumnBackground">
										<bean:message key="rejection"/>
									</TD>
									<%
									}
									%>
													
											<tr>
											<logic:iterate name="rsForm" id="object" property="danRaised">
											<%
											com.cgtsi.reports.ApplicationReport  danDetails = (com.cgtsi.reports.ApplicationReport)object;
											%>
													
												<TR>
												<% if  (date != null)
												{
												%>
												<TD  align="left" valign="top" class="ColumnBackground1">	
											 <%java.util.Date utilDate=danDetails.getApplicationDate();
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
												<%
												}
												%>

												<% if (promoter != null)
												{
												%>
												<TD  align="left" valign="top" class="ColumnBackground1">					
												<%=danDetails.getChiefPromoter()%>				
												</TD>
												<%
													}
												%>

												<% if (itpan != null)
												{
												%>
												<TD  align="left" valign="top" class="ColumnBackground1">					
												<%String itPan = null;
												itPan = danDetails.getItpan();
												if((itPan == null)|| (itPan.equals("")))
												{
												%>	
												<%=""%>
												<%
												}
												else
												{
												%>			
												<%=itPan%>
												<%
												}
												%>													
												</TD>
												<%
													}
												%>

												<% if  (ssiDetails != null)
												{
												%>
												<TD  align="left" valign="top" class="ColumnBackground1">					
												<%=danDetails.getSsiName()%>				
												</TD>
												<%
													}
												%>

												<% if  (industryType != null)
												{
												%>
												<TD  align="left" valign="top" class="ColumnBackground1">
												<%String type = null;
												type = danDetails.getIndustryType();
												if((type == null)|| (type.equals("")))
												{
												%>	
												<%=""%>
												<%
												}
												else
												{
												%>			
												<%=type%>
												<%
												}
												%>																
											   </TD>
												<%
													}
												%>
												
												<% if  (tcSanctioned != null)
												{
												%>
											   <TD  align="left" valign="top" class="ColumnBackground1">	<div align="right">				
												<%double amount1=danDetails.getTcSanctioned();%>
												<%=decimalFormat.format(amount1)%>
												</div>
											   </TD>
												<%
													}
												%>

												<% if  (tcInterest != null)
												{
												%>
											   <TD  align="left" valign="top" class="ColumnBackground1">	<div align="right">								
												<%double newTcRate=danDetails.getTcRate();
												String newTcRateString = newTcRate+"%p.a.";%>
												<%=newTcRateString%>
												</div>
											   </TD>
												<%
													}
												%>

												<% if  (tcPlr != null)
												{
												%>
											   <TD  align="left" valign="top" class="ColumnBackground1">	<div align="right">						
												<%double newTcPlr=danDetails.getTcPlr();
												String newTcPlrString = newTcPlr+"%p.a.";%>
												<%=newTcPlrString%>
												</div>
											   </TD>
												<%
													}
												%>

												<% if (tcTenure != null)
												{
												%>
											   <TD  align="left" valign="top" class="ColumnBackground1">		<div align="right">							
												<%=danDetails.getTcTenure()%>	
												</div>
											   </TD>
												<%
													}
												%>

												<% if (tcOutlay != null)
												{
												%>
											   <TD  align="left" valign="top" class="ColumnBackground1">	<div align="right">				
												<%double amount2=danDetails.getTcProjectCost();%>	
												<%=decimalFormat.format(amount2)%>							</div>					
											   </TD>
												<%
													}
												%>

												<% if  (wcSanctioned != null)
												{
												%>
											   <TD  align="left" valign="top" class="ColumnBackground1">	<div align="right">				
												<%double amount3=danDetails.getTotalSanctioned();%>
												<%=decimalFormat.format(amount3)%>						
												</div>
											   </TD>
												<%
													}
												%>

												<% if  (wcPlr != null)
												{
												%>
											   <TD  align="left" valign="top" class="ColumnBackground1">		<div align="right">							
												<%double newWcPlr=danDetails.getWcPlr();
												String newWcPlrString = newWcPlr+"%p.a.";%>
												<%=newWcPlrString%>
												</div>
											   </TD>
												<%
													}
												%>

												<% if   (wcOutlay != null)
												{
												%>
											   <TD  align="left" valign="top" class="ColumnBackground1">	<div align="right">
												<%double amount4 = danDetails.getWcProjectCost();%>	
												<%=decimalFormat.format(amount4)%>
												</div>
											   </TD>
												<%
													}
												%>

												<% if  (remarks != null)
												{
												%>
											   <TD  align="left" valign="top" class="ColumnBackground1">					
												<%String remarksNew = null;
												remarksNew=danDetails.getRemarks();
												if((remarksNew == null)|| (remarksNew.equals("")))
												{
												%>	
												<%=""%>
												<%
												}
												else
												{
												%>			
												<%=remarksNew%>
												<%
												}
												%>				
											   </TD>
												<%
													} 
												%>
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
							<A href="javascript:history.back()">
									<IMG src="images/Back.gif" alt="OK" width="49" height="37" border="0"></A>

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

