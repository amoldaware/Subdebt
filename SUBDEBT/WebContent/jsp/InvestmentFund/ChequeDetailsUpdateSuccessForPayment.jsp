<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<% session.setAttribute("CurrentPage","chequeDetailsUpdateSuccessForPayment.do?method=chequeDetailsUpdateSuccessForPayment");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<form>
<HTML>
<BODY>
<br><br><br><br><br>
<center><H4>Cheque details modified successfully</H4>
<b><A href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>"><IMG src="images/OK.gif" alt="OK" width="49" height="37" border="0"></A> </b>
<p>&nbsp;</p>
<p><br><br><br>
</p>
</BODY>
</HTML>
</form>

