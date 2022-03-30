<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>

<script type="text/javascript">

$(document).ready(function() {
	  $('.stream').on('click', function(e) {
	    if (e.target.value == 'ApplicatonReferenceNo') {
	    	 $('[id$=place]').text('ApplicatonReferenceNo');
	    }
	    else if (e.target.value == 'Cgpan') {
	    	 $('[id$=place]').text('Cgpan');
	    }
	    else if (e.target.value == 'PaymentId') {
	    	 $('[id$=place]').text('PaymentId');
	    }
	  });
	});
function checkValue(elem){
	debugger;
	 var type = elem.value;
	 if(type == "ApplicationReferenceNo"){
			document.getElementById('id1').style.display = 'block';
	        document.getElementById('id2').style.display = 'none';
	        document.getElementById('id3').style.display = 'none';
		} 
	 if(type == "Cgpan"){
		document.getElementById('id1').style.display = 'none';
        document.getElementById('id2').style.display = 'block';
        document.getElementById('id3').style.display = 'none';
	} 
	 if(type == "PaymentId"){
			document.getElementById('id1').style.display = 'none';
	        document.getElementById('id2').style.display = 'none';
	        document.getElementById('id3').style.display = 'block';
		} 
	
}
</script>

<% session.setAttribute("CurrentPage","applicationStatus.do?");%>
 
<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form name = "reportForm" type="com.cgtsi.actionform.ReportActionForm"  action="showApplicationStatus.do?method=showApplicationStatus" method="POST" >
		
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/ReportsHeading.gif" width="121" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
			<DIV align="right">			
			<A HREF="applicationReportHelp.do?method=applicationReportHelp">
			HELP</A>
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
									<td width="25%" class="ColumnBackground" ><input type="radio" name="stream" onclick="checkValue(this);" value="ApplicationReferenceNo" checked="checked">Application Reference No</td>
									<td width="25%" class="ColumnBackground"><input type="radio" name="stream" onclick="checkValue(this);" value="Cgpan">Cgpan</td>
									<td width="25%" class="ColumnBackground"><input type="radio" name="stream" onclick="checkValue(this);" value="PaymentId">Payment Id</td>
									
									<%-- <TD align="left" valign="top" class="ColumnBackground">
										  &nbsp;<bean:message key="appRefNo"/> 
									</TD> --%>									 
									  </TR>	
									  <tr>
									  <TD id="id1" align="left" style="display:block" valign="top" class="ColumnBackground">&nbsp;Application Reference No:</TD>
									  <TD id="id2" align="left" style="display:none" valign="top" class="ColumnBackground">&nbsp;Cgpan:</TD>
									  <TD id="id3" align="left" style="display:none" valign="top" class="ColumnBackground">&nbsp;Payment Id:</TD>
									  <td><html:text property="appRefNo" size="20"  alt="Cgpan" maxlength="20" name="reportForm"/>  
									  </td>
									  </tr>			

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
							<A href="javascript:submitForm('showApplicationStatus.do?method=showApplicationStatus')">
									<IMG src="images/OK.gif" alt="OK" width="49" height="37" border="0"></A>
								<A href="javascript:document.reportActionForm.reset()">
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
</TABLE>

