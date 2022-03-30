<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>

<script type="text/javascript"> 
        window.history.forward(); 
        function noBack() { 
            window.history.forward(); 
        } 
    </script> 
	
	<script src="/csrfguard"></script>

<% session.setAttribute("CurrentPage","showMessage.do?method=showMessage");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>

<TABLE width="700" border="0" cellpadding="0" cellspacing="0" align="center">
	<html:errors />
	<html:form action="showInbox.do?method=showInbox" method="POST">
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
							<TABLE width="100%" border="0" cellspacing="0" cellpadding="1">
								<TR>
									<TD colspan="4"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="31%" class="Heading"><bean:message key="message" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>
								</TR>
								<TR align="left">
								<%								        
									com.cgtsi.admin.Message message=(com.cgtsi.admin.Message)session.getAttribute("adminMail");
									
									String from=message.getFrom();
									String bankId=message.getBankId();
									String zoneId=message.getZoneId();
									String branchId=message.getBranchId();
									
									String messageInfo=message.getMessage();
									String subject = message.getSubject();
									
									String memberId=bankId+zoneId+branchId;
								%>
									<TD class="SubHeading" >
									<U>
									Message From: 
									<% 
										out.println(from); 
									%> 
									
									&nbsp;
									
									<%
									out.println(memberId);
									%>
									</U>
									<BR>
								</TR>
								<TR>
									<TD>
										<TABLE width="100%" BORDER="1" CELLSPACING="0" CELLPADDING="0" bordercolor="#111111" style="border-collapse: collapse">
											<TR>
												<TD align="center" class="TableDataCenter">
												<BR>
													&nbsp;&nbsp;<% 
														out.println(messageInfo);
														%>
													<BR>
														<%
													if (subject.indexOf("NEW DEMAND") >= 0 )
														{
															%>
															<!-- <a href="javascript:submitForm('allocatePayments.do?method=getPendingDANs');">Show DAN</a> -->
															<%
														}
													%>
												<BR>
												<BR>
												</TD>
											</TR>
										</TABLE>
									</TD>
								</TR>
								<TR>
									<TD>
										<A HREF="showInbox.do?method=showInbox"> Back to Inbox </A>
									</TD>
									<TD>
										<!--<A HREF="javascript:submitForm('replyMail.do?method=replyMail')"> Reply </A>-->
                                                                           <A HREF="javascript:submitForm('replyMail.do?method=replyMail')"></A>
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