<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@page import ="java.text.SimpleDateFormat"%>
<%@page import ="java.text.DecimalFormat"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","hvcUpdatePage.do?method=hvcUpdatePage");%>
<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>
<%DecimalFormat decimalFormat = new DecimalFormat("##########.##");%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="hvcUpdatePage.do?method=hvcUpdatePage" method="POST" enctype="multipart/form-data">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/InvestmentManagementHeading.gif" width="175" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
				<tr>
				<TD  align="left" colspan="4"><font size="2"><bold>
				Fields marked as </font><font color="#FF0000" size="2">*</font><font size="2"> are mandatory</bold></font>
				</td>
				</tr>
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="8"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="20%" class="Heading"><bean:message key="hvcHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>


									<tr>
									<TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>
										<bean:message key="fromBank"/>
									</TD>
									<TD align="left" class="TableData"> 
										<html:select  property="bankName"  name="ifForm">
										<html:option value="">Select</html:option>
										<html:options property="banks" name="ifForm"/>
										</html:select>
									</TD>
									</TR>

									<tr>
									<TD  align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>
										<bean:message key="toBank"/>
									</TD>
									<TD align="left" class="TableData"> 
										<html:select  property="toBankName"  name="ifForm">
										<html:option value="">Select</html:option>
										<html:options property="banks" name="ifForm"/>
										</html:select>
									</TD>
									</TR>

									<tr>
									<TD  align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>
										<bean:message key="chequeDate" />
									</TD>
										<TD align="left" class="TableData"> 
										<html:text property="chequeDate" size="20"  alt="Source ID" name="ifForm"/>
									</TD>
									</tr>

									<tr>
									<TD  align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>
										<bean:message key="fundTransfer" />
									</TD>
									<bean:define id="cVal" name="ifForm" property="chequeAmount"/>
									<%
										DecimalFormat df= new DecimalFormat("######################.##");
										df.setDecimalSeparatorAlwaysShown(false);
										double dVal = Double.parseDouble((String)cVal);
										String sVal = df.format(dVal);

									%>

										<TD align="left" class="TableData"> 
										<html:text property="chequeAmount" size="20"  alt="Source ID" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" name="ifForm" value="<%=sVal%>"/>
									</TD>
									</tr>


									<tr>
									<TD  align="left" valign="top" class="ColumnBackground">
										<bean:message key="remarks" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:textarea property="chequeRemarks" cols="70" rows="3" name="ifForm"/>
									</TD>
									</tr>


											</logic:iterate>
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

							<A href="javascript:submitForm('hvcUpdateSuccess.do?method=hvcUpdateSuccess')">
									<IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>

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

