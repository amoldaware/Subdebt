package com.cgtsi.thinclient;


import javax.swing.text.PlainDocument;
import javax.swing.text.AttributeSet;


import java.awt.Toolkit;


/**
 * Class NumberFormatField
 *
 *
 * @author
 * @version %I%, %G%
 */
public class NumberFormatField extends PlainDocument
{

    int length;


    /**
     * Constructor CSDUINumberFormatField
     *
     *
     * @param liNumberType
     * @param liLength
     *
     */
    public NumberFormatField(int liLength)
    {
        length     = liLength;
    }
    /**
     * Method insertString
     *
     *
     * @param offSet
     * @param insertString
     * @param attributeSet
     *
     */
    public void insertString(int offSet, String insertString, AttributeSet attributeSet)
    {

        try
        {
            if(insertString == null)
            {
                return;
            }
            else if(insertString.trim().length() == 0)
            {
                return;
            }
            else if(insertString.length() == 1)    // Insert from Keyboard Entry
            {
                if(insertString.charAt(0) == '.')
                {
                    Toolkit.getDefaultToolkit().beep();

                    return;
                }
                else if(insertString.charAt(0) == '-')
                {
                    Toolkit.getDefaultToolkit().beep();

                    return;
                }
                else if((offSet == 0) && (insertString.charAt(0) == '-'))
                {
                    if(getText(0, getLength()).indexOf('-') >= 0)
                    {
                        Toolkit.getDefaultToolkit().beep();

                        return;
                    }

                    //super.insertString(offSet, insertString, attributeSet);
                    vstoreValue(offSet, insertString, attributeSet);
                }
                else if((offSet != 0) && (insertString.charAt(0) == '-'))
                {
                    Toolkit.getDefaultToolkit().beep();

                    return;
                }
                else
                {
                    if(bisValidInput(insertString))
                    {
                        String lsCurrentText    = getText(0, getLength());
                        String lsBeforeOffSet   = lsCurrentText.substring(0, offSet);
                        String lsAfterOffSet    = lsCurrentText.substring(offSet, lsCurrentText.length());
                        String lsProposedResult = lsBeforeOffSet + insertString + lsAfterOffSet;

                        if(lsProposedResult.indexOf('-') >= 0)
                        {
                            if(lsProposedResult.length() > (length + 1))
                            {
                                Toolkit.getDefaultToolkit().beep();

                                return;
                            }
                        }
                        else
                        {
                            if(lsProposedResult.length() > length)
                            {
                                Toolkit.getDefaultToolkit().beep();

                                return;
                            }
                        }

                        //super.insertString(offSet, insertString, attributeSet);
                        vstoreValue(offSet, insertString, attributeSet);
                    }
                    else
                    {
                        Toolkit.getDefaultToolkit().beep();
                    }
                }
            }
            else                                   // Insert from Copy & Paste
            {
                if(bisValidInput(insertString))
                {
                    String lsCurrentText    = getText(0, getLength());
                    String lsBeforeOffSet   = lsCurrentText.substring(0, offSet);
                    String lsAfterOffSet    = lsCurrentText.substring(offSet, lsCurrentText.length());
                    String lsProposedResult = lsBeforeOffSet + insertString + lsAfterOffSet;

                    if(lsProposedResult.indexOf('-') >= 0)
                    {
                        if(lsProposedResult.length() > (length + 1))
                        {
                            Toolkit.getDefaultToolkit().beep();

                            return;
                        }
                    }
                    else
                    {
                        if(lsProposedResult.length() > length)
                        {
                            Toolkit.getDefaultToolkit().beep();

                            return;
                        }
                    }

                    //super.insertString(offSet, insertString, attributeSet);
                    vstoreValue(offSet, insertString, attributeSet);
                }
                else
                {
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        }
        catch(Throwable loException) {}
    }

    /**
     * Method bisValidInput
     *
     *
     * @param lsValue
     *
     * @return
     *
     */
    private boolean bisValidInput(String lsValue)
    {

        boolean lbValidNumber = false;
        long    llValue       = 0;

        try
        {
            //llValue = new Long(lsValue).longValue();
            llValue       = getBigDecimal(lsValue).longValue();
            lbValidNumber = true;

            if(llValue < 0)
            {
                lbValidNumber = false;
            }
        }
        catch(Throwable loException)
        {
            lbValidNumber = false;
        }

        return lbValidNumber;
    }

    /**
     * Method vstoreValue
     *
     *
     * @param offSet
     * @param insertString
     * @param attributeSet
     *
     * @throws Exception
     *
     */
    private void vstoreValue(int offSet, String insertString, AttributeSet attributeSet) throws Exception
    {
            String lsCurrentText    = getText(0, getLength());
            String lsBeforeOffSet   = lsCurrentText.substring(0, offSet);
            String lsAfterOffSet    = lsCurrentText.substring(offSet, lsCurrentText.length());
            String lsProposedResult = lsBeforeOffSet + insertString + lsAfterOffSet;

            try
            {
                //long llValue = new Long(lsProposedResult).longValue();
                long llValue = getBigDecimal(lsProposedResult).longValue();

                if(llValue < 0)
                {
                    super.insertString(offSet, insertString, attributeSet);
                    //Display your message here.
                    super.remove(offSet, insertString.length());
                }
                else
                {
                    super.insertString(offSet, insertString, attributeSet);
                }
            }
            catch(Throwable loException)
            {
                //
            }
    }

    private java.math.BigDecimal getBigDecimal(String lsValue)
    {

        java.math.BigDecimal loBigDecimal = null;

        if((lsValue == null) || (lsValue.trim().length() == 0) || lsValue.trim().equals("-") || lsValue.trim().equals(".")
                || lsValue.trim().equals("-."))
        {
            loBigDecimal = new java.math.BigDecimal("0");
        }
        else
        {
            loBigDecimal = new java.math.BigDecimal(lsValue);
        }

        return loBigDecimal;
    }
}
