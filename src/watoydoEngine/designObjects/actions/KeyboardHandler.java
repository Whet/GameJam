package watoydoEngine.designObjects.actions;

import java.awt.event.KeyEvent;

public abstract class KeyboardHandler implements KeyboardRespondable{
	
	private boolean focused;
	
	private int actionZ;
	
	public KeyboardHandler(){
		focused = true;
		this.actionZ = 0;
	}
	
	@Override
	public int getActionZ() {
		return actionZ;
	}

	public void setActionZ(int actionZ) {
		this.actionZ = actionZ;
	}

	@Override
	public boolean kD(KeyEvent e) {
		return false;
	}
	
	@Override
	public boolean kU(KeyEvent e) {
		return false;
	}
	
	@Override
	public boolean kP(KeyEvent e) {
		return false;
	}
	
	@Override
	public boolean isFocused() {
		return this.focused;
	}
	
	@Override
	public void setFocus(boolean active) {
		this.focused = active;
	}

}
