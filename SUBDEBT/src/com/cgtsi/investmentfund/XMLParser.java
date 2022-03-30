// Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine
// Available From: http://www.reflections.ath.cx
// Decompiler options: packimports(3) 

package com.cgtsi.investmentfund;

import com.cgtsi.common.InvalidFileFormatException;
import com.cgtsi.common.Log;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

// Referenced classes of package com.cgtsi.investmentfund:
//            StatementDetail, IFProcessor, TransactionDetail

public class XMLParser
{
    class StatementHandler extends DefaultHandler
    {

        public StatementDetail getStatementDetail()
        {
            return statementDetail;
        }

        public Hashtable getStatementDetails()
        {
            return statementDetails;
        }

        public boolean isError()
        {
            return error;
        }

        public void startElement(String s, String s1, String s2, Attributes attributes)
            throws SAXException
        {
            strValue = "";
            if(isError())
                throw new SAXException("Error occured....Please Check the File format");
            elementName = s2;
            if(elementName.equals("transactions"))
                transactionDetail = new TransactionDetail();
            for(int i = 0; i < attributes.getLength(); i++)
                statementDetails.put(attributes.getQName(i), attributes.getValue(i));

        }

        public void endElement(String s, String s1, String s2)
            throws SAXException
        {
            if(isError())
                throw new SAXException("Error occured....Please Check the File format");
            validate();
            if(s2.equals("transactions"))
            {
                transactionDetailArray.add(transactionDetail);
                int i = transactionDetailArray.size();
            }
            if(s2.equals("statement-details"))
            {
                statementDetail.setTransactionDetail(transactionDetailArray);
                int j = transactionDetailArray.size();
            }
            elementName = "";
        }

