public String register(RegisterRequest request){

    Optional<User> existingUser =
            userRepository.findByEmail(request.getEmail());

    if(existingUser.isPresent()){

        throw new RuntimeException("Email already exists");

    }

    Role role = roleRepository
            .findByName("EMPLOYEE")
            .orElseThrow();

    String encodedPassword =
            passwordEncoder.encode(request.getPassword());

    User user = new User();

    user.setName(request.getName());

    user.setEmail(request.getEmail());

    user.setPassword(encodedPassword);

    user.setRole(role);

    user.setCreatedAt(LocalDateTime.now());

    userRepository.save(user);

    return "User Registered Successfully";
}