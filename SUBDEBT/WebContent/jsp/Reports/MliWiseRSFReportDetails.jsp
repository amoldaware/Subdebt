<!-- modified by sudeep.dhiman@pathinfotech.com-->

<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@page import ="java.text.DecimalFormat"%>
<%DecimalFormat decimalFormat = new DecimalFormat("##########0.00#");%>
<% session.setAttribute("CurrentPage","mliWiseRSFReportDetails.do?method=mliWiseRSFReportDetails");%> 
<%@page import = "org.apache.struts.action.DynaActionForm"%>
<% DynaActionForm dynaForm = (DynaActionForm)session.getAttribute("rsForm");
   String value = (String)dynaForm.get("checkValue");%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="mliWiseRSFReportDetails.do?method=mliWiseRSFReportDetails" method="POST" enctype="multipart/form-data">
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
												<TD width="17%" class="Heading"><bean:message key="mliWiseHeader" /></TD>
                        <td class="Heading" width="60%"> Guantee Approved RSF Applications  <bean:message key="from"/><bean:write  name="rsForm" property="dateOfTheDocument12"/>&nbsp;<bean:message key="to"/> <bean:write  name="rsForm" property="dateOfTheDocument13"/>
                        </td>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="4" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>
                     </table>
                     <TABLE width="100%" border="0" cellpadding="1" cellspacing="1">
              <TR>
          		<td align="left" width="6%" valign="top"  class="ColumnBackground"><bean:message key="sNo"/></td>
              <TD align="left" width="49%" valign="top" class="ColumnBackground"><bean:message key="memberIdForApplication"/>
									</TD>
               <TD align="left" width="49%" valign="top" class="ColumnBackground"><bean:message key="bankName"/>
									</TD>
                  <TD align="left" width="49%" valign="top" class="ColumnBackground"><bean:message key="zoneName"/>
									</TD>
                    <TD align="left" width="49%" valign="top" class="ColumnBackground"><bean:message key="CBbranchName"/>
									</TD>
                  <TD align="left" width="49%" valign="top" class="ColumnBackground"><bean:message key="appRefNo"/>
									</TD>
                  <TD align="left" width="49%" valign="top" class="ColumnBackground"><bean:message key="cgpan"/>
									</TD>
                 <TD align="left" width="49%" valign="top" class="ColumnBackground"><bean:message key="unitName"/>
									</TD>
                  <TD align="left" width="49%" valign="top" class="ColumnBackground"><bean:message key="activitytype"/>
									</TD>
                <td align="left" valign="top" class="ColumnBackground">Approved Amount</td>
          	       <%
                    int count      = 0;
										int totalCount  = 0;
										double sum      = 0.0;
										double totalSum = 0.0;
									%>
           </TR>
           	 <tr>
									<logic:iterate id="object" name="rsForm" property="mliWiseRSFReport" indexId="id">
									<% com.cgtsi.reports.GeneralReport dReport =  (com.cgtsi.reports.GeneralReport)object;%>

									<TR align="left" valign="top">
                  <td align="left" valign="top" class="ColumnBackground1"><%=Integer.parseInt(id+"")+1%></td>
                 <TD align="left" valign="top" class="ColumnBackground1">
									<% String memberId = dReport.getMemberId(); %>
                  <%= memberId %>
									</td>
                  <TD align="left" valign="top" class="ColumnBackground1">
									<% String bank = dReport.getBankName(); %>
                  <%= bank %>
									</td>
                  <TD align="left" valign="top" class="ColumnBackground1">
									<% String zone = dReport.getZoneName(); %>
                  <%= zone %>
									</td>
                  <TD align="left" valign="top" class="ColumnBackground1">
									<% String branch = dReport.getName(); %>
                  <%= branch %>
									</td>
                  <TD align="left" valign="top" class="ColumnBackground1">
									<% String appRef = dReport.getAppRefNo(); %>
                  <%= appRef %>
									</td>
                  <TD align="left" valign="top" class="ColumnBackground1">
									<% String cgpan = dReport.getCgpan(); %>
                  <%= cgpan %>
									</td>
                  <TD align="left" valign="top" class="ColumnBackground1">
									<% String name = dReport.getSsiName(); %>
                  <%= name %>
									</td>
                  <TD align="left" valign="top" class="ColumnBackground1">
									<% String type = dReport.getType(); %>
                  <%= type %>
									</td>
                  <TD align="left" class="ColumnBackground1"> 
									<div align="right">
									<%double amount=dReport.getAmount();
									sum = amount/100000;%>
									<% totalSum = totalSum+Double.parseDouble(decimalFormat.format(sum));%>
									<%=decimalFormat.format(sum)%>
									</div>
									</TD>
									</TR> 
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
							<A href="javascript:submitForm('mliWiseReportDetailsForRsf.do?method=mliWiseReportDetailsForRsf')">
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

