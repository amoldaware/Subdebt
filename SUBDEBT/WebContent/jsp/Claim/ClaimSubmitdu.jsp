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

<script language="javascript" type="text/javascript"> 

var request; 

function submitForm1()
{

//	alert(document.getElementById('jjj').value);

	//alert("dfdfdfdfdf00");
	
	document.getElementsByName('check');
	
	var fname1 = document.getElementById('kkk').value;
	alert("comments is----"+fname1);

	var fname = document.getElementById('ppp').value;

	alert("radion value-lenth---"+fname.length);

	alert("radion value---- is"+fname);


}

function raju(name)
{

	 var clmrefno;
	 clmrefno=name.substring(21,name.length-1);
	
	    if(clmrefno.selectedIndex==0){ 	 
	      	return; 	 
	            } 	
	    urls="ChecklistValidation.do?method=ChecklistValidation&clmreno="+clmrefno; 
	  //alert("rajaaa"+urls);
		var xmlhttp;
	//var keys=document.dummy.sele.value
	// var urls="http://www.java4s.com:2011/On_select_from_database_dropdown/db_fetch.jsp?ok="+keys
	if (window.XMLHttpRequest)
	{// code for IE7+, Firefox, Chrome, Opera, Safari
	xmlhttp=new XMLHttpRequest();
	}
	else
	{// code for IE6, IE5
	xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function()
	{
		//alert(xmlhttp.readyState);
	if (xmlhttp.readyState==4 || xmlhttp.readyState==200)
	{
		//alert('Hi');
		var some=xmlhttp.responseText;
		
		alert(some);
	}
	};
	xmlhttp.open("GET",urls,true);
	xmlhttp.send();
}
</script>
 

<% session.setAttribute("CurrentPage","displayClaimProcessingSubmitDU.do?method=displayClaimProcessingSubmitDU");%>
<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>

	<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
    <html:errors />
	<html:form action="displayClaimProcessingSubmitDU.do?method=displayClaimProcessingSubmitDU" method="POST" enctype="multipart/form-data">
	<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	   <TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><img src="images/ClaimsProcessingHeading.gif" width="131" height="25"></TD>
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
		
                   <td valign="top" class="HeadingBg"> <div align="center">&nbsp;&nbsp;<strong>Sr.<br>
                      No</strong></div></td>
                  
                  <td valign="top"  class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>Date Of<br>Claim Intiation</strong></div></td>
                  <td valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>Claim<br> Reference<br>No.</strong></div></td>
                      
                       <td valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;
                      <strong>Branch<br>name</strong><br></div></td>
                   
                     <td valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>Unit<br>Name
                      </strong><br></div></td>
                 
                  <td valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>Guarantee<br>
                      Approved<br>Amount</strong><br></div></td>
                      
                       <td valign="top"  class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>View<br>D/U##<br>
                      </strong></div></td>
                  
                  <td align="center" valign="top" class="HeadingBg"> <div align="center">
                    &nbsp;&nbsp;<strong>I Certify the<br>Content of D/U@</strong>
                    </div></td>	
                    
                     <td valign="top"  class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>Checker Remarks<br>
                      </strong></div></td>	  
		        </tr>
		      
<%
String dateofclaim = null;
String claimrefnumber = "";
String branchName="";
String unitName = ""; 
double guaranteeapprovedamount=0.0;
String viewDu="";
String name="";
String name1="";
String thiskey = "";
String mlicomments = "";
String memId = "";


%>
<script type="text/javascript">
    document.getElementById('contactText').setAttribute('maxlength', '10');
</script>
                           <%
								int j=0;
                                int k=0;
                                
								%>
								
							
								
                           <logic:iterate id="object" name="cpTcDetailsForm" property="claimDandU" indexId="index">
								<%
								
								ClaimActionForm   cReport = (ClaimActionForm)object;
																						
								%>
								
								
							 <TR>
             <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%=Integer.parseInt(index+"")+1%></TD>
		
             <TD width="15%" align="left" valign="top" class="ColumnBackground1"><%=cReport.getDateOfTheDocument36()%></TD>
                  
         	  <TD width="10%" align="left" valign="top" class="ColumnBackground1"><div align="right">
                  <%
                    String ClmreferenceNumber =  cReport.getClmreferenceNumber();
                    String url = "displayClmRefNumberDetail.do?method=displayClmRefNumberDetail&clmRefNumber="+ClmreferenceNumber;%>
         	      <html:link href="<%=url%>"><%=ClmreferenceNumber%></html:link>                
                  </div>
                  </TD>
                <html:hidden property="ClmreferenceNumber"  value="<%=ClmreferenceNumber%>" />
	             <TD width="10%" align="left" valign="top" class="ColumnBackground1">						
	             <%=cReport.getBranchname()%>
	           </TD>
	             <TD width="10%" align="left" valign="top" class="ColumnBackground1">						
	             <%=cReport.getUnitname()%>
	           </TD>
	             <TD width="10%" align="center" valign="top" class="ColumnBackground1">						
	             <%=cReport.getGuranteeApprovedamt()%>
	           </TD>
	           
	           <TD width="10%" align="left" valign="top" class="ColumnBackground1">	
				 <A href="javascript:submitForm('claimDeclarationView.do?method=claimDeclarationView&clmrefnum=<%=ClmreferenceNumber%>')">View</A>
	            </TD>
			
					
	            <TD width="15%" align="left" valign="top" class="ColumnBackground1">
	            <%name="duCertifyDecisionYes("+ClmreferenceNumber+")";
	          
	          //  out.println("testname");
	            //out.println(name);
	            %>
	            <%name1="duCertifyDecisionNo(key-"+k+")";
	            
	           // out.println("testname1");
	            String raju=cReport.getClmreferenceNumber();
	            //out.println("raju"+raju);
	            
	            %>
	            <html:radio name="cpTcDetailsForm" value="Y" property="<%=name%>" onclick="raju(this.name);" styleId='ppp' disabled="disabled">ACCEPT</html:radio></br>
	            <html:radio name="cpTcDetailsForm" value="N" property="<%=name%>" onclick="enableUnitValue(N);raju(this.name);"  styleId='ppp' disabled="disabled">RETURN</html:radio>
	           	</TD>
	           	
	           	 <%
	           	 
				  mlicomments = "comments("+ClmreferenceNumber+")";
				  %>	
	           
	        			 <TD width="10%" align="left" valign="top" class="ColumnBackground1">				
	              <html:textarea styleId="contactText" property="<%=mlicomments%>" name="cpTcDetailsForm"  styleId='kkk' />	
	           </TD>
	           	    
               </TR>
              
               <%j++; %>
               <%k++; %>
			  </logic:iterate>	
	
			 	
               <tr>

                                             <TD width="10%" align="center" valign="top" class="ColumnBackground1">						
											</TD>
											
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						

											</TD>
											
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						

											</TD>
											
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">			
											
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">			
											
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">			
											
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">			
											
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">			
											
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">			
											
											</TD>
												        
          </tr>
		 </TABLE>	
		     
              <tr align="center" valign="baseline">
           		 <td colspan="4"> 
           		 <div align="center"> 
		         <a href="javascript:submitForm('displayClaimProcessingSubmitDUDetails.do?method=displayClaimProcessingSubmitDUDetails')"><IMG src="images/Submit.gif" alt="Save" width="49" height="37" border="0"></a>
                <a href="javascript:document.cpTcDetailsForm.reset()"><img src="images/Reset.gif"  alt="Reset" width="49" height="37" border="0"></a>
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