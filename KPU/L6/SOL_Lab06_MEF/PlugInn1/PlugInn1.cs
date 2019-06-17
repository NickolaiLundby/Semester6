using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Contracts;
using System.ComponentModel.Composition;

namespace PlugInn1
{
    [Export (typeof(IDLLcontract))]
    public class PlugInn1 : IDLLcontract
    {
        IAppUtil appUtil;

        public void Init(IAppUtil util)
        {
            appUtil = util;
        }

        public bool Run()
        {
            Console.WriteLine(appUtil.MyAddString("Hello ", "World"));
            Console.WriteLine("Your name is: " + appUtil.GetName());
            return true;
        }

        public void TearDown()
        {
            appUtil = null;
        }
    }
}
