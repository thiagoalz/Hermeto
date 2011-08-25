package net.thiagoalz.hermeto.panel.listeners;

public interface ExecutionListener {
	public void onStart(ExecutionEvent event);
	public void onStop(ExecutionEvent event);
	public void onReset(ExecutionEvent event);
	public void onPause(ExecutionEvent event);
	public void onStartPlayingGroup(ExecutionEvent event);
	public void onStopPlayingGroup(ExecutionEvent event);
}
