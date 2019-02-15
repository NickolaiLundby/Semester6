using System;
using System.Text.RegularExpressions;

namespace Lab41
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
                sum += int.Parse(cprTxt.Substring(i, 10)) * (10 - i);
            }
            if (sum % 11 != 0)
                cprError = CprError.Check11Error;

            return cprError;
        }

        private CprError CheckDate(string cprTxt)
        {
            CprError cprError = CprError.NoError;

            string dayStr = cprTxt.Substring(0, 1);
            string monthStr = cprTxt.Substring(2, 3);
            string yearStr = cprTxt.Substring(4, 7);

            int day;
            int month;
            int year;

            try
            {
                day = int.Parse(dayStr);
                month = int.Parse(monthStr);
                year = int.Parse(yearStr);
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
            Regex regExp;
            Match match;
            string cprFormat = "[0-9]{10}";
            CprError cprError = CprError.NoError;

            if (cprTxt.Length != 10)
                cprError = CprError.FormatError;

            regExp = new Regex(cprFormat);
            match = regExp.Match(cprTxt);
            if (!match.Success)
                cprError = CprError.FormatError;

            return cprError;
        }
    }
}
