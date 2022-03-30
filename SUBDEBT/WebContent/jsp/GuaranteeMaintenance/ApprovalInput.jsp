<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@ page import="com.cgtsi.util.SessionConstants"%>
<%@ page import="com.cgtsi.common.Constants"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@ page import="java.util.TreeMap"%>
<%@ page import="java.util.ArrayList"%>

<%@ page import="com.cgtsi.actionform.GMActionForm"%>

<% 
String name="";
String action="";
	session.setAttribute("CurrentPage","showApproval.do?method=showApprovalPeriodicInfo");
	action = "approvePeriodicInfo.do?method=approvePeriodicInfo";
%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="<%=action%>" method="POST" enctype="multipart/form-data">
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
								<TR>
									<TD colspan="4"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="31%" class="Heading"><bean:message key="modifyBorrowerHeader"/></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>
								</TR>
								<TR align="left" valign="top">
									<TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font><bean:message key="MemberID"/>
									</TD>
									<TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font><bean:message key="BorrowerID"/>
									</TD>
									<TD class="ColumnBackground" align="center"><html:checkbox property="selectAll" alt="Close" name="gmApproveForm" onclick="selectDeselect(this,1)"/>Select All									
									
									</TD>

								</TR>
								<%
								int j=0;
								%>
										<logic:iterate id="object" name="gmApproveForm" property="bidsList">
										<%										

											java.util.Map.Entry bidDtlsMap = (java.util.Map.Entry)object;
											String memberId = (String)bidDtlsMap.getKey() ;
											ArrayList borrowerIdsList = (ArrayList)bidDtlsMap.getValue();
										%>
										<TR align = "center">

											<TD class="tableData" width="25" align="center"><center>
												<%=memberId%>											
											</TD>

											<%
											for(int i=0; i<borrowerIdsList.size();i++)
											{
												String borrowerId = (String)borrowerIdsList.get(i);

												if(i==0)
												{
											
											%>
								
											<TD class="tableData" width="25" align="center"><center>
												<a href="javascript:submitForm('showOutstandingsForApproval.do?method=showOutstandingsForApproval&memberId=<%=memberId%>&borrowerId=<%=borrowerId%>')">
												<%=borrowerId%>
											</TD>
											<TD class="tableData" width="25" align="center">
											<%name="approveBorrowerFlag(key-"+j+")";%>
											<%String value=memberId + "-" + borrowerId;%>
											<html:checkbox property="<%=name%>" alt="Close" name="gmApproveForm" value="<%=value%>"/>
											</TD>
										</TR>
											<%}
												else
												{%>
												<TR align = "center">
													<TD class="tableData" width="25" align="center"></TD>
											<TD class="tableData" width="25" align="center"><center>
												<a href="javascript:submitForm('showOutstandingsForApproval.do?method=showOutstandingsForApproval&memberId=<%=memberId%>&borrowerId=<%=borrowerId%>')">
												<%=borrowerId%>
												</a> 
											</TD>
											<TD class="tableData" width="25" align="center">
											<%name="approveBorrowerFlag(key-"+j+")";%>
											<%String value=memberId + "-" + borrowerId;%>
											<html:checkbox property="<%=name%>" alt="Close" name="gmApproveForm" value="<%=value%>"/>
											</TD>

											</TR>

										<html:hidden property="memberId" name="gmApprovalForm" value="<%=memberId%>"/>
										<html:hidden property="borrowerId" name="gmApprovalForm" value="<%=borrowerId%>"/>



												<%}j++;}%>
										</TR>
									 </logic:iterate>

							</TABLE>
						</TD>
					</TR>
					<TR >
						<TD height="20" >
							&nbsp;
						</TD>
					</TR>
					<TR >
						<TD align="center" valign="baseline" >
							<DIV align="center">
								
											<A href="javascript:submitForm('approvePeriodicInfo.do?method=approvePeriodicInfo')">
									<IMG src="images/Approve.gif" alt="Approve" width="49" height="37" border="0"></A>
										<A href="javascript:document.gmApproveForm.reset()">
									<IMG src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></A>
									
								<A href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>">
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
