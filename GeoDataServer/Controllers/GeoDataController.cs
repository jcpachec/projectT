using GeoDataDB;
using System;
using System.Collections.Generic;
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

            TGeoDataEntities dbContext = new TGeoDataEntities();

            var locations = from k in dbContext.GeoDatas
                            select k;

            return locations.AsEnumerable();

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
