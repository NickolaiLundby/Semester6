#pragma once
#include "../CAppUtil.h"

class CAppUtilImp : public CAppUtil
{
	public:
		CAppUtilImp();
		CAppUtilImp(string name);
		virtual string GetName();
		virtual ~CAppUtilImp();
		virtual string MyAddString(string str1, string str2);
	private:
		string m_name;
};