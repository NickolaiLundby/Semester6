using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Runtime.InteropServices;

namespace Lab82
{
	class Program
	{
		[DllImport("ADDDLL.dll", CallingConvention = CallingConvention.Cdecl)]
		public static extern int addTwoInts(int x, int y);

		[DllImport("ADDDLL.dll", CallingConvention = CallingConvention.Cdecl, CharSet = CharSet.Ansi)]
		public static extern IntPtr addTwoCharPtrs(string x, string y);

		[DllImport("ADDDLL.dll", CallingConvention = CallingConvention.Cdecl)]
		public static extern IntPtr addTwoStrs(string x, string y);

		static void Main(string[] args)
		{
			Console.Write("Enter int:\n");
			int res;
			try
			{
				int x = Int32.Parse(Console.ReadLine());
				Console.Write("Enter another int:\n");
				int y = Int32.Parse(Console.ReadLine());
				res = addTwoInts(x, y);
			} 			
			catch
			{
				Console.Write("Error, must be ints");
				Console.ReadLine();
				return;
			}
			Console.Write("Answer:" + res);
			Console.ReadLine();

			Console.WriteLine("Calling native C++ myAddStr from C#:");
			Console.Write("Enter string:\n");
			string str1 = Console.ReadLine();
			Console.Write("Enter another string:\n");
			string str2 = Console.ReadLine();
			IntPtr ptr = addTwoCharPtrs(str1, str2);
			string strRes = Marshal.PtrToStringAnsi(ptr);
			Console.WriteLine(strRes);
			//Marshal.FreeHGlobal(ptr); // Can't free memory allocated by C++'s new --> memory leak! .. Needs changes in ADDDLL.dll
			Console.ReadLine();

			/* not working, no built-in conversion between c# and c++ string types
			Console.Write("Enter string:\n");
			string str3 = Console.ReadLine();
			Console.Write("Enter another string:\n");
			string str4 = Console.ReadLine();
			ptr = addTwoStrs(str3, str4);  // dette burde ikke virke, og det gør det heller ikke!
			string strRes2 = Marshal.PtrToStringAuto(ptr);
			Console.WriteLine(strRes2);
			Console.ReadLine();
			*/
		}
	}
}
