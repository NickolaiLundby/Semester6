using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab81
{
	class Program
	{
		static void Main(string[] args)
		{
			int errorResult = Kernel32Wrapper.Beep(250, 500);
			if (errorResult == 0)
				Console.Write("Error in Beep");
		}
	}
}
