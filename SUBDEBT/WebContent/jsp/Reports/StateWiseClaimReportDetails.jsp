<!-- added by sukumar@pathinfotech.com-->

<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@page import ="java.text.DecimalFormat"%>
<%DecimalFormat decimalFormat = new DecimalFormat("#########0.00#");%>
<% session.setAttribute("CurrentPage","stateWiseClaimReportDetails.do?method=stateWiseClaimReportDetails");%> 
<%@page import = "org.apache.struts.action.DynaActionForm"%>
<% DynaActionForm dynaForm = (DynaActionForm)session.getAttribute("rsForm");
  // String value = (String)dynaForm.get("checkValue");
%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="stateWiseClaimReportDetails.do?method=stateWiseClaimReportDetails" method="POST" enctype="multipart/form-data">
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
												<TD width="37%" class="Heading">State wise Claim Report Details </TD>
                        <td class="Heading" width="50%">&nbsp;<bean:message key="from"/><bean:write  name="rsForm" property="dateOfTheDocument12"/>&nbsp;<bean:message key="to"/> <bean:write  name="rsForm" property="dateOfTheDocument13"/>
                        </td>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>
											</TABLE>
									</TD>
								</TR>

								<TR align="left" valign="top">
                  <td align="left" valign="top" class="ColumnBackground"><bean:message key="sNo"/></td>
                  <TD align="left" valign="top" class="ColumnBackground">
										&nbsp;State Name
									</TD>
                  <TD align="left" class="ColumnBackground"><bean:message key="proposals"/></TD>
  						  	<TD align="left" class="ColumnBackground">Amount Settled (Rs. Lakh)</TD>
                  <TD align="left" class="ColumnBackground">Guarantee Issued&nbsp;<bean:message key="proposals"/></TD>
  						  	<TD align="left" class="ColumnBackground">Issued Amount (Rs. Lakh)</TD>
                  <TD align="left" class="ColumnBackground">Claim Settled (No.) as %age of guarantee issued cases</TD>
  						  	<TD align="left" class="ColumnBackground">Claim Settled (Amt) as % age of guarantee issued amount</TD>
                 
									<%
                    int count       = 0;
										int totalCount  = 0;
										double sum      = 0.0;
										double totalSum = 0.0;
                    int issuedcount = 0;
                    int totalissuedcount=0;
                    double issuedtotal = 0.0;
                    double totalissuedamt =0.0;
                    double percentofcases = 0.0;
                    double percentofamt = 0.0;
                    double totalpercentofcases = 0.0;
                    double totalperecentofamt = 0.0;
                    double issuedsum = 0.0;
									%>
									</TR>
									
									<tr>
									<logic:iterate id="object" name="rsForm" property="mliWiseReport" indexId="id">
									<% com.cgtsi.reports.GeneralReport dReport =  (com.cgtsi.reports.GeneralReport)object;%>

									<TR align="left" valign="top">
                  <td align="left" valign="top" class="ColumnBackground1"><%=Integer.parseInt(id+"")+1%></td>
                  <TD align="left" valign="top" class="ColumnBackground1"><%=dReport.getType()%>
                  </TD>
									<TD align="right" valign="top" class="ColumnBackground1">
                  <div align="right">
                  <%count=dReport.getProposals();%>
                  <%totalCount = count + totalCount;%>
									<%=dReport.getProposals()%></div></TD>
									<TD align="right" valign="top" class="ColumnBackground1">
									<div align="right">
									<%double amount=dReport.getAmount();
									sum = amount/100000;%>
									<% totalSum = totalSum+Double.parseDouble(decimalFormat.format(sum));%>
									<%=decimalFormat.format(sum)%>
									</div>
									</TD>
                  <TD align="right" valign="top" class="ColumnBackground1">
                  <div align="right">
                  <%issuedcount=dReport.getPrevProposals();%>
                  <%totalissuedcount = issuedcount + totalissuedcount;%>
									<%=dReport.getPrevProposals()%></div></TD>
                  <TD align="right" valign="top" class="ColumnBackground1">
									<div align="right">
									<%issuedsum=dReport.getPrevAmount();
									issuedtotal = issuedsum/100000;%>
									<% totalissuedamt = totalissuedamt+Double.parseDouble(decimalFormat.format(issuedtotal));%>
									<%=decimalFormat.format(issuedtotal)%>
									</div>
									</TD>
                  <TD align="right" valign="top" class="ColumnBackground1">
                  <div align="right">
                  <% 
                    if(issuedcount!=0) 
                     percentofcases = (count*100)/issuedcount;
                    if(totalissuedcount!=0)
                      totalpercentofcases = (totalCount*100)/totalissuedcount;
                    if(issuedtotal!=0) 
                       percentofamt = (sum*100)/issuedtotal;
                    if(totalissuedamt!=0) 
                      totalperecentofamt = (totalSum*100)/totalissuedamt; 
                      // System.out.println("percentofcases:"+percentofcases);
                     %>
                  <%=decimalFormat.format(percentofcases)%>
                  </div>
                  </TD>
                  <TD align="right" valign="top" class="ColumnBackground1">
                  <div align="right">
                  <%=decimalFormat.format(percentofamt)%>
                  </div>
                  </TD>
									</TR> 
								</logic:iterate>

								<tr>
									<TD align="left" valign="top" colspan="2" class="ColumnBackground">
									Total
									</TD>
									<TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%=totalCount%>
									</div>
									</TD>
									<TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%=decimalFormat.format(totalSum)%>
									</div>
									</TD>
                  <TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%=totalissuedcount%>
									</div>
									</TD>
									<TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%=decimalFormat.format(totalissuedamt)%>
									</div>
									</TD>
                  <TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%=decimalFormat.format(totalpercentofcases)%>
									</div>
									</TD>
                  <TD align="left" class="ColumnBackground"> 
									<div align="right">
									<%=decimalFormat.format(totalperecentofamt)%>
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
							<A href="javascript:submitForm('stateWiseClaimReport.do?method=stateWiseClaimReport')">
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

