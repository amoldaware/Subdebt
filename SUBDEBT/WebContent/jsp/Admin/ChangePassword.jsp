<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>

<script type="text/javascript"> 
        window.history.forward(); 
        function noBack() { 
            window.history.forward(); 
        } 
        function doChanges1(){
            if(document.getElementById("oldPassword")!=null){	
        		var strPassKey=document.getElementById("oldPassword").value;
        		strPassKey=window.btoa(strPassKey);
        		strPassKey=window.btoa(strPassKey);
        		document.getElementById("oldPassword").value=strPassKey;
        		//document.getElementById("newPassword").focus();
        		//alert(strPassKey);
        	}}
            function doChanges2(){
        	if(document.getElementById("newPassword")!=null){	
        		var strPassKey1=document.getElementById("newPassword").value;
        		strPassKey1=window.btoa(strPassKey1);
        		strPassKey1=window.btoa(strPassKey1);
        		document.getElementById("newPassword").value=strPassKey1;
        		//document.getElementById("confirm").focus();
        	}}
            function doChanges3(){        	
        	if(document.getElementById("confirm")!=null){	
        		var strPassKey2=document.getElementById("confirm").value;
        		strPassKey2=window.btoa(strPassKey2);
        		strPassKey2=window.btoa(strPassKey2);
        		document.getElementById("confirm").value=strPassKey2;
        	}	
        }  
    </script> 
    <script type="text/javascript"> 

function doReset1(){
	if(document.getElementById("oldPassword")!=null){
		document.getElementById("oldPassword").value="";
		//document.getElementById("oldPassword").focus();
	}}
function doReset2(){	
	if(document.getElementById("newPassword")!=null){
		document.getElementById("newPassword").value="";
		//document.getElementById("newPassword").focus();
	}}
function doReset3(){
	if(document.getElementById("confirm")!=null){
		document.getElementById("confirm").value="";
		//document.getElementById("confirm").focus();
	}
}
</script>
<script language="javascript"> 
	 var message="Sorry, right-click has been disabled";
	 function clickIE4(){
	     if (event.button==2){
		     alert(message);
		     return false;
	     }
	 }

	 function clickNS4(e){
	     if (document.layers||document.getElementById&&!document.all){
	         if (e.which==2||e.which==3){
	             alert(message);
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
	
	 document.oncontextmenu=new Function("alert(message);return false;");


 
</script> 
	
	<script src="/csrfguard"></script>
<%
	session.setAttribute("CurrentPage","showChangePassword.do");
%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<html:html>
	<head>
				
	</head>
	
	<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
		<html:errors />
		<body>
			<form action="showChangePassword.do" method="POST" autocomplete="off">
				<TABLE width="680" border="0" cellspacing="0" cellpadding="0" align="center">
					<TR> 
						<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
						<TD background="images/TableBackground1.gif"></TD>
						<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
					</TR>
					<TR>
						<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			
						<TD width="713">
			
							<DIV align="right">			
								<A HREF="javascript:submitForm('helpChangePassword.do?method=helpChangePassword')">
							    HELP</A>
							</DIV>
				
							<TABLE width="606" border="0" cellspacing="1" cellpadding="0">
								<TD  align="left" colspan="4"><font size="2"><bold>
									Fields marked as </font><font color="#FF0000" size="2">*</font><font size="2"> are mandatory</bold></font>
								</td>
		         		 		<TR> 
					            	<TD colspan="2" width="700"><TABLE width="604" border="0" cellspacing="0" cellpadding="0">
																	<TD width="31%" class="Heading"><bean:message key="changePasswordHeader" /></TD>
																	<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
																</TR>
																<TR>
																	<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
																</TR>
					
															</TABLE>
														</TD> 
			
								</TR>
			
						         <TR>
									    <TD align="left" valign="top" class="ColumnBackground" >
																	&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="oldPassword" />
										</TD>
										<TD align="left" valign="top" class="TableData" >										
											<!--<html:password property="oldPassword" size="20" maxlength="16"  alt="Reference"  name="adminForm"/>-->
										  <input type="password" class="form-control" name="oldPassword" size="14" id="oldPassword" maxlength="16" onblur="doChanges1()" onfocus="doReset1()" autocomplete="off"/>
										</TD>            
					         	 </TR>
		
							   	<TR>
								    <TD align="left" valign="top" class="ColumnBackground" >
																&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="newPassword" />
									</TD>
									<TD align="left" valign="top" class="TableData" >										
									<!--<html:password property="newPassword" size="20" maxlength="16"  alt="Reference"  name="adminForm"/>-->
									<input type="password" class="form-control" name="newPassword" size="14" id="newPassword" maxlength="16" onblur="doChanges2()" onfocus="doReset2()" autocomplete="off"/>
									</TD>            
					         	 </TR>
		         	 
								   <TR>
									    <TD align="left" valign="top" class="ColumnBackground" >
																	&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="confirm" />
										</TD>
										<TD align="left" valign="top" class="TableData" >										
										<!--<html:password property="confirm" size="20" maxlength="16" alt="Reference"  name="adminForm"/>-->
										<input type="password" class="form-control" name="confirm" size="14" id="confirm" maxlength="16" onblur="doChanges3()" onfocus="doReset3()" autocomplete="off"/>
										</TD>            
						          </TR>
		  
		           
		            
									<TR align="center" valign="baseline" >
										<TD colspan="2" width="700"><DIV align="center">
										
										
										<A href="javascript:submitForm('changePassword.do?method=changePassword&passwordExpiry=<%=session.getAttribute(com.cgtsi.util.SessionConstants.PASSWORD_EXPIRED)%>')">
													<IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>
												<A href="javascript:document.adminForm.reset()">
													<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0">
										</A>
											
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
	
			</form>
		</body>
	</TABLE>
</html:html>
