// addDLL.cpp : Defines the exported functions for the DLL application.
//

#include "stdafx.h"
using namespace std;
#include "addDLL.h"

extern "C" ADDDLL_API int addTwoInts(int x, int y)
{
	return x + y;
}


extern "C" ADDDLL_API char* addTwoCharPtrs(char* x, char* y)
{
	size_t str_len = strlen(x) + strlen(y) + 1;
	char* z = new char[str_len];
	z[0] = 0;
	strcat_s(z, str_len, x);
	strcat_s(z, str_len, y);
	return z;
}

#pragma warning( disable : 4190 )  // Don't warn me about using the non standard C datatype string
extern "C" ADDDLL_API string addTwoStrs(string x, string y)
{
	string z = x + y;
	return z;
}
#pragma warning( default : 4190 )


/* 
//This is an example of an exported variable
ADDDLL_API int naddDLL=0;

// This is an example of an exported function.
ADDDLL_API int fnaddDLL(void)
{
    return 42;
}

// This is the constructor of a class that has been exported.
CaddDLL::CaddDLL()
{
    return;
} */
