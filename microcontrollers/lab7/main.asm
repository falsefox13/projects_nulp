;.include "m2560def.inc"
.macro outi
	ldi _temp3, @1
	.if @0 < 0x40
		out @0, _temp3
	.else
		sts @0, _temp3
	.endif
.endm
.macro outr
	mov _temp3, @1
	.if @0 < 0x40
		out @0, _temp3
	.else
		sts @0, _temp3
	.endif
.endm

;����� ��� ������� ���������� �����������------------------
.def	_temp1		=r16
.def	_temp2		=r17
.def	_temp3		=r18
.def	_temp4		=r13
.def	_temp5		=r14
.def	_temp6		=r19

.def	_logic1		=r0		;0bit - PC5 ��������,
.def	_logic2		=r1		;0bit - PC7 
.def	_counter1	=r20
.def	_counter2	=r21

;RAM  (���������� ���'���)
		.DSEG	
																																																																																																																																																																																																																																																																																																																																					
;FLASH  (��� �������� ��� ��������, �� ���� ������������)
		.CSEG 
		;������ ����������
		.org	0x000
		jmp	reset

		.org	0x05E ; Timer/Counter1 Compare Match A
		jmp TIMER5_COMPA
		; .......
		.org 0x070 
		reti          ; USART3 Tx Complete

		algo8_arr:
			.db 0b00000001, 0b10000000
			.db 0b00000010, 0b01000000 
			.db 0b00000100, 0b00100000
			.db 0b00001000, 0b00010000
			.db 0b00000000, 0b00000000

		algo3_arr:
			.db 0b10000001, 0b01000010, 0b00100100, 0b00011000, 0b00000000, 0b00000000
;--------------------------------------------------------------------	
TIMER5_COMPA:	; Timer/Counter5 Compare Match A
		sbrs	_logic1, 0
		rjmp	casePC7
		in		_temp1, PORTA
		ldi 	ZL,low(algo8_arr*2) 	
		ldi  	ZH,high(algo8_arr*2)

		ldi		_temp3,0
        add		ZL,_counter1
        adc		ZH,_temp3   
 
		LPM 	_temp2, Z
		out		PORTA, _temp2

		; ��������� ���������
		inc		_counter1
		cpi		_counter1, 10
		brne	casePC7
		clr		_counter1

		clt ;	�=0
		bld		_logic1, 0

	casePC7: ; ���� ��������� ������ �7
		sbrs	_logic2, 0
		rjmp	end_timer
		in		_temp4, PORTF
		ldi 	ZL,low(algo3_arr*2) 	
		ldi  	ZH,high(algo3_arr*2)

		ldi		_temp6,0
        add		ZL,_counter2
        adc		ZH,_temp6   
 
		LPM 	_temp5, Z
		out		PORTF, _temp5

		; ��������� ���������
		inc		_counter2
		cpi		_counter2, 6
		brne	end_timer
		clr		_counter2

		;����� ����� ��������
		;_logic bit0 = 0
		clt
		bld		_logic2, 0
end_timer:
		reti
		
reset:		
		;����������� �����
		ldi		_temp1, Low(RAMEND)
		out		SPL, _temp1
		ldi		_temp1, High(RAMEND)
		out		SPH, _temp1

		;��������� �����������
		ldi		r16, 0b10000000
		out		ACSR, r16

		;����������� ����� �����/������
		ldi		_temp1, 0x00
		ldi		_temp2, 0xFF

		;���� A -- ������ �� ����� 
		out		DDRA, _temp2  ; ���� ���� ������ �� ����� DDR_A <- 11111111
		out		PORTA, _temp1  ;0V   PORT_A <- 00000000

		;���� F -- ������ �� ����� 
		out		DDRF, _temp2  ; ���� ���� ������ �� ����� DDR_F <- 11111111
		out		PORTF, _temp1  ;0V   PORT_F <- 00000000

		;���� B -- ������ �� ����� 
		out		DDRB, _temp2  ; ���� ���� ������ �� ����� DDR_B <- 11111111
		out		PORTB, _temp1  ;0V   PORT_B <- 00000000

		;���� C -- ������ �� ���� � ����������� ����������� 
		out		DDRC, _temp1  ; ���� ���� ������ �� ���� DDR_C <- 00000000
		out		PORTC, _temp2  ;�����.������� ���������   PORT_C <- 11111111


		outi	TCCR5A, 0x00
		outi	TCCR5B, (1 << WGM52) | (1 << CS52) | (1 << CS50) ; CTC mode & Prescaler @ 1024
		outi	TIMSK5, (1 << OCIE5A)	; ����� �� ����������� �� ���������
		outi	OCR5AH, 0x3D
		outi	OCR5AL, 0x08
		
		sei ; ���. ����� �� �����������
		;�������� ���������� ����
main:		
		; ���� ��������� ������ PC5
		sbic	PINC, 5
		rjmp	elsePC7
		; ��������� ���������
		clr		_counter1
		;������ ���������
		;_logic bit0 = 1
		set ;	�=1
		bld		_logic1, 0
		;buzzer
		sbi		PORTB, 0
		rcall	delay200ms	
		cbi		PORTB, 0

		rjmp endPC5
elsePC7:
		sbic	PINC, 7
		rjmp	endPC5
		; ��������� ���������
		clr		_counter2
		;������ ���������
		;_logic bit0 = 1
		set ;	�=1
		bld		_logic2, 0
		;buzzer
		sbi		PORTB, 0
		rcall	delay200ms	
		cbi		PORTB, 0

		rjmp endPC5
endPC5:


		rjmp main
;--------------------------------------------------------------------
		;����������
delay200ms:
		ldi		_temp1, 0x00
		ldi		_temp2, 0xC4
		ldi		_temp3, 0x09
delay115:
		ldi		_temp1, 0x40
		ldi		_temp2, 0x77
		ldi		_temp3, 0x1B
delay:	subi	_temp1, 1
		sbci	_temp2, 0
		sbci	_temp3, 0
		brne	delay
		ret

;EEPROM   (��������������� ������)
		.ESEG


