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
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<head>
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

function popupwindow(val) {
	debugger;
    $("#openModal.content").load('displayallocatePaymentFinalSubmit.do?method=displayCgpanDetails&val='+val , function(){
        $("#openModal").show();
    })

}

function calcAllocatePaymentNew(amount,name)
{
var checkBoolean = document.getElementById("chkId"+name).checked;
//alert("checkBoolean>>>>>>>>>>>>>>"+checkBoolean);
if(checkBoolean==true)
	{
	
	allocatePayment = ((Number(allocatePayment)) + (Number(amount)));
	
	numCount++;
	}
else if(checkBoolean==false)
	{
	allocatePayment = ((Number(allocatePayment)) - (Number(amount)));
	//alert("2 else=="+allocatePayment);
	numCount--;
	}
document.getElementById("tAmount").innerHTML = allocatePayment.toFixed(2);
document.getElementById("tAmount1").innerHTML = numCount;
	
}
function submitFinalMakePayment(action)
{
	var count=0;
	var a = document.getElementsByName('UTRNo');
	var len = a.length;
	for ( var int = 0; int < len; int++) {
		if(document.getElementById("chkId"+int).checked==true)
		{
			var a = document.getElementById('UTRNo'+int).value;
			var b = document.getElementById('UTRDate'+int).value;			
			if(a.length!=0 && b.length!=0 ){
				count=1;
			}	
			else{
				count=2;
			}
		}
	}
	if(count==1)
	{
		//document.getElementById('testzone').style.display='block';
		makePaymentOK('displayallocatePaymentFinalSubmit.do?method=displayallocatePaymentFinalSubmit');
	}
	else if(count==2)
	{
		alert("Please give details for selected RP numbers");
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

function showDetails(val){
	 var myWindow = window.open("displayallocatePaymentFinal.do?method=displayCgpanDetails&val="+val, "_blank", "toolbar=yes,scrollbars=yes,resizable=yes,top=500,left=500,width=600,height=200");
}


function isFutureDate(val){
		var idate = document.getElementById('UTRDate'+val).value;
	    var today = new Date().getTime(),
	        idate = idate.split("/");
		
	    idate = new Date(idate[2], idate[1] - 1, idate[0]).getTime();
	    if ((today - idate) < 0){
	    	alert ("You cannot enter Future Date!");
	    }
	}

function setHiddenUtrVal(elem){
	    var value = elem.value;
	    var id    = elem.id;
	   
	var hidVal = document.getElementById("totalUTRNoDate").value;
	if(value!=null && id!=null)
		{
		 hidVal = hidVal.concat(id.concat('@', value+','));	
		 document.getElementById("totalUTRNoDate").value = hidVal;
		}
	}
	
	function checkvalidate(elem){
		 var value = elem.value;
		 var id    = elem.id;
		 var letters = /^[0-9a-zA-Z]+$/;
		 
		 if(value.length>22){
			 alert("You cannot enter more than 22 characters!");
			 return true;
		 }
		 if(value.match(letters))
		 {
		 }
		 else
		 {
		 alert('Please input alphanumeric characters only');
		 return false;
		 }
		 setHiddenUtrVal(elem)
		
	}



function setHiddenUTRDateVal(elem,val){
	debugger;   
	var value = elem.value;
	    var id    = elem.id;
      var boolVal = false; 
	var hidVal = document.getElementById("totalUTRDate").value;
if(value!=null && id!=null)
	{
	 hidVal = hidVal.concat(id.concat('@', value+','));	
	 document.getElementById("totalUTRDate").value = hidVal;
	}
isFutureDate(val)
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

//Added by Sarita on 17 FEB 2022 [START]
function setDateValue(id) {
		var start = new Date("2020-06-24"), end = new Date(), diff = new Date(
				end - start), days = diff / 1000 / 60 / 60 / 24;
		var d = "#" + id;
		$(d).datepicker({
			dateFormat : 'dd/mm/yy',
			//setStartDate:"02-05-2021",
			minDate : days * -1,
			maxDate : end
		}).attr('readonly', 'readonly').focus();
	}
//Added by Sarita on 17 FEB 2022 [END]

function showCalendar1() {
	/* 
		p_month : 0-11 for Jan-Dec; 12 for All Months.
		p_year	: 4-digit year
		p_format: Date format (mm/dd/yyyy, dd/mm/yy, ...)
		p_item	: Return Item.
	*/
	p_item = arguments[0];
	if (arguments[1] == null)
		p_month = new String(gNow.getMonth());
	else
		p_month = arguments[1];
	if (arguments[2] == "" || arguments[2] == null)
		p_year = new String(gNow.getFullYear().toString());
	else
		p_year = arguments[2];
	if (arguments[3] == null)
		p_format = "DD/MM/YYYY";
	else
		p_format = arguments[3];

	vWinCal = window.open("", "Calendar", 
		"width=200,height=200,status=no,resizable=no,top=200,left=200");
	vWinCal.opener = self;
	vWinCal.focus();
	ggWinCal = vWinCal;

	Build1(p_item, p_month, p_year, p_format);
}

function Build1(p_item, p_month, p_year, p_format) {
	var p_WinCal = ggWinCal;
	gCal = new Calendar(p_item, p_WinCal, p_month, p_year, p_format);

	// Customize your Calendar here..
	gCal.gBGColor="#C4DEE6";
	gCal.gLinkColor="black";
	gCal.gTextColor="black";
	gCal.gHeaderColor="#CC3300";

	// Choose appropriate show function
	if (gCal.gYearly)	gCal.showY();
	else	gCal.show();
	
}

</script>
<SCRIPT language="javaScript" src="js/selectdate.js" type=text/javascript></SCRIPT>

<%

String thiskey = "";
//session.setAttribute("CurrentPage","allocatePaymentsAll.do?method=getPendingGFDANsLiveOnline");
String danDate;
SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
String allocate="N" ;

 session.setAttribute("CurrentPage","displayallocatePaymentFinal.do?method=displayallocatePaymentFinal");
 
 %>
  
		<html:form name = "rpAllocationForm" type="com.cgtsi.actionform.RPActionForm"  action="displayallocatePaymentFinal.do?method=displayallocatePaymentFinal" method="POST" >
			<link rel="stylesheet" href="//code.jquery.com/ui/1.13.1/themes/base/jquery-ui.css">
  			<link rel="stylesheet" href="/resources/demos/style.css">
  			<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
  			<script src="https://code.jquery.com/ui/1.13.1/jquery-ui.js"></script>
		<html:errors />
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
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
							<table width="100%" border="0" cellspacing="1" cellpadding="0">
							 <TR>
								<TD colspan="12">
									<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										<TR>
											
										</TR>
										<TR>
											<TD colspan="6" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
										</TR>
									</TABLE>
								</TD>
							</TR>
				
	<tr valign="top" style="height: 36px;">
		<td colspan="8" align="left">
			<table width="100%" border="0" cellpadding="0" cellspacing="0"> 
				<thead>
					<tr>
					
				<th valign="top" class="HeadingBg"> <div align="center">&nbsp;&nbsp;<strong>Sr.No</strong></div></th>
                <th valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>For Payment</strong></div></th>
                <th valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>Bank Name</strong></div></th>
                <th valign="top"  class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>RP Number</strong></div></th>
                <th valign="top"  class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>Payment Mode</strong></div></th>
                <th valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>Amount</strong><br></div></th>
					</tr>
				</thead>
			<tbody>
				<%
					String dateofclaim = null;
					String claimrefnumber = "";
					String branchName="";
					String unitName = ""; 
					double guaranteeapprovedamount=0.0;
					String viewDu="";
					String name="";
					String utrNo="";
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
                        String utrId="";
				 %>
				 <logic:iterate id="object" name="rpAllocationForm" property="allocatepaymentFinal" indexId="index">
								<%
								
								 RPActionForm rReport = (RPActionForm)object;
								DecimalFormat dec = new DecimalFormat("#0.00");
																	
								%>
								
								
		<TR style="height:40px">
             <TD width="5%" align="left" valign="top" class="ColumnBackground1" >
             <div align="center" style="height:20px"><%=Integer.parseInt(index+"")+1%></div></TD>
                                    
		    
            <%--     <TD width="15%" align="left" valign="top" class="ColumnBackground1">
                           <%=rReport.getVitrualAcF() %></TD> 
             <TD width="15%" align="left" valign="top" class="ColumnBackground1"><div align="left">
              <html:text property="UTRDate" name="rpAllocationForm"/>
              <IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('rpAllocationForm.UTRDate')" align="center">
              <%=rReport.getRPDATEF() %></div></TD> --%>
               
                   
                   <td  width="25%" align="left" valign="top" class="ColumnBackground1" style="padding-left: 45px;">  
                   <%
                 
                   String PaymentIdF =  rReport.getPaymentIdF();
                //  String VitrualAcF =  rReport.getVitrualAcF();
                   Double AmtF =  rReport.getAmtF();
                  // String RPDATEF =  rReport.getRPDATEF();
                   int amt=(int)rReport.getAmtF();              
  
                   String completeStr=PaymentIdF+"@"+AmtF;
                 
                   String completeStr1=PaymentIdF.concat("@").concat(Integer.toString(amt));
                  // completeStr="RP-00006-03-10-2016@4803";
          //         System.out.println("completeStr "+completeStr);
                   System.out.println("completeStr1 "+completeStr1);
                    name="allocationPaymentFinalSubmit("+completeStr1+")"; 
                   // utrNo=name+name; 
                    int utrCnt=0;
                   // String UTRStr=PaymentIdF.concat("@").concat(Integer.toString(amt)).concat("@").concat(VitrualAcF);
                    String name4="0";
                   // out.println("UTRNo==="+name4);
            //       name="allocationPaymentFinalSubmit(RP-00003-03-10-2016@166660000303102016@3276)";
                     name2="allocationPaymentFinalSubmit2("+AmtF+")";
                   //name3="allocationPaymentFinalSubmit3("+RPDATEF+")";
                  ///  String jsMethodDef="calcAllocatePayment("+AmtF+","+j+")";
                    String jsMethodDef="calcAllocatePaymentNew("+AmtF+","+j+")";
                   // out.println("name=="+name);
                    System.out.println("jsMethodDef "+jsMethodDef);
                    int p=0;
               
                   %>
         	       <div><input type="checkbox" id="chkId<%=index %>" name="rpAllocationForm"  onclick="<%=jsMethodDef%>" value="<%=name%>"/></div>
         	   </td> 
         	        <TD width="45%" align="left" valign="top" class="ColumnBackground1" ><div align="center"><%=rReport.getBankName()%> </div></TD>    
         	          
         	        <TD width="25%" align="left" valign="top" class="ColumnBackground1" ><div align="center"><a onclick="showDetails('<%=rReport.getPaymentIdF() %>');" href="javascript:void(0);"><%=rReport.getPaymentIdF()%> </a>
         	        <input type="hidden" id="rpId<%=index%>" name="rpIdVal" value="<%=rReport.getPaymentIdF()%>">
         	        </div></TD>
            
        	 <TD width="25%" align="left" valign="top" class="ColumnBackground1" ><div align="center"><%=rReport.getPaymentMode()%> </div></TD>    
        	
            <TD width="25%" align="left" valign="top" class="ColumnBackground1" ><div align="center">
              <%=dec.format(rReport.getAmtF())%>
            <input type="hidden" id="amtId<%=index%>" name="amountVal" value="<%=rReport.getAmtF()%>">
             </div></TD>    
               </TR>
             
               <%j++; %>
               <%k++;  %> 
         	        
			  </logic:iterate>	
			</tbody>
			
			
			</table>
			
		</td>	
		<td colspan="4" align="right" >
			<table width="100%" border="0" cellpadding="0" cellspacing="0" >  
			
				<thead>
					<tr style="height: 36px;">
					<th valign="top" class="HeadingBg"><div align="left">&nbsp;&nbsp;<strong>UTR No.
                      </strong><br></div></th>
					<th valign="top" class="HeadingBg"><div align="left">&nbsp;&nbsp;<strong>Payment Date<br/>(DD/MM/YYYY Format)
                      </strong><br></div></th>
                      <th valign="top" class="HeadingBg"><div align="left">&nbsp;&nbsp;<strong>Status
                      </strong><br></div></th>
                      
                       
					</tr>
				</thead>
			<tbody>
				 
			  <% int d=1; %>
	        <logic:iterate id="object" name="rpAllocationForm" property="allocatepaymentFinal" indexId="index">
				<%
				String name5="0";
				String name6="0";
				 RPActionForm rReport = (RPActionForm)object;
				DecimalFormat dec = new DecimalFormat("#0.00");
				%>
			 <tr style="height:40px">
				  <TD width="30%" align="left" valign="top" class="ColumnBackground1" >
                        <input type="text" name="UTRNo"  id="UTRNo<%=index %>"/>
                </TD> 
                
                 <TD width="30%" align="left" valign="top" class="ColumnBackground1" > 
              <input type="text" name="UTRDate"  autocomplete="off"  id="UTRDate<%=index%>" 
                     placeholder="DD/MM/YYYY" onclick="setDateValue(id)"/> 
            	</TD>
            
                 <TD width="55%" align="left" valign="top" class="ColumnBackground1" >
                    	<div align="center" style="height:20px" ><%=rReport.getStatus()%> </div>
                </TD>    
          
              
               </tr> 
              
								 <%--  <TD width="15%" align="left" valign="top" class="ColumnBackground1">   
         	                       <html:text property="<%=name5 %>" name="rpAllocationForm" styleId="<%=name5 %>"  /></TD>
         	                       
         	                         <TD width="15%" align="left" valign="top" class="ColumnBackground1">   
         	                       <html:text property="<%=name5 %>" name="rpAllocationForm" styleId="<%=name5 %>"  />
									    </TD>   --%> 
         	               
         	           <%--     <%
               String result="";
         	               if(request.getParameter("UTRNo")!=null){
         	            	   result= request.getParameter("UTRNo").toString().concat("@").concat(String.valueOf(d));
         	               }
				 out.println("result"+result);
					//<%=name5
				 //out.println("VitrualAcjF"+VitrualAcjF);
				if(result!=null||result!="0")
				{ 
					name5="allocationPaymentFinalSubmit3("+result+")"; 
				}                
               %> --%>
                
         	              <%d++; %> 
			 	  </logic:iterate>	
               	
			</tbody>
			
			
			</table>
			
		</td>	
	</tr>
	  
		<tr>  
		<td colspan="12">
			<table width="100%" border="0" cellpadding="0" cellspacing="0"> 
				 
			<tbody>
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
			</tbody> 
			</table>
			
		</td>      								        
          </tr>  
		 </TABLE>  <tr align="center" valign="baseline">
           		 <td colspan="4"> 
           		 <div align="center"> 
		         <A href="javascript:submitFinalMakePayment('displayallocatePaymentFinalSubmit.do?method=displayallocatePaymentFinalSubmit')"><IMG src="images/Next.gif" alt="Next" width="49" height="37" border="0"></A>
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
