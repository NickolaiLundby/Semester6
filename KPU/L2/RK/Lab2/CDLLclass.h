#pragma once


/* 
Method: CreateDllObject
	method to call to return a pointer to CDLlClass object
Signature: CDLLclass * CreateDllObject();
{
public:
	virtual bool Init(CAppUtil * pUtil) = 0;
	virtual bool Run() = 0;
	virtual void TearDown() = 0;
};