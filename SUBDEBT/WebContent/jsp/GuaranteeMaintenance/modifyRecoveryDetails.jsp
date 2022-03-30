<%@ page language="java"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.cgtsi.guaranteemaintenance.Recovery"%>
<%@ page import="java.text.SimpleDateFormat"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>

<%@ include file="/jsp/SetMenuInfo.jsp" %>

<% session.setAttribute("CurrentPage","modifyRecoveryDetails.do?method=modifyRecoveryDetails");
SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
String formatStr = null;%>
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="saveRecoveryDetails.do?method=saveRecoveryDetails" method="POST" enctype="multipart/form-data">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/GuaranteeMaintenanceHeading.gif"></TD>
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
									<TD colspan="4"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="41%" class="Heading"><bean:message key="recoveryHeader"/><bean:write name="gmPeriodicInfoForm" property="borrowerId"/></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5">
	<%--										  <A href="javascript:submitForm('showOutstandingDetailsLink.do?method=showOutstandingDetailsLink')">Outstanding Details</A>|
											   <A href="javascript:submitForm('showDisbursementDetailsLink.do?method=showDisbursementDetailsLink')">Disbursement
											  Details</A> | <A href="javascript:submitForm('showRepaymentDetailsLink.do?method=showRepaymentDetailsLink')">Repayment Details</A>| <A href="javascript:submitForm('showNPADetailsLink.do?method=showNPADetailsLink')">NPA Details</A>
--%>												
												</TD>
											</TR>

										</TABLE>
									</TD>
								</TR>
					          <tr>
					            <td colspan="6">
									<table width="100%" border="0" cellspacing="1" cellpadding="0">
										<tr>
										  <td align="center" valign="middle" class="HeadingBg"
											 align="center"><bean:message key = "srNo"/>
										  </td>
										  <td class="HeadingBg" align="center"><bean:message 					key="recoveryId"/>
										  </td>
										  <td class="HeadingBg" align="center"><bean:message 					key="amountRecovered"/>								 </td>  
										  <td class="HeadingBg" align="center"><bean:message 					key="recoveryDate"/>
										  </td>
										</TR>

									   <%  int i=0; 
										   String recId = null;
										   Recovery recovery = null;
										   double recamount = 0;
										   Date recdate = null;

									   %>
										
										<logic:iterate id="object" name="gmPeriodicInfoForm" property="recoveryDetails">
										<%
												java.util.Map.Entry recoDtlsMap = (java.util.Map.Entry)object;
												recId = (String)recoDtlsMap.getKey() ;
												recovery = (Recovery)recoDtlsMap.getValue();
												recamount = recovery.getAmountRecovered();
												recdate = recovery.getDateOfRecovery();
												if(recdate!=null){
													formatStr = dateFormat.format(recdate);
												}

										%>
										<TR align = "center">
										  <TD class="tableData" width="25" align="center"><center><%=++i%>
										  </TD>

										  <TD class="tableData" width="25" align="center"><center><a	  href="javascript:submitForm('updateRecovery.do?method=updateRecovery&recoId=<%=recId%>')"><%=recId%></a>
 
										  </TD>

										  <TD class="tableData" width="25" align="center"><center><%=recamount%>
										  </TD>
											
										<%	if(recdate!=null)
											{
												formatStr = dateFormat.format(recdate);
										%>
										  <TD class="tableData" width="25" align="center"><center><%=formatStr%>
										  </TD>
										  <%}else 
											{%>
											  <TD class="tableData" width="25" align="center"><center><%=recdate%>
											  </TD>
											<%}%>
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
								
									<A href="javascript:history.back()">
									<IMG src="images/Back.gif" alt="OK" width="49" height="37" border="0"></A>
								
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

