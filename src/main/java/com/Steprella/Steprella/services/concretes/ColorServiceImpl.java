package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.services.abstracts.ColorService;
import com.Steprella.Steprella.services.dtos.responses.colors.ListColorResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ColorServiceImpl implements ColorService {

    @Override
    public List<ListColorResponse> getAll() {
        return List.of();
    }
}
