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
<% session.setAttribute("CurrentPage","displayClaimLodgedReport.do?method=displayClaimLodgedReport");%>

	<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
    <html:errors />
	<html:form action="displayClaimLodgedReport.do?method=displayClaimLodgedReport" method="POST" enctype="multipart/form-data">
	<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	   <TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><img src="images/ClaimsProcessingHeading.gif" width="131" height="25"></TD>
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
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<tr>
									
                        
                      </TR>
           		 </TABLE>
            
	
			<tr>
		
                   <td valign="top" class="HeadingBg"> <div align="center">&nbsp;&nbsp;<strong>Sr.<br>
                      No</strong></div></td>
                  
                  <td valign="top"  class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>Bank Name</strong></div></td>
                  <td valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>Member ID</strong></div></td>
                      
                       <td valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;
                      <strong>Zone</strong><br></div></td>
                   
                     <td valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>Unit<br>Name
                      </strong><br></div></td>
                      
                      <td valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>CGPAN
                      </strong><br></div></td>
                      
                 <td valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>ClaimRefNo.
                      </strong><br></div></td>
                      
                  <td valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>Guaranteed Amount
                             </strong><br></div></td>
                      
                       <td valign="top"  class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>Claim Submit to CGTMSE With D&U<br>
                      </strong></div></td>
                  
                  <td align="center" valign="top" class="HeadingBg"> <div align="center">
                    &nbsp;&nbsp;<strong>Claim Returned For Lodgement</strong>
                    </div></td>	
                    
                     <td valign="top"  class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>Claim Returned Remarks<br>
                      </strong></div></td>	  
		        </tr>
		        
<%
String dateofclaim = null;
String bankName = "";
String memberids="";
String zone = ""; 
double guaranteedAmount=0.0;
String unitNAME="";
String cgPAN="";
String claimRefNo="";
String claimSubmitCGTMSE = "";
String claimReturnedForLodgement = "";
String claimReturnedRemarks = "";
%>

 <%
								int j=0;
                                int k=0;
                                
								%>
		        
		      <logic:iterate id="object" name="cpTcDetailsForm" property="claimLodgedReport" indexId="index">
					<%
								
								ClaimActionForm   cReport = (ClaimActionForm)object;
								String raju=cReport.getZone();
								 System.out.println("newrajukk:" + raju);
								
								%>			
								
								
							 <TR>
							   
							   
				<TD width="10%" align="left" valign="top" class="ColumnBackground1"><%=Integer.parseInt(index+"")+1%></TD>
           
			   
		     <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%=cReport.getBankName()%></TD>
                    
             <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%=cReport.getMemberids()%></TD>
             
             <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%=cReport.getZone()%></TD>
             <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%=cReport.getUnitNAME()%></TD>
             <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%=cReport.getCgPAN()%></TD>
             <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%=cReport.getClaimRefNo()%></TD>
             <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%=cReport.getGuaranteedAmount()%></TD>
             <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%=cReport.getClaimSubmitCGTMSE()%></TD>
             <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%=cReport.getClaimReturnedForLodgement()%></TD>
	         <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%=cReport.getClaimReturnedRemarks()%></TD>   
				
	           	    
               </TR>
              
              <%j++; %>
               <%k++; %>
			  </logic:iterate>	

     

                                             <TD width="10%" align="center" valign="top" class="ColumnBackground1">						
											</TD>
											
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						

											</TD>
											
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						

											</TD>
											
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">			
											
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">			
											
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">			
											
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">			
											
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">			
											
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">			
											
											</TD>
										
											
												        
          </tr>
		 </TABLE>	
		     
              <tr align="center" valign="baseline">
           		 <td colspan="4"> 
           		 <div align="center"> 
		         
                 <a href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>"><img src="images/OK.gif" width="40" height="37" border="0"></a>
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
	</TABLE>