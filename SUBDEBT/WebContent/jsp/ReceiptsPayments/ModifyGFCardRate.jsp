<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Map"%>
<%@ page import="com.cgtsi.actionform.RPActionForm"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>

<% session.setAttribute("CurrentPage","showAllCardRates.do?method=showAllCardRates");
String name="";
%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="showAllCardRates.do?method=showAllCardRates" method="POST">
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
							<tr> 
							  <td colspan="4">
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR>
										<TD width="45%" class="Heading">&nbsp;<bean:message key="guaranteeFeeCardDetails" /></TD>
										<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
									</TR>
									<TR>
										<TD colspan="6" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
									</TR>
								</TABLE>
							  </td>
							</tr>

									<TD  width="50%">		
									
										<table border="0" cellspacing="1" cellpadding="1" width="100%">		
											<tr>
												<td class="ColumnBackground" rowspan="2"><div align="center">
														Sr No.
														</div>
												</td>

												<td class="ColumnBackground" colspan="2"><div align="center">
														Amount in Rs.
														</div>
												</td>
												<td class="ColumnBackground" rowspan="2"><div align="center">
														Card Rate in %
														</div>
												</td>

											</tr>
											<tr>
												<td class="ColumnBackground"><div align="center">
														Low
														</div>
												</td>

												<td class="ColumnBackground" ><div align="center">
														High
														</div>
												</td>

											</tr>

												  <%
												  int k=0;
												  int i=1;
													
												%>

													<logic:iterate id="object" name="rpAllocationForm" property="gfCardRateList"> 	
														<%
														 com.cgtsi.receiptspayments.GFCardRate gfCardRate = ( com.cgtsi.receiptspayments.GFCardRate)object;

														int cardRateId=gfCardRate.getCardRateId();
														String rateId = (new Integer(cardRateId)).toString();

														double lowRange = gfCardRate.getLowRangeAmount();
														double highRange = gfCardRate.getHighRangeAmount();

														double cardRate = gfCardRate.getGfCardRate();
														String gfRate=(new Double(cardRate)).toString();
														
														%>
														<tr>
														<td class="TableData" height="24" ><div align="center">
														<%=i%></div>	
														</td>
															<td class="TableData" height="24">
																<div align="center">	
																<%name="lowAmount(key-"+k+")";%>
																	<%=lowRange%>
																</div>	
															</td>

															<td class="TableData" height="24">
																<div align="center">				
																<%name="highAmount(key-"+k+")";%>
																	<%=highRange%>
																</div>	
															</td>

															<bean:define type="java.util.Map" id="gfRateMap" name="rpAllocationForm" property="gfRate"/>

														<%if(request.getParameter("gfRate(key-"+k+")")!=null)
															{								
																	
																if(request.getParameter("gfRate(key-"+k+")").equals(""))
																{
																	
																	gfRateMap.put("key-"+k,new Double(0));
																}
																else
																{
																	double doubleAmt = Double.parseDouble(request.getParameter("gfRate(key-"+k+")"));

																	gfRateMap.put("key-"+k,new Double(doubleAmt));
																}

															}
														else{

															gfRateMap.put("key-"+k,gfRate);
														}

														%>


															<td class="TableData" height="24">
																<div align="center">
																<%name="gfRate(key-"+k+")";%>
																	<html:text property="<%=name%>" size="20" alt="cgpan" name="rpAllocationForm" maxlength="5" onkeypress="return decimalOnly(this, event,2)" onkeyup="isValidDecimal(this)" />								
																</div>	
															</td>
															<%name="rateId(key-"+k+")";%>
															<html:hidden property = "<%=name%>" name="rpAllocationForm" value="<%=rateId%>"/>

														</tr>		
														<%++k;
															++i;%>
													</logic:iterate>	
												<%%>

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
											<A href="javascript:submitForm('saveGFCardRates.do?method=saveGFCardRates')">
											<IMG src="images/Save.gif" alt="OK" width="49" height="37" border="0"></A>
										<A href="javascript:document.rpAllocationForm.reset()">
											<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>

										<A href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
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


