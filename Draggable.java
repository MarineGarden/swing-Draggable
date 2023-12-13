package gadget;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Draggable extends Rectangle implements MouseMotionListener {
	
	public static boolean on( Component target ) {
		
		int mouseMotionListenerCountBeforeDraggable = target.getMouseMotionListeners().length;
		target.addMouseMotionListener( new Draggable() );
		int mouseMotionListenerCountAfterDraggable = target.getMouseMotionListeners().length;
		return mouseMotionListenerCountAfterDraggable - mouseMotionListenerCountBeforeDraggable > 0;
		
	}
	public static boolean off( Component target ) {
		
		int mouseMotionListenerCountBeforeDraggable = target.getMouseMotionListeners().length;
		MouseMotionListener[] mouseMotionListeners = target.getMouseMotionListeners();
		for ( MouseMotionListener mouseMotionListener : mouseMotionListeners )
			if ( mouseMotionListener instanceof Draggable )
				target.removeMouseMotionListener( mouseMotionListener );
		int mouseMotionListenerCountAfterDraggable = target.getMouseMotionListeners().length;
		return mouseMotionListenerCountAfterDraggable - mouseMotionListenerCountBeforeDraggable < 0;
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JComponent sample = new JComponent() {};
		sample.addMouseMotionListener( new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {}
			@Override
			public void mouseDragged(MouseEvent e) {}
			
		} );
		System.out.println( Draggable.on( sample ) );
		int mouseMotionListenerCountAfterDraggableOn = sample.getMouseMotionListeners().length;
		System.out.println( Draggable.off( sample ) );
		int mouseMotionListenerCountAfterDraggableOff = sample.getMouseMotionListeners().length;
		System.out.println( mouseMotionListenerCountAfterDraggableOff - mouseMotionListenerCountAfterDraggableOn == -1 );
		
		JFrame stage = new JFrame( "test" ) { 
			
			{
				
				//
				//setUndecorated(true);
				
				setSize( 250 , 300 );
				setLayout( null );
				setDefaultCloseOperation( EXIT_ON_CLOSE );
				setVisible( true );
				
			}
			
		};
		JPanel container = new JPanel() {
			
			{
				
				setLayout( null );
				setBounds( 0 , 0 , stage.getWidth() , stage.getHeight()/2 );
				stage.add( this );
				stage.repaint();
				
			}
			
		};
		@SuppressWarnings("unused")
		JComponent greenPanelBackground = new JComponent() {
			
			{
				
				setBounds( 20 , 10 , stage.getWidth() , stage.getHeight()/2 );
				container.add( this );
				container.repaint();
				
			}
			
			@Override
			public void paint( Graphics g ) {
				
				g.setColor( new Color( 0 , 135 , 62 ) );
				g.fillRect( 0 , 0 , stage.getWidth() , stage.getHeight()/2 );
				
			}
			
		};
		JComponent redBlock = new JComponent() {
			
			{
				
				setBounds( 30 , 50 , 50 , 50 );
				container.add( this , 0 );
				container.repaint();
				
			}
			
			@Override
			public void paint( Graphics g ) {
				
				g.setColor( Color.RED );
				g.fillRect( 0 , 0 , 50 , 50 );
				
			}
			
		};
		Draggable.on( stage );
		Draggable.on( container );
		Draggable.on( redBlock );
	}
	
	@Override
	public void mouseMoved( MouseEvent event ) {
		// TODO Auto-generated method stub
		setLocation( event.getPoint() );
	}

	@Override
	public void mouseDragged( MouseEvent event ) {
		// TODO Auto-generated method stub
		Point mouse = event.getPoint();
		Component target = (Component) event.getSource();
		Point beforeDrag = target.getLocation();
		target.setLocation( beforeDrag.x + ( mouse.x - x ) , beforeDrag.y + ( mouse.y - y ) );
	}

}
