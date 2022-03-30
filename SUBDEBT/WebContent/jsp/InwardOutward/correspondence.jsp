 <%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@page import ="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="org.apache.struts.validator.DynaValidatorActionForm"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% 
  session.setAttribute("CurrentPage","InwardCorrespondence.do?method=inwardCorrespondence");
  SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
   Date systemDate  = new Date();
   String sysDate = dateFormat.format(systemDate);
  DynaValidatorActionForm dynaForm = (DynaValidatorActionForm)session.getAttribute("ioForm") ;
  dynaForm.set("inwardDts",sysDate);
%>
<HTML>
<HEAD>
	<TITLE> Add/Remove dynamic rows in HTML table </TITLE>
	 <SCRIPT language="javascript">  
   
   var req; 
      
  	/* 	 
  	* Get the second options by calling a Struts action 	 
  	*/	 
        
  	function retrieveSecondOptions(){ 
      
        section = document.getElementById('section'); 
  	//Nothing selected 	 
  	if(section.selectedIndex==0){ 	 
  	return; 	 
        } 	 
  
	selectedOption = section.options[section.selectedIndex].value; 	 
  	//get the (form based) params to push up as part of the get request 	 
        //afterTcMli.do?method=getDistricts
  	url="workshopDetails.do?method=getUserNamesForSection&section="+section.value; 
      
  	//Do the Ajax call 	 
  	if (window.XMLHttpRequest){ // Non-IE browsers 	 
  	req = new XMLHttpRequest(); 	 
  	//A call-back function is define so the browser knows which function to call after the server gives a reponse back 	 
  	req.onreadystatechange = populateSecondBox; 	 
        try { 	 
  	req.open("GET", url, true); //was get 	 
  	} catch (e) { 	 
  	alert("Cannot connect to server"); 	 
  	} 	 
 	req.send(section); 	 
 	} else if (window.ActiveXObject) { // IE 	 
 	req = new ActiveXObject("Microsoft.XMLHTTP"); 	 
 	if (req) { 	 
	req.onreadystatechange = populateSecondBox; 	 
	req.open("GET", url, true); 	 
	
        req.send(); 	 
	} 	 
 	} 	   
	} 	 

	function populateSecondBox(){ 	  
	document.getElementById('assignedTo').options.length = 0; 	 
  	if (req.readyState == 4) { // Complete 	 
  	if (req.status == 200) { // OK response 	 
  	textToSplit = req.responseText; 
        //alert ("the data: "+textToSplit);
                            if(textToSplit == '803')
                              { 	 
                                alert("No select option available on the server") 	 
                               } 	 //if close

	var returnElements=textToSplit.split("||"); 
  
	for ( var i=0; i<returnElements.length; i++ ){ 	 
               valueLabelPair = returnElements[i].split(";"); 	 
              // alert("the first value is :-=-->"+valueLabelPair[0] );
               document.getElementById('assignedTo').options[i] = new Option(valueLabelPair[0], valueLabelPair[1]); 	 
       	} 	 //for close
  	} //ok close	 
            else { 	 
        	         alert("Bad response by the server"); 	 
        	       } 
  } //complet close          	 
  
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

  function isDate(){
  var dtCh= "/";
  var minYear=1900;
  var maxYear=2050;
  var dtStr=findObj("ltrDts").value;
 // alert("dtStr:"+dtStr);
//  alert("dtStrLen:"+dtStr.length);
    if (dtStr.length >= 11 || dtStr.length < 10) 
    {
          alert("Please correct Letter Date, date format should be : dd/MM/yyyy");
          return false;
     }
	var daysInMonth = DaysArray(12)
	var pos1=dtStr.indexOf(dtCh);
 // alert("pos1:"+pos1);
	var pos2=dtStr.indexOf(dtCh,pos1+1);
//  alert("pos2:"+pos2);
	var strDay=dtStr.substring(0,pos1);
 // alert("strDay:"+strDay);
	var strMonth=dtStr.substring(pos1+1,pos2);
 // alert("strMonth:"+strMonth);
	var strYear=dtStr.substring(pos2+1);
	    strYr=strYear;
//   alert("strYear:"+strYear);    
	if (strDay.charAt(0)=="0" && strDay.length>1) strDay=strDay.substring(1)
	if (strMonth.charAt(0)=="0" && strMonth.length>1) strMonth=strMonth.substring(1)
	for (var i = 1; i <= 3; i++) {
		if (strYr.charAt(0)=="0" && strYr.length>1) strYr=strYr.substring(1)
	}
	month=parseInt(strMonth)
	day=parseInt(strDay)
	year=parseInt(strYr)
	if (pos1==-1 || pos2==-1){
		alert("Please correct Letter Date, date format should be : dd/MM/yyyy")
		return false
	}
	if (strMonth.length<1 || month<1 || month>12){
		alert("Please enter a valid month for Letter Date")
		return false
	}
	if (strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
		alert("Please enter a valid day for Letter Date")
		return false
	}
	if (strYear.length != 4 || year==0 || year<minYear || year>maxYear){
		alert("Please enter a valid 4 digit year between "+minYear+" and "+maxYear)
		return false
	}
	if (dtStr.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, dtCh))==false){
		alert("Please enter a valid date")
		return false
	}
