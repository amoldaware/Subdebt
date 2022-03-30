<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@page import = "org.apache.struts.action.DynaActionForm"%>
<%@page import ="java.text.SimpleDateFormat"%>
<%@page import ="java.util.Date"%>
<% SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");%>
<% DynaActionForm dynaForm = (DynaActionForm)session.getAttribute("ioForm"); 
  // String ltrDts = dateFormat.format(dynaForm.get("ltrDts")); 
 //  dynaForm.set("ltrDts",ltrDts);
 //  System.out.println("ltrDts:"+ltrDts);
  // String instrumentDts = dateFormat.format(dynaForm.get("instrumentDts")); 
 //  System.out.println("instrumentDts:"+instrumentDts);
  %> 

<% session.setAttribute("CurrentPage","updateInwardDetails.do?method=updateInwardDetails");%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors/>
	<html:form action="displayInwardDetails.do?method=displayInwardDetails" method="POST" enctype="multipart/form-data">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<DIV align="right">			
				</DIV>

				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
							<table width="100%" border="0" cellspacing="1" cellpadding="0">
							<tr> 
							  <td colspan="4">
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR>
										<TD width="30%" class="Heading">Display Inward Details&nbsp;</TD>
										<TD width="70%"><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
									</TR>
									<TR>
										<TD colspan="4" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
									</TR>
								</TABLE>
							  </td>
							</tr>
							 <tr align="left"> 
							   <td  class="ColumnBackground"> 
									<font color="#FF0000" size="2">*</font>	&nbsp;
									<bean:message key="inwardId"/>			
								</td>
								<td class="TableData">
                <bean:write property="inwardId" name="ioForm"/>
									<!-- <html:text property="inwardId" size="20" alt="inwardId" name="ioForm" maxlength="6" onkeypress="return numbersOnly(this, event)"/>   -->     
								</td>
                <td  class="ColumnBackground"> 
									<font color="#FF0000" size="2">*</font>	&nbsp;
									Inward <bean:message key="chequeDate"/>			
								</td>
								<td class="TableData">
                <bean:write property="inwardDts" name="ioForm"/>
									<!-- <html:text property="inwardDts" size="20" alt="inwardDts" name="ioForm" maxlength="10"/>      -->   
								</td>
							 </tr>
               <tr align="left"> 
							   <td  class="ColumnBackground"> 
									<font color="#FF0000" size="2">*</font>	&nbsp;
									<bean:message key="chqBankName"/>			
								</td>
								<td class="TableData">
									 <html:text property="bankNames" size="60" alt="bankNames" name="ioForm" maxlength="60"/>        
								</td>
                <td  class="ColumnBackground"> 
									<font color="#FF0000" size="2">*</font>	&nbsp;
									 <bean:message key="drawnAtBank"/>			
								</td>
								<td class="TableData">
									 <html:text property="drawnonBank" size="30" alt="drawnonBank" name="ioForm" maxlength="30"/>        
								</td>
							 </tr>
               <tr align="left"> 
							   <td  class="ColumnBackground"> 
									<font color="#FF0000" size="2">*</font>	&nbsp;
									<bean:message key="place"/>			
								</td>
								<td class="TableData">
									 <html:text property="places" size="60" alt="places" name="ioForm" maxlength="60"/>        
								</td>
                <td  class="ColumnBackground"> 
									<font color="#FF0000" size="2">*</font>	&nbsp;
									 <bean:message key="subject"/>			
								</td>
								<td class="TableData">
									 <html:text property="subjects" size="30" alt="subjects" name="ioForm" maxlength="30"/>        
								</td>
							 </tr>
               
               <tr align="left"> 
							   <td  class="ColumnBackground"> 
									<font color="#FF0000" size="2">*</font>	&nbsp;
									<bean:message key="referenceNo"/>			
								</td>
								<td class="TableData">
									 <html:text property="referenceIds" size="60" alt="referenceIds" name="ioForm" maxlength="60"/>        
								</td>
                <td  class="ColumnBackground"> 
									<font color="#FF0000" size="2">*</font>	&nbsp;
									Ltr Date
								</td>
								<td class="TableData">
									 <html:text property="ltrDt" size="20" alt="ltrDt" name="ioForm" maxlength="10"/>        
								</td>
							 </tr>
               
               <tr align="left"> 
							   <td  class="ColumnBackground"> 
									<font color="#FF0000" size="2">*</font>	&nbsp;
									<bean:message key="instrumentNumber"/>			
								</td>
								<td class="TableData">
									 <html:text property="sourceIds" size="20" alt="sourceIds" name="ioForm" maxlength="6" />        
								</td>
                 <td  class="ColumnBackground"> 
									<font color="#FF0000" size="2">*</font>	&nbsp;
									<bean:message key="instrumentDate"/>			
								</td>
								<td class="TableData">
									 <html:text property="instrumentDt" size="20" alt="instrumentDt" name="ioForm" maxlength="10"/>        
								</td>
             		 </tr>
               <tr align="left"> 
							      <td  class="ColumnBackground"> 
									<font color="#FF0000" size="2">*</font>	&nbsp;
								<bean:message key="instrumentAmount"/>		
								</td>
								<td class="TableData">
									 <html:text property="instrumentAmt" size="20" alt="instrumentAmt" name="ioForm" maxlength="10"/>        
								</td>
					     <td  class="ColumnBackground"> 
									<font color="#FF0000" size="2">*</font>	&nbsp;
								Section
								</td>
								<td class="TableData">
                <html:select property="section" name="ioForm">
                <html:option value="GF">GF</html:option>
                <html:option value="ASF">ASF</html:option>
                <html:option value="CLAIM">CLAIM</html:option>
                <html:option value="OTHERS">OTHERS</html:option>
									<!-- <html:text property="section" size="20" alt="section" name="ioForm" maxlength="10"/>     -->   
								</html:select>
                </td>
                </tr>
                 <tr align="left"> 
							   <td  class="ColumnBackground"> 
									<font color="#FF0000" size="2">*</font>	&nbsp;
									<bean:message key="outwardId"/>			
								</td>
								<td class="TableData">
									 <html:text property="outwardId" size="20" alt="outwardId" name="ioForm" maxlength="100" />        
								</td>
                 <td  class="ColumnBackground"> 
									<font color="#FF0000" size="2">*</font>	&nbsp;
									Outward Dt		
								</td>
								<td class="TableData">
									 <html:text property="outwardDt" size="20" alt="outwardDt" name="ioForm" maxlength="10"/>        
								   <IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('ioForm.outwardDt')" align="center">
                </td>
             		 </tr>
                 
                   <tr align="left"> 
							   <td  class="ColumnBackground"> 
									<font color="#FF0000" size="2">*</font>	&nbsp;
									<bean:message key="assignedTo"/>			
								</td>
								<td class="TableData">
								<%--	 <html:text property="assignedTo" size="20" alt="assignedTo" name="ioForm" maxlength="100" />  --%>  
							<html:select property="assignedTo" name="ioForm">
                    <html:option value="">Select</html:option>
                    <html:options property="getUserNames" name="ioForm"/>			
              	</html:select> 
                </td>
                <td class="TableData" colspan="2">
							<%--	<html:select property="assignedTo" name="ioForm">
                    <html:option value="">Select</html:option>
                    <html:options property="getUserNames" name="ioForm"/>			
              	</html:select> --%>
                </td>
                
                </tr>
                <tr>
                 <td class="ColumnBackground" colspan="1"><div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp; 
                  <bean:message key="reasons"/>
                  </div></td>
                  <td class="tableData" colspan="3"><div align="left">
                    <html:text property="reasons" size="100" alt="reasons" name="ioForm" maxlength="400" />
                  <%--  <html:textarea cols="30" rows="5" style="font-size:14;"  property="reasons" name="ioForm"></html:textarea>	--%>
                  </div></td>
                </tr>
                 
                 
    				  </table>									
						</td>
					</tr>					
					<TR>
						<TD align="center" valign="baseline">
							<DIV align="center">
								<A href="javascript:submitForm('displayInwardDetails.do?method=afterUpdateInwardDetails')"><IMG src="images/OK.gif" alt="Save" width="49" height="37" border="0"></A>	
							<!--	<A href="javascript:document.ioForm.reset()">
									<IMG src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></A> -->
								<A href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
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

