package com.Onedev.transaksiQ.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class InvoiceGenerator {

    public static String generateInvoiceNumber() {
        // Format tanggal untuk bulan dan tahun (MMYY)
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMyy");
        String datePart = dateFormat.format(new Date());

        // Format waktu untuk jam, menit, detik (HHmmss)
        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
        String timePart = timeFormat.format(new Date());

        // UUID untuk memastikan keunikan (ambil 4 karakter terakhir)
        String uuidPart = UUID.randomUUID().toString().substring(0, 4).toUpperCase();

        // Gabungkan semuanya
        return "INV-" + datePart + timePart + "-" + uuidPart;
    }

}
