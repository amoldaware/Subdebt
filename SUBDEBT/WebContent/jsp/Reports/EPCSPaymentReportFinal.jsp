<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.cgtsi.actionform.*"%>
<%@page import="java.util.HashSet"%>
<%@page import ="java.text.SimpleDateFormat"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@page import ="java.text.DecimalFormat"%>
<%@ page import="com.cgtsi.claim.ClaimConstants"%>
<% session.setAttribute("CurrentPage","epcsPaymentReportInput.do?method=epcsPaymentReportInput");%>



	<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
    <html:errors />
	<html:form action="epcsPaymentReportInput.do?method=epcsPaymentReportInput" method="POST" enctype="multipart/form-data">
	<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
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
									
                        
                      </TR>
           		 </TABLE>
            
	
		       	<tr>
		
                     <td valign="top" class="HeadingBg"> <div align="center">&nbsp;&nbsp;<strong>Sr.No</strong></div></td>
                  
                  <td valign="top"  class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>MLI ID</strong></div></td>
                
                  <td valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>Pay Id</strong></div></td>
                                             
                  <td valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>VirtualAccountNo.</strong><br></div></td>
                  
                  <td valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>Payment Date</strong><br></div></td>
                 
                  <td valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>Payment Credit Date</strong><br></div></td>
                  
                  <td valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>Amount<br></strong><br></div></td>
                 
                   <td valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>DanType<br></strong><br></div></td>
                   
                    <td valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>DanStatus<br></strong><br></div></td>
                    
                    <td valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>Payment<br>Status</strong><br></div></td>
		        </tr>
		        


   <%
								int j=0;
                                int k=0;
                                
								%>
		        
		      <logic:iterate id="object" name="rsForm" property="PaymentReportStatus" indexId="index">
					<%
	
					 com.cgtsi.reports.GeneralReport gReport =  (com.cgtsi.reports.GeneralReport)object;
												
					DecimalFormat dec = new DecimalFormat("#0.00");		
								
								%>			
								
								
							 <TR>
							   
							   
			
        	 <TD width="10%" align="right" valign="top" class="ColumnBackground1"><%=Integer.parseInt(index+"")+1%></div></td>
        	  
        	 	<TD width="10%" align="left" valign="top" class="ColumnBackground1"><%=gReport.getMliIds()%></div></td>
              
             <TD width="25%" align="left" valign="top" class="ColumnBackground1"><%=gReport.getPayids()%></div></TD>
             <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%=gReport.getVaccno()%></div></TD>
             <TD width="20%" align="left" valign="top" class="ColumnBackground1"><%=gReport.getPaymentDate()%></TD>
             <TD width="20%" align="left" valign="top" class="ColumnBackground1"><%=gReport.getPaymentcreditDate()%></TD>
             <TD width="20%" align="right" valign="top" class="ColumnBackground1"><%=dec.format(gReport.getAmounts())%></TD>
             <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%=gReport.getDantype()%></TD>
             <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%=gReport.getDanstatus()%></TD>
             <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%=gReport.getPaymentstatus()%></TD>
          
             
            </TR>
          
              
              <%j++; %>
              <%k++; %>
			  </logic:iterate>	
		     			 	
     

                                             <TD width="10%" align="center" valign="top" class="ColumnBackground1">						
											</TD>
											
											<TD width="10%" align="right" valign="top" class="ColumnBackground1">						

											</TD>
											
											<TD width="10%" align="right" valign="top" class="ColumnBackground1">						

											</TD>
											
											<TD width="10%" align="right" valign="top" class="ColumnBackground1">			
											
											</TD>
											<TD width="10%" align="right" valign="top" class="ColumnBackground1">			
											
											</TD>
											<TD width="10%" align="right" valign="top" class="ColumnBackground1">			
											
											</TD>
											<TD width="10%" align="right" valign="top" class="ColumnBackground1">			
											
											</TD>
										
											<TD width="10%" align="right" valign="top" class="ColumnBackground1">			
											
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">			
											
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">			
											
											</TD>
											
											
											
											
											
											
										
											
												        
          </tr>
          <tr>
                          <%
								int l=0;
                                int m=0;
                                
								%>
		        
		      <logic:iterate id="object" name="rsForm" property="PaymentReportStatuscount" indexId="index">
					<%
	
					 com.cgtsi.reports.GeneralReport gReport =  (com.cgtsi.reports.GeneralReport)object;
												
					DecimalFormat dec = new DecimalFormat("#0.00");		
								
				%>			
								
			                     <TD width="10%" align="center" valign="top" class="ColumnBackground1">						
												 
											</TD>
											
											   <TD width="10%" align="right" valign="top" class="ColumnBackground1">Total Payids</TD>
											   <TD width="20%" align="left" valign="top" class="ColumnBackground1"><%=gReport.getCount()%></TD>		
											      <TD width="10%" align="center" valign="top" class="ColumnBackground1"></TD>		
											         <TD width="10%" align="center" valign="top" class="ColumnBackground1">	</TD>	
											           	
											<TD width="10%" align="center" valign="top" class="ColumnBackground1">						
                                       Total Amount
					</TD>
						
          
        <TD width="20%" align="left" valign="top" class="ColumnBackground1"><%=dec.format(gReport.getToatlAmounts())%></TD>
        
         <TD width="10%" align="center" valign="top" class="ColumnBackground1">	
		  <TD width="10%" align="center" valign="top" class="ColumnBackground1">
		    <TD width="10%" align="center" valign="top" class="ColumnBackground1">
    
              <%l++; %>
              <%m++; %>
			  </logic:iterate>
			  
			         
							
											        </tr>
              
			  	
		 </TABLE>	
		     
              <tr align="center" valign="baseline">
           		 <td colspan="4"> 
           		 <div align="center"> 
		         <A href="javascript:history.back()">
				<IMG src="images/OK.gif" alt="Back" width="49" height="37" border="0"></A>
                
		      </div>
		      </td>
		     </tr>
		  	</table>     
		

	<TD width="20" background="images/TableVerticalRightBG.gif">
				&nbsp;
			</TD>
	     </tr>
		
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
 
</TABLE>

	</html:form>
	</TABLE>