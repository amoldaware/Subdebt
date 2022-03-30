<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","applicationRecievedReportInput.do?method=applicationRecievedReportInput");%>

<script>
 function selectDistrict()
 {
      document.getElementById("hide").value = "cgtsi";
      document.forms[0].method              = "POST";
      document.forms[0].action              = "slabWiseReport.do?method=slabWiseInput";
      document.forms[0].submit();
 }
 
 function checkString(id)
 {
    if(id != "")
    {
     if (!id.match(/^[0-9]+$/))
     {
      alert('MLI ID should be numeric only');
      document.forms[0].mliID.value = "";
      return false;
    }
   }
   
 }
 
 function checkRangeFrom(id)
 {
    if(id != "")
    {
     if (!id.match(/^[0-9]+$/))
     {
      alert('Range should be numeric only');
      document.forms[0].rangeFrom.value = "";
      return false;
    }
    }
    }
    
   function checkRangeTo(id)
    {
      if(id != "")
      {
         if (!id.match(/^[0-9]+$/))
         {
          alert('Range should be numeric only');
          document.forms[0].rangeTo.value = "";
          return false;
        }
      }
    }
   
 
  
</script>


<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="slabWiseReport.do" method="POST" enctype="multipart/form-data">
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
				<tr>
				<TD  align="left" colspan="4"><font size="2"><bold>
				Fields marked as </font><font color="#FF0000" size="2">*</font><font size="2"> are mandatory</bold></font>
				</td>
				</tr>
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="10"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="27%" class="Heading"><bean:message key="danReportHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>
                    
										</TABLE>
									</TD>
                 </TR> 
   <html:hidden property="hiddenvalue" value="" styleId="hide"/>
								 <TR>
                     <TD align="left" valign="top" class="ColumnBackground">
                       <DIV align="left"> &nbsp;<bean:message key="from"/>  </DIV>
                     </TD>
									   <TD  align="left" valign="center" class="TableData">
										  <DIV align="left">
										  <html:text property="dateOfTheDocument42" alt="Reference" maxlength="10" name="rsForm" size="20"/>
										  <IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rsForm.dateOfTheDocument42')" align="center">
										  </DIV>
									  </TD>
									  <TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>
										  &nbsp;<bean:message key="to"/> 
									  </TD>
									  <TD  align="left" valign="center" class="TableData">
										  <DIV align="left">
										  <html:text property="dateOfTheDocument43" alt="Reference" maxlength="10" name="rsForm" size="20"/>
										  <IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('ryForm.dateOfTheDocument43')" align="center">
										  </DIV>
									  </TD>
								 </TR>
                 <TR>
                     <TD align="left" valign="top" class="ColumnBackground">
                       <DIV align="left"> &nbsp;State  </DIV>
                     </TD>
									   <TD  align="left" valign="center" class="TableData">
										  <DIV align="left">
										     <html:select property="slabState" onchange="selectDistrict(this)">
                            <html:option value="select">Select</html:option>
                            <logic:iterate id="state" name="stateList">
                            <html:option value="<%=((String[])state)[1]%>"><%=((String[])state)[1]%></html:option>
                            </logic:iterate>
                         </html:select>
										  </DIV>
									  </TD>
									  <TD align="left" valign="top" class="ColumnBackground">
                       <DIV align="left"> &nbsp;District </DIV>
                     </TD>
									    <logic:present name="districtSet">
                     <TD  align="left" valign="center" class="TableData">
										  <DIV align="left">
                         <html:select property="slabDistrict">
                            <html:option value="select">Select</html:option>
                            <logic:iterate id="district" name="districtList">
                                <html:option value="<%=((String[])district)[1]%>"><%=((String[])district)[1]%></html:option>
                            </logic:iterate>
                         </html:select>
                      </DIV>
                      </TD>
                     </logic:present>
                     <logic:notPresent name="districtSet">
                         <TD  align="left" valign="center" class="TableData">
										  <DIV align="left">
                         <html:select property="slabDistrict" disabled="true">
                                <html:option value="select">Select</html:option>
                          </html:select>
                      </DIV>
                      </TD>
                     </logic:notPresent>
								 </TR>
                 <TR>
                     <TD align="left" valign="top" class="ColumnBackground">
                       <DIV align="left"> &nbsp;MLI </DIV>
                     </TD>
									   <TD  align="left" valign="center" class="TableData">
										  <DIV align="left">
										    <html:text property="mliID" maxlength="12" onchange="checkString(this.value)" styleId="mliIDID"/>
										  </DIV>
									  </TD>
									  <TD align="left" valign="top" class="ColumnBackground">
                       <DIV align="left"> &nbsp;Industry_sector </DIV>
                     </TD>
									   <TD  align="left" valign="center" class="TableData">
										  <DIV align="left">
										     <html:select property="slabIndustrySector">
                             <html:option value="select">Select</html:option>
                             <logic:iterate id="sector" name="sectorList">
                                <html:option value="<%=((String[])sector)[1]%>"><%=((String[])sector)[1]%></html:option>
                            </logic:iterate>
                         </html:select>
										  </DIV>
									  </TD>
								 </TR>
                 <TR>
                     <TD align="left" valign="top" class="ColumnBackground">
                       <DIV align="left"> &nbsp;Range From </DIV>
                     </TD>
									   <TD  align="left" valign="center" class="TableData">
										  <DIV align="left">
										     <html:text property="rangeFrom" onblur="checkRangeFrom(this.value)" styleId="rangeFromID"/>
                         </DIV>
									  </TD>
									  <TD align="left" valign="top" class="ColumnBackground">
                       <DIV align="left"> &nbsp;Range To </DIV>
                     </TD>
									   <TD  align="left" valign="center" class="TableData">
										  <DIV align="left">
										     <html:text property="rangeTo" onblur="checkTo(this.value)" styleId="rangeToID"/>
										  </DIV>
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
							        <A href="javascript:submitForm('slabWiseReportOutPut.do?method=slabWiseOutPut')">
									<IMG src="images/OK.gif" alt="OK" width="49" height="37" border="0"></A>
								<A href="javascript:document.myForm.reset()">
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

