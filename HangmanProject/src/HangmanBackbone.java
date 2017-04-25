/********************************************************************
*
*File: HangmanBackbone.java
*Project: Final Project - CS 144 - Dr.Christie
*Author: Leslie Murphy and Christopher Davis
*Description: Java code with static methods to assist the GUI
*Date: 12-5-15
*Comments:
*
*********************************************************************/

// -- Import Statements -- //
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

/**  Java code with static methods to assist the GUI */
public class HangmanBackbone
{
   // -- Instance Variables -- //
   private Scanner words;
   private Random random = new Random();
   private String[] options;
   
   private String guesses;
   private char currentGuess;//the users current guess
   private String currentWord;
   private int lettersCorrect;
   private char[] correct;
   private int wrong;
   private int winLose;
   
   // -- Constructor -- //
   public HangmanBackbone() throws FileNotFoundException
   {
	   File file = new File("words.txt");
	   options = new String[50];
	   words = new Scanner(file);
	   for (int i=0; i<50; i++)
	   {
	   	options[i] = words.nextLine();
	   }
	   randomChoice();
	   
	   wrong = 0;
	   lettersCorrect = 0;
	   correct = new char[currentWord.length()];
	   guesses = "";

   }
   
   /** Randomly selects a word from the word array */
   public void randomChoice()
   {
	   int i = random.nextInt(50);
	   currentWord = options[i];
   }
   
   /** Checks to see if its one char then sets it to currentGuess 
   @param guess the users current guess*/
   public void setCurrentGuess(char guess)
   {
	   currentGuess = guess;
      if (!Character.isAlphabetic(currentGuess))
	   {
	      currentGuess = '\0';  
	   }
      else
      {
         guesses = guesses + currentGuess + ", ";
      }
   }
   
   /** Returns a string of guessed letters
   @return the guessed letters */
   public String getGuesses()
   {
	   return guesses;
   }
   
   /* Gets the current word for guessing
   @return the current word */
   public String getWord()
   {
	   return currentWord;
   }
   
   /** Get the number of incorrest guesses
   @return the number of incorrect guesses */
   public int getWrong()
   {
	   return wrong;
   }
   
   /** Returns the number of correct letters
   @return the number of correct letters guessed */
   public int getLettersCorrect()
   {
	   return lettersCorrect;
   }
   
   /** Looks through the currentWord and finds and correct matches for current guess */
   public void letterLooker()
   {
	   boolean correctness = false;
	   for (int i=0; i<currentWord.length(); i++)
	   {
	      if (currentWord.charAt(i) == currentGuess) 
	   	{
	   	   correctness = true;
	   		lettersCorrect ++;
            
            boolean foundAlready = false;
            for (int j=0; j<correct.length; j++)
            {
               if (correct[j] == currentGuess)
                  foundAlready = true;
            }
            
            if (!foundAlready)
	   		   correct[i] = currentGuess;
	   	}
	   }
	   	
	   if (!correctness)
	   {
	   	wrong ++;
	   }
   }
   
   /** Prints dashes then displays the correst letter when guessed 
   @return the corresponding dash or guess*/
   public String printWord()
   {
	   String word = "";
	   for (int i=0; i < currentWord.length(); i++)
	   {
		   boolean found = false;
		   for(int j=0; j < correct.length; j++)
		   {
			   if (correct[j] == currentWord.charAt(i))
			   {
				   word += correct[j] + "  ";
				   found = true;
			   }
		   }
		    
		   if (!found)
		   {
			   word += "_ ";
		   }
	   }
	   return word;
   }
   
   /** Determines if the user has won or lost 
   @return a value associcted with win, lose, or still playing */
   public int winLose()
   {
	   if (currentWord.length() == lettersCorrect)
	   {
		   winLose = 1;
		   newGame();
	   }
	   else if (wrong == 7)//if all body parts are drawn
		   winLose = -1;
	   else
		   winLose = 0;
	   
	   return winLose;
   }
   
   /** Starts a new game */
   public void newGame()
   {
	   randomChoice();
	   guesses = "";
	   currentGuess = '\0';
	   winLose = 0;
	   lettersCorrect = 0;
	   wrong = 0;
      correct = new char[currentWord.length()];
   }
}