package com.kubinex.config;

import com.kubinex.site.SiteBlock;
import com.kubinex.site.SiteBlockRepository;
import com.kubinex.user.Role;
import com.kubinex.user.User;
import com.kubinex.user.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final SiteBlockRepository repo;
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    @Value("${app.admin.username:admin}")
    private String adminUsername;

    @Value("${app.admin.password:admin123}")
    private String adminPassword;

    public DataSeeder(SiteBlockRepository repo, UserRepository userRepo, PasswordEncoder encoder) {
        this.repo = repo;
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        if (userRepo.count() == 0) {
            userRepo.save(new User(adminUsername, encoder.encode(adminPassword), Role.ADMIN));
        }

        if (repo.count() > 0) return;

        repo.save(new SiteBlock("hero", "Kubinex", "Des solutions digitales sur mesure",
                "Agence digitale 100% en ligne, nous concevons des applications web et mobiles pour propulser votre entreprise, où que vous soyez dans le monde.", 0));
        repo.save(new SiteBlock("about", "À propos", "Qui sommes-nous ?",
                "Kubinex est une agence digitale 100% en ligne. Nous accompagnons startups et entreprises du monde entier dans la création de plateformes innovantes, avec une collaboration simple et efficace, quel que soit votre fuseau horaire.", 1));
        repo.save(new SiteBlock("services", "Nos Services", "",
                """
                Développement web — Applications mobiles — UI/UX Design — Cloud & DevOps
                Une équipe créative à votre écoute, où que vous soyez, pour donner vie à vos projets les plus ambitieux.
                """.stripIndent(), 2));
        repo.save(new SiteBlock("contact", "Contactez-nous", "Parlons de votre projet",
                "contact@kubinex.com — +33 4 00 00 00 00 — 100% en ligne, partout dans le monde", 3));
    }
}
