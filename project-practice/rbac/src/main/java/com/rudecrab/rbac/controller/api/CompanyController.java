package com.rudecrab.rbac.controller.api;

import com.rudecrab.rbac.model.entity.Company;
import com.rudecrab.rbac.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author RudeCrab
 */
@RestController
@RequestMapping("/API/company")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping("/list")
    public List<Company> list() {
        return companyService.list();
    }

}
