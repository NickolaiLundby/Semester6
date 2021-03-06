// LAB2_3.cpp : Defines the exported functions for the DLL application.
//

#include "stdafx.h"
#include "LAB2_3.h"
using namespace std;

class CDLLclass23Imp : public CDLLclass
{
	public:
		CDLLclass23Imp();
		~CDLLclass23Imp();
		bool Init(CAppUtil* pUtil);
		bool Run();
		void TearDown();
	private:
		CAppUtil * pAppUtil;
		char* pName;
};

CDLLclass* CreateDllObject()
{
	return new CDLLclass23Imp;
}

void DeleteDllObject(CDLLclass *objPtr)
{
	delete objPtr;
}

CDLLclass23Imp::CDLLclass23Imp()
{
	pName = NULL;
}

CDLLclass23Imp::~CDLLclass23Imp()
{
	delete pName;
}

bool CDLLclass23Imp::Init(CAppUtil* pUtil)
{
	pAppUtil = pUtil;
	string tmpName = pUtil->GetName();
	rsize_t length = tmpName.length() + 1;
	pName = new char[length];
	if (pName == NULL)
		return false;
	strcpy_s(pName, length, tmpName.c_str());
	return true;
}

bool CDLLclass23Imp::Run()
{
	string message = pAppUtil->MyAddString("Hi ", pName);
	cout << message << endl;
	return true;
}

void CDLLclass23Imp::TearDown()
{
	if (pName != NULL)
	{
		delete[] pName;
		pName = NULL;
	}
}