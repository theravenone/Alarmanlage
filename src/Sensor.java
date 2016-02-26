/*
 * Sensoren Grundfunktionen
 * 
 */

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class Sensor {
	private int id;
	private String name;
	private int type;
	private int status;
	private int zone;
	private Pin gpioPin;
	private Anlage alarmanlage;
	private boolean connected;
	private boolean alarm;
	private boolean sabotage;
	
	final GpioController gpio = GpioFactory.getInstance();
	final GpioPinDigitalInput switchAlarm;
	
	public Sensor(Pin sensorPin, Anlage alarmanlage){
		this.setGpioPin(sensorPin);
		this.setAlarmanlage(alarmanlage);
		
		switchAlarm = gpio.provisionDigitalInputPin(this.getGpioPin(), PinPullResistance.PULL_DOWN);
		
		// create and register gpio pin listener for status alarm
	    switchAlarm.addListener(new GpioPinListenerDigital() {
	        @Override
	        public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
         
	            if(event.getState()==PinState.HIGH){
	            	alarmanlage.checkAlarm();
	            }
	            
	        } 
	    });
	}
	

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getZone() {
		return zone;
	}
	public void setZone(int zone) {
		this.zone = zone;
	}
	public boolean isConnected() {
		return connected;
	}
	public void setConnected(boolean connected) {
		this.connected = connected;
	}
	public boolean isAlarm() {
		return alarm;
	}
	public void setAlarm(boolean alarm) {
		this.alarm = alarm;
	}
	public boolean isSabotage() {
		return sabotage;
	}
	public void setSabotage(boolean sabotage) {
		this.sabotage = sabotage;
	}
	public Pin getGpioPin() {
		return gpioPin;
	}
	public void setGpioPin(Pin gpioPin) {
		this.gpioPin = gpioPin;
	}
	public Anlage getAlarmanlage() {
		return alarmanlage;
	}
	public void setAlarmanlage(Anlage alarmanlage) {
		this.alarmanlage = alarmanlage;
	}
	
	
	
	
	
	
	
}

