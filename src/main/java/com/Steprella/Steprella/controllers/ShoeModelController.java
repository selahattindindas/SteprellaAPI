package com.Steprella.Steprella.controllers;

import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.ShoeModelService;
import com.Steprella.Steprella.services.dtos.requests.shoemodels.AddShoeModelRequest;
import com.Steprella.Steprella.services.dtos.requests.shoemodels.UpdateShoeModelRequest;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.shoemodels.AddShoeModelResponse;
import com.Steprella.Steprella.services.dtos.responses.shoemodels.ListShoeModelResponse;
import com.Steprella.Steprella.services.dtos.responses.shoemodels.UpdateShoeModelResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shoe-models")
@RequiredArgsConstructor
public class ShoeModelController extends BaseController{

    private final ShoeModelService shoeModelService;

    @GetMapping("/get-all")
    public ResponseEntity<BaseResponse<List<ListShoeModelResponse>>> getAll(){
        List<ListShoeModelResponse> models = shoeModelService.getAll();
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, models);
    }

    @GetMapping("/by-brand-id/{brandId}")
    public ResponseEntity<BaseResponse<List<ListShoeModelResponse>>> getShoeModelByBrandId(@PathVariable int brandId){
        List<ListShoeModelResponse> models = shoeModelService.getShoeModelsByBrandId(brandId);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, models);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ListShoeModelResponse>> getById(@PathVariable int id){
        ListShoeModelResponse model = shoeModelService.getById(id);
        if(model == null)
            return sendResponse(HttpStatus.NOT_FOUND, Messages.Error.CUSTOM_SHOE_MODEL_NOT_FOUND, null);
        else
            return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, model);
    }

    @PostMapping("/create-shoe-model")
    public ResponseEntity<BaseResponse<AddShoeModelResponse>> add(@RequestBody @Valid AddShoeModelRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST, null);
        }

        AddShoeModelResponse addModelResponse = shoeModelService.add(request);
        return sendResponse(HttpStatus.CREATED, Messages.Success.CUSTOM_SUCCESSFULLY, addModelResponse);
    }

    @PutMapping("/update-shoe-model")
    public ResponseEntity<BaseResponse<UpdateShoeModelResponse>> update(@RequestBody @Valid UpdateShoeModelRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST, null);
        }

        if(shoeModelService.getById(request.getId()) == null){
            return sendResponse(HttpStatus.NOT_FOUND, Messages.Error.CUSTOM_SHOE_MODEL_NOT_FOUND, null);
        }

        UpdateShoeModelResponse updateModelResponse = shoeModelService.update(request);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, updateModelResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>>delete(@PathVariable int id) {
        shoeModelService.delete(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, null);
    }
}
