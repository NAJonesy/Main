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
using System.Windows.Navigation;
using System.Windows.Shapes;
using Newtonsoft.Json.Linq;
using System.Net;

namespace ChuckNorrisAPI
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
            warning();
        }
        public bool warningShown = false;

        private void button_find_Click(object sender, RoutedEventArgs e)
        {
            string category = "";       
            if (rad_animal.IsChecked == true)
            {
                category = "animal";
            }else if(rad_career.IsChecked == true)
            {
                category = "career";
            }else if(rad_celeb.IsChecked == true)
            {
                category = "celebrity";
            }else if(rad_dev.IsChecked == true)
            {
                category = "dev";
            }
            else if (rad_explicit.IsChecked == true)
            {
                category = "explicit";
            }
            else if (rad_fashion.IsChecked == true)
            {
                category = "fashion";
            }
            else if (rad_food.IsChecked == true)
            {
                category = "food";
            }
            else if (rad_history.IsChecked == true)
            {
                category = "history";
            }
            else if (rad_money.IsChecked == true)
            {
                category = "money";
            }
            else if (rad_movie.IsChecked == true)
            {
                category = "movie";
            }
            else if (rad_music.IsChecked == true)
            {
                category = "music";
            }
            else if (rad_political.IsChecked == true)
            {
                category = "political";
            }
            else if (rad_religion.IsChecked == true)
            {
                category = "religion";
            }
            else if (rad_science.IsChecked == true)
            {
                category = "science";
            }
            else if (rad_sport.IsChecked == true)
            {
                category = "sport";
            }
            else if (rad_travel.IsChecked == true)
            {
                category = "travel";
            }
            else if (rad_random.IsChecked == true)
            {
                category = "random";
            }else
            {
                category = "how did this happen?!";
            }

            JsonHandler jh = new JsonHandler();
            Joke joke = new Joke();
            joke = jh.makeJoke(category);
            displayJoke(joke);
        }

        private void displayJoke(Joke joke)//this will display the information contained in joke onto the window
        {
            label_id.Visibility = Visibility.Visible;
            label_url.Visibility = Visibility.Visible;
            textBox_ID.Visibility = Visibility.Visible;
            textBox_url.Visibility = Visibility.Visible;
            textBox_ID.Text = joke.id;
            textBox_url.Text = joke.url;
            Uri uri = new Uri(joke.icon_url);
            BitmapImage imgSource = new BitmapImage(uri);
            image.Source = imgSource;
            textBlock.Text = joke.value.ToString();
        }

        private void button_search1_Click(object sender, RoutedEventArgs e)//this opens the search window
        {
            Search s = new Search();
            s.Closed += search_close;
            s.Show();
            this.Hide();
        }

        private void search_close(object sender, EventArgs e)//this will run when the search window is closed
        {
            this.Show();
            //this.Activate();
        }

        private void warning()
        {
            if (!warningShown)
            {
                MessageBox.Show("\tWarning!\n\nSome jokes may contain explicit content.\nThese jokes come directly from https://api.chucknorris.io/.");
                warningShown = true;
            }
                                 
        }
    }
}
