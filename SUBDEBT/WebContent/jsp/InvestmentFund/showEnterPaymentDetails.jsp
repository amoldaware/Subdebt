<%@ page language="java"%>
<% session.setAttribute("CurrentPage","saveEnterPaymentDetails.do");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>


<form>
<HTML>

<BODY>
<br><br><br><br><br>
<b><center><H5>PAYMENT DETAILS SAVED SUCCESSFULLY</H5></b>
<A href="javascript:submitForm('showEnterPaymentDetails.do?method=showEnterPaymentDetails')">
<b>Add More Payment Details<br></A>
<br></b>
<b><A href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">Exit</A> </b>
<p>&nbsp;</p>
<p><br><br><br>
</p>
</BODY>
</HTML>
</form>