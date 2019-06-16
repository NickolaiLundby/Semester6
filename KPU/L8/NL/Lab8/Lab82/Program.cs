using System;
using System.Runtime.InteropServices;

namespace Lab82
{
    class Program
    {
        // extern "C" ADDDLL_API int addTwoInts(int a, int b)
        [DllImport("ADDDLL.dll", CallingConvention = CallingConvention.Cdecl)]
        public static extern int addTwoInts(int number1, int number2);

        // extern "C" ADDDLL_API string addTwoStrs(string str1, string str2) <- PInvoke kender ikke til string klassen, så dette fejler.
        [DllImport("ADDDLL.dll", CallingConvention = CallingConvention.Cdecl)]
        public static extern IntPtr addTwoStrs(string string1, string string2);

        // extern "C" ADDDLL_API char* addTwoCharPtrs(char* str1, char* str2)
        [DllImport("ADDDLL.dll", CallingConvention = CallingConvention.Cdecl, CharSet = CharSet.Ansi)]
        public static extern IntPtr addTwoCharPtrs(string str1, string str2);
        static void Main(string[] args)
        {
            Console.WriteLine("Calling native C++ addTwoInts from C#:");
            int num1 = 2;
            int num2 = 5;
            int res;
            res = addTwoInts(num1, num2);
            Console.WriteLine("addTwoInts is saying that: {0} + {1} is {2}", num1, num2, res);

            Console.WriteLine();
            Console.WriteLine("Calling native C++ addTwoChars from C#:");
            string str1 = "Hello";
            string str2 = "World";
            IntPtr ptr = addTwoCharPtrs(str1, str2);
            string strres = Marshal.PtrToStringAnsi(ptr);
            Console.WriteLine("addTwoStrs says that {0} + {1} is {2}", str1, str2, strres);
            //Marshal.FreeHGlobal(ptr); // Can't free memory allocated by C++'s new --> memory leak!
            //To get around this, we need some memory management in our dll.
            //This could be a DeletePtr function on the .dll. Or it could be how we allocate the memory.

            Console.ReadKey();
        }
    }
}
