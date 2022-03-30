<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.cgtsi.actionform.*"%>
<%@page import="java.util.HashSet"%>
<%@page import ="java.text.SimpleDateFormat"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@page import ="java.text.DecimalFormat"%>
<%@ page import="com.cgtsi.claim.ClaimConstants"%>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">

<script type="text/javascript">

</script>
		<html:form name = "appropriationActionForm" type="com.cgtsi.actionform.AppropriationActionForm"  action="displayAppropriationDetails.do?method=displayCgpanDetails" method="POST" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" style="margin-top:-5px"> 
			
				<thead>
					<tr>
					<th valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>CGPAN
                      </strong><br></div></th>
					<th valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>DAN ID
                      </strong><br></div></th>
                      <th valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>GUARANTEE FEE
                      </strong><br></div></th>
                      <th valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>UNIT NAME
                      </strong><br></div></th>
                               
					</tr>
				</thead>
			<tbody>
				 
			  <% int d1=1; %>
	         <logic:iterate id="object" name="appropriationActionForm" property="popupData" indexId="index">
				<%
				AppropriationActionForm rReport = (AppropriationActionForm)object;
				DecimalFormat dec = new DecimalFormat("#0.00");
				%>
			 <tr>
				  <TD width="25%" align="left" valign="top" class="ColumnBackground1">
                    	 <div align="center"><%=rReport.getCgpan()%> </div>
                    	 </TD>  
                
               <TD width="25%" align="left" valign="top" class="ColumnBackground1">
                    	 <div align="center"><%=rReport.getDanid()%> </div>
                    	 </TD>  
               <TD width="25%" align="left" valign="top" class="ColumnBackground1">
                    	 <div align="center"><%=rReport.getGuaranteeFee()%> </div>
                    	 </TD>  
			  <TD width="25%" align="left" valign="top" class="ColumnBackground1">
                    	 <div align="center"><%=rReport.getUnitNameRP()%> </div>
                    	 </TD>  
               </tr> 
         	              <%d1++; %> 
			 	  </logic:iterate>	 
               	
			</tbody>			
			</table>
</html:form>