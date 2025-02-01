package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.core.utils.exceptions.types.NotFoundException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.entities.concretes.Material;
import com.Steprella.Steprella.repositories.MaterialRepository;
import com.Steprella.Steprella.services.abstracts.MaterialService;
import com.Steprella.Steprella.services.dtos.requests.materials.AddMaterialRequest;
import com.Steprella.Steprella.services.dtos.requests.materials.UpdateMaterialRequest;
import com.Steprella.Steprella.services.dtos.responses.materials.AddMaterialResponse;
import com.Steprella.Steprella.services.dtos.responses.materials.ListMaterialResponse;
import com.Steprella.Steprella.services.dtos.responses.materials.UpdateMaterialResponse;
import com.Steprella.Steprella.services.mappers.MaterialMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepository;

    @Override
    public List<ListMaterialResponse> getAll(Integer page, Integer size) {
        List<Material> materials;

        if (page != null && size != null) {
            Pageable pageable = PageRequest.of(page, size);
            materials = materialRepository.findAll(pageable).getContent();
        } else {
            materials = materialRepository.findAll();
        }

        return materials.stream()
                .map(MaterialMapper.INSTANCE::listResponseFromMaterial)
                .collect(Collectors.toList());
    }

    @Override
    public ListMaterialResponse getById(int id) {
        Material material = findMaterialById(id);
        return MaterialMapper.INSTANCE.listResponseFromMaterial(material);
    }

    @Override
    public int getTotalCount() {
        return (int) materialRepository.count();
    }

    @Override
    public AddMaterialResponse add(AddMaterialRequest request) {
        Material newMaterial = MaterialMapper.INSTANCE.materialFromAddRequest(request);
        Material savedMaterial = materialRepository.save(newMaterial);

        return MaterialMapper.INSTANCE.addResponseMaterial(savedMaterial);
    }

    @Override
    public UpdateMaterialResponse update(UpdateMaterialRequest request) {
        findMaterialById(request.getId());

        Material updateMaterial = MaterialMapper.INSTANCE.materialFromUpdateRequest(request);
        Material savedMaterial = materialRepository.save(updateMaterial);

        return MaterialMapper.INSTANCE.updateResponseMaterial(savedMaterial);
    }

    @Override
    public void delete(int id) {
        Material material = findMaterialById(id);
        materialRepository.delete(material);
    }

    private Material findMaterialById(int id) {
        return materialRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Messages.Error.CUSTOM_MATERIAL_NOT_FOUND));
    }
}
