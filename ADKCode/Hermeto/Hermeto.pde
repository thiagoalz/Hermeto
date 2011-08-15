/*
  Hermeto.pde - Driver for ADK Hermeto soft
  Copyright (c) 2011 Thiago Alvarenga Lechuga.  All right reserved.
  Written by Thiago A. Lechuga <thiagoalz@gmail.com>
  
  *Based on demokit.pde from ADK_release_0512 firmware

  This library is free software; you can redistribute it and/or
  modify it under the terms of the GNU Lesser General Public
  License as published by the Free Software Foundation; either
  version 2.1 of the License, or (at your option) any later version.

  This library is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
  Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public
  License along with this library; if not, write to the Free Software
  Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
*/

#include <Wire.h>
#include <Servo.h>

#include <Max3421e.h>
#include <Usb.h>
#include <AndroidAccessory.h>

#include <CapSense.h>

#include <SimpleJoystick.h>

#define  LED3_RED       2
#define  LED3_GREEN     4
#define  LED3_BLUE      3

#define  LED2_RED       5
#define  LED2_GREEN     7
#define  LED2_BLUE      6

#define  LED1_RED       8
#define  LED1_GREEN     10
#define  LED1_BLUE      9

#define  SERVO1         11
#define  SERVO2         12
#define  SERVO3         13

#define  TOUCH_RECV     14
#define  TOUCH_SEND     15

#define  RELAY1         A0
#define  RELAY2         A1

#define  LIGHT_SENSOR   A2
#define  TEMP_SENSOR    A3

#define  BUTTON1        A6
#define  BUTTON2        A7
#define  BUTTON3        A8

#define  JOY_SWITCH     A9      // pulls line down when pressed
#define  JOY_nINT       A10     // active low interrupt input
#define  JOY_nRESET     A11     // active low reset output

AndroidAccessory acc("Lechuga, Inc.",
		     "Hermeto",
		     "Hermeto ADK Board",
		     "1.0",
		     "http://garoa.net.br/wiki/Hermeto",
		     "0000000000000001");

SimpleJoystick joystick[4];

Servo servos[3];

// 10M ohm resistor on demo shield
CapSense   touch_robot = CapSense(TOUCH_SEND, TOUCH_RECV);

boolean lastConectionStatus = false;

int brightness = 0;    // how bright the LED is
int fadeAmount = 5;    // how many points to fade the LED by

void setup();
void loop();

void init_buttons()
{
	pinMode(BUTTON1, INPUT);
	pinMode(BUTTON2, INPUT);
	pinMode(BUTTON3, INPUT);
	pinMode(JOY_SWITCH, INPUT);

	// enable the internal pullups
	digitalWrite(BUTTON1, HIGH);
	digitalWrite(BUTTON2, HIGH);
	digitalWrite(BUTTON3, HIGH);
	digitalWrite(JOY_SWITCH, HIGH);
}


void init_relays()
{
	pinMode(RELAY1, OUTPUT);
	pinMode(RELAY2, OUTPUT);
}


void init_leds()
{
	digitalWrite(LED1_RED, 1);
	digitalWrite(LED1_GREEN, 1);
	digitalWrite(LED1_BLUE, 1);

	pinMode(LED1_RED, OUTPUT);
	pinMode(LED1_GREEN, OUTPUT);
	pinMode(LED1_BLUE, OUTPUT);

	digitalWrite(LED2_RED, 1);
	digitalWrite(LED2_GREEN, 1);
	digitalWrite(LED2_BLUE, 1);

	pinMode(LED2_RED, OUTPUT);
	pinMode(LED2_GREEN, OUTPUT);
	pinMode(LED2_BLUE, OUTPUT);

	digitalWrite(LED3_RED, 1);
	digitalWrite(LED3_GREEN, 1);
	digitalWrite(LED3_BLUE, 1);

	pinMode(LED3_RED, OUTPUT);
	pinMode(LED3_GREEN, OUTPUT);
	pinMode(LED3_BLUE, OUTPUT);
}

void init_joystick(int threshold);

byte b1, b2, b3, b4, c;
void setup()
{
	Serial.begin(115200);
	Serial.print("\r\nStart");

	init_leds();
	init_relays();
	init_buttons();
        init_simpleJoysticks();
	init_joystick( 5 );

	// autocalibrate OFF
	touch_robot.set_CS_AutocaL_Millis(0xFFFFFFFF);

	servos[0].attach(SERVO1);
	servos[0].write(90);
	servos[1].attach(SERVO2);
	servos[1].write(90);
	servos[2].attach(SERVO3);
	servos[2].write(90);


	b1 = digitalRead(BUTTON1);
	b2 = digitalRead(BUTTON2);
	b3 = digitalRead(BUTTON3);
	b4 = digitalRead(JOY_SWITCH);
	c = 0;

	acc.powerOn();
}

