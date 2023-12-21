package gadget.draggable;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Draggable extends Point implements MouseMotionListener {
	
	private HashSet<MouseButton> restraints = new HashSet<MouseButton>();
	
	public static boolean on( Component target ) {
		
		int mouseMotionListenerCountBeforeDraggable = target.getMouseMotionListeners().length;
		target.addMouseMotionListener( MouseButton.hasRestraints() ? new Draggable( MouseButton.restraints ) : new Draggable() );
		MouseButton.on();
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
	
	public static Draggable when( MouseButton trigger ) {
		
		MouseButton.when( trigger );
		return new Draggable();
		
	}
	
	private Draggable() {}
	@SuppressWarnings("unchecked")
	private Draggable( HashSet<MouseButton> restraints ) {
		
		this.restraints = (HashSet<MouseButton>) restraints.clone();
		
	}
	
	@SuppressWarnings("static-access")
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
				setBounds( 20 , 40 , 150 , 200 );
				stage.add( this );
				stage.repaint();
				
			}
			
		};
		@SuppressWarnings("unused")
		JComponent greenPanelBackground = new JComponent() {
			
			{
				
				setBounds( 0 , 0 , container.getWidth() , container.getHeight() );
				container.add( this );
				container.repaint();
				
			}
			
			@Override
			public void paint( Graphics g ) {
				
				g.setColor( new Color( 0 , 135 , 62 ) );
				g.fillRect( 0 , 0 , container.getWidth() , container.getHeight() );
				
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
		@SuppressWarnings("unused")
		JButton onOffSwitch = new JButton( "turn Draggable off" ) {
			
			private JButton self = this;
			
			{
				
				setSize( 180 , 30 );
				stage.add( this );
				stage.repaint();
				addMouseListener( new MouseAdapter() {
					
					private boolean isDraggableOff = false;

					@Override
					public void mousePressed( MouseEvent event ) {
						// TODO Auto-generated method stub
						super.mousePressed( event );
						
						isDraggableOff = ! isDraggableOff;
						operateTheSwitch();
						
					}
					
					private void operateTheSwitch() {
						
						if ( isDraggableOff ) {
							
							Draggable.off( stage );
							Draggable.off( container );
							Draggable.off( redBlock );
							
							self.setText( "turn Draggable on" );
							
						} else {
							
							forMainTest( stage , container , redBlock );
							
							self.setText( "turn Draggable off" );
							
						}
						
					}
					
				} );
				
			}
			
		};
		
		forMainTest( stage , container , redBlock );
	}
	@SuppressWarnings("static-access")
	private static void forMainTest( JFrame f , JPanel p , JComponent c ) {
		
		Draggable.when( MouseButton.RIGHT ).on( p );
		Draggable.off( p );
		Draggable.on( p );
		Draggable.when( MouseButton.LEFT ).when( MouseButton.MIDDLE  ).on( c );
		Draggable.on( f );
		
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
		if ( isMovable( event ) )
			target.setLocation( beforeDrag.x + ( mouse.x - x ) , beforeDrag.y + ( mouse.y - y ) );
		
	}
	
	private boolean isMovable( MouseEvent event ) {
		
		if ( restraints.size() == 0 )
			return true;
		boolean check = false;
		for ( MouseButton restraint : restraints )
			if ( restraint.filter( event ) ) {
				
				check = true;
				break;
				
			}
		return check;
		
	}

}
