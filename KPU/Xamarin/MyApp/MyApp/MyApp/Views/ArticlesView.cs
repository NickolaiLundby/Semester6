using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using MyApp.Models;

using Xamarin.Forms;

namespace MyApp.Views
{
	public class ArticlesView : ContentPage
	{
		public ArticlesView()
		{
			Title = "Sitepoint: Mobile";
            var listView = new ListView();

            var textCell = new DataTemplate(typeof(TextCell));
            textCell.SetBinding(TextCell.TextProperty, "Title");
            textCell.SetBinding(TextCell.DetailProperty, "Author");
            listView.ItemTemplate = textCell;
            listView.ItemsSource = MyApp.Services.DataService.GetArticles();
            listView.ItemSelected += ListView_ItemSelected;

            Content = listView;
        }

        private void ListView_ItemSelected(object sender, SelectedItemChangedEventArgs e)
        {
            // Check that there is a selected item
            if (e.SelectedItem == null) return;

            // Set the item as the article to display in the next page
            var page = new ArticleContentPage { Article = e.SelectedItem as Article };
            Navigation.PushAsync(page, true);

            // Clear the selected item in the list
            var listView = sender as ListView;
            listView.SelectedItem = null;
        }
	}
}