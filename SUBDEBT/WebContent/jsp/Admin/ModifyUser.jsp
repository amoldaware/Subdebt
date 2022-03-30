<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>

<script type="text/javascript"> 
        window.history.forward(); 
        function noBack() { 
            window.history.forward(); 
        } 
    </script> 
	
	<script src="/csrfguard"></script>


<%! String focusField = "";%>
<%! String flag = "";%>
<%    
    flag = (String)session.getAttribute("MemberSelected");
    if((flag != null) && (flag.equals("Y")))
    {
        focusField = "userId";
    }
    if((flag == null)||(flag != null) && (flag.equals("N")))
    {
        focusField = "memberId";
    }    
%>
<% session.setAttribute("CurrentPage","showModifyUser.do?method=showModifyUser");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
<html:errors />

<html:form action="showModifyUser.do" method="POST" focus="<%=focusField%>">
<table width="680" border="0" cellspacing="0" cellpadding="0" align="center">
<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<td width="713">
			<DIV align="right">			
				<A HREF="javascript:submitForm('helpModifyUser.do?method=helpModifyUser')">
			    HELP</A>
			</DIV>
			<table width="606" border="0" cellspacing="1" cellpadding="0">
			<TD  align="left" colspan="4"><font size="2"><bold>
				Fields marked as </font><font color="#FF0000" size="2">*</font><font size="2"> are mandatory</bold></font>
				</td>
          <tr> 
            <td colspan="2" width="700"><table width="604" border="0" cellspacing="0" cellpadding="0">
												<TD width="31%" class="Heading"><bean:message key="modifyUserHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD> 

								</TR>

          <tr>
		    <TD align="left" valign="top" class="ColumnBackground" >
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="memberId" />
			</TD>
			<TD align="left" valign="top" class="TableData" >										
			<html:select property="memberId" name="adminForm" onchange="javascript:submitForm('showModifyUser.do?method=getUsersForMember')">
			<html:option value="">Select</html:option>
			<html:options property="memberIds" name="adminForm"/>
										</html:select>
			</TD>            
          </tr>       

           
          </tr>
          <tr>
		  <TD width="20%" align="left" valign="top" class="ColumnBackground">
										<DIV align="left">
											&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="userId" />
										</DIV>
									</TD>
           <TD width="27%" align="left" valign="top" class="TableData">
										<html:select property="userId" name="adminForm">
											<html:option value="">Select</html:option>
											<html:options property="activeUsers" name="adminForm"/>
										</html:select>
									</TD>
									<%
									     if((flag != null) && (flag.equals("Y")))
									     {
									         session.setAttribute("MemberSelected","N");
									     }
									%>
          </tr>
		  
							<TR align="center" valign="baseline" >
						<td colspan="2" width="700"><DIV align="center">
								
								<A href="javascript:submitForm('modifyUser.do?method=modifyUser')">
									<IMG src="images/OK.gif" alt="Save" width="49" height="37" border="0"></A>
								<A href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>"><IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>
							</DIV>
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

