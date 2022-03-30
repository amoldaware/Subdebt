<%@ page language="java"%>
<%@ page import="com.cgtsi.util.SessionConstants"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<form>
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>


<TABLE width="100%" border="0" cellspacing="1" cellpadding="0">
 <tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
									<DIV align="left">
										&nbsp;Bank / Institution Reference No
									</DIV>
								</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
					Enter the Bank / Institution Reference No.
	  </TD>
</tr>
 <tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
									<DIV align="left">
										&nbsp; Bank / Institution Branch Name
									</DIV>
								</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
					Enter the  Bank / Institution Branch Name.
	  </TD>
</tr>
 <tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
									<DIV align="left">
										&nbsp; Bank / Institution Branch Code
									</DIV>
								</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
					Enter the  Bank / Institution Branch Code.
	  </TD>
</tr>

 <tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground" colspan="2">
									<DIV align="left">
										&nbsp;
								Borrower Details cannot be modified.
									</DIV>
								</TD>
</tr>
<tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
		<DIV align="left">
			&nbsp; 
			Guarantors Details
		</DIV>
		</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;		
			Enter the name of the guarantors and value of Net Worth
			If Net Worth for any of the guarantor is entered ,then name of the guarantor is mandatory.
		</TD>
</tr>
<tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
		<DIV align="left">
			&nbsp; 
			Name of Subsidy / Equity Support
		</DIV>
		</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;		
			Select the name of Subsidy / Equity Support	from the combo box.
			If 'Others' is selected, name of Subsidy / Equity Support should be entered in the text box provided.
		</TD>
</tr>
<tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
		<DIV align="left">
			&nbsp; 
			Primary Security Details
		</DIV>
		</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;		
			Enter the particulars for the security name mentioned and value of the security being provided.All the particulars details are not mandatory.If value is entered, then the corresponding particular are mandatory.	
		</TD>
</tr>
<tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
		<DIV align="left">
			&nbsp; 
			Term Credit Sanctioned 
		</DIV>
		</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;		
			Enter the sanctioned amount for term credit loan.
			This field is mandatory while applying for term loan / composite / 'both' application.</TD>
</tr>
<tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
		<DIV align="left">
			&nbsp; 
			Other Means of Finance like Promoter's Contribution,Subsidy / Equity Support,Others for Term Credit Loan
		</DIV>
		</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;		
			Enter the value of other means of finance like promoter contribution, subsidy / equity support,others.If any of these fields are entered, then term credit sanctioned amount is mandatory.
		</TD>
</tr>
<!--<tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
		<DIV align="left">
			&nbsp; 
			Subsidy / Equity Support
		</DIV>
		</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;		
			Enter the value of subsidy / equity support.If this field is entered, then term credit sanctioned amount is mandatory.
		</TD>
</tr>
<tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
		<DIV align="left">
			&nbsp; 
			Other Means of Finance
		</DIV>
		</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;		
			Enter the value of subsidy / equity support.If this field is entered, then term credit sanctioned amount is mandatory.
		</TD>
</tr>
-->
<tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
		<DIV align="left">
			&nbsp; 
			Working Capital Limit Sanctioned Value
		</DIV>
		</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;		
			Enter the value of fund based limit sanctioned amount for working capital loan
		</TD>
</tr>
<tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
		<DIV align="left">
			&nbsp; 
			Other Means of Finance like Promoter's Contribution,Subsidy / Equity Support,Others for Working Capital Loan
		</DIV>
		</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;		
			Enter the value of other means of finance like promoter contribution, subsidy / equity support,others.If any of these fields are entered, then fund based limit sanctioned amount is mandatory.
		</TD>
</tr>
<tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
		<DIV align="left">
			&nbsp; 
			Project Cost	
		</DIV>
		</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;		
			Project Cost is calculated as the sum of 
			term credit Sanctioned + promoters contribution + Subsidy / Equity Support + Others value
		</TD>
</tr>
<tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
		<DIV align="left">
			&nbsp; 
			Working Capital Assessed
		</DIV>
		</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;		
			Working Capital Assessed is calculated as the sum of 
			fund based limit sanctioned amount + promoters contribution + Subsidy / Equity Support + Others means of finance amount.
		</TD>
</tr>
<tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
		<DIV align="left">
			&nbsp; 
			Project Outlay
		</DIV>
		</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;		
			Project Outlay is calculated as the sum of Project Cost + Working Capital Assessed.
		</TD>
</tr>

 <tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground" colspan="2">
									<DIV align="left">
										&nbsp;
								Working Capital Renewal Details
									</DIV>
								</TD>
</tr>
<tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
									<DIV align="left">
										&nbsp;
								Renewal Fund Based Interest
									</DIV>
								</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
					Enter the value of Fund Based Interest.Interest should not be greater than or equal to 100.
					</TD>
</tr>
<tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
									<DIV align="left">
										&nbsp;
								Type of PLR
									</DIV>
								</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		Enter the type of PLR for working capital.
		</TD>
</tr>
<tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
									<DIV align="left">
										&nbsp;
								Prime Lending Rate
									</DIV>
								</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		Enter the Prime Lending Rate for working capital.
		</TD>
</tr>

<tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
									<DIV align="left">
										&nbsp;
								Date of Renewal
									</DIV>
								</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
					Enter the date on which the application is renewed.
					</TD>
</tr>
<tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
									<DIV align="left">
										&nbsp;
								Remarks
									</DIV>
								</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
					Enter any remarks for the renewal of the application.
					</TD>
</tr>
</table>
<%@include file="SecuritizationHelp.jsp"%>
			<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD align="center" valign="baseline" colspan="7">
							<DIV align="center">
							<A href="javascript:history.back()">
									<IMG src="images/Back.gif" alt="Back" width="49" height="37" border="0"></A>
							</DIV>
						</TD>
					</TR>	
			</table>
				
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
</body>

