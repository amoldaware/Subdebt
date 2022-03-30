<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","showCeilingOfInvestment.do?method=showCeilingOfInvestment");%>

<html>
	<body>
		<html:form action="setCeilingOfInvestment.do?method=setCeilingOfInvestment" method="POST">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
				<TR> 
					<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
					<TD background="images/TableBackground1.gif"><IMG src="images/InvestmentManagementHeading.gif" width="169" height="25"></TD>
					<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
				</TR>
    			<TR>
					<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
      				<TD>
      					<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
          					<TR>
          						<TD>
          							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1" height="764">
										<TR>
											<TD class="subHeading" height="16" colspan="2"><bean:message key="investee"/></TD>
										</TR>
                						<TR align="left" valign="top">
											<TD width="20%" valign="top" class="ColumnBackground" height="33"> <div align="left">&nbsp;<bean:message key="investee"/> </div></TD>
											<TD align="left" valign="top" class="tableData" height="33">
												<select name="investeeGrp" id="investeeGrp" >
												  <option selected>Select</option>
												  <option>SBI</option>
													<option>ICICI</option>
												</select>&nbsp;&nbsp;
		                  					</TD>
                						</TR>
                						<TR>
											<TD align="left" valign="top" class="ColumnBackground" height="33"> <div align="left">&nbsp;<bean:message key="ceilingStartDate"/></div></TD>
											<TD align="left" class="tableData" align="center" height="33">
											<input type="text" name="ceilingStartDate"><img src="images/CalendarIcon.gif" onClick="showCalendar('ifForm.ceilingStartDate')" align="center">
											</TD>
										</TR>
                						<TR align="left" valign="top">
										  <TD align="left" valign="top" class="ColumnBackground" height="33"> <div align="left">&nbsp;Ceiling
											  End&nbsp; Date</div></TD>
										  <TD align="left" class="tableData" align="center" height="33">
											<input type="text" name="ceilingStartDate"><img src="images/CalendarIcon.gif" onClick="showCalendar('form1.ceilingStartDate')" align="center">
										  </TD>
										</TR>
										<TR align="left" valign="top">
										  <TD align="left" valign="top" class="ColumnBackground" height="33"> <div align="left">&nbsp;Ceiling Limit</div></TD>
										  <TD align="left" class="tableData" height="33">
											<input type="text" name="ceilingLimit"> in Rs.
										  </TD>
										</TR>
										<TR>
											<TD class="subHeading" height="16">Maturities</TD>
										</TR>
										<TR align="left" valign="top">
										  <TD width="20%" valign="top" class="ColumnBackground" height="33"> <div align="left">&nbsp;Maturity </div></TD>
										  <TD align="left" valign="top" class="tableData" height="33">
											<select name="investeeGrp" id="investeeGrp" >
											  <option selected>Select</option>
											  <option>Short Term</option>
											  <option>Medium Term</option>
											  <option>Long Term</option>
											</select>
										  </TD>
										</TR>
										<TR>
										  <TD align="left" valign="top" class="ColumnBackground" height="34"> <div align="left">&nbsp;Ceiling Start Date</div></TD>
										  <TD align="left" class="tableData" align="center" height="34">
											<input type="text" name="ceilingStartDate"><img src="images/CalendarIcon.gif" onClick="showCalendar('form1.ceilingStartDate')" align="center">
										  </TD>
										</TR>
										<TR align="left" valign="top">
										  <TD align="left" valign="top" class="ColumnBackground" height="34"> <div align="left">&nbsp;Ceiling
											  End&nbsp; Date</div></TD>
										  <TD align="left" class="tableData" align="center" height="34">
											<input type="text" name="ceilingStartDate"><img src="images/CalendarIcon.gif" onClick="showCalendar('form1.ceilingStartDate')" align="center">
										  </TD>
										</TR>
										<TR align="left" valign="top">
										  <TD align="left" valign="top" class="ColumnBackground" height="33"> <div align="left">&nbsp;Ceiling Limit</div></TD>
										  <TD align="left" class="tableData" height="33">
											<input type="text" name="ceilingLimit"> in Rs.
										  </TD>
										</TR>
										<TR>
											<TD class="subHeading" height="16">Instruments</TD>
										</TR>
										<TR align="left" valign="top">
										  <TD width="20%" valign="top" class="ColumnBackground" height="33"> <div align="left">&nbsp;Instrument Type</div></TD>
										  <TD align="left" valign="top" class="tableData" height="33">
											<select name="investeeGrp" id="investeeGrp" >
											  <option selected>Select</option>
											  <option>Bonds</option>
											  <option>Government Securities</option>
											  <option>Mutual Funds</option>
											  <option>Commercial Papers</option>
											  <option>Fixed Deposit</option>
											</select>
										  </TD>
										</TR>
										<TR>
										  <TD align="left" valign="top" class="ColumnBackground" height="34"> <div align="left">&nbsp;Ceiling Start Date</div></TD>
										  <TD align="left" class="tableData" align="center" height="34">
											<input type="text" name="ceilingStartDate"><img src="images/CalendarIcon.gif" onClick="showCalendar('form1.ceilingStartDate')" align="center">
										  </TD>
										</TR>
										<TR align="left" valign="top">
										  <TD align="left" valign="top" class="ColumnBackground" height="33"> <div align="left">&nbsp;Ceiling
											  End Date</div></TD>
										  <TD align="left" class="tableData" align="center" height="33">
											<input type="text" name="ceilingStartDate"><img src="images/CalendarIcon.gif" onClick="showCalendar('form1.ceilingStartDate')" align="center">
										  </TD>
										</TR>
										<TR align="left" valign="top">
										  <TD align="left" valign="top" class="ColumnBackground" height="33"> <div align="left">&nbsp;Ceiling Limit</div></TD>
										  <TD align="left" class="tableData" height="33">
											<input type="text" name="ceilingLimit"> in Rs.
										  </TD>
										</TR>
										<TR>
											<TD class="subHeading" colspan="2" height="16">Tangible Assets and Net Worth</TD>
										</TR>
										<TR align="left" valign="top">
										  <TD width="20%" valign="top" class="ColumnBackground" height="33"> <div align="left">&nbsp;Investee </div></TD>
										  <TD align="left" valign="top" class="tableData" height="33">
											<select name="investeeGrp" id="investeeGrp" >
											  <option selected>Select</option>
											   <option>SBI</option>
												<option>ICICI</option>
											</select>&nbsp;&nbsp;
										  </TD>
										</TR>
										<TR align="left" valign="top">
										  <TD width="20%" valign="top" class="ColumnBackground" height="29"> <div align="left">&nbsp;Tangible Assets</div></TD>
										  <TD align="left" valign="top" class="tableData" height="29">Rs.200000</TD>
										</TR>
										<TR align="left" valign="top">
										  <TD width="20%" valign="top" class="ColumnBackground" height="29"> <div align="left">&nbsp;Net Worth</div></TD>
										  <TD align="left" valign="top" class="tableData" height="29">Rs.200000</TD>
										</TR>
										<TR>
										  <TD align="left" valign="top" class="ColumnBackground" height="33"> <div align="left">&nbsp;Ceiling Start Date</div></TD>
										  <TD align="left" class="tableData" align="center" height="33">
											<input type="text" name="ceilingStartDate"><img src="images/CalendarIcon.gif" onClick="showCalendar('form1.ceilingStartDate')" align="center">
										  </TD>
										</TR>
										<TR align="left" valign="top">
										  <TD align="left" valign="top" class="ColumnBackground" height="33"> <div align="left">&nbsp;Ceiling
											  End&nbsp; Date</div></TD>
										  <TD align="left" class="tableData" align="center" height="33">
											<input type="text" name="ceilingStartDate"><img src="images/CalendarIcon.gif" onClick="showCalendar('form1.ceilingStartDate')" align="center">
										  </TD>
										</TR>
										<TR align="left" valign="top">
										  <TD align="left" valign="top" class="ColumnBackground" height="33"> <div align="left">&nbsp;Ceiling Limit</div></TD>
										  <TD align="left" class="tableData" height="33">
											<input type="text" name="ceilingLimit"> in % of Tangible Assets + Net Worth
										  </TD>
										</TR>
              						</TABLE>
              					</TD>
          					</TR>
          					<TR >
            					<TD height="20" >&nbsp;</TD>
          					</TR>
							<TR >
								<TD align="center" valign="baseline" >
									<DIV align="center">
										<A href="javascript:submitForm('setCeilingOfInvestment.do?method=setCeilingOfInvestment')">
											<IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>
										<A href="javascript:document.investmentForm.reset()">
											<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>
										<A href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
											<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>
									</DIV>
								</TD>
							</TR>
						</TABLE>
					</TD>
      				<TD width="20" background="images/TableVerticalRightBG.gif">
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
  		</TABLE>
	</html:form>
	</body>
</html>
