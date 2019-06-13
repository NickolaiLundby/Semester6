using System;
using System.Text.RegularExpressions;

namespace Lab43
{
	public enum CprError
	{
		NoError,
		FormatError,
		DateError,
		Check11Error
	};

	public class CprCheckShared
	{
		public bool Check(string cprTxt, out CprError error)
		{
			CprError cprError = CheckFormat(cprTxt);
			if (cprError == CprError.NoError)
				cprError = CheckDate(cprTxt);
			if (cprError == CprError.NoError)
				cprError = Check11Test(cprTxt);

			error = cprError;
			return (cprError == CprError.NoError);
		}

		private CprError Check11Test(string cprTxt)
		{
			CprError cprError = CprError.NoError;

			int sum = 0;
			for (int i = 0; i < 3; i++)
			{
				sum += int.Parse(cprTxt.Substring(i, 1)) * (4 - i);
			}
			for (int i = 3; i < 10; i++)
			{
				sum += int.Parse(cprTxt.Substring(i, 1)) * (10 - i);
			}
			if (sum % 11 != 0)
				cprError = CprError.Check11Error;

			return cprError;
		}

		private CprError CheckDate(string cprTxt)
		{
			CprError cprError = CprError.NoError;

			try
			{
				int day = int.Parse(cprTxt.Substring(0, 2));
				int month = int.Parse(cprTxt.Substring(2, 2));
				int year = int.Parse(cprTxt.Substring(4, 2));
				DateTime dt = new DateTime(year, month, day);
			}
			catch (Exception)
			{
				cprError = CprError.DateError;
			}

			return cprError;
		}

		private CprError CheckFormat(string cprTxt)
		{
			string cprFormat = "[0-9]{10}";
			CprError cprError = CprError.NoError;

			if (cprTxt.Length != 10)
				cprError = CprError.FormatError;

			Regex regExp = new Regex(cprFormat);
			Match match = regExp.Match(cprTxt);
			if (!match.Success)
				cprError = CprError.FormatError;

			return cprError;
		}
	}
}