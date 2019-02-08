// lab2_3.cpp : Defines the exported functions for the DLL application.
//

#include "stdafx.h"
#include "lab2_3.h"
#include <iostream>

class CDLLclassImp : public CDLLclass
{
public:
	CDLLclassImp();
	~CDLLclassImp();
	virtual bool Init(CAppUtil * pUtil);
	virtual bool Run(void);
	virtual void TearDown(void);
private:
	CAppUtil *pAppUtil;
	char * pName;
};


CDLLclass* CreateDllObject()
{
	return new CDLLclassImp();
}

void DeleteDllObject(CDLLclass *cdllclass)
{
	delete cdllclass;
}

CDLLclassImp::CDLLclassImp()
{
	pName = NULL;
}

CDLLclassImp::~CDLLclassImp()
{
	if (pName != NULL)
		delete[] pName;
}

bool CDLLclassImp::Init(CAppUtil * pUtil)
{
	pAppUtil = pUtil;
	string name = pUtil->GetName();
	rsize_t length = name.length() + 1;
	pName = new char[length];
	if (pName == NULL)
		return false;
	strcpy_s(pName, length, name.c_str());
	return true;
}

bool CDLLclassImp::Run(void)
{
	string message = pAppUtil->MyAddString("Hello ", pName);
	cout << message << endl;
	return true;
}

void CDLLclassImp::TearDown(void)
{
	if (pName != NULL)
	{
		delete[] pName;
		pName = NULL;
	}
}
