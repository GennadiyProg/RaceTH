package ru.corporateproduct.raceth.usbreader;

import org.usb4java.Device;
import org.usb4java.javax.Services;

import javax.usb.*;
import javax.usb.event.UsbDeviceListener;
import java.util.List;

public class UsbReader {

    private final UsbHub usbHub = new Services().getRootUsbHub();
    private final short vendorId;
    private final short productId;

    public UsbReader(short vId, short pId) throws UsbException {
        this.vendorId = vId;
        this.productId = pId;
    }

    public void FindDevice() {
        this.FindDevice(usbHub, this.vendorId, this.productId);
    }

    public void FindDevice(short vendorId, short productId) {
        this.FindDevice(usbHub, vendorId, productId);
    }

    public UsbDevice FindDevice(UsbHub hub, short vendorId, short productId) {
        List<UsbDevice> devices = hub.getAttachedUsbDevices();
        for (UsbDevice device : devices) {
            UsbDeviceDescriptor dev = device.getUsbDeviceDescriptor();
            if (dev.idVendor() == vendorId && dev.idProduct() == productId)
                return device;
//            if (device.isUsbHub())
//                FindDevice((UsbHub) device, vendorId, productId);
        }
        return null;
    }

    public void main() throws UsbException {
        UsbHub hub = new Services().getRootUsbHub();
        System.out.println(FindDevice(hub, (short) 2522, (short) 62995));
    }
}
