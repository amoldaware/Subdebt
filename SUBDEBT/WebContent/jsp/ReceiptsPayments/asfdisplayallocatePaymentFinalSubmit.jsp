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

<script>
function disableBackButton()
{
window.history.forward();
//window.history.forward(1);
}
setTimeout("disableBackButton()", 0);
</script>
<script>

function submitFinalMakePayment(action)
{
  var x;
    if (confirm("Please note that after this the selected RP Nos will not be available for modification/cancellation ,want to proceed!") == true) {
        x = "You pressed PROCEED!";
        document.forms[0].action=action;
    	document.forms[0].target="_self";
    	document.forms[0].method="POST";
    	document.forms[0].submit();
                  
    } else {
        x = "You pressed CANCEL!";
    }
    document.getElementById("demo").innerHTML = x;



//	alert("Please note that after these  selected RP Nos will not be available for modification/cancellation ,want to proceed");

	
}

</script>



<script language="javascript" type="text/javascript"> 
</script>
<% session.setAttribute("CurrentPage","asfdisplayallocatePaymentFinal.do?method=asfdisplayallocatePaymentFinal");%>
** Payment Initiated Successfully For following RP's. Please use Virtual A/C No, Amount & IFSC Code for making payments for respective Pay Ids.
               Payment shall succeed only if these details are correctly entered.

  <body onload="disableBackButton()">
	<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
    <html:errors />
  
		<html:form name = "rpAllocationForm" type="com.cgtsi.actionform.RPActionForm"  action="asfdisplayallocatePaymentFinal.do?method=asfdisplayallocatePaymentFinal" method="POST" >
		 
			
		
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
                  
                  <td valign="top"  class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>PayIds</strong></div></td>
                  
                   <td valign="top"  class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>Virtual A/C No.</strong></div></td>
                  
                  <td valign="top"  class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>Amount</strong></div></td>
                    
                     <td valign="top"  class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>IFSC Code</strong></div></td>
                  </tr>
                  
                  
                   <%
								int j=0;
                                int k=0;
                                
								%>
								
							
								
                           <logic:iterate id="object" name="rpAllocationForm" property="makepaymentFinal" indexId="index">
								<%
								
								 RPActionForm rReport = (RPActionForm)object;
								DecimalFormat dec = new DecimalFormat("#0.00");
																			
								%>
								
								
							 <TR>
             <TD width="10%" align="left" valign="top" class="ColumnBackground1"><div align="center"><%=Integer.parseInt(index+"")+1%></div></TD>
                                       
		     <TD width="15%" align="left" valign="top" class="ColumnBackground1"><div align="center"><%=rReport.getPaymentIdR()%></div></TD>
		     
		     <TD width="15%" align="left" valign="top" class="ColumnBackground1"><div align="center"><%=rReport.getVaccno()%></div></TD>
							
		   <TD width="15%" align="left" valign="top" class="ColumnBackground1"><div align="center"><%=dec.format(rReport.getAmmount2())%></div></TD>
		   
		   <TD width="15%" align="left" valign="top" class="ColumnBackground1"><div align="center">CORP0000633</div></TD>			                
               
               </TR>
              
               <%j++; %>
               <%k++; %>
			  </logic:iterate>			
				

		 </TABLE>	
		     
              <tr align="center" valign="baseline">
           		 <td colspan="4"> 
           		 <div align="center"> 
		         <a href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>"><img src="images/OK.gif" width="40" height="37" border="0"></a>
		          <A href="javascript:printpage()"><IMG src="images/Print.gif" alt="Print" width="49" height="37" border="0"></A>
		          <a href="javascript:submitForm('asfdisplayallocatePaymentFinalSubmit.do?method=exportExcelNew&fileType=xls');">Export To Excel</a>
		          <a href="javascript:submitForm('asfdisplayallocatePaymentFinalSubmit.do?method=exportCsvNew&fileType=csv');">Export To Csv</a>
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