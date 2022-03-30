<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>

<HTML>
	<head>
		<script type="text/javascript"> 
		  function doChanges1(){
          		if(document.getElementById("hintAnswer")!=null){	
	        		var strPassKey=document.getElementById("hintAnswer").value;
	        		strPassKey=window.btoa(strPassKey);
	        		strPassKey=window.btoa(strPassKey);
	        		document.getElementById("hintAnswer").value=strPassKey;
	        	}
        	}

			//For Crome Browser 
			function changeAutoComplete(){
				if (document.getElementsByTagName) {
					var inputElements = document.getElementsByTagName("input");
					for (i=0; inputElements[i]; i++) {
					if (inputElements[i].className && (inputElements[i].className.indexOf("disableAutoComplete") != -1)) {
						inputElements[i].setAttribute("autocomplete","off");
					}
				}
			}
			//For  Others
			if (!document.getElementById) return false;
				var f = document.getElementById('auto_off');
				var u = f.elements[0];
				f.setAttribute("autocomplete", "off");
				u.focus();
			}
      	</script>
      	
	    <script type="text/javascript"> 

			function doReset1(){
				if(document.getElementById("hintAnswer")!=null){
					document.getElementById("hintAnswer").value="";
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
	
	</head>
	<body onload="changeAutoComplete()">
		<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
		<html:errors />
		<form action="answerHintQuestion.do" method="POST" autocomplete="off">
			<TR> 
				<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
				<TD background="images/TableBackground1.gif"></TD>
				<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
			</TR>
			<TR>
				<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
				<TD width="713">
					<TABLE width="606" border="0" cellspacing="1" cellpadding="0">
						<TR> 
							<TD colspan="2" width="700">
								<TABLE width="604" border="0" cellspacing="0" cellpadding="0">
									<TR>
										<TD width="40%" class="Heading"><bean:message key="answerHintHeader" /></TD>
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
							&nbsp;<bean:message key="hintQuestion" />
							</TD>
							<TD align="left" valign="top" class="TableData" >										
								<html:text property="hintQuestion" size="20"  alt="Reference"  name="adminForm"/>
							</TD>            
				  		</TR>

				  	 	<TR>
							<TD align="left" valign="top" class="ColumnBackground" >
									&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="hintAnswer" />
							</TD>
							<TD align="left" valign="top" class="TableData" >										
								<!--<html:text property="hintAnswer" size="20"  alt="Reference"  name="adminForm"/>-->
								<input type="password" class="form-control" name="hintAnswer" size="20" id="hintAnswer" maxlength="30" onblur="doChanges1()" onfocus="doReset1()" autocomplete="off"/>
								
							</TD>            
							<TD>
							<html:hidden property="userId" name="adminForm"/>
							</TD>
				  		</TR>
						</TR>
						<TR align="center" valign="baseline" >
							<TD colspan="2" width="700">
								<DIV align="center">
								<A href="javascript:submitForm('answerHintQuestion.do?method=answerHintQuestion')">
									<IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>
									<A href="javascript:document.adminForm.reset()">
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
		</form>
		</TABLE>
	</BODY>
</HTML>		

