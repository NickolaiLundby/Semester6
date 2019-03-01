using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Lab41;

namespace NuGetLab
{
	class Program
	{
		static void Main(string[] args)
		{
			var myCPRChecker = new CprCheck();
			string cprNo = "0101901234";
			CprError cprError;
			CprCheck cprCheck = new CprCheck();

			cprCheck.Check(cprNo, out cprError);

			switch (cprError)
			{
				case CprError.NoError:
					Console.WriteLine("CPR Valid");
					break;
				case CprError.FormatError:
					Console.WriteLine("Invalid format!");
					break;
				case CprError.DateError:
					Console.WriteLine("Invalid date!");
					break;
				case CprError.Check11Error:
					Console.WriteLine("Invalid CPR!");
					break;
				default:
					Console.WriteLine("Unknown error");
					break;
			}
			Console.ReadKey();
		}
	}
}
