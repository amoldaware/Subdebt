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


var allocatePayment=0;





</script>

<% session.setAttribute("CurrentPage","displayallocatePaymentModifySubmit.do?method=displayallocatePaymentModifySubmit");%>
<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>
<body onload="disableBackButton()">

	<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
    <html:errors />
		<html:form action="displayallocatePaymentModifySubmit.do?method=displayallocatePaymentModifySubmit" method="POST" >
			<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/ReceiptsPaymentsHeading.gif" width="121" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
							<table width="661" border="0" cellspacing="1" cellpadding="0">
							 <TR>
								<TD colspan="7">
									<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										<TR>
											
										</TR>
										<TR>
											<TD colspan="6" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
										</TR>
									</TABLE>
								</TD>
							</TR>
					<tr>
		
                   <td class="ColumnBackground"> <div align="center">&nbsp;&nbsp;<strong>Sr.No</strong></div></td>
                  
                  <td class="ColumnBackground"><div align="center">&nbsp;&nbsp;<strong>Pay Id</strong></div></td>
                  <td class="ColumnBackground"><div align="center">&nbsp;&nbsp;<strong>Vitrual Account No.</strong></div></td>
                      
                       <td class="ColumnBackground"><div align="center">&nbsp;&nbsp;
                      <strong>Amount</strong><br></div></td>
                   
                     <td class="ColumnBackground"><div align="center">&nbsp;&nbsp;<strong>PayID Created Date
                      </strong><br></div></td>
                                             
                     <td class="ColumnBackground" ><div align="center">&nbsp;&nbsp;<strong>For Cancellation<br>
                     <html:checkbox property="selectAll" alt="Close" name="rpAllocationForm" onclick="selectDeselect(this,1)"/> 
                     <br></div>
                      
                      
                      </td>
                      
                 
                 
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
String checkboxKey=null;


%>
                           <%
								int j=0;
                                int k=0;
                                
								%>
								
							
								
                           <logic:iterate id="object" name="rpAllocationForm" property="allocatepaymentmodify" indexId="index">
								<%
								
								 RPActionForm rReport = (RPActionForm)object;
								DecimalFormat dec = new DecimalFormat("#0.00");
								
																						
								%>
								
								
							 <TR>
             <TD width="10%" align="left" valign="top" class="ColumnBackground1"><div align="center"><%=Integer.parseInt(index+"")+1%></div></TD>
             
              <TD width="15%" align="left" valign="top" class="ColumnBackground1"><div align="center">
                  <%
                    String PaymentId =  rReport.getPaymentId1();
                    String url = "displayPaymentIdDetail.do?method=displayPaymentIdDetail&PaymentId="+PaymentId;%>
         	      <html:link href="<%=url%>"><%=PaymentId%></html:link>                
                  </div>
                  </TD>
               
		
             
             <TD width="15%" align="left" valign="top" class="ColumnBackground1"><div align="center"><%=rReport.getVaccno()%></div></TD>
             <TD width="15%" align="left" valign="top" class="ColumnBackground1"><div align="center"><%=dec.format(rReport.getAmmount1())%> </div></TD>
             <TD width="15%" align="left" valign="top" class="ColumnBackground1"><div align="center"><%=rReport.getPayidcreateddate()%></div></TD>
            <TD width="15%" align="left" valign="top" class="ColumnBackground1"><div align="center">
           
            <% name="allocationPaymentYes("+PaymentId+")";
          
            Double amount =  rReport.getAmmount1();
            int amt=(int)rReport.getAmmount1(); 
            
            String completeStr=PaymentId+"@"+amount;
            
            String completeStr1=PaymentId.concat("@").concat(Integer.toString(amt));
            
            name="allocationPaymentYes("+completeStr1+")"; 
        
            System.out.println("completeStr "+completeStr);
            
            String jsMethodDef="calcAllocatePayment1("+amount+","+(j+1)+")";
           // System.out.println("jsMethodDef ==="+jsMethodDef);
            
            
            %>
            
            
			<html:checkbox name="rpAllocationForm" property="<%=name%>" onclick="<%=jsMethodDef%>" value="<%=name%>"/></div>
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
              Total                            
											</TD>
											
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">			
									        <div align="center" id="tAmount" ></div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">
		     Count	
											</TD>			
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">
											<div align="center" id="tAmount1" ></div>
											</TD>
											
												        
          </tr>
		 </TABLE>	
		     
   <tr align="center" valign="baseline">
           		 <td colspan="4"> 
           		 <div align="center"> 
		         <a href="javascript:submitForm('displayallocatePaymentModifySubmitDetails.do?method=displayallocatePaymentModifySubmitDetails')"><IMG src="images/Submit.gif" alt="Save" width="49" height="37" border="0"></a>
                <a href="javascript:document.rpAllocationForm.reset();resetMakePaymentDetails()"><img src="images/Reset.gif"  alt="Reset" width="49" height="37" border="0"></a>
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
 


	</html:form>
	</TABLE>
	</body>