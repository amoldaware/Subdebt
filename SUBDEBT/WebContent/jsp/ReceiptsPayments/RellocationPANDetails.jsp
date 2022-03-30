<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.ParsePosition"%>
<%@ page import="com.cgtsi.receiptspayments.AllocationDetail"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%
session.setAttribute("CurrentPage","displayPaymentsForReallocation.do?method=getPaymentsForReallocation");
String name;
int i = 0;
String fDisbDate;
SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
String allocate ;
%>
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="submitReallocationPayments.do?method=submitReallocationPayments" method="POST">
	<TR>
		<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
		<TD background="images/TableBackground1.gif"><IMG src="images/ReceiptsPaymentsHeading.gif" height="25"></TD>
		<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
	</TR>
	<TR>
		<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>

		<TD>
			<TABLE width="100%" border="0" align="left" cellpadding="1" cellspacing="1">
				<TR>
					<TD colspan="8">
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="20%" class="Heading"><bean:message key="panDetails"/></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
												<TD>&nbsp;</TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
								</TD>
							</TR>
							<TR>
								<TH align="left" valign="top" class="ColumnBackground">
									<bean:message key="dan" />
								</TH>
								<TH align="left" valign="top" class="ColumnBackground">
									<bean:message key="cgpan" />
								</TH>
								<TH align="left" valign="top" class="ColumnBackground">
									<bean:message key="unitName" />
								</TH>
								<TH align="left" valign="top" class="ColumnBackground">
									<bean:message key="facilityCovered" />
								</TH>
								<TH align="left" valign="top" class="ColumnBackground">
									<bean:message key="amountDue" />
								</TH>
								<TH align="left" valign="top" class="ColumnBackground">
									<bean:message key="firstDisbursementDate" />
								</TH>
								<TH align="left" valign="top" class="ColumnBackground">
									<bean:message key="pay" />
								</TH>
							</TR>
							<%
								String cgpanKey=null;
								String reasonsKey=null;
								String danNo=null;
								double amtRaised=0;
								double penalty=0;
							%>
							<logic:iterate id="object" name="rpAllocationForm" property="danPanDetails">
							<%
								java.util.Map.Entry entry=(java.util.Map.Entry)object;

								String danId=(String)entry.getKey();
								danNo=danId.replace('.', '_');

								ArrayList panDetails=(ArrayList)entry.getValue();

								pageContext.setAttribute("panDetails",panDetails);
							%>
							<logic:iterate id="allocation" name="panDetails">
							<%
								AllocationDetail allocationDetail=(AllocationDetail)allocation;


								cgpanKey="cgpan("+danNo+"-"+allocationDetail.getCgpan()+")";
							%>
							<TR>
								<TD align="left" valign="top" class="TableData">
									<%=danId%>
									<%
									danId="";
									%>

								</TD>
								<TD align="left" valign="top" class="TableData">
									<%=allocationDetail.getCgpan()%>
								</TD>

								<TD align="left" valign="top" class="TableData">
									<%=allocationDetail.getNameOfUnit()%>
								</TD>
								<TD align="left" valign="top" class="TableData">
									<%=allocationDetail.getFacilityCovered()%>
								</TD>
								<TD align="left" valign="top" class="TableData">
								<%
								if (allocationDetail.getAmountDue()==0)
								{
								%>
								-
								<%}else{%>
									<%=allocationDetail.getAmountDue()%>
								<%}%>
								</TD>
								<bean:define type="java.util.Map" id="firstDisDateMap" name="rpAllocationForm" property="firstDisbursementDates"/>

								<%
								if(request.getParameter("firstDisbursementDates("+danNo+"-"+allocationDetail.getCgpan()+")")==null)
								{
									if(allocationDetail.getFirstDisbursementDate()==null)
									{
										firstDisDateMap.put(danNo+"-"+allocationDetail.getCgpan(),"");
									}
									else{

										fDisbDate = dateFormat.format(allocationDetail.getFirstDisbursementDate());
										firstDisDateMap.put(danNo+"-"+allocationDetail.getCgpan(),fDisbDate);
									}

									
								}
								else if(request.getParameter("firstDisbursementDates("+danNo+"-"+allocationDetail.getCgpan()+")")!=null && !request.getParameter("firstDisbursementDates("+danNo+"-"+allocationDetail.getCgpan()+")").equals(""))
								{
									System.out.println("first dis date :" + request.getParameter("firstDisbursementDates("+danNo+"-"+allocationDetail.getCgpan()+")"));
									java.util.Date disDate = (java.util.Date)dateFormat.parse(request.getParameter("firstDisbursementDates("+danNo+"-"+allocationDetail.getCgpan()+")"),new ParsePosition(0));
										System.out.println("disDate :" + disDate);

									if(disDate!=null)
									{
										fDisbDate = dateFormat.format(disDate);
									}
									else{

										fDisbDate = (String)request.getParameter("firstDisbursementDates("+danNo+"-"+allocationDetail.getCgpan()+")");
									}

									firstDisDateMap.put(danNo+"-"+allocationDetail.getCgpan(),fDisbDate);

								}
								%>

								<TD align="left" valign="top" class="TableData">
								<%name="firstDisbursementDates("+danNo+"-"+allocationDetail.getCgpan()+")";%>
									<%if((allocationDetail.getCgpan()).substring(allocationDetail.getCgpan().length()-2,allocationDetail.getCgpan().length()).equals("TC"))
									{
										if(allocationDetail.getFirstDisbursementDate()==null) {%>
											<html:text property="<%=name%>" alt="First Disbursement Date" name="rpAllocationForm" maxlength="10"/>
										<%} else {
											fDisbDate = dateFormat.format(allocationDetail.getFirstDisbursementDate());
										%>
											<html:text property="<%=name%>" alt="First Disbursement Date" name="rpAllocationForm"  disabled="true"/>

											<html:hidden property="<%=name%>" alt="First Disbursement Date" name="rpAllocationForm" value="<%=fDisbDate%>"/>
<!--											<%=fDisbDate%>-->
										<%}}%>	
