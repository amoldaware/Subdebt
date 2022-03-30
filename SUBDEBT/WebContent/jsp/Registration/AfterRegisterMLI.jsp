<%@ page language="java"%>
<%@ page import="com.cgtsi.action.CaptchaSession"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>

<% session.setAttribute("CurrentPage","registerMLI.do?method=registerMLI");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<head>
<script type="text/javascript"> 
        window.history.forward(); 
        function noBack() { 
            window.history.forward(); 
        } 

        
        function clickIE4(){
            if (event.button==2){
                             //  alert(message);
                               return false;
                               }
                 }
   
                 function clickNS4(e){
                               if (document.layers||document.getElementById&&!document.all){
                                              if (e.which==2||e.which==3){
                                                       // alert(message);
                                                        return false;
                               }
                       }
                 }
   
                 if (document.layers){
                               document.captureEvents(Event.MOUSEDOWN);
                               document.onmousedown=clickNS4;
                 }
   
                 else if (document.all&&!document.getElementById){
                               document.onmousedown=clickIE4;
                 }   
                 document.oncontextmenu=new Function(";return false;");
                 
                 
                 function callWaiting(){
                		document.getElementById("waitId").style.display="block";
                	} 
                 
           //=====================================================================
    function disableselect(e) {
        return false;
    }

	function reEnable() {
	    return true;
	}

document.onselectstart = new Function("return false");

if (window.sidebar) {
    document.onmousedown = disableselect;
    document.onclick = reEnable;
}
</script> 
</head>
<body onkeydown="return (event.keyCode != 116);">
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
<html:errors />

<html:form action="afterRegisterMLI.do" method="POST" focus="firstName">

<input type="hidden" name="csrfCode" id="csrfCode"  value="<%=CaptchaSession.getCaptchaSession(request)%>"/>
<table width="680" border="0" cellspacing="0" cellpadding="0" align="center">
<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<td width="713"><table width="606" border="0" cellspacing="1" cellpadding="0">
			<TD  align="left" colspan="4"><font size="2"><bold>
				Fields marked as </font><font color="#FF0000" size="2">*</font><font size="2"> are mandatory</bold></font>
				</td>
				</tr>
          <tr> 
            <td colspan="2" width="700"><table width="604" border="0" cellspacing="0" cellpadding="0">
												<TD width="31%" class="Heading"><bean:message key="afterMLIHeader" /></TD>
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
										<!-- <html:select property="designation" name="regForm">
											<html:option value="">Select</html:option>
											<html:options property="designations" name="regForm"/>
											
										</html:select> -->
					<html:text property="designation" size="20"  alt="Reference"  name="regForm" maxlength="50"/>
									</TD>
          </tr>
		  <tr>
            <TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="noEmailId" />
			</TD>
			<TD align="left" valign="top" class="TableData">										
			<html:text property="noEmailId" size="20"  alt="Reference"  name="regForm" maxlength="50"/>
			</TD>  
          </tr>	
           <tr>
            <TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font>Mobile No.
			</TD>
			<TD align="left" valign="top" class="TableData">										
			<html:text property="mobileNo" size="20"   onkeyup="isValidNumber(this)"  name="regForm" maxlength="10"/>
			</TD>  
          </tr>		  
							<TR align="center" valign="baseline" >
						<td colspan="2" width="700"><DIV align="center">

						<A href="javascript:submitForm('afterRegisterMLI.do?method=afterRegisterMLI')">
									<IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>
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
		
	</html:form>
</TABLE>
</body>


