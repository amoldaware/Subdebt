<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","reportsHome1.do?method=reportsHome1");%>


<TABLE width="725" border="0" cellpadding="0" cellspacing="0" align="center">
	<html:errors />
	<form name="rsForm" action="addInward.do?method=addInward" method="POST" enctype="multipart/form-data">

	<TR>
	<TD width="10%" align="center" valign="top" class="SubHeading">
	<br><br><br><br><br><br>
 	<bean:message key="reports"/>
    </TD>
	</TR>

	<TR>
	<TD width="10%" align="center" valign="top" class="SubHeading">
 	<bean:message key="subMenu"/>
    </TD>
	</TR>