        private void validate()
            throws SAXException
        {
            SimpleDateFormat simpledateformat = new SimpleDateFormat("dd/MM/yyyy");
            String s = (String)statementDetails.get("bankname");
            String s1 = (String)statementDetails.get("accountNumber");
            String s2 = (String)statementDetails.get("openingBalance");
            String s3 = (String)statementDetails.get("closingBalance");
            String s4 = (String)statementDetails.get("statementDate");
            if(s != null && !s.equals(""))
            {
                int i = s.length();
                if(i <= 100)
                    statementDetail.setBankName(s);
                else
                    throw new SAXException("Bank name cannot be more than 100 characters");
            } else
            {
                throw new SAXException("Bank Name is required");
            }
            if(s1 != null && !s1.equals(""))
            {
                int j = s1.length();
                if(j <= 20)
                    statementDetail.setAccountNumber(s1);
                else
                    throw new SAXException("Account number cannot be more than 20 characters");
            } else
            {
                throw new SAXException("Account Number is required");
            }
            try
            {
                if(s2 != null && !s2.equals(""))
                {
                    int k = s2.length();
                    Log.log(5, "XMLParser", "validate", "length " + k);
                    if(s2.indexOf('.') > 0)
                    {
                        Log.log(5, "XMLParser", "validate", "index > 0");
                        String s5 = s2.substring(0, s2.indexOf('.'));
                        if(s5.length() > 13)
                            throw new SAXException("Opening Balance Integer part cannot be more than 13 characters");
                        String s7 = s2.substring(s2.indexOf('.') + 1, s2.length());
                        if(s7.length() > 2)
                            throw new SAXException("Opening Balance Decimal part cannot be more than 2 characters");
                        double d2 = Double.parseDouble(s2);
                        statementDetail.setOpeningBalance(d2);
                        Log.log(5, "XMLParser", "validate", "op bal " + statementDetail.getOpeningBalance());
                    } else
                    {
                        Log.log(5, "XMLParser", "validate", "index < 0");
                        if(k <= 13)
                        {
                            Log.log(5, "XMLParser", "validate", "length <= 13");
                            double d = Double.parseDouble(s2);
                            statementDetail.setOpeningBalance(d);
                            Log.log(5, "XMLParser", "validate", "op bal " + statementDetail.getOpeningBalance());
                        } else
                        {
                            throw new SAXException("Opening Balance Integer part cannot be more than 13 characters");
                        }
                    }
                } else
                {
                    throw new SAXException("Opening Balance is required");
                }
            }
            catch(NumberFormatException numberformatexception)
            {
                throw new SAXException("Invalid Opening Balance. It should be amount");
            }
            try
            {
                if(s3 != null && !s3.equals(""))
                {
                    int l = s3.length();
                    if(s3.indexOf('.') > 0)
                    {
                        Log.log(5, "XMLParser", "validate", "index > 0");
                        String s6 = s3.substring(0, s3.indexOf('.'));
                        if(s6.length() > 13)
                            throw new SAXException("Closing Balance Integer part cannot be more than 13 characters");
                        String s8 = s3.substring(s3.indexOf('.') + 1, s3.length());
                        if(s8.length() > 2)
                            throw new SAXException("Closing Balance Decimal part cannot be more than 2 characters");
                        double d3 = Double.parseDouble(s3);
                        statementDetail.setClosingBalance(d3);
                        Log.log(5, "XMLParser", "validate", "cl bal " + statementDetail.getClosingBalance());
                    } else
                    {
                        Log.log(5, "XMLParser", "validate", "index < 0");
                        if(l <= 13)
                        {
                            Log.log(5, "XMLParser", "validate", "cl bal length <= 13");
                            double d1 = Double.parseDouble(s3);
                            statementDetail.setClosingBalance(d1);
                            Log.log(5, "XMLParser", "validate", "cl bal " + statementDetail.getClosingBalance());
                        } else
                        {
                            throw new SAXException("Closing Balance Integer part cannot be more than 13 characters");
                        }
                    }
                } else
                {
                    throw new SAXException("Closing Balance is required");
                }
            }
            catch(NumberFormatException numberformatexception1)
            {
                throw new SAXException("Invalid Closing Balance. It should be amount");
            }
            if(!isValidDDMMYYYYFormat(s4))
                throw new SAXException("Statement date is in invalid date format.It should be in DD/MM/YYYY");
            if(!isValidDate(s4))
                throw new SAXException("Statement date is in invalid date format.It should be in DD/MM/YYYY");
            java.util.Date date = simpledateformat.parse(s4, new ParsePosition(0));
            Date date1 = new Date(date.getTime());
            statementDetail.setStatementDate(date1);
            if("transactionDate".equals(elementName))
            {
                if(strValue == null || strValue.equals(""))
                    throw new SAXException("Transaction date is required");
                if(!isValidDDMMYYYYFormat(strValue))
                    throw new SAXException("Transaction date is in invalid date format.It should be in DD/MM/YYYY");
                if(!isValidDate(strValue))
                    throw new SAXException("Transaction date is in invalid date format.It should be in DD/MM/YYYY");
                transactionDetail.setTransactionDate(strValue);
            }
            if("particulars".equals(elementName))
                if(strValue != null && !strValue.equals(""))
                {
                    int i1 = strValue.length();
                    if(i1 <= 100)
                        transactionDetail.setTransactionFromTo(strValue);
                    else
                        throw new SAXException("Transaction Particulars cannot be more than 100 characters");
                } else
                {
                    throw new SAXException("Transaction Particulars is required");
                }
            if("valueDate".equals(elementName) && strValue != null && !strValue.equals(""))
            {
                if(!isValidDDMMYYYYFormat(strValue))
                    throw new SAXException("Value date is in invalid date format.It should be in DD/MM/YYYY");
                if(!isValidDate(strValue))
                    throw new SAXException("Value date is in invalid date format.It should be in DD/MM/YYYY");
                transactionDetail.setValueDate(strValue);
            }
            if("debitOrCredit".equals(elementName))
                if(strValue.equals("CR") || strValue.equals("DR"))
                    transactionDetail.setTransactionNature(strValue);
                else
                    throw new SAXException("Transaction Nature can only be either Credit or Debit");
            try
            {
                if("withdrawals".equals(elementName))
                    if(strValue != null && !strValue.equals(""))
                    {
                        int j1 = strValue.length();
                        if(strValue.indexOf('.') > 0)
                        {
                            Log.log(5, "XMLParser", "validate", "index > 0");
                            String s9 = strValue.substring(0, strValue.indexOf('.'));
                            if(s9.length() > 13)
                                throw new SAXException("Withdrawal Amount Integer part cannot be more than 13 characters");
                            String s11 = strValue.substring(strValue.indexOf('.') + 1, strValue.length());
                            if(s11.length() > 2)
                                throw new SAXException("Withdrawal Amount Decimal part cannot be more than 2 characters");
                            double d6 = Double.parseDouble(strValue);
                            transactionDetail.setWithdrawals(d6);
                        } else
                        {
                            Log.log(5, "XMLParser", "validate", "index < 0");
                            if(j1 <= 13)
                            {
                                double d4 = Double.parseDouble(strValue);
                                transactionDetail.setWithdrawals(d4);
                            } else
                            {
                                throw new SAXException("Withdrawal Amount Integer part cannot be more than 13 characters");
                            }
                        }
                    } else
                    {
                        throw new SAXException("Withdrawal Amount is required");
                    }
            }
            catch(NumberFormatException numberformatexception2)
            {
                throw new SAXException("Invalid Withdrawal Amount. It should be amount");
            }
            try
            {
                if("deposits".equals(elementName))
                    if(strValue != null && !strValue.equals(""))
                    {
                        int k1 = strValue.length();
                        if(strValue.indexOf('.') > 0)
                        {
                            Log.log(5, "XMLParser", "validate", "index > 0");
                            String s10 = strValue.substring(0, strValue.indexOf('.'));
                            if(s10.length() > 13)
                                throw new SAXException("Deposit Amount Integer part cannot be more than 13 characters");
                            String s12 = strValue.substring(strValue.indexOf('.') + 1, strValue.length());
                            if(s12.length() > 2)
                                throw new SAXException("Deposit Amount Decimal part cannot be more than 2 characters");
                            double d7 = Double.parseDouble(strValue);
                            transactionDetail.setDeposits(d7);
                        } else
                        {
                            Log.log(5, "XMLParser", "validate", "index < 0");
                            if(k1 <= 13)
                            {
                                double d5 = Double.parseDouble(strValue);
                                transactionDetail.setDeposits(d5);
                            } else
                            {
                                throw new SAXException("Deposit Amount Integer part cannot be more than 13 characters");
                            }
                        }
                    } else
                    {
                        throw new SAXException("Deposit Amount is required");
                    }
            }
            catch(NumberFormatException numberformatexception3)
            {
                throw new SAXException("Invalid Deposit Amount. It should be amount");
            }
            if("transactionId".equals(elementName))
                if(strValue != null && !strValue.equals(""))
                {
                    int l1 = strValue.length();
                    if(l1 <= 50)
                        transactionDetail.setTransactionId(strValue);
                    else
                        throw new SAXException("Transaction Id cannot be more than 50 characters");
                } else
                {
                    throw new SAXException("Transaction Id is required");
                }
            if("chequeNumber".equals(elementName) && (strValue != null || !strValue.equals("")))
                transactionDetail.setChequeNumber(strValue);
        }

