package net.thiagoalz.hermeto.audio;

import java.util.HashMap;
import java.util.Random;

import net.thiagoalz.hermeto.R;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 * Manage the sounds that are play in the application.
 * From the tutorial http://www.droidnova.com/creating-sound-effects-in-android-part-2,695.html
 */
public class SoundManager {
	
	//private static SoundManager instance;
	
	private SoundPool soundPool;
	private HashMap<Integer, Integer> soundPoolMap;
	private AudioManager audioManager;
	private Context context;
	
	public SoundManager(Context context) {
		this.initSounds(context);
		this.loadSounds();
		
	}
	
//	public static synchronized SoundManager getInstance() {
//		if (instance == null) {
//			instance = new SoundManager();
//		}
//		return instance;
//	}
	
	public void loadSounds(){
		this.addSound(0, R.raw.m1);
		this.addSound(1, R.raw.m2);
		this.addSound(2, R.raw.m3);
		this.addSound(3, R.raw.m4);
		this.addSound(4, R.raw.m5);
		this.addSound(5, R.raw.m6);
		this.addSound(6, R.raw.m7);
		this.addSound(7, R.raw.m8);
		this.addSound(8, R.raw.m9);
		this.addSound(9, R.raw.m10);
		this.addSound(10, R.raw.m11);
		this.addSound(11, R.raw.m12);
		this.addSound(12, R.raw.m13);
		this.addSound(13, R.raw.m14);
		this.addSound(14, R.raw.m15);
		this.addSound(15, R.raw.m16a);
		this.addSound(16, R.raw.m16b);
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
		if(index==15){//Random 15/16
			Random r=new Random(System.currentTimeMillis());
			index=15+r.nextInt(2);
		}
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
		//instance = null;
	}
}
