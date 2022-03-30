<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@page import="org.apache.struts.validator.DynaValidatorActionForm"%>
<%@page import ="java.text.SimpleDateFormat"%>
<%@page import ="java.text.DecimalFormat"%>
<%@page import="java.util.Date"%>
<%DecimalFormat decimalFormat = new DecimalFormat("##########.##");%>
<% session.setAttribute("CurrentPage","UpdateInvestmentetailsInput.do?method=UpdateInvestmentetailsInput");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<script>
function calculateMaturityDt()
{
 //alert("calculateMaturityDt Entered");
  var dateOfDepObj=findObj("depositDate");
  var depositDate;
  var index=0;
	var index1=0;
  var dayVal;
  var monthVal;
  var yearVal;
  var tenureday;
  var tenuremonth;
  var tenureyear;
  var tenureyear=findObj("years");
  var tenuremonth=findObj("months");
  var tenureday=findObj("days");
  var dateOfMatObj=findObj("maturityDt");
  var matDate="";
  
  if (dateOfDepObj!=null && dateOfDepObj.value!="")
	{
		depositDate = new String(dateOfDepObj.value);
	}
  
  if(depositDate!=null && depositDate!="" )
  {
  //alert("depositDate:"+depositDate);
  index=depositDate.indexOf("/");
  index1=depositDate.lastIndexOf("/");
  //alert("index:"+index+" Last Index:"+index1);
  //dayVal = parseInt(depositDate.substring(0, index));  
  //monthVal = parseInt(depositDate.substring(index+1, index1));
//	yearVal = parseInt(depositDate.substring(index1+1, depositDate.length));
 if (depositDate.length >= 11 || depositDate.length < 10) 
    {
          alert("Please correct Letter Date, date format should be : dd/MM/yyyy");
          return false;
     }
   dayVal = depositDate.substring(0, index);  
   monthVal = depositDate.substring(index+1, index1);
   yearVal = depositDate.substring(index1+1, depositDate.length);
  if (dayVal.charAt(0)=="0" && dayVal.length>1) //alert("dayvalue:"+dayVal);
	if (monthVal.charAt(0)=="0" && monthVal.length>1) monthVal=monthVal.substring(1)
	for (var i = 1; i <= 3; i++) {
		if (yearVal.charAt(0)=="0" && yearVal.length>1) yearVal=yearVal.substring(1)
	}
	dayVal=parseInt(dayVal)
	monthVal=parseInt(monthVal)
	yearVal=parseInt(yearVal)
  
 // alert("dayVal:"+dayVal+" monthVal:"+monthVal+" yearVal:"+yearVal);
 // alert(" tenure year:"+tenureyear.value+" tenure month:"+tenuremonth.value+ " tenure day:"+tenureday.value);  
  
  var i;
  var j;
  var t;
  
  if(tenureday.value!=null && tenureday.value!=""){
   dayVal = dayVal+parseInt(tenureday.value);
   if((dayVal/30) > 1)
  {
   i = Math.floor(dayVal/30);
    if(i >= 1)
    {
        monthVal = monthVal+i;
        dayVal=dayVal-(30*i);
        if(monthVal > 12 )  
        {
          j = Math.floor(monthVal/12);
          yearVal= yearVal+j;
          monthVal=parseInt(monthVal%12);
          if(monthVal==0){
           monthVal=12;
          }
        }
    }
  }
  }
   if(tenuremonth.value!=null && tenuremonth.value!=""){  
 //  monthVal= parseInt(tenuremonth.value);
   if((parseInt(tenuremonth.value)/12) > 1 ){
   j = Math.floor(parseInt(tenuremonth.value)/12);
   // alert("add month value:"+(parseInt(tenuremonth.value)%12));
   if(j>=1){  
      yearVal= yearVal+j;
      //alert("Month Value:"+monthVal);
      monthVal=monthVal+parseInt(parseInt(tenuremonth.value%12));
      if(monthVal==0){
       monthVal=12;
      }
      if(monthVal> 12){
      //alert("monthVal:"+monthVal);
      t =  Math.floor(parseInt(monthVal)/12);
      //alert("t:"+t)
      if(t >=1 ) yearVal=yearVal+t;
      monthVal = monthVal-12;
      }
   }  
   }
  }
   if(tenureyear.value!=null && tenureyear.value!=""){
   yearVal = yearVal+parseInt(tenureyear.value);
  }  
  
 
  
 
  
 // alert("dayVal:"+dayVal+" monthVal:"+monthVal+" yearVal:"+yearVal);
  if (dayVal<10)
		{
			dayVal="0"+dayVal;
		}
		if (monthVal<10)
		{
			monthVal="0"+monthVal;
		}
  matDate=dayVal+"/"+monthVal+"/"+yearVal;
 
  }
  dateOfMatObj.value=matDate;
}
</script>


