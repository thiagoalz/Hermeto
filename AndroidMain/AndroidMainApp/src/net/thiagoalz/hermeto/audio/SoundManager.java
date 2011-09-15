package net.thiagoalz.hermeto.audio;

import java.util.HashMap;

import net.thiagoalz.hermeto.R;
import net.thiagoalz.hermeto.view.strategies.FreeSelectionStrategy;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

/**
 * Manage the sounds that are play in the application.
 * From the tutorial http://www.droidnova.com/creating-sound-effects-in-android-part-2,695.html
 */
public class SoundManager {
	private static final String TAG = FreeSelectionStrategy.class.getCanonicalName();
	
	private static final int DEFAULT_MAX_PER_INSTRUMENT = 16;
	private static SoundManager instance;
	
	private SoundPool soundPool;
	private HashMap<Integer, Integer> soundPoolMap;
	private AudioManager audioManager;
	private Context context;
	
	private int currentIndex;
	private int maxPerInstrument = DEFAULT_MAX_PER_INSTRUMENT;
	
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
		
		loadSounds();
	}
	
	protected void loadSounds(){
		currentIndex=0;
		this.loadPercusion();
		this.loadVoices();
	}
	
	protected void loadPercusion() {
		this.addSound(currentIndex++, R.raw.m1);
		this.addSound(currentIndex++, R.raw.m2);
		this.addSound(currentIndex++, R.raw.m3);
		this.addSound(currentIndex++, R.raw.m4);
		this.addSound(currentIndex++, R.raw.m5);
		this.addSound(currentIndex++, R.raw.m6);
		this.addSound(currentIndex++, R.raw.m7);
		this.addSound(currentIndex++, R.raw.m8);
		this.addSound(currentIndex++, R.raw.m9);
		this.addSound(currentIndex++, R.raw.m10);
		this.addSound(currentIndex++, R.raw.m11);
		this.addSound(currentIndex++, R.raw.m12);
		this.addSound(currentIndex++, R.raw.m13);
		this.addSound(currentIndex++, R.raw.m14);
		this.addSound(currentIndex++, R.raw.m15);
		this.addSound(currentIndex++, R.raw.m16);
	}
	
	protected void loadVoices() {
		this.addSound(currentIndex++, R.raw.m16a);
		this.addSound(currentIndex++, R.raw.m16b);
		this.addSound(currentIndex++, R.raw.m16a);
		this.addSound(currentIndex++, R.raw.m16b);
		this.addSound(currentIndex++, R.raw.m16a);
		this.addSound(currentIndex++, R.raw.m16b);
		this.addSound(currentIndex++, R.raw.m16a);
		this.addSound(currentIndex++, R.raw.m16b);
		this.addSound(currentIndex++, R.raw.m16a);
		this.addSound(currentIndex++, R.raw.m16b);
		this.addSound(currentIndex++, R.raw.m16a);
		this.addSound(currentIndex++, R.raw.m16b);
		this.addSound(currentIndex++, R.raw.m16a);
		this.addSound(currentIndex++, R.raw.m16b);
		this.addSound(currentIndex++, R.raw.m16a);
		this.addSound(currentIndex++, R.raw.m16b);
	}
	
	public void addSound(int index, int soundID) {
		soundPoolMap.put(index, soundPool.load(context, soundID, 1));
	}
	
	public void playSound(int index, InstrumentType type) {
		//Switch instrument
		index = (type.ordinal() * maxPerInstrument) + index;
		
		playSound(index);
	}
	
	protected void playSound(int index) {
		float streamVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		streamVolume = streamVolume / audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		if(soundPoolMap.get(index)!=null){
			soundPool.play(soundPoolMap.get(index), streamVolume, streamVolume, 1, 0, 1f);
		}else{
			Log.d(TAG, "Null Sound: Index "+index);
		}
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

	public int getMaxPerInstrument() {
		return maxPerInstrument;
	}

	public void setMaxPerInstrument(int maxPerInstrument) {
		this.maxPerInstrument = maxPerInstrument;
	}
}
