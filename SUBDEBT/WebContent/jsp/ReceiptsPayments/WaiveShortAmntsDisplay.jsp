<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ page import="com.cgtsi.receiptspayments.DemandAdvice"%>
<%@ page import="com.cgtsi.actionform.RPActionForm"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<% session.setAttribute("CurrentPage","afterMemberInfo.do?method=afterMemberInfo");%>
<%@ include file="/jsp/SetMenuInfo.jsp"%>
<%String generatedDate;
String name;
SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="afterMemberInfo.do?method=afterMemberInfo" method="POST">
	
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
							<table width="661" border="0" cellspacing="1" cellpadding="0">
							 <TR>
								<TD colspan="7">
									<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										<TR>
											<TD width="31%" class="Heading"><bean:message key="waive_short_amounts" /></TD>
											<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
										</TR>
										<TR>
											<TD colspan="6" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
										</TR>
									</TABLE>
								</TD>
							</TR>
							</table>
							
							<table width="661" border="0" cellspacing="1" cellpadding="1">
								<tr align="left">
								  <td align="center" class="HeadingBg" width="29"><div align="center"><bean:message key = "srNo"/></div>
								  </td>
								  <td class="HeadingBg" align="center"><div align="center"><bean:message key="SHDAN"/></div>
								  </td>								  
								  <td class="HeadingBg" align="center"><div align="center"><bean:message 					key="danDate"/></div>
								  </td>	
								  <td class="HeadingBg" align="center"><div align="center"><bean:message 					key="amountDue"/></div>
								  </td>		
								  <td class="HeadingBg" align="center"><div align="center"><bean:message key="dueDate"/></div>
								  </td>
								  <td class="HeadingBg" align="center"><div align="center"><bean:message key="waiveFlag"/></div>
								  </td>								  
								</tr>

								<% int i=0;%>
								<logic:iterate id="object" name="rpAllocationForm" property="paymentDetails">
								 <%
							   com.cgtsi.receiptspayments.DemandAdvice demandAdvice = (com.cgtsi.receiptspayments.DemandAdvice)object;

							   String danId=demandAdvice.getDanNo();
							   generatedDate=dateFormat.format(demandAdvice.getDanGeneratedDate());
							   double dueAmount = demandAdvice.getAmountRaised();
							   String duedate=dateFormat.format(demandAdvice.getDanDueDate());
							   String shiftDanId = danId.replace('.', '_');
							   %>
							   <tr>
								<td class="tableData" align="center">&nbsp;<%=(i+1)%>
								</td>
								<td class="tableData" align="center" width="10%">&nbsp;<%name="danNo("+shiftDanId+")";%><%=danId%>
								<html:hidden property="<%=name%>" name="rpAllocationForm" value="<%=danId%>"/>
								</td>
								<td class="tableData" align="center">&nbsp;<%=generatedDate%>
								</td>
								<td class="tableData" align="center">&nbsp;<%=dueAmount%>
								</td>
								<td class="tableData" align="center">&nbsp;<%=duedate%>
								</td>
								<td class="tableData" align="center">&nbsp;	
									<%name="waivedFlags("+shiftDanId+")";%>
									<input type="checkbox" name="<%=name%>" value="<%=danId%>"> 	
									<html:hidden property="<%=name%>" name="rpAllocationForm" value="<%=danId%>"/>


								</td>
								</tr>
								</logic:iterate>
							 </table>									
<!--						</td>
					</tr>
-->
					
					<TR >
						<TD align="center" valign="baseline" >
							<DIV align="center">	
								<A href="javascript:submitForm('waiveShortDans.do?method=waiveShortDans')">
							
								<IMG src="images/OK.gif" alt="OK" width="49" height="37" border="0"></A>								
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