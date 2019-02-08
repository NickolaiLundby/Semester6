#include "pch.h"
#include "CAppUtilImp.h"

using namespace std;

CAppUtilImp::CAppUtilImp()
{
	m_name = "Jane Doe";
}

CAppUtilImp::CAppUtilImp(string name)
{
	m_name = name;
}

string CAppUtilImp::GetName()
{
	return m_name;
}


string CAppUtilImp::MyAddString(string str1, string str2)
{
	return str1 + str2;
}
