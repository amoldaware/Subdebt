<%@ page language="java"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","showHelp.do");%>
<HTML>
<body topmargin="0" bottommargin="0" rightmargin="0" smarginheight="0"
marginwidth="0" marginheight="0" width="100%" leftmargin="0" onLoad="setToDefault()">
<form>
<table border="0" width="100%" cellspacing="0" cellpadding="0" leftmargin="5">
  <tr>
    <td width="19%"  valign="top" class="ColumnBackground"><img src="images\CgfsiLogo.gif" width="147" height="152"
    alt="CGTSI"></td>
	<% String path=request.getContextPath();
	%>
	<td width="19%"  valign="top" class="ColumnBackground">
	<!--<div align="left"><font size="2">
		Intranet</font>
	</div><br>
		<div align="left">
		<a href="javascript:openNewWindow('<%=path+"/Download/UserManual/Intranet/Admin.pdf"%>')"><li>Administration</li></a>
		</div><br>

		<div align="left">
		<a href="javascript:openNewWindow('<%=path+"/Download/UserManual/Intranet/Application.pdf"%>')"><li>Application Processing</li></a>
		</div><br>

		<div align="left">
		<a href="javascript:openNewWindow('<%=path+"/Download/UserManual/Intranet/ClaimsProcessing.pdf"%>')"><li>Claims Processing</li></a>
		</div><br>

		<div align="left">
		<a href="javascript:openNewWindow('<%=path+"/Download/UserManual/Intranet/GuaranteeMaintenance.pdf"%>')"><li>Guarantee Maintenance</li></a>
		</div><br>

		<div align="left">
		<a href="javascript:openNewWindow('<%=path+"/Download/UserManual/Intranet/InwardOutward.pdf"%>')"><li>Inward Outward</li></a>
		</div><br>

		<div align="left">
		<a href="javascript:openNewWindow('<%=path+"/Download/UserManual/Intranet/MCGS.pdf"%>')"><li>MCGS</li></a>
		</div><br>

		<div align="left">
		<a href="javascript:openNewWindow('<%=path+"/Download/UserManual/Intranet/ReceiptsAndPayments.pdf"%>')"><li>Receipts And Payments</li></a>
		</div><br>

		<div align="left">
		<a href="javascript:openNewWindow('<%=path+"/Download/UserManual/Intranet/Reports.pdf"%>')"><li>Reports</li></a>
		</div><br>

		<div align="left">
		<a href="javascript:openNewWindow('<%=path+"/Download/UserManual/Intranet/RiskManagementModule.pdf"%>')"><li>Risk Management</li></a>
		</div><br>

		<div align="left">
		<a href="javascript:openNewWindow('<%=path+"/Download/UserManual/Intranet/Securitization.pdf"%>')"><li>Securitization</li></a>
		</div><br>

		<div align="left">
		<a href="javascript:openNewWindow('<%=path+"/Download/UserManual/Intranet/ThinClient.pdf"%>')"><li>Thin Client</li></a>
		</div><br>
		-->

		<div align="left"><font size="2">
		Internet</font>
	</div><br>
		<div align="left">
		<a href="javascript:openNewWindow('<%=path+"/Download/UserManual/Internet/Admin.pdf"%>')"><li>Administration</li></a>
		</div><br>

		<div align="left">
		<a href="javascript:openNewWindow('<%=path+"/Download/UserManual/Internet/Application.pdf"%>')"><li>Application Processing</li></a>
		</div><br>

		<div align="left">
		<a href="javascript:openNewWindow('<%=path+"/Download/UserManual/Internet/ClaimsProcessing.pdf"%>')"><li>Claims Processing</li></a>
		</div><br>

		<div align="left">
		<a href="javascript:openNewWindow('<%=path+"/Download/UserManual/Internet/GuaranteeMaintenance.pdf"%>')"><li>Guarantee Maintenance</li></a>
		</div><br>

		<div align="left">
		<a href="javascript:openNewWindow('<%=path+"/Download/UserManual/Internet/MCGS.pdf"%>')"><li>MCGS</li></a>
		</div><br>

		<div align="left">
		<a href="javascript:openNewWindow('<%=path+"/Download/UserManual/Internet/ReceiptsPayments.pdf"%>')"><li>Receipts And Payments</li></a>
			</div><br>

		<div align="left">
		<a href="javascript:openNewWindow('<%=path+"/Download/UserManual/Internet/Reports.pdf"%>')"><li>Reports</li></a>
		</div><br>

		<div align="left">
		<a href="javascript:openNewWindow('<%=path+"/Download/UserManual/Internet/ThinClient.pdf"%>')"><li>Thin Client</li></a>
		</div><br>


		<div align="left"><font size="2">
		Complete User Manual</font>
	</div><br>

		<div align="left">
		<a href="javascript:openNewWindow('<%=path+"/Download/UserManual/Intranet/IntranetUserManual.pdf"%>')"><li>Complete Intranet User Manual</li></a>
		</div><br>

		<div align="left">
		<a href="javascript:openNewWindow('<%=path+"/Download/UserManual/Internet/InternetUserManual.pdf"%>')"><li>Complete Internet User Manual</li></a>
		</div><br>



	</td>

