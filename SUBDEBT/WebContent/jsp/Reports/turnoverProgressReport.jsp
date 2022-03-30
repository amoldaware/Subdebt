<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","turnoverprogressReport.do?method=turnoverprogressReport");%>
<%@page import ="java.text.DecimalFormat"%>
<%DecimalFormat decimalFormat = new DecimalFormat("#################0.00");%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="turnoverprogressReport.do?method=turnoverprogressReport" method="POST" enctype="multipart/form-data">
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
                          <td colspan="6" class="Heading1" align="center"><u><!-- <bean:message key="reportHeader"/> --> Credit Guarantee Fund Trust for Micro And Small Enterprises </u></td>
                      </tr>
                      <tr> <td colspan="6">&nbsp;</td></tr>
                      <TR>
												<TD width="16%" class="Heading">Turnover and Export Report </TD>
												<td class="Heading" width="53%">&nbsp;<bean:message key="from"/> <bean:write  name="rsForm" property="dateOfTheDocument"/>&nbsp;<bean:message key="to"/> <bean:write  name="rsForm" property="dateOfTheDocument1"/></td>
                        <TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>
                    </TABLE>
									</TD>

	
				<tr> 
				<td colspan="12">
            <table width="100%" border="0" cellspacing="1" cellpadding="0">
             <tr> 
                  <td width="3%"  align="center" valign="middle" class="ColumnBackground "><div align="center">Sl.<br>No.</div></td>
                  <td width="15%" class="ColumnBackground "><div align="center">State Name </div></td>
                  <td width="10%" class="ColumnBackground "><div align="center">No.of Cases </div></td>
                  <td class="ColumnBackground"  width="10%"> <div align="center">Employment Generated </div></td>
                   <td class="ColumnBackground"  width="10%"> <div align="center">Projected Turnover (In Rs.) </div></td>
                   <td class="ColumnBackground"  width="10%"> <div align="center">Exports (In Rs.) </div></td>
          
            </tr> 
            
       </td>
        </tr>                       
        <tr>
        <td>
				<%
				int serial = 0;
        int total = 0;
        int total1 = 0;
        double total2 = 0.0;
        double total3 = 0.0;
        double export = 0.0;
        double turnover = 0.0;
				%>
        </td>
       </tr>
				<logic:iterate id="object" name="rsForm" property="turnoverprogressReport" indexId="index">
				<%
				com.cgtsi.reports.GeneralReport pReport = (com.cgtsi.reports.GeneralReport)object;
				%>
        <tr class="tableData">
				  <td><div align="center">&nbsp;<%= Integer.parseInt(index+"")+1%></div></td>
				  <td><div align="left">&nbsp;<%= pReport.getType()%></div></td>
				  <td><div align="right">&nbsp;<%= pReport.getProposals()%></div></td>
          <td><div align="right">&nbsp;<%= pReport.getEmployees()%></div></td>
           <td><div align="right">&nbsp;<% turnover= Double.parseDouble(decimalFormat.format(pReport.getTurnover()));%>
           <%= decimalFormat.format(turnover) %>
           </div></td>
         <td><div align="right">&nbsp;<% export= Double.parseDouble(decimalFormat.format(pReport.getExport()));
           %><%=decimalFormat.format(export) %></div></td>
         
          <% total = total+pReport.getProposals(); %>
          <% total1 = total1+pReport.getEmployees(); %>
          <% total2 = total2+Double.parseDouble(decimalFormat.format(pReport.getTurnover())); %>
          <% total3 = total3+Double.parseDouble(decimalFormat.format(pReport.getExport())); %>
          
				  <td><div align="right">
          </div>
    	</logic:iterate>
         				
				</TABLE>
				</TD>
        </TR>
        
                
          <td width = "31%" align = "right" colspan = "5" class="ColumnBackground ">&nbsp;TOTAL
          <td width = "18%" align = "center" class="ColumnBackground ">
              <div align="right"><%= total%></div></td>
				  <td width = "17%" align = "right" class="ColumnBackground ">
              <div align="right"><%= total1%></div></td>
          <td width = "22%" align = "right" class="ColumnBackground ">
              <div align="center"><%= decimalFormat.format(total2)%></div></td>
          <td width = "19%" align = "right" class="ColumnBackground ">
              <div align="right"><%= decimalFormat.format(total3)%></div></td>
        
               
				</TABLE>
         <tr class="tableData">
          


<!--         
         <div width = "3%" align="right"><%= decimalFormat.format(total)%>&nbsp;
          <%= decimalFormat.format(total1)%>&nbsp;<%= decimalFormat.format(total2)%>&nbsp;
           <%= decimalFormat.format(total3)%>&nbsp; </div>
            </td> 
-->
            </TR>
						</TD>
					</TR>
					<TR >


           
          <!--
						<TD align="justify" class="ColumnBackground "> <b>&nbsp;TOTAL</b> 
					    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <%= decimalFormat.format(total)%> 
              <%= decimalFormat.format(total1)%>&nbsp; 
              <%= decimalFormat.format(total2)%>&nbsp; 
             <%= decimalFormat.format(total3)%>&nbsp; 
						</TD>
            -->
            
					</TR>
					<TR >
						<TD align="center" valign="baseline">
							<DIV align="center">
								<A href="javascript:submitForm('turnoverReport.do?method=turnoverReport')">
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


