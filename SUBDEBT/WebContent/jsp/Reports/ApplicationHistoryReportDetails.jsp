<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>

<%@page import ="java.text.SimpleDateFormat"%>
<%@page import ="java.text.DecimalFormat"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>
<%DecimalFormat decimalFormat = new DecimalFormat("##########.##");%>
<% session.setAttribute("CurrentPage","applicationHistoryReportDetails.do?method=applicationHistoryReportDetails");%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="applicationHistoryReportDetails.do?method=applicationHistoryReportDetails" method="POST" enctype="multipart/form-data">
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
                          <td colspan="6" class="Heading1" align="center"><u>Credit Guarantee Fund Trust for Micro and Small Enterprises </u></td>
                      </tr>
                      <tr> <td colspan="6">&nbsp;</td></tr>
                      	<tr> 
                  <td class="DanReport"> <div align="left">&nbsp; <b><bean:message key="memberIdForApplication" /></b><bean:write property="applicationReport.memberId" name="rsForm"/></div></td>
				          <td class="DanReport"> <div align="left">&nbsp; <b><bean:message key="cgpanForApplication" /></b><bean:write property="applicationReport.cgpan" name="rsForm"/></div></td>
                  </tr>
                   <tr> <td colspan="6">&nbsp;</td></tr>
                  	<tr> 
                  <td> <div align="left">&nbsp; <b><bean:message key="ssiName"/> : </b><bean:write property="applicationReport.ssiName" name="rsForm"/></div></td>
                    <td> <div align="left">&nbsp; <b><bean:message key="status"/> : </b><bean:write property="applicationReport.status" name="rsForm"/></div></td>
                  </tr>
                  <tr> <td colspan="6">&nbsp;</td></tr>
											<TR>
                      </table>
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="25%" class="Heading"><bean:message key="applicationHeader" /> </TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
										<TD  width="5%" colspan="12" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
												</TR>
                  <TR align="left" valign="top">
							   	<TD  align="left" class="ColumnBackground"> 
									<bean:message key="dan"/>
									</TD>
                  <TD  align="left" class="ColumnBackground"> 
                  &nbsp;CGPAN
									
									</TD>
                 
                  <TD  align="left" class="ColumnBackground"> 
									<%-- <bean:message key="approvedAmount"/> --%>Approved <br>&nbsp;Amt.
									</TD>
									<TD  align="left" class="ColumnBackground"> 
								  &nbsp;Outstanding Amt
            			</TD>
                    <TD  align="left" class="ColumnBackground"> 
								  &nbsp;DAN Amt
            			</TD>
                  <TD width = "25%" align="left" class="ColumnBackground"> 
							&nbsp;<bean:message key="payId"/>

									</TD> 
                   <TD width = "10%" align="left" class="ColumnBackground"> 
								&nbsp;Realised Date
									</TD>
                  <%-- <TD width = "10%" align="left" class="ColumnBackground"> 
								&nbsp;Appr Date
									</TD> --%>
                  <TD width = "10%" align="left" class="ColumnBackground"> 
								&nbsp;Expiry Date
									</TD>
                 <%--  <TD align="left" class="ColumnBackground"> 
									&nbsp;<bean:message key="appropriation"/>
									</TD> --%>
                   <TD align="left" class="ColumnBackground"> 
									&nbsp;DD No.
									</TD>
                    <TD align="left" class="ColumnBackground"> 
									&nbsp;<bean:message key="Closure"/>
									</TD>
              		</TR>
                  <tr>
                  	<logic:iterate id="object" name="rsForm" property="cgpanHistoryReport" indexId="id">

									<% com.cgtsi.reports.ApplicationReport dReport =  (com.cgtsi.reports.ApplicationReport)object;%>

									<TR align="left" valign="top">
							     <TD  align="left" valign="top" class="ColumnBackground1">
                   <%=dReport.getDan() %>&nbsp;
                   </TD>
                    <TD  align="left" valign="top" class="ColumnBackground1">
                   <%=dReport.getCgpan()%>
                   </TD>  
                    <TD  align="left" valign="top" class="ColumnBackground1">
                   &nbsp;<%=dReport.getAppApprovedAmount()%>
                   </TD>
                   <TD  align="left" valign="top" class="ColumnBackground1">
                   &nbsp;<%=dReport.getDbrAmount()%>
                   </TD>
                    <TD  align="left" valign="top" class="ColumnBackground1">
                   &nbsp;<%=dReport.getAppGuaranteeFee()%>
                   </TD>
                     <TD width="25%" align="left" valign="top" class="ColumnBackground1">
                    <% String payId = dReport.getPayId();
                    if(payId==null){
                    payId="Not Appropriated";
                    }
                    %>
                   &nbsp;<%=payId%>
                   </TD> 
                     <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                   <%   java.util.Date utilDate3=dReport.getAppGuarStartDateTime();
													String formatedDate3 = null;
													if(utilDate3 != null)
													{
														 formatedDate3=dateFormat.format(utilDate3);
													}
													else
													{
														 formatedDate3 = "";
													}
											%>
										&nbsp;&nbsp;<%=formatedDate3%>
                   </TD>
                  
                     <%-- <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                 <%   java.util.Date utilDate5=dReport.getStartDate();
													String formatedDate5 = null;
													if(utilDate5 != null)
													{
														 formatedDate5=dateFormat.format(utilDate5);
													}
													else
													{
														 formatedDate5 = "";
													}
											%>
										&nbsp;&nbsp;<%=formatedDate5%>
                   </TD> --%>
                    <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                 <%   java.util.Date utilDate6=dReport.getExpiryDate();
													String formatedDate6 = null;
													if(utilDate6 != null)
													{
														 formatedDate6=dateFormat.format(utilDate6);
													}
													else
													{
														 formatedDate6 = "";
													}
											%>
										&nbsp;&nbsp;<%=formatedDate6%>
                   </TD>
                   <%-- <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                 &nbsp;&nbsp;<% String userId = dReport.getUsrId();
                 	if(userId == null || userId.equals("null"))	{
		               userId=""; } %><%=dReport.getUsrId()%>
                   </TD> --%>
                       <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                 &nbsp;&nbsp;<% String ddNum = dReport.getDdNum();
                 	if(ddNum == null || ddNum.equals("null"))	{
		               ddNum=""; } %><%=dReport.getDdNum()%>
                   </TD>
                       <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                 <%   java.util.Date utilDate7=dReport.getClosureDate();
													String formatedDate7 = null;
													if(utilDate7 != null)
													{
														 formatedDate7=dateFormat.format(utilDate7);
													}
													else
													{
														 formatedDate7 = "";
													}
											%>
										&nbsp;&nbsp;<%=formatedDate7%>
                   </TD>
                   
                  </tr>
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
									<A href="javascript:submitForm('applicationHistoryReport.do?method=applicationHistoryReport')">
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

