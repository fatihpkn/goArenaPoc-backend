package com.mtek.poc.employee_service.controller

import com.mtek.poc.employee_service.configs.ResponseWrap
import com.mtek.poc.employee_service.model.ShopModel
import com.mtek.poc.employee_service.model.ShopWithEmployeesModel
import com.mtek.poc.employee_service.repository.ShopGetRepository
import com.mtek.poc.employee_service.repository.ShopPostRepository
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping(value = ["/"])
class ShopController() {


    @Autowired
    private lateinit var shopGetRepository: ShopGetRepository

    @Autowired
    private lateinit var shopPostRepository: ShopPostRepository


    // @RolesAllowed("goarena-admins")
    @SecurityRequirement(name = "bearer-key")
    @GetMapping("")
    fun all(): ResponseWrap<List<ShopWithEmployeesModel>> {
        return ResponseWrap(shopGetRepository.findAll())
    }

    //  @RolesAllowed("goarena-shops")
    @SecurityRequirement(name = "bearer-key")
    @PostMapping("")
    fun create(@RequestBody shopModel: ShopModel): ResponseWrap<ShopModel> {

        return ResponseWrap(shopPostRepository.save(shopModel))
    }

    @SecurityRequirement(name = "bearer-key")
    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: Long): ResponseWrap<ShopWithEmployeesModel>? {
        return ResponseWrap(shopGetRepository.findById(id).orElseThrow { ResourceNotFoundException() })

    }

    // @RolesAllowed("goarena-shops")
    @SecurityRequirement(name = "bearer-key")
    @PutMapping("/{id}")
    fun update(@PathVariable("id") id: Long, @Valid @RequestBody shopModel: ShopModel): ResponseWrap<ShopModel>? {
        val entity: ShopModel = shopPostRepository.findById(id).orElseThrow { ResourceNotFoundException() }
        entity.name = shopModel.name
        return ResponseWrap(shopPostRepository.save(entity))
    }

    //  @RolesAllowed("goarena-admins")
    @SecurityRequirement(name = "bearer-key")
    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Long) {
        return shopGetRepository.deleteById(id)
    }
}