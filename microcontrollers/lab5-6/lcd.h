#include <LedControl.h>
#include <LiquidCrystal_I2C.h>

LiquidCrystal_I2C lcd(0x27, 16, 2);
LedControl lc = LedControl(11, 9, 10, 1);

void printRight() {
  lcd.clear();
  lcd.setCursor(3, 0);
  lcd.print("Right");
}

void printLeft() {
  lcd.clear();
  lcd.setCursor(3, 0);
  lcd.print("Left");
}

void printForward() {
  lc.clearDisplay(0);
  lcd.clear();
  lcd.setCursor(3, 0);
  lcd.print("Forward");
}

