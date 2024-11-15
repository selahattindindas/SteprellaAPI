package com.Steprella.Steprella.services.abstracts;

import com.Steprella.Steprella.services.dtos.responses.sizes.ListSizeResponse;

import java.util.List;

public interface SizeService {

    List<ListSizeResponse> getAll();
}
