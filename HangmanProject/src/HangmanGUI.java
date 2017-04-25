/********************************************************************
*
*File: HangmanGUI.java
*Project: Final Project - CS 144 - Dr.Christie
*Author: Leslie Murphy and Christopher Davis
*Description: Java code to implement a hangman GUI
*Date: 12-5-15
*Comments:
*
*********************************************************************/

// -- Import Statements -- //
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.awt.Font;

/** Java code to implement a hangman GUI */
public class HangmanGUI extends JFrame
{
   // -- Class Constants -- //
   private final int HEIGHT = 1024;
   private final int WIDTH = 1280;
   
   // -- Instance Variables -- //
   private JLabel guessedLabel;
   private JLabel nextGuessLabel;
   private JLabel wordLabel;
   private JLabel winLoseLabel;
   private JLabel hangmanPicture; //The label on which ImageIcon of hangman is displayed
   private JLabel emptyLabel;
   
   private JButton newGame;
   private JButton submitLetter;
   
   private JTextField userTextField;
   
   private ButtonListener buttonEvent;
   
   private JPanel northPanel;    
   private JPanel centerPanel; 
   private JPanel innerCenterPanel; 
   private JPanel southPanel;
   
   private HangmanBackbone hangmanBackbone;//Instance of HangmanBackbone
    
   //ImageIcons for displaying hangman
   private ImageIcon zeroWrong;
   private ImageIcon oneWrong;
   private ImageIcon twoWrong;
   private ImageIcon threeWrong;
   private ImageIcon fourWrong;
   private ImageIcon fiveWrong;
   private ImageIcon sixWrong;
   
   //Instantiates
   ImageIcon[] hangman;
    
   
   // -- Constructor -- //
   public HangmanGUI() throws IOException
   {
         
      //Instance of HangmanBackbone
      hangmanBackbone = new HangmanBackbone();
         
      //Sets title, size, layout, and close opertion of the JFrame
      this.setTitle( "Hangman" );
      this.setSize( WIDTH, HEIGHT );
      this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
      this.setLayout( new BorderLayout() );
      
      //Instantiates ActionListener
      buttonEvent = new ButtonListener();
      
      //Instantiates panels
      northPanel = new JPanel();
      centerPanel = new JPanel();
      innerCenterPanel = new JPanel();
      southPanel = new JPanel();
      
      //Set Layouts for panels
      northPanel.setLayout( new FlowLayout() );
      centerPanel.setLayout( new FlowLayout() );
      innerCenterPanel.setLayout( new BorderLayout() );
      southPanel.setLayout( new FlowLayout() );
           
      //Instantiates Pictures
      zeroWrong = new ImageIcon("ZeroWrongGuesses.jpg");
      oneWrong = new ImageIcon("OneWrongGuess.jpg");
      twoWrong = new ImageIcon("TwoWrongGuesses.jpg");
      threeWrong = new ImageIcon("ThreeWrongGuesses.jpg");
      fourWrong = new ImageIcon("FourWrongGuesses.jpg");
      fiveWrong = new ImageIcon("FiveWrongGuesses.jpg");
      sixWrong = new ImageIcon("SixWrongGuesses.jpg");
      
      //Array of ImageIcons
      hangman = new ImageIcon[7];
      hangman[0] = zeroWrong;
      hangman[1] = oneWrong;
      hangman[2] = twoWrong;
      hangman[3] = threeWrong;
      hangman[4] = fourWrong;
      hangman[5] = fiveWrong;
      hangman[6] = sixWrong; 
          
      //Build the elements
      buildButton(); 
      buildLabels();
      buildTextField();
      displayHangman();
      
      //Add elements to JPanels
      northPanel.add( guessedLabel );
      northPanel.add( emptyLabel );
      northPanel.add( nextGuessLabel );
      northPanel.add( userTextField );
      northPanel.add( submitLetter ); 
      
      innerCenterPanel.add( winLoseLabel, BorderLayout.NORTH );
      innerCenterPanel.add( newGame, BorderLayout.SOUTH );     
      
      centerPanel.add( hangmanPicture );
      centerPanel.add( innerCenterPanel );
      
      southPanel.add( wordLabel );
     
      //Add panels to the frame
      this.add( northPanel, BorderLayout.NORTH );
      this.add( centerPanel, BorderLayout.CENTER );
      this.add( southPanel, BorderLayout.SOUTH );
   }
   
