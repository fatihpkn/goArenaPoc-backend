package com.mtek.poc.employee_service.controller

import com.mtek.poc.employee_service.configs.ResponseWrap
import com.mtek.poc.employee_service.model.EmployeeModel
import com.mtek.poc.employee_service.model.EmployeeWithShopModel
import com.mtek.poc.employee_service.repository.EmployeeGetRepository
import com.mtek.poc.employee_service.repository.EmployeePostRepository
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping(value = ["/{shopId}/employees"])
class EmployeeController() {


    @Autowired
    private lateinit var employeeGetRepository: EmployeeGetRepository

    @Autowired
    private lateinit var employeePostRepository: EmployeePostRepository


    // @RolesAllowed("goarena-admins")
    @SecurityRequirement(name = "bearer-key")
    @GetMapping("")
    fun all(): ResponseWrap<List<EmployeeWithShopModel>> {
        return ResponseWrap(employeeGetRepository.findAll())
    }

    //  @RolesAllowed("goarena-users")
    @SecurityRequirement(name = "bearer-key")
    @PostMapping("")
    fun create(@RequestBody userModel: EmployeeModel, @PathVariable("shopId") shopId:Long): ResponseWrap<EmployeeModel> {
        userModel .shopId=shopId
        return ResponseWrap(employeePostRepository.save(userModel))
    }
    @SecurityRequirement(name = "bearer-key")
    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: Long): ResponseWrap<EmployeeWithShopModel>? {
        return ResponseWrap(employeeGetRepository.findById(id).orElseThrow { ResourceNotFoundException() })

    }

    // @RolesAllowed("goarena-users")
    @SecurityRequirement(name = "bearer-key")
    @PutMapping("/{id}")
    fun update(
        @PathVariable("id") id: Long,
        @Valid @RequestBody userModel: EmployeeModel
    ): ResponseWrap<EmployeeModel>? {
        val entity = employeePostRepository.findById(id).orElseThrow { ResourceNotFoundException() }
        entity.first_name = userModel.first_name
        entity.last_name = userModel.last_name
        entity.username = userModel.username
        entity.avatar = userModel.avatar
        return ResponseWrap(employeePostRepository.save(entity))
    }

    //  @RolesAllowed("goarena-admins")
    @SecurityRequirement(name = "bearer-key")
    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Long) {
        return employeeGetRepository.deleteById(id)
    }
}