package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import BoggleGUI.BoggleGui;
import model.BoggleBoard;
import model.BoggleGame;
import model.BogglePlayer;
import model.Dictionary;
import model.HumanPlayer;
import model.WordSet;
import utilities.BoggleConstants;
import utilities.EggTimer;
import utilities.TickListener;
import utilities.Utilities;
import view.file.BoggleFileIO;
import view.text.BoggleTextIO;

/**
 * BoggleListener - handles events generated from BoggleWest.
 * 
 * @author Michael Norotn
 * @version HW18 (16 Nov. 2019)
 *
 */
public class BoggleListener extends WindowAdapter  
                implements ActionListener, TickListener, BoggleConstants {

    private BoggleGui gui;
    
    private BoggleFileIO fileIO; // added for PA2
    private BoggleGame game;
    private BoggleTextIO io;
    
    /**
     * Explicit value constructor.
     * 
     * @param frame - the BoggleWest frame
     */
    public BoggleListener( BoggleGui frame ) {
        
        this.gui = frame;
        
        fileIO = new BoggleFileIO();
        game = new BoggleGame();
        io = new BoggleTextIO();

    } // explicit value constructor

    /**
     * Responds to button presses.
     * 
     * @param e - the ActionEvent object
     */
    @Override
    public void actionPerformed( ActionEvent e ) {

        if ( e.getSource() instanceof JButton ) {
            handleButtonEvents( ( JButton )e.getSource() );
        }
        if ( e.getSource() instanceof JRadioButton ) {
            handleRadioButton( ( JRadioButton )e.getSource() );
        }

    } // method actionPerformed
    
    /**
     * Responds to the timer, executes every second.
     * 
     * @param timer - the EggTimer generating the ticks
     */
    @Override
    public void tick( EggTimer timer ) {

        gui.getWest().getTimeLabel().setText( timer.getTimeLeft() );
        
        if ( timer.getSecondsLeft() == 0 ) {
            gui.getWest().getRoundButton().setEnabled( true );
            gui.getWest().getTextArea().setEnabled( false );
            endOfRound();

        } // end if

    } // method tick

    /**
     * Respond to window closed.
     * 
     * @param e the WindowEvent object
     */
    @Override
    public void windowClosing( WindowEvent e ) {
        try {
            if ( !writeFile() ) {
                io.display( "Unable to write new File\n" );
                io.display( "Original Dictionary preserved.\n" );

            } else {
                io.display( "No Dictionary - Unable to Play!!!" + NL );
                
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            System.exit( 0 );
        }
    } // method windowClosing
    
    /**
     * run - Play a game of boggle.
     * 
     * Modification: PA02 - modified for PA2 to test for success of dictionary
     * open PA03 - modified to handle multiple games
     *
     * @throws IOException The IOException
     */
    public void run() throws IOException {

        if ( readFile() ) {
            
            beginGame();
            
        } // end if

    } // method playGame
    
    /**
     * Add the list of words from the player to the Player's word list.
     * 
     * Modification: PA02 - added difficulty level to param of player
     *                    - refactor: add null and length tests
     *               PA03 - remove difficulty level - handle this at end
     *                      of round
     *                          
     *
     * @param words The words to add
     */
    private void addWordsToPlayer( String[] words ) {

        if ( words != null ) {
            BogglePlayer player = game.getPlayer( HUMAN );

            for ( String word : words ) {
                
                if ( Utilities.isValidWord( word ) ) {
                    HumanPlayer human = (HumanPlayer) player;
                    human.add( word );

                } // end if
                
            } // end for

        } // end if

    } // method addWordsToPlayer
    
    /**
     * Handles beginning of game events.
     * 
     * Modification: P0A2 - added call to readFile 
     *                    - added code to get/set pointsToWin and difficulty 
     *               PA03 - removed call to readFile
     *               
     * @throws IOException The IOException
     */
    private void beginGame() throws IOException {
        gui.setListener();
    }

    private void endOfRound() {
        String[] words;
        
       words = gui.getWest().getTextArea().getText().split( "\\s+" );
       
       addWordsToPlayer( words );
       
       gui.getWest().getRoundButton().setEnabled( true );
       gui.getWest().getTextArea().setEnabled( false );
       gui.getEast().setTextEnabled( false );
       setTextAreaSet();
       handleScoring();
    }
    
    private void handleScoring() {
        BogglePlayer computer = game.getPlayer( COMPUTER );
        BogglePlayer human = game.getPlayer( HUMAN );
        computer.computeScore();
        human.computeScore();
        gui.getEast().getYouRoundScoreLabel().setText( 
                human.getRoundScore() + "" );
        gui.getEast().getYouTotalScorelLabel().setText( 
                human.getTotalScore() + "" );
        gui.getEast().getComputerRoundScoreLabel().setText( 
                computer.getRoundScore() + "" );
        gui.getEast().getComputerTotalScorelLabel().setText( 
                computer.getTotalScore() + "" );
    }

    private String iteratorList( WordSet set ) {
        String text = "";
        
        while ( set.iterator().hasNext() ) {
            text += set.iterator().next() + "\n";
        }
        
        return text;
    }
    
    /**
     * Generate the WordSets for a user.
     * 
     * Modifications: PA03 - new for PA3
     *
     * @param players A BogglePlayer array
     * @return an array of WordSets
     * @throws IOException The IOException
     */
    private WordSet[] generateSets( BogglePlayer[] players ) {

        WordSet[] sets = new WordSet[ 4 ];

        // don't try this if the incoming array is not correct
        if ( players != null && players.length == 2 ) {
            
            // generate the valid/invalid lists for each player
            for ( int i = 0; i < players.length; i++ ) {
                
                players[ i ].validate();
                
            } // end for

            // get the intersection of the two lists
            sets[ COMMON_WORDS ] = players[ HUMAN ].getSet()
                            .union( players[ COMPUTER ].getSet() );

            // human list - computer list
            sets[ HUMAN_UNIQUE ] = players[ HUMAN ].getValidSet()
                            .difference( players[ COMPUTER ].getValidSet() );

            // computer list - human list
            sets[ COMPUTER_UNIQUE ] = players[ COMPUTER ].getValidSet()
                            .difference( players[ HUMAN ].getValidSet() );

            // human invalid list
            sets[ INVALID_WORDS ] = players[ HUMAN ].getInvalidSet();

            // set the unique list for each player
            players[ HUMAN ].setUniqueSet( sets[ HUMAN_UNIQUE ] );
            players[ COMPUTER ].setUniqueSet( sets[ COMPUTER_UNIQUE ] );

        } // end if

        return sets;

    } // method generateSets
    
    /**
     * Handle JButton events. Determine which button and call appropiate
     * method to handle the specific button.
     * 
     * @param btn The JButton
     */
    private void handleButtonEvents( JButton btn ) {

        if ( btn.getText().equals( "Start Round" ) ) {

            handleStartRound( btn );
        } 
        if ( btn.getText().equals( "Quit" ) ) {
            System.exit( 0 );
        }
        if ( btn.getText().equals( "Restart" ) ) {
            handleRestart();
        }
        if ( btn.getText().equals( "Reject Words" ) ) {
         handleRejectWords();   
        }
    }
    
    private void handleRejectWords() {
        JTextArea youText = gui.getEast().getYouTextField();
        String text = "";
        String done = "";
        BogglePlayer human = game.getPlayer( HUMAN );
        do {
            text = youText.getSelectedText();
            if ( text == null ) {
                done = text;
                text = "";
            } else {
                youText.getSelectedText().replaceAll( text, "" );
            }
            human.getRejectSet().add( text );
        } while( done != null );
    }
    
    private void handleRestart() {
        game.startNewGame();
    }
    
    private void handleRadioButton( JRadioButton e ) {
        for ( int i = 1; i < 11; i++ ) {
            if ( e.getText().equals( Integer.toString( i ) ) ) {
                handleRestart();
                game.setDifficulty( i );
            }
        }
        
    }
    
    /**
     * Handle start round button actions.
     * 
     * @param btn The "Start Round" JButton
     */
    private void handleStartRound( JButton btn ) {
        
        EggTimer timer;

        BoggleBoard board = BoggleBoard.getBoggleBoard();
        JButton[][] gridButtons = gui.getWest().getGridButtons();

        game.playRound();

        timer = new EggTimer( 10 );
        timer.addTickListener( this );
        gui.getWest().getTimeLabel().setText( "3:00" );

        for ( int row = 0; row < 4; row++ ) {
            for ( int col = 0; col < 4; col++ ) {
                gridButtons[ row ][ col ]
                                .setText( "" + board.getCell( row, col ) );
                
            } // end for
            
        } // end for

        gui.getWest().getRoundButton().setEnabled( false );
        gui.getWest().getTextArea().setEnabled( true );
        gui.getWest().getTextArea().setText( "" );
        gui.getWest().getTextArea().requestFocus();
        
        if ( timer.getSecondsLeft() == 0 ) {
            endOfRound();
        }
  
    }
    
    private void setTextAreaSet() {
        BogglePlayer human = game.getPlayer( HUMAN );
        BogglePlayer computer = game.getPlayer( COMPUTER );
        
        BogglePlayer[] players = new BogglePlayer[ 2 ];
        
        String text = "";
        players[ HUMAN ] = human;
        players[ COMPUTER ] = computer;
        
        WordSet[] sets = generateSets( players );
        
        text = iteratorList( sets[ HUMAN_UNIQUE ] );
        gui.getEast().getYouTextField().setText( text );
        text = iteratorList( sets[ COMPUTER_UNIQUE ] );
        gui.getEast().getComputerTextField().setText( text );
        text = iteratorList( sets[ COMMON_WORDS ] );
        gui.getEast().getCommonTextField().setText( text );
        text = iteratorList( sets[ INVALID_WORDS ] );
        gui.getEast().getInvalidTextField().setText( text );
    }
    
    /**
     * Read file and populate Dictionary.
     *
     * Modifications: PA02 - added for PA2
     *
     * @return true if the file can be read
     * @throws IOException The IOException
     **/
    private boolean readFile() throws IOException {

        Dictionary dict = Dictionary.getBoggleDictionary();

        boolean canRead = fileIO.open( READER );

        if ( canRead ) {
            String word = fileIO.readLine();
            while ( word != null ) {
                dict.add( word.toLowerCase() );
                word = fileIO.readLine();

            } // end while

            fileIO.close( READER );

        } // end if

        return canRead;

    } // method readFile
    
    /**
     * Write file from Dictionary - if over 300 words randomly remove words
     * in excess of 300.
     *
     * Modificationa: PA02 - added for PA2 (alternate version)
     *
     * @return true if writing is possible
     * @throws IOException The IOException
     **/
    private boolean writeFile() throws IOException {

        boolean canWrite = fileIO.open( BoggleFileIO.WRITER );

        Dictionary dict = Dictionary.getBoggleDictionary();
        ArrayList< String > dictList = dict.getDictionary();

        if ( canWrite ) {

            Random rand = new Random();

            // randomly remove size() - 300
            while ( dictList.size() > 300 ) {

                dictList.remove( rand.nextInt( dictList.size() ) );

            } // end while -- should be 300 left

            for ( int index = 0; index < dictList.size(); index++ ) {

                // write this if it hasn't already been written
                fileIO.write( dictList.get( index ) );

            } // end for

            fileIO.close( BoggleFileIO.WRITER );

        } // end if

        return canWrite;
    }

} // class BoggleListener
