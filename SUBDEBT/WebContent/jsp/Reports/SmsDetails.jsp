<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>

<script type="text/javascript"> 
        window.history.forward(); 
        function noBack() { 
            window.history.forward(); 
        } 
    </script> 
	
	<script src="/csrfguard"></script>
<%
	session.setAttribute("CurrentPage","hoUserCumlativeReportInput.do");
//session.setAttribute("memberId","login?method=login");
%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
<html:errors />

<html:form action="/hoUserCumlativeReportSubmit.do" method="POST" focus="memberId">
<TABLE width="680" border="0" cellspacing="0" cellpadding="0" align="center">
<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>

			<TD width="713">

			<!-- <DIV align="right">			
				<A HREF="javascript:submitForm('helpChangePassword.do?method=helpChangePassword')">
			    HELP</A>
			</DIV> -->
			
			<TABLE width="606" border="0" cellspacing="1" cellpadding="0">
			<TD  align="left" colspan="5"><font size="2"><bold>
				Fields marked as </font><font color="#FF0000" size="2">*</font><font size="2"> are mandatory</bold></font>
				</td>
          <TR> 
            <TD colspan="4" width="700"><TABLE width="604" border="0" cellspacing="0" cellpadding="0">
												<TD width="100%" class="Heading">
												The above submission is with reference to ECLGS scheme and number needs to be entered on cumulative, basis as on date.
                                             </TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="5" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD> 

								</TR>

          <TR>
		    <TD align="left" valign="top" class="ColumnBackground" >
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="hoReportBankName" />
			</TD>
			<TD align="left" valign="top" class="TableData" >										
			<html:text property="hoReportBankName" size="20" maxlength="30"  alt="hoReportBankName"  name="rsForm"/>
			</TD> 
			 <TD align="left" valign="top" class="ColumnBackground" >
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="hoReportEligBorrower" />
			</TD>
			<TD align="left" valign="top" class="TableData" >										
			<html:text property="hoReportEligBorrower" size="20" maxlength="16" value="0" alt="hoReportEligBorrower"  name="rsForm" onkeypress="return numbersOnly(this, event)"
							onkeyup="isValidNumber(this)"/>
			</TD>   
          </TR>
          
           <TR>
           <TD align="right" valign="top" class="ColumnBackground" >
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="hoReportTotalOs" />
			</TD>
			<TD align="right" valign="top" class="TableData" >										
			<html:text property="hoReportTotalOs" size="20" maxlength="16"  alt="hoReportTotalOs"  name="rsForm" onkeypress="return decimalOnly(this, event,13)"
							onkeyup="isValidDecimal(this)"/>
			</TD>   
		    <TD align="left" valign="top" class="ColumnBackground" >
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="hoReport20EligOs" />
			</TD>
			<TD align="left" valign="top" class="TableData" >										
			<html:text property="hoReport20EligOs" size="20" maxlength="16" value="0" alt="hoReport20EligOs"  name="rsForm" onkeypress="return numbersOnly(this, event)"
							onkeyup="isValidNumber(this)"/>
			</TD>    
          </TR>
            
            <TR>
            <TD align="right" valign="top" class="ColumnBackground" >
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="hoReportNoBorrowerOffer" />
			</TD>
			<TD align="right" valign="top" class="TableData" >										
			<html:text property="hoReportNoBorrowerOffer" size="20" maxlength="16" value="0"  alt="hoReportNoBorrowerOffer"  name="rsForm" onkeypress="return numbersOnly(this, event)"
							onkeyup="isValidNumber(this)"/>
			</TD>              
		    <TD align="left" valign="top" class="ColumnBackground" >
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="hoReportNoBorrowerOpt" />
			</TD>
			<TD align="left" valign="top" class="TableData" >										
			<html:text property="hoReportNoBorrowerOpt" size="20" maxlength="16"  value="0" alt="hoReportNoBorrowerOpt"  name="rsForm" onkeypress="return numbersOnly(this, event)"
							onkeyup="isValidNumber(this)"/>
			</TD>  
          </TR>
          
           <TR>
             <TD align="right" valign="top" class="ColumnBackground" >
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="hoReportSanctionNo" />
			</TD>
			<TD align="right" valign="top" class="TableData" >										
			<html:text property="hoReportSanctionNo" size="20" maxlength="16" value="0"  alt="hoReportSanctionNo"  name="rsForm" onkeypress="return numbersOnly(this, event)"
							onkeyup="isValidNumber(this)"/>
			</TD> 
		    <TD align="left" valign="top" class="ColumnBackground" >
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="hoReportSanctionAmt" />
			</TD>
			<TD align="left" valign="top" class="TableData" >										
			<html:text property="hoReportSanctionAmt" size="20" maxlength="16"  alt="hoReportSanctionAmt"  name="rsForm" onkeypress="return decimalOnly(this, event,13)"
							onkeyup="isValidDecimal(this)"/>
			</TD>    
          </TR>
          
           <TR>
            <TD align="right" valign="top" class="ColumnBackground" >
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="hoReportDisburseNo" />
			</TD>
			<TD align="right" valign="top" class="TableData" >										
			<html:text property="hoReportDisburseNo" size="20" maxlength="16" value="0"  alt="hoReportDisburseNo"  name="rsForm" onkeypress="return numbersOnly(this, event)"
							onkeyup="isValidNumber(this)"/>
			</TD> 
		    <TD align="left" valign="top" class="ColumnBackground" >
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="hoReportDisburseAmt" />
			</TD>
			<TD align="left" valign="top" class="TableData" >										
			<html:text property="hoReportDisburseAmt" size="20" maxlength="16"  alt="hoReportDisburseAmt"  name="rsForm" onkeypress="return decimalOnly(this, event,13)"
							onkeyup="isValidDecimal(this)"/>
			</TD>   
          </TR>         
          
            <TR align="center" valign="baseline" >
						<TD colspan="4" width="700"><DIV align="center">
						<A href="javascript:submitForm('hoUserCumlativeReportSubmit.do?method=hoUserCumlativeReportSubmit')">
									<IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>
								<A href="javascript:document.rsForm.reset()">
									<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>
								
						<A href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>"><IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>
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
	</html:form>
</TABLE>

