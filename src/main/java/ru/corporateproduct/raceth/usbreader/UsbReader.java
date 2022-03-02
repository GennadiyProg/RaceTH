package ru.corporateproduct.raceth.usbreader;

import org.usb4java.Device;
import org.usb4java.javax.Services;

import javax.usb.*;
import javax.usb.event.UsbDeviceListener;
import java.util.List;

public class UsbReader {

    public void main() throws UsbException {
        UsbHub hub = new Services().getRootUsbHub();
        System.out.println(FindDevice(hub, (short) 2522, (short) 62995));
    }

    public UsbDeviceDescriptor FindDevice(UsbHub hub, int vendorId, int productId) {
        List<UsbDevice> devices = hub.getAttachedUsbDevices();
        for (UsbDevice device : devices) {
            UsbDeviceDescriptor dev = device.getUsbDeviceDescriptor();
            if (dev.idVendor() == vendorId && dev.idProduct() == productId)
                return dev;
//            if (device.isUsbHub())
//                FindDevice((UsbHub) device, vendorId, productId);
        }
        return null;
    }
}
