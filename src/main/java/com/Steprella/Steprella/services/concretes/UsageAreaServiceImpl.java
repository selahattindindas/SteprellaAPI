package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.core.utils.exceptions.types.NotFoundException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.entities.concretes.UsageArea;
import com.Steprella.Steprella.repositories.UsageAreaRepository;
import com.Steprella.Steprella.services.abstracts.UsageAreaService;
import com.Steprella.Steprella.services.dtos.requests.usageareas.AddUsageAreaRequest;
import com.Steprella.Steprella.services.dtos.requests.usageareas.UpdateUsageAreaRequest;
import com.Steprella.Steprella.services.dtos.responses.usageareas.AddUsageAreaResponse;
import com.Steprella.Steprella.services.dtos.responses.usageareas.ListUsageAreaResponse;
import com.Steprella.Steprella.services.dtos.responses.usageareas.UpdateUsageAreaResponse;
import com.Steprella.Steprella.services.mappers.UsageAreaMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UsageAreaServiceImpl implements UsageAreaService {

    private final UsageAreaRepository usageAreaRepository;

    @Override
    public List<ListUsageAreaResponse> getAll(Integer page, Integer size) {
        List<UsageArea> usageAreas;

        if (page != null && size != null) {
            Pageable pageable = PageRequest.of(page, size);
            usageAreas = usageAreaRepository.findAll(pageable).getContent();
        } else {
            usageAreas = usageAreaRepository.findAll();
        }

        return usageAreas.stream()
                .map(UsageAreaMapper.INSTANCE::listResponseFromUsageArea)
                .collect(Collectors.toList());
    }

    @Override
    public ListUsageAreaResponse getById(int id) {
        UsageArea usageArea = findUsageAreaById(id);
        return UsageAreaMapper.INSTANCE.listResponseFromUsageArea(usageArea);
    }

    @Override
    public int getTotalCount() {
        return (int) usageAreaRepository.count();
    }

    @Override
    public AddUsageAreaResponse add(AddUsageAreaRequest request) {
        UsageArea newUsageArea = UsageAreaMapper.INSTANCE.usageAreaFromAddRequest(request);
        UsageArea savedUsageArea = usageAreaRepository.save(newUsageArea);

        return UsageAreaMapper.INSTANCE.addResponseUsageArea(savedUsageArea);
    }

    @Override
    public UpdateUsageAreaResponse update(UpdateUsageAreaRequest request) {
        findUsageAreaById(request.getId());

        UsageArea updateUsageArea = UsageAreaMapper.INSTANCE.usageAreaFromUpdateRequest(request);
        UsageArea savedUsageArea = usageAreaRepository.save(updateUsageArea);

        return UsageAreaMapper.INSTANCE.updateResponseUsageArea(savedUsageArea);
    }

    @Override
    public void delete(int id) {
        UsageArea usageArea = findUsageAreaById(id);
        usageAreaRepository.delete(usageArea);
    }

    private UsageArea findUsageAreaById(int id) {
        return usageAreaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Messages.Error.CUSTOM_USAGE_AREA_NOT_FOUND));
    }
}
