<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","progressReport.do?method=progressReport");%>
<%@page import ="java.text.DecimalFormat"%>
<%DecimalFormat decimalFormat = new DecimalFormat("##########.##");%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="progressReport.do?method=progressReport" method="POST" enctype="multipart/form-data">
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
									<TD colspan="10"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="22%" class="Heading"><bean:message key="progressReportHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>

	
				<tr> 
				<td colspan="12"><table width="100%" border="0" cellspacing="1" cellpadding="0">
                <tr> 
                  <td width="3%" rowspan="3" align="center" valign="middle" class="ColumnBackground "> 
                    <div align="center">Sl.<br>No.</div></td>
                  <td width="25%" rowspan="3" class="ColumnBackground "><div align="center">Name of MLI </div></td>
				   <td width="10%" rowspan="3" class="ColumnBackground "><div align="center">MLI ID </div></td>
                  <td colspan="4" class="ColumnBackground"> <div align="center">No. of Proposals</div></td>
                  <td rowspan="2" class="ColumnBackground" colspan="2" width="25%"> <div align="center">Amount Sanctioned (in Rs.Lakh)</div></td>
                  <td rowspan="2" class="ColumnBackground" colspan="2" width="25%"> <div align="center">Cumulative Position<br>(Proposals)</div></td>
                  <td colspan="2" class="ColumnBackground" rowspan="2"> <div align="center">Guarantee Issued<br>(Cumulative)</div></td>
                </tr>
                <tr align="center" valign="middle"> 
                  <td width="10%" class="ColumnBackground" colspan="2"><div align="center">Received</div></td>
                  <td width="10%" class="ColumnBackground" colspan="2"><div align="center">Approved</div></td>
				</tr>

				<tr align="center" valign="middle">
				  <td class="ColumnBackground"><div align="center">During the Month</div></td>
				  <td class="ColumnBackground "><div align="center">Cumulative</div></td>
				  <td class="ColumnBackground "><div align="center">During the Month</div></td>
				  <td class="ColumnBackground "><div align="center">Cumulative<br>#</div></td>
				  <td class="ColumnBackground "><div align="center">During the Month</div></td>
				  <td class="ColumnBackground "><div align="center">Cumulative<br>#</div></td>
				  <td class="ColumnBackground "><div align="center">Ineligible /<br>Cancelled</div></td>
				  <td class="ColumnBackground "><div align="center">Pending</div></td>
				  <td class="ColumnBackground "><div align="center">No. of Proposals</div></td>
				  <td class="ColumnBackground "><div align="center">Amount<br>(in Rs.Lakh)</div></td>
				</tr>

				<%	double amountDuring = 0;
					double newAmountDuring = 0;
					double totalAmountDuring = 0;
					double amountCumulative = 0;
					double newAmountCumulative = 0;
					double totalAmountCumulative = 0;
					double gFee = 0;
					double newGFee = 0;
					double totalgFee = 0;
					int recievedDuring = 0 ;
					int totalRecievedDuring = 0 ;
					int recievedCumulative = 0 ;
					int totalRecievedCumulative = 0 ;
					int approvedDuring = 0 ;
					int totalApprovedDuring = 0 ;
					int approvedCumulative = 0 ;
					int totalApprovedCumulative = 0 ;
					int cancelled = 0 ;
					int totalCancelled = 0 ;
					int pending = 0 ;
					int totalPending = 0 ;
					int proposals = 0 ;
					int totalProposals = 0 ;
				%>

				<tr>
				<%
				int serial = 0;
				%>

				<logic:iterate id="object" name="rsForm" property="progressReport">
				<%
				com.cgtsi.reports.ProgressReport pReport = (com.cgtsi.reports.ProgressReport)object;
				%>

				<tr class="tableData">
				  <td><div align="right">&nbsp;<%= ++serial%></div></td>
				  <td><div align="right">&nbsp;<%= pReport.getBankName()%></div></td>
				  <td><div align="right">&nbsp;<%= pReport.getMemberId()%></div></td>
				  <td><div align="right">
				  <%recievedDuring =  pReport.getProposalsRecievedDuring();
				  totalRecievedDuring = totalRecievedDuring + recievedDuring;%>
				  <%=recievedDuring %>
				  &nbsp;</div></td>
				  <td><div align="right">
				  <%recievedCumulative =  pReport.getTotalProposalsRecieved();
  				  totalRecievedCumulative = totalRecievedCumulative + recievedCumulative;%>
				  <%=recievedCumulative %>
				  &nbsp;</div></td>
				  <td><div align="right">
				  <%approvedDuring = pReport.getProposalsApprovedDuring();
				  totalApprovedDuring = totalApprovedDuring + approvedDuring;%>
				  <%=approvedDuring %>
				  &nbsp;</div></td>
				  <td><div align="right">
				  <%approvedCumulative = pReport.getTotalProposalsApproved();
				  totalApprovedCumulative = totalApprovedCumulative + approvedCumulative;%>
				  <%=approvedCumulative%>
				  &nbsp;</div></td>
				  <td><div align="right">
				  <%amountDuring= pReport.getSanctionedAmountDuring();
				  newAmountDuring= amountDuring/100000;
				   totalAmountDuring = totalAmountDuring + newAmountDuring;%>
				   <%=decimalFormat.format(newAmountDuring)%>
				  </div></td>
				 <td><div align="right">
				 <%amountCumulative= pReport.getTotalSanctionedAmount();
				 newAmountCumulative= amountCumulative/100000;
				   totalAmountCumulative = totalAmountCumulative + newAmountCumulative;%>
				   <%=decimalFormat.format(newAmountCumulative)%>
				 </div></td>
				  <td><div align="right">
				  <%cancelled = pReport.getProposalsCancelled();
				  totalCancelled = totalCancelled + cancelled;%>
				  <%=cancelled%>
				   &nbsp;</div></td>
				  <td><div align="right">
				  <%pending = pReport.getProposalsPending();
				  totalPending = totalPending + pending;%>
				  <%=pending%>
				   &nbsp;</div></td>
				  <td><div align="right">
				  <%proposals = pReport.getNumberOfProposals();
				  totalProposals = totalProposals + proposals;%>
				  <%=proposals%>
				  &nbsp;</div></td>
				  <td><div align="right">
				  <% gFee= pReport.getGuaranteeIssued();
					newGFee= gFee/100000;
				   totalgFee = totalgFee + newGFee;%>
				   <%=decimalFormat.format(newGFee)%>
				  </div></td>
				</tr>

			
				</logic:iterate>

				<tr class="tableData">
				  <td><div align="right">&nbsp;</div></td>
				  <td><div align="right"><b>Total</b>&nbsp;</div></td>
				  <td><div align="right">&nbsp;</div></td>
				  <td><div align="right"><b>
				  <%=totalRecievedDuring%></b>
				  &nbsp;</div></td>
				  <td><div align="right"><b>
				  <%=totalRecievedCumulative%></b>
				  &nbsp;</div></td>
				  <td><div align="right"><b>
				  <%=totalApprovedDuring%></b>
				  &nbsp;</div></td>
				  <td><div align="right"><b>
				  <%=totalApprovedCumulative%></b>
				  &nbsp;</div></td>
				  <td><div align="right"><b>
				   <%=decimalFormat.format(totalAmountDuring)%></b>
				 </div></td>
				 <td><div align="right"><b>
				   <%=decimalFormat.format(totalAmountCumulative)%></b>
				 </div></td>
				  <td><div align="right"><b>
				  <%=totalCancelled %></b>
				   &nbsp;</div></td>
				  <td><div align="right"><b>
				  <%=totalPending %></b>
				   &nbsp;</div></td>
				  <td><div align="right"><b>
				  <%=totalProposals%></b>
				  &nbsp;</div></td>
				  <td><div align="right"><b>
				   <%=decimalFormat.format(totalgFee)%></b>
				  </div></td>
				</tr>

				</TABLE>
				
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
						<TD align="center" valign="baseline">
							<DIV align="center">
								<A href="javascript:submitForm('monthlyProgressReport.do?method=monthlyProgressReport')">
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

