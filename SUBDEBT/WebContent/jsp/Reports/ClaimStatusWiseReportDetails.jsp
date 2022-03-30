<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@page import ="java.text.SimpleDateFormat"%>
<%@ page import="com.cgtsi.claim.ClaimConstants"%>
<%@ page import="com.cgtsi.actionform.ClaimActionForm"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>
<% 
  session.setAttribute("CurrentPage","displayListOfClaimRefNumbers.do?method=displayListOfClaimRefNumbers");
%>
  
<%
String statusFlag = null;
ClaimActionForm claimForm = (ClaimActionForm)session.getAttribute("cpTcDetailsForm") ;
statusFlag = (String)claimForm.getStatusFlag();
 
Double totGuaranteeAmount=0.0;


Double totgrandTotal=0.0;

Double totGrandClmApprovedAmt=0.0;




// System.out.println("Printing Status Flag :" + statusFlag);
%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="applicationStatusWiseReportDetails.do?method=applicationStatusWiseReportDetails" method="POST" enctype="multipart/form-data">
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
									<TD colspan="12"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<tr>
                          <td colspan="8" class="Heading1" align="center"><u><bean:message key="reportHeader"/></u></td>
                      </tr>
                      <tr> <td colspan="8">&nbsp;</td></tr>
                      <TR>
												<TD width="18%" class="Heading"><bean:message key="statusWiseHeader" /></TD>
												<td class="Heading" width="40%">(<bean:write name="radioValue"/>)&nbsp;<bean:message key="from"/> <bean:write  name="cpTcDetailsForm" property="fromDate"/>&nbsp;<bean:message key="to"/> <bean:write  name="cpTcDetailsForm" property="toDate"/></td>
                        <TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>

									<TR>
                  <TD width="3%" align="left" valign="top" class="ColumnBackground"><bean:message key="sNo"/></TD>
                                                                        <TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="memberId"/>
									</TD>
                                                                        <TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="bank"/>
									</TD>
                                                                        <TD width="10%" align="left" valign="top" class="ColumnBackground">
										Zone Name
									</TD>
									
									
                                                                        
                                                                        
                                                                        <TD width="10%" align="left" valign="top" class="ColumnBackground">
										Branch Name
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										Loan Account Number
									</TD>
                                                                         <TD width="10%" align="left" valign="top" class="ColumnBackground">
										CGPAN
									</TD>
                                                                         <TD width="10%" align="left" valign="top" class="ColumnBackground">
										Unit Name
									</TD>
                                                                        
                                                                         <TD width="10%" align="left" valign="top" class="ColumnBackground">
										Guaranteed Amount
									</TD>
                                                                        
                                                                        
                 
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="clmrefnumber"/>
									</TD>
									<logic:equal property="statusFlag" value="AP" name="cpTcDetailsForm">
									
                  <TD width="10%" align="left" valign="top" class="ColumnBackground">
										Claim Lodgment Dt.
									</TD>
                                                                        
                  <TD width="10%" align="left" valign="top" class="ColumnBackground">
										Claim Approved Amt.
									</TD>
                  <TD width="10%" align="left" valign="top" class="ColumnBackground">
										Claim Approved Dt
									</TD>
                                                                       
                                                                        
                                                                        
                                                                        
                  </logic:equal>
                  
                  
                  
                  
                  
                  
                  
                  
                  <logic:equal property="statusFlag" value="FW" name="cpTcDetailsForm">
                  
                   <TD width="10%" align="left" valign="top" class="ColumnBackground">
										Claim Lodgement Dt
									</TD>
                                                                        
                                                                         <TD width="10%" align="left" valign="top" class="ColumnBackground">
										Claim Forwarded Amount
									</TD>
                                                                        
                  	<TD width="10%" align="left" valign="top" class="ColumnBackground">
										Claim Forwarded Dt
									</TD>
                                                                       
                                                                       
                                                                        
                  </logic:equal>
                  
                  
                  
                  
                  
                    
                  <logic:equal property="statusFlag" value="RT" name="cpTcDetailsForm">
                  
                   <TD width="10%" align="left" valign="top" class="ColumnBackground">
										Claim Applied Amount
									</TD>
                                                                        
                                                                         <TD width="10%" align="left" valign="top" class="ColumnBackground">
										Claim Returned Date
									</TD>
                                                                        
                  	<TD width="10%" align="left" valign="top" class="ColumnBackground">
										Claim Returned Remarks
									</TD>
                                                                       
                                                                       
                                                                        
                  </logic:equal>
                  
                  
                  
                  
                  
                     <logic:equal property="statusFlag" value="RTD" name="cpTcDetailsForm">
                  
                   
                                                                        
                                                                         <TD width="10%" align="left" valign="top" class="ColumnBackground">
										Claim Lodgement Date
									</TD>
                                                                        <TD width="10%" align="left" valign="top" class="ColumnBackground">
										Claim Applied Amount
									</TD>
                                                                        
                                                                         <TD width="10%" align="left" valign="top" class="ColumnBackground">
										Claim Rej/Temp Rej Date
									</TD>
                  	
                                                                       
                                                                       
                                                                        
                  </logic:equal>
                  
                  
                     <logic:equal property="statusFlag" value="AS" name="cpTcDetailsForm">
                  
                   <TD width="10%" align="left" valign="top" class="ColumnBackground">
										Claim  Amount
									</TD>
                                                                        
                                                                         <TD width="10%" align="left" valign="top" class="ColumnBackground">
										Claim Submitted Date
									</TD>
                                                                        
                  	<TD width="10%" align="left" valign="top" class="ColumnBackground">
										Claim Status
									</TD>
                                                                        
                                                                        <TD width="10%" align="left" valign="top" class="ColumnBackground">
										Claim Declaration Recvd Date
									</TD>
                                                                        
                                                                          <TD width="10%" align="left" valign="top" class="ColumnBackground">
										Action Taken Date
									</TD>
                                                                       
                                                                       
                                                                        
                  </logic:equal>
                  
                   <logic:equal property="statusFlag" value="RE" name="cpTcDetailsForm">
                   
                   
                    <TD width="10%" align="left" valign="top" class="ColumnBackground">
										Claim Lodgement Dt
									</TD>
                                                                        <TD width="10%" align="left" valign="top" class="ColumnBackground">
										Claim Applied Amount
									</TD>
                                                                        
                                                                        <TD width="10%" align="left" valign="top" class="ColumnBackground">
										Claim Rejected Date
									</TD>
