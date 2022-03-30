<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>

<%@page import ="java.text.SimpleDateFormat"%>
<%@page import ="java.text.DecimalFormat"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>

<% session.setAttribute("CurrentPage","showSearchHistory.do?method=showSearchHistory");%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="showSearchHistory.do?method=showSearchHistory" method="POST" enctype="multipart/form-data">
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
                          <td colspan="6" class="Heading1" align="center"><u>Emergency <br> Credit Line Guarantee <br> Scheme (ECLGS) </u></td>
                      </tr>
                      </table>
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0" align="center">
							<TR>
                  
                  
                  
                  
                   <td width="10%" valign="top" class="HeadingBg"><div align="left">&nbsp;&nbsp;&nbsp;<strong>UNIT NAME</strong><br></div></td>
                   
                   <td width="30%" valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;&nbsp;<strong>SSI CONSTITUTION</strong><br></div></td>
                  
                  <td width="10%" valign="top" class="HeadingBg"> <div align="left"> &nbsp;&nbsp;&nbsp;<strong>GUARANTEE AMOUNT</strong></div></td>
                
                   <td width="10%" valign="top" class="HeadingBg"> <div align="left"> &nbsp;&nbsp;&nbsp;<strong>LOAN STATUS (NPA/STANDARD)</strong></div></td>
                  
               
              		</TR>
                  <tr>
                  	<logic:iterate name="rsForm" id="object" property="searchHistory">

			    <% com.cgtsi.application.Application dReport =  (com.cgtsi.application.Application)object;%>

				<TR align="left" valign="top">
					
                  <TD  align="left" valign="top" class="ColumnBackground1">
                   &nbsp;&nbsp;
                   <%=dReport.getSsiUnitName()%>&nbsp;
                   </TD>
                    
                     <TD  align="left" valign="top" class="ColumnBackground1">
                   &nbsp;&nbsp;
                   <%=dReport.getSsiConstitution()%>&nbsp;
                   </TD>
                    
                   <TD  align="left" valign="top" class="ColumnBackground1">
                   &nbsp;&nbsp;<%=dReport.getGurAmt()%>&nbsp;
                   </TD>
                   
                   <TD  align="left" valign="top" class="ColumnBackground1">
                    &nbsp;&nbsp;<%= dReport.getNpaDate()%>&nbsp;
                   </TD>
               </logic:iterate>
									</TABLE>
								</TD>

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
									<A href="javascript:submitForm('searchHistory.do?method=searchHistory')">
									<IMG src="images/OK.gif" alt="OK" width="49" height="37" border="0"></A>

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

