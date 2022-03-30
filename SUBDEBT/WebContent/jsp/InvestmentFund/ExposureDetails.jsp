<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@ page import="java.text.DecimalFormat"%>

<% session.setAttribute("CurrentPage","getExposureDetails.do?method=getExposureDetails");%>

<html:form action="getExposureDetails.do?method=getExposureDetails" method="POST">

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
            <td> <table border="0" cellspacing="1" cellpadding="0">
                <tr> 
                  <td colspan="10" ><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr> 
                        <td width="35%" class="Heading">&nbsp;
						<bean:message key="exposureDetails" /> 
						</td>
                        <td align="left" valign="bottom"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                      </tr>
                      <tr> 
                        <td colspan="10" class="Heading"><img src="images/Clear.gif" width="5" height="5"></td>
                      </tr>
                    </table></td>
                </tr>

                <tr>
                  <td class="SubHeading" colspan="10"> &nbsp;&nbsp;
					  <bean:message key="investeeWiseExp" /> 
                    </td>
				</tr>
				<tr>
					<TD align="left" valign="top" class="HeadingBg" rowspan="2">
						Sl No.
					</td>
					<TD align="left" valign="top" class="HeadingBg" rowspan="2">
						<div align="center">
						<bean:message key="expInvesteeName" /> 
						</div>
					</td>
					<TD align="left" valign="top" class="HeadingBg" rowspan="2">
						<div align="center">
						<bean:message key="expAmountInvested" /> 
						</div>
					</td>
					<TD align="left" valign="top" class="HeadingBg" colspan="2">
						<div align="center">
						<bean:message key="expLimit" /> 
						</div>
					</td>
					<TD align="left" valign="top" class="HeadingBg" rowspan="2">
						<div align="center">
						<bean:message key="expAddtlCorpus" /> 
						</div>
					</td>
					<TD align="left" valign="top" class="HeadingBg" rowspan="2">
						<div align="center">
						<bean:message key="expAbsoluteCeiling" /> 
						</div>
					</td>
					<TD align="left" valign="top" class="HeadingBg" rowspan="2">
						<div align="center">
						<bean:message key="expMaxAmount" /> 
						</div>
					</td>
					<TD align="left" valign="top" class="HeadingBg" rowspan="2">
						<div align="center">
						<bean:message key="expGapAvailable" /> 
						</div>
					</td>
				</tr>

				<tr>
					<TD align="left" valign="top" class="HeadingBg" >
						<div align="center">
						<bean:message key="expNetWorth" /> 
						</div>
					</td>
					<TD align="left" valign="top" class="HeadingBg">
						<div align="center">
						<bean:message key="expTangibleAssets" /> 
						</div>
					</td>
				</tr>


<%
DecimalFormat df= new DecimalFormat("######################.##");
df.setDecimalSeparatorAlwaysShown(false);

