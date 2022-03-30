<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@page import ="java.text.SimpleDateFormat"%>
<%@page import ="java.text.DecimalFormat"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","danReport.do?method=danReport");%>
<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>
<%DecimalFormat decimalFormat = new DecimalFormat("##########.##");%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="danReport.do?method=danReport" method="POST" enctype="multipart/form-data">
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
									<TD colspan="8"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<tr>
                          <td colspan="6" class="Heading1" align="center"><u><bean:message key="reportHeader"/></u></td>
                      </tr>
                      <tr> <td colspan="6">&nbsp;</td></tr>
                      <TR>
												<TD width="15%" class="Heading"><bean:message key="danReport" /></TD>
												<td class="Heading" width="30%">&nbsp;<bean:message key="from"/> <bean:write  name="rsForm" property="dateOfTheDocument20"/>&nbsp;<bean:message key="to"/> <bean:write  name="rsForm" property="dateOfTheDocument21"/></td>
                        <TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>

									<TR>
                  <td width="3%" align="left" valign="top" class="ColumnBackground"><bean:message key="sNo"/></td>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="memberId" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="daNumber" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="generatedDate" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="noOfAppls" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="danAmount"/>
									</TD>

									<%	double sum = 0;
										double totalSum = 0;
										int count = 0;
										int totalCount = 0;
									%>
									</TR>	

									<tr>
									<logic:iterate id="object" name="rsForm" property="dan" indexId="index">
									<%
									     com.cgtsi.reports.DanReport dReport =  (com.cgtsi.reports.DanReport)object;
										 
									%>
							
											<TR>
                      <td align="left" valign="top" class="ColumnBackground1"><%=Integer.parseInt(index+"")+1%></td>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">
											<%=dReport.getMemberId()%>
											<%String bank=dReport.getBank();
											String zone=dReport.getZone();
											String branch=dReport.getBranch();%>
											<%   java.util.Date utilDate=dReport.getDanDate();
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
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%String danId=dReport.getDan();
											String url = "danReportDetails.do?method=danReportDetails&danValue="+danId+"&bankName="+bank+"&zoneName="+zone+"&branchName="+branch+"&date="+formatedDate;%>
											<html:link href="<%=url%>"><%=danId%></html:link>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%=formatedDate%>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">
											<div align="right">
											<%count=dReport.getCount();
											   totalCount = totalCount + count;%>
											   <%=count%>
											    </div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">		
											<div align="right">
											   <% sum=dReport.getTotalAmount();
											   totalSum = totalSum + sum;
											   if(sum == 0)
											   {
											   %>
											   <%="-"%>
											   <%
											   }
											   else
											   {
											   %>
											   <%=decimalFormat.format(sum)%>
											   <%
											   }
											   %>

											   </div>
											   </TD>
											</TR>
											</logic:iterate>

									<TR>
									<TD width="10%" colspan="2" align="left" valign="top" class="ColumnBackground">
									Total
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
									
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
									
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
									<div align="right">
									<%=totalCount%>
									</div>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
									<div align="right">
									<%=decimalFormat.format(totalSum)%>
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
								<A href="javascript:submitForm('danReportInput.do?method=danReportInput')">
									<IMG src="images/Back.gif" alt="Print" width="49" height="37" border="0"></A>

									
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

