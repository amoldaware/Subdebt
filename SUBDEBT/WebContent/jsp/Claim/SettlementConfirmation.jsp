<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<html>
<head>
<meta http-equiv=Content-Type content="text/html; charset=windows-1252">

<title>CGTSI</title>
<style type="text/css">
</style>

</head>
<LINK href="css/StyleSheet.css" rel="stylesheet" type="text/css">
<SCRIPT language="JavaScript" type="text/JavaScript" src="js/selectdate.js"></SCRIPT>
<SCRIPT language="JavaScript" type="text/JavaScript" src="js/CGTSI.js"></SCRIPT>

<body bgcolor="#ffffff" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<div align="center"> <br>
<html:form action="saveSettlementDetails?method=saveSettlementDetails" method="POST" enctype="multipart/form-data">
  <table width="0%" border="0" cellspacing="0" cellpadding="0">
    <tr align="center"> 
      <td> 
        <p><img src="images/RecordAdded.gif" width="111" height="147"> </p>
      </td>
      <td><font face="Arial, Helvetica, sans-serif" size="4" color="#003399">Settlement 
        Details Updated Successfully. <br>
        <br>
        </font> 
        <hr noshade>

        <br>
        </font>
        <font color="#003399" size="3" face="Arial, Helvetica, sans-serif"> <br>
        </font><font size="2" face="Arial, Helvetica, sans-serif"><br>
        <br>
        <a href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>"><img src="images/OK.gif" width="49" height="37" border="0"></a></font> 
      </td>
    </tr>
  </table>
</html:form>  
</div>
</body>
</html>
