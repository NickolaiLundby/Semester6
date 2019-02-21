using System;

namespace CprDLL
{
	public enum CprError
	{
		NoError,
		FormatError,
		DateError,
		Check11Error
	};

	public class CprCheck
	{
		public bool Check(string cprTxt, out CprError error)
		{

			return true;

		}

		private CprError CheckFormat(string cprTxt)
		{
			CprError cprError = CprError.NoError;

			bool isNumeric = int.TryParse(cprTxt, out int n);
			if (isNumeric == false)
				cprError = CprError.FormatError;
			if (cprTxt.Length != 10)
				cprError = CprError.FormatError;

			return cprError;
		}

		private CprError CheckDate(string cprTxt)
		{
			CprError cprError = CprError.NoError;

			DateTime dateValue;
			if (DateTime.TryParse(cprTxt.Substring(0,1)+"/"+ cprTxt.Substring(2, 3)+"/"+ cprTxt.Substring(4, 5), out dateValue) == false)
				cprError = CprError.DateError;

			/*
			try
			{
				int day = int.Parse(cprTxt.Substring(0, 1));
				int month = int.Parse(cprTxt.Substring(2, 3));
				int year = int.Parse(cprTxt.Substring(4, 5));

				DateTime date = 
			}
			*/

			return cprError;
		}

		private CprError Check11(string cprTxt)
		{
			CprError cprError = CprError.NoError;

			int sum = 0;
			for (int i = 0; i < 3; i++)
				sum += int.Parse(cprTxt.Substring(i, 1)) * (4 - i);
			for (int i = 3; i < 10; i++)
				sum += int.Parse(cprTxt.Substring(i, 1)) * (10 - i);
			if (sum % 11 != 0)
				cprError = CprError.Check11Error;

			return cprError;
		}
	}
}
