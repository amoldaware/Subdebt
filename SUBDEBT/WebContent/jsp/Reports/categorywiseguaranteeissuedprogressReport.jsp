<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","categorywiseguaranteeissuedprogressReport.do?method=categorywiseguaranteeissuedprogressReport");%>
<%@page import ="java.text.DecimalFormat"%>
<%DecimalFormat decimalFormat = new DecimalFormat("##########.#####");%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="categorywiseguaranteeissuedprogressReport.do?method=categorywiseguaranteeissuedprogressReport" method="POST" enctype="multipart/form-data">
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
												<TD width="16%" class="Heading">Category Wise Guarantee Approved Report </TD>
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
                  <td width="10%" class="ColumnBackground "><div align="center">Category </div></td>
                  <td width="5%" class="ColumnBackground "><div align="center">No.Of Cases </div></td>
                  <td class="ColumnBackground"  width="5%"> <div align="center">Approved Amount </div></td>
            </tr> 
            
       </td>
        </tr>                       
        <tr>
        <td>
				<%
				int serial = 0;
        double total = 0;
				%>
        </td>
       </tr>
				<logic:iterate id="object" name="rsForm" property="categorywiseguaranteeissuedprogressReport" indexId="index">
				<%
				com.cgtsi.reports.GeneralReport pReport = (com.cgtsi.reports.GeneralReport)object;
				%>
        <tr class="tableData">
				  <td><div align="right">&nbsp;<%= Integer.parseInt(index+"")+1%></div></td>
				  <td><div align="left">&nbsp;<%= pReport.getType()%></div></td>
           <td><div align="left">&nbsp;<%= pReport.getProposal()%></div></td> 
           <% double amount = 0;
              serial = serial+pReport.getProposal();
              amount =Double.parseDouble(decimalFormat.format(pReport.getAmount()));
          %>
          <td><div align="right">&nbsp;<%=decimalFormat.format(amount) %></div></td>
          <% total = total+Double.parseDouble(decimalFormat.format(pReport.getAmount())); %>
				  <td><div align="right">
          </div>
    	</logic:iterate>
         				
				</TABLE>
				
	
        	</TD>
					</TR>
        <tr class="tableData">
         <td width = "56%"><b><div align="center" colspan = "50">&nbsp;TOTAL</div></b></td>
				  <td colspan="6" align="center"><b><div align="left">&nbsp;<%= serial%></div></b></td>
           <td width = "22.70%" ><b><div align="right" >&nbsp;<%= decimalFormat.format(total)%></div></b></td> 
        
          </TR>
					<TR >
        </TABLE>
						<TD height="20">
							&nbsp;
						</TD>
					</TR>
					<TR >
						<TD align="center" valign="baseline">
							<DIV align="center">
								<A href="javascript:submitForm('categorywiseguaranteeissuedReport.do?method=categorywiseguaranteeissuedReport')">
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


