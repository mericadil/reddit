package com.example.reddit.service;

import com.example.reddit.DTO.TopicDTO;
import com.example.reddit.entity.Topic;
import com.example.reddit.mapper.TopicMapper;
import com.example.reddit.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;

    public TopicDTO save(TopicDTO topicDTO) {
        Topic savedTopic = topicRepository.save(topicMapper.mapDtoToEntity(topicDTO));
        topicDTO.setId(savedTopic.getId());
        return topicDTO;
    }

    @Transactional(readOnly = true)
    public List<TopicDTO> getAllTopics() {
        return topicRepository.findAll()
                .stream()
                .map(topicMapper::mapTopicToDto)
                .collect(toList());
    }

    public TopicDTO getTopic(Long id) {
        Optional<Topic> optionalTopic = topicRepository.findById(id);
        if( optionalTopic.isPresent()) {
            return topicMapper.mapTopicToDto(optionalTopic.get());
        }
        throw new EntityNotFoundException();
    }
}
