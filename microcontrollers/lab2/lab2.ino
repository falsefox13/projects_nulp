const int buttonPin1 = 25;   
const int buttonPin2 = 27;   

void setup() {
  DDRF = 0xff;
  PORTF = 0;
  
  // initialize UART0
  Serial.begin(9600);
  
  // initialize buttons
  pinMode(buttonPin1, INPUT_PULLUP);
  pinMode(buttonPin2, INPUT_PULLUP);
}

void loop() {
  // read from port 0:
  if (Serial.available()) {
    int inByte = Serial.read();
    if (inByte == 0xA1) {
      //algorithm 1
        PORTF = B00011000;
        delay(500);
        PORTF = B00100100;
        delay(500);
        PORTF = B01000010;
        delay(500);
        PORTF = B10000001;
        delay(500);
        PORTF = B00000000;
    } else if (inByte == 0xB1) {
      //algorithm 2
          PORTF = B10000000;
          delay(500);
          PORTF = B00000001;
          delay(500);
          PORTF = B01000000;
          delay(500);
          PORTF = B00000010;
          delay(500);
          PORTF = B00100000;
          delay(500);
          PORTF = B00000100;
          delay(500);
          PORTF = B00010000;
          delay(500);
          PORTF = B00001000;
          delay(500);  
          PORTF = B00000000;
    }
  }

  // write to port 0:
  if(digitalRead(buttonPin1)==LOW){
     Serial.write(0xB1);
     delay(500);
  }
  
  if(digitalRead(buttonPin2)==LOW){
     Serial.write(0xB0);
     delay(500);
  }
}
