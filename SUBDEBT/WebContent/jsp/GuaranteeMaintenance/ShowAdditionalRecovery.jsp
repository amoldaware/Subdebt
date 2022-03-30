<%@ page language="java"%>
<% session.setAttribute("CurrentPage","showAdditionalRecovery.do");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>


<form>
<HTML>

<BODY>
<br><br><br><br><br>
<b><center><H3>RECOVERY DETAILS SAVED SUCCESSFULLY</H3></b>
<b>To Proceed to another Recovery<A href="javascript:submitForm('showAdditionalRecovery.do?method=showAdditionalRecovery')">Click Here</A>
<br></b><br>
<br><br><br>
<b><A href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">Exit</A> </b>
<p>&nbsp;</p>
<p><br><br><br>
</p>
</BODY>
</HTML>
</form>