void loop()
{
	byte err;
	byte idle;
	static byte count = 0;
	byte msg[3];
	long touchcount;

	if (acc.isConnected()) {
  
                if( !lastConectionStatus){
                  analogWrite(LED1_RED, 255);
      		  analogWrite(LED1_GREEN, 255);
                  analogWrite(LED1_BLUE, 200);
        	  analogWrite(LED2_RED, 255);
        	  analogWrite(LED2_GREEN, 255);
        	  analogWrite(LED2_BLUE, 200);
        	  analogWrite(LED3_RED, 255);
        	  analogWrite(LED3_GREEN, 255);
        	  analogWrite(LED3_BLUE, 200);
 
                  delay(3000);

                  analogWrite(LED1_RED, 255);
      		  analogWrite(LED1_GREEN, 255);
                  analogWrite(LED1_BLUE, 255);
        	  analogWrite(LED2_RED, 255);
        	  analogWrite(LED2_GREEN, 255);
        	  analogWrite(LED2_BLUE, 255);
        	  analogWrite(LED3_RED, 255);
        	  analogWrite(LED3_GREEN, 255);
        	  analogWrite(LED3_BLUE, 255);
                }
                lastConectionStatus = true;
                
		int len = acc.read(msg, sizeof(msg), 1);
		int i;
		byte b;
		uint16_t val;
		int x, y;
		char c0;

		if (len > 0) {
			// assumes only one command per packet
			if (msg[0] == 0x2) {
				if (msg[1] == 0x0)
					analogWrite(LED1_RED, 255 - msg[2]);
				else if (msg[1] == 0x1)
					analogWrite(LED1_GREEN, 255 - msg[2]);
				else if (msg[1] == 0x2)
					analogWrite(LED1_BLUE, 255 - msg[2]);
				else if (msg[1] == 0x3)
					analogWrite(LED2_RED, 255 - msg[2]);
				else if (msg[1] == 0x4)
					analogWrite(LED2_GREEN, 255 - msg[2]);
				else if (msg[1] == 0x5)
					analogWrite(LED2_BLUE, 255 - msg[2]);
				else if (msg[1] == 0x6)
					analogWrite(LED3_RED, 255 - msg[2]);
				else if (msg[1] == 0x7)
					analogWrite(LED3_GREEN, 255 - msg[2]);
				else if (msg[1] == 0x8)
					analogWrite(LED3_BLUE, 255 - msg[2]);
				else if (msg[1] == 0x10)
					servos[0].write(map(msg[2], 0, 255, 0, 180));
				else if (msg[1] == 0x11)
					servos[1].write(map(msg[2], 0, 255, 0, 180));
				else if (msg[1] == 0x12)
					servos[2].write(map(msg[2], 0, 255, 0, 180));
			} else if (msg[0] == 0x3) {
				if (msg[1] == 0x0)
					digitalWrite(RELAY1, msg[2] ? HIGH : LOW);
				else if (msg[1] == 0x1)
					digitalWrite(RELAY2, msg[2] ? HIGH : LOW);
			}
		}

		msg[0] = 0x1;

		b = digitalRead(BUTTON1);
		if (b != b1) {
			msg[1] = 0;
			msg[2] = b ? 0 : 1;
			acc.write(msg, 3);
			b1 = b;
		}

		b = digitalRead(BUTTON2);
		if (b != b2) {
			msg[1] = 1;
			msg[2] = b ? 0 : 1;
			acc.write(msg, 3);
			b2 = b;
		}

		b = digitalRead(BUTTON3);
		if (b != b3) {
			msg[1] = 2;
			msg[2] = b ? 0 : 1;
			acc.write(msg, 3);
			b3 = b;
		}

		b = digitalRead(JOY_SWITCH);
		if (b != b4) {
			msg[1] = 4;
			msg[2] = b ? 0 : 1;
			acc.write(msg, 3);
			b4 = b;
		}

		switch (count++ % 0x10) {
		case 0:
			val = analogRead(TEMP_SENSOR);
			msg[0] = 0x4;
			msg[1] = val >> 8;
			msg[2] = val & 0xff;
			acc.write(msg, 3);
			break;

		case 0x4:
			val = analogRead(LIGHT_SENSOR);
			msg[0] = 0x5;
			msg[1] = val >> 8;
			msg[2] = val & 0xff;
			acc.write(msg, 3);
			break;

		case 0x8:
			read_joystick(&x, &y);
			msg[0] = 0x6;
			msg[1] = constrain(x, -128, 127);
			msg[2] = constrain(y, -128, 127);
			acc.write(msg, 3);
			break;

		case 0xc:
			touchcount = touch_robot.capSense(5);

			c0 = touchcount > 750;

			if (c0 != c) {
				msg[0] = 0x1;
				msg[1] = 3;
				msg[2] = c0;
				acc.write(msg, 3);
				c = c0;
			}

			break;
		}

                //Read information about Hermeto joysticks and send messages
                read_simpleJoysticks();
                
	} else {
                lastConectionStatus = false;
                
                // change the brightness for next time through the loop:
                brightness = brightness + fadeAmount;
              
                // reverse the direction of the fading at the ends of the fade: 
                if (brightness == 0 || brightness == 255) {
                  fadeAmount = -fadeAmount ; 
                } 
		// Blink Red leds
		analogWrite(LED1_RED, brightness);
		analogWrite(LED1_GREEN, 255);
		analogWrite(LED1_BLUE, 255);
		analogWrite(LED2_RED, brightness);
		analogWrite(LED2_GREEN, 255);
		analogWrite(LED2_BLUE, 255);
		analogWrite(LED3_RED, brightness);
		analogWrite(LED3_GREEN, 255);
		analogWrite(LED3_BLUE, 255);

                //Reset status
		servos[0].write(90);
		servos[0].write(90);
		servos[0].write(90);
		digitalWrite(RELAY1, LOW);
		digitalWrite(RELAY2, LOW);
	}

	delay(10);
}

