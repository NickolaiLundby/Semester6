using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Unity;

namespace Lab61
{
    public class DriverConcrete
    {
        private CarConcrete _car;
        public DriverConcrete(CarConcrete car)
        {
            _car = car;
        }

        public void RunCar()
        {
            Console.WriteLine(_car.Run());
        }
    }
}
