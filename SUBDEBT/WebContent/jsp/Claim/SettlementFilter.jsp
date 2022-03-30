<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<html>
<head>
<title>CGTSI</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<style type="text/css">
</style>
<LINK href="css/StyleSheet.css" rel="stylesheet" type="text/css">
<SCRIPT language="JavaScript" type="text/JavaScript" src="js/selectdate.js"></SCRIPT>
<SCRIPT language="JavaScript" type="text/JavaScript" src="js/CGTSI.js"></SCRIPT>
</head>

<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<html:errors/>
<html:form action="getSettlementMemId.do?method=getSettlementMemId" method="POST" focus="memberId" enctype="multipart/form-data">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    
    <tr>
      <td>&nbsp;</td>
    </tr>
  </table>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td align="right" valign="bottom"><img src="images/TableLeftTop.gif" width="20" height="31"></td>
      <td background="images/TableBackground1.gif"><img src="images/ClaimsProcessingHeading.gif" width="131" height="25"></td>
      <td align="right" valign="bottom" background="images/TableBackground1.gif">
        <div align="right"></div></td>
      <td width="20" align="left" valign="bottom"><img src="images/TableRightTop.gif" width="23" height="31"></td>
    </tr>
    <tr>
      <td background="images/TableVerticalLeftBG.gif">&nbsp;</td>
      <td colspan="2">
      <DIV align="right">			
      		<A HREF="javascript:submitForm('helpDisplaySettlementFltr.do')">
      	        HELP</A>
      </DIV>                      
      <table width="100%" border="0" cellspacing="1" cellpadding="0">
          <tr>
            <td colspan="4"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="20%" class="Heading">&nbsp;Settlement Details</td>
                  <td colspan="2" align="left" valign="bottom"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
                  <td align="right"> <div align="right"> </div></td>
                </tr>
                <tr>
                  <td colspan="4" class="Heading"><img src="images/Clear.gif" width="5" height="5"></td>
                </tr>
              </table></td>
          </tr>
          <tr>
            <td colspan="4"><table width="100%" border="0" cellspacing="1" cellpadding="0">
                <tr>
                  <td width="25%" align="left" valign="top" class="ColumnBackground">&nbsp;&nbsp;Member</td>
                  <td class="TableData"><html:text property="memberId" maxlength="12" name="cpTcDetailsForm" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/></td>
                </tr>
              </table></td>
          </tr>
          <tr>
            <td colspan="4">&nbsp;</td>
          </tr>
          <tr align="center" valign="baseline">
            <td colspan="4"> <div align="center">            
            <A href="javascript:submitForm('getSettlementMemId.do?method=getSettlementMemId')"><img src="images/OK.gif" alt="Ok" width="49" height="37" border="0"></a>
            <a href="javascript:document.cpTcDetailsForm.reset()"><img src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></a>
            <a href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>"><img src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></a></div></td>
          </tr>
        </table></td>
      <td background="images/TableVerticalRightBG.gif">&nbsp;</td>
    </tr>
    <tr>
      <td width="20" align="right" valign="top"><img src="images/TableLeftBottom1.gif" width="20" height="15"></td>
      <td colspan="2" background="images/TableBackground2.gif"><div align="center"></div></td>
      <td align="left" valign="top"><img src="images/TableRightBottom1.gif" width="23" height="15"></td>
    </tr>
  </table>
</html:form>
</body>
</html>
