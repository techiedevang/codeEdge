package com.placement.codeedge.controller;

import com.placement.codeedge.model.Problem;
import com.placement.codeedge.service.CompanyService;
import com.placement.codeedge.service.CustomUserDetailsService.CustomUserDetails;
import com.placement.codeedge.service.ProblemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Tag(name = "Companies", description = "Company-wise question browser")
public class CompanyController {

    private final CompanyService companyService;
    private final ProblemService problemService;

    @GetMapping("/companies")
    public String companiesPage(Model model) {
        model.addAttribute("companies", companyService.getAllCompanies());
        return "companies";
    }

    @GetMapping("/companies/{name}")
    public String companyDetail(
            @PathVariable String name,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Model model) {
        companyService.getCompanyByName(name).ifPresent(c -> {
            model.addAttribute("company", c);
            List<Problem> problems = problemService.getByCompany(userDetails.getUser(), name);
            model.addAttribute("problems", problems);
            model.addAttribute("solvedCount", problems.stream().filter(Problem::isSolved).count());
        });
        return "companies";
    }

    @GetMapping("/api/companies")
    @ResponseBody
    public List<CompanyService.CompanyInfo> getCompanies() {
        return companyService.getAllCompanies();
    }

    @GetMapping("/api/companies/{name}/problems")
    @ResponseBody
    public List<Problem> getCompanyProblems(
            @PathVariable String name,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return problemService.getByCompany(userDetails.getUser(), name);
    }
}
