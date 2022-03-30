package com.cgtsi.thinclient;

import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;


/**
 * Class CSDUIDateFormatField
 *
 *
 * @author
 * @version %I%, %G%
 */
public class DateFormatField extends PlainDocument
{

    /** Field ioDateField           */
    JTextComponent ioDateField;

    /** Field isDateFormat           */
    String isDateFormat;

    /** Field isDateSeparator           */
    String isDateSeparator;

    /**
     * Constructor CSDUIDateFormatField
     *
     *
     * @param loDateField
     * @param lsDateFormat
     *
     */
    public DateFormatField(JTextComponent loDateField, String lsDateFormat)
    {

        ioDateField  = loDateField;
        isDateFormat = lsDateFormat;

        if(isDateFormat == null)
        {
            isDateFormat = "dd/MM/yyyy";
        }

        int liCtr = 0;

        while(liCtr < isDateFormat.length())
        {
            if((isDateFormat.charAt(liCtr) == 'd') || (isDateFormat.charAt(liCtr) == 'M') || (isDateFormat.charAt(liCtr) == 'y')
                    || (isDateFormat.charAt(liCtr) == 'h') || (isDateFormat.charAt(liCtr) == 'H')
                    || (isDateFormat.charAt(liCtr) == 'm'))
            {
                liCtr++;
            }
            else
            {
                isDateSeparator = "" + isDateFormat.charAt(liCtr);

                break;
            }
        }
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
                insertString = "";
            }

            if(getLength() < isDateFormat.length())
            {
                //String dateStyle = "  "+isDateSeparator+"  "+isDateSeparator+"    ";
                String dateStyle = "";

                for(int liCtr = getLength(); liCtr < isDateFormat.length(); liCtr++)
                {
                    dateStyle += " ";
                }

                super.insertString(offSet, ioDateField.getText() + dateStyle.substring(getLength(), isDateFormat.length()),
                                   attributeSet);
            }

            if(insertString.length() == 1)
            {
                if(bisValid(offSet, insertString.charAt(0)))
                {
                    super.remove(offSet, insertString.length());
                    super.insertString(offSet, insertString, attributeSet);

                    offSet += insertString.length();

                    if((offSet == 2) || (offSet == 5))
                    {
                        vfillValue(1);
                        vfillValue(4);

                        offSet += 1;
                    }
                    else
                    {
                        if(offSet > 6)
                        {
                            vfillValue(1);
                            vfillValue(4);
                            vfillValue(offSet);
                        }
                        else if(offSet > 3)
                        {
                            vfillValue(1);
                        }
                    }
                }
                else
                {
                    if(insertString.charAt(0) == isDateSeparator.charAt(0))
                    {
                        if(offSet <= 2)
                        {
                            vfillValue(offSet);

                            offSet = 3;
                        }
                        else if(offSet <= 5)
                        {
                            vfillValue(offSet);

                            offSet = 6;
                        }
                    }
                }
            }
            else
            {
                int offSetCount = offSet;

                for(int liCtr = 0; liCtr < insertString.length(); liCtr++)
                {
                    if(bisValid(offSetCount, insertString.charAt(liCtr)))
                    {
                        super.remove(offSetCount, 1);
                        super.insertString(offSetCount, "" + insertString.charAt(liCtr), attributeSet);
                    }

                    offSetCount++;
                }
            }
        }
        catch(BadLocationException loBadLocationException) {}
        catch(Throwable loBadLocationException) {}

        vsetDateSeparator();

        try
        {
            ioDateField.setCaretPosition(offSet);
        }
        catch(Throwable loException) {}
    }

    /**
     * Method remove
     *
     *
     * @param offSet
     * @param len
     *
     */
    public void remove(int offSet, int len)
    {

        try
        {
            super.remove(offSet, len);

            String lsSpaces = "";

            for(int liCtr = 0; liCtr < len; liCtr++)
            {
                lsSpaces += " ";
            }

            super.insertString(offSet, lsSpaces, null);
        }
        catch(BadLocationException loBadLocationException) {}
        catch(Throwable loBadLocationException) {}

        vsetDateSeparator();

        //ioDateField.setCaretPosition(offSet);
        try
        {
            ioDateField.setCaretPosition(offSet);
        }
        catch(Throwable loException) {}
    }

    /**
     * Method vsetDateSeparator
     *
     *
     */
    private void vsetDateSeparator()
    {

        try
        {
            super.remove(2, 1);
            super.insertString(2, "" + isDateSeparator, null);
            super.remove(5, 1);
            super.insertString(5, "" + isDateSeparator, null);
        }
        catch(BadLocationException loBadLocationException) {}
        catch(Throwable loBadLocationException) {}
    }

    /**
     * Method vfillValue
     *
     *
     * @param offSet
     *
     */
    private void vfillValue(int offSet)
    {

        if(offSet <= 2)
        {
            try
            {
                String lsValue = getText(0, 2);

                if(lsValue.trim().length() > 0)
                {
                    int liValue = new Integer(lsValue.trim()).intValue();

                    if(liValue <= 9)
                    {
                        lsValue = "0" + liValue;

                        this.insertString(0, lsValue, null);
                    }
                }
            }
            catch(Throwable loException) {}
        }
        else if(offSet <= 5)
        {
            try
            {
                String lsValue = getText(3, 2);

                if(lsValue.trim().length() > 0)
                {
                    int liValue = new Integer(lsValue.trim()).intValue();

                    if(liValue <= 9)
                    {
                        lsValue = "0" + liValue;

                        this.insertString(3, lsValue, null);
                    }
                }
            }
            catch(Throwable loException) {}
        }
        else
        {
            for(int liCtr = 6; liCtr < (offSet - 1); liCtr++)
            {
                try
                {
                    String lsValue = getText(liCtr, 1);

                    if(lsValue.trim().length() == 0)
                    {
                        this.insertString(liCtr, "0", null);
                    }
                }
                catch(Throwable loException) {}
            }
        }
    }

    /**
     * Method bisValid
     *
     *
     * @param offSet
     * @param lcChar
     *
     * @return
     *
     */
    private boolean bisValid(int offSet, char lcChar)
    {

        if((offSet == 2) || (offSet == 5))
        {
            return true;
        }
        else
        {
            return(lcChar >= '0') && (lcChar <= '9');
        }
    }
}
