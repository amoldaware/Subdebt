
package com.cgtsi.reports;
import com.cgtsi.util.DateHelper;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import com.cgtsi.claim.BorrowerInfo;
import com.cgtsi.claim.CPDAO;
import com.cgtsi.claim.ClaimApplication;
import com.cgtsi.claim.ClaimConstants;
import com.cgtsi.claim.ClaimDetail;
import com.cgtsi.claim.ClaimSummaryDtls;
import com.cgtsi.claim.DtlsAsOnDateOfNPA;
import com.cgtsi.claim.DtlsAsOnDateOfSanction;
import com.cgtsi.claim.DtlsAsOnLogdementOfClaim;
import com.cgtsi.claim.DtlsAsOnLogdementOfSecondClaim;
import com.cgtsi.claim.LegalProceedingsDetail;
import com.cgtsi.claim.MemberInfo;
import com.cgtsi.claim.RecoveryDetails;
import com.cgtsi.claim.SecurityAndPersonalGuaranteeDtls;
import com.cgtsi.claim.SettlementDetail;
import com.cgtsi.claim.TermLoanCapitalLoanDetail;
import com.cgtsi.claim.UploadFileProperties;
import com.cgtsi.claim.WorkingCapitalDetail;
import com.cgtsi.common.Constants;
import com.cgtsi.common.DatabaseException;
import com.cgtsi.common.Log;
import com.cgtsi.util.DBConnection;


public class NewReportDAO {

	public NewReportDAO() {
	}

	
	

}