return true
}

function chkdate(objName) {
   
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
                             
                             var dtStr = datefield;
                           //   alert("strDate.length:"+dtStr.length);
                             if (dtStr.length >= 11 || dtStr.length < 10) {
                              alert("Please correct Instrument Date, date format should be : dd/MM/yyyy")
                              return false;
                              }
                                   
                    var daysInMonth = DaysArray(12)
                    var pos1=dtStr.indexOf(dtCh);
                   //  alert("pos1:"+pos1);
                    var pos2=dtStr.indexOf(dtCh,pos1+1);
                  //    alert("pos2:"+pos2);
                    var strDay=dtStr.substring(0,pos1);
                   //  alert("strDay:"+strDay);
                    var strMonth=dtStr.substring(pos1+1,pos2);
                  //  alert("strMonth:"+strMonth);
                    var strYear=dtStr.substring(pos2+1);
                    strYr=strYear;
                  //  alert("strYear:"+strYear);    
                  month=parseInt(strMonth.value);
                  day=parseInt(strDay.value);
                  year=parseInt(strYr);
                //  alert("day:"+day+"month:"+month+"year:"+year);
                  if (pos1==-1 || pos2==-1){
                      alert("Please correct Instrument Date, date format should be : dd/MM/yyyy")
                      return false
                    }
                	if (strMonth.length<1 || month<1 || month>12){
                  	alert("Please enter a valid month for Intrument Date")
                  	return false
                  }
                  if (strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
                  	alert("Please enter a valid day for Instrument Date")
                    return false
                }
                  if (strYear.length != 4 || year==0 || year<minYear || year>maxYear){
                  alert("Please enter a valid 4 digit year between "+minYear+" and "+maxYear)
                	return false
                  }
                 if (dtStr.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, dtCh))==false){
                    alert("Please enter a valid date")
                  	return false
                   } 
                    return true;     
                  }                         
         
    function calSum(tableID)
    {
  
     var table = document.getElementById(tableID);  
     var rowCount = table.rows.length; 
     var sum=0;
      for (var i=0;i<rowCount;i++)
      {
       var row = table.rows[i]; 
       var fi5 = row.cells[6].childNodes[0].value;
 
       for (var k = 0; k < fi5.length; k++) {
			var ch = fi5.substring(k, k+1);

			if ((ch < "0" || ch > "9")  && ch != "-" && ch != '.')
			  {
			 alert("the value Should be number at fi5 Lopcation :-->"+fi5);
                          return false;
			  }
		  }
        if (!(isNaN(parseFloat(fi5))) && fi5!="")
        {
      sum+=parseFloat(fi5);
        
      }
     
      }  
  
    instrumentTotal.innerHTML=sum; 
    }
         
         

    function save(tableID)
    {
               var table = document.getElementById(tableID);  
                var rowCount = table.rows.length;  
                 for (var i=0;i<rowCount;i++)
                { 
                     var row = table.rows[i]; 
                     var fi1 = row.cells[2].childNodes[0].value;
                     var fi2 = row.cells[3].childNodes[0].value;
                     var fi3 = row.cells[4].childNodes[0].value;
                     var fi4 = row.cells[5].childNodes[0].value;
                     var fi5 = row.cells[6].childNodes[0].value;
                     var fi6 = row.cells[7].childNodes[0].value;
              
            if(fi4!=""){
            if (chkdate(fi4) == false) {
            alert("That Instrument date is " +fi4+ "  invalid. date Format should be dd/MM/yyyy  Please try again.");
             return false;
            }
            }


             if (fi5!="")
                {
              
                	for (var k = 0; k < fi5.length; k++) {
			var ch = fi5.substring(k, k+1);

			if ((ch < "0" || ch > "9")  && ch != "-" && ch != '.')
			  {
			 alert("the value Should be number at fi5 Location :-->"+fi5);
                          return false;
			  }
		}
                }


               if (fi2=="" && fi3=="" && fi4=="" && fi5=="" && fi6=="")
                   { 
                      alert ("please remove empty rows from the table ");
                      return false;
                   }
                
               }
                  
                  
               
                var bankNames = document.forms[0].bankNames.value;
                var ltrDt = document.forms[0].ltrDts.value;
                
            
                 if(bankNames == "" || bankNames == null){
                    alert("Pleaes enter bankNames");
                    
                    document.forms[0].bankNames.focus();
                    return false;
                }
                 if(ltrDt == "" || ltrDt == null){
                    alert("Pleaes enter letter date");
                   
                    document.forms[0].ltrDt.focus();
                    return false;
                }
                  
                  
          
                 document.ioForm.target ="_self";
                 document.ioForm.method="POST";
                 document.ioForm.action="addCorrespondence.do?method=addCorrespondence&rowcount="+rowCount;
                 document.ioForm.submit();
             
    }
              
              

   function resetColor(field){
        field.style.backgroundColor = 'white';
    }

     </SCRIPT>  
