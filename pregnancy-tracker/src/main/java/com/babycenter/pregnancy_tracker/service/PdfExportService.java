package com.babycenter.pregnancy_tracker.service;

import com.babycenter.pregnancy_tracker.entity.MemberPregnancyTracking;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PdfExportService {
    public byte[] exportTrackingToPdf(MemberPregnancyTracking tracking) throws IOException {
        // Implement PDF export logic
        return new byte[]{};
    }
}