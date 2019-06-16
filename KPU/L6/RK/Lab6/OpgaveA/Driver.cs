using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Unity;

namespace OpgaveA
{
	// One parameter constructor with dependency injection - concrete class as dependency(BMW)
	class DriverBMW
	{
		private BMW _car = null;

		public DriverBMW(BMW bmw)
		{
			_car = bmw;
		}

		public void RunCar()
		{
			Console.WriteLine("Running {0} - {1} mile ", _car.GetType().Name, _car.Run());
		}
	}

	// One parameter constructor with dependency injection - also used in parameteroveride
	class Driver
	{
		private ICar _car = null;

		public Driver(ICar car)
		{
			_car = car;
		}

		public void RunCar()
		{
			Console.WriteLine("Running {0} - {1} mile ", _car.GetType().Name, _car.Run());
		}
	}
	

	/* Two parameter constructor with dependency injection
	public class Driver
	{
		private ICar _car = null;
		private ICarKey _key = null;

		public Driver(ICar car, ICarKey key)
		{
			_car = car;
			_key = key;

		}

		public void RunCar()
		{
			Console.WriteLine("Running {0} with {1} - {2} mile ", _car.GetType().Name, _key.GetType().Name, _car.Run());
		}
	}
	*/

	/* Two constructors
	public class Driver
	{
		private ICar _car = null;

		public Driver(ICar car)
		{
			_car = car;
		}

		public Driver(string name)
		{
		}

		public void RunCar()
		{
			Console.WriteLine("Running {0} - {1} mile ", _car.GetType().Name, _car.Run());
		}
	}
	*/

	/*With primitive parameter
	public class Driver
	{
		private ICar _car = null;
		private string _name = string.Empty;

		public Driver(ICar car, string driverName)
		{
			_car = car;
			_name = driverName;
		}

		public void RunCar()
		{
			Console.WriteLine("{0} is running {1} - {2} mile ",
							_name, _car.GetType().Name, _car.Run());
		}
	}
	*/

	/* Property injection
	public class Driver
	{
		public Driver()
		{
		}

		[Dependency("LuxuryCar")]
		public ICar Car { get; set; }

		public void RunCar()
		{
			Console.WriteLine("Running {0} - {1} mile ",
								this.Car.GetType().Name, this.Car.Run());
		}
	}
	*/

	/*Method injection
	public class Driver
	{
		private ICar _car = null;

		public Driver()
		{
		}

		//[InjectionMethod]
		public void UseCar(ICar car)
		{
			_car = car;
		}

		public void RunCar()
		{
			Console.WriteLine("Running {0} - {1} mile ", _car.GetType().Name, _car.Run());
		}
	}
	*/
}
