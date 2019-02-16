using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using Lab43;

namespace Lab44
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        private CprCheckShared _cprCheck = new CprCheckShared();
        public MainWindow()
        {
            InitializeComponent();
        }

        private void btnCheckCpr_Click(object sender, RoutedEventArgs e)
        {
            CprError cprError;
            

            _cprCheck.Check(tbxCprNr.Text, out cprError);

            switch (cprError)
            {
                case CprError.NoError:
                    tblErrorMsg.Text = "CPR Valid";
                    break;
                case CprError.FormatError:
                    tblErrorMsg.Text = "Invalid format!";
                    break;
                case CprError.DateError:
                    tblErrorMsg.Text = "Invalid date!";
                    break;
                case CprError.Check11Error:
                    tblErrorMsg.Text = "Invalid CPR!";
                    break;
                default:
                    tblErrorMsg.Text = "Unknown error!";
                    break;
            }
        }

        private void Window_Loaded(object sender, RoutedEventArgs e)
        {
            tbxCprNr.Focus();
        }

        private void tbxCprNr_TextChanged(object sender, TextChangedEventArgs e)
        {
            tblErrorMsg.Text = " ";
        }

        private void btnGetInfo_Click(object sender, RoutedEventArgs e)
        {
            Type assType = _cprCheck.GetType();
            tbxAssName.Text = assType.Assembly.FullName;
            AssemblyName assName = assType.Assembly.GetName();
            tbxVersion.Text = assName.Version.ToString();
            tbxLocation.Text = assType.Assembly.Location;
            tbxLoadedFrom.Text = assType.Assembly.GlobalAssemblyCache.ToString();
        }
    }
}
