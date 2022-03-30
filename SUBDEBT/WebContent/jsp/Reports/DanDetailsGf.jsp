<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@page import ="java.text.SimpleDateFormat"%>
<%@page import = "org.apache.struts.action.DynaActionForm"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","danDetailsGf.do?method=danDetailsGf");%>
<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>
<%@page import ="java.text.DecimalFormat"%>
<%DecimalFormat decimalFormat = new DecimalFormat("##########.##");%>
<% DynaActionForm dynaForm = (DynaActionForm)session.getAttribute("rsForm");
   String value = (String)dynaForm.get("danDetail");
   String danType = value.substring(0,2);		%>

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
												<TD width="40%" class="Heading"><bean:message key="cgpanHeader" />&nbsp
												<%=value%>
												</TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>
										</TABLE>
									</TD>

									<TR>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="cgpan"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="ssi"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="approvedAmount"/>
									</TD>
									<%
									if(danType.equals("CG"))
									{
									%>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="guaranteeFeeRs"/>
									</TD>
									<%
									}
									else if(danType.equals("CL"))
									{
									%>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="claimAmount"/>
									</TD>
									<%
									}
									else if(danType.equals("SH"))
									{
									%>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="shortAmount"/>
									</TD>
									<%
									}
									else if(danType.equals("SF"))
									{
									%>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="sFee"/>
									</TD>
									<%
									}
									%>									
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="gCover"/>
									</TD>
									<%	double sum = 0;
										double totalSum = 0;
										double gFee = 0;
										double totalgFee = 0;
									%>
									</TR>	
					
													
											<tr>
											<logic:iterate name="rsForm" id="object" property="danDetails">
											<%
											com.cgtsi.reports.GuaranteeCover  danDetails = (com.cgtsi.reports.GuaranteeCover)object;
											%>
													
											<TR>
												<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
												<%String mli =danDetails.getCgpan();
												  String url1 = "securitizationReportDetailsForCgpan.do?method=securitizationReportDetailsForCgpan&number="+mli;%>
												  <html:link href="<%=url1%>"><%=mli%></html:link>		
												</TD>
												<TD width="10%" align="left" valign="top" class="ColumnBackground1">					
												<%=danDetails.getSsiName()%>				
												</TD>
												<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
												<div align="right">
												<% sum=danDetails.getTotal();
												  totalSum = sum + totalSum;%>							
												  <%=decimalFormat.format(sum)%>
												  </div>
												</TD>
												<TD width="10%" align="left" valign="top" class="ColumnBackground1">		
												<div align="right">
												<% gFee=danDetails.getGuaranteeFee();
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
												<TD width="10%" align="left" valign="top" class="ColumnBackground1">		
												
												<%   java.util.Date utilDate=danDetails.getDate();
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
											</logic:iterate>

									<tr>
									<TD align="left" valign="top" class="ColumnBackground">
									Total
									</TD>
									<TD align="left" valign="top" class="ColumnBackground1">
									
									</TD>
									<TD align="left" valign="top" class="ColumnBackground">
									<div align="right">
									<%=decimalFormat.format(totalSum)%>
									</div>
									</TD>
									<TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%=decimalFormat.format(totalgFee)%>
									</div>
									</TD>
									<TD align="left" class="ColumnBackground"> 
									
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

