package ru.corporateproduct.raceth.usbreader;

import org.usb4java.Device;
import org.usb4java.DeviceList;
import org.usb4java.LibUsb;
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
            if (device.isUsbHub())
                FindDevice((UsbHub) device, vendorId, productId);
        }
        return null;
    }

    public void ReceiveMessage() {

    }

    public void main() throws UsbException {
        UsbDevice device = FindDevice();
        System.out.println(device.getUsbDeviceDescriptor());

        UsbInterface infc = device.getActiveUsbConfiguration().getUsbInterface((byte) 0);

        infc.claim();
//        infc.claim(new UsbInterfacePolicy() {
//                       @Override
//                       public boolean forceClaim(UsbInterface usbInterface) {
//                           return true;
//                       }
//                   }
//        );

        UsbEndpoint receptionEndPoint = (UsbEndpoint) infc.getUsbEndpoints().get(0);

        UsbPipe receptionPipe = receptionEndPoint.getUsbPipe();
        receptionPipe.open();

        byte[] b = new byte[64];
        int length = 27;
        while (true) {
            var Длина = receptionPipe.syncSubmit(b);
            for (byte i : b)
                System.out.println(i);
        }
    }
}
