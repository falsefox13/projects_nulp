/* ========================================
 *
 * Copyright YOUR COMPANY, THE YEAR
 * All Rights Reserved
 * UNPUBLISHED, LICENSED SOFTWARE.
 *
 * CONFIDENTIAL AND PROPRIETARY INFORMATION
 * WHICH IS THE PROPERTY OF your company.
 *
 * ========================================
*/
#include "project.h"

#define PERIOD 20000
#define OFF 0

uint16 algo1_count = 0;
uint16 algo2_count = 0;
 
CY_ISR(MY_ISR) 
{
    algo1_count++;
    algo2_count++;
    
    if(algo1_count < 8000)
        Pin_Red2_Write(!Pin_Red2_Read()); 
    else if(algo1_count < PERIOD / 2) 
    {
        Pin_Red2_Write(OFF);
        Pin_Yellow2_Write(!Pin_Yellow2_Read());
    }
    else if((algo1_count >= 14000 && algo1_count < 15000)
            || (algo1_count >= 16000 && algo1_count < 17000)) 
        Pin_Yellow2_Write(OFF);
    else if (algo1_count < 18000) 
        Pin_Green2_Write(!Pin_Green2_Read());
     else if(algo1_count < PERIOD) 
    {
        Pin_Yellow2_Write(!Pin_Yellow2_Read());
        Pin_Green2_Write(OFF);
    }
    else if(algo1_count == PERIOD)
        algo1_count = 0;
    
    
    if(algo2_count < 4000)  
        Pin_Green1_Write(!Pin_Green1_Read()); 
    else if((algo2_count >= 4000 && algo2_count < 5000)
            || (algo2_count >= 6000 && algo2_count < 7000)) 
    {
        Pin_Red1_Write(1);
        Pin_Green1_Write(1);
    }
    else if(algo2_count < 8000)
        Pin_Green1_Write(!Pin_Green1_Read()); 
    else if(algo2_count < PERIOD / 2) 
    {
        Pin_Red1_Write(!Pin_Red1_Read());
        Pin_Green1_Write(!Pin_Green1_Read());
    }
    else if (algo2_count < 18000)
        Pin_Red1_Write(!Pin_Red1_Read());
    else if(algo2_count < PERIOD) 
    {
        Pin_Red1_Write(!Pin_Red1_Read());
        Pin_Green1_Write(!Pin_Green1_Read());
    }
    else if(algo2_count == PERIOD)
        algo2_count = 0;
}
 
int main()
{
    Timer_1_Start(); // Configure and enable timer
    Timer_Int_StartEx(MY_ISR);
    CyGlobalIntEnable; // Enable global interrupts
     
    for(;;)
    {
    
    }
}
