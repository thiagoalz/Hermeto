/*
  SimpleJoystick.cpp - A simple Joystick library
  Copyright (c) 2011 Thiago Alvarenga Lechuga.  All right reserved.
  Written by Thiago A. Lechuga <thiagoalz@gmail.com>

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


#include <inttypes.h>
#include "WProgram.h"

#include "SimpleJoystick.h"


SimpleJoystick::SimpleJoystick() {
    this->_lastState = 0;
    this->_lastStateTime = millis();
    this->_timeToHold = 0;
}

void SimpleJoystick::setup(uint8_t upPin, uint8_t downPin, uint8_t leftPin, uint8_t rightPin, uint8_t buttonPin){
    this->_upPin = upPin;
    this->_downPin = downPin;
    this->_leftPin = leftPin;
    this->_rightPin = rightPin;
    this->_buttonPin = buttonPin;



    pinMode(this->_upPin, INPUT);  
    digitalWrite(this->_upPin, HIGH);

    pinMode(this->_downPin, INPUT);  
    digitalWrite(this->_downPin, HIGH);

    pinMode(this->_leftPin, INPUT);  
    digitalWrite(this->_leftPin, HIGH);

    pinMode(this->_rightPin, INPUT);  
    digitalWrite(this->_rightPin, HIGH);

    pinMode(this->_buttonPin, INPUT);  
    digitalWrite(this->_buttonPin, HIGH);

}

void SimpleJoystick::setTimeToHold(long timeToHold){
    this->_timeToHold = timeToHold;
}

int SimpleJoystick::read() {

    int answer = 0;

    if(this->_lastState == 0 || (millis() - this->_lastStateTime) > this->_timeToHold){

        if (!digitalRead(this->_upPin)){//Negado, pois aterra quando pressionado
            answer = 1;
        }

        if (!digitalRead(this->_downPin)){//Negado, pois aterra quando pressionado
            answer = 2;
        }

        if (!digitalRead(this->_leftPin)){//Negado, pois aterra quando pressionado
            answer = 3;
        }

        if (!digitalRead(this->_rightPin)){//Negado, pois aterra quando pressionado
            answer = 4;
        }

        if (!digitalRead(this->_buttonPin)){//Negado, pois aterra quando pressionado
            answer = 5;
        }

        this->_lastState = answer;
        this->_lastStateTime = millis();
    }


	return answer;
}

