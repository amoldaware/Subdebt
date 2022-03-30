package com.cgtsi.thinclient;


import javax.swing.text.PlainDocument;
import javax.swing.text.AttributeSet;


import java.awt.Toolkit;



/**
 * Class DecimalFormatField
 *
 *
 * @author
 * @version %I%, %G%
 */
public class DecimalFormatField extends PlainDocument
{

    int integerLimit = 0;

    int fractionLimit = 0;


    /**
     * Constructor DecimalFormatField
     *
     *
     * @param liNumberType
     * @param liIntegerLimit
     * @param liFractionLimit
     *
     */
    public DecimalFormatField(int integerLimit, int fractionLimit)
    {

        this.integerLimit  = integerLimit;
        this.fractionLimit = fractionLimit;
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
                if(insertString.charAt(0) == '-')
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

                    super.insertString(offSet, insertString, attributeSet);
                }
                else if((offSet != 0) && (insertString.charAt(0) == '-'))
                {
                    Toolkit.getDefaultToolkit().beep();

                    return;
                }
                else if(insertString.charAt(0) == '.')
                {
                    if(getText(0, getLength()).indexOf('.') >= 0)
                    {
                        Toolkit.getDefaultToolkit().beep();

                        return;
                    }

                    //super.insertString(offSet, insertString, attributeSet);
                    vstoreValue(offSet, insertString, attributeSet);
                }
                else
                {
                    if(bisValidInput(insertString))
                    {
                        String lsCurrentText    = getText(0, getLength());
                        String lsBeforeOffSet   = lsCurrentText.substring(0, offSet);
                        String lsAfterOffSet    = lsCurrentText.substring(offSet, lsCurrentText.length());
                        String lsProposedResult = lsBeforeOffSet + insertString + lsAfterOffSet;

                        if(lsProposedResult.indexOf('.') >= 0)
                        {
                            String lsIntegerPart    = "";
                            String lsFractionPart   = "";
                            int    liDotPosition    = lsProposedResult.indexOf('.');
                            int    liIntegerLength  = integerLimit;
                            int    liFractionLength = fractionLimit;

                            if(lsProposedResult.indexOf('-') >= 0)
                            {
                                liIntegerLength++;
                            }

                            lsIntegerPart  = lsProposedResult.substring(0, liDotPosition);
                            lsFractionPart = lsProposedResult.substring(liDotPosition + 1, lsProposedResult.length());

                            if(lsIntegerPart.length() > liIntegerLength)
                            {
                                Toolkit.getDefaultToolkit().beep();

                                return;
                            }

                            if(lsFractionPart.length() > liFractionLength)
                            {
                                Toolkit.getDefaultToolkit().beep();

                                return;
                            }

                            //super.insertString(offSet, insertString, attributeSet);
                            vstoreValue(offSet, insertString, attributeSet);
                        }
                        else
                        {
                            String lsIntegerPart   = "";
                            int    liIntegerLength = integerLimit;

                            if(lsProposedResult.indexOf('-') >= 0)
                            {
                                liIntegerLength++;
                            }

                            lsIntegerPart = lsProposedResult.substring(0, lsProposedResult.length());

                            if(lsIntegerPart.length() > liIntegerLength)
                            {
                                Toolkit.getDefaultToolkit().beep();

                                return;
                            }
                            vstoreValue(offSet, insertString, attributeSet);
                        }
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

                    if(lsProposedResult.indexOf('.') >= 0)
                    {
                        String lsIntegerPart    = "";
                        String lsFractionPart   = "";
                        int    liDotPosition    = lsProposedResult.indexOf('.');
                        int    liIntegerLength  = integerLimit;
                        int    liFractionLength = fractionLimit;

                        if(lsProposedResult.indexOf('-') >= 0)
                        {
                            liIntegerLength++;
                        }

                        lsIntegerPart  = lsProposedResult.substring(0, liDotPosition);
                        lsFractionPart = lsProposedResult.substring(liDotPosition + 1, lsProposedResult.length());

                        if(lsIntegerPart.length() > liIntegerLength)
                        {
                            Toolkit.getDefaultToolkit().beep();

                            return;
                        }

                        if(lsFractionPart.length() > liFractionLength)
                        {
                            Toolkit.getDefaultToolkit().beep();

                            return;
                        }
                        vstoreValue(offSet, insertString, attributeSet);
                    }
                    else
                    {
                        String lsIntegerPart   = "";
                        int    liIntegerLength = integerLimit;

                        if(lsProposedResult.indexOf('-') >= 0)
                        {
                            liIntegerLength++;
                        }

                        lsIntegerPart = lsProposedResult.substring(0, lsProposedResult.length());

                        if(lsIntegerPart.length() > liIntegerLength)
                        {
                            Toolkit.getDefaultToolkit().beep();

                            return;
                        }

                        vstoreValue(offSet, insertString, attributeSet);
                    }
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

        try
        {
            double ldNumber = getBigDecimal(lsValue).doubleValue();

            lbValidNumber = true;

            if(ldNumber < 0.0)
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
			//double ldValue = new Double(lsProposedResult).doubleValue();
			double ldValue = getBigDecimal(lsProposedResult).doubleValue();

			if(ldValue < 0.0)
			{
				super.insertString(offSet, insertString, attributeSet);
//				JOptionPane.showMessageDialog(panel, "Enter Valid Values", "Error", JOptionPane.ERROR_MESSAGE);
				super.remove(offSet, insertString.length());
			}
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