<TD width="10%" align="left" valign="top" class="ColumnBackground">
										Claim Rejected Remarks
									</TD>
                                                                        
                  	   
                   
                  	
                  </logic:equal>
                   <logic:equal property="statusFlag" value="NE" name="cpTcDetailsForm">
                  	
                                                                                
									</TD>
                                                                        
                                                                        
                                                                          <TD width="10%" align="left" valign="top" class="ColumnBackground">
										claim Lodged Dt
									</TD>
                                                                        
                                                                        <TD width="10%" align="left" valign="top" class="ColumnBackground">
										claim Decl received Dt
									</TD>
                                                                       
                                                                        
                                                                        <TD width="10%" align="left" valign="top" class="ColumnBackground">
										Claim Recvd Amount
									</TD>
                  </logic:equal>
									<logic:equal property="statusFlag" value="HO" name="cpTcDetailsForm">
                  	<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="applicationDate"/>
									</TD>
                  </logic:equal>
                  <logic:equal property="statusFlag" value="TC" name="cpTcDetailsForm">
                  
                  
                  
                  
                  
                  
                  	<TD width="10%" align="left" valign="top" class="ColumnBackground">
										Claim Dt
									</TD>
                                                                        
                                                                        <TD width="10%" align="left" valign="top" class="ColumnBackground">
										Claim Applied Amount
									</TD>
                                                                        
                                                                         <TD width="10%" align="left" valign="top" class="ColumnBackground">
										Claim Temp Closed Dt
									</TD>
                                                                        
                                                                        <TD width="10%" align="left" valign="top" class="ColumnBackground">
										Claim Query Ref Number
									</TD>
                                                                        
                                                                        
                                                                        <TD width="10%" align="left" valign="top" class="ColumnBackground">
										Claim Query Date
									</TD>
                                                                        
                                                                        
                                                                        
                                                                        
                                                                        
                                                                        
                  </logic:equal>
                  <logic:equal property="statusFlag" value="TR" name="cpTcDetailsForm">
                  	
                                                                        
                                                                        <TD width="10%" align="left" valign="top" class="ColumnBackground">
										Claim Lodgement Date
									</TD>
                                                                         <TD width="10%" align="left" valign="top" class="ColumnBackground">
										Claim Applied Amount
									</TD>
                                                                        <TD width="10%" align="left" valign="top" class="ColumnBackground">
										Claim Temporory Rejected Date
									</TD>
