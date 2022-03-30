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
							<html:checkbox property="select_all" name="adminForm" value="select_all" onclick="selectDeselect(this)"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="receive_success_failure_message" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="RECEIVE_SUCCESS_FAILURE_MESSAGE" name="adminForm" value="RECEIVE_SUCCESS_FAILURE_MESSAGE"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="add_application" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="ADD_APPLICATION" name="adminForm" value="ADD_APPLICATION"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="modify_application" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="MODIFY_APPLICATION" name="adminForm" value="MODIFY_APPLICATION"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="approve_ineligible_application" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="APPROVE_INELIGIBLE_APPLICATION" name="adminForm" value="APPROVE_INELIGIBLE_APPLICATION"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="approve_duplicate_application" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="APPROVE_DUPLICATE_APPLICATION" name="adminForm" value="APPROVE_DUPLICATE_APPLICATION"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="application_approval" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="APPLICATION_APPROVAL" name="adminForm" value="APPLICATION_APPROVAL"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="application_re_approval" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="APPLICATION_RE_APPROVAL" name="adminForm" value="APPLICATION_RE_APPROVAL"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="file_upload" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="FILE_UPLOAD" name="adminForm" value="FILE_UPLOAD"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="modify_borrower_details" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="MODIFY_BORROWER_DETAILS" name="adminForm" value="MODIFY_BORROWER_DETAILS"/>
						</TD>
					</TR>

					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="periodic_info" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="PERIODIC_INFO" name="adminForm" value="PERIODIC_INFO"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="update_repayment_schedule" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="UPDATE_REPAYMENT_SCHEDULE" name="adminForm" value="UPDATE_REPAYMENT_SCHEDULE"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="shift_cgpan_or_borrower" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="SHIFT_CGPAN_OR_BORROWER" name="adminForm" value="SHIFT_CGPAN_OR_BORROWER"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="export_periodic_info" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="EXPORT_PERIDIC_INFO" name="adminForm" value="EXPORT_PERIDIC_INFO"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="application_closure" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="APPLICATION_CLOSURE" name="adminForm" value="APPLICATION_CLOSURE"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="allocate_payments" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="ALLOCATE_PAYMENTS" name="adminForm" value="ALLOCATE_PAYMENTS"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="view_pending_payments" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="VIEW_PENDING_PAYMENTS" name="adminForm" value="VIEW_PENDING_PAYMENTS"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="appropriate_payments" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="APPROPRIATE_PAYMENTS" name="adminForm" value="APPROPRIATE_PAYMENTS"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="re_allocate_payments" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="RE_ALLOCATE_PAYMENTS" name="adminForm" value="RE_ALLOCATE_PAYMENTS"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="generate_short_dan" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="GENERATE_SHORT_DAN" name="adminForm" value="GENERATE_SHORT_DAN"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="generate_cgdan" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="GENERATE_CGDAN" name="adminForm" value="GENERATE_CGDAN"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="generate_sfdan" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="GENERATE_SFDAN" name="adminForm" value="GENERATE_SFDAN"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="generate_cldan" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="GENERATE_CLDAN" name="adminForm" value="GENERATE_CLDAN"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="generate_excess_voucher" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="GENERATE_EXCESS_VOUCHER" name="adminForm" value="GENERATE_EXCESS_VOUCHER"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="generate_voucher" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="GENERATE_VOUCHER" name="adminForm" value="GENERATE_VOUCHER"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="waive_short_amounts" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="WAIVE_SHORT_AMOUNTS" name="adminForm" value="WAIVE_SHORT_AMOUNTS"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="print_pay_in_slip" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="PRINT_PAY_IN_SLIP" name="adminForm" value="PRINT_PAY_IN_SLIP"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="claim_first_installment" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="CLAIM_FIRST_INSTALLMENT" name="adminForm" value="CLAIM_FIRST_INSTALLMENT"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="claim_second_installment" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="CLAIM_SECOND_INSTALLMENT" name="adminForm" value="CLAIM_SECOND_INSTALLMENT"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="ots_request" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="OTS_REQUEST" name="adminForm" value="OTS_REQUEST"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="ots_approval" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="OTS_APPROVAL" name="adminForm" value="OTS_APPROVAL"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="claim_approval" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="CLAIM_APPROVAL" name="adminForm" value="CLAIM_APPROVAL"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="export_claim_file" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="EXPORT_CLAIM_FILE" name="adminForm" value="EXPORT_CLAIM_FILE"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="settlement" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="SETTLEMENT" name="adminForm" value="SETTLEMENT"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="limit_set_up" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="LIMIT_SET_UP" name="adminForm" value="LIMIT_SET_UP"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="risk_calculate_exposuer" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="RISK_CALCULATE_EXPOSUER" name="adminForm" value="RISK_CALCULATE_EXPOSUER"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="generate_exposure_report" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="GENERATE_EXPOSURE_REPORT" name="adminForm" value="GENERATE_EXPOSURE_REPORT"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="set_update_sub_scheme_params" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="SET_UPDATE_SUB_SCHEME_PARAMS" name="adminForm" value="SET_UPDATE_SUB_SCHEME_PARAMS"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="enter_budget_details" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="ENTER_BUDGET_DETAILS" name="adminForm" value="ENTER_BUDGET_DETAILS"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="enter_inflow_details" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="ENTER_INFLOW_DETAILS" name="adminForm" value="ENTER_INFLOW_DETAILS"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="enter_outflow_details" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="ENTER_OUTFLOW_DETAILS" name="adminForm" value="ENTER_OUTFLOW_DETAILS"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="set_ceiling_for_investment" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="SET_CEILING_FOR_INVESTMENT" name="adminForm" value="SET_CEILING_FOR_INVESTMENT"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="enter_investment_details" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="ENTER_INVESTMENT_DETAILS" name="adminForm" value="ENTER_INVESTMENT_DETAILS"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="if_calculate_exposuer" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="IF_CALCULATE_EXPOSUER" name="adminForm" value="IF_CALCULATE_EXPOSUER"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="investment_fulfillment" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="INVESTMENT_FULFILLMENT" name="adminForm" value="INVESTMENT_FULFILLMENT"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="if_update_master_tables" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="IF_UPDATE_MASTER_TABLES" name="adminForm" value="IF_UPDATE_MASTER_TABLES"/>
						</TD>
					</TR>

					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="make_request" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="MAKE_REQUEST" name="adminForm" value="MAKE_REQUEST"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="tds_details" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="TDS_DETAILS" name="adminForm" value="TDS_DETAILS"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="forecasting_details" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="FORECASTING_DETAILS" name="adminForm" value="FORECASTING_DETAILS"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="plan_investment" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="PLAN_INVESTMENT" name="adminForm" value="PLAN_INVESTMENT"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="project_expected_claims" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="PROJECT_EXPECTED_CLAIMS" name="adminForm" value="PROJECT_EXPECTED_CLAIMS"/>
						</TD>
					</TR>

					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="add_inward" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="ADD_INWARD" name="adminForm" value="ADD_INWARD"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="add_outward" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="ADD_OUTWARD" name="adminForm" value="ADD_OUTWARD"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="upload_documents" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="UPLOAD_DOCUMENTS" name="adminForm" value="UPLOAD_DOCUMENTS"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="search_documents" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="SEARCH_DOCUMENTS" name="adminForm" value="SEARCH_DOCUMENTS"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="register_collecting_bank" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="REGISTER_COLLECTING_BANK" name="adminForm" value="REGISTER_COLLECTING_BANK"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="modify_collecting_bank_details" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="MODIFY_COLLECTING_BANK_DETAILS" name="adminForm" value="MODIFY_COLLECTING_BANK_DETAILS"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="register_mli" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="REGISTER_MLI" name="adminForm" value="REGISTER_MLI"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="assign_collecting_bank" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="ASSIGN_COLLECTING_BANK" name="adminForm" value="ASSIGN_COLLECTING_BANK"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="modify_collecting_bank" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="MODIFY_COLLECTING_BANK" name="adminForm" value="MODIFY_COLLECTING_BANK"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="define_organization_structure" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="DEFINE_ORGANIZATION_STRUCTURE" name="adminForm" value="DEFINE_ORGANIZATION_STRUCTURE"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="deactivate_member" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="DEACTIVATE_MEMBER" name="adminForm" value="DEACTIVATE_MEMBER"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="activate_member" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="ACTIVATE_MEMBER" name="adminForm" value="ACTIVATE_MEMBER"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="add_modify_roles" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="ADD_MODIFY_ROLES" name="adminForm" value="ADD_MODIFY_ROLES"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="create_cgtsi_user" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="CREATE_CGTSI_USER" name="adminForm" value="CREATE_CGTSI_USER"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="create_mli_user" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="CREATE_MLI_USER" name="adminForm" value="CREATE_MLI_USER"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="modify_user_details" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="MODIFY_USER_DETAILS" name="adminForm" value="MODIFY_USER_DETAILS"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="deactivate_user" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="DEACTIVATE_USER" name="adminForm" value="DEACTIVATE_USER"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="reactivate_user" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="REACTIVATE_USER" name="adminForm" value="REACTIVATE_USER"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="assign_modify_roles_and_privileges" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="ASSIGN_MODIFY_ROLES_AND_PRIVILEGES" name="adminForm" value="ASSIGN_MODIFY_ROLES_AND_PRIVILEGES"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="reset_password" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="RESET_PASSWORD" name="adminForm" value="RESET_PASSWORD"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="enter_audit_details" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="ENTER_AUDIT_DETAILS" name="adminForm" value="ENTER_AUDIT_DETAILS"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="review_audit_details" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="REVIEW_AUDIT_DETAILS" name="adminForm" value="REVIEW_AUDIT_DETAILS"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="broadcast_message" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="BROADCAST_MESSAGE" name="adminForm" value="BROADCAST_MESSAGE"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="update_master_table" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="UPDATE_MASTER_TABLE" name="adminForm" value="UPDATE_MASTER_TABLE"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="reports" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="REPORTS" name="adminForm" value="REPORTS"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="securitization" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="SECURITIZATION" name="adminForm" value="SECURITIZATION"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="register_mcgf" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="REGISTER_MCGF" name="adminForm" value="REGISTER_MCGF"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="add_participating_banks" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="ADD_PARTICIPATING_BANKS" name="adminForm" value="ADD_PARTICIPATING_BANKS"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="add_ssi_members" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="ADD_SSI_MEMBERS" name="adminForm" value="ADD_SSI_MEMBERS"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="add_donor_details" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="ADD_DONOR_DETAILS" name="adminForm" value="ADD_DONOR_DETAILS"/>
						</TD>
					</TR>
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="set_participating_bank_limit" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="SET_PARTICIPATING_BANK_LIMIT" name="adminForm" value="SET_PARTICIPATING_BANK_LIMIT"/>
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
							<html:checkbox property="APP_STATUS_WISE_REPORT" name="adminForm" value="APP_STATUS_WISE_REPORT"/>
						</TD>
					</TR>

					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="periodic_info_approval" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="PERIODIC_INFO_APPROVAL" name="adminForm" value="PERIODIC_INFO_APPROVAL"/>
						</TD>
					</TR>

					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="day_end_process" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="DAY_END_PROCESS" name="adminForm" value="DAY_END_PROCESS"/>
						</TD>
					</TR>

					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="statement_detail" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="STATEMENT_DETAILS" name="adminForm" value="STATEMENT_DETAILS"/>
						</TD>
					</TR>

					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="if_reports" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="IF_REPORT" name="adminForm" value="IF_REPORT"/>
						</TD>
					</TR>

					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="app_special_message" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="APP_SPECIAL_MESSAGE" name="adminForm" value="APP_SPECIAL_MESSAGE"/>
						</TD>
					</TR>

					<TR>
						<TD align="left" valign="top" class="ColumnBackground">
							&nbsp;<bean:message key="update_recovery" />
						</TD>
						<TD colspan="3" align="left" valign="top" class="TableData">
							<html:checkbox property="UPDATE_RECOVERY" name="adminForm" value="UPDATE_RECOVERY"/>
						</TD>
					</TR>
