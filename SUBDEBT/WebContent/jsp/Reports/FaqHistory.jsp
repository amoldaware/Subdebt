<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>


 
 
 
 
 
  <TABLE width="725" border="0" cellpadding="0" cellspacing="0">
    <html:errors />
	<html:form action="showApplicationStatus.do?method=showApplicationStatus" method="POST" enctype="multipart/form-data">
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
										 
            
	
			<tr>
		        
		      
             <td valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>Application Ref NO
                      </strong><br></div></td>
                 
                  <td valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>CGPAN</strong><br></div></td>
                      
                       <td valign="top"  class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>APP STATUS
                      </strong></div></td>
                  
                  <td align="center" valign="top" class="HeadingBg"> <div align="center">
                    &nbsp;&nbsp;<strong>APP REMARKS</strong>
                    </div></td>
                    
                        
               
                      
                        
		         </tr>
		      
        
         	  
         	  
         	        
         	        
         	       
								 
			 
            
            
            
            	
            
             
             <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%= request.getAttribute("APP_REF_NO")%></TD>
		
             <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%= request.getAttribute("CGPAN")%></TD>
                  
         	  <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%= request.getAttribute("APP_STATUS")%></TD>
         	  
         	  <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%= request.getAttribute("APP_REMARKS")%></TD>
          
         	  
         	   
         	  
         	        
 </TD>
         	        
             
         	 
                
              
         
                
                
            </TR>
		 </TABLE>	
		     
		       <tr align="center" valign="baseline">
           		 <td colspan="4"> 
           		 <div align="center"> 
		         <a href="javascript:submitForm('applicationStatus.do?')">
		        <IMG src="images/Back.gif" alt="Back" width="49" height="37" border="0"></A>
		        
		         <A href="javascript:printpage()">
									<IMG src="images/Print.gif" alt="Print" width="49" height="37" border="0"></A>
                
                
                 
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

 <html>
<head>
<title>HTML Tables</title>
</head>
<body>
<table border="1">
<tr>
<td>NE</td>
<td>Pending for approval </td>
</tr>
<tr>
<td>AP</td>
<td>Application Approved</td>
</tr>
<tr>
<td>RE</td>
<td>Application Rejected</td>
</tr>
<tr>
<td>MO</td>
<td>Application modified and pending for approval </td>
</tr>
<tr>
<td>EN</td>
<td>Enhanced Application pending for approval of enhanced amount</td>
</tr>
<tr>
<td>CL</td>
<td>Application Closed</td>
</tr>
<tr>
<td>EX</td>
<td>Application Expired</td>
</tr>
 
</table>
</body>
</html>
 



	</html:form>
	</TABLE> 
	
	
	
 


