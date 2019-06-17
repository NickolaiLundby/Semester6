using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Linq;
using System.ServiceProcess;
using System.Text;
using System.Threading.Tasks;
using System.IO;

namespace Lab12_Service
{
	public partial class KPUfileWatcherService : ServiceBase
	{
		private const string configPath = "C:\\Windows\\Temp\\KPU1\\kpuwserviceconfig";
		private const string serviceName = "KPUfileWatcherService";
		private const string logName = "Application";
		private const int configMonitorPathCMD = 221;
		private const int configCollectionPathCMD = 222;
		private string MonitorPath { get; set; } = "C:\\Windows\\Temp\\KPU1\\";
		private string CollectionPath { get; set; } = "C:\\Users\\runek\\Documents\\KPU1 Service Lab main file";
		public KPUfileWatcherService()
		{
			InitializeComponent();
		}

		protected override void OnStart(string[] args)
		{
			if (!EventLog.SourceExists(serviceName))
				EventLog.CreateEventSource(serviceName,logName);
			fileSystemWatcher = new FileSystemWatcher();
			fileSystemWatcher.Path = MonitorPath;
			fileSystemWatcher.EnableRaisingEvents = true;
		}

		protected override void OnStop()
		{
			fileSystemWatcher.EnableRaisingEvents = false;
			fileSystemWatcher.Dispose();
		}

		private void LogEvent(string message, EventLogEntryType entryType)
		{
			EventLog eventLog = new EventLog();
			eventLog.Source = serviceName;
			eventLog.Log = logName;
			eventLog.WriteEntry(message, entryType);
		}

		private void fileSystemWatcher_Created(object sender, System.IO.FileSystemEventArgs e)
		{
			string text ="";
			try
			{
				text = File.ReadAllText(e.FullPath);
			}
			catch (Exception ex)
			{
				LogEvent(ex.Message, EventLogEntryType.Error);
			}
			try
			{
				StreamWriter sw = File.AppendText(CollectionPath);
				sw.Write(text);
				sw.Close();
				LogEvent("Read content of " + e.Name, EventLogEntryType.Information);
			}
			catch (Exception ex)
			{
				LogEvent(ex.Message, EventLogEntryType.Error);
			}
		}
		
		protected override void OnPause()
		{
			fileSystemWatcher.EnableRaisingEvents = false;
		}

		protected override void OnContinue()
		{
			fileSystemWatcher.EnableRaisingEvents = true;
		}

		protected override void OnCustomCommand(int command)
		{
			switch (command)
			{
				case configMonitorPathCMD:
					try
					{
						string path = File.ReadAllText(configPath);
						MonitorPath = path;
						File.WriteAllText(configPath, "");
						fileSystemWatcher.EnableRaisingEvents = false;
						fileSystemWatcher.Path = MonitorPath;
						fileSystemWatcher.EnableRaisingEvents = true;
						LogEvent("Configured monitor path", EventLogEntryType.Information);
					}
					catch(Exception ex)
					{
						LogEvent("Failed to configue monitor path: " + ex.Message, EventLogEntryType.Error);
					}					
					break;
				case configCollectionPathCMD:
					try
					{
						string path = File.ReadAllText(configPath);
						CollectionPath = path;
						File.WriteAllText(configPath, "");
						LogEvent("Configured collection path", EventLogEntryType.Information);
					}
					catch (Exception ex)
					{
						LogEvent("Failed to configue monitor path: " + ex.Message, EventLogEntryType.Error);
					}
					break;
				default:
					LogEvent("Unknow command received", EventLogEntryType.Warning);
					break;
			}

		}
	}
}