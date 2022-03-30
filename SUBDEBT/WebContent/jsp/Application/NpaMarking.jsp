<%@ page language="java"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.cgtsi.actionform.ReportActionForm"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="com.cgtsi.claim.ClaimConstants"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@ include file="/jsp/SetMenuInfo.jsp"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.cgtsi.actionform.NpaMarkingActionForm"%>
<%@page import="com.cgtsi.actionform.NPAMarkingPopulateData"%>
<SCRIPT language="JavaScript" type="text/JavaScript" src="js/CGTSI.js"></SCRIPT>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
	integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l"
	crossorigin="anonymous" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link href="/SUBDEBT/css/StyleSheet.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>

<title>NPA Marking</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />


</head>
<body>
	<html:errors />
	<html:form name="npaMarkingForm"
		type="com.cgtsi.actionform.NpaMarkingActionForm"
		action="npaMarking.do?method=saveNpaMarkingData" method="POST">
		<div class="container row"
			style="margin-left: 20px; border-top: 1px solid brown; border-left: 1px solid brown; border-right: 1px solid brown; border-bottom: 1px solid brown; padding-top: 10px; padding-bottom: 10px;">


			<div class="col-md-12">
				<div class="col-md-12 alert alert-primary" role="alert">Search Details</div>
			</div>
			<div class="col-lg-12" id="errormsg" style="display: none"></div>
			<div class="col-lg-12">
				<div class="row">
					<%
						String roleName = "";
							if ((String) request.getAttribute("role") != null)
								;
							{
								roleName = (String) request.getAttribute("role");
							}

							/* NpaMarkingActionForm msg=new NpaMarkingActionForm();
							String message=msg.getMessage();
							
							System.out.println("messagemessage::"+message); */
					%>



					<div class="col-md-4">
						<label for="CGPAN">CGPAN</label><font color="#FF0000" size="2">*</font>
						<html:text property="cgpan1" styleClass="form-control"
							onchange="return getPromoter();" styleId="cgpan1"
							name="npaMarkingForm" />
					</div>
					<!-- <div class="col-md-4"><label for="CGPAN">CGPAN</label><input type="Text" class="form-control" id="CGPAN" placeholder="Enter CGPAN" /></div> -->
					<!--   <div class="col-md-4">
                            <label for="PromoterName">Promoter Name</label>
                            <select class="form-control" id="PromoterName1" placeholder="Enter Promoter Name">
                                <option value="volvo">Volvo</option>
                                <option value="saab">Saab</option>
                                <option value="mercedes">Mercedes</option>
                                <option value="audi">Audi</option>
                            </select>
                        </div>-->
					<div class="col-md-4">
						<label for="PromoterName">Promoter Name</label><font
							color="#FF0000" size="2">*</font>
						<html:select styleClass="form-control" property="promoterName1"
							name="npaMarkingForm" styleId="promoterName1">
							<html:option value="0">Select</html:option>
							<html:optionsCollection property="promoterValues"
								name="npaMarkingForm" label="label" value="value" />
						</html:select>
					</div>
					<div class="col-md-4" style="padding-top: 24px;">

						<button type="button" class="btn btn-primary" id="btnSearch">Search</button>
						&nbsp;&nbsp;
						<button type="button" class="btn btn-primary" id="btnClear">Clear</button>
					</div>
				</div>

				<div id="isDisable" style="display: none">
					<div class="row" style="margin-top: 10px;">
						<div class="col-md-12">
							<div class="col-md-12 alert alert-primary" role="alert">MSME
								Unit Data</div>
						</div>
						<div class="col-md-4">
							<label for="App_Ref_No">App Ref No</label>
							<!--  <input type="Text" class="form-control" disabled id="App_Ref_No" placeholder="Enter App Ref No" /></div> -->
							<html:text property="referanceNo" styleId="referanceNo"
								alt="Reference" name="npaMarkingForm" styleClass="form-control"
								disabled="true" />

						</div>


						<div class="col-md-4">
							<label for="CGPAN">CGPAN</label>
							<!--  <input type="Text" class="form-control" disabled id="CGPAN" placeholder="Enter CGPAN" /> -->
							<html:text property="cgpansearch" alt="Reference"
								name="npaMarkingForm" styleClass="form-control" disabled="true" />
						</div>

						<div class="col-md-4">
							<label for="PMR_CGPAN">Promoter CGPAN</label>
							<html:text property="pmarCgpan" alt="Reference"
								name="npaMarkingForm" styleClass="form-control" disabled="true" />
						</div>


						<div class="col-md-4">
							<label for="UnitName">Unit Name</label>
							<!-- <input type="Text" class="form-control" disabled id="UnitName" placeholder="Enter Unit Name" /> -->
							<html:text property="unitName" alt="Reference"
								name="npaMarkingForm" styleClass="form-control" disabled="true" />
						</div>

						<div class="col-md-4">
							<label for="TypeofEntity">Type of Entity</label>
							<!--   <input type="Text" class="form-control" disabled id="TypeofEntity" placeholder="Enter Type of Entity" /> -->
							<html:text property="typeofEntity" alt="Reference"
								name="npaMarkingForm" styleClass="form-control" disabled="true" />
						</div>

						<div class="col-md-4">
							<label for="Constitution">Constitution</label>
							<!--   <input type="Text" class="form-control" disabled id="Constitution" placeholder="Enter Constitution" /> -->
							<html:text property="contribution" alt="Reference"
								name="npaMarkingForm" styleClass="form-control" disabled="true" />
						</div>


						<div class="col-md-4">
							<label for="GuaranteeAmount">Guarantee Amount</label>
							<html:text property="guaranteeAmount" alt="Reference"
								name="npaMarkingForm" styleClass="form-control" disabled="true"
								styleId="GuaranteeAmount" />
						</div>

						<div class="col-md-4">
							<label for="GuaratneeStart_Date">Guarantee Start Date</label>
							<html:text property="guaratneeStartDate" alt="Reference"
								name="npaMarkingForm" styleClass="form-control" disabled="true"
								styleId="guaratneeStartDate" />
						</div>


						<div class="col-md-4">
							<label for="LastDisbursementDate">Last Disbursement Date</label>
							<html:text property="lastDisbursementDate" alt="Reference"
								name="npaMarkingForm" styleClass="form-control" disabled="true" />
						</div>

						<div class="col-md-4">
							<label for="Sanction_Amount">Sanction Amount</label>
							<html:text property="sanctionAmount" alt="Reference"
								name="npaMarkingForm" styleClass="form-control" disabled="true" />
						</div>

						<div class="col-md-4">
							<label for="Sanction_Date">Sanction Date</label>
							<html:text property="sanctionDate" alt="Reference"
								name="npaMarkingForm" styleClass="form-control" disabled="true" />
						</div>

						<div class="col-md-4">
							<label for="InterestRate">Interest Rate</label>
							<html:text property="interestRate" alt="Reference"
								name="npaMarkingForm" styleClass="form-control" disabled="true"
								styleId="InterestRate" />
						</div>
					</div>



					<div class="row" style="margin-top: 10px;">
						<div class="col-md-12">
							<div class="col-md-12 alert alert-primary" role="alert">Promoter
								Data</div>
						</div>
						<div class="col-md-4">
							<label for="PromoterName">Promoter Name </label>
							<html:text property="promoterNamee" alt="Reference"
								name="npaMarkingForm" styleClass="form-control" disabled="true" />

						</div>
						<div class="col-md-4">
							<label for="ITPAN ">ITPAN </label>
							<html:text property="itPAN" alt="Reference" name="npaMarkingForm"
								styleClass="form-control" disabled="true" />
						</div>

						<div class="col-md-4">
							<label for="AdharNumber">Adhar Number</label>
							<html:text property="adharNumber" alt="Reference"
								name="npaMarkingForm" styleClass="form-control" disabled="true" />
						</div>

						<div class="col-md-4">
							<label for="InvestedEquity">Invested Equity</label>
							<html:text property="investedEquity" alt="Reference"
								name="npaMarkingForm" styleClass="form-control" disabled="true" />
						</div>

						<div class="col-md-4">
							<label for="Investedasdebtunsecuredloan">Invested as
								debt/unsecured loan</label>
							<html:text property="investedasdebtunsecuredloan" alt="Reference"
								name="npaMarkingForm" styleClass="form-control" disabled="true" />
						</div>

						<div class="col-md-4">
							<label for="Total">Total</label>
							<html:text property="total" alt="Reference" name="npaMarkingForm"
								styleClass="form-control" disabled="true" />
						</div>

						<div class="col-md-4">
							<label for="GuaranteeAmount1">Guarantee Amount</label>
							<html:text property="guaranteeAmount1" alt="Reference"
								name="npaMarkingForm" styleClass="form-control" disabled="true"
								styleId="guaranteeAmount1" />
							<html:hidden property="disbursementAmount" alt="Reference" 
								name="npaMarkingForm" styleClass="form-control" 
								styleId="disbursementAmount" /> 	
						</div>

						<div class="col-md-4">
							<label for="GuaratneeStart_Date">Guarantee Start Date</label>
							<html:text property="guaratneeStartDate1" alt="Reference"
								name="npaMarkingForm" styleClass="form-control"
								styleId="GuaratneeStart_Date" disabled="true" />
						</div>
					</div>


					<div class="row" style="margin-top: 10px;">
						<div class="col-md-12">
							<div class="col-md-12 alert alert-primary" role="alert">NPA
								Details</div>
						</div>
						<div class="col-md-8">
							<label for="creditrisk">Enumerate efforts taken by MLI to
								prevent account turning NPA and minimising the credit risk</label><font
								color="#FF0000" size="2">*</font>
							<!--  <select class="form-control" id="creditrisk" placeholder="Enter Promoter Name">
                                    <option value="volvo">Volvo</option>
                                    <option value="saab">Saab</option>
                                    <option value="mercedes">Mercedes</option>
                                    <option value="audi">Audi</option>
                                </select>-->
						</div>
						<div class="col-md-4">
							<html:select styleClass="form-control" property="creditRisk"
								name="npaMarkingForm" styleId="creditrisk">
								<html:option value="0">Select</html:option>
								<html:optionsCollection property="creditRiskvalue" 
									name="npaMarkingForm" label="label" value="value" />
							</html:select>
						</div>
						<%
							String date;
						    String npadate;
								if ((String) request.getAttribute("date") != null || (String) request.getAttribute("npadate") != null);
								{
									date = (String) request.getAttribute("date");
									npadate = (String) request.getAttribute("npadate");

								}
						%>

						<%
							if (date != null && !(date.equals(""))) {
						%>
						<div class="col-md-4">
							<label for="NPADate">NPA Date </label> <font color="#FF0000"
								size="3">*</font>
								<input type="text" name="npaDate"
								name="npaMarkingForm" class="form-control" autocomplete="off"
								style="background-color: #fff;" id="NPADate" 
								onclick="setDateValue();" placeholder="Enter NPA Date"
								value="<%=date%>" />
						</div>
						<%
							} else if(npadate != null && !(npadate.equals(""))){
								
						%>

						<%-- <div class="col-md-4">
							<label for="NPADate">NPA Date <font color="#FF0000"
								size="3">*</font></label><input type="text" name="npaDate"
								name="npaMarkingForm" class="form-control" id="NPADate" autocomplete="off" 
								onchange="dateValidation();" onclick="setDateValue();" placeholder="Enter NPA Date" value="<%=npadate%>" />
						</div> changes by sarita on 18-09-2020 to remove date validation on temporary basis as per mail 17-09-2021--%>
						<div class="col-md-4">
							<label for="NPADate">NPA Date <font color="#FF0000"
								size="3">*</font></label><input type="text" name="npaDate"
								name="npaMarkingForm" class="form-control" id="NPADate" autocomplete="off" 
								onclick="setDateValue();" placeholder="Enter NPA Date" value="<%=npadate%>" />
						</div>
						<%
							}else {
						%>
							<!-- <div class="col-md-4">
							<label for="NPADate">NPA Date <font color="#FF0000"
								size="3">*</font></label><input type="text" name="npaDate"
								name="npaMarkingForm" class="form-control" id="NPADate" autocomplete="off"
								onchange="dateValidation();" onclick="setDateValue();" placeholder="Enter NPA Date" />
						   </div>   changes by sarita on 18-09-2020 to remove date validation on temporary basis as per mail 17-09-2021--> 
						   <div class="col-md-4">
							<label for="NPADate">NPA Date <font color="#FF0000"
								size="3">*</font></label><input type="text" name="npaDate"
								name="npaMarkingForm" class="form-control" id="NPADate" autocomplete="off"
								onclick="setDateValue();" placeholder="Enter NPA Date" />
						   </div>
						<%
							}
						%>
						<div class="col-md-4">
							<label for="NPAReason">NPA Reason</label><font color="#FF0000"
								size="2">*</font>
							<!--  <select class="form-control" id="NPAReason" placeholder="Enter Promoter Name">
                                    <option value="volvo">Volvo</option>
                                    <option value="saab">Saab</option>
                                    <option value="mercedes">Mercedes</option>
                                    <option value="audi">Audi</option>
                                </select>-->
							<html:select styleClass="form-control" property="reason"
								name="npaMarkingForm" styleId="NPAReason">
								<html:option value="0">Select</html:option>
								<html:optionsCollection property="reasonvalue"
									name="npaMarkingForm" label="label" value="value" />
							</html:select>



						</div>


						<div class="col-md-4">
							<label for="PrinicipleRepayment">Priniciple Repayment (Before NPA)</label>
							<font color="#FF0000" size="2">*</font>
							<!--  <input type="Text" class="form-control" onchange="calculateIntrest();" 
                                id="PrinicipleRepayment" placeholder="Enter Priniciple Repayment (Before NPA)" /> -->

							<html:text property="prinicipleRepayment"
								styleId="PrinicipleRepayment" alt="Reference" 
								name="npaMarkingForm" styleClass="form-control" 
								onchange="calculateIntrest();" />
						</div>



						
						<div class="col-md-4">
							<label for="Interest_Repayment">Interest Repayment
								(Before NPA)</label><font color="#FF0000" size="2">*</font>
							<html:text property="interestRepayment" alt="Reference"
								name="npaMarkingForm" styleClass="form-control"
								styleId="interestRepayment" />
						</div>


						<div class="col-md-4">
							<label for="PrincipleOutstanding">Principle Outstanding
								(As on NPA Date)</label><font color="#FF0000" size="2">*</font>
							<html:text property="principleOutstanding" alt="Reference"
								name="npaMarkingForm" styleClass="form-control" disabled="true"
								styleId="PrincipleOutstanding" />
						</div>


						<div class="col-md-4">
							<label for="InterestOutstanding">Interest Outstanding (As
								on NPA Date)</label><font color="#FF0000" size="2">*</font>
							<html:text property="interestOutstanding" alt="Reference"
								name="npaMarkingForm" styleClass="form-control"
								styleId="InterestOutstanding" disabled="true" />
						</div>

						<div class="col-md-4"
							style="margin-top: 10px; margin-left: 2px; color: red;">
							<%
								// String check=request.getAttribute("check");

									String check;
									if ((String) request.getAttribute("check") != null)
										;
									{
										check = (String) request.getAttribute("check");
									}
							%>
							<%
								if (check.equals("N")) {
							%>
							
							<input type="checkbox" name="checkBoxVal" class="form-check-input check" id="check2" value="N" 	onclick="setCheckValue();"/> <span style="margin-left: 20px;">Is the NPA date as per the RBI guidelines</span>
						</div>
						<%
							} else {
						%>
						<input type="checkbox" checked="checked" class="form-check-input check" id="check1"/><span style="margin-left: 20px;">Is the NPA date as per the RBI guidelines</span>
						

					</div>
					<%
						}
					%>
				</div>

				<div class="row" style="margin-top: 10px;">
					<div class="col-md-12">
						<div class="col-md-12 alert alert-primary" role="alert">Value
							of Securities available</div>
					</div>
				</div>
				<div class="row" style="margin-top: 10px; margin-left: 2px;">
					<div class="col-md-3 alert alert-secondary" role="alert"
						style="margin-top: 10px;">Nature</div>
					<div class="col-md-2 alert alert-secondary" role="alert"
						style="margin-top: 10px;">As on date of Sanction</div>
					<div class="col-md-2 alert alert-secondary" role="alert"
						style="margin-top: 10px;">As on date of NPA</div>
					<div class="col-md-2 alert alert-secondary" role="alert"
						style="margin-top: 10px;">Networth of guarantor /
						Promoter(in Rs.)</div>
					<div class="col-md-3 alert alert-secondary" role="alert"
						style="margin-top: 10px;">Reasons for Reduction in the value
						of Security, if any</div>
				</div>

				<div class="row" style="margin-top: 10px; margin-left: 2px;">
					<div class="col-md-3" style="margin-top: 5px;">
						Land<font color="#FF0000" size="2">*</font>
					</div>

					<div class="col-md-2" style="margin-top: 5px;">
						<!-- <input type="Text" class="form-control" style="text-align: right;" onchange="calculatedateofSanction();" disabled id="dateofSanction0" placeholder="Enter CGPAN" value="0.00" />
                            </div> -->

						<html:text property="landSection" alt="Reference"
							name="npaMarkingForm" styleClass="form-control"
							style="text-align: right;" onchange="calculatedateofSanction();"
							disabled="true" styleId="dateofSanction0" />
					</div>

					<div class="col-md-2" style="margin-top: 5px;">
						<!-- <input type="Text" class="form-control" style="text-align: right;" onchange="calculateNPA();" id="dateofNPA0" placeholder="Enter CGPAN" value="0.00" />
                            </div> -->

						<html:text property="landDataOfNPA" alt="Reference"
							name="npaMarkingForm" styleClass="form-control"
							style="text-align: right;" onchange="calculateNPA();"
							styleId="dateofNPA0" />
					</div>

					<div class="col-md-2" style="margin-top: 5px;">
						<!--  <input type="Text" class="form-control" style="text-align: right;" onchange="calculateNetworthofguarantor();" id="Networthofguarantor0" placeholder="Enter CGPAN" value="0.00" />
                            </div> -->
						<html:text property="landNetWorth" alt="Reference"
							name="npaMarkingForm" styleClass="form-control"
							style="text-align: right;"
							onchange="calculateNetworthofguarantor();"
							styleId="Networthofguarantor0" />
					</div>
					<div class="col-md-3" style="margin-top: 5px;">
						<!--  <select class="form-control" id="ReasonsforReduction0" placeholder="Enter Promoter Name">
                                    <option value="volvo">Volvo</option>
                                    <option value="saab">Saab</option>
                                    <option value="mercedes">Mercedes</option>
                                    <option value="audi">Audi</option>
                                </select>-->

						<html:select styleClass="form-control" property="landReason"
							name="npaMarkingForm" styleId="ReasonsforReduction0">
							<html:option value="0">Select</html:option>
							<html:optionsCollection property="reproductionValue"
								name="npaMarkingForm" label="label" value="value" />
						</html:select>
					</div>
				</div>
				<div class="row" style="margin-top: 10px; margin-left: 2px;">
					<div class="col-md-3" style="margin-top: 5px;">
						Building<font color="#FF0000" size="2">*</font>
					</div>
					<div class="col-md-2" style="margin-top: 5px;">
						<!--  <input type="Text" class="form-control" style="text-align: right;" onchange="calculatedateofSanction();" disabled id="dateofSanction1" placeholder="Enter CGPAN" value="0.00" />
                            </div> -->

						<html:text property="buildingSection" alt="Reference"
							name="npaMarkingForm" styleClass="form-control"
							style="text-align: right;" onchange="calculatedateofSanction();"
							disabled="true" styleId="dateofSanction1" />
					</div>


					<div class="col-md-2" style="margin-top: 5px;">
						<!-- <input type="Text" class="form-control" style="text-align: right;" onchange="calculateNPA();" id="dateofNPA1" placeholder="Enter CGPAN" value="0.00" />
                            </div> -->

						<html:text property="buildingDateOfNpa" alt="Reference"
							name="npaMarkingForm" styleClass="form-control"
							style="text-align: right;" onchange="calculateNPA();"
							styleId="dateofNPA1" />
					</div>


					<div class="col-md-2" style="margin-top: 5px;">
						<!-- <input type="Text" class="form-control" style="text-align: right;" onchange="calculateNetworthofguarantor();" id="Networthofguarantor1" placeholder="Enter CGPAN" value="0.00" />
                            </div> -->

						<html:text property="buildingNetWorth" alt="Reference"
							name="npaMarkingForm" styleClass="form-control"
							style="text-align: right;"
							onchange="calculateNetworthofguarantor();"
							styleId="Networthofguarantor1" />
					</div>


					<div class="col-md-3" style="margin-top: 5px;">
						<!-- <select class="form-control" id="ReasonsforReduction1" placeholder="Enter Promoter Name">
                                    <option value="volvo">Volvo</option>
                                    <option value="saab">Saab</option>
                                    <option value="mercedes">Mercedes</option>
                                    <option value="audi">Audi</option>
                                </select>-->

						<html:select styleClass="form-control" property="buildingReason"
							name="npaMarkingForm" styleId="ReasonsforReduction2">
							<html:option value="0">Select</html:option>
							<html:optionsCollection property="reproductionValue"
								name="npaMarkingForm" label="label" value="value" />
						</html:select>
					</div>
				</div>

				<div class="row" style="margin-top: 10px; margin-left: 2px;">
					<div class="col-md-3" style="margin-top: 5px;">
						Plant and Machinery<font color="#FF0000" size="2">*</font>
					</div>
					<div class="col-md-2" style="margin-top: 5px;">
						<!--  <input type="Text" class="form-control" style="text-align: right;" onchange="calculatedateofSanction();" disabled id="dateofSanction2" placeholder="Enter CGPAN" value="0.00" />
                            </div> -->
						<html:text property="plantMachinerySection" alt="Reference"
							name="npaMarkingForm" styleClass="form-control"
							style="text-align: right;" onchange="calculatedateofSanction();"
							disabled="true" styleId="dateofSanction2" />
					</div>

					<div class="col-md-2" style="margin-top: 5px;">
						<!--  <input type="Text" class="form-control" style="text-align: right;" onchange="calculateNPA();" id="dateofNPA2" placeholder="Enter CGPAN" value="0.00" />
                            </div> -->
						<html:text property="plantMachineryDateNpa" alt="Reference"
							name="npaMarkingForm" styleClass="form-control"
							style="text-align: right;" onchange="calculateNPA();"
							styleId="dateofNPA2" />
					</div>

					<div class="col-md-2" style="margin-top: 5px;">
						<!-- <input type="Text" class="form-control" style="text-align: right;" onchange="calculateNetworthofguarantor();" id="Networthofguarantor2" placeholder="Enter CGPAN" value="0.00" />
                            </div> -->


						<html:text property="plantMachineryNetWorth" alt="Reference"
							name="npaMarkingForm" styleClass="form-control"
							style="text-align: right;"
							onchange="calculateNetworthofguarantor();"
							styleId="Networthofguarantor2" />
					</div>

					<div class="col-md-3" style="margin-top: 5px;">
						<!--  <select class="form-control" id="ReasonsforReduction2" placeholder="Enter Promoter Name">
                                    <option value="volvo">Volvo</option>
                                    <option value="saab">Saab</option>
                                    <option value="mercedes">Mercedes</option>
                                    <option value="audi">Audi</option>
                                </select>-->

						<html:select styleClass="form-control"
							property="plantMachineryReason" name="npaMarkingForm"
							styleId="ReasonsforReduction3">
							<html:option value="0">Select</html:option>
							<html:optionsCollection property="reproductionValue"
								name="npaMarkingForm" label="label" value="value" />
						</html:select>
					</div>
				</div>

				<div class="row" style="margin-top: 10px; margin-left: 2px;">
					<div class="col-md-3" style="margin-top: 5px;">
						Other Fixed /Movable Assets<font color="#FF0000" size="2">*</font>
					</div>
					<div class="col-md-2" style="margin-top: 5px;">
						<!-- <input type="Text" class="form-control" style="text-align: right;" onchange="calculatedateofSanction();" disabled id="dateofSanction3" placeholder="Enter CGPAN" value="0.00" />
                            </div> -->
						<html:text property="otherFixedMovableSection" alt="Reference"
							name="npaMarkingForm" styleClass="form-control"
							style="text-align: right;" onchange="calculatedateofSanction();"
							disabled="true" styleId="dateofSanction3" />
					</div>

					<div class="col-md-2" style="margin-top: 5px;">
						<!--   <input type="Text" class="form-control" style="text-align: right;" onchange="calculateNPA();" id="dateofNPA3" placeholder="Enter CGPAN" value="0.00" />
                           </div> -->
						<html:text property="otherFixedMovableDateOfNpa" alt="Reference"
							name="npaMarkingForm" styleClass="form-control"
							style="text-align: right;" onchange="calculateNPA();"
							styleId="dateofNPA3" />
					</div>
					<div class="col-md-2" style="margin-top: 5px;">
						<!-- <input type="Text" class="form-control" style="text-align: right;" onchange="calculateNetworthofguarantor();" id="Networthofguarantor3" placeholder="Enter CGPAN" value="0.00" />
                            </div> -->
						<html:text property="otherFixedMovableNetWorth" alt="Reference"
							name="npaMarkingForm" styleClass="form-control"
							style="text-align: right;"
							onchange="calculateNetworthofguarantor();"
							styleId="Networthofguarantor3" />
					</div>
					<div class="col-md-3" style="margin-top: 5px;">
						<!-- <select class="form-control" id="ReasonsforReduction3" placeholder="Enter Promoter Name">
                                    <option value="volvo">Volvo</option>
                                    <option value="saab">Saab</option>
                                    <option value="mercedes">Mercedes</option>
                                    <option value="audi">Audi</option>
                                </select>-->
						<html:select styleClass="form-control" property="otherFixedReason"
							name="npaMarkingForm" styleId="ReasonsforReduction4">
							<html:option value="0">Select</html:option>
							<html:optionsCollection property="reproductionValue"
								name="npaMarkingForm" label="label" value="value" />
						</html:select>
					</div>
				</div>
				<div class="row" style="margin-top: 10px; margin-left: 2px;">
					<div class="col-md-3" style="margin-top: 5px;">
						Current Assets<font color="#FF0000" size="2">*</font>
					</div>
					<div class="col-md-2" style="margin-top: 5px;">
						<!-- <input type="Text" class="form-control" style="text-align: right;" onchange="calculatedateofSanction();" disabled id="dateofSanction4" placeholder="Enter CGPAN" value="0.00" />
                            </div> -->

						<html:text property="currentAssetSection" alt="Reference"
							name="npaMarkingForm" styleClass="form-control"
							style="text-align: right;" disabled="true"
							onchange="calculatedateofSanction();" styleId="dateofSanction4" />
					</div>

					<div class="col-md-2" style="margin-top: 5px;">
						<!-- <input type="Text" class="form-control" style="text-align: right;" onchange="calculateNPA();" id="dateofNPA4" placeholder="Enter CGPAN" value="0.00" />
                           </div> -->

						<html:text property="currentAssetDateOfNpa" alt="Reference"
							name="npaMarkingForm" styleClass="form-control"
							style="text-align: right;" onchange="calculateNPA();"
							styleId="dateofNPA4" />
					</div>

					<div class="col-md-2" style="margin-top: 5px;">
						<!--  <input type="Text" class="form-control" style="text-align: right;" onchange="calculateNetworthofguarantor();" id="Networthofguarantor4" placeholder="Enter CGPAN" value="0.00" />
                            </div> -->
						<html:text property="currentAssetNetWorth" alt="Reference"
							name="npaMarkingForm" styleClass="form-control"
							style="text-align: right;"
							onchange="calculateNetworthofguarantor();"
							styleId="Networthofguarantor4" />
					</div>

					<div class="col-md-3" style="margin-top: 5px;">
						<!-- <select class="form-control" id="ReasonsforReduction5" placeholder="Enter Promoter Name">
                                    <option value="volvo">Volvo</option>
                                    <option value="saab">Saab</option>
                                    <option value="mercedes">Mercedes</option>
                                    <option value="audi">Audi</option>
                                </select>-->
						<html:select styleClass="form-control"
							property="currentAssetReson" name="npaMarkingForm"
							styleId="ReasonsforReduction5">
							<html:option value="0">Select</html:option>
							<html:optionsCollection property="reproductionValue"
								name="npaMarkingForm" label="label" value="value" />
						</html:select>
					</div>
				</div>

				<div class="row" style="margin-top: 10px; margin-left: 2px;">
					<div class="col-md-3" style="margin-top: 5px;">
						Others<font color="#FF0000" size="2">*</font>
					</div>
					<div class="col-md-2" style="margin-top: 5px;">
						<!--  <input type="Text" class="form-control" style="text-align: right;" onchange="calculatedateofSanction();" disabled id="dateofSanction5" placeholder="Enter CGPAN" value="0.00" />
                            </div> -->
						<html:text property="otherSection" alt="Reference"
							name="npaMarkingForm" styleClass="form-control"
							style="text-align: right;" disabled="true"
							onchange="calculatedateofSanction();" styleId="dateofSanction5" />
					</div>

					<div class="col-md-2" style="margin-top: 5px;">
						<!--   <input type="Text" class="form-control" style="text-align: right;" onchange="calculateNPA();" id="dateofNPA5" placeholder="Enter CGPAN" value="0.00" />
                            </div> -->

						<html:text property="otherNpa" alt="Reference"
							name="npaMarkingForm" styleClass="form-control"
							style="text-align: right;" onchange="calculateNPA();"
							styleId="dateofNPA5" />
					</div>

					<div class="col-md-2" style="margin-top: 5px;">
						<!--  <input type="Text" class="form-control" style="text-align: right;" onchange="calculateNetworthofguarantor();" id="Networthofguarantor5" placeholder="Enter CGPAN" value="0.00" />
                            </div> -->
						<html:text property="otherNetWorth" alt="Reference"
							name="npaMarkingForm" styleClass="form-control"
							style="text-align: right;"
							onchange="calculateNetworthofguarantor();"
							styleId="Networthofguarantor5" />
					</div>

					<div class="col-md-3" style="margin-top: 5px;">
						<!--  <select class="form-control" id="ReasonsforReduction5" placeholder="Enter Promoter Name">
                                    <option value="volvo">Volvo</option>
                                    <option value="saab">Saab</option>
                                    <option value="mercedes">Mercedes</option>
                                    <option value="audi">Audi</option>
                                </select>-->

						<html:select styleClass="form-control" property="otherReason"
							name="npaMarkingForm" styleId="ReasonsforReduction6">
							<html:option value="0">Select</html:option>
							<html:optionsCollection property="reproductionValue"
								name="npaMarkingForm" label="label" value="value" />
						</html:select>
					</div>
				</div>
				<html:hidden property="message" name="npaMarkingForm"
					styleId="message" />
				<html:hidden property="role" name="npaMarkingForm"
					styleId="rolenAME" />
				<html:hidden property="isSearchClicked" name="npaMarkingForm"
					styleId="isSearchClicked" />

				<div class="row" style="margin-top: 20px; margin-left: 2px;">
					<div class="col-md-3" style="margin-top: 10px; font-weight: 800;">Total</div>

					<!-- <div class="col-md-2 alert alert-success" role="alert" style="margin-top: 10px; text-align: right;" id="dateofSanction">0.00</div> -->
					<div class="col-md-2 " style="margin-top: 10px; text-align: right;">
						<html:text property="dateOfSectionTotal" alt="Reference"
							name="npaMarkingForm" styleClass="form-control" disabled="true"
							style="margin-top: 10px; text-align: right;"
							styleId="dateofSanction" />
					</div>

					<!--  <div class="col-md-2 alert alert-success" role="alert" style="margin-top: 10px; text-align: right;" id="divNPA">0.00</div> -->
					<div class="col-md-2 " style="margin-top: 10px; text-align: right;">
						<html:text property="dateOfNpaTotal" alt="Reference"
							name="npaMarkingForm" styleClass="form-control" disabled="true"
							style="margin-top: 10px; text-align: right;" styleId="divNPA" />
					</div>

					<!-- <div class="col-md-2 alert alert-success" role="alert" style="margin-top: 10px; text-align: right;" id="Networthofguarantor">0.00</div> -->

					<div class="col-md-2 " style="margin-top: 10px; text-align: right;">
						<html:text property="netWorthTotal" alt="Reference"
							name="npaMarkingForm" styleClass="form-control" disabled="true"
							style="margin-top: 10px; text-align: right;"
							styleId="Networthofguarantor" />
					</div>

					<div class="col-md-3 alert alert-light" role="alert"
						style="margin-top: 3px;">
						<button id="btnAdd1" type="button" onclick="SaveData();"
							class="btn btn-primary">Save</button>
						<input id="btnAdd" type="button" value="Cancel"
							onclick="return CancelData();" class="btn btn-primary" />
						<button type="button" class="btn btn-primary" data-toggle="modal"
							data-target="#myModal">View History</button>

					</div>
				</div>
			</div>
		</div>


		<!--      </div> -->

		<div class="modal fade" id="myModal" role="dialog">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header">
						<div class="col-sm-11" style='font-weight: 800;'>
							<h4 class="modal-title">CGPAN and Promoter History</h4>
						</div>
						<div class="col-sm-1">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
						</div>

					</div>
					<div class="modal-body">
						<table class="table table-striped" style="font-size: 13px;">
							<thead>
								<tr>
									<th scope="col">S.No.</th>
									<th scope="col">Level</th>
									<th scope="col">User Name</th>
									<th scope="col">Date</th>
									<th scope="col">Status</th>
									<th scope="col">Remark</th>
								</tr>
							</thead>
							<tbody>
								<%
									if (request.getAttribute("npaPopulateData") != null) {
											ArrayList<NPAMarkingPopulateData> list = (ArrayList) request.getAttribute("npaPopulateData");
											Iterator<NPAMarkingPopulateData> iterator = list.iterator();
											while (iterator.hasNext()) {
												NPAMarkingPopulateData d = iterator.next();
								%>
								<tr>
									<th scope="row"><%=d.getClm_au_id()%></th>
									<td><%=d.getClm_LVEL()%></td>
									<td><%=d.getUser_id()%></td>
									<td><%=d.getClm_dttime()%></td>
									<td><%=d.getClm_status()%></td>
									<td><%=d.getClm_remark()%></td>
								</tr>
								<%
									}
										} else {
								%>
								<tr>
									<td>No Data Found</td>
								</tr>
								<%
									}
								%>
							</tbody>
						</table>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</div>
			</div>
		</div>
	</html:form>
