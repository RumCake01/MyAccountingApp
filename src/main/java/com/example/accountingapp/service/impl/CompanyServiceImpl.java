package com.example.accountingapp.service.impl;

import com.example.accountingapp.dto.CompanyDTO;
import com.example.accountingapp.entity.Company;
import com.example.accountingapp.enums.CompanyStatus;
import com.example.accountingapp.mapper.MapperUtil;
import com.example.accountingapp.repository.CompanyRepository;
import com.example.accountingapp.service.CompanyService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final MapperUtil mapperUtil;

    public CompanyServiceImpl(CompanyRepository companyRepository, MapperUtil mapperUtil) {
        this.companyRepository = companyRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public List<CompanyDTO> listAllCompanies() {
        return companyRepository.findAll().stream().map(company -> mapperUtil.convert(company, new CompanyDTO())).collect(Collectors.toList());
    }

    @Override
    public CompanyDTO findById(Long id) {
        return mapperUtil.convert(companyRepository.findById(id).get(), new CompanyDTO());
    }


    @Override
    public void save(CompanyDTO company) {

        company.setEnabled(true);
        company.setCompanyStatus(CompanyStatus.ENABLED);
        companyRepository.save(mapperUtil.convert(company, new Company()));


    }

    @Override
    public CompanyDTO update(CompanyDTO dto) {
        Company company = companyRepository.findByEmail(dto.getEmail());
        Company convertedCompany = mapperUtil.convert(dto, new Company());
        convertedCompany.setId(company.getId());
        companyRepository.save(convertedCompany);

        return findByEmail(dto.getEmail());
    }

    @Override
    public CompanyDTO findByEmail(String email) {
        Company company = companyRepository.findByEmail(email);
        return mapperUtil.convert(company, new CompanyDTO());
    }

    @Override
    public void reopen(Long id) {
        Company company = companyRepository.findById(id).get();
        company.setCompanyStatus(CompanyStatus.ENABLED);
        companyRepository.save(company);

    }

    @Override
    public void close(Long id) {
        Company company = companyRepository.findById(id).get();
        company.setCompanyStatus(CompanyStatus.DISABLED);
        companyRepository.save(company);

    }



}
