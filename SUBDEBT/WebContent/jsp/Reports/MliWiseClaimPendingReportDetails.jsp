<!-- added by sukumar@path for display MLI wise Claim Summary Report Details -->

<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@page import ="java.text.DecimalFormat"%>
<%DecimalFormat decimalFormat = new DecimalFormat("#########0.00#");%>
<% session.setAttribute("CurrentPage","mliWiseClaimPendingReportDetails.do?method=mliWiseClaimPendingReportDetails");%> 
<%@page import = "org.apache.struts.action.DynaActionForm"%>
<% DynaActionForm dynaForm = (DynaActionForm)session.getAttribute("rsForm");
  
%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors/>
	<html:form action="mliWiseClaimPendingReportDetails.do?method=mliWiseClaimPendingReportDetails" method="POST" enctype="multipart/form-data">
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
               <tr>
                  <td colspan="8" class="Heading1" align="center"><u><!--<bean:message key="reportHeader"/>-->Credit Guarantee Fund Trust for Micro and Small Enterprises</u>
                  </td>
                </tr>
                <tr>
                  <td colspan="8">&nbsp;</td>
                </tr>
								<TR>
									<TD colspan="8"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="70%" class="Heading">Claim Pending Cases Report (Declaration Received and Not Received) &nbsp; </TD>
                        <td class="Heading" width="20%">&nbsp; as on &nbsp;<bean:write  name="rsForm" property="dateOfTheDocument13"/>
                        </td>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="5" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>
											</TABLE>
									</TD>
								</TR>

								<TR align="left" valign="top">
                  <td align="left" valign="top" class="ColumnBackground"><bean:message key="sNo"/></td>
                  <TD colspan="2" align="left"  valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="mli"/>
									</TD>
                  <TD align="left" class="ColumnBackground">&nbsp;<bean:message key="pendingCases"/></TD>
                  <TD align="left" class="ColumnBackground">&nbsp;<bean:message key="amt"/></TD>                       
                  <TD align="left" class="ColumnBackground">&nbsp;<bean:message key="declarationReceived"/></TD>
                  <TD align="left" class="ColumnBackground">&nbsp;<bean:message key="amt"/></TD>
                  <TD align="left" class="ColumnBackground">&nbsp;<bean:message key="declarationNotReceived"/></TD>
                  <TD align="left" class="ColumnBackground">&nbsp;<bean:message key="amt"/></TD> 
									<%
                    int newCases = 0;
                    int declarationReceivedCases  = 0;
                    int declarationNotReceivedCases  = 0;
                    
                    
                    double newAmt = 0.0;
                    double declarationReceivedAmt  = 0.0;
                    double declarationNotReceivedAmt  = 0.0;
                    
                    
                    int totalnewCases = 0;
                    int totaldeclarationReceivedCases  = 0;
                    int totaldeclarationNotReceivedCases  = 0;
                                        
                    double totalnewAmt = 0.0;
                    double totaldeclarationReceivedAmt  = 0.0;
                    double totaldeclarationNotReceivedAmt  = 0.0;
                    
									%>
									</TR>
									
									<tr>
									<logic:iterate id="object" name="rsForm" property="mliWiseClaimSummaryReport" indexId="id">
									<% com.cgtsi.reports.GeneralReport dReport =  (com.cgtsi.reports.GeneralReport)object;%>

									<TR align="left" valign="top">
                  <td align="left" valign="top" class="ColumnBackground1"><%=Integer.parseInt(id+"")+1%></td>
                  <TD align="left"  colspan="2" valign="top" class="ColumnBackground1"><%=dReport.getBankName()%>
                  </TD>
									<TD align="left" valign="top" class="ColumnBackground1">
                  <div align="right"><%
                    newCases =  dReport.getPendingCases();
                    totalnewCases = totalnewCases + newCases;  
                  %><%=newCases%></div>
                  </TD>
                  <TD align="left" valign="top" class="ColumnBackground1">
                  <div align="right"><% 
                    newAmt = dReport.getPendingAmt();
                    totalnewAmt = totalnewAmt + newAmt;
                   %><%=dReport.getPendingAmt()%></div>
                  </TD>
                  <TD align="left" valign="top" class="ColumnBackground1">                  
                  <div align="right"><%
                    declarationReceivedCases =  dReport.getNewDeclReceivedCases();
                    totaldeclarationReceivedCases = totaldeclarationReceivedCases + declarationReceivedCases;  
                  %><%=dReport.getNewDeclReceivedCases()%></div>
                  </TD>
                  <TD align="left" valign="top" class="ColumnBackground1">
                  <div align="right"><% 
                    declarationReceivedAmt = dReport.getNewDeclReceivedAmt();
                    totaldeclarationReceivedAmt = totaldeclarationReceivedAmt + declarationReceivedAmt;
                   %><%=dReport.getNewDeclReceivedAmt()%></div>
                  </TD>
                  <TD align="left" valign="top" class="ColumnBackground1">
                  <div align="right"><%
                    declarationNotReceivedCases =  dReport.getNewDeclNotReceivedCases();
                    totaldeclarationNotReceivedCases = totaldeclarationNotReceivedCases + declarationNotReceivedCases;  
                  %><%=dReport.getNewDeclNotReceivedCases()%></div>
                  </TD>
                  <TD align="left" valign="top" class="ColumnBackground1">
                  <div align="right"><% 
                    declarationNotReceivedAmt = dReport.getNewDeclNotReceivedAmt();
                    totaldeclarationNotReceivedAmt = totaldeclarationNotReceivedAmt + declarationNotReceivedAmt;
                   %><%=dReport.getNewDeclNotReceivedAmt()%></div>
                  </TD>       
									</TR> 
								</logic:iterate>

								<tr>
									<TD align="left" valign="top" colspan="3" class="ColumnBackground">
									Total
									</TD>
									<TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%=totalnewCases%>
									</div>
									</TD>
									<TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%=decimalFormat.format(totalnewAmt)%>
									</div>
									</TD>
                  
                  <TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%=totaldeclarationReceivedCases%>
									</div>
									</TD>
									<TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%=decimalFormat.format(totaldeclarationReceivedAmt)%>
									</div>
									</TD>
                  <TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%=totaldeclarationNotReceivedCases%>
									</div>
									</TD>
									<TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%=decimalFormat.format(totaldeclarationNotReceivedAmt)%>
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
							<A href="javascript:submitForm('mliWiseClaimPendigCasesReport.do?method=mliWiseClaimPendigCasesReport')">
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

