<%@ page language="java"%>
<%@ page import="com.cgtsi.actionform.GMActionForm"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.Iterator"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","showClaimAccountDetails.do?method=showClaimAccountDetails");%>
<%

HashMap hintQuestion=(HashMap)request.getAttribute("claimappsMap");	

Vector retClms=(Vector)hintQuestion.get("retClaims");

String clmrefno= "";
%>
<html:errors />
<html:form action="showClaimAccountDetails.do?method=showClaimAccountDetails" method="POST" enctype="multipart/form-data">
<table width="100%" border="0" cellspacing="0" cellpadding="0">

<tr>
<td class="FontStyle">&nbsp;</td>
</tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
<td align="right" valign="bottom"><img src="images/TableLeftTop.gif" width="20" height="31"></td>
<td background="images/TableBackground1.gif"></td>
<td width="20" align="left" valign="bottom"><img src="images/TableRightTop.gif" width="23" height="31"></td>
</tr>
<tr>
<td background="images/TableVerticalLeftBG.gif">&nbsp;</td>
<td><table width="100%" border="0" cellspacing="1" cellpadding="0">
  <tr>
    <td colspan="4"><table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
	  <td width="35%" class="Heading">&nbsp;Approved Memberid's'</td>	 
	</tr>
	<tr>
	  <td colspan="4" class="Heading"><img src="images/Clear.gif" width="5" height="5"></td>
	</tr>
      </table></td>
  </tr>
  
  <tr> <td>&nbsp;<strong></strong><br> &nbsp; </td> <td>&nbsp;<strong>.</strong><br> &nbsp; </td>
  <% 
  HashMap hintQuestion1=(HashMap)request.getAttribute("claimappsMap");	
  Vector appclms1=(Vector)hintQuestion.get("apprvdClaims");


  System.out.println(appclms1.size());
  Iterator itr = appclms1.iterator();
   while(itr.hasNext())

    {
      clmrefno=(String)itr.next();
      
      %>
       <tr> <td>&nbsp;<strong><%=clmrefno%></strong><br> &nbsp; </td> <td>&nbsp;<strong></strong><br> &nbsp; </td></tr>
       <% 
    }
 
  %>
  
  <tr>
  </tr>
  <tr>
   	 <td width="100%" class="Heading">&nbsp;Returned Memeberid's'</td>
	</tr>
	<tr>  
	 	
	</tr>
	<tr>  
	 	
	</tr>
	
 
  
   <% 
  HashMap hintQuestion2=(HashMap)request.getAttribute("claimappsMap");	
  Vector retClaim=(Vector)hintQuestion.get("retClaims");
  
  System.out.println("retClaim size");
  System.out.println(retClaim.size());
  
  if(retClaim.size()>0)
  {

  Iterator itr1 = retClaim.iterator();
   while(itr1.hasNext())

    {
      clmrefno=(String)itr1.next();
      
      %>
      
       <tr> <td>&nbsp;&nbsp;<strong><%=clmrefno%></strong><br> &nbsp; </td> <td>&nbsp;<strong></strong><br> &nbsp; </td></tr>
       
       <% 
    }
  }
   
   
   
 
   
   
   
   

  %>
 
  
  
  
  
  
  
  
 

 
  <tr align="center" valign="baseline">
    <td colspan="4"> 
    <div align="center">
    <a href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>"><img src="images/OK.gif" width="49" height="37" border="0"></a></div></td>
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
