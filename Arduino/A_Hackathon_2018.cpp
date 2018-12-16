
/*
* edited by Velleman / Patrick De Coninck
* Read a card using a mfrc522 reader on your SPI interface
* Pin layout should be as follows (on Arduino Uno - Velleman VMA100):
* MOSI: Pin 11 / ICSP-4
* MISO: Pin 12 / ICSP-1
* SCK: Pin 13 / ISCP-3
* SS/SDA (MSS on Velleman VMA405) : Pin 10
* RST: Pin 9
* VCC: 3,3V (DO NOT USE 5V, VMA405 WILL BE DAMAGED IF YOU DO SO)
* GND: GND on Arduino UNO / Velleman VMA100
* IRQ: not used
*/
#include <Arduino.h>
#include <SPI.h>
#include "RFID.h"

#define SS_PIN_1 10
#define RST_PIN_1 9

#define SS_PIN_2 7
#define RST_PIN_2 8

#define PEOPLE 1
#define ACTIONS 1

RFID rfid1(SS_PIN_1,RST_PIN_1);
RFID rfid2(SS_PIN_2,RST_PIN_2);

int serNum[5];

/*
* This integer should be the code of Your Mifare card / tag
*/
int actions[ACTIONS][5] = {252,7,80,163,8};

int people[PEOPLE][5] = {181,234,173,73,187};

int action = -1;
int person = -1;

void setup(){

    Serial.begin(115200);
    pinMode(2, OUTPUT);
    pinMode(3, OUTPUT);
    pinMode(4, OUTPUT);
    pinMode(5, OUTPUT);
    pinMode(6, OUTPUT);
    digitalWrite(2, LOW);
    digitalWrite(3, LOW);
    digitalWrite(4, LOW);
    digitalWrite(5, LOW);
    digitalWrite(6, LOW);
    Serial.println("begin");
    SPI.begin();
    rfid1.init();
    rfid2.init();
    Serial.println("done");
}

void loop(){
	action = -2;
	person = -2;

    if(rfid1.isCard()){
    	Serial.println("card here");
        if(rfid1.readCardSerial()){
            Serial.print(rfid1.serNum[0]);
            Serial.print(" ");
            Serial.print(rfid1.serNum[1]);
            Serial.print(" ");
            Serial.print(rfid1.serNum[2]);
            Serial.print(" ");
            Serial.print(rfid1.serNum[3]);
            Serial.print(" ");
            Serial.print(rfid1.serNum[4]);
            Serial.println("");

            for(int x = 0; x < ACTIONS; x++){
              for(int i = 0; i < 5; i++ ){
                  if(rfid1.serNum[i] != actions[x][i]) {
                	  action = -1;
                      break;
                  } else {
                	  action = x;
                  }
              }
              if(action != -1)
              {
            	  break;
              }
            }
            Serial.print("badge n ");
			Serial.println(action);
        }

    }
    if(rfid2.isCard()){
        	Serial.println("card here");
            if(rfid2.readCardSerial()){
                Serial.print(rfid2.serNum[0]);
                Serial.print(" ");
                Serial.print(rfid2.serNum[1]);
                Serial.print(" ");
                Serial.print(rfid2.serNum[2]);
                Serial.print(" ");
                Serial.print(rfid2.serNum[3]);
                Serial.print(" ");
                Serial.print(rfid2.serNum[4]);
                Serial.println("");

                for(int y = 0; y < PEOPLE; y++){
                  for(int j = 0; j < 5; j++ ){
                      if(rfid2.serNum[j] != people[y][j]) {
                          person = -1;
                          break;
                      } else {
                    	  person = y;
                      }
                  }
                  if(person != -1)
                  {
                	  break;
                  }
                }
                Serial.print("badge n ");
    			Serial.println(person);
            }

        }

    if(person == -1 || action == -1)
    {
		tone(3, 1000);
		delay(50);
		noTone(3);

    }
    if(person == -1)
    {
    	digitalWrite(2, HIGH);
    }
    else
    {
    	digitalWrite(2, LOW);
    }
    if(action == -1)
	{
		digitalWrite(4, HIGH);
	}
	else
	{
		digitalWrite(4, LOW);
	}
    if(person >=0 )
	{
		digitalWrite(5, HIGH);
	}
	else
	{
		digitalWrite(5, LOW);
	}
	if(action >= 0)
	{
		digitalWrite(6, HIGH);
	}
	else
	{
		digitalWrite(6, LOW);
	}

    //rfid.halt();

}


