					<TR>
						<TD colspan="4" class="SubHeading">
							<DIV align="left">
								<BR>
								<bean:message key="privilegeDetails" />
							</DIV>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="select_all" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
						<%
						int counter1=((Integer)(request.getAttribute("counter"))).intValue();
						String action="selectDeselect(this,"+counter1+")";
						%>
							<html:checkbox property="privileges(select_all)" name="adminActionForm" value="select_all" onclick="<%=action%>"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="receive_success_failure_message" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(RECEIVE_SUCCESS_FAILURE_MESSAGE)" name="adminActionForm" value="RECEIVE_SUCCESS_FAILURE_MESSAGE"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="add_application" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(ADD_APPLICATION)" name="adminActionForm" value="ADD_APPLICATION"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="modify_application" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(MODIFY_APPLICATION)" name="adminActionForm" value="MODIFY_APPLICATION"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="approve_ineligible_application" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(APPROVE_INELIGIBLE_APPLICATION)" name="adminActionForm" value="APPROVE_INELIGIBLE_APPLICATION"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="approve_duplicate_application" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(APPROVE_DUPLICATE_APPLICATION)" name="adminActionForm" value="APPROVE_DUPLICATE_APPLICATION"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="application_approval" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(APPLICATION_APPROVAL)" name="adminActionForm" value="APPLICATION_APPROVAL"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="application_re_approval" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(APPLICATION_RE_APPROVAL)" name="adminActionForm" value="APPLICATION_RE_APPROVAL"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="file_upload" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(FILE_UPLOAD)" name="adminActionForm" value="FILE_UPLOAD"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="modify_borrower_details" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(MODIFY_BORROWER_DETAILS)" name="adminActionForm" value="MODIFY_BORROWER_DETAILS"/>
						</TD>
					</TR>

					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="periodic_info" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(PERIODIC_INFO)" name="adminActionForm" value="PERIODIC_INFO"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="update_repayment_schedule" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(UPDATE_REPAYMENT_SCHEDULE)" name="adminActionForm" value="UPDATE_REPAYMENT_SCHEDULE"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="shift_cgpan_or_borrower" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(SHIFT_CGPAN_OR_BORROWER)" name="adminActionForm" value="SHIFT_CGPAN_OR_BORROWER"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="export_periodic_info" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(EXPORT_PERIDIC_INFO)" name="adminActionForm" value="EXPORT_PERIDIC_INFO"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="application_closure" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(APPLICATION_CLOSURE)" name="adminActionForm" value="APPLICATION_CLOSURE"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="allocate_payments" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(ALLOCATE_PAYMENTS)" name="adminActionForm" value="ALLOCATE_PAYMENTS"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="view_pending_payments" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(VIEW_PENDING_PAYMENTS)" name="adminActionForm" value="VIEW_PENDING_PAYMENTS"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="appropriate_payments" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(APPROPRIATE_PAYMENTS)" name="adminActionForm" value="APPROPRIATE_PAYMENTS"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="re_allocate_payments" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(RE_ALLOCATE_PAYMENTS)" name="adminActionForm" value="RE_ALLOCATE_PAYMENTS"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="generate_short_dan" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(GENERATE_SHORT_DAN)" name="adminActionForm" value="GENERATE_SHORT_DAN"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="generate_cgdan" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(GENERATE_CGDAN)" name="adminActionForm" value="GENERATE_CGDAN"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="generate_sfdan" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(GENERATE_SFDAN)" name="adminActionForm" value="GENERATE_SFDAN"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="generate_cldan" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(GENERATE_CLDAN)" name="adminActionForm" value="GENERATE_CLDAN"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="generate_excess_voucher" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(GENERATE_EXCESS_VOUCHER)" name="adminActionForm" value="GENERATE_EXCESS_VOUCHER"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="generate_voucher" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(GENERATE_VOUCHER)" name="adminActionForm" value="GENERATE_VOUCHER"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="waive_short_amounts" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(WAIVE_SHORT_AMOUNTS)" name="adminActionForm" value="WAIVE_SHORT_AMOUNTS"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="print_pay_in_slip" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(PRINT_PAY_IN_SLIP)" name="adminActionForm" value="PRINT_PAY_IN_SLIP"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="claim_first_installment" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(CLAIM_FIRST_INSTALLMENT)" name="adminActionForm" value="CLAIM_FIRST_INSTALLMENT"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="claim_second_installment" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(CLAIM_SECOND_INSTALLMENT)" name="adminActionForm" value="CLAIM_SECOND_INSTALLMENT"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="ots_request" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(OTS_REQUEST)" name="adminActionForm" value="OTS_REQUEST"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="ots_approval" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(OTS_APPROVAL)" name="adminActionForm" value="OTS_APPROVAL"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="claim_approval" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(CLAIM_APPROVAL)" name="adminActionForm" value="CLAIM_APPROVAL"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="export_claim_file" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(EXPORT_CLAIM_FILE)" name="adminActionForm" value="EXPORT_CLAIM_FILE"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="settlement" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(SETTLEMENT)" name="adminActionForm" value="SETTLEMENT"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="limit_set_up" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(LIMIT_SET_UP)" name="adminActionForm" value="LIMIT_SET_UP"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="risk_calculate_exposuer" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(RISK_CALCULATE_EXPOSUER)" name="adminActionForm" value="RISK_CALCULATE_EXPOSUER"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="generate_exposure_report" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(GENERATE_EXPOSURE_REPORT)" name="adminActionForm" value="GENERATE_EXPOSURE_REPORT"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="set_update_sub_scheme_params" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(SET_UPDATE_SUB_SCHEME_PARAMS)" name="adminActionForm" value="SET_UPDATE_SUB_SCHEME_PARAMS"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="enter_budget_details" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(ENTER_BUDGET_DETAILS)" name="adminActionForm" value="ENTER_BUDGET_DETAILS"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="enter_inflow_details" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(ENTER_INFLOW_DETAILS)" name="adminActionForm" value="ENTER_INFLOW_DETAILS"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="enter_outflow_details" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(ENTER_OUTFLOW_DETAILS)" name="adminActionForm" value="ENTER_OUTFLOW_DETAILS"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="set_ceiling_for_investment" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(SET_CEILING_FOR_INVESTMENT)" name="adminActionForm" value="SET_CEILING_FOR_INVESTMENT"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="enter_investment_details" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(ENTER_INVESTMENT_DETAILS)" name="adminActionForm" value="ENTER_INVESTMENT_DETAILS"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="if_calculate_exposuer" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(IF_CALCULATE_EXPOSUER)" name="adminActionForm" value="IF_CALCULATE_EXPOSUER"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="investment_fulfillment" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(INVESTMENT_FULFILLMENT)" name="adminActionForm" value="INVESTMENT_FULFILLMENT"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="if_update_master_tables" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(IF_UPDATE_MASTER_TABLES)" name="adminActionForm" value="IF_UPDATE_MASTER_TABLES"/>
						</TD>
					</TR>

					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="make_request" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(MAKE_REQUEST)" name="adminActionForm" value="MAKE_REQUEST"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="tds_details" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(TDS_DETAILS)" name="adminActionForm" value="TDS_DETAILS"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="forecasting_details" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(FORECASTING_DETAILS)" name="adminActionForm" value="FORECASTING_DETAILS"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="plan_investment" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(PLAN_INVESTMENT)" name="adminActionForm" value="PLAN_INVESTMENT"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="project_expected_claims" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(PROJECT_EXPECTED_CLAIMS)" name="adminActionForm" value="PROJECT_EXPECTED_CLAIMS"/>
						</TD>
					</TR>

					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="add_inward" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(ADD_INWARD)" name="adminActionForm" value="ADD_INWARD"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="add_outward" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(ADD_OUTWARD)" name="adminActionForm" value="ADD_OUTWARD"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="upload_documents" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(UPLOAD_DOCUMENTS)" name="adminActionForm" value="UPLOAD_DOCUMENTS"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="search_documents" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(SEARCH_DOCUMENTS)" name="adminActionForm" value="SEARCH_DOCUMENTS"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="register_collecting_bank" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(REGISTER_COLLECTING_BANK)" name="adminActionForm" value="REGISTER_COLLECTING_BANK"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="modify_collecting_bank_details" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(MODIFY_COLLECTING_BANK_DETAILS)" name="adminActionForm" value="MODIFY_COLLECTING_BANK_DETAILS"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="register_mli" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(REGISTER_MLI)" name="adminActionForm" value="REGISTER_MLI"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="assign_collecting_bank" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(ASSIGN_COLLECTING_BANK)" name="adminActionForm" value="ASSIGN_COLLECTING_BANK"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="modify_collecting_bank" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(MODIFY_COLLECTING_BANK)" name="adminActionForm" value="MODIFY_COLLECTING_BANK"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="define_organization_structure" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(DEFINE_ORGANIZATION_STRUCTURE)" name="adminActionForm" value="DEFINE_ORGANIZATION_STRUCTURE"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="deactivate_member" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(DEACTIVATE_MEMBER)" name="adminActionForm" value="DEACTIVATE_MEMBER"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="activate_member" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(ACTIVATE_MEMBER)" name="adminActionForm" value="ACTIVATE_MEMBER"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="add_modify_roles" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(ADD_MODIFY_ROLES)" name="adminActionForm" value="ADD_MODIFY_ROLES"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="create_cgtsi_user" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(CREATE_CGTSI_USER)" name="adminActionForm" value="CREATE_CGTSI_USER"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="create_mli_user" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(CREATE_MLI_USER)" name="adminActionForm" value="CREATE_MLI_USER"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="modify_user_details" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(MODIFY_USER_DETAILS)" name="adminActionForm" value="MODIFY_USER_DETAILS"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="deactivate_user" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(DEACTIVATE_USER)" name="adminActionForm" value="DEACTIVATE_USER"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="reactivate_user" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(REACTIVATE_USER)" name="adminActionForm" value="REACTIVATE_USER"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="assign_modify_roles_and_privileges" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(ASSIGN_MODIFY_ROLES_AND_PRIVILEGES)" name="adminActionForm" value="ASSIGN_MODIFY_ROLES_AND_PRIVILEGES"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="reset_password" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(RESET_PASSWORD)" name="adminActionForm" value="RESET_PASSWORD"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="enter_audit_details" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(ENTER_AUDIT_DETAILS)" name="adminActionForm" value="ENTER_AUDIT_DETAILS"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="review_audit_details" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(REVIEW_AUDIT_DETAILS)" name="adminActionForm" value="REVIEW_AUDIT_DETAILS"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="broadcast_message" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(BROADCAST_MESSAGE)" name="adminActionForm" value="BROADCAST_MESSAGE"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="update_master_table" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(UPDATE_MASTER_TABLE)" name="adminActionForm" value="UPDATE_MASTER_TABLE"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="reports" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(REPORTS)" name="adminActionForm" value="REPORTS"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="securitization" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(SECURITIZATION)" name="adminActionForm" value="SECURITIZATION"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="register_mcgf" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(REGISTER_MCGF)" name="adminActionForm" value="REGISTER_MCGF"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="add_participating_banks" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(ADD_PARTICIPATING_BANKS)" name="adminActionForm" value="ADD_PARTICIPATING_BANKS"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="add_ssi_members" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(ADD_SSI_MEMBERS)" name="adminActionForm" value="ADD_SSI_MEMBERS"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="add_donor_details" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(ADD_DONOR_DETAILS)" name="adminActionForm" value="ADD_DONOR_DETAILS"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="set_participating_bank_limit" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(SET_PARTICIPATING_BANK_LIMIT)" name="adminActionForm" value="SET_PARTICIPATING_BANK_LIMIT"/>
						</TD>
					</TR>
						<%--
						Added on 8th March 2004.
						--%>

					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="app_status_wise_report" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(APP_STATUS_WISE_REPORT)" name="adminActionForm" value="APP_STATUS_WISE_REPORT"/>
						</TD>
					</TR>



					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="periodic_info_approval" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(PERIODIC_INFO_APPROVAL)" name="adminActionForm" value="PERIODIC_INFO_APPROVAL"/>
						</TD>
					</TR>

					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="day_end_process" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(DAY_END_PROCESS)" name="adminActionForm" value="DAY_END_PROCESS"/>
						</TD>
					</TR>

					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="statement_detail" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(STATEMENT_DETAILS)" name="adminActionForm" value="STATEMENT_DETAILS"/>
						</TD>
					</TR>

					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="if_reports" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(IF_REPORT)" name="adminActionForm" value="IF_REPORT"/>
						</TD>
					</TR>

					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="app_special_message" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(APP_SPECIAL_MESSAGE)" name="adminActionForm" value="APP_SPECIAL_MESSAGE"/>
						</TD>
					</TR>

					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="update_recovery" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="privileges(UPDATE_RECOVERY)" name="adminActionForm" value="UPDATE_RECOVERY"/>
						</TD>
					</TR>
