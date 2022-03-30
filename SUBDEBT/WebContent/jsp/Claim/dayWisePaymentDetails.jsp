 <%@ page language="java"%>
 <%@ page import="java.util.Map"%>
<%@ page import="com.cgtsi.actionform.RPActionForm"%>
<%@ page import="java.util.ArrayList"%>
<%@page import ="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar" %>
<%@page import ="java.text.DecimalFormat"%>
<%@page import ="java.math.BigDecimal"%>
<%@page import="com.cgtsi.actionform.ClaimActionForm"%>
<%@page import="org.apache.struts.validator.DynaValidatorActionForm"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% 
  session.setAttribute("CurrentPage","getPaymentDetailsForMemeberID.do?method=getPaymentDetailsForMemeberID");
%>

<%
  SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
   Date systemDate  = new Date();
   String sysDate = dateFormat.format(systemDate);
 // DynaValidatorActionForm dynaForm = (DynaValidatorActionForm)session.getAttribute("ioForm") ;
 // dynaForm.set("inwardDts",sysDate);
%>
<%
String appropriate="N" ;
String thiskey = "";

%>
<HTML>
<HEAD>
	<TITLE> Update Payment Details </TITLE>
	 <SCRIPT type="text/javascript" language="javascript">   

function daysInFebruary (year){
	// February has 29 days in any year evenly divisible by four,
    // EXCEPT for centurial years which are not also divisible by 400.
    return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
}
function DaysArray(n) {
	for (var i = 1; i <= n; i++) {
		this[i] = 31
		if (i==4 || i==6 || i==9 || i==11) {this[i] = 30}
		if (i==2) {this[i] = 29}
   } 
   return this
}

 

	function isInteger(s){
	var i;
    for (i = 0; i < s.length; i++){   
        // Check that current character is number.
        var c = s.charAt(i);
        if (((c < "0") || (c > "9"))) return false;
    }
    // All characters are numbers.
    return true;
}
function stripCharsInBag(s, bag){
	var i;
    var returnString = "";
    // Search through string's characters one by one.
    // If character is not in bag, append to returnString.
    for (i = 0; i < s.length; i++){   
        var c = s.charAt(i);
        if (bag.indexOf(c) == -1) returnString += c;
    }
    return returnString;
}
           
     </SCRIPT>  
	 <script type="text/javascript" >
	 
	 	function chkdate(objName) 
		{
   
                              var dtCh= "/";
                              var minYear=1900;
                              var maxYear=2050;
                              var strDate;
                              var strDay;
                              var strMonth;
                              var strYear;
                              var strYr;
                              var intday;
                              var intMonth;
                              var intYear;                           
                              var datefield = objName;                              
                              var intElementNr;                          
                             
                       //       alert("strDate.length:"+datefield.length);
							  
                             if (datefield.length >= 11 || datefield.length < 10) {
                              alert("Please correct Date, date format should be : dd/MM/yyyy")
                              return false;
                              }
                      
					var currentdate = document.getElementById('currentDate').value;
					var currDayStr;
				    var currMonStr;
				    var currYearStr;
					var strDay;
					var strMonth;
					var strYear;
					var currDay;
					var currMon;
					var currPrevDay;
					var currPrevMon;
					
					
					
					var elem2 = currentdate.split('/');
					currDayStr = elem2[0]; 
					currMonStr = elem2[1]; 
					currYearStr = elem2[2]; 
					
				//	alert("current date:"+currentdate);
				//	alert("currDayStr:"+currDayStr+"currMonStr:"+currMonStr+"currYearStr:"+currYearStr);
					
				//	var currPrevDayStr = (currDayStr-1);
				//	var currPrevMonStr = (currMonStr-1);
				//	alert("currPrevDayStr:"+currPrevDayStr+"currPrevMonStr:"+currPrevMonStr);
					
					// if(currDayStr.length == 1){
						// currDay = '0'+ currDayStr;
						// currPrevDay = '0'+ currPrevDayStr;
						// alert("currDay:"+currDay+"currPrevDay:"+currPrevDay);
					// }
					// if(currMonStr.length == 1){
						// currMon = '0'+currMonStr;
						// currPrevMon = '0' + currPrevMonStr;
						// alert("currMon:"+currMon+"currPrevMon:"+currPrevMon);
					// }
				//	alert("after change currPrevDayStr:"+currPrevDayStr+"currPrevMonStr:"+currPrevMonStr);
					  
                    var daysInMonth = DaysArray(12);
					var pos1=datefield.indexOf(dtCh);
					var pos2=datefield.indexOf(dtCh,pos1+1);
					// var strDay=datefield.substring(0,pos1);
					// var strMonth=datefield.substring(pos1+1,pos2);
					// var strYear=datefield.substring(pos2+1);
					
					var elem3 = datefield.split('/');
					strDay = elem3[0]; 
					strMonth = elem3[1]; 
					strYear = elem3[2]; 
					
				//	alert("date entered:"+datefield);
				//	alert("entered STR day:"+strDay+"currDayStr:"+currDayStr);
				//	alert("entered sTr mon:"+strMonth+"currMonStr:"+currMonStr);
				//	alert("entered sTr year:"+strYear+"currYearStr:"+currYearStr);
					
				
					
				    year = parseInt(strYear);
				 
               //  alert("day:"+day+"month:"+month+"year:"+year);
                  if (pos1==-1 || pos2==-1){
                      alert("Please correct Date, date format should be : dd/MM/yyyy");
                      return false;
                    }
					
                	if (strMonth.length<1 || strMonth<1 || strMonth>12){
                  	alert("Please enter a valid month");
                  	return false;
                  }
                  if (strDay.length<1 || strDay<1 || strDay>31 || (strMonth==2 && strDay>daysInFebruary(year)) || strDay > daysInMonth[strMonth]){
                  	alert("Please enter a valid day");
                    return false;
                }
                  if (strYear.length != 4 || year==0 || year<minYear || year>maxYear){
                  alert("Please enter a valid 4 digit year between "+minYear+" and "+maxYear);
                	return false;
                  }
                 if (datefield.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(datefield, dtCh))==false){
                    alert("Please enter a valid date");
                  	return false;
                   } 
                    return true;     
        }                           
         
	 
	 
	  function savePaymentDetails()
       {
           //   alert("saving");
			  
			  var utrno = document.forms[0].UTRNO.value;
			  var accountno = document.forms[0].accountNO.value;
			  var outwardno = document.forms[0].outwardNO.value;
			  var paymentdate = document.forms[0].paymentDate.value;
			  var outwarddate = document.forms[0].outwardDate.value;
			  var currentdate = document.getElementById("currentDate").value;
		
			  var pymtDayStr;
			  var pymtMonStr;
			  var pymtYearStr;
			  var owdDayStr;
			  var owdMonStr;
			  var owdYearStr;
			  var currDayStr;
			  var currMonStr;
			  var currYearStr;
			  
			  var pymtDay;
			  var pymtMon;
			  var pymtYear;
			  var owdDay;
			  var owdMon;
			  var owdYear;
			  var currDay;
			  var currMon;
			  var currYear;
			  
			  var currPrevDay;
			  var currPrevMon;
			  
			  var elem1 = paymentdate.split('/');  
			  var elem2 = outwarddate.split('/');  
			  var elem3 = currentdate.split('/');
				pymtDayStr = elem1[0];  
				pymtMonStr = elem1[1];  
				pymtYearStr = elem1[2];
				
				owdDayStr = elem2[0]; 
				owdMonStr = elem2[1]; 
				owdYearStr = elem2[2];
				
				currDayStr = elem3[0]; 
				currMonStr = elem3[1]; 
				currYearStr = elem3[2]; 
				
			//	alert("payment year str:"+pymtYearStr+"payment monthy str:"+pymtMonStr+"payment day str:"+pymtDayStr);
			//	alert("outward year str:"+owdYearStr+"outward monthy str:"+owdMonStr+"outward day str:"+owdDayStr);
			//	alert("current year str:"+currYearStr+"current monthy str:"+currMonStr+"current day str:"+currDayStr);
			
				
			
                        //	alert("curr day:"+currDayStr+"  curr month:"+currMonStr+"  curr year:"+currYearStr);
                        //	alert("pymt day:"+pymtDay+"   pymt month:"+pymtMon+"  pymt year:"+pymtYear);
                        //	alert("owd day:"+owdDay+"   owd month:"+owdMon+"  owd year:"+owdYear);
		
			  if(utrno == null || utrno == "" || utrno.length == 0){
				 alert("please enter utr number");
				 document.forms[0].UTRNO.style.backgroundColor = 'yellow';
				 document.forms[0].UTRNO.focus();
				return false;				
			 }else if(accountno == null || accountno == "" || accountno.length == 0){
				 alert("please enter account number");
				 document.forms[0].accountNO.style.backgroundColor = 'yellow';
				 document.forms[0].accountNO.focus();
				return false;				
			 }else if(outwardno == null || outwardno == "" || outwardno.length == 0){
				 alert("please enter outward number");
				 document.forms[0].outwardNO.style.backgroundColor = 'yellow';
				 document.forms[0].outwardNO.focus();
				return false;				
			 }else if(paymentdate == null || paymentdate == "" || paymentdate.length == 0){
				  alert("please enter payment date");
				  document.forms[0].paymentDate.style.backgroundColor = 'yellow';
				  document.forms[0].paymentDate.focus();
				return false;				
			 }else if(outwarddate == null || outwarddate == "" || outwarddate.length == 0){
				  alert("please enter outward date");
				  document.forms[0].outwardDate.style.backgroundColor = 'yellow';
				  document.forms[0].outwardDate.focus();
				return false;				
			}else if((pymtYearStr > currYearStr) || ((pymtYearStr == currYearStr) && (pymtMonStr > currMonStr)) || ((pymtYearStr == currYearStr) && (pymtMonStr == currMonStr) && (pymtDayStr > currDayStr))){

							alert("payment date can not be greater than current date");
							document.forms[0].paymentDate.style.backgroundColor = 'yellow';
							document.forms[0].paymentDate.focus();
							return false;
				
			}else if((owdYearStr > currYearStr) || ((owdYearStr == currYearStr) && (owdMonStr > currMonStr)) || ((owdYearStr == currYearStr) && (owdMonStr == currMonStr) && (owdDayStr > currDayStr))){
			
							alert("outward date can not be greater than current date");
							document.forms[0].outwardDate.style.backgroundColor = 'yellow';
							document.forms[0].outwardDate.focus();
							return false;
			  
			}else if((pymtYearStr > owdYearStr) || ((pymtYearStr == owdYearStr) && (pymtMonStr > owdMonStr)) || ((pymtYearStr == owdYearStr) && (pymtMonStr == owdMonStr && pymtDayStr > owdDayStr))){
			
							alert("payment date can not be greater than outward date");
							document.forms[0].paymentDate.style.backgroundColor = 'yellow';
							document.forms[0].paymentDate.focus();
							return false;
			
			 }else{
			 
			 var cb = document.getElementsByTagName("input");
			 var status;
			 for(var i=0;i<cb.length;i++){
				if(cb[i].type == 'checkbox' && cb[i].checked){
				status = 'yes';
					break;
				}
			 }
			 if(status == 'yes'){
			 
			 }else{
			 alert("please select atleast one record");
				return false;
			 }
			 
                 document.cpTcDetailsForm.target ="_self";
                 document.cpTcDetailsForm.method="POST";
                 document.cpTcDetailsForm.action="updateDayWisePaymentDetails.do?method=updateDayWisePaymentDetails";
                 document.cpTcDetailsForm.submit();
				return true;
			 }
			
        }

		function numbersOnly(myfield, e)
		{
			myfield.style.backgroundColor = 'white';
			var key;
			var keychar;

			if (window.event)
			   key = window.event.keyCode;
			else if (e)
			   key = e.which;
			else
			   return true;
			keychar = String.fromCharCode(key);

			// control keys
			if ((key==null) || (key==0) || (key==8) ||
				(key==9) || (key==13) || (key==27) ){
			   return true;
			}
			// numbers
			else if ((("0123456789").indexOf(keychar) > -1)){
			   return true;
			}else{
			   return false;
			   }
		}

		function isValidNumber(field)
		{
			if(!isValid(field.value))
			{
				field.focus();
				field.value='';
				return false;
			}
		}

	 function resetColor(field){
	
	field.style.backgroundColor = 'white';
	 }

