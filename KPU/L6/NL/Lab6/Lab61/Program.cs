using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Unity;
using Unity.Injection;
using Unity.Resolution;

namespace Lab61
{
    public class CarConsts
    {
        public const string LUXURY_CAR = "Luxury.Car";
        public const string LUXURY_CAR_DRIVER = "Luxury.Car.Driver";
    }
    class Program
    {
        static void Main(string[] args)
        {
            var container = new UnityContainer()
                .RegisterType<ICar, BMW>();

            // Injects BMW as it's top of the queue
            var drv1 = container.Resolve<Driver>();
            drv1.RunCar();

            // Injects FORD as this parameteroverride, overrides the paramter "car" of the driver constructor with the argument new Ford()
            var drv2 = container.Resolve<Driver>(new ParameterOverride("car", new Ford()));
            drv2.RunCar();

            Console.ReadKey();
        }
    }
}
