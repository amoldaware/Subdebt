<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.cgtsi.guaranteemaintenance.RepaymentSchedule"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>


<% session.setAttribute("CurrentPage","showRepaymentSchedule.do?method=showRepaymentSchedule");
String name;
String dueDate;
String aMoratorium;
String installmentNo;
String period;
String borrowerId = null;
String bName = null;
Date dDate = null;
SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="saveRepaymentSchedule.do?method=saveRepaymentSchedule" method="POST" enctype="multipart/form-data">
	<TR> 
		<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" 			height="31">
		</TD>
		<TD background="images/TableBackground1.gif"><IMG src="images/GuaranteeMaintenanceHeading.gif">
		</TD>
		<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31">
		</TD>
	</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
			<DIV align="right">			
				<A HREF="javascript:submitForm('helpRepaymentSchedule.do?method=helpRepaymentSchedule')">
			    HELP</A>
			</DIV>

				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
				<TR>
					<TD>
						<TABLE width="100%" border="0" cellspacing="1" cellpadding="1" id="addRow">
							<TR>
								 <TD colspan="6"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr>
										  <td width="35%" class="Heading"><bean:message key ="repayScheduleHeader"/> <bean:write name="gmPeriodicInfoForm" property ="borrowerIdForSchedule"/>
										  </td>
										 
										  <td  align="left" valign="bottom"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
										  <td align="right"> <div align="right"> </div></td>
										  
										  <td align="right"> <div align="right"> </div>
										  </td>
										</tr>
										<tr> 
										  <td colspan="4" class="Heading" width="695"><img src="images/Clear.gif" width="5" height="5">
										  </td>
										</tr>
									  </table>
									</td>
								  </tr>
								  <tr> 
									<td colspan="2"><table width="695" border="0" cellspacing="1"     		cellpadding="0">
									  <tr> 
											<td  align="center" valign="middle" class="HeadingBg" 
											   align="center"><bean:message key="srNo"/>
											</td>

											<td  class="HeadingBg" align="center"><bean:message key = "BorrowerID"/>
											</td>

											<td class="HeadingBg" align="center"><bean:message key = "borrowerName"/>
											</td>

											<td  class="HeadingBg" align="center"><bean:message key="cgpanNumber"/>
											</td>

											<td class="HeadingBg"  align="center"><bean:message key = "scheme"/>
											</td>

											<td class="HeadingBg" align = "center"><bean:message key   			="moratorium"/>
											</td>

											<td class="HeadingBg" align="center"><font color="#FF0000" size="2">*</font><bean:message key = "firstInstallment"/>
											</td>

											<td class="HeadingBg"  align="center"><bean:message key="periodicity"/>
											</td>

											<td class="HeadingBg" align="center"><bean:message key = "noOfInstallment"/>
											</td>
									</tr>
				 
										 <tr> 
										<%int i=0; String cgpan = "";
										int repayScheduleIndex = 0;%>
										<logic:iterate id="object" name="gmPeriodicInfoForm" property="repaymentSchedules">
									<%
										
										com.cgtsi.guaranteemaintenance.RepaymentSchedule repaySchedule = (com.cgtsi.guaranteemaintenance.RepaymentSchedule)object;
										  cgpan = repaySchedule.getCgpan();
										  borrowerId = repaySchedule.getBorrowerId();
										  bName = repaySchedule.getBorrowerName();

										  %>
										
										  <td class="tableData"  align="center"><%=++i%>
										  </td>
										 
										 <% if  (borrowerId != null)
										 {
										 %>
										  <td class="tableData" >&nbsp;<A
							href="javascript:submitForm('showBorrowerDetailsLink.do?method=showBorrowerDetailsLink&formFlag=schedule&bidLink=<%=borrowerId%>')"><%=borrowerId%></A>
										  </td>
										 <%}else{ %>
													 <td class="tableData" >
											<%	}%>
										 
										 <% if  (bName != null)
										 {
										 %>
											<td class="tableData" ><%=bName%>
										  </td>
										 <%}else{ %>
													 <td class="tableData" >
										<%		}%>
										 										 
										 <td class="tableData" >&nbsp;<A
								href="javascript:submitForm('showCgpanDetailsLink.do?method=showCgpanDetailsLink&cgpanDetail=<%=cgpan%>')"><%=cgpan%></a>
										  </td>
										  
										  <td class="tableData" ><%=repaySchedule.getScheme()%>
										  </td>
										  
											<%name = "cgpans(key-"+repayScheduleIndex+")";%>
											<html:hidden property = "<%=name%>" name="gmPeriodicInfoForm" value="<%=cgpan%>"/>

										  <td class="tableData"  align="right"> 
											 <%  aMoratorium = (new Integer(repaySchedule.getMoratorium())).toString();
											 name = "moratorium(key-"+repayScheduleIndex+")";%> <html:text property = "<%=name%>" size="10" name="gmPeriodicInfoForm" value="<%=aMoratorium%>" onkeypress="return numbersOnly(this, event)"/>
										  </td>
										  <%dDate = repaySchedule.getFirstInstallmentDueDate();
										  if(dDate !=null)
										  {
										  %>
										  <td class="tableData" valign="top">
											<table cellpadding="0" cellspacing="0">
												<tr>
													<td><% dueDate = dateFormat.format(dDate);
													name = "firstInstallmentDueDate(key-"+repayScheduleIndex+")";%><html:text property="<%=name%>" size="10" alt=" Date" name="gmPeriodicInfoForm" value="<%=dueDate%>" maxlength="10"/>
													</td>
												</tr>
											</table>
										  </td>
										  <%}
											else
											{%>
											  <td class="tableData" valign="top">
												<table cellpadding="0" cellspacing="0">
													<tr>
													<td><% dueDate = null;
													name = "firstInstallmentDueDate(key-"+repayScheduleIndex+")";%><html:text property="<%=name%>" size="10" alt=" Date" name="gmPeriodicInfoForm" value="<%=dueDate%>" maxlength="10"/>
													</td>
													</tr>
												</table>
											  </td>
											<%}%>

										  <td class="tableData" width="69"><div align="center">
										  <% String per = repaySchedule.getPeriodicity();
										  if(per!=null){
											  period = (new Integer(per)).toString();
										  }else{
											  period = "";
										  }
										  
										 
										  name = "periodicity(key-"+repayScheduleIndex+")";%>
												  <html:select property = "<%=name%>" name = "gmPeriodicInfoForm" value="<%=period%>">
														<html:option value="">Select</html:option>
														<html:option value="1">Monthly</html:option>
														<html:option value="2">Quarterly</html:option>
														<html:option value="3">Half-Yearly</html:option>
												  </html:select>
												</div>
										 </td>
										 
										 <td class="tableData"  align="right"><% installmentNo = (new Integer(repaySchedule.getNoOfInstallment())).toString();
										  name = 						"noOfInstallment(key-"+repayScheduleIndex+")";%> 
										 
										 <html:text	 property = "<%=name%>" size="10"      				 name="gmPeriodicInfoForm" value="<%=installmentNo%>" onkeypress="return numbersOnly(this, event)" maxlength="3"/>
										 </td><%++repayScheduleIndex;%>
									  </tr>
									</logic:iterate>
<html:hidden property = "repayScheduleIndex" name="gmPeriodicInfoForm" value="<%=String.valueOf(repayScheduleIndex)%>"/>
								</table>
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
								
									<A href="javascript:submitForm('saveRepaymentSchedule.do?method=saveRepaymentSchedule')">
									<IMG src="images/Save.gif" alt="OK" width="49" height="37" border="0"></A>
									<A href="javascript:document.gmPeriodicInfoForm.reset()">
									<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>
								<A href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
								<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>
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
