<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<% session.setAttribute("CurrentPage","downloadJRE.do");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<form>
	<html:errors />
	<TR> 
		<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
		<TD background="images/TableBackground1.gif"></TD>
		<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
	</TR>
	<TR>
		<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
		<TD>
			<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
				<TR>
					<TD>
						<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
							<TR>
								<TD colspan="4"> 
									<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										<TR>
											<TD width="31%" class="Heading"><bean:message key="downloadJRE" /></TD>
											<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
										</TR>
										<TR>
											<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
										</TR>

									</TABLE>
								</TD>
							</TR>
							<TR align="left">
								<TD align="left" colspan="4" valign="top" class="ColumnBackground">
									&nbsp;
									<html:link href="javascript:openNewWindow('http://java.com/en/download/manual.jsp')"><bean:message key="clickToDownloadJRE"/>
									</html:link>		
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
</form>
</TABLE>





						
