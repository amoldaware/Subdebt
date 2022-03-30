<%@ page language="java"%>
<% session.setAttribute("CurrentPage","sendQueryRequest.do?method=sendQueryRequest");%>
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
	
	String currentPage=(String)session.getAttribute("CurrentPage");

	String url=request.getRequestURL().toString();
	
	
		String message="Action Successful";
	
		if(request.getParameter("message")!=null)
		{
			message=(String)request.getParameter("message");
		}
		
		if(request.getAttribute("message")!=null)
		{
			message=(String)request.getAttribute("message");
		}
	
		out.println(message);
		
	%>
</td>

<TR>
<TD width="755" align="center" valign="top">

	<a href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>">
								<IMG src="images/OK.gif" alt="ok" width="49" height="37" border="0"></A>

	
</TR>

</tr>
<TR> 
<TD width="755" align="center" valign="bottom">
	<%
	
	String subMenuItem=(String)session.getAttribute("subMenuItem");
	if(subMenuItem!=null && !subMenuItem.equals("")){
	%>
<!--	<A href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">-->
	<%}else{%>
<!--	<A href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>">-->
	<%}%>
<!--	<IMG src="images/OK.gif" width="49" height="37" border="0">-->
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

