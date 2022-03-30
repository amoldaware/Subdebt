<%
/**********************************************************************
*Name																	: Main.jsp
*Description													: Main page
*Developer														: Kesavan Srinivasan
*List of pages this asp navigates to 	: TBD
*Creation Date												: Sep 11, 2003
*Last revised													: Sep 11, 2003
*
*Revision history
*
*Modified by		Date Modified		Reason for modification
*Kesavan Srinivasan	Sep 11, 2003		Initial version
*
**********************************************************************/
%>
<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ page import="com.cgtsi.admin.Privileges,com.cgtsi.admin.User" %>

<%--
<%!

java.util.Date loggedInTime=new java.util.Date();
java.text.SimpleDateFormat dateFormat=new java.text.SimpleDateFormat("dd MMMMM yyyy ':' HH.mm");
String date=dateFormat.format(loggedInTime);

%>
--%>
<HTML>
	<HEAD>
		<LINK REV="MADE" HREF="Kesavan_Srinivasan@satyam.com">
		<LINK href="<%=request.getContextPath()%>/css/StyleSheet.css" rel="stylesheet" type="text/css">
		<TITLE>Credit Guarantee Fund Trust for Micro and Small Enterprises(CGTMSE)</TITLE>
	</HEAD>

	<SCRIPT language="JavaScript" type="text/JavaScript" src="<%=request.getContextPath()%>/js/CGTSI.js">
	</SCRIPT>

	<SCRIPT language="JavaScript" type="text/JavaScript" src="<%=request.getContextPath()%>/js/selectdate.js">
	</SCRIPT>
	<script>
		/*alert("Menu Icon is <%=session.getAttribute("menuIcon")%>");*/

		<%if(session.getAttribute("menuIcon")!=null){%>
		selection='<%=session.getAttribute("menuIcon")%>';
		<%}%>

		<%if(session.getAttribute("mainMenu")!=null){%>
		mainMenuItem='<%=session.getAttribute("mainMenu")%>';
		<%}%>

		<%if(session.getAttribute("subMenuItem")!=null){%>
		subMenuItem='<%=session.getAttribute("subMenuItem")%>';
		<%}%>

		/*alert("selection,Main menu,sub menu are <%=session.getAttribute("menuIcon")%>,<%=session.getAttribute("mainMenu")%>,<%=session.getAttribute("subMenuItem")%>");
		*/
	</script>
	<%
		//String date=null;
   // System.out.println(session.getAttribute("lastLogin"));
		if(session.getAttribute("loginTime")==null)
		{
			java.util.Date loggedInTime=new java.util.Date();
			java.text.SimpleDateFormat dateFormat=new java.text.SimpleDateFormat("dd MMMMM yyyy ':' HH.mm");
			String date=dateFormat.format(loggedInTime);
      
			session.setAttribute("loginTime",date);
		}
	%>
	<%
	String path="invalidateSession('"+request.getContextPath()+"')";
	%>
	<BODY BGCOLOR="white" leftmargin="0" topmargin="0" bottommargin="0" rightmargin="0" marginwidth="0" marginheight="0" onUnload="return invalidateSession('<%=request.getContextPath()%>')">
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="0" id="mainTable">
		<form name="Main">
			<TR>
				<%

					User user=(User)session.getAttribute(com.cgtsi.util.SessionConstants.USER);
					java.util.ArrayList userPrivileges=user.getPrivileges();

					boolean isAPOk=Privileges.isAPAvailable(userPrivileges);

					boolean isGMOk=Privileges.isGMAvailable(userPrivileges);

					boolean isRPOk=Privileges.isRPAvailable(userPrivileges);

					boolean isCPOk=Privileges.isCPAvailable(userPrivileges);

					boolean isRIOk=Privileges.isRIAvailable(userPrivileges);

					boolean isIFOk=Privileges.isIFAvailable(userPrivileges);

					boolean isReportsOk=true;//Privileges.isReportsAvailable(userPrivileges);

					boolean isIOOk=Privileges.isIOAvailable(userPrivileges);

					boolean isSCOk=Privileges.isSCAvailable(userPrivileges);

					boolean isTCOk=Privileges.isTCAvailable(userPrivileges);

					String bankId=user.getBankId();
					
					String userId=user.getUserId();
         // String mliFlag = user.getFlag();
        //  System.out.println("mliFlag:"+mliFlag);
         // System.out.println("For Test User Id:"+userId);

					boolean isCGTSIUser=false;

					String colSpan1="8";
					String colSpan2="4";

					if(bankId.equals("0000") && (!userId.equals("DEMOUSER")) )
					{
						isCGTSIUser=true;
						colSpan1="11";
						colSpan2="6";
					}



					//System.out.println("is application ok "+isAPOk);

					//System.out.println("is GM ok "+isGMOk);

					//System.out.println("is RP ok "+isRPOk);

					//System.out.println("is CP ok "+isCPOk);

					//System.out.println("is RI ok "+isRIOk);

					//System.out.println("is IF ok "+isIFOk);

					//System.out.println("is Reports ok "+isReportsOk);

					//System.out.println("is IO ok "+isIOOk);


				%>

				<TD background="<%=request.getContextPath()%>/images/TopBackground.gif">
					<A <%if(isAPOk){%>HREF="#"<%}%> >
						<IMG src="<%=request.getContextPath()%>/images/ApplicationProcessing.gif"
							alt="Application Processing (AP) " name="ApplicationProcessing"
							id="applicationProcessing" <%if(isAPOk){%>onClick="setMenuOptions('AP','<%=request.getContextPath()%>')"<%}%> border="0">
					</A>
				</TD>

				<TD background="<%=request.getContextPath()%>/images/TopBackground.gif">
					<A <%if(isGMOk){%>HREF="#"<%}%> >
						<IMG src="<%=request.getContextPath()%>/images/GuaranteeMaintenance.gif"
							alt="Guarantee Maintenance (GM)" name="GuaranteeMaintenance"
							id="guaranteeMaintenance" <%if(isGMOk){%>onClick="setMenuOptions('GM','<%=request.getContextPath()%>')"<%}%> border="0">
					</A>
				</TD>

				<TD background="<%=request.getContextPath()%>/images/TopBackground.gif">
					<A <%if(isRPOk){%>HREF="#"  <%}%> >	<IMG src="<%=request.getContextPath()%>/images/ReceiptsPayments.gif" alt="Receipts and Payments (RP)"
							name="ReceiptsPayments" id="receiptsPayments" <%if(isRPOk){%> onClick="setMenuOptions('RP','<%=request.getContextPath()%>')"<%}%> border="0">
					</A>
				</TD>

				<TD background="<%=request.getContextPath()%>/images/TopBackground.gif">
					<A <%if(isCPOk){%> HREF="#" <%}%>  >
					<IMG src="<%=request.getContextPath()%>/images/ClaimsProcessing.gif" alt="Claims Processing (CP)"
						name="ClaimsProcessing" id="claimsProcessing" <%if(isCPOk){%>onClick="setMenuOptions('CP','<%=request.getContextPath()%>')" <%}%>border="0">
					</A>
					<!--onClick="setMenuOptions('CP','<%=request.getContextPath()%>')" !-->
				</TD>

				<%
				if(isCGTSIUser)
				{
				%>
				<TD background="<%=request.getContextPath()%>/images/TopBackground.gif">
					<A <%if(isRIOk){%> HREF="#" <%}%> > 	<IMG src="<%=request.getContextPath()%>/images/RiskManagement.gif" alt="Risk Management (RI)"
						name="RiskManagement" id="riskManagement" <%if(isRIOk){%>onClick="setMenuOptions('RI','<%=request.getContextPath()%>')" <%}%> border="0">
					</A>
				</TD>

				<TD background="<%=request.getContextPath()%>/images/TopBackground.gif">
					<A <%if(isIFOk){%>HREF="#" <%}%>  >
					<IMG src="<%=request.getContextPath()%>/images/InvestmentManagement.gif"
							alt="Investment and Fund Management (IF)" name="InvestmentManagement"
							id="investmentManagement" <%if(isIFOk){%> onClick="setMenuOptions('IF','<%=request.getContextPath()%>')" <%}%> border="0">
					</A>
				</TD>
				<%
				}
				%>


				<TD background="<%=request.getContextPath()%>/images/TopBackground.gif">
					<A <%if(isReportsOk){%> HREF="#" <%}%>  >
					<IMG src="<%=request.getContextPath()%>/images/Reports.gif" alt="Reports and MIS (RS)"
							name="Reports" id="reports" <%if(isReportsOk){%> onClick="setMenuOptions('RS','<%=request.getContextPath()%>')" <%}%> border="0">
					</A>
					<!--onClick="setMenuOptions('RS','<%=request.getContextPath()%>')" !-->
				</TD>

				<%
				if(isCGTSIUser)
				{
				%>
					<TD background="<%=request.getContextPath()%>/images/TopBackground.gif">

						<A  <%if(isIOOk){%> HREF="#" <%}%>  >
						<IMG src="<%=request.getContextPath()%>/images/InwardOutward.gif" alt="Inward and Outward (IO)"
								name="InwardOutward" id="inwardOutward" border="0" <%if(isIOOk){%> onClick="setMenuOptions('IO','<%=request.getContextPath()%>')" <%}%> border="0">
						</A>
					</TD>
				<%
				}
				%>

				<TD background="<%=request.getContextPath()%>/images/TopBackground.gif">

					<A HREF="#">
					<IMG src="<%=request.getContextPath()%>/images/SystemAdministration.gif"
							alt="System Administration (AD)" name="SystemAdministration"
							id="systemAdministration" onClick="setMenuOptions('AD','<%=request.getContextPath()%>')" border="0">
					</A>
				</TD>

		<!--		<TD  background="<%=request.getContextPath()%>/images/TopBackground.gif">
					<A <%if(isTCOk){%> HREF="#" <%}%>  >
					<IMG src="<%=request.getContextPath()%>/images/ThinClient.gif" alt="Thin Client (TC)"
							name="ThinClient" id="ThinClient" <%if(isTCOk){%> 
							onClick="setMenuOptions('TC','<%=request.getContextPath()%>')" <%}%> border="0">
					</A>
				</TD> -->
				<%
				if(isCGTSIUser)
				{
				%>
				<TD background="<%=request.getContextPath()%>/images/TopBackground.gif">

					<A <%if(isSCOk){%> HREF="#" <%}%>  >
					<IMG src="<%=request.getContextPath()%>/images/Securitization.gif" alt="Securitization & MCGS (SC)" name="Securitization"
							id="logout" <%if(isSCOk){%> onClick="setMenuOptions('SC','<%=request.getContextPath()%>')" <%}%>  border="0" >
					</A>

				</TD>
				<%
				}
				%>
			</TR>
			<TR>
				<TD colspan="<%=colSpan1%>">
					<TABLE  width="100%" cellspacing="0" cellpadding="0" border="0">
						<TR>
							<TD width="40" class="Top">
									Welcome&nbsp;
               				</TD>
               				<TD width="80" class="User">
										&nbsp;<bean:write name="<%=com.cgtsi.util.SessionConstants.USER%>" property="firstName"/>
							 </TD>
							<TD width="180" class="Top">
							&nbsp;
							<%out.println(session.getAttribute("loginTime"));%>
								<%--

									out.println(date);
								--%>
							</TD>
							<TD align="left"><IMG src="<%=request.getContextPath()%>/images/TriangleOrangeTop.gif" width="18" height="22" align="top">
							</TD>
							<%
							String contextPath=request.getContextPath();
							%>
							<TD width="275" align="right">
								<SELECT class="MainMenu" name="MainMenu" disabled onChange="setSubMenuOptions(this,'<%=request.getContextPath()%>')">

									<OPTION selected >
										<!--Generate Payment Voucher for Excess,Enter Forecasting Details -->
										Select
									</OPTION>
								</SELECT>
							</TD>
							<TD align="right">
								<SELECT class="MainMenu" name="SubMenu" disabled onChange="doActionForSelection(this,'<%=request.getContextPath()%>')">

									<OPTION selected>
									<!-- Change Hint Question and Answer,Register Collecting Bank-->
										Select
									</OPTION>

								</SELECT>
							</TD>
						</TR>
					</TABLE>
				</TD>
			</TR>
			<TR>
			<!--
				To fix bug id #02092004-97
				Width is changed from 700 to 550 so as to make all the links in a single line.
			-->
				<TD colspan="<%=colSpan2%>" width="550" id="naviBar">

  						&nbsp;
  						
  						<script>
  							var naviBar=document.getElementById('naviBar');
  							// alert("before calling navigation");
  							setNaviBar(naviBar);
  						</script>
                                                
						<!--
						<IFRAME name="navigation" class="NaviStyle" src="jsp/Navi.html"
						frameborder="0" scrolling="no" marginheight="1" marginwidth="10"
						</IFRAME>
						-->
				</TD>
				<TD colspan="5">
    <%--      <%
				if(mliFlag.equals("N"))
				{
				%>
          <A HREF="javascript:callFunction();javascript:performAction('<%=contextPath%>/showModifyMLI.do?method=showModifyMLI')">
				  Modify Member Details </A>&nbsp;&nbsp;
         <% } %> --%>
					<A HREF="javascript:callFunction();javascript:performAction('<%=contextPath%>/showHelp.do?method=showHelp')">
					Help</A>&nbsp;&nbsp;
					<A href="javascript:callFunction();javascript:performAction('<%=contextPath%>/showInbox.do?method=showInbox&userId=<%=userId%>')">
					Inbox</A>&nbsp;&nbsp;
					<A href="javascript:callFunction();javascript:performAction('<%=contextPath%>/showDefaulterInput.do')">
					Search Defaulters</A>&nbsp;&nbsp;					
				 <A HREF="<%=request.getContextPath()%>/logout.do?method=logout">Logout</A>
        
		
				</TD>
			</TR>
			<TR>
				<TD colspan="11" width="700" >
					<IFRAME name="content" class="IFrameStyle"  frameborder="0"
								src="<%=session.getAttribute("CurrentPage") !=null && !((String)session.getAttribute("CurrentPage")).equals("showLogin.do") ? request.getContextPath()+"/"+(String)session.getAttribute("CurrentPage") : request.getContextPath()+"/jsp/CGTSIHome/CGTSIHome.jsp"%>">
					Errors  in IFrame loading.
					</IFRAME>

				</TD>
			</TR>
			<TR height="1">
				<TD colspan="11">
						&nbsp;
				</TD>
			</TR>
			<TR>
				<TD colspan="11">
					<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
						<TR>
							<!--
							<TD class="Top">
								Note: All Dates should be in DD/MM/YYYY format.<IMG src="<%=request.getContextPath()%>/images/Clear.gif" width="200" height="2">
							</TD>
							!-->
							<TD width="40" class="FooterMember">
									Note:&nbsp;
               				</TD>
               				<TD width="110" class="MemberInfo">
										All dates should be in
							</TD>
							<TD width="90" class="FooterMember">
										DD / MM / YYYY
							</TD>
							<TD width="40" class="MemberInfo">
										&nbsp;format.
							</TD>
							<TD align="left"><IMG src="<%=request.getContextPath()%>/images/TriangleOrangeBottomRotated.gif" width="13" height="22" align="top">
							</TD>
							<TD>
								<IMG src="<%=request.getContextPath()%>/images/Clear.gif"  height="5"></TD>
							<TD align="right"><IMG src="<%=request.getContextPath()%>/images/TriangleOrangeBottom.gif" width="13" height="22"></TD>
							<TD class="FooterMember" width="350">
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR>
										<TD class="MemberInfo">
											Bank:
										</TD>
										<TD class="FooterMember">
											&nbsp;&nbsp;&nbsp;&nbsp;<bean:write property="bankName" name="login" />
										</TD>
										<TD class="MemberInfo">
											&nbsp;&nbsp;&nbsp;&nbsp;Zone:
										</TD>
										<TD class="FooterMember">
											&nbsp;&nbsp;&nbsp;&nbsp;<bean:write property="zoneName" name="login" />
										</TD>
										<TD class="MemberInfo">
											&nbsp;&nbsp;&nbsp;&nbsp;Branch:
										</TD>
										<TD class="FooterMember">
											&nbsp;&nbsp;&nbsp;&nbsp;<bean:write property="branchName" name="login" />
                      
										</TD>
									</TR>
								</TABLE>
							</TD>
						</TR>
						<TR valign="top">
							<TD colspan="11" class="Footer">
								  <DIV align="center">
										Copyright &nbsp;@&nbsp;2002&nbsp;&nbsp;CGTSI &nbsp;Allrights reserved
								  </DIV>
							</TD>
						</TR>
					 </TABLE>
				</TD>
			</TR>
			<TR>
			</form>
		</TABLE>
	</BODY>
</HTML>
