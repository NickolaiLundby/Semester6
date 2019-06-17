using Contracts;
using System;
using System.Collections.Generic;
using System.ComponentModel.Composition;
using System.ComponentModel.Composition.Hosting;
using System.ComponentModel.Composition.Primitives;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;

namespace ExtensibleApp
{
    class Program
    {
        [ImportMany(typeof(IDLLcontract))]
        public IEnumerable<IDLLcontract> PlugInns { get; set; }

        AppUtilImp Util { get; set; }

        static void Main(string[] args)
        {
            Program app = new Program();
            app.Compose();
            app.Util = new AppUtilImp();
            app.Run();
            Console.ReadLine();
        }

        private void Run()
        {
            Console.Write("Enter your name: ");
            Util.Name = Console.ReadLine();

            // Init
            foreach (var plugInn in PlugInns)
            {
                plugInn.Init(Util);
            }

            // Run
            foreach (var plugInn in PlugInns)
            {
                plugInn.Run();
            }

            // TearDown
            foreach (var plugInn in PlugInns)
            {
                plugInn.TearDown();
            }
        }

        private void Compose()
        {
            var catalog = new DirectoryCatalog(@".\Extensions");
            // Make an instance of a CompositionContainer and give it a reference to the AggregateCatalog
            var container = new CompositionContainer(catalog, CompositionOptions.DisableSilentRejection);
            // Finally call composeparts to connect the Import with Export.
            container.ComposeParts(this);
        }
    }
}
