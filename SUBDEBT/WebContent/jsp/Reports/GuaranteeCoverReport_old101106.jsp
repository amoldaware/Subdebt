<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@page import ="java.text.SimpleDateFormat"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@page import ="java.text.DecimalFormat"%>
<%DecimalFormat decimalFormat = new DecimalFormat("##########.##");%>

<% session.setAttribute("CurrentPage","guaranteeCoverReportDetails.do?method=guaranteeCoverReportDetails");%>
<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="guaranteeCoverReport.do?method=guaranteeCoverReport" method="POST" enctype="multipart/form-data">
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
									<TD colspan="13"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="30%" class="Heading"><bean:message key="guaranteeHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>

			<tr>
		        
		<%
			double tc = 0;
			double tcTotal = 0;
			double wc = 0;
			double wcTotal = 0;
			double sum = 0;
			double sumTotal = 0;
			double gFee = 0;
			double gFeeTotal = 0;
			int days = 0;
			int daysTotal = 0;
			int count = 0;


			double tempTc = 0;
			double tempTcTotal = 0;
			double tempWc = 0;
			double TempWcTotal = 0;
			double tempSum = 0;
			double tempSumTotal = 0;
			double tempGFee = 0;
			double tempGFeeTotal = 0;
			int tempDays = 0;
			int tempDaysTotal = 0;
			int tempCount = 0;
			
		%>

		<tr>
		<logic:iterate id="object" name="rsForm" property="guaranteeCoverSsi">  
		
			<%
				 com.cgtsi.reports.GuaranteeCoverIssued coverIssued =  (com.cgtsi.reports.GuaranteeCoverIssued)object;
				 pageContext.setAttribute("coverIssued",coverIssued.getMemberCgpans());	 
				 
				tempTc = 0;
				tempTcTotal = 0;
				tempWc = 0;
				TempWcTotal = 0;
				tempSum = 0;
				tempSumTotal = 0;
				tempGFee = 0;
				tempGFeeTotal = 0;
				tempDays = 0;
				tempDaysTotal = 0;
				tempCount = 0;
				 
			%>
		<TR>
			<td class="ColumnBackground " colspan="6"><bean:message key="memberId" />&nbsp;<%=coverIssued.getMemberId()%></td>
			<td class="ColumnBackground " colspan="6"><%=coverIssued.getMemberShortName()+"-"+coverIssued.getMemberZoneName()%></td>
		
		</TR>			
		<tr>
			<td class="ColumnBackground " width="15%" rowspan="2"><div align="center"><bean:message key="CGPAN" /></div></td>
			<td class="ColumnBackground " width="15%" rowspan="2"><div align="center"><bean:message key="ssiName" /></div></td>
			<td class="ColumnBackground " width="15%" colspan="5"><div align="center"><bean:message key="creditSanctioned" /></div></td>
			<td class="ColumnBackground " width="15%" rowspan="2"><div align="center"><bean:message key="gFeePaid" /></div></td>
			<td class="ColumnBackground " width="15%" colspan="3"><div align="center"><bean:message key="gCover" /></div></td>
			<td class="ColumnBackground " width="15%" rowspan="2"><div align="center"><bean:message key="interval" /></div></td>
		</tr>
	
		<tr>
			<td class="ColumnBackground " width="15%"><div align="center"><bean:message key="tc" /></div></td>
			<td class="ColumnBackground " width="15%"><div align="center"><bean:message key="wc" /></div></td>
			<td class="ColumnBackground " width="15%"><div align="center"><bean:message key="total" /></div></td>
			<td class="ColumnBackground " width="15%"><div align="center"><bean:message key="date" /></div></td>
			<td class="ColumnBackground " width="15%"><div align="center"><bean:message key="tenure" /></div></td>
			<td class="ColumnBackground " width="15%"><div align="center"><bean:message key="approvalDate" /></div></td>
			<td class="ColumnBackground " width="15%"><div align="center"><bean:message key="issueDate" /></div></td>
			<td class="ColumnBackground " width="15%"><div align="center"><bean:message key="endDate" /></div></td>
		</tr>		
		<logic:iterate id="guaranteeCover" name="coverIssued" >  
		
		<%

			com.cgtsi.reports.GuaranteeCover gCover =  (com.cgtsi.reports.GuaranteeCover)guaranteeCover;
		%>



			
		<tr>
		<%
			java.util.Date tcDate = gCover.getTcDate();
			java.util.Date wcDate = gCover.getWcDate();
			tempCount++;
			count++;
			
		%>
			<td class="tableData" width="15%"><div align="left"></div><%=gCover.getCgpan()%></td>
			<td class="tableData" width="15%"><div align="left"><%=gCover.getSsiName()%></div></td>


			<td class="tableData" width="15%"><div align="right">
			<%tc=gCover.getTermCredit();
			tcTotal=tcTotal+tc;
			tempTcTotal+=tc;
			%>
			<%=decimalFormat.format(tc)%>
			</div></td>
			<td class="tableData" width="15%"><div align="right">
			<%wc=gCover.getWorkingCapital();
			wcTotal=wcTotal+wc;
			TempWcTotal+=wc;%>
			<%=decimalFormat.format(wc)%>
			</div></td>
			<td class="tableData" width="15%"><div align="right">
			<%sum=gCover.getTotal();
			sumTotal=sumTotal+sum;
			tempSumTotal+=sum;
			%>
			<%=decimalFormat.format(sum)%>
			</div></td>

				<%
				if (tcDate != null)
				{ 
				%>
					<td class="tableData" width="15%"><div align="left">
					<%   java.util.Date utilDate=gCover.getTcDate();
							String formatedDate=dateFormat.format(utilDate);
					%>
					<%=formatedDate%></div></td>
				<%
				}
				else if(wcDate != null)
				{
				%>				
				<td class="tableData" width="15%"><div align="left">
					<%   java.util.Date utilDate1=gCover.getWcDate();
							String formatedDate1=dateFormat.format(utilDate1);
					%>
					<%=formatedDate1%></div></td>
			<%
				}
			%>

			<td class="tableData" width="15%"><div align="right"><%=gCover.getTenure()%></div></td>

			<td class="tableData" width="15%"><div align="right">
			<%gFee=gCover.getGuaranteeFee();
			gFeeTotal=gFeeTotal+gFee;
			tempGFeeTotal+=gFee;
			%>
			<%=decimalFormat.format(gFee)%>
			</div></td>

			<td class="tableData" width="15%"><div align="left">
			<%   java.util.Date utilDate4=gCover.getApprovalDate();
					String formatedDate4 = null;
					if(utilDate4 != null)
					{
						 formatedDate4=dateFormat.format(utilDate4);
					}
					else
					{
						 formatedDate4 = "";
					}
				%>
				<%=formatedDate4%></div></td>

			<td class="tableData" width="15%"><div align="left">
			<%   java.util.Date utilDate5=gCover.getIssueDate();
					String formatedDate5 = null;
					if(utilDate5 != null)
					{
						 formatedDate5=dateFormat.format(utilDate5);
					}
					else
					{
						 formatedDate5 = "";
					}
				%>
			<%=formatedDate5%></div></td>
			<%
				if (tcDate != null)
				{ 
				%>
				<td class="tableData" width="15%"><div align="left">
					<%   java.util.Date utilDate2=gCover.getTcEndDate();
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
					<%=formatedDate2%></div></td>
				<%
				}
				else if(wcDate != null)
				{
					%>				
				<td class="tableData" width="15%"><div align="left">
				<%   java.util.Date utilDate3=gCover.getWcEndDate();
					String formatedDate3 = null;
					if(utilDate3 != null)
					{
						 formatedDate3=dateFormat.format(utilDate3);
					}
					else
					{
						formatedDate3 = "";
					}
					%>
						<%=formatedDate3%></div></td>
			<%
				}
			%>
			<td class="tableData" width="15%"><div align="right">
			<%days=gCover.getTimeInterval();
			  daysTotal=daysTotal+days;
			  tempDaysTotal+=days;
			  %>
			  <%=days%>
			</div></td>
		</tr>
	
		</logic:iterate>
		
		<tr>
			<td class="ColumnBackground " width="15%"><div align="right">Total</div></td>
			<td class="ColumnBackground " width="15%">
			<td class="ColumnBackground " width="15%"><div align="right">
			<%=decimalFormat.format(tempTcTotal)%></div></td>
			<td class="ColumnBackground " width="15%"><div align="right">
			<%=decimalFormat.format(TempWcTotal)%></div></td>
			<td class="ColumnBackground " width="15%"><div align="right">
			<%=decimalFormat.format(tempSumTotal)%></div></td>
			<td class="ColumnBackground " width="15%"></td>
			<td class="ColumnBackground " width="15%"></td>
			<td class="ColumnBackground " width="15%"><div align="right">
			<%=decimalFormat.format(tempGFeeTotal)%></div></td>
			<td class="ColumnBackground " width="15%"></td>
			<td class="ColumnBackground " width="15%"></td>
			<td class="ColumnBackground " width="15%">Average</td>
			<td class="ColumnBackground " width="15%"><div align="right"><%=tempDaysTotal/tempCount%></div></td>

		</tr>
		</logic:iterate>
		<tr>
			<td class="ColumnBackground " width="15%"><div align="right">Grand Total</div></td>
			<td class="ColumnBackground " width="15%">
			<td class="ColumnBackground " width="15%"><div align="right">
			<%=decimalFormat.format(tcTotal)%></div></td>
			<td class="ColumnBackground " width="15%"><div align="right">
			<%=decimalFormat.format(wcTotal)%></div></td>
			<td class="ColumnBackground " width="15%"><div align="right">
			<%=decimalFormat.format(sumTotal)%></div></td>
			<td class="ColumnBackground " width="15%"></td>
			<td class="ColumnBackground " width="15%"></td>
			<td class="ColumnBackground " width="15%"><div align="right">
			<%=decimalFormat.format(gFeeTotal)%></div></td>
			<td class="ColumnBackground " width="15%"></td>
			<td class="ColumnBackground " width="15%"></td>
			<td class="ColumnBackground " width="15%">Average</td>
			<td class="ColumnBackground " width="15%"><div align="right"><%=daysTotal/count%></div></td>

		</tr>

		</TABLE>
				</TD>
					</TR>
				<tr>
				<td>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
				<tr>
				<td class="tableData">
				<b>Approval Date</b>: Date of Approval of GCover/ Demand Advice Raised
				<br><b>Issue Date</b>: Date of Receipt of GCover/ Commencement of GCover
				<br><b>*End Date</b> is subject to payment of Annual Service Feeand compliance of others terms and conditions of the Scheme
				</td>
				</tr>
				</TABLE>
				</td>
				</tr>

					<TR> 
						<TD align="center" valign="baseline" >


							<DIV align="center">
							<A href="javascript:submitForm('guaranteeCoverReport.do?method=guaranteeCoverReport')">
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

