#include "pch.h"

using namespace std;

int main()
{
	wstring dllname;
	HINSTANCE dllHandler;

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
	string name;
	cout << "Enter your name:\n";
	getline(cin, name);
	CAppUtilImp* cappobject = new CAppUtilImp(name);

	CDLLclass* dllobject = CreateDllObject();
	dllobject->Init(cappobject);
	dllobject->Run();
	dllobject->TearDown();
	DeleteDllObject(dllobject);

}