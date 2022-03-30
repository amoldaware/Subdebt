<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","turnoverandexportsReport.do?method=turnoverandexportsReport");%>
<%@page import ="java.text.DecimalFormat"%>
<%DecimalFormat decimalFormat = new DecimalFormat("##########.#####");%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="turnoverandexportsReport.do?method=turnoverandexportsReport" method="POST" enctype="multipart/form-data">
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
                          <td colspan="6" class="Heading1" align="center"><u><bean:message key="reportHeader"/></u></td>
                      </tr>
                      <tr> <td colspan="6">&nbsp;</td></tr>
                      <TR>
												<TD width="16%" class="Heading">Minority Report </TD>
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
                  <td width="20%" class="ColumnBackground "><div align="center">Promoter's Name </div></td>
                  <td class="ColumnBackground"  width="10%"> <div align="center">Approved Amount </div></td>
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
				<logic:iterate id="object" name="rsForm" property="turnoverandexportprogressreport" indexId="index">
				<%
				com.cgtsi.reports.GeneralReport pReport = (com.cgtsi.reports.GeneralReport)object;
				%>
        <tr class="tableData">
				  <td><div align="right">&nbsp;<%= Integer.parseInt(index+"")+1%></div></td>
				  <td><div align="left">&nbsp;<%= pReport.getType()%></div></td>
				  <td><div align="left">&nbsp;<%= pReport.getName()%></div></td>
          <td><div align="right">&nbsp;<%= pReport.getAmount()%></div></td>
          <% total = total+Double.parseDouble(decimalFormat.format(pReport.getAmount())); %>
				  <td><div align="right">
          </div>
    	</logic:iterate>
         				
				</TABLE>
				
				</TD>
        </TR>
               
				</TABLE>
        <TR>
            <td align = "right" colspan = "3" class="ColumnBackground ">&nbsp;TOTAL<div align="right"><%= decimalFormat.format(total)%>&nbsp;</div></TD>
            </TR>
						</TD>
					</TR>
					<TR >
						<TD height="20">
							&nbsp;
						</TD>
					</TR>
					<TR >
						<TD align="center" valign="baseline">
							<DIV align="center">
								<A href="javascript:submitForm('turnoverandexport.do?method=turnoverandexport')">
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


