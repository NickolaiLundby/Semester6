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

namespace Lab12_NotifyIcon
{
	public partial class Form1 : Form
	{
		bool paused = false;
		private ServiceController sc;

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
			sc.Stop();
		}

		private void pauseToolStripMenuItem_Click(object sender, EventArgs e)
		{
			if (!paused)
			{
				sc.Pause();
				pauseToolStripMenuItem.Text = "Resume";
				paused = true;
			}
			else
			{
				sc.Continue();
				pauseToolStripMenuItem.Text = "Pause";
				paused = false;
			}
			

		}

		private void configureMonitorPathToolStripMenuItem_Click(object sender, EventArgs e)
		{

		}

		private void configureCollectionPathToolStripMenuItem_Click(object sender, EventArgs e)
		{

		}
	}
}
