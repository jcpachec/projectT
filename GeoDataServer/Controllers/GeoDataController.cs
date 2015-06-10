using GeoDataDB;
using GeoDataServer.Repo;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace GeoDataServer.Controllers
{
    public class GeoDataController : ApiController
    {

        
        // GET api/geodata/5
        public IEnumerable<GeoData> Get()
        {
           
            try
            {
                TGeoDataEntities dbContext = new TGeoDataEntities();
             

                string Mode = ConfigHelper.getValue("GlobalConfig", "Mode");
            
                switch (Mode)
                {
                    case "STOP": return new Collection<GeoData>();
                    

                    case "DEBUG": var locations = from k in dbContext.GeoDatas
                                                  select k;
                        return locations.AsEnumerable();

                    case "NORMAL":
                        BussinesLogic _bussinesLogic = new BussinesLogic();
                        if (_bussinesLogic.IsTimeToDisplayMarkers()) // TORITO! ;)
                        {
                            DateTime initDate = _bussinesLogic.getStartTimeforMarkers();
                            DateTime endDate = _bussinesLogic.getStartTimeforMarkers().AddDays(1);

                            var locations1 = from k1 in dbContext.GeoDatas
                                             where k1.time >= initDate && k1.time <= endDate && k1.categoryId == 1
                                             select k1;
                            return locations1.AsEnumerable();
                        }
                        else
                        {
                            var locations1 = from k1 in dbContext.GeoDatas //RETURN ALL OTHER CATEGORIES
                                             where k1.categoryId != 1
                                             select k1;
                            return locations1.AsEnumerable();
                        }

                }
            }
            catch(Exception ex)
            {
                using (TGeoDataEntities dbContext = new TGeoDataEntities())
                {
                    Logger log = new Logger();
                    log.log = ex.Message + " - " + ex.StackTrace; 
                    dbContext.Loggers.Add(log);
                    dbContext.SaveChanges();
                }
            }

            return new Collection<GeoData>(); 
        }
                                          
        // GET api/geodata/5
        public IEnumerable<GeoData> Get(DateTime StartDate, DateTime EndDate)
        {

            using (TGeoDataEntities dbContext = new TGeoDataEntities())
            {
                var locations = from k in dbContext.GeoDatas
                                where k.time == StartDate && k.time == EndDate
                                select k;

                return locations.AsEnumerable();
            }
        }

        // POST api/geodata
        public void Post(GeoData NewValue)
        {
            using (TGeoDataEntities dbContext = new TGeoDataEntities())
            {
                NewValue.time = DateTime.Now;
                dbContext.GeoDatas.Add(NewValue);
                dbContext.SaveChanges();
            }
        }
      
    }
}
