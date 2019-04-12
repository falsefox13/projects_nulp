#include "LiquidCrystal.h"

#define DDR_KEYPAD  DDRF
#define PORT_KEYPAD PORTF
#define PIN_KEYPAD  PINF
#include "keypad4x4.h"

const int buzzerPin = 21;
bool timerStopped = false;
bool menuEnabled = false;
int viewed = 1;
byte value = 0;
int number_saved;

// initialize the library by associating any needed LCD interface pin
// with the arduino pin number it is connected to
const int rs = A8, rw = A9, en = A10, d4 = A11, d5 = A12, d6 = A13, d7 = A14;
LiquidCrystal lcd(rs, rw, en, d4, d5, d6, d7);

const PROGMEM  char sixty[60][3] = {
  {"00"}, {"01"}, {"02"}, {"03"}, {"04"}, {"05"}, {"06"}, {"07"}, {"08"}, {"09"},
  {"10"}, {"11"}, {"12"}, {"13"}, {"14"}, {"15"}, {"16"}, {"17"}, {"18"}, {"19"},
  {"20"}, {"21"}, {"22"}, {"23"}, {"24"}, {"25"}, {"26"}, {"27"}, {"28"}, {"29"},
  {"30"}, {"31"}, {"32"}, {"33"}, {"34"}, {"35"}, {"36"}, {"37"}, {"38"}, {"39"},
  {"40"}, {"41"}, {"42"}, {"43"}, {"44"}, {"45"}, {"46"}, {"47"}, {"48"}, {"49"},
  {"50"}, {"51"}, {"52"}, {"53"}, {"54"}, {"55"}, {"56"}, {"57"}, {"58"}, {"59"}
};

struct Time
{
  unsigned char second, minute, hour;
};
Time T2 = {0, 0, 0};

//збережені записи часу
struct Time *saved_times;


void LCD_WriteStrPROGMEM(char *str, int n)  //вивід масиву символів,
{
  for (int i = 0; i < n; i++)
    lcd.print( (char)pgm_read_byte( &(str[i]) ) );
}

void beep(int timeToBeep) {
  digitalWrite(buzzerPin, HIGH);
  delay(timeToBeep);
  digitalWrite(buzzerPin, LOW);
}

ISR(TIMER2_COMPA_vect)  // Таймер Т5, запускається щосекунди
{
  if (!menuEnabled) {
    if (++T2.second == 60)
    {
      T2.second = 0;
      beep(100);
      if (++T2.minute == 60)
      {
        T2.minute = 0;
        if (++T2.hour == 24)
          T2.hour = 0;
      }
    }
    //пишемо значення часу
    lcd.setCursor(3, 0);
    LCD_WriteStrPROGMEM(sixty[T2.hour], 2);
    lcd.write(':');
    LCD_WriteStrPROGMEM(sixty[T2.minute], 2);
    lcd.write(':');
    LCD_WriteStrPROGMEM(sixty[T2.second], 2);

    //вивід кількості збережених записів в куточку
    lcd.setCursor(13, 1);
    if (number_saved > 0) {
      lcd.print("M");
      lcd.print(number_saved);
    }
  }
}

void setup() {
  saved_times = malloc(2 * sizeof(Time));
  noInterrupts();

  TCCR2A = 0x00;
  TCCR2B = (1 << WGM22) | (1 << CS22) | (1 << CS20); //CTC mode & Prescaler @ 1024
  TIMSK2 = (1 << OCIE2A); // дозвіл на переривання по співпадінню
  OCR2A = 0x3D08;// compare value = 1 sec (16MHz AVR)

  initKeyPad();

  lcd.begin(16, 2);
  interrupts();  // Enable global interrupts

  pinMode(buzzerPin, OUTPUT);
  digitalWrite(buzzerPin, LOW);
}

void loop() {
  if ( isButtonPressed() ) {
    beep(100);

    char key = readKeyFromPad4x4();
    switch (key) {
      case 'D':
        if (!timerStopped) {
          noInterrupts();
          timerStopped = true;
        }
        else {
          interrupts();
          timerStopped = false;
        }
        break;

      case 'C':
        lcd.clear();
        noInterrupts();
        T2 = {0, 0, 0};
        //пишемо обнулені значення
        lcd.setCursor(3, 0);
        LCD_WriteStrPROGMEM(sixty[T2.hour], 2);
        lcd.write(':');
        LCD_WriteStrPROGMEM(sixty[T2.minute], 2);
        lcd.write(':');
        LCD_WriteStrPROGMEM(sixty[T2.second], 2);
        timerStopped = true;
        break;

      case 'E':
        if (!menuEnabled) {
          //зберігаємо поточний час
          saved_times[number_saved] = T2;
          number_saved++;
          lcd.setCursor(0, 1);
          lcd.write("Saved");
        }
        else {
          if ( value >= 0 && value < number_saved )
          {
            beep(100);
            delay(100);
            beep(100);

            lcd.clear();
            lcd.setCursor(0, 0);
            lcd.print("Memory entry:");
            lcd.setCursor(0, 1);
            lcd.print("#");
            lcd.print(value + 1);

            //виводимо зі збережених
            lcd.setCursor(4, 1);
            LCD_WriteStrPROGMEM(sixty[saved_times[value].hour], 2);
            lcd.write(':');
            LCD_WriteStrPROGMEM(sixty[saved_times[value].minute], 2);
            lcd.write(':');
            LCD_WriteStrPROGMEM(sixty[saved_times[value].second], 2);
            value = 0;
          }
          else {
            lcd.clear();
            beep(300);
            lcd.setCursor(3, 0);
            lcd.print("ERROR");
            value = 0;
          }
        }
        break;

      case 'F':
        //резет збережених
        free(saved_times);
        number_saved = 0;
        lcd.setCursor(3, 1);
        lcd.write("Deleted");
        break;

      case 'A': 
        lcd.clear();
        if (!menuEnabled) {
          menuEnabled = true;
          lcd.setCursor(0, 0);
          lcd.print("# Saved ");
          lcd.print(number_saved);
          lcd.setCursor(0, 1);

          if (number_saved) { //якщо є збережені записи 
            lcd.write("1");
            //виводимо перше збережене
            lcd.setCursor(3, 1);
            LCD_WriteStrPROGMEM(sixty[saved_times[0].hour], 2);
            lcd.write(':');
            LCD_WriteStrPROGMEM(sixty[saved_times[0].minute], 2);
            lcd.write(':');
            LCD_WriteStrPROGMEM(sixty[saved_times[0].second], 2);
          }
          else {
            lcd.write("Empty");
          }
        }
        else {
          lcd.clear();
          menuEnabled = false;
          interrupts();
        }
        break;

      case 'B':
        if (menuEnabled && viewed < number_saved) {
          lcd.setCursor(0, 1);
          lcd.print(viewed + 1);
          lcd.setCursor(3, 1);
          LCD_WriteStrPROGMEM(sixty[saved_times[viewed].hour], 2);
          lcd.write(':');
          LCD_WriteStrPROGMEM(sixty[saved_times[viewed].minute], 2);
          lcd.write(':');
          LCD_WriteStrPROGMEM(sixty[saved_times[viewed].second], 2);
          viewed++;
        }
        break;
      default:
        if (menuEnabled) {
          //для зчитування чисел з декількох цифр 
          if ( (key >= '0') && (key <= '9') )
          {
            value = value * 10;
            value = value + key - '0';
          }
        }
        break;
    }
  }
}


