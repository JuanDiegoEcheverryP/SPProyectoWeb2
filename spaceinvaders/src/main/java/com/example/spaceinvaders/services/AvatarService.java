package com.example.spaceinvaders.services;

import java.util.List;

import com.example.spaceinvaders.model.Avatar;
import com.example.spaceinvaders.repository.AvatarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvatarService {
    @Autowired
    private AvatarRepository avatarRepository;

    public List<Avatar> listarAvatars() {
        return avatarRepository.findAll();
    }
}
