<%@ page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean"%>
<%@ include file="/jsp/SetMenuInfo.jsp"%>

<%
ArrayList invalidApps = new  ArrayList();
out.println("outstanding ERROR  page RAJUKKKKKKK..................................................................");
%>

<HTML>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<BODY>

		<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
			<html:form action="uploadOutstandingAmt.do?method=uploadOutstandingAmt"
				method="POST" enctype="multipart/form-data">
				<html:errors />
				<TR>
					<TD width="20" align="right" valign="bottom">
						<IMG src="images/TableLeftTop.gif" width="20" height="31">
					</TD>
					<TD background="images/TableBackground1.gif"></TD>
					<TD width="20" align="left" valign="bottom">
						<IMG src="images/TableRightTop.gif" width="23" height="31">
					</TD>
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
														<TD width="31%" class="Heading">&nbsp;
															<bean:message key="FileUpload" /> Summary
														</TD>
														<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
													</TR>
													<TR>
														<TD colspan="3" class="Heading">
															<IMG src="images/Clear.gif" width="5" height="5"></TD>
													</TR>
												</TABLE>
											</TD>
										</TR>
									</table>
									<%			
										System.out.println("request.getAttribute(DUPAPPS) before");
										invalidApps = (ArrayList) request.getAttribute("DUPAPPS");
										System.out.println("request.getAttribute(DUPAPPS) After=======invalidApps==="+invalidApps.size());
									%>									
									<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
										<TR>
											<TD style="color: red; size: 5pt;">
												Please correct the following data in the uploaded excel file.
											</TD>
										</TR>
									</TABLE>
									<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
										<tr>
											<TD class="ColumnBackground" width="5%" style="text-align: center;">
												&nbsp;Excel&nbsp;<br>&nbsp;Sheet&nbsp;<br>&nbsp;Row No.&nbsp;
											</TD>
											<TD class="ColumnBackground" width="95%" style="text-align: center;">
												&nbsp;Incorrect/Invalid Data
											</TD>
										</tr>
										<%
										if(invalidApps != null)
										{  
											int srlNo=0;
											System.out.println("invalid apps ! null");
											System.out.println("invalid apps:" + invalidApps.size());
											Iterator itr = invalidApps.iterator();
											int no = 1;
											while (itr.hasNext())
											{
												ArrayList errors = (ArrayList) itr.next();
												int total_errors = errors.size();
												String data1 = "";
												String data2 = "";
												System.out.println("total errors:" + total_errors);
										%>
										<TR>
										<%
										for (int i = 0; i < total_errors; i++)
										{
											if (i == 0)
											{
												data1 = (String) errors.get(i);

											}
											else if (i >= 1)
											{
												data2 = data2 + (String) errors.get(i) + ".";
											}
										%>
										<%
										}
										%><td class="TableData" style="text-align: left;"><%=srlNo++%></td>
											<td class="TableData" style="text-align: center;"><%=data1%>&nbsp;&nbsp;</td>
											<td class="TableData" style="text-align: right;"><%=data2%>&nbsp;&nbsp;</td>
										</TR>
										<%
											no++;
										}
										}
										%>
									</TABLE>
								</TD>
							</TR>							
							<TR align="center" valign="baseline" >
								<TD colspan="2" width="700">
									<DIV align="center">
										<A href="javascript:submitForm('uploadOutstandingInput.do')">
											<IMG src="images/Back.gif" alt="Back" width="49" height="37" border="0">
										</A>								
									</DIV>
								</TD>
							</TR>				
						</TABLE>
					</TD>
					<TD width="20" background="images/TableVerticalRightBG.gif">&nbsp;</TD>
				</TR>
				<TR>
					<TD width="20" align="right" valign="top">
						<IMG src="images/TableLeftBottom1.gif" width="20" height="15">
					</TD>
					<TD background="images/TableBackground2.gif">&nbsp;</TD>
					<TD width="20" align="left" valign="top">
						<IMG src="images/TableRightBottom1.gif" width="23" height="15">
					</TD>
				</TR>
			</html:form>
		</TABLE>
	</BODY>
</HTML>