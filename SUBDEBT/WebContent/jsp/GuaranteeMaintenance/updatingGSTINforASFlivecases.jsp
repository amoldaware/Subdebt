<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.cgtsi.receiptspayments.DANSummary"%>
<%@ page import="com.cgtsi.receiptspayments.AllocationDetail"%>
<%@ page import="com.cgtsi.actionform.RPActionForm"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="com.cgtsi.claim.ClaimConstants"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@page import="com.cgtsi.registration.MLIInfo"%>
<%@page import="java.util.Iterator"%>
<%@ page import="com.cgtsi.util.SessionConstants"%>

<%@page import="com.cgtsi.registration.MLIInfo"%>
<%@page import="java.util.Iterator"%>
<%@ page import="com.cgtsi.util.SessionConstants"%>	
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@page import="java.util.HashSet"%>


<%
	String thiskey = "";
	session.setAttribute("CurrentPage","getIdForUpdateGSTIN.do?method=getIdForUpdateGSTIN");
%>
<style type="text/css">

.ds_box {
	background-color: #FFF;
	border: 1px solid #000;
	position: absolute;
	z-index: 32767;
}

.ds_tbl {
	background-color: #FFF;
}

.ds_head {
	background-color: #333;
	color: #FFF;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 13px;
	font-weight: bold;
	text-align: center;
	letter-spacing: 2px;
}

.ds_subhead {
	background-color: #CCC;
	color: #000;
	font-size: 12px;
	font-weight: bold;
	text-align: center;
	font-family: Arial, Helvetica, sans-serif;
	width: 32px;
}

.ds_cell {
	background-color: #EEE;
	color: #000;
	font-size: 13px;
	text-align: center;
	font-family: Arial, Helvetica, sans-serif;
	padding: 5px;
	cursor: pointer;
}

.ds_cell:hover {
	background-color: #F3F3F3;
} /* This hover code won't work for IE */

</style>
</head>
<body>

<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
<tr><td id="ds_calclass">
</td></tr>
</table>

<script type="text/javascript">
// <!-- <![CDATA[

// Project: Dynamic Date Selector (DtTvB) - 2006-03-16
// Script featured on JavaScript Kit- http://www.javascriptkit.com
// Code begin...
// Set the initial date.
var ds_i_date = new Date();
ds_c_month = ds_i_date.getMonth() + 1;
ds_c_year = ds_i_date.getFullYear();

// Get Element By Id
function ds_getel(id) {
	return document.getElementById(id);
}

// Get the left and the top of the element.
function ds_getleft(el) {
	var tmp = el.offsetLeft;
	el = el.offsetParent
	while(el) {
		tmp += el.offsetLeft;
		el = el.offsetParent;
	}
	return tmp;
}
function ds_gettop(el) {
	var tmp = el.offsetTop;
	el = el.offsetParent
	while(el) {
		tmp += el.offsetTop;
		el = el.offsetParent;
	}
	return tmp;
}

// Output Element
var ds_oe = ds_getel('ds_calclass');
// Container
var ds_ce = ds_getel('ds_conclass');

// Output Buffering
var ds_ob = ''; 
function ds_ob_clean() {
	ds_ob = '';
}
function ds_ob_flush() {
	ds_oe.innerHTML = ds_ob;
	ds_ob_clean();
}
function ds_echo(t) {
	ds_ob += t;
}

var ds_element; // Text Element...

var ds_monthnames = [
'January', 'February', 'March', 'April', 'May', 'June',
'July', 'August', 'September', 'October', 'November', 'December'
]; // You can translate it for your language.

var ds_daynames = [
'Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'
]; // You can translate it for your language.

// Calendar template
function ds_template_main_above(t) {
	return '<table cellpadding="3" cellspacing="1" class="ds_tbl">'
	     + '<tr>'
		 + '<td class="ds_head" style="cursor: pointer" onclick="ds_py();"><<</td>'
		 + '<td class="ds_head" style="cursor: pointer" onclick="ds_pm();"><</td>'
		 + '<td class="ds_head" style="cursor: pointer" onclick="ds_hi();" colspan="3">[Close]</td>'
		 + '<td class="ds_head" style="cursor: pointer" onclick="ds_nm();">></td>'
		 + '<td class="ds_head" style="cursor: pointer" onclick="ds_ny();">>></td>'
		 + '</tr>'
	     + '<tr>'
		 + '<td colspan="7" class="ds_head">' + t + '</td>'
		 + '</tr>'
		 + '<tr>';
}

