<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
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
String action = "";
	//session.setAttribute("CurrentPage","showBorrowerApproval.do?method=showModifiedBorrowerDetails");
	session.setAttribute("CurrentPage","approveBorrowerDetails.do?method=approveBorrowerDetails");
	//action = "approveBorrowerDetails.do?method=approveBorrowerDetails";
	action = "showBorrowerApproval.do?method=showModifiedBorrowerDetails";
//action = "/CGINTERM_N_updated/approveBorrowerDetails.do?method=approveBorrowerDetails";
%>

 
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form   action="approveBorrowerDetails.do?method=approveBorrowerDetails" method="POST" >
	
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
									<TD align="center" valign="top" class="ColumnBackground_center"><font color="#FF0000" size="2">*</font><bean:message key="MemberID"/>
									</TD>
									<TD align="center" valign="top" class="ColumnBackground_center"><font color="#FF0000" size="2">*</font><bean:message key="BorrowerID"/>
									</TD>
									<TD class="ColumnBackground_center" align="center">
										<bean:message key="borrowerRemark"/>					
									
									</TD>
									<TD class="ColumnBackground_center" align="center"></TD>
									

								</TR>
								<%
							
									// GMActionForm gmActionForm = new GMActionForm();
									// System.out.println("gmActionForm bidsList"+gmActionForm.getBidsList());
									%>
									<logic:iterate id="object" name="gmApproveForm" property="bidsList">
									<%	
									String memberId ="";
									String borrowerId ="";
									String remarks ="";
									try
									{
									java.util.Map.Entry bidDtlsMap = (java.util.Map.Entry)object;
								//	System.out.println("bidDtlsMap "+bidDtlsMap);
										 memberId = (String)bidDtlsMap.getKey() ;
										System.out.println("memberId "+memberId);
									//	String borrowerId=(String)bidDtlsMap.getValue();
										//System.out.println("bid "+borrowerId);
										
										  String borrowersList[] = (String[])bidDtlsMap.getValue();
										
										  borrowerId=borrowersList[0];
										  remarks=borrowersList[1];
									}
									catch(Exception e)
									{
										e.printStackTrace();
									}
										//ArrayList borrowerIdsList = (ArrayList)bidDtlsMap.getValue();
								//	System.out.println("borrowerIdsList "+borrowerIdsList.size());
									%>
									<TR align = "center">

										<TD class="TableDataCenter" width="25" align="center">
											<%=memberId%>											
										</TD>

										<%
										%>
							
										<TD class="TableDataCenter" width="25" align="center">
											<a href="javascript:submitForm('showBorrowerDetailsForApproval.do?method=showBorrowerDetailsForApproval&memberId=<%=memberId%>&borrowerId=<%=borrowerId%>')">
											<%=borrowerId%>
											</a> 
										</TD>
											
										<TD class="TableDataCenter" width="25" align="center">
											<html:textarea name="gmApproveForm" property="borrowerApprovalRemarks" disabled="true" value="<%=remarks%>" rows="3" cols="50" />
											
										</TD>
										
										<TD class="TableDataCenter" width="25" align="center">
										
										<%String value=memberId + "-" + borrowerId;%>
										<%name="approveBorrowerFlag("+value+")";%>
										<%System.out.println("name "+name); %>
										    <html:select property="<%=name%>" name="gmApproveForm" onchange="validateSelectOptionTenureDetailsUpdated(this.value,this.name)" >
			    <html:option value="">Select</html:option>
                            <html:option value="RE">Reject</html:option>
                            <html:option value="AP">Accept</html:option>		
                            
                         
								
		    	</html:select>
										</TD>
									</TR>
										
								
								 </logic:iterate>
								<TR align="left" valign="top" >
	 								<TD align="left" valign="top"   class="ColumnBackground" colspan="11" >									
										In case of above mentioned 'Accepted' cases , MLI accept and declare that 
									</TD> 
								</TR> 
								<TR align="left" valign="top" >
					
									<TD align="left" valign="top"   class="ColumnBackground" colspan="11" >

									 <input type="checkbox" value="Account is Standard and Regular" id="declaration"> &nbsp;&nbsp; Account is Standard and Regular

									</TD>
									
									
								</TR>  			
								<TR align="left" valign="top" >
	 								<TD align="left" valign="top"   class="ColumnBackground" colspan="11" >

	 								<input type="checkbox" value="Account is Standard and Regular" id="declaration1"> &nbsp;&nbsp; The changes made are as per the record available with the bank and are duly approved by the delegated authority.
									</TD> 
								</TR> 
									
									
								<TR align="left" valign="top" >
	 								<TD align="left" valign="top"   class="ColumnBackground" colspan="11" >									
										<div id="errorsMessage" class="errorsMessageNew"></div>
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
							<DIV align="center">
								
											<A href="javascript:submitFormApproveBorrowerDetails('approveBorrowerDetails.do?method=approveBorrowerDetails')">
									<IMG src="images/Submit.gif" alt="Approve" width="49" height="37" border="0"></A>

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

