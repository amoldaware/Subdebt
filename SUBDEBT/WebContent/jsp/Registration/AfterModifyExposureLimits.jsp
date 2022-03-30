<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","modifyExposureLimits.do?method=modifyExposureLimits");%>


<script type="text/javascript"> 
        window.history.forward(); 
        function noBack() { 
            window.history.forward(); 
        } 
</script> 
<script src="/csrfguard"></script>
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="modifyExposureLimits.do" method="POST" >
	<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
	
			<TABLE width="100%" border="0" cellspacing="1" cellpadding="0">
			<TD  align="left" colspan="4"><font size="2"><bold>
				Fields marked as </font><font color="#FF0000" size="2">*</font><font size="2"> are mandatory</bold></font>
				</td>
          <TR> 
            <TD colspan="4"><TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
												<TD width="31%" class="Heading">Modify Exposure Limits</TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="4" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>

								</TR>
                <TR align="left" valign="top">
									<TD valign="top" class="ColumnBackground" > 
										<DIV align="left">
											&nbsp;
											<bean:message key="bank"/>
										</DIV>
									</TD>
									
									<TD align="left" valign="top" class="ColumnBackground"> 
									Existing Exposure Limit(In Cr.)
									</TD>
									<TD align="left"  class="ColumnBackground">
									New Exposure Limit(In Cr.)
									</TD>
									
								</TR>
									<TR align="left" valign="top">
									<TD valign="top" class="ColumnBackground" > 
										<DIV align="left">
											&nbsp;
											<bean:write property="bank" name="regForm"/>
										</DIV>
									</TD>
									
									<TD align="left" valign="top" class="ColumnBackground"> 
										<DIV align="left">
											&nbsp;
											<bean:write property="cbMessage" name="regForm"/></DIV>
									</TD>
									<TD align="left"  class="TableData">
								 <html:text property="exposureLimit" size="20" maxlength="6" alt="New Exposure Limit" name="regForm"/>
									</TD>
									
								</TR>
								
					<TR>
						<TD align="center" valign="baseline" colspan="4">
							<DIV align="center">
								<A href="javascript:submitForm('showModifyExposureLimit.do?method=showModifyExposureLimit')">
									<IMG src="images/Back.gif" alt="Back" width="49" height="37" border="0"></A>
								<A href="javascript:submitForm('afterModifyExposureLimits.do?method=afterModifyExposureLimits')">
									<IMG src="images/OK.gif" alt="Save" width="49" height="37" border="0"></A>
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

