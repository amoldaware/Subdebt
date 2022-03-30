<%@ page language="java"%>
<%@ page import="java.util.Map"%>
<%@ page import="com.cgtsi.actionform.RPActionForm"%>
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.Iterator"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.cgtsi.actionform.ClaimActionForm"%>
<%@page import="org.apache.struts.validator.DynaValidatorActionForm"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@ include file="/jsp/SetMenuInfo.jsp"%>
<%
session.setAttribute("CurrentPage","claimFilesAttachmentInput.do?method=claimFilesAttachmentInput");
%>
<HTML>
<body>

<table width="925" border="0" cellpadding="0" cellspacing="0">
	<html:form action="uploadClaimFiles.do?method=uploadClaimFiles"
		enctype="multipart/form-data" method="post">
		<%
			ClaimActionForm claimForm = (ClaimActionForm) session
						.getAttribute("cpTcDetailsForm");
				double totalAppAmt = 0.0;
				int tc_cgpans = claimForm.getTcCounter();
				int wc_cgpans = claimForm.getWcCounter();
				int total_cgpans = tc_cgpans + wc_cgpans;
				Vector cgpans = (Vector) claimForm.getCgpansVector();
				String appAmtStr = claimForm.getAppAmount();
				if (appAmtStr != null) {
					totalAppAmt = Double.parseDouble(appAmtStr);
				}
		%>
		<td><input type="hidden" name="total_cgpans" id="total_cgpans"
			value="<%=total_cgpans%>" /> <input type="hidden"
			name="total_amount" id="total_amount" value="<%=totalAppAmt%>" /></td>


		<tr>
			<td width="20" align="right" valign="bottom"><img
				src="images/TableLeftTop.gif" width="20" height="31"></img></td>
			<td background="images/TableBackground1.gif">&nbsp;</td>
			<td width="20" align="left" valign="bottom"><img
				src="images/TableRightTop.gif" width="23" height="31"></img></td>
		</tr>
		<tr>
			<td width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</td>
			<td>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
				<TR>
					<TD width="20%" class="Heading">&nbsp;Enter Claim Details</TD>
					<TD width="80%"><IMG src="images/TriangleSubhead.gif"
						width="19" height="19"></TD>
				</TR>
				<TR>
					<TD colspan="4" class="Heading"><IMG src="images/Clear.gif"
						width="5" height="5"></TD>
				</TR>

			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD class="Heading" width="30%" colspan="2">&nbsp;MemberID :<bean:write
						name="cpTcDetailsForm" property="memberId" /></TD>
					<TD class="Heading" width="30%" colspan="2">&nbsp;Claim
					reference Number :<bean:write name="cpTcDetailsForm"
						property="clmRefNumberNew" /></TD>
					<TD class="Heading" width="40%" colspan="2">&nbsp;Unit Name :<bean:write
						name="cpTcDetailsForm" property="unitName" /></TD>
				</TR>
			</TABLE>
			<table width="100%">
				<tr>
					<td style="font-size: 15px;">
					<b>Note: </b>&nbsp;1. Fields marked as <font style="color: red">*</font> are mandatory.
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2. Files being uploaded must be in jpg or jpeg or png or pdf or doc or docx format.
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3. Maximum file size for an individual file must be less than 1MB.
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4. Maximum length for file name is 25 characters (including space).
					</td>
				</tr>
				<tr>
					<TD>
					<!-- new table -->
					<table width="100%">
						<tr>
							<td class="TableData" width="2%"><b>1.</b></td>
							<th class="TableData">Screen shot of NPA date<font
								color="red">*</font></th>
							<td class="HeadingBg"><html:file
								name="cpTcDetailsForm" property="npaReportFile" /></td>
						</tr>
						<tr>
							<td class="TableData"><b>2.</b></td>
							<th class="TableData">Due diligence report at the time of
							appraisal explaining antecedents of the borrower<font color="red">*</font></th>
							<td class="HeadingBg"><div align="left">
							<html:file name="cpTcDetailsForm" property="diligenceReportFiles[0]" /></div>
								
							<div align="left" id="dili1">
							</div>								
							<div align="left" id="dili2">
							</div>
							<hr>
							<div align="left" style="height: 0pt;"><input
								type="button" value="Attach More Files" id="dili"
								style="display: inline; background: gray; text-align: center;"
								onclick="addMoreFiles(this.id)" /></div>
							</td>
						</tr>
						
						<tr>
							<td class="TableData"><b>3.</b></td>
							<th class="TableData">Post disbursement visit/ inspection
							report <font color="red">(Scanned Copies to be furnished)*</font></th>
							<td class="HeadingBg"><div align="left"><html:file
								name="cpTcDetailsForm" property="postInspectionReportFiles[0]" /></div>
							<div align="left" id="inspec1">
							</div>								
							<div align="left" id="inspec2">
							</div>
							<hr>
							<div align="left" style="height: 0pt;"><input
								type="button" value="Attach More Files" id="inspec"
								style="display: inline; background: gray; text-align: center;"
								onclick="addMoreFiles(this.id)" /></div>
								</td>
						</tr>
						<tr>
							<td class="TableData"><b>4.</b></td>
							<th class="TableData">Post NPA visit / inspection reports
							<font color="red">(Scanned Copies to be furnished)*</font></th>
							<td class="HeadingBg"><div align="left"><html:file
								name="cpTcDetailsForm" property="postNpaReportFiles[0]" /></div>
								<div align="left" id="npa1">
							</div>								
							<div align="left" id="npa2">
							</div>
							<hr>
							<div align="left" style="height: 0pt;"><input
								type="button" value="Attach More Files" id="npa"
								style="display: inline; background: gray; text-align: center;"
								onclick="addMoreFiles(this.id)" /></div>
							</td>
						</tr>				
						<tr>
							<td class="TableData"><b>5.</b></td>
							<th class="TableData">Copy of suit filed <font color="red">(details indicating parties, suit no, amount claimed in the suit)*</font></th>
							<td class="HeadingBg"><html:file
								name="cpTcDetailsForm" property="suitReportFile" />
							
							</td>
						</tr>
						<tr>
							<td class="TableData"><b>6.</b></td>
							<th class="TableData">Copy of the final verdict of the suit
							filed cases, if any</th>
							<td class="HeadingBg"><html:file
								name="cpTcDetailsForm" property="finalVerdictFile" /></td>
						</tr>
						<tr>
							<td class="TableData"><b>7.</b></td>
							<th class="TableData">IT PAN, Voter ID copy of promoter /
							Proprietor (or any other KYC details)<font color="red">*</font></th>
							<td class="HeadingBg"><div align="left"><html:file
								name="cpTcDetailsForm" property="idProofFiles[0]" /></div>
								<div align="left" id="idproof1">
							</div>								
							<div align="left" id="idproof2">
							</div>
							<hr>
							<div align="left" style="height: 0pt;"><input
								type="button" value="Attach More Files" id="idproof"
								style="display: inline; background: gray; text-align: center;"
								onclick="addMoreFiles(this.id)" /></div>
							</td>
						</tr>
						<tr>
							<td class="TableData"><b>8.</b></td>
							<th class="TableData">Any other documents that can have
							bearing on the authenticity of the claim (i.e the projected
							balance sheet, P&L A/c, the income tax returns of the previous
							years)<font color="red">*</font></th>
							<td class="HeadingBg"><div align="left"><html:file
								name="cpTcDetailsForm" property="otherFiles[0]" /></div>
								<div align="left" id="other1">
							</div>								
							<div align="left" id="other2">
							</div>
							<hr>
							<div align="left" style="height: 0pt;"><input
								type="button" value="Attach More Files" id="other"
								style="display: inline; background: gray; text-align: center;"
								onclick="addMoreFiles(this.id)" /></div>
						</td>
						</tr>
						<% String flag = (String)request.getAttribute("QUICK_MARALITY"); %>
						<tr>
							<td class="TableData"><b>9.</b></td>
							<th class="TableData">Staff accountability report (in case
							of quick mortality case where NPA date is within one year from
							date of sanction/ renewal date)<font color="red">*</font></th>
							<td class="HeadingBg"><html:file
								name="cpTcDetailsForm" property="staffReportFile" />
							<input type="hidden" name="staff_flag" id="staff_flag" value="<%=flag %>"/>	
							</td>
						</tr>
					</table>
					<!-- new table -->
					<table width="100%">
						<tr>
							<th class="Heading"></th>
							<th class="Heading">
							<div align="center">CGPAN</div>
							</th>
							<% for (int i = 0; i < total_cgpans; i++) {
									String cg = (String) cgpans.get(i);
							%>
							<td class="Heading">&nbsp;<%=cg %></td>
							<%
								}
							%>
						</tr>
						<TR>
							<td class="TableData"><b>10.</b></td>
							<th class="TableData">Statement of account of borrower unit
							since beginning (from date of disbursement) till date<font
								color="red">*</font></th>
							<%
									String statementFiles = "";
									String statementFilesFlag = "";
							%>
							<% for (int i = 0; i < total_cgpans; i++) {
									statementFiles = "statementReportFiles(key-" + i + ")";
									statementFilesFlag = "statementReportFiles(key-" + i + "-flag)";									
							%>
							<td class="HeadingBg">
							
							<div align="center" style="height: 15pt;"></div>
							
							<hr>
							<div align="left" style="height: 10pt;"><html:file
								name="cpTcDetailsForm" property="<%=statementFiles %>" /></div>
							<div align="left" id="stmt1<%=i %>"></div>
							<div align="left" id="stmt2<%=i %>"></div>
							<hr>
							<div align="left" style="height: 10pt;"><input
								type="button" value="Attach More Files" id="<%=i %>"
								style="display: inline; background: gray; text-align: center;"
								onclick="addStatementFiles(this.id)" /></div>
							</td>
							<%
							}
							%>
						</tr>
						<TR>
							<td class="TableData"><b>11.</b></td>
							<th class="TableData">Copy of appraisal report prior to
							sanction<font color="red">*</font></th>
							<%
									String appraisalFiles = "";
									String appraisalFilesFlag = "";
							%>
							<% for (int i = 0; i < total_cgpans; i++) {
								appraisalFiles = "appraisalReportFiles(key-" + i + ")";
								appraisalFilesFlag = "appraisalReportFiles(key-" + i + "-flag)";									
							%>
							<td class="HeadingBg">&nbsp; <% if(i > 0){%>
							<div align="center" style="height: 10pt;">Same as
							earlier CGPAN&nbsp;&nbsp;<html:checkbox name="cpTcDetailsForm"
								property="<%=appraisalFilesFlag %>" onclick="changeState(this);" value="Y"></html:checkbox>
							</div>
							<%}else{ %>
							<div align="center" style="height: 15pt;"></div>
							<% }%>
							<hr>
							<div align="left" style="height: 10pt;"><html:file
								name="cpTcDetailsForm" property="<%=appraisalFiles %>"/></div>
							<div align="left" id="appr1<%=i %>"></div>
							<div align="left" id="appr2<%=i %>"></div>
							<hr>
							<div align="left" style="height: 10pt;"><input
								type="button" value="Attach More Files" id="<%=i %>"
								style="display: inline; background: gray; text-align: center;"
								onclick="addAppraisalFiles(this.id)" /></div>
							</td>
							<%
							}
							%>
						</tr>
						<TR>
							<td class="TableData"><b>12.</b></td>
							<th class="TableData">Copies of all sanction letters & all
							the amendments to the sanction letter<font color="red">*</font></th>
							<%
									String sanctionFiles = "";
									String sanctionFilesFlag = "";
							%>
							<% for (int i = 0; i < total_cgpans; i++) {
								sanctionFiles = "sanctionLetterFiles(key-" + i + ")";
								appraisalFilesFlag = "sanctionLetterFiles(key-" + i + "-flag)";									
							%>
							<td class="HeadingBg">&nbsp; <% if(i > 0){%>
							<div align="center" style="height: 10pt;">Same as
							earlier CGPAN&nbsp;&nbsp;<html:checkbox name="cpTcDetailsForm"
								property="<%=appraisalFilesFlag %>" onclick="changeState(this);" value="Y"></html:checkbox>
							</div>
							<%}else{ %>
							<div align="center" style="height: 15pt;"></div>
							<% }%>
							<hr>
							<div align="left" style="height: 10pt;"><html:file
								name="cpTcDetailsForm" property="<%=sanctionFiles %>" /></div>
							<div align="left" id="sanc1<%=i %>"></div>
							<div align="left" id="sanc2<%=i %>"></div>
							<hr>
							<div align="left" style="height: 10pt;"><input
								type="button" value="Attach More Files" id="<%=i %>"
								style="display: inline; background: gray; text-align: center;"
								onclick="addSanctionLetterFiles(this.id)" /></div>
							</td>
							<%
							}
							%>
						</tr>
						<TR>
							<td class="TableData"><b>13.</b></td>
							<th class="TableData">Compliance report on all sanction
							terms & conditions<font color="red">*</font></th>
							<%
									String complianceReportFiles = "";
									String complianceReportFilesFlag = "";
							%>
							<% for (int i = 0; i < total_cgpans; i++) {
								complianceReportFiles = "complianceReportFiles(key-" + i + ")";
								complianceReportFilesFlag = "complianceReportFiles(key-" + i + "-flag)";									
							%>
							<td class="HeadingBg">&nbsp; <% if(i > 0){%>
							<div align="center" style="height: 10pt;">Same as
							earlier CGPAN&nbsp;&nbsp;<html:checkbox name="cpTcDetailsForm"
								property="<%=complianceReportFilesFlag %>"
								onclick="changeState(this);" value="Y"></html:checkbox></div>
							<%}else{ %>
							<div align="center" style="height: 15pt;"></div>
							<% }%>
							<hr>
							<div align="left" style="height: 10pt;"><html:file
								name="cpTcDetailsForm" property="<%=complianceReportFiles %>" /></div>
							<div align="left" id="comp1<%=i %>"></div>
							<div align="left" id="comp2<%=i %>"></div>
							<hr>
							<div align="left" style="height: 10pt;"><input
								type="button" value="Attach More Files" id="<%=i %>"
								style="display: inline; background: gray; text-align: center;"
								onclick="addComplianceFiles(this.id)" /></div>
							</td>
							<%
							}
							%>
						</tr>
						<TR>
							<td class="TableData"><b>14.</b></td>
							<th class="TableData" width="50%">Pre disbursement
							visit/inspection report <font color="red"><b>(Scanned Copies to be furnished)*</b></font></th>
							<%
									String preInspectionReportFiles = "";
									String preInspectionReportFilesFlag = "";
							%>
							<% for (int i = 0; i < total_cgpans; i++) {
								preInspectionReportFiles = "preInspectionReportFiles(key-" + i + ")";
								preInspectionReportFilesFlag = "preInspectionReportFiles(key-" + i + "-flag)";									
							%>
							<td class="HeadingBg">&nbsp; <% if(i > 0){%>
							<div align="center" style="height: 10pt;">Same as
							earlier CGPAN&nbsp;&nbsp;<html:checkbox name="cpTcDetailsForm"
								property="<%=preInspectionReportFilesFlag %>"
								onclick="changeState(this);" value="Y"></html:checkbox></div>
							<%}else{ %>
							<div align="center" style="height: 15pt;"></div>
							<% }%>
							<hr>
							<div align="left" style="height: 10pt;"><html:file
								name="cpTcDetailsForm" property="<%=preInspectionReportFiles %>" /></div>
							<div align="left" id="preinspec1<%=i %>"></div>
							<div align="left" id="preinspec2<%=i %>"></div>
							<hr>
							<div align="left" style="height: 10pt;"><input
								type="button" value="Attach More Files" id="<%=i %>"
								style="display: inline; background: gray; text-align: center;"
								onclick="addPreInspectionFiles(this.id)" /></div>
							</td>
							<%
							}
							%>
						</tr>
						
						<tr>
							<th class="Heading"></th>
							<th class="Heading">
							<div align="center">CGPAN</div>
							</th>
							<% String cgs = "";
								for (int i = 0; i < total_cgpans; i++) {
									String cg = (String) cgpans.get(i);
									cgs = "cgpans(key-" + i + ")";
							%>
							<td class="Heading">&nbsp;<%=cg %>
							<html:hidden property="<%=cgs %>" name="cpTcDetailsForm"
							value="<%=cg %>" />
							</td>
							<%
								}
							%>
						</tr>
						
						<tr>
							<th class="TableData" width="2%">15.</th>
							<th class="TableData" width="50%">Amount of repayment before NPA date<font color="red">*</font></th>
							<%
									String repayBeforeNpaAmts = "";
									String repayBeforeNpaAmts2 = "";									
							%>
							<% for (int i = 0; i < total_cgpans; i++) {
								String cg = (String) cgpans.get(i);								
							%>
							<%repayBeforeNpaAmts = "repayBeforeNpaAmts(principal-key-"+i+")"; %>
							<%repayBeforeNpaAmts2 = "repayBeforeNpaAmts(interest-key-"+i+")"; %>
														
							<td class="HeadingBg">&nbsp;
							<table cellspacing="1" border="0">
							<tr>
							<th class="TableData" style="text-align: center;">Principal</th>
							<th class="TableData" style="text-align: center;">Interest</th>
							</tr>
							<tr>
							
							<%if(cg.endsWith("TC")){ %>
							<td><html:text name="cpTcDetailsForm"
							property="<%=repayBeforeNpaAmts%>"
							onkeypress="return decimalOnly(this, event,13)"
							onkeyup="isValidDecimal(this)" /></td>
							<td><html:text name="cpTcDetailsForm"
							property="<%=repayBeforeNpaAmts2%>"
							onkeypress="return decimalOnly(this, event,13)"
							onkeyup="isValidDecimal(this)" /></td>
							<%}else{ %>
							<td><input type="text" name="NA" value="Not Applicable" style="background-color: teal;text-align: center;" disabled="disabled"/></td>
							<td><input type="text" name="NA" value="Not Applicable" style="background-color: teal;text-align: center;" disabled="disabled"/></td>
							<%} %>
							
							</tr>
							</table>
							</td>							
							<%
							}
							%>
						</tr>
						
						<tr>
							<th class="TableData" width="2%">16.</th>
							<th class="TableData" width="50%">Amount of recovery after NPA date<font color="red">*</font></th>
							<%
									String recoveryAfterNpaAmts = "";
									String recoveryAfterNpaAmts2 = "";
							%>
							<% for (int i = 0; i < total_cgpans; i++) {																									
							%>
							<%recoveryAfterNpaAmts = "recoveryAfterNpaAmts(principal-key-"+i+")"; %>
							<%recoveryAfterNpaAmts2 = "recoveryAfterNpaAmts(interest-key-"+i+")"; %>
														
							<td class="HeadingBg">&nbsp;
							<table cellspacing="1" border="0">
							<tr>
							<th class="TableData" style="text-align: center;">Principal</th>
							<th class="TableData" style="text-align: center;">Interest</th>
							</tr>
							<tr>
							<td><html:text name="cpTcDetailsForm"
							property="<%=recoveryAfterNpaAmts%>"
							onkeypress="return decimalOnly(this, event,13)"
							onkeyup="isValidDecimal(this)" /></td>
							<td><html:text name="cpTcDetailsForm"
							property="<%=recoveryAfterNpaAmts2%>"
							onkeypress="return decimalOnly(this, event,13)"
							onkeyup="isValidDecimal(this)" /></td>
							</tr>
							</table>
							</td>
							
							<%
							}
							%>
						</tr>
						<tr>
							<th class="TableData" width="2%">17.</th>
							<th class="TableData" width="50%">Rate of Interest charged during the
								currency/tenure of loan(in %)<font color="red">*</font></th>
							<%
									String interestRates = "";
									
							%>
							<% for (int i = 0; i < total_cgpans; i++) {																									
							%>
							<%interestRates = "interestRates(key-"+i+")"; %>
							<td class="HeadingBg">&nbsp;<html:text name="cpTcDetailsForm"
							property="<%=interestRates%>"
							onkeypress="return decimalOnly(this, event,13)"
							onkeyup="isValidDecimal(this)" /></td>							
							<%
							}
							%>
						</tr>						
					</table>
					
					
					<!-- new table -->
					<table width="100%">
						<TR>
							<th class="TableData" width="1%">18.</th>
							<th class="TableData" width="60%">Indicate whichever is applicable
							<font color="red">*</font></th>
							<td class="HeadingBg" width="39%">
							&nbsp;<html:radio
							name="cpTcDetailsForm" property="bankRateType" value="P" onclick="enableRateField('P')">Bank PLR</html:radio>&nbsp;
							<html:text name="cpTcDetailsForm" property="plr" maxlength="5" size="10"onkeypress="return decimalOnly(this, event,13)"
							onkeyup="isValidDecimal(this)" disabled="true"></html:text>&nbsp;(%)
							
							&nbsp;<html:radio
							name="cpTcDetailsForm" property="bankRateType" value="R" onclick="enableRateField('R')">Base Rate</html:radio>&nbsp;
							<html:text name="cpTcDetailsForm" property="rate" maxlength="5"  size="10"onkeypress="return decimalOnly(this, event,13)"
							onkeyup="isValidDecimal(this)" disabled="true"></html:text>&nbsp;(%)
							</td>
						</TR>
						<TR>
							<th class="TableData" width="1%">19.</th>
							<th class="TableData" width="60%">Insurance copy of primary
							assets, if available<font color="red">*</font></th>
							<td class="HeadingBg" width="39%">&nbsp;
							<html:radio name="cpTcDetailsForm"
								property="insuranceFileFlag" value="Y" onclick="changeField('Y')">Yes</html:radio>
							&nbsp;<html:radio name="cpTcDetailsForm"
								property="insuranceFileFlag" value="N" onclick="changeField('N')">No</html:radio>
							</td>
						</TR>
					</table>	
					<!-- new table -->				
					<table width="100%">
						<tr>
							<th class="Heading"></th>
							<th class="Heading">
							<div align="center">CGPAN</div>
							</th>
							<% for (int i = 0; i < total_cgpans; i++) {
									String cg = (String) cgpans.get(i);
							%>
							<td class="Heading">&nbsp;<%=cg %></td>
							<%
								}
							%>
						</tr>
						<tr>
							<td class="TableData" width="2%"><b>19.1.</b></td>
							<th class="TableData" width="50%">Please attach the insurance
							copy<font color="red">*</font></th>
							<%
									String insuranceCopyFiles = "";
									String insuranceCopyFilesFlag = "";
							%>
							<% for (int i = 0; i < total_cgpans; i++) {
								insuranceCopyFiles = "insuranceCopyFiles(key-" + i + ")";
								insuranceCopyFilesFlag = "insuranceCopyFiles(key-" + i + "-flag)";									
							%>
							<td class="HeadingBg">&nbsp; <% if(i > 0){%>
							<div align="center" style="height: 10pt;">Same as
							earlier CGPAN&nbsp;&nbsp;<html:checkbox name="cpTcDetailsForm"
								property="<%=insuranceCopyFilesFlag %>"
								onclick="changeState(this);"  value="Y" disabled="true"></html:checkbox></div>
							<%}else{ %>
							<div align="center" style="height: 15pt;"></div>
							<% }%>
							<hr>
							<div align="left" style="height: 10pt;"><html:file
								name="cpTcDetailsForm" property="<%=insuranceCopyFiles %>" disabled="true"/></div>
							<div align="left" id="insurance1<%=i %>"></div>
							<div align="left" id="insurance2<%=i %>"></div>
							<hr>
							<div align="left" style="height: 10pt;"><input
								type="button" value="Attach More Files" id="<%=i %>"
								style="display: inline; background: gray; text-align: center;"
								onclick="addInsuranceFiles(this.id)" /></div>
							</td>
							<%
							}
							%>
						</tr>
					</table>
					<!-- new table -->
					<table width="100%">
						<tr>
							<th class="TableData" width="3%">19.2.</th>
							<th class="TableData" width="58%">Provide reason for non
							availability of insurance copy<font color="red">*</font></th>
							<td class="HeadingBg" width="39%">&nbsp;<html:textarea
								name="cpTcDetailsForm" property="insuranceReason" rows="4"
								cols="60" disabled="true" /></td>
						</tr>
						<tr>
							<th class="TableData" width="3%">20.</th>
							<th class="TableData" width="58%">Status of security with detailed explanation
							 ( whether it is in the custody of bank or sold etc)<font color="red">*</font></th>
							<td class="HeadingBg" width="39%">&nbsp;<html:textarea
								name="cpTcDetailsForm" property="securityRemarks" rows="4"
								cols="60"/></td>
						</tr>
						<tr>
							<th class="TableData" width="3%">21.</th>
							<th class="TableData" width="58%">Recovery efforts made by bank after NPA - brief note<font color="red">*</font></th>
							<td class="HeadingBg" width="39%">&nbsp;<html:textarea
								name="cpTcDetailsForm" property="recoveryEffortsTaken" rows="4"
								cols="60"/></td>
						</tr>
						<tr>
							<th class="TableData" width="3%">22.</th>
							<th class="TableData" width="58%">Specify Internal rating assigned (if any) to the case (mandatory for cases above 50 lakh)<font color="red">*</font></th>
							<td class="HeadingBg" width="39%">&nbsp;<html:text 
								name="cpTcDetailsForm" property="rating"/></td>
						</tr>
						<tr>
							<th class="TableData" width="3%">23.</th>
							<th class="TableData" width="58%">Attachment for Internal rating assigned (if any) to the case (mandatory for cases above 50 lakh)<font color="red">*</font></th>
							<td class="HeadingBg" width="39%"><html:file
								name="cpTcDetailsForm" property="internalRatingFile"/></td>
						</tr>
						<tr>
							<th class="TableData" width="3%">24.</th>
							<th class="TableData" width="58%">Branch Address<font color="red">*</font></th>
							<td class="HeadingBg" width="39%">&nbsp;<html:textarea
								name="cpTcDetailsForm" property="branchAddress" rows="4"
								cols="60"/></td>
						</tr>
						<tr>
							<th class="TableData" width="3%">25.</th>
							<th class="TableData" width="58%">Whether the above internal rating is of investment grade<font color="red">*</font></th>
							<td class="HeadingBg" width="39%">&nbsp;<html:radio name="cpTcDetailsForm"
								property="investmentGradeFlag" value="Y">Yes</html:radio>
							&nbsp;<html:radio name="cpTcDetailsForm"
								property="investmentGradeFlag" value="N">No</html:radio></td>
						</tr>
					</table>
					
					</td>
				</tr>
			</table>
			<TABLE width="925">
				<TR>
					<TD height="20">&nbsp;</TD>
				</TR>
				<TR>
					<TD align="center" valign="baseline">
					<DIV align="center"><A href="javascript:history.back();">
					<IMG src="images/Back.gif" alt="Back" width="49" height="37"
						border="0"></A> <A href="#" onclick="uploadClaimFiles();"> <IMG
						src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>
					</DIV>
					</TD>
				</TR>
			</TABLE>
			</td>
			<td width="20" background="images/TableVerticalRightBG.gif">&nbsp;</td>
		</tr>
		<tr>
			<td width="20" align="right" valign="top"><img
				src="images/TableLeftBottom1.gif" width="20" height="15"></img></td>
			<td background="images/TableBackground2.gif">&nbsp;</td>
			<td width="20" align="left" valign="top"><img
				src="images/TableRightBottom1.gif" width="23" height="15"></img></td>
		</tr>
	</html:form>
