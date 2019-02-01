// The following ifdef block is the standard way of creating macros which make exporting
// from a DLL simpler. All files within this DLL are compiled with the ADDDLL_EXPORTS
// symbol defined on the command line. This symbol should not be defined on any project
// that uses this DLL. This way any other project whose source files include this file see
// ADDDLL_API functions as being imported from a DLL, whereas this DLL sees symbols
// defined with this macro as being exported.

#pragma once
#ifdef ADDDLL_EXPORTS
#define ADDDLL_API __declspec(dllexport)
#else
#define ADDDLL_API __declspec(dllimport)
#endif


extern "C" ADDDLL_API int addTwoInts(int x, int y);

extern "C" ADDDLL_API char* addTwoCharPtrs(char* x, char* y);

#pragma warning( disable : 4190 ) 
extern "C" ADDDLL_API string addTwoStrs(string x, string y);
#pragma warning( default : 4190 )
