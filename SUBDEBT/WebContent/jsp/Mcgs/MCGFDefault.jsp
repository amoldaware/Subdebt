<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<% session.setAttribute("CurrentPage","showMCGFDefault.do?method=showMCGFDefault");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<html:errors />
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<TR>
		<TD>
		<html:form action="updateMCGFStatus.do?method=updateMCGFStatus" method="POST" focus="mcgfName">
			<TABLE width="680" border="0" cellspacing="0" cellpadding="0" align="center">
				<TR> 
					<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
					<TD background="images/TableBackground1.gif"></TD>
					<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
				</TR>
				<TR>
					<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
					<TD width="713">
					<DIV align="right">			
						<A HREF="javascript:submitForm('mcgfDefaultHelp.do?method=mcgfDefaultHelp')">
						HELP</A>
					</DIV>
						<TABLE width="606" border="0" cellspacing="1" cellpadding="0">
							<TR> 
								<TD colspan="2" width="700">
									<TABLE width="604" border="0" cellspacing="0" cellpadding="0">
										<TR>
											<TD width="31%" class="Heading"><bean:message key="updateMCGFStatus" /></TD>
											<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
										</TR>
										<TR>
											<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
										</TR>
									</TABLE>
								</TD> 
							</TR>
							<TR>
								<TD width="20%" align="left" valign="top" class="ColumnBackground">
									<DIV align="left">
										&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="mcgfName" />
									</DIV>
								</TD>
								<TD width="27%" align="left" valign="top" class="TableData">
									<html:select property="mcgfName" name="mcgsForm">
									<html:option value="">Select</html:option>
									<html:options property="mcgfs" name="mcgsForm"/> 
									</html:select>
								</TD>
							</TR>
							<TR>
								<TD align="left" valign="top" class="ColumnBackground">
									&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="reason" />
								</TD>
								<TD align="left" valign="top" class="TableData"> 
									<DIV align="left">
									<html:textarea property="reason" name="mcgsForm" rows="3" cols="50"/>
									</DIV>
								</TD>
							</TR>
							<TR align="center" valign="baseline" >
								<TD colspan="2" width="700"><DIV align="center">
									<A href="javascript:submitForm('updateMCGFStatus.do?method=updateMCGFStatus')">
										<IMG src="images/OK.gif" alt="Save" width="49" height="37" border="0"></A>
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
			</TABLE>
		</html:form>		
		</TD>
	</TR>
</TABLE>
</HTML>
