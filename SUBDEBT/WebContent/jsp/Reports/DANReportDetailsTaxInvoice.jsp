<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="org.apache.struts.action.DynaActionForm"%>
<%@page import="com.cgtsi.common.CommonDAO"%>
<%@page import="com.cgtsi.reports.DanReport"%>
<%@page import="java.util.Iterator"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@ include file="/jsp/SetMenuInfo.jsp"%>

<% session.setAttribute("CurrentPage","danReportDetails.do?method=danReportDetails");%>
<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>
<%DecimalFormat decimalFormat = new DecimalFormat("##########.#####");%>

<% DynaActionForm dynaForm = (DynaActionForm)session.getAttribute("rsForm");
	String danId = request.getParameter("danValue");
	String danType = danId.substring(0,2);
	String date = request.getParameter("date");
	System.out.println("date"+date);
  String bName = request.getParameter("bName");
  String memid = request.getParameter("memid");

	String value = (String)dynaForm.get("memberId");
	String bank = (String)request.getParameter("bankName");
	String zone = (String)request.getParameter("zoneName");
	if((zone == null) || (zone.equals("null")))
	{
		zone="";	
	}
	String branch = (String)request.getParameter("branchName");
	if(branch == null || branch.equals("null"))
	{
		branch="";
		
	}
%>



