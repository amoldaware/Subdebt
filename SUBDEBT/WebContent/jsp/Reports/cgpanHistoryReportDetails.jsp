<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>

<%@page import ="java.text.SimpleDateFormat"%>
<%@page import ="java.text.DecimalFormat"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>
<%DecimalFormat decimalFormat = new DecimalFormat("##########.##");%>
<% session.setAttribute("CurrentPage","cgpanHistoryReportDetails.do?method=cgpanHistoryReportDetails");%>
<%
String cgpan = "";
%>
<TABLE width="730" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="cgpanHistoryReportDetails.do?method=cgpanHistoryReportDetails" method="POST" enctype="multipart/form-data">
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
									<TD colspan="10"> 
                  <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                      <tr>
                          <td colspan="6" class="Heading1" align="center"><u>Credit Guarantee Fund Trust for Micro and Small Enterprises </u></td>
                      </tr>
                      </table><TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD colspan="5" width="50%" class="Heading"><bean:message key="applicationHeader"/></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
										<TR>
										<TD  width="5%" colspan="12" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
										</TR>                       
                    <tr><td colspan="5">&nbsp;</td></tr>   
                   <TR><TD colspan="4"><font color="red" size="1">MLI DETAILS&nbsp;</font></TD></TR> 
                   <TR align="left" valign="top">
							   	<TD colspan="3" align="left" class="ColumnBackground"> 
									<bean:message key="bankName"/>
									</TD>
                  <TD colspan="2" align="left" class="ColumnBackground"> 
									<bean:message key="zoneName"/>
									</TD>
                  <TD colspan="2" align="left" class="ColumnBackground"> 
									<bean:message key="CBbranchName"/>
									</TD>
                  <TD  colspan="1" align="left" class="ColumnBackground"> 
									<bean:message key="MemberID"/>
									</TD>
                 <%-- <TD colspan="1" align="left" class="ColumnBackground"> 
									<bean:message key="appRefNo"/>
									</TD>--%>
                  <TD colspan="2"  align="left" class="ColumnBackground"> 
									<bean:message key="scheme"/>
									</TD>
                  </TR>                 
                    <TR align="left" valign="top">
							     <TD  colspan="3" align="left" valign="top" class="ColumnBackground1">
                   <bean:write property="applicationReport.bankName" name="rsForm"/>&nbsp;
                   </TD>
                   <TD colspan="2" align="left" valign="top" class="ColumnBackground1">
                   <bean:write property="applicationReport.zoneName" name="rsForm"/>&nbsp;
                   </TD>
                   <TD  colspan="2" align="left" valign="top" class="ColumnBackground1">
                   <bean:write property="applicationReport.branchName" name="rsForm"/>&nbsp;
                   </TD>                    
                   <TD  colspan="1" align="left" valign="top" class="ColumnBackground1">
                   <bean:write property="applicationReport.memberId" name="rsForm"/>&nbsp;
                   </TD>                   
                   <TD  colspan="2" align="left" valign="top" class="ColumnBackground1">
                   <bean:write property="applicationReport.schemeName" name="rsForm"/>&nbsp;
                   </TD>
                  </TR>                  
                  <TR><TD colspan="4"><font color="red" size="1">APPLICATION DETAILS&nbsp;</font></TD></TR> 
                   <TR align="left" valign="top">
							   	<TD colspan="1" align="left" class="ColumnBackground"> 
									<bean:message key="appRefNo"/>
									</TD>
                  <TD colspan="1" align="left" class="ColumnBackground"> 
									<bean:message key="cgpan"/>
									</TD>
                  <TD colspan="1" align="left" class="ColumnBackground"> 
									 Bank Ref&nbsp;
									</TD>
                  <TD  colspan="1" align="left" class="ColumnBackground"> 
									Type&nbsp;
									</TD>
                  <TD  colspan="2" align="left" class="ColumnBackground"> 
									<bean:message key="applicationDate"/>
									</TD>
                 < <TD colspan="2" align="left" class="ColumnBackground"> 
									<bean:message key="amountApprovedRs"/>
									</TD>
                  <TD colspan="2"  align="left" class="ColumnBackground"> 
									<bean:message key="mcgfApprovedDate"/>
									</TD>
                  </TR>
                  <TR align="left" valign="top">
							     <TD  colspan="1" align="left" valign="top" class="ColumnBackground1">
                   <bean:write property="applicationReport.appRefNo" name="rsForm"/>&nbsp;
                   </TD>
                   <TD colspan="1" align="left" valign="top" class="ColumnBackground1">
                   <bean:write property="applicationReport.cgpan" name="rsForm"/>&nbsp;
                   </TD>
                   <TD  colspan="1" align="left" valign="top" class="ColumnBackground1">
                   <bean:write property="applicationReport.appBankAppRefNo" name="rsForm"/>&nbsp;
                   </TD>                    
                   <TD  colspan="1" align="left" valign="top" class="ColumnBackground1">
                   <bean:write property="applicationReport.loanType" name="rsForm"/>&nbsp;
                   </TD>
                   <TD  colspan="2" align="left" valign="top" class="ColumnBackground1">
                   <bean:define id="applicationDate" name="rsForm" property="applicationReport" type="com.cgtsi.reports.ApplicationReport"/>
                   <%String date = null;
                    java.util.Date submittedDt = applicationDate.getApplicationDate();
                    if(submittedDt != null)
                    {
                         date = dateFormat.format(submittedDt);
                    }
                    else
                    {
                       date = ""; 
                    }
                    %>
                  <%=date%>
                   </TD>
                   
                   <TD  colspan="2" align="left" valign="top" class="ColumnBackground1">
                   <bean:write property="applicationReport.appApprovedAmount" name="rsForm"/>&nbsp;
                   </TD>
                   <TD  colspan="2" align="left" valign="top" class="ColumnBackground1">
                   <bean:define id="appApprovedDateTime" name="rsForm" property="applicationReport" type="com.cgtsi.reports.ApplicationReport"/>
                   <%String date1 = null;
                    java.util.Date newApplicationDate = appApprovedDateTime.getAppApprovedDateTime();
                    if(newApplicationDate != null)
                    {
                         date1 = dateFormat.format(newApplicationDate);
                    }
                    else
                    {
                       date1 = ""; 
                    }
                    %>
                  <%=date1%>
                   </TD>
                  </TR>                   
                  <TR><TD colspan="4"><font color="red" size="1">BORROWER DETAILS&nbsp;</font></TD></TR> 
                   <TR align="left" valign="top">
							   	<TD colspan="1" align="left" class="ColumnBackground"> 
									<bean:message key="borrowerUnitName"/>
									</TD>
                  <TD colspan="3" align="left" class="ColumnBackground"> 
									<bean:message key="address"/>
									</TD>
                  <TD colspan="1" align="left" class="ColumnBackground"> 
									 <bean:message key="stateName"/>
									</TD>
                  <TD  colspan="1" align="left" class="ColumnBackground"> 
									<bean:message key="activitytype"/>
									</TD>
                  <TD  colspan="2" align="left" class="ColumnBackground"> 
									<bean:message key="industryNature"/>
									</TD>
                 < <TD colspan="1" align="center" class="ColumnBackground"> 
									<bean:message key="noOfEmployees"/>
									</TD>
                  <TD colspan="1"  align="left" class="ColumnBackground"> 
									Turnover&nbsp;
									</TD>
                  </TR>
                   <TR align="left" valign="top">
							     <TD  colspan="1" align="left" valign="top" class="ColumnBackground1">
                   <bean:write property="applicationReport.ssiName" name="rsForm"/>&nbsp;
                   </TD>
                   <TD colspan="3" align="left" valign="top" class="ColumnBackground1">
                   <bean:write property="applicationReport.address" name="rsForm"/>&nbsp;
                   </TD>
                   <TD colspan="1" align="left" valign="top" class="ColumnBackground1">
                   <bean:write property="applicationReport.state" name="rsForm"/>&nbsp;
                   </TD>
                   <TD colspan="1" align="left" valign="top" class="ColumnBackground1">
                   <bean:write property="applicationReport.typeOfActivity" name="rsForm"/>&nbsp;
                   </TD>
                   <TD colspan="2" align="left" valign="top" class="ColumnBackground1">
                   <bean:write property="applicationReport.industryType" name="rsForm"/>&nbsp;
                   </TD> 
                   <TD colspan="1" align="center" valign="top" class="ColumnBackground1">
                   <bean:write property="applicationReport.employees" name="rsForm"/>&nbsp;
                   </TD>
                   <TD colspan="1" align="left" valign="top" class="ColumnBackground1">
                   <bean:write property="applicationReport.turnover" name="rsForm"/>&nbsp;
                   </TD>
                   </TR>
                   <TR><TD colspan="4"><font color="red" size="1"><bean:message key="chiefInfo"/></font></TD></TR> 
                   <TR align="left" valign="top">
							   	<TD colspan="2" align="left" class="ColumnBackground"> 
									<bean:message key="name"/>
									</TD>
                  <TD colspan="2" align="left" class="ColumnBackground"> 
									<bean:message key="chiefItpan"/>
									</TD>
                  <TD colspan="1" align="left" class="ColumnBackground"> 
									<bean:message key="gender"/>
									</TD>
                  <TD  colspan="2" align="left" class="ColumnBackground"> 
									<bean:message key="socialCategory"/>
									</TD>
                  <TD colspan="2" align="left" class="ColumnBackground"> 
									<bean:message key="unitType"/>
									</TD>
                  <TD colspan="1"  align="left" class="ColumnBackground"> 
									<bean:message key="dob"/>
									</TD>
                  </TR>
                   <TR align="left" valign="top">
							     <TD  colspan="2" align="left" valign="top" class="ColumnBackground1">
                   <bean:write property="applicationReport.chiefPromoter" name="rsForm"/>&nbsp;
                   </TD>
                   <TD colspan="2" align="left" valign="top" class="ColumnBackground1">
                   <bean:write property="applicationReport.itpan" name="rsForm"/>&nbsp;
                   </TD>
                   <TD  colspan="1" align="left" valign="top" class="ColumnBackground1">
                   <bean:write property="applicationReport.gender" name="rsForm"/>&nbsp;
                   </TD>                    
                   <TD  colspan="2" align="left" valign="top" class="ColumnBackground1">
                   <bean:write property="applicationReport.socialCategory" name="rsForm"/>&nbsp;
                   </TD>                   
                   <TD  colspan="2" align="left" valign="top" class="ColumnBackground1">
                   <bean:write property="applicationReport.unitType" name="rsForm"/>&nbsp;
                   </TD>
                   <TD  colspan="1" align="left" valign="top" class="ColumnBackground1">
                   <bean:define id="promoterDob" name="rsForm" property="applicationReport" type="com.cgtsi.reports.ApplicationReport"/>
                   <%
                   String date3 = null;
                    java.util.Date promoterDt = promoterDob.getPromoterDob();
                    if(promoterDt != null)
                    {
                         date3 = dateFormat.format(promoterDt);
                    }
                    else
                    {
                       date3 = ""; 
                    }
                    %>
                  <%=date3%>
                   </TD>
                  </TR>    
                  <TR><TD colspan="4"><font color="red" size="1">SANCTIONED DETAILS&nbsp;</font></TD></TR>     
                  <TR align="left" valign="top">
							   	<TD  colspan="2" align="left" class="ColumnBackground"> 
									<bean:message key="TCSanctionedAmount"/>
									</TD>                  
                  <TD colspan="2" align="left" class="ColumnBackground"> 
									<bean:message key="WCFBLimitSanctioned"/>
									</TD>
                  <TD colspan="2" align="left" class="ColumnBackground"> 
									<bean:message key="WCNFBLimitSanctioned"/>
									</TD>
                  <TD  colspan="2" align="left" class="ColumnBackground"> 
									<bean:message key="amountSanctionedDate"/>
									</TD>
                    <TD colspan="2" align="left" class="ColumnBackground"> 
								  <bean:message key="tenure"/>
            			</TD>                  
              		</TR>
                  
                  <TR align="left" valign="top">
							     <TD  colspan="2" align="left" valign="top" class="ColumnBackground1">
                   <bean:write property="applicationReport.tcSanctioned" name="rsForm"/>&nbsp;
                   </TD>
                   <TD colspan="2" align="left" valign="top" class="ColumnBackground1">
                   <bean:write property="applicationReport.wcFbSanctioned" name="rsForm"/>&nbsp;
                   </TD>
                   <TD  colspan="2" align="left" valign="top" class="ColumnBackground1">
                   <bean:write property="applicationReport.wcNfbSanctioned" name="rsForm"/>&nbsp;
                   </TD> 
                   <TD  colspan="2" align="left" valign="top" class="ColumnBackground1">
                   <bean:define id="tcSanctionedOn" name="rsForm" property="applicationReport" type="com.cgtsi.reports.ApplicationReport"/>
                   <%
                   String sanctionDt = null;
                    java.util.Date sanctionedDt = tcSanctionedOn.getTcSanctionedOn();
                    if(sanctionedDt != null)
                    {
                         sanctionDt = dateFormat.format(sanctionedDt);
                    }
                    else
                    {
                       sanctionDt = ""; 
                    }
                    %>
                  <%=sanctionDt%>
                   </TD>
                   <TD  colspan="2" align="left" valign="top" class="ColumnBackground1">
                   <bean:write property="applicationReport.tcTenure" name="rsForm"/>&nbsp;
                   </TD>
                  </TR>    
                  
                  
                  <TR><TD colspan="4"><font color="red" size="1">DAN DETAILS&nbsp;</font></TD></TR>     
                  <TR align="left" valign="top">
							   	<TD  align="left" class="ColumnBackground"> 
									<bean:message key="dan"/>
									</TD>
                  <TD  align="left" class="ColumnBackground"> 
									<bean:message key="approvedAmount"/>
									</TD>
                    <TD  align="left" class="ColumnBackground"> 
								  &nbsp;DAN Amt
            			</TD>
                  <TD width = "25%" align="left" class="ColumnBackground"> 
								&nbsp;<bean:message key="payId"/>
									</TD>
                   <TD width = "10%" align="left" class="ColumnBackground"> 
								&nbsp;Realised Date
									</TD>
                  <TD width = "10%" align="left" class="ColumnBackground"> 
								&nbsp;Appr Date
									</TD>
                  <TD width = "10%" align="left" class="ColumnBackground"> 
								&nbsp;Expiry Date
									</TD>
                   <TD align="left" class="ColumnBackground"> 
									&nbsp;<bean:message key="appropriation"/>
									</TD>
                   <TD align="left" class="ColumnBackground"> 
									&nbsp;DD No.
									</TD>
                    <TD align="left" class="ColumnBackground"> 
									&nbsp;<bean:message key="Closure"/>
									</TD>
              		</TR>
                  
                  
                  
                  <tr>
                  	<logic:iterate id="object" name="rsForm" property="cgpanHistoryReport" indexId="id">

									<% com.cgtsi.reports.ApplicationReport dReport =  (com.cgtsi.reports.ApplicationReport)object;%>

									<TR align="left" valign="top">
							     <TD  align="left" valign="top" class="ColumnBackground1">
                   <%=dReport.getDan() %>&nbsp;
                   </TD>
                   <!-- <TD  align="left" valign="top" class="ColumnBackground1">
                   <%= dReport.getCgpan()%>
                   </TD>  -->
                   <% cgpan = dReport.getCgpan(); %>
                    <TD  align="left" valign="top" class="ColumnBackground1">
                   &nbsp;<%=dReport.getAppApprovedAmount()%>
                   </TD>
                    <TD  align="left" valign="top" class="ColumnBackground1">
                   &nbsp;<%=dReport.getAppGuaranteeFee()%>
                   </TD>
                    <TD width="25%" align="left" valign="top" class="ColumnBackground1">
                    <% String payId = dReport.getPayId();
                    if(payId==null){
                    payId="Not Appropriated";
                    }
                    %>
                   &nbsp;<%=payId%>
                   </TD>
                     <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                   <%   java.util.Date utilDate3=dReport.getAppGuarStartDateTime();
													String formatedDate3 = null;
													if(utilDate3 != null)
													{
														 formatedDate3=dateFormat.format(utilDate3);
													}
													else
													{
														 formatedDate3 = "";
													}
											%>
										&nbsp;&nbsp;<%=formatedDate3%>
                   </TD>
                  
                     <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                 <%   java.util.Date utilDate5=dReport.getStartDate();
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
										&nbsp;&nbsp;<%=formatedDate5%>
                   </TD>
                    <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                 <%   java.util.Date utilDate6=dReport.getExpiryDate();
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
										&nbsp;&nbsp;<%=formatedDate6%>
                   </TD>
                   <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                 &nbsp;&nbsp;<% String userId = dReport.getUsrId();
                 	if(userId == null || userId.equals("null"))	{
		               userId=""; } %><%=dReport.getUsrId()%>
                   </TD>
                       <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                 &nbsp;&nbsp;<% String ddNum = dReport.getDdNum();
                 	if(ddNum == null || ddNum.equals("null"))	{
		               ddNum=""; } %><%=dReport.getDdNum()%>
                   </TD>
                       <TD width="10%" align="left" valign="top" class="ColumnBackground1">
                 <%   java.util.Date utilDate7=dReport.getClosureDate();
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
										&nbsp;&nbsp;<%=formatedDate7%>
                   </TD>
                   
                  </tr>
                 </logic:iterate>
									</TABLE>
								</TD>

				</TABLE>
						</TD>
					</TR>    
          <TR><TD colspan="4"><font color="red" size="1">NPA DETAILS&nbsp;</font></TD></TR> 
          <TR align="left" valign="top"><TD>
          <TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">             
          <TR>
          <TD  align="left" class="ColumnBackground"> 
									NPA Effective Dt&nbsp;
					</TD>
          <TD align="left" class="ColumnBackground"> 
									Reporting Dt&nbsp;
					</TD>
          <TD  align="left" class="ColumnBackground"> 
									<bean:message key="outstandingasondateofnpa"/>
					</TD>
           <TD align="left" class="ColumnBackground"> 
									<bean:message key="reasonfornpa"/>
					 </TD>  
          </TR>
          <TR align="left" valign="top">
           <TD  align="left" valign="top" class="ColumnBackground1">           
           <bean:define id="npaEffectiveDt" name="rsForm" property="applicationReport" type="com.cgtsi.reports.ApplicationReport"/>
                   <%
                   String date4 = null;
                    java.util.Date npaDt = npaEffectiveDt.getNpaEffectiveDt();
                    if(npaDt != null)
                    {
                         date4 = dateFormat.format(npaDt);
                    }
                    else
                    {
                       date4 = ""; 
                    }
                    %>
                  <%=date4%>
           </TD>
           <TD  align="left" valign="top" class="ColumnBackground1">           
           <bean:define id="reportingDt" name="rsForm" property="applicationReport" type="com.cgtsi.reports.ApplicationReport"/>
                   <%
                   String date5 = null;
                    java.util.Date reportDt = reportingDt.getReportingDt();
                    if(reportDt != null)
                    {
                         date5 = dateFormat.format(reportDt);
                    }
                    else
                    {
                       date5 = ""; 
                    }
                    %>
                  <%=date5%>
           </TD>
           <TD   align="left" valign="top" class="ColumnBackground1">
           <bean:write property="applicationReport.outstandingAsonNPA" name="rsForm"/>&nbsp;
           </TD>                    
           <TD   align="left" valign="top" class="ColumnBackground1">
           <bean:write property="applicationReport.npaReasons" name="rsForm"/>&nbsp;
           </TD> 
          </TR>   

          </TABLE>
          </TD></TR>
          <TR><TD colspan="4"><font color="red" size="1">LEGAL DETAILS&nbsp;</font></TD></TR> 
           <TR align="left" valign="top"><TD>
          <TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">             
          <TR>
          <TD  align="left" class="ColumnBackground"> 
									<bean:message key="suit/caseregnumber"/>
					</TD>
          <TD  align="left" class="ColumnBackground"> 
									Legal Forum&nbsp;
					</TD>
          <TD align="left" class="ColumnBackground"> 
									<bean:message key="nameoftheforum"/>
					</TD>
          <TD align="left" class="ColumnBackground"> 
									<bean:message key="location"/>
					</TD>
           <TD align="left" class="ColumnBackground"> 
									<bean:message key="filingdate"/>
					 </TD>  
           <TD align="left" class="ColumnBackground"> 
									Legal Claim Amount&nbsp;
					 </TD>
          </TR>
          <TR align="left" valign="top">
           <TD  align="left" valign="top" class="ColumnBackground1">           
            <bean:write property="applicationReport.suitNumber" name="rsForm"/>&nbsp;
           </TD>
           <TD  align="left" valign="top" class="ColumnBackground1">   
           <bean:write property="applicationReport.legalForum" name="rsForm"/>&nbsp;           
           </TD>
           <TD  align="left" valign="top" class="ColumnBackground1">   
           <bean:write property="applicationReport.forumName" name="rsForm"/>&nbsp;           
           </TD>
           <TD   align="left" valign="top" class="ColumnBackground1">
           <bean:write property="applicationReport.location" name="rsForm"/>&nbsp;
           </TD>
           <TD   align="left" valign="top" class="ColumnBackground1">
           <bean:define id="legalFilingDt" name="rsForm" property="applicationReport" type="com.cgtsi.reports.ApplicationReport"/>
                   <%
                   String date6 = null;
                    java.util.Date filingDt = legalFilingDt.getLegalFilingDt();
                    if(reportDt != null)
                    {
                         date6 = dateFormat.format(filingDt);
                    }
                    else
                    {
                       date6 = ""; 
                    }
                    %>
                  <%=date6%>
          </TD>
           <TD   align="left" valign="top" class="ColumnBackground1">
           <bean:write property="applicationReport.legalClaimAmt" name="rsForm"/>&nbsp;
           </TD> 
          </TR>   

          </TABLE>
          </TD></TR>
          <TR><TD colspan="4"><font color="red" size="1">CLAIM DETAILS&nbsp;</font></TD></TR>
          <TR align="left" valign="top"><TD>
          <TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">             
          <TR>
          <TD  align="left" class="ColumnBackground"> 
									<bean:message key="cpclaimrefnumber"/>
					</TD>
          <TD  align="left" class="ColumnBackground"> 
									<bean:message key="cgclannumber"/>
					</TD>
          <TD align="left" class="ColumnBackground"> 
									<bean:message key="filingdate"/>
					</TD>
          <TD align="left" class="ColumnBackground"> 
									Claim &nbsp;<bean:message key="cpdate"/>
					</TD>
           <TD align="left" class="ColumnBackground"> 
									<bean:message key="cpapprvdclmamnt"/>
					 </TD>  
           <TD align="left" class="ColumnBackground"> 
									Approved Dt / Submitted Dt
					 </TD>
           <TD align="left" class="ColumnBackground"> 
								<bean:message key="status"/>
					 </TD>
          </TR>
          <TR align="left" valign="top">
           <TD  align="left" valign="top" class="ColumnBackground1">
           <bean:write property="applicationReport.claimRefNo" name="rsForm"/>&nbsp;      
           </TD>
           <TD  align="left" valign="top" class="ColumnBackground1">
           <bean:write property="applicationReport.cgClan" name="rsForm"/>&nbsp; 
           </TD>
           <TD  align="left" valign="top" class="ColumnBackground1">              
           <bean:define id="claimFilingDt" name="rsForm" property="applicationReport" type="com.cgtsi.reports.ApplicationReport"/>
                   <%
                   String date7 = null;
                    java.util.Date clmFilingDt = claimFilingDt.getClaimFilingDt();
                    if(clmFilingDt != null)
                    {
                         date7 = dateFormat.format(clmFilingDt);
                    }
                    else
                    {
                       date7 = ""; 
                    }
                    %>
                  <%=date7%>

           </TD>
           
           <TD   align="left" valign="top" class="ColumnBackground1">           
           <bean:define id="claimDate" name="rsForm" property="applicationReport" type="com.cgtsi.reports.ApplicationReport"/>
                   <%
                   String date8 = null;
                    java.util.Date claimDt = claimDate.getClaimDate();
                    if(claimDt != null)
                    {
                         date8 = dateFormat.format(claimDt);
                    }
                    else
                    {
                       date8 = ""; 
                    }
                    %>
                  <%=date8%>

          </TD>
           <TD   align="left" valign="top" class="ColumnBackground1">
            <bean:write property="applicationReport.claimApprovedAmt" name="rsForm"/>&nbsp;
            </TD>
           <TD   align="left" valign="top" class="ColumnBackground1">
           
           <bean:define id="claimApprovedDt" name="rsForm" property="applicationReport" type="com.cgtsi.reports.ApplicationReport"/>
                   <%
                   String date9 = null;
                    java.util.Date createdModifiedDt = claimApprovedDt.getClaimApprovedDt();
                    if(createdModifiedDt != null)
                    {
                         date9 = dateFormat.format(createdModifiedDt);
                    }
                    else
                    {
                       date9 = ""; 
                    }
                    %>
                  <%=date9%>

          </TD> 
           <TD   align="left" valign="top" class="ColumnBackground1">
           <bean:write property="applicationReport.claimStatus" name="rsForm"/>&nbsp;
          </TD> 
          </TR>   

          </TABLE>
          </TD></TR>
          
          
          
					<TR >          
						<TD height="20" >
							&nbsp;
						</TD>
					</TR>
					<TR >
						<TD align="center" valign="baseline" >
							<DIV align="center">
									<A href="javascript:submitForm('cgpanHistoryReport.do?method=cgpanHistoryReport')">
									<IMG src="images/OK.gif" alt="OK" width="49" height="37" border="0"></A>

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