</tr>


<!--    <td width="81%" valign="top" class="HelpData"><div align="center">Table of Contents</div><ol>
      <li><a href="#1"
       >Who are the lenders
        eligible for availing guarantee under the Scheme?</a><br>
      </li>
      <li><a href="#2"
        >Which type of
        borrowers can be covered under the Scheme?</a><br>
        </li>
      <li><a href="#3"
        >What is quantum of
        credit facility which can be covered under the Scheme?</a><br>
        </li>
      <li><a href="#4"
        >What is the
        difference between primary security and collateral security?</a><br>
        </li>
      <li><a href="#5"
        >Can term loan or
        working capital facility alone be extended by an eligible lender and still be covered
        under the guarantee scheme?</a><br>
        </li>
      <li><a href="#6"
        >Can a credit
        facility extended to a borrower against a collateral security be covered under the
        Guarantee Scheme, if the lending institution relinquishes its rights on the collateral
        security.</a><br>
        </li>
      <li><a href="#7"
        >If aggregate credit
        facility (both term loan and working capital) exceeds Rs.25 lakh, can it still be covered
        under the Scheme?</a><br>
        </li>
      <li><a href="#8"
        >Whether the lender
        can sanction term loan and / or working capital facilities of say Rs.100 lakh and can
        obtain guarantee cover for eligible amount of Rs.25 lakh?</a><br>
        </li>
      <li><a href="#9"
        >Whether borrowers
        from service sector enterprises are eligible under the Scheme?</a><br>
        </li>
      <li><a href="#10"
        >Whether the
        personal guarantee of the promoters of the borrower can be obtained for sanctioning credit
        facility?</a><br>
        </li>
      <li><a href="#11"
        >What is the
        guarantee cap available to the lender?</a><br>
        </li>
      <li><a href="#12"
        >After the sanction
        of credit when can the lender apply for the guarantee cover?</a><br>
        </li>
      <li><a href="#13"
        >When will the
        guarantee cover commence for the eligible credit facility?</a><br>
        </li>
      <li><a href="#14"
        >When can the lender
        invoke the guarantee given by the Trust in respect of credit facility advanced by it to
        the eligible borrower?</a><br>
        </li>
      <li><a href="#15"
        >How the claim of
        lender will be settled by the Trust in respect of defaulting account?</a><br>
        </li>
      <li><a href="#16"
        >What is meant by
        conclusion of recovery proceedings?</a><br>
        </li>
      <li><a href="#17"
        >Whether there is
        any ceiling in respect of interest to be levied on the credit facility advanced to the
        borrower which is being covered under the Scheme?</a><br>
        </li>
      <li><a href="#18"
        >Whether the lender
        can obtain guarantee cover in respect of credits sanctioned prior to the commencement of
        the Scheme?</a><br>
        </li>
      <li><a href="#19"
        >Whether guarantee
        cover will be available till the entire term loan and other credit facilities are repaid
        by the borrower?</a><br>
        </li>
      <li><a href="#20"
        >Whether all banks /
        lenders are automatically eligible for covering their credit facilities for guarantee
        under the Scheme?</a><br>
        </li>
      <li><a href="#21"
        >Whether the
        interest on term loan and other charges can also be guaranteed by the Trust?</a><br>
        </li>
      <li><a href="#22"
        >Whether the credit
        facility for rehabilitation / nursing of the sick unit can also be eligible for guarantee
        under the Scheme?</a><br>
        </li>
      <li><a href="#23"
        >Under what
        circumstances the guarantee cover obtained by the lender in respect of particular borrower
        will lapse?</a><br>
        </li>
      <li><a href="#24"
        >Whether the
        guarantee fee and annual service fee can be passed on to the borrower?</a><br>
        </li>
      <li><a href="#25"
        >Whether the
        guarantee fee and annual service fee are fixed once the guarantee cover is approved by the
        Trust?</a><br>
        </li>
      <li><a href="#26"
        >Whether the
        responsibility to recover the defaulted credit is taken over by the Trust after the
        settlement of claim in respect of particular borrower account?</a><br>
        </li>
      <li><a href="#27"
        >How is this
        guarantee scheme operated by the Trust?</a><br>
        </li>
      <li><a href="#28"
        >Will the Credit
        Guarantee Fund Trust will re-appraise the proposals sanctioned by the lenders?</a><br>
        </li>
      <li><a href="#29"
        >What is the date
        from which the Guarantee Scheme will be in operation?</a><br>
        </li>
      <li><a href="#30"
        >Where and how to
        apply for guarantee cover?</a><br>
        </li>
      <li><a href="#31"
        >Explain the B2B
        concept? Its relevance to the operations of the Credit Guarantee Fund Trust for Small
        Industries?</a><br>
        </li>
      <li><a href="#32"
        >What is the
        contact address of Credit Guarantee Trust?</a><br>
        </li>
      <li><a href="#33"
        >Can a foreign bank
        be eligible for guarantee cover?</a><br>
        </li>
      <li><a href="#34"
        >Whether the
        guarantee will continue to be available in respect of a particular borrower unit if there
        is change in management of that borrower during the period the guarantee is in force?</a>

      </li>