function addAmount(recordNo){
	
	var firstinstallmentid = 'firstinstallment'+recordNo;
	var asfdeductableid = 'asfdeductable'+recordNo;
	var netpaidid = 'netpaid'+recordNo;
	
	//alert("ids:"+firstinstallmentid+"--"+asfdeductableid+"---"+netpaidid);
	
	var firstinstallment = document.getElementById(firstinstallmentid).innerHTML | 0;
	var asfdeductable = document.getElementById(asfdeductableid).innerHTML | 0;
	var netpaid = document.getElementById(netpaidid).innerHTML | 0;
	
	if(firstinstallment == ""){
		firstinstallment = 0.0;
	}
	if(asfdeductable == ""){
		asfdeductable = 0.0;
	}
	if(netpaid == ""){
		netpaid = 0.0;
	}
	
	//alert("amounts:"+firstinstallment+"---"+asfdeductable+"---"+netpaid);
	
	
	var firstinstallmenttotal = document.getElementById('firstinstallmenttotal').innerHTML | 0;
	var asfdeductabletotal = document.getElementById('asfdeductabletotal').innerHTML | 0;
	var netpaidtotal = document.getElementById('netpaidtotal').innerHTML | 0;
	
	if(firstinstallmenttotal == ""){
		firstinstallmenttotal = 0.0;
	}
	if(asfdeductabletotal == ""){
		asfdeductabletotal = 0.0;
	}
	if(netpaidtotal == ""){
		netpaidtotal = 0.0;
	}
	
	//alert("total amounts:"+firstinstallmenttotal+"----"+asfdeductabletotal+"----"+netpaidtotal);
	
	
//	 var cb = document.getElementsByTagName("input");
	 
	// alert("total input tag:"+cb.length);
	 
					firstinstallmenttotal = parseFloat(firstinstallmenttotal) + parseFloat(firstinstallment);
					asfdeductabletotal = parseFloat(asfdeductabletotal) + parseFloat(asfdeductable);
					netpaidtotal = parseFloat(netpaidtotal) + parseFloat(netpaid);
					
					
				
		document.getElementById('firstinstallmenttotal').innerHTML = firstinstallmenttotal;
		document.getElementById('asfdeductabletotal').innerHTML = asfdeductabletotal;
		document.getElementById('netpaidtotal').innerHTML = netpaidtotal;
		}
		
