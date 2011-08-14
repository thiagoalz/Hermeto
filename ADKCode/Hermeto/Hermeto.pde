/*  Incio dos testes.... controlando n joysticks
*/
   
#include <SimpleJoystick.h>


SimpleJoystick joystick[4];

void setup() {
  delay(1000);
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
  
  Serial.begin(9600);
}

void loop() {
 
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
      
      if(action!=""){
        Serial.print("[");Serial.print(i);Serial.print("] ");
        Serial.println(action);
      }
    }
}
