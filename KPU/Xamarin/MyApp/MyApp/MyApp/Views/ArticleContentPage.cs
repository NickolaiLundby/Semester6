using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using MyApp.Models;

using Xamarin.Forms;

namespace MyApp.Views
{
    public class ArticleContentPage : ContentPage
    {
        private WebView _webView;
        public ArticleContentPage()
        {
            Content = _webView = new WebView();
        }

        public Article Article { get; set; }

        protected override void OnAppearing()
        {
            base.OnAppearing();
            Title = Article.Title;
            _webView.Source = new UrlWebViewSource
            {
                Url = Article.ArticleUrl
            };
        }
    }
}