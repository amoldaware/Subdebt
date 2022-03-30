<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<html>
<head>
<meta http-equiv=Content-Type content="text/html; charset=windows-1252">
<title>CGTSI</title></head>
<SCRIPT language="JavaScript" type="text/JavaScript" src="js/CGTSI.js"></SCRIPT>
<LINK href="css/StyleSheet.css" rel="stylesheet" type="text/css">

<body bgcolor="#ffffff" topmargin="0">
<div align="center"><br>
  <table width="0%" border="0" cellspacing="0" cellpadding="0">
    <tr align="center"> 
      <td> 
        <p><img src="images/RecordAdded.gif" width="111" height="147"> </p>
      </td>
      <td><font face="Arial, Helvetica, sans-serif" size="4" color="#003399"><bean:message key="cpActionSuccessfulMsg"/><br>
        </font> 
        <hr noshade>
        <br>
        </font><font size="2" face="Arial, Helvetica, sans-serif">
        <a href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>"><br>
        <img src="images/OK.gif" alt="Close" width="49" height="37" border="0"></a></font> 
      </td>
    </tr>
  </table>
</div>
</body>
</html>
