<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@page import ="java.text.DecimalFormat"%>
<%@page import ="java.math.BigDecimal"%>

<% session.setAttribute("CurrentPage","claimSettledReport.do?method=claimSettledReport");%>
<%
    String condition = (String)request.getAttribute("reportCondition");
 // out.println(condition)
    request.setAttribute("reportCondition",condition);
%>
<html:form action="claimSettledReport.do?method=claimSettledReport" method="POST" enctype="multipart/form-data">
    <TABLE width="725" border="0" cellpadding="0" cellspacing="0">    
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" alt="" width="20" height="31">
			</TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/ReportsHeading.gif" alt="" width="121" height="25">
			</TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" alt="" width="23" height="31">
			</TD>
		</TR> 
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;
			</TD>
                         
			<TD>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
                                        <TR>
                                 
                                        </TR>
                                        <TR>
                                            <TD>
                                                    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<tr>
												<td colspan="6" class="Heading1" align="center"><u><bean:message key="reportHeader"/></u>
												</td>
											</tr>
											<tr>
													<td colspan="6">&nbsp;</td>
											</tr>												  
											<TR>
												<TD class="Heading" width="30%">CLAIM SETTLED REPORT</TD>
												<TD class="Heading" width="30%">&nbsp;<bean:message key="from"/><bean:write name="rsForm" property="dateOfTheDocument26"/>
																				&nbsp;<bean:message key="to"/><bean:write name="rsForm" property="dateOfTheDocument27"/>
												</TD>
												<TD><IMG src="images/TriangleSubhead.gif" alt="" height="19" ></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" alt="" height="5" width="5"></TD>
											</TR> 
                                                    </TABLE>
                                                </TD>
                                        </TR>
					<TR>
                                                
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
						<!--		<TR>
									<TD colspan="16"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<tr>
												<td colspan="6" class="Heading1" align="center"><u><bean:message key="reportHeader"/></u>
												</td>
											</tr>
											<tr>
													<td colspan="6">&nbsp;</td>
											</tr>												  
											<TR>
												<TD class="Heading" width="30%">CLAIM SETTLED REPORT</TD>
												<TD class="Heading" width="30%">&nbsp;<bean:message key="from"/><bean:write name="rsForm" property="dateOfTheDocument26"/>
																				&nbsp;<bean:message key="to"/><bean:write name="rsForm" property="dateOfTheDocument27"/>
												</TD>
												<TD><IMG src="images/TriangleSubhead.gif" alt="" height="19" ></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" alt="" height="5" width="5"></TD>
											</TR> 
										</TABLE>
									</TD>
								</TR> -->
							<% 
								ArrayList arraylist = null;
								String ASFStringArray[]=null;
								String size=(String)request.getAttribute("pendingCaseDetailsArray_size");
							  
								double totalAsf_summary_Amount=0.0d;
								if(size=="0")
								{
								out.println("<tr><td class=\"Heading\" colspan=\"11\"><center>No Data Found</center</td></tr>");
								}
								//if(size!=null && size!="0")
								//{
								arraylist=(ArrayList)request.getAttribute("pendingCaseDetailsArray");
							%> 								
								<TR class="tableData">											  
									<th class="HeadingBg">SL No</th>
									<th class="HeadingBg">BANK NAME</th>
									<th class="HeadingBg">ZONE NAME</th>
									<th class="HeadingBg">MEMBER ID</th>
									<th class="HeadingBg">CGPAN</th>
									<th class="HeadingBg">UNIT NAME</th>										   
									<th class="HeadingBg">APPROVED AMOUNT</th>
									
									<th class="HeadingBg">Revised OUTSTANDING AMOUNT AS ON NPA</th>
									<th class="HeadingBg">AMOUNT RECOVERED AFTER  NPA</th>											  
									<th class="HeadingBg">NET OUTSTANDING AMOUNT</th>
									<th class="HeadingBg">AMOUNT CLAIMED BY MLI</th>
									<th class="HeadingBg">CLAIM ELIGIBLE AMOUNT</th> 											  
									<th class="HeadingBg">AMOUNT PAYBLE IN FIRST INSTALMENT</th>
									<th class="HeadingBg">ASF DEDUCTED AMOUNT</th>
                                    <th class="HeadingBg">ASF REFUNDABLE AMOUNT</th>
                                     <!-- New Added columns for swatch bharat tast start from here-->
                                     <th class="HeadingBg">SWBH SERVICE TAX</th>
                                    <th class="HeadingBg">SERVICE TAX DED</th>
                                     <!-- New Added columns for swatch bharat tast end here-->                                   
									<th class="HeadingBg">NET PAID AMOUNT</th>
                                    <th class="HeadingBg">UTR NO.</th>
									<th class="HeadingBg">ACCOUNT NO.</th>
									<th class="HeadingBg">OUTWARD NO.</th>
									<th class="HeadingBg">OUTWARD DATE</th>  
                                   <th class="HeadingBg">CLAIM APPROVED DATE</th>
									<th class="HeadingBg">CLAIM PAYMENT DATE</th>
								</TR>
                          <%
				String totalFirstInstAmount="";
				double totFirstInstAmount = 0.0d;
				String totFirstInstAmountStr="";
				
				String totalASFDeducted="";
				double totASFDeducted = 0.0d;
				String totASFDeductedStr="";
				
				String totalNetPaidAmount="";
				double totNetPaidAmount = 0.0d;
				String totNetPaidAmountStr="";
                                String totalRefundAmount = "";
                                double totRefundAmount = 0.0d;
                                String totRefundAmountStr = "";
                                
                            	String totalSWBHSerTax="";
                            	double totSWBHSerTax = 0.0d;
                            	
                            	String totalSWBHSerTaxDiducted="";
                            	double totSWBHSerTaxDiducted = 0.0d;
			   %>
			   <%DecimalFormat decimalFormat = new DecimalFormat("##########.#####");%>
                <%  
               
              for(int count=0;count<arraylist.size();count++)
    {
	
	
      double asf_amount=0.0d;
      ASFStringArray=new String[19];
      ASFStringArray=(String[])arraylist.get(count);
 	%>
 
                <tr>
				<td class="ColumnBackground1">&nbsp;<%=count+1%></td>
                  

                  <td class="ColumnBackground1">
                    <%= ASFStringArray[0]%>
                  </td>
                  <td class="ColumnBackground1">
                    <%= ASFStringArray[1]%>
                  </td>
                  <td class="ColumnBackground1">
                    <%= ASFStringArray[2]%>
                  </td>
                  <td class="ColumnBackground1">
                    <%= ASFStringArray[3]%>
                  </td>
                   <td class="ColumnBackground1">
                    <%= ASFStringArray[4]%>
                  </td>
                  <td class="ColumnBackground1">
                    <%= ASFStringArray[5]%>
                  </td>
                   <td class="ColumnBackground1">
                    <%= ASFStringArray[6]%>
                  </td>
                  
                 <td class="ColumnBackground1">
                    <%= ASFStringArray[7]%>
                  </td>  
                
				  <td class="ColumnBackground1">
                    <%= ASFStringArray[8]%>
                  </td>
				  <td class="ColumnBackground1">
                    <%= ASFStringArray[9]%>
                  </td>
				  <td class="ColumnBackground1">
                    <%= ASFStringArray[10]%>
                  </td>
                  <td class="ColumnBackground1">
                    <%= ASFStringArray[11]%>
					<%
						totalFirstInstAmount = ASFStringArray[11];
						double tempamt1 =  Double.parseDouble(totalFirstInstAmount);
						totFirstInstAmount = totFirstInstAmount + tempamt1;
					%>
                  </td>
		  <td class="ColumnBackground1">
                    <%= ASFStringArray[12]%>
					<%
						totalASFDeducted = ASFStringArray[12];
						double tempamt2 = Double.parseDouble(totalASFDeducted);
						totASFDeducted = totASFDeducted + tempamt2;
					%>
                  </td>
                  <td class="ColumnBackground1">
                    <%= ASFStringArray[20]%>
					<%
						 totalRefundAmount = ASFStringArray[20];
						 double tempamt4 = Double.parseDouble(totalRefundAmount);
						 totRefundAmount = totRefundAmount + tempamt4;
					%>
                  </td>
                  
                  <%
						totalNetPaidAmount = ASFStringArray[13];
                                                double tempamt3 = 0.0;
                                               
                                               if("Y".equals(ASFStringArray[21])){
                                                    tempamt3 = Double.parseDouble(totalFirstInstAmount) + Double.parseDouble(ASFStringArray[20]);
                                                   
                                                }else{
                                                    tempamt3  = Double.parseDouble(totalNetPaidAmount);
                                                }
                                                 totalNetPaidAmount = String.valueOf(tempamt3);
                                                totNetPaidAmount = totNetPaidAmount + tempamt3;
                                               
                                        %>
                  <td class="ColumnBackground1">
                      <%= ASFStringArray[22] %>
                    
                    	<%
						totalSWBHSerTax = ASFStringArray[22];
						double tempamt22 = Double.parseDouble(totalSWBHSerTax);
						totSWBHSerTax = totSWBHSerTax + tempamt22;
					%>
					
                  </td>
                   <td class="ColumnBackground1">
                    <%= ASFStringArray[23] %>
					<%
						totalSWBHSerTaxDiducted = ASFStringArray[23];
						double tempamt23 = Double.parseDouble(totalSWBHSerTaxDiducted);
						totSWBHSerTaxDiducted = totSWBHSerTaxDiducted + tempamt23;
					%>
                  </td>
                  <td class="ColumnBackground1">
                    <%= totalNetPaidAmount %>
					
                  </td>
                  
                  <td class="ColumnBackground1">
                    <%= ASFStringArray[14]%>
                  </td>
                  <td class="ColumnBackground1">
                    <%= ASFStringArray[15]%>
                  </td>	
                   <td class="ColumnBackground1">
                    <%= ASFStringArray[16]%>
                  </td>
                  <td class="ColumnBackground1">
                    <%= ASFStringArray[17]%>
                  </td>	
                   <td class="ColumnBackground1">
                    <%= ASFStringArray[18]%>
                  </td>
                  <td class="ColumnBackground1">
                    <%= ASFStringArray[19]%>
                  
                  
								</TR>	
								<%}%>
								<TR class="ColumnBackground1">
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td><b>Total</b></td>										
										<td class="ColumnBackground1"><%=decimalFormat.format(totFirstInstAmount)%></td>
                                                                                <td class="ColumnBackground1"><%=decimalFormat.format(totASFDeducted)%></td>
                                                                                <td class="ColumnBackground1"><%=decimalFormat.format(totRefundAmount)%></td>
                                                                                 <td class="ColumnBackground1"><%=totSWBHSerTax%></td>
                                                                                <td class="ColumnBackground1"><%=totSWBHSerTaxDiducted%></td>
                                                                                <td class="ColumnBackground1"><%=decimalFormat.format(totNetPaidAmount)%></td>
                                                                                <td></td>
										<td></td>
										<td></td>
                                                                                <td></td>
										<td></td>
                                                                                <td></td>
<!--										<td></td>-->
								</TR>
								<TR>                     
										  <TD width="10%" align="left"  valign="top" class="ColumnBackground">
												<div align="right"></div>
										  </td>
											<TD width="50%" align="left" colspan="3" valign="top" class="ColumnBackground">						
											</TD>
								</TR>  									
							</TABLE>
						</TD>
					</TR>
					<TR >
						<TD height="20">
							&nbsp;
						</TD>
					</TR>
					<TR>
						<td colspan="3" align="left" width="700"><font size="2" color="red">Report Generated On :
                                                        <% 
                                                             java.util.Date loggedInTime=new java.util.Date();
                                                             java.text.SimpleDateFormat dateFormat1=new java.text.SimpleDateFormat("dd MMMMM yyyy ':' HH.mm");
                                                             String date1=dateFormat1.format(loggedInTime);
                                                                out.println(date1);
                                                         %> 
					hrs.</font></td>
					</TR>
					<TR>
						<TD align="center" valign="baseline" >
							<DIV align="center">						
								<A href="javascript:history.back()">
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
    </TABLE> 
</html:form>

