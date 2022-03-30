<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<% session.setAttribute("CurrentPage","helpExposureDetails.do");%>
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
									<TD width="31%" class="Heading"><bean:message key="chooseInvestee" /></TD>
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
		  <TD  align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp; Proposed Date of investment
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
												Date until which the investment has to be proposed.This Date should be later than today's date.
		  </TD>
          </tr>
		  
		   <tr>
		  <TD  align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp; Investments Live upto Proposed Date of Investment   
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
												Enter the total invesments that are live till the Proposed Date of Investment.
		  </TD>
          </tr>
		   <tr>
		  <TD  align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp; Amount invested on  today's date 
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
												Enter the total amount invested on the current date.
		  </TD>
          </tr>          
		   <tr>
		  <TD align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;Investments maturing between  current Date and Proposed Date of Investment 
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
												Enter the total investment maturing between current Date and Proposed Date of Investment.
		  </TD>
          </tr>		  
		   

		   <tr>
		  <TD width="100%" align="left" valign="top" class="ColumnBackground" colspan="2">
										<DIV align="left">
											&nbsp;Additional Amount to be Received on Proposed Date of Investment  
										</DIV>
									</TD>
          </tr>		  

		   <tr>
		  <TD align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;Corpus
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
												Enter the additional Amount to be received through corpus on the proposed date of investment.
		  </TD>
          </tr>		  
							

		   <tr>
		  <TD  align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;Other Receipts
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
												Enter the additional Amount to be received through other receipts on the proposed date of investment.
		  </TD>
          </tr>		  

		   <tr>
		  <TD align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;Provision for Proposed Date of Investment's Expenditure 
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
												Enter the Provision for Proposed Date of Investment's Expenditure.
		  </TD>
          </tr>		  

							
							<TR align="center" valign="baseline" >
						<td colspan="2" width="700">
						<DIV align="center">
						<A href="javascript:submitForm('chooseInvestee.do?method=chooseInvestee')">
						<IMG src="images/Back.gif" alt="Save" width="49" height="37" border="0">
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

