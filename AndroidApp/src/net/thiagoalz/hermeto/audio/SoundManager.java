package net.thiagoalz.hermeto.audio;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundManager {
	private SoundPool soundPool;
	private HashMap soundPoolMap;
	private AudioManager audioManager;
	private Context context;
	
	private SoundManager() {}
	
	public void initSounds(Context context) {
		this.context = context;
		this.soundPool = new SoundPool(16, AudioManager.STREAM_MUSIC, 0);
	}
}
