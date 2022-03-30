<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@page import = "org.apache.struts.action.DynaActionForm"%>
<%@page import ="java.text.SimpleDateFormat"%>
<%@page import ="java.util.Date"%>
<% SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");%>
<% DynaActionForm dynaForm = (DynaActionForm)session.getAttribute("ioForm"); 
  // String ltrDts = dateFormat.format(dynaForm.get("ltrDts")); 
 //  dynaForm.set("ltrDts",ltrDts);
 //  System.out.println("ltrDts:"+ltrDts);
  // String instrumentDts = dateFormat.format(dynaForm.get("instrumentDts")); 
 //  System.out.println("instrumentDts:"+instrumentDts);
  %> 

<% session.setAttribute("CurrentPage","displayCorrespondenceDetails.do?method=displayCorrespondenceDetails");%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors/>
	<html:form action="displayCorrespondenceDetails.do?method=displayCorrespondenceDetails" method="POST" enctype="multipart/form-data">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<DIV align="right">			
				</DIV>

				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
							<table width="100%" border="0" cellspacing="1" cellpadding="0">
							<tr> 
							  <td colspan="4">
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR>
										<TD width="30%" class="Heading">Display Correspondence Details&nbsp;</TD>
										<TD width="70%"><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
									</TR>
									<TR>
										<TD colspan="4" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
									</TR>
								</TABLE>
							  </td>
							</tr>
							 <tr align="left"> 
							   <td  class="ColumnBackground"> 
									<font color="#FF0000" size="2">*</font>	&nbsp;
									<bean:message key="inwardId"/>			
								</td>
								<td class="TableData">
                <bean:write property="inwardId" name="ioForm"/>
									     
								</td>
                <td  class="ColumnBackground"> 
									<font color="#FF0000" size="2">*</font>	&nbsp;
									Inward <bean:message key="chequeDate"/>			
								</td>
								<td class="TableData">
                <bean:write property="inwardDts" name="ioForm"/>
									<!-- <html:text property="inwardDts" size="20" alt="inwardDts" name="ioForm" maxlength="10"/>      -->   
								</td>
							 </tr>
               <tr align="left"> 
							   <td  class="ColumnBackground"> 
									<font color="#FF0000" size="2">*</font>	&nbsp;
									<bean:message key="chqBankName"/>			
								</td>
								<td class="TableData">
									 <html:text property="bankNames" size="60" alt="bankNames" name="ioForm" maxlength="60" onkeypress="resetColor(this);"/>        
								</td>
                <td  class="ColumnBackground"> 
									<font color="#FF0000" size="2">*</font>	&nbsp;
									 <bean:message key="drawnAtBank"/>			
								</td>
								<td class="TableData">
									 <html:text property="drawnonBank" size="30" alt="drawnonBank" name="ioForm" maxlength="30"/>        
								</td>
							 </tr>
               <tr align="left"> 
							   <td  class="ColumnBackground"> 
									<font color="#FF0000" size="2">*</font>	&nbsp;
									<bean:message key="place"/>			
								</td>
								<td class="TableData">
									 <html:text property="places" size="60" alt="places" name="ioForm" maxlength="60"/>        
								</td>
                <td  class="ColumnBackground"> 
									<font color="#FF0000" size="2">*</font>	&nbsp;
									 <bean:message key="subject"/>			
								</td>
								<td class="TableData">
									 <html:text property="subjects" size="30" alt="subjects" name="ioForm" maxlength="30"/>        
								</td>
							 </tr>
               
               <tr align="left"> 
							   <td  class="ColumnBackground"> 
									<font color="#FF0000" size="2">*</font>	&nbsp;
									<bean:message key="referenceNo"/>			
								</td>
								<td class="TableData">
									 <html:text property="referenceIds" size="60" alt="referenceIds" name="ioForm" maxlength="60"/>        
								</td>
                <td  class="ColumnBackground"> 
									<font color="#FF0000" size="2">*</font>	&nbsp;
									Ltr Date
								</td>
								<td class="TableData">
									 <html:text property="ltrDt" size="20" alt="ltrDt" name="ioForm" maxlength="10" onkeypress="resetColor(this);" onchange="isDate(this.value);"/>        
								</td>
							 </tr>
               
               <tr align="left"> 
							   <td  class="ColumnBackground"> 
									<font color="#FF0000" size="2">*</font>	&nbsp;
									<bean:message key="instrumentNumber"/>			
								</td>
								<td class="TableData">
									 <html:text property="sourceIds" size="20" alt="sourceIds" name="ioForm" maxlength="6" />        
								</td>
                 <td  class="ColumnBackground"> 
									<font color="#FF0000" size="2">*</font>	&nbsp;
									<bean:message key="instrumentDate"/>			
								</td>
								<td class="TableData">
									 <html:text property="instrumentDt" size="20" alt="instrumentDt" name="ioForm" maxlength="10" onchange="isDate(this.value);"/>        
								</td>
                </tr>
               <tr align="left"> 
							      <td  class="ColumnBackground"> 
									<font color="#FF0000" size="2">*</font>	&nbsp;
								<bean:message key="instrumentAmount"/>		
								</td>
								<td class="TableData">
									 <html:text property="instrumentAmt" size="20" alt="instrumentAmt" name="ioForm" maxlength="10"/>        
								</td>
                                                                <td  class="ColumnBackground"> 
									<font color="#FF0000" size="2">*</font>	&nbsp;
								Section
								</td>
								<td class="TableData">
                                                                <html:select property="section" name="ioForm">
                                                                        <html:option value="GF">GF</html:option>
                                                                        <html:option value="ASF">ASF</html:option>
                                                                        <html:option value="CLAIM">CLAIM</html:option>
                                                                        <html:option value="OTHERS">OTHERS</html:option>									
								</html:select>
                                                                </td>
                </tr>
                 <tr align="left"> 
                                                                <td  class="ColumnBackground"> 
									<font color="#FF0000" size="2">*</font>	&nbsp;
									<bean:message key="outwardId"/>			
								</td>
								<td class="TableData">
									 <html:text property="outwardId" size="20" alt="outwardId" name="ioForm" maxlength="100" />        
								</td>
                 <td  class="ColumnBackground"> 
									<font color="#FF0000" size="2">*</font>	&nbsp;
									Outward Dt		
								</td>
								<td class="TableData">
									 <html:text property="outwardDt" size="20" alt="outwardDt" name="ioForm" maxlength="10" onchange="isDate(this.value);"/>        
								   <IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('ioForm.outwardDt')" align="center">
                </td>
             		 </tr>
                 
                   <tr align="left"> 
							   <td  class="ColumnBackground"> 
									<font color="#FF0000" size="2">*</font>	&nbsp;
									<bean:message key="assignedTo"/>			
								</td>
								<td class="TableData">
								
							<html:select property="assignedTo" name="ioForm">
                    <html:option value="">Select</html:option>
                    <html:options property="getUserNames" name="ioForm"/>			
              	</html:select> 
                </td>
                <td class="TableData" colspan="2">
							
                </td>
                
                </tr>
                <tr>
                 <td class="ColumnBackground" colspan="1"><div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp; 
                  <bean:message key="reasons"/>
                  </div></td>
                  <td class="tableData" colspan="3"><div align="left">
                    <html:text property="reasons" size="100" alt="reasons" name="ioForm" maxlength="400" />
               
                  </div></td>
                </tr>
                 
                 
    				  </table>									
						</td>
					</tr>					
					<TR>
						<TD align="center" valign="baseline">
							<DIV align="center">
							<!--	<A href="javascript:submitForm('displayCorrespondenceDetails.do?method=afterUpdateCorrespondenceDetails')"> -->
                                                                <A href="#" onclick="saveForm();">
                                                                <IMG src="images/OK.gif" alt="Save" width="49" height="37" border="0"></A>	
							
								<A href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
								<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>
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
        
