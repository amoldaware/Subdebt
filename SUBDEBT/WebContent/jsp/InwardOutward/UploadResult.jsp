<%@ page language="java"%>
<% session.setAttribute("CurrentPage","uploadResult.do?method=uploadResult");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<html> 
<head>
    <title><bean:message key="success" /> </title>
</head>
<body>
<form>
<table>
<tr>
<td width="755" align="center" valign="bottom" height="100">
	<%
		String message="Document Uploaded Successfully";
		
		if(request.getAttribute("message")!=null)
		{
			message=(String)request.getAttribute("message");
		}
		out.println(message);
	%>
</td>
</tr>
<TR>
<TD width="755" align="center" valign="bottom">
	<A href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>">
	<IMG src="images/OK.gif" width="49" height="37" border="0">
	<!--
	<html:img page="/images/OK.gif" width="49" height="37" border="0"/>
	!-->
	</A>
</TD>
</TR>
</TABLE>
</form>
</body>
</html>

