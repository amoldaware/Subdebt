
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
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean"%>
<%@ page import="com.cgtsi.admin.Privileges,com.cgtsi.admin.User"%>

<script type="text/javascript"> 
        window.history.forward(); 
        function noBack() { 
            window.history.forward(); 
        } 
    </script>

<script src="/csrfguard"></script>

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
<LINK href="<%=request.getContextPath()%>/css/StyleSheet.css"
	rel="stylesheet" type="text/css">
<TITLE>Credit Guarantee Scheme for Subordinate Debt (CGSSD)</TITLE>
<style>

.form-control {
    display: block;
    width: 265px;
    margin-top: 5px !important;
    height: 40px;
    padding: 6px 12px;
    font-size: 17px;
    line-height: 1.42857143;
    color: #555;
    background-color: #fff;
    background-image: none;
    border: 1px solid #ccc;
    border-radius: 4px;
    -webkit-box-shadow: inset 0 1px 1px rgb(0 0 0 / 8%);
    box-shadow: inset 0 1px 1px rgb(0 0 0 / 8%);
    -webkit-transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
    -o-transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
    -webkit-transition: border-color ease-in-out .15s,-webkit-box-shadow ease-in-out .15s;
    transition: border-color ease-in-out .15s,-webkit-box-shadow ease-in-out .15s;
    transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
    transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s,-webkit-box-shadow ease-in-out .15s;
}
iframe.IFrameStyle {
    width: 1412px !important;
    max-width: 1447px !important;
}
</style>
</HEAD>
<script src="/csrfguard"></script>
<script type="text/javascript">
function openReasBox(field){
			var id = field;
			//alert('id:'+id);
			var target = findObj("branchName");
			var data = target.value;
			if(data != ''){
				//regex to set checkbox(s) for selected reasons.
			}
			var left = (screen.width/2)-(800/2);
			var top = (screen.height/2)-(400/2);
			//Here child window handler to get selected values.
			var myWin = window.open('/faqHistory.do?method=faqHistory','_blank','width=800,height=400,resizable=1,scrollbars=1,location=no,top='+top+'left='+left);			
			//pass id to child window.
			setInterval(function(){myTimer();},1000);
			function myTimer() {
    				
    				myWin.document.getElementById('test').value = id;
			}

			
				
		}
</SCRIPT>

<SCRIPT language="JavaScript" type="text/JavaScript"
	src="<%=request.getContextPath()%>/js/CGTSI.js">
	</SCRIPT>

<SCRIPT language="JavaScript" type="text/JavaScript"
	src="<%=request.getContextPath()%>/js/selectdate.js">
	</SCRIPT>
<script>
	

		<%if (session.getAttribute("menuIcon") != null) {%>
		selection='<%=session.getAttribute("menuIcon")%>';
		<%}%>

		<%if (session.getAttribute("mainMenu") != null) {%>
		mainMenuItem='<%=session.getAttribute("mainMenu")%>';
		<%}%>

		<%if (session.getAttribute("subMenuItem") != null) {%>
		subMenuItem='<%=session.getAttribute("subMenuItem")%>';
		<%}%>

		<%-- alert("selection,Main menu,sub menu are <%=session.getAttribute("menuIcon")%>,<%=session.getAttribute("mainMenu")%>,<%=session.getAttribute("subMenuItem")%>"); --%>
		
	</script>


<%
	//String date=null;
	// System.out.println(session.getAttribute("lastLogin"));
	if (session.getAttribute("loginTime") == null) {
		java.util.Date loggedInTime = new java.util.Date();
		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("dd MMMMM yyyy ':' HH.mm");
		String date = dateFormat.format(loggedInTime);

		session.setAttribute("loginTime", date);
	}
%>
<%
	String path = "invalidateSession('" + request.getContextPath() + "')";
%>

