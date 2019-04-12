
 #include "configuration.h"

/*motor control*/
void go_Advance(void)  //Forward
{
  digitalWrite(dir1PinL,HIGH);
  digitalWrite(dir2PinL,LOW);
  digitalWrite(dir1PinR,HIGH);
  digitalWrite(dir2PinR,LOW);
}
void go_Left(void)  //Turn left
{
  digitalWrite(dir1PinL,HIGH);
  digitalWrite(dir2PinL,LOW);
  digitalWrite(dir1PinR,LOW);
  digitalWrite(dir2PinR,HIGH);
}
void go_Right(void)  //Turn right
{
  digitalWrite(dir1PinL,LOW);
  digitalWrite(dir2PinL,HIGH);
  digitalWrite(dir1PinR,HIGH);
  digitalWrite(dir2PinR,LOW);
}
void go_Back(void)  //Reverse
{
  digitalWrite(dir1PinL, LOW);
  digitalWrite(dir2PinL,HIGH);
  digitalWrite(dir1PinR,LOW);
  digitalWrite(dir2PinR,HIGH);
}
void stop_Stop()    //Stop
{
  digitalWrite(dir1PinL, LOW);
  digitalWrite(dir2PinL,LOW);
  digitalWrite(dir1PinR,LOW);
  digitalWrite(dir2PinR,LOW);
}

/*set motor speed */
void set_Motorspeed(int speed_L,int speed_R)
{
  analogWrite(speedPinL,speed_L); 
  analogWrite(speedPinR,speed_R);   
}

void buzz_ON()   //open buzzer
{
 digitalWrite(BUZZ_PIN, LOW);
}
void buzz_OFF()  //close buzzer
{
 digitalWrite(BUZZ_PIN, HIGH);
}
void alarm(){
  buzz_ON();
  delay(100);
  buzz_OFF();
}

//Pins initialize
void init_GPIO()
{
	pinMode(dir1PinL, OUTPUT); 
	pinMode(dir2PinL, OUTPUT); 
	pinMode(speedPinL, OUTPUT);  
  pinMode(BUZZ_PIN, OUTPUT);
	pinMode(dir1PinR, OUTPUT);
  pinMode(dir2PinR, OUTPUT); 
  pinMode(speedPinR, OUTPUT); 
	stop_Stop();
}

void setup()
{
	init_GPIO();
  	go_Advance(); 
    set_Motorspeed(255,255);
    delay(2500);

    go_Right(); 
    set_Motorspeed(255,255);
    delay(200);

    go_Advance();
    set_Motorspeed(128,128);
    delay(1500);

    go_Right();
    set_Motorspeed(255,255);
    delay(250);

    go_Advance();
    set_Motorspeed(128,128);
    delay(1500);

    go_Right();
    set_Motorspeed(255,255);
    delay(200);

    go_Advance();
    set_Motorspeed(255,255);
    delay(2500);
  
    go_Right();
    set_Motorspeed(255,255);
    delay(400);

    go_Advance();
    set_Motorspeed(128,128);
    delay(1500);

    go_Left();
    set_Motorspeed(255, 255);
    delay(220);

    go_Advance();
    set_Motorspeed(128,128);
    delay(1500);
  alarm();
  stop_Stop();
}

void loop(){
}
