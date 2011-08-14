/*
  SimpleJoystick.h - A simple Joystick library
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

#ifndef SimpleJoystick_h
#define SimpleJoystick_h

#include <inttypes.h>

/**
 * Purpose: Provides an easy access to a basic Joystick
 * Author:  Thiago A. Lechuga
 */
class SimpleJoystick {
  public:
    /**
     * Contructor.
     */
    SimpleJoystick();


    /**
     * Setup pins.
     */
    void setup(uint8_t upPin, uint8_t downPin, uint8_t leftPin, uint8_t rightPin, uint8_t buttonPin);

    /**
     * Setup time do hold each value.
     */
    void setTimeToHold(long timeToHold);

    
    /**
     * Returns the control status.
     * 0= stopped, 1=up, 2=down, 3=left, 4=right, 5=button
     */
    int read();

  private:
    uint8_t _upPin;
    uint8_t _downPin;
    uint8_t _leftPin;
    uint8_t _rightPin;
    uint8_t _buttonPin;

    int _lastState;   // the previous reading
    long _lastStateTime;  // the last time the read result toggled
    long _timeToHold;    // tempo que a leitura retorna apenas zeros depois de uma leitura != de zero.
                         // Pode ser usado para diminuir a sensibilidade da leitura.
     
};

#endif
