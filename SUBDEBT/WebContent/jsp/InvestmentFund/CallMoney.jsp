
<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","showInflowDetails.do");%>

<html:form action="updateBudgetHeadMaster.do?method=updateBudgetHeadMaster" method="POST" enctype="multipart/form-data">

  <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
    <tr> 
		<td align="center" class="subHeading2">Details Are Awaited</td>
	</tr>
  </table>
</html:form>
