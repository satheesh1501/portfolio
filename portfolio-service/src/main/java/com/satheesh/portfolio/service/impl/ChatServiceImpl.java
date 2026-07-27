package com.satheesh.portfolio.service.impl;

import com.satheesh.common.constants.AppConstants;
import com.satheesh.common.constants.MessageConstants;
import com.satheesh.portfolio.dto.ChatMessageDTO;
import com.satheesh.portfolio.security.RateLimiterService;
import com.satheesh.portfolio.service.ChatService;
import com.satheesh.common.util.AppLogger;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 6.2.0
 * 
 * @description Optimized Token-Based NLP Intent Engine with local testing rate-limit bypass for E2E automation runs.
 */
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private static final Logger log = LoggerFactory.getLogger(ChatServiceImpl.class);
    private static final String CLASS_NAME = ChatServiceImpl.class.getSimpleName();

    private final RateLimiterService rateLimiterService;

    // Domain keywords list for boundary validation
    private static final List<String> DOMAIN_KEYWORDS = Arrays.asList(
            "satheesh", "experience", "exp", "year", "years", "work", "job", "career", "background",
            "education", "college", "degree", "cgpa", "btech", "b.tech", "mepco", "schlenk", "university",
            "hcl", "profit", "company", "companies", "role", "roles", "engineer", "developer", "fullstack", "full stack",
            "skill", "skills", "stack", "technology", "technologies", "tech", "java", "spring", "boot", "security",
            "react", "angular", "kafka", "redis", "postgres", "postgresql", "mysql", "elasticsearch",
            "aws", "docker", "testcontainer", "testcontainers", "cypress", "junit", "mockito", "flyway", "github", "leetcode",
            "project", "projects", "civil", "saas", "microservice", "microservices", "portfolio", "architecture",
            "hobby", "hobbies", "interest", "interests", "passion", "passions", "dsa", "problem solving", "trekking", "fishing",
            "talks", "contact", "email", "reach", "hire", "location", "aruppukottai", "tamil nadu", "resume", "summary",
            "looking", "seeking", "goal", "goals", "ambition", "target", "opportunity",
            "relocate", "relocation", "remote", "hybrid", "onsite", "language", "languages", "tamil", "english", "why"
    );

    @Override
    public String processChat(ChatMessageDTO chatDTO, String ipAddress) {
        String methodName = "processChat";

        // Rate Limiting (10 requests per 5 minutes per IP, relaxed for local dev loopback testing)
        if (!isLocalhost(ipAddress)) {
            rateLimiterService.checkRateLimit(
                    AppConstants.REDIS_RATE_LIMIT_CHAT_PREFIX,
                    ipAddress,
                    AppConstants.CHAT_RATE_LIMIT,
                    AppConstants.CHAT_RATE_LIMIT_WINDOW_MINUTES
            );
        }

        AppLogger.info(log, "Portfolio-Service", CLASS_NAME, methodName, ipAddress, MessageConstants.LOG_ACTION_PROCESS_CHAT,
                "Processing AI Chat prompt: " + chatDTO.message());

        String rawPrompt = chatDTO.message().trim();
        String prompt = rawPrompt.toLowerCase();
        Set<String> tokens = extractTokens(prompt);

        // 1. Domain Boundary Check
        boolean isDomainValid = DOMAIN_KEYWORDS.stream().anyMatch(prompt::contains) ||
                prompt.startsWith("who") || prompt.startsWith("what") || prompt.startsWith("how") ||
                prompt.startsWith("tell") || prompt.startsWith("can") || prompt.startsWith("where") ||
                prompt.startsWith("why") || prompt.startsWith("which") || prompt.startsWith("is satheesh") ||
                prompt.startsWith("does satheesh") || tokens.contains("hi") || tokens.contains("hello") || tokens.contains("hey");

        boolean isIrrelevant = prompt.contains("weather") || prompt.contains("president") || prompt.contains("crypto") ||
                prompt.contains("bitcoin") || prompt.contains("movie") || prompt.contains("recipe") ||
                prompt.contains("song") || prompt.contains("football") || prompt.contains("cricket score") ||
                (prompt.length() < 3 && !tokens.contains("hi"));

        if (!isDomainValid || isIrrelevant) {
            return "Please ask a valid question about Satheesh Kumar P! You can ask about his 2.5+ years of experience, education (Mepco Schlenk College), LeetCode 138+ milestone, tech stack (Java 21, Spring Boot 3, React, AWS), past companies (Profit.co, HCL), projects, or work setup preferences.";
        }

        // 2. Tokenized Priority Intent Scoring Engine

        // GREETING (Exact Token Match ONLY — prevents 'his' from triggering a greeting)
        if ((tokens.contains("hi") || tokens.contains("hello") || tokens.contains("hey") || tokens.contains("greetings")) && tokens.size() <= 3) {
            return "Hello! 👋 I am Satheesh's AI Portfolio Assistant. Ask me anything about his 2.5+ years experience, education at Mepco Schlenk, LeetCode 138+ milestone, tech stack, or work preferences!";
        }

        // RANK 1: TARGET ROLE & CAREER GOALS ("what company he looking for", "seeking role")
        if (prompt.contains("looking for") || prompt.contains("seeking") || prompt.contains("goal") || prompt.contains("ambition") || prompt.contains("target role") || prompt.contains("next role") || prompt.contains("opportunity")) {
            return "Satheesh is seeking Senior / Mid-Level Full Stack Developer or Backend Java/Spring Boot Engineering roles where he can architect scalable microservices and high-performance applications.";
        }

        // RANK 2: LEETCODE MILESTONE & PROBLEM SOLVING ("his leetcode questions count", "dsa solved")
        if (tokens.contains("leetcode") || tokens.contains("dsa") || tokens.contains("138") || prompt.contains("problem solving") || (tokens.contains("problems") && tokens.contains("count"))) {
            return "Satheesh has solved 138+ Data Structures & Algorithms problems on LeetCode covering Arrays, Linked Lists, Trees, Graphs, and Dynamic Programming!";
        }

        // RANK 3: EDUCATION & COLLEGE ("his degree", "cgpa", "college")
        if (tokens.contains("education") || tokens.contains("college") || tokens.contains("degree") || tokens.contains("cgpa") || tokens.contains("btech") || tokens.contains("mepco") || tokens.contains("schlenk") || prompt.contains("where did he study")) {
            return "Satheesh graduated with a B.Tech in Information Technology from Mepco Schlenk Engineering College (2020 – 2024) achieving a CGPA of 7.56 / 10.";
        }

        // RANK 4: WORK SETUP & RELOCATION PREFERENCES
        if (tokens.contains("remote") || tokens.contains("hybrid") || tokens.contains("onsite") || tokens.contains("relocate") || tokens.contains("relocation") || prompt.contains("work setup")) {
            return "Satheesh is flexible regarding work setup! He is open to Remote, Hybrid, or Onsite roles and is fully willing to relocate.";
        }

        // RANK 5: LANGUAGES SPOKEN
        if (tokens.contains("language") || tokens.contains("languages") || tokens.contains("speak") || tokens.contains("tamil") || tokens.contains("english")) {
            return "Satheesh is fluent in English (Professional) and Tamil (Native).";
        }

        // RANK 6: PROFIT.CO
        if (tokens.contains("profit")) {
            return "At Profit.co (Software Engineer), Satheesh developed & optimized automated Data Export REST APIs, resolved critical backend & frontend production defects (Java 17, Spring Boot 3, Angular, React), and optimized DB query latency.";
        }

        // RANK 7: HCL TECHNOLOGIES
        if (tokens.contains("hcl")) {
            return "At HCL Technologies (Software Engineer), Satheesh built data migration REST APIs, resolved 15+ production bugs across Spring Boot microservices, and refactored complex SQL queries for faster execution.";
        }

        if (prompt.contains("past company") || prompt.contains("previous company") || tokens.contains("companies")) {
            return "Satheesh's experience includes working as a Software Engineer at Profit.co (Data Export APIs & defect fixes) and HCL Technologies (Migration APIs & microservices bug fixes).";
        }

        // RANK 8: CURRENT VS PAST TECH STACK
        if (tokens.contains("current") && (tokens.contains("tech") || tokens.contains("stack") || tokens.contains("using"))) {
            return "Currently, Satheesh works with Java 21, Spring Boot 3, Spring Security, Spring AI, PostgreSQL, Redis, Apache Kafka, React 18, Vite, Docker, and AWS (App Runner, S3, CloudFront).";
        }

        if (tokens.contains("past") && (tokens.contains("tech") || tokens.contains("stack") || tokens.contains("using"))) {
            return "In past roles at Profit.co and HCL, Satheesh worked with Java 8/11/17, Spring Boot, Spring Data JPA, Angular, MySQL, and Elasticsearch.";
        }

        // RANK 9: SKILLS ("his skills", "tech stack")
        if (tokens.contains("skill") || tokens.contains("skills") || tokens.contains("stack") || tokens.contains("technology") || tokens.contains("technologies")) {
            return "Satheesh's Core Skills include:\n• Backend: Java 21, Spring Boot 3, Spring Security, REST APIs, Microservices\n• Frontend: React 18, Angular, Vite, Modern CSS3, JavaScript (ES6+)\n• Database & Cache: PostgreSQL, MySQL, Redis, Flyway\n• Event Messaging: Apache Kafka\n• Cloud & DevOps: AWS, Docker, GitHub Actions\n• Testing: JUnit 5, Mockito, Testcontainers, Cypress";
        }

        // RANK 10: PROJECTS (CIVIL SAAS & PORTFOLIO)
        if (tokens.contains("civil") || tokens.contains("construction") || tokens.contains("saas")) {
            return "Satheesh is co-architecting Civil Platform — a multi-tenant construction SaaS digitizing site workforce management, automated geo-fenced attendance, payroll calculation, and site reporting (Java 21, Spring Boot 3, PostgreSQL, Redis, React 18, AWS).";
        }

        if (tokens.contains("project") || tokens.contains("projects") || tokens.contains("portfolio") || tokens.contains("kafka")) {
            return "Satheesh has built:\n1. Civil Platform: Enterprise Construction SaaS\n2. Event-Driven Portfolio: Built with Java 21 microservices, Apache Kafka event streaming, Redis caching, Spring AI, React 18, and Cypress E2E testing!";
        }

        // RANK 11: PASSIONS & HOBBIES
        if (tokens.contains("hobby") || tokens.contains("hobbies") || tokens.contains("interest") || tokens.contains("interests") || tokens.contains("passion") || tokens.contains("passions") || tokens.contains("trekking") || tokens.contains("fishing")) {
            return "Beyond coding, Satheesh is passionate about LeetCode/DSA Problem Solving (138+ solved), building SaaS products, watching system design tech talks (SpringOne, AWS re:Invent), and outdoor activities like trekking, fishing, and travelling!";
        }

        // RANK 12: WHY HIRE / STRENGTHS
        if (prompt.contains("why hire") || tokens.contains("strength") || tokens.contains("strengths") || tokens.contains("unique") || prompt.contains("why satheesh")) {
            return "You should hire Satheesh because he brings complete end-to-end full-stack ownership — 2.5+ years of production experience building Java 21 microservices, REST APIs, Angular & React UIs, system data migrations, and production defect resolution at Profit.co and HCL Technologies! He holds a B.Tech in IT from Mepco Schlenk (7.56 CGPA) and has solved 138+ problems on LeetCode.";
        }

        // RANK 13: CONTACT & LOCATION
        if (tokens.contains("contact") || tokens.contains("email") || tokens.contains("reach") || tokens.contains("hire") || tokens.contains("location") || tokens.contains("aruppukottai")) {
            return "You can contact Satheesh via email at psatheeshkumar89@gmail.com or through the website form. He is based in Aruppukottai, Tamil Nadu, India and ready for new engineering challenges!";
        }

        // DEFAULT SMART RESPONSE
        return "Satheesh Kumar P is a Full Stack Engineer (B.Tech IT from Mepco Schlenk, 7.56 CGPA, 138+ LeetCode solved) with 2.5+ years experience at Profit.co and HCL Technologies in Java 21, Spring Boot 3, React 18, Kafka, Redis, PostgreSQL, and AWS!";
    }

    private boolean isLocalhost(String ip) {
        return ip == null || "127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip) || ip.startsWith("127.") || ip.startsWith("localhost");
    }

    /**
     * Extracts lowercase word tokens using word boundary matching [a-z0-9]+
     */
    private Set<String> extractTokens(String text) {
        Set<String> tokens = new HashSet<>();
        Matcher matcher = Pattern.compile("[a-z0-9]+").matcher(text);
        while (matcher.find()) {
            tokens.add(matcher.group());
        }
        return tokens;
    }
}
