<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.cgtsi.receiptspayments.DANSummary"%>
<%@ page import="com.cgtsi.receiptspayments.AllocationDetail"%>
<%@ page import="com.cgtsi.actionform.RPActionForm"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@ page import="java.text.DecimalFormat"%>

<%@ page import="com.cgtsi.claim.ClaimConstants"%>


<%

String thiskey = "";
	session.setAttribute("CurrentPage","gfallocatePayments.do?method=getPendingGFDANs");

%>
<% 

String sdanDate;
SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
String allocate="N" ;
%>

<script type="text/javascript">
	window.onload= function(){
		document.getElementById("msg").focus();
	}
</script>
<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form  name = "rpAllocationForm" type="com.cgtsi.actionform.RPActionForm" action="gfallocatePayments.do?method=submitDANPayments" method="POST" >
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/ReceiptsPaymentsHeading.gif" width="121" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			
			<TD>
				<TABLE width="100%" border="0" align="left" cellpadding="1" cellspacing="1">
				<TR>
					<TD colspan="13"> 
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="20%" class="Heading"><bean:message key="selectDanHeader" /></TD>
							<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
						</TR>
						<TR>
							<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
						</TR>
						</TABLE>
					</TD>
				</TR>
				
				<html:hidden property="bankId" name="rpAllocationForm"/>
				<html:hidden property="zoneId" name="rpAllocationForm"/>
				<html:hidden property="branchId" name="rpAllocationForm"/>
				<TR>
					<TD align="left" valign="top" class="ColumnBackground" colspan="8">
						<bean:message key="memberId" />
					</TD>
					<TD align="left" valign="top" class="tableData" colspan="8">
						<bean:write name="rpAllocationForm" property="memberId"/>
					</TD>
				</TR>
				<TR>
					<TD align="left" valign="top" class="ColumnBackground">
						<bean:message key="sNo" />
					</TD>
                                         
					<TD align="left" valign="top" class="ColumnBackground">
						<bean:message key="dan" />
					</TD>

                                        <TD align="left" valign="top" class="ColumnBackground">
						<bean:message key="cgpan" />
					</TD>
                                        <TD align="left" valign="top" class="ColumnBackground">
						<bean:message key="unitName" />
					</TD>
					<TD align="left" valign="top" class="ColumnBackground">
						<bean:message key="noOfCGPANs" />
					</TD>
					<TD align="left" valign="top" class="ColumnBackground">
						<bean:message key="danDate" />
					</TD>
					<TD align="left" valign="top" class="ColumnBackground">
						<bean:message key="amountDue" />
					</TD>
					<TD align="left" valign="top" class="ColumnBackground">
						<bean:message key="amountPaid" />
					</TD>
					<TD align="left" valign="top" class="ColumnBackground">
						<bean:message key="balance" />
					</TD>
					<TD align="left" valign="top" class="ColumnBackground">
						<bean:message key="amountBeingPaid" />
					</TD>
                                        
                                        <TD align="left" valign="top" class="ColumnBackground">
						Included Service Tax
					 </TD>
					 <TD width="10%" align="left" valign="top" class="ColumnBackground">
						Included Ecess
					 </TD>
					 <TD align="left" valign="top" class="ColumnBackground">
						Included S & H Ecess
					</TD>
                                        
					<TD align="left" valign="top" class="ColumnBackground">
						<bean:message key="pay" />100
					</TD>
					<TD align="left" valign="top" class="ColumnBackground">
						<bean:message key="shortDanReasons" />
					</TD>
				</TR>	
				<%int i = 0;

				DecimalFormat df= new DecimalFormat("######################.##");
				df.setDecimalSeparatorAlwaysShown(false);

				int shortDanIndex = 0;
				String name="";
				String danNo = "";
                                String cgpan="";
                                String ssiname="";
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
				<html:hidden property="danNo" name="rpAllocationForm"/>
				<logic:iterate id="object" name="rpAllocationForm" property="danSummaries">
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
				 checkboxKey="allocatedFlagnew("+danNoKey+")";
				amountBeingPaid = 0.0;
				allocate = "N" ;

				//amountToBePaid = amountDue - amountPaid;
				if (amountPaid!=amountDue || amountDue==0)
				{
				%>
				<TR>
					<TD align="left" valign="top" class="tableData"><%=(i+1)%></TD>
                                         
					<TD align="left" valign="top" class="tableData">
							<!--<a href="javascript:setHiddenFieldValue('danNo', '<%=danNo%>', 'gfallocatePayments.do?method=getGFPANDetails');">-->
						<%=danNo%>
						
					</TD>
                                        <TD align="left" valign="top" class="tableData">
							<%=cgpan%>
					</TD>
                                        <TD align="left" valign="top" class="tableData">
							<%=ssiname%>
					</TD>
					<TD align="left" valign="top" class="tableData">
							<%=noOfCGPANs%>
					</TD>
					<TD align="left" valign="top" class="tableData">
						<%sdanDate = dateFormat.format(danDate);%>
						<%=sdanDate%>
					</TD>
					<TD align="left" valign="top" class="tableData">
						<%=amountDueValue%>
					</TD>
					<TD align="left" valign="top" class="tableData">
						<%=amountPaid%>
					</TD>
					<TD align="left" valign="top" class="tableData">
						<%balance = amountDue - amountPaid;
						String balanceValue=df.format(balance);%>
						<%=balanceValue%>	
					</TD>
					<TD align="left" valign="top" class="tableData">
					
						<%=amountBeingPaid%>
						
						
					</TD>
                                        
                                       <TD align="left" valign="top" class="tableData">
						<%=df.format(danSummary.getInclSTaxAmnt())%>
					</TD>
                                        <TD align="left" valign="top" class="tableData">
						<%=df.format(danSummary.getInclECESSAmnt())%>
					</TD>
                                        <TD align="left" valign="top" class="tableData">
						<%=df.format(danSummary.getInclHECESSAmnt())%>
					</TD>    
                                        
					<TD align="left" valign="top" class="tableData">

<% 
                        thiskey=danNoKey+ClaimConstants.CLM_DELIMITER1+balanceValue;
			                  checkboxKey="appropriatedFlags("+thiskey+")";
                       // checkboxKeyId = "depositedFlags("+inwardId+")";
                        %>







							
						
							
							<html:checkbox name="rpAllocationForm" property="<%=checkboxKey%>" value="Y"/>
						
						
						 <!--html:checkbox property="<%=name%>" alt="Pay" name="rpAllocationForm" value="Y"/-->
					</TD>
					<TD align="left" valign="top" class="tableData">
						
							
						
					</TD>
				</TR>

				<%
					++i;
				}
				%>
				</logic:iterate>
                     
					<tr>
						<td style="font:15px;color:green;" id="msg" colspan="18"><b>Note:</b>
							The amounts mentioned under Service Tax, Education Cess and Higher education Cess are
							<b> already included in balance amount in Rs.</b>and are displayed for your information. 
							Users are requested to <b>make payment of amount mentioned under balance amount in Rs. only.</b>
						</td>
					</tr> 
					 
                                
                                <TR>
						<TD align="left" valign="top" class="SubHeading" colspan="18">
							Service Category : Other Taxable Services - Other Than The 119 LISTED
						</TD>
					</TR>
					<TR >
						
                        <td valign="top" class="ColumnBackground" colspan="4"><div align="center">&nbsp;Service Tax Number:</div></td>
						
						<TD valign="top" class="ColumnBackground" colspan="5"><div align="leftt">	&nbsp;AAATC2613DSD001</div></td>
						
						<td valign="top" class="ColumnBackground" colspan="4"><div align="center">	&nbsp;PAN Number:</div></td>
										
						<td valign="top" class="ColumnBackground" colspan="5"><div align="leftt">	&nbsp;AAATC2613D</div></TD>
						
					</TR>
                                
                                
				<TR>
					<TD align="center" valign="baseline" colspan="10">
					<DIV align="center">
				<!-- 	<A href="javascript:submitForm('gfallocatePayments.do?method=submitDANPayments')">
							<IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A> -->
               <A onclick ="" href="javascript:if(checkbox_checker()) submitForm('gfallocatePayments.do?method=submitDANPayments')">
          		  <IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>
						<A href="javascript:document.rpAllocationForm.reset()">
							<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>						
								<A href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>">
								<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>
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
			<TD background="images/TableBackground2.gif">
				&nbsp;
			</TD>
			<TD width="20" align="left" valign="top">
				<IMG src="images/TableRightBottom1.gif" width="23" height="15">
			</TD>
		</TR>
</html:form>
</TABLE>