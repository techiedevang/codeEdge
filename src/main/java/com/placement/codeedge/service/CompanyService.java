package com.placement.codeedge.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CompanyService {

    public record CompanyInfo(
            String name,
            String logo,
            String description,
            String difficulty,
            List<String> rounds,
            List<String> focusAreas,
            String avgPackage,
            String color
    ) {}

    private static final List<CompanyInfo> COMPANIES = List.of(
        new CompanyInfo("Amazon", "🛒",
            "Amazon focuses heavily on Leadership Principles. Expect system design + LPs in interviews.",
            "MEDIUM-HARD",
            List.of("Online Assessment", "Technical Phone Screen", "System Design", "Leadership Principles", "Bar Raiser"),
            List.of("Arrays", "Dynamic Programming", "Trees", "System Design", "OOP"),
            "₹18-45 LPA",
            "#FF9900"),

        new CompanyInfo("Google", "🔍",
            "Google interviews are algorithm-heavy. Clean code and time complexity analysis are critical.",
            "HARD",
            List.of("Online Coding Round", "Technical Phone Screen x2", "Onsite Coding x3", "System Design"),
            List.of("Graphs", "Dynamic Programming", "Binary Search", "Strings", "System Design"),
            "₹25-60 LPA",
            "#4285F4"),

        new CompanyInfo("Microsoft", "🪟",
            "Microsoft values problem-solving clarity. They test fundamentals and design skills.",
            "MEDIUM",
            List.of("Online Assessment", "Technical Rounds x3", "System Design", "HR"),
            List.of("Trees", "Arrays", "Linked Lists", "OOP Design", "System Design"),
            "₹20-50 LPA",
            "#00A4EF"),

        new CompanyInfo("Flipkart", "🛍️",
            "Flipkart tests strong DSA and backend design skills for SDE roles.",
            "MEDIUM",
            List.of("Online Assessment", "Technical Round x2", "System Design", "HR"),
            List.of("Arrays", "Trees", "Dynamic Programming", "Greedy", "System Design"),
            "₹20-42 LPA",
            "#F7B731"),

        new CompanyInfo("Goldman Sachs", "💰",
            "GS focuses on algorithms, data structures, and quantitative problem solving.",
            "MEDIUM-HARD",
            List.of("Online Coding Test", "Technical Interviews x3", "HR Round"),
            List.of("Arrays", "Graphs", "Dynamic Programming", "Math", "Sorting"),
            "₹15-35 LPA",
            "#6495ED"),

        new CompanyInfo("Adobe", "🎨",
            "Adobe emphasizes OOP design, algorithms, and sometimes creative problem solving.",
            "MEDIUM",
            List.of("Online Assessment", "Technical Rounds x2", "System Design", "Managerial", "HR"),
            List.of("Trees", "Strings", "Graphs", "OOP Design", "Heaps"),
            "₹15-35 LPA",
            "#FF0000"),

        new CompanyInfo("Uber", "🚗",
            "Uber focuses on scalable systems and solid algorithm fundamentals.",
            "MEDIUM-HARD",
            List.of("Recruiter Screen", "Technical Phone Screen", "Onsite x4", "System Design"),
            List.of("Graphs", "Heaps", "Arrays", "Dynamic Programming", "System Design"),
            "₹20-45 LPA",
            "#1C1C1C"),

        new CompanyInfo("Salesforce", "☁️",
            "Salesforce tests Java/OOP skills and clean architecture for SDE intern roles.",
            "MEDIUM",
            List.of("Online Assessment", "Technical Rounds x2", "HR"),
            List.of("Arrays", "Strings", "OOP Design", "Trees", "Sorting"),
            "₹15-30 LPA",
            "#00A1E0")
    );

    public List<CompanyInfo> getAllCompanies() {
        return COMPANIES;
    }

    public Optional<CompanyInfo> getCompanyByName(String name) {
        return COMPANIES.stream()
                .filter(c -> c.name().equalsIgnoreCase(name))
                .findFirst();
    }
}
