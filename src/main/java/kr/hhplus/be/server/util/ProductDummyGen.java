package kr.hhplus.be.server.util;

import java.util.ArrayList;
import java.util.List;

public class ProductDummyGen {
    static final String[] dummyProducts = {
            "Apple", "Banana", "Cherry", "Date", "Elderberry",
            "Fig", "Pen", "Hyundai Avante", "Apple M2 Macbook Pro 14-inches",
            "Samsung Galaxy S23", "Sony WH-1000XM5 Headphones", "Nike Air Max 270",
            "Adidas Ultraboost 21", "Dell XPS 13 Laptop", "Bose SoundLink Revolve+",
            "Canon EOS R5 Camera", "GoPro HERO9 Black", "LG OLED55CXPUA TV",
            "Microsoft Surface Pro 7", "Apple iPad Air (4th Gen)",
            "Samsung Galaxy Tab S7", "Amazon Kindle Paperwhite", "Sony PlayStation 5",
            "Xbox Series X", "Nintendo Switch OLED Model", "Razer Blade 15 Laptop",
            "Asus ROG Zephyrus G14", "HP Spectre x360", "Lenovo ThinkPad X1 Carbon",
            "Apple Watch Series 7",
            "Samsung Galaxy Watch 4", "Fitbit Charge 5", "Garmin Forerunner 245",
            "Bose QuietComfort Earbuds", "Apple AirPods Pro",
            "Jabra Elite 85t", "Sony WF-1000XM4", "Anker Soundcore Liberty 3 Pro",
            "Logitech MX Master 3 Mouse", "Razer DeathAdder V2 Mouse",
            "Corsair K95 RGB Platinum Keyboard", "SteelSeries Apex Pro Keyboard",
            "HyperX Cloud II Gaming Headset", "Logitech G Pro X Gaming Headset",
            "Elgato Stream Deck", "Blue Yeti USB Microphone",
            "Rode NT-USB Microphone", "Audio-Technica AT2020 USB Microphone",
            "Samsung T7 Portable SSD", "WD My Passport External Hard Drive",
    };

    public static String genProductName() {
        final int rndIndex = (int) (Math.random() * dummyProducts.length);
        return dummyProducts[rndIndex];
    }

    public static List<String> genProductNames(int count) {
        var ret = new ArrayList<String>(count);
        for (int i = 0; i < count; i++) {
            ret.add(genProductName());
        }
        return ret;
    }
}