function minusAmount(recordNo){
			
	
	
	var firstinstallmentid = 'firstinstallment'+recordNo;
	var asfdeductableid = 'asfdeductable'+recordNo;
	var netpaidid = 'netpaid'+recordNo;
	
//	alert("ids:"+firstinstallmentid+"--"+asfdeductableid+"---"+netpaidid);
	
	var firstinstallment = document.getElementById(firstinstallmentid).innerHTML | 0;
	var asfdeductable = document.getElementById(asfdeductableid).innerHTML | 0;
	var netpaid = document.getElementById(netpaidid).innerHTML | 0;
	
	if(firstinstallment == "" || isNaN(firstinstallment)){
		firstinstallment = 0.0;
	}
	if(asfdeductable == "" || isNaN(asfdeductable)){
		asfdeductable = 0.0;
	}
	if(netpaid == "" || isNaN(netpaid)){
		netpaid = 0.0;
	}
	
//	alert("amounts:"+firstinstallment+"---"+asfdeductable+"---"+netpaid);
	
	
	var firstinstallmenttotal = document.getElementById('firstinstallmenttotal').innerHTML | 0;
	var asfdeductabletotal = document.getElementById('asfdeductabletotal').innerHTML | 0;
	var netpaidtotal = document.getElementById('netpaidtotal').innerHTML | 0;
	
	if(firstinstallmenttotal == "" || isNaN(firstinstallmenttotal)){
		firstinstallmenttotal = 0.0;
	}
	if(asfdeductabletotal == "" || isNaN(asfdeductabletotal)){
		asfdeductabletotal = 0.0;
	}
	if(netpaidtotal == "" || isNaN(netpaidtotal)){
		netpaidtotal = 0.0;
	}
	
//	alert("total amounts:"+firstinstallmenttotal+"----"+asfdeductabletotal+"----"+netpaidtotal);
	
//	 var cb = document.getElementsByTagName("input");
	 
//	 alert("total input tag:"+cb.length);
	 
			 
					
					firstinstallmenttotal = parseFloat(firstinstallmenttotal) - parseFloat(firstinstallment);
					asfdeductabletotal = parseFloat(asfdeductabletotal) - parseFloat(asfdeductable);
					netpaidtotal = parseFloat(netpaidtotal) - parseFloat(netpaid);
					
		document.getElementById('firstinstallmenttotal').innerHTML = firstinstallmenttotal;
		document.getElementById('asfdeductabletotal').innerHTML = asfdeductabletotal;
		document.getElementById('netpaidtotal').innerHTML = netpaidtotal;
		}
	
	
	
	
	function selectDeselect(field,counter)
	{
	
		//alert("length "+document.forms[0].elements.length);
		
		//alert("0 "+document.forms[0].elements[0].value);
		
		//alert("3 "+document.forms[0].elements[3].value);
		
		var start=3;
		if(counter)
		{
			start=counter;
		}
		//alert("counter "+counter);
		
		if(field.checked==true)
		{
			for(i=start;i<document.forms[0].elements.length;i++)
			{

				document.forms[0].elements[i].checked=true;
			}
			document.getElementById('firstinstallmenttotal').innerHTML = document.getElementById('totFirstInstAmount').value;
			document.getElementById('asfdeductabletotal').innerHTML = document.getElementById('totASFDeducted').value;
			document.getElementById('netpaidtotal').innerHTML = document.getElementById('totNetPaidAmount').value;
		}
		if(field.checked==false)
		{
			for(i=start;i<document.forms[0].elements.length;i++)
			{
				document.forms[0].elements[i].checked=false;
			}
			document.getElementById('firstinstallmenttotal').innerHTML = '';
			document.getElementById('asfdeductabletotal').innerHTML = '';
			document.getElementById('netpaidtotal').innerHTML = '';
		}
		
		
}
	 </script>
