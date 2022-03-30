<%@ page language="java"%>
<%
String contextPath=request.getContextPath();
%>
<HTML>
	<BODY>
		<form name="incorrectHintAnswer" action="/showLogin.do">
		<BR>
		<BR>
		<BR>
		<BR>
		<BR>
		<BR>
		
		<%-- This is a comment. will not be visible in HTML file--%>
		
			<DIV align="center">
			Invalid Hint answer. Press ok to log in.
			<BR>
			<A href="<%=contextPath%>/showLogin.do">
			<IMG src="images/OK.gif" alt="Ok" width="49" height="37" border="0"></A>
			</DIV>
		</form>
	</BODY>
	
</HTML>
