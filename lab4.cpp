#include "stdafx.h"
#include <iostream>
#include <vector>

using namespace std;
const short rows = 5;
const short columns = 5;

class Matrix
{
private:
	int matrix[rows][columns];
	vector<int> minimumsInColumns;
public:
	void ReadMatrixFromConsole();
	void WriteMatrixToConsole();
	void InsertionSortOfRows();
	void ChooseMinimumsOfColumns();
	void WriteMinimums();
	int ProductOfMinimums();
};

void Matrix::ReadMatrixFromConsole()
{
	cout << "Enter matrix:" << endl;

	for (int i = 0; i < rows; i++)
	{
		for (int j = 0; j < columns; j++)
		{
			cout << "matrix[" << i + 1 << "][" << j + 1 << "] = ";
			cin >> matrix[i][j];
		}
	}
}

void Matrix::WriteMatrixToConsole()
{
	for (int i = 0; i < rows; i++, cout << endl)
	{
		for (int j = 0; j < columns; j++)
		{
			cout << "\t" << matrix[i][j];
		}
	}
}

void Matrix::InsertionSortOfRows()
{
	int temp, previousItem;
	for (int i = 0; i < rows; i++)
	{
		for (int j = 1; j < columns; j++)
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

void Matrix::ChooseMinimumsOfColumns()
{
	for (int j = 0; j < columns; j++)
	{
		int min = matrix[0][j];
		for (int i = 0; i < rows; i++)
		{
			if (matrix[i][j] < min)
				min = matrix[i][j];
		}
		minimumsInColumns.push_back(min);
	}
}

void Matrix::WriteMinimums()
{
	cout << "\nMinimums in columns list:" << endl;

	for (int i = 0; i < rows; i++)
	{
		cout << minimumsInColumns[i] << ' ';
	}
	cout << endl << endl;
}


int Matrix::ProductOfMinimums() {
	int product = 1;

	for (int i = 0; i < rows; i++)
	{
		product *= minimumsInColumns[i];
	}
}

int main() 
{
	Matrix matrix;

	matrix.ReadMatrixFromConsole();

	cout << "\n Inputed matrix:" << endl;
	matrix.WriteMatrixToConsole();

	matrix.InsertionSortOfRows();

	cout << "\n Sorted matrix:" << endl;
	matrix.WriteMatrixToConsole();

	matrix.ChooseMinimumsOfColumns();
	matrix.WriteMinimums();

	cout << "Product of minimums: " << matrix.ProductOfMinimums() << endl << endl;

	system("PAUSE");
	return 0;
}
