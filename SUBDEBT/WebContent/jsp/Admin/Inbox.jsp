<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>

<script type="text/javascript"> 
        window.history.forward(); 
        function noBack() { 
            window.history.forward(); 
        } 
    </script> 
	
	<script src="/csrfguard"></script>

<% session.setAttribute("CurrentPage","showInbox.do?method=showInbox");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<BODY onLoad="setToDefault()">
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="deleteReadMails.do?method=deleteReadMails" method="POST">
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
									<TD colspan="5"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="31%" class="Heading"><bean:message key="Inbox" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>
								</TR>
								<TR align="left">
									<TD>
										<TABLE width="100%" border="0" cellpadding="1" cellspacing="1">
											<TR>
												<TD class="ColumnBackground" width="15%">
												<input type="checkbox" name="ALL" onclick="selectAll(this)"/><bean:message key="select_all"/>
												</TD>
												<TD class="ColumnBackground" width="25%"> 
													&nbsp;<bean:message key="from" />
												</TD>
												<TD class="ColumnBackground" width="20%">
													<bean:message key="subject" />
												</TD>
												<TD class="ColumnBackground" width="20%">
													<bean:message key="date" />
												</TD>
												<TD class="ColumnBackground" width="20%">
													<bean:message key="status" />
												</TD>											
											</TR>
											<logic:iterate id="mailInfo" property="inboxMails" name="adminForm" >
											<%
												com.cgtsi.admin.Message message=(com.cgtsi.admin.Message)mailInfo;
												
												String id=""+message.getMessageId();
												
												String from=message.getFrom();
												String subject=message.getSubject();
												String bankId=message.getBankId();
												String zoneId=message.getZoneId();
												String branchId=message.getBranchId();
												
												String info=from+" "+bankId+zoneId+branchId;
												
												String href="javascript:submitForm('showMessage.do?method=showMessage&mailId="+id+"')";
											%>
											<TR>
												<TD class="TableData"> 
												<html:checkbox property="checks" name="adminForm" value="<%=id%>"/>
												</TD>
												<TD class="TableData">
												&nbsp;<A HREF="<%=href%>"><% out.println(info);%></A>
												</TD>
												<TD class="TableData" > <bean:write property="subject" name="mailInfo" />
												
												</TD>
												<TD class="TableData" > <bean:write property="date" name="mailInfo" />
												
												</TD>
												<TD class="TableData" > <bean:write property="status" name="mailInfo" />
												
												</TD>											
											</TR>
											</logic:iterate>
										</TABLE>
									</TD>
								</TR>
								<TR>
									<TD>
										<html:submit styleClass="FontStyle">
											<bean:message key="delete" />
										</html:submit>
										&nbsp;checked mail(s)
									</TD>
								</TR>								
							</TABLE>
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
</body>




						


