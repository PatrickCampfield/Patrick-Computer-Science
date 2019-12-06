package BoggleGUI;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import controller.BoggleListener;

public class BoggleGui extends JFrame { 
    private JFrame mainFrame;
    
    BoggleWest west;
    BoggleEast east;
    
    private JPanel eastPanel;
    private JPanel mainPanel;
    private JPanel westPanel;
    
    private BoggleListener listener;
    
    public BoggleGui() throws IOException {
        west = new BoggleWest();
        east = new BoggleEast();
        
        mainPanel = new JPanel( new FlowLayout() );
        eastPanel = east.getPanel();
        mainFrame = new JFrame();
        westPanel = west.getPanel();
        
        mainPanel.add( westPanel );
        mainPanel.add( eastPanel );
        
        mainFrame.add( mainPanel );

        setListener();
        
        mainFrame.setContentPane( mainPanel );
        mainFrame.pack();
        mainFrame.setVisible( true );
    }
    
    public BoggleEast getEast() {
        return east;
    }
    
    public BoggleWest getWest() {
        return west;
    }
    
    public BoggleListener getListener() {
        return listener;
    }

    public void setListener() throws IOException {
        listener = new BoggleListener( this );
        listener.run();
        JRadioButton level[] = east.getRadioButtons();
        
        west.getRoundButton().addActionListener( ( ActionListener )listener );
        east.getRejectButton().addActionListener( ( ActionListener )listener);
        east.getRestartButton().addActionListener( ( ActionListener )listener );
        east.getQuitButton().addActionListener( ( ActionListener )listener ); 
        for ( int i = 0; i < level.length; i++ ) {
            level[ i ].addActionListener( ( ActionListener ) listener );
        }
    }
    
}
