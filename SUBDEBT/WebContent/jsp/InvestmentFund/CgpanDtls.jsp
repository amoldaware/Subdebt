<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@ page import="org.apache.struts.validator.DynaValidatorActionForm"%>
<%@ page import="com.cgtsi.guaranteemaintenance.CgpanDetail"%>
<% session.setAttribute("CurrentPage","showCgpanDetails.do?method=showCgpanDetails");%>


<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="showCgpanDetails.do?method=showCgpanDetails" method="POST" enctype="multipart/form-data">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/InvestmentManagementHeading.gif"></TD>
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
 			            
						
						<TR> 
							  <TD width="50%" class="ColumnBackground"><bean:message key="cgbid"/>
							  </TD>
							  <TD class="tableData"><bean:write name="ifForm" property="cgpanDtl.borrowerId"/>
							  </TD>
				  		</TR>

						<TR> 
							  <TD class="ColumnBackground"><bean:message key="unitNameExisting"/>
							  </TD>
							  <TD class="tableData"><bean:write name="ifForm" property="cgpanDtl.borrowerName"/>
							  </TD>
						</TR>

						<TR> 
								<TD class="ColumnBackground"><bean:message key="chiefPromoterName"/>
								</TD>
								<TD class="tableData"><bean:write name="ifForm" property="cgpanDtl.chiefPromoterName"/>
								</TD>
						</TR>

						<TR> 
							<TD class="ColumnBackground"><bean:message key="city"/>
							</TD>
							<TD class="tableData"><bean:write name="ifForm" property="cgpanDtl.city"/>
							</TD>
						</TR>
						
						<TR> 
								<TD class="ColumnBackground"><bean:message key="wcAmountSanctioned"/>
								</TD>
								<TD class="tableData"><bean:write name="ifForm" property="cgpanDtl.wcAmountSanctioned"/>
								</TD>
						</TR>
						
						    <%
						       DynaValidatorActionForm form = (DynaValidatorActionForm)session.getAttribute("ifForm") ;
						       CgpanDetail obj = (CgpanDetail)form.get("cgpanDtl");
						       java.util.Date date = obj.getGuaranteeIssueDate();
						       java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
						       String dateStr = sdf.format(date);						       
						       // System.out.println("Member Id from Claim Action Form :" +memberId);
						    %>						     
						
						
						<TR> 
								<TD class="ColumnBackground"><bean:message key="guaranteeIssueDate"/>
								</TD>
								<TD class="tableData"><%=dateStr%>
								</TD>
						</TR>
						<TR> 
							<TD class="ColumnBackground"><bean:message key="tcAmountSanctioned"/>
							</TD>
							<TD class="tableData"><bean:write name="ifForm" property="cgpanDtl.tcAmountSanctioned"/>
							</TD>
						</TR>
						
						<TR> 
							<TD class="ColumnBackground"><bean:message key="amountApproved"/>
							</TD>
							<TD class="tableData"><bean:write name="ifForm" property="cgpanDtl.amountApproved"/>
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