function ds_template_day_row(t) {
	return '<td class="ds_subhead">' + t + '</td>';
	// Define width in CSS, XHTML 1.0 Strict doesn't have width property for it.
}

function ds_template_new_week() {
	return '</tr><tr>';
}

function ds_template_blank_cell(colspan) {
	return '<td colspan="' + colspan + '"></td>'
}

function ds_template_day(d, m, y) {
	return '<td class="ds_cell" onclick="ds_onclick(' + d + ',' + m + ',' + y + ')">' + d + '</td>';
	// Define width the day row.
}

function ds_template_main_below() {
	return '</tr>'
	     + '</table>';
}

// This one draws calendar...
function ds_draw_calendar(m, y) {
	// First clean the output buffer.
	ds_ob_clean();
	// Here we go, do the header
	ds_echo (ds_template_main_above(ds_monthnames[m - 1] + ' ' + y));
	for (i = 0; i < 7; i ++) {
		ds_echo (ds_template_day_row(ds_daynames[i]));
	}
	// Make a date object.
	var ds_dc_date = new Date();
	ds_dc_date.setMonth(m - 1);
	ds_dc_date.setFullYear(y);
	ds_dc_date.setDate(1);
	if (m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12) {
		days = 31;
	} else if (m == 4 || m == 6 || m == 9 || m == 11) {
		days = 30;
	} else {
		days = (y % 4 == 0) ? 29 : 28;
	}
	var first_day = ds_dc_date.getDay();
	var first_loop = 1;
	// Start the first week
	ds_echo (ds_template_new_week());
	// If sunday is not the first day of the month, make a blank cell...
	if (first_day != 0) {
		ds_echo (ds_template_blank_cell(first_day));
	}
	var j = first_day;
	for (i = 0; i < days; i ++) {
		// Today is sunday, make a new week.
		// If this sunday is the first day of the month,
		// we've made a new row for you already.
		if (j == 0 && !first_loop) {
			// New week!!
			ds_echo (ds_template_new_week());
		}
		// Make a row of that day!
		ds_echo (ds_template_day(i + 1, m, y));
		// This is not first loop anymore...
		first_loop = 0;
		// What is the next day?
		j ++;
		j %= 7;
	}
	// Do the footer
	ds_echo (ds_template_main_below());
	// And let's display..
	ds_ob_flush();
	// Scroll it into view.
	ds_ce.scrollIntoView();
}

// A function to show the calendar.
// When user click on the date, it will set the content of t.
function ds_sh(t) {
	// Set the element to set...
	ds_element = t;
	// Make a new date, and set the current month and year.
	var ds_sh_date = new Date();
	ds_c_month = ds_sh_date.getMonth() + 1;
	ds_c_year = ds_sh_date.getFullYear();
	// Draw the calendar
	ds_draw_calendar(ds_c_month, ds_c_year);
	// To change the position properly, we must show it first.
	ds_ce.style.display = '';
	// Move the calendar container!
	the_left = ds_getleft(t);
	the_top = ds_gettop(t) + t.offsetHeight;
	ds_ce.style.left = the_left + 'px';
	ds_ce.style.top = the_top + 'px';
	// Scroll it into view.
	ds_ce.scrollIntoView();
}

// Hide the calendar.
function ds_hi() {
	ds_ce.style.display = 'none';
}

// Moves to the next month...
function ds_nm() {
	// Increase the current month.
	ds_c_month ++;
	// We have passed December, let's go to the next year.
	// Increase the current year, and set the current month to January.
	if (ds_c_month > 12) {
		ds_c_month = 1; 
		ds_c_year++;
	}
	// Redraw the calendar.
	ds_draw_calendar(ds_c_month, ds_c_year);
}

