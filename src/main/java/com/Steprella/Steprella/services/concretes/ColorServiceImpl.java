package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.core.utils.exceptions.types.NotFoundException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.entities.concretes.Color;
import com.Steprella.Steprella.repositories.ColorRepository;
import com.Steprella.Steprella.services.abstracts.ColorService;
import com.Steprella.Steprella.services.dtos.responses.colors.ListColorResponse;
import com.Steprella.Steprella.services.mappers.ColorMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ColorServiceImpl implements ColorService {

    private ColorRepository colorRepository;

    @Override
    public List<ListColorResponse> getAll() {
        List<Color> colors = colorRepository.findAll();
        return colors.stream().map(ColorMapper.INSTANCE::listResponseFromColor).collect(Collectors.toList());
    }

    @Override
    public ListColorResponse getById(int id) {
        Color color = colorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Messages.Error.CUSTOM_COLOR_NOT_FOUND));

        return ColorMapper.INSTANCE.listResponseFromColor(color);
    }
}
