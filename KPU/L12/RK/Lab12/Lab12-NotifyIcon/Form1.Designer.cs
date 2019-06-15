namespace Lab12_NotifyIcon
{
	partial class Form1
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

		#region Windows Form Designer generated code

		/// <summary>
		/// Required method for Designer support - do not modify
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
			this.components = new System.ComponentModel.Container();
			System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Form1));
			this.notifyIcon1 = new System.Windows.Forms.NotifyIcon(this.components);
			this.contextMenuStrip1 = new System.Windows.Forms.ContextMenuStrip(this.components);
			this.Start = new System.Windows.Forms.ToolStripMenuItem();
			this.stopToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
			this.pauseToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
			this.configurePathsToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
			this.configureMonitorPathToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
			this.configureCollectionPathToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
			this.contextMenuStrip1.SuspendLayout();
			this.SuspendLayout();
			// 
			// notifyIcon1
			// 
			this.notifyIcon1.ContextMenuStrip = this.contextMenuStrip1;
			this.notifyIcon1.Icon = ((System.Drawing.Icon)(resources.GetObject("notifyIcon1.Icon")));
			this.notifyIcon1.Text = "notifyIcon1";
			this.notifyIcon1.Visible = true;
			// 
			// contextMenuStrip1
			// 
			this.contextMenuStrip1.ImageScalingSize = new System.Drawing.Size(20, 20);
			this.contextMenuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.Start,
            this.stopToolStripMenuItem,
            this.pauseToolStripMenuItem,
            this.configurePathsToolStripMenuItem});
			this.contextMenuStrip1.Name = "contextMenuStrip1";
			this.contextMenuStrip1.Size = new System.Drawing.Size(182, 100);
			// 
			// Start
			// 
			this.Start.Name = "Start";
			this.Start.Size = new System.Drawing.Size(181, 24);
			this.Start.Text = "Start";
			this.Start.Click += new System.EventHandler(this.Start_Click);
			// 
			// stopToolStripMenuItem
			// 
			this.stopToolStripMenuItem.Name = "stopToolStripMenuItem";
			this.stopToolStripMenuItem.Size = new System.Drawing.Size(181, 24);
			this.stopToolStripMenuItem.Text = "Stop";
			this.stopToolStripMenuItem.Click += new System.EventHandler(this.stopToolStripMenuItem_Click);
			// 
			// pauseToolStripMenuItem
			// 
			this.pauseToolStripMenuItem.Name = "pauseToolStripMenuItem";
			this.pauseToolStripMenuItem.Size = new System.Drawing.Size(181, 24);
			this.pauseToolStripMenuItem.Text = "Pause";
			this.pauseToolStripMenuItem.Click += new System.EventHandler(this.pauseToolStripMenuItem_Click);
			// 
			// configurePathsToolStripMenuItem
			// 
			this.configurePathsToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.configureMonitorPathToolStripMenuItem,
            this.configureCollectionPathToolStripMenuItem});
			this.configurePathsToolStripMenuItem.Name = "configurePathsToolStripMenuItem";
			this.configurePathsToolStripMenuItem.Size = new System.Drawing.Size(181, 24);
			this.configurePathsToolStripMenuItem.Text = "Configure Paths";
			// 
			// configureMonitorPathToolStripMenuItem
			// 
			this.configureMonitorPathToolStripMenuItem.Name = "configureMonitorPathToolStripMenuItem";
			this.configureMonitorPathToolStripMenuItem.Size = new System.Drawing.Size(252, 26);
			this.configureMonitorPathToolStripMenuItem.Text = "Configure Monitor Path";
			this.configureMonitorPathToolStripMenuItem.Click += new System.EventHandler(this.configureMonitorPathToolStripMenuItem_Click);
			// 
			// configureCollectionPathToolStripMenuItem
			// 
			this.configureCollectionPathToolStripMenuItem.Name = "configureCollectionPathToolStripMenuItem";
			this.configureCollectionPathToolStripMenuItem.Size = new System.Drawing.Size(252, 26);
			this.configureCollectionPathToolStripMenuItem.Text = "Configure Collection Path";
			this.configureCollectionPathToolStripMenuItem.Click += new System.EventHandler(this.configureCollectionPathToolStripMenuItem_Click);
			// 
			// Form1
			// 
			this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
			this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
			this.ClientSize = new System.Drawing.Size(285, 144);
			this.Name = "Form1";
			this.Text = "Form1";
			this.contextMenuStrip1.ResumeLayout(false);
			this.ResumeLayout(false);

		}

		#endregion

		private System.Windows.Forms.NotifyIcon notifyIcon1;
		private System.Windows.Forms.ContextMenuStrip contextMenuStrip1;
		private System.Windows.Forms.ToolStripMenuItem Start;
		private System.Windows.Forms.ToolStripMenuItem stopToolStripMenuItem;
		private System.Windows.Forms.ToolStripMenuItem pauseToolStripMenuItem;
		private System.Windows.Forms.ToolStripMenuItem configurePathsToolStripMenuItem;
		private System.Windows.Forms.ToolStripMenuItem configureMonitorPathToolStripMenuItem;
		private System.Windows.Forms.ToolStripMenuItem configureCollectionPathToolStripMenuItem;
	}
}

