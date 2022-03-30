<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ page import = "com.cgtsi.actionform.GMActionForm" %>
<%@ page import = "java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>

<% session.setAttribute("CurrentPage","updateNpaDtDetail.do?method=updateNpaDtDetail");%> 

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors/>
	<html:form action="updateNpaDtDetailSave.do?method=updateNpaDtDetailSave" method="POST" enctype="multipart/form-data">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/GuaranteeMaintenanceHeading.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">								
								<tr><td colspan="8">&nbsp;</td></tr>
								<TR>
									<TD colspan="8"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>												
												<TD width="31%" class="Heading"><bean:message key="modifyBorrowerHeader"/></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading">
													<IMG src="images/Clear.gif" width="5" height="5">
												</TD>
											</TR>
										</TABLE>
									</TD>
								</TR>
								<tr>
									<TD  align="left" colspan="4"><font size="2">
										<b>Notes : </b> &nbsp;&nbsp;1. <b>Fields marked as</b></font>
										<font color="#FF0000" size="2">*</font><font size="2"><b>are mandatory</b></font><br/>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;										
										<font size="2"><b>2. Files being uploaded must be in jpg or jpeg or png or pdf or doc or docx format.<br/></b></font>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<font size="2"><b>3. Maximum file size for an individual file must be less than 1MB.<br/></b></font>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<font size="2"><b>4. Maximum length for file name is 25 characters (including space).</b></font>
										
									</td>
								</tr>
								<TR align="left" valign="top">									
									<TD align="left" valign="top" class="ColumnBackground">Unit Name</TD>
									<TD align="left" valign="top" class="TableData">
										<bean:write property="unitName" name="gmClosureForm"/>
									</TD>									
								</tr>
								<tr>
									<TD align="left" valign="top" class="ColumnBackground">CGPAN</td>
									<TD align="left" valign="top" class="TableData">
										<bean:write property="cgpan" name="gmClosureForm"/>
									</TD>									
								</tr>
								<tr>
									<TD align="left" valign="top" class="ColumnBackground">Npa Date Updated<br/>in the System</TD>
									<TD align="left" valign="top" class="TableData">
										<bean:write property="npaUpdateDt" name="gmClosureForm"/>
									</TD>
								</tr>
								<tr>
									<TD align="left" valign="top" class="ColumnBackground">Npa Reasons Turning</TD>
									<TD align="left" valign="top" class="TableData">
										<bean:write property="npaResons" name="gmClosureForm"/>
									</TD>
								</tr>
								<tr>
									<TD align="left" valign="top" class="ColumnBackground"><bean:message key="FileUpload"/></TD>
									<TD align="left" valign="top" class="TableData">
										<html:file property="npaFile" name="gmClosureForm"/>
									</TD>
								</tr>
								<tr>
									<TD align="left" valign="top" class="ColumnBackground">
										<font color="#FF0000" size="2">*</font>Correction in Npa Date
									</td>
									<TD align="left" valign="top" class="TableData">
										<html:text property="correctionDt" name="gmClosureForm" maxlength="10"/>
										<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('gmClosureForm.correctionDt')" align="middle">
									</TD>
								</tr>
								<tr>
									<TD align="left" valign="top"   class="ColumnBackground">
										<font color="#FF0000" size="2">*</font>Modification Of Remarks
									</TD>
									<TD align="left" valign="top" class="TableData">
										<html:text property="modificationOfRemarks" name="gmClosureForm" size="100" maxlength="200"/>										
									</TD>
								</tr>																						
							</TABLE>
						</TD>
					</TR>
					<TR><TD height="20">&nbsp;</TD></TR>
					<TR>
						<TD align="center" valign="baseline">
							<DIV align="center">
								<A href="javascript:submitForm('updateNpaDtDetailSave.do?method=updateNpaDtDetailSave')">
									<IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0">
								</A>
								<A href="javascript:document.gmClosureForm.reset()">
									<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0">
								</A>
								<A href="javascript:submitForm('updateNpaDtInput.do?method=updateNpaDtInput')">
									<IMG src="images/Back.gif" alt="Back" width="49" height="37" border="0">
								</A>															
							</DIV>
						</TD>
					</TR>
				</TABLE>
			</TD>
			<TD width="20" background="images/TableVerticalRightBG.gif">&nbsp;</TD>
		</TR>
		<TR>
			<TD width="20" align="right" valign="top">
				<IMG src="images/TableLeftBottom1.gif" width="20" height="15">
			</TD>
			<TD background="images/TableBackground2.gif">&nbsp;</TD>
			<TD width="20" align="left" valign="top">
				<IMG src="images/TableRightBottom1.gif" width="23" height="15">
			</TD>
		</TR>     
	</html:form>  
</TABLE>