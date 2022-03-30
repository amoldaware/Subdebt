<%@ page language="java"%>
<SCRIPT language="JavaScript" type="text/JavaScript" src="<%=request.getContextPath()%>/js/CGTSI.js">
</script>
<%
String contextPath=request.getContextPath();
String passwordExpiryTime=(String)session.getAttribute("pwdDisplayPeriod");
int passwordInt=Integer.parseInt(passwordExpiryTime);
passwordInt*=1000;
%>
<script>
var firstTime=false;
function callHome(number)
{
	var path="<%=contextPath%>/jsp/Home.jsp"
	if(number==2 && !firstTime)
	{
		path="<%=contextPath%>/showLogin.do"
	}
	else
	{
		firstTime=true;
	}

	location.replace(path);
}

</script>

<HTML>
	<HEAD Pragma: no-cache >
		<TITLE>CGTSI - Organization wide IT Systems</TITLE>
		<meta http-equiv="Refresh" Content="<%=passwordExpiryTime%>;URL=home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>">
		<meta HTTP-EQUIV="pragma" CONTENT="no-cache">
	</HEAD>
	<BODY bgcolor="#ffffff" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"
		onLoad="window.setTimeout('callHome(1)',<%=passwordInt%>)" onUnload ="callHome(2)">
	<form name="dummy">
	<DIV align="center">
	  <TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="40%">
		<TR align="center" valign="middle">
		  <TD>
			<TABLE width="56%" border="0" cellspacing="0" cellpadding="0" height="0%" valign=center>
			  <TR align="center">
				<TD height="2" class="Processing" colspan="3">
				  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Password is
				  	<b><% out.println(request.getAttribute("displayPassword")); %></b>
				  </TD>
			  </TR>
			  <TR>
			  	<TD align="center">
					<A href="javascript:submitForm('<%=contextPath%>/showLogin.do')">
					<IMG src="images/OK.gif" alt="Ok" width="49" height="37" border="0"></A>
				</TD>
			  <TR>
			</TABLE>
		  </TD>
		</TR>
	  </TABLE>

	</DIV>
	</form>
	</BODY>
</HTML>
