<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@page import="java.util.HashSet"%>
<%@page import ="java.text.SimpleDateFormat"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","gfallocatedpaymentReport.do?method=gfallocatedpaymentReport");%>
<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>
<%@page import ="java.text.DecimalFormat"%>
<%DecimalFormat decimalFormat = new DecimalFormat("##########.##");%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="gfallocatedpaymentReport.do?method=gfallocatedpaymentReport" method="POST" enctype="multipart/form-data">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/ReportsHeading.gif" width="121" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
      <TABLE> <tr>
             <td align="left" width="80%">&nbsp;Ltr Ref No:---------------------------</td>
             <td align="right">&nbsp;Date:---------------</td></tr>
      </TABLE>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
         <tr><td>&nbsp;</td>
         </tr>
					 <tr>
                       <td>To,</td> </tr>
                      <tr> <td>Dy. General Manager,</td></TR>
                       <tr><td>CGTMSE,</Td></tr>
                       <tr><td>1002 & 1003, Naman Centre, 10th floor,</td></TR>
                       <tr><td>Plot No. C-31, G - Block,</td></tr>
                       <tr><td>Bandra Kurla Complex, Bandra (East),</td></tr>
                       <tr><td><u>MUMBAI - 400051</u></td></tr>
                       <tr><td>&nbsp;</td></tr>
                       <tr><td>&nbsp;</td></tr>
                       <tr><td align = "center"><b><u>Details of Remittance towards Guarantee Fee </u></b></td></tr>
                       <tr><td>&nbsp;</td></tr>
                       <tr><td>&nbsp;</td></tr>
                       <tr><td>Dear Sir,</td></tr>
                       <tr><td>&nbsp;</td></tr>
                       <tr><td>&nbsp;</td></tr>
                       <tr><td> We forward herewith the following DD(s) towards remittance of Guarantee Fee. Details of DD(s) and RP Nos. are indicated below :-</td></TR>
                       					
          <TR>
						<TD>
           	<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
								 	<TD colspan="10">  
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											                    
                          <!--<td colspan="6" class="Heading1" align="center"><u><bean:message key="reportHeader"/></u></td> -->
                      </tr>
                      <tr> <td colspan="6">&nbsp;</td></tr>
                      
                     <!-- <TR>
												<TD width="18%" class="Heading">GF ALLOCATED PAYMENT REPORT</TD>
												<td class="Heading" width="40%">&nbsp;<bean:message key="from"/> <bean:write  name="rsForm" property="dateOfTheDocument16"/>&nbsp;<bean:message key="to"/> <bean:write  name="rsForm" property="dateOfTheDocument17"/></td>
                        <TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>
