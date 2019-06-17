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

namespace KPU_Service
{
    public partial class KPUService : ServiceBase
    {
        private const string configPath = "C:\\Windows\\Temp\\KPU1\\config.txt";
        private const string serviceName = "KPUService";
        private const string logName = "Application";
        private const int configMonitorPath = 221;
        private const int configCollectionPath = 222;
        private string MonitorPath { get; set; } = "C:\\Windows\\Temp\\KPU1\\";
        private string CollectionPath { get; set; } = "C:\\Users\\Nickolai\\Documents\\KPU1 Service Lab main file";
        public KPUService()
        {
            InitializeComponent();
        }

        protected override void OnStart(string[] args)
        {
            if (!EventLog.SourceExists(serviceName))
                EventLog.CreateEventSource(serviceName, logName);
            fileSystemWatcher1.Path = MonitorPath;
            fileSystemWatcher1.EnableRaisingEvents = true;
        }

        protected override void OnStop()
        {
            fileSystemWatcher1.EnableRaisingEvents = false;
        }

        protected override void OnContinue()
        {
            fileSystemWatcher1.EnableRaisingEvents = true;
        }

        protected override void OnCustomCommand(int command)
        {
            switch (command)
            {
                case configMonitorPath:
                    try
                    {
                        string path = File.ReadAllText(configPath);
                        fileSystemWatcher1.EnableRaisingEvents = false;
                        MonitorPath = path;
                        fileSystemWatcher1.Path = MonitorPath;
                        fileSystemWatcher1.EnableRaisingEvents = true;
                        File.WriteAllText(configPath, "");
                        LogEvent($"Configured monitor path to {path}", EventLogEntryType.Information);
                    }
                    catch (Exception ex)
                    {
                        LogEvent("Failed to configure monitor path: " + ex.Message, EventLogEntryType.Error);
                    }
                    break;
                case configCollectionPath:
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
                    LogEvent("Unknown command received", EventLogEntryType.Warning);
                    break;
            }
        }

        private void LogEvent(string message, EventLogEntryType entryType)
        {
            EventLog eventLog = new EventLog();
            eventLog.Source = serviceName;
            eventLog.Log = logName;
            eventLog.WriteEntry(message, entryType);
        }

        private void fileSystemWatcher1_Created(object sender, System.IO.FileSystemEventArgs e)
        {
            string text = "";
            try {
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
    }
}