<script type="text/javascript" >

    function saveForm(){
    
               
               
                var bankNames = document.forms[0].bankNames.value;
                var ltrDt = document.forms[0].ltrDt.value;
                
            
                 if(bankNames == "" || bankNames == null){
                    alert("Pleaes enter bankNames");
                    document.forms[0].bankNames.style.backgroundColor = 'yellow';
                    document.forms[0].bankNames.focus();
                    return false;
                }
                 if(ltrDt == "" || ltrDt == null){
                    alert("Pleaes enter letter date");
                    document.forms[0].ltrDt.style.backgroundColor = 'yellow';
                    document.forms[0].ltrDt.focus();
                    return false;
                }
               
    
                 document.ioForm.target ="_self";
                 document.ioForm.method="POST";
                 document.ioForm.action="displayCorrespondenceDetails.do?method=afterUpdateCorrespondenceDetails";
                 document.ioForm.submit();
    }

   function resetColor(field){
        field.style.backgroundColor = 'white';
    }
    
     function isDate(field){
      var dtCh= "/";
      var minYear=1900;
      var maxYear=2050;
      //var dtStr=findObj("ltrDts").value;
     // alert("dtStr:"+dtStr);
    //  alert("dtStrLen:"+dtStr.length);
        if (field.length >= 11 || field.length < 10) 
        {
              alert("Please correct Letter Date, date format should be : dd/MM/yyyy");
              return false;
         }
	var daysInMonth = DaysArray(12)
	var pos1=field.indexOf(dtCh);
 // alert("pos1:"+pos1);
	var pos2=field.indexOf(dtCh,pos1+1);
//  alert("pos2:"+pos2);
	var strDay=field.substring(0,pos1);
 // alert("strDay:"+strDay);
	var strMonth=field.substring(pos1+1,pos2);
 // alert("strMonth:"+strMonth);
	var strYear=field.substring(pos2+1);
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
	if (field.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(field, dtCh))==false){
		alert("Please enter a valid date")
		return false
	}
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

 function isInteger(s){
	var i;
    for (i = 0; i < s.length; i++){   
        // Check that current character is number.
        var c = s.charAt(i);
        if (((c < "0") || (c > "9"))) return false;
    }
  
    return true;
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

</script>

        
</TABLE>


