<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@page import ="java.text.SimpleDateFormat"%>
<%@page import ="java.text.DecimalFormat"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","gfdanReport.do?method=gfdanReport");%>
<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>
<%DecimalFormat decimalFormat = new DecimalFormat("##########.##");%>
<script type="text/javascript">
	window.onload= function(){
		document.getElementById("msg").focus();
	}
</script>
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="gfdanReport.do?method=gfdanReport" method="POST" enctype="multipart/form-data">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/ReportsHeading.gif" width="121" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
								<tr>
									<TD colspan="8"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<tr>
                          								<td colspan="6" class="Heading1" align="center"><u><bean:message key="reportHeader"/></u></td>
                      									</tr>
														
									<tr>
												<td colspan="12">
													<marquee style="color:red;" scrollamount="200" scrolldelay="1200">The amounts mentioned under Service Tax, Education Cess and Higher education Cess are
														<b> already included in demand generated amount in Rs.</b>and are displayed for your information. 
														Users are requested to <b>make payment of amount mentioned under demand generated amount in Rs. only.</b></marquee>
												</td>
											</tr>
														
                      									<tr> <td colspan="6">&nbsp;</td></tr>
                      									<TR>
												<TD width="15%" class="Heading"><bean:message key="danReport" /></TD>
												<td class="Heading" width="30%">&nbsp;
													<bean:message key="from"/> <bean:write  name="rsForm" property="dateOfTheDocument20"/>&nbsp;
													<bean:message key="to"/> <bean:write  name="rsForm" property="dateOfTheDocument21"/></td>
                        									<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="6" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>
								</tr>
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								
								<TR>
									

									<TR>
                  							<td width="3%" align="left" valign="top" class="ColumnBackground"><bean:message key="sNo"/></td>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="memberId" />
									</TD>
                                                                         <TD width="10%" align="left" valign="top" class="ColumnBackground">
										Branch Name
									</TD> 
                                                     			<!-- added by sukumar -->
                                              				<TD width="10%" align="left" valign="top" class="ColumnBackground">
                        							<bean:message key="cgpanNumber" />
                      							</TD>
                                      					 <TD width="10%" align="left" valign="top" class="ColumnBackground">
                        							<bean:message key="ssiName"/>
                     							 </TD>

                                                    			 <!--            -->
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="daNumber" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="generatedDate" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="noOfAppls" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="danAmount"/>
									</TD>

									<%	double sum = 0;
										double totalSum = 0;
										int count = 0;
										int totalCount = 0;
										double totalst = 0;
										double totalecess = 0;
										double totalhecess = 0;
									%>
                                                                        
                                                                        <TD width="10%" align="left" valign="top" class="ColumnBackground">
										Included Service Tax
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										Included Ecess
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										Included S & H Ecess
									</TD>
                                                                        
                                                                        
									</TR>	

									<tr>
									<logic:iterate id="object" name="rsForm" property="gfdan" indexId="index">
									<%
									     com.cgtsi.reports.DanReport dReport =  (com.cgtsi.reports.DanReport)object;
										 
									%>
							
											<TR>
                      <td align="left" valign="top" class="ColumnBackground1"><%=Integer.parseInt(index+"")+1%></td>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">
											<%=dReport.getMemberId()%>
											<%String bank=dReport.getBank();
											String zone=dReport.getZone();
											String branch=dReport.getBranch();
 String branchName = dReport.getBranchName();%>
											<%   java.util.Date utilDate=dReport.getDanDate();
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
											</TD>
                                                                                        <td align="left" valign="top" class="ColumnBackground1">
                   <%=branchName%> 
                        	 
                     </td>
                                                            <!-- added by sukumar -->

                                                                 <TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%String cgpan=dReport.getCgpan();
											%>
											<%=cgpan%>
											</TD>
                                                                   
                                                               <TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%String ssi=dReport.getSsi();
											%>
											<%=ssi%>

											</TD>

                                                            <!--                  -->
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%String danId=dReport.getDan();
											String url = "danReportDetails.do?method=danReportDetails&danValue="+danId+"&bankName="+bank+"&zoneName="+zone+"&branchName="+branch+"&bName="+branchName+"&date="+formatedDate;%>
											<html:link href="<%=url%>"><%=danId%></html:link>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%=formatedDate%>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">
											<div align="right">
											<%count=dReport.getCount();
											   totalCount = totalCount + count;%>
											   <%=count%>
											    </div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">		
											<div align="right">
											   <% sum=dReport.getTotalAmount();
											   totalSum = totalSum + sum;
											   if(sum == 0)
											   {
											   %>
											   <%="-"%>
											   <%
											   }
											   else
											   {
											   %>
											   <%=decimalFormat.format(sum)%>
											   <%
											   }
											   %>

											   </div>
											   </TD>
                                                                                           
                                                                                           <TD width="10%" align="left" valign="top" class="ColumnBackground1">		
											<div align="right">
											   <% double inclSTax =dReport.getInclSTaxAmnt();
													totalst = totalst + inclSTax;
											   %>
                                                                                            <%=decimalFormat.format(inclSTax)%>
											   </div>
											   </TD>
											   <TD width="10%" align="left" valign="top" class="ColumnBackground1">		
											<div align="right">
											  <%
											  double inclecess = dReport.getInclECESSAmnt();
												totalecess = totalecess + inclecess;
											  %>
                                                                                            <%=decimalFormat.format(inclecess)%>
											   </div>
											   </TD>
                                                                                         <TD width="10%" align="left" valign="top" class="ColumnBackground1">		
											<div align="right">
												<%
													double inclhecess = dReport.getInclHECESSAmnt();
													totalhecess = totalhecess + inclhecess;
												%>
											   
                                                                                                <%=decimalFormat.format(inclhecess)%>
											   </div>
                                                                                        </TD>
                                                                                           
											</TR>
											</logic:iterate>

									<TR>
									<TD width="10%" colspan="2" align="left" valign="top" class="ColumnBackground">
									Total
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
									
									</TD>

									<TD width="10%" align="left" valign="top" class="ColumnBackground">
									
									</TD>

									<TD width="10%" align="left" valign="top" class="ColumnBackground">
									
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
									
									</TD>
                                                                         <TD width="10%" align="left" valign="top" class="ColumnBackground">
									
									</TD>

									<TD width="10%" align="left" valign="top" class="ColumnBackground">
									<div align="right">
									<%=totalCount%>
									</div>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
									<div align="right">
									<%=decimalFormat.format(totalSum)%>
									</div>
									</TD>
                                    <TD width="10%" align="left" valign="top" class="ColumnBackground">
									<div align="right">
									<%=decimalFormat.format(totalst)%>
									</div>
									</TD>
                                    <TD width="10%" align="left" valign="top" class="ColumnBackground">
									<div align="right">
									<%=decimalFormat.format(totalecess)%>
									</div>
									</TD>
                                    <TD width="10%" align="left" valign="top" class="ColumnBackground">
									<div align="right">
									<%=decimalFormat.format(totalhecess)%>
									</div>
									</TD>
									</TR>	
									</TABLE>
					</TD>
					</TR>
					
					<tr>
						<td style="font:15px;color:green;" id="msg"><b>Note:</b>
							The amounts mentioned under Service Tax, Education Cess and Higher education Cess are
							<b> already included in demand generated amount in Rs.</b>and are displayed for your information. 
							Users are requested to <b>make payment of amount mentioned under demand generated amount in Rs. only.</b>
						</td>
					</tr> 
					
                                        
                                      <tr><td>
					<table width="100%" border="0" cellspacing="1" cellpadding="0">
					<TR >
						<TD align="left" colspan="4" valign="top" class="SubHeading">
							Service Category : Other Taxable Services - Other Than The 119 LISTED
						</TD>
					</TR>
					<TR >
						
                        <td align="left" valign="top" class="ColumnBackground"><div align="center">&nbsp;Service Tax Number:</div></td>
						
						<TD align="left" valign="top" class="ColumnBackground"><div align="leftt">	&nbsp;AAATC2613DSD001</div></td>
						
						<td align="left" valign="top" class="ColumnBackground"><div align="center">	&nbsp;PAN Number:</div></td>
										
						<td align="left" valign="top" class="ColumnBackground"><div align="leftt">	&nbsp;AAATC2613D</div></TD>
						
					</TR>
					</table>
					</td>
					</tr>
                                       
                                        
                                        
					<TR >
						<TD height="20" >
							&nbsp;
						</TD>
					</TR>
					<TR >
						<TD align="center" valign="baseline" >
							<DIV align="center">
								<A href="javascript:submitForm('gfdanReportInput.do?method=gfdanReportInput')">
									<IMG src="images/Back.gif" alt="Print" width="49" height="37" border="0"></A>

									
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

