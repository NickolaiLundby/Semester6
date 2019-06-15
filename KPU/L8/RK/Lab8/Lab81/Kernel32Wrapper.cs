using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Runtime.InteropServices;

namespace Lab81
{
	public class Kernel32Wrapper
	{
		[DllImport("Kernel32", SetLastError = true)]
		public static extern int Beep(uint freq, uint duration);
	}
}
