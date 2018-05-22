using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;
using Newtonsoft.Json.Linq;
using System.Net;

namespace ChuckNorrisAPI
{
    /// <summary>
    /// Interaction logic for Search.xaml
    /// </summary>
    public partial class Search : Window
    {
        public Search()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, RoutedEventArgs e)//search button, queries the text user wants to search
        {
            if (textBox_search.Text != "")
            {
                JsonHandler jh = new JsonHandler();
                Results result = new Results();
                result = jh.searchJoke(textBox_search.Text);
                displayJokes(result);
            }else
            {
                MessageBox.Show("You must enter something to search for.");
            }
            
        }

        private void button_return_Click(object sender, RoutedEventArgs e)//return button
        {
            //MainWindow mw = new MainWindow();
            //mw.Show();
            this.Close();
        }

        private void displayJokes(Results result)//this will display all of the jokes into the listbox
        {
            if(result.jokes.Count.ToString() == "0")
            {
                MessageBox.Show("No results found. Try searching for something else.");
            }
            else
            {
                label_amount.Content = result.jokes.Count.ToString();
                listBox_results.Items.Clear();
                foreach (var item in result.jokes)
                {
                    ListBoxItem tb = new ListBoxItem();
                    tb.Width = 480;
                    tb.Content = new TextBlock { Text = item.value.ToString() + "\n", TextWrapping = TextWrapping.Wrap };
                    listBox_results.Items.Add(tb);
                }
            }
        }
    }
}
