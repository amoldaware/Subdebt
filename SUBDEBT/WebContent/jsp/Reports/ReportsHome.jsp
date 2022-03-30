<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","reportsHome.do?method=reportsHome");%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0" align="center">
	<html:errors />
	<form name="rsForm" action="addInward.do?method=addInward" method="POST" enctype="multipart/form-data">

	<br><br><br><br><br><br>
	<TR>
	<TD width="10%" align="center" valign="top" class="SubHeading">
 	<bean:message key="reports"/>
    </TD>
	</TR>

	<TR>
	<TD width="10%" align="center" valign="top" class="SubHeading">
 	<bean:message key="menu"/>
    </TD>
	</TR>