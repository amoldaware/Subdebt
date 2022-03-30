<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<% session.setAttribute("CurrentPage","getMemberDetails.do?method=getMemberDetails");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>

<%
String focusflag = "";
if(request.getAttribute("district")!=null && request.getAttribute("district").equals("1"))
{
	focusflag="district";
}
else{

	focusflag="bankName";
}
%>

<HTML>
	<BODY onLoad="danDelivery()">
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="updateMemberDetails.do" method="POST" focus="<%=focusflag%>">
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
						<DIV align="right">			
				<A HREF="javascript:submitForm('helpRegisterMLI.do?method=helpRegisterMLI')">
			    HELP</A>
			</DIV>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
							<TD  align="left" colspan="4"><font size="2"><bold>
				Fields marked as </font><font color="#FF0000" size="2">*</font><font size="2"> are mandatory</bold></font>
				</td>
				</tr>
								<TR>
									<TD colspan="4"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="31%" class="Heading"><bean:message key="modifyMemberHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>
								</TR>

								<bean:define id="memberId" name="regForm" property="memberId" type="java.lang.String"/>

				<%
				String bankId=memberId.substring(0,4);
				String zoneId=memberId.substring(4,8);
				String branchId=memberId.substring(8,12);


								if(zoneId.equals("0000")&&branchId.equals("0000")){%>
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="bankName" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="bankName" size="20" alt="Bank name" name="regForm" maxlength="100"/>
									</TD>
								</TR>
								<% }else{%>
								 <TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="bankName" />
									</TD>
									<TD align="left" class="TableData"> 
										<bean:write property="bankName" name="regForm"/>
									</TD>
								</TR>

								<% }%>

								</TR>
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="shortName" />
									</TD>
									<TD align="left" class="TableData"> 								
										<bean:write property="shortName" name="regForm"/>
									</TD>
								</TR>					
								
							<%	if(!zoneId.equals("0000")){%>

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="zoRoName" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="zoneName" size="20" name="regForm" maxlength="100"/>
									</TD>
								</TR>
								<%}

								if(!branchId.equals("0000")){%>

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="CBbranchName" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="branchName" size="20" name="regForm" maxlength="100"/>
									</TD>
								</TR>
								<%}

								if(!zoneId.equals("0000")||!branchId.equals("0000")){%>

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="reportingZoneId" />
									</TD>
									<TD align="left" class="TableData"> 
										<bean:write property="reportingZone" name="regForm" />
									</TD>
								<%}%>

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="bankAddress" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:textarea property="address" cols="20" alt="Bank Address" name="regForm"/>
									</TD>
								</TR>

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="city" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="city" size="20" alt="City" name="regForm" maxlength="20"/>
									</TD>
								</TR>

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="state" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:select property="state" name="regForm" onchange="javascript:submitForm('getMemberDetails.do?method=getDistricts')">
											<html:option value="">Select</html:option>
											<html:options property="states" name="regForm"/>					</html:select>
									</TD>
								</TR>

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="district" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:select property="district" name="regForm">
											<html:option value="">Select</html:option>
											<html:options property="districts" name="regForm"/>
										</html:select>
									</TD>
								</TR>
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="pinCode" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="pin" size="5" maxlength="6" alt="pinCode" name="regForm"onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>
									</TD>
								</TR>	

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="phoneNo" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="phoneStdCode" size="10" alt="Phone No" name="regForm"  onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)" maxlength="10"/>&nbsp;&nbsp;-&nbsp;&nbsp;
										<html:text property="phone" size="10" alt="Phone No" name="regForm"onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)" maxlength="20"/>
									</TD>
								</TR>

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="faxNo" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="faxStdCode" size="10" alt="Fax No" name="regForm"onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)" maxlength="10"/>&nbsp;&nbsp;-&nbsp;&nbsp;
										<html:text property="fax" size="10" alt="Fax No" name="regForm"onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)" maxlength="20"/>
									</TD>
								</TR>								

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="emailId" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="emailId" size="20" alt="Email Address" name="regForm" maxlength="40"/>
									</TD>

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="deliveryOfDAN"/>
									</TD>
									<TD align="left" valign="top" class="TableData">
										<html:multibox name="regForm" value="Mail" property="danDelivery"/><bean:message key="mail"/>
										<html:multibox name="regForm" value="EMail" property="danDelivery"/><bean:message key="eMail"/>
										<html:multibox name="regForm" value="HardCopy" property="danDelivery"/><bean:message key="hardCopy"/>	
									</TD>
								</TR>
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="supportMCGF"/>							
									</TD>
									<TD align="left" valign="top" class="TableData">
										<!-- <html:radio name="regForm" value="Y" property="supportMCGF" ><bean:message key="yes" /></html:radio>&nbsp;&nbsp;
										<html:radio name="regForm" value="N" property="supportMCGF" ><bean:message key="no" /></html:radio> -->
										<bean:write property="supportMCGF" name="regForm"/>
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
							<A href="javascript:submitForm('updateMemberDetails.do?method=updateMemberDetails')"><IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>
								<A href="javascript:document.regForm.reset()">
									<IMG src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></A>
							<A href="javascript:history.back()"><IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>									
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
		</TABLE>
	</html:form>
</BODY>
</HTML>





						
