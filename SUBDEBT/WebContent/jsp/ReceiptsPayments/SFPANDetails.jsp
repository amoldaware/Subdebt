<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.cgtsi.receiptspayments.AllocationDetail"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%session.setAttribute("CurrentPage","appropriateallocatePayments.do?method=getSFPANDetails");
String name;
int i = 0;
String fDisbDate;
SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
DecimalFormat df=new DecimalFormat("######################.##");
df.setDecimalSeparatorAlwaysShown(false);
String allocate ;
%>
<%
String focusField="test";

%>
<body>
	<bean:define id="danNumber" name="rpAllocationForm" type="com.cgtsi.actionform.RPActionForm"/> 
	<html:form name="rpAllocationForm" type="org.apache.struts.validator.DynaValidatorActionForm" action="submitSFPANPayment.do?method=submitSFPANPayments" method="POST" enctype="multipart/form-data" focus="<%=focusField%>">
	<html:hidden name="rpAllocationForm" property="test"/>
	<html:errors />
	<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
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
<!--								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										<TR>
											<TD width="40%" class="Heading">
												<bean:message key="selectPanHeader" />
												<bean:write name="rpAllocationForm" property="danNo"/>
											</TD>
											<TD>
												<IMG src="images/TriangleSubhead.gif" width="19" height="19">
											</TD>
										</TR>
										<TR>
											<TD colspan="4" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
										</TR>
									</TABLE>-->
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="40%" class="Heading"><bean:message key="selectPanHeader" />
							<bean:write name="rpAllocationForm" property="danNo"/></TD>
							<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
						</TR>
						<TR>
							<TD colspan="4" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
						</TR>
						</TABLE>
<!--									<html:hidden property="danNo" name="rpAllocationForm" />
									<html:hidden property="bankId" name="rpAllocationForm"/>
									<html:hidden property="zoneId" name="rpAllocationForm"/>
									<html:hidden property="branchId" name="rpAllocationForm"/>
	-->							</TD>
							</TR>
							<TR>
								<TD align="left" valign="top" class="ColumnBackground">
									<bean:message key="cgpan" />
								</TD>
								<TD align="left" valign="top" class="ColumnBackground">
									<bean:message key="unitName" />
								</TD>
								<TD align="left" valign="top" class="ColumnBackground">
									<bean:message key="guarCoverAmount" />
								</TD>
								<TD align="left" valign="top" class="ColumnBackground">
									<bean:message key="amountDue" />
								</TD>
								<TD align="left" valign="top" class="ColumnBackground">
									<bean:message key="firstDisbursementDate" />
								</TD>
								<TD align="left" valign="top" class="ColumnBackground">
									<bean:message key="pay" />
								</TD>
								<TD align="left" valign="top" class="ColumnBackground">
									<bean:message key="reasons" />
								</TD>
								<TD align="left" valign="top" class="ColumnBackground">
									<bean:message key="newDanId" />
								</TD>
							</TR>
							<%
								String cgpanKey=null;
								String reasonsKey=null;
								String value="";
							%>
							<html:hidden property="danNo" name="rpAllocationForm"/>
							<bean:define id="danId" name="rpAllocationForm" property="danNo"/>
							<logic:iterate id="object" name="rpAllocationForm" property="panDetails">
							<%
								//java.util.Map.Entry entry=(java.util.Map.Entry)object;
								AllocationDetail allocationDetail =(AllocationDetail)object;//entry.getValue();
								String strDanId = (String)danId;
								if (strDanId.indexOf(".")>0)
								{
									strDanId=strDanId.replace('.', '_');
								}
								cgpanKey="cgpan("+strDanId+"-"+allocationDetail.getCgpan()+")";
								reasonsKey="notAllocatedReason("+strDanId+"-"+allocationDetail.getCgpan()+")";
								String newDanId="";
								if (allocationDetail.getNewDanId()!=null)
								{
									newDanId=allocationDetail.getNewDanId();
								}
							%>
								
							<TR>
							<%
							name="cgpans("+strDanId+"-"+allocationDetail.getCgpan()+")";
							value=danId+ "-" + allocationDetail.getCgpan();
							%>
							<html:hidden property="<%=name%>" alt="DAN number" name="rpAllocationForm" value="<%=value%>"/>
								<TD align="left" valign="top" class="TableData">
									<%=allocationDetail.getCgpan()%>
								</TD>
								<TD align="left" valign="top" class="TableData">
									<%=allocationDetail.getNameOfUnit()%>
								</TD>
								<TD align="left" valign="top" class="TableData">
									<%
									double amt=allocationDetail.getGuarCoverAmount();
									String strAmt = df.format(amt);
									%>
									<%=allocationDetail.getGuarCoverAmount()%>
								</TD>
								<TD align="left" valign="top" class="TableData">
									<%if (allocationDetail.getAmountDue()==0)
									{%>
									-
									<%}else{%>
									<%=allocationDetail.getAmountDue()%>
									<%}%>
								</TD>
								<TD align="left" valign="top" class="TableData">
									<%
							name="firstDisbursementDates("+strDanId+"-"+allocationDetail.getCgpan()+")";
