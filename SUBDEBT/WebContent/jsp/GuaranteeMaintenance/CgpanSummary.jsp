<%@ page language="java"%>
<%@ page import="com.cgtsi.claim.ClaimConstants"%>
<%@ page import="com.cgtsi.actionform.*"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.ArrayList"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@ page import="com.cgtsi.util.SessionConstants"%>	
<%

	session.setAttribute("CurrentPage","getIdForUpdateGSTIN.do?method=getIdForUpdateGSTIN");
%>

<html:errors />
<html:form action="getIdForUpdateGSTINDetails.do?method=getIdForUpdateGSTINDetails" method="POST" enctype="multipart/form-data">
<table width="100%" border="0" cellspacing="0" cellpadding="0">

<tr>
<td class="FontStyle">&nbsp;</td>
</tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
<TD background="images/TableBackground1.gif"></TD>
<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
</tr>
<tr>
<td background="images/TableVerticalLeftBG.gif">&nbsp;</td>
<td><table width="100%" border="0" cellspacing="1" cellpadding="0">
  <tr>
    <td colspan="4"><table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
	  <td width="35%" class="Heading">&nbsp;GSTIN Updated Successfully </td>	 
	</tr>
	<tr>

	  <td colspan="4" class="Heading"><img src="images/Clear.gif" width="5" height="5"></td>
	
	   <td class="Heading" colspan="4">
				  <logic:iterate name="cgpanArray" id="ClaimExpirydate">					 	
						<option value="<bean:write name="ClaimExpirydate"/>"><br></br><bean:write name="ClaimExpirydate"/></option>					
				  </logic:iterate>				
				   	  </TD>
				  </tr>  
  </table>

 
  <tr align="center" valign="baseline">
    <td colspan="4"> 
    <div align="center">
    <a href="getIdForUpdateGSTIN.do?method=getIdForUpdateGSTIN"><img src="images/OK.gif" width="49" height="37" border="0"></a></div></td>
  </tr>
</table></td>
<td background="images/TableVerticalRightBG.gif">&nbsp;</td>
</tr>
<tr>
<td width="20" align="right" valign="top"><img src="images/TableLeftBottom1.gif" width="20" height="15"></td>
<td background="images/TableBackground2.gif"><div align="center"></div></td>
<td align="left" valign="top"><img src="images/TableRightBottom1.gif" width="23" height="15"></td>
</tr>
</table>




</html:form>
</body>
</html>
