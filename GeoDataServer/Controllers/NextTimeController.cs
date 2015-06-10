using GeoDataServer.Repo;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace GeoDataServer.Controllers
{
    public class NextTimeController : ApiController
    {
        // GET api/time
        public DateTime? Get()
        {
            BussinesLogic business = new BussinesLogic();
            DateTime? date = business.GetNextStartTime();
            if (date.HasValue)
                return date.Value.ToUniversalTime();
            else
                return null;
        }

       
    }
}
