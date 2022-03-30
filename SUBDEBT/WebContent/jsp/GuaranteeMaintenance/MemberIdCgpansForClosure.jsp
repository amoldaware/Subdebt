<%@ page language="java"%>
<%@ page import="java.util.TreeMap"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>

<%@ include file="/jsp/SetMenuInfo.jsp" %>

<% session.setAttribute("CurrentPage","showDetailsForFeeNotPaid.do?method=showDetailsForFeeNotPaid");%>
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="closeAppsForFeeNotPaid.do?method=closeAppsForFeeNotPaid" method="POST" enctype="multipart/form-data">
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
									<TD colspan="6"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="31%" class="Heading"><bean:message key="closureOfApplications"/></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
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
										  <td class="HeadingBg" align="center"><bean:message 					key="memberId"/>
										  </td>
										  <td class="HeadingBg" align="center"><bean:message 					key="noOfCGPANs"/>
										  </td>
										</TR>

									   <%  
											int i=0; 
											String memberId = null;
											Integer noOfCgpans = new Integer(0);
									   %>
										
										<logic:iterate id="object" name="gmCloseSFGFForm" property="memberIdCgpans">
										<%
											java.util.Map.Entry clDtlsMap = (java.util.Map.Entry)object;
											memberId = (String)clDtlsMap.getKey() ;
											noOfCgpans = (Integer)clDtlsMap.getValue();
										%>

										<TR align = "center">
											<TD class="tableData" width="25" align="center"><center>
												<%=++i%>
											</TD>

											<TD class="tableData" width="25" align="center"><center>
												<%=memberId%>											
											</TD>
								
											<TD class="tableData" width="25" align="center"><center>
												<a	  href="javascript:submitForm('closeAppsForFeeNotPaid.do?method=closeAppsForFeeNotPaid&memberIdClosure=<%=memberId%>')">
												<%=noOfCgpans%>
												</a> 
											</TD>
										</TR>
									 </logic:iterate>
								</TABLE>
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
							
								<A href="javascript:submitForm('showFilterForFeeNotPaid.do?method=showFilterForFeeNotPaid')">
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
