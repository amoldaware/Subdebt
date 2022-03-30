<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Map"%>
<%@ page import="com.cgtsi.actionform.APForm"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>

<% session.setAttribute("CurrentPage","showTCAppDetailsForConv.do?method=showTCAppDetailsForConv");
String name="";
%>

<body onload ="javascript:enableDecision()" >
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="showTCAppDetailsForConv.do?method=showTCAppDetailsForConv" method="POST">
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

							<table width="661" border="0" cellspacing="1" cellpadding="1">								
								<TR align="left">
									<TD  width="50%">		
									
										<table border="0" cellspacing="1" cellpadding="1" width="100%">		
											<tr>
												<td class="Heading"><div align="center">
														<bean:message key="listOfAppRefNos"/>
														</div>
												</td>
												<td class="Heading"><div align="center">
														Select
														</div>
												</td>
												<td class="Heading"><div align="center">
														Link CGPAN
														</div>
												</td>



											</tr>
												  <%
												  int k=0;
													
													APForm apForm = (APForm)session.getAttribute("apForm");
													

													if (apForm.getTcApplications().size()!=0)
													{
														
												%>

													<logic:iterate id="tcAppRefNo" name="apForm" property="tcApplications"> 	
														<%
														String linkTcAppRefNo=(String)tcAppRefNo;
														
														%>
														<tr>
															<td class="ColumnBackground" height="24">
																<div align="center">
																<%name="tcAppRefNo(key-"+k+")";%>											<a href="afterCGPANPage.do?method=showCgpanList&appRef=<%=linkTcAppRefNo%>&flag=1"><%=linkTcAppRefNo%></a>
																	<html:hidden property="<%=name%>" name="apForm" value="<%=linkTcAppRefNo%>"/>
																</div>	
															</td>

															<td class="ColumnBackground" height="24">
															<%name="tcDecision(key-"+k+")";%>
															<bean:define type="java.util.Map" id="tcDecisionMap" name="apForm" property="tcDecision"/>

															<%
																System.out.println(request.getParameter("tcDecision(key-"+k+")"));
																if(request.getParameter("tcDecision(key-"+k+")")!=null && request.getParameter("tcDecision(key-"+k+")").equals("ATL"))
																{
																	String status = (String)request.getParameter("tcDecision(key-"+k+")");
																	System.out.println("status 1 :" + status);
																	tcDecisionMap.put("key-"+k,status);

																}
																else{
																	System.out.println(request.getParameter("tcDecision(key-"+k+")"));
																	String status = (String)request.getParameter("tcDecision(key-"+k+")");
																	System.out.println("status 1 :" + status);
																	tcDecisionMap.put("key-"+k,status);


																}

															%>

															<html:checkbox property="<%=name%>" alt="Close" name="apForm" value="ATL" onclick="javascript:enableDecision()"/>
<!--																<html:select property="<%=name%>" name="apForm">
																	<html:option value="">Select</html:option>
																	<html:option value="ATL">Additional Term Loan</html:option>
																</html:select>
	-->														</td>

															<td class="ColumnBackground" height="24">
																<div align="center">
																<%name="tcCgpan(key-"+k+")";%>
																	<html:text property="<%=name%>" size="20" alt="cgpan" name="apForm" maxlength="13" />								
																</div>	
															</td>

														</tr>	
														<%++k;%>
													</logic:iterate>	
												<%}%>
										<html:hidden property="tcEntryIndex" name="apForm" value="<%=String.valueOf(k)%>"/>
										</table>

									</TD>
								</TR>										
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
											<A href="javascript:submitForm('afterTcConversion.do?method=afterTcConversion')">
											<IMG src="images/Save.gif" alt="OK" width="49" height="37" border="0"></A>
										<A href="javascript:document.apForm.reset()">
											<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>

										<A href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
										<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>
									</DIV>
								</TD>
							</TR>

				</table>
			</td>
										
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
</body>


