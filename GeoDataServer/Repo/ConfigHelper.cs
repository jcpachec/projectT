using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using GeoDataDB;

namespace GeoDataServer.Repo
{  
    public class ConfigHelper
    {

        /// <summary>
        /// gets one value from the configuration items from the URL
        /// </summary>
        /// <param name="Group">A name in order to group the attributes in example ConnectionsStrings</param>
        /// <param name="Attribute">name of teh attribute ie ProductionEndPoint, PreProdEndPoint</param>
        /// <returns>Single value from the configuration attribute</returns>
        public static string getValue(string Group, string Attribute)
        {
            using (TGeoDataEntities _dbContext = new TGeoDataEntities())
            {
                var _dbvalues = from rm in _dbContext.Configs
                                where ((rm.Class == Group) && (rm.Attribute == Attribute))
                                select rm;

                Config cfg = _dbvalues.FirstOrDefault();

                if (cfg != null)
                {
                       return cfg.Value;
                }
                else
                {
                    return string.Empty;
                }
            }

        }

        /// <summary>
        /// Retrives more multiple values for the configuration items from the URL
        /// </summary>
        /// <param name="Group">A name in order to group the attributes in example ConnectionsStrings</param>
        /// <param name="Attribute">name of teh attribute ie ProductionEndPoint, PreProdEndPoint</param>
        /// <returns>Collection of String values</returns>
        public static string[] getValues(string Group, string Attribute)
        {
            using (TGeoDataEntities _dbContext = new TGeoDataEntities())
            {

                var _dbvalues = from rm in _dbContext.Configs
                                where ((rm.Class == Group) && (rm.Attribute == Attribute))
                                select rm.Value;

                List<String> _listvalues = new List<string>();

                foreach (string value in _dbvalues)
                {
                    _listvalues.Add(value);
                }

                return _listvalues.ToArray();
            }

        }

    }
}