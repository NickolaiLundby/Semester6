using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Unity;
using Unity.Injection;
using Unity.Resolution;

namespace OpgaveA
{
	class Program
	{
		static void Main(string[] args)
		{
			/*Without unity
			Driver driver = new Driver(new BMW());
			driver.RunCar();
			*/

			//Constructor injection
			/* one parameter part 
			// With unity
			IUnityContainer container = new UnityContainer();
			container.RegisterType<ICar, BMW>();
			container.RegisterType<ICar, Audi>("LuxuryCar");

			ICar bmw = container.Resolve<ICar>();  // return BMW object
			ICar audi = container.Resolve<ICar>("LuxuryCar"); // return Audi object

			container.RegisterType<Driver>("LuxuryCarDriver", new InjectionConstructor(container.Resolve<ICar>("LuxuryCar")));

			Driver driver1 = container.Resolve<Driver>(); // injects BMW
			driver1.RunCar();

			Driver driver2 = container.Resolve<Driver>("LuxuryCarDriver"); // injects Audi
			driver2.RunCar();

			Console.WriteLine("\nInstance injection\n");
			// Register instance
			var containerIns = new UnityContainer();
			ICar audiIns = new Audi();
			containerIns.RegisterInstance<ICar>(audiIns);

			Driver driver1Ins = containerIns.Resolve<Driver>();
			driver1Ins.RunCar();
			driver1Ins.RunCar();

			Driver driver2Ins = containerIns.Resolve<Driver>();
			driver2Ins.RunCar();
			*/

			/* with two parameters part
			var container = new UnityContainer();
			
			container.RegisterType<ICar, Audi>();
			container.RegisterType<ICarKey, AudiKey>();

			var driver = container.Resolve<Driver>();
			driver.RunCar();
			*/

			/*with multiple constructors*/
			//var container = new UnityContainer();

			/*multiple constructors
			container.RegisterType<ICar, Ford>();
			container.RegisterType<Driver>(new InjectionConstructor(container.Resolve<ICar>()));
			*/

			/* primitive parameter
			container.RegisterType<Driver>(new InjectionConstructor(new object[] { new Audi(), "Steve" }));

			var driver = container.Resolve<Driver>();
			driver.RunCar(); */

			/*Property injection
			var container = new UnityContainer();
			container.RegisterType<ICar, BMW>();
			container.RegisterType<ICar, Audi>("LuxuryCar");
			//run time
			container.RegisterType<Driver>(new InjectionProperty("Car", new BMW()));


			var driver = container.Resolve<Driver>();
			driver.RunCar();
			*/

			/*method injection
			var container = new UnityContainer();
			container.RegisterType<ICar, BMW>();

			var driver = container.Resolve<Driver>();
			driver.RunCar();
			

			//method injection run time
			//run-time configuration
			var container = new UnityContainer();
			container.RegisterType<Driver>(new InjectionMethod("UseCar", new Audi()));

			//to specify multiple parameters values
			container.RegisterType<Driver>(new InjectionMethod("UseCar", new object[] { new Audi() }));

			var driver = container.Resolve<Driver>();
			driver.RunCar();
			*/

			/* Parameter overide

			var container = new UnityContainer().RegisterType<ICar, BMW>();

			var driver1 = container.Resolve<Driver>(); // Injects registered ICar type
			driver1.RunCar();

			// Override registered ICar type 
			var driver2 = container.Resolve<Driver>(new ParameterOverride("car", new Ford()));
			driver2.RunCar();
			

			var driver2 = container.Resolve<Driver>(new ResolverOverride[] {
				new ParameterOverride("car1", new Ford()),
				new ParameterOverride("car2", new BMW()),
				new ParameterOverride("car3", new Audi())
			});
			driver2.RunCar();
			*/

			/*Property overide
			var container = new UnityContainer();

			//Configure default value of Car property
			container.RegisterType<Driver>(new InjectionProperty("Car", new BMW()));

			var driver1 = container.Resolve<Driver>();
			driver1.RunCar();

			//Override default value of Car property
			var driver2 = container.Resolve<Driver>(new PropertyOverride("Car", new Audi()));

			driver2.RunCar();
			*/

			Console.ReadKey();
		}
	}
}
