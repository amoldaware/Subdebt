<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ page import="java.util.TreeMap"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Date"%>
<%@page import ="java.text.DecimalFormat"%>

<% session.setAttribute("CurrentPage","showClaimProjectionDetails.do?method=showClaimProjectionDetails");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>

<%DecimalFormat decimalFormat = new DecimalFormat("##########.##");
%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="checkDuplicate.do" method="POST">
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
							<table width="100%" border="0" cellspacing="1" cellpadding="0">
							<tr> 
							  <td colspan="7">
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR>
										<TD width="31%" class="Heading"><bean:message key="claimProjectionHeader" /></TD>
										<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
									</TR>
									<TR>
										<TD colspan="6" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
									</TR>
								</TABLE>
							  </td>
							</tr>

							<bean:define id="npaAcct" name="ifForm" property = "npaAccounts"/>

							<%
							if(npaAcct.equals("N"))
							{
							%>

								<tr>
									<td class="ColumnBackground" colspan="5"> 
										<bean:message key="nonNpaAccts"/>
									</td>
								</tr>

								<bean:define id="nonNPAMap" name="ifForm" property = "nonNPAAccountsMap"/>
								<bean:define id="nonNPAArrayList" name="ifForm" property = "nonNPAAccountsArrayList"/>

								<%	java.util.TreeMap map=(java.util.TreeMap)nonNPAMap;
									//java.util.Set nonNPASet = map.keySet();
									java.util.ArrayList arrayList = (java.util.ArrayList)nonNPAArrayList;

									System.out.println("size :" + arrayList.size());

										for(int i=0;i<arrayList.size();i++)
										{
											 String key = (String)arrayList.get(i);
											 double approvedAmount=((Double)map.get(key)).doubleValue();
											
										

										%>

										<tr>
											<td class="ColumnBackground" width="50%"> 
												<%=key%>
											</td>
											<td class="TableData" width="50%"> 
												<%=decimalFormat.format(approvedAmount)%>
											</td>

										</tr>
								<%	
									}
							}
							else if(npaAcct.equals("A"))
							{
								%>

								<tr>
									<td class="ColumnBackground" colspan="5"> 
										<bean:message key="npaAccts"/>
									</td>
								</tr>

								<bean:define id="npaMap" name="ifForm" property = "npaAccountsMap"/>
								<bean:define id="npaArrayList" name="ifForm" property = "npaAccountsArrayList"/>

								<%	java.util.TreeMap map=(java.util.TreeMap)npaMap;
									//java.util.Set nonNPASet = map.keySet();
									java.util.ArrayList arrayList = (java.util.ArrayList)npaArrayList;

										for(int i=0;i<arrayList.size();i++)
										{
											 String key = (String)arrayList.get(i);
											 double approvedAmount=((Double)map.get(key)).doubleValue();

										%>

										<tr>
											<td class="ColumnBackground" width="50%"> 
												<%=key%>
											</td>
											<td class="TableData" width="50%"> 
												<%=decimalFormat.format(approvedAmount)%>
											</td>

										</tr>


								<%}
								}
								else if(npaAcct.equals("B"))
								{%>
								<table width="661" border="0" cellspacing="1" cellpadding="1">
									<TR align="left">
										<TD  width="50%">	
											<table border="0" cellspacing="1" cellpadding="1" width="100%">		
												<tr>
													<td class="ColumnBackground" colspan="5"> 
														<bean:message key="nonNpaAccts"/>
													</td>
												</tr>

												<bean:define id="nonNPAMapTemp" name="ifForm" property = "nonNPAAccountsMap"/>
												<bean:define id="nonNPAArrayListTemp" name="ifForm" property = "nonNPAAccountsArrayList"/>

												<%	java.util.TreeMap mapTemp=(java.util.TreeMap)nonNPAMapTemp;
													//java.util.Set nonNPASet = map.keySet();
													java.util.ArrayList arrayListTemp = (java.util.ArrayList)nonNPAArrayListTemp;


														for(int i=0;i<arrayListTemp.size();i++)
														{
															 String key = (String)arrayListTemp.get(i);
															 double approvedAmount=((Double)mapTemp.get(key)).doubleValue();
															
														

														%>

														<tr>
															<td class="ColumnBackground" width="50%"> 
																<%=key%>
															</td>
															<td class="TableData" width="50%"> 
																<%=decimalFormat.format(approvedAmount)%>
															</td>

														</tr>
												<%	
													}
												%>
											</table>
										</td>

									<TD  width="50%">	
										<table border="0" cellspacing="1" cellpadding="1" width="100%">		

											<tr>
												<td class="ColumnBackground" colspan="5"> 
													<bean:message key="npaAccts"/>
												</td>
											</tr>

											<bean:define id="npaMapTemp1" name="ifForm" property = "npaAccountsMap"/>
											<bean:define id="npaArrayListTemp1" name="ifForm" property = "npaAccountsArrayList"/>

											<%	java.util.TreeMap mapTemp1=(java.util.TreeMap)npaMapTemp1;
												//java.util.Set nonNPASet = map.keySet();
												java.util.ArrayList arrayListTemp1 = (java.util.ArrayList)npaArrayListTemp1;

													for(int i=0;i<arrayListTemp1.size();i++)
													{
														 String key = (String)arrayListTemp1.get(i);
														 double approvedAmount=((Double)mapTemp1.get(key)).doubleValue();
														
													

													%>

													<tr>
														<td class="ColumnBackground" width="50%"> 
															<%=key%>
														</td>
														<td class="TableData" width="50%"> 
															<%=decimalFormat.format(approvedAmount)%>
														</td>

													</tr>
											<%}%>
										</table>
									</td>

								<%}%>


							<tr align="left"> 
								<td colspan="4">
									<img src="../images/clear.gif" width="5" height="15">
								</td>
							 </tr>
						  </table>									
						</td>
					</tr>

					
					<TR >
						<TD align="center" valign="baseline" colspan="6">
							<DIV align="center">					
							<A href="javascript:history.back()">
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






