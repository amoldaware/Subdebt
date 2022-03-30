<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","chequeDetailsReport.do?method=chequeDetailsReport");%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="chequeDetailsReportPage.do?method=chequeDetailsReportPage" method="POST" enctype="multipart/form-data">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/InvestmentManagementHeading.gif" width="121" height="25"></TD>
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
												<TD width="28%" class="Heading"><bean:message key="chequeHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>

									<TR>
									<TD align="left" valign="top" class="ColumnBackground">
										<bean:message key="chequeNotPresented" />
									</TD>
									<TD align="left" valign="top" class="ColumnBackground">
										<bean:message key="chequePresented" />
									</TD>
									</TR>	 
					
									<%		   int i = 0;
											   int j = 0;
									%>
											<tr>
											<logic:iterate name="ifForm" id="object" property="chequeArray">
											<%
											com.cgtsi.investmentfund.ChequeDetails cheque = (com.cgtsi.investmentfund.ChequeDetails)object;

											   String value1 = cheque.getChequeNumber(); 
											   //System.out.println("value1:"+value1);
											   String value2 = cheque.getChequeId();
											   //System.out.println("value1:"+value2);
											   String value3 = cheque.getStatus(); 
											   //System.out.println("value1:"+value3);

											%>

											<TR>

											<TD align="center" valign="top" class="ColumnBackground1">	
											<%
											if(value3.equals("I"))
											{
												++i;
											//System.out.println("value1:"+value3);
											  String url = "chequeDetailsReportPage.do?method=chequeDetailsReportPage&number="+value2;%>
											<html:link href="<%=url%>"><%=value1%></html:link>
											</TD>
											<% 
											}
											%>
											<TD align="center" valign="top" class="ColumnBackground1">	
											<%
											if(value3.equals("P"))
											{	
												++j;
											//System.out.println("value2:"+value3);
											 String url = "chequeDetailsReportPage.do?method=chequeDetailsReportPage&number="+value2;%>
											<html:link href="<%=url%>"><%=value1%></html:link>
											</TD>
											<%
											  }
											%>
											</TR>
											</logic:iterate>

											<tr>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">	
											<%=i%>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">	
											<%=j%>
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
							<A href="javascript:submitForm('chequeDetailsReportInput.do?method=chequeDetailsReportInput')">
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

