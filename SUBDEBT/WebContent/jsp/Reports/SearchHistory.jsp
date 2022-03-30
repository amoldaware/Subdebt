<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","searchHistory.do?method=searchHistory");%>

<script type="text/javascript" language="JavaScript"><!--



function submitForm1(action)
{	    

	 var x;
	var items=document.getElementsByName('search');
	var cnt=0;

	for(var i=0; i<items.length; i++)
	{
		if(items[i].checked==true)
		{
			
			cnt++;
		}
	}
	
	
	if(cnt<4)
	{
		alert("Please select checkbox for confirmation");
		document.getElementsByName('search').focus();	
	}
	
	
   /* if (confirm("Fee of  Rs.50/- and applicable tax will be charged per successful search") == true) 
    {
        x = "You pressed PROCEED!";
        document.forms[0].action=action;
    	document.forms[0].target="_self";
    	document.forms[0].method="POST";
    	document.forms[0].submit();
                  
    } 
 else
  {
        x = "You pressed CANCEL!";
    }
	 */
	 x = "You pressed PROCEED!";
     document.forms[0].action=action;
 	document.forms[0].target="_self";
 	document.forms[0].method="POST";
 	document.forms[0].submit();
    document.getElementById("demo").innerHTML = x;
	
	
}
</script>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form  action="showSearchHistory.do?method=showSearchHistory" method="POST" enctype="multipart/form-data">
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
									<TD align="left" valign="top" class="ColumnBackground">
										  &nbsp;Enter UNIT ITPAN:
									
										 <html:text property="itpan" size="20"  alt="Cgpan" maxlength="12" name="reportForm"/>  
									  </TD>
									   </TR>
									
									 
									  
									   <TR>
									   
									   
									   
									   <TD align="left" valign="top" class="ColumnBackground" class="TableData"><br>
									   
									   <font color="black" size="2"> We undertake that:</font>
									   <br><br>
									   
									      <!-- <input type="checkbox" name="search" property="agree" value="Y">
									   <font color="#FF0000" size="2">*Fee of  &#8377 50/- and applicable tax will be charged per successful search.</font> -->
									   <br><br><br>
									   
									   	<input type="checkbox" name="search" property="agree" value="Y">
									   <font color="#FF0000" size="2">* The information accessed is strictly for internal use and shall be kept confidential.</font>
									   <br>
									   
									   <input type="checkbox" name="search" property="agree" value="Y">
									   <font color="#FF0000" size="2">* CGTMSE shall not be held accountable for credit decision taken based on the information shared.</font>
									   <br>
									   
									   <input type="checkbox" name="search" property="agree" value="Y">
									   <font color="#FF0000" size="2">* We understand that the information accessed is as per data available in the system at a given point of time which may change subsequently due to modifications/changes.</font>
									   <br>
									   
									   
									   
									   <input type="checkbox" name="search" property="agree" value="Y">
									   <font color="#FF0000" size="2">* We understand that the information is not a substitute to CIBIL check and any other due diligence which shall be carried out as per the Banks/FIs policy.</font>
									   <br><br><br>
									   
									   <font color="black" size="1"> The result generated is based on the availability of ITPAN in the record.</font>
									   <br>
					
									   
									   <br>
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
							<A href="javascript:submitForm1('showSearchHistory.do?method=showSearchHistory')">
									<IMG src="images/Search.gif" alt="Search" width="49" height="37" border="0"></A>
								<A href="javascript:document.reportForm.reset()">
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


