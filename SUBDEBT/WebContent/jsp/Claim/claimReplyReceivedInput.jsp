<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@ include file="/jsp/SetMenuInfo.jsp"%>

<% session.setAttribute("CurrentPage","claimReplyReceivedInput.do?method=claimReplyReceivedInput");%>

        <SCRIPT type="text/javascript" LANGUAGE="JavaScript">
        function saveForm()
        {
		var inwardno = document.getElementById('inwardno').value;
		var inwarddt = document.getElementById('inwarddt').value;
		var claimno = document.cpTcDetailsForm.claimRefNum.value;
		
		if(claimno == null || claimno == ""){
			alert("please enter claim number");	
			return false;
			}
		if(inwardno == null || inwardno == ""){
			alert("please enter inward number");
			return false;
			}
		if(inwarddt == null || inwarddt == ""){
			alert("please enter inward date");
			return false;
			}
		
			
        document.cpTcDetailsForm.target ="_self";
        document.cpTcDetailsForm.method="POST";
        document.cpTcDetailsForm.action="insertClaimReplyReceivedDetails.do?method=insertClaimReplyReceivedDetails";
        document.cpTcDetailsForm.submit();
        	
        }
        </SCRIPT>

<table width="725" border="0" cellpadding="0" cellspacing="0">
 <html:errors/>
 <html:form name="cpTcDetailsForm" type="org.apache.struts.validator.DynaValidatorActionForm" action="insertClaimReplyReceivedDetails.do?method=insertClaimReplyReceivedDetails"
            method="POST" enctype="multipart/form-data">
  <tr>
   <td width="20" align="right" valign="bottom">
    <img src="images/TableLeftTop.gif" width="20" height="31"></img>
   </td>
   <td background="images/TableBackground1.gif">&nbsp;</td>
   <td width="20" align="left" valign="bottom">
    <img src="images/TableRightTop.gif" width="23" height="31"></img>
   </td>
  </tr>
  <tr>
   <td width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</td>
   <td>
    <div align="right">
     <table width="100%" border="0" align="left" cellpadding="0"
            cellspacing="0">
      <tr>
       <td>
        <table width="100%" border="0" cellspacing="1" cellpadding="1">
         <tr>
          <td colspan="8">
           <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
             <td width="30%" class="Heading">   
              Claim Reply Received 
             </td>
             <TD><IMG src="images/TriangleSubhead.gif" alt="" height="19" ></TD>
            </tr>
            <tr>
             <TD colspan="3" class="Heading"><IMG src="images/Clear.gif" alt="" height="5" width="5"></TD>
            </tr>
           </table>
          </td>
         </tr>
		 <tr align="left" valign="top">
          <td align="left" valign="top" class="ColumnBackground">&nbsp;
           <font color="#FF0000" size="2">*</font>
           <bean:message key="cpclaimrefnumber"/>
          </td>
          <td align="left" valign="top" class="TableData">
			<html:text property="claimRefNum" size="20" maxlength="20" name="cpTcDetailsForm"/>
          </td>
        </tr>
        <tr align="left" valign="top">
          <td align="left" valign="top" class="ColumnBackground">&nbsp;
           <font color="#FF0000" size="2">*</font>
           Inward Id
          </td>
          <td align="left" valign="top" class="TableData" colspan="4">
			<input type="text" name="inwardno" id="inwardno"  />
          </td>
        </tr>
		<tr align="left" valign="top">
			<td align="left" valign="top" class="ColumnBackground">&nbsp;
			<font color="#FF0000" size="2">*</font>
           Inward Date
          </td>
          <td align="left" valign="top" class="TableData">      
			<input type="text" name="inwarddt" id="inwarddt" />
          <IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('cpTcDetailsForm.inwarddt')" align="center"></TD>
        </TR> 
        </table>
       </td>
      </tr>
      <tr>
       <td height="20">&nbsp;</td>
      </tr>
      <tr>
       <td align="center" valign="baseline">
        <div align="center">
         <!--<a href="javascript:submitForm('declartionDetails.do?method=getDeclartaionDetailData')"> -->
                  <a href="#" onclick="saveForm();">
          <img src="images/OK.gif" alt="OK" width="49" height="37" border="0"></img>
         </a>
          
         <a href="javascript:document.cpTcDetailsForm.reset()">
          <img src="images/Reset.gif" alt="Reset" width="49" height="37"
               border="0"></img>
         </a>
          
          <a href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>">
          <img src="images/Cancel.gif" alt="Cancel" width="49" height="37"
               border="0"></img>
         </a>
        </div>
       </td>
      </tr>
     </table>
    </div>
   </td>
   <td width="20" background="images/TableVerticalRightBG.gif">&nbsp;</td>
  </tr>
  <tr>
   <td width="20" align="right" valign="top">
    <img src="images/TableLeftBottom1.gif" width="20" height="15"></img>
   </td>
   <td background="images/TableBackground2.gif">&nbsp;</td>
   <td width="20" align="left" valign="top">
    <img src="images/TableRightBottom1.gif" width="23" height="15"></img>
   </td>
  </tr>
 </html:form>
</table>
