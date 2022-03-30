<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","showSubSchemeValuesReport.do?method=showSubSchemeValuesReport");%>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
	<html:errors />
	<html:form action="showSubSchemeValuesReport.do?method=showSubSchemeValuesReport" method="POST" >
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
									<TD colspan="5">
										<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
											<TR> 
												<TD width="25%" height="19" class="Heading">&nbsp;
													<bean:message key="subSchemeDetailsHeading" />
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
											<bean:message key="appFilingTimeLimit" name="rmForm" />
										</DIV>
									</TD>
									<TD class="ColumnBackground">
										<DIV align="left">
											<bean:message key="rule" name="rmForm"/>
						                </DIV>
									</TD>
									<TD class="TableData">
										<DIV align="left">
											<bean:write name="rmForm" property="rule"/>
										</DIV>
									</TD>
									<TD class="ColumnBackground">
										<DIV align="left">
											<bean:message key="value" name="rmForm"/>
						                </DIV>
									</TD>
									<TD class="TableData">
										<DIV align="left">
											<bean:write name="rmForm" property="appFilingTimeLimitValue"/>
										</DIV>
									</TD>
								</TR>
								<TR> 
									<TD class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="validFrom" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" >
										<DIV align="left">
											<bean:write name="rmForm" property="appFilingTimeLimitValidFrom"/>
						                </DIV>
									</TD>
									<TD  class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="validTo" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" colspan="2">
										<DIV align="left" >
											<bean:write name="rmForm" property="appFilingTimeLimitValidTo"/>
						                </DIV>
									</TD>
								</TR>
<!--								<TR> 
									<TD width="35%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="maxGCoverExposure" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" colspan="4">
										<DIV align="left">
											<bean:write name="rmForm" property="maxGCoverExposure"/>
						                </DIV>
									</TD>
								</TR>
								<TR> 
									<TD width="25%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="validFrom" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="20%">
										<DIV align="left">
											<bean:write name="rmForm" property="maxGCoverExposureValidFrom"/>
						                </DIV>
									</TD>
									<TD width="20%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="validTo" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="25%" colspan="2">
										<DIV align="left" colspan="2">
											<bean:write name="rmForm" property="maxGCoverExposureValidTo"/>
						                </DIV>
									</TD>
								</TR>
								<TR> 
									<TD width="25%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="minSanctionedAmount" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" colspan="4">
										<DIV align="left">
											<bean:write name="rmForm" property="minSanctionedAmount"/>
						                </DIV>
									</TD>
								</TR>
								<TR> 
									<TD width="25%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="validFrom" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="20%">
										<DIV align="left">
											<bean:write name="rmForm" property="minSanctionedAmtValidFrom"/>
						                </DIV>
									</TD>
									<TD width="20%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="validTo" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="25%" colspan="2">
										<DIV align="left" colspan="2">
											<bean:write name="rmForm" property="minSanctionedAmtValidTo"/>
						                </DIV>
									</TD>
								</TR>
								<TR> 
									<TD width="25%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="maxSanctionedAmount" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" colspan="4">
										<DIV align="left">
											<bean:write name="rmForm" property="maxSanctionedAmount"/>
						                </DIV>
									</TD>
								</TR>
								<TR> 
									<TD width="25%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="validFrom" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="20%">
										<DIV align="left">
											<bean:write name="rmForm" property="maxSanctionedAmtValidFrom"/>
						                </DIV>
									</TD>
									<TD width="20%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="validTo" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="25%" colspan="2">
										<DIV align="left" colspan="2">
											<bean:write name="rmForm" property="maxSanctionedAmtValidTo"/>
						                </DIV>
									</TD>
								</TR>-->
								<TR> 
									<TD width="25%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="maxBorrowerExposureAmount" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" colspan="4">
										<DIV align="left">
											<bean:write name="rmForm" property="maxBorrowerExposureAmount"/>&nbsp;<bean:message key="inRs" />
						                </DIV>
									</TD>
								</TR>
								<TR> 
									<TD width="25%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="validFrom" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="20%">
										<DIV align="left">
											<bean:write name="rmForm" property="maxBorrowerExpAmtValidFrom"/>
						                </DIV>
									</TD>
									<TD width="20%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="validTo" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="25%" colspan="2">
										<DIV align="left" colspan="2">
											<bean:write name="rmForm" property="maxBorrowerExpAmtValidTo"/>
						                </DIV>
									</TD>
								</TR>
