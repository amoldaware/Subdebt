<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<% session.setAttribute("CurrentPage","helpNpaDetails.do");%>
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
												<TD width="31%" class="Heading"><bean:message key="NPADetailsHeader" /></TD>
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
											&nbsp;Date On Which Account Turned NPA 
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
										Enter the Date On Which the Account Turned NPA 
		  </TD>
          </tr>
          <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;Outstanding Amount as on NPA Date
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
										Enter the Outstanding Amount for the Borrower as on Date of Acount turning NPA.
		  </TD>
          </tr>

		  <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;Whether NPA was reported to CGTSI 
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
										Select an option for whether the borrower turned to NPA has been reported to the CGTSI or not.
		  </TD>							
          </tr>
		  	  
		  <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;  Date Of Reporting 
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
										If the account turned NPA reported to CGTSI then enter the date of reporting.
		  </TD>							
          </tr>

		  <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;Reasons for account turning NPA 
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
										Enter the reason if any for the account turning NPA.
		  </TD>							
          </tr>
		  
		  <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;Willful Defaulter 
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
										select an option(Yes/No) for whether the borrower has willfully defaulted or not.
		  </TD>							
          </tr>

		  <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;Enumerate efforts taken by MLI to prevent account
											turning NPA and minimising the credit risk 
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
										Enter the efforts taken by MLI if any to prevent account turning NPA and minimising the credit risk .
		  </TD>							
          </tr>

	      <tr>
	        <TD align="left" valign="top" class="ColumnBackground" colspan="3">
        	  &nbsp;Recovery Procedure 
	        </td>
          </tr>	
		  
		  <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;Action Type 
										</DIV>
									</TD>

		   <TD width="27%" align="left" valign="top" class="TableData">
										Select the type of Recovery Action taken from the dropdown.
		  </TD>							
          </tr>


		  <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;Details
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
										Enter the details for the type of action taken.
		  </TD>							
          </tr>
							

		  <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;Date
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
										Enter the date on which the recovery action was taken.
		  </TD>							
          </tr>

		  <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;Attachment
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
										You can attach any files regarding the recovery action taken.Any reference file for the recovery action. The file path will be stored here.
		  </TD>							
          </tr>

		  <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;Recovery Procedure Initiated 
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
										Select an option for whether the recovery procedure has been initiated.
		  </TD>							
          </tr>

		  <tr>
	        <TD align="left" valign="top" class="ColumnBackground" colspan="3">
        	  &nbsp; Details Of Legal Proceedings 
	        </td>
          </tr>	

		  <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;Forum through which legal proceedings were initiated against borrower  
										</DIV>
									</TD>

           <TD width="27%" align="left" valign="top" class="TableData">
										Select any option from the drop down indicating the forum through which the legal proceedings were initiated against borrower such as Civil Court/DRT/Lok Adalat/Revenue Recovery Authority/ Securitisation Act, 2002 (SRFAESIA) etc..
		  </TD>							
          </tr>

		  <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;Suit/Case Registration No. 
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
										Enter the Suit/Case Registration No for the legal proceeding if any. If there are legal details already present then this field is non editable.
		  </TD>							
          </tr>

		  <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp; Date
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
										Enter the date of filing the legal suit.
		  </TD>							
          </tr>

		  <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;Name of the Forum
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
										Enter the Name of the court for the legal proceedings.
		  </TD>							
          </tr>

		  <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;Location
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
										Enter the Location of the legal proceedings taken.
		  </TD>							
          </tr>

		  <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;Amount Claimed
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
										Enter the the amount claimed through the legal proceedings.
		  </TD>							
          </tr>

		  <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;Current Status/Remarks
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
										Enter the the current status/remarks of the legal proceedings carried out against the defaulted borrower.
		  </TD>							
          </tr>

		  <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;Whether recovery proceedings have concluded
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
										Enter the flag to indicate whether the recovery proceedings have concluded or not.
		  </TD>							
          </tr>

		  <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp; Expected Date For Conclusion Of Above Efforts 
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
										Enter the expected date of completion of Recovery actions.
		  </TD>							
          </tr>

		  <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;MLI's Comment on financial position of Borrower/Unit
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
										Enter the remarks/comment from the MLI on the financial position of the borrower.
		  </TD>							
          </tr>

		  <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp; Details of Financial Assistance provided/being considered by MLI to minimize default 
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
										Enter the details of Financial Assistance provided/being considered by MLI to minimize default 
		  </TD>							
          </tr>

		  <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;Does the MLI propose to provide credit support to Chief Promoter/Borrower for any other project 
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
										Enter the Flag to indicate whether the  MLI proposes to provide credit support to Chief Promoter/Borrower for any other project.
		  </TD>							
          </tr>

		  <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;Details Of Bank Facility already provided to Borrower 
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
										Enter the Details Of Bank Facility already provided to Borrower
		  </TD>							
          </tr>

		  <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;Does the MLI advise placing the Borrower and/or Chief Promoter under watch-List of CGTSI 
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
										Enter the Flag to indicate whether the MLI advises placing the Borrower and/or Chief Promoter under watch-List of CGTSI.
		  </TD>							
          </tr>


		  <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;Remarks
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
										Enter any other remarks on the NPA.
		  </TD>							
          </tr>



						<TR align="center" valign="baseline" >
						<td colspan="2" width="700">
						<DIV align="center">
						<A  HREF="javascript:history.back()">	
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
