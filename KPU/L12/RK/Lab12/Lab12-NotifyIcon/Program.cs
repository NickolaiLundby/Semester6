using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.ServiceProcess;

namespace Lab12_NotifyIcon
{
	static class Program
	{
		private const string serviceName = "KPUfileWatcherService";
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
			foreach (ServiceController scTemp in scServices)
			{
				if (scTemp.ServiceName == serviceName)
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
