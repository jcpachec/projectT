using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace GeoDataServer.Controllers
{
    public class TimeController : ApiController
    {
        // GET api/time
        public IEnumerable<DateTime> Get()
        {
            return new DateTime[] { DateTime.Now, DateTime.UtcNow };
        }

        // GET api/time/5
        public DateTime Get(int id)
        {
            return DateTime.Now;
        }

        // POST api/time
        public void Post([FromBody]string value)
        {
        }

        // PUT api/time/5
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE api/time/5
        public void Delete(int id)
        {
        }
    }
}