<!--
								<%name="firstDisbursementDates("+danNo+"-"+allocationDetail.getCgpan()+")";%>
									<%if(allocationDetail.getFirstDisbursementDate()==null) {%>
										<html:text property="<%=name%>" alt="First Disbursement Date" name="rpAllocationForm" value=""/>
									<%} else {
										fDisbDate = dateFormat.format(allocationDetail.getFirstDisbursementDate());
									%>

										<%=fDisbDate%>
									<%}%>
-->
								</TD>
								<TD align="left" valign="top" class="TableData">
								<%
								if (allocationDetail.getAmountDue()==0)
								{
								%>
									<html:checkbox name="rpAllocationForm" property="<%=cgpanKey%>" value="<%=allocationDetail.getCgpan()%>" disabled="true"/>
								<%}else{%>
									<html:checkbox name="rpAllocationForm" property="<%=cgpanKey%>" value="<%=allocationDetail.getCgpan()%>"/>
								<%}%>
								</TD>
							</TR>
							<%
								amtRaised += allocationDetail.getAmountDue();
								penalty += allocationDetail.getPenalty();
							%>
						<%++i;%>
							<%name="amountsRaised("+danNo+"-"+allocationDetail.getCgpan()+")";%>
							<html:hidden property="<%=name%>" name="rpAllocationForm" value='<%=""+amtRaised%>' />

							<%name="penalties("+danNo+"-"+allocationDetail.getCgpan()+")";%>
							<html:hidden property="<%=name%>" name="rpAllocationForm" value='<%=""+penalty%>' />

							<%
								amtRaised=0;
								penalty=0;
							%>
						</logic:iterate>
						</logic:iterate>
						<TR>
							<TD align="center" valign="baseline" colspan="7">
								<DIV align="center">
								<A href="javascript:submitForm('submitReallocationPayments.do?method=submitReallocationPayments')">
										<IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>
									<A href="javascript:document.rpAllocationForm.reset()">
										<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>
									
									<A href="javascript:history.back()">
										<IMG src="images/Back.gif" alt="Cancel" width="49" height="37" border="0"></A>
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