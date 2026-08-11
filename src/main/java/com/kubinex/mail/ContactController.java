package com.kubinex.mail;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class ContactController {

    private final MailService mailService;

    public ContactController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/contact")
    public ResponseEntity<Void> contact(@Valid @RequestBody ContactRequest req) {
        mailService.sendContact(req.name(), req.email(), req.subject(), req.message());
        return ResponseEntity.ok().build();
    }

    record ContactRequest(@NotBlank String name,
                          @NotBlank @Email String email,
                          @NotBlank String subject,
                          @NotBlank String message) {}
}
