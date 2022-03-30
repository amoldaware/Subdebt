<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ page import="com.cgtsi.application.DuplicateApplication"%>
<%@ page import="com.cgtsi.application.DuplicateCriteria"%>
<%@ page import="com.cgtsi.application.DuplicateCondition"%>
<%@ page import="java.util.ArrayList"%>
<% session.setAttribute("CurrentPage","afterCheckDuplicate.do?method=checkDuplicate.do");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="updateApplicationStatus.do?method=updateApplicationStatus" method="POST">
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
								<TD colspan="4">
									<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										<TR>
											<TD width="31%" class="Heading"><bean:message key="probableDuplicatesHeader" /></TD>
											<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
										</TR>
										<TR>
											<TD colspan="4" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
										</TR>
									</TABLE>
								</TD>
							</TR>
							</table>
							<tr>
								<td class="SubHeading" colspan="4">
									<bean:message key="probableDuplicatesWithCgpans"/>
							</tr>
							<tr>
								<td>

								<TABLE width="100%" border="0" cellpadding="1" cellspacing="1">
								<tr> 
								  <td align="center" class="HeadingBg" width="29"><div align="center"><bean:message key = "srNo"/></div>
								  </td>
								  <td class="HeadingBg" align="center"><div align="center"><bean:message key="borrowerId"/></div>
								  </td>
								  <td class="HeadingBg" align="center"><div align="center"><bean:message 					key="oldCgpanNumber"/></div>
								  </td>
								  <td class="HeadingBg" align="center"><div align="center"><bean:message 					key="newAppRefNo"/></div>
								  </td>
								  <td class="HeadingBg" align="center" width="15%"><div align="center"><bean:message key ="details"/></div>
								  </td>
								  <td class="HeadingBg" align = "center"><div align="center"><bean:message key ="existing"/></div>
								  </td>
								  <td class="HeadingBg"  align="center"><div align="center"><bean:message key="newDetails"/></div>
								  </td>								 						
							  </tr>
							 
							   <% int k=0;%>
							   <logic:iterate id="object" name="apForm" property="duplicateApplications">
							   <%
									com.cgtsi.application.DuplicateApplication duplicateApplication= (com.cgtsi.application.DuplicateApplication)object;
									String borrowerId = duplicateApplication.getBorrowerId();
									String oldCgpan = duplicateApplication.getOldCgpan();				
									String newAppRefNo = duplicateApplication.getNewAppRefNo();		
									ArrayList duplicateConditionList=duplicateApplication.getDuplicateCondition();
									int j=duplicateConditionList.size();
									
									String stringVal=new String();
									String jVal=stringVal.valueOf(j);

								%>
								
								 <tr align="center">
							  
								<td class="tableData" align="center" rowspan="<%=jVal%>">&nbsp;<%=(k+1)%>
								</td>
								<td class="tableData" align="center" rowspan="<%=jVal%>" width="10%">&nbsp;
								<%if (borrowerId!=null)
									{%>
									<%=borrowerId%>
								<%}%>

								</td>
								<td class="tableData" align="center" rowspan="<%=jVal%>" width="10%">&nbsp;
								<%if (oldCgpan.substring(0,2).equals("CG"))
								{%>
								<a href="afterCGPANPage.do?method=showCgpanList&cgpan=<%=oldCgpan%>&flag=0"><%=oldCgpan%></a>
								<%}
								else
								{%>
								<a href="afterCGPANPage.do?method=showCgpanList&appRef=<%=oldCgpan%>&flag=1"><%=oldCgpan%></a>
								<%}%>
								</td>
								<td class="tableData" align="center" rowspan="<%=jVal%>" width="10%">
									<!--<%String name="appRefNo(key-"+k+")";%>-->&nbsp;
									<a href="afterCGPANPage.do?method=showCgpanList&appRef=<%=newAppRefNo%>&flag=1"><%=newAppRefNo%></a>
									<!--<html:hidden property = "<%=name%>" name="apForm" value="<%=newAppRefNo%>"/>-->

								</td>	
								<%
									java.util.ArrayList duplicateConditions=(java.util.ArrayList)duplicateApplication.getDuplicateCondition();
									int duplicateConditionSize=duplicateConditions.size();

									com.cgtsi.application.DuplicateCondition duplicateCondition=null;

									for (int i=0; i<duplicateConditionSize;i++)
									{
										duplicateCondition=(com.cgtsi.application.DuplicateCondition)duplicateConditions.get(i);
										String conditionName=duplicateCondition.getConditionName();
										String oldValue=duplicateCondition.getExistingValue();
										String newValue=duplicateCondition.getNewValue();
										if(i==0)
										{										
								%>
										<td class="ColumnBackground" align="center"><%=conditionName%></td>
										<td  class="TableData" align="center"><div align="center"><%=oldValue%></div></td>
										<td  class="TableData" align="center"><div align="center"><%=newValue%></div></td>
										
										
										<%}	else {											
								%>
										<tr>						

											<td class="ColumnBackground" align="center"><div align="center"><%=conditionName%></div></td>
											<td  class="tableData" align="center"><div align="center"><%=oldValue%></div></td>
											<td  class="tableData" align="center"><div align="center"><%=newValue%></div></td>										
										<%}%>
										</tr>
										<%}
										++k;%>
									</logic:iterate>
									

							
							 </table>
							</td>
						</tr>
							  

							 
							<TR >
								<TD height="20" >
									&nbsp;
								</TD>
							</TR>
							<TR >
								<TD align="center" valign="baseline" >
									<DIV align="center">
										<!--<A href="javascript:document.appForm.reset()">
											<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>-->
											<A href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
											<IMG src="images/OK.gif" alt="OK" width="49" height="37" border="0"></A>
										<!--<A href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
										<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>-->
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
