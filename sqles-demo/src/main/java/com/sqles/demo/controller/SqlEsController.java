package com.sqles.demo.controller;

import com.sqles.demo.domain.TestEntity;
import com.sqles.demo.http.Response;
import com.sqles.demo.service.TestEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */

@Controller
public class SqlEsController {
    @Autowired
    private TestEntityService testEntityService;

    @GetMapping(value = "/sqles/index/create")
    @ResponseBody
    public Response createIndex() {
        try {
            boolean result = testEntityService.createIndex();
            Response response = Response.success(result);
            return response;
        } catch (Exception e) {
            Response response = Response.failed(e.getMessage());
            return response;
        }
    }

    @GetMapping(value = "/sqles/index/insert")
    @ResponseBody
    public Response insert() {
        try {
            TestEntity testEntity = new TestEntity();
            testEntity.setId(111L);
            testEntity.setTextField("insert es");
            int result = testEntityService.insert(testEntity);
            Response response = Response.success(result);
            return response;
        } catch (Exception e) {
            Response response = Response.failed(e.getMessage());
            return response;
        }
    }

    @GetMapping(value = "/sqles/index/update")
    @ResponseBody
    public Response update() {
        try {
            TestEntity testEntity = new TestEntity();
            testEntity.setId(111L);
            testEntity.setTextField("update es");
            int result = testEntityService.update(testEntity);
            Response response = Response.success(result);
            return response;
        } catch (Exception e) {
            Response response = Response.failed(e.getMessage());
            return response;
        }
    }
}
