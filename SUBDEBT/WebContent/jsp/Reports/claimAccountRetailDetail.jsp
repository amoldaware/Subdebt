<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DecimalFormat"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@ include file="/jsp/SetMenuInfo.jsp"%>
<% session.setAttribute("CurrentPage","claimAccountReportInput.do?method=claimAccountReportInput");%>
<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>
<%DecimalFormat decimalFormat = new DecimalFormat("#########0.00");%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form
		action="claimAccountReportInput.do?method=claimAccountReportInput"
		method="POST" enctype="multipart/form-data">
		<TR>
			<TD width="20" align="right" valign="bottom"><IMG
				src="images/TableLeftTop.gif" width="20" height="31" alt=""></TD>
			<TD background="images/TableBackground1.gif"><IMG
				src="images/ReportsHeading.gif" width="121" height="25" alt=""></TD>
			<TD width="20" align="left" valign="bottom"><IMG
				src="images/TableRightTop.gif" width="23" height="31" alt=""></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
			<TABLE width="100%" border="0" align="left" cellpadding="0"
				cellspacing="0">
				<TR>
					<TD>
					<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
						<TR>
							<TD colspan="23">
							<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td colspan="6" class="Heading1" align="center"><u><bean:message
										key="reportHeader" /></u></td>
								</tr>
								<tr>
									<td colspan="6">&nbsp;</td>
								</tr>
								<TR>
									<TD width="15%" class="Heading">CLAIM ACCOUNT REPORT</TD>
									
									<TD><IMG src="images/TriangleSubhead.gif" width="19"
										height="19" alt=""></TD>
								</TR>
								<TR>
									<TD colspan="3" class="Heading"><IMG
										src="images/Clear.gif" width="5" height="5" alt=""></TD>
								</TR>

							</TABLE>
							</TD>
						</TR>
						
								<%	
										int columnCount=0;
							%>	
						<TR>
						<td width="3%" align="left" valign="top" class="ColumnBackground"><bean:message
								key="sNo" /></td>
						<logic:iterate id="object" name="reportForm" property="colletralCoulmnName"
							indexId="index">
							<%	
										String str=(String)object;
										columnCount++;
										
							%>
		
								<TD width="10%" align="left" valign="top"
									class="ColumnBackground1"><%=str %>
								</TD>
								</logic:iterate>
							</TR>
							
						
						<logic:iterate id="object1" name="reportForm" property="colletralCoulmnValue"
							indexId="index1">
							<TR>
								
								<td align="left" valign="top" class="ColumnBackground1"><%=index1+1%></td>
							<%	
							ArrayList value =  (ArrayList)object1;
							
							
							for(int i=0;i<value.size();i++){
							%>
									<TD width="10%" align="left" valign="top"
									class="ColumnBackground1">
							
								<%=value.get(i) %>
							<% } %>
							</TD>
					
							
							
							</TR>
						</logic:iterate>
						
					</TABLE>
					</TD>
				</TR>

				


				<TR>
					<TD height="20">&nbsp;</TD>
				</TR>
				<TR>
					<TD align="left" valign="baseline">
						<font color="blue" 	> MEM_STATUS				
			<p style="font-size:12px;">MO -- MLI Maker Updated.</p>
			<p style="font-size:12px;">MA --  MLI Checker Approval. </p>
			<p style="font-size:12px;">CA  -- CGTMSE Approved(Account Activated)</p></font>
		
					</TD>
				</TR>
			</TABLE>
			</TD>
			<TD width="20" background="images/TableVerticalRightBG.gif">
			&nbsp;</TD>
		</TR>
		<TR>
			<TD width="20" align="right" valign="top"><IMG
				src="images/TableLeftBottom1.gif" width="20" height="15" alt="">
			</TD>
			<TD background="images/TableBackground2.gif">&nbsp;</TD>
			<TD width="20" align="left" valign="top"><IMG
				src="images/TableRightBottom1.gif" width="23" height="15" alt="">
			</TD>
		</TR>
	</html:form>
</TABLE>

