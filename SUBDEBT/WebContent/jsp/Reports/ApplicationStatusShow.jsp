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
<script src="/csrfguard"></script>

 
 
 
 
 Guarantee will start only after remittance fee. For Fee payment please follow the path Receipts and Payments--> GF payment through NEFT/RTGS -->Initiate Payment
  <TABLE width="725" border="0" cellpadding="0" cellspacing="0">
    <html:errors />
	<html:form name = "reportForm" type="com.cgtsi.actionform.ReportActionForm"  action="showApplicationStatus.do?method=showApplicationStatus" method="POST" >
	<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	   <TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/ReportsHeading.gif" width="121" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="16"> 
										 
            
	
			<tr>
	       			<td valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>Application Ref NO
                      	</strong><br></div></td>
                 
                	 <td valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>UNIT NAME
                      	</strong><br></div></td>
                 
          			<td valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>CGPAN</strong><br></div></td>
                  
          			<td valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>GUARANTEE APPROVED AMOUNT</strong><br></div></td>
                        
           			<td valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>DAN_ID</strong><br></div></td>
           
           			<td valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>GUARANTEE FEE AMOUNT</strong><br></div></td>
           
           			<!-- <td valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>GUARANTEE START DATE</strong><br></div></td> -->
                    <td valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>REALISED DATE</strong><br></div></td>
                               
            		<td valign="top"  class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>APP STATUS</strong></div></td>
                      
                      
                         <td valign="top"  class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>RP NUMBER
                      </strong></div></td>
                      
                      <td valign="top"  class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>APPROPRIATION DATE
                      </strong></div></td>
                      
                      <td valign="top"  class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>APPROPRIATED BY
                      </strong></div></td>
                  
                  <td align="center" valign="top" class="HeadingBg"> <div align="center">
                    &nbsp;&nbsp;<strong>APP REMARKS</strong>
                    </div></td>
                     
		   </tr>
		   
		     <% int d1=1; %>
		      		<logic:iterate id="object" name="reportForm" property="allocatepaymentFinal" indexId="index">
		     	
		     	<%
		     	ReportActionForm rReport = (ReportActionForm)object;
				%>
				<tr>
		      <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%= rReport.getAPP_REF_NO()%></TD>
             
              <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%= rReport.getUNIT_NAME()%></TD>
		
             <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%= rReport.getCGPAN()%></TD>
             
              <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%= rReport.getApproved_amt()%></TD>
              
              <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%= rReport.getDanId()%></TD>
               
			  <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%= rReport.getDanAmount()%></TD>
			  
			  <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%= rReport.getGUARANTEE_DATE()%></TD>
			  
			  <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%= rReport.getAPP_STATUS()%></TD>
         	  
         	  <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%= rReport.getRP_NUMBER()%></TD>
         	  
         	  <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%= rReport.getAPPROPRIATION_DATE()%></TD>
         	  
         	  <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%= rReport.getAPPROPRIATED_BY()%></TD>
         	  
         	  <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%= rReport.getAPP_REMARKS()%></TD>
		    </tr>
		    <%d1++; %>
		      </logic:iterate>   
          
		 </TABLE>	
		     
		       <tr align="center" valign="baseline">
           		 <td colspan="4"> 
           		 <div align="center"> 
		         <a href="javascript:submitForm('applicationStatus.do?')">
		        <IMG src="images/Back.gif" alt="Back" width="49" height="37" border="0"></A>
		        
		         <A href="javascript:printpage()">
						<IMG src="images/Print.gif" alt="Print" width="49" height="37" border="0"></A>
                
                
                 
		      </div>
		      </td>
		     </tr>
		  	</table>     
		

			<TD width="20" background="images/TableVerticalRightBG.gif">
				&nbsp;
			</TD>
	     </tr>
		
		<TR>
			<TD width="20" align="right" valign="top">
				<IMG src="images/TableLeftBottom1.gif" width="20" height="15">
			</TD>
			<TD background="images/TableBackground2.gif">
				&nbsp;
			</TD>
			<TD width="20" align="left" valign="top">
				<IMG src="images/TableRightBottom1.gif" width="23" height="15">
			</TD>
		</TR>
 
</TABLE>
 </html:form>
</table>

	
	
	
 


