#include "stdafx.h"
#include <stdio.h>
#include <math.h>

int factorial(int n)
{
	int i;
	for (i = n - 1; i > 0; i--)
		n *= i;
	return (n == 0) ? 1 : n;
}

int main()
{
	double lowerInRange, higherInRange;
	double stepForTabulation, fault, result;
	int n;
	printf_s("please enter a range. lower number: ");
	scanf_s("%lf", &lowerInRange);
	double x = lowerInRange;
	printf_s("higher number: ");
	scanf_s("%lf", &higherInRange);
	printf_s("please, enter a step: ");
	scanf_s("%lf", &stepForTabulation);
	printf_s("please, enter a fault: ");
	scanf_s("%lf", &fault);
	double sumOnIteration = 0;
	int m = 20;
	for (x = lowerInRange; x <= higherInRange; x += stepForTabulation)
	{
		n = 1;
		sumOnIteration = 0;
		result = 0;
		while (sumOnIteration <= fault)
		{
			if (x == 0)
				break;
			int argument = (m - n + 1);
			sumOnIteration = ((pow(-1, n) * factorial(argument) / (factorial(n))))* pow(x, n);
			result = sumOnIteration + result;
			n++;
		}
		result += 1;
		printf("For %.2lf, the result = %.1lf\n", x, result);
	}
	return 0;
}
