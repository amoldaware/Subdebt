<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%session.setAttribute("CurrentPage","forwardToNextPage.do?method=forwardToNextPage");%>

<html:errors/>
<html:form action="proceedFromRecoveryFilterPage.do?method=proceedFromRecoveryFilterPage" method="POST" enctype="multipart/form-data">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    
    <tr>
      <td class="FontStyle">&nbsp;</td>
    </tr>
  </table>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td width="20" align="right" valign="bottom"><img src="images/TableLeftTop.gif" width="20" height="31"></td>
      <td width="248" background="images/TableBackground1.gif"></td>
    <td align="right" valign="top" background="images/TableBackground1.gif"> </td>
    <td width="23" align="left" valign="bottom"><img src="images/TableRightTop.gif" width="23" height="31"></td>
  </tr>
  <tr>
    <td width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</td>
    <td colspan="2">
    <DIV align="right">			
	<A HREF="javascript:submitForm('helpRecoveryFltr.do')">
	HELP</A>
    </DIV>        
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td> <table width="100%" border="0" cellspacing="1" cellpadding="0">
                <tr>
                  <td colspan="4"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		      <tr>
		       <%                   
			 if(((String)session.getAttribute("mainMenu")).equals(com.cgtsi.admin.MenuOptions.getMenu(com.cgtsi.admin.MenuOptions.CP_CLAIM_FOR)))
			 {
                       %>
			 <td width="25%" class="Heading">&nbsp;Recovery Filter</td>
		       <% 
		         }
		         if(((String)session.getAttribute("mainMenu")).equals(com.cgtsi.admin.MenuOptions.getMenu(com.cgtsi.admin.MenuOptions.GM_PERIODIC_INFO))){
		       %>
		       <td width="25%" class="Heading">&nbsp;OTS Filter</td>
		       <%
		         }
		       %>
			 <td align="left" valign="bottom"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
			 <td>&nbsp;</td>
			 <td>&nbsp;</td>
		      </tr>
                      <tr>
                        <td colspan="4" class="Heading"><img src="images/Clear.gif" width="5" height="5"></td>
                      </tr>
                    </table></td>
                </tr>
                <%                   
                   if(((String)session.getAttribute("mainMenu")).equals(com.cgtsi.admin.MenuOptions.getMenu(com.cgtsi.admin.MenuOptions.CP_CLAIM_FOR)))
                   {
                %>
                     <tr colspan="2">
			 <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="recoveryfilter"/></div></td>
			  <td  class="TableData"><div align="left">
			  <html:radio property="recoveryFlag" name="cpTcDetailsForm" value="Y"><bean:message key="otsyes"/></html:radio>
			  <html:radio property="recoveryFlag" name="cpTcDetailsForm" value="N"><bean:message key="otsno"/></html:radio>
			  </div></td>			  			  			 			  
		     </tr>
		 <% 
		 }else if(((String)session.getAttribute("mainMenu")).equals(com.cgtsi.admin.MenuOptions.getMenu(com.cgtsi.admin.MenuOptions.GM_PERIODIC_INFO))){
		 %>
		      <tr colspan="2">
			 <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="otsfilter"/></div></td>
			  <td  class="TableData"><div align="left">
			  <html:radio property="otsFlag" name="cpTcDetailsForm" value="Y"><bean:message key="otsyes"/></html:radio>
			  <html:radio property="otsFlag" name="cpTcDetailsForm" value="N"><bean:message key="otsno"/></html:radio>
			  </div></td>			  			  			 			  
		     </tr>		 
		 <%}%>		 
               <tr>
                  <td colspan="3"><img src="images/Clear.gif" width="5" height="15"></td>
                </tr>
              </table>
      </table></td>
    <td width="23" background="images/TableVerticalRightBG.gif">&nbsp;</td>
  </tr>
  <tr>
      <td width="20" align="right" valign="bottom"><img src="images/TableLeftBottom.gif" width="20" height="51"></td>
      <td colspan="2" valign="bottom" background="images/TableBackground3.gif">
        <div>
          <div align="center">          
          <A href="javascript:submitForm('proceedFromRecoveryFilterPage.do?method=proceedFromRecoveryFilterPage')">
<img src="images/OK.gif" alt="Apply" width="49" height="37" border="0"></a>                    
          </div>
      </div></td>
      <td width="23" align="right" valign="bottom"><img src="images/TableRightBottom.gif" width="23" height="51"></td>
  </tr>
</table>
</html:form>
</body>
</html>