</table>
<script type="text/javascript">
var stmt_id_array = [];
var appr_id_array = [];
var sanc_id_array = [];
var comp_id_array = [];
var preinspec_id_array = [];
var insurance_id_array = [];
var id_array = [];

	function uploadClaimFiles(){

		var npaFile = document.forms[0].npaReportFile;
		var diligenceReportFile = document.forms[0].diligenceReportFile;
		var postInspectionReportFile = document.forms[0].postInspectionReportFile;
		var postNpaReportFile = document.forms[0].postNpaReportFile;
		var insuranceFileFlag = document.forms[0].insuranceFileFlag;
		var insuranceReason = document.forms[0].insuranceReason;
		var suitReportFile = document.forms[0].suitReportFile;
		var finalVerdictFile = document.forms[0].finalVerdictFile;
		var idProofFile = document.forms[0].idProofFile;
		var otherFile = document.forms[0].otherFile;
		var staffReportFile = document.forms[0].staffReportFile;
		var bankRateType = document.forms[0].bankRateType;
		var plr = document.forms[0].plr;
		var rate = document.forms[0].rate;
		var securityRemarks = document.forms[0].securityRemarks;
		var recoveryEffortsTaken = document.forms[0].recoveryEffortsTaken;
		var rating = document.forms[0].rating.value;
		var internalRatingFile = document.forms[0].internalRatingFile;
		var branchAddress = document.forms[0].branchAddress;
		var investmentGradeFlag = document.forms[0].investmentGradeFlag;

		var diligenceReportFiles = findObj('diligenceReportFiles[0]');
		var postInspectionReportFiles = findObj('postInspectionReportFiles[0]');
		var postNpaReportFiles = findObj('postNpaReportFiles[0]');
		var idProofFiles = findObj('idProofFiles[0]');
		var otherFiles = findObj('otherFiles[0]');
		
		if(npaFile.value == ''){
			alert('Please attach file at point 1.');
			return false;
		}else if(!validateFileExtension(npaFile.value)){
			alert('File format should be JPEG or PNG or DOC or DOCX or PDF at point 1.'); return false;}
		//else if(validateFileSize(npaFile)){alert('File format should be JPEG or PNG or DOC or DOCX or PDF at point 1.'); return false;}
		
		if(diligenceReportFiles.value == ''){
			alert('Please attach file at point 2.');
			return false;
		}else if(!validateFileExtension(diligenceReportFiles.value)){
			alert('File format should be JPEG or PNG or DOC or DOCX or PDF at point 2.'); return false;}
		else if(!validateFileSize(diligenceReportFiles)){alert('File format should be JPEG or PNG or DOC or DOCX or PDF at point 1.'); return false;}
		
		if(postInspectionReportFiles.value == ''){
			alert('Please attach file at point 3.');
			return false;
		}else if(!validateFileExtension(postInspectionReportFiles.value)){
			alert('File format should be JPEG or PNG or DOC or DOCX or PDF at point 3.'); return false;}
		else if(!validateFileSize(postInspectionReportFiles)){alert('File format should be JPEG or PNG or DOC or DOCX or PDF at point 1.'); return false;}
		
		if(postNpaReportFiles.value == ''){
			alert('Please attach file at point 4.');
			return false;
		}else if(!validateFileExtension(postNpaReportFiles.value)){
			alert('File format should be JPEG or PNG or DOC or DOCX or PDF at point 4.'); return false;}
		else if(!validateFileSize(postNpaReportFiles)){alert('File format should be JPEG or PNG or DOC or DOCX or PDF at point 1.'); return false;}
		
		if(suitReportFile.value == ''){
			alert('Please attach file at point 5.');
			return false;
		}else if(!validateFileExtension(suitReportFile.value)){
			alert('File format should be JPEG or PNG or DOC or DOCX or PDF at point 5.'); return false;}
		else if(!validateFileSize(suitReportFile)){alert('File format should be JPEG or PNG or DOC or DOCX or PDF at point 1.'); return false;}
		
		if(finalVerdictFile.value != ''){
			if(!validateFileExtension(finalVerdictFile.value)){
			alert('File format should be JPEG or PNG or DOC or DOCX or PDF at point 6.'); return false;}
		else if(!validateFileSize(finalVerdictFile)){alert('File format should be JPEG or PNG or DOC or DOCX or PDF at point 1.'); return false;}
		}
		
		if(idProofFiles.value == ''){
			alert('Please attach file at point 7.');
			return false;
		}else if(!validateFileExtension(idProofFiles.value)){
			alert('File format should be JPEG or PNG or DOC or DOCX or PDF at point 7.'); return false;}
		else if(!validateFileSize(idProofFiles)){alert('File format should be JPEG or PNG or DOC or DOCX or PDF at point 1.'); return false;}
		
		if(otherFiles.value == ''){
			alert('Please attach file at point 8.');
			return false;
		}else if(!validateFileExtension(otherFiles.value)){
			alert('File format should be JPEG or PNG or DOC or DOCX or PDF at point 8.'); return false;}
		else if(!validateFileSize(otherFiles)){alert('File format should be JPEG or PNG or DOC or DOCX or PDF at point 1.'); return false;}

		var staff_flag = document.getElementById('staff_flag').value;
		if('Y' === staff_flag){
			if(staffReportFile.value == ''){
				alert('Please attach file at point 9.');
				return false;
			}else if(!validateFileExtension(staffReportFile.value)){
				alert('File format should be JPEG or PNG or DOC or DOCX or PDF at point 9.'); return false;}
			else if(!validateFileSize(staffReportFile)){alert('File format should be JPEG or PNG or DOC or DOCX or PDF at point 1.'); return false;}
		}else if(staffReportFile.value != '' && ('N' === staff_flag)){
			if(!validateFileExtension(staffReportFile.value)){
				alert('File format should be JPEG or PNG or DOC or DOCX or PDF at point 9.'); return false;}
		}		
		//alert('stage1');
/*cgpan wise files*/
		var total = document.getElementById('total_cgpans').value;

		for(var i = 0; i < total; i++){
			var id = '';
			var flag = false;
			
						
				var id2 = 'statementReportFiles(key-' + i + ')';				
				var field = findObj(id2);
				if(field.value == ''){
					alert('Please attch file at point 10.');
					
					return false;
				}else if(!validateFileExtension(field.value)){
					alert('File format should be JPEG or PNG or DOC or DOCX or PDF at point 10.'); return false;}
				
					var count = 1;
					for(var j = 0; j<stmt_id_array.length; j++){
						if(stmt_id_array[j] == i){
							var id3 = 'statementReportFiles(key-'+ i + count + ')';
							if(findObj(id3).value != ''){
								if(!validateFileExtension(findObj(id3).value)){
									alert('File format should be JPEG or PNG or DOC or DOCX or PDF at point 10.'); return false;}
							}
							count++;
						}
					}
				
			
		}
		for(var i = 0; i < total; i++){
			var id = '';
			var flag = false;
			if( i > 0){				
				id = 'appraisalReportFiles(key-' + i + '-flag)';
				flag = findObj(id).checked;
			}
			if(!flag){				
				var id2 = 'appraisalReportFiles(key-' + i + ')';				
				var field = findObj(id2);
				if(field.value == ''){
					alert('Please attch file at point 11.');
					
					return false;
				}else if(!validateFileExtension(field.value)){
					alert('File format should be JPEG or PNG or DOC or DOCX or PDF at point 11.'); return false;}
					
					var count = 1;
					for(var j = 0; j<appr_id_array.length; j++){
						if(appr_id_array[j] == i){
							var id3 = 'appraisalReportFiles(key-'+ i + count + ')';
							if(findObj(id3).value != ''){
								if(!validateFileExtension(findObj(id3).value)){
									alert('File format should be JPEG or PNG or DOC or DOCX or PDF at point 11.'); return false;}
							}
							count++;
						}
					}
				
			}
		}
		for(var i = 0; i < total; i++){
			var id = '';
			var flag = false;
			if( i > 0){				
				id = 'sanctionLetterFiles(key-' + i + '-flag)';
				flag = findObj(id).checked;
			}
			if(!flag){				
				var id2 = 'sanctionLetterFiles(key-' + i + ')';				
				var field = findObj(id2);
				if(field.value == ''){
					alert('Please attch file at point 12.');
					
					return false;
				}else if(!validateFileExtension(field.value)){
					alert('File format should be JPEG or PNG or DOC or DOCX or PDF at point 12.'); return false;}
					var count = 1;
					//alert(stmt_id_array.length);
					for(var j = 0; j<sanc_id_array.length; j++){
						if(sanc_id_array[j] == i){
							var id3 = 'sanctionLetterFiles(key-'+ i + count + ')';
							if(findObj(id3).value != ''){
								if(!validateFileExtension(findObj(id3).value)){
									alert('File format should be JPEG or PNG or DOC or DOCX or PDF at point 12.'); return false;}
							}
							count++;
						}
					}
				
			}
		}
		for(var i = 0; i < total; i++){
			var id = '';
			var flag = false;
			if( i > 0){				
				id = 'complianceReportFiles(key-' + i + '-flag)';
				flag = findObj(id).checked;
			}
			if(!flag){				
				var id2 = 'complianceReportFiles(key-' + i + ')';				
				var field = findObj(id2);
				if(field.value == ''){
					alert('Please attch file at point 13.');
					
					return false;
				}else if(!validateFileExtension(field.value)){
					alert('File format should be JPEG or PNG or DOC or DOCX or PDF at point 13.'); return false;}
					var count = 1;
					//alert(stmt_id_array.length);
					for(var j = 0; j<comp_id_array.length; j++){
						if(comp_id_array[j] == i){
							var id3 = 'complianceReportFiles(key-'+ i + count + ')';
							if(findObj(id3).value != ''){
								if(!validateFileExtension(findObj(id3).value)){
									alert('File format should be JPEG or PNG or DOC or DOCX or PDF at point 13.'); return false;}
							}
							count++;
						}
					}
				
			}
		}
		for(var i = 0; i < total; i++){
			var id = '';
			var flag = false;
			if( i > 0){				
				id = 'preInspectionReportFiles(key-' + i + '-flag)';
				flag = findObj(id).checked;
			}
			if(!flag){				
				var id2 = 'preInspectionReportFiles(key-' + i + ')';				
				var field = findObj(id2);
				if(field.value == ''){
					alert('Please attch file at point 14.');
					
					return false;
				}else if(!validateFileExtension(field.value)){
					alert('File format should be JPEG or PNG or DOC or DOCX or PDF at point 14.'); return false;}
					var count = 1;
					//alert(stmt_id_array.length);
					for(var j = 0; j<preinspec_id_array.length; j++){
						if(preinspec_id_array[j] == i){
							var id3 = 'preInspectionReportFiles(key-'+ i + count + ')';
							if(findObj(id3).value != ''){
								if(!validateFileExtension(findObj(id3).value)){
									alert('File format should be JPEG or PNG or DOC or DOCX or PDF at point 14.'); return false;}
							}
							count++;
						}
					}
				
			}
		}
		for(var i = 0;i<total; i++){
			var id = 'cgpans(key-'+i+')';			
			var cg = findObj(id);
			var len = cg.value.length;			
			if(cg.value.substr(len-2,len) === 'TC'){
				id = 'repayBeforeNpaAmts(principal-key-' + i + ')';
				field = findObj(id);
				if(field.value < 0 || field.value === '' || field.value === '.' || field.value === '0.' || field.value === '.0'){
					alert('Please enter valid repayment amount.');
				return false;}
				id = 'repayBeforeNpaAmts(interest-key-' + i + ')';
				field = findObj(id);
				if(field.value < 0 || field.value === '' || field.value === '.' || field.value === '0.' || field.value === '.0'){
					alert('Please enter valid repayment amount.');
				return false;}
			}
			id = 'recoveryAfterNpaAmts(principal-key-' + i + ')';
			field = findObj(id);
			if(field.value < 0 || field.value === '' || field.value === '.' || field.value === '0.' || field.value === '.0'){
				alert('Please enter valid recovery amount.');
			return false;}
			id = 'recoveryAfterNpaAmts(interest-key-' + i + ')';
			field = findObj(id);
			if(field.value < 0 || field.value === '' || field.value === '.' || field.value === '0.' || field.value === '.0'){
				alert('Please enter valid recovery amount.');
			return false;}
			id = 'interestRates(key-' + i + ')';
			field = findObj(id);
			if(field.value < 1 || field.value === '' || field.value === '.' || field.value === '0.' || field.value === '.0'){
				alert('Please enter valid interest rate.');
			return false;}	
		}
		//alert('statge2');
		if(bankRateType[0].checked){
			if(plr.value < 0 || plr.value === '.' || plr.value === '.0' || plr.value === '0.' || plr.value === '0.0' || plr.value === '0'){
				alert('Please enter Bank PLR.');				
				return false;
			}			
		}else if(bankRateType[1].checked){
			if(rate.value < 0 || rate.value === '.' || rate.value === '.0' || rate.value === '0.'|| rate.value === '0.0'  || rate.value === '0'){
				alert('Please enter Base Rate.');				
				return false;
			}
		}else{
			alert('Please choose bank rate type at point 18.');
			
			return false;
		}
		if(insuranceFileFlag[0].checked){
			for(var i = 0; i < total; i++){
				var id = '';
				var flag = false;
				if( i > 0){				
					id = 'insuranceCopyFiles(key-' + i + '-flag)';
					flag = findObj(id).checked;
				}
				if(!flag){				
					var id2 = 'insuranceCopyFiles(key-' + i + ')';				
					var field = findObj(id2);
					if(field.value == ''){
						alert('Please attch file at point 19.1.');
						
						return false;
					}else if(!validateFileExtension(field.value)){
						alert('File format should be JPEG or PNG or DOC or DOCX or PDF at point 19.1.'); return false;}
						var count = 1;
						//alert(stmt_id_array.length);
						for(var j = 0; j<preinspec_id_array.length; j++){
							if(preinspec_id_array[j] == i){
								var id3 = 'insuranceCopyFiles(key-'+ i + count + ')';
								if(findObj(id3).value != ''){
									if(!validateFileExtension(findObj(id3).value)){
										alert('File format should be JPEG or PNG or DOC or DOCX or PDF at point 19.1.'); return false;}
								}
								count++;
							}
						}
					
				}
			}			
		}else if(insuranceFileFlag[1].checked){
			if(insuranceReason.value === '' || insuranceReason.value === '.' || insuranceReason.value.length < 10){
				alert('Please enter reason for non avalability of insurance copy at point 19.2.');
				
				return false;
			}
		}else{
			alert('Please choose any one option at point 19.');
			
			return false;
		}
		//alert('stage3');
		if(securityRemarks.value == '' || securityRemarks.value.length < 10){
			alert('Please remark on security at point 20.');
			
			return false;
		}	
		//alert('stage3');
		if(recoveryEffortsTaken.value == '' || recoveryEffortsTaken.value.length < 10){
			alert('Please remark on efforts taken on recovery at point 21.');
			
			return false;
		}
		var totAmt = document.getElementById('total_amount').value;
		if( parseFloat(totAmt) > 5000000.0 && (rating == '' || rating == '.' || rating == '0.' || rating == '.0' || rating == '0.0')){
			alert('Rating is required at point 22.');
				return false;
		}
		if( parseFloat(totAmt) > 5000000.0){
			if(internalRatingFile.value == ''){
				alert('Please attach file at point 23.');
				return false;
			}else if(!validateFileExtension(internalRatingFile.value)){
				alert('File format should be JPEG or PNG or DOC or DOCX or PDF at point 23.'); return false;
			}
		}else{
			if(internalRatingFile.value != ''){
				if(!validateFileExtension(internalRatingFile.value)){
					alert('File format should be JPEG or PNG or DOC or DOCX or PDF at point 23.'); return false;
				}
			}
		}
		
		if(branchAddress.value == '' || branchAddress.value.length < 10){
			alert('Please enter complete bank address at point 24.');
			
			return false;
		}
		
		if(parseFloat(totAmt) > 5000000.0 && (investmentGradeFlag.value == '' || (!(investmentGradeFlag[0].checked) && !(investmentGradeFlag[1].checked)))){
			alert('Please choose any one option at point 25.');
			return false;
		}
		
		//alert('-----going----');
		document.cpTcDetailsForm.target ="_self";
	    document.cpTcDetailsForm.method="POST";
	    document.cpTcDetailsForm.action="uploadClaimFiles.do?method=uploadClaimFiles";
	    document.cpTcDetailsForm.submit();    
	}

	function validateFileSize(file){
		//alert(file.size);
		
		return true;
	}
	function validateFileExtension(file){
		if(file == ''){
			return true;
		}
		var ext = file.split('.');
		//alert(ext[1]);
		var extensions = ['jpeg','png','doc','pdf','jpg','docx','JPEG','PNG','DOC','PDF','JPG','DOCX'];
		for(var j=0; j<extensions.length;j++){
			if(ext[1] === extensions[j]){
				return true;
			}
		}
		return false;
	}
	
	function changeField(flag){
		var total = document.getElementById('total_cgpans').value;
		var field = document.forms[0].insuranceReason;
		if(flag === 'Y'){
			for(var i = 0; i< total;i++){
				var id = '';
				var flag = false;
				if( i > 0){				
					id = 'insuranceCopyFiles(key-' + i + '-flag)';
					findObj(id).disabled = false;
				}							
					var id2 = 'insuranceCopyFiles(key-' + i + ')';				
					var field = findObj(id2);
					field.disabled = false;
					var count = 1;
					//alert(stmt_id_array.length);
					for(var j = 0; j<insurance_id_array.length; j++){
						if(insurance_id_array[j] == i){
							var id3 = 'insuranceCopyFiles(key-'+ i + count + ')';
							findObj(id3).disabled = false;
							count++;
						}
					}				
			}
			document.forms[0].insuranceReason.disabled = true;
			document.forms[0].insuranceReason.value = '';
		}
		if(flag === 'N'){			
			for(var i = 0; i< total;i++){
				var id = '';
				var flag = false;
				if( i > 0){				
					id = 'insuranceCopyFiles(key-' + i + '-flag)';
					findObj(id).disabled = true;
					findObj(id).checked = false;
				}						
					var id2 = 'insuranceCopyFiles(key-' + i + ')';				
					var field = findObj(id2);
					field.disabled = true;
					var count = 1;
					//alert(stmt_id_array.length);
					for(var j = 0; j<insurance_id_array.length; j++){
						if(insurance_id_array[j] == i){
							var id3 = 'insuranceCopyFiles(key-'+ i + count + ')';
							findObj(id3).disabled = true;
							count++;
						}
					}				
			}
			document.forms[0].insuranceReason.disabled = false;			
		}
	}
	
	function changeState(field){
		var index = field.name.indexOf('-');
		//alert(index);
		var key = field.name.substr(0,index+2) + ')';
		if(field.checked){
			findObj(key).disabled = true;
		}else{
			findObj(key).disabled = false;
		}
	}


	function addMoreFiles(id){
		var count = 1;
		for(var i = 0; i<id_array.length; i++){					
			if(id == id_array[i]){
				count++;
			}
		}
		var id1 = id+'1';
		var id2 = id+'2';

		if(id === 'other'){						
			if(count == 1){					
				var elem = '<html:file name="cpTcDetailsForm" property="otherFiles[1]" />';
				
				document.getElementById(id1).innerHTML = 
					document.getElementById(id1).innerHTML + elem;
				id_array[id_array.length] = id;
			}else
			if(count == 2){
				var elem = '<html:file name="cpTcDetailsForm" property="otherFiles[2]" />';
				//alert(elem);
				document.getElementById(id2).innerHTML = 
					document.getElementById(id2).innerHTML + elem;
				id_array[id_array.length] = id;					
			}else{
				alert('Maximum two additional files can be attached.');			
			}			
		}
		if(id === 'idproof'){						
			if(count == 1){					
				var elem = '<html:file name="cpTcDetailsForm" property="idProofFiles[1]" />';
				
				document.getElementById(id1).innerHTML = 
					document.getElementById(id1).innerHTML + elem;
				id_array[id_array.length] = id;
			}else
			if(count == 2){
				var elem = '<html:file name="cpTcDetailsForm" property="idProofFiles[2]" />';
				//alert(elem);
				document.getElementById(id2).innerHTML = 
					document.getElementById(id2).innerHTML + elem;
				id_array[id_array.length] = id;					
			}else{
				alert('Maximum two additional files can be attached.');			
			}			
		}
		if(id === 'npa'){						
			if(count == 1){					
				var elem = '<html:file name="cpTcDetailsForm" property="postNpaReportFiles[1]" />';
				
				document.getElementById(id1).innerHTML = 
					document.getElementById(id1).innerHTML + elem;
				id_array[id_array.length] = id;
			}else
			if(count == 2){
				var elem = '<html:file name="cpTcDetailsForm" property="postNpaReportFiles[2]" />';
				//alert(elem);
				document.getElementById(id2).innerHTML = 
					document.getElementById(id2).innerHTML + elem;
				id_array[id_array.length] = id;					
			}else{
				alert('Maximum two additional files can be attached.');			
			}			
		}
		if(id === 'inspec'){						
			if(count == 1){					
				var elem = '<html:file name="cpTcDetailsForm" property="postInspectionReportFiles[1]" />';
				
				document.getElementById(id1).innerHTML = 
					document.getElementById(id1).innerHTML + elem;
				id_array[id_array.length] = id;
			}else
			if(count == 2){
				var elem = '<html:file name="cpTcDetailsForm" property="postInspectionReportFiles[2]" />';
				//alert(elem);
				document.getElementById(id2).innerHTML = 
					document.getElementById(id2).innerHTML + elem;
				id_array[id_array.length] = id;					
			}else{
				alert('Maximum two additional files can be attached.');			
			}			
		}
		if(id === 'dili'){						
			if(count == 1){					
				var elem = '<html:file name="cpTcDetailsForm" property="diligenceReportFiles[1]" />';
				
				document.getElementById(id1).innerHTML = 
					document.getElementById(id1).innerHTML + elem;
				id_array[id_array.length] = id;
			}else
			if(count == 2){
				var elem = '<html:file name="cpTcDetailsForm" property="diligenceReportFiles[2]" />';
				//alert(elem);
				document.getElementById(id2).innerHTML = 
					document.getElementById(id2).innerHTML + elem;
				id_array[id_array.length] = id;					
			}else{
				alert('Maximum two additional files can be attached.');			
			}			
		}
		
	}	
	
	function addInsuranceFiles(id){
		var insuranceFileFlag = document.forms[0].insuranceFileFlag;
		if(insuranceFileFlag[0].checked){
			var count = 1;
			for(var i = 0; i<insurance_id_array.length; i++){
				
				if(id == insurance_id_array[i]){
					count++;
				}
			}
			var flag = 'N';
			if(id > 0){
				var key = 'insuranceCopyFiles(key-' + id + '-flag)';
				//alert(key);
				 flag = findObj(key);			
			}
			var id1 = 'insurance1' + id;
			var id2 = 'insurance2' + id;
			if(id == 0 || (id > 0 && !(flag.checked))){			
				if(count == 1){					
					var elem = '<html:file name="cpTcDetailsForm" property="insuranceCopyFiles(key-'+id+'1)" />';
					//alert(elem);
					
					document.getElementById(id1).innerHTML = 
						document.getElementById(id1).innerHTML + elem;
					insurance_id_array[insurance_id_array.length] = id;	
				}else
				if(count == 2){
					var elem = '<html:file name="cpTcDetailsForm" property="insuranceCopyFiles(key-' +id+ '2)" />';
					//alert(elem);
					document.getElementById(id2).innerHTML = 
						document.getElementById(id2).innerHTML + elem;
					insurance_id_array[insurance_id_array.length] = id;				
				}else{
					alert('Maximum two additional files can be attached.');			
				}
			}
		}
	}
	
	function addPreInspectionFiles(id){
		var count = 1;
		for(var i = 0; i<preinspec_id_array.length; i++){
			
			if(id == preinspec_id_array[i]){
				count++;
			}
		}
		var flag = 'N';
		if(id > 0){
			var key = 'preInspectionReportFiles(key-' + id + '-flag)';
			//alert(key);
			 flag = findObj(key);			
		}
		var id1 = 'preinspec1' + id;
		var id2 = 'preinspec2' + id;
		if(id == 0 || (id > 0 && !(flag.checked))){			
			if(count == 1){					
				var elem = '<html:file name="cpTcDetailsForm" property="preInspectionReportFiles(key-'+id+'1)" />';
				//alert(elem);
				
				document.getElementById(id1).innerHTML = 
					document.getElementById(id1).innerHTML + elem;
				preinspec_id_array[preinspec_id_array.length] = id;	
			}else
			if(count == 2){
				var elem = '<html:file name="cpTcDetailsForm" property="preInspectionReportFiles(key-' +id+ '2)" />';
				//alert(elem);
				document.getElementById(id2).innerHTML = 
					document.getElementById(id2).innerHTML + elem;
				preinspec_id_array[preinspec_id_array.length] = id;				
			}else{
				alert('Maximum two additional files can be attached.');			
			}
		}
	}
	function addComplianceFiles(id){
		var count = 1;
		for(var i = 0; i<comp_id_array.length; i++){			
			if(id == comp_id_array[i]){
				count++;
			}
		}
		var flag = 'N';
		if(id > 0){
			var key = 'complianceReportFiles(key-' + id + '-flag)';
			//alert(key);
			 flag = findObj(key);			
		}
		var id1 = 'comp1' + id;
		var id2 = 'comp2' + id;
		if(id == 0 || (id > 0 && !(flag.checked))){			
			if(count == 1){					
				var elem = '<html:file name="cpTcDetailsForm" property="complianceReportFiles(key-'+id+'1)" />';
				//alert(elem);			
				document.getElementById(id1).innerHTML = 
					document.getElementById(id1).innerHTML + elem;
				comp_id_array[comp_id_array.length] = id;	
			}else
			if(count == 2){
				var elem = '<html:file name="cpTcDetailsForm" property="complianceReportFiles(key-' +id+ '2)" />';
				//alert(elem);
				document.getElementById(id2).innerHTML = 
					document.getElementById(id2).innerHTML + elem;
				comp_id_array[comp_id_array.length] = id;				
			}else{
				alert('Maximum two additional files can be attached.');			
			}
		}
	}
	function addSanctionLetterFiles(id){
		var count = 1;
		for(var i = 0; i<sanc_id_array.length; i++){			
			if(id == sanc_id_array[i]){
				count++;
			}
		}
		var flag = 'N';
		if(id > 0){
			var key = 'sanctionLetterFiles(key-' + id + '-flag)';
			//alert(key);
			 flag = findObj(key);			
		}
		var id1 = 'sanc1' + id;
		var id2 = 'sanc2' + id;
		if(id == 0 || (id > 0 && !(flag.checked))){			
			if(count == 1){					
				var elem = '<html:file name="cpTcDetailsForm" property="sanctionLetterFiles(key-'+id+'1)" />';
				//alert(elem);				
				document.getElementById(id1).innerHTML = 
					document.getElementById(id1).innerHTML + elem;
				sanc_id_array[sanc_id_array.length] = id;	
			}else
			if(count == 2){
				var elem = '<html:file name="cpTcDetailsForm" property="sanctionLetterFiles(key-' +id+ '2)" />';
				//alert(elem);
				document.getElementById(id2).innerHTML = 
					document.getElementById(id2).innerHTML + elem;
				sanc_id_array[sanc_id_array.length] = id;				
			}else{
				alert('Maximum two additional files can be attached.');			
			}
		}
	}
	function addAppraisalFiles(id){
		var count = 1;
		for(var i = 0; i<appr_id_array.length; i++){			
			if(id == appr_id_array[i]){
				count++;
			}
		}
		var flag = 'N';
		if(id > 0){
			var key = 'appraisalReportFiles(key-' + id + '-flag)';
			//alert(key);
			 flag = findObj(key);			
		}
		var id1 = 'appr1' + id;
		var id2 = 'appr2' + id;
		if(id == 0 || (id > 0 && !(flag.checked))){			
			if(count == 1){					
				var elem = '<html:file name="cpTcDetailsForm" property="appraisalReportFiles(key-'+id+'1)" />';
				//alert(elem);				
				document.getElementById(id1).innerHTML = 
					document.getElementById(id1).innerHTML + elem;
				appr_id_array[appr_id_array.length] = id;	
			}else
			if(count == 2){
				var elem = '<html:file name="cpTcDetailsForm" property="appraisalReportFiles(key-' +id+ '2)" />';
				//alert(elem);
				document.getElementById(id2).innerHTML = 
					document.getElementById(id2).innerHTML + elem;
				appr_id_array[appr_id_array.length] = id;				
			}else{
				alert('Maximum two additional files can be attached.');			
			}
		}
	}
	function addStatementFiles(id){
		var count = 1;
		for(var i = 0; i<stmt_id_array.length; i++){			
			if(id == stmt_id_array[i]){
				count++;
			}
		}
		
		var id1 = 'stmt1' + id;
		var id2 = 'stmt2' + id;		
			if(count == 1){					
				var elem = '<html:file name="cpTcDetailsForm" property="statementReportFiles(key-'+id+'1)" />';
				//alert(elem);				
				document.getElementById(id1).innerHTML = 
					document.getElementById(id1).innerHTML + elem;
				stmt_id_array[stmt_id_array.length] = id;	
			}else
			if(count == 2){
				var elem = '<html:file name="cpTcDetailsForm" property="statementReportFiles(key-' +id+ '2)" />';
				//alert(elem);
				document.getElementById(id2).innerHTML = 
					document.getElementById(id2).innerHTML + elem;
				stmt_id_array[stmt_id_array.length] = id;				
			}else{
				alert('Maximum two additional files can be attached.');			
			}
		
	}
	function enableRateField(rateType){
		if(rateType === 'P'){
			document.forms[0].plr.disabled = false;
			document.forms[0].rate.disabled = true;
			document.forms[0].rate.value = '0.0';
		}
		if(rateType === 'R'){
			document.forms[0].rate.disabled = false;
			document.forms[0].plr.disabled = true;
			document.forms[0].plr.value = '0.0';
		}
	}
</script>
</body>
</HTML>