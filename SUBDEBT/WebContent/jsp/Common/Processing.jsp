<%
/**********************************************************************
*Name																	: Main.jsp
*Description													: Main page
*Developer														: Kesavan Srinivasan
*List of pages this asp navigates to 	: TBD
*Creation Date												: Sep 11, 2003
*Last revised													: Sep 11, 2003
*
*Revision history
*
*Modified by		Date Modified		Reason for modification
*Kesavan Srinivasan	Sep 11, 2003		Initial version
*
**********************************************************************/
%>
<%@ page language="java"%>
<% session.setAttribute("CurrentPage","upload.do?method=upload");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<HTML>
	<HEAD Refresh: 10; URL=upload.do?method=upload>
		<TITLE>CGTSI - Organization wide IT Systems</TITLE>
		<meta http-equiv="Refresh" Content="10;URL=upload.do?method=upload">
	</HEAD>
	<BODY bgcolor="#ffffff" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<DIV align="center">
	  <TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
		<TR align="center" valign="middle"> 
		  <TD>
			<TABLE width="56%" border="0" cellspacing="0" cellpadding="0" height="0%" valign=center>
			  <TR align="center"> 
				<TD height="8" class="Processing" colspan="3"> 
				  <p>&nbsp; 
				  </p>
				  <img src="images/Processes.gif" width="60" height="33"><br>
				  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Please 
				  	wait till the process completes....
				  	<br>
				  </TD>
			  </TR>
			</TABLE>
		  </TD>
		</TR>
	  </TABLE>

	</DIV>
	</BODY>
</HTML>
