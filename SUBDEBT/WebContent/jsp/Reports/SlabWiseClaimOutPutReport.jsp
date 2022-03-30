<!-- modified by sudeep.dhiman@pathinfotech.com-->

<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@page import ="java.text.DecimalFormat"%>
<%DecimalFormat decimalFormat = new DecimalFormat("##########.##");%>
<% session.setAttribute("CurrentPage","mliWiseReportDetails.do?method=mliWiseReportDetails");%> 
<%@page import = "org.apache.struts.action.DynaActionForm"%>


<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="mliWiseReportDetails.do?method=mliWiseReportDetails" method="POST" enctype="multipart/form-data">
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
                  <td colspan="6" class="Heading1" align="center"> <!-- <bean:message key="reportHeader"/> --> <u>Credit Guarantee Fund Trust for Micro And Small Enterprises </u>
                  </td>
                </tr>
                <tr>
                  <td colspan="6">&nbsp;</td>
                </tr>
								<TR>
									<TD colspan="4"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="17%" class="Heading">Slab wise Claim Settled Cases Report&nbsp;</TD>
                        <td class="Heading" width="50%"><bean:message key="from"/><bean:write  name="rsForm" property="dateOfTheDocument42"/>&nbsp;<bean:message key="to"/><bean:write  name="rsForm" property="dateOfTheDocument43"/>
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
                  <td align="left" valign="top" class="ColumnBackground"><bean:message key="range"/></td>
                  <TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="noOfProposal"/>
									</TD>
									<TD align="left" class="ColumnBackground"> 
									<bean:message key="loan"/>
									</TD>
  							</TR>
									
                <logic:iterate id="slab" name="slabList" indexId="id">
								<TR>
                   <td valign="top" class="ColumnBackground1" >
                      &nbsp;<%=Integer.parseInt(id+"")+1%></td>
                   <td valign="top" class="ColumnBackground1">
                     &nbsp;<%=((String[])slab)[0]%></td>
                   <td valign="top" class="ColumnBackground1">
                     <div align="right"><%=((String[])slab)[1]%></div></td>
                   <td valign="top" class="ColumnBackground1">
                    <div align="right"><%=((String[])slab)[2]%></div></td>
                </TR> 
								
                </logic:iterate>
								<tr>
									<TD align="left" valign="top" colspan="2" class="ColumnBackground"> Total	</TD>
									<TD align="left" class="ColumnBackground"> 
									   <div align="right"><bean:write name="totalProposals"/></div>
									</TD>
									<TD align="left" class="ColumnBackground"> 
									   <div align="right"><bean:write name="totalLoan"/></div>
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
					<tr><td colspan="3" align="left" width="700"><font size="2" color="red">Report Generated On : 
					<% java.util.Date loggedInTime=new java.util.Date();
			          java.text.SimpleDateFormat dateFormat1=new java.text.SimpleDateFormat("dd MMMMM yyyy ':' HH.mm");
			          String date1=dateFormat1.format(loggedInTime);
					  out.println(date1);
					  %> hrs.</font></td></tr>
					<TR>
						<TD align="center" valign="baseline">
							<DIV align="center">
							<A href="javascript:submitForm('slabWiseClaimReport.do?method=slabWiseClaimReport')">
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