// Moves to the previous month...
function ds_pm() {
	ds_c_month = ds_c_month - 1; // Can't use dash-dash here, it will make the page invalid.
	// We have passed January, let's go back to the previous year.
	// Decrease the current year, and set the current month to December.
	if (ds_c_month < 1) {
		ds_c_month = 12; 
		ds_c_year = ds_c_year - 1; // Can't use dash-dash here, it will make the page invalid.
	}
	// Redraw the calendar.
	ds_draw_calendar(ds_c_month, ds_c_year);
}

// Moves to the next year...
function ds_ny() {
	// Increase the current year.
	ds_c_year++;
	// Redraw the calendar.
	ds_draw_calendar(ds_c_month, ds_c_year);
}

// Moves to the previous year...
function ds_py() {
	// Decrease the current year.
	ds_c_year = ds_c_year - 1; // Can't use dash-dash here, it will make the page invalid.
	// Redraw the calendar.
	ds_draw_calendar(ds_c_month, ds_c_year);
}

// Format the date to output.
function ds_format_date(d, m, y) {
	// 2 digits month.
	m2 = '00' + m;
	m2 = m2.substr(m2.length - 2);
	// 2 digits day.
	d2 = '00' + d;
	d2 = d2.substr(d2.length - 2);
	// YYYY-MM-DD
	return y + '-' + m2 + '-' + d2;
}

// When the user clicks the day.
function ds_onclick(d, m, y) {
	// Hide the calendar.
	ds_hi();
	// Set the value of it, if we can.
	if (typeof(ds_element.value) != 'undefined') {
		ds_element.value = ds_format_date(d, m, y);
	// Maybe we want to set the HTML in it.
	} else if (typeof(ds_element.innerHTML) != 'undefined') {
		ds_element.innerHTML = ds_format_date(d, m, y);
	// I don't know how should we display it, just alert it to user.
	} else {
		alert (ds_format_date(d, m, y));
	}
}

// And here is the end.

// ]]> -->
</script>
<script language="javascript" type="text/javascript">


function submitFormR(action)
{

//	$('#onSubmit').click(function () {
	       // var object1=new Object();
		//	var dataList1 = [];
		var items=document.getElementsByClassName('checkbox');
		var selectedItems="";
		var arr=[];
		for(var i=0; i<items.length; i++){
			if(items[i].type=='checkbox' && items[i].checked==true)
				//selectedItems+=items[i].value+"\n";
			arr.push(items[i].value);
		}
		if(arr.length>0){
		  if(arr.length<=50){
			    //alert('Proceeded for the next');
				document.forms[0].action=action;
				document.forms[0].target="_self";
				document.forms[0].method="POST";
				document.forms[0].submit();
				}else{
					alert('You cannot select more than 50');
					
				}
			}else {		
				alert('Please select checkbox for GST Update');
			}
		
		
		

	//alert("rajukkkk");
	
	
	}
//}


function selectAll(chk)
{
	//var allocatedAmt="";
	//var receivedAmt="";
	//alert('1st outside loop');
	
	alert('rajuk');
	for(i=0;chk.length;i++)
	{
		//chk[i].checked=true;
		//alert('inside loop'+chk.length);
			chk[i].checked=true;
			if(i==50)
			{
break;
				}
	}
}


</script>
 
