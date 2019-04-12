#include <Servo.h>
#include <Wire.h>

#include "L298N_conf.h"
#include "lcd.h"
#include "turn_signal_blink.h"

Servo servo_motor;
#include "Sonar.h"

int distanceRight;
int distanceLeft;
int stepCount;

void setup() {

  pinMode(RightMotorForward, OUTPUT);
  pinMode(LeftMotorForward, OUTPUT);
  pinMode(LeftMotorBackward, OUTPUT);
  pinMode(RightMotorBackward, OUTPUT);
  pinMode(SpeedPinL, OUTPUT);
  pinMode(SpeedPinR, OUTPUT);

  lc.shutdown(0, false); // Wake up display
  lc.setIntensity(0, 5); // Set intensity level
  lc.clearDisplay(0);    // Clear Display

  lcd.begin();
  lcd.backlight();
  servo_motor.attach(3);
}

void avoidObstacleRight() {
  blink_right();
  printRight();
  turnRight();
  set_Motorspeed(255, 255);
  delay(250);

  printForward();
  moveForward();
  set_Motorspeed(128, 128);
  delay(1000);

  blink_left();
  printLeft();
  turnLeft();
  set_Motorspeed(255, 255);
  delay(250);

  printForward();
  moveForward();
  set_Motorspeed(128, 128);
  delay(1000);

  blink_left();
  printLeft();
  turnLeft();
  set_Motorspeed(255, 255);
  delay(250);

  printForward();
  moveForward();
  set_Motorspeed(128, 128);
  delay(1000);

  blink_right();
  printRight();
  turnRight();
  set_Motorspeed(255, 255);
  delay(250);
}

void avoidObstacleLeft() { 
  blink_left();
  printLeft();
  turnLeft();
  set_Motorspeed(255, 255);
  delay(250);

  printForward();
  moveForward();
  set_Motorspeed(128, 128);
  delay(1000);

  blink_right();
  printRight();
  turnRight();
  set_Motorspeed(255, 255);
  delay(250);

  printForward();
  moveForward();
  set_Motorspeed(128, 128);
  delay(1000);

  blink_right();
  printRight();
  turnRight();
  set_Motorspeed(255, 255);
  delay(250);

  printForward();
  moveForward();
  set_Motorspeed(128, 128);
  delay(1000);

  printLeft();
  turnLeft();
  set_Motorspeed(255, 255);
  delay(250);
}

void moveNext() {
  if (stepCount == 20 || stepCount == 40) {
    blink_right();
    printRight();
    turnRight();
    set_Motorspeed(255, 255);
    delay(200);
  }
  else if (stepCount == 30) {
    blink_right();
    printRight();
    turnRight();
    set_Motorspeed(255, 255);
    delay(250);
  }
  else if (stepCount == 60)  {
    blink_right();
    printRight();
    turnRight();
    set_Motorspeed(255, 255);
    delay(400);
  }
  else if (stepCount == 70) {
    blink_left();
    printLeft();
    turnLeft();
    set_Motorspeed(255, 255);
    delay(220);
  }
  else if (stepCount == 80) {
    blink_right();
    printRight();
    lcd.setCursor(3, 1);
    lcd.print("New cycle");
    turnRight();
    set_Motorspeed(255, 255);
    delay(400);
    stepCount = 0;
  }
  else {
    printForward();
    moveForward();
    set_Motorspeed(128, 128);
    delay(200);
  }
}

void loop() {
  distance = readPing();
  if (distance <= 30) {
    lcd.clear();
    lcd.setCursor(3, 0);
    lcd.print("Obstacle");
    moveStop();
    set_Motorspeed(128, 128);
    delay(100);
    moveBackward();
    set_Motorspeed(255, 255);
    delay(200);
    moveStop();
    set_Motorspeed(128, 128);
    delay(100);
    distanceRight = lookRight();
    delay(300);
    distanceLeft = lookLeft();
    delay(300);

    if (distanceRight >= distanceLeft) {
      avoidObstacleRight();
    }
    else if (distanceLeft > distanceRight) {
      avoidObstacleLeft();
    }
  }
  else {
    moveNext();
  }
  stepCount++;
}
