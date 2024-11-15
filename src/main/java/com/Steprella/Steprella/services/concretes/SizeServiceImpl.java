package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.services.abstracts.SizeService;
import com.Steprella.Steprella.services.dtos.responses.sizes.ListSizeResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SizeServiceImpl implements SizeService {

    @Override
    public List<ListSizeResponse> getAll() {
        return List.of();
    }
}
