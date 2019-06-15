using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab12_Console
{
	class Program
	{
		static void Main(string[] args)
		{
			var kpuService = new KPUFileWatcherService();
			kpuService.OnStart(new string[1]);
			Console.ReadKey();
		}
	}
}