<!--								<TR> 
									<TD width="25%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="guaranteeCoverExtent" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" colspan="4">
										<DIV align="left">
											<bean:write name="rmForm" property="guaranteeCoverExtent"/>
						                </DIV>
									</TD>
								</TR>
								<TR> 
									<TD width="25%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="validFrom" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="20%">
										<DIV align="left">
											<bean:write name="rmForm" property="guaranteeCoverExtentValidFrom"/>
						                </DIV>
									</TD>
									<TD width="20%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="validTo" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="25%" colspan="2">
										<DIV align="left" colspan="2">
											<bean:write name="rmForm" property="guaranteeCoverExtentValidTo"/>
						                </DIV>
									</TD>
								</TR>-->
								<TR> 
									<TD width="25%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="serviceFeeCardRate" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" colspan="4">
										<DIV align="left">
											<bean:write name="rmForm" property="serviceFeeCardRate"/>&nbsp;<bean:message key="inPer" />
						                </DIV>
									</TD>
								</TR>
								<TR> 
									<TD width="25%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="validFrom" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="20%">
										<DIV align="left">
											<bean:write name="rmForm" property="serviceFeeCardRateValidFrom"/>
						                </DIV>
									</TD>
									<TD width="20%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="validTo" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="25%" colspan="2">
										<DIV align="left" colspan="2">
											<bean:write name="rmForm" property="serviceFeeCardRateValidTo"/>
						                </DIV>
									</TD>
								</TR>
<!--								<TR> 
									<TD width="25%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="guaranteeFeeCardRate" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" colspan="4">
										<DIV align="left">
											<bean:write name="rmForm" property="guaranteeFeeCardRate"/>&nbsp;<bean:message key="inPer" />
						                </DIV>
									</TD>
								</TR>
								<TR> 
									<TD width="25%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="validFrom" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="20%">
										<DIV align="left">
											<bean:write name="rmForm" property="guaranteeFeeCardRateValidFrom"/>
						                </DIV>
									</TD>
									<TD width="20%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="validTo" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="25%" colspan="2">
										<DIV align="left" colspan="2">
											<bean:write name="rmForm" property="guaranteeFeeCardRateValidTo"/>
						                </DIV>
									</TD>
								</TR>-->
								<TR> 
									<TD width="25%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="defaultRateApplicable" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" colspan="4">
										<DIV align="left">
											<bean:write name="rmForm" property="defaultRateApplicable"/>
										</DIV>
									</TD>
								</TR>
								<TR> 
									<TD width="25%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="validFrom" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="20%">
										<DIV align="left">
											<bean:write name="rmForm" property="defRateApplicableValidFrom"/>
						                </DIV>
									</TD>
									<TD width="20%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="validTo" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="25%" colspan="2">
										<DIV align="left" colspan="2">
											<bean:write name="rmForm" property="defRateApplicableValidTo"/>
						                </DIV>
									</TD>
								</TR>
								<TR> 
									<TD width="25%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="defaultRate" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" colspan="4">
										<DIV align="left">
											<bean:write name="rmForm" property="defaultRate"/>&nbsp;<bean:message key="inPer" />
						                </DIV>
									</TD>
								</TR>
								<TR> 
									<TD width="25%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="validFrom" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="20%">
										<DIV align="left">
											<bean:write name="rmForm" property="defRateValidFrom"/>
						                </DIV>
									</TD>
									<TD width="20%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="validTo" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="25%" colspan="2">
										<DIV align="left" colspan="2">
											<bean:write name="rmForm" property="defRateValidTo"/>
						                </DIV>
									</TD>
								</TR>
								<TR> 
									<TD width="25%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="moderationFactor" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" colspan="4">
										<DIV align="left">
											<bean:write name="rmForm" property="moderationFactor"/>
						                </DIV>
									</TD>
								</TR>
								<TR> 
									<TD width="25%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="validFrom" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="20%">
										<DIV align="left">
											<bean:write name="rmForm" property="moderationFactorValidFrom"/>
						                </DIV>
									</TD>
									<TD width="20%" class="ColumnBackground">
										<DIV align="left"> &nbsp;
											<bean:message key="validTo" name="rmForm" />
										</DIV>
									</TD>
									<TD class="TableData" width="25%" colspan="2">
										<DIV align="left" colspan="2">
											<bean:write name="rmForm" property="moderationFactorValidTo"/>
						                </DIV>
									</TD>
								</TR>
							</TABLE>
						</TD>
					</TR>
					<TR >
						<TD align="center" valign="baseline" >
							<DIV align="center">
								<A href="javascript:history.back()">
									<IMG src="images/Back.gif" alt="Back" width="49" height="37" border="0"></A>
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
</body>