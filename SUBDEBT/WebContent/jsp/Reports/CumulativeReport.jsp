<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<script type="text/javascript"> 
        window.history.forward(); 
        function noBack() { 
            window.history.forward(); 
        } 
    </script> 
	
	<script src="/csrfguard"></script>
	
<% session.setAttribute("CurrentPage","cumulativeReport.do?method=cumulativeReport");%>
<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>
<%DecimalFormat decimalFormat = new DecimalFormat("#########0.00");%>

	

<%@ include file="/jsp/SetMenuInfo.jsp" %>
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
<html:errors />

<html:form action="cumulativeReport.do?method=cumulativeReport" method="POST" focus="memberId">
<TABLE width="680" border="0" cellspacing="0" cellpadding="0" align="center">
<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>

			<TD width="713">

			<!-- <DIV align="right">			
				<A HREF="javascript:submitForm('helpChangePassword.do?method=helpChangePassword')">
			    HELP</A>
			</DIV> -->
			
			<TABLE width="606" border="0" cellspacing="1" cellpadding="0">
			<TD  align="left" colspan="5">
				</td>
          <TR> 
            <TD colspan="4" width="700"><TABLE width="604" border="0" cellspacing="0" cellpadding="0">
												<TD width="100%" class="Heading">
												CUMULATIVE REPORT
                                             </TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="5" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD> 

								</TR>


         <%	
										int columnCount=0;
							%>	
						<TR>
						<TD align="right" valign="top" class="TableData" ><bean:message
								key="sNo" /></td>
						<logic:iterate id="object" name="reportForm" property="colletralCoulmnName"
							indexId="index">
							<%	
										String str=(String)object;
										columnCount++;
										
							%>
		
								<TD align="right" valign="top" class="TableData" ><B><%=str %></B>
								</TD>
								</logic:iterate>
							</TR>
							
						
						<logic:iterate id="object1" name="reportForm" property="colletralCoulmnValue"
							indexId="index1">
							<TR>
								
								<TD align="right" valign="top" class="TableData" ><%=index1+1%></td>
							<%	
							ArrayList value =  (ArrayList)object1;
							
							
							for(int i=0;i<value.size();i++){
							%>
								<TD align="right" valign="top" class="TableData" ><%=value.get(i) %></TD>
								
							
							<% } %></TR>
						</logic:iterate>
						
					
          
            <TR align="center" valign="baseline" >
						<TD colspan="4" width="700"><DIV align="center">
						
								
											<A href="javascript:document.rsForm.reset()">
									<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>
								
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

