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

namespace KPU_Service_Control
{
    public partial class Form1 : Form
    {
        private const int configMonitorPath = 221;
        private const int configCollectionPath = 222;
        private string configPath = "C:\\Windows\\Temp\\KPU1\\config.txt";
        private ServiceController _serviceController;
        public Form1(ServiceController sc)
        {
            _serviceController = sc;
            InitializeComponent();
        }

        private void notifyIcon1_MouseDoubleClick(object sender, MouseEventArgs e)
        {

        }

        private void contextMenuStrip1_Opening(object sender, CancelEventArgs e)
        {

        }

        private void startToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if(_serviceController.Status != ServiceControllerStatus.Running)
                _serviceController.Start();
        }

        private void stopToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if(_serviceController.Status == ServiceControllerStatus.Running)
                _serviceController.Stop();
        }

        private void pauseToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if(_serviceController.Status == ServiceControllerStatus.Running)
                _serviceController.Pause();
        }

        private void configurePathToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if(folderBrowserDialog1.ShowDialog() == DialogResult.OK)
            {
                string path = folderBrowserDialog1.SelectedPath;
                using (StreamWriter sw = new StreamWriter(configPath))
                {
                    path.Replace("\\", "\\\\");
                    sw.WriteLine(path);
                }
                _serviceController.ExecuteCommand(configMonitorPath);
            }
        }
    }
}
