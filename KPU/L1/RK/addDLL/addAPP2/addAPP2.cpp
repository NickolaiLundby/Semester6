// addAPP2.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include "pch.h"
using namespace std;

int main()
{
	wstring libraryName = TEXT("ADDDLL.dll");

	typedef int(*TAddTwoInts) (int, int);
	TAddTwoInts addTwoInts;

	typedef char* (*TAddTwoCharPtrs) (char*, char*);
	TAddTwoCharPtrs addTwoCharPtrs;

	typedef string(*TAddTwoStrs) (string, string);
	TAddTwoStrs addTwoStrs;

	HINSTANCE dllHandler = NULL;
	dllHandler = LoadLibrary(libraryName.c_str());
	if (dllHandler == NULL) {
		wcout << "Failed to load dll file" << endl;
		return 1;
	}

	const char* funcName = "addTwoInts";
	addTwoInts = (TAddTwoInts)GetProcAddress(dllHandler, funcName);
	if (addTwoInts == NULL) {
		cout << "failed to load AddTwoInts function" << endl;
		return 1;
	}

	funcName = "addTwoCharPtrs";
	addTwoCharPtrs = (TAddTwoCharPtrs)GetProcAddress(dllHandler, funcName);
	if (addTwoCharPtrs == NULL) {
		cout << "failed to laod AddTwoCharPtrs function" << endl;
		return 1;
	}

	funcName = "addTwoStrs";
	addTwoStrs = (TAddTwoStrs)GetProcAddress(dllHandler, funcName);
	if (addTwoStrs == NULL) {
		cout << "failed to load AddTwoStrs" << endl;
		return 1;
	}


	cout << "Test add two ints" << endl;
	cout << "Enter first int: " << endl;
	int a;
	cin >> a;

	cout << "Enter second int: " << endl;
	int b;
	cin >> b;

	int intRes = addTwoInts(a, b);
	cout << "Result:" << intRes << endl;

	cout << "Test add two char*" << endl;
	const int STRLEN = 81;
	char cpstr1[STRLEN];
	char cpstr2[STRLEN];
	char *cpstrRes = NULL;
	cout << "Please first string: ";
	cin.getline(cpstr1, STRLEN);  // Get rid of new-line chars
	cin.getline(cpstr1, STRLEN);
	cout << "Please enter second string: ";
	cin.getline(cpstr2, STRLEN);
	cpstrRes = addTwoCharPtrs(cpstr1, cpstr2);

	cout << "Result: " << cpstrRes << "\n\n";
	delete[] cpstrRes;	// Free the allocated memory

	cout << "Test add two strings*" << endl;
	cout << "Enter first string: " << endl;
	string str1;
	getline(cin, str1);

	cout << "Enter second string: " << endl;
	string str2;
	getline(cin, str2);

	string strRes = addTwoStrs(str1, str2);

	cout << "Result: " << strRes << endl;

	FreeLibrary(dllHandler);

	return 0;
}

// Run program: Ctrl + F5 or Debug > Start Without Debugging menu
// Debug program: F5 or Debug > Start Debugging menu

// Tips for Getting Started: 
//   1. Use the Solution Explorer window to add/manage files
//   2. Use the Team Explorer window to connect to source control
//   3. Use the Output window to see build output and other messages
//   4. Use the Error List window to view errors
//   5. Go to Project > Add New Item to create new code files, or Project > Add Existing Item to add existing code files to the project
//   6. In the future, to open this project again, go to File > Open > Project and select the .sln file
