<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","guaranteeCover.do?method=guaranteeCover");%>


<script>

function submitFormQueryStatus(action)
{
	//alert("dfdfdf");

var fromdt=document.forms[0].dateOfTheDocument.value;

//alert(fromdt);
var todate=document.forms[0].dateOfTheDocument1.value;

//alert(todate);

var checkyes=document.forms[0].check1[0].checked;

var checkfalse=document.forms[0].check1[1].checked;

//alert(checkyes);

//alert(checkfalse);


if(fromdt=='' ||  todate=='' )
{
	alert("please select from date or to date");
	 fromdt.focus();
}


if(checkyes=='' &&  checkfalse=='' )
{
	alert("please select status filed");
	checkyes.focus();
}




//alert(fromdt);


//alert(todate);

//alert(fromdt>todate);

//alert(todate>fromdt);


 //if(fromdt>todate)
 //{
	// alert("from date should not be more than to date");
	 //return false;
	// fromdt.focus();
	 
 //}
 


document.forms[0].target ="_self";
document.forms[0].method="POST";
document.forms[0].action=action;
document.forms[0].submit();



	
}


</script>


<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="showQueryStatusReport.do?method=showQueryStatusReport" method="POST" enctype="multipart/form-data">
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
										  <html:text property="dateOfTheDocument" size="20" maxlength="10" alt="Reference" name="adminActionForm"/>
										  <IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('adminActionForm.dateOfTheDocument')" align="center">
										  <DIV align="left">
									  </TD>

									  <TD align="left" valign="top" class="ColumnBackground">
									<font color="#FF0000" size="2">*</font>
										  &nbsp;<bean:message key="toDate"/> 
										 
									</TD>
									   <TD  align="left" valign="center" class="TableData">
										  <DIV align="left">
										  <html:text property="dateOfTheDocument1"   size="20" maxlength="10" alt="Reference" name="adminActionForm"/>
										  <IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('adminActionForm.dateOfTheDocument1')" align="center">
										  <DIV align="left">
									  </TD>
									  </TR>

									<TR>
									<TD  align="left" valign="top" class="ColumnBackground">
									<DIV align="center">
										
										</DIV>
									</TD>
									<TD colspan="20" align="left" valign="top" class="ColumnBackground">
									<DIV align="center">
									<font color="#FF0000" size="2">*</font>
								   <html:radio name="adminActionForm" value="OP" property="check1" >Open</html:radio>&nbsp;&nbsp;
                                    <html:radio name="adminActionForm" value="CL" property="check1" >Closed</html:radio>
									</DIV>
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
							        <A href="javascript:submitFormQueryStatus('showQueryStatusReport.do?method=showQueryStatusReport')">
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

