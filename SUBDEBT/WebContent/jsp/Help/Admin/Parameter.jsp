<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<% session.setAttribute("CurrentPage","HelpParameter.do");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>


<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
<html:errors />

<form>
<table width="680" border="0" cellspacing="0" cellpadding="0" align="center">
<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<td width="713"><table width="606" border="0" cellspacing="1" cellpadding="0">
          <tr> 
            <td colspan="2" width="700"><table width="604" border="0" cellspacing="0" cellpadding="0">
												<TD width="31%" class="Heading"><bean:message key="parameterMasterHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD> 

								</TR>   
          </tr>
		  <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;Parameter Details
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		   Enter the parameter details to be saved.
							
		  </TD>
          </tr>      	
          	<tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<bean:message key="activeUserLimit"/>
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		   <bean:message key="activeUserLimit"/>
							
		  </TD>
               </tr>
          	<tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<bean:message key="passwordExpiry"/>
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		   <bean:message key="passwordExpiry"/>
							
		  </TD>
               </tr>		  	
          	<tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<bean:message key="passwordDisplayPeriod"/>
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		   <bean:message key="passwordDisplayPeriod"/>
							
		  </TD>
               </tr>		  	
          	<tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<bean:message key="workingCapitalTenure"/>
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		   <bean:message key="workingCapitalTenure"/>
							
		  </TD>
               </tr>		  	
          	<tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<bean:message key="maxInterestRate"/>
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		   <bean:message key="maxInterestRate"/>
							
		  </TD>
               </tr>		  	
          	<tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<bean:message key="minAmtMandatoryPAN"/>
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		   <bean:message key="minAmtMandatoryPAN"/>
							
		  </TD>
               </tr>		  	
          	<tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<bean:message key="serviceFeeWithoutPenalty"/>
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		   <bean:message key="serviceFeeWithoutPenalty"/>
							
		  </TD>
               </tr>		  	
          	<tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<bean:message key="serviceFeePenaltyRate"/>
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		   <bean:message key="serviceFeePenaltyRate"/>
							
		  </TD>
               </tr>		  	
          	<tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<bean:message key="cgpanPerCgdan"/>
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		   <bean:message key="cgpanPerCgdan"/>
							
		  </TD>
               </tr>		  	
          	<tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<bean:message key="gfWithoutPenalty"/>
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		   <bean:message key="gfWithoutPenalty"/>
							
		  </TD>
               </tr>		  	
          	<tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<bean:message key="shortPaymentLimit"/>
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		   <bean:message key="shortPaymentLimit"/>
							
		  </TD>
               </tr>		  	
          	<tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<bean:message key="excessPaymentLimit"/>
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		   <bean:message key="excessPaymentLimit"/>
							
		  </TD>
               </tr>		  	
          	<tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<bean:message key="waiveLimit"/>
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		   <bean:message key="waiveLimit"/>
							
		  </TD>
               </tr>		  	
          	<tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<bean:message key="gfFirstAlertDate"/>
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		   <bean:message key="gfFirstAlertDate"/>
							
		  </TD>
               </tr>		  	
          	<tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<bean:message key="gfAlertFrequency"/>
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		   <bean:message key="gfAlertFrequency"/>
							
		  </TD>
               </tr>		  	
          	<tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<bean:message key="sfCalDate"/>
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		   <bean:message key="sfCalDate"/>
							
		  </TD>
               </tr>		  	
          	<tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<bean:message key="sfCalFreqency"/>
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		   <bean:message key="sfCalFreqency"/>
							
		  </TD>
               </tr>		  	
          	<tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<bean:message key="periodicInfoFrequency"/>
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		   <bean:message key="periodicInfoFrequency"/>
							
		  </TD>
               </tr>		  	
          	<tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<bean:message key="periodicInfoAlertFreq"/>
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		   <bean:message key="periodicInfoAlertFreq"/>
							
		  </TD>
               </tr>		  	
          	<tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<bean:message key="bankRate"/>
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		   <bean:message key="bankRate"/>
							
		  </TD>
               </tr>		  	
          	<tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<bean:message key="gfPenaltyRate"/>
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		   <bean:message key="gfPenaltyRate"/>
							
		  </TD>
               </tr>		  	
          	<tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<bean:message key="firstInstallClaim"/>
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		   <bean:message key="firstInstallClaim"/>
							
		  </TD>
               </tr>		  	
          	<tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<bean:message key="lockInPeriod"/>
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		   <bean:message key="lockInPeriod"/>
							
		  </TD>
               </tr>		  	
          	<tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<bean:message key="periodClaimWithoutPenalty"/>
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		   <bean:message key="periodClaimWithoutPenalty"/>
							
		  </TD>
               </tr>		  	
          	<tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<bean:message key="claimPenaltyRate"/>
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		   <bean:message key="claimPenaltyRate"/>
							
		  </TD>
               </tr>		  	
          	<tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<bean:message key="penaltyCalculation"/>
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		   <bean:message key="penaltyCalculation"/>
							
		  </TD>
               </tr>		  	
          	<tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<bean:message key="mcgfServiceFee"/>
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		   <bean:message key="mcgfServiceFee"/>
							
		  </TD>
               </tr>		  	
          	<tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<bean:message key="mcgfGuaranteeFeePercentage"/>
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		   <bean:message key="mcgfGuaranteeFeePercentage"/>
							
		  </TD>
               </tr>		  	
          	<tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<bean:message key="applicationFilingTimeLimit"/>
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		   <bean:message key="applicationFilingTimeLimit"/>
							
		  </TD>
               </tr>		  	
          	<tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<bean:message key="minSanctionedAmount"/>
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		   <bean:message key="minSanctionedAmount"/>
							
		  </TD>
               </tr>		  	
          	<tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<bean:message key="maxSanctionedAmount"/>
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		   <bean:message key="maxSanctionedAmount"/>
							
		  </TD>
               </tr>		  	
          	<tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<bean:message key="maxApprovalAmt"/>
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		   <bean:message key="maxApprovalAmt"/>
							
		  </TD>
               </tr>		  	
          	<tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<bean:message key="serviceFee"/>
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		   <bean:message key="serviceFee"/>
							
		  </TD>
               </tr>		  	
          	<tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<bean:message key="guaranteeFee"/>
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		   <bean:message key="guaranteeFee"/>
							
		  </TD>
               </tr>		  	
          	<tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<bean:message key="thirdPartyGuarantee"/>
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		   <bean:message key="thirdPartyGuarantee"/>
							
		  </TD>
               </tr>		  	
          	<tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<bean:message key="collateralTaken"/>
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		   <bean:message key="collateralTaken"/>
							
		  </TD>
               </tr>		  	
          	<tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<bean:message key="isDefaultRateApplicable"/>
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		   <bean:message key="isDefaultRateApplicable"/>
							
		  </TD>
               </tr>		  	
          	<tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<bean:message key="defRate"/>
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		   <bean:message key="defRate"/>
							
		  </TD>
               </tr>		  	
          	<tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<bean:message key="moderationFactorAdmin"/>
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		   <bean:message key="moderationFactorAdmin"/>
							
		  </TD>
               </tr>		  	
          	<tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<bean:message key="cgtsiLiability"/>
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		   <bean:message key="cgtsiLiability"/>
							
		  </TD>
               </tr>		  	
          	<tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<bean:message key="highValClearanceAmnt"/>
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		   <bean:message key="highValClearanceAmnt"/>
							
		  </TD>
               </tr>		  	
               
		  <TR align="center" valign="baseline" >
						<td colspan="2" width="700">
						<DIV align="center">
						<A  HREF="javascript:history.back()">	
						<IMG src="images/Back.gif" alt="Back" width="49" height="37" border="0">
						</A>								
						</DIV>
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
	</form>
</TABLE>
