import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

/**
 * 
 */

/**
 * @author svkip
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String args[]) throws InterruptedException {
		System.out.println("Alarmanlage initalisiert");
		
		Benutzer user1 = new Benutzer("admin", "qwertz");
		Benutzer user2 = new Benutzer("blup", "blup");

		Anlage alarmanlage = new Anlage();
		alarmanlage.addUser(user1);
		alarmanlage.addUser(user2);
		Sensor tuer = new Sensor(RaspiPin.GPIO_00, alarmanlage);
	    
	 // keep program running until user aborts (CTRL-C)
        for (;;) {
            Thread.sleep(500);
        }
	    
     
	}

}
