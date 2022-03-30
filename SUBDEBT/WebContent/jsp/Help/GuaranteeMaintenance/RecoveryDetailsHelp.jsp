<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<% session.setAttribute("CurrentPage","helpRecoveryDetails.do");%>
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
												<TD width="31%" class="Heading"><bean:message key="recoveryHeaderNew" /></TD>
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
											&nbsp;Date of Recovery 
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
										Enter the date of Recovery made.
		  </TD>
          </tr>
          <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;Amount Recovered
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
										Enter the Amount Recovered for the corresponding Borrower Id.
		  </TD>
          </tr>

		  <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;Legal Charges incurred, if any
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
										Enter the Legal Charges incurred while making recovery for the Borrower Id.
		  </TD>							
          </tr>
		  	  
		  <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;Remarks
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
										Enter the Remarks if any.
		  </TD>							
          </tr>

		  <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;Is Recovery By way of OTS
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
										Enter whether the Recovery made is by way of OTS or not.
		  </TD>							
          </tr>
		  
		  <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;Recovery by sale of Assets 
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
										Enter whether the Recovery made is by Sale of Assets.
		  </TD>							
          </tr>

		  <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;If Yes Details of asset sold
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
										If Recovery by sale of Assets is Yes then enter the details of the asset sold.
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
