<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","newMonthlyProgressReport.do?method=newMonthlyProgressReport");%> 

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="newMonthlyProgressReport.do?method=newMonthlyProgressReport" method="POST" enctype="multipart/form-data">
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
												<TD class="Heading" width="42%"><bean:message key="monthlyProgressReport" /></TD>
                        <TD class="Heading" width="50%">&nbsp;<bean:message key="from"/><bean:write name="rsForm" property="dateOfTheDocument38"/>&nbsp;<bean:message key="to"/><bean:write name="rsForm" property="dateOfTheDocument39"/></TD>
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
    String MonthlyStringArray[]=null;
    String size=(String)request.getAttribute("monthlyProgressReportArray_size");
   
    if(size=="0")
    {
    out.println("<tr><td class=\"Heading\" colspan=\"11\"><center>No Data Found</center</td></tr>");
    }
    if(size!=null && size!="0")
    {
    arraylist=(ArrayList)request.getAttribute("monthlyProgressReportArray");
    %> 
                 <TR class="tableData">
                  <th class="ColumnBackground" align="center">&nbsp;&nbsp;Year</th>
                  <th class="ColumnBackground">No. of Cases</th>
                  <th class="ColumnBackground">No Of Emp Generation</th>
                  <th class="ColumnBackground">Projected Sales TurnOver(in Rs. Lakh)</th>
                  <th class="ColumnBackground">Projected Export(in Rs. Lakh)</th>
                 
                  
                </TR>
                <%
							
	 for(int count=0;count<arraylist.size();count++)
    {
      MonthlyStringArray=new String[4];
     
      MonthlyStringArray=(String[])arraylist.get(count);
      if(count==0)
      {
 	  %>
                <tr>
                   <td class="ColumnBackground">
                    &nbsp;&nbsp;Current Year
                  </td>
                  <td class="ColumnBackground1">
                    <div align="right">
					<%
          String colname=MonthlyStringArray[0];
          if (colname==null)
          colname="&nbsp";
          out.println(colname);
          %>
					</div>
                  </td>
                  <td class="ColumnBackground1">
				   <div align="right">
                    <%String colname1=MonthlyStringArray[1];
          if (colname1==null)
          colname1="&nbsp";
          out.println(colname1);%>
					</div>
                  </td>
                  <td class="ColumnBackground1">
				   <div align="right">
                    <%
                    String colname2=MonthlyStringArray[2];
          if (colname2==null)
          colname2="&nbsp";
          out.println(colname2);
          %>
					</div>
                  </td>
                  <td class="ColumnBackground1">
				   <div align="right">
           <%String colname3=MonthlyStringArray[3];
          if (colname3==null)
          colname3="&nbsp";
          out.println(colname3);
          %>
					</div>
                  </td>
				  
             </TR>
             <%
             }
             else
             {
             %>
                  <TR>
                   <td class="ColumnBackground">
                    &nbsp;&nbsp;Previous Year
                  <td class="ColumnBackground1">
                    <div align="right">
					<%
          String colname=MonthlyStringArray[0];
          if (colname==null)
          colname="&nbsp";
          out.println(colname);
          %>
					</div>
                  </td>
                  <td class="ColumnBackground1">
				   <div align="right">
                    <%String colname1=MonthlyStringArray[1];
          if (colname1==null)
          colname1="&nbsp";
          out.println(colname1);%>
					</div>
                  </td>
                  <td class="ColumnBackground1">
				   <div align="right">
                    <%
                    String colname2=MonthlyStringArray[2];
          if (colname2==null)
          colname2="&nbsp";
          out.println(colname2);
          %>
					</div>
                  </td>
                  <td class="ColumnBackground1">
				   <div align="right">
           <%String colname3=MonthlyStringArray[3];
          if (colname3==null)
          colname3="&nbsp";
          out.println(colname3);
          %>
					</div>
                  </td>
                  </tr>
                <%  }
        }
          }%>
          </TR>
							</TABLE>
						</TD>
					</TR>
					<TR >
						<TD height="20" >
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
						
								<A href="javascript:submitForm('newMonthlyReport.do?method=newMonthlyReport')">
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
