<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@page import ="java.text.SimpleDateFormat"%>
<%@page import ="java.util.Date"%>
<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>

<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","documentDetailsOutward.do?method=documentDetailsOutward");%>



<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="documentDetailsOutward.do?method=documentDetailsOutward" method="POST" enctype="multipart/form-data">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/InwardOutwardHeading.gif" width="121" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="4"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="31%" class="Heading"><bean:message key="inwardHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>
								</TR>

								<TR>
									<TD colspan="4" class="SubHeading">
										<DIV align="left">
											<BR>
											<bean:message key="sourceDetails" />
										</DIV>
									</TD>
								</TR>

								<TR align="left" valign="top">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="sourceType"/>
									</TD>
									<TD align="left" class="TableData"> 

											<bean:write property="sourceType" name="ioForm"/>
									</TD>
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="sourceId"/>
									</TD>
									<TD align="left" class="TableData"> 
								
										<bean:write property="sourceId" name="ioForm"/>
									</TD>
								</TR>

								<TR align="left" valign="top">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="sourceName"/>
									</TD>
									<TD align="left" class="TableData"> 

											<bean:write property="sourceName" name="ioForm"/>
									</TD>
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="sourceRef"/>
									</TD>
									<TD align="left" class="TableData"> 
											<bean:write property="sourceRef" name="ioForm"/>
									</TD>
								</TR>


								<TR>
									<TD colspan="4" class="SubHeading">
										<DIV align="left">
											<BR>
											<bean:message key="documentDetails" />
										</DIV>
									</TD>
								</TR>
					
									<TR>
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="documentType" />
									</TD>
									<TD align="left" valign="top" class="TableData"> 
									<bean:write property="documentType" name="ioForm"/>
									</TD>
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="modeOfReceipt" />
									</TD>
									<TD align="left" valign="top" class="TableData"> 
									
										<bean:write property="modeOfReceipt" name="ioForm"/>
									</TD>
								</TR>
								

								<TR>
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="documentDate" />
									</TD>
									<TD align="left" valign="top" class="TableData"> 
								     <!-- To fix bug 07092004-18 -->
										<bean:write property="dateOfDoc" name="ioForm"/>
								     <!-- Fix is completed. -->
									 </TD>
									  <TD align="left" valign="top" class="ColumnBackground">
										  &nbsp;<bean:message key="language" />
									  </TD>
									  <TD align="left" valign="top" class="TableData"> 
									
										<bean:write property="language"  name="ioForm"/>
									</TD>
								</TR>

								<TR>
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="inwardId" />
									</TD>
									<TD align="left" valign="top" class="TableData" colspan="3"> 
								
										<bean:write property="inwardId"  name="ioForm"/>
									</TD>
								</TR>

								<TR>
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="subject" />
									</TD>
									<TD align="left" valign="top" class="TableData" colspan="3"> 
								
										<bean:write property="subject"  name="ioForm"/>
									</TD>
								</TR>
								
								<TR>
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="remarks" />
									</TD>
									<TD align="left" valign="top" class="TableData" colspan="3"> 
										<DIV align="left">
									
										<bean:write property="remarks" name="ioForm"/>
										</DIV>
									</TD>
								</TR>

							

							</TABLE>
						</TD>
					</TR>
					<TR >
						<TD height="20" >
							&nbsp;
						</TD>
					</TR>
					<TR>
						<TD align="center" valign="baseline" >
							<DIV align="center">
								<A href="javascript:history.back()">
									<IMG src="images/Back.gif" alt="Back" width="49" height="37" border="0"></A>
								
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