</body>
<script src="https://code.jquery.com/ui/1.10.1/jquery-ui.js"></script>
<script type="text/javascript">
	<%
		String datemessage = (String)request.getAttribute("datemessage");
		out.println(datemessage);
	%>

	var isSearchClicked;

	function CancelData() {
		$("#cgpan1").val('');
		getPromoter();
		$("#isDisable").css("display", "none");
	}

	$(document)
			.ready(
					function() {//alert("okkk");
						
						var cgpan = document.getElementById('cgpan1').value;
						//alert("Server cgpan ::" + cgpan); 
						var isSearchClicked = document
								.getElementById('isSearchClicked').value;
						//alert("Server cgpan ::" + isSearchClicked); 
						var message = document.getElementById('message').value;
						//alert("Message is :::" +message);

						if ((cgpan == "" && isSearchClicked == "")) {
							$("#isDisable").css("display", "none");
						} else if ((cgpan != "" && isSearchClicked == "")) {
							$("#isDisable").css("display", "none");
						} else if ((cgpan == "" && isSearchClicked != "")) {
							$("#isDisable").css("display", "none");
						} else {
							$("#isDisable").css("display", "block");
						}

						if ((message != "" && !(message.indexOf('Success') > -1))) {
							$("#isDisable").css("display", "block");
							var databasemsg = "<b>Please correct the following Error:</b><ul><li>";
							databasemsg = databasemsg + message + "</li></ul>";

							$("#errormsg").html(databasemsg).css({
								"color" : "#ff6666",
								"display" : "block"
							});
							$(window).scrollTop(0);
							$("#message").val("");

						}

						if ((message != "" && (message.indexOf('Success') > -1))) {
							$("#isDisable").css("display", "none");
							var databasemsg1 = "<b>" + message + "</b>";

							$("#errormsg").html(databasemsg1).css({
								"color" : "#ff6666",
								"display" : "block"
							});

							$("#message").val("");
							$("#cgpan1").val('');
							$("#promoterName1").empty();
							$("#promoterName1").append(
									'<option value="0">Select</option>');
						}
					});
	$(document).ready(function() {

		console.log($("#rolenAME").val());
		if ($("#rolenAME").val() == "CHECKER") {
			$("#NPADate").prop('disabled', true);
			$("#NPAReason").prop('disabled', true);
			$("#PrinicipleRepayment").prop('disabled', true);
			$("#creditrisk").prop('disabled', true);
			$("#check1").prop('disabled', true);
			$("#dateofNPA0").prop('disabled', true);
			$("#dateofNPA1").prop('disabled', true);
			$("#dateofNPA2").prop('disabled', true);
			$("#dateofNPA3").prop('disabled', true);
			$("#dateofNPA4").prop('disabled', true);
			$("#dateofNPA5").prop('disabled', true);
			$("#Networthofguarantor0").prop('disabled', true);
			$("#Networthofguarantor1").prop('disabled', true);
			$("#Networthofguarantor2").prop('disabled', true);
			$("#Networthofguarantor3").prop('disabled', true);
			$("#Networthofguarantor4").prop('disabled', true);
			$("#Networthofguarantor5").prop('disabled', true);

			$("#ReasonsforReduction0").prop('disabled', true);
			$("#ReasonsforReduction2").prop('disabled', true);
			$("#ReasonsforReduction3").prop('disabled', true);
			$("#ReasonsforReduction4").prop('disabled', true);
			$("#ReasonsforReduction5").prop('disabled', true);
			$("#ReasonsforReduction6").prop('disabled', true);
			$("#btnAdd1").hide();
			$("#interestRepayment").prop('disabled', true);
		} else {
			$("#NPADate").prop('disabled', false);
			$("#NPAReason").prop('disabled', false);
			$("#PrinicipleRepayment").prop('readonly', false);
			$("#creditrisk").prop('disabled', false);
			$("#check1").prop('disabled', false);
			$("#dateofNPA0").prop('disabled', false);
			$("#dateofNPA1").prop('disabled', false);
			$("#dateofNPA2").prop('disabled', false);
			$("#dateofNPA3").prop('disabled', false);
			$("#dateofNPA4").prop('disabled', false);
			$("#dateofNPA5").prop('disabled', false);
			$("#Networthofguarantor0").prop('disabled', false);
			$("#Networthofguarantor1").prop('disabled', false);
			$("#Networthofguarantor2").prop('disabled', false);
			$("#Networthofguarantor3").prop('disabled', false);
			$("#Networthofguarantor4").prop('disabled', false);
			$("#Networthofguarantor5").prop('disabled', false);

			$("#ReasonsforReduction0").prop('disabled', false);
			$("#ReasonsforReduction2").prop('disabled', false);
			$("#ReasonsforReduction3").prop('disabled', false);
			$("#ReasonsforReduction4").prop('disabled', false);
			$("#ReasonsforReduction5").prop('disabled', false);
			$("#ReasonsforReduction6").prop('disabled', false);
			$("#btnAdd1").show();
			$("#interestRepayment").prop('disabled', false);
		}
	});

	function setDateValue() {
		var start = new Date("1990-01-01"), end = new Date(), diff = new Date(
				end - start), days = diff / 1000 / 60 / 60 / 24;
		$("#NPADate").datepicker({
			dateFormat : 'dd/mm/yy',
			minDate : days * -1,
			maxDate : end
		}).attr('readonly', 'readonly').focus();
	}
	
	function dateValidation()
	{
		var PrinicipleRepayment = $("#PrinicipleRepayment").val();
		var creditrisk = $("#creditrisk").val();
		var NPAReason = $("#NPAReason").val();
		var interestRepayment = $("#interestRepayment").val();
		var PrincipleOutstanding = $("#PrincipleOutstanding").val();
		var InterestOutstanding = $("#InterestOutstanding").val();
		
		
		document.npaMarkingForm.target = "_self";
		document.npaMarkingForm.method = "POST";
		document.npaMarkingForm.action = "npaMarking.do?method=getNpaMarkingSearchDetail&creditrisk="+creditrisk+"&NPAReason="+NPAReason+
				                         "&PrinicipleRepayment="+PrinicipleRepayment+"&interestRepayment="+interestRepayment+
				                         "&PrincipleOutstanding="+PrincipleOutstanding+"&InterestOutstanding="+InterestOutstanding;
		
		document.npaMarkingForm.submit();
	}
	function calculateNPA() {
		var total = parseFloat($("#dateofNPA0").val())
				+ parseFloat($("#dateofNPA1").val())
				+ parseFloat($("#dateofNPA2").val())
				+ parseFloat($("#dateofNPA3").val())
				+ parseFloat($("#dateofNPA4").val())
				+ parseFloat($("#dateofNPA5").val());
		$("#divNPA").val(total.toFixed(2));
	}

	function calculateNetworthofguarantor() {
		var total = parseFloat($("#Networthofguarantor0").val())
				+ parseFloat($("#Networthofguarantor1").val())
				+ parseFloat($("#Networthofguarantor2").val())
				+ parseFloat($("#Networthofguarantor3").val())
				+ parseFloat($("#Networthofguarantor4").val())
				+ parseFloat($("#Networthofguarantor5").val());
		$("#Networthofguarantor").val(total.toFixed(2));
	}
	function calculatedateofSanction() {
		var total = parseFloat($("#dateofSanction0").val())
				+ parseFloat($("#dateofSanction1").val())
				+ parseFloat($("#dateofSanction2").val())
				+ parseFloat($("#dateofSanction3").val())
				+ parseFloat($("#dateofSanction4").val())
				+ parseFloat($("#dateofSanction5").val());
		$("#dateofSanction").val(total.toFixed(2));
	}
    
	function calculateIntrest() {
		if(parseFloat($("#PrinicipleRepayment").val()) == 0){
			var disbAmt = parseFloat($("#disbursementAmount").val());
			$("#PrincipleOutstanding").val(disbAmt);
			$("#InterestOutstanding").val(0);
		}
		else
		{
			var PO = parseFloat($("#disbursementAmount").val())
			- parseFloat($("#PrinicipleRepayment").val());
			$("#PrincipleOutstanding").val(PO);
			var intrest = ((PO * parseFloat($("#InterestRate").val())) / 100 / 365) * 90;
			$("#InterestOutstanding").val(parseInt(intrest));
		}	
	}
	/* function calculateIntrest() {
		debugger;
		var PO = parseFloat($("#GuaranteeAmount").val())
				- parseFloat($("#PrinicipleRepayment").val());
		$("#PrincipleOutstanding").val(PO);
		var intrest = ((PO * parseFloat($("#InterestRate").val())) / 100 / 365) * 90;
		$("#InterestOutstanding").val(parseInt(intrest));
	} Commented by Sarita 19OCT2021*/

	$(function() {
		$("#btnSearch").bind("click", function() {

			$("#btnSearch").data('clicked', true);
			if ($("#btnSearch").data('clicked')) {
				var isSearchClicked = "Clicked";
				$("#isSearchClicked").val(isSearchClicked);
			} else {
				var isSearchClicked = "";
				$("#isSearchClicked").val(isSearchClicked);
			}

			var msg = "<b>Please correct the following Error:</b><ul>";
			var val = 0;
			if ($("#promoterName1").val() == "0") {
				msg = msg + "<li>Promoter Name can not be black!!</li>";
				val = 1;
			}
			if ($("#cgpan1").val() == "") {
				msg = msg + "<li>CGPAN can not be black!!</li>";
				val = 1;

			}
			if (val == 1) {
				$("#errormsg").html(msg).css({
					"color" : "#ff6666",
					"display" : "block"
				});
				msg = msg + "</ul>";
				$(window).scrollTop(0);
				return;
			}
			if (val == 0) {//alert("okk");
				$("#btnSearch")[0].innerText = 'loading';
				$("#btnSearch").prop('disabled', true);
				setTimeout(function() {
					$("#btnSearch")[0].innerText = 'Search';
					$("#btnSearch").prop('disabled', false);
				}, 8000);

				getPromoterDetails();

			} else {
				$("#btnSearch").prop('disabled', true);
			}
		});

		$("#btnClear").bind("click", function() {
			$("#cgpan1").val('');
			getPromoter();
		});
	});

	function getPromoter() {
		var cgpan = document.getElementById('cgpan1').value;
		$("#btnSearch")[0].innerText = 'loading';
		$("#btnSearch").prop('disabled', true);
		setTimeout(function() {
			$("#btnSearch")[0].innerText = 'Search';
			$("#btnSearch").prop('disabled', false);
		}, 8000);
		document.npaMarkingForm.target = "_self";
		document.npaMarkingForm.method = "POST";
		document.npaMarkingForm.action = "npaMarking.do?method=getNpaMarking&cgpan1="
				+ cgpan;
		document.npaMarkingForm.submit();
	}

	function getPromoterDetails() {
		var cgpan = document.getElementById('cgpan1').value;
		var promoterName = document.getElementById('promoterName1').value;

		document.npaMarkingForm.target = "_self";
		document.npaMarkingForm.method = "POST";
		document.npaMarkingForm.action = "npaMarking.do?method=getNpaMarkingSearchDetail&cgpan1="
				+ cgpan
				+ "&promoterName1"
				+ promoterName
				+ "&isSearchClicked"
				+ isSearchClicked;
		document.npaMarkingForm.submit();

	}

	var checkVal = $("#check2").val();
	function setCheckValue() {
		if ($("#check2").prop("checked") == true) {
			checkVal = "Y";
			$("#check2").val(checkVal);
		} else {
			//alert("2222");
			checkVal = "N";
			$("#check2").val(checkVal);
		}

	}
