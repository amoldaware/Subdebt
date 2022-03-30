<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@page import ="java.text.SimpleDateFormat"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","rpCancelledReportDetails.do?method=rpCancelledReportDetails");%>
<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>
<%@page import ="java.text.DecimalFormat"%>
<%DecimalFormat decimalFormat = new DecimalFormat("#########0.0##");%>

<TABLE width="800" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
<html:form action="rpCancelledReportDetails.do?method=rpCancelledReportDetails" method="POST" enctype="multipart/form-data">
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
											<tr>
                          <td colspan="6" class="Heading1" align="center"><u>Credit Guarantee Fund Trust for Micro and Small Enterprises&nbsp;</u></td>
                      </tr>
                      <tr> <td colspan="6">&nbsp;</td></tr>
                      
                      <TR>
												<TD width="25%" class="Heading">RP Cancelled Report Details&nbsp;</TD>
												<td class="Heading" width="25%">&nbsp;</td>
                        <TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>
        			</TABLE>
									</TD>

									<TR>
                  <td width="3%" align="left" valign="top" class="ColumnBackground"><bean:message key="sNo"/></td>
                  
                  <TD  align="left" class="ColumnBackground"> 
                  <bean:message key="MemberID"/>
                  </TD>
                  <TD  align="left" class="ColumnBackground"> 
                  <bean:message key="SSIBorrowerName"/>
                  </TD>                 
                  
                  <TD  align="left" class="ColumnBackground"> 
                  <bean:message key="dan"/>
                  </TD>
                  
                  <TD  align="left" class="ColumnBackground"> 
                  <bean:message key="cgpanForApplication"/>
                  </TD>
                  
                  <TD  align="right" class="ColumnBackground"> 
                  &nbsp;DAN Amt
                  </TD>
                  <TD width = "25%" align="left" class="ColumnBackground"> 
                  &nbsp;<bean:message key="payId"/>
                  </TD>
                  
                    <TD width = "25%" align="left" class="ColumnBackground"> 
                  &nbsp;<bean:message key="remarks"/>
                  </TD> 
                  <TD align="left" class="ColumnBackground">                    
                  &nbsp;<bean:message key="instrumentNumber"/>
                  </TD>
                  <TD align="left" class="ColumnBackground"> 
                  &nbsp;<bean:message key="instrumentDate"/>
                  </TD>
 
									</TR>	
	
								<%
										double amount = 0;
										double totalAmount = 0;
									%>

									<tr>
								<logic:iterate id="object" name="rsForm" property="rpCancelledReport" indexId="index">
                <% com.cgtsi.reports.ApplicationReport dReport =  (com.cgtsi.reports.ApplicationReport)object;%> 
              			<TR>
                     <TD  align="left" valign="top" class="ColumnBackground1">
                  &nbsp;		<%=Integer.parseInt(index+"")+1%>
                   </TD>
                     <TD  align="left" valign="top" class="ColumnBackground1">
                   <%=dReport.getMemberId() %>&nbsp;
                   </TD>
                    <TD  align="left" valign="top" class="ColumnBackground1">
                   <%=dReport.getBid() %>&nbsp;
                   </TD>
                    <TD  align="left" valign="top" class="ColumnBackground1">
                   <%=dReport.getDan() %>&nbsp;
                     </TD>
                      <TD  align="left" valign="top" class="ColumnBackground1">
                   <%=dReport.getCgpan() %>&nbsp;
                     </TD>
                    <TD  align="right" valign="top" class="ColumnBackground1">
                    <% 
                     amount =Integer.parseInt(dReport.getDciAmountRaised());
                     totalAmount = totalAmount+amount;
                    %>
                  <div align="right"> <%=dReport.getDciAmountRaised()%></div>&nbsp;
                     </TD> 
                  <TD width="40%" align="left" valign="top" class="ColumnBackground1">
                   <%=dReport.getPayId() %>&nbsp;
                     </TD>    
                   <TD  align="left" valign="top" class="ColumnBackground1">
                   <%=dReport.getRemarks() %>&nbsp;
                     </TD>  
                     <TD  align="left" valign="top" class="ColumnBackground1">
                   <%=dReport.getDdNum() %>&nbsp;
                     </TD>  
                      <TD  align="left" valign="top" class="ColumnBackground1">
                         <%java.util.Date utilDate=dReport.getDbrDt();
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
        			<TR>
											<TD width="10%" align="left" colspan="2" valign="top" class="ColumnBackground">						
											Total
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						

											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						

											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						

											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">		
                       <div align="right">
											<%=decimalFormat.format(totalAmount)%>
											</div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						

											</TD>
											
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						

											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						
											</TD>
                      
                      <TD width="10%" align="left" valign="top" class="ColumnBackground">	
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
								<A href="javascript:history.back()">
									<IMG src="images/Back.gif" alt="Back" width="49" height="37" border="0"></A>													
							<!--	<INPUT type="button" value="Export to Excel" onclick="exportToExcel()" />   -->             
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

