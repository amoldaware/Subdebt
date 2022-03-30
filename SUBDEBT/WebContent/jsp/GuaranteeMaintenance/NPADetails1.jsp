	<%@ page language="java"%>
	<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
	<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
	<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
	<%@ page import="com.cgtsi.guaranteemaintenance.NPADetails"%>
	<%@ page import="com.cgtsi.guaranteemaintenance.RecoveryProcedure"%>
	<%@ page import="com.cgtsi.admin.MenuOptions"%>
	<%@ include file="/jsp/SetMenuInfo.jsp" %>
	<%@ page import="java.util.Date"%>
	<%@ page import="java.util.ArrayList"%>
	<%@ page import="java.text.SimpleDateFormat"%>
	<%@page import="java.util.HashMap"%>
	<%@page import="java.lang.String"%>
	

       <% 

		// session.setAttribute("CurrentPage","showNPADetails.do?method=showNPADetails");
		String menu = (String)session.getAttribute("mainMenu");
		String subMenu = (String)session.getAttribute("subMenuItem");
		// System.out.println("**** - Main Menu :" + menu);
		// System.out.println("**** - Sub Menu :" + subMenu);
		if(menu!=null && subMenu!=null)
		{
			if((menu.equals(MenuOptions.getMenu(MenuOptions.GM_PERIODIC_INFO))) &&
			  (subMenu.equals(MenuOptions.getMenu(MenuOptions.GM_PI_NPA_DETAILS))))
			  {
				 session.setAttribute("CurrentPage","showNPADetails.do?method=showNPADetails");
			  }
			if((menu.equals(MenuOptions.getMenu(MenuOptions.CP_CLAIM_FOR))) &&
			  (subMenu.equals(MenuOptions.getMenu(MenuOptions.CP_CLAIM_FOR_FIRST_INSTALLMENT))))
			  {
				 session.setAttribute("CurrentPage","getBorrowerId.do?method=setBankId");
			  }
		}
	%>
