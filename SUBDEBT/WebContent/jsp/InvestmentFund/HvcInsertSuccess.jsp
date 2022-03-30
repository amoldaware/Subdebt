<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<% session.setAttribute("CurrentPage","hvcInsertSuccess.do?method=hvcInsertSuccess");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<form>
<HTML>

<BODY>
<br><br><br><br><br>
<b><center><H3>Hvc details added successfully</H3></b>
<b>To add more details<A href="hvcInsertInput.do?method=hvcInsertInput">Click Here</A>
<br></b><br>
<br><br><br>
<b><A href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">Exit</A> </b>
<p>&nbsp;</p>
<p><br><br><br>
</p>
</BODY>
</HTML>
</form>