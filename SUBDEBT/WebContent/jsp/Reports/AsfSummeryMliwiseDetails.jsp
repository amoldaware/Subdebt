 <%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@page import ="java.text.DecimalFormat"%>
<%DecimalFormat decimalFormat = new DecimalFormat("##########.##");%>
<% session.setAttribute("CurrentPage","asfSummeryReport.do?method=asfSummeryMliwiseReport");%>
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">

	<html:form action="asfSummeryReport.do?method=asfSummeryMliwiseReport" method="POST" enctype="multipart/form-data">

		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/ReportsHeading.gif" width="121" height="25"></TD>
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
									<TD colspan="16"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<tr>
                        <td colspan="6" class="Heading1" align="center"><u><bean:message key="reportHeader"/></u>
                        </td>
                      </tr>
                      <tr>
                        <td colspan="6">&nbsp;</td>
                      </tr>
                      
                      <TR>
												<TD class="Heading" width="42%"><!-- <bean:message key="AsfSummeryMLIwise" /> --> ASF Summary MLI wise Report</TD>
                        <TD class="Heading" width="50%">&nbsp;<bean:message key="from"/><bean:write name="rsForm" property="dateOfTheDocument36"/>&nbsp;<bean:message key="to"/><bean:write name="rsForm" property="dateOfTheDocument37"/></TD>
												<TD><IMG src="images/TriangleSubhead.gif"  height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>

									<TR>
              <% 
    ArrayList arraylist = null;
    String ASFStringArray[]=null;
    String size=(String)request.getAttribute("asfSummeryMLIDetailsArray_size");
  
    double totalAsf_summary_Amount=0.0d;
    if(size=="0")
    {
    out.println("<tr><td class=\"Heading\" colspan=\"11\"><center>No Data Found</center</td></tr>");
    }
    if(size!=null && size!="0")
    {
    arraylist=(ArrayList)request.getAttribute("asfSummeryMLIDetailsArray");
    %> 
                 <TR class="tableData">
                  
                  <th class="ColumnBackground">SL No</th>
                  <th class="ColumnBackground">Branch Name</th>
                  <th class="ColumnBackground">MLI ID</th>
                  <th class="ColumnBackground">DAN ID</th>
                  <th class="ColumnBackground">CGPAN No</th>
                  <th class="ColumnBackground">UNIT NAME</th>
                  <th class="ColumnBackground">DCI Amount</th>
                   <th class="ColumnBackground">BASE AMOUNT</th>
                  <th class="ColumnBackground">Payment Status</th>
                  <th class="ColumnBackground">Remarks</th>
                  <th class="ColumnBackground">App. Status</th>
                     <th class="ColumnBackground">IGST</th>
                        <th class="ColumnBackground">CGST</th>
                           <th class="ColumnBackground">SGST</th>
                </TR>
               
                <%  
              for(int count=0;count<arraylist.size();count++)
    {
      double asf_amount=0.0d;
      ASFStringArray=new String[13];
      ASFStringArray=(String[])arraylist.get(count);
 	%>
 
                <tr>
				<td class="ColumnBackground1">&nbsp;<%=count+1%></td>
                  <td class="ColumnBackground1">
                    <%= ASFStringArray[8]%>
                  </td>

                  <td class="ColumnBackground1">
                    <%= ASFStringArray[0]%>
                  </td>
                  <td class="ColumnBackground1">
                    <%= ASFStringArray[1]%>
                  </td>
                  <td class="ColumnBackground1">
                    <%= ASFStringArray[2]%>
                  </td>
                  <td class="ColumnBackground1">
                    <%= ASFStringArray[3]%>
                  </td>
                  <td class="ColumnBackground1">
                    <div align="right">
                    <%
                    asf_amount= Float.parseFloat(ASFStringArray[4]);
                    totalAsf_summary_Amount = totalAsf_summary_Amount + asf_amount;%>
                    <%=asf_amount%>
                    </div>
                  </td>
                   <td class="ColumnBackground1">
                    <%= ASFStringArray[12]%>
                  </td>
                  <td class="ColumnBackground1">
                    <%= ASFStringArray[5]%>
                  </td>
                  <td class="ColumnBackground1">
                    <% 
        String remarks=ASFStringArray[6];
        if(remarks==null)
        remarks="&nbsp";
        out.println(remarks);
        %>
                  </td>
           <td class="ColumnBackground1">
                    <%= ASFStringArray[7]%>
                  </td>  
                     <td class="ColumnBackground1">
                    <%= ASFStringArray[9]%>
                  </td>    
                     <td class="ColumnBackground1">
                    <%= ASFStringArray[10]%>
                  </td>    
                     <td class="ColumnBackground1">
                    <%= ASFStringArray[11]%>
                  </td>         
                  </tr>
                <%  }%>
          
          </TR>
          <tr>
          <td class="ColumnBackground">&nbsp;</td>
          <TD width="40%" align="left"  colspan="5" valign="top" class="ColumnBackground">						
											Total
											</TD>
                      
          <TD width="10%" align="left"  valign="top" class="ColumnBackground">
          <div align="right">
                      <%=totalAsf_summary_Amount%>
                      </div>
                      </td>
                      <TD width="50%" align="left" colspan="3" valign="top" class="ColumnBackground">						
											</TD>
                      <%}%>
                       
							</TABLE>
						</TD>
					</TR>
					<TR >
						<TD height="20">
							&nbsp;
						</TD>
					</TR>
					<tr><td colspan="3" align="left" width="700"><font size="2" color="red">Report Generated On : 
					<% java.util.Date loggedInTime=new java.util.Date();
			          java.text.SimpleDateFormat dateFormat1=new java.text.SimpleDateFormat("dd MMMMM yyyy ':' HH.mm");
			          String date1=dateFormat1.format(loggedInTime);
					  out.println(date1);
					  %> hrs.</font></td></tr>
					<TR >
						<TD align="center" valign="baseline" >
							<DIV align="center">
						
								<A href="javascript:history.back()">
									<IMG src="images/Back.gif" alt="Back" width="49" height="37" border="0"></A>
									
									<A href="javascript:printpage()">
									<IMG src="images/Print.gif" alt="Print" width="49" height="37" border="0"></A>
								
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
