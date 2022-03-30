<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<% session.setAttribute("CurrentPage","showModifyExposureLimit.do?method=showModifyExposureLimit");%>
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
<html:errors />
<body onLoad='init()'>
<html:form action="showModifyExposureLimit.do" method="POST" focus="member" styleId="auto_off">
<table width="680" border="0" cellspacing="0" cellpadding="0" align="center">
<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<td width="713">
	
			<table width="606" border="0" cellspacing="1" cellpadding="0">
			<TD  align="left" colspan="4"><font size="2"><bold>
				Fields marked as </font><font color="#FF0000" size="2">*</font><font size="2"> are mandatory</bold></font>
				</td>
				</tr>
          <tr> 
            <td colspan="2" width="700"><table width="604" border="0" cellspacing="0" cellpadding="0">
												<TD width="31%" class="Heading">Modify Exposure Limits </TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD> 

								</TR>   
          </tr>
          <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="member" />
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
										<html:select property="member" name="regForm">
											<html:option value="">Select</html:option>
											<html:options property="cbMembers" name="regForm"/>			
										</html:select>
									</TD>
          </tr>
		  
							<TR align="center" valign="baseline" >
						<td colspan="2" width="700"><DIV align="center">
								<A href="javascript:submitForm('modifyExposureLimits.do?method=modifyExposureLimits')">
									<IMG src="images/OK.gif" alt="Save" width="49" height="37" border="0"></A>
								<A href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>"><IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>
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
</body>


