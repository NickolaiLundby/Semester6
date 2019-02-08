#include "stdafx.h"
#include "CAppUtilImp.h"

using namespace std;

CAppUtilImp::CAppUtilImp()
{
	m_name = "Default";
	return;
}

CAppUtilImp::CAppUtilImp(string name)
{
	m_name = name;
	return;
}

string CAppUtilImp::GetName()
{
	return m_name;
}

CAppUtilImp::~CAppUtilImp()
{
}

string CAppUtilImp::MyAddString(string str1, string str2)
{
	return str1 + str2;
}