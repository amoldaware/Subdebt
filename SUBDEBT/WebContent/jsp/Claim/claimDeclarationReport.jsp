<%@page import="java.util.Iterator"%>
<%@page import="com.cgtsi.claim.ClaimDetail"%>
<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@page import ="java.text.DecimalFormat"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.cgtsi.actionform.ClaimActionForm"%>
<%
	DecimalFormat decimalFormat = new DecimalFormat("##########.##");
%>
<%
	session.setAttribute("CurrentPage",
			"claimDeclarationReport.do?method=claimDeclarationReport");

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	String claimDate;

	String iseligcom = "";

	String Isallrecinclmformcomm1 = "";
	String Isinternratinvestgradcomm1 = "";
	String Whetdeficinvolbystaffcomm1 = "";
	String Whetmajordeficinvolvdcomm1 = "";
	String Isrataspercgscomm1 = "";
	String Isthirdcollattakencomm1 = "";
	String Isnpadtasperguidcomm1 = "";

	String Isclmoswrtnpadtcomm1 = "";
	String Whetcibildonecomm1 = "";

	String Whetseriousdeficinvolcomm1 = "";
%>
  
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
<html:errors />
	
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><img src="images/ClaimsProcessingHeading.gif" width="131" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR> 
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="16"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<tr>
                        
                      </tr>
                      <tr>
                        <td colspan="6">&nbsp;</td>
                      </tr>
                      
                        <tr><td><b>Letter Ref No</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Date</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>
                       <TR >
						<TD height="20" >
							&nbsp;
						</TD>
					</TR>
                                        <TR >
						<TD height="20" >
							&nbsp;
						</TD>
					</TR>
                      
                      <tr>
                       <td>To,</td> </tr>
                      <tr> <td>Dy. General Manager,</td></TR>
                       <tr><td>CGTMSE,</Td></tr>
                       <tr><td>1002 & 1003,Naman Centre, 10th floor,</td></TR>
                       <tr><td>Plot No. C-31, G - Block,</td></tr>
                       <tr><td>Bandra Kurla Complex,Bandra (East),</td></tr>
                       <tr><td><u>MUMBAI - 400051</u></td></tr>
                          
                         <TR >
						<TD height="20" >
							&nbsp;
						</TD>
					</TR>
                      <%
                      	String memId = (String) request.getAttribute("memId");
                      	System.out.println("raaaassskkkk" + memId);
                      	//java.util.Date endDate1=(java.util.Date)request.getAttribute("endDate");

                      	//claimDate=dateFormat.format(endDate1);
                      %>
                      <TR>
                      		<TD class="Heading" width="100%"><p>Application for First Claim Installment  </p> </TD>
												
                       
												
											</TR>
											  
											

										</TABLE>
									</TD>
									<TR>
    <%
    	ArrayList arraylist = null;
    	String AsfStringArray[] = null;
    	String size = (String) request.getAttribute("claimViewArraySize");
   
    	

    	if (size.equals("0")) {
    		out.println("<tr><td class=\"Heading\" colspan=\"11\"><center>No Data Found</center</td></tr>");
    	}
    	if (size != null && size != "0") {
    		arraylist = (ArrayList) request.getAttribute("claimViewArray");
    %> 
                           <tr>
                      <td colspan="4">
                       <table width="100%" border="0" cellspacing="1" cellpadding="0">
                        <tr class="ColumnBackground">
                       <td>
						<div align="center">S.No.</div>
						</td>
						 <td class="ColumnBackground">
						<div align="center">Claim Ref Number</div>
						</td>
						 <td class="ColumnBackground">
						<div align="center">Cgpan</div>
						</td>
						 <td class="ColumnBackground">
						<div align="center">Unit Name</div>
						</td>
						 <td class="ColumnBackground">
						<div align="center">Approved Amount</div>
						</td>
                      							
						</TR>     
   				<%
        			
        			for (int count7 = 0; count7 < arraylist.size(); count7++) {
        				ClaimDetail claimDetails = new ClaimDetail();
	        			int sum = 0;
	        			claimDetails = (ClaimDetail) arraylist.get(count7);
	        			System.out.println("countasssssssssss"+ count7);
	        			System.out.println("raaaassskkkk" + claimDetails.getCgpan());
	        			System.out.println("raaaassskkkk"+ claimDetails.getSsiUnitName());
	        					
        %>
                <tr>
                    <td class="ColumnBackground1">&nbsp;<%=count7 + 1%></td>
                    <td class="ColumnBackground1" size="30">&nbsp;
                  
                    <%=claimDetails.getClaimRefNum()%>
                   
                  </td>  
                  <td class="ColumnBackground1" size="15">&nbsp;
                 
                   <%=claimDetails.getCgpan()%>
                
                  </td>
                  
                   <td class="ColumnBackground1" size="15">&nbsp;
                 
                   <%=claimDetails.getSsiUnitName()%>
                
                  </td>
                  
                  
                   <td class="ColumnBackground1" size="15">&nbsp;
                 
                   <%=claimDetails.getApprovedClaimAmount()%>
                
                  </td>
               
                <%
                               	}
                               %>
                
         <%
                         	}
                         %>
          
          </TR>
        </TABLE>
                                                        
                                                        
                                                        
                                                         <p><b><u>Declaration and Undertaking by Member Lending Institution[MLI]</u></b></p>
                                                         <p><b><u>Declaration</u></b></p>
          
                                                        
                                                        <p>We Declare that the information given above is true and correct in every respect.We further declare that there has been no fault or negligence on the part of the MLI or any of its  officers in conducting the account.We also declare that the officer preferring the claim on behalf of MLI is having the authority to do so</p>
                                                        <p>We  hereby declare that no fault or negligence has been pointed out by internal/external auditors,inspectors of CGTMSE or its agency in respect of the account(s) for which claim is being preferred</p>
                                                        <p><b><u>Undertaking-We hereby undertake</u></b></p>
                                                        <p>(a) To pursue all recovery steps including Legal Proceedings<p/>
                                                        <p>(b) To report to CGTMSE the position of outstanding dues from the borrower on half-yearly basis as on 31 March  and 30th September of each year till final settlement of guarantee claim by CGTMSE<p/>
                                                        <p>(c) To refund to CGTMSE the claim paid amount along with interest thereof at 4% over and above the prevailing Bank Rate. if in the view of CGTMSE we have failed or neglected to take any action for recovery of the guaranteed debt from the borrower or any other person from whom the amount is to be recovered <p/>
                                                        <p>(d) On payment of claim by CGTMSE to remit to CGTMSE all such recoveries, after adjusting towards the legal expenses incurred for recovery of the amount, which we or our agents acting on our behalf, may make from the person or persons responsible for the administration of debt, or otherwise, in respect of the debt due from him/them to us </p>
                                                        
                                                        <p><b><u>NOTE</u></b></p>
                                                        <p><b>(1)CGTMSE reserve the right to ask for any additional information, if required</b></p>
                                        
                                         <p><b>(2)CGTMSE reserve the right to initiate any appropriate action/appoint any person/institution etc.to verify the facts as mentioned above and if found contrary to the declaration, reserves the right to treat the claim under CGFSI invalid</b></p>
                                                        
						</TD>
					</TR>
                                       
                       <TR>
						<TD height="20" >
							&nbsp;
						</TD>
					</TR>
                   	<TD height="20" >
							&nbsp;
						</TD>
					</TR>
					       <TR>
                      		<TD class="Heading" width="100%"><p>ACCOUNT DETAILS </p> </TD>
												
                       
												
											</TR>
					
							<TR>
    <%
    	ArrayList arraylist1 = null;
    	String AsfStringArray1[] = null;
    	String size1 = (String) request	.getAttribute("accountdetailviewSize");
    	System.out.println("size1===" + size1);

    	if (size1.equals("0")) {
    		out.println("<tr><td class=\"Heading\" colspan=\"11\"><center>No Data Found</center</td></tr>");
    	}
    	if (size1 != null && size1 != "0") {
    		arraylist1 = (ArrayList) request
    				.getAttribute("accountdetailview");
    %> 
                           <tr>
                      <td colspan="4">
                       <table width="100%" border="0" cellspacing="1" cellpadding="0">
                        <tr class="ColumnBackground">
                     
						 <td class="ColumnBackground">
						<div align="center">MEMBER ID</div>
						</td>
						 <td class="ColumnBackground">
						<div align="center">MLI NAME</div>
						</td>
						 <td class="ColumnBackground">
						<div align="center">BENEFICIARY</div>
						</td>
						 <td class="ColumnBackground">
						<div align="center">ACCOUNT NUMBER</div>
						</td>
						 <td class="ColumnBackground">
						<div align="center">IFSC CODE</div>
						</td>
						 
                      							
						</TR>     
   <%
        	ClaimDetail claimDetailsss = new ClaimDetail();
        		for (int count = 0; count < arraylist1.size(); count++) {

        			int sum = 0;

        			AsfStringArray = new String[5];
        			AsfStringArray[0] = "";
        			AsfStringArray[1] = "";
        			AsfStringArray[2] = "";
        			AsfStringArray[3] = "";
        			AsfStringArray[4] = "";
        			// AsfStringArray[5]="";
        			//AsfStringArray=(String[])arraylist.get(count);
        			claimDetailsss = (ClaimDetail) arraylist.get(count);
        			// System.out.println("raaaassskkkk"+claimDetailsss.getCgpan());
        %>
                  <tr>
                    
                       <td class="ColumnBackground1" >&nbsp;
                    
                      <%=claimDetailsss.getMliid()%>
                     
                    </td>  
                    <td class="ColumnBackground1">&nbsp;
                   
                     <%=claimDetailsss.getMliname()%>
                  
                    </td>
                    
                     <td class="ColumnBackground1">&nbsp;
                   
                     <%=claimDetailsss.getMembenificiary()%>
                  
                    </td>
                    
                    
                     <td class="ColumnBackground1" >&nbsp;
                   
                     <%=claimDetailsss.getMemaccountno()%>
                  
                    </td>
                     <td class="ColumnBackground1" >&nbsp;
                   
                     <%=claimDetailsss.getMemrtgsno()%>
                  
                    </td>
                  
                  
                  <%
                                                      	}
                                                      %>
                  
           <%
                             	}
                             %>
          
          </TR>
        </TABLE>
                                                        
                                                 
                                                        
						</TD>
					</TR>
                                       
                       <TR>
						<TD height="20" >
							&nbsp;
						</TD>
					</TR>
                   	<TD height="20" >
							&nbsp;
						</TD>
					</TR>
                                        
  <p></p>
                                       
   <%
                                          	ArrayList arraylists = null;
                                          	String AsfStringArrayss[] = null;
                                          	String sizes = (String) request
                                          			.getAttribute("claimCheckListViewSize");

                                          	if (sizes.equals("0")) {
                                          		out.println("<tr><td class=\"Heading\" colspan=\"11\"><center>No Data Found</center</td></tr>");
                                          	}

                                          	if (sizes != null && sizes != "0") {
                                          		arraylists = (ArrayList) request
                                          				.getAttribute("claimCheckListView");
                                          %> 
                      	<tr align="center">
						<TD class="SubHeading" align="left">CheckList to be submitted by mli alongwith claim lodgement</TD>
						</TR>
                        
                        <tr>
                      <td colspan="4">
                       <table width="100%" border="0" cellspacing="1" cellpadding="0">
                        <tr class="ColumnBackground">
                       <td>
						<div align="center">S.No.</div>
						</td>
						 <td class="ColumnBackground">
						<div align="center">Description</div>
						</td>
						 <td class="ColumnBackground">
						<div align="center">Yes/No</div>
						</td>
						 <td class="ColumnBackground">
						<div align="center">Comments</div>
						</td>
                      							
						</TR>
						
	<%
								ClaimDetail claimDetailss = new ClaimDetail();
									for (int count = 0; count < arraylists.size(); count++) {

										int sum = 0;

										AsfStringArrayss = new String[22];
										AsfStringArrayss[0] = "";
										AsfStringArrayss[1] = "";
										AsfStringArrayss[2] = "";
										AsfStringArrayss[3] = "";
										AsfStringArrayss[4] = "";
										AsfStringArrayss[5] = "";
										AsfStringArrayss[6] = "";
										AsfStringArrayss[7] = "";
										AsfStringArrayss[8] = "";
										AsfStringArrayss[9] = "";
										AsfStringArrayss[10] = "";
										AsfStringArrayss[11] = "";
										AsfStringArrayss[12] = "";
										AsfStringArrayss[13] = "";
										AsfStringArrayss[14] = "";
										AsfStringArrayss[15] = "";
										AsfStringArrayss[16] = "";
										AsfStringArrayss[17] = "";
										AsfStringArrayss[18] = "";
										AsfStringArrayss[19] = "";
										AsfStringArrayss[20] = "";
										AsfStringArrayss[21] = "";
										//  AsfStringArray=(String[])arraylist.get(count);
										claimDetailss = (ClaimDetail) arraylists.get(count);

										System.out.println("raaakkkk" + claimDetailss.getCgpan());

										if ((claimDetailss.getIseligactcomm() == null)
												|| (claimDetailss.getIseligactcomm() == "")) {

											iseligcom = "--";

										} else {

											iseligcom = claimDetailss.getIseligactcomm();
										}

										if ((claimDetailss.getWhetcibildonecomm() == null)
												|| (claimDetailss.getWhetcibildonecomm() == "")) {

											Whetcibildonecomm1 = "---";

										} else {

											Whetcibildonecomm1 = claimDetailss
													.getWhetcibildonecomm();
										}

										if ((claimDetailss.getIsrataspercgscomm() == null)
												|| (claimDetailss.getIsrataspercgscomm() == "")) {

											Isrataspercgscomm1 = "---";

										} else {

											Isrataspercgscomm1 = claimDetailss
													.getIsrataspercgscomm();
										}

										if ((claimDetailss.getIsthirdcollattakencomm() == null)
												|| (claimDetailss.getIsthirdcollattakencomm() == "")) {

											Isthirdcollattakencomm1 = "---";

										} else {

											Isthirdcollattakencomm1 = claimDetailss
													.getIsthirdcollattakencomm();
										}

										if ((claimDetailss.getIsnpadtasperguidcomm() == null)
												|| (claimDetailss.getIsnpadtasperguidcomm() == "")) {

											Isnpadtasperguidcomm1 = "---";

										} else {

											Isnpadtasperguidcomm1 = claimDetailss
													.getIsnpadtasperguidcomm();
										}

										if ((claimDetailss.getWhetseriousdeficinvolcomm() == null)
												|| (claimDetailss.getWhetseriousdeficinvolcomm() == "")) {

											Whetseriousdeficinvolcomm1 = "---";

										} else {

											Whetseriousdeficinvolcomm1 = claimDetailss
													.getWhetseriousdeficinvolcomm();
										}

										if ((claimDetailss.getIsclmoswrtnpadtcomm() == null)
												|| (claimDetailss.getIsclmoswrtnpadtcomm() == "")) {

											Isclmoswrtnpadtcomm1 = "---";

										} else {

											Isclmoswrtnpadtcomm1 = claimDetailss
													.getIsclmoswrtnpadtcomm();
										}

										if ((claimDetailss.getIsallrecinclmformcomm() == null)
												|| (claimDetailss.getIsallrecinclmformcomm() == "")) {

											Isallrecinclmformcomm1 = "---";

										} else {

											Isallrecinclmformcomm1 = claimDetailss
													.getIsallrecinclmformcomm();
										}

										if ((claimDetailss.getIsinternratinvestgradcomm() == null)
												|| (claimDetailss.getIsinternratinvestgradcomm() == "")) {

											Isinternratinvestgradcomm1 = "---";

										} else {

											Isinternratinvestgradcomm1 = claimDetailss
													.getIsinternratinvestgradcomm();
										}

										if ((claimDetailss.getWhetdeficinvolbystaffcomm() == null)
												|| (claimDetailss.getWhetdeficinvolbystaffcomm() == "")) {

											Whetdeficinvolbystaffcomm1 = "---";

										} else {

											Whetdeficinvolbystaffcomm1 = claimDetailss
													.getWhetdeficinvolbystaffcomm();
										}

										if ((claimDetailss.getWhetmajordeficinvolvdcomm() == null)
												|| (claimDetailss.getWhetmajordeficinvolvdcomm() == "")) {

											Whetmajordeficinvolvdcomm1 = "---";

										} else {

											Whetmajordeficinvolvdcomm1 = claimDetailss
													.getWhetdeficinvolbystaffcomm();
										}
							%>
                                 
                      <TR>
                      <TD align="left" class="ColumnBackground" >1.</TD>
					 <TD align="left" valign="top" class="ColumnBackground">&nbsp;Activity is eligible as per Credit Guarantee Scheme(CGS)</TD>
					<td class="TableData"><div align="center">&nbsp;<%=claimDetailss.getIseligact()%></div></td>
					
					<td class="TableData"><div align="center">&nbsp;<%=iseligcom%></div></td>
						</TR>
					
						<TR>
						<TD align="left" class="ColumnBackground" >2.</TD>
							<TD align="left" valign="top" class="ColumnBackground">&nbsp;Whether CIBIL done/CIR/KYC obtained and findings are satisfactory. </TD>
							<td class="TableData"><div align="center">&nbsp;<%=claimDetailss.getWhetcibildone()%></div></td>
						<td class="TableData"><div align="center">&nbsp;<%=Whetcibildonecomm1%></div></td>
						</TR>
						
						<TR>
						<TD align="left" class="ColumnBackground" >3.</TD>
					   <TD align="left" valign="top" class="ColumnBackground">&nbsp;Rate charged on loan is as per CGS guidelines. </TD>
							<td class="TableData"><div align="center">&nbsp;<%=claimDetailss.getIsrataspercgs()%></div></td>
										
										<td class="TableData"><div align="center">&nbsp;<%=Isrataspercgscomm1%></div></td>
						</TR>
						<TR>
						<TD align="left" class="ColumnBackground" >4.</TD>
							<TD align="left" valign="top" class="ColumnBackground">&nbsp;Third party gaurantee/collateral security stipulated. </TD>
							<td class="TableData"><div align="center">&nbsp;<%=claimDetailss.getIsthirdcollattaken()%></div></td>
										
										<td class="TableData"><div align="center">&nbsp;<%=Isthirdcollattakencomm1%></div></td>
						</TR>
						<TR>
						<TD align="left" class="ColumnBackground" >5.</TD>
                      <TD align="left" valign="top" class="ColumnBackground">&nbsp;Date of NPA as fed in the system is as per RBI guidelines. </TD>
							<td class="TableData"><div align="center">&nbsp;<%=claimDetailss.getIsnpadtasperguid()%></div></td>
										
										<td class="TableData"><div align="center">&nbsp;<%=Isnpadtasperguidcomm1%></div></td>
						</TR>
						<TR>
						<TD align="left" class="ColumnBackground" >6.</TD>
							<TD align="left" valign="top" class="ColumnBackground">&nbsp;Whether outstanding amount mentioned in the claim application form is with respect to the NPA date as reported in claim form. </TD>
							<td class="TableData"><div align="center">&nbsp;<%=claimDetailss.getIsclmoswrtnpadt()%></div></td>
										
										<td class="TableData"><div align="center">&nbsp;<%=Isclmoswrtnpadtcomm1%></div></td>
						</TR>
						<TR>
						<TD align="left" class="ColumnBackground" >7.</TD>
							<TD align="left" valign="top" class="ColumnBackground">&nbsp;Whether serious deficiencies have been observed in the matter of appraisal/renewal/disbursement/followup/conduct of the account. </TD>
							<td class="TableData"><div align="center">&nbsp;<%=claimDetailss.getWhetseriousdeficinvol()%></div></td>
										
										<td class="TableData"><div align="center">&nbsp;<%=Whetseriousdeficinvolcomm1%></div></td>
						</TR>
						<TR><TD align="left" class="ColumnBackground" >8.</TD>
							<TD align="left" valign="top" class="ColumnBackground">&nbsp;Major deficiencies observed in Pre sanction/Post disbursement inspections </TD>
							<td class="TableData"><div align="center">&nbsp;<%=claimDetailss.getWhetmajordeficinvolvd()%></div></td>
										
						<td class="TableData"><div align="center">&nbsp;<%=Whetmajordeficinvolvdcomm1%></div></td>
						</TR>
						<TR>
						<TD align="left" class="ColumnBackground" >9.</TD>
							<TD align="left" valign="top" class="ColumnBackground">&nbsp;Whether deficiencies observed on the part of internal staff as per the Staff Accountability exercise carried out. </TD>
							<td class="TableData"><div align="center">&nbsp;<%=claimDetailss.getWhetdeficinvolbystaff()%></div></td>
										
										<td class="TableData"><div align="center">&nbsp;<%=Whetdeficinvolbystaffcomm1%></div></td>
						</TR>
						<TR>
						<TD align="left" class="ColumnBackground" >10.</TD>
                      <TD align="left" valign="top" class="ColumnBackground">&nbsp;Internal rating was carried out and the proposal was found of Investment Grade.(applicable for loans sanctioned above 50 lakh) </TD>
							<td class="TableData"><div align="center">&nbsp;<%=claimDetailss.getIsinternratinvestgrad()%></div></td>
										
										<td class="TableData"><div align="center">&nbsp;<%=Isinternratinvestgradcomm1%></div></td>
						</TR>
						<TR>
						<TD align="left" class="ColumnBackground" >11.</TD>
							<TD align="left" valign="top" class="ColumnBackground">&nbsp;Whether all the recoveries pertaining to the account after the date of NPA and before the claim lodgement have been duly incorporated in the claim form. </TD>
								<td class="TableData"><div align="center">&nbsp;<%=claimDetailss.getIsallrecinclmform()%></div></td>
						<td class="TableData"><div align="center">&nbsp;<%=Isallrecinclmformcomm1%></div></td>
						</TR>

                                        
                         <%
                                                                 	}
                                                                 %>
                
                         <%
                                         	}
                                         %>
          
                      
                      
                      
                       <TR>
						<TD height="20" >
							&nbsp;
						</TD>
					</TR>
                        </table>                  
                                  
                                    
                                         
					<tr>
					<td colspan="3" align="left" width="700"><font size="2" color="red">Report Generated On : 
					<%
						java.util.Date loggedInTime = new java.util.Date();
						java.text.SimpleDateFormat dateFormat1 = new java.text.SimpleDateFormat(
								"dd MMMMM yyyy ':' HH.mm");
						String date1 = dateFormat1.format(loggedInTime);
						out.println(date1);
					%> hrs.</font>
					  </td></tr>
					<TR>
						<TD align="center" valign="baseline" >
							<DIV align="center">
						
								<A href="javascript:history.back()">
									<IMG src="images/Back.gif" alt="Back" width="49" height="37" border="0"></A>
									
									<A href="javascript:printpage()">
									<IMG src="images/Print.gif" alt="Print" width="49" height="37" border="0"></A>
								
							</DIV>
						</TD>
					</TR>
				</TABLE>
			 </table> 
			</TD>
			<TD width="20" background="images/TableVerticalRightBG.gif">
				&nbsp;
			</TD>
		</TR>
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
    </TABLE>