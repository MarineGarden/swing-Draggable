package gadget.draggable;

import java.awt.event.MouseEvent;
import java.util.HashSet;

public enum MouseButton {
	
	LEFT, MIDDLE, RIGHT;
	
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
		
		}
		return false;
		
	}
	
}
