<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","epcsPaymentReport.do?method=epcsPaymentReport");%>
 <SCRIPT type="text/javascript" LANGUAGE="JavaScript"><!--



 function submitDeclaResubmit(action)
 {
	 var status=true;
	// alert('submitDeclaResubmit'+document.forms[0].dantype[1]);
	 if(document.forms[0].memberId.value=="" || document.forms[0].memberId.value==null )
		{
			alert("please enter memberID ");

			document.getElementById('memberId').focus();
			status=false;
		}
	 
	 if(document.forms[0].dantype[0].checked==false && document.forms[0].dantype[1].checked==false &&  document.forms[0].dantype[2].checked==false &&  document.forms[0].dantype[3].checked==false)
	 {
		 
		 alert("please select dan type option ");

	 		document.getElementById('dantype').focus();
	 		status=false;
	 }
	 
 	if(document.forms[0].checks[0].checked==false && document.forms[0].checks[1].checked==false && document.forms[0].checks[2].checked==false && document.forms[0].checks[3].checked==false ) 
 	{
 	
 		alert("please select dan status option ");

 		document.getElementById('checks').focus();
 		status=false;

 	}

//alert(status);
 	if(status==true)
 	{
	 	document.forms[0].action=action;
	 	document.forms[0].target="_self";
	 	document.forms[0].method="POST";
	 	document.forms[0].submit();
 	}
 }


 
 function funcdisable(id)
 {


	if(id=='A')
	{		
		
	document.forms[0].dateOfTheDocument.disabled = false;
			
	return true;
	}
 	if(id=='B')
 	{		
 	
 		document.forms[0].dateOfTheDocument.disabled = true;
 		document.forms[0].dateOfTheDocument.value = null;

 		
 	return false;
 	}
 }
	</SCRIPT>
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="epcsPaymentReport.do?method=epcsPaymentReport" method="POST" enctype="multipart/form-data">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/ReportsHeading.gif" width="121" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
			<DIV align="right">			
			<A HREF="guaranteeCoverHelp.do?method=guaranteeCoverHelp">
			HELP</A>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
				<tr>
				<TD  align="left" colspan="4"><font size="2"><bold>
				Fields marked as </font><font color="#FF0000" size="2">*</font><font size="2"> are mandatory</bold></font>
				</td>
				</tr>
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="10"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="27%" class="Heading"><bean:message key="danReportHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>

									<TR>
									<TD align="left" valign="top" class="ColumnBackground">
									<DIV align="center">
										  &nbsp;<bean:message key="fromdate" /> 
										  </DIV>
									</TD>
									  <TD  align="left" valign="center" class="TableData">
										  <DIV align="left">
										  <html:text property="dateOfTheDocument" size="20" maxlength="10" alt="Reference" name="rsForm"/>
										  <IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rsForm.dateOfTheDocument')" align="center">
										  <DIV align="left">
									  </TD>

									  <TD align="left" valign="top" class="ColumnBackground">
									<font color="#FF0000" size="2">*</font>
										  &nbsp;<bean:message key="toDate"/> 
										 
									</TD>
									   <TD  align="left" valign="center" class="TableData">
										  <DIV align="left">
										  <html:text property="dateOfTheDocument1" size="20" maxlength="10" alt="Reference" name="rsForm"/>
										  <IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rsForm.dateOfTheDocument1')" align="center">
										  <DIV align="left">
									  </TD>
									  </TR>
									  <tr>
									  <TD align="left" valign="top" class="ColumnBackground">
									<DIV align="center">
										  &nbsp;Member ID
										  </DIV>
									</TD>
									    <TD align="left" valign="center" class="ColumnBackground" colspan=3>
									   <DIV align="left">
										 <html:text property="memberId" size="20"  alt="memberId" name="rsForm"  maxlength="12" onkeypress="return numbersOnly(this, event);" onkeyup="isValidNumber(this);"/>  
										 </DIV>
									  </TD>
									  
									  
									  </tr>

									<TR>
									<TD  align="left" valign="top" class="ColumnBackground">
									<DIV align="center">
										&nbsp;DanStatus
										</DIV>
									</TD>
									<TD colspan="4" align="left" valign="top" class="ColumnBackground">
									<DIV align="center">
									<font color="#FF0000" size="2">*</font>
								    <html:radio name="rsForm" value="R" property="checks"   onclick="funcdisable('A');">Payment Received</html:radio>&nbsp;&nbsp;
                                    <html:radio name="rsForm" value="I" property="checks"  onclick="funcdisable('B');">Payment Intiated But Not Paid</html:radio>
                                    <html:radio name="rsForm" value="N" property="checks"   onclick="funcdisable('c');">Allocated But Payment Not Intiated</html:radio>&nbsp;&nbsp;
                                    <html:radio name="rsForm" value="C" property="checks"  onclick="funcdisable('d');">Cancelled</html:radio>
                                    
									</DIV>
									</TD>
									</TR>
									<tr>
									<TD  align="left" valign="top" class="ColumnBackground">
									<DIV align="center">
									 &nbsp;DanType
									</DIV>
									</TD>
									<TD colspan="4" align="left" valign="top" class="ColumnBackground">
									<DIV align="center">
									<font color="#FF0000" size="2">*</font>
									 <html:radio name="rsForm" value="GF" property="dantype"  onclick="funcdisable('e');">GF</html:radio>
                                      <html:radio name="rsForm" value="AF" property="dantype"  onclick="funcdisable('f');">AF</html:radio>
                                       <html:radio name="rsForm" value="SF" property="dantype"  onclick="funcdisable('g');">SF</html:radio>
                                        <html:radio name="rsForm" value="AF,SF" property="dantype"  onclick="funcdisable('H');">AF+SF</html:radio>
		                               </DIV>
		                              </td>
		                                                          </tr>
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
							        <A href="javascript:submitDeclaResubmit('epcsPaymentReportInput.do?method=epcsPaymentReportInput')">
									<IMG src="images/OK.gif" alt="OK" width="49" height="37" border="0"></A>
								<A href="javascript:document.rsForm.reset()">
									<IMG src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></A>
									
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

