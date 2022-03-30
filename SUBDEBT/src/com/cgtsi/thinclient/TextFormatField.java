package com.cgtsi.thinclient;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class TextFormatField extends PlainDocument {

	private int maxLength;
	

	 public TextFormatField(int maxLength) {
		  super();
		  this.maxLength = maxLength;		
	   }

public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
      if (getLength() + str.length() > maxLength) {
         Toolkit.getDefaultToolkit().beep();
         return;
      }
      else {
          super.insertString(offset, str, attr);
         }
      return;
   }


}