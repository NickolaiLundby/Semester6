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
            var container = new UnityContainer();

            // Injects concrete car, auto mapping is fine for this.
            var drv1 = container.Resolve<DriverConcrete>();
            drv1.RunCar();

            // Attempts to inject car, but we havent registered types.
            try
            {
                var drv2 = container.Resolve<Driver>();
                drv2.RunCar();
            }
            catch (Exception e)
            {
                Console.WriteLine("DI did not work for driver 2, because:");
                Console.WriteLine("You need to register types!");
            }

            // Inject the car, but register the concrete class implementing the interface
            container.RegisterType<ICar, Car>();

            // Injection now works, because the interface is mapped to the implementation
            var drv3 = container.Resolve<Driver>();
            drv3.RunCar();

            Console.WriteLine();
            Console.WriteLine("Transient Lifecycle:");

            // Lifecycle, default is transient, a new car instance is injected to each new driver:
            var drv4 = container.Resolve<Driver>();
            drv4.RunCar();
            drv3.RunCar();
            drv4.RunCar();
            drv3.RunCar();
            drv4.RunCar();
            drv3.RunCar();
            drv4.RunCar();

            Console.WriteLine();
            Console.WriteLine("Singleton Lifecycle:");

            // If we want to use singleton:
            container.RegisterInstance<ICar>(new Car());
            var drv5 = container.Resolve<Driver>();
            var drv6 = container.Resolve<Driver>();
            drv5.RunCar();
            drv6.RunCar();
            drv5.RunCar();
            drv6.RunCar();
            drv5.RunCar();
            drv6.RunCar();

            Console.ReadKey();
        }
    }
}
