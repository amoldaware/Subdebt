<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>

<% session.setAttribute("CurrentPage","generateClaimSFDAN.do?method=generateClaimSFDAN");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%
String focusField="";
%>
<HTML>
	<BODY>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="generateClaimASFDAN.do?method=generateClaimASFDAN" method="POST" enctype="multipart/form-data" focus="<%=focusField%>">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/ReceiptsPaymentsHeading.gif"></TD>
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
												<TD width="80%" class="Heading">Generate ASF DAN for Claim Settled Application</TD>
												<TD width="20%" ><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="4" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>
								</TR>
       			    <TR>
									<TD align="left" colspan="2" valign="top" class="ColumnBackground"><bean:message key="cgpan"/></TD>
									<TD align="left" valign="top" class="TableData">
									 <html:text property="cgpan" size="20" alt="CGPAN" name="rpReallocationForm"  maxlength ="13" />
									</TD>
                  <TD align="left" valign="top" class="ColumnBackground"><bean:message key="sFee"/></TD>
									<TD align="left" valign="top" class="TableData">
									 <html:text property="danAmt" size="20" alt="danAmt" name="rpReallocationForm"  maxlength ="13" />
									</TD>
								</TR>
                <TR>
									<TD align="left" colspan="2" valign="top" class="ColumnBackground"><bean:message key="remarks"/></TD>
                  <TD align="left" valign="top" class="TableData">
							<!--		 <html:text property="applRemarks" size="100" alt="applRemarks" name="rpReallocationForm"  maxlength ="100" /> -->
									<html:select name="rpReallocationForm" property="applRemarks">
                   <html:option value="">Select</html:option>
                  <html:option value="CLAIM SETTLED MANUALLY-A/C CLOSED">CLAIM SETTLED MANUALLY-A/C CLOSED</html:option>
                  <html:option value="REQUESTED BY MLI-A/C CLOSED">REQUESTED BY MLI-A/C CLOSED</html:option>
                  </html:select>
                  </TD>
                  <TD align="left"  valign="top" class="ColumnBackground">Dan Type</TD>
                  <TD align="left" valign="top" class="TableData">
                  <html:select name="rpReallocationForm" property="danType">
                  <html:option value="">Select</html:option>
                  <html:option value="SF">ASF</html:option>
                  <html:option value="CG">CG</html:option>
                  </html:select>
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
								
									<A href="javascript:submitForm('generateClaimASFDAN.do?method=generateClaimASFDAN')">
									<IMG src="images/OK.gif" alt="OK" width="49" height="37" border="0"></A>
									<A href="javascript:document.rpReallocationForm.reset()">
									<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>

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
	</BODY>
</HTML>