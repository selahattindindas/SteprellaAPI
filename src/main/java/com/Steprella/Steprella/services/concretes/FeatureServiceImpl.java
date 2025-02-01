package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.core.utils.exceptions.types.NotFoundException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.entities.concretes.Feature;
import com.Steprella.Steprella.repositories.FeatureRepository;
import com.Steprella.Steprella.services.abstracts.FeatureService;
import com.Steprella.Steprella.services.dtos.requests.features.AddFeatureRequest;
import com.Steprella.Steprella.services.dtos.requests.features.UpdateFeatureRequest;
import com.Steprella.Steprella.services.dtos.responses.features.AddFeatureResponse;
import com.Steprella.Steprella.services.dtos.responses.features.ListFeatureResponse;
import com.Steprella.Steprella.services.dtos.responses.features.UpdateFeatureResponse;
import com.Steprella.Steprella.services.mappers.FeatureMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FeatureServiceImpl implements FeatureService {

    private final FeatureRepository featureRepository;

    @Override
    public List<ListFeatureResponse> getAll(Integer page, Integer size) {
        List<Feature> features;

        if (page != null && size != null) {
            Pageable pageable = PageRequest.of(page, size);
            features = featureRepository.findAll(pageable).getContent();
        } else {
            features = featureRepository.findAll();
        }

        return features.stream()
                .map(FeatureMapper.INSTANCE::listResponseFromFeature)
                .collect(Collectors.toList());
    }

    @Override
    public ListFeatureResponse getById(int id) {
        Feature feature = findFeatureById(id);
        return FeatureMapper.INSTANCE.listResponseFromFeature(feature);
    }

    @Override
    public int getTotalCount() {
        return (int) featureRepository.count();
    }

    @Override
    public AddFeatureResponse add(AddFeatureRequest request) {
        Feature newFeature = FeatureMapper.INSTANCE.featureFromAddRequest(request);
        Feature savedFeature = featureRepository.save(newFeature);

        return FeatureMapper.INSTANCE.addResponseFeature(savedFeature);
    }

    @Override
    public UpdateFeatureResponse update(UpdateFeatureRequest request) {
        findFeatureById(request.getId());

        Feature updateFeature = FeatureMapper.INSTANCE.featureFromUpdateRequest(request);
        Feature savedFeature = featureRepository.save(updateFeature);

        return FeatureMapper.INSTANCE.updateResponseFeature(savedFeature);
    }

    @Override
    public void delete(int id) {
        Feature feature = findFeatureById(id);
        featureRepository.delete(feature);
    }

    private Feature findFeatureById(int id) {
        return featureRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Messages.Error.CUSTOM_FEATURE_NOT_FOUND));
    }
}