</script>

<script type="text/javascript">
	function SaveData() {
		debugger;
		var securityDetails = [];
		//alert("GuaratneeStart_Date  :"+datediff);
		var NPAReason = $("#NPAReason").val();
		var PrinicipleRepayment = $("#PrinicipleRepayment").val();
		var creditrisk = $("#creditrisk").val();
		var interestRepayment = $("#interestRepayment").val();
		var guaranteeAmount1 = $("#GuaranteeAmount").val();
		var NPADatee = $("#NPADate").val();
		var startGuaranteeDate = $("#guaratneeStartDate").val();
		//var checkVal=document.getElementById('check2').value;
		//alert("checkVal::"+checkVal);

		var PrincipleOutstanding = document
				.getElementById('PrincipleOutstanding').value;
		var InterestOutstanding = document
				.getElementById('InterestOutstanding').value;

		var dateofNPA0 = $("#dateofNPA0").val();
		var dateofNPA1 = $("#dateofNPA1").val();
		var dateofNPA2 = $("#dateofNPA2").val();
		var dateofNPA3 = $("#dateofNPA3").val();
		var dateofNPA4 = $("#dateofNPA4").val();
		var dateofNPA5 = $("#dateofNPA5").val();

		var Networthofguarantor0 = $("#Networthofguarantor0").val();
		var Networthofguarantor1 = $("#Networthofguarantor1").val();
		var Networthofguarantor2 = $("#Networthofguarantor2").val();
		var Networthofguarantor3 = $("#Networthofguarantor3").val();
		var Networthofguarantor4 = $("#Networthofguarantor4").val();
		var Networthofguarantor5 = $("#Networthofguarantor5").val();

		var ReasonsforReduction0 = $("#ReasonsforReduction0").val();
		var ReasonsforReduction2 = $("#ReasonsforReduction2").val();
		var ReasonsforReduction3 = $("#ReasonsforReduction3").val();
		var ReasonsforReduction4 = $("#ReasonsforReduction4").val();
		var ReasonsforReduction5 = $("#ReasonsforReduction5").val();
		var ReasonsforReduction6 = $("#ReasonsforReduction6").val();
		var msg = "<b>Please correct the following Error:</b><ul>";
		var countErr = 0 ;
		if (NPADatee == "") {
			msg = msg + "<li>NPA Date can't be blank.</li>";
			countErr = 1;
		}
		if(startGuaranteeDate == "")
		{
			msg = msg + "<li>Guarantee Not Started Can't Save the Data!!</li>";
			countErr = 1;
		}

		var NPADate = $("#NPADate").val().split('/');
		var tt1 = NPADate[1] + '/' + NPADate[0] + '/' + NPADate[2];
		var dateNPA = new Date(tt1);

		var GuaratneeStart_Date = $("#guaratneeStartDate").val().split('/');
		var tt = GuaratneeStart_Date[1] + '/' + GuaratneeStart_Date[0] + '/'
				+ GuaratneeStart_Date[2];

		var date = new Date(tt);
		var newdate = new Date(date);
		newdate.setDate(newdate.getDate() + 90);

		/*if (dateNPA < newdate) {
			msg = msg
					+ "<li>NPA Date can't be less than 90  days Guarantee Start Date</li>";
			countErr = 1;

		}SARITA24102021 as per RBI guidelines this Validation Not Required..*/

		if (NPAReason == "0") {
			msg = msg + "<li>Please Select a valid Reason.</li>";
			countErr = 1;

		}

		if (PrinicipleRepayment == "" || parseFloat(PrinicipleRepayment) < 0) {
			msg = msg
					+ "<li>Priniciple Repayment (Before NPA)  can't be blank OR Negative</li>";
			countErr = 1;

		}

		if (interestRepayment == "" || parseFloat(interestRepayment) < 0) {
			msg = msg
					+ "<li>Interest_Repayment (Before NPA) can't be blank OR Negative</li>";
			countErr = 1;

		}

		if (parseFloat(interestRepayment) > parseFloat(guaranteeAmount1)) {
			msg = msg
					+ "<li>Interest_Repayment (Before NPA) Amount Can't be greater than Guaranteed Amount!!</li>";
			countErr = 1;

		}

		/*if (parseFloat(interestRepayment) > parseFloat(PrinicipleRepayment)) {
			msg = msg
					+ "<li>Interest_Repayment (Before NPA) Amount Can't be greater than Principle Repayment!!</li>";
			countErr = 1;

		}*/

		if (parseFloat(PrinicipleRepayment) > parseFloat(guaranteeAmount1)) {
			msg = msg
					+ "<li>Priniciple Repayment (Before NPA) Amount Can't be greater then Guaranteed Amount!!</li>";
			countErr = 1;

		}

		if (creditrisk == "0") {
			//msg = msg + "<li>Please Select turning NPA and minimising the credit risk</li>";
			msg = msg + "<li>Please Select enumerate efforts taken by MLI to prevent account turning NPA and minimizing the credit risk</li>";
			countErr = 1;

		}

		if ($("#check2").val() == "N") {
			msg = msg
					+ "<li>Select Checkbox NPA date as per the RBI guidelines</li>";
			countErr = 1;

		}

		if (dateofNPA0 == "") {
			msg = msg
					+ "<li>Land security As on date of NPA can't be blank</li>";
			countErr = 1;

		}

		if (dateofNPA1 == "") {
			msg = msg
					+ "<li>Building security As on date of NPA can't be blank</li>";
			countErr = 1;

		}

		if (dateofNPA2 == "") {
			msg = msg
					+ "<li>Plant security As on date of NPA can't be blank</li>";
			countErr = 1;

		}

		if (dateofNPA4 == "") {
			msg = msg
					+ "<li>Other Fixed security As on date of NPA can't be blank</li>";
			countErr = 1;

		}

		if (dateofNPA4 == "") {
			msg = msg
					+ "<li>Current Assets security As on date of NPA can't be blank</li>";
			countErr = 1;

		}

		if (dateofNPA5 == "") {
			msg = msg
					+ "<li>Others security As on date of NPA can't be blank</li>";
			countErr = 1;

		}

		if (Networthofguarantor0 == "") {
			msg = msg
					+ "<li>Land networth of guarantor/Promoter(in Rs.) can't be blank</li>";
			countErr = 1;

		}
		if (Networthofguarantor1 == "") {
			msg = msg
					+ "<li>Building networth of guarantor/Promoter(in Rs.) can't be blank</li>";
			countErr = 1;

		}
		if (Networthofguarantor2 == "") {
			msg = msg
					+ "<li>Plant and Machinery networth of guarantor/Promoter(in Rs.) can't be blank</li>";
			countErr = 1;

		}
		if (Networthofguarantor3 == "") {
			msg = msg
					+ "<li>Other Fixed networth of guarantor/Promoter(in Rs.) can't be blank</li>";
			countErr = 1;

		}
		if (Networthofguarantor4 == "") {
			msg = msg
					+ "<li>Current Assets networth of guarantor/Promoter(in Rs.) can't be blank</li>";
			countErr = 1;

		}
		if (Networthofguarantor5 == "") {
			msg = msg
					+ "<li>Others networth of guarantor/Promoter(in Rs.) can't be blank</li>";
			countErr = 1;

		}
		
		securityDetails.push({
			"ReasonsforReduction0" : ReasonsforReduction0,
		    "ReasonsforReduction2" : ReasonsforReduction2,
		    "ReasonsforReduction3" : ReasonsforReduction3,
		    "ReasonsforReduction4" : ReasonsforReduction4,
		    "ReasonsforReduction5" : ReasonsforReduction5,
		    "ReasonsforReduction6" : ReasonsforReduction6,
		});
		var securityDetailsErr = 0;
		jQuery.each(securityDetails,function(i, val) 
		{
			if((val.ReasonsforReduction0 == "0") && (val.ReasonsforReduction2 == "0") && 
			   (val.ReasonsforReduction3 == "0") && (val.ReasonsforReduction4 == "0") && 
			   (val.ReasonsforReduction5 == "0") && (val.ReasonsforReduction6 == "0") ){
				securityDetailsErr = 1;
			}
		});
		if(securityDetailsErr == 1)
		{
			msg = msg+ "<li>Please select alleast one Reasons for Reduction in the value of Security, if any</li>";
	        countErr = 1;
		}
		/* 
		if (ReasonsforReduction0 == 0 || ReasonsforReduction0 == "") {
			msg = msg
					+ "<li>Please select land  Reasons for Reduction in the value of Security, if any</li>";
			countErr = 1;
		}
		if (ReasonsforReduction2 == 0 || ReasonsforReduction2 == "") {
			msg = msg
					+ "<li>Please select building  Reasons for Reduction in the value of Security, if any</li>";
			countErr = 1;

		}
		if (ReasonsforReduction3 == 0 || ReasonsforReduction3 == "") {
			msg = msg
					+ "<li>Please select plant and machinery  Reasons for Reduction in the value of Security, if any</li>";
			countErr = 1;

		}
		if (ReasonsforReduction4 == 0 || ReasonsforReduction4 == "") {
			msg = msg
					+ "<li>Please select other fixed  Reasons for Reduction in the value of Security, if any</li>";
			countErr = 1;

		}
		if (ReasonsforReduction5 == 0 || ReasonsforReduction5 == "") {
			msg = msg
					+ "<li>Please select current Assets  reasons for Reduction in the value of Security, if any</li>";
			countErr = 1;

		}
		if (ReasonsforReduction6 == 0 || ReasonsforReduction6 == "") {
			msg = msg
					+ "<li>Please select Others  Reasons for Reduction in the value of Security, if any.</li>";
			countErr = 1;

		} */

		if (countErr == 1) {
			$("#errormsg").html(msg).css({
				"color" : "#ff6666",
				"display" : "block"
			});
			msg = msg + "</ul>";
			$(window).scrollTop(0);
			return;
		}

		if (countErr == 0) {

			$("#btnAdd1")[0].innerText = 'loading...';
			$("#btnAdd1").prop('disabled', true);
			setTimeout(function() {
				$("#btnAdd1")[0].innerText = 'Save';
				$("#btnAdd1").prop('disabled', false);
			}, 8000);
			document.npaMarkingForm.target = "_self";
			document.npaMarkingForm.method = "POST";
			document.npaMarkingForm.action = "npaMarking.do?method=getNpaDetail&NPADate="
					+ NPADatee
					+ "&check="
					+ checkVal
					+ "&PrincipleOutstanding="
					+ PrincipleOutstanding
					+ "&InterestOutstanding=" + InterestOutstanding;
			document.npaMarkingForm.submit();
		}
	}
</script>
</html>