<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form  name = "rpAllocationForm" type="com.cgtsi.actionform.RPActionForm" action="getIdForUpdateGSTINDetails.do?method=getIdForUpdateGSTINDetails" method="POST" >
		<TR> 
				<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/GuaranteeMaintenanceHeading.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			
			<TD>
				<TABLE width="100%" border="0" align="left" cellpadding="1" cellspacing="1" class="tableData">
				<TR>
					<TD colspan="13"> 
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR>
							
							<TD align="Right" valign="top" ><h6><font class="fontsized">Total CGPANS Pending For GSTIN Updation:<%=request.getAttribute("cgpanCount")%></font></h6>
							</td>						
												
												
												
												
							
						</TR>
						<TR>
							<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
						</TR>
						</TABLE>
					</TD>
				</TR>
				
				<html:hidden property="bankId" name="rpAllocationForm"/>
				<html:hidden property="zoneId" name="rpAllocationForm"/>
				<html:hidden property="branchId" name="rpAllocationForm"/>
			
				<TR>
					<TD align="left" valign="top" class="ColumnBackground">
						<bean:message key="sNo" />
					</TD>
                    <TD align="left" valign="top" class="ColumnBackground">
						BRANCH NAME
					</TD> 
					<TD align="left" valign="top" class="ColumnBackground">
						CGPAN
					</TD>
                   <TD align="left" valign="top" class="ColumnBackground">
						UNIT NAME
					</TD>
					<TD align="left" valign="top" class="ColumnBackground">
						BRANCH STATE
					</TD>
                          
                          <TD align="left" valign="top" class="ColumnBackground">
						GSTIN NUMBER
					</TD>
                          
                                        
					<TD align="left" valign="top" class="ColumnBackground">
						UPDATE GSTIN NUMBER
					</TD>
					
		
				</TR>	
				
				<% int i = 0;
				System.out.println("rajukkk"+request.getAttribute("allstatelist"));
				//System.out.println("rajukkk"+request.getAttribute("statecode"));
				
				DecimalFormat df= new DecimalFormat("######################.##");
				df.setDecimalSeparatorAlwaysShown(false);

				int shortDanIndex = 0;
				String name="";
				String danNo = "";
                String cgpan="";
                String ssiname="";
				String allocated = "" ;
				String ststename="";
				Date danDate;
				String GSTIN="";
				 String jsMethodDef="";
				 String statecode="";

			
				AllocationDetail allocationDetail ;
				
				String checkboxKey=null;
				 %>
				
				   <%
								int j=0;
                                int k=0;
                                
								%>
							
			
				
				<logic:iterate id="object" name="rpAllocationForm" property="danSummaries" indexId="index">
				<%
				com.cgtsi.receiptspayments.DANSummary danSummary =(com.cgtsi.receiptspayments.DANSummary)object;
				
				String queryRespon = "";
				cgpan=danSummary.getCgpan();
				statecode= danSummary.getStateCode();
				// System.out.println("statecode ==="+statecode);
				
				 
				%>
					
				<TR>
					<TD align="left" valign="top" class="tableData"><%=Integer.parseInt(index+"")+1%></TD>
                                 
          
                <TD width="15%" align="left" valign="top" class="ColumnBackground1"><div align="center"><%=danSummary.getStateName()%></div>--></TD>
             <TD width="15%" align="left" valign="top" class="ColumnBackground1"><div align="center"><%=danSummary.getCgpan()%></div></TD>
             <TD width="15%" align="left" valign="top" class="ColumnBackground1"><div align="center"><%=danSummary.getUnitname()%></div></TD>
             
				   <TD align="left" valign="top" class="tableData">
				    <input type="text" id="gstNo<%=index %>" name="gstNo<%=index %>"  >  
				   	</TD>
				   	
				   		
						<TD align="left" valign="top" class="tableData"><!--

				
				   <input type="text"  id="dateNo<%=index %>"  name="dateNo<%=index %>"  value="dateNo<%=index %>" >
					<IMG src="images/CalendarIcon.gif" width="20"  onClick="showCalendar('dateNo<%=index %>')" />
				-->
				
				<input onclick="ds_sh(this);" name="actionDate<%=index %>"   id="actionDate<%=index %>"  readonly="readonly" style="cursor: text" /><br />
				</TD>
					
				
				             
					<TD align="left" valign="top" class="tableData">
          <input type="checkbox" class="checkbox" name="qryRemarks<%=index %>" id="qryRemarks<%=index %>"  />
					
		
			
					</TD>
					<TD align="left" valign="top" class="tableData">
						
					</TD>
				</TR>
               <%j++; %>
               <%k++; %>
			
				</logic:iterate>
                    
				
				<TR>
					<TD align="center" valign="baseline" colspan="10">
					<DIV align="center">
					
							
							<a href="javascript:submitFormR('getIdForUpdateGSTINDetails.do?method=getIdForUpdateGSTINDetails')">
							<IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></a>
						<A href="javascript:document.rpAllocationForm.reset()">
							<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>						
						<A href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>">
							<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>
					</DIV>
					</TD>
				</TR>
			</TABLE>
		</TD>

		<TD width="20" background="images/TableVerticalRightBG.gif">&nbsp;</TD>
		
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
</TABLE>