package com.babycenter.pregnancy_tracker.controller;

import com.babycenter.pregnancy_tracker.entity.DiaryEntry;
import com.babycenter.pregnancy_tracker.service.DiaryService;
import com.babycenter.pregnancy_tracker.service.TrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member/diary")
public class DiaryController {

    @Autowired
    private DiaryService diaryService;

    @Autowired
    private TrackingService trackingService;

    @GetMapping
    public String showDiary(Model model, Authentication authentication) {
        String username = authentication.getName();
        Long userId = trackingService.getTrackingByUsername(username).getId();
        model.addAttribute("diaryEntries", diaryService.getDiaryEntries(userId));
        model.addAttribute("diaryEntry", new DiaryEntry());
        return "diary";
    }

    @PostMapping("/add")
    public String addDiaryEntry(@ModelAttribute("diaryEntry") DiaryEntry diaryEntry, Authentication authentication) {
        String username = authentication.getName();
        Long userId = trackingService.getTrackingByUsername(username).getId();
        diaryEntry.setUserId(userId);
        diaryService.saveDiaryEntry(diaryEntry);
        return "redirect:/member/diary";
    }

    @GetMapping("/delete/{id}")
    public String deleteDiaryEntry(@PathVariable("id") Long id) {
        diaryService.deleteDiaryEntry(id);
        return "redirect:/member/diary";
    }
}