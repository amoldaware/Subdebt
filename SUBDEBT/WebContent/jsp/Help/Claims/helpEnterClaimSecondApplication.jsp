<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<% session.setAttribute("CurrentPage","helpEnterClaimFirstApplication.do");%>
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
					<TD width="31%" class="Heading">Claim Application Details</TD>
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
				&nbsp;Date of Issue of Recall Notice
			</DIV>
		</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
				Date of Issue of Recall Notice
		  </TD>
          </tr>
	<tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
			<DIV align="left">
				&nbsp;Upload File
			</DIV>
		</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
				Attachment if any for Recall Notice
		  </TD>
          </tr>          
		   <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
			<DIV align="left">
				&nbsp;Forum thru which Legal Proceedings were initiated.
			</DIV>
		</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
			Forum thru which Legal Proceedings were initiated.
		  </TD>
          </tr>
		   <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
			<DIV align="left">
				&nbsp;Suit/Case Registration Number
			</DIV>
		</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
			Suit/Case Registration Number
		  </TD>
          </tr>
		   <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
			<DIV align="left">
				&nbsp;Filing Date
			</DIV>
		</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
			Date of Legal Proceedings
		  </TD>
          </tr>        
		   <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
			<DIV align="left">
				&nbsp;Name of the Forum
			</DIV>
		</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
			Name of the Forum
		  </TD>
          </tr>     
		   <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
			<DIV align="left">
				&nbsp;Location
			</DIV>
		</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		 Name of place of Legal Proceedings
		  </TD>
          </tr> 
		   <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
			<DIV align="left">
				&nbsp;Amount Claimed in the Suit
			</DIV>
		</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		 Amount Claimed in the Suit
		  </TD>
          </tr>
		   <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
			<DIV align="left">
				&nbsp;Any Attachments
			</DIV>
		</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		 Any Attachments towards Legal Proceedings
		  </TD>
          </tr>
		   <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
			<DIV align="left">
				&nbsp;Current Status/Remarks
			</DIV>
		</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		      Current Status/Remarks
		  </TD>
          </tr> 
		   <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
			<DIV align="left">
				&nbsp;Whether Recovery Proceedings have concluded?
			</DIV>
		</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		      Whether Recovery Proceedings have concluded?
		  </TD>
          </tr>
		   <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
			<DIV align="left">
				&nbsp;Term Loan/Composite Loan Details
			</DIV>
		</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
		      <li>Date of Last Disbursement</li>, <br><li>Principal Amount Repaid,</li> <br><li>Interest and Other Charges Repaid</li>
		      <br><li>Outstanding Amount as on Date of NPA</li>
		      <br><li>Outstanding Amount as stated in Civil Suit</li>
		      <br><li>Outstanding Amount as on Date of Lodgement of Claim</li>
		  </TD>
          </tr>    
		   <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
			<DIV align="left">
				&nbsp;Working Capital Limit Details
			</DIV>
		</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
		      <li>Outstanding Amount as on Date of NPA</li>, <br><li>Outstanding Amount as stated in the Civil Suit,</li> <br><li>Outstanding Amount as on Date of Lodgment of Claim</li>
		  </TD>
          </tr> 
		   <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
			<DIV align="left">
				&nbsp;Date of Release of WC
			</DIV>
		</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
		      Date of Release of Working Capital
		  </TD>
          </tr>                                                                                                                                                                
          
		   <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
			<DIV align="left">
				&nbsp;Security and Personal Guarantee Details
			</DIV>
		</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
		      <li>Value of property as on Date of Sanction of Credit, Date of NPA and &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Date of Preferrment of Claim</li>, 
		      <br><li>Networth of Guarantor as on Date of Sanction of Credit, Date of &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;NPA and Date of Preferrment of Claim</li> 
		      <br><li>Reason for Reduction in the value of Security as on Date of &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Sanction of Credit, Date of NPA and Date of Preferrment of Claim</li>
		  </TD>
          </tr>
		   <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
			<DIV align="left">
				&nbsp;Recovery Details
			</DIV>
		</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
		      <li>Principal Amount for Term Loan/Composite Loan Details Recovered</li>, 
		      <br><li>Interest and Other Charges for Term Loan/Composite Loan Details Recovered</li> 
		      <br><li>Principal Amount for Working Capital Loan(s) Recovered</li>
		      <br><li>Other Charges for Working Capital Loan(s) Recovered</li>
		      <br><li>Mode of Recovery. If mode of recovery is thru OTS, enter the Date of Seeking of OTS.</li>
		  </TD>
          </tr>                        
		   <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;Amount Claimed in (Rs.)
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
									Amount being claimed towards each Loan Account.
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