<body onload="onOffFields();">
<table width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="saveNpaDetails1.do?method=saveNpaDetails1" method="POST" enctype="multipart/form-data">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/GuaranteeMaintenanceHeading.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<DIV align="right"><A HREF="javascript:submitForm('helpNpaDetails.do?method=helpNpaDetails')">HELP</A>
				</DIV>
				<table width="100%" border="0" align="left" cellpadding="0" cellspacing="0"> 
					<TR>
						<TD>
						<table width="100%" border="0" cellspacing="1" cellpadding="1">
					<!--header of form -->
							<TR>
								<TD>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<TR>
											<td class="Heading" WIDTH="55%">&nbsp; 
											<bean:message key="NPADetailsHeader"/>&nbsp; for 
											&nbsp;<bean:write name="newNpaForm" property="unitName"/> 
											&nbsp;(<bean:write name="newNpaForm" property="borrowerId"/>)
											</TD>

											<td  align="left" valign="bottom"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
											<td align="right"> <div align="right"> </div></td>
										</TR>
											
									</TABLE>
								</TD>
							</TR>			
                        </TABLE>
						</TD>
					</TR>
					<TR>
						<TD class="SubHeading" COLSPAN="12">&nbsp;
						</TD>
					</TR>
					<TR>
						<TD>
						<table width="100%" border="0" cellspacing="1" cellpadding="1">
					<!--sanction details -->
					<!--loop start -->
								
								
								<%
									//java.lang.Double totalGuaranteeAmt = (java.lang.Double)request.getAttribute("totalGuaranteedAmt");
									
								//	java.lang.Double totalApprovedAmount = (java.lang.Double)request.getAttribute("totalApprovedAmount");
								
								
									org.apache.struts.validator.DynaValidatorActionForm newNpaForm2 = (org.apache.struts.validator.DynaValidatorActionForm)session.getAttribute("newNpaForm") ;
									
									
									java.lang.Double totalApprovedAmount = (java.lang.Double)newNpaForm2.get("totalApprovedAmount");
									//	out.println("totalApprovedAmount:"+totalApprovedAmount);
									double totalAmount = 0;
									if(totalApprovedAmount != null){
										totalAmount = totalApprovedAmount.doubleValue();
									}
									
									//java.lang.Integer size = (java.lang.Integer)session.getAttribute("size");
									
									java.lang.Integer size = (java.lang.Integer)newNpaForm2.get("size");
									
									//out.println("size:"+size);
									int total = 0;
									if(size != null){
										total = size.intValue();
									}
								//	out.println("total:"+total);
									
								//	request.setAttribute("size",size);
								//	java.util.Vector cgpansVector = (java.util.Vector)request.getAttribute("cgpansVector");
									
									java.util.Vector cgpansVector = (java.util.Vector)newNpaForm2.get("cgpansVector");
								
									String cgpan = null;
									String guarStartDt = null;
									String sanctionDt = null;
									String firstDisbDt = null;
									String lastDisbDt = null;
									String firstInstDt = null;
									
									String moratoriumPrincipal = null;
									String moratoriumInterest = null;
									
									
									for(int i=1;i<=total;i++){
									java.util.HashMap map = (java.util.HashMap)cgpansVector.get(i-1);
									String cgpanNo = (String)map.get("CGPAN");
									String loanType = (String)map.get("CGPAN_LOAN_TYPE");
									String guarStartDate = (String)map.get("GUARANTEE_START_DT");
									String sanctionDate = (String)map.get("SANCTION_DT");	
								//	out.println("cgpan:"+cgpanNo+"--guarStartDt:"+guarStartDate+"--sanctionDt:"+sanctionDate);
									
									cgpan = "cgpan"+i;
									guarStartDt = "guarStartDt"+i;
									sanctionDt = "sanctionDt"+i;
									firstDisbDt = "firstDisbDt"+i;
									lastDisbDt = "lastDisbDt"+i;
									firstInstDt = "firstInstDt"+i;
									
									moratoriumPrincipal = "moratoriumPrincipal"+i;
									moratoriumInterest = "moratoriumInterest"+i;
								%>
                               
							    <%
									}
							    %>
					<!--loop end -->
						</TABLE>
						</TD>
					</TR>
					<tr>
						<td><input type="hidden" name="total" id="total" value="<%=total%>" /></td>
					</tr>
					<TR>
						<TD class="SubHeading">Npa Details</TD>
					</TR> 
					
					<TR>
						<TD>
						<table width="100%" border="0" cellspacing="1" cellpadding="1">
					<!--npa details -->			
								<TR>
									<TD colspan="1" class="ColumnBackground">&nbsp;<font color="#FF0000" size="2"></font>
									</TD>
									<TD colspan="3" class="tableData">
										<table cellpadding="0" cellspacing="0">
											
										</table>
									</TD>
								</TR>
								
								
																		
								
					<!--Subsidy Details -->
								
								
								
								
								
								<TR>
									<TD colspan="1" class="ColumnBackground">&nbsp;<font color="#FF0000" size="2"></font>
									</TD>
									<TD colspan="3" class="tableData">
									<table cellpadding="0" cellspacing="0">
										
									</table>
									</TD>
								</TR>
								
						</TABLE>
						</TD>
					</TR>
					<TR>
						<TD class="SubHeading" colspan="12">Repayment (before NPA date exclusive of subsidy) and Outstanding amount Details
						</TD>
					</TR>
					<TR>
						<TD>
						<table width="100%" border="0" cellspacing="1" cellpadding="1">
								
                    <!--Repayment (before NPA date exclusive of subsidy) and Outstanding details -->  
								<!--loop start -->
								<TR>
									<TD rowspan="2" class="ColumnBackground">&nbsp;&nbsp;cgpan</TD>
									<TD rowspan="2" class="ColumnBackground">&nbsp;&nbsp;Total Amount Disbursed<font color="#FF0000" size="2">*</font></TD>
									<TD colspan="2" class="ColumnBackground"><div align="center">Repayment(before NPA date)<font color="#FF0000" size="2">*</font></DIV></TD>
									<td colspan="2" class="ColumnBackground"><div align="center">Outstanding amount(as on NPA date)<font color="#FF0000" size="2">*</font></DIV></TD>
									<td colspan="2" class="ColumnBackground"><div align="center">Outstanding amount as on 31 March 2015(Does not include any interest or other charges beyond NPA date)<font color="#FF0000" size="2">*</font></DIV></TD>
								</TR>
								<TR>									
									<td class="ColumnBackground"><div align="center"><font color="#FF0000" size="2">*</font>principal(in Rs.)</div></td>
									<td class="ColumnBackground"><div align="center"><font color="#FF0000" size="2">*</font>interest(in Rs.)</div></td>	
									<td class="ColumnBackground"><div align="center"><font color="#FF0000" size="2">*</font>principal(in Rs.)</div></td>
									<td class="ColumnBackground"><div align="center"><font color="#FF0000" size="2">*</font>interest(in Rs.)</div></td>										
                                </TR>
								<% 
									String totalDisbAmt = null;
									String repayPrincipal = null;
									String repayInterest = null;
									String outstandingPrincipal = null;
									String outstandingInterest = null;
									String approvedAmount = null;
									String interestRate = null;
									//koteswar start
									String outStandingAmtAsonForCurTCFy=null; 
									String outStandingAmtAsonForCurWCFy=null;
									
									//koteswar start end
									
									for(int i=1;i<=total;i++){
										java.util.HashMap map = (java.util.HashMap)cgpansVector.get(i-1);
										String cgpanNo = (String)map.get("CGPAN");
										String loanType = (String)map.get("CGPAN_LOAN_TYPE");
										Double approvedAmt = (java.lang.Double)map.get("APPROVED_AMOUNT");
										double guarAmt = approvedAmt.doubleValue();
										Double rate = (java.lang.Double)map.get("RATE");
										double r = rate.doubleValue();
									
										totalDisbAmt = "totalDisbAmt"+i;
										repayPrincipal = "repayPrincipal"+i;
										repayInterest = "repayInterest"+i;
										outstandingPrincipal = "outstandingPrincipal"+i;
										outstandingInterest = "outstandingInterest"+i;
										approvedAmount = "approvedAmount"+i;
										
										//koteswar start
										outStandingAmtAsonForCurTCFy = "outStandingAmtAsonForCurTCFy"+i;
										
										outStandingAmtAsonForCurWCFy = "outStandingAmtAsonForCurWCFy"+i;
										//koteswar end
								%>
								<TR>
									<TD class="tableData">&nbsp;<%=cgpanNo%><html:hidden name="newNpaForm" property="<%=approvedAmount%>" value="<%=String.valueOf(guarAmt)%>"/>
									<html:hidden name="newNpaForm" property="<%=interestRate%>" value="<%=String.valueOf(r)%>"/>
									</TD>
									<%if("TC".equals(loanType) || "CC".equals(loanType)){%>
									<TD class="tableData"><div align="center">
									<html:text name="newNpaForm" property="<%=totalDisbAmt%>"    onchange="calOutstanding(this);" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" readonly="true"/></DIV></TD>
									<TD class="tableData"><div align="center">
									<html:text name="newNpaForm" property="<%=repayPrincipal%>" onchange="calOutstanding(this);" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" readonly="true"/></DIV></TD>
									<TD class="tableData"><div align="center">
									<html:text name="newNpaForm" property="<%=repayInterest%>" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" readonly="true"/></DIV></TD>
									<TD class="tableData"><div align="center"><html:text name="newNpaForm" property="<%=outstandingPrincipal%>" readonly="true"/></DIV></TD>
									<TD class="tableData"><div align="center">
									<html:text name="newNpaForm" property="<%=outstandingInterest%>" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" readonly="true"/></DIV></TD>
									
									
									
									<TD class="tableData"><div align="center">
									<html:text name="newNpaForm" property="<%=outStandingAmtAsonForCurTCFy%>" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/></DIV></TD>
									
									<%}else{%>
									<TD class="tableData"><div align="center"><html:text name="newNpaForm" property="<%=totalDisbAmt%>" disabled="true"  /></DIV></TD>
									<TD class="tableData"><div align="center"><html:text name="newNpaForm" property="<%=repayPrincipal%>" disabled="true"/></DIV></TD>
									<TD class="tableData"><div align="center"><html:text name="newNpaForm" property="<%=repayInterest%>" disabled="true"/></DIV></TD>
									<TD class="tableData"><div align="center"><html:text name="newNpaForm" property="<%=outstandingPrincipal%>" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" readonly="true"/></DIV></TD>
									<TD class="tableData"><div align="center"><html:text name="newNpaForm" property="<%=outstandingInterest%>" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" readonly="true"/></DIV></TD>
									
									<TD class="tableData"><div align="center"><html:text name="newNpaForm" property="<%=outStandingAmtAsonForCurWCFy%>" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/></DIV></TD>
									<%}%>
									
								</TR>
								<%}%>
					<!--loop end -->
								<TR>
									<TD style="color:green;font:15px;" COLSPAN="12">	
									</TD>
								</TR>
						</TABLE>
						</TD>
					</TR>
					
					<TR>
						<TD>
						<table width="100%" border="0" cellspacing="1" cellpadding="1">
					<!--Primary Security Details -->
							
								
								
					<!-- as on sanction date fields -->	

								<%
								//	java.lang.Double totalSecurityAsOnSanc = (java.lang.Double)request.getAttribute("totalSecurityAsOnSanc");
									
									java.lang.Double totalSecurityAsOnSanc = (java.lang.Double)newNpaForm2.get("totalSecurityAsOnSanc");
									
									double totalSecurityAsOnSanc2 = 0.0;
									if(totalSecurityAsOnSanc != null){
										totalSecurityAsOnSanc2 = totalSecurityAsOnSanc.doubleValue();
									}
									String totalSecurityAsOnSancDt = String.valueOf(totalSecurityAsOnSanc2);
									
									String totalLandValueStr = "";
                                    String totalMachineValueStr = "";
                                    String totalBldgValueStr = "";
                                    String totalOFMAValueStr = "";
                                    String totalCurrAssetsValueStr = "";
                                    String totalOthersValueStr = "";
                                           
                                    String landAsOnDtOfSnctnDtl = "";                              
                                    String bldgAsOnDtOfSnctn = "";
                                    String machinecAsOnDtOfSnctn = "";
                                    String otherAssetsAsOnDtOfSnctn = "";
                                    String currAssetsAsOnDtOfSnctn = "";
                                    String otherValAsOnDtOfSnctn = "";
									
									String landAsOnDtOfNPA = "";
                                    String bldgAsOnDtOfNPA = "";
                                    String machinecAsOnDtOfNPA = "";
                                    String otherAssetsAsOnDtOfNPA = "";
                                    String currAssetsAsOnDtOfNPA = "";
                                    String otherValAsOnDtOfNPA = "";
								%>
								<%  						
									   landAsOnDtOfSnctnDtl = "securityAsOnSancDt(LAND)";
									   bldgAsOnDtOfSnctn ="securityAsOnSancDt(BUILDING)";
									   machinecAsOnDtOfSnctn ="securityAsOnSancDt(MACHINE)";
									   otherAssetsAsOnDtOfSnctn = "securityAsOnSancDt(OTHER_FIXED_MOVABLE_ASSETS)";
									   currAssetsAsOnDtOfSnctn="securityAsOnSancDt(CUR_ASSETS)";
									   otherValAsOnDtOfSnctn="securityAsOnSancDt(OTHERS)";
									   
									   landAsOnDtOfNPA = "securityAsOnNpaDt(LAND)";
									   bldgAsOnDtOfNPA ="securityAsOnNpaDt(BUILDING)";
									   machinecAsOnDtOfNPA ="securityAsOnNpaDt(MACHINE)";
									   otherAssetsAsOnDtOfNPA = "securityAsOnNpaDt(OTHER_FIXED_MOVABLE_ASSETS)";	
									   currAssetsAsOnDtOfNPA="securityAsOnNpaDt(CUR_ASSETS)";
									   otherValAsOnDtOfNPA="securityAsOnNpaDt(OTHERS)";
								%>
					
								
								
					<!-- as on date of npa--onchange totalsecurityasonsancdt should be compared with totalsecurityasonnpadt -->
								
						</TABLE>
						</TD>
					</TR>
						
			<%--	<TR>
						<TD>
						<table>
							<tr>
								<TD align="center" valign="baseline" >
									<DIV align="center">
									
									<A href="javascript:submitForm('saveNpaDetails1.do?method=saveNpaDetailsNew1')">
									
										<IMG src="images/Save.gif" alt="OK" width="49" height="37" border="0"></A>
									
									<A href="javascript:document.newNpaForm.reset()">
										<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>
										<A href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
										<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>
									</DIV>
								</TD>
							</TR>
						</table>
						</TD>
						<TD width="20" background="images/TableVerticalRightBG.gif">
									&nbsp;
						</TD>
					</TR> --%>
					
				</table>  
			</TD>
		</TR>
		<tr> 
			<td width="20" align="right" valign="bottom"><img src="images/TableLeftBottom.gif" width="20" height="51"></td>
			<td colspan="2" valign="bottom" background="images/TableBackground3.gif"> 
			  <div>
				<div align="center">
					<A href="javascript:submitForm('saveNpaDetails1.do?method=saveNpaDetails1')">
					<IMG src="images/Save.gif" alt="OK" width="49" height="37" border="0"></A>
					<A href="javascript:document.newNpaForm.reset()">
					<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>
					<A href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
					<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>
				</div>
			</td>
			<td width="20" align="right" valign="bottom"><img src="images/TableRightBottom.gif" width="23" height="51"></td>
		</tr>
	<!--	<TR>
			<TD width="20" align="right" valign="top"><IMG src="images/TableLeftBottom1.gif" width="20" height="15"></TD>
			<TD background="images/TableBackground2.gif">&nbsp;</TD>
			<TD width="20" align="left" valign="top"><IMG src="images/TableRightBottom1.gif" width="23" height="15"></TD>
		</TR> -->
	</html:form>
</table>
</body>

