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
<%@page import="com.cgtsi.actionform.CheckListForm"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous" />
 <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
 <script>
 $(document).ready(function() 
{
	var memberId = document.getElementById('memberId').value;	
});
 
 function mainForm()
 {
	 $("#backbutton").attr("disabled", "disabled");
		$("#backbutton")[0].innerText = 'loading';
		setTimeout(function() {
			$("#backbutton")[0].innerText = Back;
		}, 8000); 
 	var memberId = $("#memberId").val();
 	var bankIdd = memberId.substr(0,4);
 	
 	document.claimProcessingApprovalForm.target = "_self";
 	document.claimProcessingApprovalForm.method = "POST";
 	document.claimProcessingApprovalForm.action = "claimProcessingApproval.do?method=getClaimProcessingApproval&bankIdd="+bankIdd;
 	document.claimProcessingApprovalForm.submit();
 }
	function printDiv() 
	{
	  var divToPrint=document.getElementById('Print');
	  var newWin=window.open('','Print-Window');
	  newWin.document.open();
	  newWin.document.write('<html><head><link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous" /></head><body onload="window.print()">'+divToPrint.innerHTML+'</body></html>');
	  newWin.document.close();
	  setTimeout(function(){newWin.close();},10);
	}
	
	
</script>
</head>

<body>
<html:form name="claimProcessingApprovalForm"
		type="com.cgtsi.actionform.ClaimProcessingApprovalActionForm"
		action="claimProcessingApproval.do?method=getClaimProcessingApproval" method="POST">

<%
		

 String sysCurrentDDate="";
 String claimRefNumber="";
 String cGPAN="";
 String sSIUNITNAME="";
 String gUARANTEEAMT="";
 String officialRAMTRIP="";
 String mEMBERID="";
 String mLI_Name="";
 String dateofissue="";
 String checkList1="";
 String checkList2="";
 String checkList3="";
 String checkList4="";
 String checkList5="";
 String checkList6="";
 String checkList7="";
 String checkList8="";
 String checkList9="";
 String checkList10="";
 String checkList11="";

if (request.getAttribute("checkListDtls") != null) {
				ArrayList<CheckListForm> list = (ArrayList) request.getAttribute("checkListDtls");
				Iterator<CheckListForm> iterator = list.iterator();
				while (iterator.hasNext()) {
					CheckListForm data = iterator.next();
					
					  sysCurrentDDate=data.getSysCurrentDDate();
					  claimRefNumber=data.getClaimRefNumber();
					  cGPAN=data.getcGPAN();
					  sSIUNITNAME=data.getsSIUNITNAME();
					  gUARANTEEAMT=data.getgUARANTEEAMT();
					  officialRAMTRIP=data.getOfficialRAMTRIP();
					  mEMBERID=data.getmEMBERID();
					  mLI_Name=data.getmLI_Name();
					  dateofissue=data.getDateofissue();
					
					  checkList1=data.getCheckList1();
					  checkList2=data.getCheckList2();
					  checkList3=data.getCheckList3();
					  checkList4=data.getCheckList4();
					  checkList5=data.getCheckList5();
					  checkList6=data.getCheckList6();
					  checkList7=data.getCheckList7();
					  checkList8=data.getCheckList8();
					  checkList9=data.getCheckList9();
					  checkList10=data.getCheckList1();
					  checkList11=data.getCheckList11();
					
					
				}
		}		
	%>


