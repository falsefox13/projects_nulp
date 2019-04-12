void setup() {
  DDRF = 0xff;
  PORTF = 0;
  pinMode(25, INPUT_PULLUP);
}

void loop() {
  if (digitalRead(25) == LOW) {
    PORTF = B00011000;
    delay(500);
    PORTF = B00100100;
    delay(500);
    PORTF = B01000010;
    delay(500);
    PORTF = B10000001;
    delay(500);
  }
  else {
    PORTF = B00000000;
  }

}