        public void characters(char ac[], int i, int j)
            throws SAXException
        {
            if(isError())
                throw new SAXException("Error occured....Please Check the File format");
            String s = strValue;
            strValue = (new String(ac, i, j)).trim();
            strValue += s;
            if(strValue.equals("") || elementName.equals(""))
            {
                return;
            } else
            {
                statementDetails.put(elementName, strValue);
                return;
            }
        }

        public void warning(SAXParseException saxparseexception)
        {
            error = true;
        }

        public void error(SAXParseException saxparseexception)
            throws SAXException
        {
            error = true;
        }

        public void fatalError(SAXParseException saxparseexception)
        {
            error = true;
        }

        private final String transactionDate = "transactionDate";
        private final String particulars = "particulars";
        private final String valueDate = "valueDate";
        private final String debitOrCredit = "debitOrCredit";
        private final String withdrawals = "withdrawals";
        private final String deposits = "deposits";
        private final String transactionId = "transactionId";
        private final String chequeNumber = "chequeNumber";
        private final String transactions = "transactions";
        private final String statementdetails = "statement-details";
        private final Hashtable statementDetails = new Hashtable();
        IFProcessor iFProcessor;
        StatementDetail statementDetail;
        private boolean error;
        private String elementName;
        int noOfTransactions;
        private TransactionDetail transactionDetail;
        String strValue;
        ArrayList transactionDetailArray;

