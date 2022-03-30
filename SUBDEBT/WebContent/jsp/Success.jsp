<!-- Page  : Success.jsp
  UpdatedBy: Deepak@PATH
 -->

<%@page import="java.util.HashMap"%>
<%@ page language="java"%>
<% session.setAttribute("CurrentPage","showSuccessPage.do");%>
<%@ include file="/jsp/SetMenuInfo.jsp"%>
<html>
<head>
<script language="javascript">  
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
               
</script>

<title><bean:message key="success" /></title>

</head>
<body onkeydown="return (event.keyCode != 116);">
	<form>
		<table>
			<tr>
				<td width="755" align="center" valign="bottom" height="100">
					<% 
					if(request.getSession(false)!=null){
						
	 String message="Action Successful.";
			 if(request.getAttribute("dMessage")!=null){
				message = (String)request.getAttribute("dMessage").toString();	
			 }
			  if(request.getAttribute("message")!=null)
			 {
			   message=(String)request.getAttribute("message");
			 }		     	       
			 out.println("<font color='blue'>"+message+"</font>");
	%>
				</td>
			</tr>
			<TR>
				<TD width="755" align="center" valign="bottom">
					<%	
	   String subMenuItem=(String)session.getAttribute("subMenuItem");
	  if(subMenuItem!=null && !subMenuItem.equals("")){
	%> <A
					href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
						<%}else{%> <A
						href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>">
							<%}%> <IMG src="images/OK.gif" width="49" height="37" border="0">
							<!--
	<html:img page="/images/OK.gif" width="49" height="37" border="0"/>
	!-->
					</A>
				</TD>
			</TR>
		</TABLE>
		<%
	  }else{		  
		 // request.getSession().invalidate();
		  //response.sendRedirect(request.getContextPath() + "/showLogin");
	  }
  %>
	</form>
</body>
</html>
