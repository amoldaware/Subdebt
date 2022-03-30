<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","rrQueryClaimDetail.do?method=rrQueryClaimDetail");%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="displayRRQueryDetail.do?method=displayRRQueryDetail" method="POST" enctype="multipart/form-data">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/ReportsHeading.gif" width="121" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
			<DIV align="right">			
			
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
				
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="8"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="22%" class="Heading"><bean:message key="danReportHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>
										</TABLE>
									</TD>
						
									<TR>
									<TD align="left" valign="top" class="ColumnBackground">
										  &nbsp;<bean:message key="cgpan"/> 
									</TD>
									 <TD align="left" valign="center" class="TableData">
										 <html:text property="cgpan" size="20"  alt="Cgpan" maxlength="15" name="cpTcDetailsForm"/>  
									  </TD>
									  </TR>

									  <TR>
									<TD align="left" valign="top" class="ColumnBackground">
										  &nbsp; Claim Ref No 
									</TD>
									  <TD align="left" valign="center" class="TableData">
										   <html:text property="clmRefNumber" maxlength="100" size="20"  alt="SSI" name="cpTcDetailsForm"/>
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
							<!--<A href="javascript:submitClaimDetailForm('displayRRQueryDetail.do?method=displayRRQueryDetail')">
									<IMG src="images/OK.gif" alt="OK" width="49" height="37" border="0"></A>-->
                                                                        <A href="#" onclick="saveForm();">
									<IMG src="images/OK.gif" alt="OK" width="49" height="37" border="0"></A>
								<A href="javascript:document.cpTcDetailsForm.reset()">
									<IMG src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></A>
									
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
    <script type="text/javascript">
                    function saveForm(){
                    
                    if(document.forms[0].cgpan.value=="" && document.forms[0].clmRefNumber.value==""){
                        alert("please enter cgpan or claim reference number");
                        return false;
                    }
                    
                    document.cpTcDetailsForm.target ="_self";
                        document.cpTcDetailsForm.method="POST";
                        document.cpTcDetailsForm.action="displayRRQueryDetail.do?method=displayRRQueryDetail";
                        document.cpTcDetailsForm.submit(); 
              }      
                    
        </script>    
</TABLE>

