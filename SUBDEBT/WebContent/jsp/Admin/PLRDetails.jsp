<%@ page language="java"%>
<%@ page import="com.cgtsi.admin.PLRMaster"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","getPLRDetails.do?method=getPLRDetails");%>
<body onLoad="chooseModifyPLR()">
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="modifyPLR.do?method=modifyPLR" method="POST" enctype="multipart/form-data">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>

		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
			      <DIV align="right">			
			      		<A HREF="javascript:submitForm('helpModifyPLRDtl.do')">
			      	        HELP</A>
			      </DIV>        
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="10"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="27%" class="Heading"><bean:message key="plrMasterHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>
									  <tr>
										  <TD width="20%" align="left" valign="top" class="ColumnBackground">
																		<DIV align="left">
																			&nbsp;Member Id
																		</DIV>
																	</TD>
									   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
																		<bean:write property="memberId" name="adminActionForm"/>
										  </TD>
									  </tr>
									  
									  <tr>
										  <TD width="20%" align="left" valign="top" class="ColumnBackground">
																		<DIV align="left">
																			&nbsp;Bank Name
																		</DIV>
																	</TD>
									   <TD width="27%" align="left" valign="top" class="TableData">&nbsp;
																		<bean:write property="bankName" name="adminActionForm"/>
										  </TD>
									  </tr>
								</TR>
								
				<tr>
					<td colspan="9"><table width="100%" border="0" cellspacing="1" cellpadding="0">
						<tr class="HeadingBg">
							<td ><bean:message key="startDate"/><br></td>
							<td ><bean:message key="endDate"/><br></td>
							<td ><bean:message key="shortTermPLR"/>&nbsp<bean:message key="inPer"/></td>							
							<td ><bean:message key="mediumTermPLR"/>&nbsp<bean:message key="inPer"/></td>
							<td ><bean:message key="longTermPLR"/>&nbsp<bean:message key="inPer"/></td>
							<td ><bean:message key="shortTermPeriod"/>&nbsp<bean:message key="inDays"/></td>							
							<td ><bean:message key="mediumTermPeriod"/>&nbsp<bean:message key="inYears"/></td>
							<td ><bean:message key="longTermPeriod"/>&nbsp<bean:message key="inYears"/></td>							
							<td ><bean:message key="PLR"/></td>
							<td ><bean:message key="BPLR"/>&nbsp<bean:message key="inPer"/></td>
						</tr>	
						<tr class="TableData">
							<td><div align="center">
							<html:text property="plrMaster.startDate" size="7" maxlength="10" name="adminActionForm"/>								
							</div></td>							
							
							<td><div align="center">
							<html:text property="plrMaster.endDate" size="7" maxlength="10" name="adminActionForm"/>
							</div></td>
							
							<td><div align="center">
							<html:text property="plrMaster.shortTermPLR" maxlength="5" size="5" name="adminActionForm" onkeypress="return decimalOnly(this, event,2)" onkeyup="isValidDecimal(this)"/>
							</div></td>
							
							<td><div align="center">
							<html:text property="plrMaster.mediumTermPLR"  maxlength="5" size="5" name="adminActionForm" onkeypress="return decimalOnly(this, event,2)" onkeyup="isValidDecimal(this)"/>
							</div></td>									
							
							<td><div align="center">
							<html:text property="plrMaster.longTermPLR" maxlength="5" size="5" name="adminActionForm" onkeypress="return decimalOnly(this, event,2)" onkeyup="isValidDecimal(this)"/>
							</div></td>
							<td><div align="center">
							<html:text property="plrMaster.shortTermPeriod" maxlength="3" size="5" name="adminActionForm" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>
							</div></td>
							
							<td><div align="center">
							<html:text property="plrMaster.mediumTermPeriod" maxlength="3" size="5" name="adminActionForm" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>
							</div></td>									
							
							<td><div align="center">
							<html:text property="plrMaster.longTermPeriod" maxlength="3" size="5" name="adminActionForm" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>
							</div></td>																
							<TD>
								&nbsp;&nbsp;&nbsp;&nbsp;<html:radio name="adminActionForm" value="B" property="plrMaster.PLR" onclick="chooseModifyPLR()"><br><bean:message key="BPLR" /></html:radio>
                                                                <br>
								&nbsp;&nbsp;&nbsp;&nbsp;<html:radio name="adminActionForm" value="T" property="plrMaster.PLR" onclick="chooseModifyPLR()"><br><bean:message key="TPLR" /></html:radio>
							</TD>
							<TD>
							<html:text property="plrMaster.BPLR" size="5" maxlength="6" onkeypress="return decimalOnly(this, event,2)" onkeyup="isValidDecimal(this)" name="adminActionForm"/>
							</td>																
						</tr>
					</table></td>
				</tr>									  
								

							</TABLE>
						</TD>
					</TR>
					<TR >
						<TD align="center" valign="baseline" >
							<DIV align="center">
									<A href="javascript:submitForm('modifyPLR.do?method=modifyPLR')">
									<IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>
								<A href="getPLRSummary.do?method=getPLRSummary">
									<IMG src="images/Back.gif" alt="Back" width="49" height="37" border="0"></A>									
									<a href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
									<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>
							</DIV>
						</TD>
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

