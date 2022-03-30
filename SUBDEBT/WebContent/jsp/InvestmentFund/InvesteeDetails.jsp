<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","showInflowDetails.do");%>

<html:form action="updateBudgetHeadMaster.do?method=updateBudgetHeadMaster" method="POST" enctype="multipart/form-data">

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
      <td><table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
          <tr> 
            <td><table width="100%" border="0" cellspacing="1" cellpadding="1">
                <tr > 
                  <td colspan="2"></td>
                </tr>
                <tr align="left" valign="top"> 
                  <td width="20%" valign="top" class="ColumnBackground"> <div align="left">&nbsp;Investee Group</div></td>
                  <td align="left" valign="top" class="tableData">
					<html:select property="investeeGrp" styleId="investeeGrp" >
					  <html:option selected>Select</html:option>
                    </html:select>&nbsp;
                  </td>
                </tr>
                <tr align="left" valign="top"> 
                  <td align="left" valign="top" class="ColumnBackground"> <div align="left">&nbsp;Investee Name</div></td>
                  <td align="left" class="tableData"> <html:text property="investeeName"  styleId="investeeName" size="20" styleClass="text"/></td>
                </tr>
                <tr align="left" valign="top"> 
                  <td align="left" valign="top" class="ColumnBackground"> <div align="left">&nbsp;Tangible Assets</div></td>
                  <td align="left" class="tableData">
					<html:text  property="tangibleAssets"/>in Rs.
				  </td>
                </tr>
                <tr align="left" valign="top"> 
                  <td align="left" valign="top" class="ColumnBackground"> <div align="left">&nbsp;Net Worth</div></td>
                  <td align="left" class="tableData">
					<html:text  property="netWorth"/>in Rs.
				  </td>
                </tr>
              </table></td>
          </tr>
          <tr > 
            <td height="20" >&nbsp;</td>
          </tr>
          <tr > 
            <td align="center" valign="baseline" > <div align="center"><html:link href="javascript:window.history.back()"><img src="images/Save.gif" alt="Save" width="49" height="37" border="0"></html:link><html:link href="../IFSubHome.html"><img src="../../images/btn_cancel.gif" alt="Cancel" width="49" height="37" border="0"></html:link> 
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