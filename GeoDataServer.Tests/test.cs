using System;
using System.Text;
using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace GeoDataServer.Tests
{
    /// <summary>
    /// Summary description for test
    /// </summary>
    [TestClass]
    public class test
    {
        public test()
        {
            //
            // TODO: Add constructor logic here
            //
        }

        private TestContext testContextInstance;

        /// <summary>
        ///Gets or sets the test context which provides
        ///information about and functionality for the current test run.
        ///</summary>
        public TestContext TestContext
        {
            get
            {
                return testContextInstance;
            }
            set
            {
                testContextInstance = value;
            }
        }

        #region Additional test attributes
        //
        // You can use the following additional attributes as you write your tests:
        //
        // Use ClassInitialize to run code before running the first test in the class
        // [ClassInitialize()]
        // public static void MyClassInitialize(TestContext testContext) { }
        //
        // Use ClassCleanup to run code after all tests in a class have run
        // [ClassCleanup()]
        // public static void MyClassCleanup() { }
        //
        // Use TestInitialize to run code before running each test 
        // [TestInitialize()]
        // public void MyTestInitialize() { }
        //
        // Use TestCleanup to run code after each test has run
        // [TestCleanup()]
        // public void MyTestCleanup() { }
        //
        #endregion

        [TestMethod]
        public void TestMethod1()
        {

            //Logic to check if we can display the Data,

            //InitialDate: 22:00:00
            //NextDayEndTime: 

            TimeSpan startToday = TimeSpan.Parse("16:00"); // 10 PM
            TimeSpan endNextDay = TimeSpan.Parse("2:00");   // 2 AM
            TimeSpan midnight = TimeSpan.Parse("23:59");   // 2 AM

            TimeSpan currentTime1 = DateTime.Now.TimeOfDay;
          
            TimeSpan currentTime = DateTime.Now.AddHours(7).TimeOfDay;

            if (startToday <= endNextDay)
            {
                // start and stop times are in the same day
                if (currentTime >= startToday && currentTime <= endNextDay)
                {
                    // current time is between start and stop

                    DateTime StartDate1 = DateTime.Now.Date + endNextDay;
                    DateTime StartDate2 = DateTime.Now.Date.AddDays(1) + endNextDay;

                }
            }
            else
            {
                // start and stop times are in different days
                if (currentTime >= startToday || currentTime <= endNextDay)
                {

                    if (currentTime >= startToday)
                    {
                        // current time is between start and stop
                        DateTime StartDate3 = DateTime.Now.Date + endNextDay;
                        DateTime StartDate4 = StartDate3.AddDays(1) + endNextDay;
                    }
                    else
                    {
                        DateTime StartDate3 = DateTime.Now.Date.AddDays(-1) + endNextDay;
                        DateTime StartDate4 = StartDate3.AddDays(1) + endNextDay;

                    }


                    if (currentTime <= midnight)
                    {
                        
                    }
                    else
                    {
                    }



                }
            }


                      

        }
    }
}