<li><small><a href="#35"
        >
What is the tenure of the cover for credit relating to working capital ?</a></small><br>
        <br>
      </li>


    </ol>
    <hr>
    <table height="219" width="100%" cellspacing="0" cellpadding="0">
      <tr>
        <td vAlign="top" width="100%" rowspan="2" height="215"><h3 style="color: rgb(0,0,0)"><small><font
        face="Verdana"><a name="1"><small>Who are the lenders eligible for availing guarantee
        under the Scheme?</small></a></font></small></h3>
        <p style="color: rgb(0,0,0)"><small><font face="Verdana">All Scheduled Commercial Banks
        (SCBs) and specified Regional Rural Banks are eligible to avail of guarantee facility. `</font></small></p>
        <h5 style="color: rgb(0,0,0)"><small><font face="Verdana"><a href="#top"
        style="color: rgb(128,0,0); text-decoration: underline">Back to Top</a></font></small></h5>
        <hr>
        <h3 style="color: rgb(0,0,0)"><small><font face="Verdana"><small><a name="2">Which type of
        borrowers can be covered under the Scheme?</a></small></font></small></h3>
        <p style="color: rgb(0,0,0)"><small><font face="Verdana">New and existing SSI units
        engaged in manufacturing activity or in information technology (IT) and software industry.</font></small></p>
        <h5 style="color: rgb(0,0,0)"><small><font face="Verdana"><a href="#top"
        style="color: rgb(128,0,0); text-decoration: underline">Back to Top</a></font></small></h5>
        <hr>
        <h3 style="color: rgb(0,0,0)"><small><font face="Verdana"><small><a name="3">What is
        quantum of credit facility which can be covered under the Scheme?</a></small></font></small></h3>
        <p style="color: rgb(0,0,0)"><small><font face="Verdana">Term loan and / or working
        capital facilities aggregating Rs.25 lakh extended by eligible lender to eligible borrower
        without any collateral security including third party guarantee can be covered under the
        guarantee scheme.</font></small></p>
        <h5 style="color: rgb(0,0,0)"><small><font face="Verdana"><a href="#top"
        style="color: rgb(128,0,0); text-decoration: underline">Back to Top</a></font></small></h5>
        <hr>
        <h3 style="color: rgb(0,0,0)"><small><font face="Verdana"><small><a name="4">What is the
        difference between primary security and collateral security</a></small></font></small></h3>
        <p style="color: rgb(0,0,0)"><small><font face="Verdana">Primary security is the asset
        created out of the credit facility extended to the borrower and / or which are directly
        associated with the business / project of the borrower for which the credit facility has
        been extended. Collateral security is any other security offered for the said loan. For
        example, hypothecation of jewellery, mortgage of house, etc.</font></small></p>
        <h5 style="color: rgb(0,0,0)"><small><font face="Verdana"><a href="#top"
        style="color: rgb(128,0,0); text-decoration: underline">Back to Top</a></font></small></h5>
        <hr>
        <h3 style="color: rgb(0,0,0)"><small><font face="Verdana"><a name="5"><small>Can term loan
        or working capital facility alone be extended by an eligible lender and still be covered
        under the</small> <small>guarantee scheme?</small> </a></font></small></h3>
        <p style="color: rgb(0,0,0)"><small><font face="Verdana">Yes, a lender can extend either
        term loan or working capital facility alone and still be eligible for a guarantee cover if
        it meets the other eligibility parameters. Needless to say, the total credit facility
        extended to a borrower shall not exceed Rs.25 lakhs, and should be made available without
        any collateral security.<br>
        It would be preferable if a single lender extends both term loan and working capital
        facilities to the borrower. The idea is to help the small borrower. He should not be made
        to run from one agency to another for different facilities. The Trust shall advocate for
        providing composite loan facility to the eligible borrower by the lending institution. </font></small></p>
        <h5 style="color: rgb(0,0,0)"><small><font face="Verdana"><a href="#top"
        style="color: rgb(128,0,0); text-decoration: underline">Back to Top</a></font></small></h5>
        <hr>
        <h3 style="color: rgb(0,0,0)"><small><font face="Verdana"><small><a name="6">Can a credit
        facility extended to a borrower against a collateral security be covered under the
        Guarantee Scheme, if the lending institution relinquishes its rights on the collateral
        security.</a></small></font></small></h3>
        <p style="color: rgb(0,0,0)"><small><font face="Verdana">Yes, provided the lending
        institution relinquishes its rights on the assets and releases the same in favour of the
        borrower.<br>
        </font></small></p>
        <h5 style="color: rgb(0,0,0)"><small><font face="Verdana"><a href="#top"
        style="color: rgb(128,0,0); text-decoration: underline">Back to Top</a></font></small></h5>
        <hr>
        <h3 style="color: rgb(0,0,0)"><small><font face="Verdana"><small>I<a name="7">f aggregate
        credit facility (both term loan and working capital) exceeds Rs.25 lakh, can it still be
        covered under the Scheme?</a></small></font></small></h3>
        <p style="color: rgb(0,0,0)"><small><font face="Verdana">While normally loans extended up
        to Rs.25 lakh would be covered by the guarantee scheme, there could be occasion where
        additional loan / working capital facilities are required to be extended to the already
        assisted borrower. The eligible lender may sanction term loan and working capital facility
        exceeding Rs.25 lakh without obtaining collateral (including third party guarantee) and up
        to Rs.15 lakh, however, guarantee cover will be available only for credit facility upto
        Rs.25 lakh.</font></small></p>
        <h5 style="color: rgb(0,0,0)"><small><font face="Verdana"><a href="#top"
        style="color: rgb(128,0,0); text-decoration: underline">Back to Top</a></font></small></h5>
        <hr>
        <h3 style="color: rgb(0,0,0)"><small><font face="Verdana"><small><a name="8">Whether the
        lender can sanction term loan and / or working capital facilities of say Rs.100 lakh and
        can obtain guarantee cover for eligible amount of Rs.25 lakh?</a><br>
        </small></font></small></h3>
        <p style="color: rgb(0,0,0)"><small><font face="Verdana">No. The guarantee facility is
        mainly meant for the borrowers seeking loans generally not exceeding Rs.25 lakh. As stated
        earlier, maximum loan facility that could be extended is Rs.15 lakh, though for the
        purpose of guarantee cover, the cap is Rs.25 lakh.</font></small></p>
        <h5 style="color: rgb(0,0,0)"><small><font face="Verdana"><a href="#top"
        style="color: rgb(128,0,0); text-decoration: underline">Back to Top</a></font></small></h5>
        <hr>
        <h3 style="color: rgb(0,0,0)"><small><font face="Verdana"><small><a name="9">Whether
        borrowers from service sector enterprises are eligible under the Scheme</a></small></font></small></h3>
        <p style="color: rgb(0,0,0)"><small><font face="Verdana">No. However, borrowers from
        information technology and software industry are eligible for the guarantee cover subject
        to meeting of other eligible norms / criteria.</font></small></p>
        <h5 style="color: rgb(0,0,0)"><small><font face="Verdana"><a href="#top"
        style="color: rgb(128,0,0); text-decoration: underline">Back to Top</a></font></small></h5>
        <hr>
        <h3 style="color: rgb(0,0,0)"><small><font face="Verdana"><small><a name="10">Whether the
        personal guarantee of the promoters of the borrower can be obtained for sanctioning credit
        facility?</a></small></font></small></h3>
        <p style="color: rgb(0,0,0)"><small><font face="Verdana">Yes. However, if the third party
        guarantee is obtained for sanctioning credit facility then such credit cannot be covered
        under the Scheme.<br>
        </font></small></p>
        <h5 style="color: rgb(0,0,0)"><small><font face="Verdana"><a href="#top"
        style="color: rgb(128,0,0); text-decoration: underline">Back to Top</a></font></small></h5>
        <hr>
        <h3 style="color: rgb(0,0,0)"><small><font face="Verdana"><small><a name="11">What is the
        guarantee cap available to the lender?</a></small></font></small></h3>
        <p style="color: rgb(0,0,0)"><small><font face="Verdana">Trust can guarantee maximum
        amount of Rs.18.75 lakh of eligible credit per borrower i.e. 75% of maximum eligible credit
        facility of Rs.25 lakh.</font></small></p>
        <h5 style="color: rgb(0,0,0)"><small><font face="Verdana"><a href="#top"
        style="color: rgb(128,0,0); text-decoration: underline">Back to Top</a></font></small></h5>
        <hr>
        <h3 style="color: rgb(0,0,0)"><small><font face="Verdana"><small><a name="12">After the
        sanction of credit when can the lender apply for the guarantee cover?</a></small></font></small></h3>
        <p style="color: rgb(0,0,0)"><small><font face="Verdana">The eligible lender can apply for
        guarantee cover immediately after sanction of credit facility to the eligible borrower but
        in any case not later than 90 days from the date of sanction of credit facility.</font></small></p>
        <h5 style="color: rgb(0,0,0)"><small><font face="Verdana"><a href="#top"
        style="color: rgb(128,0,0); text-decoration: underline">Back to Top</a></font></small></h5>
        <hr>
        <h3 style="color: rgb(0,0,0)"><small><font face="Verdana"><small><a name="13">When will
        the guarantee cover commence for the eligible credit facility?</a></small></font></small></h3>
        <p style="color: rgb(0,0,0)"><small><font face="Verdana">The guarantee cover will commence
        from the date of payment of guarantee fee of 2.5% of the amount of credit facility
        sanctioned by the lender. Since the routing of applications by a lender to the Trust is
        done on an electronic mode, the response from the Trust would be within 24 hours.<br>
        </font></small></p>
        <h5 style="color: rgb(0,0,0)"><small><font face="Verdana"><a href="#top"
        style="color: rgb(128,0,0); text-decoration: underline">Back to Top</a></font></small></h5>
        <hr>
        <h3 style="color: rgb(0,0,0)"><small><font face="Verdana"><small><a name="14">When can the
        lender invoke the guarantee given by the Trust in respect of credit facility advanced by
        it to the eligible borrower?</a><br>
        </small></font></small></h3>
        <p style="color: rgb(0,0,0)"><small><font face="Verdana">The lender can invoke the
        guarantee given by the Trust only after the lock-in period of 24 months either from the
        date of last disbursement of credit to the borrower or from the date of the guarantee
        cover coming into force in respect of the particular credit facility, whichever is later.
        The lender shall, however, prefer a claim on the defaulted account, which has become NPA,
        immediately after recall of loan and initiation of recovery proceedings by filing a suit
        in the Civil Court.</font></small></p>
        <h5 style="color: rgb(0,0,0)"><small><font face="Verdana"><a href="#top"
        style="color: rgb(128,0,0); text-decoration: underline">Back to Top</a></font></small></h5>
        <hr>
        <h3 style="color: rgb(0,0,0)"><small><font face="Verdana"><small><a name="15">How the
        claim of lender will be settled by the Trust in respect of defaulting account?</a></small></font></small></h3>
        <p style="color: rgb(0,0,0)"><small><font face="Verdana">After satisfying itself about the
        procedural aspects met by the lender, regarding lodgement / preferment of claim for
        guarantee, the Trust will honour 75% of the guaranteed portion of the principal
        outstanding in default, subject to maximum of 75% of the guaranteed cap amount. The
        balance 25% shall be paid on conclusion of the recovery proceedings.<br>
        </font></small></p>
        <h5 style="color: rgb(0,0,0)"><small><font face="Verdana"><a href="#top"
        style="color: rgb(128,0,0); text-decoration: underline">Back to Top</a></font></small></h5>
        <hr>
        <h3 style="color: rgb(0,0,0)"><small><font face="Verdana"><small><a name="16">What is
        meant by conclusion of recovery proceedings?</a></small></font></small></h3>
        <p style="color: rgb(0,0,0)"><small><font face="Verdana">The recovery proceedings would be
        stated to have been concluded after the decree for recovery has been passed by a Court of
        Law.<br>
        <br>
        </font></small></p>
        <h5 style="color: rgb(0,0,0)"><small><font face="Verdana"><a href="#top"
        style="color: rgb(128,0,0); text-decoration: underline">Back to Top</a></font></small></h5>
        <hr>
        <p style="color: rgb(0,0,0)"><small><font face="Verdana"><strong><a name="17">Whether
        there is any ceiling in respect of interest to be levied on the credit facility advanced
        to the borrower which is being covered under the Scheme?</a></strong></font></small></p>
        <p style="color: rgb(0,0,0)"><small><font face="Verdana">The lender has to follow the
        guidelines issued by RBI regarding charging of interest on the credit. However, the
        interest shall not exceed 3% over and above Primary Lending Rate (PLR) of the lender,
        excluding the annual service fee payable to the Trust for covering the credit under the
        Scheme.<br>
        </font></small></p>
        <h5 style="color: rgb(0,0,0)"><small><font face="Verdana"><a href="#top"
        style="color: rgb(128,0,0); text-decoration: underline">Back to Top</a></font></small></h5>
        <hr>
        <h3 style="color: rgb(0,0,0)"><small><font face="Verdana"><small><a name="18">Whether the
        lender can obtain guarantee cover in respect of credits sanctioned prior to the
        commencement of the Scheme?</a></small></font></small></h3>
        <p style="color: rgb(0,0,0)"><small><font face="Verdana">The eligible credit facility
        (both term loan and working capital facility) with a cap of Rs.25 lakh extended by the
        eligible lender on or after June 01, 2000 can be covered under the Scheme.<br>
        </font></small></p>
        <h5 style="color: rgb(0,0,0)"><small><font face="Verdana"><a href="#top"
        style="color: rgb(128,0,0); text-decoration: underline">Back to Top</a></font></small></h5>
        <hr>
        <h3 style="color: rgb(0,0,0)"><small><font face="Verdana"><small><a name="19">Whether
        guarantee cover will be available till the entire term loan and other credit facilities
        are repaid by the borrower?</a></small></font></small></h3>
        <p style="color: rgb(0,0,0)"><small><font face="Verdana">Guarantee will commence from the
        date of payment of guarantee fee and shall run through the agreed tenure of the term loan
        / composite loans. Where working capital facilities alone are extended to eligible
        borrowers, it would be for a period of 5 years or for such period as may be specified by
        the Trust in this behalf.</font></small></p>
        <h5 style="color: rgb(0,0,0)"><small><font face="Verdana"><a href="#top"
        style="color: rgb(128,0,0); text-decoration: underline">Back to Top</a></font></small></h5>
        <hr>
        <h3 style="color: rgb(0,0,0)"><small><font face="Verdana"><small><a name="20">Whether all
        banks / lenders are automatically eligible for covering their credit facilities for
        guarantee under the Scheme?</a></small></font></small></h3>
        <p style="color: rgb(0,0,0)"><small><font face="Verdana">Each Scheduled Commercial Banks
        (SCBs) and specified Regional Rural Banks (RRBs) after entering into an agreement with the
        Guarantee Trust for the purpose can apply for guarantee cover in respect of their eligible
        credit facility sanctioned to the eligible borrower.</font></small></p>
        <h5 style="color: rgb(0,0,0)"><small><font face="Verdana"><a href="#top"
        style="color: rgb(128,0,0); text-decoration: underline">Back to Top</a></font></small></h5>
        <hr>
        <h3 style="color: rgb(0,0,0)"><small><font face="Verdana"><small><a name="21">Whether the
        interest on term loan and other charges can also be guaranteed by the Trust?</a></small></font></small></h3>
        <p style="color: rgb(0,0,0)"><small><font face="Verdana">In case of default by the
        borrower, the items of credit facility which are eligible are :-<br>
        &nbsp;&nbsp;&nbsp; (i)&nbsp;&nbsp;&nbsp; Term loan
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp; - &nbsp;&nbsp;&nbsp;
        Defaulted principal amount<br>
        &nbsp;&nbsp;&nbsp; (ii)&nbsp;&nbsp;&nbsp; Working capital facility&nbsp;&nbsp;&nbsp;
	&nbsp;&nbsp;&nbsp; -&nbsp;&nbsp;&nbsp; O/s working capital advance (inclusive of interest)<br>
        <br>
        &nbsp;&nbsp;&nbsp; Other charges such as interest on principal amount of term loan, penal
        interest, commitment charge, service charge or any other levies expenses shall not qualify
        for the guarantee cover.</font></small></p>
        <h5 style="color: rgb(0,0,0)"><small><font face="Verdana"><a href="#top"
        style="color: rgb(128,0,0); text-decoration: underline">Back to Top</a></font></small></h5>
        <hr>
        <h3 style="color: rgb(0,0,0)"><small><font face="Verdana"><small><a name="22">Whether the
        credit facility for rehabilitation / nursing of the sick unit can also be eligible for
        guarantee under the Scheme?</a></small></font></small></h3>
        <p style="color: rgb(0,0,0)"><small><font face="Verdana">The eligible borrower unit which
        has been covered under the Scheme and subsequently becomes sick due to factors beyond the
        control of the management, the assistance / credit for rehabilitation extended by the
        lender could also be covered under the Scheme provided the overall assistance is within
        the credit cap of Rs.25 lakh, for such extended period of guarantee and on such terms as
        may be decided by the Trust.<br>
        </font></small></p>
        <h5 style="color: rgb(0,0,0)"><small><font face="Verdana"><a href="#top"
        style="color: rgb(128,0,0); text-decoration: underline">Back to Top</a></font></small></h5>
        <hr>
        <h3 style="color: rgb(0,0,0)"><small><font face="Verdana"><small><a name="23">Under what
        circumstances the guarantee cover obtained by the lender in respect of particular borrower
        will lapse?</a></small></font></small></h3>
        <p style="color: rgb(0,0,0)"><small><font face="Verdana">The guarantee cover given by the
        Trust to the lender in respect of credit facility to a particular borrower will lapse if
	<br>
        (i) it is subsequently brought to the knowledge of the Trust that the lender has obtained
        collateral / third party guarantee from the borrower while sanctioning the particular
        credit facility which has been covered under the guarantee,
	<br>
	(ii) it is subsequently gathered that the lender has advanced second / subsequent credit facility to the borrower
        with collateral / third party guarantee and extended the scope of collateral / third party
        guarantee to the existing credit facility for which guarantee cover has been obtained from
        the Trust,
	<br>
	(iii) annual service charge is not paid to the Trust by the specified period or
        such extended time limit as may be granted by the Trust,
	<br>
	(iv) there is change in the management of the borrower which has not been brought to the notice of the Trust and
	<br>
	(v)  the borrower unit undertakes another activity which is otherwise not eligible for
        obtaining the guarantee cover from the Trust.</font></small></p>
        <h5 style="color: rgb(0,0,0)"><small><font face="Verdana"><a href="#top"
        style="color: rgb(128,0,0); text-decoration: underline">Back to Top</a></font></small></h5>
        <hr>
        <h3 style="color: rgb(0,0,0)"><small><font face="Verdana"><small><a name="24">Whether the
        guarantee fee and annual service fee can be passed on to the borrower?</a><br>
        </small></font></small></h3>
        <p style="color: rgb(0,0,0)"><small><font face="Verdana">Yes.</font></small></p>
        <h5 style="color: rgb(0,0,0)"><small><font face="Verdana"><a href="#top"
        style="color: rgb(128,0,0); text-decoration: underline">Back to Top</a></font></small></h5>
        <hr>
        <h3 style="color: rgb(0,0,0)"><small><font face="Verdana"><small><a name="25">Whether the
        guarantee fee and annual service fee are fixed once the guarantee cover is approved by the
        Trust?</a></small></font></small></h3>
        <p style="color: rgb(0,0,0)"><small><font face="Verdana">Since the guarantee fee is
        one-time which has to be paid upfront it is almost fixed for that particular credit
        facility. <br>
        As regards annual service fee, payable on the outstanding credit facilities as on March
        31, the prevailing rate at that time will apply.</font></small></p>
        <h5 style="color: rgb(0,0,0)"><small><font face="Verdana"><a href="#top"
        style="color: rgb(128,0,0); text-decoration: underline">Back to Top</a></font></small></h5>
        <hr>
        <h3 style="color: rgb(0,0,0)"><small><font face="Verdana"><a name="26"><small>Whether the
        responsibility to recover the defaulted credit is taken over by the Trust after the
        settlement of claim in respect of particular borrower account?</small><br>
        </a></font></small></h3>
        <p style="color: rgb(0,0,0)"><small><font face="Verdana">No. The lender is responsible to
        take efforts in recovery of credit advanced to the borrower who had defaulted, including
        filing of civil suits, invoking of personal guarantee of the promoter, etc. </font></small></p>
        <h5 style="color: rgb(0,0,0)"><small><font face="Verdana"><a href="#top"
        style="color: rgb(128,0,0); text-decoration: underline">Back to Top</a></font></small></h5>
        <hr>
        <h3 style="color: rgb(0,0,0)"><small><font face="Verdana"><small><a name="27">How is this
        guarantee scheme operated by the Trust?</a></small></font></small></h3>
        <p style="color: rgb(0,0,0)"><small><font face="Verdana">The operations of this Scheme are
        fully computerised using B2B e-business concept to enable the Trust to deliver prompt
        service to the lenders.</font></small></p>
        <h5 style="color: rgb(0,0,0)"><small><font face="Verdana"><a href="#top"
        style="color: rgb(128,0,0); text-decoration: underline">Back to Top</a></font></small></h5>
        <hr>
        <h3 style="color: rgb(0,0,0)"><small><font face="Verdana"><small><a name="28">Will the
        Emergency Credit Guarantee Fund Trust will re-appraise the proposals sanctioned by the lenders?</a></small></font></small></h3>
        <p style="color: rgb(0,0,0)"><small><font face="Verdana">No. Lenders are required to use
        their commercial discretion and due diligence in ensuring financial viability of the
        proposal of the borrower prior to sanctioning of the loan..</font></small></p>
        <h5 style="color: rgb(0,0,0)"><small><font face="Verdana"><a href="#top"
        style="color: rgb(128,0,0); text-decoration: underline">Back to Top</a></font></small></h5>
        <hr>
        <h3 style="color: rgb(0,0,0)"><small><font face="Verdana"><a name="29"><small>What is the
        date from which the Guarantee Scheme will be in operation?</small><br>
        </a></font></small></h3>
        <p style="color: rgb(0,0,0)"><small><font face="Verdana">The Guarantee Scheme is made
        operational from August 01, 2000. Lender can apply for guarantee cover in respect of
        borrowal accounts sanctioned and disbursed on or after June 1, 2000.</font></small></p>
        <h5 style="color: rgb(0,0,0)"><small><font face="Verdana"><a href="#top"
        style="color: rgb(128,0,0); text-decoration: underline">Back to Top</a></font></small></h5>
        <hr>
        <h3 style="color: rgb(0,0,0)"><small><font face="Verdana"><small><a name="30">Where and
        how to apply for guarantee cover?</a></small></font></small></h3>
        <p style="color: rgb(0,0,0)"><small><font face="Verdana">The eligible lending banks
        desirous of obtaining guarantee cover in respect of the eligible credit facility
        sanctioned / to be sanctioned to the eligible borrowers may approach the Emergency Credit Guarantee
        Fund Trust for Small Industries, Nariman Bhavan, 14th Floor, 227, Vinay K. Shah Marg,
        Nariman Point, Mumbai 400021 for the 'one time' procedural formalities to be complied
        viz., execution of formal agreement. Once the agreement is executed, the Trust will allow
        the bank concerned to obtain guarantee cover for all its credit facility extended to
        eligible borrowers.</font></small></p>
        <h5 style="color: rgb(0,0,0)"><small><font face="Verdana"><a href="#top"
        style="color: rgb(128,0,0); text-decoration: underline">Back to Top</a></font></small></h5>
        <hr>
        <h3 style="color: rgb(0,0,0)"><small><font face="Verdana"><small><a name="31">Explain the
        B2B concept? Its relevance to the operations of the Emergency Credit Guarantee Fund Trust for Small
        Industries?</a></small></font></small></h3>
        <p style="color: rgb(0,0,0)"><small><font face="Verdana">B2B concept is business to
        business operations conducted through Internet between two or more business partners. The
        participating lending institutions would be allotted a member-id which shall be used for
        uploading the specific details of sanctions effected to the small industries by the
        lending institutions, get our guarantee cover, and lodge claims. Also status of the
        applications, claims, etc. and various reports required by the lending institutions shall
        be available to them at a click of a button.</font></small></p>
        <h5 style="color: rgb(0,0,0)"><small><font face="Verdana"><a href="#top"
        style="color: rgb(128,0,0); text-decoration: underline">Back to Top</a></font></small></h5>
        <hr>
        <h3 style="color: rgb(0,0,0)"><small><font face="Verdana"><a name="32"><small>What is the
        contact address of Emergency Credit Guarantee Trust?</small><br>
        </a></font></small></h3>
        <p style="color: rgb(0,0,0)"><small><font face="Verdana">The Head / Registered Office of
        the Emergency Credit Guarantee Trust -<br>
        &nbsp;&nbsp;&nbsp; The Chief Executive Officer<br>
        &nbsp;&nbsp;&nbsp; Emergency Credit Guarantee Fund Trust for Small Industries<br>
        &nbsp;&nbsp;&nbsp; Nariman Bhavan (14th Floor)<br>
        &nbsp;&nbsp;&nbsp; 227, Vinay K. Shah Marg<br>
        &nbsp;&nbsp;&nbsp; Nariman Point<br>
        &nbsp;&nbsp;&nbsp; Mumbai - 400 021<br>
        &nbsp;&nbsp;&nbsp; Tel No.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; : &nbsp;&nbsp;&nbsp;
        2042753<br>
        &nbsp;&nbsp;&nbsp; Fax No.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; : &nbsp;&nbsp;&nbsp;
        2044448</font></small></p>
        <h5 style="color: rgb(0,0,0)"><small><font face="Verdana"><a href="#top"
        style="color: rgb(128,0,0); text-decoration: underline">Back to Top</a></font></small></h5>
        <hr>
        <h3 style="color: rgb(0,0,0)"><small><font face="Verdana"><small><a name="33">Can a
        foreign bank be eligible for guarantee cover?</a></small><br>
        </font></small></h3>
        <p style="color: rgb(0,0,0)"><small><font face="Verdana">Yes, provided it is a commercial
        bank listed in the II Schedule to the Reserve Bank of India, 1949.</font></small></p>
        <h5 style="color: rgb(0,0,0)"><small><font face="Verdana"><a href="#top"
        style="color: rgb(128,0,0); text-decoration: underline">Back to Top</a></font></small></h5>
        <hr>
        <h3 style="color: rgb(0,0,0)"><small><font face="Verdana"><small><a name="34">Whether the
        guarantee will continue to be available in respect of a particular borrower unit if there
        is change in management of that borrower during the period the guarantee is in force?</a></small></font></small></h3>
        <p style="color: rgb(0,0,0)"><small><font face="Verdana">If the new promoters / management
        meets / satisfy the norms of the eligible borrower viz. maximum credit availed and
        outstanding, SSI status etc., and continues to perform the existing activities of borrower
        or undertakes the new activities which otherwise are eligible under the Scheme for
        guarantee then the lender can continue such borrower with existing liabilities under the
        scheme of guarantee. However, if the new promoter / management do not satisfy any of the
        norms of the Scheme, the guarantee in respect of the credit facility shall be deemed to be
        terminated from the date of said transfer or assignment.<br>
        </font></small></p>
        <h3 style="color: rgb(0,0,0)"><small><font face="Verdana"><small><a name="35">What is the tenure of the cover for credit relating to working capital ?
	</a></small></font></small></h3>
        <p style="color: rgb(0,0,0)"><small><font face="Verdana">The tenure for coverage of working capital facilities is 5 years, where working capital alone is covered under the scheme.  In case term credit and working capital both are covered under the scheme, the tenure relating to working capital facility would match the normal repayment period of term credit.  The reason for keeping a limit of 5 years wherever working capital alone are covered are that the period for which the same are extended by the lending institutions are not time bound.  The same are reviewed periodically for increase/ decrease in the limit sanctioned, and are expected to continue for a time frame much longer than 5 years.  CGTSI welcomes any renewal of guarantee cover beyond 5 years on a payment of commission @ 2.5% on the sanctioned limit prevailing at that time.  <br>
        </font></small></p>


        <h5 style="color: rgb(0,0,0)"><small><font face="Verdana"><a href="#top"
        style="color: rgb(128,0,0); text-decoration: underline">Back to Top</a></font></small></h5>
</font></small></h5>
        </td>-->
      </tr>
    </table>
    </td>
  </tr>
</table>
<p><br>


	</form>
	</BODY>
</HTML>
