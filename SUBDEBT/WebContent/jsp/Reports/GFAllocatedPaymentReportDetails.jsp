<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@page import ="java.text.DecimalFormat"%>
<%DecimalFormat decimalFormat = new DecimalFormat("##########0.00#");%>
<% session.setAttribute("CurrentPage","gfAllocatedReportDetails.do?method=gfAllocatedReportDetails");%> 
<%@page import = "org.apache.struts.action.DynaActionForm"%>
<% DynaActionForm dynaForm = (DynaActionForm)session.getAttribute("rsForm");
String paymentId = (String) request.getParameter("payId");
// System.out.println("sukumar:"+paymentId);
 %>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="gfAllocatedReportDetails.do?method=gfAllocatedReportDetails" method="POST" enctype="multipart/form-data">
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
               <TR>
									<TD colspan="4"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="40%" class="Heading">Payment Details for <%=paymentId %></TD>
                  				<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="8" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>
                     </table>
                     <TABLE width="100%" border="0" cellpadding="1" cellspacing="1">
              <TR>
          		<td align="left" width="6%" valign="top"  class="ColumnBackground"><bean:message key="sNo"/></td>
              <TD align="left" valign="top" class="ColumnBackground">MemberId
									</TD>
                  <TD align="left" valign="top" class="ColumnBackground">Dand Id
									</TD>
                <TD align="left" valign="top" class="ColumnBackground"><bean:message key="cgpan"/>
									</TD>
                  <TD align="left" valign="top" class="ColumnBackground">Borrower Name
									</TD>
                 <TD align="left"  valign="top" class="ColumnBackground"> Amount Paid
									</TD>
                  <TD align="left" width="20%" valign="top" class="ColumnBackground">RP Number
									</TD>
                <td align="left" valign="top" class="ColumnBackground">Instrument Number</td>
                
          	       <%
                    int count      = 0;
										int totalCount  = 0;
										double sum      = 0.0;
										double totalSum = 0.0;
									%>
           </TR>
           	 <tr>
									<logic:iterate id="object" name="rsForm" property="gfallocatedpaymentdetails" indexId="id">
                  	<% com.cgtsi.reports.PaymentReport pReport =  (com.cgtsi.reports.PaymentReport)object;%>

									<TR align="left" valign="top">
                  <td align="left" valign="top" class="ColumnBackground1"><%=Integer.parseInt(id+"")+1%></td>
                 <TD align="left" valign="top" class="ColumnBackground1">
								 <%=pReport.getMemberId()%>
									</td>
                   <TD align="left" valign="top" class="ColumnBackground1">
								 <%=pReport.getDanId()%>
									</td>
              <TD align="left" valign="top" class="ColumnBackground1">
								   <%=pReport.getCgpan()%>
									</td>
                  <TD align="left" valign="top" class="ColumnBackground1">
								   <%=pReport.getName()%>
									</td>
                   <TD align="left" valign="top" class="ColumnBackground1">
								    <% double paid= pReport.getAmountPaid();
                      %><%=decimalFormat.format(paid)%>
									</td>
                   <TD align="left" valign="top" class="ColumnBackground1">
								  <%=pReport.getPayId()%>
									</td>
                  <TD align="left" valign="top" class="ColumnBackground1">
								  <%=pReport.getInstrumentNumber()%>
									</td>
								  </logic:iterate>
						
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
							<A href="javascript:submitForm('gfallocatedpaymentReport.do?method=gfallocatedpaymentReport')">
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

