using System;
using System.Runtime.InteropServices;

namespace Lab81
{
    public class Kernel32Wrapper
    {
        [DllImport("Kernel32", SetLastError = true)]
        public static extern int Beep(uint freq, uint duration);
    }
}
