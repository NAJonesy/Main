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
    class JsonHandler
    {

        public Joke makeJoke(string category)//gets a joke from the website and returns it
        {
            WebClient wc = new WebClient();
            Joke joke = new Joke();
            string uri = string.Format(@"https://api.chucknorris.io/jokes/random?category={0}", category);
            string uri1 = @"https://api.chucknorris.io/jokes/random";
            if (category == "random")
            {
                string data = wc.DownloadString(uri1);
                return ConvertToObject(data);
            }
            else
            {
                string data = wc.DownloadString(uri);
                return ConvertToObject(data);
            }
        }

        private Joke ConvertToObject(string data)//converts the given json object to a Joke object
        {
            JObject rawjoke = JObject.Parse(data);
            JToken token = rawjoke;
            Joke newJoke = token.ToObject<Joke>();
            return newJoke;
        }

        public Results searchJoke(string query)//searches the website for jokes containing the query
        {
            Results result = new Results();

            string uri = string.Format(@"https://api.chucknorris.io/jokes/search?query={0}", query);

            WebClient wc = new WebClient();
            string data = wc.DownloadString(uri);

            JObject rawjoke = JObject.Parse(data);
            List<JToken> tokens = rawjoke["result"].Children().ToList();
            foreach (var item in tokens)
            {
                Joke newjoke = item.ToObject<Joke>();
                result.jokes.Add(newjoke);
            }

            return result;
        }

    }
}
