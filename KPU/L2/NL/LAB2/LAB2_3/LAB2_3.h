#pragma once
#include "../CDLLclass.h"
// The following ifdef block is the standard way of creating macros which make exporting 
// from a DLL simpler. All files within this DLL are compiled with the LAB23_EXPORTS
// symbol defined on the command line. This symbol should not be defined on any project
// that uses this DLL. This way any other project whose source files include this file see 
// LAB23_API functions as being imported from a DLL, whereas this DLL sees symbols
// defined with this macro as being exported.
#ifdef LAB23_EXPORTS
#define LAB23_API __declspec(dllexport)
#else
#define LAB23_API __declspec(dllimport)
#endif

// This class is exported from the LAB2_3.dll
extern "C" LAB23_API CDLLclass* CreateDllObject();

extern "C" LAB23_API void DeleteDllObject(CDLLclass*);