<div class="container row" style=" border: 1px solid black;margin-left: 10px;margin-top: 10px;" id="Print">
                <div class="col-lg-12">
                    <div class="row" style="margin-top: 9px;">
                    <html:hidden property="memberId" name="claimProcessingApprovalForm" styleId="memberId" />
                        <div class="col-md-5">Letter Ref No:</div>
                        <div class="col-md-5">
                           
                        </div>
                        <div class="col-md-2">
                            Date:<%=sysCurrentDDate %>
                        </div>
                    </div>
					<div class="row" style="margin-top: 9px;">
						<div class="col-md-12">
							To,<br/>
							Dy. General Manager,CGTMSE,<br/>
							1002 & 1003,Naman Centre, 10th floor,<br/>
							Plot No. C-31, G - Block,<br/>
							Bandra Kurla Complex,Bandra (East),<br/>
							MUMBAI - 400051
						</div>
					</div>
					<div class="row" style="margin-top: 9px;color: #1475ed;">
						<div class="col-md-12">
							Application for First Claim Installment
						</div>
					</div>
					
					<div class="row" style="margin-top: 9px;">
						<div class="col-md-2" style="font-weight: 600;">
							S.No. 
						</div>
						<div class="col-md-2" style="font-weight: 600;">
							 Claim Ref Number 
						</div>
						<div class="col-md-2" style="font-weight: 600;">
							 Cgpan 
						</div>
						<div class="col-md-4" style="font-weight: 600;">
							Unit Name 
						</div>
						<div class="col-md-2" style="font-weight: 600;">
							Approved Amount
						</div>
					</div>
					<div class="row" style="margin-top: 9px;">
						<div class="col-md-2" >
							1.
						</div>
						<div class="col-md-2" >
							<%=claimRefNumber %> 
						</div>
						<div class="col-md-2" >
							<%=cGPAN %> 
						</div>
						<div class="col-md-4" >
						<%=sSIUNITNAME %> 
						</div>
						<div class="col-md-2" >
						<%=gUARANTEEAMT %> 
						</div>
					</div>
					<div class="row" style="margin-top: 9px;">
						<div class="col-md-12" style="font-weight: 800;">
							<u>Declaration and Undertaking by Member Lending Institution[MLI]</u>
						</div>
					</div>
					<div class="row" style="margin-top: 9px;">
						<div class="col-md-12" style="font-weight: 800;">
							<u>Declaration</u>
						</div>
					</div>
					<div class="row" style="margin-top: 9px;">
						<div class="col-md-12" style="text-align: justify;">
							We Declare that the information given above is true and correct in every respect.We further declare that 
							there has been no fault or negligence on the part of the MLI or any of its officers in conducting the 
							account.We also declare that the officer preferring the claim on behalf of MLI is having the authority to 
							do so. <br/>
							We hereby declare that no fault or negligence has been pointed out by internal/external 
							auditors,inspectors of CGTMSE or its agency in respect of the account(s) for which claim is being 
							preferred.
						</div>
					</div>
					<div class="row" style="margin-top: 9px;">
						<div class="col-md-12" style="font-weight: 800;">
							<u>Undertaking-We hereby undertake</u>
						</div>
					</div>
					<div class="row" style="margin-top: 9px;">
						<div class="col-md-12" style="text-align: justify;">
							(a) To pursue all recovery steps including Legal Proceedings <br/>
							(b) To report to CGTMSE the position of outstanding dues from the borrower on half-yearly basis as on 
							31 March and 30th September of each year till final settlement of guarantee claim by CGTMSE 
							(c) To refund to CGTMSE the claim paid amount along with interest thereof at 4% over and above the 
							prevailing Bank Rate. if in the view of CGTMSE we have failed or neglected to take any action for 
							recovery of the guaranteed debt from the borrower or any other person from whom the amount is to be 
							recovered <br/>
							(d) On payment of claim by CGTMSE to remit to CGTMSE all such recoveries, after adjusting towards 
							the legal expenses incurred for recovery of the amount, which we or our agents acting on our behalf, may 
							make from the person or persons responsible for the administration of debt, or otherwise, in respect of the 
							debt due from him/them to us
						</div>
					</div>
					<div class="row" style="margin-top: 9px;">
						<div class="col-md-12" style="font-weight: 800;">
							<u>NOTE</u><br/>
							(1)CGTMSE reserve the right to ask for any additional information, if required<br/>
							(2)CGTMSE reserve the right to initiate any appropriate action/appoint any person/institution 
							etc.to verify the facts as mentioned above and if found contrary to the declaration, reserves the 
							right to treat the claim under cgfsi invalid.<br/>
						</div>
						<div class="col-md-8" style="font-weight: 800;">
							Name of Official RAM TRIP							
						</div>
						<div class="col-md-4"><%=officialRAMTRIP%></div>
						<div class="col-md-8" style="font-weight: 800;">							
							MLI(Bank)Name BARODA UTTAR PRADESH GRAMIN BANK							
						</div>
						<div class="col-md-4"><%=mLI_Name%></div>
						<div class="col-md-8" style="font-weight: 800;">							
							Member Id 							
						</div>
						<div class="col-md-4"><%=mEMBERID%></div>
						<div class="col-md-8" style="font-weight: 800;">							
							Date of Claim Submission
						</div>
						<div class="col-md-4"><%=dateofissue%></div>
					</div>
					<div class="row" style="margin-top: 9px;">
						<div class="col-md-12" style="color: crimson;">
							CheckList to be submitted by mli alongwith claim lodgement
						</div>
					</div>
					<div class="row" style="margin-top: 10px;BACKGROUND-COLOR: GRAY;COLOR: WHITE;FONT-WEIGHT: 700;TEXT-ALIGN: center;">
                        <div class="col-md-1">S.No.</div>
                        <div class="col-md-9"> Description</div>
                        <div class="col-md-2" >Yes/No</div>
                    </div>
					<div class="row" style="margin-top: 10px;">
                        <div class="col-md-1" style="TEXT-ALIGN: center;FONT-WEIGHT: BOLD;">1</div>
                        <div class="col-md-9">Activity is eligible as per Credit Guarantee Scheme(CGS)</div>
                        <div class="col-md-2">
                            <%=checkList1%>
                        </div>
                    </div>
					<div class="row" style="margin-top: 10px;">
                        <div class="col-md-1" style="TEXT-ALIGN: center;FONT-WEIGHT: BOLD;">2</div>
                        <div class="col-md-9">Wheather CIBIL done/CIR/KYC obtained and findings are satisfactory.</div>
                        <div class="col-md-2">
                             <%=checkList2%>
                        </div>
                    </div>
					<div class="row" style="margin-top: 10px;">
                        <div class="col-md-1" style="TEXT-ALIGN: center;FONT-WEIGHT: BOLD;">3</div>
                        <div class="col-md-9">Rate charged on loan is as per CGS guidelines.</div>
                        <div class="col-md-2">
                           <%=checkList3%>
                        </div>
                    </div>
					<div class="row" style="margin-top: 10px;">
                        <div class="col-md-1" style="TEXT-ALIGN: center;FONT-WEIGHT: BOLD;">4</div>
                        <div class="col-md-9">Third party gaurantee/collateral security stipulated.</div>
                        <div class="col-md-2">
                            <%=checkList4%>
                        </div>
                    </div>
					<div class="row" style="margin-top: 10px;">
                        <div class="col-md-1" style="TEXT-ALIGN: center;FONT-WEIGHT: BOLD;">5</div>
                        <div class="col-md-9">Date of NPA as fed in the system is as per RBI guidelines.</div>
                        <div class="col-md-2">
                          <%=checkList5%>
                        </div>
                    </div>
					<div class="row" style="margin-top: 10px;">
                        <div class="col-md-1" style="TEXT-ALIGN: center;FONT-WEIGHT: BOLD;">6</div>
                        <div class="col-md-9">Wheather outstanding amount mentioned in the claim application form is with respect to the NPA date as reported in claim form.</div>
                        <div class="col-md-2">
                        <%=checkList6%>
                        </div>
                    </div>
					<div class="row" style="margin-top: 10px;"> 
                        <div class="col-md-1" style="TEXT-ALIGN: center;FONT-WEIGHT: BOLD;">7</div>
                        <div class="col-md-9">Whether serious deficiencies have been observed in the matter of appraisal/renewal/disbursement/followup/conduct of the account.</div>
                        <div class="col-md-2">
                           <%=checkList7%>
                        </div>
                    </div>
					<div class="row" style="margin-top: 10px;">
                        <div class="col-md-1" style="TEXT-ALIGN: center;FONT-WEIGHT: BOLD;">8</div>
                        <div class="col-md-9">Major deficiencies observed in Pre sanction/Post disbursement inspections</div>
                        <div class="col-md-2">
                           <%=checkList8%>
                        </div>
                    </div>
					<div class="row" style="margin-top: 10px;">
                        <div class="col-md-1" style="TEXT-ALIGN: center;FONT-WEIGHT: BOLD;">9</div>
                        <div class="col-md-9">Wheather deficiencies observed on the part of internal staff as per the Staff Accountability exercise carried out.</div>
                        <div class="col-md-2">
                          <%=checkList9%>
                        </div>
                    </div>
					<div class="row" style="margin-top: 10px;">
                        <div class="col-md-1" style="TEXT-ALIGN: center;FONT-WEIGHT: BOLD;">10</div>
                        <div class="col-md-9">Internal rating was carried out and the proposal was found of Investment Grade.(applicable for loans sanctioned above 50 lakh)</div>
                        <div class="col-md-2">
                          <%=checkList10%>
                        </div>
                    </div>
					<div class="row" style="margin-top: 10px;">
                        <div class="col-md-1" style="TEXT-ALIGN: center;FONT-WEIGHT: BOLD;">11</div>
                        <div class="col-md-9">Wheather all the recoveries pertaining to the account after the date of NPA and before the claim lodgement have been duly incorporated in the claim form.</div>
                        <div class="col-md-2">
                            <%=checkList11%>
                        </div>
                    </div>
					<div class="row" style="margin-top: 9px;">
						<div class="col-md-12" style="font-weight: 800;color: red;">
							Report Generated On : 03 June 2021 : 17.42 hrs.
						</div>
					</div>
                </div>
				
            </div>
			<div class="row" style="margin-top: 9px;">
			<div class="col-md-4" >&nbsp;</div>
						<div class="col-md-4" >
							<button type="button" class="btn btn-Primary" onclick='printDiv();'>Print</button>
						</div>
						<div style="padding-right: 200px;">
							<button type="button" id="backbutton" class="btn btn-primary"  onclick="return mainForm()">Back</button>
						</div>
						<div class="col-md-4" >&nbsp;</div>
					</div>
					
			
					
					</html:form>
</body>
</html>