//							name = "firstDisbursementDates(key-"+i+")";
									%>
									<%if((allocationDetail.getCgpan()).substring(allocationDetail.getCgpan().length()-2,allocationDetail.getCgpan().length()).equals("TC"))
									{
										if(allocationDetail.getFirstDisbursementDate()==null) {
											if (!newDanId.equals("") || allocationDetail.getAmountDue()==0)
											{
											%>

											<html:text property="<%=name%>" alt="First Disbursement Date" name="rpAllocationForm" maxlength="10" disabled="true"/>
										<%}
											else
											{
										%>
										<html:text property="<%=name%>" alt="First Disbursement Date" name="rpAllocationForm" maxlength="10"/>
										<%}
											} else {
											fDisbDate = dateFormat.format(allocationDetail.getFirstDisbursementDate());
										%>
											<html:text property="<%=name%>" alt="First Disbursement Date" name="rpAllocationForm" value="<%=fDisbDate%>" disabled="true"/>

											<html:hidden property="<%=name%>" alt="First Disbursement Date" name="rpAllocationForm" value="<%=fDisbDate%>"/>
<%--											<%=fDisbDate%>--%>
										<%}}%>
								</TD>
<%--								<TD align="left" valign="top" class="TableData">
									<%name="allocatedFlags(key-"+i+")";
									allocate = allocationDetail.getAllocatedFlag() ;
									if(allocate.equals("Y")) {
										%>
										<html:checkbox name="rpAllocationForm" property="<%=cgpanKey%>" value="<%=allocationDetail.getCgpan()%>"/>
									<%} else {%>

										<html:checkbox name="rpAllocationForm" property="<%=cgpanKey%>" value="<%=allocationDetail.getCgpan()%>"/>
									<%}%>
									<html:hidden property="<%=name%>" alt="allocated" name="rpAllocationForm"/>
								</TD>--%>
								<TD align="left" valign="top" class="tableData">
									<%
									name="allocatedFlags("+strDanId+"-"+allocationDetail.getCgpan()+")";
									%>
									<%
									if (newDanId.equals("") &&  allocationDetail.getAmountDue()>0)
									{
									%>
									<html:checkbox property="<%=name%>" value="Y" name="rpAllocationForm"/>
									<%
									}
									else if (!newDanId.equals("") || allocationDetail.getAmountDue()==0)
									{
									%>
									<html:checkbox property="<%=name%>" value="Y" name="rpAllocationForm" disabled="true"/>
									<%}%>
								</TD>
								<TD align="left" valign="top" class="tableData">
									<%name="notAllocatedReasons("+strDanId+"-"+allocationDetail.getCgpan()+")";%>
									<%
										if (!newDanId.equals("") || allocationDetail.getAmountDue()==0)
										{
									%>
									<html:textarea property="<%=name%>" rows="4" cols="40" name="rpAllocationForm" disabled="true"/>
									<%}else{%>
									<html:textarea property="<%=name%>" rows="4" cols="40" name="rpAllocationForm" />
									<%}%>
								</TD>
								<TD align="left" valign="top" class="TableData">
								<%name="newDanIds("+strDanId+"-"+allocationDetail.getCgpan()+")";%>
									<%=newDanId%>
									<html:hidden property="<%=name%>" alt="New Dan Id" name="rpAllocationForm" value="<%=newDanId%>"/>
								</TD>
							</TR>
						<%++i;%>
						</logic:iterate>
							<logic:iterate id="object" name="rpAllocationForm" property="allocatedPanDetails">
							<%
								//java.util.Map.Entry entry=(java.util.Map.Entry)object;
								AllocationDetail allocationDetail =(AllocationDetail)object;//entry.getValue();
							%>
								
							<TR>	
								<TD align="left" valign="top" class="TableData">
									<%=allocationDetail.getCgpan()%>
								</TD>
								<TD align="left" valign="top" class="TableData">
									<%=allocationDetail.getNameOfUnit()%>
								</TD>
								<TD align="left" valign="top" class="TableData">
									<%=allocationDetail.getGuarCoverAmount()%>
								</TD>
								<TD align="left" valign="top" class="TableData">
									<%=allocationDetail.getAmountDue()%>
								</TD>
								<TD align="left" valign="top" class="TableData">
										<%
										fDisbDate = "";
										if (allocationDetail.getFirstDisbursementDate() != null)
										{
											fDisbDate = dateFormat.format(allocationDetail.getFirstDisbursementDate());
										}
										%>
										<%=fDisbDate%>
								</TD>
								<TD align="left" valign="top" class="TableData">
									<%
									allocate = allocationDetail.getAllocatedFlag() ;
									if(allocate.equals("Y")) {%>
										&nbsp;&nbsp;<%=allocate%>
									<%}%>
								</TD>
								<TD align="left" valign="top" class="tableData">
								</TD>
								<TD align="left" valign="top" class="TableData">
								</TD>
							</TR>
						<%++i;%>
						</logic:iterate>
						<TR>
							<TD align="center" valign="baseline" colspan="8">
								<DIV align="center">
									<A href="javascript:submitForm('submitSFPANPayments.do?method=submitSFPANPayments')">
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
	<%session.removeAttribute(danNumber.getDanNo()) ;%>
		</TABLE>
	</html:form>
</body>
