#pragma once
#include "../CAppUtil.h"

using namespace std;

class CAppUtilImp: public CAppUtil
{
public:
	CAppUtilImp();
	CAppUtilImp(string name);
	string GetName();
	~CAppUtilImp();
	string MyAddString(string str1, string str2);

private:
	string m_name;
};
