using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Forms;

namespace MyApp
{
	// Learn more about making custom code visible in the Xamarin.Forms previewer
	// by visiting https://aka.ms/xamarinforms-previewer
	[DesignTimeVisible(false)]
	public partial class MainPage : ContentPage
	{
		private int _count = 0;
		public MainPage()
		{
			InitializeComponent();
		}

		public void Button_Clicked(object sender, EventArgs e)
		{
			_count++;
			((Button)sender).Text = $"You clicked {_count} times.";
		}
	}
}
