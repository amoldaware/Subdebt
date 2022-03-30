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
/* window.onload = function() {
	loadData();
	};
function loadData (){
	const queryString = window.location.search;
	var value = queryString.split("=")
	var val = value[1];
 	makePaymentOK('displayallocatePaymentFinal.do?method=displayCgpanDetails');
} */
function makePaymentOK(action)
{	 
	alert("makepaymentok");
	debugger;
	document.forms[0].action=action;
 	document.forms[0].target="_self";
 	document.forms[0].method="POST";
 	document.forms[0].submit();
}
</script>
		<html:form name = "rpAllocationForm" type="com.cgtsi.actionform.RPActionForm"  action="displayallocatePaymentFinal.do?method=displayCgpanDetails" method="POST" >
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
	         <logic:iterate id="object" name="rpAllocationForm" property="allocatepaymentFinal" indexId="index">
				<%
				String name5="0";
				String name6="0";
				 RPActionForm rReport = (RPActionForm)object;
				DecimalFormat dec = new DecimalFormat("#0.00");
				%>
			 <tr>
				  <TD width="25%" align="left" valign="top" class="ColumnBackground1">
                    	 <div align="center"><%=rReport.getCgpan()%> </div>
                    	 </TD>  
                
               <TD width="25%" align="left" valign="top" class="ColumnBackground1">
                    	 <div align="center"><%=rReport.getDanid1()%> </div>
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