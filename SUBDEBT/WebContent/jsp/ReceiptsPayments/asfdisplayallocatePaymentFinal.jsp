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
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">

<style type="text/css">
.titlebar{font-size:11px;
		  font-family:verdana;
		  color:white;
		  font-weight:bold;
		  width:100%;
		  background-color:Darkslateblue;
		  padding:2px;
		  text-align:left;
		  }
		
.msgbox{border:outset 2px white;
		 background-color:gainsboro;
		 width:500px;
		 height:160px;
		 color:black;
		 padding-left:1px;
		 padding-right:2px;
		 padding-top:1px;
		 font-family: verdana;
		 font-size:11px;
		 text-align:center
			 }
.cross{border:outset 2px white;
		 background-color:gainsboro;
		 left:2px;
		 width:18px;
		 color:black;
 	     font-family:tahoma;
 	     float:right;
 	     margin-top:0px;
 	     padding-left:4px;
 	     padding-bottom:2px;
 	     padding-top:1px;
 	     top:0px;
 	     line-height:10px;
 	     cursor:pointer;
 	     }
.bouton{width:80px;
		 height:25px;
		 border:oustet 2px silver;
		 position:relative;
		 font-size:11px;
		 font-family: tahoma;
		  text-align: center;
		 }

.innerText{width:100%;
			padding-left:30px;
			text-align:left;
			color:black;
			 font-size: 130%;
			}		

#testzone {position:absolute;
           top:100px;
           left:200px;
           }
</style>
</head>
<script>

function submitFinalMakePayment(action)
{
	var count=0;
	for ( var int = 0; int < document.forms[0].elements.length; int++) {
		//alert(int+"length "+document.forms[0].elements[int].checked);
		if(document.forms[0].elements[int].checked==true)
		{
			count=1;
		}
	}
	//alert(document.forms[0].elements.length);
	if(count==1)
	{
		document.getElementById('testzone').style.display='block';
	}
	else
	{
		alert("Please select at least 1 record for process..");
	}
 /* var x;
    if (confirm("Please note that after this the selected RP Nos WILL NOT be available for modification/cancellation ,want to proceed!") == true) {
        x = "You pressed PROCEED!";
        document.forms[0].action=action;
    	document.forms[0].target="_self";
    	document.forms[0].method="POST";
    	document.forms[0].submit();
                  
    } else {
        x = "You pressed CANCEL!";
    }
    document.getElementById("demo").innerHTML = x; */
   // document.getElementById("tAmount").innerHTML=allocatePayment;



//	alert("Please note that after these  selected RP Nos will not be available for modification/cancellation ,want to proceed");

	
}

function makePaymentOK(action)
{
	
	
	 document.forms[0].action=action;
 	document.forms[0].target="_self";
 	document.forms[0].method="POST";
 	document.forms[0].submit();
}

function makePaymentCancel()
{
	document.getElementById('testzone').style.display='none';
}

function raju(action)
{
alert(rajuk);
document.getElementById("tAmount").innerHTML = allocatePayment;
document.getElementById("tAmount1").innerHTML = numCount;
}
</script>


<%

String thiskey = "";
session.setAttribute("CurrentPage","allocatePaymentsAll.do?method=getPendingGFDANsLiveOnline");

%>

<% 

String danDate;
SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
String allocate="N" ;

%>
<script language="javascript" type="text/javascript"> 
</script>
<% session.setAttribute("CurrentPage","asfdisplayallocatePaymentFinal.do?method=asfdisplayallocatePaymentFinal");%>

	

    <html:errors />
		<html:form name = "rpAllocationForm" type="com.cgtsi.actionform.RPActionForm"  action="asfdisplayallocatePaymentFinal.do?method=asfdisplayallocatePaymentFinal" method="POST" >
		<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
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
		
                   <td valign="top" class="HeadingBg"> <div align="center">&nbsp;&nbsp;<strong>Sr.No</strong></div></td>
                  
                  <td valign="top"  class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>Pay Id</strong></div></td>
                  <td valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>Vitrual Account No.</strong></div></td>
                      
                       <td valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;
                      <strong>Amount</strong><br></div></td>
                   
                     <td valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>PayID Created Date
                      </strong><br></div></td>
                       <td valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>For Payment
                      </strong><input type="checkbox" name="selectAll" value="on" 

onclick="selectDeselect(this,1)" alt="Close"> <br></div></td>
                                
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
String name2="";
String name3="";
String mlicomments = "";
String memId = "";
String checkboxKey=null;

