<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.cgtsi.receiptspayments.DANSummary"%>
<%@ page import="com.cgtsi.receiptspayments.AllocationDetail"%>
<%@ page import="com.cgtsi.actionform.GMActionForm"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@ page import="java.text.DecimalFormat"%>


<%
	session.setAttribute("CurrentPage","getMemberForShiftingrequest.do?method=getShiftingCgpans");
  String tempname="";
%>
<% 

String sdanDate;
SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
String allocate="N" ;
%>

<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form  name = "gmPeriodicInfoForm" type="com.cgtsi.actionform.GMActionForm" action="getMemberForShiftingrequest.do?method=submitCgpanRequests" method="POST" >
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/GuaranteeMaintenanceHeading.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			
			<TD>
				<TABLE width="100%" border="0" align="left" cellpadding="1" cellspacing="1">
				<TR>
					<TD colspan="5"> 
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="20%" class="Heading"><bean:message key="selectDanHeader" /></TD>
							<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
						</TR>
						<TR>
							<TD colspan="5" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
						</TR>
						</TABLE>
					</TD>
				</TR>
				
				<html:hidden property="bankId" name="gmPeriodicInfoForm"/>
				<html:hidden property="zoneId" name="gmPeriodicInfoForm"/>
				<html:hidden property="branchId" name="gmPeriodicInfoForm"/>
				<TR>
					<TD align="left" valign="top" class="ColumnBackground" colspan="3">
						<bean:message key="memberId" />
					</TD>
					<TD align="left" valign="top" class="tableData" colspan="3">
						<bean:write name="gmPeriodicInfoForm" property="memberId"/>
					</TD>
				</TR>
				<TR>
					<TD align="left" valign="top" class="ColumnBackground">
						<bean:message key="sNo" />
					</TD>
           <TD align="left" valign="top" class="ColumnBackground">
										Branch Name
					</TD> 
			<!--		<TD align="left" valign="top" class="ColumnBackground">
						<bean:message key="dan" />
					</TD> -->
          <TD align="left" valign="top" class="ColumnBackground">
						<bean:message key="cgpan" />
					</TD>
          <TD align="left" valign="top" class="ColumnBackground">
						<bean:message key="unitName" />
					</TD>
          <TD align="left" valign="top" class="ColumnBackground">
          CGPAN TO SHIFT MEMBERID
          </TD>
			
				</TR>	
				<%int i = 0;

				DecimalFormat df= new DecimalFormat("######################.##");
				df.setDecimalSeparatorAlwaysShown(false);

				int shortDanIndex = 0;
				String name="";
			//	String danNo = ""; 
        String cgpan = "";
        String ssiname = "";
				String allocated = "" ;
				%>
			<!--	<html:hidden property="danNo" name="gmPeriodicInfoForm"/> -->
        	<html:hidden property="cgpan" name="gmPeriodicInfoForm"/>
				<logic:iterate id="object" name="gmPeriodicInfoForm" property="danSummaries" indexId="index">
				<%
				com.cgtsi.receiptspayments.DANSummary danSummary =(com.cgtsi.receiptspayments.DANSummary)object;
		//		danNo = danSummary.getDanId();
        cgpan = danSummary.getCgpan();
        ssiname = danSummary.getUnitname();
			%>
				<TR>
					 <td align="left" valign="top" class="tableData"><div align="center">&nbsp;<%= Integer.parseInt(index+"")+1%></div></td>
          <TD align="left" valign="top" class="tableData">
          <%=danSummary.getBranchName()%>
          </TD>
			
          <TD align="left" valign="top" class="tableData">&nbsp;<%tempname="clearCgpan("+cgpan+")";%>
						<%=cgpan%>
            
					</TD>
          <TD align="left" valign="top" class="tableData">
						<%=ssiname%>
					</TD>
          <TD align="left" valign="top" class="tableData">
          <%tempname="clearCgpan("+cgpan+")";%>
         <html:select property="<%=tempname%>" name="gmPeriodicInfoForm">
														<html:option value="">Select</html:option>
														<html:options name="gmPeriodicInfoForm" property="memberList"/>
													</html:select>	
                          </TD>               
					</TR>
         <%
					++i;
			   %>
				</logic:iterate>
				<TR>
					<TD align="center" valign="baseline" colspan="10">
					<DIV align="center">
				 	<A href="javascript:submitForm('getMemberForShiftingrequest.do?method=submitCgpanRequests')">
							<IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A> 
        		<A href="javascript:document.gmPeriodicInfoForm.reset()">
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