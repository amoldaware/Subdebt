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
<style>
#dtable4{
   margin-top: -2000px;
}
</style>
<% session.setAttribute("CurrentPage","monthwiseData.do?method=monthwiseData");%>
<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>

	<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
    <html:errors />
	<html:form action="monthwiseData.do?method=monthwiseData" method="POST" enctype="multipart/form-data">
	<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
	   <TR> 
			<TD width="5" align="left" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><img src="images/ClaimsProcessingHeading.gif" width="131" height="25"></TD>
			<TD width="5" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="5" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
						
			<TABLE id="dtable1" align="left" width="15%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="10"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<tr>									
                        
                      </TR>
           		 </TABLE>            
	
			<tr>		
                                   
                  <td valign="top" class="HeadingBg"><div align="center"><strong>SR.NO</strong></div></td>
                      
                  <td valign="top" class="HeadingBg"><div align="center"><strong>MEM_BANK_NAME</strong></div></td>
                      
                       <td valign="top" class="HeadingBg"><div align="center">
                      <strong>NOS</strong><br></div></td>
                                                    
                  <td valign="top" class="HeadingBg"><div align="center"><strong>Guarantee<br>
                      Approved<br>Amount</strong><br></div></td>
                        
		      
		    

                           <%
								int j=0;
                                int k=0;
                                
								%>
								
							
								
                         <logic:iterate id="object" name="cpTcDetailsForm" property="claimLodgedReport" indexId="index">
					<%
								
								ClaimActionForm   cReport = (ClaimActionForm)object;
							
								
								%>			
								
								
							 <TR>
             <TD align="center" valign="top" class="ColumnBackground1"><%=Integer.parseInt(index+"")+1%></TD>
             
                  <TD   valign="top" class="ColumnBackground1"><%=cReport.getBankName()%></TD>
		
             <TD valign="top" class="ColumnBackground1"><%=cReport.getUnitNAME()%></TD>
                    	
               
	             <TD valign="top" class="ColumnBackground1"><%=cReport.getMemberids()%> </TD>
	           
	             
	           	    
               </TR>
              
               <%j++; %>
               <%k++; %>
			  </logic:iterate>
			  <tr>

                                             <TD width="5%" align="center" valign="top" class="ColumnBackground1">						
											</TD>
											
											<TD width="5%" align="left" valign="top" class="ColumnBackground1">						

											</TD>
											
											<TD width="5%" align="left" valign="top" class="ColumnBackground1">						

											</TD>
											
											<TD width="5%" align="left" valign="top" class="ColumnBackground1">			
											
											</TD>
											    
          </tr>	
		
		</table>
	            
                  <TABLE id="dtable2"  align="left" width="20%" border="0" cellspacing="1" cellpadding="1" > 
                  <tr>                
                  
                      
                  <td valign="top" class="HeadingBg"><div align="center"><strong>MEM_BANK_NAME D</strong></div></td>
                      
                       <td valign="top" class="HeadingBg"><div align="center">
                      <strong>NOS</strong><br></div></td>
                                                    
                  <td valign="top" class="HeadingBg"><div align="center"><strong>Guarantee<br>
                      Approved<br>Amount</strong><br></div></td>
                      
                      
		        </tr>
			  			
                         <logic:iterate id="object1" name="cpTcDetailsForm" property="monthwisedata" indexId="index">
					<%
								
								ClaimActionForm   cReport = (ClaimActionForm)object1;
							
								
								%>			
								
								
							 <TR>
             
             
                  <TD valign="top" class="ColumnBackground1"><%=cReport.getCgpan()%></TD>
		
             <TD valign="top" class="ColumnBackground1"><%=cReport.getAmountclaimed()%></TD>
                    	
               
	             <TD  align="left" valign="top" class="ColumnBackground1"><%=cReport.getCgPAN()%> </TD>
	        
	              <%j++; %>
               <%k++; %>
                </TR>       
			  </logic:iterate>	    
	           	    
                  
                        
           
         
		 </TABLE><!--	
		Deepak *
		--><TABLE id="dtable3" width="15%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="10"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<tr>
									
                        
                      </TR>
           		 </TABLE>
            
	
			<tr><!--
		
                                   
                  <td valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>SR.NO</strong></div></td>
                      
                  --><td valign="top" class="HeadingBg"><div align="center"><strong>MEM_BANK_NAME</strong></div></td><!--
                      
                       <td valign="top" class="HeadingBg"><div align="center">
                      <strong>NOS</strong><br></div></td>
                                                    
                  --><td valign="top" class="HeadingBg"><div align="center"><strong>Guarantee<br>
                      Approved<br>Amount</strong><br></div></td>
                            <%
								int l=0;
                                int m=0;
                                
								%>
										
                         <logic:iterate id="object" name="cpTcDetailsForm" property="claimLodgedReport" indexId="index">
					<%
								
								ClaimActionForm   cReport = (ClaimActionForm)object;
					%>			
				 <TR>
            
             
                  <TD  align="left" valign="top" class="ColumnBackground1"><%=cReport.getBankName()%></TD>
		
             <!--<TD  align="left" valign="top" class="ColumnBackground1"><%=cReport.getUnitNAME()%></TD>
                    	
               
	             --><TD align="left" valign="top" class="ColumnBackground1"><%=cReport.getMemberids()%> </TD>
	           
	             
	           	    
               </TR>
              
               <%l++; %>
               <%m++; %>
			  </logic:iterate>
			  <tr>

                                             <TD width="5%" align="center" valign="top" class="ColumnBackground1">						
											</TD>
											
											<TD width="5%" align="left" valign="top" class="ColumnBackground1">						

											</TD>
											
											<TD width="5%" align="left" valign="top" class="ColumnBackground1">						

											</TD>
											
											<TD width="5%" align="left" valign="top" class="ColumnBackground1">			
											
											</TD>
											    
          </tr>	
		
		</table>
	            
                  <TABLE id="dtable4" align="right" width="20%" border="0" cellspacing="1" cellpadding="1" > 
                  <tr>                
                  
                      
                  <td valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>MEM_BANK_NAME</strong></div></td>
                      
                       <td valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;
                      <strong>NOS</strong><br></div></td>
                                                    
                  <td valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>Guarantee<br>
                      Approved<br>Amount</strong><br></div></td>
                      
                      
		        </tr>
		          <%
								int n=0;
                                int o=0;
                                
								%>
			  			
                         <logic:iterate id="object1" name="cpTcDetailsForm" property="monthwisedata" indexId="index">
					<%
								
								ClaimActionForm   cReport = (ClaimActionForm)object1;
							
								
								%>			
								
								
							 <TR>
             
             
                  <TD  align="left" valign="top" class="ColumnBackground1"><%=cReport.getCgpan()%></TD>
		
             <TD align="left" valign="top" class="ColumnBackground1"><%=cReport.getAmountclaimed()%></TD>
                    	
               
	             <TD align="left" valign="top" class="ColumnBackground1"><%=cReport.getCgPAN()%> </TD>
	        
	              <%n++; %>
               <%o++; %>
                </TR>       
			  </logic:iterate>	    
	           	    
                  
                        
           
         
		 </TABLE>	
		
		 
		 </TD></TR>    
                
		
   <tr align="center" valign="baseline">
           		 <td colspan="4"> 
           		 <div align="center"> 
		         
                 <a href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>"><img src="images/OK.gif" width="40" height="37" border="0"></a>
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