</HEAD>
<BODY>
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
  <html:errors/>
	<html:form action="updateDayWisePaymentDetails.do?method=updateDayWisePaymentDetails" enctype="multipart/form-data" method="POST" >
	<%
		java.util.Calendar cal = java.util.Calendar.getInstance();
			int day = cal.get(Calendar.DATE);
			int month = cal.get(Calendar.MONTH)+1;
			int year = cal.get(Calendar.YEAR);
			
			String currentDate = day+"/"+month+"/"+year;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date cdate = sdf.parse(currentDate);
			currentDate = sdf.format(cdate);
	%>
	<input type="hidden" name="currDate" id="currentDate" value="<%=currentDate%>" />
	
	<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR>
										<TD width="20%" class="Heading">&nbsp;Insert Payment Details</TD>
										<TD width="80%"><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
									</TR>
									<TR>
										<TD colspan="4" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
									</TR>
    </TABLE>
	<!-- first part of the page -->
	
	<TABLE width="100%">
		<TR>
			 <td width="30%" class="ColumnBackground "><div align="center"><font size="2" style="color:red;"><bold>*</font>UTR No</div></td> 
			 <TD width="30%"><html:text property="UTRNO" name="cpTcDetailsForm" size="70" maxlength="20" onkeypress="resetColor(this);"/></td> 
             <td width="30%" class="ColumnBackground "><div align="center"><font size="2" style="color:red;"><bold>*</font>Payment Date</div></td>
             <TD width="30%"><html:text property="paymentDate" name="cpTcDetailsForm" size="20" maxlength="10" onchange="javascript:chkdate(this.value);" onkeypress="resetColor(this);"/><IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('cpTcDetailsForm.paymentDate')" align="center">
			 </TD>
			  
                        
        </TR>
        <TR>
			 
			 <td width="30%" class="ColumnBackground "><div align="center"><font size="2" style="color:red;"><bold>*</font>Account No</div></td>
             <TD width="30%"><html:text property="accountNO" name="cpTcDetailsForm" size="70" maxlength="20"/></TD> 
             <td width="30%" ><div align="center"></div></td>
             <TD width="30%"><div align="center"></div></TD> 
		        
        </TR>
        <TR>
			 <td width="30%" class="ColumnBackground "><div align="center"><font size="2" style="color:red;"><bold>*</font>Outward No</div></td>
			 <TD width="30%"><html:text property="outwardNO" name="cpTcDetailsForm" size="70" maxlength="20" onkeypress="resetColor(this);"/></td>
		     <td width="30%" class="ColumnBackground "><div align="center"><font size="2" style="color:red;"><bold>*</font>Outward Date</div></td>
          	 <TD width="30%"><html:text property="outwardDate" name="cpTcDetailsForm" size="20" maxlength="10" onchange="javascript:chkdate(this.value);" onkeypress="resetColor(this);"/><IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('cpTcDetailsForm.outwardDate')" align="center">
			 </TD>
			 
		    		 
        </TR>
    </TABLE>
  <!--second part of the page -->
  <%
	com.cgtsi.actionform.ClaimActionForm claimForm = (com.cgtsi.actionform.ClaimActionForm)session.getAttribute("cpTcDetailsForm");
	String fromDate = (String)claimForm.getDateOfTheDocument36();
	String toDate = (String)claimForm.getDateOfTheDocument37();
	String memberID = (String)claimForm.getMemberID();
	
  %>
 
	<TABLE width="100%">
		<TR>
			<TD class="Heading" width="50%">
					&nbsp;<bean:message key="from"/>&nbsp;&nbsp;<%=fromDate%>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="to"/>&nbsp;&nbsp;<%=toDate%>
					<html:hidden property="dateOfTheDocument36" name="cpTcDetailsForm" value="<%=fromDate%>" />
					<html:hidden property="dateOfTheDocument37" name="cpTcDetailsForm" value="<%=toDate%>" />
			</TD>
			<TD class="Heading" width="50%">&nbsp;<bean:message key="memberId"/>&nbsp;&nbsp;<%=memberID%>
			</TD>
		</TR>
	</TABLE>
				<% 
					
					String checkboxKey = null;
					ArrayList arraylist = null;
					String ASFStringArray[] = null;
					String size = (String)request.getAttribute("pendingCaseDetailsArray_size");
				 
					double totalAsf_summary_Amount = 0.0d;
					
					if(!(size.equals("0")))
					{
					arraylist=(ArrayList)request.getAttribute("pendingCaseDetailsArray");
				%>
	<TABLE width="725">	
            <TR class="tableData">
                  
				<th class="HeadingBg">SL No</th>
				<th class="HeadingBg">BANK NAME</th>
				<th class="HeadingBg">ZONE NAME</th>
                <th class="HeadingBg">MEMBER ID</th>
                <th class="HeadingBg">CGPAN</th>
                <th class="HeadingBg">UNIT NAME</th>
                <th class="HeadingBg">APPROVED AMOUNT</th>
                <th class="HeadingBg">REVISED NPA APPROVED AMOUNT</th>
                <th class="HeadingBg">REVISED NPA RECOVERED AMOUNT</th>
                  
                <th class="HeadingBg">NET OUTSTANDING AMOUNT</th>
                <th class="HeadingBg">CLAIM APPLIED AMOUNT</th>
                <th class="HeadingBg">CLAIM ELIGIBLE AMOUNT</th>
                <th class="HeadingBg">FIRST INSTALLMENT PAY AMOUNT</th>
                <th class="HeadingBg">ASF DEDUCTABLE </th>
                <th class="HeadingBg">NET PAID AMOUNT</th>
                <th class="HeadingBg">MARK All<html:checkbox property="selectAll" alt="pay" name="cpTcDetailsForm" onclick="selectDeselect(this,1)"/></th>  
            </TR>
				<%
				String totalFirstInstAmount="";
				double totFirstInstAmount = 0.0d;
				String totFirstInstAmountStr="";
				
				String totalASFDeducted="";
				double totASFDeducted = 0.0d;
				String totASFDeductedStr="";
				
				String totalNetPaidAmount="";
				double totNetPaidAmount = 0.0d;
				String totNetPaidAmountStr="";
			   %>
			   <%DecimalFormat decimalFormat = new DecimalFormat("##########.#####");%>
                <%  
					int recordNo = 0;
					for(int count=0;count<arraylist.size();count++)
					{
						
						double asf_amount = 0.0d;
						ASFStringArray = new String[14];
						ASFStringArray = (String[])arraylist.get(count);
				%>
			
            <tr>
				<td class="ColumnBackground1">&nbsp;<%=count+1%></td>
                <td class="ColumnBackground1">
                    <%= ASFStringArray[0]%>
                </td>
                <td class="ColumnBackground1">
                    <%= ASFStringArray[1]%>
                </td>
                <td class="ColumnBackground1">
                    <%= ASFStringArray[2]%>
                </td>
				
               
				
				<% 
                    thiskey = ASFStringArray[3];
				//	out.println("clmrefno:"+thiskey);
			        checkboxKey="depositedFlags("+thiskey+")";
                      
                %>
				 <td class="ColumnBackground1">
                    <%= ASFStringArray[4]%>
                </td>
                <td class="ColumnBackground1">
                    <%= ASFStringArray[5]%>
                </td>
                <td class="ColumnBackground1">
                    <%= ASFStringArray[6]%>
                </td>
                <td class="ColumnBackground1">
                    <%= ASFStringArray[7]%>
                </td>
                <td class="ColumnBackground1">
                    <%= ASFStringArray[8]%>
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
                <td class="ColumnBackground1" id="firstinstallment<%=recordNo%>">
                    <%= ASFStringArray[12]%>
					<%
						totalFirstInstAmount = ASFStringArray[12];
						double tempamt1 =  Double.parseDouble(totalFirstInstAmount);
						totFirstInstAmount = totFirstInstAmount + tempamt1;
					%>
                  </td>
                <td class="ColumnBackground1" id="asfdeductable<%=recordNo%>">
                    <%= ASFStringArray[13]%>
					<%
						totalASFDeducted = ASFStringArray[13];
						double tempamt2 = Double.parseDouble(totalASFDeducted);
						totASFDeducted = totASFDeducted + tempamt2;
					%>
                  </td>
                <td class="ColumnBackground1" id="netpaid<%=recordNo%>">
                    <%= ASFStringArray[14]%>
					<%
						totalNetPaidAmount = ASFStringArray[14];
						double tempamt3 = Double.parseDouble(totalNetPaidAmount);
						totNetPaidAmount = totNetPaidAmount + tempamt3;
						
					%>
                  </td>
                  <td class="ColumnBackground1">
                    
					<input type="checkbox" name="<%=checkboxKey%>" id="cb" value="Y" onclick="if(this.checked){addAmount(<%=count%>);}else{minusAmount(<%=count%>);}"/>
					<!-- <input type="hidden" name="" id="cbkey" /> -->
                  </td>
            </tr>
			<% recordNo++; %>
                <%  }%> 
				
				
				
				<TR class="ColumnBackground1">
										<td><input type="hidden" name="selectRecords" value="<%=recordNo%>" /></td>
										<td><input type="hidden" name="totFirstInstAmount" id="totFirstInstAmount" value="<%=decimalFormat.format(totFirstInstAmount)%>" /></td>
										<td><input type="hidden" name="totASFDeducted" id="totASFDeducted" value="<%=decimalFormat.format(totASFDeducted)%>" /></td>
										<td><input type="hidden" name="totNetPaidAmount" id="totNetPaidAmount" value="<%=decimalFormat.format(totNetPaidAmount)%>" /></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td><b>Total</b></td>										
										<td class="ColumnBackground1" id="firstinstallmenttotal"></td><!--use innerHTML-->
                                        <td class="ColumnBackground1" id="asfdeductabletotal"></td><!--use innerHTML-->
                                        <td class="ColumnBackground1" id="netpaidtotal"></td><!--use innerHTML-->
										<td></td>
								</TR>
						
    </TABLE>
	
	<TABLE width="725">
		
			<TR>
				<TD height="20">
							&nbsp;
				</TD>
			</TR>
			<tr>
				<td colspan="3" align="left" width="700"><font size="2" color="red">Report Generated On : 
					<% java.util.Date loggedInTime=new java.util.Date();
			          java.text.SimpleDateFormat dateFormat1=new java.text.SimpleDateFormat("dd MMMMM yyyy : HH.mm");
			          String date1=dateFormat1.format(loggedInTime);
					  out.println(date1);
					  %> hrs.</font>
				</td>
			</tr>
			<TR>
			
				<TD align="center" valign="baseline">
							<DIV align="center">
						
								<A href="displayUpdatePaymentDetailInput.do?method=displayUpdatePaymentDetailInput">
									<IMG src="images/Back.gif" alt="Back" width="49" height="37" border="0"></A>
								<A href="#" onclick="return savePaymentDetails();">
							<IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A> 									
							</DIV>
				</TD>				
			</TR>
						
   </TABLE>
	
	<%}else{%>
	<TABLE width="725">
		<tr>
			<td>
						<DIV align="center">
										No Data Found
						</DIV>
			</td>
		</tr>
			<TR>			
				<TD align="center" valign="baseline">
							<DIV align="center">					
								<A href="displayUpdatePaymentDetailInput.do?method=displayUpdatePaymentDetailInput">
								<IMG src="images/Back.gif" alt="Back" width="49" height="37" border="0"></A>									
							</DIV>
				</TD>				
			</TR>
	</TABLE> 
		<%}%>
	
</html:form>  
</TABLE>   
</BODY>
</HTML>