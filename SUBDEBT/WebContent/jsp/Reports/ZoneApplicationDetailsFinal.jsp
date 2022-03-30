<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@page import ="java.text.DecimalFormat"%>
<%DecimalFormat decimalFormat = new DecimalFormat("##########.##");%>
<% session.setAttribute("CurrentPage","zoneDetailsForMli.do?method=zoneDetailsForMli");%>
<%@page import = "org.apache.struts.action.DynaActionForm"%>
<% DynaActionForm dynaForm = (DynaActionForm)session.getAttribute("rsForm");
   String value = (String)dynaForm.get("checkValue");%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="zoneDetailsForMli.do?method=zoneDetailsForMli" method="POST" enctype="multipart/form-data">
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
									<TD colspan="4"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="31%" class="Heading"><bean:message key="stateHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>
											</TABLE>
									</TD>
								</TR>


								<TR align="left" valign="top">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="zoneName"/>
									</TD>
									<TD align="left" class="ColumnBackground"> 
									<bean:message key="units"/>
									</TD>
									<TD align="left" class="ColumnBackground"> 
									<bean:message key="amountApproved"/>
									</TD>
									<% int count = 0;
										int totalCount =0;
										double sum = 0;
										double totalSum = 0;
									%>
									</TR>

									<tr>
									<logic:iterate id="object" name="rsForm" property="zoneWiseReport">
									<%
									     com.cgtsi.reports.GeneralReport dReport =  (com.cgtsi.reports.GeneralReport)object;
									%>


									<TR align="left" valign="top"> 
									<TD align="left" class="ColumnBackground1"> 
										<%String zone=dReport.getType();
										if((zone == null) || (zone.equals("")))
										{
										%>
										<%="Applied By HO"%>
										<%
										}
										else
										{
										%>
										<%=zone%>										
										<%
										}
										%>
									</TD>
									<TD align="left" class="ColumnBackground1"> 
									<%count=dReport.getProposals();%>
									<%totalCount = count + totalCount;%>
									<%=dReport.getProposals()%>
									</TD>
									<TD align="left" class="ColumnBackground1"> 
									<div align="right">
									<%double amount=dReport.getAmount();
									sum = amount;%>
									<% totalSum = totalSum+sum;%>
									<%=decimalFormat.format(amount)%>
									</div>
									</TD>
									</TR> 
								</logic:iterate>

								<tr>
									<TD align="left" valign="top" class="ColumnBackground">
									Total
									</TD>
									<TD align="left" class="ColumnBackground"> 
									<%=totalCount%>
									</TD>
									<TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%=decimalFormat.format(totalSum)%>
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
					<TR>
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