        StatementHandler()
        {
            iFProcessor = new IFProcessor();
            statementDetail = new StatementDetail();
            error = false;
            elementName = "";
            noOfTransactions = 0;
            transactionDetail = null;
            strValue = null;
            transactionDetailArray = new ArrayList();
        }
    }


    public XMLParser()
    {
    }

    public boolean isValidDDMMYYYYFormat(String s)
    {
        if(s != null && !s.equals("") && s.trim().length() == 10)
        {
            String s1 = s.trim();
            if(s1.indexOf("/") == 2 && s1.lastIndexOf("/") == 5)
            {
                int i = Integer.parseInt(s1.substring(0, 2));
                int j = Integer.parseInt(s1.substring(3, 5));
                int k = Integer.parseInt(s1.substring(6, 10));
                return validateDate(i, j, k);
            } else
            {
                return false;
            }
        } else
        {
            return false;
        }
    }

    public boolean isValidDate(String s)
    {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date date = simpledateformat.parse(s, new ParsePosition(0));
        return date != null;
    }

    public boolean validateDate(int i, int j, int k)
    {
        if(k > 9999 || k < 1000)
            return false;
        if(j < 1 || j > 12 || i < 1 || i > 31)
            return false;
        if((j == 4 || j == 6 || j == 9 || j == 11) && i == 31)
            return false;
        if(j == 2)
            if(k % 4 == 0 && (k % 100 != 0 || k % 400 == 0))
            {
                if(i > 29)
                    return false;
            } else
            if(i > 28)
                return false;
        return true;
    }

    public StatementDetail xmlParse(String s)
        throws InvalidFileFormatException
    {
        StatementDetail statementdetail = new StatementDetail();
        try
        {
            SAXParserFactory saxparserfactory = SAXParserFactory.newInstance();
            saxparserfactory.setNamespaceAware(true);
            saxparserfactory.setValidating(true);
            SAXParser saxparser = saxparserfactory.newSAXParser();
            StatementHandler statementhandler = new StatementHandler();
            saxparser.getParser().setDTDHandler(statementhandler);
            saxparser.parse(new File(s), statementhandler);
            statementdetail = statementhandler.getStatementDetail();
            ArrayList arraylist = statementdetail.getTransactionDetail();
            int i = arraylist.size();
        }
        catch(FactoryConfigurationError factoryconfigurationerror)
        {
            throw new InvalidFileFormatException(factoryconfigurationerror.getMessage());
        }
        catch(ParserConfigurationException parserconfigurationexception)
        {
            throw new InvalidFileFormatException(parserconfigurationexception.getMessage());
        }
        catch(SAXException saxexception)
        {
            throw new InvalidFileFormatException(saxexception.getMessage());
        }
        catch(IOException ioexception)
        {
            throw new InvalidFileFormatException("Enter a valid XML File");
        }
        return statementdetail;
    }
}