// ==============================================================================
//SimpleJoysticks

void init_simpleJoysticks(){
  //Configurando Joysticks
  //Os pinos 50 e 51 apresentaram problemas, nao funcionando corretamente!
  //Pares
  joystick[0].setup(22, 24, 26, 28, 30);
  joystick[1].setup(32, 34, 36, 38, 40);
  
  //Impares
  joystick[2].setup(23, 25, 27, 29, 31);
  joystick[3].setup(33, 35, 37, 39, 41);
  
  int i;
  for(i=0; i<4; i++){
    joystick[i].setTimeToHold(250);
  } 
}

void read_simpleJoysticks(){
  byte msg[3];
  
  int i;
        for(i=0; i<4; i++){
          int c = joystick[i].read();
          
          String action = "";
          switch (c) {
            //case 0: action = "None";    break;
            case 1: action = "Up";      break;
            case 2: action = "Down";    break;
            case 3: action = "Left";    break;
            case 4: action = "Right";   break;
            case 5: action = "Button";  break;
            default: break;
          }
          
          if(c > 0){
            Serial.print("[");Serial.print(i);Serial.print("] ");
            Serial.println(action);
            
            msg[0] = 0x9;
	    msg[1] = i;
	    msg[2] = c;
            acc.write(msg, 3);
          }
        }
}

// ==============================================================================
// Austria Microsystems i2c Joystick
void init_joystick(int threshold)
{
	byte status = 0;

	pinMode(JOY_SWITCH, INPUT);
	digitalWrite(JOY_SWITCH, HIGH);

	pinMode(JOY_nINT, INPUT);
	digitalWrite(JOY_nINT, HIGH);

	pinMode(JOY_nRESET, OUTPUT);

	digitalWrite(JOY_nRESET, 1);
	delay(1);
	digitalWrite(JOY_nRESET, 0);
	delay(1);
	digitalWrite(JOY_nRESET, 1);

	Wire.begin();

	do {
		status = read_joy_reg(0x0f);
	} while ((status & 0xf0) != 0xf0);

	// invert magnet polarity setting, per datasheet
	write_joy_reg(0x2e, 0x86);

	calibrate_joystick(threshold);
}


int offset_X, offset_Y;

void calibrate_joystick(int dz)
{
	char iii;
	int x_cal = 0;
	int y_cal = 0;

	// Low Power Mode, 20ms auto wakeup
	// INTn output enabled
	// INTn active after each measurement
	// Normal (non-Reset) mode
	write_joy_reg(0x0f, 0x00);
	delay(1);

	// dummy read of Y_reg to reset interrupt
	read_joy_reg(0x11);

	for(iii = 0; iii != 16; iii++) {
		while(!joystick_interrupt()) {}

		x_cal += read_joy_reg(0x10);
		y_cal += read_joy_reg(0x11);
	}

	// divide by 16 to get average
	offset_X = -(x_cal>>4);
	offset_Y = -(y_cal>>4);

	write_joy_reg(0x12, dz - offset_X);  // Xp, LEFT threshold for INTn
	write_joy_reg(0x13, -dz - offset_X);  // Xn, RIGHT threshold for INTn
	write_joy_reg(0x14, dz - offset_Y);  // Yp, UP threshold for INTn
	write_joy_reg(0x15, -dz - offset_Y);  // Yn, DOWN threshold for INTn

	// dead zone threshold detect requested?
	if (dz)
		write_joy_reg(0x0f, 0x04);
}


void read_joystick(int *x, int *y)
{
	*x = read_joy_reg(0x10) + offset_X;
	*y = read_joy_reg(0x11) + offset_Y;  // reading Y clears the interrupt
}

char joystick_interrupt()
{
	return digitalRead(JOY_nINT) == 0;
}


#define  JOY_I2C_ADDR    0x40

char read_joy_reg(char reg_addr)
{
	char c;

	Wire.beginTransmission(JOY_I2C_ADDR);
	Wire.send(reg_addr);
	Wire.endTransmission();

	Wire.requestFrom(JOY_I2C_ADDR, 1);

	while(Wire.available())
		c = Wire.receive();

	return c;
}

void write_joy_reg(char reg_addr, char val)
{
	Wire.beginTransmission(JOY_I2C_ADDR);
	Wire.send(reg_addr);
	Wire.send(val);
	Wire.endTransmission();
}
