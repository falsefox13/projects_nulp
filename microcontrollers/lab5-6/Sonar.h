#include <NewPing.h>       //for the Ultrasonic sensor function library.
//sensor pins
#define trig_pin A3 //analog input 1
#define echo_pin A2 //analog input 2

#define maximum_distance 200
int distance = 100;

NewPing sonar(trig_pin, echo_pin, maximum_distance); //sensor function

int readPing(){
  delay(70);
  int cm = sonar.ping_cm();
  if (cm==0){
    cm=250;
  }
  return cm;
}

int lookRight(){  
  servo_motor.write(10);
  delay(300);
  int distance = readPing();
  delay(100);
  servo_motor.write(90);
  return distance;
}

int lookLeft(){
  servo_motor.write(170);
  delay(500);
  int distance = readPing();
  delay(100);
  servo_motor.write(90);
  return distance;
}


