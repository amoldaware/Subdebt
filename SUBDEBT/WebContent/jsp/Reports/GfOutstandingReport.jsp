<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@page import ="java.text.SimpleDateFormat"%>
<%@page import = "org.apache.struts.action.DynaActionForm"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","gfOutstandingReport.do?method=gfOutstandingReport");%>
<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>
<%@page import ="java.text.DecimalFormat"%>
<%DecimalFormat decimalFormat = new DecimalFormat("##########.##");%>
<% DynaActionForm dynaForm = (DynaActionForm)session.getAttribute("rsForm");
   String value = (String)dynaForm.get("bank");%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="danDetailsGf.do?method=danDetailsGf" method="POST" enctype="multipart/form-data">
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
									<TD colspan="12"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="65%" class="Heading"><bean:message key="danDetailsHeader" />&nbsp
												<%=value%>
												</TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>

									<%	double outstanding = 0;
										double totalOutstanding= 0;
										double gFee = 0;
										double totalgFee = 0;
										double gFeePaid = 0;
										double totalgFeePaid = 0;
										double overallOutstanding = 0;
										double overallgFeePaid= 0;
										double overallgFee = 0;
										double overallOutstanding1 = 0;
										double overallgFeePaid1 = 0;
										double overallgFee1 = 0;
										double overallOutstanding2 = 0;
										double overallgFeePaid2 = 0;
										double overallgFee2 = 0;
										String memberId = null;
										String zone = null;
										String memberIdTemp = null;
										String zoneTemp = null;
										int count = 0;
										int totalCount = 0;
										int overallCount = 0;
										int overallCount1 = 0;
										int overallCount2 = 0;

									%>
									

	
									<tr>
									<logic:iterate name="rsForm" id="object" property="gFeeReport">
									<%
									com.cgtsi.reports.GFee danReport = (com.cgtsi.reports.GFee)object;
									%>

										<%zone=danReport.getZone(); %>
									<%memberId=danReport.getMemberId(); %>
									
									<%
									if ((memberIdTemp == null) || (memberIdTemp.equals("")))
									{
									%>
									<TR>
									<TD	 align="left" valign="top" class="ColumnBackground1" colspan="6">	<%
									if((zone == null) || (zone.equals("")))
									{
									%>
									<%=memberId%>
									</TR>

									<TR>
									<TD  align="left" valign="top" class="ColumnBackground">
										<bean:message key="dan" />
									</TD>
									<TD  align="left" valign="top" class="ColumnBackground">
										<bean:message key="danDate"/>
									</TD>
									<TD  align="left" valign="top" class="ColumnBackground">
										<bean:message key="applications" />
									</TD>
									<TD  align="left" valign="top" class="ColumnBackground">
										<bean:message key="gFee/sFee/claimDemanded"/>
									</TD>
									<TD  align="left" valign="top" class="ColumnBackground">
										<bean:message key="gFee/sFee/claimReceived"/>
									</TD>
									<TD  align="left" valign="top" class="ColumnBackground">
										<bean:message key="outstanding"/>
									</TD>
									</TR>

									<TR>
									<TD  align="left" valign="top" class="ColumnBackground1">	
									<% String cgdan = danReport.getDan(); 
									  String url = "danDetailsGf.do?method=danDetailsGf&danDetail="+cgdan;%>
									<html:link href="<%=url%>"><%=cgdan%></html:link>
									</TD>
									<TD  align="left" valign="top" class="ColumnBackground1">	
									<%   java.util.Date utilDate=danReport.getDanDate();
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
									<TD  align="left" valign="top" class="ColumnBackground1">			<div align="right">			
									<%count=danReport.getApplications();
									totalCount = totalCount + count;%>
									<%=count%>
									</div>
									</TD>
									<TD  align="left" valign="top" class="ColumnBackground1">	
									<div align="right">
									<% gFee=danReport.getGuaranteeFee();
									  totalgFee = gFee + totalgFee;
									  if(gFee == 0)
									  {
									  %>
									   <%="-"%>
									   <%
									  }
									  else
									  {
									  %>
										 <%=decimalFormat.format(gFee)%>
									   <%
										}
									   %>
									</div>
									</TD>
									<TD  align="left" valign="top" class="ColumnBackground1">	
									<div align="right">
									<%gFeePaid = danReport.getGuaranteeFeePaid() ;
									totalgFeePaid = totalgFeePaid +gFeePaid;%>
									<%=decimalFormat.format(gFeePaid)%>
									</div>
									</TD>
									<TD  align="left" valign="top" class="ColumnBackground1">
									<div align="right">
									<%outstanding = danReport.getOutstanding() ;
									totalOutstanding = totalOutstanding +outstanding;%>			
									<%=decimalFormat.format(outstanding)%>
									<%memberIdTemp = memberId;%>
									</div>
									</TD>
									</TR>
									<%
									}
									else
									{
										%>
									<%=zone%>
									<%=memberId%>
									</TR>

									<TR>
									<TD  align="left" valign="top" class="ColumnBackground">
										<bean:message key="dan" />
									</TD>
									<TD  align="left" valign="top" class="ColumnBackground">
										<bean:message key="danDate"/>
									</TD>
									<TD  align="left" valign="top" class="ColumnBackground">
										<bean:message key="applications" />
									</TD>
									<TD  align="left" valign="top" class="ColumnBackground">
										<bean:message key="gFee/sFee/claimDemanded"/>
									</TD>
									<TD  align="left" valign="top" class="ColumnBackground">
										<bean:message key="gFee/sFee/claimReceived"/>
									</TD>
									<TD  align="left" valign="top" class="ColumnBackground">
										<bean:message key="outstanding"/>
									</TD>
									</TR>

									<TR>
									<TD  align="left" valign="top" class="ColumnBackground1">	
									<% String cgdan = danReport.getDan(); 
									  String url = "danDetailsGf.do?method=danDetailsGf&danDetail="+cgdan;%>
									<html:link href="<%=url%>"><%=cgdan%></html:link>
									</TD>
									<TD  align="left" valign="top" class="ColumnBackground1">	
									<%   java.util.Date utilDate=danReport.getDanDate();
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
									<TD  align="left" valign="top" class="ColumnBackground1">				<div align="right">		
									<%count=danReport.getApplications();
									totalCount = totalCount + count;%>
									<%=count%>
									</div>
									</TD>
									<TD  align="left" valign="top" class="ColumnBackground1">	
									<div align="right">
									<% gFee=danReport.getGuaranteeFee();
									  totalgFee = gFee + totalgFee;
									  if(gFee == 0)
									  {
									  %>
									   <%="-"%>
									   <%
									  }
									  else
									  {
									  %>
										 <%=decimalFormat.format(gFee)%>
									   <%
										}
									   %>
									</div>
									</TD>
									<TD  align="left" valign="top" class="ColumnBackground1">	
									<div align="right">
									<%gFeePaid = danReport.getGuaranteeFeePaid() ;
									totalgFeePaid = totalgFeePaid +gFeePaid;%>
									<%=decimalFormat.format(gFeePaid)%>
									</div>
									</TD>
									<TD  align="left" valign="top" class="ColumnBackground1">
									<div align="right">
									<%outstanding = danReport.getOutstanding() ;
									totalOutstanding = totalOutstanding +outstanding;%>			
									<%=decimalFormat.format(outstanding)%>
									<%memberIdTemp = memberId;%>
									</div>
									</TD>
									</TR>
									<%
									}
								}
                                     
									else if (memberId.equals(memberIdTemp))
									{
									%>

									<TR>
									<TD  align="left" valign="top" class="ColumnBackground1">	
									<% String cgdan = danReport.getDan(); 
									  String url = "danDetailsGf.do?method=danDetailsGf&danDetail="+cgdan;%>
									<html:link href="<%=url%>"><%=cgdan%></html:link>
									</TD>
									<TD  align="left" valign="top" class="ColumnBackground1">	
									<%   java.util.Date utilDate=danReport.getDanDate();
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
									<TD  align="left" valign="top" class="ColumnBackground1">				<div align="right">							
									<%count=danReport.getApplications();
									totalCount = totalCount + count;%>
									<%=count%>
									</div>
									</TD>
									<TD  align="left" valign="top" class="ColumnBackground1">	
									<div align="right">
									<% gFee=danReport.getGuaranteeFee();
									  totalgFee = gFee + totalgFee;
									  if(gFee == 0)
									  {
									  %>
									   <%="-"%>
									   <%
									  }
									  else
									  {
									  %>
										 <%=decimalFormat.format(gFee)%>
									   <%
										}
									   %>
									</div>
									</TD>
									<TD  align="left" valign="top" class="ColumnBackground1">
									<div align="right">
									<%gFeePaid = danReport.getGuaranteeFeePaid() ;
									totalgFeePaid = totalgFeePaid +gFeePaid;%>
									<%=decimalFormat.format(gFeePaid)%>
									</div>
									</TD>
									<TD  align="left" valign="top" class="ColumnBackground1">
									<div align="right">
									<%outstanding = danReport.getOutstanding() ;
									totalOutstanding = totalOutstanding +outstanding;%>			
									<%=decimalFormat.format(outstanding)%>
									<%memberIdTemp = memberId;%>
									</div>
									</TD>
									</TR>
									<%
									}
									

									else 
									{
									%>

								    <tr>
									<TD align="left" valign="top" class="ColumnBackground">
									Total
									</TD>
									<TD align="left" class="ColumnBackground"> 
									</TD>
									<TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%overallCount = totalCount ;
									  overallCount1 = overallCount1 + overallCount;%>
									<%=totalCount%>
									</div>
									</TD>
									<TD align="left" valign="top" class="ColumnBackground">
									<div align="right">
									<%overallgFee = totalgFee ;
									  overallgFee1 = overallgFee1 + overallgFee;%>
									<%=decimalFormat.format(totalgFee)%>
									</div>
									</TD>
									<TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%overallgFeePaid = totalgFeePaid ;
									overallgFeePaid1 = overallgFeePaid1 +overallgFeePaid;%>
									<%=decimalFormat.format(totalgFeePaid)%>
									</div>
									</TD>
									<TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%overallOutstanding = totalOutstanding ;
									overallOutstanding1 = overallOutstanding1 +overallOutstanding;%>
									<%=decimalFormat.format(totalOutstanding)%>
									</div>
									</TD>
								
								</tr>
								<%	 outstanding = 0;
									 totalOutstanding= 0;
									 gFee = 0;
									 totalgFee = 0;
									 gFeePaid = 0;
									 totalgFeePaid = 0;
									 count = 0;
									totalCount = 0;
								%>
											
									<TR>
									<TD	 align="left" valign="top" class="ColumnBackground1" colspan="6">	<%
									if((zone == null) || (zone.equals("")))
									{
									%>
									<%=memberId%>
									</TR>


                                    <TR>
									<TD  align="left" valign="top" class="ColumnBackground">
										<bean:message key="dan" />
									</TD>
									<TD  align="left" valign="top" class="ColumnBackground">
										<bean:message key="danDate"/>
									</TD>
									<TD  align="left" valign="top" class="ColumnBackground">
										<bean:message key="applications" />
									</TD>
									<TD  align="left" valign="top" class="ColumnBackground">
										<bean:message key="gFee/sFee/claimDemanded"/>
									</TD>
									<TD  align="left" valign="top" class="ColumnBackground">
										<bean:message key="gFee/sFee/claimReceived"/>
									</TD>
									<TD  align="left" valign="top" class="ColumnBackground">
										<bean:message key="outstanding"/>
									</TD>
									</TR>

									<TR>
									<TD  align="left" valign="top" class="ColumnBackground1">	
									<% String cgdan = danReport.getDan(); 
									  String url = "danDetailsGf.do?method=danDetailsGf&danDetail="+cgdan;%>
									<html:link href="<%=url%>"><%=cgdan%></html:link>
									</TD>
									<TD  align="left" valign="top" class="ColumnBackground1">	
									<%   java.util.Date utilDate=danReport.getDanDate();
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
									<TD  align="left" valign="top" class="ColumnBackground1">				<div align="right">								<%count=danReport.getApplications();
									totalCount = totalCount + count;%>
									<%=count%>		
									</div>
									</TD>
									<TD  align="left" valign="top" class="ColumnBackground1">
									<div align="right">
									<% gFee=danReport.getGuaranteeFee();
									  totalgFee = gFee + totalgFee;
									  if(gFee == 0)
									  {
									  %>
									   <%="-"%>
									   <%
									  }
									  else
									  {
									  %>
										 <%=decimalFormat.format(gFee)%>
									   <%
										}
									   %>
									</div>
									</TD>
									<TD  align="left" valign="top" class="ColumnBackground1">
									<div align="right">
									<%gFeePaid = danReport.getGuaranteeFeePaid() ;
									totalgFeePaid = totalgFeePaid +gFeePaid;%>
									<%=decimalFormat.format(gFeePaid)%>
									</div>
									</TD>
									<TD  align="left" valign="top" class="ColumnBackground1">
									<div align="right">
									<%outstanding = danReport.getOutstanding() ;
									totalOutstanding = totalOutstanding +outstanding;%>			
									<%=decimalFormat.format(outstanding)%>
									<%memberIdTemp = memberId;%>
									</div>
									</TD>
									</TR>
									<%
									}
									else
									{
									%>
									<%=zone%>
									<%=memberId%>
									</TR>


                                    <TR>
									<TD  align="left" valign="top" class="ColumnBackground">
										<bean:message key="dan" />
									</TD>
									<TD  align="left" valign="top" class="ColumnBackground">
										<bean:message key="danDate"/>
									</TD>
									<TD  align="left" valign="top" class="ColumnBackground">
										<bean:message key="applications" />
									</TD>
									<TD  align="left" valign="top" class="ColumnBackground">
										<bean:message key="gFee/sFee/claimDemanded"/>
									</TD>
									<TD  align="left" valign="top" class="ColumnBackground">
										<bean:message key="gFee/sFee/claimReceived"/>
									</TD>
									<TD  align="left" valign="top" class="ColumnBackground">
										<bean:message key="outstanding"/>
									</TD>
									</TR>

									<TR>
									<TD  align="left" valign="top" class="ColumnBackground1">	
									<% String cgdan = danReport.getDan(); 
									  String url = "danDetailsGf.do?method=danDetailsGf&danDetail="+cgdan;%>
									<html:link href="<%=url%>"><%=cgdan%></html:link>
									</TD>
									<TD  align="left" valign="top" class="ColumnBackground1">	
									<%   java.util.Date utilDate=danReport.getDanDate();
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
									<TD  align="left" valign="top" class="ColumnBackground1">			<div align="right">									<%count=danReport.getApplications();
									totalCount = totalCount + count;%>
									<%=count%>			
									</div>
									</TD>
									<TD  align="left" valign="top" class="ColumnBackground1">
									<div align="right">
									<% gFee=danReport.getGuaranteeFee();
									  totalgFee = gFee + totalgFee;
									  if(gFee == 0)
									  {
									  %>
									   <%="-"%>
									   <%
									  }
									  else
									  {
									  %>
										 <%=decimalFormat.format(gFee)%>
									   <%
										}
									   %>
									</div>
									</TD>
									<TD  align="left" valign="top" class="ColumnBackground1">
									<div align="right">
									<%gFeePaid = danReport.getGuaranteeFeePaid() ;
									totalgFeePaid = totalgFeePaid +gFeePaid;%>
									<%=decimalFormat.format(gFeePaid)%>
									</div>
									</TD>
									<TD  align="left" valign="top" class="ColumnBackground1">
									<div align="right">
									<%outstanding = danReport.getOutstanding() ;
									totalOutstanding = totalOutstanding +outstanding;%>			
									<%=decimalFormat.format(outstanding)%>
									<%memberIdTemp = memberId;%>
									</div>
									</TD>
									</TR>
									<%
									}
								}
									%>

									</logic:iterate>

								<tr>

									<TD align="left" valign="top" class="ColumnBackground">
									Total
									</TD>
									<TD align="left" class="ColumnBackground"> 
									</TD>
									<TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%=totalCount%>
									</div>
									</TD>
									<TD align="left" valign="top" class="ColumnBackground">
									<div align="right">
									<%=decimalFormat.format(totalgFee)%>
									</div>
									</TD>
									<TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%=decimalFormat.format(totalgFeePaid)%>
									</div>
									</TD>
									<TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%=decimalFormat.format(totalOutstanding)%>
									</div>
									</TD>
								
								</tr>

							<tr>

									<TD align="left" valign="top" class="ColumnBackground">
									Grand Total
									</TD>
									<TD align="left" class="ColumnBackground"> 
									</TD>
									<TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%overallCount2 = overallCount1 +totalCount;%>
									<%=overallCount2%>
									</div>
									</TD>
									<TD align="left" valign="top" class="ColumnBackground">
									<div align="right">
									<%overallgFee2 = overallgFee1 +totalgFee;%>
									<%=decimalFormat.format(overallgFee2)%>
									</div>
									</TD>
									<TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%overallgFeePaid2 = overallgFeePaid1 +totalgFeePaid;%>
									<%=decimalFormat.format(overallgFeePaid2)%>
									</div>
									</TD>
									<TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%overallOutstanding2 = overallOutstanding1 +totalOutstanding;%>
									<%=decimalFormat.format(overallOutstanding2)%>
									</div>
									</TD>
								
								</tr>


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

