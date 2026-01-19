package com.sanjeev.pulse.user;

import com.sanjeev.pulse.user.dto.CreateUserRequest;
import com.sanjeev.pulse.user.dto.UserResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sanjeev.pulse.post.PostService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repo;

    @Transactional
    public UserResponse create(CreateUserRequest req) {
        String normalizedHandle = normalizeHandle(req.handle());
        if (repo.existsByHandleIgnoreCase(normalizedHandle)) {
            throw new ResourceConflictException("Handle already exists: " + normalizedHandle);
        }
        User saved = repo.save(new User(normalizedHandle, req.displayName().trim()));
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public UserResponse getById(Long userId) {
        User user = repo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));
        return toResponse(user);
    }

    @Transactional(readOnly = true)
    public User requireUser(Long userId) {
        return repo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));
    }

    private static UserResponse toResponse(User u) {
        return new UserResponse(u.getId(), u.getHandle(), u.getDisplayName());
    }

    private static String normalizeHandle(String handle) {
        String h = handle.trim();
        if (h.startsWith("@")) h = h.substring(1);
        return h;
    }

    public User getUser(@NotNull Long userId) {
        return repo.findById(userId).orElse(null);
    }
}
