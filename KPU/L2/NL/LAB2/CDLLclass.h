#pragma once
// To instantiate the mainclass from the DLL, call the method CreateDllObject.
// CDLLclass * CreateDllObject();
// To tear down the object again from the DLL, call the method DeleteDllObject.
// CDLLClass * DeleteDllObject();
#include "CAppUtil.h"

class CDLLclass
{
	public:
		virtual bool Init(CAppUtil * pUtil) = 0;
		virtual bool Run() = 0;
		virtual void TearDown() = 0;
};