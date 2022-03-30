<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<% session.setAttribute("CurrentPage","showDayEndProcess.do");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
<html:errors />

<html:form action="dayEndProcess.do" method="POST" >
<table width="680" border="0" cellspacing="0" cellpadding="0" align="center">
<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<td width="713">
			<DIV align="right">			
				<A HREF="javascript:submitForm('helpDayEndProcesses.do?method=helpDayendProcesses')">
				HELP</A>
			</DIV>
			<table width="606" border="0" cellspacing="1" cellpadding="0">
          <tr> 
            <td colspan="2" width="700"><table width="604" border="0" cellspacing="0" cellpadding="0">
												<TD width="31%" class="Heading"><bean:message key="dayEndProcessHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>
								</TR>

				<TR align="left">
					<TD align="left" valign="top" class="ColumnBackground">
						&nbsp;<bean:message key="removeData"/>
					</TD>
					<TD align="left" valign="top" class="TableData">
						<html:multibox name="adminForm" value="removeData" property="dayEndProcesses"/>
					</TD>
				 </TR>

				 <TR align="left">
					<TD align="left" valign="top" class="ColumnBackground">
						&nbsp;<bean:message key="moveData"/>
					</TD>
					<TD align="left" valign="top" class="TableData">
						<html:multibox name="adminForm" value="moveData" property="dayEndProcesses"/>
					</TD>
				 </TR>

				 <!-- <TR align="left">
					<TD align="left" valign="top" class="ColumnBackground">
						&nbsp;<bean:message key="resetPID"/>
					</TD>
					<TD align="left" valign="top" class="TableData">
						<html:multibox name="adminForm" value="resetPID" property="dayEndProcesses"/>
					</TD>
				 </TR> -->

				 <!-- <TR align="left">
					<TD align="left" valign="top" class="ColumnBackground">
						&nbsp;<bean:message key="synchronization"/>
					</TD>
					<TD align="left" valign="top" class="TableData">
						<html:multibox name="adminForm" value="synchronization" property="dayEndProcesses"/>
					</TD>
				 </TR> -->

				 <TR align="left">
					<TD align="left" valign="top" class="ColumnBackground">
						&nbsp;<bean:message key="loanClosing"/>
					</TD>
					<TD align="left" valign="top" class="TableData">
						<html:multibox name="adminForm" value="loanClosing" property="dayEndProcesses"/>
					</TD>
				 </TR>

				 <TR align="left">
					<TD align="left" valign="top" class="ColumnBackground">
						&nbsp;<bean:message key="generateSFDan"/>
					</TD>
					<TD align="left" valign="top" class="TableData">
						<html:multibox name="adminForm" value="generateSFDan" property="dayEndProcesses"/>
					</TD>
				 </TR>

				 <TR align="left">
					<TD align="left" valign="top" class="ColumnBackground">
						&nbsp;<bean:message key="generateCGDan"/>
					</TD>
					<TD align="left" valign="top" class="TableData">
						<html:multibox name="adminForm" value="generateCGDan" property="dayEndProcesses"/>
					</TD>
				 </TR>
				 <TR align="left">
					<TD align="left" valign="top" class="ColumnBackground">
						&nbsp;<bean:message key="generateCLDan"/>
					</TD>
					<TD align="left" valign="top" class="TableData">
						<html:multibox name="adminForm" value="generateCLDan" property="dayEndProcesses"/>
					</TD>
				 </TR>
				 <TR align="left">
					<TD align="left" valign="top" class="ColumnBackground">
						&nbsp;<bean:message key="generateShortDan"/>
					</TD>
					<TD align="left" valign="top" class="TableData">
						<html:multibox name="adminForm" value="generateShortDan" property="dayEndProcesses"/>
					</TD>
				 </TR>
		
				 <TR align="left">
					<TD align="left" valign="top" class="ColumnBackground">
						&nbsp;<bean:message key="calculatePenalty"/>
					</TD>
					<TD align="left" valign="top" class="TableData">
						<html:multibox name="adminForm" value="calculatePenalty" property="dayEndProcesses"/>
					</TD>
				 </TR>

				<TR align="left">
					<TD align="left" valign="top" class="ColumnBackground">
						&nbsp;<bean:message key="batchProcess"/>
					</TD>
					<TD align="left" valign="top" class="TableData">
						<html:multibox name="adminForm" value="batchProcess" property="dayEndProcesses"/>
					</TD>
				 </TR>

				<TR align="left">
					<TD align="left" valign="top" class="ColumnBackground">
						&nbsp;<bean:message key="archiveData"/>
					</TD>
					<TD align="left" valign="top" class="TableData">
						<html:multibox name="adminForm" value="archiveData" property="dayEndProcesses"/>
					</TD>
				 </TR>

       
					<TR align="center" valign="baseline" >
				<td colspan="2" width="700"><DIV align="center">						
						<A href="javascript:submitForm('dayEndProcess.do?method=dayEndProcess')">
							<IMG src="images/Submit.gif" alt="Save" width="49" height="37" border="0"></A>
						<a href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>"><IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>
					</DIV>
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

