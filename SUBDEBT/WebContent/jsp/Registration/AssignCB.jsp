<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<% session.setAttribute("CurrentPage","showAssignCB.do?method=showAssignCB");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<HTML>
<BODY onLoad="selectMember()">
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="showAssignCB.do" method="POST" focus="memberBank" >
	<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
			<DIV align="right">			
				<A HREF="javascript:submitForm('helpAssignCB.do?method=helpAssignCB')">
			    HELP</A>
			</DIV>
			<TABLE width="100%" border="0" cellspacing="1" cellpadding="0">
			<TD  align="left" colspan="4"><font size="2"><bold>
				Fields marked as </font><font color="#FF0000" size="2">*</font><font size="2"> are mandatory</bold></font>
				</td>
				</tr>
          <TR> 
            <TD colspan="4"><TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
												<TD width="31%" class="Heading"><bean:message key="CBHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="4" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>

								</TR>
									<TR align="left" valign="top">
									<TD valign="top" class="ColumnBackground" width="25%"> 
										<DIV align="left">
											&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="memberBank" />
										</DIV>
									</TD>
									<TD  align="left" valign="top" class="TableData" width="25%">
									<html:select property="memberBank"  multiple="true"  name="regForm">
									<html:options property="members" name="regForm"/>
									</html:select>
									</TD>
									<TD align="left" valign="top" class="TableData" width="25%"> 
										<DIV align="left">
											&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="collectingBank" />
										</DIV>
									</TD>
									<TD align="left"  class="TableData" width="25%">
									<html:select property="collectingBank"  name="regForm">
										<html:option value="">Select </html:option>
										<html:options property="collectingBanks" name="regForm"/>		
										</html:select>
									</TD>
									
								</TR>
								
					<TR>
						<TD align="center" valign="baseline" colspan="4">
							<DIV align="center">
								
								<A href="javascript:submitForm('assignCB.do?method=assignCB')">
									<IMG src="images/OK.gif" alt="Save" width="49" height="37" border="0"></A>
								<A href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>"><IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>
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

