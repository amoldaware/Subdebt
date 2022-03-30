<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>

<%@ page import="java.util.ArrayList"%>
<%@ page import="com.cgtsi.guaranteemaintenance.BorrowerInfo"%>
<%@ page import="com.cgtsi.guaranteemaintenance.CgpanInfo"%>

<%@ include file="/jsp/SetMenuInfo.jsp" %>

<% session.setAttribute("CurrentPage","showFilterForPIReport.do?method=showFilterForPIReport");%>
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="showPeriodicInfoReport.do?method=showPeriodicInfoReport" method="POST" enctype="multipart/form-data">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/ReportsHeading.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
							<table width="100%" border="0" cellspacing="1" cellpadding="0">
							<tr> 
							  <td colspan="6">
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR>
										<TD width="31%" class="Heading"><bean:message key="periodicInfoReport" /></TD>
										<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
									</TR>
									<TR>
										<TD colspan="6" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
									</TR>
								</TABLE>
							  </td>
							</tr>
								<tr>
					            <td colspan="6">
									<table width="100%" border="0" cellspacing="1" cellpadding="0">
										<tr>
										  <td align="center" valign="middle" class="HeadingBg"
											 align="center"><bean:message key = "srNo"/>
										  </td>
										  <td class="HeadingBg" align="center"><bean:message key="cgbid"/>
										  </td>
										  <td class="HeadingBg" align="center"><bean:message 					key="unitName"/>	
										  </td>  
										  <td class="HeadingBg" align="center"><bean:message 					key="listOfCgpans"/>
										  </td>
										</TR>

									   <%  int i=0,j=0; 
										   String borrowerId = null;
										   String borrowerName = null;
										   String cgpan = null;

									   %>
										
										<logic:iterate id="object" name="rsPeriodicInfoForm" property="borrowerDetailsForPIReport">
										<%
											BorrowerInfo bInfo = (BorrowerInfo)object;
											borrowerId = bInfo.getBorrowerId();
											borrowerName = bInfo.getBorrowerName();
										%>

										<TR align = "center">
										  <TD class="tableData" width="25" align="center"><center><%=++i%>
										  </TD>

										  <TD class="tableData" width="25" align="center"><center><a	  href="javascript:submitForm('showPeriodicInfoReport.do?method=showPeriodicInfoReport&bidForPIReport=<%=borrowerId%>')"><%=borrowerId%></a>
 
										  </TD>

										  <TD class="tableData" width="25" align="center"><center><%=borrowerName%>
										  </TD>
										
									<% 
										ArrayList cgpanInfos = (ArrayList)bInfo.getCgpanInfos(); 

										int cgpanInfosSize = cgpanInfos.size();

										CgpanInfo cgpanInfo = null;	
										
										for(j=0; j< cgpanInfosSize; ++j) 
										{							
											cgpanInfo = (CgpanInfo)cgpanInfos.get(j);
						
											cgpan = cgpanInfo.getCgpan() ;

											if(j==0)
											{
									%>	
												<TD class="tableData" width="25" 							align="center"><center><%=cgpan%>
												</TD>
									<%
										     }
											 else
											 {
									%>
											<TR>
												<TD class="tableData"></TD>
												<TD class="tableData"></TD>
												<TD class="tableData"></TD>
		
												<TD class="tableData" width="25" 							align="center"><center><%=cgpan%>
												</TD>
									<%
											 }
										}
									%>	
										</TR>
									 </logic:iterate>

								</TABLE>
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
									<A href="javascript:window.history.back()">
								<IMG src="images/Back.gif" alt="Back" width="49" height="37" border="0"></A>
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
