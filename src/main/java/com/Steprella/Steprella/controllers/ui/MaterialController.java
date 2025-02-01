package com.Steprella.Steprella.controllers.ui;

import com.Steprella.Steprella.controllers.BaseController;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.MaterialService;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.materials.ListMaterialResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/materials")
@PreAuthorize("permitAll()")
public class MaterialController extends BaseController {

    private final MaterialService materialService;

    @GetMapping("/get-all")
    public ResponseEntity<BaseResponse<List<ListMaterialResponse>>> getAll(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        List<ListMaterialResponse> materials;
        int totalCount;

        if (page == null || size == null) {
            materials = materialService.getAll(null, null);
            totalCount = materials.size();
        } else {
            materials = materialService.getAll(page, size);
            totalCount = materialService.getTotalCount();
        }

        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, materials, totalCount);
    }


    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ListMaterialResponse>> getById(@PathVariable int id){
        ListMaterialResponse material = materialService.getById(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, material);
    }
}
