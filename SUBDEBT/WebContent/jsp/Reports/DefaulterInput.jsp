
<%@ page language ="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>



<% session.setAttribute("CurrentPage", "showDefaulterInput.do?method=showDefaulterInput");%>
<BODY onLoad="setToDefault()">
<TABLE width="725" border="0" cellspacing="0" cellpadding="0">
<html:errors />
<html:form action="showDefaulterReport.do?method=defaulterReport" method="POST" enctype="multipart/form-data">
	<TR>
		<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
		<TD background="images/TableBackground1.gif"><IMG src="images/ReportsHeading.gif" width="121" height="25"></TD>
		<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
	</TR>
	<TR>
		<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
		<TD>
			<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
				<TR>
					<TD colspan="3">
						<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
						<TR>
							<TD width="110" class="Heading"><bean:message key="defaulterDetails"/></TD>
							<TD><IMG src="images/TriangleSubhead.gif" width="35" height="25"></TD>
						</TR>
						<TR>
							<TD colspan="3" class="heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
						</TR>
						</TABLE>
					</TD>
				</TR>
				<TR align="center" valign="top">
					<TD  width="40%" valign="top"  class="HeadingBg">
						<DIV align="center">&nbsp;&nbsp;<bean:message key="field"/>
						</DIV>
					</TD>

					<TD  width="30%" valign="top"  class="HeadingBg">
						<DIV align="center">&nbsp;&nbsp;<bean:message key="value"/>
						</DIV>
					</TD>

					<TD  width="30%" valign="top"  class="HeadingBg">
						<DIV align="center">&nbsp;&nbsp;<bean:message key="relation"/>
						</DIV>
					</TD>
				</TR>


					 
				
				<TR align="left" valign="top">
					<TD width="30%" valign="top" class="TableData">
						<DIV align="left">&nbsp;&nbsp;<bean:message key="borrowerUnitName" />
						</DIV>
					</TD>
					
					<TD class="TableData" align="center">&nbsp;
					<DIV align="center">
					<html:text property="borrUnitName" size="20" maxlength="100" alt="Borrower Unit Name" name="defaulterReport"/></DIV></TD>

					<TD class="TableData" align="center">&nbsp;
					<DIV align="center">
					<html:select name="defaulterReport" property="borrUnitNameBoolean">
					<html:option value="and">AND</html:option>
					<html:option value="or">OR</html:option>
					</html:select>
					</DIV></TD>
				</TR>

				<TR align="left" valign="top">
					<TD width="30%" valign="top" class="TableData">
						<DIV align="left">&nbsp;&nbsp;<bean:message key="itpanOfTheUnit" />
						</DIV>
					</TD>

					<TD class="TableData" align="center">&nbsp;
					<DIV align="center">
					<html:text property="itpanOfTheUnit" size="20" maxlength="10" alt="ITPAN Of The unit" name="defaulterReport"/></DIV></TD>

					<TD class="TableData" align="center">&nbsp;
					<DIV align="center">
					<html:select name="defaulterReport" property="itpanOfTheUnitBoolean">
					<html:option value="and">AND</html:option>
					<html:option value="or">OR</html:option>
					</html:select>
					</DIV></TD>
				</TR>

				<TR align="left" valign="top">
					<TD width="30%" valign="top" class="TableData">
						<DIV align="left">&nbsp;&nbsp;<bean:message key="chiefPromoterName" />
						</DIV>
					</TD>
					<TD class="TableData" align="center">&nbsp;
					<DIV align="center">
					<html:text property="chiefPromoterName"
					size="20" maxlength="60" alt="Chief Promoter Name" name="defaulterReport"
					/></DIV></TD>

					<TD class="TableData" align="center">&nbsp;
					<DIV align="center">
					<html:select name="defaulterReport" property="chiefPromoterNameBoolean">
					<html:option value="and">AND</html:option>
					<html:option value="or">OR</html:option>
					</html:select>
					</DIV></TD>
				</TR>

				<TR align="left" valign="top">
					<TD width="30%" valign="top" class="TableData">
						<DIV align="left">&nbsp;&nbsp;<bean:message key="chiefPromoterDOB" />
						</DIV>
					</TD>

					<TD class="TableData" align="center">&nbsp;
					<DIV align="center">
					<html:text property="chiefPromoterDOB"
					size="20" maxlength="10" alt="Chief Promoter DOB" name="defaulterReport"
					/></DIV></TD>

					<TD class="TableData" align="center">&nbsp;
					<DIV align="center">
					<html:select name="defaulterReport" property="chiefPromoterDOBBoolean">
					<html:option value="and">AND</html:option>
					<html:option value="or">OR</html:option>
					</html:select>
					</DIV></TD>
				</TR>

				<TR align="left" valign="top">
					<TD width="30%" valign="top" class="TableData">
						<DIV align="left">&nbsp;&nbsp;<bean:message key="itpanOfTheChiefPromoter" />
						</DIV>
					</TD>
				
					<TD class="TableData" align="center">&nbsp;
					<DIV align="center">
					<html:text property="itpanOfTheChiefPromoter"
					size="20" maxlength="10" alt="ITPAN Of The Chief Promoter" name="defaulterReport"
					/></DIV></TD>

					<TD class="TableData" align="center">&nbsp;
					<DIV align="center">
					<html:select name="defaulterReport" property="itpanOfTheChiefPromoterBoolean">
					<html:option value="and">AND</html:option>
					<html:option value="or">OR</html:option>
					</html:select>
					</DIV></TD>
				</TR>

				<TR align="left" valign="top">
					<TD width="30%" valign="top" class="TableData">
						<DIV align="left">&nbsp;&nbsp;<bean:message key="legalIDOfTheChiefPromoter" />
						</DIV>
					</TD>
					<TD class="TableData" align="center">&nbsp;
					<DIV align="center">
					<html:text property="legalIDOfTheChiefPromoter"
					size="20" maxlength="20" alt="Legal ID Of The Chief Promoter" name="defaulterReport"
					/></DIV></TD>

					<TD class="TableData" align="center">&nbsp;
					<DIV align="center">
					</DIV></TD>
				</TR>
			<TR>
					<TD colspan="3" align="center" valign="baseline">
					<DIV align="center">
						<A href="javascript:submitForm('showDefaulterReport.do?method=defaulterReport')"><IMG src="images/Search.gif" alt="Submit" width="49" height="37" border="0"></A>
						<A href="javascript:document.defaulterReport.reset()"><IMG src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></A>						
					</DIV>
					</TD>
			</TR>
			</TABLE>
			</TD>

			<TD width="20" background="images/TableVerticalRightBG.gif">&nbsp;
			</TD>
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

</body>