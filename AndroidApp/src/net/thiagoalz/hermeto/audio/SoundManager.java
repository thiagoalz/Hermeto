package net.thiagoalz.hermeto.audio;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 * Manage the sounds that are play in the application.
 * From the tutorial http://www.droidnova.com/creating-sound-effects-in-android-part-2,695.html
 */
public class SoundManager {
	
	private static SoundManager instance;
	
	private SoundPool soundPool;
	private HashMap<Integer, Integer> soundPoolMap;
	private AudioManager audioManager;
	private Context context;
	
	private SoundManager() {}
	
	public static synchronized SoundManager getInstance() {
		if (instance == null) {
			instance = new SoundManager();
		}
		return instance;
	}
	
	public void initSounds(Context context) {
		this.context = context;
		this.soundPool = new SoundPool(16, AudioManager.STREAM_MUSIC, 0);
		this.soundPoolMap = new HashMap<Integer, Integer>();
		this.audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
	}
	
	public void addSound(int index, int soundID) {
		soundPoolMap.put(index, soundPool.load(context, soundID, 1));
	}
	
	public void playSound(int index) {
		float streamVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		streamVolume = streamVolume / audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		soundPool.play(soundPoolMap.get(index), streamVolume, streamVolume, 1, 0, 1f);
	}
	
	public void playLoopedSound(int index) {
		float streamVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		streamVolume = streamVolume / audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		soundPool.play(soundPoolMap.get(index), streamVolume, streamVolume, 1, -1, 1f);
	}
	
	public void stopSound(int index) {
		soundPool.stop(soundPoolMap.get(index));
	}
	
	public void cleanUp() {
		soundPool.release();
		soundPool = null;
		soundPoolMap.clear();
		audioManager.unloadSoundEffects();
		instance = null;
	}
}