%>



			<%int i=1;%>
			<logic:iterate id="object" name="ifForm" property="investeeWiseDetails">

                <%
                	com.cgtsi.investmentfund.ExposureDetails exposure=
                	(com.cgtsi.investmentfund.ExposureDetails)object;
					
					String investeeName = exposure.getInvesteeName();
					String amtInvested = df.format(exposure.getInvestedAmount());
					String netWorth = df.format(exposure.getInvesteeNetWorth());
					String tangibleAssets = df.format(exposure.getInvesteeTangibleAssets());
					String corpusAmount = df.format(exposure.getCeilingLimit());
					String absoluteCeilingAmount = df.format(exposure.getInvCeilingAmt());
					String maxAmount = df.format(exposure.getInvesteeEligibleAmt());
					String gapAvailable = df.format(exposure.getGapAvailableAmount());

                %>

				<tr>
					<TD align="left" valign="top" class="TableData">
						<%=i%>
					</td>
					<TD align="left" valign="top" class="TableData">
						<div align="center">
						<%=investeeName%>
						</div>
					</td>
					<TD align="left" valign="top" class="TableData">
						<div align="center">
						<%=amtInvested%>
						</div>
					</td>
					<TD align="left" valign="top" class="TableData">
						<div align="center">
						<%=netWorth%>
						</div>
					</td>
					<TD align="left" valign="top" class="TableData">
						<div align="center">
						<%=tangibleAssets%>
						</div>
					</td>
					<TD align="left" valign="top" class="TableData">
						<div align="center">
						<%=corpusAmount%>
						</div>
					</td>
					<TD align="left" valign="top" class="TableData">
						<div align="center">
						<%=absoluteCeilingAmount%>
						</div>
					</td>
					<TD align="left" valign="top" class="TableData">
						<div align="center">
						<%=maxAmount%>
						</div>
					</td>
					<TD align="left" valign="top" class="TableData">
						<div align="center">
						<%=gapAvailable%>
						</div>
					</td>



				</tr>
				<%++i;%>

			</logic:iterate>


			  			<!--Instrument Category Wise Details-->	

                <tr>
                  <td class="SubHeading" colspan="10"> &nbsp;&nbsp;
					  <bean:message key="instCatWiseExp" /> 
                    </td>
				</tr>
				<tr>
					<TD align="left" valign="top" class="HeadingBg" >
						Sl No.
					</td>
					<TD align="left" valign="top" class="HeadingBg" colspan="2">
						<div align="center">
						<bean:message key="instrumentCategory" /> 
						</div>
					</td>
					<TD align="left" valign="top" class="HeadingBg" colspan="2">
						<div align="center">
						<bean:message key="expAmountInvested" /> 
						</div>
					</td>
					<TD align="left" valign="top" class="HeadingBg" colspan="2" >
						<div align="center">
						<bean:message key="expSurplusFunds" /> 
						</div>
					</td>
					<TD align="left" valign="top" class="HeadingBg" colspan="2">
						<div align="center">
						<bean:message key="expGapAvailable" /> 
						</div>
					</td>
				</tr>


			<%int a=1;%>
			<logic:iterate id="object" name="ifForm" property="instCatWiseDetails">

                <%
                	com.cgtsi.investmentfund.ExposureDetails exposure=
                	(com.cgtsi.investmentfund.ExposureDetails)object;
					
					String instCatName = exposure.getInstCatName();
					String amtInvested = df.format(exposure.getInvestedAmount());

					String maxAmount = df.format(exposure.getInvCeilingAmt());
					String gapAvailable = df.format(exposure.getGapAvailableAmount());
					if(gapAvailable.equals("-1"))
					{
						gapAvailable = "-";
					}

                %>

				<tr>
					<TD align="left" valign="top" class="TableData">
						<%=a%>
					</td>
					<TD align="left" valign="top" class="TableData" colspan="2">
						<div align="center">
						<%=instCatName%>
						</div>
					</td>
					<TD align="left" valign="top" class="TableData" colspan="2">
						<div align="center">
						<%=amtInvested%>
						</div>
					</td>
					<TD align="left" valign="top" class="TableData" colspan="2">
						<div align="center">
						<%=maxAmount%>
						</div>
					</td>
					<TD align="left" valign="top" class="TableData" colspan="2">
						<div align="center">
						<%=gapAvailable%>
						</div>
					</td>



				</tr>
				<%++a;%>

			</logic:iterate>



			<!--Investee Group Wise Details-->	

                <tr>
                  <td class="SubHeading" colspan="10"> &nbsp;&nbsp;
					  <bean:message key="investeeGrpWiseExp" /> 
                    </td>
				</tr>
				<tr>
					<TD align="left" valign="top" class="HeadingBg" >
						Sl No.
					</td>
					<TD align="left" valign="top" class="HeadingBg" colspan="2">
						<div align="center">
						<bean:message key="investeeGrpName" /> 
						</div>
					</td>
					<TD align="left" valign="top" class="HeadingBg">
						<div align="center">
						<bean:message key="expAmountInvested" /> 
						</div>
					</td>
					<TD align="left" valign="top" class="HeadingBg" >
						<div align="center">
						<bean:message key="expAddtlCorpus" /> 
						</div>
					</td>
					<TD align="left" valign="top" class="HeadingBg" colspan="2">
						<div align="center">
						<bean:message key="expAbsoluteCeilingGrpWise" /> 
						</div>
					</td>
					<TD align="left" valign="top" class="HeadingBg" >
						<div align="center">
						<bean:message key="expMaxAmountGrpWise" /> 
						</div>
					</td>
					<TD align="left" valign="top" class="HeadingBg" >
						<div align="center">
						<bean:message key="expGapAvailable" /> 
						</div>
					</td>
				</tr>


			<%int k=1;%>
			<logic:iterate id="object" name="ifForm" property="investeeGrpWiseDetails">

                <%
                	com.cgtsi.investmentfund.ExposureDetails exposure=
                	(com.cgtsi.investmentfund.ExposureDetails)object;
					
					String investeeGrpName = exposure.getInvesteeGroup();
					String amtInvested = df.format(exposure.getInvestedAmount());
					String corpusAmount = df.format(exposure.getCeilingLimit());
					String absoluteCeilingAmount = df.format(exposure.getInvCeilingAmt());
					String maxAmount = df.format(exposure.getInvesteeEligibleAmt());
					String gapAvailable = df.format(exposure.getGapAvailableAmount());

                %>

				<tr>
					<TD align="left" valign="top" class="TableData">
						<%=k%>
					</td>
					<TD align="left" valign="top" class="TableData" colspan="2">
						<div align="center">
						<%=investeeGrpName%>
						</div>
					</td>
					<TD align="left" valign="top" class="TableData">
						<div align="center">
						<%=amtInvested%>
						</div>
					</td>
					<TD align="left" valign="top" class="TableData">
						<div align="center">
						<%=corpusAmount%>
						</div>
					</td>
					<TD align="left" valign="top" class="TableData" colspan="2">
						<div align="center">
						<%=absoluteCeilingAmount%>
						</div>
					</td>
					<TD align="left" valign="top" class="TableData">
						<div align="center">
						<%=maxAmount%>
						</div>
					</td>
					<TD align="left" valign="top" class="TableData">
						<div align="center">
						<%=gapAvailable%>
						</div>
					</td>



				</tr>
				<%++k;%>

			</logic:iterate>


			  			<!--Maturity Wise Details-->	

                <tr>
                  <td class="SubHeading" colspan="10"> &nbsp;&nbsp;
					  <bean:message key="maturityWiseExp" /> 
                    </td>
				</tr>
				<tr>
					<TD align="left" valign="top" class="HeadingBg" >
						Sl No.
					</td>
					<TD align="left" valign="top" class="HeadingBg" colspan="2">
						<div align="center">
						<bean:message key="maturityTerm" /> 
						</div>
					</td>
					<TD align="left" valign="top" class="HeadingBg" colspan="2">
						<div align="center">
						<bean:message key="expAmountInvested" /> 
						</div>
					</td>
					<TD align="left" valign="top" class="HeadingBg" colspan="2" >
						<div align="center">
						<bean:message key="expSurplusFunds" /> 
						</div>
					</td>
					<TD align="left" valign="top" class="HeadingBg" colspan="2">
						<div align="center">
						<bean:message key="expGapAvailable" /> 
						</divs>
					</td>
				</tr>


			<%int j=1;%>
			<logic:iterate id="object" name="ifForm" property="maturityWiseDetails">

                <%
                	com.cgtsi.investmentfund.ExposureDetails exposure=
                	(com.cgtsi.investmentfund.ExposureDetails)object;
					
					String maturityName = exposure.getMaturityName();
					String amtInvested = df.format(exposure.getInvestedAmount());

					String maxAmount = df.format(exposure.getInvCeilingAmt());
					String gapAvailable = df.format(exposure.getGapAvailableAmount());

					if(gapAvailable.equals("-1"))
					{
						gapAvailable = "-";
					}


                %>

				<tr>
					<TD align="left" valign="top" class="TableData">
						<%=j%>
					</td>
					<TD align="left" valign="top" class="TableData" colspan="2">
						<div align="center">
						<%=maturityName%>
						</div>
					</td>
					<TD align="left" valign="top" class="TableData" colspan="2">
						<div align="center">
						<%=amtInvested%>
						</div>
					</td>
					<TD align="left" valign="top" class="TableData" colspan="2">
						<div align="center">
						<%=maxAmount%>
						</div>
					</td>
					<TD align="left" valign="top" class="TableData" colspan="2">
						<div align="center">
						<%=gapAvailable%>
						</div>
					</td>



				</tr>
				<%++j;%>

			</logic:iterate>

			  </table>
      </table></td>
    <td width="23" background="images/TableVerticalRightBG.gif">&nbsp;</td>
  </tr>
  <tr> 
      <td width="20" align="right" valign="bottom"><img src="images/TableLeftBottom.gif" width="20" height="51"></td>
      <td colspan="2" valign="bottom" background="images/TableBackground3.gif"> 
        <div>
          <div align="center">
           <A href="javascript:history.back()">
						<IMG src="images/Back.gif" alt="Save" width="49" height="37" border="0">
						</A></div>
      </div></td>
      <td width="23" align="right" valign="bottom"><img src="images/TableRightBottom.gif" width="23" height="51"></td>
  </tr>
</table>
</html:form>
