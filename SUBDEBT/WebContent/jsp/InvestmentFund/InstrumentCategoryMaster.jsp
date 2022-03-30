<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% 
String req = (String)session.getAttribute("modFlag");
if (req.equals("0"))
{
	session.setAttribute("CurrentPage","showInstCategoryMaster.do?method=showInstCategoryMaster");
}
else if (req.equals("1"))
{
	session.setAttribute("CurrentPage","showInstCategoryMaster.do?method=showModInstCategoryMaster");
}
%>
<% String modifiedInstCat = "";%>
<body onload="insCategory(this)">
<html:form action="saveInstrumentCategory.do?method=saveInstrumentCategory" method="POST" enctype="multipart/form-data" >

  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <html:errors />
    <tr> 
      <td width="20" align="right" valign="bottom"><img src="images/TableLeftTop.gif" width="20" height="31"></td>
      <td width="323" background="images/TableBackground1.gif"><img src="images/InvestmentManagementHeading.gif" width="169" height="25"></td>
      <td align="right" background="images/TableBackground1.gif"> </td>
      <td width="23" align="left" valign="bottom"><img src="images/TableRightTop.gif" width="23" height="31"></td>
    </tr>
  <tr> 
    <td width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</td>
    <td colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
	  <TD>			
			<DIV align="right">			
				<A HREF="javascript:submitForm('helpInstrumentCatMaster.do?method=helpInstrumentCatMaster')">
			    HELP</A>
			</DIV>
		</td>
	  </tr>
        <tr>
            <td > <table border="0" cellspacing="1" cellpadding="0">
                <tr> 
                  <td colspan="5" ><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr> 
                        <td width="30%" class="Heading">&nbsp;
						 <bean:message key="instCategoryDtls" />
						</td>
                        <td align="left" valign="bottom"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                      </tr>
                      <tr> 
                        <td colspan="4" class="Heading"><img src="images/Clear.gif" width="5" height="5"></td>
                      </tr>
                    </table></td>
                </tr>
				<TR align="left">
					<TD align="left" valign="top" class="ColumnBackground">
						&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="instrumentCategory" />
					</TD>
					<TD align="left" class="TableData">
						<html:select property="instrumentCategory" name="ifForm" onchange="insCategory(this);javascript:submitForm('showInstCategoryMaster.do?method=showModInstCategoryMaster')">
							<html:option value="">Select</html:option>
							<html:options property="instrumentCategories" name="ifForm"/>			
						</html:select>
					</TD>
			  <bean:define id="nVal" name="ifForm" property="newInstrumentCat"/>
			  <%
			  String reqVal1 = (String)nVal;
			  if (request.getParameter("newInstrumentCat")!=null)
			  {
				reqVal1 = (String)request.getParameter("newInstrumentCat");
			  }
			  %>
					<TD align="left" class="ColumnBackground"><bean:message key="newInstrumentCat" />
						<html:text property="newInstrumentCat" size="20" alt="Enter" name="ifForm"  maxlength="20" value="<%=reqVal1%>" onkeypress="newInstCategory(this)" onkeyup="newInstCategory(this)"/>
					</TD>									
		
				</TR>
                <tr>
                  <td width="360" class="ColumnBackground" COLSPAN="2"> <div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;
                      <bean:message key="modInstrumentCat" />
			 &nbsp;</div></td>
                	<td class="tableData" width="474" COLSPAN="2"> <div align="left"> 

			  <bean:define id="mVal" name="ifForm" property="modInstrumentCat"/>
			  <%
			  String reqVal = (String)mVal;
			  if (request.getParameter("modInstrumentCat")!=null)
			  {
				reqVal = (String)request.getParameter("modInstrumentCat");
			  }
			  %>
			<html:text  property="modInstrumentCat" size="20" value="<%=reqVal%>" alt="Investee Group" name="ifForm" maxlength="50"/>
			</div></td>
			<%
			modifiedInstCat = "";
			%>
                </tr>

                <tr>
                  <td  class="ColumnBackground" width="260"> &nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp; 
				  <bean:message key="ictDesc" />
				  </td>
                  <td class="tableData"  colspan="3"> <div align="left"> 
<!--                      <html:text  property="maturityDescription" size="20" alt="Maturity Description" name="ifForm" maxlength="100"/>-->
						<html:textarea name="ifForm" property="ictDesc" rows="3" cols="50"/>
                    </div></td>
                </tr>

              </table>
      </table></td>
    <td width="23" background="images/TableVerticalRightBG.gif">&nbsp;</td>
  </tr>
  <tr> 
      <td width="20" align="right" valign="bottom"><img src="images/TableLeftBottom.gif" width="20" height="51"></td>
      <td colspan="2" valign="bottom" background="images/TableBackground3.gif"> 
        <div>
          <div align="center"><html:link href="javascript:submitForm('saveInstrumentCategory.do?method=saveInstrumentCategory')"><img src="images/Save.gif" alt="Ok" width="49" height="37" border="0"></html:link><html:link href="javascript:document.ifForm.reset()"><img src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></html:link></div>
      </div></td>
      <td width="23" align="right" valign="bottom"><img src="images/TableRightBottom.gif" width="23" height="51"></td>
  </tr>
</table>
</html:form>
</body>