</HEAD>
<BODY>
  <html:errors/>
	<html:form name="ioForm" type="org.apache.struts.validator.DynaValidatorActionForm"  action="InwardCorrespondence.do?method=inwardCorrespondence" method="POST">
   
        <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR>
										<TD width="30%" class="Heading">Insert Inward Correspondence Details&nbsp;</TD>
										<TD width="70%"><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
									</TR>
									<TR>
										<TD colspan="4" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
									</TR>
        </TABLE>
        <TABLE>
  	  <TR>
            <td width="30%" class="ColumnBackground "><div align="center">Bank Name&nbsp;</div></td>
             <TD width="100"> <html:select property="bankNames" name="ioForm">
             <html:option value="">Select</html:option>
             <html:options name="ioForm" property="getAllMembers"/>
             </html:select></TD>
             <td width="7%" class="ColumnBackground "><div align="center">Place&nbsp;</div></td> 
             <TD width="100"> <html:text property="places" name="ioForm" size="20" value=""/> </TD>  
             </TR>
             <TR><td width="30%" class="ColumnBackground "><div align="center">Ltr Ref No&nbsp;</div></td>
             <TD width="100"> <html:text property="referenceIds" name="ioForm" size="110" value=""/> </TD> 
             <td width="7%" class="ColumnBackground "><div align="center">Ltr Date&nbsp;</div></td>
		         <TD width="100"> <html:text property="ltrDts" name="ioForm" size="20" value="" onblur= "javascript:isDate()"/></TD>
          </TR>
            <TR><td width="30%" class="ColumnBackground "><div align="center"><bean:message key="subject"/>	&nbsp;</div></td>
          	  	<TD width="100"> <html:text property="subjects" name="ioForm" size="110" value=""/> </TD>
		            <TD colspan="2" width="130" class="ColumnBackground ">&nbsp;</TD>        
            </TR>
        </TABLE>
        <TABLE>
             <tr> 
                <td colspan="12">
                <table width="100%" border="0" cellspacing="1" cellpadding="0">
                <tr> 
                  <td width="3%" class="ColumnBackground "><div align="center">&nbsp;</div></td>
                  <td width="2%"  align="center" valign="middle" class="ColumnBackground "><div align="center">Sr.<br>No.</div></td>
                  <td width="10%" class="ColumnBackground "><div align="center">Inward Dt&nbsp;</div></td>
               
                  <td colspan="16%" width="14%" class="ColumnBackground "><div align="center">Drawn on Bank&nbsp;</div></td>
               
                  <td width="11%" class="ColumnBackground "><div align="center">Instrument No&nbsp;</div></td>
                  <td width="12%" class="ColumnBackground "><div align="center">Instrument Dt&nbsp;</div></td>
                   <td width="12%" class="ColumnBackground "><div align="center">Instrument Amt&nbsp;</div></td>
                  <td width="10%" class="ColumnBackground "><div align="center">Section&nbsp;</div></td>
                 <td width="14%" class="ColumnBackground "><div align="center"><bean:message key="assignedTo"/>&nbsp;</div></td>
             
               </tr> 
              
       </td>
        </tr>   
    </TABLE>
   <TABLE  id="dataTable"  border="1">
	      
		<%for (int i=1;i<=1;i++){%>
    
                <TR>
			<TD><INPUT type="checkbox" name="chk" value="ON"/></TD>
			<TD> <label  id="index"><%=i%> </label> </TD>
			<TD width="10"><bean:write property="inwardDts" name="ioForm"/>&nbsp;</TD>
                        <TD> <html:text property="drawnonBank" name="ioForm" size="30" value=""/> </TD>
			<TD> <html:text property="sourceIds" name="ioForm" size="20" maxlength="6" value="" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/> </TD>
			<TD> <html:text property="instrumentDts" name="ioForm" size="20" value=""/> </TD>
		  <TD> <html:text property="instrumentAmt" name="ioForm" size="20" value="" onblur="calSum('dataTable')"/> </TD>
                  
	    <TD> 
                    <html:select property="section" name="ioForm">
                                  <html:option value="">Select</html:option> 
                                  <html:option value="GF">GF</html:option>
                                  <html:option value="ASF">ASF</html:option>
                                   <html:option value="CLAIM">CLAIM</html:option>
                                   <html:option value="OTHERS">OTHERS</html:option>
                                   <html:option value="RTI">RTI</html:option>
                  </html:select>
            </TD>
             <TD> 
                <html:select property="assignedTo" name="ioForm">
					<html:option value="">Select</html:option>
					<html:options property="getUserNames" name="ioForm"/>			
                </html:select>
            </TD>
        </TR>
  <%}%>
		
	</TABLE> 
  
  <TABLE width="100%">
  <TR>
  <TD colspan="20"> 
  <TABLE width="100%">
  <TR>
   <TD class="ColumnBackground" colspan="6">Total Instrument Amount&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</TD>
   <TD class="ColumnBackground" colspan="14" id="instrumentTotal">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
    <INPUT type="text" name="instrumentTotal" maxlength="80" size="20" /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   </TD>
  </TR></TABLE></TD></TR></TABLE>
  
	<TABLE width="100%">
  <TR>
  <TD colspan="20"> 
  <TABLE width="100%">
  <TR>
    <TD colspan="18">
        <INPUT type="button" value="SAVE" onclick="save('dataTable')" /> 
<!--	<INPUT type="button" value="Add Row" onclick="addRow('dataTable')" /> -->
<!--	<INPUT type="button" value="Delete Row" onclick="deleteRow('dataTable')" /> -->
    </TD>
       <!--  <TD colspan="2" align="right">
         <INPUT type="button" value="SAVE" onclick="save('dataTable')" /> 
         </TD> -->
    </TR>
    </TABLE>
    </TD>
    </TR>
    </TABLE>
</html:form> 




</BODY>
</HTML>