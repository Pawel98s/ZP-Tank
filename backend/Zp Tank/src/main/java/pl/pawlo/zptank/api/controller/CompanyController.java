package pl.pawlo.zptank.api.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.pawlo.zptank.api.dto.company.CompanyDTO;
import pl.pawlo.zptank.api.mapper.CompanyMapper;
import pl.pawlo.zptank.domain.company.Company;
import pl.pawlo.zptank.service.CompanyService;

@RestController
@RequestMapping("/api/company")
@AllArgsConstructor
public class CompanyController {

    private final CompanyService companyService;
    private final CompanyMapper companyMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CompanyDTO save(@RequestBody CompanyDTO companyDTO) {
        Company save = companyService.save(companyMapper.mapToDomain(companyDTO));
        return companyMapper.mapToDTO(save);
    }

    @GetMapping("/{id}")
    public CompanyDTO findById(@PathVariable Long id) {
        Company company = companyService.findById(id);
        return companyMapper.mapToDTO(company);
    }

    @PatchMapping("/{id}")
    public CompanyDTO update(@PathVariable Long id,
                             @RequestBody CompanyDTO companyDTO) {
        Company company = companyMapper.mapToDomain(companyDTO);
        Company update = companyService.update(id, company);
        return companyMapper.mapToDTO(update);
    }
}
