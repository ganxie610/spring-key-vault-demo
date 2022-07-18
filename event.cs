using Microsoft.Azure.EventGrid.Models;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using System.Web.Http;

namespace EventSubscriptionHandler.Controller
{
    public class EventGridEventHandlerController : ApiController
    {

        private const string SUBSCRIPTION_VALIDATION_EVENT = "Microsoft.EventGrid.SubscriptionValidationEvent";
        private const string STORAGE_BLOB_CREATED_EVENT = "Microsoft.Storage.BlobCreated";
        private const string STORAGE_BLOB_DELETED_EVENT = "Microsoft.Storage.BlobDeleted";

        [Route("pb")]
        [HttpPost]
        public JObject HandleEvent(EventGridEvent[] events) 
        {
            if (events is null)
            {
                throw new ArgumentNullException(nameof(events));
            }
            EventGridEvent _event = events.FirstOrDefault();
            var _data = _event.Data as JObject;
            if (string.Equals(_event.EventType, SUBSCRIPTION_VALIDATION_EVENT, StringComparison.OrdinalIgnoreCase))
            { 
                var _eventData = _data.ToObject<SubscriptionValidationEventData>();
                var _responseData = new SubscriptionValidationResponse
                {
                    ValidationResponse = _eventData.ValidationCode
                };
                if (_responseData.ValidationResponse != null)
                {
                    return JObject.FromObject(_responseData);
                }
            }
            if (string.Equals(_event.EventType, STORAGE_BLOB_CREATED_EVENT, StringComparison.OrdinalIgnoreCase))
            {
                var _eventData = _data.ToObject<StorageBlobCreatedEventData>();
                Console.WriteLine("blob created, url ===> {0}", _eventData.Url);
                return JObject.FromObject(new HttpResponseMessage(System.Net.HttpStatusCode.OK));
            }
            if (string.Equals(_event.EventType, STORAGE_BLOB_DELETED_EVENT, StringComparison.OrdinalIgnoreCase))
            {
                var _eventData = _data.ToObject<StorageBlobDeletedEventData>();
                Console.WriteLine("blob deleted, url ===> {0}", _eventData.Url);
                return JObject.FromObject(new HttpResponseMessage(System.Net.HttpStatusCode.OK));
            }
            return JObject.FromObject(new HttpResponseMessage(System.Net.HttpStatusCode.BadRequest));
        }

        
    }
}
