/*************************************************************
   *
   * Name of the class: SecuritizationDAO.java
   * This class is repsonsible for handling securitization sub-system related
   * database requests.
   *
   *
   * @author : Veldurai T
   * @version:
   * @since: Nov 21, 2003
   **************************************************************/

package com.cgtsi.securitization;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import com.cgtsi.common.Constants;
import com.cgtsi.common.DatabaseException;
import com.cgtsi.common.Log;
import com.cgtsi.util.DBConnection;
import com.cgtsi.admin.DuplicateException;
import com.cgtsi.admin.User;
import com.cgtsi.application.ApplicationConstants;

/**
 * This class is repsonsible for handling securitization sub-system related
 * database requests.
 */
public class SecuritizationDAO
{

   /**
    * @roseuid 3A16470102D3
    */
   public SecuritizationDAO()
   {

   }

   /**
    * This method is used to get all the homogenous loans from database based on the
    * given select criteria. Only non-default, non-NPA accounts, accounts not closed,
    * not cancelled need to be considered.This method returns an ArrayList of
    * HomogenousLoan objects.
    * @param selectCriteria
    * @return ArrayList
    * @throws DatabaseException
    * @roseuid 39FBE70203D4
    */
   public ArrayList getHomogenousLoans(SelectCriteria selectCriteria) throws DatabaseException
   {
		ArrayList loans=new ArrayList();
		Log.log(Log.INFO,"SecuritizationDAO","getHomogenousLoans","Entered");

		String loanType=selectCriteria.getLoanType();

		Connection connection=DBConnection.getConnection();
		StringBuffer query=new StringBuffer(2000);
		String[] mlisArray=selectCriteria.getMlis();
		String[] statesArray=selectCriteria.getStates();
		String[] sectorsArray=selectCriteria.getSectors();

		StringBuffer tempBuffer=new StringBuffer(mlisArray.length*4);

		for (int i=0;i<mlisArray.length;i++)
		{
			if(mlisArray.length-1==i)
			{
				tempBuffer.append(" ?");
			}
			else
			{
				tempBuffer.append(" ?,");
			}
		}

		String mliQuery=tempBuffer.toString();

		tempBuffer.setLength(0);

		Log.log(Log.INFO,"SecuritizationDAO","getHomogenousLoans","mli query is "+mliQuery);

		for (int i=0;i<statesArray.length;i++)
		{
			if(statesArray.length-1==i)
			{
				tempBuffer.append(" ?");
			}
			else
			{
				tempBuffer.append(" ?,");
			}
		}

		String statesQuery=tempBuffer.toString();

		tempBuffer.setLength(0);

		Log.log(Log.INFO,"SecuritizationDAO","getHomogenousLoans","states query is "+statesQuery);

		for (int i=0;i<sectorsArray.length;i++)
		{
			if(sectorsArray.length-1==i)
			{
				tempBuffer.append(" ?");
			}
			else
			{
				tempBuffer.append(" ?,");
			}
		}

		String sectorQuery=tempBuffer.toString();

		tempBuffer.setLength(0);

		tempBuffer=null;

		Log.log(Log.INFO,"SecuritizationDAO","getHomogenousLoans","sector query is "+sectorQuery);

		if(loanType.equalsIgnoreCase(ApplicationConstants.TC_APPLICATION))
		{
			Log.log(Log.DEBUG,"SecuritizationDAO","getHomogenousLoans","If block ");

			query.append("SELECT Sector,State, COUNT(CGPAN),SUM(term_loan_sanctioned),");
			query.append(" SUM(approved_amount) FROM ");
			query.append(" (SELECT	Sector,");
			query.append(" State,CGPAN,Term_Loan_Sanctioned,Approved_Amount,MIN(Disbursement_Date)");
			query.append(" FROM(SELECT	ssi.ssi_industry_sector Sector,");
			query.append(" ssi.ssi_state_name State,vi.cgpan CGPAN,");
			query.append(" vi.tc_sanctioned_amt Term_Loan_Sanctioned,");
			query.append(" vi.approved_amt Approved_Amount,");
			query.append(" d.dbr_dt Disbursement_Date FROM	ssi_detail ssi,");
			query.append(" view_appl_amounts vi,application_detail app,");
			query.append(" term_loan_detail trm,disbursement_detail d");
			//query.append(" WHERE	LTRIM(RTRIM(UPPER(app.mem_bnk_id || app.mem_zne_id || app.mem_brn_id)))");
			query.append(" WHERE app.mem_bnk_id");
			query.append(" IN ");
			query.append(" ( ");
			query.append(mliQuery);

			query.append(" )");
			query.append(" AND ssi.ssi_state_name");
			query.append(" IN ");
			query.append(" ( ");
			query.append(statesQuery);

			query.append(" )");
			query.append(" AND ssi.ssi_industry_sector");
			query.append(" IN ");
			query.append(" ( ");
			query.append(sectorQuery);

			query.append(" )");

			query.append(" AND app.app_loan_type=?");
			query.append(" AND	app.app_status");
			query.append(" =");
			query.append(" ? ");
			//query.append(" AND	LTRIM(RTRIM(UPPER(trm.trm_interest_rate))) 	>	" );
			//query.append(" LTRIM(RTRIM(UPPER(");
			//query.append(" ?");
			//query.append(" )))");
			query.append(" AND	(trm.trm_interest_rate between " );
			query.append(" ? and ");
			query.append(" ? )");
			query.append(" AND	trm.trm_interest_type	=	" );
			query.append(" ? " );
			//query.append(" AND  	LTRIM(RTRIM(trm.trm_amount_sanctioned))		=	" );
			//query.append(" LTRIM(RTRIM(");
			//query.append(" ?" );
			//query.append(" ))");
			query.append(" AND	(vi.approved_amt	" );
			query.append(" between ");
			query.append(" ? and " );
			query.append(" ? )");

			query.append(" AND	vi.cgpan NOT IN	(SELECT cgpan FROM loan_pool_application)");
			query.append(" AND	vi.cgpan =	app.cgpan");
			query.append(" AND	ssi.ssi_reference_number=	app.ssi_reference_number");
			query.append(" AND	vi.appref=	d.app_ref_no");
			query.append(" AND	app.app_ref_no= trm.app_ref_no");
			query.append(" AND	to_date(ADD_MONTHS(vi.submitted_dt,nvl(trm.trm_tenure,0)))");
			query.append(" <=	to_date(ADD_MONTHS(" );
			query.append(" ? ");
			query.append(" ," );
			query.append(" ? ");
			query.append(" )) ");
			query.append(" AND	trunc(SYSDATE)");
			query.append(" <=	(SELECT ADD_MONTHS(MIN(dbr.dbr_dt)," );
			query.append(" ? ");
			query.append(" )");
			query.append(" FROM	disbursement_detail dbr");
			query.append(" WHERE dbr.app_ref_no= vi.APPREF))");
			query.append(" GROUP BY Sector,State,CGPAN,Term_Loan_Sanctioned,Approved_Amount)");
			query.append(" GROUP BY Sector,State");
		}
		else
		{
			//For WC applications.


			query.append(" SELECT 	Sector,State,COUNT(CGPAN),SUM(WC_FB) + SUM(WC_NFB),");
			query.append(" SUM(approved_amount) FROM (SELECT Sector,");
			query.append(" State,CGPAN,WC_FB,WC_NFB,Approved_Amount FROM");
			query.append(" (SELECT	ssi.ssi_industry_sector Sector,");
			query.append(" ssi.ssi_state_name State,vi.cgpan CGPAN,");
			query.append(" vi.wc_fb_limit WC_FB,vi.wc_nfb_limit WC_NFB,");
			query.append(" decode(app.app_enhanced_approved_amt,null, app.app_approved_amount,");
			query.append(" app.app_enhanced_approved_amt) Approved_Amount");
			query.append(" FROM	ssi_detail ssi,view_appl_amounts vi,");
			query.append(" application_detail app,working_capital_detail wcp");
			query.append(" WHERE	app.mem_bnk_id");
			query.append(" IN 	(");
			query.append(mliQuery);
			query.append(" )");
			query.append(" AND	ssi.ssi_state_name");
			query.append(" IN	(");
			query.append(statesQuery);
			query.append(" )");
			query.append(" AND	ssi.ssi_industry_sector");
			query.append(" IN	(");
			query.append(sectorQuery);
			query.append(" )");
			query.append(" AND	app.app_loan_type=");
			query.append(" ?" );
			query.append(" AND	app.app_status=");

			query.append(" ?" );

			query.append(" AND	(wcp.wcp_interest between");
			query.append(" ? and ");

			query.append(" ? )" );

			/*
			query.append(" AND  	LTRIM(RTRIM(wcp.wcp_fb_limit_sanctioned))  	+");
			query.append(" LTRIM(RTRIM(wcp.wcp_nfb_limit_sanctioned))");
			*/
			query.append(" AND (decode(app.app_enhanced_approved_amt,null,");
			query.append(" app.app_approved_amount,app.app_enhanced_approved_amt)");

			query.append(" between ");


			query.append(" ?" );
			query.append(" and " );
			query.append(" ?) " );

			query.append(" AND	vi.cgpan NOT IN	(SELECT cgpan");
			query.append(" FROM	loan_pool_application)");
			query.append(" AND	vi.cgpan	" );
			query.append(" = app.cgpan");
			query.append(" AND	ssi.ssi_reference_number =	app.ssi_reference_number");
			query.append(" AND	app.app_ref_no ");
			query.append(" = 	wcp.app_ref_no)");
			query.append(" GROUP BY Sector,State,CGPAN,WC_FB,WC_NFB,Approved_Amount)");
			query.append(" GROUP BY Sector,State");

			Log.log(Log.DEBUG,"SecuritizationDAO","getHomogenousLoans","Else block ");

		}
		try
		{
			String queryStr=query.toString();
			Log.log(Log.DEBUG,"SecuritizationDAO","getHomogenousLoans","query is "+queryStr);

			PreparedStatement preparedStatement=connection.prepareStatement(queryStr);
			int j=1;
			for(int i=0;i<mlisArray.length;i++)
			{
				Log.log(Log.DEBUG,"SecuritizationDAO","getHomogenousLoans"," mli value At "+j+ " is  "+mlisArray[i].substring(0,4));
				preparedStatement.setString(j,mlisArray[i].substring(0,4));
				j++;
			}
			Log.log(Log.DEBUG,"SecuritizationDAO","getHomogenousLoans","J after mlis value is  "+j);

			for(int i=0;i<statesArray.length;i++)
			{
				Log.log(Log.DEBUG,"SecuritizationDAO","getHomogenousLoans","at "+j+" is "+statesArray[i]);

				preparedStatement.setString(j,statesArray[i]);
				j++;
			}

			Log.log(Log.DEBUG,"SecuritizationDAO","getHomogenousLoans","J after states value is  "+j);

			for(int i=0;i<sectorsArray.length;i++)
			{
				Log.log(Log.DEBUG,"SecuritizationDAO","getHomogenousLoans sectors ","at "+j+" is "+sectorsArray[i]);
				preparedStatement.setString(j,sectorsArray[i]);
				j++;
			}

			Log.log(Log.DEBUG,"SecuritizationDAO","getHomogenousLoans","J after sectors value is  "+j);

			if(loanType.equalsIgnoreCase(ApplicationConstants.TC_APPLICATION))
			{
				Log.log(Log.DEBUG,"SecuritizationDAO","getHomogenousLoans","TC Application "+ApplicationConstants.TC_APPLICATION );
				preparedStatement.setString(j,ApplicationConstants.TC_APPLICATION);
			}
			else
			{
				preparedStatement.setString(j,ApplicationConstants.WC_APPLICATION);
			}
			//preparedStatement.setString(j,ApplicationConstants.TC_APPLICATION);

			preparedStatement.setString(++j,ApplicationConstants.APPLICATION_APPROVED_STATUS);
			Log.log(Log.DEBUG,"SecuritizationDAO","getHomogenousLoans","APP status "+j+" "+ApplicationConstants.APPLICATION_APPROVED_STATUS);

			preparedStatement.setDouble(++j,selectCriteria.getInterestRate() );
			Log.log(Log.DEBUG,"SecuritizationDAO","getHomogenousLoans","I Rate1 "+j+" "+selectCriteria.getInterestRate());
			preparedStatement.setDouble(++j,selectCriteria.getNextInterestRate() );

			Log.log(Log.DEBUG,"SecuritizationDAO","getHomogenousLoans","I Rate2 "+j+" "+selectCriteria.getNextInterestRate());

			if(loanType.equalsIgnoreCase(ApplicationConstants.TC_APPLICATION))
			{
				preparedStatement.setString(++j,selectCriteria.getTypeOfInterest() );
				Log.log(Log.DEBUG,"SecuritizationDAO","getHomogenousLoans","type "+j+" "+selectCriteria.getTypeOfInterest());

				preparedStatement.setDouble(++j,selectCriteria.getLoanSize());
				Log.log(Log.DEBUG,"SecuritizationDAO","getHomogenousLoans","loan size1 "+j+" "+selectCriteria.getLoanSize());

				preparedStatement.setDouble(++j,selectCriteria.getNextLoanSize());
				Log.log(Log.DEBUG,"SecuritizationDAO","getHomogenousLoans","loan size2 "+j+" "+selectCriteria.getNextLoanSize());

				java.sql.Date effDate=new java.sql.Date(selectCriteria.getEffectiveDate().getTime());
				Calendar cal=Calendar.getInstance();
				cal.setTime(selectCriteria.getEffectiveDate());
				
				Log.log(Log.DEBUG,"SecuritizationDAO","getHomogenousLoans","day "+cal.get(Calendar.DATE));
				Log.log(Log.DEBUG,"SecuritizationDAO","getHomogenousLoans","month "+cal.get(Calendar.MONTH));
				Log.log(Log.DEBUG,"SecuritizationDAO","getHomogenousLoans","year "+cal.get(Calendar.YEAR));
				
				Log.log(Log.DEBUG,"SecuritizationDAO","getHomogenousLoans","eff date "+j+" "+selectCriteria.getEffectiveDate());
				
				//preparedStatement.setString(++j,cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR));
				preparedStatement.setDate(++j,effDate);
				

				preparedStatement.setInt(++j,selectCriteria.getTenure());
				Log.log(Log.DEBUG,"SecuritizationDAO","getHomogenousLoans","loan tenur "+j+" "+selectCriteria.getTenure());

				preparedStatement.setInt(++j, selectCriteria.getTrackRecord());
				Log.log(Log.DEBUG,"SecuritizationDAO","getHomogenousLoans","track record "+j+" "+selectCriteria.getTrackRecord());
			}
			else
			{
				preparedStatement.setDouble(++j,selectCriteria.getLoanSize());
				preparedStatement.setDouble(++j,selectCriteria.getNextLoanSize());
			}


			Log.log(Log.DEBUG,"SecuritizationDAO","getHomogenousLoans","J final value is  "+j);

			ResultSet results=preparedStatement.executeQuery();

			Map sectorLoans=new HashMap();

			while(results.next())
			{
				HomogeneousLoan homogeneousLoan=null;
				ArrayList sectorWiseData=null;

				String sector=results.getString(1);
				String state=results.getString(2);
				int noOfLoans=results.getInt(3);
				double sanctionedAmt=results.getDouble(4);
				double approvedAmt=results.getDouble(5);

				Log.log(Log.DEBUG,"SecuritizationDAO","getHomogenousLoans",
					"Results "+sector+" ,"+state+","+noOfLoans+","+sanctionedAmt+","+approvedAmt);

				homogeneousLoan=(HomogeneousLoan)sectorLoans.get(sector);
				Log.log(Log.DEBUG,"SecuritizationDAO","getHomogenousLoans",
					"Home.Loan available???..."+homogeneousLoan);
				if(homogeneousLoan==null)
				{
					Log.log(Log.DEBUG,"SecuritizationDAO","getHomogenousLoans",
						"new sector...");
					homogeneousLoan=new HomogeneousLoan();
					homogeneousLoan.setSector(sector);
					sectorWiseData=new ArrayList();

					homogeneousLoan.setStates(sectorWiseData);

					sectorLoans.put(sector,homogeneousLoan);
				}
				else
				{
					sectorWiseData=homogeneousLoan.getStates();
				}

				SectorWise sectorWise=new SectorWise();
				sectorWise.setNoOfLoans(noOfLoans);
				sectorWise.setState(state);
				sectorWise.setTotalGC(approvedAmt);
				sectorWise.setTotalSanctionedAmt(sanctionedAmt);

				sectorWiseData.add(sectorWise);
			}

			Iterator loansIterator=sectorLoans.keySet().iterator();

			while(loansIterator.hasNext())
			{
				String key=(String)loansIterator.next();

				Log.log(Log.DEBUG,"SecuritizationDAO","getHomogenousLoans",
					"key "+key);

				HomogeneousLoan homeLoan=(HomogeneousLoan)sectorLoans.get(key);

				Log.log(Log.DEBUG,"SecuritizationDAO","getHomogenousLoans",
					"sector "+homeLoan.getSector());

				loans.add(homeLoan);
			}

			results.close();
			results=null;
			preparedStatement.close();
			preparedStatement=null;


		}
		catch (SQLException e)
		{
			Log.log(Log.ERROR,"SecuritizationDAO","getHomogenousLoans",e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to get Homogeneous Loans");

		}
		finally
		{
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO,"SecuritizationDAO","getHomogenousLoans","Exited");

		return loans;
   }
   /*
   public ArrayList getHomogenousLoans(SelectCriteria selectCriteria) throws DatabaseException
   {
    	ArrayList loans=new ArrayList();

		Log.log(Log.INFO,"SecuritizationDAO","getHomogenousLoans","Entered");

		Connection connection=DBConnection.getConnection();

		try
		{
			String[] mlisArray=selectCriteria.getMlis();
			StringBuffer buffer=new StringBuffer(mlisArray.length*3);

			if(mlisArray.length==1)
			{
				buffer.append(mlisArray[0].substring(0,12));
			}
			else
			{
				for (int i=0;i<mlisArray.length;i++)
				{
					if(i==0)
					{
						buffer.append(mlisArray[i].substring(0,12)+"$,");
					}
					else if((mlisArray.length-1)==i)
					{
						buffer.append("$"+mlisArray[i].substring(0,12));
					}
					else
					{
						buffer.append("$"+mlisArray[i].substring(0,12)+"$,");
					}
				}

			}

			String mliString=buffer.toString();

			Log.log(Log.DEBUG,"SecuritizationDAO","getHomogenousLoans","Mlis are "+mliString);

			buffer.setLength(0);

			String[] statesArray=selectCriteria.getStates();

			if(statesArray.length==1)
			{
				buffer.append(statesArray[0]);
			}
			else
			{
				for( int i=0;i<statesArray.length;i++)
				{
					if(i==0)
					{
						buffer.append(statesArray[i]+"$,");
					}
					else if(statesArray.length-1==i)
					{
						buffer.append("$"+statesArray[i]);
					}
					else
					{
						buffer.append("$"+statesArray[i]+"$,");
					}
				}

			}

			String statesStr=buffer.toString();

			Log.log(Log.DEBUG,"SecuritizationDAO","getHomogenousLoans","States are "+statesStr);

			buffer.setLength(0);


			String[] sectorsArray=selectCriteria.getSectors();

			if(sectorsArray.length==1)
			{
				buffer.append(sectorsArray[0]);
			}
			else
			{
				for( int i=0;i<sectorsArray.length;i++)
				{

					if(i==0)
					{
						buffer.append(sectorsArray[i]+"$,");
					}
					else if(sectorsArray.length-1==i)
					{
						buffer.append("$"+sectorsArray[i]);
					}
					else
					{
						buffer.append("$"+sectorsArray[i]+"$,");
					}
				}
			}


			String sectorsStr=buffer.toString();

			Log.log(Log.DEBUG,"SecuritizationDAO","getHomogenousLoans","Sectors are "+sectorsStr);

			buffer.setLength(0);

			CallableStatement callable=
				connection.prepareCall("{?=call PACKGETHOMOGENEOUSLOAN.FUNCGETHOMOGENLOAN(?,?,?,?,?,?,?,?,?,?,?,?)}");

			callable.registerOutParameter(1,Types.INTEGER);


			callable.setString(2,mliString);
			callable.setString(3,statesStr);
			callable.setString(4,sectorsStr);
			callable.setInt(5,selectCriteria.getTenure());
			Log.log(Log.DEBUG,"SecuritizationDAO","getHomogenousLoans","Tenure "+selectCriteria.getTenure());

			java.sql.Date sqlDate=new java.sql.Date(selectCriteria.getEffectiveDate().getTime());
			callable.setDate(6,sqlDate);

			Log.log(Log.DEBUG,"SecuritizationDAO","getHomogenousLoans","date "+selectCriteria.getEffectiveDate()+" , "+sqlDate);

			callable.setString(7,selectCriteria.getLoanType());

			Log.log(Log.DEBUG,"SecuritizationDAO","getHomogenousLoans","loan type "+selectCriteria.getLoanType());

			callable.setDouble(8,selectCriteria.getInterestRate());
			Log.log(Log.DEBUG,"SecuritizationDAO","getHomogenousLoans","interest rate "+selectCriteria.getInterestRate());

			callable.setString(9,selectCriteria.getTypeOfInterest());

			Log.log(Log.DEBUG,"SecuritizationDAO","getHomogenousLoans","interest Type "+selectCriteria.getTypeOfInterest());

			callable.setInt(10,selectCriteria.getTrackRecord());

			Log.log(Log.DEBUG,"SecuritizationDAO","getHomogenousLoans"," Track Record "+selectCriteria.getTrackRecord());
			callable.setDouble(11,selectCriteria.getLoanSize());

			Log.log(Log.DEBUG,"SecuritizationDAO","getHomogenousLoans"," Loan Size "+selectCriteria.getLoanSize());

			callable.registerOutParameter(12,Constants.CURSOR);
			callable.registerOutParameter(13,Types.VARCHAR);

			callable.execute();
			int errorCode=callable.getInt(1);
			String error=callable.getString(13);

			Log.log(Log.DEBUG,"SecuritizationDAO","getHomogenousLoans","Error code and error "+errorCode+" "+error);

			if(errorCode==Constants.FUNCTION_FAILURE)
			{
				callable.close();
				callable=null;
				Log.log(Log.ERROR,"SecuritizationDAO","getHomogenousLoans",error);

				throw new DatabaseException(error);
			}

			ResultSet results=(ResultSet)callable.getObject(12);

			Map sectorLoans=new HashMap();

			while(results.next())
			{
				HomogeneousLoan homogeneousLoan=null;
				ArrayList sectorWiseData=null;

				String sector=results.getString(1);
				String state=results.getString(2);
				int noOfLoans=results.getInt(3);
				double sanctionedAmt=results.getDouble(4);
				double approvedAmt=results.getDouble(5);

				Log.log(Log.DEBUG,"SecuritizationDAO","getHomogenousLoans",
					"Results "+sector+" ,"+state+","+noOfLoans+","+sanctionedAmt+","+approvedAmt);

				homogeneousLoan=(HomogeneousLoan)sectorLoans.get(sector);
				Log.log(Log.DEBUG,"SecuritizationDAO","getHomogenousLoans",
					"Home.Loan available???..."+homogeneousLoan);
				if(homogeneousLoan==null)
				{
					Log.log(Log.DEBUG,"SecuritizationDAO","getHomogenousLoans",
						"new sector...");
					homogeneousLoan=new HomogeneousLoan();
					homogeneousLoan.setSector(sector);
					sectorWiseData=new ArrayList();

					homogeneousLoan.setStates(sectorWiseData);

					sectorLoans.put(sector,homogeneousLoan);
				}
				else
				{
					sectorWiseData=homogeneousLoan.getStates();
				}

				SectorWise sectorWise=new SectorWise();
				sectorWise.setNoOfLoans(noOfLoans);
				sectorWise.setState(state);
				sectorWise.setTotalGC(approvedAmt);
				sectorWise.setTotalSanctionedAmt(sanctionedAmt);

				sectorWiseData.add(sectorWise);
			}

			Iterator loansIterator=sectorLoans.keySet().iterator();

			while(loansIterator.hasNext())
			{
				String key=(String)loansIterator.next();

				Log.log(Log.DEBUG,"SecuritizationDAO","getHomogenousLoans",
					"key "+key);

				HomogeneousLoan homeLoan=(HomogeneousLoan)sectorLoans.get(key);

				Log.log(Log.DEBUG,"SecuritizationDAO","getHomogenousLoans",
					"sector "+homeLoan.getSector());

				loans.add(homeLoan);
			}


			results.close();
			results=null;
			callable.close();
			callable=null;
		}
		catch (SQLException e)
		{
			Log.log(Log.ERROR,"SecuritizationDAO","getHomogenousLoans",e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to insert Select Query");
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO,"SecuritizationDAO","getHomogenousLoans","Exited");

    	return loans;
   }
   */
   /**
    * This method is used to capture the loan pool details. The loans selected for
    * the trench are marked so that they will not participate in another trench
    * creation.This method throws DuplicateException if the pool name already
    * exists.After creating a pool, a unique number called pool number is generated.
    * @param loanPool
    * @param selectCriteria
    * @param createdBy
    * @throws DatabaseException
    * @throws DuplicateException
    * @roseuid 39FBF37A03AB
    */
   public int createLoanPool(LoanPool loanPool, SelectCriteria selectCriteria, User createdBy) throws DatabaseException, DuplicateException
   {
		Log.log(Log.INFO,"SecuritizationDAO","createLoanPool","Entered");
		Connection connection=DBConnection.getConnection(false);
		int poolId=-1;
		try
		{
			//Insert the loan pool details and returns the Id.
			poolId=insertLoanPoolDetails(loanPool,createdBy.getUserId(),connection);

			//Insert select query. and get the id.
			int selectionCriteriaId=insertSelectCriteria(poolId,selectCriteria,createdBy.getUserId(),connection);

			Log.log(Log.DEBUG,"SecuritizationDAO","createLoanPool","Pool id and selectionCriteriaId id "+poolId+" "+selectionCriteriaId);

			//call the procedure to place all the selected CGPANS into a table.
			updateLoanPoolApplications(poolId,selectionCriteriaId,connection);

			//commit the transaction.
			connection.commit();
		}
		catch (SQLException e)
		{

			Log.log(Log.ERROR,"SecuritizationDAO","createLoanPool",e.getMessage());
			Log.logException(e);

			try
			{
				connection.rollback();
			}
			catch (SQLException ignore){}

			throw new DatabaseException("Unable to create Loan Pool");
		}
		finally
		{
//			Free the connection here.
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO,"SecuritizationDAO","createLoanPool","Exited");

		return poolId;
   }

   /**
    * This method is used to insert the loan pool details into database. And it returns
    * the generated id.
    *
	* @param loanPool 	The Loan Pool details to be inserted.
	* @param userId	  	The creating user id.
	* @param connection	The Connection object.
	* @return int	  	The generated id.
	* @throws DatabaseException	If any Database error occured, DatabaseException is thrown.
	*/

   private int insertLoanPoolDetails(LoanPool loanPool,
   					String userId,Connection connection) throws DatabaseException
   {
		Log.log(Log.INFO,"SecuritizationDAO","insertLoanPoolDetails","Entered");

		int returnValue=-1;

		try
		{
			CallableStatement callable=
				connection.prepareCall("{?=call funcInsSecLoanPool(?,?,?,?,?,?,?,?,?,?)}");

			callable.registerOutParameter(1,Types.INTEGER);

			callable.setString(2,loanPool.getLoanPoolName());
			callable.setString(3,loanPool.getSpvName());
			callable.setString(4,loanPool.getRatingAgencyName());
			callable.setDouble(5,loanPool.getAmountSecuritized());
			callable.setDouble(6,loanPool.getInterestRate());

			callable.setDate(7,new java.sql.Date(loanPool.getSecuritizationIssueDate().getTime()));

			callable.setString(8,loanPool.getRating());
			callable.setString(9,userId);

			callable.registerOutParameter(10,Types.INTEGER);
			callable.registerOutParameter(11,Types.INTEGER);

			callable.execute();
			int errorCode=callable.getInt(1);
			String error=callable.getString(11);

			Log.log(Log.DEBUG,"SecuritizationDAO","insertLoanPoolDetails","Error code and error "+errorCode+" "+error);

			if(errorCode==Constants.FUNCTION_FAILURE)
			{
				callable.close();
				callable=null;

				connection.rollback();

				Log.log(Log.ERROR,"SecuritizationDAO","insertLoanPoolDetails",error);

				throw new DatabaseException(error);
			}

			returnValue=callable.getInt(10);

			Log.log(Log.DEBUG,"SecuritizationDAO","insertLoanPoolDetails","returnValue "+returnValue);

			callable.close();
			callable=null;

		}
		catch (SQLException e)
		{
			Log.log(Log.ERROR,"SecuritizationDAO","insertLoanPoolDetails",e.getMessage());
			Log.logException(e);

			try
			{
				connection.rollback();
			}
			catch (SQLException ignore) {}

			throw new DatabaseException("Unable to insert Loan Pool Details");
		}

		Log.log(Log.INFO,"SecuritizationDAO","insertLoanPoolDetails","Exited");

   		return returnValue;
   }

   /**
    * This method is used to insert the Select Query of the loan pool.
	* @param selectCriteria	The selected query
	* @param user				User id of the creating user.
	* @param connection		The Connection object.
	* @return	int				The id of the inserted row.
	* @throws DatabaseException	If any error occured while accessing database,
	* 								this exception is thrown.
 	*/

	private int insertSelectCriteria(int poolId,SelectCriteria selectCriteria, String user,
   									Connection connection) throws DatabaseException
   	{
		Log.log(Log.INFO,"SecuritizationDAO","insertSelectCriteria","Entered");

		int code=-1;

		try
		{
			CallableStatement callable=
				connection.prepareCall("{?=call funcInsLoanSelCriteria(?,?,?,?,?,?,?,?,?,?,?,?) }");


			callable.registerOutParameter(1,Types.INTEGER);

			callable.setInt(2,selectCriteria.getTenure());

			callable.setDate(3,new java.sql.Date(selectCriteria.getEffectiveDate().getTime()));

			callable.setDouble(4,selectCriteria.getInterestRate());
			callable.setDouble(5,selectCriteria.getNextInterestRate());
			callable.setString(6,selectCriteria.getTypeOfInterest());
			callable.setInt(7,selectCriteria.getTrackRecord());
			callable.setDouble(8,selectCriteria.getLoanSize());
			callable.setDouble(9,selectCriteria.getNextLoanSize());
			callable.setString(10,selectCriteria.getLoanType());
			callable.setInt(11,poolId);
			
			callable.registerOutParameter(12,Types.INTEGER);
			callable.registerOutParameter(13,Types.VARCHAR);

			callable.execute();
			int errorCode=callable.getInt(1);
			String error=callable.getString(13);

			Log.log(Log.DEBUG,"SecuritizationDAO","insertSelectCriteria","Error code and error "+errorCode+" "+error);

			if(errorCode==Constants.FUNCTION_FAILURE)
			{
				callable.close();
				callable=null;

				connection.rollback();

				Log.log(Log.ERROR,"SecuritizationDAO","insertSelectCriteria",error);

				throw new DatabaseException(error);
			}

			code=callable.getInt(12);

			Log.log(Log.DEBUG,"SecuritizationDAO","insertSelectCriteria","code received is  "+code);

			callable.close();
			callable=null;

			String[] allMLIs=selectCriteria.getMlis();

			String bankId=null;
			String zoneId=null;
			String branchId=null;

			Log.log(Log.DEBUG,"SecuritizationDAO","insertSelectCriteria","Inserting members.");

			for(int i=0;i<allMLIs.length;i++)
			{
				String member=allMLIs[i];
				bankId=member.substring(0,4);
				zoneId=member.substring(4,8);
				branchId=member.substring(8,12);

				Log.log(Log.DEBUG,"SecuritizationDAO","insertSelectCriteria","member Id "+member);

				callable=
					connection.prepareCall("{?=call funcInsLoanMemCriteria(?,?,?,?,?)}");

				callable.registerOutParameter(1,Types.INTEGER);

				callable.setInt(2,code);

				callable.setString(3,bankId);
				callable.setString(4,zoneId);
				callable.setString(5,branchId);

				callable.registerOutParameter(6,Types.VARCHAR);

				callable.execute();

				errorCode=callable.getInt(1);

				error=callable.getString(6);

				Log.log(Log.DEBUG,"SecuritizationDAO","insertSelectCriteria","Errorcode and errors "+errorCode+" "+error);

				if(errorCode==Constants.FUNCTION_FAILURE)
				{
					callable.close();
					callable=null;

					connection.rollback();

					Log.log(Log.ERROR,"SecuritizationDAO","insertSelectCriteria",error);

					throw new DatabaseException(error);
				}
				callable.close();
				callable=null;
			}

			String[] states=selectCriteria.getStates();

			Log.log(Log.DEBUG,"SecuritizationDAO","insertSelectCriteria","Inserting states.");

			for(int i=0;i<states.length;i++)
			{
				String state=states[i];

				Log.log(Log.DEBUG,"SecuritizationDAO","insertSelectCriteria","state "+state);

				callable=
					connection.prepareCall("{?=call funcInsLoanStateCriteria(?,?,?)}");

				callable.registerOutParameter(1,Types.INTEGER);

				callable.setInt(2,code);
				callable.setString(3,state);

				callable.registerOutParameter(4,Types.VARCHAR);

				callable.execute();

				errorCode=callable.getInt(1);

				error=callable.getString(4);

				Log.log(Log.DEBUG,"SecuritizationDAO","insertSelectCriteria","Errorcode and errors "+errorCode+" "+error);

				if(errorCode==Constants.FUNCTION_FAILURE)
				{
					callable.close();
					callable=null;

					connection.rollback();

					Log.log(Log.ERROR,"SecuritizationDAO","insertSelectCriteria",error);

					throw new DatabaseException(error);
				}
				callable.close();
				callable=null;
			}

			String[] sectors=selectCriteria.getSectors();

			Log.log(Log.DEBUG,"SecuritizationDAO","insertSelectCriteria","Inserting sectors.");

			for(int i=0;i<sectors.length;i++)
			{
				String sector=sectors[i];

				Log.log(Log.DEBUG,"SecuritizationDAO","insertSelectCriteria","sector "+sector);

				callable=
					connection.prepareCall("{?=call funcInsLoanSecCriteria(?,?,?)}");

				callable.registerOutParameter(1,Types.INTEGER);

				callable.setInt(2,code);
				callable.setString(3,sector);

				callable.registerOutParameter(4,Types.VARCHAR);

				callable.execute();

				errorCode=callable.getInt(1);

				error=callable.getString(4);

				Log.log(Log.DEBUG,"SecuritizationDAO","insertSelectCriteria","Errorcode and errors "+errorCode+" "+error);

				if(errorCode==Constants.FUNCTION_FAILURE)
				{
					callable.close();
					callable=null;

					connection.rollback();

					Log.log(Log.ERROR,"SecuritizationDAO","insertSelectCriteria",error);

					throw new DatabaseException(error);
				}
				callable.close();
				callable=null;
			}
		}
		catch (SQLException e)
		{
			Log.log(Log.ERROR,"SecuritizationDAO","insertSelectCriteria",e.getMessage());
			Log.logException(e);

			try
			{
				connection.rollback();
			}
			catch (SQLException ignore){}

			throw new DatabaseException("Unable to insert Select Query");
		}

		Log.log(Log.INFO,"SecuritizationDAO","insertSelectCriteria","Exited");

		return code;
   }

   /**
    * This method is used to update the loan pool applications table. This table holds
    * the CGPANs of the earlier created Loan pool applications. Once an application
    * become part of a pool, that application's CGPAN would be updated into this table.
    * Henceforth, this CGPAN will not be part of any other pool.
 	* @param poolId
 	* @param selectionCriteriaId
 	* @param connection
 	* @throws DatabaseException
 	*/

	private void updateLoanPoolApplications(int poolId,
   			int selectionCriteriaId,Connection connection) throws DatabaseException
   	{
		Log.log(Log.INFO,"SecuritizationDAO","updateLoanPoolApplications","Entered");


		try
		{
			CallableStatement callable=
				connection.prepareCall("{?=call funcInsLoanPoolApp(?,?,?) }");

			callable.registerOutParameter(1,Types.INTEGER);
			callable.setInt(2,poolId);
			callable.setInt(3,selectionCriteriaId);
			callable.registerOutParameter(4,Types.VARCHAR);

			callable.execute();

			int errorCode=callable.getInt(1);
			String error=callable.getString(4);

			Log.log(Log.DEBUG,"SecuritizationDAO","updateLoanPoolApplications","Error code and error "+errorCode+" "+error);

			if(errorCode==Constants.FUNCTION_FAILURE)
			{
				callable.close();
				callable=null;

				connection.rollback();

				Log.log(Log.ERROR,"SecuritizationDAO","updateLoanPoolApplications",error);

				throw new DatabaseException(error);
			}

			callable.close();
			callable=null;

		}
		catch (SQLException e)
		{
			Log.log(Log.ERROR,"SecuritizationDAO","updateLoanPoolApplications",e.getMessage());
			Log.logException(e);

			try
			{
				connection.rollback();
			}
			catch (SQLException ignore){}

			throw new DatabaseException("Unable to insert Select Query");
		}
		Log.log(Log.INFO,"SecuritizationDAO","updateLoanPoolApplications","Exited");
   }
   /**
	* This method is used to add the investor details for the just created loan pool.
	* @param poolId 	The created pool id.
	* @param name	The investor Name
	* @param amount			Amount invested
	* @throws DatabaseException	throws DatabaseException if any error occured
	* 							while inserting data into database.
	*/
   public void addInvesterDetails(int poolId,Investor investor) throws DatabaseException
   {
		Log.log(Log.INFO,"SecuritizationDAO","addInvesterDetails","Entered");

		Connection connection=DBConnection.getConnection();

		try
		{
			CallableStatement callable=
			connection.prepareCall("{?=call funcInsSecInvestmentDtl(?,?,?,?) }");

			callable.registerOutParameter(1,Types.INTEGER);

			callable.setInt(2,poolId);
			callable.setString(3,investor.getInvestorName());
			callable.setDouble(4,investor.getInvestedAmount());

			callable.registerOutParameter(5,Types.INTEGER);
			callable.execute();

			int errorCode=callable.getInt(1);
			String error=callable.getString(5);

			Log.log(Log.DEBUG,"SecuritizationDAO","addInvesterDetails","Error Code and Error "+errorCode+" , "+error);

			if(errorCode==Constants.FUNCTION_FAILURE)
			{
				callable.close();
				callable=null;

				Log.log(Log.ERROR,"SecuritizationDAO","addInvesterDetails",error);

				throw new DatabaseException(error);
			}

			callable.close();
			callable=null;

		}
		catch (SQLException e)
		{
			Log.log(Log.ERROR,"SecuritizationDAO","addInvesterDetails",e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable to add Investor Details.");
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}


		Log.log(Log.INFO,"SecuritizationDAO","addInvesterDetails","Exited");
   }
   /**
	* This method is used to get the homogenous loan pool details for the selected state.
	* @param sector 			The selected Sector
	* @param state				The selected state
	* @param selectCriteria	The select criteria
	* @throws DatabaseException	throws DatabaseException if any error occured
	* 							while selecting data from the database.
	*/
   public StateWise getStateWiseLoans(String sector,String state,
   								SelectCriteria selectCriteria)	throws DatabaseException
   {
		Log.log(Log.INFO,"SecuritizationDAO","getStateWiseLoans","Entered");

		Connection connection=DBConnection.getConnection();

		StateWise stateWise=new StateWise();

		String[] mlisArray=selectCriteria.getMlis();

		StringBuffer mlisBuffer=new StringBuffer(mlisArray.length*4);

		for (int i=0;i<mlisArray.length;i++)
		{
			if(mlisArray.length-1==i)
			{
				mlisBuffer.append(" ?");
			}
			else
			{
				mlisBuffer.append(" ?,");
			}
		}

		String mlisQuery=mlisBuffer.toString();

		Log.log(Log.DEBUG,"SecuritizationDAO","getStateWiseLoans","MLIs Query is "+mlisQuery);

		try
		{
			StringBuffer query=new StringBuffer(2000);

			if(selectCriteria.getLoanType().equals(ApplicationConstants.TC_APPLICATION))
			{
				Log.log(Log.DEBUG,"SecuritizationDAO","getStateWiseLoans","IF Block");
				query.append(" SELECT 	MemberId,");
				query.append(" COUNT(CGPAN),");
				query.append(" SUM(term_loan_sanctioned),");
				query.append(" SUM(approved_amount)");
				query.append(" FROM(SELECT	MemberId,");
				query.append(" CGPAN,Term_Loan_Sanctioned,Approved_Amount,");
				query.append(" MIN(Disbursement_Date)FROM");
				query.append(" (SELECT app.mem_bnk_id || app.mem_zne_id || app.mem_brn_id MemberId,");
				query.append(" vi.cgpan CGPAN,vi.tc_sanctioned_amt Term_Loan_Sanctioned,");
				query.append(" vi.approved_amt Approved_Amount,d.dbr_dt Disbursement_Date");
				query.append(" FROM	ssi_detail ssi,view_appl_amounts vi,");
				query.append(" application_detail app,term_loan_detail trm,");
				query.append(" disbursement_detail d " );
				//query.append(" WHERE	LTRIM(RTRIM(UPPER(app.mem_bnk_id || app.mem_zne_id || app.mem_brn_id)))");
				query.append(" WHERE	app.mem_bnk_id");
				query.append(" IN ");
				query.append(" (");
				query.append(mlisQuery);
				query.append(" )");
				query.append( " AND	ssi.ssi_state_name");
				query.append(" IN");
				query.append(" (");
				query.append(" ?");
				query.append(" )");
				query.append(" AND	ssi.ssi_industry_sector");
				query.append(" IN(");
				query.append(" ?");
				query.append(" )");
				query.append(" AND	app.app_loan_type =");
				query.append(" ?");
				query.append( "AND	app.app_status=" );
				query.append(" ?");
				query.append(" AND	( trm.trm_interest_rate between " );
				query.append(" ? and ");
				query.append(" ? )");
				query.append(" AND	trm.trm_interest_type=");
				query.append(" ?");
				query.append(" AND  (vi.approved_amt ");
				query.append("	between ");
				query.append(" ? and ");
				query.append(" ? ) ");
				query.append(" AND	vi.cgpan ");
				query.append(" NOT IN	(SELECT cgpan FROM loan_pool_application)");
				query.append(" AND	vi.cgpan =");
				query.append(" app.cgpan");
				query.append(" AND	ssi.ssi_reference_number =	app.ssi_reference_number");
				query.append(" AND vi.appref=");
				query.append(" d.app_ref_no");
				query.append(" AND	app.app_ref_no =" );
				query.append(" trm.app_ref_no");
				query.append(" AND	to_date(ADD_MONTHS(vi.submitted_dt" );
				query.append(" ,nvl(trm.trm_tenure,0)))");
				query.append(" <=	to_date(ADD_MONTHS(?,");
				query.append(" ?");
				query.append(" ))");
				query.append(" AND	TRUNC(SYSDATE)");
				query.append(" <=(SELECT ADD_MONTHS(MIN(dbr.dbr_dt),");
				query.append(" ?");
				query.append(" )");
				query.append(" FROM	disbursement_detail dbr");
				query.append(" WHERE	dbr.app_ref_no = ");
				query.append(" vi.APPREF))");
				query.append(" GROUP BY MemberId,CGPAN,Term_Loan_Sanctioned,Approved_Amount)");
				query.append(" GROUP BY MemberId");

			}
			else
			{
				Log.log(Log.DEBUG,"SecuritizationDAO","getStateWiseLoans","Else Block");

				query.append(" SELECT 	MemberId,COUNT(CGPAN),SUM(WC_FB) + SUM(WC_NFB),");
				query.append(" SUM(approved_amount) FROM (SELECT MemberId,");
				query.append(" CGPAN,WC_FB,WC_NFB,Approved_Amount");
				query.append(" FROM");
				query.append(" (SELECT	app.mem_bnk_id || app.mem_zne_id || app.mem_brn_id MemberId,");
				query.append(" vi.cgpan CGPAN,vi.wc_fb_limit WC_FB,");
				query.append(" vi.wc_nfb_limit WC_NFB,");
				query.append(" decode(app.app_enhanced_approved_amt,null, app.app_approved_amount,");
				query.append(" app.app_enhanced_approved_amt) Approved_Amount");
				query.append(" FROM	ssi_detail ssi,view_appl_amounts vi,");
				query.append(" application_detail app,working_capital_detail wcp");
				query.append(" WHERE	app.mem_bnk_id");
				query.append(" IN 	(");
				query.append(mlisQuery);

				query.append(" )");
				query.append(" AND	ssi.ssi_state_name");
				query.append(" IN	(");
				query.append(" ?");
				query.append(" )");
				query.append(" AND	ssi.ssi_industry_sector");
				query.append(" IN	(");
				query.append(" ?");
				query.append(" )");
				query.append(" AND	app.app_loan_type  =");
				query.append(" ?");
				query.append(" AND	app.app_status =");
				query.append(" ?");
				query.append(" AND	(wcp.wcp_interest between");
				query.append(" ? and ");
				query.append(" ?) ");
				query.append(" AND (decode(app.app_enhanced_approved_amt,null,");
				query.append(" app.app_approved_amount,app.app_enhanced_approved_amt)");
				query.append(" between	");
				query.append(" ? and ");
				query.append(" ?) ");
				query.append(" AND	vi.cgpan");
				query.append(" NOT IN	(SELECT cgpan");
				query.append(" FROM	loan_pool_application)");
				query.append(" AND	vi.cgpan 	=");
				query.append(" app.cgpan");
				query.append(" AND	ssi.ssi_reference_number =");
				query.append(" app.ssi_reference_number");
				query.append(" AND	app.app_ref_no");
				query.append(" = wcp.app_ref_no)");
				query.append(" GROUP BY MemberId,CGPAN,WC_FB,");
				query.append(" WC_NFB,Approved_Amount) GROUP BY MemberId");
			}

			String queryStr=query.toString();

			Log.log(Log.DEBUG,"SecuritizationDAO","getStateWiseLoans","Query is "+queryStr);

			PreparedStatement preparedStatement=connection.prepareStatement(queryStr);

			int j=1;

			for(int i=0;i<mlisArray.length;i++)
			{
				//preparedStatement.setString(j,mlisArray[i].substring(0,12));
				preparedStatement.setString(j,mlisArray[i].substring(0,4));
				Log.log(Log.DEBUG,"SecuritizationDAO","getStateWiseLoans","MLIs at J "+j+" is  "+(mlisArray[i].substring(0,12)));
				j++;
			}

			Log.log(Log.DEBUG,"SecuritizationDAO","getStateWiseLoans","After MLIs J is "+j);

			preparedStatement.setString(j,state);
			//Log.log(Log.DEBUG,"SecuritizationDAO","getStateWiseLoans","state is "+state);

			preparedStatement.setString(++j,sector);
			//Log.log(Log.DEBUG,"SecuritizationDAO","getStateWiseLoans","sector is "+sector);

			if(selectCriteria.getLoanType().equals(ApplicationConstants.TC_APPLICATION))
			{
				preparedStatement.setString(++j,ApplicationConstants.TC_APPLICATION);
			}
			else
			{
				preparedStatement.setString(++j,ApplicationConstants.WC_APPLICATION);
			}
			//Log.log(Log.DEBUG,"SecuritizationDAO","getStateWiseLoans","application type is "+ApplicationConstants.TC_APPLICATION);

			preparedStatement.setString(++j,ApplicationConstants.APPLICATION_APPROVED_STATUS);
			//Log.log(Log.DEBUG,"SecuritizationDAO","getStateWiseLoans","application status "+ApplicationConstants.APPLICATION_APPROVED_STATUS);

			preparedStatement.setDouble(++j,selectCriteria.getInterestRate() );
			preparedStatement.setDouble(++j,selectCriteria.getNextInterestRate() );

			//Log.log(Log.DEBUG,"SecuritizationDAO","getStateWiseLoans","interest rate "+selectCriteria.getInterestRate());
			if(selectCriteria.getLoanType().equals(ApplicationConstants.TC_APPLICATION))
			{
				preparedStatement.setString(++j,selectCriteria.getTypeOfInterest() );
			}
			//Log.log(Log.DEBUG,"SecuritizationDAO","getStateWiseLoans","type of interest "+selectCriteria.getTypeOfInterest());

			preparedStatement.setDouble(++j,selectCriteria.getLoanSize());
			preparedStatement.setDouble(++j,selectCriteria.getNextLoanSize());

			//Log.log(Log.DEBUG,"SecuritizationDAO","getStateWiseLoans","Loan size "+selectCriteria.getLoanSize());

			//preparedStatement.setDate(++j,new java.sql.Date(selectCriteria.getEffectiveDate().getTime()));
			if(selectCriteria.getLoanType().equals(ApplicationConstants.TC_APPLICATION))
			{
				java.sql.Date effDate=new java.sql.Date(selectCriteria.getEffectiveDate().getTime());
				
				preparedStatement.setDate(++j,effDate);
				/*
				Calendar cal=Calendar.getInstance();
				cal.setTime(selectCriteria.getEffectiveDate());
				
				Log.log(Log.DEBUG,"SecuritizationDAO","getStateWiseLoans","day "+cal.get(Calendar.DATE));
				Log.log(Log.DEBUG,"SecuritizationDAO","getStateWiseLoans","month "+cal.get(Calendar.MONTH));
				Log.log(Log.DEBUG,"SecuritizationDAO","getStateWiseLoans","year "+cal.get(Calendar.YEAR));
				
				Log.log(Log.DEBUG,"SecuritizationDAO","getStateWiseLoans","eff date "+j+" "+selectCriteria.getEffectiveDate());
				
				preparedStatement.setString(++j,cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR));
				*/
				//preparedStatement.setString(++j,"01-Jan-2001");
				
				preparedStatement.setInt(++j,selectCriteria.getTenure());

				//Log.log(Log.DEBUG,"SecuritizationDAO","getStateWiseLoans","tenure "+selectCriteria.getTenure());

				preparedStatement.setInt(++j, selectCriteria.getTrackRecord());

				//Log.log(Log.DEBUG,"SecuritizationDAO","getStateWiseLoans","track record "+selectCriteria.getTrackRecord());
			}

			//Log.log(Log.DEBUG,"SecuritizationDAO","getStateWiseLoans","Finally "+j);

			ResultSet results=preparedStatement.executeQuery();

			Map mlisMap=new HashMap();

			ArrayList mliWiseData=new ArrayList();
			while(results.next())
			{

				String mliId=results.getString(1);

				MLIWise mliWise=new MLIWise();

				mliWise.setMliName(mliId);
				mliWise.setNoOfLoans(results.getInt(2));
				mliWise.setTotalSanctionedAmt(results.getDouble(3));
				mliWise.setTotalGC(results.getDouble(4));

				mliWiseData.add(mliWise);
			}

			if(mliWiseData.size()!=0)
			{
				stateWise.setState(state);
			}

			stateWise.setMliWise(mliWiseData);

			results.close();
			results=null;

			preparedStatement.close();
			preparedStatement=null;
		}
		catch (SQLException e)
		{
			Log.log(Log.INFO,"SecuritizationDAO","getStateWiseLoans",e.getMessage());

			Log.logException(e);

			throw new DatabaseException("Unable to get State wise loans");
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO,"SecuritizationDAO","getStateWiseLoans","Exited");

		return stateWise;
   }


   public ArrayList getLoanPoolNames() throws DatabaseException
   {
		Log.log(Log.INFO,"SecuritizationDAO","getLoanPoolNames","Entered");

   		ArrayList poolNames=new ArrayList();

		Connection connection=DBConnection.getConnection();

		try
		{
			CallableStatement callable=connection.prepareCall("{?=call packGetAllLoanPool.FUNCGETALLLOANPOOL(?,?)}");

			callable.registerOutParameter(1,Types.INTEGER);
			callable.registerOutParameter(2,Constants.CURSOR);
			callable.registerOutParameter(3,Types.VARCHAR);

			callable.execute();

			int errorCode=callable.getInt(1);

			String error=callable.getString(3);

			Log.log(Log.DEBUG,"SecuritizationDAO","getLoanPoolNames","error and error code "+error+" "+errorCode);

			if(errorCode==Constants.FUNCTION_FAILURE)
			{
				Log.log(Log.ERROR,"SecuritizationDAO","getLoanPoolNames",error);

				callable.close();
				callable=null;

				throw new DatabaseException(error);
			}

			ResultSet results=(ResultSet)callable.getObject(2);

			while(results.next())
			{
				int poolId=results.getInt(1);
				String poolName=results.getString(2);
				String pool=poolId+")"+poolName;
				poolNames.add(pool);
			}

			results.close();
			results=null;
			callable.close();
			callable=null;
		}
		catch (SQLException e)
		{
			Log.log(Log.ERROR,"SecuritizationDAO","getLoanPoolNames",e.getMessage());
			Log.logException(e);

			throw new DatabaseException("Unable get Loan pool Names");
		}
		finally
		{
			DBConnection.freeConnection(connection);
		}

		Log.log(Log.INFO,"SecuritizationDAO","getLoanPoolNames","Exited");
   		return poolNames;
   }
}
