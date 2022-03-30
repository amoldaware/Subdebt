 <%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","ClaimOtsReport.do?method=getOtsReport");%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="ClaimOtsReport.do?method=getOtsReportDetails" method="POST" enctype="multipart/form-data">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/ReportsHeading.gif" width="121"  height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
			<DIV align="right">			
			<A HREF="otsReportHelp.do?method=otsReportHelp">
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
									<TD colspan="8"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="22%" class="Heading"><bean:message key="danReportHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>

									
									<TR>
									<TD align="center" valign="top" class="ColumnBackground">
                   <DIV align="center">
										  &nbsp;<bean:message key="fromDate"/> 
                      </DIV>
									</TD>
									  <TD  align="left" valign="center" class="TableData">
										  <DIV align="left">
										  <html:text property="cp_ots_fromDate" size="20" maxlength="10" alt="Reference" name="cpRecoveryOTS" />
										  <IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('cpRecoveryOTS.cp_ots_fromDate')" align="center">
										  <DIV align="left">
									  </TD>

									<TD align="center" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">
                  <DIV align="center">
                  *</font>
                   &nbsp;<bean:message key="toDate"/> 
                   </DIV>
									</TD>
									  <TD  align="left" valign="center" class="TableData">
										  <DIV align="left">
										  <html:text property="cp_ots_toDate" size="20" maxlength="10" alt="Reference" name="cpRecoveryOTS"/>
										  <IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('cpRecoveryOTS.cp_ots_toDate')" align="center">
										  <DIV align="left">
									  </TD>
									  </TR>
                     <TR>
									<TD align="left" valign="top" class="ColumnBackground">
									<DIV align="center">
										  &nbsp;Member Id
										  </DIV>
									</TD>
									   <TD  align="left" valign="center" class="TableData">
									   <div align="left">
                      <html:text property="cp_ots_enterMember" size="20" maxlength="12" alt="Reference" name="cpRecoveryOTS" />
                       </div>
                      </TD>
									<TD align="left" valign="top" class="ColumnBackground"><font  color="#FF0000" size="2"></font>
									<DIV align="center">
										  &nbsp;SSI Unit Name
                      </DIV>
									</TD>

									    <TD  align="left" valign="center" class="TableData">
										  <DIV align="left">
                       <html:text property="cp_ots_unitName" size="20" maxlength="12" alt="Reference" name="cpRecoveryOTS" />
											  </DIV >
									  </TD>
									  </TR>
                    <TR>
									<TD align="left" valign="top" class="ColumnBackground">
									<DIV align="left">
									
										  </DIV>
									</TD>
									  
									    <TD  align="left" valign="center" class="TableData">
                      </TD>
									  </TR>
                   
                     <tr>
                     <TD  align="left" valign="center" class="TableData">
                      </TD>	
                     <TD  align="left" valign="center" class="TableData">
	                      <div align="center">
                     <html:radio name="cpRecoveryOTS" value="AP" property="clm_decision" disabled="false"  ></html:radio>
                   Approved
                       </div>
                      </TD>
										  <TD align="left" valign="top" class="ColumnBackground"><font  color="#FF0000" size="2"></font>
                      <div align="center">
                      <html:radio name="cpRecoveryOTS" value="RE" property="clm_decision" disabled="false"></html:radio>
                    Rejected
                     </div>
  									</TD>
									     <TD align="left" valign="top" class="ColumnBackground"><font  color="#FF0000" size="2"></font>
                      <div align="center">
                      <html:radio name="cpRecoveryOTS" value="HO" property="clm_decision" disabled="false"></html:radio>
                     Hold
                     </div>
  									</TD>
									    							   
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
							       <A href="javascript:submitForm('ClaimOtsReport.do?method=getOtsReportDetails')">
									<IMG src="images/OK.gif" alt="OK" width="49" height="37" border="0"></A>
								<A href="javascript:document.cpRecoveryOTS.reset()">
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

