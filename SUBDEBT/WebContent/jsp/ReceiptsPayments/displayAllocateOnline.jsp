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
<%session.setAttribute("CurrentPage","allocatePaymentsAll.do?method=getPendingGFDANsLiveOnline");
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
** Payment Allocation Successfull.
<body>
	<bean:define id="danNumber" name="rpAllocationForm" type="com.cgtsi.actionform.RPActionForm"/> 
	<html:form name="rpAllocationForm" type="org.apache.struts.validator.DynaValidatorActionForm" action="submitASFPANPayments.do?method=submitASFPANPayments" method="POST" enctype="multipart/form-data" focus="<%=focusField%>">
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
					</TD>
							</TR>
				
							  <tr>
              <TD align="left" valign="top" class="ColumnBackground">Payment ID</td>
              <td class="tableData"><bean:write name="rpAllocationForm" property="paymentId"/></td>
            </tr>
            
            <tr>
              <TD align="left" valign="top" class="ColumnBackground">Amount</td>
                <td class="tableData"><bean:write name="rpAllocationForm" property="instrumentAmount" format="00.00"/></td>
            </tr>
           
				 	
						<TR>
							<TD align="center" valign="baseline" colspan="8">
								  <div align="center">          
         <a href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>"><img src="images/OK.gif" width="40" height="37" border="0"></a>
          </div>
								
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

		</TABLE>
	</html:form>
 ** Please go to Initiate Payment module  to Initiate payment for this Payment ID.
</body>
