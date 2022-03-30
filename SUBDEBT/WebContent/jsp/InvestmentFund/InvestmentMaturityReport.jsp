<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@page import ="java.text.DecimalFormat"%>
<%@page import ="java.text.SimpleDateFormat"%>
<%@page import = "org.apache.struts.action.DynaActionForm"%>

<%DecimalFormat decimalFormat = new DecimalFormat("########.##");%>
<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>

<% session.setAttribute("CurrentPage","investmentMaturityReport.do?method=investmentMaturityReport");%>

<% DynaActionForm dynaForm = (DynaActionForm)session.getAttribute("ifForm");
   java.util.Date startDate = (java.util.Date)dynaForm.get("documentDate");
	String  formatedDate=dateFormat.format(startDate);

   java.util.Date date1 = (java.util.Date)dynaForm.get("documentDate1");
   String  formatedDate1=dateFormat.format(date1);

   java.util.Date date2 = (java.util.Date)dynaForm.get("documentDate2");
   String  formatedDate2=dateFormat.format(date2);

   java.util.Date date3 = (java.util.Date)dynaForm.get("documentDate3");
   String  formatedDate3=dateFormat.format(date3);

   java.util.Date date4 = (java.util.Date)dynaForm.get("documentDate4");
   String  formatedDate4=dateFormat.format(date4);

   java.util.Date date5 = (java.util.Date)dynaForm.get("documentDate5");
   String  formatedDate5=dateFormat.format(date5);

   java.util.Date date6 = (java.util.Date)dynaForm.get("documentDate6");
   String  formatedDate6=dateFormat.format(date6);

   java.util.Date date7 = (java.util.Date)dynaForm.get("documentDate7");
   String  formatedDate7=dateFormat.format(date7);

   java.util.Date date8 = (java.util.Date)dynaForm.get("documentDate8");
   String  formatedDate8=dateFormat.format(date8);

   java.util.Date date9 = (java.util.Date)dynaForm.get("documentDate9");
   String  formatedDate9=dateFormat.format(date9);

   java.util.Date date10 = (java.util.Date)dynaForm.get("documentDate10");
   String  formatedDate10=dateFormat.format(date10);

   java.util.Date date11 = (java.util.Date)dynaForm.get("documentDate11");
   String  formatedDate11=dateFormat.format(date11);

   java.util.Date date12 = (java.util.Date)dynaForm.get("documentDate12");
   String  formatedDate12=dateFormat.format(date12);

   java.util.Date date13 = (java.util.Date)dynaForm.get("documentDate13");
   String  formatedDate13=dateFormat.format(date13);

   java.util.Date date14 = (java.util.Date)dynaForm.get("documentDate14");
   String  formatedDate14=dateFormat.format(date14);
   
   %>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="investmentMaturityReport.do?method=investmentMaturityReport" method="POST" enctype="multipart/form-data">
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
												<TD width="40%" class="Heading"><bean:message key="investmentProfile" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>

									<TR>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="instrumentName" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="range1"/>
										(<%=formatedDate%>  to  <%=formatedDate1%>)
										<bean:message key="approvedAmountLakhs"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="range2" />
										(<%=formatedDate2%>  to  <%=formatedDate3%>)
										<bean:message key="approvedAmountLakhs"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="range3"/>
										(<%=formatedDate4%>  to  <%=formatedDate5%>)
										<bean:message key="approvedAmountLakhs"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="range4"/>
										(<%=formatedDate6%>  to  <%=formatedDate7%>)
										<bean:message key="approvedAmountLakhs"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="range5"/>
										(<%=formatedDate8%>  to  <%=formatedDate9%>)
										<bean:message key="approvedAmountLakhs"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="range6"/>
										(<%=formatedDate10%>  to  <%=formatedDate11%>)
										<bean:message key="approvedAmountLakhs"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="range7"/>
										(<%=formatedDate12%>  to  <%=formatedDate13%>)
										<bean:message key="approvedAmountLakhs"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="range8"/>
										(Above <%=formatedDate14%>)
										<bean:message key="approvedAmountLakhs"/>
									</TD>
									</TR>	

									<%
									double amount1 = 0;
									double totalAmount1 = 0;
									double amount2 = 0;
									double totalAmount2 = 0;
									double amount3 = 0;
									double totalAmount3 = 0;
									double amount4 = 0;
									double totalAmount4 = 0;
									double amount5 = 0;
									double totalAmount5 = 0;
									double amount6 = 0;
									double totalAmount6 = 0;
									double amount7 = 0;
									double totalAmount7 = 0;
									double amount8 = 0;
									double totalAmount8 = 0;
									%>

									<tr>
									<logic:iterate id="object"  name="ifForm"  property="investmentArray">  
										<%
											 com.cgtsi.investmentfund.InvestmentDetails size =  (com.cgtsi.investmentfund.InvestmentDetails)object;
										%>

											<TR>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						
											<%String instrument =size.getInstrumentName();%>
											<%=instrument%>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">			
											<div align="right">
											<% amount1=size.getRange1();%>
											<%totalAmount1 = totalAmount1+amount1;%>
											<%
											if(amount1 != 0)
											{
											  String url1 = "investmentMaturityReportDetails.do?method=investmentMaturityReportDetails&number="+instrument+"&flag=range1";%>
											<html:link href="<%=url1%>"> <%=decimalFormat.format(amount1)%>  </html:link>		
											<%
											}
											else
											{
											%>
											<%=decimalFormat.format(amount1)%>
											<%
											 }
											%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
											<div align="right">
											<%amount2=size.getRange2();%>
											<%totalAmount2 = totalAmount2+amount2;%>
											<%
											if(amount2 != 0)
											{
											  String url2 = "investmentMaturityReportDetails.do?method=investmentMaturityReportDetails&number="+instrument+"&flag=range2";%>
											<html:link href="<%=url2%>"> <%=decimalFormat.format(amount2)%>  </html:link>		
											<%
											}
											else
											{
											%>
											<%=decimalFormat.format(amount2)%>
											<%
											 }
											%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">		
											<div align="right">
											<%amount3=size.getRange3();%>
											<%totalAmount3 = totalAmount3+amount3;%>
											<%
											if(amount3 != 0)
											{
											  String url3 = "investmentMaturityReportDetails.do?method=investmentMaturityReportDetails&number="+instrument+"&flag=range3";%>
											<html:link href="<%=url3%>"> <%=decimalFormat.format(amount3)%>  </html:link>		
											<%
											}
											else
											{
											%>
											<%=decimalFormat.format(amount3)%>
											<%
											 }
											%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">		
											<div align="right">
											<% amount4=size.getRange4();%>
											<%totalAmount4 = totalAmount4+amount4;%>
											<%
											if(amount4 != 0)
											{
											  String url4 = "investmentMaturityReportDetails.do?method=investmentMaturityReportDetails&number="+instrument+"&flag=range4";%>
											<html:link href="<%=url4%>"> <%=decimalFormat.format(amount4)%>  </html:link>		
											<%
											}
											else
											{
											%>
											<%=decimalFormat.format(amount4)%>
											<%
											 }
											%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
											<div align="right">
											<% amount5=size.getRange5();%>
											<%totalAmount5 = totalAmount5+amount5;%>
											<%
											if(amount5 != 0)
											{
											  String url5 = "investmentMaturityReportDetails.do?method=investmentMaturityReportDetails&number="+instrument+"&flag=range5";%>
											<html:link href="<%=url5%>"> <%=decimalFormat.format(amount5)%>  </html:link>		
											<%
											}
											else
											{
											%>
											<%=decimalFormat.format(amount5)%>
											<%
											 }
											%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<div align="right">
											<%amount6=size.getRange6();%>
											<%totalAmount6 = totalAmount6+amount6;%>
											<%
											if(amount6 != 0)
											{
											  String url6 = "investmentMaturityReportDetails.do?method=investmentMaturityReportDetails&number="+instrument+"&flag=range6";%>
											<html:link href="<%=url6%>"> <%=decimalFormat.format(amount6)%>  </html:link>		
											<%
											}
											else
											{
											%>
											<%=decimalFormat.format(amount6)%>
											<%
											 }
											%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<div align="right">
											<%amount7=size.getRange7();%>
											<%totalAmount7 = totalAmount7+amount7;%>
											<%
											if(amount7 != 0)
											{
											  String url7 = "investmentMaturityReportDetails.do?method=investmentMaturityReportDetails&number="+instrument+"&flag=range7";%>
											<html:link href="<%=url7%>"> <%=decimalFormat.format(amount7)%>  </html:link>		
											<%
											}
											else
											{
											%>
											<%=decimalFormat.format(amount7)%>
											<%
											 }
											%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<div align="right">
											<%amount8=size.getRange8();%>
											<%totalAmount8 = totalAmount8+amount8;%>
											<%
											if(amount8 != 0)
											{
											  String url8 = "investmentMaturityReportDetails.do?method=investmentMaturityReportDetails&number="+instrument+"&flag=range8";%>
											<html:link href="<%=url8%>"> <%=decimalFormat.format(amount8)%>  </html:link>		
											<%
											}
											else
											{
											%>
											<%=decimalFormat.format(amount8)%>
											<%
											 }
											%>
											</div>
											</TD>
											</TR>
											</logic:iterate>


											<TR>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						
											Total
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">			
											<div align="right">

											<%=decimalFormat.format(totalAmount1)%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">	
											<div align="right">

											<%=decimalFormat.format(totalAmount2)%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">		
											<div align="right">

											<%=decimalFormat.format(totalAmount3)%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">		
											<div align="right">

											<%=decimalFormat.format(totalAmount4)%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">	
											<div align="right">

											<%=decimalFormat.format(totalAmount5)%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						
											<div align="right">

											<%=decimalFormat.format(totalAmount6)%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						
											<div align="right">

											<%=decimalFormat.format(totalAmount7)%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						
											<div align="right">

											<%=decimalFormat.format(totalAmount8)%>
											</div>
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
						
								<A href="javascript:submitForm('investmentMaturityReportInput.do?method=investmentMaturityReportInput')">
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
