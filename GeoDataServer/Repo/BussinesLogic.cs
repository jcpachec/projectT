using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GeoDataServer.Repo
{
    public class BussinesLogic
    {
        public DateTime? GetNextStartTime()
        {
            TimeSpan startToday = TimeSpan.Parse(ConfigHelper.getValue("GlobalConfig", "StartTime")); // 10 PM
            TimeSpan endNextDay = TimeSpan.Parse(ConfigHelper.getValue("GlobalConfig", "EndDate"));   // 5 AM
            TimeSpan currentTime = DateTime.Now.TimeOfDay;
            DateTime? nextStart;
            if (startToday <= endNextDay)
            {
                // start and stop times are in the same day
                if (currentTime >= startToday && currentTime <= endNextDay)
                {
                    nextStart = null;
                }
                else
                    if (currentTime < endNextDay)
                        nextStart = DateTime.Now.Date.Add(startToday);
                    else
                        nextStart = DateTime.Now.Date.AddDays(1).Add(startToday);
            }
            else
            {
                if (currentTime >= startToday || currentTime <= endNextDay)
                {
                    // current time is between start and stop
                    nextStart = null;
                }
                else
                {
                    nextStart = DateTime.Now.Date.Add(startToday);
                }
            }
            return nextStart;
        }


        public bool IsTimeToDisplayMarkers()
        {          
         
                //Select all the items that where set for this particular time.
                //We need to check if the app can show the Data
                TimeSpan startToday = TimeSpan.Parse(ConfigHelper.getValue("GlobalConfig", "StartTime")); // 10 PM
                TimeSpan endNextDay = TimeSpan.Parse(ConfigHelper.getValue("GlobalConfig", "EndDate"));   // 5 AM
                TimeSpan currentTime = DateTime.Now.TimeOfDay;

                if (startToday <= endNextDay)
                {
                    // start and stop times are in the same day
                    if (currentTime >= startToday && currentTime <= endNextDay)
                    {
                        // current time is between start and stop
                        return true;
                    }
                }
                else
                {
                    // start and stop times are in different days
                    if (currentTime >= startToday || currentTime <= endNextDay)
                    {
                        // current time is between start and stop
                        return true;
                    }
                }
            
            return false;

        }


        public DateTime getStartTimeforMarkers()
        {

            //Select all the items that where set for this particular time.
            //We need to check if the app can show the Data
            TimeSpan startToday = TimeSpan.Parse(ConfigHelper.getValue("GlobalConfig", "StartTime")); // 10 PM
            TimeSpan endNextDay = TimeSpan.Parse(ConfigHelper.getValue("GlobalConfig", "EndDate"));   // 5 AM
            TimeSpan currentTime = DateTime.Now.TimeOfDay;

            DateTime returnDate = DateTime.Now;

            if (startToday <= endNextDay)
            {
                // start and stop times are in the same day
                if (currentTime >= startToday && currentTime <= endNextDay)
                {
                    //we need to refactor this.
                    // current time is between start and stop
                    return returnDate;
                }
            }
            else
            {
                // start and stop times are in different days
                if (currentTime >= startToday || currentTime <= endNextDay)
                {
                    // current time is between start and stop

                    if (currentTime >= startToday)
                    {
                        // current time is between start and stop
                        returnDate = DateTime.Now.Date + endNextDay;                        
                    }
                    else
                    {
                        returnDate = DateTime.Now.Date.AddDays(-1) + endNextDay;                       
                    }

                }
            }

            return returnDate;

        }

    }
}