%>
                  <%
								int j=0;
                                int k=0;
                                double totalAmount = 0;
                                String strTotalAmount="";
                                
								%>
								
							
								
                           <logic:iterate id="object" name="rpAllocationForm" property="allocatepaymentFinal" indexId="index">
								<%
								
								 RPActionForm rReport = (RPActionForm)object;
								DecimalFormat dec = new DecimalFormat("#0.00");
																						
								%>
								
								
							 <TR>
             <TD width="10%" align="left" valign="top" class="ColumnBackground1"><div align="center"><%=Integer.parseInt(index+"")+1%></div></TD>
                                    
		     <TD width="15%" align="left" valign="top" class="ColumnBackground1"><div align="center"><%=rReport.getPaymentIdF()%></div></TD>
            
             <TD width="15%" align="left" valign="top" class="ColumnBackground1"><div align="center"><%=rReport.getVitrualAcF()%></div></TD>
             <TD width="15%" align="left" valign="top" class="ColumnBackground1"><div align="center"><%=dec.format(rReport.getAmtF())%> </div></TD>
             <TD width="15%" align="left" valign="top" class="ColumnBackground1"><div align="center"><%=rReport.getRPDATEF()%></div></TD>
                   
                   <td  width="10%" align="left" valign="top" class="ColumnBackground1">  
                   <%
                 
                   String PaymentIdF =  rReport.getPaymentIdF();
                   String VitrualAcF =  rReport.getVitrualAcF();
                   Double AmtF =  rReport.getAmtF();
                   String RPDATEF =  rReport.getRPDATEF();
                   int amt=(int)rReport.getAmtF();              
  
                   String completeStr=PaymentIdF+"@"+AmtF;
             
                   String completeStr1=PaymentIdF.concat("@").concat(Integer.toString(amt)).concat("@").concat(VitrualAcF);
                  // completeStr="RP-00006-03-10-2016@4803";
          //         System.out.println("completeStr "+completeStr);
                   System.out.println("completeStr1 "+completeStr1);
                    name="allocationPaymentFinalSubmit("+completeStr1+")"; 
            //       name="allocationPaymentFinalSubmit(RP-00003-03-10-2016@166660000303102016@3276)";
                     name2="allocationPaymentFinalSubmit2("+AmtF+")";
                   //name3="allocationPaymentFinalSubmit3("+RPDATEF+")";
                    String jsMethodDef="calcAllocatePayment("+AmtF+","+j+")";
                    System.out.println("jsMethodDef "+jsMethodDef);
               
                   %>
         	       <div><html:checkbox name="rpAllocationForm" property="<%=name%>" onclick="<%=jsMethodDef%>" value="<%=name%>"/></div>
         	         
         	              
         	          </td>                
               
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
											<div align="center" id="tAmount"></div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">
											Count
											</TD>	
	 										<TD width="10%" align="left" valign="top" class="ColumnBackground1">
	 										<div align="center" id="tAmount1"></div></TD> 
            	        
         	       					
												        
          </tr>
          
          
		 </TABLE>	
		     
              <tr align="center" valign="baseline">
           		 <td colspan="4"> 
           		 <div align="center"> 
		         <A href="javascript:submitFinalMakePayment('asfdisplayallocatePaymentFinalSubmit.do?method=asfdisplayallocatePaymentFinalSubmit')"><IMG src="images/Next.gif" alt="Next" width="49" height="37" border="0"></A>
                <a href="javascript:document.rpAllocationForm.reset()"><img src="images/Reset.gif"  alt="Reset" width="49" height="37" border="0" ></a>
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

<div id='testzone' class="msgbox" style="display:none;" >
 <div class="titlebar">
  <div class="cross" onclick="document.getElementById('testzone').style.display='none';">X</div> Make Payment
 </div>
 <br/>
<br/>
<br/>
<div class='innerText'>
Please note that after this the selected <b><u><font color="blue">RP No.s WILL NOT</font></u></b> be available for modification/cancellation ,want to proceed!
</div>
<br/>
<br/>
<br/>
<div>
<input type="button" id="trap" value="OK" class="bouton" onclick="makePaymentOK('displayallocatePaymentFinalSubmit.do?method=displayallocatePaymentFinalSubmit');" style="left:0px;"/>

<input type="button" id="trap" value="CANCEL" class="bouton" style="left:5px;" onclick="makePaymentCancel();"/>
</div>

</div>
	</html:form>
