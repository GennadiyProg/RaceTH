package ru.corporateproduct.raceth.usbreader;

import org.usb4java.Device;
import org.usb4java.javax.Services;

import javax.usb.*;
import java.util.List;

public class UsbReader {

    private final UsbHub usbHub = new Services().getRootUsbHub();
    private final short vendorId;
    private final short productId;

    public UsbReader(short vId, short pId) throws UsbException {
        this.vendorId = vId;
        this.productId = pId;
    }

    private UsbDevice FindDevice() {
        return this.FindDevice(usbHub, this.vendorId, this.productId);
    }

    private UsbDevice FindDevice(UsbHub hub, short vendorId, short productId) {
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

    public void main() {
        var device = FindDevice();
        System.out.println(device.getUsbDeviceDescriptor());
        for (UsbConfiguration item : (List<UsbConfiguration>) device.getUsbConfigurations()) {
            System.out.println(item.getUsbConfigurationDescriptor());

            for (var item0 : (List<UsbInterface>) item.getUsbInterfaces())
                System.out.println(item0.getUsbInterfaceDescriptor());
        }
    }
}
