package BoggleGUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class BoggleEast extends JFrame {

    private ButtonGroup group;
    
    private String[] pointsAllowed;
    
    private JComboBox< String > pointsNeedBox;
   
    private JLabel difficultyLabel;
    private JLabel commonLabel;
    private JLabel invalidLabel;
    private JLabel pointsToWinLabel;
    private JLabel roundLabel;
    private JLabel youLabel;
    private JLabel computerLabel;
    private JLabel humanRoundScore;
    private JLabel humanTotalScore;
    private JLabel computerRoundScore;
    private JLabel computerTotalScore;
    
    private JButton rejectWordButton;
    private JButton restartButton;
    private JButton quitButton;
    
    private JPanel buttonPanel;
    private JPanel gameBottomPanel;
    private JPanel pointsToWinPanel;
    private JPanel mainPanel;
    private JPanel gamePanel;
    private JPanel scoringPanel;
    private JPanel scoringTopPanel;
    private JPanel scorePanel;
    private JPanel difficultyPanel;
    private JPanel textBoxPanel;
    private JPanel youTextPanel;
    private JPanel commonTextPanel;
    private JPanel computerTextPanel;
    private JPanel invalidTextPanel;
    
    private JTextArea youTextField;
    private JTextArea computerTextField;
    private JTextArea commonTextField;
    private JTextArea invalidTextField;
 
    private JRadioButton[] levels; 
  
    public BoggleEast () {
        creatComponents();
        setLayout();
        setComboxBox();
        setRadioButtons();
        setComponents();
        setGamePanel();
        setTextFieldPanel();
        addComponents();
        addBorder();
        
        pack();
        setVisible( true );
    }
    
    private void addBorder() {
        CompoundBorder gameBorder = new CompoundBorder(
                new EmptyBorder( 5, 5, 5, 5 ),
                new TitledBorder( new EtchedBorder(), "Game" ) );
        
        CompoundBorder scoreBorder = new CompoundBorder(
                new EmptyBorder( 5, 5, 5, 5 ),
                new TitledBorder( new EtchedBorder(), "Score" ) );
        CompoundBorder internalBorder = new CompoundBorder(
                new EmptyBorder( 5, 5, 5, 5 ), new EtchedBorder() );
        
        gamePanel.setBorder( gameBorder );
        scoringPanel.setBorder( scoreBorder );
        youTextField.setBorder( internalBorder );
        commonTextField.setBorder( internalBorder );
        computerTextField.setBorder( internalBorder );
        invalidTextField.setBorder( internalBorder );
    }
    
    private void addComponents() {
        difficultyPanel.add( difficultyLabel );
        for ( int i = 0; i < 10; i++ ) {
            difficultyPanel.add( levels[ i ] );
        } 
        pointsToWinPanel.add( pointsToWinLabel );
        pointsToWinPanel.add( pointsNeedBox );
        
        gameBottomPanel.add( pointsToWinPanel, BorderLayout.WEST );
        gameBottomPanel.add( buttonPanel, BorderLayout.EAST );
        
        buttonPanel.add( restartButton );
        buttonPanel.add( quitButton );
        
        scorePanel.add( youLabel, BorderLayout.WEST );
        scorePanel.add( computerLabel, BorderLayout.WEST );
        
        scoringPanel.add( scorePanel, BorderLayout.NORTH );
        scoringPanel.add( textBoxPanel, BorderLayout.CENTER );
        
        mainPanel.add( gamePanel );
        mainPanel.add( scoringPanel );
        
        setContentPane( mainPanel );
    }
    
    private void creatComponents() {
        
        group = new ButtonGroup();
      
        difficultyLabel = new JLabel();
        pointsToWinLabel = new JLabel();
        roundLabel = new JLabel();
        youLabel = new JLabel();
        computerLabel = new JLabel();
        humanTotalScore = new JLabel();
        humanRoundScore = new JLabel();
        computerTotalScore = new JLabel();
        computerRoundScore = new JLabel();
        commonLabel = new JLabel();
        invalidLabel = new JLabel();
        
        levels = new JRadioButton[ 10 ];
        pointsAllowed = new String[ 10000 ];
        
        rejectWordButton = new JButton();
        quitButton = new JButton();
        restartButton = new JButton();
        
        buttonPanel = new JPanel();
        pointsToWinPanel = new JPanel();
        gameBottomPanel = new JPanel();
        mainPanel = new JPanel();
        gamePanel = new JPanel();
        scoringPanel = new JPanel();
        scoringTopPanel = new JPanel();
        difficultyPanel = new JPanel();
        scorePanel = new JPanel();
        youTextPanel = new JPanel();
        computerTextPanel = new JPanel();
        commonTextPanel = new JPanel();
        invalidTextPanel = new JPanel();
        
        youTextField = new JTextArea( 20, 5 );
        commonTextField = new JTextArea( 20, 5 );
        computerTextField = new JTextArea( 20, 5 );
        invalidTextField = new JTextArea( 20, 5 );
    }
    
    private void setTextFieldPanel() {
        JPanel mainPanel = new JPanel();
        textBoxPanel = new JPanel();
        
        mainPanel.setLayout( new FlowLayout() );
        textBoxPanel.setLayout( new BorderLayout() );
        youTextPanel.setLayout( new BorderLayout() );
        commonTextPanel.setLayout( new BorderLayout() );
        computerTextPanel.setLayout( new BorderLayout() );
        invalidTextPanel.setLayout( new BorderLayout() );
          
        youTextPanel.add( Box.createHorizontalStrut( 20 ), BorderLayout.WEST );
        computerTextPanel.add( Box.createHorizontalStrut( 20 ), BorderLayout.WEST );
        commonTextPanel.add( Box.createHorizontalStrut( 20 ), BorderLayout.WEST );
        invalidTextPanel.add( Box.createHorizontalStrut( 20 ), BorderLayout.WEST );
        invalidTextPanel.add( Box.createHorizontalStrut( 20 ), BorderLayout.EAST );
        
        youTextPanel.add( youTextField, BorderLayout.CENTER );
        computerTextPanel.add( computerTextField, BorderLayout.CENTER );
        commonTextPanel.add( commonTextField, BorderLayout.CENTER );
        invalidTextPanel.add( invalidTextField, BorderLayout.CENTER );
        
        youTextPanel.add( youLabel, BorderLayout.NORTH );
        commonTextPanel.add( commonLabel, BorderLayout.NORTH );
        computerTextPanel.add( computerLabel, BorderLayout.NORTH );
        invalidTextPanel.add( invalidLabel, BorderLayout.NORTH );
        
        mainPanel.add( youTextPanel );
        mainPanel.add( computerTextPanel );
        mainPanel.add( commonTextPanel );
        
        textBoxPanel.add( mainPanel, BorderLayout.CENTER );
        textBoxPanel.add( invalidTextPanel, BorderLayout.EAST );
        textBoxPanel.add( rejectWordButton, BorderLayout.SOUTH );
    }
    
    public JTextArea getCommonTextField() {
        return commonTextField;
    }
    
    public JTextArea getComputerTextField() {
        return computerTextField;
    }
    
    public JTextArea getInvalidTextField() {
        return invalidTextField;
    }
    
    public JButton getQuitButton() {
        return quitButton;
    }
    
    public JButton getRejectButton () {
        return rejectWordButton;
    }
    
    public JButton getRestartButton () {
        return restartButton;
    }
    
    public JPanel getPanel() {
        return mainPanel;
    }
    
    public JTextArea getYouTextField() {
        return youTextField;
    }

    public JRadioButton[] getRadioButtons() {
        return levels;
    }
    public JLabel getYouTotalScorelLabel() {
        return humanTotalScore; 
    }
    public JLabel getYouRoundScoreLabel() {
        return humanRoundScore;
    }
    
    public JLabel getComputerRoundScoreLabel() {
        return computerRoundScore;
    }
    
    public JLabel getComputerTotalScorelLabel() {
        return computerTotalScore;
    }
    
    public void setTextEnabled( boolean b ) {
        youTextField.setEditable( b );
        commonTextField.setEditable( b );
        computerTextField.setEditable( b );
        invalidTextField.setEditable( b );
    }
    
    private void setComboxBox() {
        for ( int i = 1; i <= 10000; i++ ) {
            pointsAllowed[ i - 1 ] = Integer.toString( i );
        }
        pointsNeedBox = new JComboBox<>( pointsAllowed );
        
    }
    
    private void setComponents() {
        difficultyLabel.setText( "Difficulty: " );
        pointsToWinLabel.setText( "Points to win: " );
        
        rejectWordButton.setText( "Reject Words" );
        restartButton.setText( "Restart" );
        quitButton.setText( "Quit" );
        
        roundLabel.setText( "Round " );
        youLabel.setText( "You:" );
        computerLabel.setText( "Computer:" );
        commonLabel.setText( "Common:" );
        invalidLabel.setText( "Invalid" );
        
        computerRoundScore.setText( "0" );
        computerTotalScore.setText( "0" );
        humanRoundScore.setText( "0" );
        humanTotalScore.setText( "0" );
    }
    
    private void setRadioButtons() {
        for ( int i  = 0; i < 10; i++ ) {
            levels[ i ] = new JRadioButton( Integer.toString( i + 1 ) );
            group.add( levels[ i ] );
        }
    }
    
    private void setGamePanel() {
        gamePanel.add( gameBottomPanel );
        gamePanel.add( difficultyPanel );
    }
    
    private void setLayout() {
        mainPanel.setLayout( new BoxLayout( mainPanel, BoxLayout.Y_AXIS ) );
        difficultyPanel.setLayout( new FlowLayout() );
        gameBottomPanel.setLayout( new BorderLayout() );
        pointsToWinPanel.setLayout( new FlowLayout() );
        buttonPanel.setLayout( new FlowLayout() );
        scoringTopPanel.setLayout( new FlowLayout() );
        scoringPanel.setLayout( new BorderLayout() );
        scorePanel.setLayout( new BorderLayout() );
        gamePanel.setLayout( new BoxLayout( gamePanel, BoxLayout.Y_AXIS ) );
    }
    
    private void setScoringPanel() {
        
    }
    
}
