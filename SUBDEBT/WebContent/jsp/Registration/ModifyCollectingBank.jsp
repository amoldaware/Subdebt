<%@ page language="java"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<% session.setAttribute("CurrentPage","modifyCollBank.do?method=modifyCollBank");%>


<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="modifyCollBank.do" method="POST" focus="address">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
			<DIV align="right">			
				<A HREF="javascript:submitForm('helpModifyCollectingBank.do?method=helpModifyCollectingBank')">
			    HELP</A>
			</DIV>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
				<TD  align="left" colspan="4"><font size="2"><bold>
				Fields marked as </font><font color="#FF0000" size="2">*</font><font size="2"> are mandatory</bold></font>
				</td>
				</tr>
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="4"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="31%" class="Heading"><bean:message key="modifyDetailsHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>
								</TR>

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="bankName" />
									</TD>
									<TD align="left" class="TableData"> 
										<bean:write property="collectingBankName" name="regForm"/>
									</TD>
								</TR>

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="CBbranchName" />
									</TD>
									<TD align="left" class="TableData"> 
										<bean:write property="branchName"  name="regForm"/>
									</TD>
								</TR>

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="address" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:textarea property="address" cols="20" alt="Address" name="regForm"/>
									</TD>
								</TR>

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="city" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="city" size="20" alt="City" name="regForm" maxlength="50"/>
									</TD>
								</TR>

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="state" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:select property="state" name="regForm">
											<html:option value="">Select</html:option>
											<html:options property="states" name="regForm"/>				
										</html:select>
									</TD>
								</TR>				
								
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="phoneNo" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="phoneStdCode" size="10" alt="Phone No" name="regForm"onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"maxlength="10"/>&nbsp;&nbsp-&nbsp&nbsp;<html:text property="phone" size="10" alt="Phone No" name="regForm"onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)" maxlength="20"/>
									</TD>
								</TR>								

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="contactPerson" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="contactPerson" size="20" alt="Contact Person" name="regForm" maxlength="50"/>
									</TD>
								</TR>

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="emailId" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="emailId" size="20" alt="Email Address" name="regForm" maxlength="40"/>
									</TD>
								</TR>							

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="accountNo" name="regForm"/>
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="accNo" size="20" alt="Account No." maxlength="20"/>
									</TD>
								</TR>

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="hoAddress" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:textarea property="hoAddress" cols="20" alt="HO Address" name="regForm"/>
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
							<A href="javascript:submitForm('afterModifyCollBank.do?method=afterModifyCollBank')"><IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>	
								<A href="javascript:document.regForm.reset()">
									<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>
															
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





						
