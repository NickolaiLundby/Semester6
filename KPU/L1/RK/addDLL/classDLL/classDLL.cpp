// classDLL.cpp : Defines the exported functions for the DLL application.
//

#include "stdafx.h"
using namespace std;
#include "classDLL.h"


CMyDllClass::CMyDllClass()
{
	namePtr = NULL;
	return;
}

CMyDllClass::CMyDllClass(const char* aNamePtr)
{
	size_t len = strlen(aNamePtr) + 1;
	namePtr = new char[len];
	strcpy_s(namePtr, len, aNamePtr);
	return;
}

CMyDllClass::~CMyDllClass()
{
	if (namePtr != NULL)
		delete[]namePtr;
	return;
}

void CMyDllClass::SetName(const char* aNamePtr)
{
	if (namePtr != NULL)
		delete[]namePtr;
	size_t len = strlen(aNamePtr) + 1;
	namePtr = new char[len];
	strcpy_s(namePtr, len, aNamePtr);
	return;
}

char* CMyDllClass::GetName()
{
	return namePtr;
}

void CMyDllClass::Greeting()
{
	cout << "Hello ";
	if (namePtr != NULL) {
		cout << namePtr;
	}
	cout << endl;
}




