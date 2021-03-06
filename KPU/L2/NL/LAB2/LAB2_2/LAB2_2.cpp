// LAB2_2.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include "../CDLLclass.h"
#include "CAppUtilImp.h"

using namespace std;

int main()
{
	//VARIABLES
	CAppUtilImp *pUtil;
	wstring libraryName;
	HINSTANCE dllHandle = NULL;
	const char* createDllObject = "CreateDllObject";
	const char* deleteDllObject = "DeleteDllObject";
	string name;

	//TYPEDEFS
	typedef CDLLclass*(*TCreateObject)();
	TCreateObject tCreateObject;
	typedef void*(*TDeleteObject)(CDLLclass*);
	TDeleteObject tDeleteObject;

	//GET INPUTS FROM USER
	cout << "Type the name of the DLL to load: " << endl;
	getline(wcin, libraryName);
	cout << "Please enter your name: ";
	getline(cin, name);

	//Load library
	dllHandle = LoadLibrary(libraryName.c_str());

	//GET PROCESS ADDRESSES
	tCreateObject = (TCreateObject)GetProcAddress(dllHandle, createDllObject);
	if (tCreateObject == NULL)
	{
		wcout << "Create function missing" << endl;
		return 1;
	}
	tDeleteObject = (TDeleteObject)GetProcAddress(dllHandle, deleteDllObject);
	if (tDeleteObject == NULL)
	{
		wcout << "Delete function missing" << endl;
		return 1;
	}

	//Create objects and run them
	pUtil = new CAppUtilImp(name);
	CDLLclass* myObj = tCreateObject();
	myObj->Init(pUtil);
	myObj->Run();
	myObj->TearDown();

	//Delete ressources and terminate
	tDeleteObject(myObj);
	delete pUtil;
	if (dllHandle != NULL)
		FreeLibrary(dllHandle);

	cout << "Press key to terminate application: ";
	getline(cin, name);

	return 0;
}

