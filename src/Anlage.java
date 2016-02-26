import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;


public class Anlage {
	
	// create gpio controller
    final GpioController gpio = GpioFactory.getInstance();
    Scanner scanner = new Scanner(System.in);
	
	final GpioPinDigitalOutput ledActive = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "LED_Active", PinState.LOW);
    final GpioPinDigitalOutput ledAlarm = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "LED_Alarm", PinState.LOW);
    
    final GpioPinDigitalInput switchStatus = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN);    
	
	private boolean statusEinAus = false;
	private List<Benutzer> userList = new ArrayList<Benutzer>();
	
	public boolean isStatusEinAus() {
		return statusEinAus;
	}

	public void setStatusEinAus(boolean statusEinAus) {
		this.statusEinAus = statusEinAus;
		if(statusEinAus == true){
			this.ledActive.high();
		}else{
			this.ledActive.low();
		}
	}
	
	public void einschalten(){
		setStatusEinAus(true);
	}
	
	public void ausschalten(){
		setStatusEinAus(false);
		resetAlarm();
	}
	
	public String isStatusEinAusText(){
		if(isStatusEinAus()==true){
			return "Anlage ist eingeschaltet";
		}else{
			return "Anlage ist ausgeschaltet";
		}
	}
	
	public Anlage(){	
		// create and register gpio pin listener for status switch
	    switchStatus.addListener(new GpioPinListenerDigital() {
	        @Override
	        public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
	            
	            if(event.getState()==PinState.HIGH){
	            	if(checkUserAuth()){
		            	einschalten();
		            	System.out.println(isStatusEinAusText());
	            	}
	            }else if(event.getState()==PinState.LOW){
	            	if(checkUserAuth()){
		            	ausschalten();
		            	System.out.println(isStatusEinAusText());
	            	}
	            }
	            
	        }
	        
	    });		
	}

	private boolean statusAlarm = false;
	
	public boolean isStatusAlarm() {
		return statusAlarm;
	}

	public void setStatusAlarm(boolean statusAlarm) {
		this.statusAlarm = statusAlarm;
		if(statusAlarm == true){
			this.ledAlarm.high();
		}else{
			this.ledAlarm.low();
		}
	}
	
	public void checkAlarm(){
		if(isStatusEinAus()==true){
			setStatusAlarm(true);
		}
	}
	
	public void resetAlarm(){
		setStatusAlarm(false);
	}

	public List<Benutzer> getUserList() {
		return userList;
	}

	public void setUserList(List<Benutzer> userList) {
		this.userList = userList;
	}
	
	public void addUser(Benutzer user){
		this.userList.add(user);
	}
	
	public boolean checkUserAuth(){
		System.out.println("Geben Sie bitte Ihren Benutzernamen ein:");
    	String userName = scanner.nextLine();
    	System.out.println("Geben Sie bitte Ihr Passwort ein:");
    	String password = scanner.nextLine();
    	
    	boolean foundUser = false;
    	boolean auth = false;
    	
    	for(Benutzer user : userList) {
    		if(user.getUserName().equals(userName)){
    	    	foundUser=true;
    	    	if(user.getPassword().equals(password)){
    	      		auth = true;
    	      		break;
    	    	}
    		}
    	}
    	
    	
    	if(auth==true){
    		System.out.println("Benutzerünerprüfung erfolgreich.");
    		return true;
    	}else if(foundUser){
    		System.out.println("Das Passwort war falsch.");
    		return false;
    	}else{
    		System.out.println("Sie sind kein registrierter Benutzer.");
    		return false;
    	}
	}
	
}




