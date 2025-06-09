package com.agbafune.tradesys.web;

import com.agbafune.tradesys.domain.repository.AssetRepository;
import com.agbafune.tradesys.web.model.AssetBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(
        path = "/api/assets",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class AssetResource {

    @Autowired
    private AssetRepository repo;

    @GetMapping
    public List<AssetBean> getAllAssets() {
        return repo.findAll()
                .stream()
                .map(AssetBean::new)
                .toList();
    }
}
