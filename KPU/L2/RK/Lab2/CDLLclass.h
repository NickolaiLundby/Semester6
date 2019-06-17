#pragma once#include "CAppUtil.h"


/* 
Method: CreateDllObject
	method to call to return a pointer to CDLlClass object
Signature: CDLLclass * CreateDllObject();*//*
Method: DeleteDllObject
	method to call to teardown object from the DLL
Signature: CDLLclass * DeleteDllObject();*/class CDLLclass
{
public:
	virtual bool Init(CAppUtil * pUtil) = 0;
	virtual bool Run() = 0;
	virtual void TearDown() = 0;
};