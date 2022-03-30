<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@page import ="java.text.SimpleDateFormat"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","ddDepositReport.do?method=ddDepositReport");%>
<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>
<%@page import ="java.text.DecimalFormat"%>
<%DecimalFormat decimalFormat = new DecimalFormat("#########0.0##");%>

<TABLE width="800" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="ddDepositReport.do?method=ddDepositReport" method="POST" enctype="multipart/form-data">
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
									<TD colspan="11"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<tr>
                          <td colspan="6" class="Heading1" align="center"><u>Credit Guarantee Fund Trust for Micro and Small Enterprises&nbsp;</u></td>
                      </tr>
                      <tr> <td colspan="6">&nbsp;</td></tr>
                      
                      <TR>
												<TD width="18%" class="Heading">DD Depostied Report Details</TD>
												<td class="Heading" width="40%">&nbsp;<bean:message key="from"/> <bean:write  name="rsForm" property="dateOfTheDocument26"/>&nbsp;<bean:message key="to"/> <bean:write  name="rsForm" property="dateOfTheDocument27"/></td>
                        <TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>
        			</TABLE>
									</TD>

									<TR>
                  <td width="3%" align="left" valign="top" class="ColumnBackground">
                  <bean:message key="sNo"/></td>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="inwardId" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
									 Inward Dt.
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="bankName" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
								   Drawn On Bank&nbsp;
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
									 <bean:message key="place" />
									</TD>
									<TD width="15%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="instrumentNumber" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="instrumentDate" />
									</TD>
									<TD width="15%" align="left" valign="top" class="ColumnBackground">
									 <bean:message key="instrumentAmount" />
									</TD>
                  <TD width="20%" align="left" valign="top" class="ColumnBackground">
									 Inward Section &nbsp;
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="DateOfDep" />
									</TD>
									
                  
									</TR>	
	
								<%
										double amount = 0;
										double totalAmount = 0;
									%>

									<tr>
								<logic:iterate name="rsForm" id="object" property="danRaised" indexId="index">
               	<%
									     com.cgtsi.reports.GeneralReport danReport = (com.cgtsi.reports.GeneralReport)object;
							  %>
										<TR>
                      <td align="left" valign="top" class="ColumnBackground1"><%=Integer.parseInt(index+"")+1%></td>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%=danReport.getInwardNum()%> 
											</TD>
                      
                      <TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											 <% java.util.Date inwardDate=danReport.getDateOfTheDocument();
													String inwardDt = null;
													if(inwardDate != null)
													{
														 inwardDt=dateFormat.format(inwardDate);
													}
													else
													{
														 inwardDt = "";
													}
											%>
											<%=inwardDt%>
			             </TD>
                    <TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%=danReport.getBankName()%> 
										</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%   String drawnOnBank =  danReport.getDrawnonBank();
                     	if((drawnOnBank == null)||(drawnOnBank.equals("")))
                      {
                       %>
                       <%=""%>
                       <% } else
											{
											%>
											<%=drawnOnBank%>
											<%
											}
                      %> 
											</TD>
                      
									    
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%= danReport.getPlace() %>
               			</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
                      <%String ddNumber= danReport.getDdNum();
											if((ddNumber == null)||(ddNumber.equals("")))
											{
											%>
											<%=""%>
											<%
											}
											else
											{
											%>
											<%=ddNumber%>
											<%
											}
											%>
											</TD>
											
                    <TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											 <% java.util.Date instrumentDate=danReport.getDateOfTheDocument1();
													String instrumentDt = null;
													if(instrumentDate != null)
													{
														 instrumentDt=dateFormat.format(instrumentDate);
													}
													else
													{
														 instrumentDt = "";
													}
											%>
											<%=instrumentDt%>
			             </TD>
                   
                   <TD width="10%" align="right" valign="top" class="ColumnBackground1">
                    <div align="right"><%totalAmount = totalAmount + danReport.getInstrumentAmt(); %>
                      <%=decimalFormat.format(danReport.getInstrumentAmt())%> </div>
                   </TD>
                    
                  <TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%= danReport.getInwardSection() %>
               		</TD>
                   
                   <TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											 <% java.util.Date depositedDate=danReport.getDateOfTheDocument10();
													String depositDt = null;
													if(depositedDate != null)
													{
														 depositDt=dateFormat.format(depositedDate);
													}
													else
													{
														 depositDt = "";
													}
											%>
											<%=depositDt%>
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
								<A href="javascript:submitForm('ddDepositReportInput.do?method=ddDepositReportInput'')">
									<IMG src="images/Back.gif" alt="Back" width="49" height="37" border="0">
                  </A>	
                  <A href="javascript:printpage()">
                  <IMG src="images/Print.gif" alt="Print" width="49" height="37" border="0">
								 </A>             
        					<!-- <IMG src="images/Print.gif" alt="Print" width="49" height="37" border="0"></A> -->
								
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

