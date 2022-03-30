<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@ page import="com.cgtsi.actionform.RPActionForm"%>
<% session.setAttribute("CurrentPage","modifyAllocatePaymentDetail.do?method=modifyAllocatePaymentDetail");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>

<%
  RPActionForm rpActionForm= (RPActionForm)session.getAttribute("cpTcDetailsForm") ;
  
%>


<script>
function validateInstrument()
{
//alert("Validating Instrument Number Entered");
var instrumentNo = findObj("newInstrumentNo").value;
//alert("instrumentNo"+instrumentNo);
if(instrumentNo.value=""){
alert("Instrument Number is required");
return false;
}
//alert(instrumentNo.length);
if(instrumentNo.length < 6){
alert("Invalid Instrument Number " +instrumentNo +"  , the length of instrument number should be 6");
return false;
}
//alert(instrumentNo.value.length);
//alert("Validating Instrument Number Exited");
return true;
}
</script>

<HTML>
	<BODY>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="modifyAllocatePaymentDetail.do?method=modifyAllocatePaymentDetail" method="POST" enctype="multipart/form-data">
			<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/ReceiptsPaymentsHeading.gif" width="121" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
			<DIV align="right">			
	<!--			<A HREF="javascript:submitForm('helpClosureDetailsFilter.do?method=helpClosureDetailsFilter')">
			    HELP</A> -->
			</DIV>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="4"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="51%" class="Heading">Modify Allocated Payment Id Details</TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>
								</TR>
                <tr>
			<TD  align="left" colspan="4"><font size="2"><bold>
				Fields marked as </font><font color="#FF0000" size="2">*</font><font size="2"> are mandatory</bold></font>
			</td>
		</tr>
						<TR align="left" valign="top">
									<TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font><bean:message key="paymentId"/>
									</TD>
									<TD align="left" valign="top" class="TableData" colspan="4">
										<bean:write property="paymentId" name="rpAllocationForm"/>
									</TD>
         					</TR>
            
              <TR>                								
								  <TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>Existing &nbsp;<bean:message key="instrumentNumber"/>
									</TD>
									<TD align="left" valign="top" class="TableData">
										<bean:write property="instrumentNo" name="rpAllocationForm"/>
                  </TD>
								             								
								  <TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>Existing &nbsp;<bean:message key="instrumentDate"/>
									</TD>
									<TD align="left" valign="top" class="TableData">
										<bean:write property="instrumentDate"  name="rpAllocationForm"/>
										 	</TD>
								</TR>  
                <TR>                								
								  <TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font><bean:message key="payableAt"/>
									</TD>
									<TD align="left" valign="top" class="TableData">
										<html:text property="payableAt" size="20" maxlength="30" name="rpAllocationForm"/>
                  </TD>
								             								
								  <TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font><bean:message key="instrumentAmount"/>
									</TD>
									<TD align="left" valign="top" class="TableData">
										<bean:write property="instrumentAmount"  name="rpAllocationForm"/>
										 	</TD>
								</TR>         
								<TR align="left" valign="top">
                <TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>New &nbsp;<bean:message key="instrumentNumber"/>
									</TD>
									<TD align="left" valign="top" class="TableData">
										<html:text property="newInstrumentNo" size="20" name="rpAllocationForm" maxlength="6" onchange="validateInstrument()" onkeypress="return numbersOnly(this, event)" />
                  </TD>
                  <TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>New &nbsp;<bean:message key="instrumentDate"/>
									</TD>
									<TD align="left" valign="top" class="TableData">
										<html:text property="newInstrumentDt" size="20" name="rpAllocationForm" maxlength ="10" />
										  <IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rpAllocationForm.newInstrumentDt')" align="center">
									</TD>
								</TR> 
                
                


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
								
									<A href="javascript:submitForm('afterModifyAllocatePaymentDetail.do?method=afterModifyAllocatePaymentDetail')">
									<IMG src="images/OK.gif" alt="OK" width="49" height="37" border="0"></A>
									<A href="javascript:document.rpAllocationForm.reset()">
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
	</BODY>
</HTML>