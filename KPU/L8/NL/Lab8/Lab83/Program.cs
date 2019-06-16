using System;
using System.Runtime.InteropServices;

namespace Lab83
{
    class Program
    {
        [DllImport("Lab3_Dll.dll", CallingConvention = CallingConvention.Cdecl)]
        public static extern void SomeUnmanagedFunctionUsingCallback(double x, CallBackDelegate callback);

        // Definition of delegate used for callback function
        public delegate double CallBackDelegate(double x);

        static void Main(string[] args)
        {
            CallBackDelegate functionDeletegate = new CallBackDelegate(Math.Sin);
            SomeUnmanagedFunctionUsingCallback(Math.PI / 2.0, functionDeletegate);

            Console.WriteLine("\n-----------------------------\n");
            functionDeletegate = new CallBackDelegate(Square);
            SomeUnmanagedFunctionUsingCallback(2.0, functionDeletegate);

            Console.ReadKey();
        }

        public static double Square(double x)
        {
            return x * x;
        }
    }
}
