#include "stdafx.h"
#include <iostream>
#include <vector>

using namespace std;
const short rows = 5;
const short cols = 5;

class Matrix
{
private:
	int matrix[rows][cols];
	vector<int> minimumsInCols;
public:
	void InputMatrix();
	void OutputMatrix();
	void InsertionSort();
	void MinimumsOfColumns();
	void WriteMinimums();
	int ProductOfMinimums();
};

void Matrix::InputMatrix()
{
	cout << "Enter matrix:" << endl;

	for (int i = 0; i < rows; i++)
	{
		for (int j = 0; j < cols; j++)
		{
			cout << "matrix[" << i + 1 << "][" << j + 1 << "] = ";
			cin >> matrix[i][j];
		}
	}
}

void Matrix::OutputMatrix()
{
	for (int i = 0; i < rows; i++, cout << endl)
	{
		for (int j = 0; j < cols; j++)
		{
			cout << "\t" << matrix[i][j];
		}
	}
}

void Matrix::InsertionSort()
{
	int temp, previousItem;
	for (int i = 0; i < rows; i++)
	{
		for (int j = 1; j < cols; j++)
		{
			temp = matrix[i][j];
			previousItem = j - 1;
			while (previousItem >= 0 && matrix[i][previousItem] > temp)
			{
				matrix[i][previousItem + 1] = matrix[i][previousItem];
				matrix[i][previousItem] = temp;
				previousItem--;
			}
		}
	}
}

void Matrix::MinimumsOfColumns()
{
	for (int j = 0; j < cols; j++)
	{
		int min = matrix[0][j];
		for (int i = 0; i < rows; i++)
		{
			if (matrix[i][j] < min)
				min = matrix[i][j];
		}
		minimumsInCols.push_back(min);
	}
}

void Matrix::WriteMinimums()
{
	cout << "\nMinimums in columns list:" << endl;

	for (int i = 0; i < rows; i++)
	{
		cout << minimumsInCols[i] << ' ';
	}
	cout << endl << endl;
}


int Matrix::ProductOfMinimums() {
	int product = 1;

	for (int i = 0; i < rows; i++)
	{
		product *= minimumsInCols[i];
	}
}

int main() 
{
	Matrix matrix;

	matrix.InputMatrix();

	cout << "\n Inputed matrix:" << endl;
	matrix.OutputMatrix();

	matrix.InsertionSort();

	cout << "\n Sorted matrix:" << endl;
	matrix.OutputMatrix();

	matrix.MinimumsOfColumns();
	matrix.WriteMinimums();

	cout << "Product of minimums: " << matrix.ProductOfMinimums() << endl << endl;

	system("PAUSE");
	return 0;
}
