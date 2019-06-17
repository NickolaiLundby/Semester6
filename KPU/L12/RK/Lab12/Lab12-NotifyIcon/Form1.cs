using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;

using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.ServiceProcess;
using System.IO;

namespace Lab12_NotifyIcon
{
	public partial class Form1 : Form
	{
		private ServiceController sc;
		private const string configPath = "C:\\Windows\\Temp\\KPU1\\kpuwserviceconfig";
		private const int configMonitorPathCMD = 221;
		private const int configCollectionPathCMD = 222;

		public Form1()
		{			
			InitializeComponent();
		}
		public Form1(ServiceController sc)
		{
			this.sc = sc;

			InitializeComponent();
		}

		private void Start_Click(object sender, EventArgs e)
		{
			if (sc.Status == ServiceControllerStatus.Stopped)
				sc.Start();
		}

		private void stopToolStripMenuItem_Click(object sender, EventArgs e)
		{
			if (sc.Status == ServiceControllerStatus.Running)
				sc.Stop();
		}

		private void pauseToolStripMenuItem_Click(object sender, EventArgs e)
		{
				if (sc.Status == ServiceControllerStatus.Running)
					sc.Pause();
		}

		private void configureMonitorPathToolStripMenuItem_Click(object sender, EventArgs e)
		{
			if (folderBrowserDialog1.ShowDialog() == DialogResult.OK)
			{
				string path = folderBrowserDialog1.SelectedPath;
				label1.Text = path;
				path.Replace("\\", "\\\\");
				File.WriteAllText(configPath, path);
				sc.ExecuteCommand(configMonitorPathCMD);
			}

		}

		private void configureCollectionPathToolStripMenuItem_Click(object sender, EventArgs e)
		{
			if (folderBrowserDialog1.ShowDialog() == DialogResult.OK)
			{
				string path = folderBrowserDialog1.SelectedPath;
				label1.Text = path;
				path.Replace("\\", "\\\\");
				File.WriteAllText(configPath, path);
				sc.ExecuteCommand(configCollectionPathCMD);
			}
		}

		private void resumeToolStripMenuItem_Click(object sender, EventArgs e)
		{
			if (sc.Status == ServiceControllerStatus.Paused)
				sc.Continue();
		}

	}
}