-->
										</TABLE>
									</TD>

									<TR>
                  <td width="3%" align="left" valign="top" class="ColumnBackground"><bean:message key="sNo"/></td>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="memberId" />
									</TD>
               <!--    <TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="SFDAN" />
									</TD>
                  <TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="cgpan" />
									</TD> -->
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="instrumentNumber" />
									</TD>
                <!--  <TD width="10%" align="left" valign="top" class="ColumnBackground">
										Pay Amount
									</TD> -->
								<!--	<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="paidDate" /> -->
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="instrumentDate"/>
									</TD>
									<TD width="10%" align="right" valign="top" class="ColumnBackground">
                  <div align = "right">
										Pay Amount
                    </div>
									</TD>
                  <TD width="20%" align="right" valign="top" class="ColumnBackground">
                  <div align = "right">
										Payment Id
                    </div>
									</TD>
                                                                        
                                                                        
                                                               <TD width="10%" align="right" valign="top" class="ColumnBackground">
                  <div align = "right">
										BASE AMT
                    </div>
									</TD>
                                                                        
                                                                        
                                                                        <TD width="10%" align="right" valign="top" class="ColumnBackground">
                  <div align = "right">
										STAX
                    </div>
									</TD>
                                                                        
                                                                        
                                                                        <TD width="10%" align="right" valign="top" class="ColumnBackground">
                  <div align = "right">
										ECESS
                    </div>
									</TD>
                                                                        
                                                                        
                                                                        <TD width="10%" align="right" valign="top" class="ColumnBackground">
                  <div align = "right">
										HECESS
                    </div>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
									<div align = "right">
										Included S.B Ecess
										  </div>
									</TD>
									
										<TD width="10%" align="left" valign="top" class="ColumnBackground">
									<div align = "right">
									 Krishi Kalyan Cess
										  </div>
									</TD>
                                                                                 
                                                                        
                                                                        
                                                                        
								<!--	<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="realisationDate"/> 
									</TD> -->
									</TR>	
                    	<%
										double amount = 0;
											double totalAmount = 0;
                                                                                        
                                                                                          double bamount = 0;
											double totalbAmount = 0;
                                                                                        
                                                                                        
                                                                                        double samount = 0;
											double totalsAmount = 0;
                                                                                        
                                                                                        double eamount = 0;
											double totaleAmount = 0;
                                                                                        
                                                                                        double hamount = 0;
											double totalhAmount = 0;
											double totalSBhecess = 0; //Added By Rajuk
											double totalkrishiKalCess=0;
                    int temp=0;
                   		%>

									<tr>
                  <% HashSet n = new HashSet(); %>
									<logic:iterate name="rsForm"  property="gfallocatedpayment"  id="object" indexId="index">
									<%
									      com.cgtsi.reports.PaymentReport pReport =  (com.cgtsi.reports.PaymentReport)object;
								
                  %>
									
					
											<TR>
                      <td align="left" valign="top" class="ColumnBackground1">
                      <% temp=Integer.parseInt(index+"")+1;%>
                      <%=Integer.parseInt(index+"")+1%></td>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%=pReport.getMemberId()%>
											</TD>
                   <!--   <TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%=pReport.getDanId()%>
											</TD>
                      <TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%=pReport.getCgpan()%>
											</TD> -->
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%=pReport.getInstrumentNumber()%>
                      <% n.add(pReport.getInstrumentNumber()); 
                     %>
											</TD>
                     
										<!--	<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%   java.util.Date utilDate=pReport.getRecievedDate();
													String formatedDate = null;
													if(utilDate != null)
													{
														 formatedDate=dateFormat.format(utilDate);
													}
													else
													{
														 formatedDate = "";
													}
											%>
											<%=formatedDate%>
											</TD> -->
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%   java.util.Date utilDate1=pReport.getInstrumentDate();
													String formatedDate1 = null;
													if(utilDate1 != null)
													{
														 formatedDate1=dateFormat.format(utilDate1);
													}
													else
													{
														 formatedDate1 = "";
													}
											%>
											<%=formatedDate1%>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">			
											<div align="right">
											<% amount=pReport.getAmountPaid();
                     // System.out.println("totalamountA:"+totalAmount);
                      //System.out.println("amount:"+amount);
											totalAmount = totalAmount + amount;
                      //System.out.println("totalamount:"+totalAmount);%>		
											<%=decimalFormat.format(amount)%>
											</div>
											</TD>
								<!--			<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%String instrumentType= pReport.getInstrumentType();
											if((instrumentType == null)||(instrumentType.equals("")))
											{
											%>
											<%=""%>
											<%
											}
											else
											{
											%>
											<%=instrumentType%>
											<%
											}
											%>
											</TD> -->
									<TD width="20%" align="right" valign="top" class="ColumnBackground1">
                  <div align="right">
                  <%
                    String payId =  pReport.getPayId();
                    String url = "gfAllocatedReportDetails.do?method=gfAllocatedReportDetails&payId="+payId;%>
         
								 <html:link href="<%=url%>"><%=payId%></html:link>
                      
                  </div>
                  </TD>
                  
                  
                   <TD width="10%" align="left" valign="top" class="ColumnBackground1">			
											<div align="right">
											<% bamount=pReport.getBaseAmount();
                     // System.out.println("totalamountA:"+totalAmount);
                      //System.out.println("amount:"+amount);
											totalbAmount = totalbAmount + bamount;
                      //System.out.println("totalamount:"+totalAmount);%>		
											<%=decimalFormat.format(bamount)%>
											</div>
											</TD>
                                                                                        
                                                                                        
                                                                                        
                                                                                        <TD width="10%" align="left" valign="top" class="ColumnBackground1">			
											<div align="right">
											<% samount=pReport.getSerTaxAmount();
                     // System.out.println("totalamountA:"+totalAmount);
                      //System.out.println("amount:"+amount);
											totalsAmount = totalsAmount + samount;
                      //System.out.println("totalamount:"+totalAmount);%>		
											<%=decimalFormat.format(samount)%>
											</div>
											</TD>
                                                                                        
                                                                                        
                                                                                        <TD width="10%" align="left" valign="top" class="ColumnBackground1">			
											<div align="right">
											<% eamount=pReport.getEduCessbaseAmount();
                     // System.out.println("totalamountA:"+totalAmount);
                      //System.out.println("amount:"+amount);
											totaleAmount = totaleAmount + eamount;
                      //System.out.println("totalamount:"+totalAmount);%>		
											<%=decimalFormat.format(eamount)%>
											</div>
											</TD>
                                                                                        
                                                                                        
                                                                                        <TD width="10%" align="left" valign="top" class="ColumnBackground1">			
											<div align="right">
											<% hamount=pReport.getHighereduCessbaseAmount();
                     // System.out.println("totalamountA:"+totalAmount);
                      //System.out.println("amount:"+amount);
											totalhAmount = totalhAmount + hamount;
                      //System.out.println("totalamount:"+totalAmount);%>		
											<%=decimalFormat.format(hamount)%>
											</div>
											</TD>
                  
									<!--		<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%   java.util.Date utilDate2=pReport.getRealisationDate();
													String formatedDate2 = null;
													if(utilDate2 != null)
													{
														 formatedDate2=dateFormat.format(utilDate2);
													}
													else
													{
														 formatedDate2 = "";
													}
											%>
											<%=formatedDate2%>
											</TD> -->
											<!--Added By Rajuk--> 
											 <TD width="10%" align="left" valign="top" class="ColumnBackground1">		
											<div align="right">
												<%
													double inclhSBecess = pReport.getSwatchBharatTax();
												totalSBhecess=totalSBhecess+inclhSBecess;
												%>
											    <%=decimalFormat.format(inclhSBecess)%>
											   </div>
                                               </TD>
                                            <!--Ended By Rajuk--> 
                                            
                                            
                                            
                                              <TD width="10%" align="left" valign="top" class="ColumnBackground1">		
											<div align="right">
												<%
												 
												     double KrishiKalCess = pReport.getKrishiKalCess();
											    	totalkrishiKalCess=totalkrishiKalCess+KrishiKalCess;
												%>
											    <%=decimalFormat.format(KrishiKalCess)%>
											   </div>
                                               </TD>     
											</TR>
											</logic:iterate>

											<TR>
											<TD width="10%" align="left" colspan="2" valign="top" class="ColumnBackground">						
											Total
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						

											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground">						

											</TD>
                 <!--     <TD width="10%" align="left" valign="top" class="ColumnBackground">						

											</TD>
                      <TD width="10%" align="left" valign="top" class="ColumnBackground">						

											</TD> -->
                      <TD width="10%" align="left" valign="top" class="ColumnBackground">			
											<div align="right">
											<%=decimalFormat.format(totalAmount)%>
											</div>
											</TD>
											
                                                                                        <td></td>
                                                                                          <TD width="10%" align="left" valign="top" class="ColumnBackground">			
											<div align="right">
											<%=decimalFormat.format(totalbAmount)%>
											</div>
																	

											</TD>
                                                                                          <TD width="10%" align="left" valign="top" class="ColumnBackground">			
											<div align="right">
											<%=decimalFormat.format(totalsAmount)%>
											</div>
																	

											</TD>
                                                                                          <TD width="10%" align="left" valign="top" class="ColumnBackground">			
											<div align="right">
											<%=decimalFormat.format(totaleAmount)%>
											</div>
											</TD>
											
                                                                                          <TD width="10%" align="left" valign="top" class="ColumnBackground">			
											<div align="right">
											<%=decimalFormat.format(totalhAmount)%>
											</div>
											</TD>
											
										<!--   	<TD width="10%" align="left" valign="top" class="ColumnBackground">		-->				

											</TD>
									<!--Added By Rajuk-->
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
									<div align="right">
									<%=decimalFormat.format(totalSBhecess)%>
									</div>
									</TD>
									
                                     <!--Ended By Rajuk--> 
                                     
                                     <TD width="10%" align="left" valign="top" class="ColumnBackground">
									<div align="right">
									<%=decimalFormat.format(totalkrishiKalCess)%>
									</div>
									</TD>
                                                                                        
											
											</TR>
									</TABLE>
																				
						</TD>
					</TR>
         <%  System.out.println("n:"+n.size()); %>
				          
          <tr><td width="100%" align="center"  valign="top" class="ColumnBackground">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <U>Our updated Address is as mentioned below:</U></td></tr>
          <tr>
					<TD align="left" valign="top" class="ColumnBackground">
												&nbsp;<font color="#FF0000" size="2">*</font>
                        <bean:message key="bankAddress" />&nbsp;&nbsp;<textarea cols="50" rows="6" name="comment"></textarea>
   </TD>
				  </tr> 
          <tr>
					<TD align="left" valign="top" class="ColumnBackground">
												&nbsp;<font color="#FF0000" size="2">*</font>
                        <bean:message key="location" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="location" size="25">
          </TD>
				  </tr> 
           <tr>
					<TD align="left" valign="top" class="ColumnBackground">
												&nbsp;<font color="#FF0000" size="2">*</font>
                        <bean:message key="city" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="city" size="25">
          </TD>
				  </tr> 
          
            <tr>
					<TD align="left" valign="top" class="ColumnBackground">
												&nbsp;<font color="#FF0000" size="2">*</font>
                        <bean:message key="pinCode" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="pinCode" size="25">
          </TD>
				  </tr> 
         <tr>
         <TD align="left" valign="top" class="ColumnBackground">
												&nbsp;<font color="#FF0000" size="2">*</font>
                    STD Code &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <!-- <bean:message key="phoneStdCode" /> --><input type="text" name="phoneStdCode" size="25">
          </TD>
          </tr>
          <tr>
					<TD align="left" valign="top" class="ColumnBackground">
												&nbsp;<font color="#FF0000" size="2">*</font>
                        <bean:message key="phoneNo" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="phoneNo" size="25">
          </TD>
				  </tr>
          <tr>
					<TD align="left" valign="top" class="ColumnBackground">
												&nbsp;<font color="#FF0000" size="2">*</font>
                        <bean:message key="faxNo" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="faxNo" size="25">
          </TD>
				  </tr>
          <tr>
					<TD align="left" valign="top" class="ColumnBackground">
												&nbsp;<font color="#FF0000" size="2">*</font>
                        <bean:message key="emailId" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="mail" size="25">
          </TD>
				  </tr>
   <tr><td>&nbsp;</td></tr>
<tr><TD width="10%" align="left" valign="top" class="ColumnBackground"></TD><tr>
<tr><td>&nbsp;</td></tr>
<tr><td>Encl. : <%=n.size()%> DDs for a total amount of Rs. <%=decimalFormat.format(totalAmount)%>/-  </td></TR>

<tr><td align = "right">Yours faithfully</td></TR>
<tr><td>&nbsp;</td></tr>
<tr><td>&nbsp;</td></tr>
<tr><td align = "right">(Authorised Signatory)</td></TR>


					<TR >
						<TD align="center" valign="baseline" >
							<DIV align="center">
								<A href="javascript:submitForm('gfallocatedpaymentReportInput.do?method=gfallocatedpaymentReportInput')">
									<IMG src="images/Back.gif" alt="Back" width="49" height="37" border="0"></A>

									
									<A href="javascript:printpage()">
									<IMG src="images/Print.gif" alt="Print" width="49" height="37" border="0"></A>
								
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

