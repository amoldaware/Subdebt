<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.cgtsi.util.SessionConstants"%>
<%@ page import="com.cgtsi.risk.SubSchemeParameters"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","showSubSchemeReport.do?method=showSubSchemeReport");%>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
	<html:errors />
	<html:form action="showSubSchemeReport.do?method=showSubSchemeReport" method="POST">
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
									<TD colspan="8">
										<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
											<TR> 
												<TD width="25%" height="19" class="Heading">&nbsp;
													<bean:message key="subSchemeParameterHeading" />
												</TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR> 
												<TD colspan="8" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>
										</TABLE>
									</TD>
								</TR>
								<TR> 
									<TD class="ColumnBackground" width="18%">
										<DIV align="left"> &nbsp;
											<bean:message key="subScheme" name="rmForm" />
										</DIV>
									</TD>
									<TD class="ColumnBackground" width="10%"> &nbsp;
										<bean:message key="state" name="rmForm" />
									</TD>
									<TD class="ColumnBackground" width="10%">
										<DIV align="left"> &nbsp;
											<bean:message key="mli" name="rmForm" />
										</DIV>
									</TD>
									<TD class="ColumnBackground" width="20%"> &nbsp;
										<bean:message key="industry" name="rmForm" />
									</TD>
									<TD class="ColumnBackground" width="8%">
										<bean:message key="gender" name="rmForm" />
									</TD>
									<TD class="ColumnBackground" width="15%"> &nbsp;
										<bean:message key="socialCategory" name="rmForm" />
									</TD>
									<TD class="ColumnBackground" width="10%"> &nbsp;
										<bean:message key="validFromDate" name="rmForm" />
									</TD>
									<TD class="ColumnBackground" width="10%"> &nbsp;
										<bean:message key="validToDate" name="rmForm" />
									</TD>
								</TR>
								<%
									SubSchemeParameters subSchemeParameters = new SubSchemeParameters();

									String subSchemeId="";
									String subSchemeName="";
									String strState="";
									String strMli="";
									String strIndustry="";
									String strGender="";
									String strSocialCat="";
									String strValidFromDate="";
									String strValidToDate="";

									SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
								%>
								<logic:iterate name="rmForm" property="subSchemes" id="object">
								<%
									subSchemeParameters = (SubSchemeParameters) object;
									String[] states = subSchemeParameters.getState();
									String[] mli = subSchemeParameters.getMli();
									String[] industry = subSchemeParameters.getIndustry();
									String[] gender = subSchemeParameters.getGender();
									String[] socialCat = subSchemeParameters.getSocialCategory();
									int i=0;

									subSchemeName = subSchemeParameters.getSubScheme();
									subSchemeId = subSchemeParameters.getSubSchemeId();

									for (i=0;i<states.length;i++)
									{
										strState = strState + ", " + states[i];
									}
									strState=strState.substring(2);

									for (i=0;i<mli.length;i++)
									{
										strMli = strMli + ", " + mli[i];
									}
									strMli=strMli.substring(2);

									for (i=0;i<industry.length;i++)
									{
										strIndustry = strIndustry + ", " + industry[i];
									}
									strIndustry=strIndustry.substring(2);

									for (i=0;i<gender.length;i++)
									{
										strGender = strGender + ", " + gender[i];
									}
									strGender=strGender.substring(2);

									for (i=0;i<socialCat.length;i++)
									{
										strSocialCat = strSocialCat + ", " + socialCat[i];
									}
									strSocialCat=strSocialCat.substring(2);

									Date validFromDate = subSchemeParameters.getValidFromDate();
									strValidFromDate = dateFormat.format(validFromDate);

									Date validToDate = subSchemeParameters.getValidToDate();
									if (validToDate!=null)
									{
										strValidToDate = dateFormat.format(validToDate);
									}
								%>
								<TR> 
									<TD class="ColumnBackground">
										<a href="showSubSchemeValuesReport.do?method=showSubSchemeValuesReport&id=<%=subSchemeId%>"><%=subSchemeName%></a>
									</TD>
									<TD class="ColumnBackground" >
										<%=strState%>
									</TD>
									<TD class="ColumnBackground">
										<%=strMli%>
									</TD>
									<TD class="ColumnBackground" > 
										<%=strIndustry%>
									</TD>
									<TD class="ColumnBackground">
										<%=strGender%>
									</TD>
									<TD class="ColumnBackground" > 
										<%=strSocialCat%>
									</TD>
									<TD class="ColumnBackground" >
										<%=strValidFromDate%>
									</TD>
									<TD class="ColumnBackground" > 
										<%=strValidToDate%>
									</TD>
								</TR>
								<%
								strState="";
								strMli="";
								strIndustry="";
								strGender="";
								strSocialCat="";
								strValidFromDate="";
								strValidToDate="";
								%>
								</logic:iterate>
							</TABLE>
						</TD>
					</TR>
					<TR >
						<TD align="center" valign="baseline" >
							<DIV align="center">
						  <A href="javascript:submitForm('showSubSchemeReportInput.do?method=showSubSchemeReportInput')">
										<IMG src="images/Back.gif" alt="Save" width="49" height="37" border="0">
										</A>
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
