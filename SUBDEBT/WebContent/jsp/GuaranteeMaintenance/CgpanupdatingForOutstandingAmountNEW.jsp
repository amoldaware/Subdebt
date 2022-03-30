<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>

<% session.setAttribute("CurrentPage","flaggingWctlRequestDetails.do?method=flaggingWctlRequestDetails");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%
String focusField="";
%>
<script type="text/javascript">

function submitclaimform(action)
{


	


	
	if(document.forms[0].closureDate.value=="" || document.forms[0].closureDate.value==null )
	{
		alert("please enter nameOfOfficial ");
		document.getElementById('closureDate').focus();

	}

	if(document.forms[0].rescheduledt.value=="" || document.forms[0].rescheduledt.value==null )
	{
		alert("please enter designationOfOfficial ");
		document.getElementById('rescheduledt').focus();

	}
	if(document.forms[0].select.value=="" || document.forms[0].select.value==null )
	{
		alert("please select checkbox ");
		document.getElementById('select').focus();

	}



	


	
	document.forms[0].action=action;
	document.forms[0].target="_self";
	document.forms[0].method="POST";
	document.forms[0].submit();

	


	
}

</script>
<HTML>
	<BODY>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="flaggingWctlRequestDetails.do?method=flaggingWctlRequestDetails" method="POST" enctype="multipart/form-data" focus="<%=focusField%>">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/GuaranteeMaintenanceHeading.gif"></TD>
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
												<TD width="31%" class="Heading"><bean:message key="modifyBorrowerHeader"/></TD>
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
						
             <logic:equal property="bankIdForClosure" value="0000" name="gmPeriodicInfoForm">		
								<TR align="left" valign="top">
								
                  <TD align="left" valign="top" class="ColumnBackground"></font><bean:message key="cgpan"/>
									</TD>
									<TD align="left" valign="top" class="TableData">
									 <bean:write property="cgpanForClosure" name="gmPeriodicInfoForm"/>
									</TD>
									   <TD align="left" valign="top" class="ColumnBackground"> <font color="#FF0000" size="2"></font>SSI UNIT NAME</TD>
									<TD align="left" valign="top" class="TableData">
									 <bean:write property="applicationReport.ssiUnitName" name="gmPeriodicInfoForm"/>
									</TD>
								</TR>
						</logic:equal>
					
              <TR>                								
								
								</TR>
								
								<TR>                								
								  <TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>Reschedulement date
									</TD>
									<TD align="left" valign="top" class="TableData" colspan="4">
										<html:text property="closureDate" size="20" name="gmPeriodicInfoForm" maxlength ="10" />
										  <IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('gmPeriodicInfoForm.closureDate')" align="center">
									</TD>
								</TR>

								<TR>                								
								  <TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>MLI Request Date
									</TD>
									<TD align="left" valign="top" class="TableData" colspan="4">
										<html:text property="rescheduledt" size="20" name="gmPeriodicInfoForm" maxlength ="10" />
										  <IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('gmPeriodicInfoForm.rescheduledt')" align="center">
									</TD>
								</TR>
               
								<TR align="left" valign="top">
									<TD align="left" valign="top"   class="ColumnBackground"><font color="#FF0000" size="2">*</font>Conversion of WC to WCTL
									</TD>
									<TD align="left" valign="top" class="TableData" colspan="4">
              <!--    <html:textarea cols="20" rows="2" property="closureRemarks"   name="gmPeriodicInfoForm" >  </html:textarea> -->
								  <!-- <html:text property="closureRemarks"  size="100" alt="closure Remarks" name="gmPeriodicInfoForm"/>-->
								<html:checkbox property="select" alt="Close" name="gmPeriodicInfoForm" />
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
								
									<A href="javascript:submitclaimform('flaggingWctlSave.do?method=flaggingWctlSave')">
									<IMG src="images/OK.gif" alt="OK" width="49" height="37" border="0"></A>
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
	</BODY>
</HTML>