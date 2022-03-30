<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.cgtsi.util.SessionConstants"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","showSubSchemeParameters.do?method=showNewSubScheme");%>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
	<html:errors />
	<html:form action="showSubSchemeValues.do" method="POST" focus="subScheme">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/RiskManagementHeading.gif" width="121" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR> 
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<TABLE width="100%" align="left" border="0" cellspacing="0" cellpadding="0">
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="0">
								<TR>
									<TD align="right" colspan="4">
										<A href="showSubSchemeParametersHelp.do">HELP</A>
									</TD>
								</TR>
								<TR> 
									<TD colspan="4">
										<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
											<TR> 
												<TD width="25%" height="19" class="Heading">&nbsp;
													<bean:message key="subSchemeParameterHeading" />
												</TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR> 
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>
										</TABLE>
									</TD>
								</TR>
								<TR> 
									<TD width="25%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											* &nbsp;<bean:message key="subScheme" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" colspan="3">
										<DIV align="left">
											<html:text name="rmForm" property="subScheme" size="20" maxlength="13"/>
						                </DIV>
									</TD>
								</TR>
								<TR> 
									<TD class="ColumnBackground" width="25%" > &nbsp;
										* &nbsp;<bean:message key="state" name="rmForm" />
									</TD>
									<TD class="TableData" width="25%">
										<html:select property="state" name="rmForm" multiple="true">
											<html:option value="ALL">All </html:option>
											<html:options name="rmForm" property="statesList" />
										</html:select>
									</TD>
									<TD width="25%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											* &nbsp;<bean:message key="mli" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="25%">
										<DIV align="left">
											<html:select property="mli" name="rmForm" multiple="true">
												<html:option value="ALL">All </html:option>
												<html:options name="rmForm" property="mliList" />
											</html:select>
						                </DIV>
									</TD>
								</TR>
								<TR> 
									<TD class="ColumnBackground" width="25%" > &nbsp;
										* &nbsp;<bean:message key="industry" name="rmForm" />
									</TD>
									<TD class="TableData" width="25%">
										<html:select property="industry" name="rmForm" multiple="true">
											<html:option value="ALL">All </html:option>
											<html:options name="rmForm" property="industryList" />
										</html:select>
									</TD>
									<TD width="25%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
										* &nbsp;	<bean:message key="gender" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="25%">
										<DIV align="left">
										<html:select property="gender" name="rmForm" multiple="true">
											<html:option value="A">All </html:option>
											<html:option value="M">Male </html:option>
											<html:option value="F">Female </html:option>
										</html:select>
						                </DIV>
									</TD>
								</TR>
								<TR> 
									<TD class="ColumnBackground" width="25%" > &nbsp;
										* &nbsp;<bean:message key="socialCategory" name="rmForm" />
									</TD>
									<TD class="TableData" width="25%" colspan="3">
										<html:select property="socialCategory" name="rmForm" multiple="true">
											<html:option value="ALL">All </html:option>
											<html:options name="rmForm" property="socialCategoriesList" />
										</html:select>
									</TD>
								</TR>
								<TR> 
									<TD class="ColumnBackground" width="25%" > &nbsp;
										* &nbsp;<bean:message key="validFromDate" name="rmForm" />
									</TD>
									<TD class="TableData" width="25%">
										<html:text property="validFromDate" size="20" maxlength="10"  alt="Limit Valid From" name="rmForm"/>
										<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rmForm.validFromDate')" align="center">
									</TD>
									<TD class="ColumnBackground" width="25%" > &nbsp;
										<bean:message key="validToDate" name="rmForm" />
									</TD>
									<TD class="TableData" width="25%">
										<html:text property="validToDate" size="20" maxlength="10"  alt="Limit Valid To" name="rmForm"/>
										<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rmForm.validToDate')" align="center">
									</TD>
								</TR>
							</TABLE>
						</TD>
					</TR>
					<TR >
						<TD align="center" valign="baseline" >
							<DIV align="center">
								<A href="javascript:submitForm('showSubSchemeValues.do')">
									<IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>
								<A href="javascript:document.rmForm.reset()">
									<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>
								<A href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
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
