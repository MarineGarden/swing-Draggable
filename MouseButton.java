package gadget.draggable;

import java.awt.event.MouseEvent;
import java.util.HashSet;

public enum MouseButton {
	
	LEFT, MIDDLE, RIGHT, ALL;
	
	static HashSet<MouseButton> restraints = new HashSet<MouseButton>();
	static void when( MouseButton trigger ) {
		
		restraints.add( trigger );
		
	}
	static void on() {
		
		restraints.clear();
		
	}
	static boolean hasRestraints() {
		
		return restraints.size() > 0;
		
	}
	
	boolean filter( MouseEvent event ) {
		
		switch ( event.getModifiersEx() ) {
		
			case MouseEvent.BUTTON1_DOWN_MASK:
				return name().equals( "LEFT" );
				
			case MouseEvent.BUTTON2_DOWN_MASK:
				return name().equals( "MIDDLE" );
				
			case MouseEvent.BUTTON3_DOWN_MASK:
				return name().equals( "RIGHT" );
				
			default:
				return name().equals( "ALL" );
		
		}
		
	}
	
	private int getModifiersExNumber() {
		
		switch ( name() ) {
		
			case "LEFT":
				return MouseEvent.BUTTON1_DOWN_MASK;
			
			case "MIDDLE":
				return MouseEvent.BUTTON2_DOWN_MASK;
				
			case "RIGHT":
				return MouseEvent.BUTTON3_DOWN_MASK;
				
			default:
				return 0;
		
		}
		
	}
	
	boolean canMove( MouseEvent event ) {
		
		return event.getModifiersEx() == getModifiersExNumber() || getModifiersExNumber() == 0;
					
	}
	
}
