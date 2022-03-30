<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","correspondenceReportInput.do?method=correspondenceReportInput");%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />

        <html:form action="correspondenceReport.do?method=correspondenceReport" method="POST" enctype="multipart/form-data">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/ReportsHeading.gif" width="121" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
			<DIV align="right">			
			<A HREF="securitizationReportHelp.do?method=securitizationReportHelp">
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
										  &nbsp;<bean:message key="from" /> 
										  </DIV>
									</TD>
									   <TD  align="left" valign="center" class="ColumnBackground">
										  <DIV align="left">
										  <html:text property="dateOfTheDocument26" maxlength="10"   size="20"  alt="Reference" name="rsForm"/>
										  <IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rsForm.dateOfTheDocument26')" align="center">
										  <DIV align="left">
									  </TD>
									  <TD align="left" valign="top" class="ColumnBackground">
									<font color="#FF0000" size="2">*</font>
										  &nbsp;<bean:message key="to" /> 
										  
									</TD>
									    <TD  align="left" valign="center" class="ColumnBackground">
										  <DIV align="left">
										  <html:text property="dateOfTheDocument27" maxlength="10"  size="20"  alt="Reference" name="rsForm"/>
										  <IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rsForm.dateOfTheDocument27')" align="center">
										  <DIV align="left">
									  </TD>
									  </TR>
                                                                          <TR>
                                                                                <TD colspan="4" align="left" valign="top" class="ColumnBackground">
                                                                                <DIV align="center">
                                                                                <font color="#FF0000" size="2">*</font>&nbsp;
                                                                                               <input type="radio" name="rsForm" value="inwardDate" id="inwardDate"/>&nbsp; Inward Date&nbsp;&nbsp;
												<input type="radio" name="rsForm" value="ltrDate" id="ltrDate"/>Letter Date
                                                                                                 <input type="hidden" name="condition" id="condition" />
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
							        <A href="#" onclick="showInwardReport();">
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
    
    <script type="text/javascript" >
           function showInwardReport(){
                var inwardDate = document.getElementById("inwardDate");
                var ltrDate = document.getElementById("ltrDate");
				
                var condition;
				
                            if(inwardDate.checked){                                    
                                condition = 'approvedDateWiseReport';
                    document.getElementById("condition").value = 'inwardDateWiseReport';
                            }else
                            if(ltrDate.checked){                                    
                                condition = 'paymentDateWiseReport';
                    document.getElementById("condition").value = 'ltrDateWiseReport';
                            }else{
                                alert("please select atleast one condition");
                                return false;
                            }	
if(document.forms[0].dateOfTheDocument27.value =="" || document.forms[0].dateOfTheDocument27.value == null)	{
				alert("please enter to date");
				return false;
}						
                        document.rsForm.target ="_self";
                        document.rsForm.method="POST";
                        document.rsForm.action="correspondenceReport.do?method=correspondenceReport";
                        document.rsForm.submit();
           }
    </script>
</TABLE>

