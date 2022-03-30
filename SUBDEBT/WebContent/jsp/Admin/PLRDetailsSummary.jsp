<%@ page language="java"%>
<%@ page import="com.cgtsi.admin.PLRMaster"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","getPLRSummary.do?method=getPLRSummary");%>
<body>
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="getPLRSummary.do?method=getPLRSummary" method="POST" enctype="multipart/form-data">
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
							<td ><bean:message key="SerialNumber"/><br></td>
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
						<%
						int counter=1;
						%>
						<logic:iterate property="plrDetails" name="adminActionForm" id="object" type="com.cgtsi.admin.PLRMaster">
						<TR>
						<td class="TableData">
							<DIV align="center">
							<%
							int plrId=object.getPlrId();
							String url="javascript:submitForm('getPLRDetails.do?method=getPLRDetails&plrId="+plrId+"')";
							%>
							<a href="<%=url%>"><%=counter++%> </A>
							</div>
						</td>
						
						<td class="TableData">
							<DIV align="center">
							<bean:write property="startDate" name="object"/>
							</div>
						</td>

						<td class="TableData">
							<DIV align="center">
							<bean:write property="endDate" name="object"/>
							</div>
						</td>

						<td class="TableData">
							<DIV align="center">
							<bean:write property="shortTermPLR" name="object"/>
							</div>
						</td>

						<td class="TableData">
							<DIV align="center">
							<bean:write property="mediumTermPLR" name="object"/>
							</div>
						</td>

						<td class="TableData">
							<DIV align="center">
							<bean:write property="longTermPLR" name="object"/>
							</div>
						</td>

						<td class="TableData">
							<DIV align="center">
							<bean:write property="shortTermPeriod" name="object"/>
							</div>
						</td>

						<td class="TableData">
						<DIV align="center">
							<bean:write property="mediumTermPeriod" name="object"/>
							</div>
						</td>
						<td class="TableData">
							<DIV align="center">
							<bean:write property="longTermPeriod" name="object"/>
							</div>
						</td>						
						<td class="TableData">
							<%
							String plrType=null;
							if(object.getPLR().equals("B"))
							{
								plrType="Benchmark PLR";
							}
							else
							{
								plrType="Tenure PLR";
							}
							%>
							<DIV align="center">
							<%=plrType%>
							</div>
						</td>
						<td class="TableData">
							<DIV align="center">
							<bean:write property="BPLR" name="object"/>
							</div>
						</td>
						
						
						</TR>
						
						</logic:iterate>
						
					</table></td>
				</tr>									  
								

							</TABLE>
						</TD>
					</TR>
					<TR >
						<TD align="center" valign="baseline" >
							<DIV align="center">
								<A href="javascript:history.back()">
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