<TD width="10%" align="left" valign="top" class="ColumnBackground">
										Claim Rejected Remarks
									</TD>
                  </logic:equal>
                  <logic:equal property="statusFlag" value="WD" name="cpTcDetailsForm">
                  	
                                                                         <TD width="10%" align="left" valign="top" class="ColumnBackground">
										Claim Lodgement Date
									</TD>
                   
                   
                    <TD width="10%" align="left" valign="top" class="ColumnBackground">
										Claim Applied Amount
									</TD>
                                                                        
                                                                        <TD width="10%" align="left" valign="top" class="ColumnBackground">
										Claim Temporory With Drawn Date
									</TD>
                                                                         
                  	
                                                                       
                                                                      
                   
                  	
                  </logic:equal>
                                                                        
                 
				  <logic:equal property="statusFlag" value="RR" name="cpTcDetailsForm">
                                  
                                  <TD width="10%" align="left" valign="top" class="ColumnBackground">
										Claim Lodgement Date
									</TD>
                   
                   
                    <TD width="10%" align="left" valign="top" class="ColumnBackground">
										Claim Applied Amount
									</TD>
                                                                        
                                                                        <TD width="10%" align="left" valign="top" class="ColumnBackground">
										Claim Reply Recvd Date
									</TD>
                                  
                  	<TD width="10%" align="left" valign="top" class="ColumnBackground">
										Claim Query Ref Number.
									</TD>     
                                                                        
                                                                        <TD width="10%" align="left" valign="top" class="ColumnBackground">
										Claim Query Date.
									</TD>   
                                                                        
                                                                        
                                                                         <TD width="10%" align="left" valign="top" class="ColumnBackground">
										Reply Inward Id
									</TD>
                                                                        
                                                                         <TD width="10%" align="left" valign="top" class="ColumnBackground">
										Reply Inward Date
									</TD>
                  </logic:equal>
				</TR>	
					
											<tr>
											<logic:iterate name="cpTcDetailsForm" id="object" property="listOfClmRefNumbers" indexId="index">
											<%
											com.cgtsi.claim.ClaimDetail details = (com.cgtsi.claim.ClaimDetail)object;
											
											String loanacc=details.getLoanaccountNumber();
											System.out.println("loanacc=="+loanacc);
											String Loan="NA";
											%>
													
											<TR>
                        <TD width="3%" align="left" valign="top" class="ColumnBackground1"><%=Integer.parseInt(index+"")+1%></TD>
                        <TD width="10%" align="left" valign="top" class="ColumnBackground1">					
												<%=details.getMliId()%>				
												</TD>
												<TD width="10%" align="left" valign="top" class="ColumnBackground1">	
												<%=details.getMliName()%>				
												</TD>
												
                                                                                                
                                                                                                <TD width="10%" align="left" valign="top" class="ColumnBackground1">					
												<%=details.getZoneName()%>				
												</TD>
                                                                                                
                                                                                                <TD width="10%" align="left" valign="top" class="ColumnBackground1">					
												<%=details.getBranchName()%>				
												</TD>
												
											
												 <TD width="10%" align="left" valign="top" class="ColumnBackground1">
												<%
												
												if (loanacc==null){
													
													%>   				
																
											        <%=Loan%>					
												
												<% }else{%>
                                              			
												<%=loanacc%>				
												
                                                 <%   }  %> 
                                                 </TD>                                         
                                                                                                
                                                                                                 <TD width="10%" align="left" valign="top" class="ColumnBackground1">					
												<%=details.getCgpan()%>				
												</TD>
												
												
                                                                                                 <TD width="10%" align="left" valign="top" class="ColumnBackground1">					
												<%=details.getSsiUnitName()%>				
												</TD>
                                                                                                <TD width="10%" align="left" valign="top" class="ColumnBackground1">					
												<%=details.getApplicationApprovedAmount()%>				
												</TD>
                                                                                                
                                                                                                
                                                                                              
                                                                                              
                                                                                             <%   totGuaranteeAmount=details.getTotalGuarnteAmt();%>
                                                                                             
                                                                                             
                                                                                                 <%  totgrandTotal=details.getTotgrandTotal();%>
                                                                                                 
                                                                                                   <%  totGrandClmApprovedAmt=details.getTotGrandClmApprovedAmt();%>
                                                                                                 
                                                                                                 
                                                                                                 
																	
												<%String reference=details.getClaimRefNum(); %>
                                                                                               
                                                                                              
                                                                                              
                                                                                              
                                                                                              <% if("RR".equals(statusFlag)) {%>
                                                                                              
                                                                                              
                                                                                               <%String reference67=details.getClaimRefNum(); %>
                                                                                                <%
												String url = "displayClmRefNumberDtl.do?method=displayClmRefNumberDtl&"+ ClaimConstants.CLM_CLAIM_REF_NUMBER+"=" + reference; 	
												
												%>
                                                                                                <TD width="10%" align="left" valign="top" class="ColumnBackground1">	
												<html:link href="<%=url%>"><%=reference67%></html:link>			
												</TD>
                                                                                              
                                                                                              
                                                                                              
                                                                                             
                                                                                                
                                                                                                 
                                                                                                
                                                                                                <TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%   java.util.Date utilDate88=details.getClmSubmittedDt();
													String formatedDate88 = null;
													if(utilDate88 != null)
													{
														 formatedDate88=dateFormat.format(utilDate88);
													}
													else
													{
														 formatedDate88 = "";
													}
											%>
											<%=formatedDate88%>
											</TD>    
                                                                                        
                                                                                        
                                                                                                 
                                                                                              
                                                                                                <TD width="10%" align="left" valign="top" class="ColumnBackground1">					
												<%=details.getClaimApprovedAmount()%>				
												</TD>
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                <TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%   java.util.Date utilDate808=details.getClaimReplyRecvdDate();
													String formatedDate808 = null;
													if(utilDate808 != null)
													{
														 formatedDate808=dateFormat.format(utilDate808);
													}
													else
													{
														 formatedDate808 = "";
													}
											%>
											<%=formatedDate808%>
											</TD>    
                                                                                                
                                                                                                
                                                                                                <TD width="10%" align="left" valign="top" class="ColumnBackground1">					
												<%=details.getClmQryRefNumber()%>				
												</TD>
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                <TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%   java.util.Date utilDate80=details.getClamQryDate();
													String formatedDate80 = null;
													if(utilDate80 != null)
													{
														 formatedDate80=dateFormat.format(utilDate88);
													}
													else
													{
														 formatedDate80 = "";
													}
											%>
											<%=formatedDate80%>
											</TD>    
                                                                                                
                                                                                                
                                                                                               
                                                                                                <TD width="10%" align="left" valign="top" class="ColumnBackground1">					
												<%=details.getReplyInwardId()%>				
												</TD>
                                                                                                
                                                                                                
                                                                                                
                                                                                                <TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%   java.util.Date utilDate999=details.getReplyInwardDt();
													String formatedDate999 = null;
													if(utilDate999 != null)
													{
														 formatedDate999=dateFormat.format(utilDate88);
													}
													else
													{
														 formatedDate999 = "";
													}
											%>
											<%=formatedDate999%>
											</TD>    
                                                                                                
                                                                                               
                                                                                               
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                
												
												
												<%}%>
                                                                                                
                                                                                              
                                                                                              
                                                                                              
                                                                                              
                                                                                            
                                                                                              
                                                                                                
                                                                                                
												<% if("KRR".equals(statusFlag) || "WD".equals(statusFlag)){%>
                                                                                                
                                                                                                
												 
                                                                                               <%String reference67=details.getClaimRefNum(); %>
                                                                                                <%
												String url = "displayClmRefNumberDtl.do?method=displayClmRefNumberDtl&"+ ClaimConstants.CLM_CLAIM_REF_NUMBER+"=" + reference; 	
												
												%>
                                                                                                <TD width="10%" align="left" valign="top" class="ColumnBackground1">	
												<html:link href="<%=url%>"><%=reference67%></html:link>			
												</TD>
                                                                                              
                                                                                                
                                                                                                 
                                                                                                
                                                                                                <TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%   java.util.Date utilDate88=details.getClmSubmittedDt();
													String formatedDate88 = null;
													if(utilDate88 != null)
													{
														 formatedDate88=dateFormat.format(utilDate88);
													}
													else
													{
														 formatedDate88 = "";
													}
											%>
											<%=formatedDate88%>
											</TD>    
                                                                                        
                                                                                        
                                                                                                 
                                                                                              
                                                                                                <TD width="10%" align="left" valign="top" class="ColumnBackground1">					
												<%=details.getClaimAppliedAmt()%>				
												</TD>
                                                                                               
                                                                                                
                                                                                                   <TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%   java.util.Date utilDate199=details.getClaimWithDrawnDate();
													String formatedDate199 = null;
													if(utilDate199 != null)
													{
														 formatedDate199=dateFormat.format(utilDate199);
													}
													else
													{
														 formatedDate199 = "";
													}
											%>
											<%=formatedDate199%>
											</TD>    
                                                                                                
                                                                                                
                                                                                                
												
												
												<%}%>
                                                                                                
                                                                                                
                                                                                                
                                                                                            
                                                                                         <% if("KC".equals(statusFlag) || "TR".equals(statusFlag)){%>
												 
                                                                                               <%String reference67=details.getClaimRefNum(); %>
                                                                                                <%
												String url = "displayClmRefNumberDtl.do?method=displayClmRefNumberDtl&"+ ClaimConstants.CLM_CLAIM_REF_NUMBER+"=" + reference; 	
												
												%>
                                                                                                <TD width="10%" align="left" valign="top" class="ColumnBackground1">	
												<html:link href="<%=url%>"><%=reference67%></html:link>			
												</TD>
                                                                                              
                                                                                                
                                                                                                 
                                                                                                
                                                                                                <TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%   java.util.Date utilDate88=details.getClmSubmittedDt();
													String formatedDate88 = null;
													if(utilDate88 != null)
													{
														 formatedDate88=dateFormat.format(utilDate88);
													}
													else
													{
														 formatedDate88 = "";
													}
											%>
											<%=formatedDate88%>
											</TD>    
                                                                                        
                                                                                        
                                                                                                 
                                                                                              
                                                                                                <TD width="10%" align="left" valign="top" class="ColumnBackground1">					
												<%=details.getClaimAppliedAmt()%>				
												</TD>
                                                                                               
                                                                                                
                                                                                                
                                                                                                <TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%   java.util.Date utilDate777=details.getTempRejectedDate();
													String formatedDate777 = null;
													if(utilDate777 != null)
													{
														 formatedDate777=dateFormat.format(utilDate777);
													}
													else
													{
														 formatedDate777 = "";
													}
											%>
											<%=formatedDate777%>
											</TD>	

 <% if(details.getClaimRetRemarks() != null)
                                                                                                 {%>
												<TD width="10%" align="left" valign="top" class="ColumnBackground1"><%=details.getClaimRetRemarks()%>				
												</TD>
                                                                                                <%} else { %>
                                                                                               <TD width="10%" align="left" valign="top" class="ColumnBackground1">-------</td>
                                                                                                <%}%>


                                                                                               
                                                                                                
                                                                                                
												
												
												<%}%>
                                                                                                
                                                                                                
                                                                                                
                                                                                                    
                                                                                         <% if("RT".equals(statusFlag)){%>
												 
                                                                                               <%String reference67=details.getClaimRefNum(); %>
                                                                                                <%
												String url = "displayClmRefNumberDtl.do?method=displayClmRefNumberDtl&"+ ClaimConstants.CLM_CLAIM_REF_NUMBER+"=" + reference; 	
												
												%>
                                                                                                <TD width="10%" align="left" valign="top" class="ColumnBackground1">	
												<html:link href="<%=url%>"><%=reference67%></html:link>			
												</TD>
                                                                                              
                                                                                                
                                                                                                 
                                                                                                    <TD width="10%" align="left" valign="top" class="ColumnBackground1">					
												<%=details.getClaimAppliedAmt()%>				
												</TD>
                                                                                               
                                                                                                 
                                                                                                 
                                                                                                
                                                                                                <TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%   java.util.Date formatedDate99999=details.getClaimReturnDate();
													String formated = null;
													if(formatedDate99999 != null)
													{
														 formated=dateFormat.format(formatedDate99999);
													}
													else
													{
														 formated = "";
													}
											%>
											<%=formated%>
											</TD>    
                                                                                        
                                                                                        
                                                                                                 
                                                                                              
                                                                                                <TD width="10%" align="left" valign="top" class="ColumnBackground1">					
												<%=details.getClaimRetRemarks()%>				
												</TD>
                                                                                               
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                
												
												
												<%}%>
                                                                                          
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                  <% if("RTD".equals(statusFlag)){%>
												 
                                                                                               <%String reference67=details.getClaimRefNum(); %>
                                                                                                <%
												String url = "displayClmRefNumberDtl.do?method=displayClmRefNumberDtl&"+ ClaimConstants.CLM_CLAIM_REF_NUMBER+"=" + reference; 	
												
												%>
                                                                                                <TD width="10%" align="left" valign="top" class="ColumnBackground1">	
												<html:link href="<%=url%>"><%=reference67%></html:link>			
												</TD>
                                                                                              
                                                                                                
                                                                                                
                                                                                                <TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%   java.util.Date utilDate88=details.getClmSubmittedDt();
													String formatedDate88 = null;
													if(utilDate88 != null)
													{
														 formatedDate88=dateFormat.format(utilDate88);
													}
													else
													{
														 formatedDate88 = "";
													}
											%>
											<%=formatedDate88%>
											</TD>    
                                                                                                
                                                                                                 
                                                                                                    <TD width="10%" align="left" valign="top" class="ColumnBackground1">					
												<%=details.getClaimAppliedAmt()%>				
												</TD>
                                                                                               
                                                                                                 
                                                                                                 
                                                                                              
                                                                                                
                                                                                                <TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%   java.util.Date utilDate999=details.getTempRejOrRejDt();
													String formatedDat999 = null;
													if(utilDate999 != null)
													{
														 formatedDat999=dateFormat.format(utilDate999);
													}
													else
													{
														 formatedDat999 = "";
													}
											%>
											<%=formatedDat999%>
											</TD>    
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                
												
												
												<%}%>
                                                                                          
                                                                                         
                                                                                                
                                                                                                  <% if("AS".equals(statusFlag)){%>
												 
                                                                                               <%String reference67=details.getClaimRefNum(); %>
                                                                                                <%
												String url = "displayClmRefNumberDtl.do?method=displayClmRefNumberDtl&"+ ClaimConstants.CLM_CLAIM_REF_NUMBER+"=" + reference; 	
												
												%>
                                                                                                <TD width="10%" align="left" valign="top" class="ColumnBackground1">	
												<html:link href="<%=url%>"><%=reference67%></html:link>			
												</TD>
                                                                                              
                                                                                                
                                                                                                 
                                                                                                    <TD width="10%" align="left" valign="top" class="ColumnBackground1">					
												<%=details.getClaimAppliedAmt()%>				
												</TD>
                                                                                               
                                                                                                 
                                                                                                 
                                                                                                
                                                                                                <TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%   java.util.Date formatedDate99999=details.getClmSubmittedDt();
													String formated = null;
													if(formatedDate99999 != null)
													{
														 formated=dateFormat.format(formatedDate99999);
													}
													else
													{
														 formated = "";
													}
											%>
											<%=formated%>
											</TD>    
                                                                                        
                                                                                           <TD width="10%" align="left" valign="top" class="ColumnBackground1">					
												<%=details.getClmStatus()%>				
												</TD>
                                                                                                 
                                                                                              
                                                                                              
                                                                                              
                                                                                                
                                                                                                <TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%   java.util.Date format=details.getClmDeclRecvdDt();
													String format2 = null;
													if(format != null)
													{
														 format2=dateFormat.format(format);
													}
													else
													{
														 format2 = "";
													}
											%>
											<%=format2%>
											</TD>    
                                                                                              
                                                                                              
                                                                                               
                                                                                                
                                                                                                
                                                                                                 <TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%   java.util.Date format444=details.getLastActionTakenDt();
													String format888 = null;
													if(format444 != null)
													{
														 format888=dateFormat.format(format444);
													}
													else
													{
														 format888 = "";
													}
											%>
											<%=format888%>
											</TD>    
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                
												
												
												<%}%>
                                                                                          
                                                                                         
                                                                                                
                                                                                                
                                                                                            
                                                                                                
                                                                                                  <% if("TC".equals(statusFlag) ){%>
                                                                                                  
                                                                                                  
                                                                                                  
												 
                                                                                               <%String reference67=details.getClaimRefNum(); %>
                                                                                                <%
												String url = "displayClmRefNumberDtl.do?method=displayClmRefNumberDtl&"+ ClaimConstants.CLM_CLAIM_REF_NUMBER+"=" + reference; 	
												
												%>
                                                                                                <TD width="10%" align="left" valign="top" class="ColumnBackground1">	
												<html:link href="<%=url%>"><%=reference67%></html:link>			
												</TD>
                                                                                              
                                                                                                 
                                                                                                
                                                                                                <TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%   java.util.Date utilDate88=details.getClmSubmittedDt();
													String formatedDate88 = null;
													if(utilDate88 != null)
													{
														 formatedDate88=dateFormat.format(utilDate88);
													}
													else
													{
														 formatedDate88 = "";
													}
											%>
											<%=formatedDate88%>
											</TD>    
                                                                                        
                                                                                        
                                                                                        
                                                                                        
                                                                                                 
                                                                                              
                                                                                                <TD width="10%" align="left" valign="top" class="ColumnBackground1">					
												<%=details.getClaimAppliedAmt()%>				
												</TD>
                                                                                                
                                                                                                
                                                                                                
                                                                                                 <TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%   java.util.Date utilDate223=details.getTempClosedDate();
													String formatedDate223 = null;
													if(utilDate223 != null)
													{
														 formatedDate223=dateFormat.format(utilDate223);
													}
													else
													{
														 formatedDate223 = "";
													}
											%>
											<%=formatedDate223%>
											</TD>    
                                                                                                
                                                                                                
                                                                                                
                                                                                                    <TD width="10%" align="left" valign="top" class="ColumnBackground1">					
												<%=details.getClmQryRefNumber()%>				
												</TD>
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                 <TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%   java.util.Date utilDate99=details.getClamQryDate();
													String utilDate991 = null;
													if(utilDate99 != null)
													{
														 utilDate991=dateFormat.format(utilDate88);
													}
													else
													{
														 utilDate991 = "";
													}
											%>
											<%=utilDate991%>
											</TD>    
                                                                                                
                                                                                                
                                                                                                   
                                                                                               
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                
												
												
												<%}%>
											
                                                                                                
                                                                                                
											
                                                                                            
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                
												
												
												<%
												String cgclan = (String)details.getCGCLAN();
											double claimApproved = (double)details.getEligibleClaimAmt();	%>
												<logic:equal property="statusFlag" value="AP" name="cpTcDetailsForm">
                                                                                                
                                                                                                
                                                                                               <%String reference67=details.getClaimRefNum(); %>
                                                                                                <%
												String url = "displayClmRefNumberDtl.do?method=displayClmRefNumberDtl&"+ ClaimConstants.CLM_CLAIM_REF_NUMBER+"=" + reference; 	
												
												%>
                                                                                                <TD width="10%" align="left" valign="top" class="ColumnBackground1">	
												<html:link href="<%=url%>"><%=reference67%></html:link>			
												</TD>
                                                                                              
                                                                                                
                                                                                                
												
                                                                                                
                                                                                                
                                                                                                  <TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%   java.util.Date utilDate5=details.getClmSubmittedDt();
													String formatedDate5 = null;
													if(utilDate5 != null)
													{
														 formatedDate5=dateFormat.format(utilDate5);
													}
													else
													{
														 formatedDate5 = "";
													}
											%>
											<%=formatedDate5%>
											</TD>
                                                                                        
                                                                                        
                                                                                      <%  double claimApproved3 = (double)details.getClaimApprovedAmount();	%>
                                                                                        
                        <TD width="10%" align="left" valign="top" class="ColumnBackground1">	
												<%=claimApproved3%>				
												</TD>
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                           <TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%   java.util.Date utilDate99=details.getClaimApprovedDt();
													String formatedDate99 = null;
													if(utilDate99 != null)
													{
														 formatedDate99=dateFormat.format(utilDate99);
													}
													else
													{
														 formatedDate99 = "";
													}
											%>
											<%=formatedDate99%>
											</TD>      
                                                                                       
                                                                                        
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                        </logic:equal>
                        
                        
                     
                                                                                     
                                                                                     
                                                                                              
                                                                                              
                                                                                              
                                                                                              
                                                                                              
                                                                                              
                                                                                              
                                                                                              
                                                                                              
                                                                                              
                                                                                              
                                                                                              
                                                                                              
                                                                                              
                                                                                              
                                                                                              
                                                                                              
                                                                                              
                                                                                              
                                                                                              
                                                                                              
                                                                                              
                                                                                              
                                                                                              
                                                                                              
                                                                                              
                                                                                              
                                                                                               <% if("RE".equals(statusFlag) || "RE".equals(statusFlag)){%>
												
                                                                                                <%String reference67=details.getClaimRefNum(); %>
                                                                                                <%
												String url = "displayClmRefNumberDtl.do?method=displayClmRefNumberDtl&"+ ClaimConstants.CLM_CLAIM_REF_NUMBER+"=" + reference; 	
												
												%>
                                                                                                <TD width="10%" align="left" valign="top" class="ColumnBackground1">	
												<html:link href="<%=url%>"><%=reference67%></html:link>			
												</TD>
                                                                                                
                                                                                                
                                                                                                 
                                                                                                  <TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%   java.util.Date utilDate9=details.getClmSubmittedDt();
													String formatedDate9 = null;
													if(utilDate9 != null)
													{
														 formatedDate9=dateFormat.format(utilDate9);
													}
													else
													{
														 formatedDate9 = "";
													}
											%>
											<%=formatedDate9%>
											</TD>	
                                                                                                 
                                                                                               
                                                                                                 <%  double  ClaimAppliedAmt = (double)details.getClaimAppliedAmt();	%>
                                                                                        
                        <TD width="10%" align="left" valign="top" class="ColumnBackground1">	
												<%=ClaimAppliedAmt%>				
												</TD>
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                  <TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%   java.util.Date utilDate1213=details.getClaimRejectedDt();
													String formatedDate123 = null;
													if(utilDate1213 != null)
													{
														 formatedDate123=dateFormat.format(utilDate1213);
													}
													else
													{
														 formatedDate123 = "";
													}
											%>
											<%=formatedDate123%>
											</TD>	


 <% if(details.getClaimRetRemarks() != null)
                                                                                                 {%>
												<TD width="10%" align="left" valign="top" class="ColumnBackground1"><%=details.getClaimRetRemarks()%>				
												</TD>
                                                                                                <%} else { %>
                                                                                               <TD width="10%" align="left" valign="top" class="ColumnBackground1">-------</td>
                                                                                                <%}%>



                                                                                            
                                                                                                    
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                <%} 
                                                                                                %>
                                                                                     
                                                                                        
                                                                                        
                                                                                          <% if("NE".equals(statusFlag) || "NE".equals(statusFlag)){%>
												
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                 <%String reference67=details.getClaimRefNum(); %>
                                                                                                <%
												String url = "displayClmRefNumberDtl.do?method=displayClmRefNumberDtl&"+ ClaimConstants.CLM_CLAIM_REF_NUMBER+"=" + reference; 	
												
												%>
                                                                                                <TD width="10%" align="left" valign="top" class="ColumnBackground1">	
												<html:link href="<%=url%>"><%=reference67%></html:link>			
												</TD>
                                                                                          
												<TD width="10%" align="left" valign="top" class="ColumnBackground1">
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                <%   java.util.Date utilDate39=details.getClmSubmittedDt();
													String formatedDate39 = null;
													if(utilDate39 != null)
													{
														 formatedDate39=dateFormat.format(utilDate39);
													}
													else
													{
														 formatedDate39 = "";
													}
											%>
											<%=formatedDate39%>
													
												</TD> 
                                                                                                
                                                                                                
                                                                                               
                                                                                                  <TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%   java.util.Date utilDate7=details.getClmDeclRecvdDt();
													String formatedDate7 = null;
													if(utilDate7 != null)
													{
														 formatedDate7=dateFormat.format(utilDate7);
													}
                                                                                                        else
													{
														 formatedDate7 = "";
													}
													
											%>
											<%=formatedDate7%>
											</TD>
                                                                                        
                                                                                        
                                                                                         
                                                                                                 
                                                                                                
                                                                                                
                                                                                        
                                                                                           <% if(details.getClaimAppliedAmt() != 0.0)
                                                                                                 {%>
												<TD width="10%" align="left" valign="top" class="ColumnBackground1"><%=details.getClaimAppliedAmt()%>				
												</TD>
                                                                                                <%} else { %>
                                                                                               <TD width="10%" align="left" valign="top" class="ColumnBackground1">--</td>
                                                                                                <%}%>
                                                                                        
                                                                                        
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                 
                                                                                               
                                                                                                
                                                                                                
                                                                                            
                                                                                                
                                                                                                
                                                                                                
                                                                                               
                                                                                                
                                                                                               
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                <%} 
                                                                                                %>
                                                                                        
                                                                                        
                                                                                          
                                                                                          <% if("FW".equals(statusFlag) || "FW".equals(statusFlag)){%>
                                                                                          
                                                                                          
                                                                                           <%String reference67=details.getClaimRefNum(); %>
                                                                                                <%
												String url = "displayClmRefNumberDtl.do?method=displayClmRefNumberDtl&"+ ClaimConstants.CLM_CLAIM_REF_NUMBER+"=" + reference; 	
												
												%>
                                                                                                <TD width="10%" align="left" valign="top" class="ColumnBackground1">	
												<html:link href="<%=url%>"><%=reference67%></html:link>			
												</TD>
                                                                                          
												<TD width="10%" align="left" valign="top" class="ColumnBackground1">
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                                <%   java.util.Date utilDate39=details.getClmSubmittedDt();
													String formatedDate39 = null;
													if(utilDate39 != null)
													{
														 formatedDate39=dateFormat.format(utilDate39);
													}
													else
													{
														 formatedDate39 = "";
													}
											%>
											<%=formatedDate39%>
													
												</TD> 
                                                                                             
                                                                                                
                                                                                             
                                                                                              <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                                                                                               <% double claimApproved2 = (double)details.getClaimForwdAmt(); %>
                                                                                                
													<%=claimApproved2%>	
												</TD> 
                                                                                             
                                                                                                
                                                                                                <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                                                                                                <%   java.util.Date utilDate6=details.getClaimForwardedDt();
													String formatedDate6 = null;
													if(utilDate6 != null)
													{
														 formatedDate6=dateFormat.format(utilDate6);
													}
													else
													{
														 formatedDate6 = "";
													}
											%>
											<%=formatedDate6%>
													
												</TD> 
                                                                                                
                                                                                                
                                                                                                
                                                                                                
                                                                                               
                                                                                                
                                                                                                
                                                                                                
                                                                                               
												<%} 
                                                                                                %>
                                                                                                
                                                                                        
                                                                                        
                                                                                        
                                                                                        
                                                                                        
                                                                                        
                                                                                        
                                                                                        
                                                                                        
                                                                                        
                                                                                        
												
											</TR>
                                                                                         
                                                                                        
											</logic:iterate>
                                                                                      
   
							</TABLE>
                                                        <table>
                                                        <tr><td   colspan="275" align="left" valign="top"  class="ColumnBackground1">TOTAL(in Lakhs)</td><td   colspan="30" align="left" valign="top"  class="ColumnBackground1">Total Guaranteed Amt(in Lakhs)</td>  <td   colspan="30" align="left" valign="top"  class="ColumnBackground1"><%=totgrandTotal%></td><td   colspan="30" align="left" valign="top"  class="ColumnBackground1">Total of Any of above Claim Applied/Approved/Rejct/withDrawn Amt(in Lakhs)</td></td><td   colspan="102" align="left" valign="top"  class="ColumnBackground1"><%=totGrandClmApprovedAmt%></td></tr>
                                                        </TABLE>
						</TD>
                                                
                                                
					</TR>
                                        
                                        
                                        
					<TR >
						<TD height="20" >
							&nbsp;
						</TD>
					</TR>
					<TR >
						<TD align="center" valign="baseline">
							<DIV align="center">
							<A href="javascript:submitForm('showFilterForClaimDetails.do?method=showFilterForClaimDetails')">
									<IMG src="images/Back.gif" alt="OK" width="49" height="37" border="0"></A>
							</DIV>
						</TD>
					</TR>
				</TABLE>
			</TD>
			<TD width="20" background="images/TableVerticalRightBG.gif">
				&nbsp;
			</TD>
		</TR>
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
	</html:form>
</TABLE>       

