<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.cgtsi.receiptspayments.DANSummary"%>
<%@ page import="com.cgtsi.receiptspayments.AllocationDetail"%>
<%@ page import="com.cgtsi.actionform.RPActionForm"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@ include file="/jsp/SetMenuInfo.jsp"%>
<%@ page import="java.text.DecimalFormat"%>


<%
	session.setAttribute("CurrentPage","appropriateallocatePayments.do?method=getPendingSFDANs");
%>
<% 
String sdanDate;
SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
String allocate="N" ;
%>
<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form
		action="appropriateallocatePayments.do?method=submitSFDANPayments"
		method="POST">
		<TR>
			<TD width="20" align="right" valign="bottom"><IMG
				src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG
				src="images/ReceiptsPaymentsHeading.gif" width="121" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG
				src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>

			<TD>
			<TABLE width="100%" border="0" align="left" cellpadding="1"
				cellspacing="1">
				<TR>
					<TD colspan="13">
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="20%" class="Heading"><bean:message
								key="selectDanHeader" /></TD>
							<TD><IMG src="images/TriangleSubhead.gif" width="19"
								height="19"></TD>
						</TR>
						<TR>
							<TD colspan="3" class="Heading"><IMG src="images/Clear.gif"
								width="5" height="5"></TD>
						</TR>
					</TABLE>
					</TD>
				</TR>

				<html:hidden property="bankId" name="rpAllocationForm" />
				<html:hidden property="zoneId" name="rpAllocationForm" />
				<html:hidden property="branchId" name="rpAllocationForm" />
				<TR>
					<TD align="left" valign="top" class="ColumnBackground" colspan="5">
					<bean:message key="memberId" /></TD>
					<TD align="left" valign="top" class="tableData" colspan="5"><bean:write
						name="rpAllocationForm" property="memberId" /></TD>
				</TR>
				<TR>
					<TD align="left" valign="top" class="ColumnBackground"><bean:message
						key="sNo" /></TD>
					<TD align="left" valign="top" class="ColumnBackground">Branch
					Name</TD>

					<TD align="left" valign="top" class="ColumnBackground"><bean:message
						key="dan" /></TD>
					<TD align="left" valign="top" class="ColumnBackground"><bean:message
						key="cgpan" /></TD>
					<TD align="left" valign="top" class="ColumnBackground"><bean:message
						key="unitName" /></TD>

					<TD align="left" valign="top" class="ColumnBackground"><bean:message
						key="noOfCGPANs" /></TD>
					<TD align="left" valign="top" class="ColumnBackground"><bean:message
						key="danDate" /></TD>
					<TD align="left" valign="top" class="ColumnBackground"><bean:message
						key="amountDue" /></TD>
					<TD align="left" valign="top" class="ColumnBackground"><bean:message
						key="amountPaid" /></TD>
					<TD align="left" valign="top" class="ColumnBackground"><bean:message
						key="balance" /></TD>
					<TD align="left" valign="top" class="ColumnBackground"><bean:message
						key="amountBeingPaid" /></TD>
					<TD align="left" valign="top" class="ColumnBackground"><bean:message
						key="pay" /></TD>
					<TD align="left" valign="top" class="ColumnBackground"><bean:message
						key="shortDanReasons" /></TD>
				</TR>
				<%int i = 0;

				DecimalFormat df= new DecimalFormat("######################.##");
				df.setDecimalSeparatorAlwaysShown(false);

				int shortDanIndex = 0;
				String name="";
				String danNo = "";
        String cgpan = "";
        String ssiname = "";
			
				String allocated = "" ;
				int noOfCGPANs = 0;
				Date danDate;
				double amountDue = 0.0;
				double amountPaid = 0.0;
				double amountBeingPaid = 0.0 ;
				double balance = 0.0;

				ArrayList panDetails ;
				AllocationDetail allocationDetail ;
				
				String checkboxKey=null;
				%>
				<html:hidden property="danNo" name="rpAllocationForm" />
				<logic:iterate id="object" name="rpAllocationForm"
					property="danSummaries">
					<%
				com.cgtsi.receiptspayments.DANSummary danSummary =(com.cgtsi.receiptspayments.DANSummary)object;
				danNo = danSummary.getDanId();
        cgpan = danSummary.getCgpan();
        ssiname = danSummary.getUnitname();
      
				noOfCGPANs = danSummary.getNoOfCGPANs();
				danDate = danSummary.getDanDate();
				amountDue = danSummary.getAmountDue();
				String amountDueValue=df.format(amountDue);
				amountPaid = danSummary.getAmountPaid();
				String danNoKey=danNo.replace('.', '_');
				checkboxKey="allocatedFlag("+danNoKey+")";
				amountBeingPaid = 0.0;
				allocate = "N" ;

				//amountToBePaid = amountDue - amountPaid;
				if (amountPaid!=amountDue || amountDue==0)
				{
				%>
					<TR>
						<TD align="left" valign="top" class="tableData"><%=(i+1)%></TD>
						<TD align="left" valign="top" class="tableData"><%=danSummary.getBranchName()%>
						</TD>

						<TD align="left" valign="top" class="tableData">
						<a href="javascript:setHiddenFieldValue('danNo', '<%=danNo%>', 'appropriateallocatePayments.do?method=getSFPANDetails');">
						<%=danNo%> <%name="danIds(key-"+i+")";%></a> <html:hidden
							property="<%=name%>" alt="DAN number" name="rpAllocationForm"
							value="<%=danNo%>" /></TD>
						<TD align="left" valign="top" class="tableData"><%=cgpan%></TD>
						<TD align="left" valign="top" class="tableData"><%=ssiname%>
						</TD>

						<TD align="left" valign="top" class="tableData"><%=noOfCGPANs%>
						</TD>
						<TD align="left" valign="top" class="tableData">
						<%sdanDate = dateFormat.format(danDate);%> <%=sdanDate%></TD>
						<TD align="left" valign="top" class="tableData"><%=amountDueValue%>
						</TD>
						<TD align="left" valign="top" class="tableData"><%=amountPaid%>
						</TD>
						<TD align="left" valign="top" class="tableData">
						<%balance = amountDue - amountPaid;
						String balanceValue=df.format(balance);%> <%=balanceValue%></TD>
						<TD align="left" valign="top" class="tableData"><logic:iterate
							id="danCgpanInfo" name="rpAllocationForm"
							property="danPanDetails">
							<%
						//System.out.println(danCgpanInfo.getClass());
						java.util.Map.Entry entry=(java.util.Map.Entry)danCgpanInfo;
						ArrayList panDetail=null;
						
						if(danNo.equals(entry.getKey()))
						{
							//System.out.println("dan id "+danNo);
							
							panDetail=(ArrayList)entry.getValue();
							
							
							if(panDetail==null) 
							{
							//System.out.println("PAN detail null");
								amountBeingPaid = 0.0 ;
							}
							else 
							{
								int arSize = panDetail.size() ;
								//System.out.println("arSize "+arSize);
								for(int arIndex=0; arIndex<arSize; ++arIndex) 
								{
									allocationDetail = (AllocationDetail)panDetail.get(arIndex) ;
									allocated = allocationDetail.getAllocatedFlag() ;
									//System.out.println("allocated "+allocated);
									if(allocated.equals("Y")) 
									{
										allocate = "Y" ;
										amountBeingPaid += allocationDetail.getAmountDue() ;
										amountBeingPaid += allocationDetail.getPenalty() ;
										
										//System.out.println("amountBeingPaid "+amountBeingPaid);
									}
								}
							}
						}
					 %>
						</logic:iterate> <%=amountBeingPaid%> <%name="amountBeingPaid(key-"+i+")";%> <html:hidden
							property="<%=name%>" alt="DAN number" name="rpAllocationForm"
							value="<%=String.valueOf(amountBeingPaid)%>" /></TD>
						<TD align="left" valign="top" class="tableData">
						<%name="allocatedFlags(key-"+i+")";
						if(allocate.equals("Y") || amountDue==0) {%> <html:checkbox
							name="rpAllocationForm" property="<%=checkboxKey%>"
							value="<%=danNo%>" disabled="true" /> <%} else {%> <html:checkbox
							name="rpAllocationForm" property="<%=checkboxKey%>"
							value="<%=danNo%>" /> <%}%> <html:hidden property="<%=name%>"
							alt="DAN number" name="rpAllocationForm" /> <!--html:checkbox property="<%=name%>" alt="Pay" name="rpAllocationForm" value="Y"/-->
						</TD>
						<TD align="left" valign="top" class="tableData">
						<%name="remarks(key-"+i+")";%> <%if(noOfCGPANs==0.0) {%> <html:textarea
							property="<%=name%>" alt="Remarks" rows="4" cols="40"
							name="rpAllocationForm" value="" /> <%} else {%> <html:hidden
							property="<%=name%>" alt="Remarks" name="rpAllocationForm"
							value="" /> <%}%>
						</TD>
					</TR>

					<%
					++i;
				}
				%>
				</logic:iterate>
				<TR>
					<TD align="center" valign="baseline" colspan="10">
					<DIV align="center"><A
						href="javascript:submitForm('appropriateallocatePayments.do?method=submitSFDANPayments')">
					<IMG src="images/Save.gif" alt="Save" width="49" height="37"
						border="0"></A> <A
						href="javascript:document.rpAllocationForm.reset()"> <IMG
						src="images/Reset.gif" alt="Cancel" width="49" height="37"
						border="0"></A> <A
						href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>">
					<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37"
						border="0"></A></DIV>
					</TD>
				</TR>
			</TABLE>
			</TD>

			<TD width="20" background="images/TableVerticalRightBG.gif">&nbsp;</TD>

		</TR>

		<TR>
			<TD width="20" align="right" valign="top"><IMG
				src="images/TableLeftBottom1.gif" width="20" height="15"></TD>
			<TD background="images/TableBackground2.gif">&nbsp;</TD>
			<TD width="20" align="left" valign="top"><IMG
				src="images/TableRightBottom1.gif" width="23" height="15"></TD>
		</TR>
	</html:form>
</TABLE>