<BODY BGCOLOR="white" leftmargin="0" topmargin="0" bottommargin="0"
	rightmargin="0" marginwidth="0" marginheight="0">
	<TABLE width="100%" border="0" cellpadding="0" cellspacing="0"
		id="mainTable">
		<form name="Main">
			<TR>
				<%
					User user = (User) session.getAttribute(com.cgtsi.util.SessionConstants.USER);
					java.util.ArrayList userPrivileges = user.getPrivileges();

					boolean isAPOk = Privileges.isAPAvailable(userPrivileges);

					boolean isGMOk = Privileges.isGMAvailable(userPrivileges);

					boolean isRPOk = Privileges.isRPAvailable(userPrivileges);

					boolean isCPOk = Privileges.isCPAvailable(userPrivileges);

					boolean isRIOk = Privileges.isRIAvailable(userPrivileges);

					boolean isIFOk = Privileges.isIFAvailable(userPrivileges);

					boolean isReportsOk = true;//Privileges.isReportsAvailable(userPrivileges);

					boolean isIOOk = Privileges.isIOAvailable(userPrivileges);

					boolean isSCOk = Privileges.isSCAvailable(userPrivileges);

					boolean isTCOk = Privileges.isTCAvailable(userPrivileges);

					boolean isDBOk = true;

					String bankId = user.getBankId();

					String userId = user.getUserId();

					boolean isCGTSIUser = false;

					String colSpan1 = "8";
					String colSpan2 = "4";

					if (bankId.equals("0000") && (!userId.equals("DEMOUSER"))) {
						isCGTSIUser = true;
						colSpan1 = "11";
						colSpan2 = "6";
					}
				%>

				<TD style="width: 25%; margin: 0 auto; text-align: center;"
					background="<%=request.getContextPath()%>/images/TopBackground.gif">
					<A <%if (isAPOk) {%> HREF="#" <%}%>> <IMG
						src="<%=request.getContextPath()%>/images/ApplicationProcessing.gif"
						alt="Application Processing (AP) " name="ApplicationProcessing"
						id="applicationProcessing" <%if (isAPOk) {%>
						onClick="setMenuOptions('AP','<%=request.getContextPath()%>')"
						<%}%> border="0">
				</A>
				</TD>

				<TD
					background="<%=request.getContextPath()%>/images/TopBackground.gif">
					
				</TD>

				<TD style="width: 25%; margin: 0 auto; text-align: center;"
					background="<%=request.getContextPath()%>/images/TopBackground.gif">
					<A <%if (isRPOk) {%> HREF="#" <%}%>> <IMG
						src="<%=request.getContextPath()%>/images/payment.gif"
						alt="Receipts and Payments (RP)" name="ReceiptsPayments"
						id="receiptsPayments" <%if (isRPOk) {%>
						onClick="setMenuOptions('RP','<%=request.getContextPath()%>')"
						<%}%> border="0">
				</A>
				</TD>

				<TD
					background="<%=request.getContextPath()%>/images/TopBackground.gif">
					
				</TD>

				<%
					if (isCGTSIUser) {
				%>
				
				<%
					}
				%>


				<TD style="width: 25%; margin: 0 auto; text-align: center;"
					background="<%=request.getContextPath()%>/images/TopBackground.gif">
					<A <%if (isReportsOk) {%> HREF="#" <%}%>> <IMG
						src="<%=request.getContextPath()%>/images/Reports.gif"
						alt="Reports and MIS (RS)" name="Reports" id="reports"
						<%if (isReportsOk) {%>
						onClick="setMenuOptions('RS','<%=request.getContextPath()%>')"
						<%}%> border="0">
				</A> <!--onClick="setMenuOptions('RS','<%=request.getContextPath()%>')" !-->
				</TD>



				<%
					if (isCGTSIUser) {
				%>
				<TD
					background="<%=request.getContextPath()%>/images/TopBackground.gif">

					<%-- <A <%if(isIOOk){%> HREF="#" <%}%>> <IMG
			src="<%=request.getContextPath()%>/images/InwardOutward.gif"
			alt="Inward and Outward (IO)" name="InwardOutward" id="inwardOutward"
			border="0" <%if(isIOOk){%>
			onClick="setMenuOptions('IO','<%=request.getContextPath()%>')" <%}%>
			border="0"> </A> --%>
				</TD>
				<%
					}
				%>

				<TD style="width: 25%; margin: 0 auto; text-align: center;"
					background="<%=request.getContextPath()%>/images/TopBackground.gif">

					<A HREF="#"> <IMG
						src="<%=request.getContextPath()%>/images/SystemAdministration.gif"
						alt="System Administration (AD)" name="SystemAdministration"
						id="systemAdministration"
						onClick="setMenuOptions('AD','<%=request.getContextPath()%>')"
						border="0">
				</A>
				</TD>


				<%
					if (isCGTSIUser) {
				%>
				<TD
					background="<%=request.getContextPath()%>/images/TopBackground.gif">

					
				</TD>
				<%
					}
				%>
			</TR>

			<TR>
				<TD colspan="<%=colSpan1%>">
					<TABLE width="100%" cellspacing="0" cellpadding="0" border="0">
						<TR>
							<TD width="40" class="Top" style="font-size: 14px;">Welcome&nbsp;</TD>
							<TD width="80" class="User" style="font-size: 14px;">&nbsp;<bean:write
									name="<%=com.cgtsi.util.SessionConstants.USER%>"
									property="firstName" /></TD>
							<TD width="180" class="Top" style="font-size: 14px;">&nbsp; <%
 	out.println(session.getAttribute("loginTime"));
 %>

							</TD>
							<TD align="left"><IMG
								src="<%=request.getContextPath()%>/images/TriangleOrangeTop.gif"
								width="18" height="22" align="top"></TD>
							<%
								String contextPath = request.getContextPath();
							%>
							<TD width="275" align="left"><SELECT class="MainMenu form-control"
								name="MainMenu" disabled
								onChange="setSubMenuOptions(this,'<%=request.getContextPath()%>')">

									<OPTION selected><!--Generate Payment Voucher for Excess,Enter Forecasting Details -->
										Select
									</OPTION>
							</SELECT></TD>
							<TD align="left"><SELECT class="MainMenu form-control" name="SubMenu" 
								disabled
								onChange="doActionForSelection(this,'<%=request.getContextPath()%>')">

									<OPTION selected>Select</OPTION>

							</SELECT></TD>
							<TD style="font-size: 22px;font-weight: 600;">
					 <A	HREF="<%=request.getContextPath()%>/logout.do?method=logout">Logout</A>				

				</TD>
						</TR>
					</TABLE>
				</TD>
			</TR>





			<TR>

				<TD colspan="<%=colSpan2%>" width="550" id="naviBar" style="padding-bottom: 10px;">&nbsp; <script>
  							var naviBar=document.getElementById('naviBar');
  						
  							setNaviBar(naviBar);
  						</script>
				</TD>				

			</TR>
			<TR>
				<TD colspan="6"><IFRAME name="content" class="IFrameStyle"
						frameborder="0"
						src="<%=session.getAttribute("CurrentPage") != null
					&& !((String) session.getAttribute("CurrentPage")).equals("showLogin.do")
							? request.getContextPath() + "/" + (String) session.getAttribute("CurrentPage")
							: request.getContextPath() + "/jsp/CGTSIHome/CGTSIHome.jsp"%>">
						Errors in IFrame loading. </IFRAME></TD>
				<%=session.getAttribute("CurrentPage")%>
			</TR>


			<TR height="1">

				<TD colspan="11">&nbsp;</TD>


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
							<TD width="40" class="FooterMember">Note:&nbsp;</TD>
							<TD width="110" class="MemberInfo">All dates should be in</TD>
							<TD width="90" class="FooterMember">DD / MM / YYYY</TD>
							<TD width="40" class="MemberInfo">&nbsp;format.</TD>
							<TD align="left"><IMG
								src="<%=request.getContextPath()%>/images/TriangleOrangeBottomRotated.gif"
								width="13" height="22" align="top"></TD>
							<TD><IMG
								src="<%=request.getContextPath()%>/images/Clear.gif" height="5"></TD>
							<TD align="right"><IMG
								src="<%=request.getContextPath()%>/images/TriangleOrangeBottom.gif"
								width="13" height="22"></TD>
							<TD class="FooterMember" width="350">




								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR>
										<TD class="MemberInfo">Bank:</TD>
										<TD class="FooterMember">&nbsp;&nbsp;&nbsp;&nbsp;<bean:write
												property="bankName" name="login" /></TD>
										<TD class="MemberInfo">&nbsp;&nbsp;&nbsp;&nbsp;Zone:</TD>
										<TD class="FooterMember">&nbsp;&nbsp;&nbsp;&nbsp;<bean:write
												property="zoneName" name="login" /></TD>
										<TD class="MemberInfo">&nbsp;&nbsp;&nbsp;&nbsp;Branch:</TD>
										<TD class="FooterMember">&nbsp;&nbsp;&nbsp;&nbsp;<bean:write
												property="branchName" name="login" /></TD>
									</TR>
								</TABLE>
							</TD>
						</TR>
						<TR valign="top">
							<TD colspan="11" class="Footer">
								<DIV align="center">Copyright
									&nbsp;@&nbsp;2002&nbsp;&nbsp;CGTSI &nbsp;Allrights reserved</DIV>
							</TD>
						</TR>
					</TABLE>
				</TD>
			</TR>
		</form>
	</TABLE>
</BODY>
</HTML>
