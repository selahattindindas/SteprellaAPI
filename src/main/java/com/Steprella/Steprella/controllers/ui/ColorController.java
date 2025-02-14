package com.Steprella.Steprella.controllers.ui;

import com.Steprella.Steprella.controllers.BaseController;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.ColorService;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.colors.ListColorResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/colors")
@PreAuthorize("permitAll()")
public class ColorController extends BaseController {

    private final ColorService colorService;

    @GetMapping("/get-all")
    public ResponseEntity<BaseResponse<List<ListColorResponse>>> getAll(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        List<ListColorResponse> colors;
        int totalCount;

        if (page == null || size == null) {
            colors = colorService.getAll(null, null);
            totalCount = colors.size();
        } else {
            colors = colorService.getAll(page, size);
            totalCount = colorService.getTotalCount();
        }

        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, colors, totalCount);
    }
}
