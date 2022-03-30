<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@page import ="java.text.SimpleDateFormat"%>
<%@page import ="java.text.DecimalFormat"%>
<%@page import = "org.apache.struts.action.DynaActionForm"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","shortReportDetails.do?method=shortReportDetails");%>
<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>

<%DecimalFormat decimalFormat = new DecimalFormat("##########.##");%>



<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="shortReportDetails.do?shortReportDetails" method="POST" enctype="multipart/form-data">
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
												<TD width="40%" class="Heading"><bean:message key="shortInfoReport"/>&nbsp</TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>

									<TR>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="dan"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="cgpan"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="danDate"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="guaranteeFeeRs"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="allocationDate"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="appropriationDate"/>
									</TD>
									<%	double sum = 0;
										double totalSum = 0;
										double gFee = 0;
										double totalgFee = 0;
										double overallgFee = 0;
										double overallgFee1 = 0;
										double overallgFee2 = 0;
										String danId = null;
										String danIdTemp = null;
									%>
									</TR>	
					
											<tr>
											<logic:iterate name="rsForm" id="object" property="danRaisedReport">
											<%
											com.cgtsi.reports.ExcessInfo  danDetails = (com.cgtsi.reports.ExcessInfo)object;
											%>

											<%danId=danDetails.getDanId();%>
											<%
											if((danIdTemp == null) || (danIdTemp.equals("")))
											{
											%>	
											<TR>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
											<%=danId%>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">					
											<%=danDetails.getCgpan()%>				
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">		
											<div align="right">
											<%   java.util.Date utilDate=danDetails.getDanDate();
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
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">		
											<div align="right">
											<% gFee=danDetails.getGFee();
											  totalgFee = gFee + totalgFee;%>
											  <%=decimalFormat.format(gFee)%>
											  </div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
											<%   java.util.Date utilDate1=danDetails.getAllocationDate();
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
										<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
											<%   java.util.Date utilDate2=danDetails.getAppropriationDate();
												String formatedDate2 = null;
												if(utilDate2 != null)
												{
													 formatedDate2=dateFormat.format(utilDate2);
												}
												else
												{
													 formatedDate2 = "";
												}
											%>
										<%=formatedDate2%>
										<%danIdTemp=danId;%>

										<%
										}

										else if(danId.equals(danIdTemp))
										{
										%>
											<TR>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
											<%=""%>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">					
											<%=danDetails.getCgpan()%>				
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">		
											<div align="right">
											<%   java.util.Date utilDate=danDetails.getDanDate();
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
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">		
											<div align="right">
											<% gFee=danDetails.getGFee();
											  totalgFee = gFee + totalgFee;%>
											  <%=decimalFormat.format(gFee)%>
											  </div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
											<%   java.util.Date utilDate1=danDetails.getAllocationDate();
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
										<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
											<%   java.util.Date utilDate2=danDetails.getAppropriationDate();
												String formatedDate2 = null;
												if(utilDate2 != null)
												{
													 formatedDate2=dateFormat.format(utilDate2);
												}
												else
												{
													 formatedDate2 = "";
												}
											%>
										<%=formatedDate2%>
										<%danIdTemp=danId;%>

										<%
										}

									else
									{
									%>
									<tr>
									<TD align="left" valign="top" class="ColumnBackground">
									Total
									</TD>
									<TD align="left" valign="top" class="ColumnBackground1">
									
									</TD>
									<TD align="left" valign="top" class="ColumnBackground">

									</TD>
									<TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%overallgFee = totalgFee ;
									  overallgFee1 = overallgFee1 + overallgFee;%>
									<%=decimalFormat.format(totalgFee)%>
									</div>
									</TD>
									<TD align="left" class="ColumnBackground1"> 
									
									</TD>
									<TD align="left" class="ColumnBackground1"> 
									
									</TD>
									</tr>
									<%
										gFee = 0;
										totalgFee = 0;
									%>

										<TR>
										<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
										<%=danId%>
										</TD>
										<TD width="10%" align="left" valign="top" class="ColumnBackground1">					
										<%=danDetails.getCgpan()%>				
										</TD>
										<TD width="10%" align="left" valign="top" class="ColumnBackground1">		
										<div align="right">
										<%   java.util.Date utilDate=danDetails.getDanDate();
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
										</div>
										</TD>
										<TD width="10%" align="left" valign="top" class="ColumnBackground1">		
										<div align="right">
										<% gFee=danDetails.getGFee();
										  totalgFee = gFee + totalgFee;%>
										  <%=decimalFormat.format(gFee)%>
										  </div>
										</TD>
										<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
										<%   java.util.Date utilDate1=danDetails.getAllocationDate();
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
									<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
									<%   java.util.Date utilDate2=danDetails.getAppropriationDate();
										String formatedDate2 = null;
										if(utilDate2 != null)
										{
											 formatedDate2=dateFormat.format(utilDate2);
										}
										else
										{
											 formatedDate2 = "";
										}
									%>
									<%=formatedDate2%>
									<%danIdTemp=danId;%>
									<%
									}
									%>
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

									</TD>
									<TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%=decimalFormat.format(totalgFee)%>
									</div>
									</TD>
									<TD align="left" class="ColumnBackground1"> 
									
									</TD>
									<TD align="left" class="ColumnBackground1"> 
									
									</TD>
									</tr>
<!--

									<tr>
									<TD align="left" valign="top" class="ColumnBackground">
									Grand Total
									</TD>
									<TD align="left" valign="top" class="ColumnBackground1">
									
									</TD>
									<TD align="left" valign="top" class="ColumnBackground">

									</TD>
									<TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%overallgFee2 = overallgFee1 +totalgFee;%>
									<%=decimalFormat.format(overallgFee2)%>
									</div>
									</TD>
									<TD align="left" class="ColumnBackground1"> 
									
									</TD>
									<TD align="left" class="ColumnBackground1"> 
									
									</TD>
									</tr>
 -->  
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