<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="danReport.do?method=danReport" method="POST"
		enctype="multipart/form-data">
		<TR>
			<TD width="20" align="right" valign="bottom"><IMG
				src="images/TableLeftTop.gif" width="20" height="31" alt=""></TD>
			<TD background="images/TableBackground1.gif"><IMG
				src="images/ReportsHeading.gif" width="121" height="25" alt=""></TD>
			<TD width="20" align="left" valign="bottom"><IMG
				src="images/TableRightTop.gif" width="23" height="31" alt=""></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<TABLE width="100%" border="0" align="left" cellpadding="0"
					cellspacing="0">
					<TR>
						<TD>
						<table>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="21">
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                                                                                
                                                                                <% if("GF".equalsIgnoreCase(danType)||"sf".equalsIgnoreCase(danType)||"aF".equalsIgnoreCase(danType)||"cg".equalsIgnoreCase(danType)||"RS".equalsIgnoreCase(danType))
                            {
                            %>
                            <tr id="invoice" >
                               <td colspan="6"  align="center"><u>Tax Invoice</u></td>
                              
                            </tr>
                            <% }%>
                           
                            <% if("CN".equalsIgnoreCase(danType))
                            {
                            %>
                            <tr id="creditnote" >
                                <td colspan="6"  align="center"><u><bean:message key="creditnote"/></u></td>
                            </tr>
                            <% }%>
                            
                                                                                       
											<tr>
												<td colspan="6"  align="center"><u><bean:message
															key="reportHeader1" /></u></td>
                                                                                                                        
                                                                                                                        
											</tr>
                                                                                        
                                                                                        <tr>
                                                                                        
                                                                                                 <TD colspan="6"  align="center">
                                                                                       
                                                                                                    <h6>
                                                                                                    (Set up by Govt. of India and SIDBI)
                                                                                                    <br/>
                                                                                                    GSTIN:27AAATC2613D1ZC    STATE:Maharastra-MH
                                                                                                    <br/>
                                                                                           Office No.1002-1003,Naman Center,10th Floor,C-31,G-Block,Bandra Kurla Complex,Bandra(EAST),Mumbai-400051   Toll Free No.1800222659 Fax No.26597264
                                                                                           <html:link href="http://www.cgtmse.in" target="_blank">www.cgtmse.in</html:link> 
                                                                                           
                                                                                            
                                                                                                    </h6>
                                                                                             </TD>
                                                                                        
                                                                                        </tr>
                                                                                     
                                                                                        
                                                                                        
                                                                                        
											<tr>
												<td align="left" colspan="6" >&nbsp; <h6><font class="fontsized">Tax Invoice No:
												
												<%
											
											ArrayList list=(ArrayList)session.getAttribute("danDetails");
											if(list.size()>0){
												Iterator iter=list.iterator();
												while(iter.hasNext()){
													DanReport danDetails=(DanReport)iter.next();
													String guaranteeStartDate1 = null;
													 java.util.Date dcgadate=danDetails.getDciguaranteestartdate();
													 
													 guaranteeStartDate1 =dateFormat.format(dcgadate);
                                               
                                                       
											%>
												
												
												
												
												<%=danDetails.getDanproforma()%>
												</font>
												 </h6>
												
												</td>
												<TD align="left" valign="top">
													<h6><font class="fontsized">Date:<%=guaranteeStartDate1%></font></h6>
												</TD>
											</tr>
										
												
													
												
											
											<TR>
												<TD align="left" valign="top" ><h6><font class="fontsized">MLI Name:<%=danDetails.getBankname()%></font></h6>
												</TD>
												
												
											</TR>
											<TR>
												<TD align="left" valign="top" ><h6><font class="fontsized">Branch Name:<%=danDetails.getBranchname()%></font></h6>
												
												</TD>
											
												<TD align="left" valign="top" ><h6><font class="fontsized">
													GSTIN:<%=danDetails.getGstno()%></font></h6>
													
												</TD>
												
												
											</TR>
										
											<TR>
												<TD align="left" valign="top" colspan="6"><font class="fontsized"><h6>Address of Principal Place of Business:<%=danDetails.getBankaddress()%></h6></font>
												
												</TD>
												<TD align="left" valign="top" ><h6><font class="fontsized">State:<%=danDetails.getStatename()%></font></h6>
											</TD>
											</TR>
											
											
											
<%}
		}%>
											<TR>
												<TD class="Heading" width="40%"><bean:message
														key="danReportDetailsHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19"
													height="19" alt=""></TD>
											</TR>
											<TR>
												<TD width="20%" colspan="5" class="Heading"><IMG
													src="images/Clear.gif" width="5" height="5" alt=""></TD>
											</TR>
										</TABLE>
									</TD>
								</TR>
                                                                <%
                                                                %>
                                                                   
									<logic:iterate id="object" name="rsForm" property="dan"	indexId="index">
									<%
                                                                                double sum = 0;
                                                                                double totalSum = 0;
                                                                                double gFee = 0;
                                                                                double totalgFee = 0;
                                                                                double gFeePaid = 0;
                                                                                double totalgFeePaid = 0;
                                                                                int count = 0;
                                                                                int totalCount = 0;
                                                                                double totalbaseamt = 0;
                                                                                double totalst = 0;
                                                                                double totalecess = 0;
                                                                                double totalhecess = 0;
                                                                                double totalsbcess = 0;
                                                                                double totalSBhecess = 0;//rajuk
                                                                                
                                                                              int igstRate = 0; //rajuk
                                                                              double igstAmt = 0;
                                                                              int cgstRate = 0;
                                                                              double cgstAmt = 0;
                                                                              int sgstRate = 0;
                                                                              double sgstAmt = 0;
                                                                             double  totalamount= 0;
                                                                             double taxableamount = 0;
                                                                            
                                                                          
        
                                                                             
                                                                        com.cgtsi.reports.DanReport dReport =  (com.cgtsi.reports.DanReport)object;
                                                                                gFeePaid=dReport.getGuaranteeFeePaid();
                                                                                totalgFeePaid = totalgFeePaid + gFeePaid;
                                                                                DecimalFormat dec = new DecimalFormat("#0.00");	
                                                                                int srNo = Integer.parseInt(index+"")+1 ;
                                                                                String memberId = dReport.getMemberId();
                                                                                String memberLandingIns = dReport.getCgpan();
                                                                                String url = "securitizationReportDetailsForCgpan.do?method=securitizationReportDetailsForCgpan&number="+memberLandingIns;
                                                                                String ssi = dReport.getSsi() ;
                                                                                
                                                                                sum=dReport.getTotalAmount();
                                                                                String sancAmt=decimalFormat.format(sum);
                                                                                
                                                                                sum=dReport.getAppAmount();
                                                                                totalSum = totalSum + sum;
                                                                                String approvedAmount =decimalFormat.format(sum);
                                                                               
                                                                                sum=dReport.getReAppAmount();
                                                                                String Reappamt=decimalFormat.format(sum);
                                                                                
                                                                                java.util.Date utilDate3=dReport.getDanDate();
										String danDate = null;
										if(utilDate3 != null)
										{
                                                                                     danDate=dateFormat.format(utilDate3);
										}
										else
										{
                                                                                    danDate = "";
										}
                                                                                gFee=dReport.getGuaranteeFee();
										totalgFee = totalgFee + gFee;
                                                                                
                                                                                String guranteeFee= gFee==0?"-":decimalFormat.format(gFee) ;
                                                                                
                                                                                String status = dReport.getStatus() ;
                                                                                String appNumber = dReport.getApplicationNumber();
                                                                                
                                                                                java.util.Date utilDate=dReport.getApplicationDate();
										String applicationDate = null;
										if(utilDate != null)
										{
                                                                                    applicationDate=dateFormat.format(utilDate);
										}
										else
										{
                                                                                    applicationDate = "";
										}
                                                                                
                                                                                java.util.Date startDate=dReport.getGuaranteeStartDate();
										String guaranteeStartDate = null;
										if(startDate != null)
										{
                                                                                    guaranteeStartDate=dateFormat.format(startDate);
										}
										else
										{
                                                                                    guaranteeStartDate = "";
										}
                                                                                
                                                                                String newDanId=dReport.getDan();
										String newDanId1= null ;
                                                                                if((newDanId == null) || (newDanId.equals("")))
										{
                                                                                    newDanId1= "" ;
										}
										else
										{
                                                                                    newDanId1 =newDanId ;
										}
                                                                                
                                                                                java.util.Date utilDate1=dReport.getCloseDate();
										String closeDate = null;
										if(utilDate1 != null)
										{
                                                                                    closeDate=dateFormat.format(utilDate1);
										}
										else
										{
                                                                                    closeDate = "";
										}
                                                                                String appropriatedBy = dReport.getAppropriationBy();
                                                                                
                                                                                double baseAmt =dReport.getBaseAmnt();
										totalbaseamt = totalbaseamt + baseAmt;
										String baseAmountFormated =decimalFormat.format(baseAmt);
                                                                                
                                                                                double inclSTax =dReport.getInclSTaxAmnt();
										totalst = totalst + inclSTax;
										String inclSTaxFormated =decimalFormat.format(inclSTax);
                                                                                
                                                                                double inclecess = dReport.getInclECESSAmnt();
										totalecess = totalecess + inclecess;
										String includedCessAmount=decimalFormat.format(inclecess);
                                                                                
                                                                                double inclhecess = dReport.getInclHECESSAmnt();
                                                                                    totalhecess = totalhecess + inclhecess;
                                                                    
										                               String includedHigherEduAmount =decimalFormat.format(inclhecess);
										
										double inclhSBecess = dReport.getSwatchBharatTax();
										totalSBhecess = totalSBhecess + inclhSBecess;
                                        String includswbhcess =decimalFormat.format(inclhSBecess);
                                        double totalamt=dReport.getTotalAmount();
                                        
                                        java.util.Date startDate1=dReport.getGuaranteeStartDate();
										//String guaranteeStartDate = null;
										if(startDate1 != null)
										{
                                                                                    guaranteeStartDate=dateFormat.format(startDate1);
										}
										else
										{
                                                                                    guaranteeStartDate = "";
										}
                                        
										String Reapproveddate=null;
										
								         java.util.Date startDate2=dReport.getReapproveddate();
											//String guaranteeStartDate = null;
											if(startDate2 != null)
											{
												Reapproveddate=dateFormat.format(startDate2);
											}
											else
											{
												Reapproveddate = "";
											}
	                                        
										
                                        
                                                                                
									%>
									
                                                    	
								
                                                                
								<% if(danType.equalsIgnoreCase("GF")||danType.equalsIgnoreCase("CG")||danType.equalsIgnoreCase("RS")) 
                                                                {  %>
                                                                 
									     
                    <td colspan="5"><table width="100%" border="0" cellspacing="1">
  					<tr>
  						
                                                                <TD width="3%" align="left" valign="top"  class="ColumnBackground"> <bean:message key="sNo" /></TD>
                                                                <TD width="10%" align="left" valign="top" class="ColumnBackground">  Member Id</TD>
                                                                <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> <bean:message key="cgpanNumber" /></TD>
                                                                 <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> <bean:message key="ssiName" /></TD>               
                                                                <TD width="10%" align="left" valign="top"
										class="ColumnBackground">Sanctioned Amount</TD>
                                                                 <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> Approved Amount</TD>
                                                                <TD width="10%" align="left" valign="top"
										class="ColumnBackground">Guarantee StartDate**</TD> 
										  <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> Tenure (Months)</TD>                
                                                                <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> ReApproved Amount</TD>
                                                                <TD width="10%" align="left" valign="top"
										class="ColumnBackground">ReApproved Date</TD>
                                                               
                                                                 <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> Application Number</TD>
                                                                <TD width="10%" align="left" valign="top"
										class="ColumnBackground">Application Submitted Date</TD>                
                                                                 <TD width="10%" align="left" valign="top"
										class="ColumnBackground">Taxable Amount</TD>
                               				 <td colspan="2" class="ColumnBackground" bordercolor="white"><table width="100%" border="0" cellspacing="1" colspan="2">
										 <tr>
										 <td width="10%" align="left" valign="top" class="ColumnBackground" colspan="2"><center>IGST</center></td>
										 </tr>
										 <tr><td width="10%" align="left" valign="top" align="left" class="ColumnBackground">Rate</td>
										 <td width="10%" align="left" valign="top" align="right" class="ColumnBackground">Amt</td></tr>
										 </table>	
										 
										  <td colspan="2" class="ColumnBackground" bordercolor="white"><table width="100%" border="0" cellspacing="1" colspan="2">
										 <tr>
										 <td width="10%" align="left" valign="top" class="ColumnBackground" colspan="2"><center>CGST</center></td>
										 </tr>
										 <tr><td width="10%" align="left" valign="top" align="left" class="ColumnBackground">Rate</td>
										 <td width="10%" align="left" valign="top" align="right" class="ColumnBackground">Amt</td></tr>
										 </table>	
										 
										  <td colspan="2" class="ColumnBackground" bordercolor="white"><table width="100%" border="0" cellspacing="1" colspan="2">
										 <tr>
										 <td width="10%" align="left" valign="top" class="ColumnBackground" colspan="2"><center>SGST</center></td>
										 </tr>
										 <tr><td width="10%" align="left" valign="top" align="left" class="ColumnBackground">Rate</td>
										 <td width="10%" align="left" valign="top" align="right" class="ColumnBackground">Amt</td></tr>
										 </table> 
										
										</TD>
										 
										<TD width="10%" align="left" valign="top"
										class="ColumnBackground">Total Amount</TD>
									    </tr>  
  				
  					
					
										
                                                                                
                                                                 
                                                                    <TR>
                                                                            <TD align="left" valign="top" class="ColumnBackground1"><%=srNo%>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top"	class="ColumnBackground1"><%=memid%>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                                                                                <html:link href="<%=url%>"><%=memberLandingIns%></html:link>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                                                                                <%=ssi%>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" 	class="ColumnBackground1">
                                                                                <div align="right"><%=dec.format(dReport.getSanctionedAmount())%>
                                                                                </div>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                                                                                <div align="right"><%=dec.format(dReport.getApprovedAmount())%></div>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" 	class="ColumnBackground1">
                                                                                <div align="right"><%=guaranteeStartDate%></div>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" 	class="ColumnBackground1">
                                                                                 <%=dReport.getTenure()%>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" 	class="ColumnBackground1">
                                                                                <div align="right"><%=dReport.getReAppAmount()%></div>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                                                                                <%=Reapproveddate%>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                                                                                <%=dReport.getApplicationNumber()%>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                                                                                 <%=dReport.getApplicationDate()%>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                                                                           <div align="right"><%=dec.format(dReport.getTaxableamount())%>
										                                    
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" class="ColumnBackground1">
										                                     <div align="right"> <%=dReport.getIgstRate()%>%
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                                                                             <div align="right"><%=dec.format(dReport.getIgstAmt())%>   
    `                                                                       </TD>
                                                                            <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                                                                              <div align="right">  <%=dReport.getCgstRate()%>%
                                                                            </TD>
                                                                                                                                                   
                                                                            <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                                                                                <div align="right"><%=dec.format(dReport.getCgstAmt())%></div>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top"	class="ColumnBackground1">
                                                                                <div align="right"><%=dReport.getSgstRate()%>%</div>
                                                                            </TD>                                                                            
									     <TD width="10%" align="left" valign="top"	class="ColumnBackground1">
										<div align="right"><%=dec.format(dReport.getSgstAmt())%></div>
									     </TD>
									      <TD width="10%" align="left" valign="top"	class="ColumnBackground1">
                                                                                <div align="right">
                                                                                 <%=dec.format(dReport.getTotalAmount())%>
                                                                                </div>
                                                                            </TD>                                                                            
									  
                                                                	</TR>
                                                                           
                                                                <%   }%>
                                                                
                                                               
                                                                <% if(danType.equalsIgnoreCase("AF")) 
                                                                {  %>
                                                                <tr>
                                                                <TD width="3%" align="left" valign="top"
										class="ColumnBackground"> <bean:message key="sNo" /></TD>
                                                                <TD width="10%" align="left" valign="top"
										class="ColumnBackground">  Member Id</TD>
                                                                <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> <bean:message key="cgpanNumber" /></TD>
                                                                 <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> <bean:message key="ssiName" /></TD>               
                                                                <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> <bean:message
											key="totalSanctionAmount" /></TD>
                                                                 <TD width="10%" align="left" valign="top"
										class="ColumnBackground">  <bean:message
											key="amountApprovedRs" /></TD>
                                                                <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> <bean:message
											key="reapprovedAmount" /></TD>                
                                                                <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> ReApproved Date</TD>
                                                                <TD width="10%" align="left" valign="top"
										class="ColumnBackground"><bean:message key="sFee" /></TD>
                                                                 <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> Status</TD> 
                                                                 <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> <bean:message
											key="applicationNumber" /></TD>
                                                                <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> <bean:message
											key="applicationDate" /></TD>                
                                                                 <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> Guarantee Start Date</TD>
                                                                 
                                                                 <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> <bean:message key="closeDate" /></TD>
                                                                 <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> Appropriation By </TD>
                                                                  <TD width="10%" align="left" valign="top"
										class="ColumnBackground"><bean:message key="baseamount" /></TD>
                                                                 <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> <bean:message
											key="includedsertax" /></TD>
                                                                 <TD width="10%" align="left" valign="top"
										class="ColumnBackground">IGST</TD>
																<TD width="10%" align="left" valign="top"
										class="ColumnBackground">Included S.B Cess.</TD>
										                         <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> <bean:message key="incsandhecess" /></TD>
										                         <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> <bean:message key="danAmount" /></TD>
                                                                                
                                                                    </tr>    
                                                                    <TR>
                                                                            <TD align="left" valign="top" class="ColumnBackground1"><%=srNo%>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top"	class="ColumnBackground1"><%=memberId%>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                                                                                <html:link href="<%=url%>"><%=memberLandingIns%></html:link>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                                                                                <%=ssi%>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" 	class="ColumnBackground1">
                                                                                <div align="right"><%=sancAmt%></div>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                                                                                <div align="right"><%=approvedAmount%></div>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" 	class="ColumnBackground1">
                                                                                <div align="right"><%=Reappamt%></div>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" 	class="ColumnBackground1">
                                                                                 <%=danDate%>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" 	class="ColumnBackground1">
                                                                                <div align="right"><%=guranteeFee%></div>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                                                                                <%=status%>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                                                                                <%=appNumber%>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                                                                                 <%=applicationDate%>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" class="ColumnBackground1">
										 <%=guaranteeStartDate%>
                                                                            </TD>
                                                                          
                                                                            <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                                                                                <%=closeDate%>
    `                                                                       </TD>
                                                                            <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                                                                                <%=appropriatedBy%>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                                                                                <div align="right"><%=baseAmountFormated%></div>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top"	class="ColumnBackground1">
                                                                                <div align="right"><%=inclSTaxFormated%></div>
                                                                            </TD>
									     <TD width="10%" align="left" valign="top"	class="ColumnBackground1">
										<div align="right"></div>
									     </TD>	
									     								     
									     <TD width="10%" align="left" valign="top" class="ColumnBackground1">
										<div align="right"></div>
									    </TD>								     
										     
									     <TD width="10%" align="left" valign="top" class="ColumnBackground1">
										<div align="right"></div>
									    </TD>
									    
									    
                                                                            <TD width="10%" align="left" valign="top"	class="ColumnBackground1">
                                                                                <div align="right"><%=guranteeFee%></div>
                                                                            </TD>
                                                                	</TR>
                                                                           
                                                                <%   }%>
                                                                
                                                                 <% if(danType.equalsIgnoreCase("SF")) 
                                                                {  ;%>
                                                                <tr>
                                                                <TD width="3%" align="left" valign="top"
										class="ColumnBackground"> <bean:message key="sNo" /></TD>
                                                                <TD width="10%" align="left" valign="top"
										class="ColumnBackground">  Member Id</TD>
                                                                <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> <bean:message key="cgpanNumber" /></TD>
                                                                 <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> <bean:message key="ssiName" /></TD>               
                                                                <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> <bean:message
											key="totalSanctionAmount" /></TD>
                                                                 <TD width="10%" align="left" valign="top"
										class="ColumnBackground">  <bean:message
											key="amountApprovedRs" /></TD>
                                                                <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> <bean:message
											key="reapprovedAmount" /></TD>                
                                                                <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> ReApproved Date</TD>
                                                                <TD width="10%" align="left" valign="top"
										class="ColumnBackground"><bean:message key="sFee" /></TD>
                                                                 <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> Status</TD> 
                                                                 <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> <bean:message
											key="applicationNumber" /></TD>
                                                                <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> <bean:message
											key="applicationDate" /></TD>                
                                                                 <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> Guarantee Start Date</TD>
                                                                 

                                                                 <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> Appropriation By </TD>
                                                                 <TD width="10%" align="left" valign="top"
										class="ColumnBackground"><bean:message key="baseamount" /></TD>
                                                                 <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> <bean:message
											key="includedsertax" /></TD>
                                                                 <TD width="10%" align="left" valign="top"
										class="ColumnBackground"><bean:message key="includecess" /></TD>
										
										
										
										 <TD width="10%" align="left" valign="top"
										class="ColumnBackground">Included S.B Cess.</TD>
										
                                                                <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> <bean:message
											key="incsandhecess" /></TD>
                                                                 <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> <bean:message key="danAmount" /></TD>        
                                                                    </tr>    
                                                                    <TR>
                                                                            <TD align="left" valign="top" class="ColumnBackground1"><%=srNo%>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top"	class="ColumnBackground1"><%=memberId%>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                                                                                <html:link href="<%=url%>"><%=memberLandingIns%></html:link>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                                                                                <%=ssi%>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" 	class="ColumnBackground1">
                                                                                <div align="right"><%=sancAmt%></div>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                                                                                <div align="right"><%=approvedAmount%></div>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" 	class="ColumnBackground1">
                                                                                <div align="right"><%=Reappamt%></div>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" 	class="ColumnBackground1">
                                                                                 <%=danDate%>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" 	class="ColumnBackground1">
                                                                                <div align="right"><%=guranteeFee%></div>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                                                                                <%=status%>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                                                                                <%=appNumber%>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                                                                                 <%=applicationDate%>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" class="ColumnBackground1">
										 <%=guaranteeStartDate%>
                                                                            </TD>
                                                                          
                                                                            
                                                                            <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                                                                                <%=appropriatedBy%>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                                                                                <div align="right"><%=baseAmountFormated%></div>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top"	class="ColumnBackground1">
                                                                                <div align="right">/div>
                                                                            </TD>
									     <TD width="10%" align="left" valign="top"	class="ColumnBackground1">
										<div align="right"></div>
									     </TD>
									     <TD width="10%" align="left" valign="top" class="ColumnBackground1">
										<div align="right"></div>
									    </TD>
									     <TD width="10%" align="left" valign="top" class="ColumnBackground1">
										<div align="right"></div>
									    </TD>
                                                                            <TD width="10%" align="left" valign="top"	class="ColumnBackground1">
                                                                                <div align="right"><%=guranteeFee%></div>
                                                                            </TD>
                                                                	</TR>
                                                                           
                                                                <%    }%>
                                                                
                                                                
                                                                
                                                                <% if(danType.equalsIgnoreCase("CN")) 
                                                                {  ;%>
                                                                <tr>
                                                                <TD width="3%" align="left" valign="top"
										class="ColumnBackground"> <bean:message key="sNo" /></TD>
                                                                <TD width="10%" align="left" valign="top"
										class="ColumnBackground">  Member Id</TD>
                                                                <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> <bean:message key="cgpanNumber" /></TD>
                                                                 <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> <bean:message key="ssiName" /></TD>               
                                                                <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> <bean:message
											key="totalSanctionAmount" /></TD>
                                                                 <TD width="10%" align="left" valign="top"
										class="ColumnBackground">  <bean:message
											key="amountApprovedRs" /></TD>
                                                                <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> <bean:message
											key="reapprovedAmount" /></TD>                
                                                                <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> ReApproved Date</TD>
                                                                
                                                                
                                                                
                                                                 <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> Close Date</TD>
                                                                 <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> Appropriation By </TD>
                                                                   <TD width="10%" align="left" valign="top"
										class="ColumnBackground"><bean:message key="baseamount" /></TD>
                                                                 <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> <bean:message
											key="includedsertax" /></TD>
                                                                 <TD width="10%" align="left" valign="top"
										class="ColumnBackground"><bean:message key="includecess" /></TD>
                                                                <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> <bean:message
											key="incsandhecess" /></TD>
                                                                 <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> <bean:message key="danAmount" /></TD>
                                                                                
                                                                    </tr>    
                                                                    <TR>
                                                                            <TD align="left" valign="top" class="ColumnBackground1"><%=srNo%>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top"	class="ColumnBackground1"><%=memberId%>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                                                                                <html:link href="<%=url%>"><%=memberLandingIns%></html:link>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                                                                                <%=ssi%>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" 	class="ColumnBackground1">
                                                                                <div align="right"><%=sancAmt%></div>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                                                                                <div align="right"><%=approvedAmount%></div>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" 	class="ColumnBackground1">
                                                                                <div align="right"><%=Reappamt%></div>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" 	class="ColumnBackground1">
                                                                                 <%=danDate%>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                                                                                <%=closeDate%>
    `                                                                       </TD>
                                                                            <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                                                                                <%=appropriatedBy%>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                                                                                <div align="right"><%=baseAmountFormated%></div>
                                                                            </TD>
                                                                            <TD width="10%" align="left" valign="top"	class="ColumnBackground1">
                                                                                <div align="right"><%=inclSTaxFormated%></div>
                                                                            </TD>
									     <TD width="10%" align="left" valign="top"	class="ColumnBackground1">
										<div align="right"><%=includedCessAmount%></div>
									     </TD>
									     <TD width="10%" align="left" valign="top" class="ColumnBackground1">
										<div align="right"><%=includedHigherEduAmount%></div>
									    </TD>
                                                                            <TD width="10%" align="left" valign="top"	class="ColumnBackground1">
                                                                                <div align="right"><%=guranteeFee%></div>
                                                                            </TD>
                                                                	</TR>
                                                                           
                                                                <%    }%>
                                                                
                         							</TABLE>

						</TD>
					
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD align="center" valign="baseline" class="ColumnBackground" width="20%">
										<DIV align="left">
											Total Amount Payable Rs. In Words:
										</DIV>
									</TD>

									<TD align="center" valign="baseline" class="ColumnBackground">
										<DIV align="left">
											<% double sum1=totalamt;
                                                         
                                                        CommonDAO dao=new CommonDAO();
                                                        String sim2=dao.inWordFormat(sum1);
                                                        
                                                    
                                                        
                                                        %>
											<%= sim2%>
											 Only
											<!--<%=decimalFormat.format(totalamt)%>-->
										</DIV>
									</TD>
								</TR>
                                                                
                                                              </logic:iterate>  
                                    </TABLE>                            
								<TR>
									<TD>
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="0">
									                                                                              
											<TR>
                                                                                        
                                                                                        
												<TD align="left" colspan="4" valign="top" ><h6>
                                                                                                    Term And Conditions:-
                                                                                                    <br>
                                                                                                   1)	The Courts in Mumbai shall have exclusive jurisdiction in respect of any dispute arising in connection with the above matter.
                                                                                                    <br>
                                                                                                   2)	Please revert back to us within 7 days of receipt of the invoice,in case of discrepancy in state code and/or GSTIN as mentioned herein with place of supply or your actual GSTIN by email on 
                                                                                                    <br>
                                                                                                  
                                                                                                    
                                                                                                    <br>
                                                                                                    SAC code / Service Category :
                                                                                                    
                                                                                                    <br>Code under GST :	997113 - Credit - granting services including stand - by commitment, guarantees & securities.
                                                                                            </h6>
                                                                                                </TD>
                                                                                                
                                                                                              
                                                                         	                 
												
											</TR>
											<tr>
											<TD align="right" valign="top">
									
                                  
                                             </TD>
										  <TD align="right" valign="top" ><h6><font class="fontsized">For CGTMSE</font></h6></TD> 
                                                </tr>
                                          <tr>
											<TD align="right" valign="top">
									 </TD>
                                                                       
                                      
   </tr>
                                        <tr>
                                        <TD align="right" valign="top" > </TD>                              	
                                          <TD align="right" valign="top" ><h6><font class="fontsized">Authorised Signatory</font></h6></TD>                      
										
                                                                     
										
											

										         
                                                                                                                

										
                                                                            
										</TABLE>
									</TD>
								</TR>

							
						</TD>
					</TR>
					<TR>

					</TR>
					</table>
					<TR>
						<TD align="center" valign="baseline">
							<DIV align="center">
								<!--<A href="javascript:submitForm('danReport.do?method=danReport')">
									-->
									<A href="javascript:history.back()">
									<IMG src="images/Back.gif" alt="Print" width="49" height="37"
									border="0">
								</A> <A href="javascript:printpage()"> <IMG
									src="images/Print.gif" alt="Print" width="49" height="37"
									border="0"></A>

							</DIV>
						</TD>
					</TR>
				</TABLE>
			</TD>
			<TD width="20" background="images/TableVerticalRightBG.gif">
				&nbsp;</TD>
		</TR>
		<TR>
			<TD width="20" align="right" valign="top"><IMG
				src="images/TableLeftBottom1.gif" width="20" height="15" alt=""></TD>
			<TD background="images/TableBackground2.gif">&nbsp;</TD>
			<TD width="20" align="left" valign="top"><IMG
				src="images/TableRightBottom1.gif" width="23" height="15" alt=""></TD>
		</TR>
	</html:form>
</TABLE>


