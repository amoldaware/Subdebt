 <%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@page import ="java.text.DecimalFormat"%>
<%DecimalFormat decimalFormat = new DecimalFormat("##########.##");%>




<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	
	<html:form action="approvalDateWise.do?method=approvalDateWise" method="POST" enctype="multipart/form-data">
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
												<TD class="Heading" width="42%"><!-- <bean:message key="AsfSummeryMLIwise" /> --> Date Wise Applications Approval</TD>
                        <TD class="Heading" width="50%">&nbsp;<bean:message key="from"/><%=request.getAttribute("fromdate") %>&nbsp;<bean:message key="to"/><%=request.getAttribute("todate") %></TD>
												<TD><IMG src="images/TriangleSubhead.gif"  height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>

					
                                        
                                        
                                       
                                        
                                        
                                        
                                        				
              <% 
    ArrayList arraylist = null;
    String DateWiseApp[]=null;
    String size=(String)request.getAttribute("DateWiseAppArrayListSize");
    
   
    if(size=="0")
    {
    out.println("<tr><td class=\"Heading\" colspan=\"11\"><center>No Data Found</center</td></tr>");
    }
    if(size!=null && size!="0")
    {
    arraylist=(ArrayList)request.getAttribute("DateWiseAppArrayList");
    %> 
                 <TR class="tableData">
                  
                  <th class="ColumnBackground">SL No</th>
                 
                  <th class="ColumnBackground">MEMBER ID</th>
               <th class="ColumnBackground">SCHEME NAME</th>
                  <th class="ColumnBackground">BRANCH NAME</th>
                  <th class="ColumnBackground">BANK REFERENCE NUMBER</th>
                  <th class="ColumnBackground">CGPAN</th>
                   <th class="ColumnBackground">UNIT NAME</th>
                  <th class="ColumnBackground">APPROVED AMOUNT</th>
                  <th class="ColumnBackground">APPROVED DATE</th>
                  <th class="ColumnBackground">APP STATUS</th>
                  
                  
                  
                </TR>
               
                <%  
              for(int count=0;count<arraylist.size();count++)
    {
    
    
      DateWiseApp=new String[9];
      DateWiseApp=(String[])arraylist.get(count);
 	%>
 
                <tr>
				<td class="ColumnBackground1">&nbsp;<%=count+1%></td>
                  

                  <td class="ColumnBackground1">
                    <%= DateWiseApp[0]%>
                  </td>
                  <td class="ColumnBackground1">
                    <%= DateWiseApp[1]%>
                  </td>
                  <td class="ColumnBackground1">
                    <%= DateWiseApp[2]%>
                  </td>
                  <td class="ColumnBackground1">
                    <%= DateWiseApp[3]%>
                  </td>
                  
                  <td class="ColumnBackground1">
                    <%= DateWiseApp[4]%>
                  </td>
                  
                  <td class="ColumnBackground1">

                    <%= DateWiseApp[5]%>
                  </td>
                   <td class="ColumnBackground1">
                    <%= DateWiseApp[6]%>
                  </td>
                  
           <td class="ColumnBackground1">
                    <%= DateWiseApp[7]%>
                  </td>  
                  
                   <td class="ColumnBackground1">
                    <%= DateWiseApp[8]%>
                  </td> 
                  
                  
                  
                  </tr>
                <%  }%>
          
          </TR>
          
          <tr>
          
          <TD width="40%" align="left"  colspan="5" valign="top" class="ColumnBackground">						
											
											</TD>
                      
          <TD width="10%" align="left"  valign="top" class="ColumnBackground">
          <div align="right">
                    
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
