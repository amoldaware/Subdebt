<!-- 
Description : Bulk Upload for 2.5Cr.
Creaded By  : Deepak Kr Ranjan
Created Date: 14-Apr-2020
 -->
<%@page import="com.cgtsi.application.ApplicationProcessor"%>
<%@page import="com.cgtsi.action.ApplicationProcessingAction"%>
<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean"%>
<%@ page import="com.cgtsi.action.CaptchaSession"%>
<%@ include file="/jsp/SetMenuInfo.jsp"%>

<HTML>
<head>
<script src="/csrfguard"></script>
<%
  session.setAttribute("CurrentPage", "ApplicationInBulkMudraInput.do");
  if(request.getSession(false)!=null){
%>
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
<BODY  onkeydown="return (event.keyCode != 116);">

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:form action="ApplicationInBulkMudraProcess.do?method=ApplicationInBulkMudraProcess"
		method="POST" enctype="multipart/form-data">
		<html:errors />
		<input type="hidden" name="csrfCode" id="csrfCode"  value="<%=CaptchaSession.getCaptchaSession(request)%>"/>
		<TR>
			<TD width="20" align="right" valign="bottom"><IMG
				src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG
				src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>

		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
			<TABLE width="100%" border="0" align="left" cellpadding="0"
				cellspacing="0">
				<TR>
				
					<TD>
				  	<!-- <A target="_blank" href="ApplicationInBulkProcess.do?method=getBulkUploadTemplate" >
					Click here to download Excel Template File</A>-->
					
					<!-- &#9758; --><!-- <A target="_blank" href="Download/BULKUPLOAD_TEMPLAET_2FCr.xls" >
					 Download Excel Template File</A> -->		
					 
					 <font style="color: green; font-size: 15px;">Please use prescribed excel template(Refer user manual) </font>		
					
					<DIV align="right"><A HREF="#"><!-- HELP --></A></DIV><br/>
					<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
						<TR>
							<TD colspan="4">
							<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR>
									<TD width="31%" class="Heading">&nbsp;<bean:message
										key="FileUpload" /></TD>
									<TD><IMG src="images/TriangleSubhead.gif" width="19"
										height="19"></TD>
								</TR>
								<TR>
									<TD colspan="3" class="Heading"><IMG
										src="images/Clear.gif" width="5" height="5"></TD>
								</TR>

							</TABLE>
							</TD>
						</TR>
						<TR>
							<TD colspan="3">
							<font style="color: red; font-size: 15px;">
							      Note: 1] File Format should be .xls  
								  
							</font>
							</TD>
						</TR>
						<TR>
							<TD class="ColumnBackground">&nbsp;&nbsp;<bean:message
								key="ChooseFile" /></TD>							
							<TD class="TableData">
							<div align="left">&nbsp;&nbsp; <html:file property="filePath" name="ioForm" /></div>
							<div align="center" id="waitId" style="display:none;"><font color="red" style="align:center"><b>Please Wait...</b></font><IMG src="images/warten-gif-5.gif" alt="OK" width="79" height="77"
						border="0"></div>
							</td>
						</tr>
						<tr>
							<TD class="ColumnBackground"><font style="color: red; font-size: 11px;">Important Note: </font></TD>							
							<td class="ColumnBackground" >
							
							
							<div>
                                    <font style="color:black; font-size: 11px;">
                            -  Your uploaded file has error pertaining to data format and filed values, please correct and re-upload the same.   
                            </font></div></br>
                            <div><font style="color:black; font-size: 11px;">
                            - Please check your successful uploaded batch status in "Reports and MIS-->Batch Upload Status"   
                            </font></div>
                            </br>
                            <div><font style="color:black; font-size: 11px;">
                             - Your successful submitted file may have some business logics / validations failed, which you will get in processed details. You need to tune failed records as per scheme guidelines and re-upload as fresh file.
                            </font></div>
							<!-- 	<div>
									<font style="color: red; font-size: 11px;">
											Please upload guarantee application records only once. Only Unsuccessful / Error records may be re-uploaded again</font>
											<br><br>									
									<font style="color: green; font-size: 11px;">Successful Records - </font>
									<font style="color: black; font-size: 11px;"> Records uploaded successfully. </font> <br>
									<font style="color: red; font-size: 11px;">Unsuccessful Records - </font> 
									<font style="color: black; font-size: 11px;">Business validations failed.</font> <br>
									<font style="color: red; font-size: 11px;">Error - </font> 
									<font style="color: black; font-size: 11px;">Data not as per Excel Template.</font><br> 
								</div> -->
							</TD>
						</TR>
					</TABLE>
					</TD>
				</TR>
				<TR>
					<TD height="20">&nbsp;</TD>
				</TR>
				<TR>
					<TD align="center" valign="baseline">
					<DIV align="center"><A
						href="javascript:submitForm('ApplicationInBulkMudraProcess.do?method=ApplicationInBulkMudraProcess')">
					<IMG src="images/Upload.gif" alt="OK" width="49" height="37"
						border="0" onclick="callWaiting()"></A> 
						 
						<A href="javascript:document.appForm.reset()">
							<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37"	border="0">
						</A> 
						
						<A href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
					<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37"
						border="0"></A></DIV>
						
					</TD>
				</TR>
			</TABLE>
			</TD>
			<TD width="20" background="images/TableVerticalRightBG.gif">
			&nbsp;</TD>
		</TR>
		<TR>
			<TD width="20" align="right" valign="top"><IMG
				src="images/TableLeftBottom1.gif" width="20" height="15"></TD>
			<TD background="images/TableBackground2.gif">&nbsp;</TD>
			<TD width="20" align="left" valign="top"><IMG
				src="images/TableRightBottom1.gif" width="23" height="15"></TD>
		</TR>
	</html:form>
</TABLE>
</BODY>
 <%
	  }else{		  
		  request.getSession().invalidate();
		  response.sendRedirect(request.getContextPath() + "/showLogin");
	  }
  %>
</HTML>