<% 
  SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
  Date systemDate  = new Date();
  String sysDate = dateFormat.format(systemDate);
  DynaValidatorActionForm dynaForm = (DynaValidatorActionForm)session.getAttribute("ifForm") ;
  String maturityDt =dateFormat.format(dynaForm.get("maturityDate"));
 // System.out.println("depositAmount:"+decimalFormat.format(dynaForm.get("depositAmt")));
  dynaForm.set("depositAmount",decimalFormat.format(dynaForm.get("depositAmt")));
%>

<body >
<html:form action="displayInvestmentDetails.do?method=displayInvestmentDetails" method="POST" enctype="multipart/form-data">
<html:errors />
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td><br></td>
	</tr>
    <tr> 
      <td width="20" align="right" valign="bottom"><img src="images/TableLeftTop.gif" width="20" height="31"></td>
      <td background="images/TableBackground1.gif"><img src="images/InvestmentManagementHeading.gif" width="169" height="25"></td>
      <td width="20" align="left" valign="bottom"><img src="images/TableRightTop.gif" width="23" height="31"></td>
    </tr>
    <tr> 
      <td width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</td>
      <td>
      <table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
	  <tr>
	  <TD>			
			<DIV align="right">			
				<%--<A HREF="javascript:submitForm('helpFixedDepositDetails.do?method=helpFixedDepositDetails')">
			    HELP</A> --%>
			</DIV>
		</td>
	  </tr>
	<TR>
		<TD colspan="4"> 
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
				<TR>
					<TD width="31%" class="Heading">
					<bean:message key="investmentDetails" /></TD>
					<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
				</TR>
				<TR>
					<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
				</TR>

			</TABLE>
		</TD>

	</TR>      
      
          <tr> 
            <td><table width="100%" border="0" cellspacing="1" cellpadding="1">
                <tr > 
                  <td colspan="2"></td>
                </tr>
               <tr>
					<td class="ColumnBackground"><div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;
					<bean:message key="dateOfDeposit"/>&nbsp;&nbsp;
					</div></td>
					<td class="tableData" align="center" colspan="2"><div align="left">
					<html:text  property="depositDate" maxlength="10" onkeypress="return dateOnly(this, event)" onkeyup="isValidDate(this)"/>&nbsp;<img src="images/CalendarIcon.gif" onclick="showCalendar('ifForm.depositDate')" align="center" width="24" height="22"> &nbsp; &nbsp;
					</div></td>
				</tr>
        
        <TR align="left">
					<TD align="left" valign="top" class="ColumnBackground">
						&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="BankName" />
					</TD>
					<TD align="left" class="TableData" colspan="2">
						<html:text property="bankName" size="60" alt="bankName" name="ifForm" maxlength="60"/>        
					</TD>
				</tr>
        
        <tr>
					<td class="ColumnBackground"><div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="depositAmt"/>
					</div></td>
					<td class="tableData" colspan="2"><div align="left">
						<html:text  property="depositAmount" maxlength="16"/> <bean:message key="inRs"/>
					</div></td>
				</tr>  
				<tr>
					<td class="ColumnBackground" width="30%"><div align="left">&nbsp;<bean:message key="compoundingFrequency"/> </div></td>
					<td class="tableData" colspan="2"><div align="left">
                        <html:select property="compoundingFrequency" styleId="rating"  name="ifForm">
							<html:option value="">Select </html:option>
							<html:option value="12">Monthly </html:option>
							<html:option value="4">Quarterly </html:option>
							<html:option value="2">Half-Yearly </html:option>
							<html:option value="1">Annually </html:option>
						</html:select>
					</div></td>
				</tr>
        <tr>
					<td class="ColumnBackground" colspan="1"><div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="tenureType"/> 
					</div></td>
          <td class="ColumnBackground" colspan="3"><table><tr><td class="ColumnBackground" colspan="1"><bean:message key="years"/>&nbsp;&nbsp;<html:text  property="years" size="6"  maxlength="3" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)" /></td>
          <td class="ColumnBackground" colspan="1"><bean:message key="months"/>&nbsp;&nbsp;<html:text  property="months" size="6" maxlength="3" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)" /></td>
           <td class="ColumnBackground" colspan="1"><bean:message key="days"/>&nbsp;&nbsp;<html:text  property="days" size="6" maxlength="4" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)" /></td>
           </tr> </table></td>
        </tr>
        <tr>
					<td class="ColumnBackground"><div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="interestRate"/> 
					</div></td>
					<td class="tableData" colspan="2"><div align="left">
						<html:text  property="rateOfInterest" maxlength="5" onkeypress="return decimalOnly(this, event)" onkeyup="isValidDecimal(this)" /> <bean:message key="inPa"/> 
					</div></td>
				</tr>    
			<%--	<tr>
					<td class="ColumnBackground"><div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="fdReceiptNumber"/> 
					</div></td>
					<td class="tableData" colspan="2"><div align="left">
						<html:text  property="receiptNumber" maxlength="25"/>
					</div></td>
				</tr>--%>	
        <tr>
					<td class="ColumnBackground"><div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp; 
					<bean:message key="maturityDate"/>
					</div></td>
					<td class="tableData" colspan="2"><div align="left">
						<html:text property="maturityDt" maxlength="10" onclick="calculateMaturityDt()"/><img src="images/CalendarIcon.gif" onclick="showCalendar('ifForm.maturityDt')" align="center">
					</div></td>
				</tr>
        <tr>
					<td class="ColumnBackground"><div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="maturityAmount"/>
					</div></td>
					<td class="tableData" colspan="2"><div align="left">
						<html:text  property="maturityAmount" maxlength="16" onkeypress="return decimalOnly(this, event, 13)" onkeyup="isValidDecimal(this)"/> <bean:message key="inRs"/>
					</div></td>
                </tr>
				
              </table></td>
          </tr>
          <tr > 
            <td height="20" >&nbsp;</td>
          </tr>
          <tr > 
            <td align="center" valign="baseline" > <div align="center"><html:link href="javascript:submitForm('displayInvestmentDetails.do?method=afterUpdateInvestmentDetails')"><img src="images/Save.gif" alt="Save" width="49" height="37" border="0"></html:link><html:link href="javascript:document.ifForm.reset()"><img src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></html:link> 
              </div></td>
          </tr>
        </table></td>
      <td width="20" background="images/TableVerticalRightBG.gif">&nbsp;</td>
    </tr>
    <tr> 
      <td width="20" align="right" valign="top"><img src="images/TableLeftBottom1.gif" width="20" height="15"></td>
      <td background="images/TableBackground2.gif">&nbsp;</td>
      <td width="20" align="left" valign="top"><img src="images/TableRightBottom1.gif" width="23" height="15"></td>
    </tr>
  </table>
  <br>
  <p>&nbsp;</p>
</html:form>
</body>