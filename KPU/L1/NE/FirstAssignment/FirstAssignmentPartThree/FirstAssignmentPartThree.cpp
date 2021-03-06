// FirstAssignmentPartThree.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
using namespace std;

void GiveErrMessageFuncMissing(wstring libraryName, const char* funcName)
{
	wcout << "Unable to locate the function: " << funcName << " in " << libraryName << endl;
}

int main()
{
	//Declaration of all variables
	int int1;
	int int2;
	int intRes;
	const int STRLEN = 81;
	char charInput1[STRLEN];
	char charInput2[STRLEN];
	char* charRes = NULL;
	string strInput1;
	string strInput2;
	string strRes;
	wstring libraryName;

	//Library name to load
	libraryName = TEXT("FIRSTASSIGNMENT.dll");

	//Typedefinition to hold the type of myAdd method and a variable to hold the function
	typedef int(*PFMyAdd)(int, int);
	PFMyAdd pfMyAdd;

	//Typedefinition to hold the type of myAddChars method and a variable to hold the function
	typedef char*(*PFMyAddChars)(char*, char*);
	PFMyAddChars pfMyAddChars;

	//Typedefinition to hold the type of myAddStrings method and a variable to hold the function
	typedef string(*PFMyAddStrings)(string, string);
	PFMyAddStrings pfMyAddStrings;

	//Define the dllHandler and handle error if NULL
	HINSTANCE dllHandle = NULL;
	dllHandle = LoadLibrary(libraryName.c_str());
	if (dllHandle == NULL)
	{
		wcout << "Unable to load library: " << libraryName << endl;
		return 1;
	}

	//Load the function by using GetProcAddress and handle error in case of NULL
	const char* addIntsFunc = "addTwoInts";
	pfMyAdd = (PFMyAdd)GetProcAddress(dllHandle, addIntsFunc);
	if (pfMyAdd == NULL)
	{
		GiveErrMessageFuncMissing(libraryName, addIntsFunc);
		return 1;
	}

	//Load the function by using GetProcAddress and handle error in case of NULL
	const char* addCharsFunc = "addTwoChars";
	pfMyAddChars = (PFMyAddChars)GetProcAddress(dllHandle, addCharsFunc);
	if (pfMyAddChars == NULL)
	{
		GiveErrMessageFuncMissing(libraryName, addCharsFunc);
		return 1;
	}

	//Load the function by using GetProcAddress and handle error in case of NULL
	const char* addStringsFunc = "addMyTwoStrings";
	pfMyAddStrings = (PFMyAddStrings)GetProcAddress(dllHandle, addStringsFunc);
	if (pfMyAddStrings == NULL)
	{
		GiveErrMessageFuncMissing(libraryName, addStringsFunc);
		return 1;
	}

	//TEST THE FUNCTIONS
	//Test the function adding two integers:
	cout << "Test of adding two integers: \n\n";
	cout << "Enter first number: ";
	cin >> int1;
	cout << "Enter second number: ";
	cin >> int2;
	intRes = pfMyAdd(int1, int2);
	cout << "The sum of " << int1 << " and " << int2 << " is " << intRes << ".\n\n";

	//Test the function of concatenating two chars:
	cout << "Test of adding two chars: \n\n";
	cout << "Enter first string: ";
	cin.getline(charInput1, STRLEN); //Get rid of new line char
	cin.getline(charInput1, STRLEN);
	cout << "Enter second string: ";
	cin.getline(charInput2, STRLEN);
	charRes = pfMyAddChars(charInput1, charInput2);
	cout << "The result of concatenating chars: " << charInput1 << " and " << charInput2 << " is " << charRes << ".\n\n";

	//Test the function of concatenating two strings:
	cout << "Test of adding two strings: \n\n";
	cout << "Enter first string: ";
	getline(cin, strInput1);
	cout << "Enter second string: ";
	getline(cin, strInput2);
	strRes = pfMyAddStrings(strInput1, strInput2);
	cout << "The result of concatenating strings: " << strInput1 << " and " << strInput2 << " is " << strRes << ".\n\n";

	//Free the library:
	FreeLibrary(dllHandle);

	return 0;
}

