// The following ifdef block is the standard way of creating macros which make exporting
// from a DLL simpler. All files within this DLL are compiled with the CLASSDLL_EXPORTS
// symbol defined on the command line. This symbol should not be defined on any project
// that uses this DLL. This way any other project whose source files include this file see
// CLASSDLL_API functions as being imported from a DLL, whereas this DLL sees symbols
// defined with this macro as being exported.
#ifdef CLASSDLL_EXPORTS
#define CLASSDLL_API __declspec(dllexport)
#else
#define CLASSDLL_API __declspec(dllimport)
#endif

class CLASSDLL_API CMyDllClass
{
public:
	// Construction
	CMyDllClass(void);
	CMyDllClass(const char* aNamePtr);
	~CMyDllClass();
	// Attributes
	void SetName(const char* aNamePtr);
	char * GetName(void);
	// Methods
	void Greeting();
private:
	// Data members
	char *namePtr;
};

/*
// This class is exported from the dll
class CLASSDLL_API CclassDLL {
public:
	CclassDLL(void);
	// TODO: add your methods here.
};

extern CLASSDLL_API int nclassDLL;

CLASSDLL_API int fnclassDLL(void);
*/