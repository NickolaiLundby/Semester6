using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;

namespace Lab12_Console
{
	class KPUFileWatcherService
	{
		private string MonitorPath { get; set; } = "C:\\Windows\\Temp\\KPU1\\";
		private string CollectionPath { get; set; } = "C:\\Users\\runek\\Documents\\KPU1 Service Lab main file";

		public void OnStart(string[] args)
		{
			FileSystemWatcher fileSystemWatcher = new FileSystemWatcher();
			fileSystemWatcher.Path = MonitorPath;
			fileSystemWatcher.Created += fileSystemWatcher_Created;
			fileSystemWatcher.EnableRaisingEvents = true;
		}

		private void fileSystemWatcher_Created(object sender, System.IO.FileSystemEventArgs e)
		{
			string text = File.ReadAllText(e.FullPath);
			using (StreamWriter sw = File.AppendText(CollectionPath))
			{
				sw.Write(text);
			}

		}
	}
}
