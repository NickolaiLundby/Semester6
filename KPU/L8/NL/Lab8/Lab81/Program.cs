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
            int errorResult;

            Console.WriteLine("Testing Beep");
            errorResult = Kernel32Wrapper.Beep(2000, 500);
            if (errorResult == 0)
                Console.WriteLine("Calling Beep returned an error");

            Kernel32Wrapper.Beep(1000, 300);
            Kernel32Wrapper.Beep(1500, 250);

            Console.ReadLine();
        }
    }
}
