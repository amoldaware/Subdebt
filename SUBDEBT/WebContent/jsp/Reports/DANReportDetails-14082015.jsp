<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="org.apache.struts.action.DynaActionForm"%>
<%@page import="com.cgtsi.common.CommonDAO"%>

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
  String bName = request.getParameter("bName");

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
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="21">
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                                                                                
                                                                                <% if("GF".equalsIgnoreCase(danType)||"sf".equalsIgnoreCase(danType)||"aF".equalsIgnoreCase(danType)||"cg".equalsIgnoreCase(danType)||"RS".equalsIgnoreCase(danType))
                            {
                            %>
                            <tr id="invoice" >
                               <td colspan="6" class="Heading1" align="center"><u><bean:message key="invoice"/></u></td>
                              
                            </tr>
                            <% }%>
                           
                            <% if("CN".equalsIgnoreCase(danType))
                            {
                            %>
                            <tr id="creditnote" >
                                <td colspan="6" class="Heading1" align="center"><u><bean:message key="creditnote"/></u></td>
                            </tr>
                            <% }%>
                            
                                                                                       
											<tr>
												<td colspan="6" class="Heading1" align="center"><u><bean:message
															key="reportHeader1" /></u></td>
                                                                                                                        
                                                                                                                        
											</tr>
                                                                                        
                                                                                        <tr>
                                                                                        
                                                                                                 <TD colspan="6"  align="center">
                                                                                       
                                                                                                    <h6>
                                                                                                    (Set up by Govt. of India and SIDBI)
                                                                                                1002 & 1003, Naman Centre, 10th floor, 
                                                                                             Plot No. C-31, G- Block, Bandra-Kurla Complex, 
                                                                                                    Bandra (East) , Mumbai - 400 051 
                                                                                                    Tel:-26541803/04/06/07 Fax-26541821 
                                                                                              <html:link href="www.cgtmse.in">www.cgtmse.in</html:link>
                                                                                                    </h6>
                                                                                             </TD>
                                                                                        
                                                                                        </tr>
                                                                                        
                                                                                        
                                                                                        
											<tr>
												<td colspan="6">&nbsp;</td>
											</tr>
											<TR>
												<TD align="left" valign="top" class="DanReport">Dan Id:<%=danId%>
												</TD>
												<TD align="left" valign="top" class="DanReport">
													Generated on:<%=date%>
												</TD>
											</TR>

											<TR>
												<TD align="left" valign="top" class="SubMenu">Bank:<%=bank%>
												</TD>
												<TD align="left" valign="top" class="SubMenu">Zone:<%=zone%>
												</TD>
												<TD align="left" valign="top" class="SubMenu">Branch: <% if(branch.equals("")||branch==null){
                        
                        %><%=bName%> <% } %> <%=branch%>
												</TD>
											</TR>

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
                                                                               
                                                                               
                                                                                com.cgtsi.reports.DanReport dReport =  (com.cgtsi.reports.DanReport)object;
                                                                                gFeePaid=dReport.getGuaranteeFeePaid();
                                                                                totalgFeePaid = totalgFeePaid + gFeePaid;
                                                                                
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
                                                                                
									%>
									
                                                    	
								
                                                                
								<% if(danType.equalsIgnoreCase("GF")||danType.equalsIgnoreCase("CG")||danType.equalsIgnoreCase("RS")) 
                                                                {  %>
                                                                <tr>
                                                                <TD width="3%" align="left" valign="top"  class="ColumnBackground"> <bean:message key="sNo" /></TD>
                                                                <TD width="10%" align="left" valign="top" class="ColumnBackground">  Member Id</TD>
                                                                <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> <bean:message key="cgpanNumber" /></TD>
                                                                 <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> <bean:message key="ssiName" /></TD>               
                                                                <TD width="10%" align="left" valign="top"
										class="ColumnBackground"><bean:message
											key="totalSanctionAmount" /></TD>
                                                                 <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> <bean:message
											key="amountApprovedRs" /></TD>
                                                                <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> <bean:message
											key="reapprovedAmount" /></TD>                
                                                                <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> ReApproved Date</TD>
                                                                <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> <bean:message
											key="guaranteeFeeRs" /></TD>
                                                                 <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> Status</TD> 
                                                                 <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> <bean:message
											key="applicationNumber" /></TD>
                                                                <TD width="10%" align="left" valign="top"
										class="ColumnBackground"><bean:message
											key="applicationDate" /></TD>                
                                                                 <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> Guarantee Start Date</TD>
                                                                  <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> <bean:message key="newDanID" /></TD>
                                                                 <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> Close Date</TD>
                                                                 <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> Appropriation By </TD>
                                                                  <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> <bean:message key="baseamount" /></TD>
                                                                 <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> <bean:message
											key="includedsertax" /></TD>
                                                                 <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> <bean:message key="includecess" /></TD>
                                                                <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> <bean:message
											key="incsandhecess" /></TD>
                                                                 <TD width="10%" align="left" valign="top"
										class="ColumnBackground"> Dan generated Amount Rs.</TD>
                                                                                
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
										 <%=newDanId1%>
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
										<div align="right"><%=includedCessAmount%></div>
									     </TD>
									     <TD width="10%" align="left" valign="top" class="ColumnBackground1">
										<div align="right"><%=includedHigherEduAmount%></div>
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
					</TR>
					<TR>
						<TD></TD>
					</TR>

					<TR>
						<TD height="20">&nbsp;</TD>
					</TR>
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>


									<TD align="center" valign="baseline" class="ColumnBackground">
										<DIV align="left">
											<%
									if(danType.equals("CG") || "GF".equals(danType))
									{
									%>
											<bean:message key="ttlGuaranteeFee" />
											<%
									}
									else if(danType.equals("CL"))
									{
									%>
											<bean:message key="ttlAmountRecovered" />
											<%
									}
									else if(danType.equals("SH"))
									{
									%>
											<bean:message key="ttlShortFee" />
											<%
									}
									else if(danType.equals("SF") || "AF".equals(danType))
									{
									%>
											<bean:message key="ttlServiceFee" />
											<%
									}
                                                                        else if(danType.equals("CN"))
                                                                        {
                                                                       %>
                                                                        <bean:message key="ttlCreditFee" />
                                                                       <%
                                                                        }
                                                                        else if(danType.equals("RS"))
                                                                        {
                                                                        %>
                                                                        <bean:message key="ttlRefundAmt" />
                                                                        <%
                                                                        }
                                                                        
											 								%>
                                                                        
                                                                       
                                                                        
									                                    
										</DIV>
									</TD>

									<TD align="center" valign="baseline" class="ColumnBackground">
										<DIV align="left">


											<%=decimalFormat.format(totalgFee)%>
										</DIV>
									</TD>
								</TR>

								<TR>
									<TD align="center" valign="baseline" class="ColumnBackground">
										<DIV align="left">
											<bean:message key="amtPaid" />
										</DIV>
									</TD>

									<TD align="center" valign="baseline" class="ColumnBackground">
										<DIV align="left">
											<%=decimalFormat.format(totalgFeePaid)%>
										</DIV>
									</TD>
								</TR>

								<TR>
									<TD align="center" valign="baseline" class="ColumnBackground">
										<DIV align="left">
											<bean:message key="amtPayable" />

											

											<% double amountPayable = totalgFee - totalgFeePaid;
                                                               
                                                               
                                                               %>


										</DIV>
									</TD>

									<TD align="center" valign="baseline" class="ColumnBackground">
										<DIV align="left">
											<%=decimalFormat.format(amountPayable)%>
										</DIV>
									</TD>
								</TR>
								<TR>
									<TD align="center" valign="baseline" class="ColumnBackground">
										<DIV align="left">
											<bean:message key="amtInWords" />
										</DIV>
									</TD>

									<TD align="center" valign="baseline" class="ColumnBackground">
										<DIV align="left">
											<% double sum1=totalgFee;
                                                         
                                                        CommonDAO dao=new CommonDAO();
                                                        String sim2=dao.inWordFormat(sum1);
                                                        
                                                    
                                                        
                                                        %>
											<%= sim2%>
											 Only
											<!--<%=decimalFormat.format(totalgFee)%>-->
										</DIV>
									</TD>
								</TR>
                                                                
                                                              </logic:iterate>  
                                                                
								<TR>
									<TD>
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="0">
                                                                                
											<TR>
                                                                                        
                                                                                        
												<TD align="left" colspan="4" valign="top" class="SubHeading">
                                                                                                    Term And Conditions:-
                                                                                                    <br>
                                                                                                    1. Any dispute arising are subject to Mumbai jurisdiction.
                                                                                                    <br>
                                                                                                    2. Guarantee is effective/remain live only after receipt of guarantee fees/Annual Service fees from the
                                                                                                    member lending institution.
                                                                                                    <br>
                                                                                                    Service Category :
                                                                                                    <br>Other Taxable Services - Other Than The 119 LISTED (Guarantee Services) 
                                                                                                </TD>
											
                                                                                        
												
											</TR>
											<TR>

												<td align="left" valign="top" class="ColumnBackground"><div
														align="center">&nbsp;Service Tax Number:</div></td>

												<TD align="left" valign="top" class="ColumnBackground"><div
														align="left">&nbsp;AAATC2613DSD001</div></td>

												<td align="left" valign="top" class="ColumnBackground"><div
														align="center">&nbsp;PAN Number:</div></td>

												<td align="left" valign="top" class="ColumnBackground"><div
														align="left">&nbsp;AAATC2613D</div></TD>
                                                                                                                
                                                                                                                

											</TR>
                                                                                        <TR>
                                                                                                                     <td align="left" colspan="4" valign="top" class="ColumnBackground"><div
														align="left">&nbsp;***This is computer genereted advice and does not need signature.*******</div></TD>
                                                                                        </TR>
										</TABLE>
									</TD>
								</TR>

							</TABLE>
						</TD>
					</TR>
					<TR>

					</TR>
					<TR>
						<TD align="center" valign="baseline">
							<DIV align="center">
								<A href="javascript:submitForm('danReport.do?method=danReport')">

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

