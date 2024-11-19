package com.Steprella.Steprella.services.abstracts;

import com.Steprella.Steprella.services.dtos.responses.colors.ListColorResponse;

import java.util.List;

public interface ColorService {

    List<ListColorResponse> getAll();

    ListColorResponse getById(int id);
}
