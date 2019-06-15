namespace Lab12_Service
{
	partial class KPUfileWatcherService
	{
		/// <summary> 
		/// Required designer variable.
		/// </summary>
		private System.ComponentModel.IContainer components = null;

		/// <summary>
		/// Clean up any resources being used.
		/// </summary>
		/// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
		protected override void Dispose(bool disposing)
		{
			if (disposing && (components != null))
			{
				components.Dispose();
			}
			base.Dispose(disposing);
		}

		#region Component Designer generated code

		/// <summary> 
		/// Required method for Designer support - do not modify 
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
			this.fileSystemWatcher = new System.IO.FileSystemWatcher();
			((System.ComponentModel.ISupportInitialize)(this.fileSystemWatcher)).BeginInit();
			// 
			// fileSystemWatcher
			// 
			this.fileSystemWatcher.EnableRaisingEvents = false;
			this.fileSystemWatcher.Created += new System.IO.FileSystemEventHandler(this.fileSystemWatcher_Created);
			// 
			// KPUfileWatcherService
			// 
			this.CanPauseAndContinue = true;
			this.ServiceName = "KPUfileWatcherService";
			((System.ComponentModel.ISupportInitialize)(this.fileSystemWatcher)).EndInit();

		}

		#endregion

		private System.IO.FileSystemWatcher fileSystemWatcher;
	}
}
