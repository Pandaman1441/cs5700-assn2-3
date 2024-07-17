import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class TrackerViewHelper: Observer {
    var shipmentId by mutableStateOf("")
    var shipmentNotes by mutableStateOf(listOf<String>())
    var shipmentUpdateHistory by mutableStateOf(listOf<ShippingUpdate>())
    var expectedShipmentDeliveryDate by mutableStateOf("")
    var shipmentStatus by mutableStateOf("")
    fun trackShipment(id: String){
        shipmentId = id
        val shipment = TrackingSimulator.findShipment(id)
        if (shipment != null) {
            shipment.subscribe(this)
            update(shipment)
        }
        else{
            shipmentStatus = "Invalid shipment ID"
        }
    }

    fun stopTracking(){
        val shipment = TrackingSimulator.findShipment(shipmentId)
        shipment?.unsubscribe(this)
        shipmentId = ""
        shipmentNotes = emptyList()
        shipmentUpdateHistory = emptyList()
        expectedShipmentDeliveryDate = ""
        shipmentStatus = ""
    }

    override fun update(shipment: Shipment) {
        if (shipment.id == shipmentId) {
            shipmentNotes = shipment.notes
            shipmentUpdateHistory = shipment.updateHistory
            expectedShipmentDeliveryDate = shipment.expectedDeliveryDateTimestamp.toString()
            shipmentStatus = shipment.status
        }
    }
}