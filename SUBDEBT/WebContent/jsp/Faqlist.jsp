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
<script type="text/javascript">



</script>

   <html>
    <head>
        <meta http-equiv="Content-Type"
              content="text/html; charset=windows-1252"></meta>
       
 </head>
   
    <html:errors />
	<html:form action="displayClaimProcessingSubmitDU.do?method=displayClaimProcessingSubmitDU" method="POST" enctype="multipart/form-data">
    <LINK href="<%=request.getContextPath()%>/css/StyleSheet.css" rel="stylesheet" type="text/css">
      <td colspan="12">
<marquee style="color:red;" scrollamount="200" scrolldelay="1200">
<b> All Fields Are Mandatory </b></marquee>
</td> 
 
  <tbody>

                           <%
								int j=0;
                                int k=0;
								%>
								
				<logic:iterate id="object" name="cpTcDetailsForm" property="claimDandU" indexId="index">
							<%
								ClaimActionForm   cReport = (ClaimActionForm)object;
								
								%>
	
								
			 <TD width="10%" align="left" valign="top" >						
	             <%=cReport.getBranchname()%>
	           </TD>
	              <TD width="10%" align="left" valign="top" >						
	             <%=cReport.getBranchname()%>
	           </TD>
	           
	              <TD width="10%" align="left" valign="top">						
	             <%=cReport.getBranchname()%>
	           </TD>
				 </logic:iterate>				  
       
                <tr>
                  <th class="SubHeading">S.No</th>
                    <th class="SubHeading">Description</th>
                    <th class="SubHeading">Yes/No</th>
                      <th class="SubHeading">Comments/Observations</th>
                </tr>
                    
                  
          
         </html:form>
    
       
</html>