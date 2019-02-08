// Lab2_2.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include "pch.h"

using namespace std;

int main()
{
	wstring dllname;
	HINSTANCE dllHandler;
	CAppUtilImp* cappobject = new CAppUtilImp();

	typedef CDLLclass*(*TCreateDllObject)();
	TCreateDllObject CreateDllObject;

	typedef void*(*TDeleteDllObject)(CDLLclass*);
	TDeleteDllObject DeleteDllObject;

    cout << "Enter DLL name:\n";
	getline(wcin, dllname);

	dllHandler = LoadLibrary(dllname.c_str());
	if (dllHandler == NULL) {
		wcout << "Failed to load dll file" << endl;
		return 1;
	}

	const char* funcName = "CreateDllObject";
	CreateDllObject = (TCreateDllObject)GetProcAddress(dllHandler, funcName);
	if (CreateDllObject == NULL) {
		cout << "failed to load CreateDllObject method" << endl;
		return 1;
	}

	funcName = "DeleteDllObject";
	DeleteDllObject = (TDeleteDllObject)GetProcAddress(dllHandler, funcName);
	if (DeleteDllObject == NULL) {
		cout << "failed to load DeleteDllObject method" << endl;
		return 1;
	}

	CDLLclass* dllobject = CreateDllObject();
	dllobject->Init(cappobject);
	dllobject->Run();
	dllobject->TearDown();
	DeleteDllObject(dllobject);

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
