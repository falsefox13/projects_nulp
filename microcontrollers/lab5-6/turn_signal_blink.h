byte en_left[] =
{
  B00000111, 
  B00001111,
  B00011111,
  B01111111,
  B01111111,
  B00011111,
  B00001111,
  B00000111
};

byte en_right[] =
{
  B11100000, 
  B11110000,
  B11111000,
  B11111110,
  B11111110,
  B11111000,
  B11110000,
  B11100000
};

void blink_right()
{
  lc.clearDisplay(0);
  for (int i = 0; i < 8; i++)
  {
    lc.setRow(0,i, en_right[i]);
  }
}

void blink_left()
{
  lc.clearDisplay(0);
  for (int i = 0; i < 8; i++)
  {
    lc.setRow(0,i, en_left[i]);
  }
}