   /** Builds the buttons for display */
   public void buildButton()
   {
      //Instantiate Button
      newGame = new JButton( "New Game" );
      submitLetter = new JButton( "Submit Letter");
      
      //Attach an Action Listener
      newGame.addActionListener( buttonEvent );  
      submitLetter.addActionListener( buttonEvent );   
      
      //Sets Action Commands
      newGame.setActionCommand("n");
      submitLetter.setActionCommand("s");  
   }
   
   /** Builds the labels  for dsiplay */
   public void buildLabels()
   {
      hangmanPicture = new JLabel(); //Label that displays the hangman ImageIcon
      emptyLabel = new JLabel("                                 ");
      guessedLabel = new JLabel( "Letters you havd guessed: " );
      nextGuessLabel = new JLabel( "Please enter your guess:" );
      wordLabel = new JLabel( hangmanBackbone.printWord() ); //displays number of dashes for each word            
      winLoseLabel = new JLabel();//displays if/when the player wins/loses
      
      //Changes the font for the wordLabel and winLoseLabel
      wordLabel.setFont( new Font("TimesRoman", Font.PLAIN, 80));
      winLoseLabel.setFont( new Font("TimesRoman", Font.PLAIN, 40) );
   }
   
   /**Builds the textField for display */
   public void buildTextField()
   {
      //Instantiate Text Field
      userTextField = new JTextField( 10 );
   
   }

   /** Displays the correst ImageIcon of the hangman for each incorrect guess */
   public void displayHangman()
   {
      hangmanPicture.setIcon( hangman[hangmanBackbone.getWrong()] );
   }
   
   /** Displays the You Win/You Lose label when the user wins/loses */
   public void labelChooser()
   {
	   if (hangmanBackbone.getWrong() == 6)
		   winLoseLabel.setText("YOU LOSE!!!!!!!!!  The word was: " + hangmanBackbone.getWord());
	   else if (hangmanBackbone.getLettersCorrect() == hangmanBackbone.getWord().length())
	      winLoseLabel.setText("YOU WIN!!!!!!!!!");
   }
   
   /** Retrieves the word that you are guessing
   @return the Word you're guessing as a String */
   public String getWord()
   {
      return hangmanBackbone.getWord();  
   }
   
   
   // -- Inner Classes -- //
   //Button Listener
   class ButtonListener implements ActionListener
   {
      public void actionPerformed( ActionEvent buttonEvent )
      {
          String actionCommand = buttonEvent.getActionCommand(); //Retrieves action command for comaparsin
          
          if( actionCommand == "n")
          {
        	   hangmanBackbone.newGame();
            userTextField.setText("");
            guessedLabel.setText("Letters you havd guessed: " + hangmanBackbone.getGuesses()); 
            wordLabel.setText(hangmanBackbone.printWord());
            winLoseLabel.setText("");
            displayHangman();
          }     
          else //actionCommand == 's'
          {
            String text = userTextField.getText();
            userTextField.setText("");
            displayHangman();
            hangmanBackbone.setCurrentGuess( text.charAt( 0 ) );
            guessedLabel.setText("Letters you havd guessed: " + hangmanBackbone.getGuesses()); 
            hangmanBackbone.letterLooker();
            wordLabel.setText(hangmanBackbone.printWord());
            labelChooser();
            displayHangman();
          }
      }     
   }
     
   // -- Main Method -- //
   public static void main(String[] args) throws IOException
   {
      HangmanGUI gui = new HangmanGUI();
      gui.setVisible(true);
      System.out.println(gui.getWord());
   }
}//End of class HangmanGUI
