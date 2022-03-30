<%@ page language="java"%>
<%@ page import="org.apache.struts.validator.DynaValidatorActionForm"%>
<%@ page import="com.cgtsi.admin.MenuOptions"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<% session.setAttribute("CurrentPage","showReviewAudit.do?method=showReviewAudit");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<HTML>
	<BODY>
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="addReviewAudit.do" method="POST" focus="cgpan">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
			<DIV align="right">			
				<A HREF="javascript:submitForm('helpReviewAudit.do?method=helpReviewAudit')">
			    HELP</A>
			</DIV>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
				<TD  align="left" colspan="4"><font size="2"><bold>
				Fields marked as </font><font color="#FF0000" size="2">*</font><font size="2"> are mandatory</bold></font>
				</td>
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="4"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="31%" class="Heading"><bean:message key="reviewAuditDtls" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>
								</TR>

								 <TR align="left">
									<TD class="ColumnBackground" width="353">&nbsp;&nbsp;<bean:message key="cgpan" />&nbsp;</td>									
									<TD align="left" valign="middle" class="TableData" width="397">				
										<html:text property="cgpan" alt="CGPAN" name="regForm" maxlength="13" />&nbsp;	&nbsp;									
									<A href="javascript:submitForm('getAuditForCgpan.do?method=getAuditForCgpan')"><B>GET AUDIT DETAILS</B></A>
									</TD>
								</TR>

								<TR align="left">
									<TD class="ColumnBackground" width="353">&nbsp;&nbsp;<bean:message key="auditorComments" />&nbsp;</td>
									<TD class="TableData" width="397">
									  <p style="line-height: 100%">&nbsp;</p>
									  <p style="line-height: 100%"><bean:write property="comment" name="regForm"/></p>
									  <p>&nbsp;</p>
									</TD>
								 </TR>   
								 <TR align="left">
									<TD class="ColumnBackground" width="353">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="reviewedComments" />&nbsp;</td>
									<TD align="left" class="TableData" width="397"> 
										<html:textarea property="reviewedComments" cols="35" rows="5" alt="Comments" name="regForm"/>
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
					<TR >
						<TD align="center" valign="baseline" >
							
							        <%
							             // System.out.println("Main Menu :" + session.getAttribute("mainMenu"));
							             if((session.getAttribute("mainMenu")).equals(MenuOptions.getMenu(MenuOptions.CP_SETTLEMENT)))
							             {
									     DynaValidatorActionForm form = (DynaValidatorActionForm)session.getAttribute("regForm");
									     // System.out.println("DynaValidatorActionForm :" + form);
									     String mId = (String)form.get("memberId");
									     // System.out.println("Member Id from Audit Page:" + mId);								    								     							        
							        %>
							        <DIV align="left">
                                                                <a href="getSettlementMemId.do?method=getSettlementMemId&MemberId=<%=mId%>&Src="Reg"">Back to Settlement Details</a>							
                                                                </div>
                                                                <%
                                                                }
                                                                %>
                                                                <DIV align="center">
								<A								    href="javascript:submitForm('showReviewAuditPrev.do?method=showReviewAuditPrev')">
									<IMG src="images/Previous.gif" alt="Previous" width="49" height="37" border="0"></A>								
								<A href="javascript:submitForm('addReviewAudit.do?method=addReviewAudit')"><IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>
								<A href="javascript:document.regForm.reset()">					
									<IMG src="images/Reset.gif" alt="reset" width="49" height="37" border="0"></A>
								<A									href="javascript:submitForm('showReviewAuditNext.do?method=showReviewAuditNext')">
									<IMG src="images/Next.gif" alt="Next" width="49" height="37" border="0"></A>
								<A href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>"><IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>
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
</BODY>
</HTML>





