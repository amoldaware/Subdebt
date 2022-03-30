<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<% session.setAttribute("CurrentPage","showDefOrgStr.do?method=showDefOrgStr");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>

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

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">

<%
// this focusField is a variable which will point to the field which has to be focused in case of no errors.
String focusField="firstName";
org.apache.struts.action.ActionErrors errors = (org.apache.struts.action.ActionErrors)request.getAttribute(org.apache.struts.Globals.ERROR_KEY);
if (errors!=null && !errors.isEmpty())
{
            focusField="test";
}
%>
<body onLoad='init()'>
<html:form action="afterDefOrgStr.do" method="POST" focus="<%=focusField%>" styleId="auto_off">
<html:hidden name="regForm" property="test"/>
<html:errors />
<table width="680" border="0" cellspacing="0" cellpadding="0" align="center">
<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<td width="713">
			<DIV align="right">			
				<A HREF="javascript:submitForm('helpAfterDefOrgStr.do?method=helpAfterDefOrgStr')">
			    HELP</A>
			</DIV>
			<table width="606" border="0" cellspacing="1" cellpadding="0">
			<TD  align="left" colspan="4"><font size="2"><bold>
				Fields marked as </font><font color="#FF0000" size="2">*</font><font size="2"> are mandatory</bold></font>
				</td>
          <tr> 
            <td colspan="2" width="700"><table width="604" border="0" cellspacing="0" cellpadding="0">
												<TD width="31%" class="Heading"><bean:message key="MLIUserHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD> 

								</TR>

          <tr>
		    <TD align="left" valign="top" class="ColumnBackground" >
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="firstName" />
			</TD>
			<TD align="left" valign="top" class="TableData" >										
			<html:text property="firstName" size="20"  alt="Reference"  name="regForm" maxlength="20"/>
			</TD>            
          </tr>

          <tr>
		  <TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="middleName" />
			</TD>
			<TD align="left" valign="top" class="TableData">										
			<html:text property="middleName" size="20"  alt="Reference"  name="regForm" maxlength="20"/>
			</TD>  
            
          </tr>

          <tr>
            <TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="lastName" />
			</TD>
			<TD align="left" valign="top" class="TableData">										
			<html:text property="lastName" size="20"  alt="Reference"  name="regForm" maxlength="20"/>
			</TD>  
          </tr>
          <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="designation" />
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
		   <html:text property="designation" size="20"  alt="Reference"  name="regForm" maxlength="50"/>
										<!-- <html:select property="designation" name="regForm">
											<html:option value="">Select</html:option>
											<html:options property="designations" name="regForm"/>			
										</html:select> -->
									</TD>
          </tr>
		  <tr>
            <TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="bankId" />
			</TD>
			<TD align="left" valign="top" class="TableData">										
			<bean:write property="bankId" name="regForm"/>
			</TD>  
          </tr>
		  <tr>
            <TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="zoneId" />
			</TD>
			<TD align="left" valign="top" class="TableData">										
			<bean:write property="zoneId" name="regForm"/>
			</TD>  
          </tr>
		  <tr>
            <TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="branchId" />
			</TD>
			<TD align="left" valign="top" class="TableData">										
			<bean:write property="branchId" name="regForm"/>
			</TD>  
          </tr>

          <tr>
            <TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="emailId" />
			</TD>
			<TD align="left" valign="top" class="TableData">										
			<html:text property="userEmailId" size="20"  alt="Reference"  name="regForm" maxlength="50"/>
			</TD>  
          </tr>		  
							<TR align="center" valign="baseline" >
						<td colspan="2" width="700"><DIV align="center">
								
								<!-- <A href="javascript:submitForm('afterDefOrgStr.do?method=afterDefOrgStr&flag=1')">
									Add New User</A> -->
								<A href="javascript:submitForm('afterDefOrgStr.do?method=afterDefOrgStr&flag=0')">
									SAVE</A>

									<A href="javascript:document.regForm.reset()">
									<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>
								
							</DIV>
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
</TABLE>
</body>

