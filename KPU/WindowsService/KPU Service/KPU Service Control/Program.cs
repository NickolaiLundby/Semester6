using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.ServiceProcess;

namespace KPU_Service_Control
{
    static class Program
    {
        private const string serviceName = "KPUService";
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            ServiceController[] scServices;
            scServices = ServiceController.GetServices();
            foreach(ServiceController scTemp in scServices)
            {
                if(scTemp.ServiceName == serviceName)
                {
                    ServiceController sc = new ServiceController(serviceName);
                    Application.Run(new Form1(sc));
                    return;
                }
            }
            MessageBox.Show("No service found!", "Error");
            return;
        }
    }
}
