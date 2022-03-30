<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@page import ="java.text.DecimalFormat"%>
<%DecimalFormat decimalFormat = new DecimalFormat("##########.##");%>
<% session.setAttribute("CurrentPage","sizeWiseReportDetails.do?method=sizeWiseReportDetails");%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="sizeWiseReportDetails.do?method=sizeWiseReportDetails" method="POST" enctype="multipart/form-data">
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
												<TD width="55%" class="Heading"><bean:message key="sizeWiseReportHeaderAmount" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>

									<TR>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="mli" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="memberId" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="1" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="5000" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="10000"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="15000"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="20000"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="25000"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="50000"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="75000"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="100000"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="200000"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="500000"/>
									</TD>

									</TR>	

									<%
									double amount0 = 0;
									double newAmount0 = 0;
									double totalAmount0 = 0;
									double amount1 = 0;
									double totalAmount1 = 0;
									double newAmount1 = 0;
									double amount2 = 0;
									double totalAmount2 = 0;
									double newAmount2 = 0;
									double amount3 = 0;
									double totalAmount3 = 0;
									double newAmount3 = 0;
									double amount4 = 0;
									double totalAmount4 = 0;
									double newAmount4 = 0;
									double amount5 = 0;
									double totalAmount5 = 0;
									double newAmount5 = 0;
									double amount6 = 0;
									double totalAmount6 = 0;
									double newAmount6 = 0;
									double amount7 = 0;
									double totalAmount7 = 0;
									double newAmount7 = 0;
									double amount8 = 0;
									double totalAmount8 = 0;
									double newAmount8 = 0;
									double amount9 = 0;
									double totalAmount9 = 0;
									double newAmount9 = 0;
									double amount10 = 0;
									double totalAmount10 = 0;
									double newAmount10 = 0;
									int count0 = 0;
									int totalCount0 = 0;
									int count1 = 0;
									int totalCount1 = 0;
									int count2 = 0;
									int totalCount2 = 0;
									int count3 = 0;
									int totalCount3 = 0;
									int count4 = 0;
									int totalCount4 = 0;
									int count5 = 0;
									int totalCount5 = 0;
									int count6 = 0;
									int totalCount6 = 0;
									int count7 = 0;
									int totalCount7 = 0;
									int count8 = 0;
									int totalCount8 = 0;
									int count9 = 0;
									int totalCount9 = 0;
									int count10 = 0;
									int totalCount10 = 0;
									%>

									<tr>
									<logic:iterate id="object"  name="rsForm"  property="proposalSizeReport">  
										<%
											 com.cgtsi.reports.SizeWiseReport size =  (com.cgtsi.reports.SizeWiseReport)object;
										%>

											<TR>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						
											<%=size.getBank()%>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						
											<%=size.getBankId()%>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
											<div align="right">
											<% amount0=size.getRange0();
											newAmount0=amount0/100000;%>
											<%totalAmount0 = totalAmount0+newAmount0;%>
											<%=decimalFormat.format(newAmount0)%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">			
											<div align="right">
											<% amount1=size.getRange1();
											newAmount1=amount1/100000;%>
											<%totalAmount1 = totalAmount1+newAmount1;%>
											<%=decimalFormat.format(newAmount1)%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
											<div align="right">
											<%amount2=size.getRange2();
											newAmount2=amount2/100000;%>
											<%totalAmount2 = totalAmount2+newAmount2;%>
											<%=decimalFormat.format(newAmount2)%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">		
											<div align="right">
											<%amount3=size.getRange3();
											newAmount3=amount3/100000;%>
											<%totalAmount3 = totalAmount3+newAmount3;%>
											<%=decimalFormat.format(newAmount3)%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">		
											<div align="right">
											<% amount4=size.getRange4();
											newAmount4=amount4/100000;%>
											<%totalAmount4 = totalAmount4+newAmount4;%>
											<%=decimalFormat.format(newAmount4)%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
											<div align="right">
											<% amount5=size.getRange5();
											newAmount5=amount5/100000;%>
											<%totalAmount5 = totalAmount5+newAmount5;%>
											<%=decimalFormat.format(newAmount5)%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<div align="right">
											<%amount6=size.getRange6();
											newAmount6=amount6/100000;%>
											<%totalAmount6 = totalAmount6+newAmount6;%>
											<%=decimalFormat.format(newAmount6)%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<div align="right">
											<%amount7=size.getRange7();
											newAmount7=amount7/100000;%>
											<%totalAmount7 = totalAmount7+newAmount7;%>
											<%=decimalFormat.format(newAmount7)%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<div align="right">
											<%amount8=size.getRange8();
											newAmount8=amount8/100000;%>
											<%totalAmount8 = totalAmount8+newAmount8;%>
											<%=decimalFormat.format(newAmount8)%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<div align="right">
											<%amount9=size.getRange9();
											newAmount9=amount9/100000;%>
											<%totalAmount9 = totalAmount9+newAmount9;%>
											<%=decimalFormat.format(newAmount9)%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<div align="right">
											<%amount10=size.getRange10();
											newAmount10=amount10/100000;%>
											<%totalAmount10 = totalAmount10+newAmount10;%>
											<%=decimalFormat.format(newAmount10)%>
											</div>
											</TD>
											</TR>
											</logic:iterate>


											<TR>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						
											Total
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						

											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">	
											<div align="right">
											<%=decimalFormat.format(totalAmount0)%>
											</div>
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
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						
											<div align="right">

											<%=decimalFormat.format(totalAmount9)%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						
											<div align="right">

											<%=decimalFormat.format(totalAmount10)%>
											</div>
											</TD>
											</TR>

									</TABLE>

								

									<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="16"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="40%" class="Heading"><bean:message key="sizeWiseReportHeaderCount" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>

											
									<TR>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="mli" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">						
										<bean:message key="memberId" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="1" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="5000" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="10000"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="15000"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="20000"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="25000"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="50000"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="75000"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="100000"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="200000"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="500000"/>
									</TD>

									</TR>	

									<tr>
									<logic:iterate id="object"  name="rsForm"  property="proposalSizeReport">  
										<%
											 com.cgtsi.reports.SizeWiseReport size =  (com.cgtsi.reports.SizeWiseReport)object;
										%>

											<TR>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						
											<%=size.getBank()%>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						
											<%=size.getBankId()%>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">		
											<div align="right">
											<%count0=size.getCount0();%>
											<%totalCount0=totalCount0+count0;%>
											<%=count0%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
											<div align="right">
											<%count1=size.getCount1();%>
											<%totalCount1=totalCount1+count1;%>
											<%=count1%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">			
											<div align="right">
											<%count2=size.getCount2();%>
											<%totalCount2=totalCount2+count2;%>
											<%=count2%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
											<div align="right">
											<%count3=size.getCount3();%>
											<%totalCount3=totalCount3+count3;%>
											<%=count3%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">		
											<div align="right">
											<%count4=size.getCount4();%>
											<%totalCount4=totalCount4+count4;%>
											<%=count4%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">		
											<div align="right">
											<%count5=size.getCount5();%>
											<%totalCount5=totalCount5+count5;%>
											<%=count5%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">		
											<div align="right">
											<%count6=size.getCount6();%>
											<%totalCount6=totalCount6+count6;%>
											<%=count6%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">		
											<div align="right">
											<%count7=size.getCount7();%>
											<%totalCount7=totalCount7+count7;%>
											<%=count7%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
											<div align="right">
											<%count8=size.getCount8();%>
											<%totalCount8=totalCount8+count8;%>
											<%=count8%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
											<div align="right">
											<%count9=size.getCount9();%>
											<%totalCount9=totalCount9+count9;%>
											<%=count9%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
											<div align="right">
											<%count10=size.getCount10();%>
											<%totalCount10=totalCount10+count10;%>
											<%=count10%>
											</div>
											</TD>
											</TR>
											</logic:iterate>


											<TR>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						
											Total
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						

											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">	
											<div align="right">
											<%=totalCount0%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">	
											<div align="right">
											<%=totalCount1%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">	
											<div align="right">
											<%=totalCount2%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">
											<div align="right">
											<%=totalCount3%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">
											<div align="right">
											<%=totalCount4%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">	
											<div align="right">
											<%=totalCount5%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">
											<div align="right">
											<%=totalCount6%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">	
											<div align="right">
											<%=totalCount7%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">	
											<div align="right">
											<%=totalCount8%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">	
											<div align="right">
											<%=totalCount9%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						
											<div align="right">
											<%=totalCount10%>
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
						
								<A href="javascript:submitForm('sizeWiseReport.do?method=sizeWiseReport')">
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
