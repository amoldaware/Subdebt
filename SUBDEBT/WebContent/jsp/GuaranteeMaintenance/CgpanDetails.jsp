<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@ page import="com.cgtsi.actionform.GMActionForm"%>
<%@ page import="com.cgtsi.application.BorrowerDetails"%>
<%@ page import="com.cgtsi.application.SSIDetails"%>
<%@page import ="java.text.SimpleDateFormat"%>
<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>

<% session.setAttribute("CurrentPage","showCgpanDetailsLink.do?method=showCgpanDetailsLink");%>


<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="showCgpanDetailsLink.do?method=showCgpanDetailsLink" method="POST" enctype="multipart/form-data">
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
												<TD width="30%" class="Heading"><bean:message key="cgpanHeader"/></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="6" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>
								</TR>
 			            
						
					<%
						HttpSession gmSession = request.getSession(false);
						GMActionForm gmActionForm = (GMActionForm)session.getAttribute("gmPeriodicInfoForm");						
						java.util.Date dob = gmActionForm.getGuaranteeIssueDateForLink();
						System.out.println("dob:"+dob);
						String formatedDate = null;
						if(dob != null)
						{
							 formatedDate=dateFormat.format(dob);
							 System.out.println("formatedDate:"+formatedDate);
						}
						else
						{
							 formatedDate = "";
							 System.out.println("formatedDate:"+formatedDate);
						}
					%>
						<TR> 
							  <TD width="50%" class="ColumnBackground"><bean:message key="cgbid"/>
							  </TD>
							  <TD class="tableData"><bean:write name="gmPeriodicInfoForm" property="bidForCgpanLink"/>
							  </TD>
				  		</TR>

						<TR> 
							  <TD class="ColumnBackground"><bean:message key="unitNameExisting"/>
							  </TD>
							  <TD class="tableData"><bean:write name="gmPeriodicInfoForm" property="borrowerNameForLink"/>
							  </TD>
						</TR>

						<TR> 
								<TD class="ColumnBackground"><bean:message key="chiefPromoterName"/>
								</TD>
								<TD class="tableData"><bean:write name="gmPeriodicInfoForm" property="promoterNameForLink"/>
								</TD>
						</TR>

						<TR> 
							<TD class="ColumnBackground"><bean:message key="city"/>
							</TD>
							<TD class="tableData"><bean:write name="gmPeriodicInfoForm" property="cityForCgpanLink"/>
							</TD>
						</TR>
						
						<TR> 
								<TD class="ColumnBackground"><bean:message key="wcAmountSanctioned"/>
								</TD>
								<TD class="tableData"><bean:write name="gmPeriodicInfoForm" property="wcAmountSanctionedForLink"/>
								</TD>
						</TR>
						
						<TR> 
								<TD class="ColumnBackground"><bean:message key="guaranteeIssueDate"/>
								</TD>
								<TD class="tableData">
								<%=formatedDate%>
								</TD>
						</TR>
						<TR> 
							<TD class="ColumnBackground"><bean:message key="tcAmountSanctioned"/>
							</TD>
							<TD class="tableData"><bean:write name="gmPeriodicInfoForm" property="tcAmountSanctionedForLink"/>
							</TD>
						</TR>
						
						<TR> 
							<TD class="ColumnBackground"><bean:message key="amountApproved"/>
							</TD>
							<TD class="tableData"><bean:write name="gmPeriodicInfoForm" property="amountApprovedForLink"/>
							</TD>
						</TR>
						
						<TR> 
							<TD colspan="6"><img src="images/Clear.gif" width="5" height="15">
							</TD>
						</TR>

						</TABLE>
					  </TD>
					</TR>

					<TR >
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
