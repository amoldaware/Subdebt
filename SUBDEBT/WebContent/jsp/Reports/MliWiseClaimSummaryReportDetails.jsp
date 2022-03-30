<!-- added by sukumar@path for display MLI wise Claim Summary Report Details -->

<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@page import ="java.text.DecimalFormat"%>

<style type="text/css" media="print,screen" >  
2 th {  
3     font-family:Arial;  
4     color:black;  
5     background-color:lightgrey;  
6 }  
7 thead {  
8     display:table-header-group;  
9 }  
10 tbody {  
11     display:table-row-group;  
12 }  
13 </style>  


<%DecimalFormat decimalFormat = new DecimalFormat("#########0.00#");%>
<% session.setAttribute("CurrentPage","mliWiseClaimSummaryReportDetails.do?method=mliWiseClaimSummaryReportDetails");%> 
<%@page import = "org.apache.struts.action.DynaActionForm"%>
<% DynaActionForm dynaForm = (DynaActionForm)session.getAttribute("rsForm");
  // String value = (String)dynaForm.get("checkValue");
%>

<TABLE width="850" border="0" cellpadding="0" cellspacing="0">
	<html:errors/>
	<html:form action="mliWiseClaimSummaryReportDetails.do?method=mliWiseClaimSummaryReportDetails" method="POST" enctype="multipart/form-data">
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
               <thead>
               <tr>
                  <td colspan="19" class="Heading1" align="center"><u><!--<bean:message key="reportHeader"/>-->Credit Guarantee Fund Trust for Micro and Small Enterprises</u>
                  </td>
                </tr>
                <tr>
                  <td colspan="19">&nbsp;</td>
                </tr>
               
								<TR>
									<TH colspan="19"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="70%" class="Heading">Claim Received, Settled, Forwarded, TC, TR, Rejected,New and Claim WithdrawnCases Report&nbsp; </TD>
                        <td class="Heading" width="50%">&nbsp; as on &nbsp;<bean:write  name="rsForm" property="dateOfTheDocument13"/>
                        </td>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>
											</TABLE>
									</TH>
								</TR>
              
								<TR align="left" valign="top">
                  <th align="left" valign="top" class="ColumnBackground"><bean:message key="sNo"/></th>
                  <TH colspan="2" align="left"  valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="mli"/>
									</TH>                  
                  <TH align="left" class="ColumnBackground">&nbsp;<bean:message key="receivedCases"/></TH>
                  <TH align="left" class="ColumnBackground">&nbsp;<bean:message key="amt"/></TH>                      
                  <TH align="left" class="ColumnBackground">&nbsp;<bean:message key="settledCases"/></TH>
                  <TH align="left" class="ColumnBackground">&nbsp;<bean:message key="amt"/></TH>
                  <TH align="left" class="ColumnBackground">&nbsp;<bean:message key="forwardCases"/></TH>
                  <TH align="left" class="ColumnBackground">&nbsp;<bean:message key="amt"/></TH>
                  <TH align="left" class="ColumnBackground">&nbsp;<bean:message key="tcCases"/></TH>
                  <TH align="left" class="ColumnBackground">&nbsp;<bean:message key="amt"/></TH>
                  <TH align="left" class="ColumnBackground">&nbsp;<bean:message key="trCases"/></TH>
                  <TH align="left" class="ColumnBackground">&nbsp;<bean:message key="amt"/></TH>
                  <TH align="left" class="ColumnBackground">&nbsp;<bean:message key="rejectedCases"/></TH>
                  <TH align="left" class="ColumnBackground">&nbsp;<bean:message key="amt"/></TH>
                  <TH align="left" class="ColumnBackground">&nbsp;<bean:message key="pendingCases"/></TH>
                  <TH align="left" class="ColumnBackground">&nbsp;<bean:message key="amt"/></TH>              
                  <TH align="left" class="ColumnBackground">&nbsp;<bean:message key="withdrawnCases"/></TH>
                  <TH align="left" class="ColumnBackground">&nbsp;<bean:message key="amt"/></TH>
  						    
									<%
                    int receivedCases = 0;
                    int settledCases  = 0;
                    int forwardCases  = 0;
                    int tcCases  = 0;
                    int trCases  = 0;
                    int rejectedCases  = 0;
                    int newCases  = 0;
                    int wdCases = 0;
                    
                    double receivedAmt = 0.0;
                    double settledAmt  = 0.0;
                    double forwardAmt  = 0.0;
                    double tcAmt  = 0.0;
                    double trAmt  = 0.0;
                    double rejectedAmt  = 0.0;
                    double newAmt  = 0.0;
                    double wdAmt = 0.0;
                    
                    int totalreceivedCases = 0;
                    int totalsettledCases  = 0;
                    int totalforwardCases  = 0;
                    int totaltcCases  = 0;
                    int totaltrCases  = 0;
                    int totalrejectedCases  = 0;
                    int totalnewCases  = 0;
                    int totalwdCases = 0;
                    
                    double totalreceivedAmt = 0.0;
                    double totalsettledAmt  = 0.0;
                    double totalforwardAmt  = 0.0;
                    double totaltcAmt  = 0.0;
                    double totaltrAmt  = 0.0;
                    double totalrejectedAmt  = 0.0;
                    double totalnewAmt  = 0.0;
                    double totalwdAmt = 0.0;
									%>
									</TR>
									</thead>
                  
									<tr>
									<logic:iterate id="object" name="rsForm" property="mliWiseClaimSummaryReport" indexId="id">
									<% com.cgtsi.reports.GeneralReport dReport =  (com.cgtsi.reports.GeneralReport)object;%>

									<TR align="left" valign="top">
                  <td align="left" valign="top" class="ColumnBackground1"><%=Integer.parseInt(id+"")+1%></td>
                  <TD align="left"  colspan="2" valign="top" class="ColumnBackground1"><%=dReport.getBankName()%>
                  </TD>
                 
									<TD align="left" valign="top" class="ColumnBackground1">
                  <div align="right"><%
                    receivedCases =  dReport.getClaimReceivedCases();
                    totalreceivedCases = totalreceivedCases + receivedCases;  
                  %><%=receivedCases%></div>
                  </TD>
                  <TD align="left" valign="top" class="ColumnBackground1">
                  <div align="right"><% 
                    receivedAmt = dReport.getClaimReceivedAmt();
                    totalreceivedAmt = totalreceivedAmt + receivedAmt;
                   %><%=dReport.getClaimReceivedAmt()%></div>
                  </TD>
                  <TD align="left" valign="top" class="ColumnBackground1">                  
                  <div align="right"><%
                    settledCases =  dReport.getClaimSettledCases();
                    totalsettledCases = totalsettledCases + settledCases;  
                  %><%=dReport.getClaimSettledCases()%></div>
                  </TD>
                  <TD align="left" valign="top" class="ColumnBackground1">
                  <div align="right"><% 
                    settledAmt = dReport.getClaimSettledAmt();
                    totalsettledAmt = totalsettledAmt + settledAmt;
                   %><%=dReport.getClaimSettledAmt()%></div>
                  </TD>
                  <TD align="left" valign="top" class="ColumnBackground1">
                  <div align="right"><%
                    forwardCases =  dReport.getClaimForwardCases();
                    totalforwardCases = totalforwardCases + forwardCases;  
                  %><%=dReport.getClaimForwardCases()%></div>
                  </TD>
                  <TD align="left" valign="top" class="ColumnBackground1">
                  <div align="right"><% 
                    forwardAmt = dReport.getClaimForwardAmt();
                    totalforwardAmt = totalforwardAmt + forwardAmt;
                   %><%=dReport.getClaimForwardAmt()%></div>
                  </TD>
                  <TD align="left" valign="top" class="ColumnBackground1">
                  <div align="right"><%
                    tcCases =  dReport.getTcCases();
                    totaltcCases = totaltcCases + tcCases;  
                  %><%=dReport.getTcCases()%></div>
                  </TD>
                  <TD align="left" valign="top" class="ColumnBackground1">
                  <div align="right"><% 
                    tcAmt = dReport.getTcAmt();
                    totaltcAmt = totaltcAmt + tcAmt;
                   %><%=dReport.getTcAmt()%></div>
                  </TD>
                  <TD align="left" valign="top" class="ColumnBackground1">
                  <div align="right"><%
                    trCases =  dReport.getTrCases();
                    totaltrCases = totaltrCases + trCases;  
                  %><%=dReport.getTrCases()%></div>
                  </TD>
                  <TD align="left" valign="top" class="ColumnBackground1">
                  <div align="right"><% 
                    trAmt = dReport.getTrAmt();
                    totaltrAmt = totaltrAmt + trAmt;
                   %><%=dReport.getTrAmt()%></div>
                  </TD>
                   <TD align="left" valign="top" class="ColumnBackground1">
                  <div align="right"><%
                    rejectedCases =  dReport.getRejectedCases();
                    totalrejectedCases = totalrejectedCases + rejectedCases;  
                  %><%=dReport.getRejectedCases()%></div>
                  </TD>
                  <TD align="left" valign="top" class="ColumnBackground1">
                  <% 
                    rejectedAmt = dReport.getRejectedAmt();
                    totalrejectedAmt = totalrejectedAmt + rejectedAmt;
                   %><%=dReport.getRejectedAmt()%></div>
                  </TD>
                  <TD align="left" valign="top" class="ColumnBackground1">
                  <div align="right"><%
                    newCases =  dReport.getPendingCases();
                    totalnewCases = totalnewCases + newCases;  
                  %><%=dReport.getPendingCases()%></div>
                  </TD>
                  <TD align="left" valign="top" class="ColumnBackground1">
                  <div align="right"><% 
                    newAmt = dReport.getPendingAmt();
                    totalnewAmt = totalnewAmt + newAmt;
                   %><%=dReport.getPendingAmt()%></div></TD>   
                   
                   
                   <TD align="left" valign="top" class="ColumnBackground1">
                  <div align="right"><%
                    wdCases =  dReport.getClaimWithDrawnCases();
                    totalwdCases = totalwdCases + wdCases;  
                  %><%=dReport.getClaimWithDrawnCases()%></div>
                  </TD>
                  <TD align="left" valign="top" class="ColumnBackground1">
                  <div align="right"><% 
                    wdAmt = dReport.getClaimWithdrawnAmt();
                    totalwdAmt = totalwdAmt + wdAmt;
                   %><%=dReport.getClaimWithdrawnAmt()%></div></TD>  
                   
									</TR> 
								</logic:iterate>

								<tr>
									<TD align="left" valign="top" colspan="3" class="ColumnBackground">
									Total
									</TD>
									<TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%=totalreceivedCases%>
									</div>
									</TD>
									<TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%=decimalFormat.format(totalreceivedAmt)%>
									</div>
									</TD>
                  
                  <TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%=totalsettledCases%>
									</div>
									</TD>
									<TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%=decimalFormat.format(totalsettledAmt)%>
									</div>
									</TD>
                  <TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%=totalforwardCases%>
									</div>
									</TD>
									<TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%=decimalFormat.format(totalforwardAmt)%>
									</div>
									</TD>
                  <TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%=totaltcCases%>
									</div>
									</TD>
									<TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%=decimalFormat.format(totaltcAmt)%>
									</div>
									</TD>
                  <TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%=totaltrCases%>
									</div>
									</TD>
									<TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%=decimalFormat.format(totaltrAmt)%>
									</div>
									</TD>
                  <TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%=totalrejectedCases%>
									</div>
									</TD>
									<TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%=decimalFormat.format(totalrejectedAmt)%>
									</div>
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
									<%=totalwdCases%>
									</div>
									</TD>
									<TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%=decimalFormat.format(totalwdAmt)%>
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
							<A href="javascript:submitForm('mliWiseClaimSummaryReport.do?method=mliWiseClaimSummaryReport')">
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

