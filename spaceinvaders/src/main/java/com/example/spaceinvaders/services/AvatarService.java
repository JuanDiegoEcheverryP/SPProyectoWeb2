package com.example.spaceinvaders.services;

import com.example.spaceinvaders.model.Avatar;
import com.example.spaceinvaders.repository.AvatarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AvatarService {

    private final AvatarRepository avatarRepository;

    @Autowired
    public AvatarService(AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
    }

    public List<Avatar> getAll() {
        return avatarRepository.findAll();
    }

    public Avatar getById(Long id) {
        Optional<Avatar> optionalAvatar = avatarRepository.findById(id);
        return optionalAvatar.orElse(null);
    }

    public Avatar create(Avatar avatar) {
        return avatarRepository.save(avatar);
    }

    public Avatar update(Long id, Avatar newAvatarData) {
        Optional<Avatar> optionalAvatar = avatarRepository.findById(id);
        if (optionalAvatar.isPresent()) {
            Avatar existingAvatar = optionalAvatar.get();
            existingAvatar.setNombre(newAvatarData.getNombre());
            existingAvatar.setImagen(newAvatarData.getImagen());
            return avatarRepository.save(existingAvatar);
        } else {
            return null;
        }
    }

    public void delete(Long id) {
        avatarRepository.deleteById(id);
    }
}
