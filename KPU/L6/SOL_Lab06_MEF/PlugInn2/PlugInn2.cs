using Contracts;
using System;
using System.Collections.Generic;
using System.ComponentModel.Composition;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PlugInn2
{
    [Export(typeof(IDLLcontract))]
    public class PlugInn2 : IDLLcontract
    {
        IAppUtil appUtil;

        public void Init(IAppUtil util)
        {
            appUtil = util;
        }

        public bool Run()
        {
            Console.WriteLine(appUtil.MyAddString("This is ", "PlugInn2 calling!"));
            Console.WriteLine("Wake up " + appUtil.GetName() + "!");
            return true;
        }

        public void TearDown()
        {
            appUtil = null;
        }
    }
}
