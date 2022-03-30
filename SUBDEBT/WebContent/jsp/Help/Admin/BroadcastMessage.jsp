<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<% session.setAttribute("CurrentPage","helpBroadcastMessage.do");%>
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
												<TD width="31%" class="Heading"><bean:message key="broadcastMessageHeader" /></TD>
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
											&nbsp;All Hos
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
							Select All Hos  to send the entered message to all the users of HO of all the members.
		  </TD>
          </tr>
          <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;Members of Bank
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
						Select Members of Bank and choose a member from the Bank combo box to send the entered message to the all the users of the chosen Bank.
		  </TD>
          </tr>	
		  <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;Members of zone/region
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
								Select Members of zone/region.Select a member from the Bank combobox and select a zone from the zone/region combo box.The entered message will be sent to the all the users of the chosen zone/region.Select ALL in the zone/region combobox to send message to all the users of all the zones/region of the bank.	
		  </TD>
          </tr>	
		  <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;Members of Branch
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
					Select Members of branch.Select a member from the Bank combobox and select a branch from the branch combo box.The entered message will be sent to the all the users of the chosen branch.Select ALL in the branch combobox to send message to all the users of all the branches of the bank.	
		  </TD>
          </tr>	
		  <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;NO of Bank
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
										By selecting NO of Bank and choosing a member from the Bank combo box the entered message will be sent to the NO user of the chosen Bank.
		  </TD>
          </tr>	
		  <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;NO of Branches
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
										Select NO of Branches.Select a member from the Bank combobox and select a branch from the branch combo box.The entered message will be sent to the NO user of the chosen branch.Select ALL in the branch combobox to send message to the NO users of all the branches of the bank.	
		  </TD>
          </tr>	
		   <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;NO of Zone/region						</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
										Select NO of Zone/region.Select a member from the Bank combobox and select a zone from the zone/region combo box.The entered message will be sent to the NO users of the chosen zone/region.Select ALL in the zone/region combobox to send message to NO users of all the zones/region of the bank.	
		  </TD>
          </tr>	
		  <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;All Members			
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
										Select All Members to send the entered message to all the users of all the members.
		  </TD>
          </tr>	
		  <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;Message
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
										Enter the Message to be sent.
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
