//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\admin\\Hint.java

package com.cgtsi.admin;


/**
 * Responsible for diplaying and changing the hint questions and answers.
 */
public class Hint 
{
   private String hintQuestion = null;
   private String hintAnswer = null;
   
   /**
    * @roseuid 39B875C403C6
    */
   public Hint() 
   {
    
   }
   
   /**
    * Access method for the hintQuestion property.
    * 
    * @return   the current value of the hintQuestion property
    */
   public String getHintQuestion() 
   {
      return hintQuestion;
   }
   
   /**
    * Sets the value of the hintQuestion property.
    * 
    * @param aHintQuestion the new value of the hintQuestion property
    */
   public void setHintQuestion(String aHintQuestion) 
   {
      hintQuestion = aHintQuestion;
   }
   
   /**
    * Access method for the hintAnswer property.
    * 
    * @return   the current value of the hintAnswer property
    */
   public String getHintAnswer() 
   {
      return hintAnswer;
   }
   
   /**
    * Sets the value of the hintAnswer property.
    * 
    * @param aHintAnswer the new value of the hintAnswer property
    */
   public void setHintAnswer(String aHintAnswer) 
   {
      hintAnswer = aHintAnswer;
   }
}
