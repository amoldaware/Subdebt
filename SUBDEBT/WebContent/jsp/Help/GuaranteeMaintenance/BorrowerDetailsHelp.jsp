<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<% session.setAttribute("CurrentPage","helpBorrowerDetails.do");%>
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
												<TD width="31%" class="Heading"><bean:message key="borrowerHeader" /></TD>
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
										&nbsp; Whether already assisted by bank 
									</DIV>
								</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
					Select an option for Whether the borrower has already been assisted by bank or not.
	  </TD>
</tr>
 <tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
									<DIV align="left">
										&nbsp; 
										Outstanding Amount on date of sanction of credit Facility
									</DIV>
								</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;			
				If borrower has been assisted by bank,outstanding amount can been entered.If not, then outstanding amount cannot be entered.
		</TD>
</tr>
 <tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
									<DIV align="left">
										&nbsp; 
										NPA 
									</DIV>
								</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;			
		Select the option for whether borrower has turned NPA or not.If NPA is selected as 'Yes',then he cannot submit the application further.
		</TD>
</tr>
<tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
									<DIV align="left">
										&nbsp; 
										 Whether Borrower previously Covered by CGTSI  
									</DIV>
								</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;			
			If borrower has been covered by CGTSI previously, then either the value of CGPAN / Borrower ID should be entered.Only if the borrower has been assisted by bank,he can be covered by CGTSI.
		</TD>
</tr>
<tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
									<DIV align="left">
										&nbsp; 
										Constitution
									</DIV>
								</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;			
		Select the value of Constitution from the combo box. If 'Others' is selected, then enter the value in the text box provided.
		</TD>
</tr>
<tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
									<DIV align="left">
										&nbsp; 
										Borrower Name
									</DIV>
								</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;			
			Select the Borrower Type from the combo box and enter the name of teh borrower.		
			</TD>
</tr>
<tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
									<DIV align="left">
										&nbsp; 
										SSI Registration No.
									</DIV>
								</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;		
			Enter the registration no. of the borrower
		</TD>
</tr>
<tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
									<DIV align="left">
										&nbsp; 
										ITPAN of Firm
									</DIV>
								</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;		
			Enter the itpan of the firm
		</TD>
</tr>
<tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
									<DIV align="left">
										&nbsp; 
										Nature of Industry
									</DIV>
								</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;		
			Select the nature of industry from the combo box
		</TD>
</tr>
<tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
									<DIV align="left">
										&nbsp; 
										Industry Sector
									</DIV>
								</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;		
			Select the industry sector from the combo box		
		</TD>
</tr>
<tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
									<DIV align="left">
										&nbsp; 
										Type of Activity
									</DIV>
								</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;		
			Enter the type of activity of the borrower
		</TD>
</tr>
<tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
									<DIV align="left">
										&nbsp; 
										Number of Employees
									</DIV>
								</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;		
			Enter the number of employees in the firm
		</TD>
</tr>
<tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
									<DIV align="left">
										&nbsp; 
										Projected Optimum Sales Turnover
									</DIV>
								</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;		
			Enter the value of projected optimum sales turnover of the borrower
		</TD>
</tr>
<tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
									<DIV align="left">
										&nbsp; 
										Projected Optimum Exports
									</DIV>
								</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;		
			Enter the value of projected optimum exports of the borrower
		</TD>
</tr>
<tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
									<DIV align="left">
										&nbsp; 
										Address
									</DIV>
								</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;		
			Enter the address of the borrower
		</TD>
</tr>
<tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
									<DIV align="left">
										&nbsp; 
										State
									</DIV>
								</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;		
			Select the state where borrower belongs to
		</TD>
</tr>
<tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
									<DIV align="left">
										&nbsp; 
										District
									</DIV>
								</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;		
			Select the district where borrower belongs to.
			If 'Others' is selected, the name of district has to be entered in the text box provided.
		</TD>
</tr>
<tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
									<DIV align="left">
										&nbsp; 
										Pincode
									</DIV>
								</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;		
	   Enter the pincode of the borrower's location.
		</TD>
</tr>

<tr>
	  <TD align="left" valign="top" class="ColumnBackground" colspan="3">
	  Promoter's Details
	  </td>
</tr>	

<tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
									<DIV align="left">
										&nbsp; 
										Promoter's Title
									</DIV>
								</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;		
			Select the promoter's title from the combo box
		</TD>
</tr>
<tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
									<DIV align="left">
										&nbsp; 
										Promoter's First Name
									</DIV>
								</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;		
			Enter the first name of the promoter
		</TD>
</tr>
<tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
									<DIV align="left">
										&nbsp; 
										Promoter's Middle Name
									</DIV>
								</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;		
			Enter the middle name of the promoter
		</TD>
</tr>
<tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
									<DIV align="left">
										&nbsp; 
										Promoter's Last Name(Surname)
									</DIV>
								</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;		
			Enter the last name of the promoter
		</TD>
</tr>
<tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
									<DIV align="left">
										&nbsp; 
										Gender
									</DIV>
								</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;		
			Select the gender of the promoter by checking one of the radio buttons.
		</TD>
</tr>

<tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
									<DIV align="left">
										&nbsp; 
										Gender
									</DIV>
								</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;		
			Select the gender of the promoter by checking one of the radio buttons.
		</TD>
</tr>

<tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
									<DIV align="left">
										&nbsp; 
										ITPAN of Chief Promoter 
									</DIV>
								</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;		
			Enter the ITPAN of Chief Promoter.
		</TD>
</tr>

<tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
									<DIV align="left">
										&nbsp; 
										Date Of Birth
									</DIV>
								</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;		
			Enter the Date Of Birth of the Chief Promoter.
		</TD>
</tr>
<tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
		<DIV align="left">
			&nbsp; 
			Legal ID
		</DIV>
		</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;		
			Select the legal ID from the combo box.If 'Others' is selected, enter the legal ID in the text box provided.
			Enter the value of legal ID in the text box provided.   
		</TD>
</tr>
<tr>
	  <TD width="20%" align="left" valign="top" class="ColumnBackground">
		<DIV align="left">
			&nbsp; 
			Other Main Promoters
		</DIV>
		</TD>
	   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;		
			Enter the name ,ITPAN or Date of Birth of the main promoters.
			All the main promoter's names are not mandatory.If the ITPAN or Date of Birth of any promoter is entered,name of the promoter is mandatory.
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

