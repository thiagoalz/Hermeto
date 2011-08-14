/*  SimpleJoystick library example
*/
   
#include <SimpleJoystick.h>

const int upPin = 3;
const int downPin =  4;
const int leftPin =  5;
const int rightPin =  6;
const int buttonPin =  7;

SimpleJoystick joystick;

void setup() {
  delay(1000);
  joystick.setup(upPin, downPin, leftPin, rightPin, buttonPin);
  Serial.begin(9600);
}

void loop() {
 
    int c = joystick.read();
    
    switch (c) {
      case 0: Serial.println("None");    break;
      case 1: Serial.println("Up");      break;
      case 2: Serial.println("Down");    break;
      case 3: Serial.println("Left");    break;
      case 4: Serial.println("Right");   break;
      case 5: Serial.println("Button");  break;
      default: break;
    }
}
