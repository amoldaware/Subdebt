<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@ include file="/jsp/SetMenuInfo.jsp"%>
<SCRIPT language="JavaScript" type="text/JavaScript" src="<%=request.getContextPath()%>/js/CGTSI.js">
</SCRIPT>
<% session.setAttribute("CurrentPage","RecoveryAfterOTS.do?method=getRecoveryDetail");%>

        <SCRIPT LANGUAGE="JavaScript">  
        function Validation()
        {
         if(document.forms[0].enterMember.value=="" ||document.forms[0].enterMember.value.length < 12 )
        {
        alert("Please Enter Member Id of Correct Length ");
                 document.forms[0].target ="_self";
                 document.forms[0].method="POST";
                 document.forms[0].action="RecoveryAfterOTS.do?method=getRecoveryDetail";
                 document.forms[0].submit();
        }
//       else if(document.forms[0].enterCgpan.value !="" && document.forms[0].enterCgpan.value.length < 13)
//        {
//                 alert("Please Enter CGPAN of Correct Length ");
//                 document.forms[0].target ="_self";
//                 document.forms[0].method="POST";
//                 document.forms[0].action="/cgtsi-ViewController-context-root/RecoveryAfterOTS.do?method=getRecoveryDetail";
//                 document.forms[0].submit();
//                     
//        }

      else if (document.forms[0].enterCgpan.value == "")
      {
                 alert("Please Enter CGPAN Number  ");
                 document.forms[0].target ="_self";
                 document.forms[0].method="POST";
                 document.forms[0].action="RecoveryAfterOTS.do?method=getRecoveryDetail";
                 document.forms[0].submit();
      }
      else
        {
        
        document.forms[0].target ="_self";
        document.forms[0].method="POST";
        document.forms[0].action="RecoveryAfterOTS.do?method=getRecoveryDetailData";
        document.forms[0].submit();
        }	
        }
        </SCRIPT>

<table width="725" border="0" cellpadding="0" cellspacing="0">
 <html:errors/>
 <html:form action="RecoveryAfterOTS.do?method=getRecoveryDetailData"
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
             <td width="22%" class="Heading">   
              <bean:message key="danReportHeader"/>
             </td>
             <td>
              <img src="images/TriangleSubhead.gif" width="19" height="19"></img>
             </td>
            </tr>
            <tr>
             <td colspan="3" class="Heading">
              <img src="images/Clear.gif" width="5" height="5"></img>
             </td>
            </tr>
           </table>
          </td>
         </tr>
         <tr align="left" valign="top">
          <td align="left" valign="top" class="ColumnBackground">
           <font color="#FF0000" size="2">*</font>
           <bean:message key="MemberID"/>
          </td>
          <td align="left" valign="top" class="TableData" colspan="4">
           <html:text property="enterMember" size="20" alt="MemberID"
                      maxlength="12" name="cpRecoveryOTS"  onkeyup="isValidNumber(this)" onkeypress="return numbersOnly(this, event)"/>
          </td>
         </tr>
         <tr align="left" valign="top">
                   
          <td align="left" valign="top" class="ColumnBackground">
           <font color="#FF0000" size="2">*</font>
           <bean:message key="cgpan"/>
          </td>
          <td align="left" valign="top" class="TableData">
           <html:text property="enterCgpan" size="20" maxlength="15" alt="CGPAN"
                      name="cpRecoveryOTS" />
          </td>
         </tr>
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
                  <a href="javascript:Validation()">
          <img src="images/OK.gif" alt="OK" width="49" height="37" border="0"></img>
         </a>
          
         <a href="javascript:document.cpRecoveryOTS.reset()">
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
