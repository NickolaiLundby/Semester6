#pragma once
// The following ifdef block is the standard way of creating macros which make exporting 
// from a DLL simpler. All files within this DLL are compiled with the LAB3_DLL_EXPORTS
// symbol defined on the command line. This symbol should not be defined on any project
// that uses this DLL. This way any other project whose source files include this file see 
// LAB3_DLL_API functions as being imported from a DLL, whereas this DLL sees symbols
// defined with this macro as being exported.
#ifdef LAB3DLL_EXPORTS
#define LAB3_DLL_API __declspec(dllexport)
#else
#define LAB3_DLL_API __declspec(dllimport)
#endif

typedef double (CALLBACK *FafX)(double x);
// This exported function call backs pF in a loop where x is incremented by 1 
//extern "C" DLLWITHCALLBACK_API void SomeUnmanagedFunctionUsingCallback(double x, FafX pF);
extern "C" LAB3_DLL_API void SomeUnmanagedFunctionUsingCallback(double x, FafX pF);