<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>


<script type="text/javascript"> 
        window.history.forward(); 
        function noBack() { 
            window.history.forward(); 
        } 
</script> 
<script type="text/javascript">
function init() {
if (!document.getElementById) return false;
var f = document.getElementById('auto_off');
var u = f.elements[0];
f.setAttribute("autocomplete", "off");
u.focus();
}
</script>

<script src="/csrfguard"></script>


<%  String currentPage;
	String user=(String)session.getAttribute("loggedInUser");
	System.out.println(user);
	if (user.equals("CGTSI"))
	{
		currentPage="memberSelected.do?method=memberSelected";
	}
	else{
		
		currentPage="showDefOrgStr.do?method=showDefOrgStr";
	} %>

<% session.setAttribute("CurrentPage",currentPage);%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%
String focusflag = "";
if(request.getAttribute("district")!=null && request.getAttribute("district").equals("1"))
{
	focusflag="district";
}
else{

	focusflag="zoneName";
}
%>

<body onLoad="setRZonesEnabled(),danDelivery(),init()">
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
<%
// this focusField is a variable which will point to the field which has to be focused in case of no errors.
String focusField=focusflag;
org.apache.struts.action.ActionErrors errors = (org.apache.struts.action.ActionErrors)request.getAttribute(org.apache.struts.Globals.ERROR_KEY);
if (errors!=null && !errors.isEmpty())
{
            focusField="test";
}
%>
<html:form action="defOrgStr.do" method="POST" focus="<%=focusField%>" styleId="auto_off">
<html:hidden name="regForm" property="test"/>
<html:errors />
	
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
			<DIV align="right">			
				<A HREF="javascript:submitForm('helpDefOrgStr.do?method=helpDefOrgStr')">
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
												<TD width="31%" class="Heading"><bean:message key="defineOrgStructHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>
								</TR>

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground" width="40%">
										&nbsp;<bean:message key="bankId" />
									</TD>
									<TD  align="left" class="TableData"> 
										<bean:write property="bankId" name="regForm"/>
									</TD>
								</TR>
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground" width="40%">
										&nbsp;<bean:message key="select" />
									</TD>
									<TD  align="left" class="TableData"> 
										<html:radio name="regForm" value="ZO" property="setZoRo" onclick="setRZonesEnabled(this)" ><bean:message key="newZone" /></html:radio>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										&nbsp;&nbsp;&nbsp;&nbsp;
										
										<%-- <html:radio name="regForm" value="RO" property="setZoRo" onclick="setRZonesEnabled(this)" ><bean:message key="newRegion" /></html:radio>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; --%>
										<html:hidden name="regForm" value="RO" property="setZoRo" onclick="setRZonesEnabled(this)" ></html:hidden>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																				
										<%-- <html:radio name="regForm" value="NBR" property="setZoRo" onclick="setRZonesEnabled(this)" ><bean:message key="newBranch" /></html:radio>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; --%>
										<html:hidden name="regForm" value="NBR" property="setZoRo" onclick="setRZonesEnabled(this)" ></html:hidden>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<%-- <html:hidden name="regForm" value="BRB" property="setZoRo" onclick="setRZonesEnabled(this)" ><bean:message key="branchToBank" /></html:hidden> --%>
										<html:hidden name="regForm"  property="setZoRo"  ></html:hidden>
									</TD>

								</TR>
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="zoRoName" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="zoneName" size="20" alt="ZO/RO Name" name="regForm" maxlength="100"/>	
									
										</TD>
									
								</TR> 
								<%-- <TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="region" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:select property="reportingZone" name="regForm" >
											<html:option value="">Select</html:option>					
											<html:options property="reportingZones" name="regForm"/> 						
										</html:select>
									</TD>
								</TR>  --%>
							<%-- 	<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="CBbranchName" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="branchName" size="20" alt="Branch Name" name="regForm" maxlength="100"/>
										<bean:message key="selectZone" />
										<html:select property="zoneList" name="regForm" >
											<html:option value="">Select</html:option>					
											<html:options property="reportingZones" name="regForm"/>
										</html:select>
									</TD>
								</TR>  --%>
								

								<!-- <TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="zoRoName" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="zoneName" size="20" alt="ZO/RO Name" name="regForm"/>&nbsp;&nbsp;
										<html:radio name="regForm" value="ZO" property="setZoRo" onclick="setRZonesEnabled(this)" ><bean:message key="zo" /></html:radio>&nbsp;&nbsp;
										<html:radio name="regForm" value="RO" property="setZoRo" onclick="setRZonesEnabled(this)" ><bean:message key="ro" /></html:radio>&nbsp;&nbsp;
										<bean:message key="ho" />&nbsp;&nbsp;
										<html:select property="reportingZone" name="regForm" >
											<html:option value="">Select</html:option>					
											<html:options property="reportingZones" name="regForm"/> 						
										</html:select>
									</TD>
								</TR>
									
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="CBbranchName" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="branchName" size="20" alt="Branch Name" name="regForm"/>
									</TD>
								</TR> -->

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="address" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:textarea property="address" cols="20" alt="Branch/Zone Address" name="regForm"/>
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
										<html:select property="state" name="regForm" onchange="javascript:submitForm('showDefOrgStr.do?method=getDistricts')">
											<html:option value="">Select</html:option>
											<html:options property="states" name="regForm"/>			
										</html:select>
									</TD>
								</TR>
								
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="brnGstNo" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="brnGstNo" size="20" alt="brnGstNo" name="regForm" maxlength="15"/>
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
										<html:text property="pin" size="5" alt="pinCode" name="regForm" maxlength="6" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>
									</TD>
								</TR>	

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="phoneNo" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="phoneStdCode" size="10" maxlength="10" alt="Phone No" name="regForm" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>&nbsp;&nbsp;-&nbsp;&nbsp;
										<html:text property="phone" size="10" maxlength="20" alt="Phone No" name="regForm"  onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>
									</TD>
								</TR>													

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="faxNo" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="faxStdCode" size="10" maxlength="10" alt="Fax No" name="regForm" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>&nbsp;&nbsp;-&nbsp;&nbsp;
										<html:text property="fax" size="10" maxlength="20" alt="Fax No" name="regForm" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>
									</TD>
								</TR>								

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="emailId" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="emailId" size="20" maxlength="50" alt="Email Address" styleId="emailId" name="regForm"/>
									</TD>
								</TR>
								<html:hidden property="danDelivery" name="regForm"></html:hidden>
								<%-- <TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="deliveryOfDAN"/>
									</TD>
									<TD align="left" valign="top" class="ColumnBackground">
										<html:multibox name="regForm" value="Mail" property="danDelivery"/><bean:message key="mail"/>
										<html:multibox name="regForm" value="EMail" property="danDelivery"/><bean:message key="eMail"/>
										<html:multibox name="regForm" value="HardCopy" property="danDelivery"/><bean:message key="hardCopy"/>	
									</TD>
								</TR> --%>
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
							<A href="javascript:submitForm('defOrgStr.do?method=defOrgStr'),checkEmailFormatValid();"><IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>
								<A href="javascript:document.regForm.reset()">
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
		</TABLE>
	</html:form>
</body>




						
